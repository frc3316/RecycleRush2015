package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command that finishes when a game piece is completely collected
 */
public class WaitForGamePiece extends Command
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;

	private int finishCounter;
	private int maxFinishCounter;

	public WaitForGamePiece()
	{
	}

	protected void initialize()
	{
		logger.fine(this.getName() + " initialize");

		finishCounter = 0;
		try
		{
			maxFinishCounter = (int) config
					.get("rollerGripper_WaitForTote_MaxFinishCounter");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}

	protected void execute()
	{
	}

	protected boolean isFinished()
	{
		if (Robot.rollerGripper.getSwitchGamePiece())
		{
			finishCounter++;
		}
		else
		{
			finishCounter = 0;
		}

		return finishCounter >= maxFinishCounter;
	}

	protected void end()
	{
		logger.fine(this.getName() + " end");
	}

	protected void interrupted()
	{
		logger.fine(this.getName() + " interrupted");
	}
}
