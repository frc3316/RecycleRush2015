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
	GamePieceCollected gp;
	
	protected void initialize()
	{
		logger.fine("MoveStackerToStep command initialize");
		
		gp = Robot.rollerGripper.getGamePieceCollected();
		if (gp == GamePieceCollected.None)
		{
			/* If there is nothing at floor position, what we might have on
			 * the stacker will colide with the roller gripper.
			 */
			logger.fine("NO game piece in roller gripper");
			Robot.stacker.openSolenoidGripper();
		}
		else
		{
			logger.fine("YES game piece in roller gripper");
		}
		super.initialize();
	}
	
	protected void setSolenoids()
	{
		/* We always want to close the container pistons so they don't colide
		 * with any gamepiece that might be at floor position.
		 */
		Robot.stacker.closeSolenoidContainer();
		
		Robot.stacker.openSolenoidUpper();
		Robot.stacker.closeSolenoidBottom();
	}
}
