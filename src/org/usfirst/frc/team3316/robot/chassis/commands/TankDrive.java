package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

import edu.wpi.first.wpilibj.Joystick;

public class TankDrive extends Drive 
{
	protected Joystick joystickLeft, joystickRight;
	
	boolean invertY, invertX;
	
	public TankDrive ()
	{
		super();
		joystickLeft = Robot.joysticks.joystickLeft;
		joystickRight = Robot.joysticks.joystickRight;
	}
	
	protected void set() 
	{
		left = getLeftY();
		right = getRightY();
	}
	
	private void updateInverts ()
	{
		try
		{
			invertX = (boolean)config.get("chassis_TankDrive_InvertX");
			invertY = (boolean)config.get("chassis_TankDrive_InvertY");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
	
	protected double getLeftY ()
	{
		updateInverts();
		if (invertY)
		{
			return -joystickLeft.getY();
		}
		return joystickLeft.getY();
	}
	
	protected double getLeftX ()
	{
		updateInverts();
		if (invertX)
		{
			return -joystickLeft.getX();
		}
		return joystickLeft.getX();
	}
	
	protected double getRightY ()
	{
		updateInverts();
		if (invertY)
		{
			return -joystickRight.getY();
		}
		return joystickRight.getY(); 
	}
	
	protected double getRightX() 
	{
		updateInverts();
		if (invertX)
		{
			return -joystickRight.getX();
		}
		return joystickRight.getX();
	}
}
