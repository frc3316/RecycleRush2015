package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.stacker.StackerPosition;

/**
 *
 */
public class MoveStackerToFloor extends MoveStacker
{
	protected StackerPosition setSetpointState() 
	{
		return StackerPosition.Floor;
	}
}
