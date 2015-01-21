package org.usfirst.frc.team3316.robot.commands.chassis;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;

public class TankDrive extends Drive 
{
	protected Joystick joystickLeft, joystickRight;
	
	public TankDrive ()
	{
		super();
		joystickLeft = Robot.joysticks.joystickLeft;
		joystickRight = Robot.joysticks.joystickRight;
	}
	
	protected void set() 
	{
		this.left = -joystickLeft.getY();
		this.right = -joystickRight.getY();
	}
}
