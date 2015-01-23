package org.usfirst.frc.team3316.robot.chassis.test;

public class TankDrive extends Drive 
{
	protected Joystick joystickLeft, joystickRight;
	
	boolean invertY, invertX;
	
	public TankDrive ()
	{
		super();
		joystickLeft = Test.joystickLeft;
		joystickRight = Test.joystickRight;
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
