package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.rollerGripper.GamePieceCollected;

public class RollOut extends Roll
{
	protected void setSpeeds ()
	{
		try
		{
			this.left = (double) config.get("rollerGripper_RollOut_SpeedLeft"); 
			this.right = (double) config.get("rollerGripper_RollOut_SpeedRight");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
	
	protected boolean isFinished ()
	{
		return Robot.rollerGripper.getGamePieceCollected() == GamePieceCollected.None;
	}
}
