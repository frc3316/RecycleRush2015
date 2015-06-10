 package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedNavigation;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousDriveForward extends CommandGroup 
{    
    public AutonomousDriveForward() 
    {
    	addSequential(new RobotOrientedNavigation(0, 1.5, 0, 3));
    }
    
    DBugLogger logger = Robot.logger;
    
    protected void initialize ()
    {
    	logger.info(this.getName() + " initialize");
    }
    
    protected void end ()
    {
    	logger.info(this.getName() + " end");
    }
    
    protected void interrupted ()
    {
    	logger.info(this.getName() + " interrupted");
    }
}
