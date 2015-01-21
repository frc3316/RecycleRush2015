package org.usfirst.frc.team3316.robot.commands.chassis;

public class RobotOrientedDrive extends StrafeDrive 
{
	protected void set ()
	{
		
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
		/*
		 * calculates inner and outer velocities so they're 
		 */
		if ((outerVelocity + requiredTurn) > 1) //1 should be replaced with MaxSpeed
		{
			outerVelocity = 1;
			innerVelocity = 1 - requiredTurn*2;
		}
		else if ((innerVelocity - requiredTurn) < -1) //-1 should be replaced with MinSpeed
		{
			innerVelocity = -1;
			outerVelocity = -1 + requiredTurn*2;
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
