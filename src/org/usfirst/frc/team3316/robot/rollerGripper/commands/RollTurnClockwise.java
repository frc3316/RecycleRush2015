package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

public class RollTurnClockwise extends Roll{

	protected void setSpeed() 
    {
    	try
    	{
			speedLeft = (double) config.get("rollerGripper_RollTurnClockwise_SpeedLeft");
			speedRight = (double) config.get("rollerGripper_RollTurnClockwise_SpeedRight");
		} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
    }
}
