package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.utils.StackerPosition;

/**
 * Moves stacker to step position while checking
 * for mechanical safety constraints. 
 */
public class MoveStackerToStep extends MoveStacker
{
	double v;
	@Override
	protected void initialize()
    {
		super.initialize();

		try
		{
			v = (double) Robot.config.get("stacker_ElevatorSpeed");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
    }

	@Override
	protected void execute()
	{
		Robot.stacker.setMotors(v);
	}

	@Override
	protected boolean isFinished()
	{
		if (Robot.stacker.getPosition() == StackerPosition.Step && Robot.stacker.getHeightSwitch() == true)
		{
			Robot.stacker.closeBrakeAndHolders();
			return true;
		}
		return false;
	}
}
