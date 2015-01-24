/**
 * Class for joysticks and joystick buttons
 */
package org.usfirst.frc.team3316.robot.humanIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Joysticks 
{
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
	public JoystickButton openAnschlussButton;
	public JoystickButton closeAnschlussButton;
	
	public Joysticks ()
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
	    	openAnschlussButton = new JoystickButton(joystickOperator, (int) config.get("openAnschlussButton"));
	    	closeAnschlussButton = new JoystickButton(joystickOperator, (int) config.get("closeAnschlussButton"));
    	} 
    	catch (ConfigException e) 
    	{
    		//CR: use the logger to log the stack trace
			e.printStackTrace();
			logger.severe(e.getMessage());
		}
	}
}
