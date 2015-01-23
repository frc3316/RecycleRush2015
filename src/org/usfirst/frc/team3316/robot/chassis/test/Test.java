package org.usfirst.frc.team3316.robot.chassis.test;


public class Test 
{
	public static DBugLogger logger = new DBugLogger();
	public static Joystick joystickLeft, joystickRight;
	public static void main (String[]args)
	{
		joystickLeft = new Joystick();
		joystickRight = new Joystick();
		Drive driveTest;
	
		logger.finest("Robot Oriented Drive Test");
		driveTest = new RobotOrientedDrive();

		for (int j = -10; j <= 10; j += 1)
		{
			joystickRight.setY((double)j/10);
			for (int k = -10; k <= 10; k += 1)
			{
				joystickLeft.setX((double)k/10);
				for (int l = -10; l <= 10; l += 1)
				{
					joystickRight.setX((double)l/10);
					logger.finest(
							"Right Y = " + String.format("%.1f", joystickRight.getY()) + 
							", Right X = " + String.format("%.1f", joystickRight.getX()) +
							", Left X = " + String.format("%.1f", joystickLeft.getX())); 

					driveTest.run();
				}
			}
		}
		
		logger.finest("Field Oriented Drive Test");
		driveTest = new FieldOrientedDrive();
		{
			for (int j = -10; j <= 10; j += 1)
			{
				joystickRight.setY((double)j/10);
				for (int k = -10; k <= 10; k += 1)
				{
					joystickLeft.setX((double)k/10);
					for (int l = -10; l <= 10; l += 1)
					{
						joystickRight.setX((double)l/10);
						for (int i = -10; i <= 10; i += 1)
						{
							((FieldOrientedDrive)driveTest).setRobotAngle(i*18);
							logger.finest(
									"Right Y = " + String.format("%.1f", joystickRight.getY()) + 
									", Right X = " + String.format("%.1f", joystickRight.getX()) +
									", Left X = " + String.format("%.1f", joystickLeft.getX()) +
									", Robot Angle = " + String.format("%.0f",((FieldOrientedDrive)driveTest).getRobotAngle()));
							driveTest.run();
						}
					}
				}
			}
		}
	}
}