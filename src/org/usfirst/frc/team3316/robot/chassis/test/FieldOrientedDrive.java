package org.usfirst.frc.team3316.robot.chassis.test;


public class FieldOrientedDrive extends RobotOrientedDrive 
{
	public double robotAngle = 0;
	protected void set ()
	{
		setCartesianVector(joystickRight.getX(), -joystickRight.getY());
		orientDriveToField();
		setRotation(joystickLeft.getX());
	}
	
	public void setRobotAngle (double angle)
	{
		robotAngle = angle;
	}
	
	public double getRobotAngle ()
	{
		return robotAngle;
	}
	
	protected void orientDriveToField ()
	{
		double x = this.center;
		double y;
		if (this.left == this.right)
		{
			y = this.left;
		}
		else //if left does not equal right then something went wrong
		{
			return;
		}
		
		double r = Math.sqrt(x*x + y*y);
		double vectorAngle =  Math.atan2(x, y); //angle from y axis, clockwise is positive and counter-clockwise is negative
		//double robotAngle = Robot.chassis.getHeading();
		double angleDiff = vectorAngle - robotAngle;
		
		this.right = this.left = Math.cos(angleDiff)*r;
		this.center = Math.sin(angleDiff)*r;
	}
}
