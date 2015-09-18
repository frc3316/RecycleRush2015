package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Brake extends Command
{

	DBugLogger logger = Robot.logger;
	
    public Brake() 
    {
    	//This command is asynchronous and therefore does not require stacker subsystem
    }

    protected void initialize()
    {
    	logger.fine(this.getName() + " initialize");
    	Robot.stacker.disallowStackMovement();
    	
    	setTimeout(0.2);
    }

    protected void execute() {}

    protected boolean isFinished()
    {
        if (isTimedOut())
        {
        	Robot.stacker.setMotors(0);
        	return true;
        }
        else
        {
        	return false;
        }
        
    }

    protected void end() {

    	logger.fine(this.getName() + " end");
    }

    protected void interrupted() 
    {
    	logger.fine(this.getName() + " interrupted");
    }
}
