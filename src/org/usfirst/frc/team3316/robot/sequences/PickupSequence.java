package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.rollerGripper.commands.WaitForGamePiece;
import org.usfirst.frc.team3316.robot.stacker.commands.CloseGripper;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToFloor;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToTote;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PickupSequence extends CommandGroup 
{    
	Command moveToEndPosition;
	
    public PickupSequence() 
    {
        addSequential(new MoveStackerToTote());
        addSequential(new CloseGripper());
        addSequential(new WaitForGamePiece());
        
        //TODO: check if this should be changed to a sequence of floor --> step
        moveToEndPosition = new MoveStackerToFloor();
    }
    
    protected void end ()
    {
    	/*
    	 * This is in end because we don't want this to execute if the sequence
    	 * was cancelled (and interrupted was called instead of end)
    	 */
    	moveToEndPosition.start();
    }
}
