package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

public class RollTurnCounterClockwise extends Roll
{
	protected void setSpeeds ()
	{
		try
		{
			this.left = (double) config.get("rollerGripper_RollTurnCounterClockwise_SpeedLeft"); 
			this.right = (double) config.get("rollerGripper_RollTurnCounterClockwise_SpeedRight");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}
