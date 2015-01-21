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
	/*
	 * config and logger
	 */
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	/*
	 * General
	 */
	
	/*
	 * Chassis
	 */
	VictorSP chassisLeft;
	VictorSP chassisRight;
	VictorSP chassisCenter;
	/*
	 * RollerGripper
	 */
	
	/*
	 * Stacker
	 */
	
	/*
	 * Anschluss
	 */
	
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
		}
		catch (ConfigException e) 
    	{
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}
}
