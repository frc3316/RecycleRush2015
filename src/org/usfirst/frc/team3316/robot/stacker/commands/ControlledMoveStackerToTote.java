package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.utils.GamePieceCollected;
import org.usfirst.frc.team3316.robot.utils.StackerPosition;

public class ControlledMoveStackerToTote extends MoveStackerToTote 
{
	protected void prepareSolenoids ()
	{
		//TODO: Make sure gp will not be determined by the ratchets (because currently this condition is never met)
		if (gp == GamePieceCollected.Tote &&
				(!Robot.rollerGripper.getSwitchLeft() || !Robot.rollerGripper.getSwitchRight()))
		{
			this.cancel();
		}
		else
		{
			super.prepareSolenoids();
		}
	}
	
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
		return (Robot.stacker.getPositionIR() == StackerPosition.Tote); 
	}
}
