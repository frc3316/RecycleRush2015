package org.usfirst.frc.team3316.robot.chassis.test;

public class Test 
{
	public static DBugLogger logger = new DBugLogger();
	public static Joystick leftJoystick, rightJoystick;
	public static void main (String[]args)
	{
		leftJoystick = new Joystick();
		rightJoystick = new Joystick();
		Drive driveTest;
	
		logger.finest("Robot Oriented Drive Test");
		driveTest = new RobotOrientedDrive();

		for (int j = -10; j <= 10; j += 1)
		{
			rightJoystick.setY((double)j/10);
			for (int k = -10; k <= 10; k += 1)
			{
				leftJoystick.setX((double)k/10);
				for (int l = -10; l <= 10; l += 1)
				{
					rightJoystick.setX((double)l/10);
					logger.finest(
							"Right Y = " + (double)rightJoystick.getY() + 
							", Right X = " + (double)rightJoystick.getX() +
							", Left X = " + (double)leftJoystick.getX()); 

					driveTest.run();
				}
			}
		}
		
		logger.finest("Field Oriented Drive Test");
		driveTest = new FieldOrientedDrive();
		{
			for (int j = -10; j <= 10; j += 1)
			{
				rightJoystick.setY((double)j/10);
				for (int k = -10; k <= 10; k += 1)
				{
					leftJoystick.setX((double)k/10);
					for (int l = -10; l <= 10; l += 1)
					{
						rightJoystick.setX((double)l/10);
						for (int i = -10; i <= 10; i += 1)
						{
							((FieldOrientedDrive)driveTest).setRobotAngle(i*18);
							logger.finest(
									"Right Y = " + (double)rightJoystick.getY() + 
									", Right X = " + (double)rightJoystick.getX() +
									", Left X = " + (double)leftJoystick.getX() +
									", Robot Angle = " + (double)((FieldOrientedDrive)driveTest).getRobotAngle());
							driveTest.run();
						}
					}
				}
			}
		}
	}
}