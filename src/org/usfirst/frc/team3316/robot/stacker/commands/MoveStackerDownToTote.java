package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;

public class MoveStackerDownToTote extends MoveStackerToFloor {
	protected boolean isFinished()
    {
		//finish if both of the ratchets switches are pressed
    	return Robot.rollerGripper.getSwitchLeft() && Robot.rollerGripper.getSwitchRight();
    }
}
