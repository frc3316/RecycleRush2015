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
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Stacker extends Subsystem 
{	
    Config config = Robot.config;
    DBugLogger logger = Robot.logger;
    
    private DoubleSolenoid solenoidUpper, 
    					   solenoidBottom; //Lifting solenoids
    
    private DoubleSolenoid solenoidContainer; //The solenoid that holds the containers
    private DoubleSolenoid solenoidGripper; //The solenoid that opens and closes the roller gripper
    
    private AnalogInput heightIR; //infrared
    private MovingAverage heightAverage;
    
    private DigitalInput switchRight, switchLeft; //the switches that signify if there's a tote or a container in the stacker
    
    private double heightFloorMin, heightFloorMax,
    			   heightStepMin, heightStepMax,
    			   heightToteMin, heightToteMax;
    
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
    	
    	try
    	{
    		heightAverage = new MovingAverage(
    				(int) config.get("stacker_HeightAverage_Size"), 
    				(int) config.get("stacker_HeightAverage_UpdateRate"), 
    				() -> { return (1 / heightIR.getVoltage()); });
    	}
    	catch (ConfigException e)
    	{
    		logger.severe(e);
    	}
    }
    
    public void timerInit ()
    {
    	heightAverage.timerInit();
    }

    public void initDefaultCommand() {}
    
    public boolean openSolenoidUpper ()
    {
    	logger.fine("Try to open upper solenoid");
    	if (solenoidUpper.get() == DoubleSolenoid.Value.kForward)
    	{
    		logger.fine("Solenoid is already opened, aborting");
    		return false;
    	}
    	if (solenoidContainer.get() == DoubleSolenoid.Value.kForward)
    	{
    		logger.fine("Container solenoid is opened, aborting");
    		return false;
    	}
    	logger.fine("Solenoid upper opened");
    	solenoidUpper.set(DoubleSolenoid.Value.kForward);
    	return true;
    }
    
    public boolean closeSolenoidUpper ()
    {
    	logger.fine("Try to close upper solenoid");
    	if (solenoidUpper.get() == DoubleSolenoid.Value.kReverse)
    	{
    		logger.fine("Solenoid is already closed, aborting");
    		return false;
    	}
    	logger.fine("Solenoid upper closed");
    	solenoidUpper.set(DoubleSolenoid.Value.kReverse);
    	return true;
    }
    
    public boolean openSolenoidBottom ()
    {
    	logger.fine("Try to open bottom solenoid");
    	if (solenoidBottom.get() == DoubleSolenoid.Value.kForward)
    	{
    		logger.fine("Solenoid is already opened, aborting");
    		return false;
    	}
    	if (solenoidContainer.get() == DoubleSolenoid.Value.kForward)
    	{
    		logger.fine("Container solenoid is opened, aborting");
    		return false;
    	}
    	logger.fine("Solenoid bottom opened");
    	solenoidBottom.set(DoubleSolenoid.Value.kForward);
    	return true;
    }
    
    public boolean closeSolenoidBottom ()
    {
    	logger.fine("Try to close bottom solenoid");
    	if (solenoidBottom.get() == DoubleSolenoid.Value.kReverse)
    	{
    		logger.fine("Solenoid is already closed, aborting");
    		return false;
    	}
    	logger.fine("Solenoid upper closed");
    	solenoidBottom.set(DoubleSolenoid.Value.kReverse);
    	return true;
    }
    
    public boolean openSolenoidContainer ()
    {
    	logger.fine("Try to open container solenoid");
    	if (solenoidContainer.get() == DoubleSolenoid.Value.kForward)
    	{
    		logger.fine("Solenoid is already opened, aborting");
    		return false;
    	}
    	logger.fine("Solenoid container opened");
    	solenoidContainer.set(DoubleSolenoid.Value.kForward);
    	return true;
    }
    
    public boolean closeSolenoidContainer ()
    {
    	logger.fine("Try to close container solenoid");
    	if (solenoidContainer.get() == DoubleSolenoid.Value.kReverse)
    	{
    		logger.fine("Solenoid is already closed, aborting");
    		return false;
    	}
    	logger.fine("Solenoid container closed");
    	solenoidContainer.set(DoubleSolenoid.Value.kReverse);
    	return true;
    }
    
    public boolean openSolenoidGripper ()
    {
    	logger.fine("Try to open gripper solenoid");
    	if (solenoidGripper.get() == DoubleSolenoid.Value.kForward)
    	{
    		logger.fine("Solenoid is already opened, aborting");
    		return false;
    	}
    	logger.fine("Solenoid gripper opened");
    	solenoidGripper.set(DoubleSolenoid.Value.kForward);
    	return true;
    }
    
    public boolean closeSolenoidGripper ()
    {
    	logger.fine("Try to close gripper solenoid");
    	if (solenoidGripper.get() == DoubleSolenoid.Value.kReverse)
    	{
    		logger.fine("Solenoid is already closed, aborting");
    		return false;
    	}
    	logger.fine("Solenoid gripper closed");
    	solenoidGripper.set(DoubleSolenoid.Value.kReverse);
    	return true;
    }
    
    public double getHeight ()
    {
    	return heightAverage.get();
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
    	if (solenoidUpper.get() == DoubleSolenoid.Value.kForward &&
    			solenoidBottom.get() == DoubleSolenoid.Value.kForward)
    	{
    		return StackerPosition.Floor;
    	}
    	if (solenoidUpper.get() == DoubleSolenoid.Value.kReverse &&
    			solenoidBottom.get() == DoubleSolenoid.Value.kReverse)
    	{
    		return StackerPosition.Tote;
    	}
    	else
    	{
    		return StackerPosition.Step;
    	}
    }
    
    public StackerPosition getPositionIR ()
    {
    	updateHeights();
    	
    	double height = getHeight();
    	
    	if ((height > heightFloorMin) && (height < heightFloorMax))
    	{
    		return StackerPosition.Floor;
    	}
    	else if ((height > heightStepMin) && (height < heightStepMax))
    	{
    		return StackerPosition.Step;
    	}
    	else if ((height > heightToteMin) && (height < heightToteMax))
    	{
    		return StackerPosition.Tote;
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
}

