package org.usfirst.frc.team3316.robot.vision;

import java.util.logging.Logger;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SaveFrame extends Command
{
	Logger logger = Robot.logger;
	
	@Override
	protected void initialize()
	{
		logger.fine("Saving frame");
		((AutonomousCamera)Robot.autonCamera).saveFrame();
	}

	@Override
	protected void execute()
	{}

	@Override
	protected boolean isFinished()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void end()
	{}

	@Override
	protected void interrupted()
	{}

}
