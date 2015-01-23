/**
 * Le robot sensors
 */
package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

public class Sensors 
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	/*
	 * Chassis
	 */
	public Encoder chassisEncoderLeft, chassisEncoderRight, chassisEncoderCenter;
	
	/*
	 * Anschluss
	 */
	public DigitalInput anschlussDigitalInputClosed;
	public DigitalInput anschlussDigitalInputOpened;
	public Sensors ()
	{
		try 
		{
			/*
			 * Chassis
			 */
			chassisEncoderLeft = new Encoder((int)config.get("chassisEncoderLeftA"), 
											 (int)config.get("chassisEncoderLeftB"), 
											 false, 
											 CounterBase.EncodingType.k4X);
			
			chassisEncoderRight = new Encoder((int)config.get("chassisEncoderRightA"), 
											 (int)config.get("chassisEncoderRightB"), 
											 false, 
											 CounterBase.EncodingType.k4X);
			
			chassisEncoderCenter = new Encoder((int)config.get("chassisEncoderCenterA"), 
					 						  (int)config.get("chassisEncoderCenterB"), 
					 						  false, 
					 						  CounterBase.EncodingType.k4X);
			
			anschlussDigitalInputClosed = new DigitalInput(0); //check the channel and update it accordingly
		}
		catch (ConfigException e)
		{
			logger.severe(e.getMessage());
		}
	}
}
