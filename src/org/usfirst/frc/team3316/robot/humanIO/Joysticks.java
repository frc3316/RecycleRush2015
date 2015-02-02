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
import org.usfirst.frc.team3316.robot.rollerGripper.commands.RollTurnClockwise;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.RollTurnCounterClockwise;

import edu.wpi.first.wpilibj.Joystick;
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
	public JoystickButton openAnschlussButton;
	public JoystickButton closeAnschlussButton;
	public WidePOVButton buttonRollIn, buttonRollOut, buttonRollTurnClockwise, buttonRollTurnCounterClockwise;
	
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
	    	
	    	buttonRollIn = new WidePOVButton(joystickOperator, (int[]) config.get("JOYSTICKS_BUTTON_ROLL_IN"));
	    	buttonRollIn.whileHeld(new RollIn());
	    	buttonRollOut = new WidePOVButton(joystickOperator, (int[]) config.get("JOYSTICKS_BUTTON_ROLL_OUT"));
	    	buttonRollOut.whileHeld(new RollOut());
	    	buttonRollTurnClockwise = new WidePOVButton(joystickOperator, (int[]) config.get("JOYSTICKS_BUTTON_ROLL_TURN_CLOCKWISE"));
	    	buttonRollTurnClockwise.whileHeld(new RollTurnClockwise());
	    	buttonRollTurnCounterClockwise = new WidePOVButton(joystickOperator, (int[]) config.get("JOYSTICKS_BUTTON_ROLL_TURN_COUNTER_CLOCKWISE"));
	    	buttonRollTurnCounterClockwise.whileHeld(new RollTurnCounterClockwise());
    	} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
	}
}
