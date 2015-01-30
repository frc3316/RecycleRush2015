package org.usfirst.frc.team3316.robot.chassis.test;

/**
 *
 */
public abstract class Drive 
{	
	protected double left = 0, right = 0, center = 0;
	
	protected DBugLogger logger = Robot.logger;
	
    public Drive() {}

    public void run ()
    {
    	set();
    	logger.fine("left = " + String.format("%.3f", left) + 
    				  ", right = " + String.format("%.3f", right) + 
    				  ", center = " + String.format("%.3f", center));
    }
    
    protected abstract void set ();
}
