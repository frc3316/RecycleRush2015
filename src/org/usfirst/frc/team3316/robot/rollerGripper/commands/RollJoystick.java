package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 */
public class RollJoystick extends Roll
{
	Joystick joystick = Robot.joysticks.joystickOperator;
	
	/*
	 * These are all set in updateConfigVariables()
	 */
	private int channelX, channelY;
	private boolean invertX, invertY;
	private double lowPass;
	
	protected void setSpeeds() 
	{
		double x = getX();
		double y = getY();
		
		/*
		 * Sets left and right so that:
		 * (right - left) = 2x
		 * (right + left) = 2y
		 * -1 <= x, y <= 1 
		 */
		
		this.left = (y - x);
		this.right = (y + x);
		
		double max = Math.max(this.left, this.right);
		double min = Math.min(this.left, this.right);
		
		double excess = 0;
		if (max > 1)
		{
			excess = (max - 1);
		}
		else if (min < -1)
		{
			excess = (min + 1);
		}
		
		this.left -= excess;
		this.right -= excess;
	}
	
	private double getX ()
	{
		updateConfigVariables();
		
		double x = lowPass(joystick.getRawAxis(channelX));
		if (invertX)
		{
			return -x;
		}
		return x;
	}
	
	private double getY ()
	{
		updateConfigVariables();
		
		double y = lowPass(joystick.getRawAxis(channelY));
		if (invertY)
		{
			return -y;
		}
		return y;
	}
	
	private double lowPass (double x)
	{
		if (Math.abs(x) < lowPass)
		{
			return 0;
		}
		return x;
	}
	
	private void updateConfigVariables ()
	{
		try 
		{
			channelX = (int) config.get("rollerGripper_RollJoystick_ChannelX");
			channelY = (int) config.get("rollerGripper_RollJoystick_ChannelY");
			
			invertX = (boolean) config.get("rollerGripper_RollJoystick_InvertX");
			invertY = (boolean) config.get("rollerGripper_RollJoystick_InvertY");
			
			lowPass = (double) config.get("rollerGripper_RollJoystick_LowPass");
		} 
		catch (ConfigException e) 
		{
			logger.severe(e);
		}
	}
}
