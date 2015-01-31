package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.stacker.GamePiece;
import org.usfirst.frc.team3316.robot.stacker.GamePieceType;
import org.usfirst.frc.team3316.robot.subsystems.Stacker;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
 //CR: All MoveStackerTo* commands should inherit from a basic 'MoveStacker' abstract command
 //    since all of them do the same thing:
 //     * override an abstract which will set your solenoids
 //     * override constructor with correct mix/min height variable names
 //    and then you don't need to rewrite the logic in initialize, isFinished and updateHeightRange
 //    additionally the basic MoveStacker command should have a 'canRun' function that will be called
 //    when initializing. In this function we will run any prerequisite checks similar to 'isFull';
 //    by default the 'canRun' function will simply return true, but if you want to add logic, 
 //    all you need to do is override it.
public class MoveStackerToTote extends Command 
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	private double heightMax, heightMin;
	
    public MoveStackerToTote() 
    {
        requires(Robot.stacker);
    }

	protected void initialize() 
    {
    	if(!Robot.stacker.isFull()) 
    	{
    		Robot.stacker.closeSolenoidStep();
    		Robot.stacker.closeSolenoidTote();
    	}
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
			heightMax = (double) config.get("STACKER_MOVE_STACKER_TO_TOTE_HEIGHT_MAX");
			heightMin = (double) config.get("STACKER_MOVE_STACKER_TO_TOTE_HEIGHT_MIN");
		} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
    }
}
