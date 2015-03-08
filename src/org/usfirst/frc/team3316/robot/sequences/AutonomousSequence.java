 package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedNavigation;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousSequence extends CommandGroup 
{    
    public  AutonomousSequence() 
    {
    	/*
    	 * Movement profile #3
    	 */
        addSequential(new SweepContainerSequence());
        addSequential(new RobotOrientedNavigation(0, 2.06, 0, 3));
        
        addSequential(new SweepContainerSequence());
        addSequential(new RobotOrientedNavigation(0, 2.06, 0, 3));
        
        addSequential(new RobotOrientedNavigation(3, 0, 90, 4));
    }
}
