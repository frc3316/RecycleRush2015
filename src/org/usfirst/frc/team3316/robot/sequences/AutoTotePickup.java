package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.WaitForGamePiece;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToFloor;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToStep;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToTote;
import org.usfirst.frc.team3316.robot.stacker.commands.OpenGripper;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTotePickup extends CommandGroup
{
	Command endMovement = new MoveStackerToTote();
	
	public AutoTotePickup()
	{
		addSequential(new MoveStackerToFloor());
		addSequential(new WaitForGamePiece());
	}

	DBugLogger logger = Robot.logger;

	protected void initialize()
	{
		logger.info(this.getName() + " initialize");
	}
	
	protected void end()
	{
		logger.info(this.getName() + " end");
		
		if (Robot.stacker.totesCollected < 6)
		{
			endMovement.start();
		}
	}

	protected void interrupted()
	{
		logger.info(this.getName() + " interrupted");
	}

}
