package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerManually;
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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Stacker extends Subsystem
{
	public class HeightTrigger extends Trigger
	{
		public boolean get()
		{
			return getHeightSwitch();
		}
	}

	private class HeightTriggerCommand extends Command
	{
		protected boolean isFinished()
		{
			return true;
		}

		protected void interrupted()
		{
		}

		protected void initialize()
		{
		}

		protected void execute()
		{
			if (left.get() > 0)
			{
				heightPosition += 0.5; // Adds 0.5 to heightPosition if going up
			}
			else if (left.get() < 0)
			{
				heightPosition -= 0.5; // Lowers 0.5 to heightPosition if going
										// down
			}
		}

		protected void end()
		{
		}
	}

	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	private DoubleSolenoid solenoidContainer; // The solenoid that holds the
												// containers
	private DoubleSolenoid solenoidGripper; // The solenoid that opens and
											// closes the roller gripper
	private DoubleSolenoid solenoidHolder; // The solenoid that opens and
											// closes the holders

	// CR: need to initialize this solenoid
	// CR: add stack balancing solenoid
	private DoubleSolenoid solenoidBrake; // The solenoid that brakes the
											// elevator

	private static DigitalInput switchRight, switchLeft, heightSwitch; // the switches
																		// that signify
																		// if there's
																		// a tote or a
																		// container in
																		// the stacker

	// CR: should check starting position (if it's floor, step or floor) from a
	// config variable
	private static double heightPosition = 0; // the position of the stacker:
												// 0 - floor, 2 - step, 4 - tote
	private SpeedController left;
	private SpeedController right;

	private double scale;
//	private double downScale = (double) config.get("STACKER_DOWNSCALE");

	private HeightTrigger heightTrigger;

	public Stacker()
	{

		left = Robot.actuators.elevatorMotorControllerLeft;
		right = Robot.actuators.elevatorMotorControllerRight;
		
		solenoidContainer = Robot.actuators.stackerSolenoidContainer;
		solenoidGripper = Robot.actuators.stackerSolenoidGripper;

		solenoidBrake = Robot.actuators.stackerSolenoidBrake;
		solenoidHolder = Robot.actuators.stackerSolenoidHolder;

		switchRight = Robot.sensors.stackerSwitchRatchetRight;
		switchLeft = Robot.sensors.stackerSwitchRatchetLeft;
		heightSwitch = Robot.sensors.stackerSwitchHeight;

		heightTrigger = new HeightTrigger();
		heightTrigger.whenInactive(new HeightTriggerCommand());
		heightTrigger.whenActive(new HeightTriggerCommand());
	}

	public void initDefaultCommand()
	{
		setDefaultCommand(new MoveStackerManually());
	}

	/*
	 * In order to get to each height we use: - Floor Height: both solenoids
	 * extended - Step Height: bottom solenoid is retracted and upper extended -
	 * Tote Height: both solenoids retracted
	 */

	public boolean setMotors(double v)
	{
		updateScale();
		
		if (solenoidBrake.get() == DoubleSolenoid.Value.kForward
				|| solenoidHolder.get() == DoubleSolenoid.Value.kReverse)
		{
			return false;
		}

		// TODO: REMOVE THIS AFTER MANUAL TESTING
		SmartDashboard.putNumber("Stacker setMotors value: ", v);

		this.left.set(v * scale);

		this.right.set(v * -scale);

		return true;
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

	public boolean openBrakeAndHolders()
	{
		logger.fine("Try to close brake solenoid");
		solenoidBrake.set(DoubleSolenoid.Value.kForward);
		logger.fine("Solenoid brake closed");

		logger.fine("Try to open holder solenoid");
		solenoidHolder.set(DoubleSolenoid.Value.kReverse);
		logger.fine("Solenoid holders opened");

		return true;
	}

	public boolean closeBrakeAndHolders()
	{
		logger.fine("Try to close brake solenoid");
		solenoidBrake.set(DoubleSolenoid.Value.kReverse);
		logger.fine("Solenoid brake closed");

		logger.fine("Try to close holder solenoid");
		solenoidHolder.set(DoubleSolenoid.Value.kForward);
		logger.fine("Solenoid holder closed");

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
		if (heightPosition == 2)
			return StackerPosition.Step;
		if (heightPosition == 4)
			return StackerPosition.Tote;
		else
			return StackerPosition.Unknown;
	}

	public boolean getHeightSwitch()
	{
		return !(heightSwitch.get());
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
