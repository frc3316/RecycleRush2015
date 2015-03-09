package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedNavigation;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveStraightToAutoZone extends CommandGroup {
    
    public  DriveStraightToAutoZone() {
    	//When the robot is first placed facing the auto zone, at a distance smaller than 1
    	addParallel(new RobotOrientedNavigation(0, 2, 0, 3));
    }
}
