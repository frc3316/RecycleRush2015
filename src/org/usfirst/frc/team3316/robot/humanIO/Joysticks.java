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
import org.usfirst.frc.team3316.robot.stacker.commands.HoldContainer;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToFloor;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToStep;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToTote;
import org.usfirst.frc.team3316.robot.stacker.commands.ReleaseContainer;

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
	public WidePOVButton buttonRollIn, 
						 buttonRollOut, 
						 buttonRollTurnClockwise, 
						 buttonRollTurnCounterClockwise;
	
	public JoystickButton moveStackerToFloor, 
						  moveStackerToStep, 
						  moveStackerToTote,
						  holdContainer,
						  releaseContainer;
	
	public Joysticks ()
	{
		try 
    	{
			/*
			 * Joysticks
			 */
			joystickLeft = new Joystick((int) config.get("JOYSTICK_LEFT"));
			joystickRight = new Joystick((int) config.get("JOYSTICK_RIGHT"));
	    	joystickOperator = new Joystick((int) config.get("JOYSTICK_OPERATOR"));
    	} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
	}
	
	public void initButtons ()
	{
		/*
    	 * Joystick Buttons
    	 */
		try
		{
			/*
			 * Anschluss
			 */
	    	openAnschlussButton = new JoystickButton(joystickOperator, 
	    			(int) config.get("openAnschlussButton"));
	    	closeAnschlussButton = new JoystickButton(joystickOperator, 
	    			(int) config.get("closeAnschlussButton"));
	    	
	    	/*
	    	 * Roller Gripper
	    	 */
	    	buttonRollIn = new WidePOVButton(joystickOperator, 
	    			(int[]) config.get("JOYSTICKS_BUTTON_ROLL_IN"));
	    	buttonRollIn.whileHeld(new RollIn());
	    	
	    	buttonRollOut = new WidePOVButton(joystickOperator, 
	    			(int[]) config.get("JOYSTICKS_BUTTON_ROLL_OUT"));
	    	buttonRollOut.whileHeld(new RollOut());
	    	
	    	buttonRollTurnClockwise = new WidePOVButton(joystickOperator, 
	    			(int[]) config.get("JOYSTICKS_BUTTON_ROLL_TURN_CLOCKWISE"));
	    	buttonRollTurnClockwise.whileHeld(new RollTurnClockwise());
	    	
	    	buttonRollTurnCounterClockwise = new WidePOVButton(joystickOperator, 
	    			(int[]) config.get("JOYSTICKS_BUTTON_ROLL_TURN_COUNTER_CLOCKWISE"));
	    	buttonRollTurnCounterClockwise.whileHeld(new RollTurnCounterClockwise());
	    	
	    	/*
	    	 * Stacker
	    	 */
	    	moveStackerToFloor = new JoystickButton(joystickOperator, 
 					(int) config.get("MOVE_STACKER_TO_FLOOR_BUTTON"));
			moveStackerToFloor.whenPressed(new MoveStackerToFloor());
			
			moveStackerToStep = new JoystickButton(joystickOperator, 
					(int) config.get("MOVE_STACKER_TO_STEP_BUTTON"));
			moveStackerToStep.whenPressed(new MoveStackerToStep());
			
			moveStackerToTote = new JoystickButton(joystickOperator, 
					(int) config.get("MOVE_STACKER_TO_TOTE_BUTTON"));
			moveStackerToTote.whenPressed(new MoveStackerToTote());
			
			holdContainer = new JoystickButton(joystickOperator,
					(int) config.get("HOLD_CONTAINER_BUTTON"));
			holdContainer.whenPressed(new HoldContainer());
			
			releaseContainer = new JoystickButton(joystickOperator,
					(int) config.get("RELEASE_CONTAINER_BUTTON"));
			releaseContainer.whenPressed(new ReleaseContainer());
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}
