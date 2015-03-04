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
        addSequential(new RobotOrientedNavigation(1, 0, 0));
        addSequential(new RobotOrientedNavigation(0, 2, 0));
        addSequential(new RobotOrientedNavigation(-1, 0, 0));
        addSequential(new RobotOrientedNavigation(0, 2, 0));
    }
}
