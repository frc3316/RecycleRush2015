package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Roll extends Command {


    Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	protected double speed;
	

    public Roll() {
    	requires(Robot.rollerGripper);
    }
    
    protected void initialize() {}

    protected void execute() {}

    protected boolean isFinished() 
    {
        return false;
    }

    protected void end() {
    	Robot.rollerGripper.set(0, 0);
    }

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
