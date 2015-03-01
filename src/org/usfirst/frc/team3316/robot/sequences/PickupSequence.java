package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.WaitForGamePiece;
import org.usfirst.frc.team3316.robot.stacker.commands.CloseGripper;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToFloor;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToStep;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToTote;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PickupSequence extends CommandGroup 
{    
	DBugLogger logger = Robot.logger;
	
	Command moveToEndPosition;
	
    public PickupSequence() 
    {
        addSequential(new MoveStackerToTote());
        addSequential(new CloseGripper());
        addSequential(new WaitForGamePiece());
        
        moveToEndPosition = new MoveToEndPosition();
    }
    
    protected void initialize ()
    {
    	logger.fine("PickupSequence initialize");
    }
    
    protected void end ()
    {
    	logger.fine("PickupSequence end");
    	/*
    	 * This is in end because we don't want this to execute if the sequence
    	 * was cancelled (and interrupted was called instead of end)
    	 */
    	moveToEndPosition.start();
    }
    
    protected void interrupted ()
    {
    	logger.fine("PickupSequence interrupted");
    }
    
    private class MoveToEndPosition extends CommandGroup
    {
    	public MoveToEndPosition ()
    	{
    		addSequential(new MoveStackerToFloor());
    		addSequential(new WaitForGamePiece());
    		addSequential(new MoveStackerToTote());
    	}
    }
}
