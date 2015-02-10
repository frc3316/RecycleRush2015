/**
 * Class for managing the SmartDashboard data
 */
package org.usfirst.frc.team3316.robot.humanIO;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.heading.SetHeadingSDB;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import org.usfirst.frc.team3316.robot.stacker.commands.HoldContainer;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToFloor;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToStep;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToTote;
import org.usfirst.frc.team3316.robot.stacker.commands.ReleaseContainer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SDB 
{
	/*
	 * Thread that periodically updates values from the robot into the SmartDashboard
	 * This is the place where all of the robot data should be displayed from
	 */
	private class SDBThread extends Thread
	{
		public synchronized void run ()
		{
			while (true)
			{
				put ("Current Heading", Robot.chassis.getHeading());
				put ("Current Height", Robot.stacker.getHeight());
				
				put ("Angular Velocity", Robot.chassis.getAngularVelocity());
				put ("Angular Velocity Encoders", Robot.chassis.getAngularVelocityEncoders());
				
				try 
				{
					sleep(20);
				} 
				catch (InterruptedException e) 
				{
					logger.severe(e);
				}
			}
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
	}
	
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	private SDBThread thread;
	
	private Hashtable <String, Class <?> > variablesInSDB;
	
	public SDB ()
	{
		variablesInSDB = new Hashtable <String, Class <?> > ();
		
		SmartDashboard.putData(new UpdateVariablesInConfig());
		SmartDashboard.putData(new SetHeadingSDB());
		
		initSDB();
		
		thread = new SDBThread();
		thread.start();
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
		SmartDashboard.putData(new UpdateVariablesInConfig()); // NEVER REMOVE THIS COMMAND
				
		/*
		 * Set Heading SDB
		 */
		SmartDashboard.putData(new SetHeadingSDB());
		putConfigVariableInSDB("chassis_HeadingToSet");
		
		/*
		 * Stacker Testing
		 */
		SmartDashboard.putData(new MoveStackerToFloor());
		SmartDashboard.putData(new MoveStackerToStep());
		SmartDashboard.putData(new MoveStackerToTote());
		
		SmartDashboard.putData(new HoldContainer());
		SmartDashboard.putData(new ReleaseContainer());
	}
}