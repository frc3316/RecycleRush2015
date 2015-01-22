package org.usfirst.frc.team3316.robot.chassis.test;

/**
 *
 */
public abstract class Drive 
{
	protected double left = 0, right = 0, center = 0;
	DBugLogger logger = new DBugLogger();

    protected void initialize() {
    }

    protected void execute() 
    {
    	set();
    	logger.finest("left = " + (double)left + ", right = " + (double)right + ", center = " + (double)center);
    }

    protected abstract void set ();
    
    public void run ()
    {
    	execute();
    }
}
