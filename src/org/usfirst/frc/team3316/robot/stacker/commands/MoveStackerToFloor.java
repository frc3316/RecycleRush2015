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
	protected void set() 
	{
		Robot.stacker.openSolenoidBottom();
		Robot.stacker.openSolenoidUpper();		
	}
	
	protected boolean isFinished ()
	{
		return (Robot.stacker.getPosition() == StackerPosition.Floor);
	}
}
