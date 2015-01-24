/**
 * Anschluss subsystem
 */
package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Anschluss extends Subsystem {
    
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	private VictorSP anschluss;
	
	DigitalInput hallEffectClosed;
	DigitalInput hallEffectOpened;
	
	private double anschlussMotorMinSpeed;
	private double anschlussMotorMaxSpeed;
	
	public Anschluss ()	{
		anschluss = Robot.actuators.anschluss;
		hallEffectClosed = Robot.sensors.anschlussDigitalInputClosed;
		hallEffectOpened = Robot.sensors.anschlussDigitalInputOpened;
		configUpdate();
	}
	
	public void configUpdate() {
		 try {
			anschlussMotorMinSpeed = (double) config.get("anschlussMotorMinSpeed");
			anschlussMotorMaxSpeed = (double) config.get("anschlussMotorMaxSpeed");
		} catch (ConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.severe(e.getMessage());
		}
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public boolean set(double motorSpeed) {
    	if(Robot.anschluss.isOpened())
    		config.add("motorMaxSpeed", 0);
    	if(Robot.anschluss.isClosed())
    		config.add("motorMinSpeed", 0);
    	
    	motorSpeed = Math.min(Math.max(anschlussMotorMinSpeed, motorSpeed), anschlussMotorMaxSpeed);
    	anschluss.set(motorSpeed);
    	
    	return true;
	}
    
    public boolean isClosed() {
    	return hallEffectClosed.get();
    }
    
    public boolean isOpened() {
    	return hallEffectOpened.get();
    }
    
}
