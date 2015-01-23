package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

public class RobotOrientedDrive extends StrafeDrive 
{	
	double maxTurn, minTurn;
	
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
		/*
		 * the following code sets left and right so that:
		 * for turning clockwise, left > right
		 * for turning counter-clockwise, right > left
		 * left - right = requiredTurn*2
		 * -1 < left, right < 1 
		 */
		double yIn = this.left;
		
		// The wheels speed differes by the requiredTurn * 2
		double leftWheel = yIn + requiredTurn;
		double rightWheel = yIn - requiredTurn;
		
		if (Math.signum(yIn) == Math.signum(requiredTurn)) 
		{
			if (leftWheel > 1 || leftWheel < -1) 
			{
				leftWheel = Math.signum(yIn);
				rightWheel = leftWheel + Math.signum(yIn) * Math.abs(requiredTurn);
			}
		} 
		else 
		{
			if (rightWheel > 1 || rightWheel < -1) 
			{
				rightWheel = Math.signum(yIn);
				leftWheel = rightWheel - Math.signum(yIn) * Math.abs(requiredTurn);
			}
		}
			
		
			
		
		// The outer wheel is the one which drives faster (absolutely)
		// (when requiredTurn is 0 then it doesn't matter which is outer because were driving straight)
		// (when yIn is 0 then the outer wheel will be 0, but we fix this later...)
		double outerWheel = Math.max(Math.abs(wheel1), Math.abs(wheel2)) * Math.signum(yIn);
		double innerWheel;
		
		
		if (outerWheel > 0)
		{
			outerWheel = Math.max(outerWheel, 1); //makes sure outerWheel <= 1
			
			if (requiredTurn > 0)
			{
				innerWheel = outerWheel - (requiredTurn*2);
			}
			else
			{
				innerWheel = outerWheel + (requiredTurn*2);
			}
		}
		else
		{
			outerWheel = Math.min(outerWheel, -1); //makes sure outerWheel >= -1
			
			if (requiredTurn < 0)
			{
				innerWheel = outerWheel - (requiredTurn*2);
			}
			else
			{
				innerWheel = outerWheel + (requiredTurn*2);
			}
		}
		
		//if requiredTurn > 0 then the robot needs to turn clockwise
		if (requiredTurn > 0)
		{
			this.left = outerWheel;
			this.right = innerWheel;
		}
		else //the robot needs to turn counter-clockwise
		{
			this.left = outerWheel;
			this.right = innerWheel;
		}
	}
}
