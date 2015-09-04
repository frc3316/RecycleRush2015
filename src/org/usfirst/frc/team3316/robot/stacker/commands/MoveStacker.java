package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.utils.GamePieceCollected;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.WaitCommand;

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
    	
    }

    protected void execute() 
    {
    }

    protected boolean isFinished()
    {
    	return false;
    }
    
    protected void end() 
    {
    	logger.fine(this.getName() + " end");
    }

    protected void interrupted() 
    {
    	logger.fine(this.getName() + " interrupted");
    } 
}
