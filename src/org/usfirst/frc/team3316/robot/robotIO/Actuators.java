/**
 * Le robot actuators
 */
package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.VictorSP;

public class Actuators 
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	/*
	 * Chassis
	 */
	public VictorSP chassisMotorControllerLeft;
	public VictorSP chassisMotorControllerRight;
	public VictorSP chassisMotorControllerCenter;
	
	/*
	 * Anschluss
	 */
	public VictorSP anschlussMotorController;
	
	public Actuators ()
	{
		try 
		{
			/*
			 * Chassis
			 */
			chassisMotorControllerLeft = new VictorSP ((int) config.get("CHASSIS_MOTOR_CONTROLLER_LEFT"));
			chassisMotorControllerRight = new VictorSP ((int) config.get("CHASSIS_MOTOR_CONTROLLER_RIGHT"));
			chassisMotorControllerCenter = new VictorSP((int) config.get("CHASSIS_MOTOR_CONTROLLER_CENTER"));
			
			/*
			 * Anschluss
			 */
			anschlussMotorController = new VictorSP ((int) config.get("ANSCHLUSS_MOTOR_CONTROLLER"));
		}
		catch (ConfigException e) 
    	{
			logger.severe(e);
		}
	}
}
