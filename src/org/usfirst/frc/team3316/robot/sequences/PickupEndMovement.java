package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToFloor;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToTote;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PickupEndMovement extends CommandGroup 
{    
    public  PickupEndMovement() 
    {
    	addSequential(new MoveStackerToFloor());
    	addSequential(new MoveStackerToTote());
    }
}
