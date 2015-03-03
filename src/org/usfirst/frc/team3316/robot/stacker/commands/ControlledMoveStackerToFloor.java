package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.utils.StackerPosition;

public class ControlledMoveStackerToFloor extends MoveStackerToFloor 
{
	protected void setSolenoids() 
	{
		//If can't open upper or bottom solenoids - abort
		if (!Robot.stacker.closeSolenoidContainer() ||
			!Robot.stacker.openSolenoidUpper() || 
			!Robot.stacker.openSolenoidBottom())
		{
			this.cancel();
		}
	}
	
	protected boolean isFinished ()
	{
		return (Robot.stacker.getPositionIR() == StackerPosition.Floor); 
	}
}
