package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;

public class ControlledMoveStackerToTote extends MoveStackerToTote 
{	
	protected boolean setSolenoids ()
	{
		if (!super.setSolenoids())
		{
			this.cancel();
			return false;
		}
		return true;
	}
	
	protected boolean isFinished ()
	{
		return (!Robot.rollerGripper.getSwitchGamePiece()); 
	}
}
