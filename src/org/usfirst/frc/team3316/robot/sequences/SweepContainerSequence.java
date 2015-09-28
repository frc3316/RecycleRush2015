package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedNavigation;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.RollContainer;
import org.usfirst.frc.team3316.robot.stacker.commands.CloseGripper;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SweepContainerSequence extends CommandGroup
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	
	
	public SweepContainerSequence ()
	{
		addParallel(new RollContainer(), 2);
		
		try 
		{
			addSequential(new RobotOrientedNavigation(
					0, 
					(double) config.get("chassis_DriveToYellowTote_DefaultSetPoint"), 
					0, 
					5));
		}
		catch (ConfigException e) 
		{
			logger.severe(e);
		}
		
	}
	
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
