/**
 * Le robot sensors
 */
package org.usfirst.frc.team3316.robot.robotIO;

import com.kauailabs.nav6.frc.IMUAdvanced;
import com.ni.vision.NIVision;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;

public class Sensors 
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	/*
	 * Camera
	 */
	private boolean cameraFound;
	private int session;
	
	/*
	 * Chassis
	 */
	public Encoder chassisEncoderLeft, chassisEncoderRight, chassisEncoderCenter;
	public IMUAdvanced navx;
	SerialPort serial_port;
	
	/*
	 * Stacker
	 */	
	public AnalogInput stackerHeightIR;
	public DigitalInput switchRatchetRight,
						switchRatchetLeft;
	
	/*
	 * Anschluss
	 */
	public DigitalInput anschlussDigitalInputClosed;
	public DigitalInput anschlussDigitalInputOpened;
	
	/*
	 * RollerGripper
	 */
	public AnalogInput rollerGripperGPIR;
	public DigitalInput rollerGripperSwitchGP;
	
	
	public Sensors ()
	{	
		
		try
		{
			session = NIVision.IMAQdxOpenCamera("cam0",
	                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	        logger.info("Session is " + session);
	        cameraFound = true;
			NIVision.IMAQdxConfigureGrab(session);
			NIVision.IMAQdxStartAcquisition(session);
		}
		catch (Exception e)
		{
			logger.severe("Camera not found!");
			logger.severe(e);
			session = -1;
			cameraFound = false;
		}
		
		try 
		{
			/*
			 * Chassis
			 */
			chassisEncoderLeft = new Encoder((int)config.get("CHASSIS_ENCODER_LEFT_A"), 
											 (int)config.get("CHASSIS_ENCODER_LEFT_B"), 
											 (boolean) config.get("CHASSIS_ENCODER_LEFT_REVERSE_DIRECTION"), 
											 CounterBase.EncodingType.k4X);
			
			chassisEncoderRight = new Encoder((int)config.get("CHASSIS_ENCODER_RIGHT_A"), 
											 (int)config.get("CHASSIS_ENCODER_RIGHT_B"), 
											 (boolean) config.get("CHASSIS_ENCODER_RIGHT_REVERSE_DIRECTION"), 
											 CounterBase.EncodingType.k4X);
			
			chassisEncoderCenter = new Encoder((int)config.get("CHASSIS_ENCODER_CENTER_A"), 
					 						  (int)config.get("CHASSIS_ENCODER_CENTER_B"), 
					 						 (boolean) config.get("CHASSIS_ENCODER_CENTER_REVERSE_DIRECTION"), 
					 						  CounterBase.EncodingType.k4X);
			
			chassisEncoderLeft.setDistancePerPulse(
					(double) config.get("CHASSIS_ENCODER_LEFT_DISTANCE_PER_PULSE"));
			chassisEncoderRight.setDistancePerPulse(
					(double) config.get("CHASSIS_ENCODER_RIGHT_DISTANCE_PER_PULSE"));
			chassisEncoderCenter.setDistancePerPulse(
					(double) config.get("CHASSIS_ENCODER_CENTER_DISTANCE_PER_PULSE"));

			serial_port = new SerialPort(57600, SerialPort.Port.kMXP);
			navx = new IMUAdvanced(serial_port);
			
			/*
			 * Stacker
			 */
			stackerHeightIR = new AnalogInput((int) config.get("STACKER_IR"));
			
			switchRatchetRight = new DigitalInput((int) config.get("SWITCH_RATCHET_RIGHT"));
			switchRatchetLeft = new DigitalInput((int) config.get("SWITCH_RATCHET_LEFT"));
			
			/*
			 * Anschluss
			 */
			anschlussDigitalInputClosed = new DigitalInput((int) config.get("ANSCHLUSS_DIGITAL_INPUT_CLOSED"));
			anschlussDigitalInputOpened = new DigitalInput((int) config.get("ANSCHLUSS_DIGITAL_INPUT_OPENED"));
			
			/*
			 * RollerGripper
			 */
			rollerGripperGPIR = new AnalogInput(
					(int) config.get("ROLLER_GRIPPER_GAME_PIECE_IR"));
			
			rollerGripperSwitchGP = new DigitalInput(
					(int) config.get("ROLLER_GRIPPER_SWITCH_GAME_PIECE"));
		}
		
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
	
	public boolean isCameraFound ()
	{
		return cameraFound;
	}
	
	public int getCameraSession ()
	{
		return session;
	}
}
