package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FieldOrientedDrive extends RobotOrientedDrive 
{	
	protected void set ()
	{
		setFieldVector(getRightX(), getRightY());
		setRotation(getLeftX());
	}
	
	protected void setFieldVector (double x, double y)
	{	
		double r = Math.sqrt(x*x + y*y); //The vector's magnitude
		double vectorAngle =  Math.atan2(x, y); //Vector angle from y axis, 
												//clockwise is positive and 
												//counter-clockwise is negative
		double robotAngleRad = Math.toRadians(Robot.chassis.getHeading()); //Robot angle
		
		SmartDashboard.putNumber("Current Heading", Robot.chassis.getHeading()); //TODO: MOVE THIS TO A BETTER PLACE
		
		double angleDiff = vectorAngle - robotAngleRad; //The angle of the vector oriented to the robot
		
		/*
		 * Breaks down the field vector to the robot's axes
		 */
		double outputX = Math.sin(angleDiff)*r;
		double outputY = Math.cos(angleDiff)*r;
		
		//Normalize outputs
		double max = Math.max(Math.abs(outputX), Math.abs(outputY));
		if (max > 1)
		{
			outputX /= max;
			outputY /= max;
		}
		
		setRobotVector(outputX, outputY);
	}
}