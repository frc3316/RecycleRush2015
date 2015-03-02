package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class MoveStacker extends Command 
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
    public MoveStacker()
    {
        requires(Robot.stacker);
    }

    protected void initialize()
    {
    	setSolenoids();
    }

    protected void execute() {}

    protected boolean isFinished()
    {
    	return true;
    }
    
    protected void end() {}

    protected void interrupted() {}
    
    protected abstract void setSolenoids();
}
