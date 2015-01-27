package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RollOut extends Command 
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	private double speed;
    public RollOut() 
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

    protected void end() 
    {
    	Robot.rollerGripper.set(0, 0);
    }

    protected void interrupted() 
    {
    	end();
    }
    
    private void updateSpeed ()
    {
    	try 
    	{
			speed = (double)config.get("rollerGripper_RollOut_Speed");
		} 
    	catch (ConfigException e) 
    	{
			Robot.logger.severe(e);
		} 
    }
}
