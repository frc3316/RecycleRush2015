package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class Roll extends Command 
{
    Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	protected double left, right;

    public Roll () 
    {
    	requires(Robot.rollerGripper);
    }
    
    protected void initialize() 
    {
    	logger.fine(this.getName() + " initialize");
    }

    protected void execute() 
    {
    	setSpeeds();
    	Robot.rollerGripper.set(left, right);
    }

    protected boolean isFinished() 
    {
        return false;
    }

    protected void end() 
    {
    	logger.fine(this.getName() + " end");
    	Robot.rollerGripper.set(0, 0);
    }

    protected void interrupted() 
    {
    	logger.fine(this.getName() + " interrupted");
    	Robot.rollerGripper.set(0, 0);
    }
    
    protected abstract void setSpeeds ();
}
