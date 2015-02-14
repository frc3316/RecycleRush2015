package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.rollerGripper.GamePieceCollected;
import org.usfirst.frc.team3316.robot.stacker.StackerPosition;

public class MoveStackerToStep extends MoveStacker
{
	protected void initialize()
	{
		/*
		 * If one of the ratchets is not in place and they should be pressed, dont start
		 */
		if (	Robot.stacker.getPosition() == StackerPosition.Floor &&
				(!Robot.stacker.getSwitchLeft() || !Robot.stacker.getSwitchRight())) 
		{
			this.cancel();
		}
		else
		{
			super.initialize();
		}
	}
	
	protected void setSolenoids() 
	{
		if(Robot.stacker.getPosition() == StackerPosition.Tote)
		{
			Robot.stacker.openSolenoidGripper();
		}
		//TODO: check if the condition should be if the command started when the stacker was stuck on a container
		if (Robot.rollerGripper.getGamePieceCollected() == GamePieceCollected.Container)
		{
			Robot.stacker.openSolenoidContainer();
		}
		Robot.stacker.closeSolenoidUpper();
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
			return (Robot.stacker.getPosition() == StackerPosition.Step); 
		}
	}
}
