package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

public class RollIn extends Roll
{
	protected boolean isFinished ()
	{
		return Robot.sensors.stackerSwitchContainer.get(); //a GamePiece(tote/container) is inside the stacker
	}
	
	protected void setSpeed() 
    {
    	try
    	{
			speedLeft = (double) config.get("rollerGripper_RollIn_Speed_Left");
			speedRight = (double) config.get("rollerGripper_RollIn_Speed_Right");
		} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
    }
}
