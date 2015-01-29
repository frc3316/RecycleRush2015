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
	public VictorSP chassisMotorControllerLeft;
	public VictorSP chassisMotorControllerRight;
	public VictorSP chassisMotorControllerCenter;
	/*
	 * Roller-Gripper
	 */
	public VictorSP rollerGripperMotorControllerLeft;
	public VictorSP rollerGripperMotorControllerRight;
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
			anschlussMotorController = new VictorSP ((int) config.get("ANSCHLUSS_MOTOR_CONTROLLER"));
			/*
			 * Roller-Gripper
			 */
			rollerGripperMotorControllerLeft = new VictorSP ((int) config.get("ROLLER_GRIPPER_MOTOR_CONTROLLER_LEFT"));
			rollerGripperMotorControllerRight = new VictorSP ((int) config.get("ROLLER_GRIPPER_MOTOR_CONTROLLER_RIGHT"));
		}
		catch (ConfigException e) 
    	{
			logger.severe(e);
		}
	}
}
