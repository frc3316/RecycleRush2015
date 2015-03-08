package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedNavigation;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TwoStackedTotes extends CommandGroup {
    
    public  TwoStackedTotes() {
    	//Pick first tote (that is already inside the gripper)
    	addSequential(new AutoTotePickup());
    	
    	/*
    	 * Clear the way to the second tote, drive and pick it up
    	 */
    	addSequential(new SweepContainerSequence());
    	addParallel(new RobotOrientedNavigation(0, 2.06, 0, 3));
    	addSequential(new AutoToteCollect());
    	
    	//drive to the auto zone
    	addSequential(new RobotOrientedNavigation(3, 0, 90, 4));
    }
}
