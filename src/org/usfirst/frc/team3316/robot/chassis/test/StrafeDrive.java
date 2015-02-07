package org.usfirst.frc.team3316.robot.chassis.test;


public class StrafeDrive extends TankDrive 
{
	protected void set ()
	{
		this.center = getRightX();
		super.set();
	}
}
