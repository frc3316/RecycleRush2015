 package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedNavigation;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousSequence extends CommandGroup 
{    
    public AutonomousSequence() 
    {
    	/*
    	 * Movement profile #3
    	 */
    	addSequential(new AutoTotePickup());
        
    	sweepAndRoll(2);
    	
        addSequential(new RobotOrientedNavigation(3, 0, 90, 4));
    }
    
    /*
     * The only recursion in the code
     * i is the number of times to add the sequence to the code
     */
    private void sweepAndRoll (int i)
    {
    	addSequential(new SweepContainerSequence());
    	addParallel(new RobotOrientedNavigation(0, 2.06, 0, 3));
    	addSequential(new AutoToteCollect());
    	
    	if (i > 1)
    	{
    		sweepAndRoll(i - 1);
    	}
    }
}
