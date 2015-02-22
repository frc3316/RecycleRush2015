package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;

/**
 *
 */
public class MoveStackerToFloor extends MoveStacker
{
	protected void set ()
	{
		Robot.stacker.openSolenoidUpper();
		Robot.stacker.openSolenoidBottom();
	}
}
