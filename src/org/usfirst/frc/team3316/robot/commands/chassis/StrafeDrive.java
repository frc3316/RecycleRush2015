package org.usfirst.frc.team3316.robot.commands.chassis;

public class StrafeDrive extends TankDrive 
{
	protected void set ()
	{
		this.center = joystickRight.getX();
		super.set();
	}
}
