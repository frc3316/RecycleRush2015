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
	
	public DoubleSolenoid stackerStepSolenoidLeft;
	public DoubleSolenoid stackerStepSolenoidRight;
	public DoubleSolenoid stackerToteSolenoidLeft;
	public DoubleSolenoid stackerToteSolenoidRight;
	public DoubleSolenoid stackerContainerSolenoid;
	
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
			
			stackerStepSolenoidLeft = new DoubleSolenoid((int) config.get("STACKER_STEP_SOLENOID_LEFT_FORWARD"), 
														 (int) config.get("STACKER_STEP_SOLENOID_LEFT_REVERSE"));
			
			stackerStepSolenoidRight = new DoubleSolenoid((int) config.get("STACKER_STEP_SOLENOID_RIGHT_FORWARD"), 
					 									  (int) config.get("STACKER_STEP_SOLENOID_RIGHT_REVERSE"));
			
			stackerToteSolenoidLeft = new DoubleSolenoid((int) config.get("STACKER_TOTE_SOLENOID_LEFT_FORWARD"), 
														 (int) config.get("STACKER_TOTE_SOLENOID_LEFT_REVERSE"));
			
			stackerStepSolenoidRight = new DoubleSolenoid((int) config.get("STACKER_TOTE_SOLENOID_RIGHT_FORWARD"), 
														  (int) config.get("STACKER_TOTE_SOLENOID_RIGHT_REVERSE"));
			
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
