package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.utils.GamePieceCollected;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class MoveStacker extends Command 
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	GamePieceCollected gp;
	
    public MoveStacker()
    {
        requires(Robot.stacker);
    }

    protected void initialize()
    {
    	logger.fine(this.getName() + " initialized");
    	
    	gp = Robot.rollerGripper.getGamePieceCollected();
    	
    	setSolenoids();
    }

    protected void execute() {}

    protected boolean isFinished()
    {
    	return true;
    }
    
    protected void end() 
    {
    	logger.fine(this.getName() + "ended");
    }

    protected void interrupted() 
    {
    	logger.fine(this.getName() + "interrupted");
    }
    
    protected abstract void setSolenoids();
}
