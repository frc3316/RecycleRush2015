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
    	logger.fine(this.getName() + " initialize");
    	Robot.stacker.openBrakeAndHolders();
    }

    protected abstract void execute();

    protected abstract boolean isFinished();
    
    protected void end() 
    {
    	logger.fine(this.getName() + " end");
    	_end();
    }

    protected void interrupted() 
    {
    	logger.fine(this.getName() + " interrupted");
    	_end();
    }
    
    private void _end()
    {
    	Robot.stacker.closeBrakeAndHolders();
    }
}
