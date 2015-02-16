package org.usfirst.frc.team3316.robot.subsystems;

import java.util.Stack;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.rollerGripper.GamePieceCollected;
import org.usfirst.frc.team3316.robot.stacker.GamePiece;
import org.usfirst.frc.team3316.robot.stacker.GamePieceType;
import org.usfirst.frc.team3316.robot.stacker.StackerPosition;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

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
	    	
	    	stack = new Stack <GamePiece>();
		}
		
		public StackerPosition getCurrentState ()
		{
			return currentState;
		}
		
		public boolean setSetpointState (StackerPosition setpoint)
		{
			if (setpointState != null)
			{
				return false;
			}
			setpointState = setpoint;
			return true;
		}
	    
		public void run ()
		{
			currentState = Robot.stacker.getPosition();
			
			if (setpointState == null)
			{
				return;
			}
			else if (currentState == setpointState)
			{
				setpointState = null;
				return;
			}
			
			GamePieceCollected gp = Robot.rollerGripper.getGamePieceCollected();
			
			if (setpointState == StackerPosition.Tote)
			{
				if (currentState == StackerPosition.Step)
				{
					moveToTote();
				}
				
				else if (currentState == StackerPosition.Floor)
				{
					//TODO: check if following condition should include tote as well
					if (gp == GamePieceCollected.Container)
					{
						openSolenoidContainer();
						openSolenoidGripper();
					}
					moveToTote();
				}
			}
			
			else if (setpointState == StackerPosition.Step)
			{
				if (currentState == StackerPosition.Tote)
				{
					if (gp == GamePieceCollected.None) //TODO: check if needs to open for container too
					{
						openSolenoidGripper();
					}
					moveToStep();
				}
				
				else if (currentState == StackerPosition.Floor)
				{
					if (gp == GamePieceCollected.Container)
					{
						openSolenoidContainer();
						openSolenoidGripper();
					}
					moveToStep();
				}
			}
			
			else if (setpointState == StackerPosition.Floor)
			{
				if (currentState == StackerPosition.Tote)
				{
					if (gp == GamePieceCollected.None)
					{
						closeSolenoidContainer();
						openSolenoidGripper();
					}
					moveToFloor();
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
	
		}//end of run
	
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
    
    private Stack <GamePiece> stack;
    
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
    	
    	manager = new StackerManager();
    	Robot.timer.schedule(manager, 0, 20);
    }
    
    public void initDefaultCommand() {}
    
    public boolean openSolenoidUpper ()
    {
    	solenoidUpper.set(DoubleSolenoid.Value.kForward);
    	return true;
    }
    
    public boolean closeSolenoidUpper ()
    {
    	solenoidUpper.set(DoubleSolenoid.Value.kReverse);
    	return true;
    }
    
    public boolean openSolenoidBottom ()
    {
    	solenoidBottom.set(DoubleSolenoid.Value.kForward);
    	return true;
    }
    
    public boolean closeSolenoidBottom ()
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
    
    /*
	 * Stack methods
	 */
    public GamePiece getStackBase ()
    {
    	if (stack.isEmpty())
    	{
    		return null;
    	}
    	return stack.get(0);
    }
    
    public boolean isFull() 
    {
    	return stack.size() >= 6;
    }
    
    public Stack <GamePiece> getStack() 
    {
    	return stack;
    }
    
    public void pushToStack(GamePiece g) 
    {
    	stack.push(g);
    }
    
    public void clearStack ()
    {
       	stack.clear();
    }
    
    private void addGamePiece ()
    {
    	GamePieceCollected gp = Robot.rollerGripper.getGamePieceCollected();
    	if (gp == GamePieceCollected.Container)
    	{
    		Robot.stacker.pushToStack(new GamePiece(GamePieceType.Container));
    	}
    	else if (gp == GamePieceCollected.Tote)
    	{
    		Robot.stacker.pushToStack(new GamePiece(GamePieceType.Tote));
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
     * Methods for StackerManager
     */
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

