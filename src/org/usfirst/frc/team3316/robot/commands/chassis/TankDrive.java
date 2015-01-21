package org.usfirst.frc.team3316.robot.commands.chassis;

import edu.wpi.first.wpilibj.Joystick;

public class TankDrive extends Drive 
{
	protected Joystick joystickLeft, joystickRight;
	
	public TankDrive ()
	{
		//add init for joystickLeft and joystickRight
		super();
	}
	
	protected void set() 
	{
		this.left = -joystickLeft.getY();
		this.right = -joystickRight.getY();
	}
}
