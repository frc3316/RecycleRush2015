package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.utils.StackerPosition;

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
					.get("stacker_MoveStackerToTote_SetPoint"));
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}

	protected void initialize()
	{
		super.initialize();

		logger.fine("Ratchet right: " + Robot.stacker.getSwitchRatchetRight());
		logger.fine("Ratchet left: " + Robot.stacker.getSwitchRatchetLeft());
		logger.fine("Stacker Position: " + Robot.stacker.getPosition().toString());
		
		if (!(Robot.stacker.getSwitchRatchetLeft())
				&& (!Robot.stacker.getSwitchRatchetRight())
				&& (Robot.stacker.getPosition() == StackerPosition.Floor))
		{
			logger.fine("It's a container!");
			Robot.stacker.openSolenoidContainer();
			Robot.stacker.openSolenoidGripper();
		}
	}
}
