/**
 * Class for managing the Smart Dashboard data
 */
package org.usfirst.frc.team3316.robot.humanIO;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SDB 
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	private Hashtable <String, Class> variables;
	
	public SDB ()
	{
		variables = new Hashtable <String, Class> ();
		
		SmartDashboard.putData(new UpdateVariables());
	}
	
	public void addVariable (String key)
	{
		Class type = Robot.config.addToSDB(key);
		if (type != null)
		{
			variables.put(key, type);
			logger.info("Added to SDB " + key + " of type " + type);
		}
		else
		{
			logger.severe("Key " + key + "was not added to SDB because it was not found in config");
		}
	}
	
	public Set <Entry <String, Class> > getVariables ()
	{
		return variables.entrySet();
	}
}