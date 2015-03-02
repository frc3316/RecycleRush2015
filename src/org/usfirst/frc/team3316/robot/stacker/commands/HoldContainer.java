package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HoldContainer extends Command 
{
	DBugLogger logger = Robot.logger;
	
    public HoldContainer() 
    {
    	//This command is asynchronous and therefore does not require stacker subsystem
    }

    protected void initialize() 
    {
    	logger.fine("HoldContainer command initialize");
    	Robot.stacker.openSolenoidContainer();
    }

    protected void execute() {}

    protected boolean isFinished() 
    {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
