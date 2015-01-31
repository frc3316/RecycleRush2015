package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

public class RollIn extends Roll
{	
	protected void setSpeed() 
    {
    	try
    	{
			speedLeft = (double) config.get("rollerGripper_RollIn_SpeedLeft");
			speedRight = (double) config.get("rollerGripper_RollIn_SpeedRight");
		} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
    }
}
