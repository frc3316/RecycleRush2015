package org.usfirst.frc.team3316.robot.subsystems;

import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.rollerGripper.GamePieceCollected;
import org.usfirst.frc.team3316.robot.stacker.StackerPosition;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Stacker extends Subsystem 
{	
	private class StackerManager extends TimerTask
	{
		
		private StackerPosition currentState;
		private StackerPosition setpointState;
		
		public StackerManager ()
		{	
			currentState = Robot.stacker.getPosition();
			setpointState = null;
		}
		
		public StackerPosition setSetpointState (StackerPosition setpoint)
		{

			if (setpoint == null)
			{
				return null;
			}
			
			if (setpointState != null)
			{
				return null;
			}

			GamePieceCollected gp = Robot.rollerGripper.getGamePieceCollected();
			
			if (setpoint == StackerPosition.Tote)
			{
				if (currentState == StackerPosition.Step)
				{
					moveToTote();
				}
				
				else if (currentState == StackerPosition.Floor)
				{
					//if one of the ratchets is not in place - abort
					if (!Robot.stacker.getSwitchRatchetLeft() ||
						!Robot.stacker.getSwitchRatchetRight())
					{
						return null;
					}
					
					//TODO: check if following condition should include tote as well (only for opening gripper)
					if (gp == GamePieceCollected.Container)
					{
						openSolenoidContainer();
						openSolenoidGripper();
					}
					moveToTote();
				}
			}
			
			else if (setpoint == StackerPosition.Step)
			{
				if (currentState == StackerPosition.Tote)
				{
					if (gp == GamePieceCollected.None) //TODO: check if needs to open for container too
					{
						openSolenoidGripper();
						closeSolenoidContainer();
					}
					moveToStep();
				}
				
				else if (currentState == StackerPosition.Floor)
				{
					//if one of the ratchets is not in place - abort
					if (!Robot.stacker.getSwitchRatchetLeft() ||
						!Robot.stacker.getSwitchRatchetRight())
					{
						return null;
					}
					
					//TODO: check if following condition should include tote as well (only for opening gripper)
					if (gp == GamePieceCollected.Container)
					{
						openSolenoidContainer();
						openSolenoidGripper();
					}
					moveToStep();
				}
			}
			
			else if (setpoint == StackerPosition.Floor)
			{
				if (currentState == StackerPosition.Tote)
				{	
					if (gp == GamePieceCollected.None ||
							gp == GamePieceCollected.Unsure)
					{
						closeSolenoidContainer();
						openSolenoidGripper();
						setpointState = StackerPosition.Step;
						moveToStep();
					}
					
					else
					{
						moveToFloor();
					}
				}
				
				else if (currentState == StackerPosition.Step)
				{
					if (gp == GamePieceCollected.None)
					{
						closeSolenoidContainer();
						openSolenoidGripper();
					}
					moveToFloor();
				}
			}
			setpointState = setpoint;
			
			return setpointState;
		}
	    
		public void run ()
		{
			currentState = Robot.stacker.getPosition();
			
			if (currentState == setpointState)
			{
				setpointState = null;
				return;
			}
			
			//FOR TESTING. NEEDS TO BE REMOVED.
			SmartDashboard.putString("Current State", currentState.toString());
			SmartDashboard.putString("Setpoint State", setpointState.toString());
		}
	
	}//end of class
	
    Config config = Robot.config;
    DBugLogger logger = Robot.logger;
    
    private DoubleSolenoid solenoidUpper, 
    					   solenoidBottom; //Lifting solenoids
    
    private DoubleSolenoid solenoidContainer; //The solenoid that holds the containers
    private DoubleSolenoid solenoidGripper; //The solenoid that opens and closes the roller gripper
    
    private AnalogInput heightIR; //infrared
    private DigitalInput switchRight, switchLeft; //the switches that signify if there's a tote or a container in the stacker
    
    private double heightFloorMin, heightFloorMax,
    			   heightStepMin, heightStepMax,
    			   heightToteMin, heightToteMax;
    
    private StackerManager manager;
    
    public Stacker () 
    {
		/* In order to get to each height we use:
		 *  - Floor Height: both solenoids extended
	     *  - Step Height: bottom solenoid is retracted and upper extended
		 *  - Tote Height: both solenoids retracted
		 */
    	solenoidUpper = Robot.actuators.stackerSolenoidUpper;
    	
    	solenoidBottom = Robot.actuators.stackerSolenoidBottom;
    	
    	solenoidContainer = Robot.actuators.stackerSolenoidContainer;
		solenoidGripper = Robot.actuators.stackerSolenoidGripper;
    	
    	heightIR = Robot.sensors.stackerHeightIR;
    	
    	switchRight = Robot.sensors.switchRatchetRight;
    	switchLeft = Robot.sensors.switchRatchetLeft;
    }
    
    public void timerInit ()
    {
    	manager = new StackerManager();
    	Robot.timer.schedule(manager, 0, 20);
    }
    
    public void initDefaultCommand() {}
    
    private boolean openSolenoidUpper ()
    {
    	solenoidUpper.set(DoubleSolenoid.Value.kForward);
    	return true;
    }
    
    private boolean closeSolenoidUpper ()
    {
    	solenoidUpper.set(DoubleSolenoid.Value.kReverse);
    	return true;
    }
    
    private boolean openSolenoidBottom ()
    {
    	solenoidBottom.set(DoubleSolenoid.Value.kForward);
    	return true;
    }
    
    private boolean closeSolenoidBottom ()
    {
    	solenoidBottom.set(DoubleSolenoid.Value.kReverse);
    	return true;
    }
    
    public boolean openSolenoidContainer ()
    {
    	solenoidContainer.set(DoubleSolenoid.Value.kForward);
    	return true;
    }
    
    public boolean closeSolenoidContainer ()
    {
    	solenoidContainer.set(DoubleSolenoid.Value.kReverse);
    	return true;
    }
    
    public boolean openSolenoidGripper ()
    {
    	solenoidGripper.set(DoubleSolenoid.Value.kForward);
    	return true;
    }
    
    public boolean closeSolenoidGripper ()
    {
    	solenoidGripper.set(DoubleSolenoid.Value.kReverse);
    	return true;
    }
	
    public double getHeight ()
    {
    	return (1 / heightIR.getVoltage());
    }
    
    public boolean getSwitchRatchetRight ()
    {
    	return switchRight.get();
    }
    
    public boolean getSwitchRatchetLeft ()
    {
    	return switchLeft.get();
    }
    
    public StackerPosition getPosition ()
    {
    	updateHeights();
    	
    	double height = getHeight();
    	
    	if ((height > heightFloorMin) && (height < heightFloorMax))
    	{
    		return StackerPosition.Floor;
    	}
    	else if ((height > heightToteMin) && (height < heightToteMax))
    	{
    		return StackerPosition.Tote;
    	}
    	else if ((height > heightStepMin) && (height < heightStepMax))
    	{
    		return StackerPosition.Step;
    	}
    	else 
    	{
    		return null;
    	}
    }
    
    private void updateHeights ()
    {
    	try
    	{
    		heightFloorMin = (double) config.get("stacker_HeightFloorMinimum");
    		heightFloorMax = (double) config.get("stacker_HeightFloorMaximum");
    		
    		heightToteMin = (double) config.get("stacker_HeightToteMinimum");
    		heightToteMax = (double) config.get("stacker_HeightToteMaximum");
    		
    		heightStepMin = (double) config.get("stacker_HeightStepMinimum");
    		heightStepMax = (double) config.get("stacker_HeightStepMaximum");
    	}
    	catch (ConfigException e)
    	{
    		logger.severe(e);
    	}
    }
    
    /*
     * StackerManager Methods
     */
    public StackerPosition setSetpointState (StackerPosition setpoint)
    {
    	return manager.setSetpointState(setpoint);
    }
    
    private void moveToFloor ()
	{
		Robot.stacker.openSolenoidBottom();
		Robot.stacker.openSolenoidUpper();
	}
	
	private void moveToStep ()
	{
		Robot.stacker.openSolenoidBottom();
		Robot.stacker.closeSolenoidUpper();
	}
	
	private void moveToTote ()
	{
		Robot.stacker.closeSolenoidBottom();
		Robot.stacker.closeSolenoidUpper();
	}
}

