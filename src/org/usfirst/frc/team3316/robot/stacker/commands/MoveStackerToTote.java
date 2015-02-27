package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;

/**
 *
 */
public class MoveStackerToTote extends MoveStacker
{
	protected void setSolenoids() 
	{
		Robot.stacker.closeSolenoidUpper();
		Robot.stacker.closeSolenoidBottom();
	}
}
