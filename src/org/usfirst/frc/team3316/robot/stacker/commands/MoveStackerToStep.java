package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveStackerToStep extends Command {

	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	private double heightMax, heightMin;
	
    public MoveStackerToStep()
    {
        requires(Robot.stacker);
    }

    protected void initialize() 
    {
    	Robot.stacker.closeSolenoidStep();
    	Robot.stacker.openSolenoidTote();
    }

    protected void execute() {
    }

    protected boolean isFinished() 
    {
    	double currentHeight = Robot.stacker.getHeight();
    	updateHeightRange();
        return (currentHeight > heightMin) && (currentHeight < heightMax);
    }
    protected void end() {
    }

    protected void interrupted() {
    }
    
    private void updateHeightRange ()
    {
    	try 
    	{
			heightMax = (double) config.get("STACKER_MOVE_STACKER_TO_STEP_HEIGHT_MAX");
			heightMin = (double) config.get("STACKER_MOVE_STACKER_TO_STEP_HEIGHT_MIN");
		} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
    }
}
