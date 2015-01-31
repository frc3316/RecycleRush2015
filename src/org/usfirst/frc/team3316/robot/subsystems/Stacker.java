package org.usfirst.frc.team3316.robot.subsystems;

import java.util.Stack;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.stacker.GamePiece;
import org.usfirst.frc.team3316.robot.stacker.GamePieceType;

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
    
    private DoubleSolenoid solenoidStepLeft, 
    					   solenoidStepRight, 
    					   solenoidToteLeft, 
    					   solenoidToteRight; //lifting solenoids
    
    private DoubleSolenoid solenoidContainer; //the solenoid that holds the containers
    
    private AnalogInput heightIR; //infrared
    private DigitalInput switchTote, switchContainer; //the switches that signify if there's a tote or a container in the stacker
    
    private double heightScale, heightOffset; //variables to make the output of getHeight have a connection to the real world
    
    private Stack <GamePiece> stack; 

    public Stacker () 
    {
    	solenoidStepLeft = Robot.actuators.stackerSolenoidStepLeft;
    	solenoidStepRight = Robot.actuators.stackerSolenoidStepRight;
    	
    	solenoidToteLeft = Robot.actuators.stackerSolenoidToteLeft;
    	solenoidToteRight = Robot.actuators.stackerSolenoidToteRight;
    	
    	solenoidContainer = Robot.actuators.stackerSolenoidContainer;
    	
    	heightIR = Robot.sensors.stackerHeightIR;
    	
    	switchTote = Robot.sensors.stackerSwitchTote;
    	switchContainer = Robot.sensors.stackerSwitchContainer;
    	
    	stack = new Stack <GamePiece>();
    }
    
	//CR: Sort the functions according to interface with the Stack or interface with actuators
    public boolean isFull() 
    {
		//CR: I want to see more logical ability behind 'full stack':
		//     * If we have X number of totes and a container
		//     * If we have Y number of totes but no container
		//     * If we have Z number of yellow totes
		//    and X, Y and Z should be configurable
    	int totesCount = 0;
    	for (GamePiece gp : stack)
    	{
    		if (gp.getType() == GamePieceType.GreyTote || gp.getType() == GamePieceType.YellowTote)
    		{
    			totesCount++;
    		}
    	}
    	return totesCount >= 6;
    }
    
    public Stack getStack() {
    	return stack;
    }
    
    public void pushToStack(GamePiece g) {
    	stack.push(g);
    }
    
    public void initDefaultCommand() {}
    
	//CR: Since the subsystem should protect the machine - sit with Yiftach and make sure you
	//    understand 100% whats possible operations the machine can do.
	//    i.e: stacker shouldn't be able to go down to floor with the container solenoids
	//         pushing on the game-piece...
    public boolean openSolenoidStep ()
    {
    	solenoidStepLeft.set(DoubleSolenoid.Value.kForward);
    	solenoidStepRight.set(DoubleSolenoid.Value.kForward);
    	return true;
    }
    
    public boolean closeSolenoidStep ()
    {
    	solenoidStepLeft.set(DoubleSolenoid.Value.kReverse);
    	solenoidStepRight.set(DoubleSolenoid.Value.kReverse);
    	return true;
    }
    
    public boolean openSolenoidTote ()
    { 
    	solenoidToteLeft.set(DoubleSolenoid.Value.kForward);
    	solenoidToteRight.set(DoubleSolenoid.Value.kForward);
    	return true;
    }
    
    public boolean closeSolenoidTote ()
    {
    	solenoidToteLeft.set(DoubleSolenoid.Value.kReverse);
    	solenoidToteRight.set(DoubleSolenoid.Value.kReverse);
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
    
    public double getHeight ()
    {
    	updateDistanceVariables();
    	return ((heightScale/(heightIR.getVoltage()))+heightOffset);
    }
    
    private void updateDistanceVariables ()
    {
    	try 
    	{
			heightScale = (double) config.get("stackerHeightScale");
			heightOffset = (double) config.get("stackerHeightOffset");
		} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
    }
    
    public void addToStackContainer ()
    {
    	stack.add(new GamePiece(GamePieceType.Container));
    }
    
    public void addToStackGreyTote ()
    {
    	stack.add(new GamePiece(GamePieceType.GreyTote));
    }
    
    public void addToStackYellowTote ()
    {
    	stack.add(new GamePiece(GamePieceType.YellowTote));
    }
    
	//CR: The switches are configured such that when both are pressed it's a tote and when
	//    and when only one is pressed - it's a container.
    public boolean getSwitchTote ()
    {
    	return switchTote.get();
    }
    
    public boolean getSwitchContainer ()
    {
    	return switchContainer.get();
    }
}

