package org.usfirst.frc.team3316.robot.anschluss.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Command that opens the anschluss
 */
public class openAnschluss extends Command 
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	double motorSpeed;
	
    public openAnschluss() 
    {
    	requires(Robot.anschluss);
    }

    protected void initialize() {}

    protected void execute() 
    {
    	updateMotorSpeed();
    	Robot.anschluss.set(motorSpeed);
    }

    protected boolean isFinished() {
        return Robot.anschluss.isOpened();
    }

    protected void end() {}
    
    protected void interrupted() {}
    
    private void updateMotorSpeed() 
    {
    	try 
    	{
			motorSpeed = (double) config.get("closeAnschlussMotorSpeed");
		}
    	catch (ConfigException e) 
    	{
    		logger.severe(e);			
		}
    }
}
