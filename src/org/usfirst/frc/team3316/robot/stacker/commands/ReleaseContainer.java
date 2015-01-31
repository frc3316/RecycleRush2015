package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ReleaseContainer extends Command {

	public ReleaseContainer() 
    {
    	//This command is asynchronous and therefore does not require stacker subsystem
    }

    protected void initialize() 
    {
    	Robot.stacker.closeSolenoidContainer();
    }

    protected void execute() {}

    protected boolean isFinished() 
    {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
