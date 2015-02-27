package org.usfirst.frc.team3316.robot.subsystems;

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
	
    double[] prevHeights = new double[100];
    
    public double getHeight ()
    {
    	double currentHeight = (1 / heightIR.getVoltage());
    	double avg = currentHeight;
    	for(int i=0; i<prevHeights.length-1; i++) {
    		prevHeights[i] = prevHeights[i+1];
    		avg += prevHeights[i];
    	}
    	prevHeights[prevHeights.length-1] = currentHeight;
    	avg /= prevHeights.length;
    	return avg;
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

