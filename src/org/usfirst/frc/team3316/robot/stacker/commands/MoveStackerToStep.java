package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.rollerGripper.GamePieceCollected;
import org.usfirst.frc.team3316.robot.stacker.StackerPosition;

public class MoveStackerToStep extends MoveStacker
{
	protected void setSolenoids()
	{
		Robot.stacker.openSolenoidUpper();
    	Robot.stacker.closeSolenoidBottom();
	}
}
