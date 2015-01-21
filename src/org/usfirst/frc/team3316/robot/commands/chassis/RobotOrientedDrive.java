package org.usfirst.frc.team3316.robot.commands.chassis;

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
		setCartisianVector(joystickRight.getX(), -joystickRight.getY());
		setRotation(joystickLeft.getX());
	}
	
	protected void setCartisianVector (double x, double y)
	{
		this.left = y;
		this.right = y;
		this.center = x;
	}
	
	protected void setRotation (double requiredTurn)
	{
		if (requiredTurn == 0) //if the robot shouldn't turn does nothing
		{
			return;
		}
		
		double innerVelocity, outerVelocity;
		
		if (requiredTurn > 0) //if the robot needs to turn clockwise, sets left to outer and right to inner
		{
			outerVelocity = this.left;
			innerVelocity = this.right;
		}
		else //if the robot needs to turn counter clockwise, sets right to outer and left to inner
		{
			outerVelocity = this.right;
			innerVelocity = this.left;
		}
		
		double maxSpeed = 1, minSpeed = -1; //sets defaults in case they're not configured in Config
		try 
		{
			maxSpeed = (double)config.get("driveMaxSpeed");
			minSpeed = (double)config.get("driveMinSpeed");
		} 
		catch (ConfigException e) 
		{
			logger.info(e.getMessage());
		}
		/*
		 * calculates inner and outer velocities so they're in the range of (minSpeed to maxSpeed)
		 */
		if ((outerVelocity + requiredTurn) > maxSpeed)
		{
			outerVelocity = maxSpeed;
			innerVelocity = Math.max(maxSpeed - requiredTurn*2, minSpeed);
		}
		else if ((innerVelocity - requiredTurn) < minSpeed)
		{
			innerVelocity = minSpeed;
			outerVelocity = Math.min(minSpeed + requiredTurn*2, maxSpeed);
		}
		else
		{
			outerVelocity = outerVelocity + requiredTurn;
			outerVelocity = innerVelocity - requiredTurn;
		}
		
		if (requiredTurn > 0) //if the robot needs to turn clockwise, sets outer to left and inner to right
		{
			this.left = outerVelocity;
			this.right = innerVelocity;
		}
		else //if the robot needs to turn counter clockwise, sets outer to right and inner to left
		{
			this.right = outerVelocity;
			this.left = innerVelocity;
		}
	}
}
