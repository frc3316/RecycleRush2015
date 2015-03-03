package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.utils.StackerPosition;

public class ControlledMoveStackerToStep extends MoveStackerToStep 
{
	protected void setSolenoids() 
	{
		if (!Robot.stacker.closeSolenoidContainer() ||
			!Robot.stacker.openSolenoidUpper() || 
			!Robot.stacker.closeSolenoidBottom())
		{
			this.cancel();
		}
	}
	
	protected boolean isFinished ()
	{
		return (Robot.stacker.getPositionIR() == StackerPosition.Step); 
	}
}
