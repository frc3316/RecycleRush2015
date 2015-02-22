package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;

public class MoveStackerToStep extends MoveStacker
{
	protected void set ()
	{
		Robot.stacker.openSolenoidUpper();
		Robot.stacker.closeSolenoidBottom();
	}
}
