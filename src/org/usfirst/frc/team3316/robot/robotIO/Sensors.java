/**
 * Le robot sensors
 */
package org.usfirst.frc.team3316.robot.robotIO;

import com.kauailabs.nav6.frc.IMUAdvanced;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;

public class Sensors 
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	/*
	 * Chassis
	 */
	public Encoder chassisEncoderLeft, chassisEncoderRight, chassisEncoderCenter;
	public IMUAdvanced navx;
	SerialPort serial_port;
	
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
			
			serial_port = new SerialPort(57600,SerialPort.Port.kMXP);
			navx = new IMUAdvanced(serial_port);
		}
		catch (ConfigException e)
		{
			logger.info(e.getMessage());
		}
	}
}
