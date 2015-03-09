package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.FieldOrientedNavigation;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

public class ReturnToTrackSequence extends FieldOrientedNavigation 
{
	public ReturnToTrackSequence() 
	{
		super(0, 0, 0, 2);
		requires(Robot.rollerGripper);
	}
	
	protected void set ()
    {
    	try
    	{
    		Robot.rollerGripper.set((double) config.get("rollerGripper_ReturnToTrackSequence_LeftSpeed"), 
    								(double) config.get("rollerGripper_ReturnToTrackSequence_RightSpeed"));
    	}
    	catch (ConfigException e)
    	{
    		logger.severe(e);
    	}
    	super.set();
    }
    
    protected void end ()
    {
    	Robot.rollerGripper.set(0, 0);
    	super.end();
    }

}
