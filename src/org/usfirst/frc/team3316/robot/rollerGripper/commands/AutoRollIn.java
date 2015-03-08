package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

/**
 *
 */
public class AutoRollIn extends Roll 
{
	private int finishCounter;
	private int maxFinishCounter;
	
    public AutoRollIn() 
    {
        requires(Robot.rollerGripper);
    }

    protected void initialize() 
    {
    	finishCounter = 0;
    }

	protected void setSpeeds() 
	{	
		try
		{
			left = (double) config.get("rollerGripper_AutoRollIn_Left");
			right = (double) config.get("rollerGripper_AutoRollIn_Right");
			
			maxFinishCounter = (int) config.get("rollerGripper_AutoRollIn_MaxFinishCounter");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
	
	protected boolean isFinished() 
    {
        if (Robot.rollerGripper.getSwitchGamePiece())
        {
        	finishCounter++;
        }
        else
        {
        	finishCounter = 0;
        }
        return finishCounter >= maxFinishCounter;
    }
}
