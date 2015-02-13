package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.stacker.commands.AddGamePieceToStack;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToFloor;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToTote;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AddGamePieceSequence extends CommandGroup 
{    
    public  AddGamePieceSequence() 
    {
        addSequential(new MoveStackerToFloor());
        addSequential(new AddGamePieceToStack());
        addSequential(new MoveStackerToTote());
    }
}
