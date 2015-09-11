package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CloseBrake extends Command
{

	DBugLogger logger = Robot.logger;
	
    public CloseBrake() {}

    protected void initialize()
    {
    	logger.fine(this.getName() + " initialize");
    	Robot.stacker.closeBrakeAndHolders();
    }

    protected void execute() {}

    protected boolean isFinished()
    {
        return false;
    }

    protected void end() {

    	logger.fine(this.getName() + " end");
    }

    protected void interrupted() 
    {
    	logger.fine(this.getName() + " interrupted");
    }
}
