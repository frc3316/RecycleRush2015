package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.rollerGripper.GamePieceCollected;
import org.usfirst.frc.team3316.robot.stacker.GamePieceType;
import org.usfirst.frc.team3316.robot.stacker.StackerPosition;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class MoveStackerToTote extends MoveStacker
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
		//TODO: check if the condition should be if the command started when the stacker was stuck on a container
		if (Robot.rollerGripper.getGamePieceCollected() == GamePieceCollected.Container)
		{
			Robot.stacker.openSolenoidContainer();
		}
		Robot.stacker.closeSolenoidUpper();
    	Robot.stacker.closeSolenoidBottom();
	}
	
	protected boolean isFinished ()
	{
		return (Robot.stacker.getPosition() == StackerPosition.Tote); 
	}
}
