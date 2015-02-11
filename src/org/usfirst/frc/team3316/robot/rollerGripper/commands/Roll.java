package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Roll extends Command 
{
    Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	protected String speedLeftName, speedRightName;
	
	protected double speedLeft, speedRight;

    public Roll(String speedLeftName, String speedRightName) 
    {
    	requires(Robot.rollerGripper);
    	this.speedLeftName = speedLeftName;
        this.speedRightName = speedRightName;
    }
    
    protected void initialize() {}

    protected void execute() 
    {
    	updateSpeeds();
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
    
    protected void updateSpeeds ()
    {
    	try 
    	{
			speedLeft = (double) config.get(speedLeftName);
			speedRight = (double) config.get(speedRightName);
		} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
    }
}
