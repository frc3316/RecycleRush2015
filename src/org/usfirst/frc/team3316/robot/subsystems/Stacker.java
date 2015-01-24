package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Stacker extends Subsystem 
{	
    Config config = Robot.config;
    DBugLogger logger = Robot.logger;
    
    private DoubleSolenoid stepSolenoidLeft, stepSolenoidRight, toteSolenoidLeft, toteSolenoidRight;
    private DoubleSolenoid containerSolenoid;
    
    private AnalogInput heightIR; //infrared
    
    private double heightScale, heightOffset;

    public Stacker () 
    {
    	stepSolenoidLeft = Robot.actuators.stackerStepSolenoidLeft;
    	stepSolenoidRight = Robot.actuators.stackerStepSolenoidRight;
    	
    	toteSolenoidLeft = Robot.actuators.stackerToteSolenoidLeft;
    	toteSolenoidRight = Robot.actuators.stackerToteSolenoidRight;
    	
    	containerSolenoid = Robot.actuators.stackerContainerSolenoid;
    	
    	heightIR = Robot.sensors.stackerHeightIR;
    }
    
    public void initDefaultCommand() {}
    
    public boolean openStepSolenoids ()
    {
    	stepSolenoidLeft.set(DoubleSolenoid.Value.kForward);
    	stepSolenoidRight.set(DoubleSolenoid.Value.kForward);
    	return true;
    }
    
    public boolean closeStepSolenoids ()
    {
    	stepSolenoidLeft.set(DoubleSolenoid.Value.kReverse);
    	stepSolenoidRight.set(DoubleSolenoid.Value.kReverse);
    	return true;
    }
    
    public boolean openToteSolenoids ()
    {
    	toteSolenoidLeft.set(DoubleSolenoid.Value.kForward);
    	toteSolenoidRight.set(DoubleSolenoid.Value.kForward);
    	return true;
    }
    
    public boolean closeToteSolenoids ()
    {
    	toteSolenoidLeft.set(DoubleSolenoid.Value.kReverse);
    	toteSolenoidRight.set(DoubleSolenoid.Value.kReverse);
    	return true;
    }
    
    public boolean openContainerSolenoid ()
    {
    	containerSolenoid.set(DoubleSolenoid.Value.kForward);
    	return true;
    }
    
    public boolean closeContainerSolenoid ()
    {
    	containerSolenoid.set(DoubleSolenoid.Value.kReverse);
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
}

