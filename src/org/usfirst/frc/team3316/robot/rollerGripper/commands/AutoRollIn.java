package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;

/**
 *
 */
public class AutoRollIn extends Roll {

    public AutoRollIn() {
        requires(Robot.rollerGripper);
    }

    protected void initialize() {}

    protected void execute() {
    	
    }

    protected boolean isFinished() {
        return Robot.rollerGripper.getSwitchGamePiece();
    }

    protected void end() {
    }

    protected void interrupted() {
    }

	protected void setSpeeds() {
		this.left = 1;
		this.right = 1;
	}
}
