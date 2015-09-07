package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.subsystems.Stacker;
import org.usfirst.frc.team3316.robot.utils.GamePieceCollected;
import org.usfirst.frc.team3316.robot.utils.StackerPosition;

/**
 * Moves stacker to floor position while checking
 * for mechanical safety constraints. 
 */
public class MoveStackerToFloor extends MoveStacker
{
	@Override
	protected void initialize()
    {
		super.initialize();
		Robot.stacker.lastStackerPosition = StackerPosition.Floor;
    }
	
	@Override
	protected void execute() {}

	@Override
	protected boolean isFinished()
	{
		if (Robot.stacker.getPosition() == StackerPosition.Floor)
		{
			Robot.stacker.closeBrake();
			return true;
		}
		return false;
	}
	
}
