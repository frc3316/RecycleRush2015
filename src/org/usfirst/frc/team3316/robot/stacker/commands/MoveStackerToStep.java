package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.stacker.StackerPosition;

public class MoveStackerToStep extends MoveStacker
{
	protected StackerPosition setSetpointState() 
	{
		return StackerPosition.Step;
	}
}
