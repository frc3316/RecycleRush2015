package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class MoveStacker extends Command 
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	protected double heightMin, heightMax;
	
    public MoveStacker(String heightMaxName, String heightMinName)
    {
        requires(Robot.stacker);
        updateHeightRange(heightMaxName, heightMinName);
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
    
    protected void updateHeightRange (String heightMaxName, String heightMinName)
    {
    	try 
    	{
			heightMax = (double) config.get(heightMaxName);
			heightMin = (double) config.get(heightMinName);
		} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
    }
}
