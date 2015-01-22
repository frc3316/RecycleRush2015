package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;

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
	
	protected double getLeftY ()
	{
		if (invertY)
		{
			return -joystickLeft.getY();
		}
		return joystickLeft.getY();
	}
	
	protected double getLeftX ()
	{
		if (invertX)
		{
			return -joystickLeft.getX();
		}
		return joystickLeft.getX();
	}
	
	protected double getRightY ()
	{
		if (invertY)
		{
			return -joystickRight.getY();
		}
		return joystickRight.getY(); 
	}
	
	protected double getRightX() 
	{
		if (invertX)
		{
			return -joystickRight.getX();
		}
		return joystickRight.getX();
	}
}
