package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RollerGripper extends Subsystem 
{	
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	private VictorSP gripperLeft,
			 		 gripperRight;
	
	private AnalogInput IRLeft, IRRight;
	
    public RollerGripper () 
    {
    	gripperLeft = Robot.actuators.rollerGripperMotorControllerLeft;
    	gripperRight = Robot.actuators.rollerGripperMotorControllerRight;
    }

    public void initDefaultCommand() {}
    
    public boolean set(double leftSpeed, double rightSpeed) 
    {
    	gripperLeft.set(leftSpeed);
    	gripperRight.set(rightSpeed);
    	return true;
    }
    
    //TODO: Figure out the calculation for converting from distance to angle
    public double getLeftAngle ()
    { 
    	return 1/(IRLeft.getVoltage());
    }
    
    public double getRightAngle ()
    {
    	return 1/(IRRight.getVoltage());
    }
}

