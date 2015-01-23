package org.usfirst.frc.team3316.robot.chassis.test;

public class RobotOrientedDrive extends StrafeDrive 
{
	//Config config = Test.config;
	DBugLogger logger = Test.logger;
	
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
		//outerWheel is the wheel that would exceed the range of -1 to 1
		double outerWheel = Math.max(Math.abs(yIn + requiredTurn), Math.abs(yIn - requiredTurn))*Math.signum(yIn);
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
