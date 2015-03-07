package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.rollerGripper.commands.AutoRollIn;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToStep;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToTote;
import org.usfirst.frc.team3316.robot.stacker.commands.OpenGripper;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutonomousSequenceWithoutDrive extends CommandGroup {
    
    public  AutonomousSequenceWithoutDrive() {
        addSequential(new AutoRollIn());
        addSequential(new MoveStackerToStep());
        addSequential(new OpenGripper());
        addSequential(new WaitCommand(1));
        addSequential(new MoveStackerToTote());
    }
}
