package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

public class RobotOrientedDrive extends StrafeDrive 
{	
	double turnScale;
	
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
		//TODO: figure out PID rotation
		
		/*
		 * the following code sets left and right so that:
		 * for turning clockwise, left > right
		 * for turning counter-clockwise, right > left
		 * left - right = requiredTurn*2
		 * -1 < left, right < 1 
		 */
		double yIn = this.left;
		updateTurnScale();
		requiredTurn = requiredTurn * turnScale;
		// The outer wheel is the one which drives faster (absolutely)
		// Therefore it is the one the would exceed the range of -1 to 1
		// (when requiredTurn is 0 then it doesn't matter which is outer because were driving straight)
		// (when yIn is 0 then the outer wheel will be 0, but we fix this later...)
		double outerWheel = Math.max(Math.abs(yIn + requiredTurn), Math.abs(yIn - requiredTurn))*Math.signum(yIn);
		double innerWheel;
		
		//sets innerWheel so it is slower than outerWheel
		if (outerWheel > 0)
		{
			outerWheel = Math.min(outerWheel, 1); //makes sure outerWheel <= 1
			
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
			outerWheel = Math.max(outerWheel, -1); //makes sure outerWheel >= -1
			
			if (requiredTurn < 0)
			{
				innerWheel = outerWheel - (requiredTurn*2);
			}
			else
			{
				innerWheel = outerWheel + (requiredTurn*2);
			}
		}
		
		if (requiredTurn > 0) //the robot needs to turn clockwise
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
	
	private void updateTurnScale ()
	{
		try 
		{
			turnScale = (double)config.get("chassis_RobotOrientedDrive_TurnScale");
		}
		catch (ConfigException e)
		{
			logger.severe(e.getMessage());
		}
	}
}
