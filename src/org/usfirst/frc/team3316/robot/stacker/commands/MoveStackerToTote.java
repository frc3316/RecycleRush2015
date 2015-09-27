package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.utils.GamePieceCollected;

/**
 * Moves stacker to tote position while checking for mechanical safety
 * constraints.
 */

public class MoveStackerToTote extends MoveStacker
{
	protected void setSetpoint()
	{
		try
		{
			pidHeight.setSetpoint((double) config
					.get("stacker_MoveStackerToFloor_SetPoint"));
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}

	protected void initialize()
	{
		super.initialize();
		if (Robot.rollerGripper.getGamePieceCollected() == GamePieceCollected.Container)
		{
			Robot.stacker.openSolenoidContainer();
			Robot.stacker.openSolenoidGripper();
		}
	}
}
