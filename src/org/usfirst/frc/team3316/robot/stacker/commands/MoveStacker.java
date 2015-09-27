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
		pidHeight.setOutputRange(-1, 1);

	}

	protected abstract void setSetpoint();

	protected void initialize()
	{
		logger.fine(this.getName() + " initialize");
		
		unbrake.start();
		brakeStarted = false;
		
		setSetpoint();
		
		pidHeight.enable();
	}

	protected void execute()
	{
		updatePIDValues();
	}

	protected boolean isFinished()
	{
		if (pidHeight.onTarget())
		{
			brake.start();
			brakeStarted = true;
		}
		
		return brakeStarted && !brake.isRunning();
	}

	protected void end()
	{
		logger.fine(this.getName() + " end");
		_end();
	}

	protected void interrupted()
	{
		logger.fine(this.getName() + " interrupted");
		_end();
	}

	private void _end()
	{
		pidHeight.reset();
		Robot.stacker.setMotors(0);
	}

	private void updatePIDValues()
	{
		try
		{
			pidHeight.setPID(
					(double) config.get("stacker_MoveStacker_PIDHeight_KP"),
					(double) config.get("stacker_MoveStacker_PIDHeight_KI"),
					(double) config.get("stacker_MoveStacker_PIDHeight_KD"));

			pidHeight.setAbsoluteTolerance((double) config
					.get("stacker_MoveStacker_PIDHeight_AbsoluteTolerance"));
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}
