package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.utils.MovingAverage;
import org.usfirst.frc.team3316.robot.utils.StackerPosition;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Stacker extends Subsystem
{
	public static class HeightTrigger extends Trigger
	{
		public boolean get()
		{
			return heightSwitch.get();
		}
	}

	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	private DoubleSolenoid solenoidContainer; // The solenoid that holds the
												// containers
	private DoubleSolenoid solenoidGripper; // The solenoid that opens and
											// closes the roller gripper
	private DoubleSolenoid solenoidBrake; // The solenoid that brakes the
											// elevator

	private static DigitalInput switchRight, switchLeft, heightSwitch; // the
																		// switches
	private static int heightPosition = 0; // that signify
	// if there's a
	// tote or a
	// container in
	// the stacker

	private SpeedController left1, left2;
	private SpeedController right1, right2;
	private double scale;

	private HeightTrigger heightTrigger;

	public Stacker()
	{

		solenoidContainer = Robot.actuators.stackerSolenoidContainer;
		solenoidGripper = Robot.actuators.stackerSolenoidGripper;

		switchRight = Robot.sensors.stackerSwitchRatchetRight;
		switchLeft = Robot.sensors.stackerSwitchRatchetLeft;
		heightSwitch = Robot.sensors.stackerSwitchHeight;

		heightTrigger = new HeightTrigger();
		heightTrigger.whenActive(new Command()
		{
			protected boolean isFinished()
			{
				return true;
			}

			protected void interrupted() {}

			protected void initialize() {}

			protected void execute()
			{
				if (left1.get() > 0)
				{
					heightPosition++;
				}
				else if (left1.get() < 0)
				{
					heightPosition--;
				}
			}

			protected void end() {}
		});

	}

	public void initDefaultCommand()
	{
	}

	/*
	 * In order to get to each height we use: - Floor Height: both solenoids
	 * extended - Step Height: bottom solenoid is retracted and upper extended -
	 * Tote Height: both solenoids retracted
	 */

	public boolean setMotors(double v)
	{
		updateScale();
		if (solenoidBrake.get() == DoubleSolenoid.Value.kReverse)
		{
			return false;
		}

		this.left1.set(v * scale);
		this.left2.set(v * scale);

		this.right1.set(v * -scale);
		this.right2.set(v * -scale);

		return true;

	}

	public boolean brakeOpen()
	{
		solenoidBrake.set(DoubleSolenoid.Value.kForward);
		{
			return true;
		}
	}

	public void moveDown()
	{

	}

	public boolean brakeClose()
	{
		solenoidBrake.set(DoubleSolenoid.Value.kReverse);
		{
			return true;
		}
	}

	public boolean openSolenoidContainer()
	{
		logger.fine("Try to open container solenoid");

		solenoidContainer.set(DoubleSolenoid.Value.kForward);
		logger.fine("Solenoid container opened");

		return true;
	}

	public boolean closeSolenoidContainer()
	{
		logger.fine("Try to close container solenoid");

		solenoidContainer.set(DoubleSolenoid.Value.kReverse);
		logger.fine("Solenoid container closed");

		return true;
	}

	public boolean openSolenoidGripper()
	{
		logger.fine("Try to open gripper solenoid");

		solenoidGripper.set(DoubleSolenoid.Value.kForward);
		logger.fine("Solenoid gripper opened");

		return true;
	}

	public boolean closeSolenoidGripper()
	{
		logger.fine("Try to close gripper solenoid");

		solenoidGripper.set(DoubleSolenoid.Value.kReverse);
		logger.fine("Solenoid gripper closed");

		return true;
	}

	public boolean getSwitchRatchetRight()
	{
		return switchRight.get();
	}

	public boolean getSwitchRatchetLeft()
	{
		return switchLeft.get();
	}

	public StackerPosition getPosition()
	{
		if (heightPosition == 0)
			return StackerPosition.Floor;
		if (heightPosition == 1)
			return StackerPosition.Step;
		else
			return StackerPosition.Tote;
	}

	 private void updateScale()
	    {
	    	try
	    	{
	    		scale = (double) config.get("stacker_Scale");
	    	}
	    	catch (ConfigException e)
	    	{
	    		logger.severe(e);
	    	}
	    }
}
