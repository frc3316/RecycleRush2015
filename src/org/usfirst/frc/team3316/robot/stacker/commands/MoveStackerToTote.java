package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.utils.GamePieceCollected;
import org.usfirst.frc.team3316.robot.utils.StackerPosition;

/**
 * Moves stacker to tote position while checking
 * for mechanical safety constraints. 
 */
public class MoveStackerToTote extends MoveStacker
{
	@Override
	protected void initialize()
    {
		super.initialize();
		Robot.stacker.lastStackerPosition = StackerPosition.Tote;
    }

	@Override
	protected void execute()
	{
		Robot.stacker.setMotors(v);
	}

	@Override
	protected boolean isFinished()
	{
		if (Robot.stacker.getPosition() == StackerPosition.Tote)
		{
			Robot.stacker.closeBrake();
			return true;
		}
		return false;
	}
}
