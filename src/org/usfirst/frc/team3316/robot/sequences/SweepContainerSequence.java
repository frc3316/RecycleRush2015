package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.FieldOrientedNavigation;
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedNavigation;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.RollOut;
import org.usfirst.frc.team3316.robot.stacker.commands.CloseGripper;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SweepContainerSequence extends CommandGroup
{
	DBugLogger logger = Robot.logger;
	
	ReturnToTrackSequence returnToTrack;
	
	
	public SweepContainerSequence ()
	{
		returnToTrack = new ReturnToTrackSequence();
		
		addSequential(new PushContainerSequence());
		addSequential(new CloseGripper()); //Closing gripper before driving forward
		addSequential(returnToTrack);
	}
	
	protected void initialize ()
	{
		logger.info(this.getName() + " initialize");
		returnToTrack.startFieldIntegrator();
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
