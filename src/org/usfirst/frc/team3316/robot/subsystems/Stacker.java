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
import edu.wpi.first.wpilibj.DigitalSource;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
			boolean get = getHeightSwitch();

			//logger.finest("Height switch get: " + get);
			
			if (get != previousGet)
			{
				//logger.fine("Found stacker hall effect");
				
				if (get && !previousGet)
				{
					//logger.fine("Hall effect activated");
				}
				else
				{
					//logger.fine("Hall effect deactivated");
				}
				
				
				if (right.get() > lowPass)
				{
					heightPosition += 0.5; // Adds 0.5 to heightPosition if going up
					//logger.fine("stacker going up");
				}
				else // if (right.get() < lowPass)
				{
					heightPosition -= 0.5; // Lowers 0.5 to heightPosition if going down
					//logger.fine("stacker going down");
				}
				
				//logger.fine("height position is: " + heightPosition);
			}
			
			previousGet = get;
			/*
			if (right.get() > lowPass)
			{
				heightCounter.setReverseDirection(false);
			}
			else 
			{
				heightCounter.setReverseDirection(true);
			}
			*/
			
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
	
	public class HeightCounterDirectionSource extends DigitalInput
	{
		public HeightCounterDirectionSource ()
		{
			this(20);
		}

		public HeightCounterDirectionSource(int channel) 
		{
			super(channel);
		}
		
		public boolean get ()
		{
			logger.finer("Someone tried to get me");
			return right.get() > lowPass;
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

	private DigitalInput switchRight, switchLeft, heightSwitch; // the switches
																		// that signify
																		// if there's
																		// a tote or a
																		// container in
																		// the stacker
	private Counter heightCounter;

	// CR: should check starting position (if it's floor, step or floor) from a
	// config variable
	private double heightPosition = 0; // the position of the stacker:
												// 0 - floor, 2 - step, 4 - tote
	private int height = 0;
	private SpeedController left;
	private SpeedController right;

	private double downScale = 0; //Initial assignment, later updated from config
	private double upScale = 0; //Initial assignment, later updated from config
	private double lowPass = 0;
	private boolean isMovementAllowed = true;

	private HeightTask heightTask;

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
		//heightCounter.setUpSourceEdge(true, true);
		//heightCounter.setDownSource(new HeightCounterDirectionSource());
		//heightCounter.setExternalDirectionMode();
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

	/*
	 * In order to get to each height we use: - Floor Height: both solenoids
	 * extended - Step Height: bottom solenoid is retracted and upper extended -
	 * Tote Height: both solenoids retracted
	 */

	public boolean setMotors(double v)
	{
		updateSetMotors();
		
		
		if (isMovementAllowed == false)
		{
			return false;
		}

		// TODO: REMOVE THIS AFTER MANUAL TESTING
		SmartDashboard.putNumber("Stacker setMotors value: ", v);
		//logger.fine("Stacker setMotors value: " + v);
		//logger.fine("Stacker speed controller get: " + right.get());
		
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
	
	public boolean moveDown()
	{
		return setMotors(downScale);
	}
	
	public boolean moveUp()
	{
		return setMotors(upScale);
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

	public boolean disallowStackMovement()
	{
		logger.fine("Try to close brake solenoid (brake)");
		solenoidBrake.set(DoubleSolenoid.Value.kReverse);
		logger.fine("Solenoid brake closed");

		logger.fine("Try to close holder solenoid");
		solenoidHolder.set(DoubleSolenoid.Value.kForward);
		logger.fine("Solenoid holder closed");
		
		return true;
	}
	
	public boolean isMovementAllowed ()
	{
		return isMovementAllowed;
	}
	
	public void setMovementAllowed(boolean bool)
	{
		isMovementAllowed = bool;
	}

	public boolean getSwitchRatchetRight()
	{
		return switchRight.get();
	}

	public boolean getSwitchRatchetLeft()
	{
		return switchLeft.get();
	}
	
	public double getHeightPosition ()
	{
		return heightPosition;
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

	public int getHeight ()
	{
		return height;
	}
	
	private void updateSetMotors()
	{
		try
		{
			downScale = (double) config.get("stacker_MoveDown_Scale");
			upScale = (double) config.get("stacker_MoveUp_Scale");
			lowPass = (double) config.get("stacker_SetMotors_LowPass");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}
