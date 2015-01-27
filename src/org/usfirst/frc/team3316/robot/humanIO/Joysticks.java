/**
 * Class for joysticks and joystick buttons
 */
package org.usfirst.frc.team3316.robot.humanIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.RollIn;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.RollOut;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Joysticks 
{
	/*
	 * Defines a button in a gamepad POV for an array of angles 
	 */
	private class WidePOVButton extends Button
	{
		Joystick m_joystick;
		int [] m_deg;
		public WidePOVButton (Joystick joystick, int [] deg)
		{
			m_joystick = joystick;
			m_deg = deg;
		}
		
		public boolean get()
		{
			for (int i = 0; i < m_deg.length; i++)
			{
				if (m_joystick.getPOV() == m_deg[i])
				{
					return true;
				}
			}
			return false;
		}
	}
	
	
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
	public WidePOVButton buttonRollIn, buttonRollOut;
	
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
	    	buttonRollIn = new WidePOVButton(joystickOperator, (int[]) config.get("JOYSTICKS_BUTTON_ROLL_IN"));
	    	buttonRollIn.whileHeld(new RollIn());
	    	buttonRollOut = new WidePOVButton(joystickOperator, (int[]) config.get("JOYSTICKS_BUTTON_ROLL_OUT"));
	    	buttonRollOut.whileHeld(new RollOut());
    	} 
    	catch (ConfigException e) 
    	{
    		//CR: two things: first use the logger to log the stack trace, second a config exception is bad. it can't be level INFO.
			e.printStackTrace();
			logger.severe(e.getMessage());
		}
	}
}
