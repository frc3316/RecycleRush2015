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
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Stacker extends Subsystem 
{	
    Config config = Robot.config;
    DBugLogger logger = Robot.logger;
    

    
    private DoubleSolenoid solenoidContainer; //The solenoid that holds the containers
    private DoubleSolenoid solenoidGripper; //The solenoid that opens and closes the roller gripper
    private DoubleSolenoid solenoidElevatorBrake; // The solenoid that brake the elevator
    
    private AnalogInput heightIR; //infrared
    private MovingAverage heightAverage;
    
    private DigitalInput switchRight, switchLeft, switchUp , switchMiddle , switchBottom; //the switches that signify if there's a tote or a container in the stacker
    
    private double heightFloorMin, heightFloorMax,
    			   heightStepMin, heightStepMax,
    			   heightToteMin, heightToteMax;
    
    private SpeedController left1, left2;
	private SpeedController right1, right2;
	private double leftScale , rightScale ;
    
    public Stacker () 
    {
    	
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
    
    /* 
	 * In order to get to each height we use:
	 *  - Floor Height: both solenoids extended
     *  - Step Height: bottom solenoid is retracted and upper extended
	 *  - Tote Height: both solenoids retracted
	 */
    
   
    
    public boolean setMotors (double v)
    { 
    	if (solenoidElevatorBrake.get() == DoubleSolenoid.Value.kReverse)
    	{
    		return false;
    	}
    	
    	this.left1.set (v * leftScale);
    	this.left2.set (v * leftScale);
    	
    	this.right1.set (v * rightScale);
    	this.right2.set (v * rightScale);
    	
    	return true;
    	
    	
    }
    
    public boolean brakeOpen()
    {
    	solenoidElevatorBrake.set(DoubleSolenoid.Value.kForward);
    	{
    		return true;
    	}
    }
   
    public boolean brakeClose()
    {
       solenoidElevatorBrake.set(DoubleSolenoid.Value.kReverse);
       {
    	   return true;
       }
    }
   
    
    
    public boolean openSolenoidContainer ()
    {
    	logger.fine("Try to open container solenoid");
    	
    	solenoidContainer.set(DoubleSolenoid.Value.kForward);
    	logger.fine("Solenoid container opened");
    	
    	return true;
    }
    
    public boolean closeSolenoidContainer ()
    {
    	logger.fine("Try to close container solenoid");
    	
    	solenoidContainer.set(DoubleSolenoid.Value.kReverse);
    	logger.fine("Solenoid container closed");
    	
    	return true;
    }
    
    public boolean openSolenoidGripper ()
    {
    	logger.fine("Try to open gripper solenoid");
    	
    	solenoidGripper.set(DoubleSolenoid.Value.kForward);
    	logger.fine("Solenoid gripper opened");
    	
    	return true;
    }
    
    public boolean closeSolenoidGripper ()
    {
    	logger.fine("Try to close gripper solenoid");
    	
    	solenoidGripper.set(DoubleSolenoid.Value.kReverse);
    	logger.fine("Solenoid gripper closed");
    	
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
    	if (switchUp.get())
    	{
    		return StackerPosition.Tote;
    	}
    	
    	if (switchBottom.get())
    	{
    		return StackerPosition.Floor;
    		
    	}
    	
    	if (switchMiddle.get())
    	{
    		return StackerPosition.Step;
    	}
    	
    	return null;
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

