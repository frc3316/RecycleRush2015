package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.rollerGripper.GamePieceCollected;
import org.usfirst.frc.team3316.robot.sequences.PickupSequence;

/**
 *
 */
public class MoveStackerToStep extends MoveStacker
{
	protected void initialize()
	{
		if (Robot.rollerGripper.getGamePieceCollected() == GamePieceCollected.None)
		{
			Robot.stacker.openSolenoidGripper();
		}
		Robot.stacker.closeSolenoidContainer();
	}
	
	protected void setSolenoids()
	{
		Robot.stacker.openSolenoidUpper();
		Robot.stacker.closeSolenoidBottom();
	}
}
