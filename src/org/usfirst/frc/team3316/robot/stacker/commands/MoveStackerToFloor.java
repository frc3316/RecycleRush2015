package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.rollerGripper.GamePieceCollected;
import org.usfirst.frc.team3316.robot.stacker.StackerPosition;

/**
 *
 */
public class MoveStackerToFloor extends MoveStacker
{

	protected void setSolenoids() 
	{
		if (Robot.rollerGripper.getGamePieceCollected() == GamePieceCollected.None)
		{
			Robot.stacker.openSolenoidGripper();
		}
		Robot.stacker.closeSolenoidContainer();
		Robot.stacker.openSolenoidUpper();
		Robot.stacker.openSolenoidBottom();
	}
	
	protected boolean isFinished ()
	{
		if (Robot.rollerGripper.getGamePieceCollected() == GamePieceCollected.Container)
		{
			return (Robot.stacker.getPosition() == StackerPosition.StuckOnContainer);
		}
		else 
		{
			return (Robot.stacker.getPosition() == StackerPosition.Floor); 
		}
	}
}
