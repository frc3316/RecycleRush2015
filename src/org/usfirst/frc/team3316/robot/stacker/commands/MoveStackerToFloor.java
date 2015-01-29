package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.stacker.GamePiece;
import org.usfirst.frc.team3316.robot.stacker.GamePieceType;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveStackerToFloor extends Command 
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	private double heightMax, heightMin;
	
    public MoveStackerToFloor() 
    {
        requires(Robot.stacker);
    }

    protected void initialize() 
    {
    	if(Robot.sensors.stackerSwitchContainer.get()) {
    		if(Robot.sensors.stackerSwitchTote.get())
    			Robot.stacker.pushToStack(new GamePiece(GamePieceType.GreyTote));
    		else
    			Robot.stacker.pushToStack(new GamePiece(GamePieceType.Container));
    	}
    	Robot.stacker.openSolenoidStep();
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
			heightMax = (double) config.get("STACKER_MOVE_STACKER_TO_FLOOR_HEIGHT_MAX");
			heightMin = (double) config.get("STACKER_MOVE_STACKER_TO_FLOOR_HEIGHT_MIN");
		} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
    }
}
