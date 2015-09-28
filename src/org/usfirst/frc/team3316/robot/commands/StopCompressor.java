package org.usfirst.frc.team3316.robot.commands;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StopCompressor extends Command {

    public StopCompressor() {}

    protected void initialize() {
    	Robot.comp.stop();
    }

    protected void execute() {}

   
    protected boolean isFinished() {
        return true;
    }

    
    protected void end() {}

    protected void interrupted() {}
}
