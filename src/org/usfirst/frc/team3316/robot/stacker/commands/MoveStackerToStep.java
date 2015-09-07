package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.utils.GamePieceCollected;
import org.usfirst.frc.team3316.robot.utils.StackerPosition;

/**
 * Moves stacker to step position while checking
 * for mechanical safety constraints. 
 */
public class MoveStackerToStep extends MoveStacker
{
	@Override
	protected void initialize()
    {
		super.initialize();
		Robot.stacker.lastStackerPosition = StackerPosition.Step;
    }

	@Override
	protected void execute()
	{
		if (Robot.stacker.lastStackerPosition == StackerPosition.Floor)
		{
			Robot.stacker.setMotors(v);
		}
		else if (Robot.stacker.lastStackerPosition == StackerPosition.Tote)
		{
			Robot.stacker.setMotors(v);
		}
	}

	@Override
	protected boolean isFinished()
	{
		if (Robot.stacker.getPosition() == StackerPosition.Step)
		{
			Robot.stacker.closeBrake();
			return true;
		}
		return false;
	}
}
