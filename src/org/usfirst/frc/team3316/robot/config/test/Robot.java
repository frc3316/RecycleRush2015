package org.usfirst.frc.team3316.robot.config.test;


import org.usfirst.frc.team3316.robot.config.test.Config.ConfigException;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot
{
	static DBugLogger logger;
	static Config config;
	static SDB sdb;
	
	public void robotInit()
	{
		logger = new DBugLogger();
		config = new Config();
		sdb = new SDB();
		
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
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {}

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}