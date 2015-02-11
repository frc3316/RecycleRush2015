package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

/**
 *
 */
public class MoveStackerToFloor extends MoveStacker
{
	double gpDistanceMin, gpDistanceMax;
	
    public MoveStackerToFloor() 
    {
        super("STACKER_MOVE_STACKER_TO_FLOOR_HEIGHT_MAX",
        		"STACKER_MOVE_STACKER_TO_FLOOR_HEIGHT_MIN");
    }

	protected void setSolenoids() 
	{
		
		Robot.stacker.closeSolenoidContainer();
		Robot.stacker.openSolenoidUpper();
		Robot.stacker.openSolenoidBottom();
	}
	
	//TODO: implement this method in setSolenoids()
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
