package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;

/**
 *
 */
public class MoveStackerToFloor extends MoveStacker
{
    public MoveStackerToFloor() 
    {
        super("STACKER_MOVE_STACKER_TO_FLOOR_HEIGHT_MAX", "STACKER_MOVE_STACKER_TO_FLOOR_HEIGHT_MIN");
    }

	protected void setSolenoids() 
	{
		Robot.stacker.closeSolenoidContainer();
		Robot.stacker.openSolenoidUpper();
		Robot.stacker.openSolenoidBottom();
	}
}
