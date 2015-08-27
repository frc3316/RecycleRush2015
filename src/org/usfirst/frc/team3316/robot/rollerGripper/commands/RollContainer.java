package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

/**
 *
 */
public class RollContainer extends Roll 
{
	protected void setSpeeds() 
	{
		try
		{
			left = (double) config.get("rollerGripper_RollContainer_LeftSpeed");
			right = (double) config.get("rollerGripper_RollContainer_RightSpeed");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}