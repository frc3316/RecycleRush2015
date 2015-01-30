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
	
	private static Hashtable<String, Object> variables;
	private static Hashtable<String, Object> constants;
	
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
			logger.info("replaced " + key + " in variables hashtable");
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
		/*
		 * Chassis
		 */
			/*
			 * Constants
			 */
			addToConstants("CHASSIS_MOTOR_CONTROLLER_LEFT", 0);
			addToConstants("CHASSIS_MOTOR_CONTROLLER_RIGHT", 1);
			addToConstants("CHASSIS_MOTOR_CONTROLLER_CENTER", 2);
			/*
			 * Variables
			 */
			
			//Subsystem
			addToVariables("chassis_LeftScale", -1);
			addToVariables("chassis_RightScale", 1);
			addToVariables("chassis_CenterScale", 1);
			
			//TankDrive
			addToVariables("chassis_TankDrive_InvertX", false);
			addToVariables("chassis_TankDrive_InvertY", true);
			
			//RobotOrientedDrive
			addToVariables("chassis_RobotOrientedDrive_TurnScale", 1);
			
			//RobotOrientedNavigation
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerX_KP_0", 0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerX_KI_0", 0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerX_KD_0", 0);
			
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerY_KP_0", 0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerY_KI_0", 0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerY_KD_0", 0);
			
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerHeading_KP_0", 0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerHeading_KI_0", 0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerHeading_KD_0", 0);
			
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerX_AbsoluteTolerance", 0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerY_AbsoluteTolerance", 0);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerHeading_AbsoluteTolerance", 0);
			
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerX_MinimumOutput", -1);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerX_MaximumOutput", 1);
			
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerY_MinimumOutput", -1);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerY_MaximumOutput", 1);
			
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerHeading_MinimumOutput", -1);
			addToVariables("chassis_RobotOrientedNavigation_PIDControllerHeading_MaximumOutput", 1);
		/*
		 * Anschluss
		 */
			/*
			 * Constants
			 */
			addToConstants("ANSCHLUSS_MOTOR_CONTROLLER", 3);
			addToConstants("ANSCHLUSS_MOTOR_SPEED_CLOSE", -0.5);
			addToConstants("ANSCHLUSS_MOTOR_SPEED_OPEN", 0.5);
			
			addToConstants("ANSCHLUSS_BUTTON_CLOSE", 2);
			addToConstants("ANSCHLUSS_BUTTON_OPEN", 1);
	}

}
