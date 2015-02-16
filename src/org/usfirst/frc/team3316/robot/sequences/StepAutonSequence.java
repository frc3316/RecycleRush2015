package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.anschluss.commands.OpenAnschluss;
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedNavigation;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StepAutonSequence extends CommandGroup {

	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
    
	private double distanceForward;
	
    public  StepAutonSequence() {
    	setStepAutonSequenceDistanceStraight();
    	
        addSequential(new OpenAnschluss());
        addSequential(new RobotOrientedNavigation(0, distanceForward, 0));
    }
    
    private void setStepAutonSequenceDistanceStraight ()
    {
    	try
    	{
    		distanceForward = (double)config.get("chassis_StepAutonSequence_DistanceForward");
    	}
    	catch (ConfigException e)
    	{
    		logger.severe(e);
    	}
    }
}
