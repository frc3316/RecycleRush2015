package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

public class MoveStackerToFloorWithIR extends MoveStackerToFloorWithoutIR
{
	private double heightMax, heightMin;
	
	protected boolean isFinished() 
    {
		double currentHeight = Robot.stacker.getHeight();
    	updateHeightRange();
        return (currentHeight > heightMin) && (currentHeight < heightMax);
    }
	
	private void updateHeightRange ()
    {
    	try 
    	{
			heightMax = (double) config.get("STACKER_MOVE_STACKER_TO_FLOOR_HEIGHT_MAX");
			heightMin = (double) config.get("STACKER_MOVE_STACKER_TO_FLOOR_HEIGHT_MIN");
		} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
    }
}
