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
        addSequential(new RobotOrientedNavigation(0, 0, -45, 1));
        addSequential(new RobotOrientedNavigation(0, 0, 45, 1));
        //addSequential(new RobotOrientedNavigation(0.71*2.06, 0.71*2.06, 45, 1));
        addSequential(new RobotOrientedNavigation(0, 2.06, 0, 1)); //staging zone length is 4', distance between two staging zones is 2'9''
        
        
        addSequential(new RobotOrientedNavigation(0, 0, -45, 1));
        addSequential(new RobotOrientedNavigation(0, 0, 45, 1));
        //addSequential(new RobotOrientedNavigation(0.71*2.06, 0.71*2.06, -45, 1));
        addSequential(new RobotOrientedNavigation(0, 2.06, 0, 1)); //staging zone length is 4', distance between two staging zones is 2'9''
        
        
        addSequential(new RobotOrientedNavigation(3, 0, 90, 1));
    }
}
