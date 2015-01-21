package org.usfirst.frc.team3316.robot.chassis.test;


public class TankDrive extends Drive 
{
	
	protected Joystick joystickLeft, joystickRight;
	
	public TankDrive ()
	{
		super();
		//joystickLeft = Robot.joysticks.joystickLeft;
		//joystickRight = Robot.joysticks.joystickRight;
		joystickLeft = Test.leftJoystick;
		joystickRight = Test.rightJoystick;
		
	}
	
	protected void set() 
	{
		this.left = -joystickLeft.getY();
		this.right = -joystickRight.getY();	
	}
}
