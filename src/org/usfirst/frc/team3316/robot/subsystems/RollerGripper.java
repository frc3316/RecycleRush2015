package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RollerGripper extends Subsystem 
{	
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	private VictorSP gripperLeft, gripperRight;
	
	private AnalogInput IRLeft, IRRight;
	
	private double leftScale, rightScale;
	
	private double angleScale, angleOffset;
	
    public RollerGripper () 
    {
    	gripperLeft = Robot.actuators.rollerGripperMotorControllerLeft;
    	gripperRight = Robot.actuators.rollerGripperMotorControllerRight;
    }

    public void initDefaultCommand() {}
    
    public boolean set(double speedLeft, double speedRight) 
    {
    	updateScales();
     	gripperLeft.set(speedLeft*leftScale);
    	gripperRight.set(speedRight*rightScale);
    	return true;
    }
    
    //TODO: Figure out the calculation for converting from distance to angle
    public double getLeftAngle ()
    { 
    	updateAngleVariables();
    	return (angleScale / (IRLeft.getVoltage())) + angleOffset; //this calculation is wrong
    }
    
    public double getRightAngle ()
    {
    	updateAngleVariables();
    	return (angleScale / (IRRight.getVoltage())) + angleOffset; //this calculation is wrong
    }
    
    private void updateScales ()
    {
    	try
    	{
    		leftScale = (double)config.get("rollerGripper_LeftScale");
    		rightScale = (double)config.get("rollerGripper_RightScale");
    	}
    	catch (ConfigException e)
    	{
    		logger.severe(e);
    	}
    }
    
    private void updateAngleVariables ()
    {
    	try
    	{
    		angleScale = (double)config.get("rollerGripper_AngleScale");
    		angleOffset = (double)config.get("rollerGripper_AngleOffset");
    	}
    	catch (ConfigException e)
    	{
    		logger.severe(e);
    	}
    }
    
    private void printTheTruth()
    {
    	System.out.println("Vita is the Melech!!");
    }
}

