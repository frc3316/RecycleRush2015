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
	}
	
	private void configUpdate() {
		try 
		{
			//CR: These are useless... you get the speed from the command and you just need
			//    to make sure you're not endangering the robot physically.
			anschlussMotorMinSpeed = (double) config.get("anschlussMotorMinSpeed");
			anschlussMotorMaxSpeed = (double) config.get("anschlussMotorMaxSpeed");
		} 
		catch (ConfigException e) {
			logger.severe(e);
		}
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public boolean set(double motorSpeed) {
    	configUpdate();
		//CR: fix this so that it doesn't use the configuration. only changes the current motorSpeed.
    	if(Robot.anschluss.isOpened())
    		config.add("anschlussMotorMaxSpeed", 0);
    	if(Robot.anschluss.isClosed())
    		config.add("anschlussMotorMinSpeed", 0);
    	
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