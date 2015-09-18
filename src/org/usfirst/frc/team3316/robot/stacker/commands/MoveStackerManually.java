package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedDrive;
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

	// Called just before this Command runs the first time
	protected void initialize()
	{
		logger.fine(this.getName() + " initialize");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute()
	{
		v = Robot.joysticks.joystickOperator.getRawAxis(5);
		/*
		if (!Robot.stacker.isMovementAllowed())
		{
			v = 0;
		}
		*/
		SmartDashboard.putNumber("Stacker setMotors value: ", v);
		Robot.stacker.setMotors(v);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished()
	{
		return false;
	}

	// Called once after isFinished returns true
	protected void end()
	{
		Robot.stacker.setMotors(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted()
	{
		end();
	}


}
