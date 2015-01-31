package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.stacker.GamePiece;
import org.usfirst.frc.team3316.robot.stacker.GamePieceType;

public class RollIn extends Roll
{
	protected boolean isFinished ()
	{
		//Finishes one second after gamepiece switch is pressed
		if (Robot.sensors.stackerSwitchGamePiece.get())
		{
			setTimeout(1);
		}
		return isTimedOut();
	}
	
	protected void end()
	{
		super.end();
		/*
		 * Adds Gamepiece collected to the stack
		 */
		if (Robot.sensors.stackerSwitchGamePiece.get()) 
    	{
    		if(Robot.sensors.stackerSwitchTote.get())
    		{
    			Robot.stacker.pushToStack(new GamePiece(GamePieceType.GreyTote));
    		}
    		else
    		{
    			Robot.stacker.pushToStack(new GamePiece(GamePieceType.Container));
    		}
    	}
	}
	
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
