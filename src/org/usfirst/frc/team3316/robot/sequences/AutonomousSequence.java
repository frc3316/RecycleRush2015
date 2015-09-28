 package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.DriveToYellowTote;
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedNavigation;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

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
        
    	addSequential(new DriveToYellowTote());
    	
    	addSequential(new DriveToYellowTote());
    	
        addSequential(new RobotOrientedNavigation(3.3, 0, -179, 4));
        
        addSequential(new DropoffSequence());
    }
    
    DBugLogger logger = Robot.logger;
    Config config= Robot.config;
    
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
