package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RollerGripper extends Subsystem {
	
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	private VictorSP gripperLeft,
			 		 gripperRight;
	
	private AnalogPotentiometer potLeft,
								potRight;
	
    public RollerGripper () {
    	gripperLeft = Robot.actuators.rollerGripperLeft;
    	gripperRight = Robot.actuators.rollerGripperRight;
    	
    	potLeft = Robot.sensors.rollerGripperPotLeft;
    	potRight = Robot.sensors.rollerGripperPotRight;   	
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public boolean set(double leftSpeed, double rightSpeed) {
    	gripperLeft.set(leftSpeed);
    	gripperRight.set(rightSpeed);
    	return true;
    }
    
    public double getPotLeftPosition () {
    	return potLeft.get();
    }
    
    public double getPotRighttPosition () {
    	return potRight.get();
    }
}

