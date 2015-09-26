package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class UseNavx extends Command 
{
	boolean bool;
	
    public UseNavx(boolean bool) 
    {
    	this.bool = bool;
    	setRunWhenDisabled(true);
    }

    protected void initialize() 
    {
    	Robot.chassis.navxEnabled = bool;
    }

    protected void execute() {}

    protected boolean isFinished() 
    {
        return true;
    }
    
    protected void end() {}
    
    protected void interrupted() {}
}
