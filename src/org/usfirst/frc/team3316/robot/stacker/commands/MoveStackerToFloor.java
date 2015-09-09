package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.utils.StackerPosition;

/**
 * Moves stacker to floor position while checking
 * for mechanical safety constraints. 
 */
public class MoveStackerToFloor extends MoveStacker
{
	double v;
	@Override
	protected void initialize()
    {
		super.initialize();

		Robot.stacker.lastStackerSetpoint = StackerPosition.Floor;
		try
		{
			v = (double) Robot.config.get("stacker_ElevatorSpeed");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
    }
	
	protected void execute() 
	{
		Robot.stacker.setMotors(-v);
	}

	protected boolean isFinished()
	{
		if (Robot.stacker.getPosition() == StackerPosition.Floor && Robot.stacker.getHeightSwitch() == true)
		{
			Robot.stacker.closeBrakeAndHolders();
			return true;
		}
		return false;
	}
	
}
