package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

/**
 *
 */
public class RollLeft extends Roll 
{
	protected void setSpeeds() 
	{
		try
		{
			left = (double) config.get("rollerGripper_PushContainerSequence_LeftSpeed_MoveLeft");
			right = (double) config.get("rollerGripper_PushContainerSequence_RightSpeed_MoveLeft");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}