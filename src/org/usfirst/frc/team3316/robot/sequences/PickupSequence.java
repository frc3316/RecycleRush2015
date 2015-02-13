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
public class PickupSequence extends CommandGroup 
{    
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	AddGamePieceSequence addGamePieceSequence;
	
	public PickupSequence ()
	{
		addSequential(new MoveStackerToTote());
		addSequential(new RollIn());
		
		addGamePieceSequence = new AddGamePieceSequence();
	}
	
	protected void end ()
	{
		if (!Robot.stacker.isFull() && Robot.rollerGripper.getSwitchGamePiece())
		{
			addGamePieceSequence.start();
		}
	}
}
