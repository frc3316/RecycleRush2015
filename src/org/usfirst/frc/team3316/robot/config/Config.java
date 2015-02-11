package org.usfirst.frc.team3316.robot.config;

import java.util.Hashtable;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

public class Config 
{
	DBugLogger logger = Robot.logger;
	public class ConfigException extends Exception
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ConfigException (String key)
		{
			super(key);
		}
	}
	
	private static Hashtable <String, Object> variables;
	private static Hashtable <String, Object> constants;
	
	public Config ()
	{
		if (variables == null || constants == null)
		{
			variables = new Hashtable<String, Object>();
			constants = new Hashtable<String, Object>();
			initConfig();
		}
	}
	
	public void add (String key, Object value)
	{
		addToVariables(key, value);
	}
	
	/**
	 * Returns the value attached to a requested key
	 * @param key the key to look for
	 * @return returns the corresponding value
	 * @throws ConfigException if the key does not exist
	 */
	public Object get (String key) throws ConfigException
	{
		if (constants.containsKey(key))
		{
			return constants.get(key);
		}
		else if (variables.containsKey(key))
		{
			return variables.get(key);
		}
		else
		{
			throw new ConfigException(key);
		}
	}
	
	private void addToConstants (String key, Object value)
	{
		if (constants.containsKey(key))
		{
			constants.replace(key, value);
			logger.info("replaced " + key + " in constants hashtable");
		}
		else
		{
			constants.put(key, value);
			logger.info("added " + key + " in constants hashtable");
		}
	}
	
	private void addToVariables (String key, Object value)
	{
		if (variables.containsKey(key))
		{
			variables.replace(key, value);
			logger.info("replaced " + key + " in variables hashtable");
		}
		else
		{
			variables.put(key, value);
			logger.info("added " + key + " in variables hashtable");
		}
	}
	
	private void initConfig ()
	{
		/*
		 * Human IO
		 */
			/*
			 * Constants
			 */
			addToConstants("JOYSTICK_LEFT", 0);
			addToConstants("JOYSTICK_RIGHT", 1);
			addToConstants("JOYSTICK_OPERATOR", 2);
			
			//subsystem
				/*
				 * Stacker
				 */
				addToConstants("MOVE_STACKER_TO_FLOOR_BUTTON", 1);
				addToConstants("MOVE_STACKER_TO_STEP_BUTTON", 2);
				addToConstants("MOVE_STACKER_TO_TOTE_BUTTON", 3);
				
				addToConstants("HOLD_CONTAINER_BUTTON", 4);
				addToConstants("RELEASE_CONTAINER_BUTTON", 5);
				
				addToConstants("OPEN_GRIPPER_BUTTON", 6);
				addToConstants("CLOSE_GRIPPER_BUTTON", 7);
			
			
		/*
		 * Chassis
		 */
			/*
			 * Constants
			 */
			addToConstants("CHASSIS_MOTOR_CONTROLLER_LEFT_1", 6);
			addToConstants("CHASSIS_MOTOR_CONTROLLER_LEFT_2", 7);
			
			addToConstants("CHASSIS_MOTOR_CONTROLLER_RIGHT_1", 1);
			addToConstants("CHASSIS_MOTOR_CONTROLLER_RIGHT_2", 2);
			
			addToConstants("CHASSIS_MOTOR_CONTROLLER_CENTER_1", 0);
			addToConstants("CHASSIS_MOTOR_CONTROLLER_CENTER_2", 5);
			
			addToConstants("CHASSIS_ENCODER_LEFT_A", 4);
			addToConstants("CHASSIS_ENCODER_LEFT_B", 5);
			
			addToConstants("CHASSIS_ENCODER_RIGHT_A", 2);
			addToConstants("CHASSIS_ENCODER_RIGHT_B", 3);
			
			addToConstants("CHASSIS_ENCODER_CENTER_A", 0);
			addToConstants("CHASSIS_ENCODER_CENTER_B", 1);
			
			addToConstants("CHASSIS_ENCODER_LEFT_DISTANCE_PER_PULSE", -0.07363107781851077902646820429561);
			addToConstants("CHASSIS_ENCODER_RIGHT_DISTANCE_PER_PULSE", 0.07363107781851077902646820429561);
			addToConstants("CHASSIS_ENCODER_CENTER_DISTANCE_PER_PULSE", 0.02454369260617025967548940143187);
			
			addToConstants("CHASSIS_WIDTH", 0.5322); //in meters
			/*
			 * Variables
			 */

			 //Subsystem
			addToVariables("chassis_LeftScale", 1);
			addToVariables("chassis_RightScale", -1);
			addToVariables("chassis_CenterScale", 1);
			
			addToVariables("chassis_HeadingToSet", 0.0); // This is the heading that the SetHeadingSDB command sets to
														 // It is configurable in the SDB (it should appear in initSDB())
			//TankDrive
			addToVariables("chassis_TankDrive_InvertX", false);
			addToVariables("chassis_TankDrive_InvertY", true);
			
			addToVariables("chassis_TankDrive_LowPass", 0.0);
			//RobotOrientedDrive
			addToVariables("chassis_RobotOrientedDrive_TurnScale", 1);
			
			//RobotOrientedNavigation
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerX_KP", 0.0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerX_KI", 0.0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerX_KD", 0.0);
			
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerY_KP", 0.0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerY_KI", 0.0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerY_KD", 0.0);
			
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerHeading_KP", 0.0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerHeading_KI", 0.0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerHeading_KD", 0.0);
			
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerX_AbsoluteTolerance", 0.0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerY_AbsoluteTolerance", 0.0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerHeading_AbsoluteTolerance", 0.0);
			
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerX_MinimumOutput", -1.0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerX_MaximumOutput", 1.0);
			
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerY_MinimumOutput", -1.0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerY_MaximumOutput", 1.0);
			
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerHeading_MinimumOutput", -1.0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerHeading_MaximumOutput", 1.0);
		/*
		 * Anschluss
		 */
			/*
			 * Constants
			 */
			addToConstants("ANSCHLUSS_MOTOR_CONTROLLER", 4);
			
			addToConstants("ANSCHLUSS_BUTTON_CLOSE", 1);
			addToConstants("ANSCHLUSS_BUTTON_OPEN", 0);
			
			/*
			 * Variables
			 */
			addToVariables("anschluss_CloseAnschluss_MotorSpeed", -0.5);
			addToVariables("anschluss_OpenAnschluss_MotorSpeed", 0.5);
		/*
		 * Roller Gripper
		 */
			
			/*
			 * Constants
			 */
			addToConstants("ROLLER_GRIPPER_MOTOR_CONTROLLER_LEFT", 3);
			addToConstants("ROLLER_GRIPPER_MOTOR_CONTROLLER_RIGHT", 8);
			
				//Subsystem
					addToConstants("ROLLER_GRIPPER_IR_LEFT", 0);
					addToConstants("ROLLER_GRIPPER_IR_RIGHT", 1);
			/*
			 * Variables
			 */
				//Subsystem
					addToVariables("rollerGripper_LeftScale", 0.7);
					addToVariables("rollerGripper_RightScale", -0.7);
				//RollIn
					addToVariables("rollerGripper_RollIn_SpeedLeft", 1);
					addToVariables("rollerGripper_RollIn_SpeedRight", 1);
			
				//RollOut
					addToVariables("rollerGripper_RollOut_SpeedLeft", -1);
					addToVariables("rollerGripper_RollOut_SpeedRight", -1);
			
				//RollTurnClockwise
					addToVariables("rollerGripper_RollTurnClockwise_SpeedLeft", 1);
					addToVariables("rollerGripper_RollTurnClockwise_SpeedRight", -1);
			
				//RollTurnCounterClockwise
					addToVariables("rollerGripper_RollTurnCounterClockwise_SpeedLeft", 1);
					addToVariables("rollerGripper_RollTurnCounterClockwise_SpeedRight", -1);
					
		/*
		 * Stacker
		 */
			/*
			 * Constants
			 */
				//Subsystem
					addToConstants("STACKER_SOLENOID_UPPER_FORWARD", 6);
					addToConstants("STACKER_SOLENOID_UPPER_REVERSE", 7);
					
					addToConstants("STACKER_SOLENOID_BOTTOM_FORWARD", 4);
					addToConstants("STACKER_SOLENOID_BOTTOM_REVERSE", 5);
					
					addToConstants("STACKER_SOLENOID_CONTAINER_FORWARD", 3);
					addToConstants("STACKER_SOLENOID_CONTAINER_REVERSE", 2);
					
					addToConstants("STACKER_SOLENOID_GRIPPER_FORWARD", 0);
					addToConstants("STACKER_SOLENOID_GRIPPER_REVERSE", 1);
					
					addToConstants("STACKER_IR", 0);
					
					addToConstants("STACKER_SWITCH_TOTE", 8);
					addToConstants("STACKER_SWITCH_GAMEPIECE", 9);
					
			/*
			 * Variables
			 */
				//MoveStacker
					//MoveStackerToFloor
					addToVariables("stacker_MoveStackerToFloor_HeightMax", 0.4);
					addToVariables("stacker_MoveStackerToFloor_HeightMin", 0.42);
					//MoveStackerToStep
					addToVariables("stacker_MoveStackerToStep_HeightMax", 1.05);
					addToVariables("stacker_MoveStackerToStep_HeightMin", 0.95);
					//MoveStackerToTote
					addToVariables("stacker_MoveStackerToTote_HeightMax", 1.62);
					addToVariables("stacker_MoveStackerToTote_HeightMin", 1.45);
	}
}
