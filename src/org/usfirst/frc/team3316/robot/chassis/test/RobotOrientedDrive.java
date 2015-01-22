package org.usfirst.frc.team3316.robot.chassis.test;

public class RobotOrientedDrive extends StrafeDrive 
{
	/*
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	*/
	protected void set ()
	{
		setCartesianVector(joystickRight.getX(), -joystickRight.getY());
		setRotation(joystickLeft.getX());
	}
	
	protected void setCartesianVector (double x, double y)
	{
		this.left = y;
		this.right = y;
		this.center = x;
	}
	
	protected void setRotation (double requiredTurn)
	{
		this.left += requiredTurn;
		this.right -= requiredTurn;
		
		if (this.left > 1)
		{
			this.right -= (this.left - 1);
			this.left = 1;
		}
		else if (this.right > 1)
		{
			this.left -= (this.right - 1);
			this.right = 1;
		}
		else if (this.left < -1)
		{
			this.right += (this.left + 1);
			this.left = -1;
		}
		else if (this.right < -1)
		{
			this.left += (this.right + 1);
			this.right = -1;
		}
	}
}
