package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RollIn extends Command {
	
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	private double speed;
	
	
    public RollIn() 
    {
        requires(Robot.rollerGripper);
    }
    
    protected void initialize() {}

    protected void execute() 
    {
    	Robot.rollerGripper.set(speed, speed);
    }

    protected boolean isFinished() 
    {
        return false;
    }

    protected void end() {}

    protected void interrupted() {}
    
    public void updateSpeed () 
    {
    	try 
    	{
			speed = (double) config.get("rollerGripper_RollIn_Speed");
		} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
    }
}
