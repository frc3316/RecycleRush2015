/**
 * Class for joysticks and joystick buttons
 */
package org.usfirst.frc.team3316.robot.humanIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class Joysticks 
{
	/*
	 * config and logger
	 */
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	/*
	 * Joysticks
	 */
	public Joystick joystickLeft;
	public Joystick joystickRight;
	public Joystick joystickOperator;
	/*
	 * Joystick Buttons
	 */
	public void initJoysticks ()
	{
		try 
    	{
			/*
			 * Joysticks
			 */
			joystickLeft = new Joystick((int) config.get("joystickLeft"));
			joystickRight = new Joystick((int) config.get("joystickRight"));
	    	joystickOperator = new Joystick((int) config.get("joystickOperator"));
	    	/*
	    	 * Joystick Buttons
	    	 */
    	} 
    	catch (ConfigException e) 
    	{
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
}
