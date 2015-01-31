package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CloseGripper extends Command 
{
    public CloseGripper() 
    {
    	//This command is asynchronous and therefore does not require stacker subsystem
    }

    protected void initialize() 
    {
    	Robot.stacker.closeSolenoidGripper();
    }

    protected void execute() {}

    protected boolean isFinished() 
    {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
