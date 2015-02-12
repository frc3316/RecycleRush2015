package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.stacker.GamePieceType;
import org.usfirst.frc.team3316.robot.stacker.StackerPosition;

public class MoveStackerToTote extends MoveStacker
{
	public MoveStackerToTote() 
    {
        super("STACKER_MOVE_STACKER_TO_TOTE_HEIGHT_MAX", 
        		"STACKER_MOVE_STACKER_TO_TOTE_HEIGHT_MIN");
    }

	protected void initialize()
	{
		/*
		 * If one of the ratchets is not in place and they should be pressed, dont start
		 */
		if (Robot.stacker.getPosition() == StackerPosition.Floor &&
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
		if (	Robot.stacker.getStackBase() != null &&
				Robot.stacker.getStackBase().getType() == GamePieceType.Container)
		{
			Robot.stacker.openSolenoidContainer();
		}
		Robot.stacker.closeSolenoidUpper();
    	Robot.stacker.closeSolenoidBottom();
	}
}
