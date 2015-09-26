package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedNavigation;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

/**
 *
 */
public class PushContainerSequence extends RobotOrientedNavigation 
{   
    public PushContainerSequence() 
    {
        super(0, 0, -45, 1);
        requires(Robot.rollerGripper);
    }
    
    protected void set ()
    {
    	try
    	{
    		Robot.rollerGripper.set((double) config.get("rollerGripper_PushContainerSequence_LeftSpeed"), 
    								(double) config.get("rollerGripper_PushContainerSequence_RightSpeed"));
    	}
    	catch (ConfigException e)
    	{
    		logger.severe(e);
    	}
    	super.set();
    }
    
    protected boolean isFinished ()
    {
    	return (Math.abs(integrator.getHeading()) > Math.abs(setpointHeading));
    }
    
    protected void end ()
    {
    	Robot.rollerGripper.set(0, 0);
    	super.end();
    }
}
