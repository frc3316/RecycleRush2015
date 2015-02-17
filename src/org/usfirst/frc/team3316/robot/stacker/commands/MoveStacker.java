package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.stacker.StackerPosition;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class MoveStacker extends Command 
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	private StackerPosition setpoint;
	
	private boolean setSuccessful = false;
	
    public MoveStacker()
    {
        requires(Robot.stacker);
        setpoint = setSetpointState();
    }

    protected void initialize() {}

    protected void execute() 
    {
    	if (!setSuccessful) 
    	{
    		set();
    	}
    }

    protected boolean isFinished()
    {
    	if (Robot.stacker.getPosition() == setpoint)
    	{
    		return true;
    	}
    	else if (Robot.stacker.getSetpointState() == null && setSuccessful)
    	{
    		return true;
    	}
    	return false;
    }
    
    protected void end() 
    {
    	setSuccessful = false;
    }

    protected void interrupted() {}
    
    private void set ()
    {
    	if (Robot.stacker.setSetpointState(setpoint) == setpoint)
    	{
    		setSuccessful = true;
    	}
    }
    
    protected abstract StackerPosition setSetpointState();
}
