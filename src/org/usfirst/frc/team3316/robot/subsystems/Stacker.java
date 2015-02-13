package org.usfirst.frc.team3316.robot.subsystems;

import java.util.Stack;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
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
    Config config = Robot.config;
    DBugLogger logger = Robot.logger;
    
    private DoubleSolenoid solenoidUpper, 
    					   solenoidBottom; //Lifting solenoids
    
    private DoubleSolenoid solenoidContainer; //The solenoid that holds the containers
    private DoubleSolenoid solenoidGripper; //The solenoid that opens and closes the roller gripper
    
    private AnalogInput heightIR; //infrared
    private DigitalInput switchRight, switchLeft; //the switches that signify if there's a tote or a container in the stacker
    
    private Stack <GamePiece> stack; 

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
    	
    	stack = new Stack <GamePiece>();
    }
    
    public void initDefaultCommand() {}
    
    public boolean openSolenoidUpper ()
    {
    	/*
    	 * Upper solenoid is opened only when trying to move to floor
    	 * Therefore it is the solenoid that when opened can harm the gripper or the container pistons
    	 */
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
    
    public boolean getSwitchRight ()
    {
    	return switchRight.get();
    }
    
    public boolean getSwitchLeft ()
    {
    	return switchLeft.get();
    }
    
    public StackerPosition getPosition ()
    {
    	/*
    	 * TODO: this method should return a position based on the stacker IR
    	 */
    	if (solenoidUpper.get() == DoubleSolenoid.Value.kForward &&
    			solenoidBottom.get() == DoubleSolenoid.Value.kForward)
    	{
    		return StackerPosition.Floor;
    	}
    	else if (solenoidUpper.get() == DoubleSolenoid.Value.kReverse &&
    			solenoidBottom.get() == DoubleSolenoid.Value.kReverse)
    	{
    		return StackerPosition.Tote;
    	}
    	else
    	{
    		return StackerPosition.Step;
    	}
    }
    
    public DoubleSolenoid.Value getSolenoidContainer ()
    {
    	return solenoidContainer.get();
    }
    
    public DoubleSolenoid.Value getSolenoidGripper ()
    {
    	return solenoidGripper.get();
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
}

