package org.usfirst.frc.team3316.robot.subsystems;

import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerManually;
import org.usfirst.frc.team3316.robot.utils.StackerPosition;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Stacker extends Subsystem
{
	public class HeightTask extends TimerTask
	{
		boolean previousGet = false;
		
		int currentHeight, previousHeight = 0;

		public void run()
		{
			currentHeight = heightCounter.get();
			int heightDifference = currentHeight - previousHeight;

			if (right.get() > lowPass)
			{
				height += heightDifference;
			}
			else
			{
				height -= heightDifference;
			}
			previousHeight = currentHeight;
		}
	}

	public class HeightResetTrigger extends Trigger
	{
		public boolean get()
		{
			return getHeightSecure();
		}
	}

	public class HeightReset extends Command
	{
		protected void initialize()
		{
			height = 0;
		}

		protected void execute() {}

		protected boolean isFinished()
		{
			return true;
		}

		protected void end() {}

		protected void interrupted() {}
	}

	Config config = Robot.config;
	DBugLogger logger = Robot.logger;

	private DoubleSolenoid solenoidContainer; // The solenoid that holds the
												// containers
	private DoubleSolenoid solenoidGripper; // The solenoid that opens and
											// closes the roller gripper
	private DoubleSolenoid solenoidHolder; // The solenoid that opens and
											// closes the holders

	private DoubleSolenoid solenoidBrake; // The solenoid that brakes the
											// elevator

	private DigitalInput switchRight, switchLeft, heightSwitch, heightSecure; // the
																				// switches
																				// that
																				// signify
																				// if
																				// there's
																				// a
																				// tote
																				// or
																				// a
																				// container
																				// in
																				// the
																				// stacker
	private HeightResetTrigger heightResetTrigger;
	private Counter heightCounter;

	private int height = 0;
	private SpeedController left;
	private SpeedController right;

	private double lowPass = 0;

	private boolean isMovementAllowed = true;

	private HeightTask heightTask;

	private double floorHeight, stepHeight, toteHeight;
	private double heightTolerance;

	public int totesCollected = 0;
	
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
		heightCounter = Robot.sensors.stackerHeightCounter;
		heightSecure = Robot.sensors.stackerSwitchSecure;
		
		heightResetTrigger = new HeightResetTrigger();
		heightResetTrigger.whenActive(new HeightReset());
		heightResetTrigger.whenInactive(new HeightReset());
	}

	public void timerInit()
	{
		heightTask = new HeightTask();
		Robot.timer.schedule(heightTask, 0, 1);
	}

	public void initDefaultCommand()
	{
		setDefaultCommand(new MoveStackerManually());
	}

	/**
	 * Sets a certain % voltage to the stacker speed controllers
	 * 
	 * @param v
	 *            The % voltage
	 * @return true if succeeded in setting, false otherwise
	 */
	public boolean setMotors(double v)
	{
		updateSetMotors();

		if (isMovementAllowed == false)
		{
			return false;
		}

		if (v < lowPass && v > 0)
		{
			this.left.set(0);
			this.right.set(0);
			return false;
		}

		this.left.set(-v);
		this.right.set(v);
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

	/**
	 * Unbrakes the stacker and opens stack holders
	 * 
	 * @return true if succeeded, false otherwise
	 */
	public boolean allowStackMovement()
	{
		logger.fine("Try to close brake solenoid (unbrake)");
		solenoidBrake.set(DoubleSolenoid.Value.kForward);
		logger.fine("Solenoid brake closed");

		logger.fine("Try to open holder solenoid");
		solenoidHolder.set(DoubleSolenoid.Value.kReverse);
		logger.fine("Solenoid holders opened");

		return true;
	}

	/**
	 * Brakes the stacker and closes stack holders
	 * 
	 * @return true if succeeded, false otherwise
	 */
	public boolean disallowStackMovement()
	{
		logger.fine("Try to open brake solenoid (brake)");
		solenoidBrake.set(DoubleSolenoid.Value.kReverse);
		logger.fine("Solenoid brake closed");

		logger.fine("Try to close holder solenoid");
		solenoidHolder.set(DoubleSolenoid.Value.kForward);
		logger.fine("Solenoid holder closed");

		return true;
	}

	/**
	 * Returns whether the stacker can be moved
	 * 
	 * @param bool
	 *            true if stacker can move, false if cannot
	 */
	public boolean isMovementAllowed()
	{
		return isMovementAllowed;
	}

	/**
	 * Sets whether the stacker can be moved
	 * 
	 * @param bool
	 *            true if stacker can move, false if cannot
	 */
	public void setMovementAllowed(boolean bool)
	{
		isMovementAllowed = bool;
	}

	/**
	 * Returns whether the right ratchet is closed
	 * 
	 * @return true or false
	 */
	public boolean getSwitchRatchetRight()
	{
		return switchRight.get();
	}

	/**
	 * Returns whether the left ratchet is closed
	 * 
	 * @return true or false
	 */
	public boolean getSwitchRatchetLeft()
	{
		return switchLeft.get();
	}

	/**
	 * Returns whether the height switch (or counter) is currently facing a
	 * screw
	 * 
	 * @return true or false
	 */
	public boolean getHeightSwitch()
	{
		return !(heightSwitch.get());
	}

	/**
	 * Returns whether the elevator is at its lowest position (floor level)
	 * 
	 * @return true or false
	 */
	public boolean getHeightSecure()
	{
		return !(heightSecure.get());
	}

	/**
	 * Returns the height summed from the height counter
	 * 
	 * @return The current stacker height (in screws counted)
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Returns a stacker position based on which setpoint the stacker is at
	 * 
	 * @return Floor, Step or Tote if in one of the PID setpoint (at the
	 *         tolerance variable), otherwise Unknown
	 */
	public StackerPosition getPosition()
	{
		updateStackerHeights();

		if (Math.abs(height - floorHeight) <= heightTolerance)
		{
			return StackerPosition.Floor;
		}
		else if (Math.abs(height - stepHeight) <= heightTolerance)
		{
			return StackerPosition.Step;
		}
		else if (Math.abs(height - toteHeight) <= heightTolerance || height < 0)
		{
			return StackerPosition.Tote;
		}

		else
			return StackerPosition.Unknown;
	}

	private void updateSetMotors()

	{
		try
		{
			lowPass = (double) config.get("stacker_SetMotors_LowPass");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}	

	private void updateStackerHeights()
	{
		try
		{
			floorHeight = (double) config
					.get("stacker_MoveStackerToFloor_SetPoint");
			stepHeight = (double) config
					.get("stacker_MoveStackerToStep_SetPoint");
			toteHeight = (double) config
					.get("stacker_MoveStackerToTote_SetPoint");

			heightTolerance = (double) config
					.get("stacker_MoveStacker_PIDHeight_AbsoluteTolerance");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}
