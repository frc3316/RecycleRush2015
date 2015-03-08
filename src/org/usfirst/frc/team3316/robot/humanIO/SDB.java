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
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedNavigation;
import org.usfirst.frc.team3316.robot.chassis.heading.SetHeadingPreset;
import org.usfirst.frc.team3316.robot.chassis.heading.SetHeadingSDB;
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedDrive;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.sequences.AutonomousSequence;
import org.usfirst.frc.team3316.robot.sequences.SweepContainerSequence;
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
		
		initSDB();
		
		if (Robot.sensors.isCameraFound())
		{
			frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		}
	}
	
	public void timerInit ()
	{
		updateSDBTask = new UpdateSDBTask();
		Robot.timer.schedule(updateSDBTask, 0, 100);
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
		SmartDashboard.putData(new UpdateVariablesInConfig()); //NEVER REMOVE THIS COMMAND
		
		/*
		 * Basic Drives
		 */
		SmartDashboard.putData(new RobotOrientedDrive());
		SmartDashboard.putData(new FieldOrientedDrive());
		
		//AutonomousSequence
		SmartDashboard.putData(new AutonomousSequence());
		
		SmartDashboard.putData(new SweepContainerSequence());
		
		SmartDashboard.putData("Auton Movement Segment 1", new RobotOrientedNavigation(0, 0, -45, 1));
		SmartDashboard.putData("Auton Movement Segment 2", new RobotOrientedNavigation(0, 0, 45, 2));
		SmartDashboard.putData("Auton Movement Segment 3", new RobotOrientedNavigation(0, 2.06, 0, 3));
		SmartDashboard.putData("Auton Movement Segment 4", new RobotOrientedNavigation(3, 0, 90, 4));
		
		/*
		 * Set Heading SDB
		 */
		SmartDashboard.putData("Zero Heading", new SetHeadingPreset(0));
		
		SmartDashboard.putData(new SetHeadingSDB());
		putConfigVariableInSDB("chassis_HeadingToSet");
		
		/*
		 * Robot Oriented Navigation
		 */
		//Set 1
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_KP_1");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_KI_1");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_KD_1");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_KP_1");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_KI_1");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_KD_1");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_KP_1");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_KI_1");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_KD_1");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_AbsoluteTolerance_1");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_AbsoluteTolerance_1");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_AbsoluteTolerance_1");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_MinimumOutput_1");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_MaximumOutput_1");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_MinimumOutput_1");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_MaximumOutput_1");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_MinimumOutput_1");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_MaximumOutput_1");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_MaxFinishCounter_1");

		
		//Set 2
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_KP_2");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_KI_2");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_KD_2");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_KP_2");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_KI_2");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_KD_2");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_KP_2");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_KI_2");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_KD_2");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_AbsoluteTolerance_2");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_AbsoluteTolerance_2");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_AbsoluteTolerance_2");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_MinimumOutput_2");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_MaximumOutput_2");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_MinimumOutput_2");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_MaximumOutput_2");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_MinimumOutput_2");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_MaximumOutput_2");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_MaxFinishCounter_2");
		
		//Set 3
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_KP_3");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_KI_3");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_KD_3");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_KP_3");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_KI_3");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_KD_3");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_KP_3");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_KI_3");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_KD_3");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_AbsoluteTolerance_3");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_AbsoluteTolerance_3");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_AbsoluteTolerance_3");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_MinimumOutput_3");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_MaximumOutput_3");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_MinimumOutput_3");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_MaximumOutput_3");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_MinimumOutput_3");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_MaximumOutput_3");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_MaxFinishCounter_3");
		
		//Set 4
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_KP_4");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_KI_4");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_KD_4");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_KP_4");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_KI_4");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_KD_4");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_KP_4");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_KI_4");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_KD_4");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_AbsoluteTolerance_4");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_AbsoluteTolerance_4");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_AbsoluteTolerance_4");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_MinimumOutput_4");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerX_MaximumOutput_4");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_MinimumOutput_4");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerY_MaximumOutput_4");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_MinimumOutput_4");
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_PIDControllerHeading_MaximumOutput_4");
		
		putConfigVariableInSDB("chassis_RobotOrientedNavigation_MaxFinishCounter_4");
		
		logger.info("Finished initSDB()");
	}
}