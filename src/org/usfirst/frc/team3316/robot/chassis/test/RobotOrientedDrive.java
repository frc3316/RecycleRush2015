package org.usfirst.frc.team3316.robot.chassis.test;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

public class RobotOrientedDrive extends StrafeDrive 
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	protected void set ()
	{
		setCartesianVector(getRightX(), getRightY());
		setRotation(getLeftX());
	}
	
	protected void setCartesianVector (double x, double y)
	{
		this.left = y;
		this.right = y;
		this.center = x;
	}
	
	protected void setRotation (double requiredTurn)
	{
		//CR: Is this the same function we've demostrated? use the proper naming of outer and inner wheels for readbility
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
