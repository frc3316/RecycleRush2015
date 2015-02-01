package org.usfirst.frc.team3316.robot.config.test;

import org.usfirst.frc.team3316.robot.config.test.Config.ConfigException;

public class Robot 
{
	static DBugLogger logger = new DBugLogger();
	static Config config = new Config ();
	
	public static void main (String [] args)
	{
		try 
		{
			logger.fine("JOYSTICK_LEFT toString: " + 
						(config.get("JOYSTICK_LEFT")).toString());
			
			logger.fine("JOYSTICK_LEFT casted to int: " + 
						(int)config.get("JOYSTICK_LEFT"));
			
			logger.fine("chassis_TankDrive_InvertX casted to boolean: " + 
						(boolean)config.get("chassis_TankDrive_InvertX"));
			
			logger.fine("rollerGripper_LeftScale casted to double: " + 
						(double)config.get("rollerGripper_LeftScale"));
			
			//logger.fine("CHEESY_POOFS: " + config.get("CHEESY_POOFS"));
			//Removed this line because it raised a ConfigException
			
			config.add("CHEESY_POOFS", 254);
			logger.fine("Added CHEESY_POOFS: 254");
			logger.fine("CHEESY_POOFS: " + config.get("CHEESY_POOFS"));
			
			config.add("CHEESY_POOFS", 253);
			logger.fine("Added CHEESY_POOFS: 253");
			logger.fine("CHEESY_POOFS: " + config.get("CHEESY_POOFS"));
		}
		catch (ConfigException e) 
		{
			logger.severe(e);
		}
	}
}