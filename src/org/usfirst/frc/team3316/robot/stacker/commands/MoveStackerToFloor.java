package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

/**
 * Moves stacker to floor position while checking for mechanical safety
 * constraints.
 */
public class MoveStackerToFloor extends MoveStacker
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
		Robot.stacker.closeSolenoidContainer();
	}
}
