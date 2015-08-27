 package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedNavigation;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.RollContainer;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousSequence extends CommandGroup 
{    
	double pushTime;
	
    public AutonomousSequence() 
    {
    	
    	/*
    	 * Movement profile #3
    	 */
    	addSequential(new AutoTotePickup());
        
    	//addSequential(new SweepContainerSequence());
    	addParallel(new RobotOrientedNavigation(0, 2.11, 0, 3));
    	addSequential(new RollContainer(), pushTime);
    	addSequential(new AutoToteCollect());
    	
    	//addSequential(new SweepContainerSequence());
    	addParallel(new RobotOrientedNavigation(0, 2.11, 0, 3));
    	addSequential(new AutoToteCollect());
    	
        addSequential(new RobotOrientedNavigation(3.3, 0, -179, 4));
        
        addSequential(new DropoffSequence());
    }
    
    DBugLogger logger = Robot.logger;
    Config config= Robot.config;
    
    protected void initialize ()
    {
    	logger.info(this.getName() + " initialize");

    	try
		{
    		pushTime = (double) config.get("rollerGripper_PushContainer_PushTime");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
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
