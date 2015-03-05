/**
 * Class for managing the SmartDashboard data
 */
package org.usfirst.frc.team3316.robot.humanIO;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.FieldOrientedDrive;
import org.usfirst.frc.team3316.robot.chassis.commands.StrafeDrive;
import org.usfirst.frc.team3316.robot.chassis.commands.StartIntegrator;
import org.usfirst.frc.team3316.robot.chassis.commands.TankDrive;
import org.usfirst.frc.team3316.robot.chassis.heading.SetHeadingPreset;
import org.usfirst.frc.team3316.robot.chassis.heading.SetHeadingSDB;
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedDrive;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.SavePictures;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SDB 
{
	/*
	 * Runnable that periodically updates values from the robot into the SmartDashboard
	 * This is the place where all of the robot data should be displayed from
	 */
	private class UpdateSDBTask extends TimerTask
	{
		public UpdateSDBTask ()
		{
			logger.info("Created UpdateSDBTask");
		}
		public void run ()
		{
			/*
			 * Camera
			 */
			if (Robot.sensors.isCameraFound())
			{
				NIVision.IMAQdxGrab(Robot.sensors.getCameraSession(), frame, 1);
				CameraServer.getInstance().setImage(frame);
			}
			/*
			 * Robot Info
			 */
			put ("Current Heading", Robot.chassis.getHeading());
			put ("Current Height", Robot.stacker.getHeight());
			
			put ("Angular Velocity", Robot.chassis.getAngularVelocity());
			put ("Angular Velocity Encoders", Robot.chassis.getAngularVelocityEncoders());
			
			put ("Left Ratchet", Robot.stacker.getSwitchRatchetLeft());
			put ("Right Ratchet", Robot.stacker.getSwitchRatchetRight());
			
			put ("Game Piece Switch", Robot.rollerGripper.getSwitchGamePiece());
			
			put ("Distance Left", Robot.chassis.getDistanceLeft());
			put ("Distance Right", Robot.chassis.getDistanceRight());
			put ("Distance Center", Robot.chassis.getDistanceCenter());
			
			put ("Speed Left", Robot.chassis.getSpeedLeft());
			put ("Speed Right", Robot.chassis.getSpeedRight());
			put ("Speed Center", Robot.chassis.getSpeedCenter());
			
			if (Robot.rollerGripper.getGamePieceCollected() == null)
			{
				put ("Game Piece Collected", null);
			}
			else
			{
				put ("Game Piece Collected", Robot.rollerGripper.getGamePieceCollected().toString());
			}
			
			/*
			 * Integrator testing
			 * should be removed
			 */
			put ("Integrator X", Robot.chassis.navigationIntegrator.getX());
			put ("Integrator Y", Robot.chassis.navigationIntegrator.getY());
			put ("Integrator Heading", Robot.chassis.navigationIntegrator.getHeading());
		}
		
		private void put (String name, double d)
	    {
	    	SmartDashboard.putNumber(name, d);
	    }
	    
	    private void put (String name, int i)
	    {
	    	SmartDashboard.putInt(name, i);
	    }
	    
	    private void put (String name, boolean b)
	    {
	    	SmartDashboard.putBoolean(name, b);
	    }
	    
	    private void put (String name, String s)
	    {
	    	SmartDashboard.putString(name, s);
	    }
	}
	
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	private Image frame;
	
	private UpdateSDBTask updateSDBTask;
	
	private Hashtable <String, Class <?> > variablesInSDB;
	
	public SDB ()
	{
		variablesInSDB = new Hashtable <String, Class <?> > ();
		
		SmartDashboard.putData(new UpdateVariablesInConfig()); //NEVER REMOVE THIS COMMAND
		
		initSDB();
		
		if (Robot.sensors.isCameraFound())
		{
			frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		}
	}
	
	public void timerInit ()
	{
		updateSDBTask = new UpdateSDBTask();
		Robot.timer.schedule(updateSDBTask, 0, 20);
	}
	
	/**
	 * Adds a certain key in the config to the SmartDashboard
	 * @param key the key required
	 * @return whether the value was put in the SmartDashboard
	 */
	public boolean putConfigVariableInSDB (String key)
	{
		try
		{
			Object value = config.get(key);
			Class <?> type = value.getClass();
			
			boolean constant = Character.isUpperCase(key.codePointAt(0));
			
			if (type == Double.class)
			{
				SmartDashboard.putNumber(key, (double) value);
			}
			else if (type == Integer.class)
			{
				SmartDashboard.putNumber(key, (int) value);
			}
			else if (type == Boolean.class)
			{
				SmartDashboard.putBoolean(key, (boolean) value);
			}
			
			if (!constant)
			{
				variablesInSDB.put(key, type);
				logger.info("Added to SDB " + key + " of type " + type + 
						"and allows for its modification");
			}
			else
			{
				logger.info("Added to SDB " + key + " of type " + type + 
						"BUT DOES NOT ALLOW for its modification");
			}
			
			return true;
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
		return false;
	}
	
	public Set <Entry <String, Class <?> > > getVariablesInSDB ()
	{
		return variablesInSDB.entrySet();
	}
	
	private void initSDB ()
	{	
		SmartDashboard.putData(new TankDrive());
		SmartDashboard.putData(new StrafeDrive());
		SmartDashboard.putData(new RobotOrientedDrive());
		SmartDashboard.putData(new FieldOrientedDrive());
		SmartDashboard.putData(new StartIntegrator()); //For integrator testing, should be removed
		SmartDashboard.putData(new SavePictures());
		/*
		 * Set Heading SDB
		 */
		SmartDashboard.putData("Zero Heading", new SetHeadingPreset(0));
		
		SmartDashboard.putData(new SetHeadingSDB());
		putConfigVariableInSDB("chassis_HeadingToSet");
		
		/*
		 * Roller Gripper
		 */
		putConfigVariableInSDB("rollerGripper_RollJoystick_ChannelX");
		putConfigVariableInSDB("rollerGripper_RollJoystick_ChannelY");
		
		putConfigVariableInSDB("rollerGripper_RollJoystick_InvertX");
		putConfigVariableInSDB("rollerGripper_RollJoystick_InvertY");
		
		putConfigVariableInSDB("rollerGripper_RollJoystick_LowPass");
		
		//Game Piece IR
		putConfigVariableInSDB("rollerGripper_ToteDistanceMinimum");
		putConfigVariableInSDB("rollerGripper_ToteDistanceMaximum");
		
		putConfigVariableInSDB("rollerGripper_ContainerDistanceMinimum");
		putConfigVariableInSDB("rollerGripper_ContainerDistanceMaximum");
		
		putConfigVariableInSDB("rollerGripper_SomethingDistanceThreshold");
		putConfigVariableInSDB("rollerGripper_UnsureDistanceThreshold");
		
		/*
		 * Stacker
		 */
		SmartDashboard.putData(Robot.stacker);
		
		putConfigVariableInSDB("stacker_HeightFloorMinimum");
		putConfigVariableInSDB("stacker_HeightFloorMaximum");
		
		putConfigVariableInSDB("stacker_HeightToteMinimum");
		putConfigVariableInSDB("stacker_HeightToteMaximum");
		
		putConfigVariableInSDB("stacker_HeightStepMinimum");
		putConfigVariableInSDB("stacker_HeightStepMaximum");
		
		
		logger.info("Finished initSDB()");
	}
}