/**
 * Le robot actuators
 */
package org.usfirst.frc.team3316.robot.robotIO;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class Actuators 
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	/*
	 * Chassis
	 */
	public VictorSP chassisLeft;
	public VictorSP chassisRight;
	public VictorSP chassisCenter;
	
	/*
	 * Stacker
	 */
	
	public DoubleSolenoid stackerSolenoidStepLeft;
	public DoubleSolenoid stackerSolenoidStepRight;
	
	public DoubleSolenoid stackerSolenoidToteLeft;
	public DoubleSolenoid stackerSolenoidToteRight;
	
	public DoubleSolenoid stackerSolenoidContainer;
	
	/*
	 * Anschluss
	 */
	public VictorSP anschluss;
	
	public Actuators ()
	{
		try 
		{
			/*
			 * Chassis
			 */
			chassisLeft = new VictorSP ((int) config.get("CHASSIS_MOTOR_CONTROLLER_LEFT"));
			chassisRight = new VictorSP ((int) config.get("CHASSIS_MOTOR_CONTROLLER_RIGHT"));
			chassisCenter = new VictorSP((int) config.get("CHASSIS_MOTOR_CONTROLLER_CENTER"));
			
			/*
			 * Stacker
			 */
			
			stackerSolenoidStepLeft = new DoubleSolenoid((int) config.get("STACKER_SOLENOID_STEP_LEFT_FORWARD"), 
														 (int) config.get("STACKER_SOLENOID_STEP_LEFT_REVERSE"));
			
			stackerSolenoidStepRight = new DoubleSolenoid((int) config.get("STACKER_SOLENOID_STEP_RIGHT_FORWARD"), 
					 									  (int) config.get("STACKER_SOLENOID_STEP_RIGHT_REVERSE"));
			
			stackerSolenoidToteLeft = new DoubleSolenoid((int) config.get("STACKER_SOLENOID_TOTE_LEFT_FORWARD"), 
														 (int) config.get("STACKER_SOLENOID_TOTE_LEFT_REVERSE"));
			
			stackerSolenoidToteRight = new DoubleSolenoid((int) config.get("STACKER_SOLENOID_TOTE_RIGHT_FORWARD"), 
														  (int) config.get("STACKER_SOLENOID_TOTE_RIGHT_REVERSE"));
			
			stackerSolenoidContainer = new DoubleSolenoid((int) config.get("STACKER_SOLENOID_CONTAINER_FORWARD"),
														  (int) config.get("STACKER_SOLENOID_CONTAINER_REVERSE"));
			
			/*
			 * Anschluss
			 */
			anschluss = new VictorSP ((int) config.get("ANSCHLUSS_MOTOR_CONTROLLER"));
		}
		catch (ConfigException e) 
    	{
			logger.severe(e);
		}
	}
}
