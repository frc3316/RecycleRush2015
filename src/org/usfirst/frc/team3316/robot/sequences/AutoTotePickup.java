package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.rollerGripper.commands.WaitForTote;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToFloor;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToStep;
import org.usfirst.frc.team3316.robot.stacker.commands.OpenGripper;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTotePickup extends CommandGroup {
    
    public  AutoTotePickup() {
        addSequential(new OpenGripper());
        addSequential(new MoveStackerToFloor());
        addSequential(new WaitForTote());
        addSequential(new MoveStackerToStep());
    }
}
