package org.usfirst.frc.team3316.robot.chassis.heading;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class SetHeading extends Command 
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	protected double headingToSet = 0; 
	
    public SetHeading () 
    {
    	setRunWhenDisabled(true);
    }

    protected void initialize() {}

    protected void execute() {}

    protected boolean isFinished() 
    {
        return true;
    }

    protected void end() 
    {
    	Robot.chassis.setHeading(headingToSet);
    }
    
    protected void interrupted() {}
}
