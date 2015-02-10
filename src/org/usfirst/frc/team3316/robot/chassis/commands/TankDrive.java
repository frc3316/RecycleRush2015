package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

import edu.wpi.first.wpilibj.Joystick;

public class TankDrive extends Drive 
{
	protected Joystick joystickLeft, joystickRight;
	
	boolean invertY, invertX;
	
	double lowPass = 0.0;
	
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
		updateConfigVariables();
		double y = lowPass(joystickLeft.getY());
		if (invertY)
		{
			return -y;
		}
		return y;
	}
	
	protected double getLeftX ()
	{
		updateConfigVariables();
		double x = lowPass(joystickLeft.getX());
		if (invertX)
		{
			return -x;
		}
		return x;
	}
	
	protected double getRightY ()
	{
		updateConfigVariables();
		double y = lowPass(joystickRight.getY());
		if (invertY)
		{
			return -y;
		}
		return y; 
	}
	
	protected double getRightX() 
	{
		updateConfigVariables();
		double x = lowPass(joystickRight.getX());
		if (invertX)
		{
			return -x;
		}
		return x;
	}
	
	private void updateConfigVariables ()
	{
		try
		{
			lowPass = (double)config.get("chassis_TankDrive_LowPass");
			
			invertX = (boolean)config.get("chassis_TankDrive_InvertX");
			invertY = (boolean)config.get("chassis_TankDrive_InvertY");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
	
	private double lowPass (double x)
	{
		if (Math.abs(x) < lowPass)
		{
			return 0;
		}
		return x;
	}
}
