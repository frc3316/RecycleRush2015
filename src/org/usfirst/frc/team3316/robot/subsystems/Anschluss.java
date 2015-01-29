/**
 * Anschluss subsystem
 */
package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Anschluss extends Subsystem 
{    
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	private VictorSP anschluss;
	
	DigitalInput hallEffectClosed;
	DigitalInput hallEffectOpened;
	
	public Anschluss ()	
	{
		anschluss = Robot.actuators.anschlussMotorController;
		hallEffectClosed = Robot.sensors.anschlussDigitalInputClosed;
		hallEffectOpened = Robot.sensors.anschlussDigitalInputOpened;
	}

    public void initDefaultCommand() {}
    
    public boolean set (double motorSpeed) 
    {
    	//assuming motorSpeed > 0 is for opening and motorSpeed < 0 is for closing
    	if ((motorSpeed > 0 && this.isOpened()) || (motorSpeed < 0 && this.isClosed()))
    	{
    		anschluss.set(0);
    		String state = "";
    		if (this.isOpened())
    		{
    			state = "opened";
    		}
    		else if (this.isClosed())
    		{
    			state = "closed";
    		}
    		logger.info("Tried to set " + motorSpeed + "to Anschluss motor controller when anschluss is " + state);
    		return false;
    	}
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