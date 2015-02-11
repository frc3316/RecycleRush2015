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
	protected double speedLeft, speedRight;
	

    public Roll() 
    {
    	requires(Robot.rollerGripper);
    }
    
    protected void initialize() {}

    protected void execute() 
    {
    	setSpeed();
    	Robot.rollerGripper.set(speedLeft, speedRight);
    }

    protected boolean isFinished() 
    {
        return false;
    }

    protected void end() 
    {
    	Robot.rollerGripper.set(0, 0);
    }

    protected void interrupted() 
    {
    	end();
    }
    
    protected abstract void setSpeed();
}
