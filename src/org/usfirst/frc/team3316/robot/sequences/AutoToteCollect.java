package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.rollerGripper.commands.AutoRollIn;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.WaitForTote;
import org.usfirst.frc.team3316.robot.stacker.commands.CloseGripper;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToFloor;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToStep;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToTote;
import org.usfirst.frc.team3316.robot.stacker.commands.OpenGripper;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoToteCollect extends CommandGroup {
    
    public  AutoToteCollect() {
        addSequential(new MoveStackerToTote());
        addSequential(new CloseGripper());
        addSequential(new AutoRollIn());
        addSequential(new AutoTotePickup());
    }
}
