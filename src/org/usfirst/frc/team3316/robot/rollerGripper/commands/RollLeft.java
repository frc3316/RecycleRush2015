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
			left = (double) config.get("rollerGripper_RollOut_Left");
			right = (double) config.get("rollerGripper_RollOut_Right");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}