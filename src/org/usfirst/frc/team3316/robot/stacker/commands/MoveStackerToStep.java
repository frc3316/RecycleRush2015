package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.rollerGripper.GamePieceCollected;
import org.usfirst.frc.team3316.robot.stacker.StackerPosition;

/**
 * Moves stacker to step position while checking
 * for mechanical safety constraints. 
 */
public class MoveStackerToStep extends MoveStacker
{
	protected void initialize()
	{
		StackerPosition sp = Robot.stacker.getPosition();
		
		if (sp == StackerPosition.Tote)
		{
			if (Robot.rollerGripper.getGamePieceCollected() == GamePieceCollected.None)
			{
				/* If there is nothing at floor position, what we might have on
				 * the stacker will colide with the roller gripper.
				 */
				Robot.stacker.openSolenoidGripper();
			}
		}
		super.initialize();
	}
	
	protected void setSolenoids()
	{
		Robot.stacker.openSolenoidUpper();
		Robot.stacker.closeSolenoidBottom();
		/*  We always want to close the container pistons so they don't colide
		 *  with any gamepiece that might be at floor position.
		 */
		Robot.stacker.closeSolenoidContainer();
	}
}
