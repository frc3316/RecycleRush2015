package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.RollIn;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToFloor;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToTote;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class pickupSequence extends CommandGroup 
{    
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	protected void initialize() 
	{
		if (Robot.stacker.isFull())
		{
			this.cancel();
		}
	}
    public pickupSequence() 
    {
    	addSequential(new MoveStackerToTote()); //makes sure the new gamepiece can enter
    	addSequential(new RollIn());
        addSequential(new MoveStackerToFloor());
        addSequential(new MoveStackerToTote());
    }
}
