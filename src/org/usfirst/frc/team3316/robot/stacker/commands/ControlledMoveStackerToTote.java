package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.utils.GamePieceCollected;
import org.usfirst.frc.team3316.robot.utils.StackerPosition;

public class ControlledMoveStackerToTote extends MoveStackerToTote 
{
	protected void prepareSolenoids() 
	{
		//If there is a tote inside and one of the ratchets is not in place - abort
		//If can't close the upper or bottom pistons - abort
		if (gp == GamePieceCollected.Tote && 
				(!Robot.stacker.getSwitchRatchetLeft() || !Robot.stacker.getSwitchRatchetRight())
			||
			(!Robot.stacker.closeSolenoidBottom() || !Robot.stacker.closeSolenoidUpper()))
		{
			this.cancel();
		}
	}
	
	protected boolean isFinished ()
	{
		return (Robot.stacker.getPositionIR() == StackerPosition.Tote); 
	}
}
