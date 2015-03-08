package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.utils.StackerPosition;

public class ControlledMoveStackerToStep extends MoveStackerToStep 
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
		return (Robot.stacker.getPositionIR() == StackerPosition.Step); 
	}
}
