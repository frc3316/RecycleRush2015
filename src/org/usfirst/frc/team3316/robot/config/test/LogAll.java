package org.usfirst.frc.team3316.robot.config.test;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LogAll extends Command 
{
	Config config = Robot.config;
	
    public LogAll() {}

    protected void initialize() 
    {
    	config.logAll();
    }
    
    protected void execute() {}

    protected boolean isFinished() 
    {
        return true;
    }
    
    protected void end() {}

    protected void interrupted() {}
}
