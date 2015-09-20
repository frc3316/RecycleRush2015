package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class UnBrake extends Command
{

	DBugLogger logger = Robot.logger;
	
    public UnBrake() 
    {
    	//This command is asynchronous and therefore does not require stacker subsystem
    }

    protected void initialize()
    {
    	logger.fine(this.getName() + " initialize");
    	Robot.stacker.allowStackMovement();
    }

    protected void execute() {}

    protected boolean isFinished()
    {
        return true;
    }

    protected void end()
    {
    	Robot.stacker.setMovementAllowed(true);
    	logger.fine(this.getName() + " end");
    }

    protected void interrupted() 
    {
    	Robot.stacker.setMovementAllowed(true);
    	logger.fine(this.getName() + " interrupted");
    }
}