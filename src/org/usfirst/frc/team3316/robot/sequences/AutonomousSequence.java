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
        addSequential(new RobotOrientedNavigation(0, 0, 45));
        addSequential(new RobotOrientedNavigation(-0.707, 0.707, -45)); //1 meter in the original direction
        addSequential(new RobotOrientedNavigation(0, 1, 0));
        
        addSequential(new RobotOrientedNavigation(0, 0, 45));
        addSequential(new RobotOrientedNavigation(-0.707, 0.707, -45)); //1 meter in the original direction
        addSequential(new RobotOrientedNavigation(0, 1, 0));
    }
}
