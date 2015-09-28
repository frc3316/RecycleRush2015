package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class MoveStacker extends Command
{
	private class PIDSourceHeight implements PIDSource
	{
		public double pidGet()
		{
			return Robot.stacker.getHeight();
		}
	}

	private class PIDOutputHeight implements PIDOutput
	{
		public void pidWrite(double output)
		{
			Robot.stacker.setMotors(output);
		}
	}

	DBugLogger logger = Robot.logger;
	Config config = Robot.config;

	PIDController pidHeight;

	Brake brake;
	UnBrake unbrake;

	boolean brakeStarted;

	public MoveStacker()
	{
		requires(Robot.stacker);

		brake = new Brake();
		unbrake = new UnBrake();

		pidHeight = new PIDController(0, 0, 0, new PIDSourceHeight(),
				new PIDOutputHeight(), 0.05);

		setSetpoint();
	}

	protected abstract void setSetpoint();

	protected void initialize()
	{
		logger.fine(this.getName() + " initialize");

		unbrake.start();
		brakeStarted = false;

		setTimeout(2);

		pidHeight.enable();
	}

	protected void execute()
	{
		updatePIDValues();
	}

	protected boolean isFinished()
	{
		if ((brakeStarted && !brake.isRunning()) || isTimedOut())
		{
			if (isTimedOut())
			{
				logger.info(this.getName() + " finished because timed out");
			}
			else
			{
				logger.info(this.getName() + " finished because reached target");
			}

			return true;
		}

		if (pidHeight.onTarget() && !brakeStarted)
		{
			brake.start();
			brakeStarted = true;
		}

		return false;
	}

	protected void end()
	{
		logger.fine(this.getName() + " end");
		__end();
	}

	protected void interrupted()
	{
		logger.fine(this.getName() + " interrupted");
		__end();
	}

	private void __end()
	{
		pidHeight.reset();
		Robot.stacker.setMotors(0);
	}

	private void updatePIDValues()
	{
		try
		{
			pidHeight
					.setPID((double) config
							.get("stacker_MoveStacker_PIDHeight_KP") / 1000,
							(double) config
									.get("stacker_MoveStacker_PIDHeight_KI") / 1000,
							(double) config
									.get("stacker_MoveStacker_PIDHeight_KD") / 1000);

			pidHeight.setAbsoluteTolerance((double) config
					.get("stacker_MoveStacker_PIDHeight_AbsoluteTolerance"));
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}
