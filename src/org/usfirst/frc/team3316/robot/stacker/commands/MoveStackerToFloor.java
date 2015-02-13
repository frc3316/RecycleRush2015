package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.rollerGripper.GamePieceCollected;

/**
 *
 */
public class MoveStackerToFloor extends MoveStacker
{
	double gpDistanceMin, gpDistanceMax;

	protected void setSolenoids() 
	{
		if (Robot.rollerGripper.getGamePieceCollected() == GamePieceCollected.None)
		{
			Robot.stacker.openSolenoidGripper();
		}
		Robot.stacker.closeSolenoidContainer();
		Robot.stacker.openSolenoidUpper();
		Robot.stacker.openSolenoidBottom();
	}
	
	private void updateDistanceRange ()
	{
		try
		{
			gpDistanceMax = (double) config.get("stacker_MoveStackerToFloor_GPDistanceMax");
			gpDistanceMin = (double) config.get("stacker_MoveStackerToFloor_GPDistanceMin");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}
