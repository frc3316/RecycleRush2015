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
	public VictorSP chassisLeft;
	public VictorSP chassisRight;
	public VictorSP chassisCenter;
	
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
			chassisLeft = new VictorSP ((int) config.get("chassisLeft"));
			chassisRight = new VictorSP ((int) config.get("chassisRight"));
			chassisCenter = new VictorSP((int) config.get("chassisCenter"));
			
			/*
			 * Anschluss
			 */
			//CR: Use more explicit names. the config name should reflect EXACTLY what this indicates.
			//    i.e: ANSCHLUSS_MOTOR_PWM_PORT
			anschluss = new VictorSP ((int) config.get("anschluss"));
		}
		catch (ConfigException e) 
    	{
			e.printStackTrace();
			logger.severe(e.getMessage());
		}
	}
}
