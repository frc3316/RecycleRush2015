package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.WaitForGamePiece;
import org.usfirst.frc.team3316.robot.stacker.commands.CloseGripper;
import org.usfirst.frc.team3316.robot.stacker.commands.ControlledMoveStackerToFloor;
import org.usfirst.frc.team3316.robot.stacker.commands.ControlledMoveStackerToTote;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToFloor;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerDownToTote;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToStep;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToTote;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TotePickupSequence extends CommandGroup 
{    
	DBugLogger logger = Robot.logger;
	
	Command moveToEndPosition;
	
    public TotePickupSequence() 
    {
        addSequential(new MoveStackerToTote());
        addSequential(new CloseGripper());
        addSequential(new WaitForGamePiece());
        
        moveToEndPosition = new MoveToEndPosition();
    }
    
    protected void initialize ()
    {
    	logger.fine(this.getName() + "initialize");
    }
    
    protected void end ()
    {
    	logger.fine(this.getName() + "end");
    	/*
    	 * This is in end because we don't want this to execute if the sequence
    	 * was cancelled (and interrupted was called instead of end)
    	 */
    	moveToEndPosition.start();
    }
    
    protected void interrupted ()
    {
    	logger.fine(this.getName() + "interrupted");
    }
    
    private class MoveToEndPosition extends CommandGroup
    {
    	public MoveToEndPosition ()
    	{
    		addSequential(new MoveStackerDownToTote());
    		addSequential(new WaitForGamePiece());
    		addSequential(new MoveStackerToTote());
    	}
    	
    	protected void initialize ()
    	{
    	   	logger.fine(this.getName() + "initialize");
    	}
    	   
    	protected void end ()
    	{
    	  	logger.fine(this.getName() + "end");
    	}
    	
    	protected void interrupted ()
        {
        	logger.fine(this.getName() + "interrupted");
        }
    }
}
