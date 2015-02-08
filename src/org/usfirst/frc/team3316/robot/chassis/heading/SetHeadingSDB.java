package org.usfirst.frc.team3316.robot.chassis.heading;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
/**
 * Sets the robot heading to the angle in the chassis_HeadingToSet in the SmartDashboard
 */
public class SetHeadingSDB extends SetHeading 
{
	Config config = Robot.config;
	
	protected void initialize ()
	{
		try
		{
			headingToSet = (double) config.get("chassis_HeadingToSet");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}