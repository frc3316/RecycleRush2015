package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MoveStackerManually extends Command
{
	double v;
	DBugLogger logger = Robot.logger;

	public MoveStackerManually()
	{
		requires(Robot.stacker);
	}

	protected void initialize()
	{
		logger.fine(this.getName() + " initialize");
	}

	protected void execute()
	{
		v = -Robot.joysticks.joystickOperator.getRawAxis(5);
		
		SmartDashboard.putNumber("Stacker setMotors value: ", v);
		Robot.stacker.setMotors(v);
	}

	protected boolean isFinished()
	{
		return false;
	}

	protected void end()
	{
		Robot.stacker.setMotors(0);
	}

	protected void interrupted()
	{
		end();
	}
}
