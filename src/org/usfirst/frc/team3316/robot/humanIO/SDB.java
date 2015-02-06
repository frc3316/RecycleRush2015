/**
 * Class for managing the SmartDashboard data
 */
package org.usfirst.frc.team3316.robot.humanIO;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SDB 
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	private Hashtable <String, Class> variablesInSDB;
	
	public SDB ()
	{
		variablesInSDB = new Hashtable <String, Class> ();
		SmartDashboard.putData(new UpdateVariables());
		initSDB();
	}
	
	/**
	 * Adds a certain key in the config to the SmartDashboard
	 * @param key the key required
	 * @return whether the value was put in the SmartDashboard
	 */
	public boolean addToSDB (String key)
	{
		try
		{
			Object value = config.get(key);
			Class type = value.getClass();
			
			boolean constant = Character.isUpperCase(key.codePointAt(0));
			
			if (type == Double.class)
			{
				SmartDashboard.putNumber(key, (double) value);
			}
			else if (type == Integer.class)
			{
				SmartDashboard.putInt(key, (int) value);
			}
			else if (type == Boolean.class)
			{
				SmartDashboard.putBoolean(key, (boolean) value);
			}
			
			if (!constant)
			{
				variablesInSDB.put(key, type);
				logger.info("Added to SDB " + key + " of type " + type + 
						"and allows for its modification");
			}
			else
			{
				logger.info("Added to SDB " + key + " of type " + type + 
						"BUT DOES NOT ALLOW for its modification");
			}
			
			
			return true;
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
		return false;
	}
	
	public Set <Entry <String, Class> > getVariablesInSDB ()
	{
		return variablesInSDB.entrySet();
	}
	
	private void initSDB ()
	{
		addToSDB ("chassis_LeftScale");
		addToSDB ("chassis_RightScale");
		addToSDB ("chassis_CenterScale");
		
		addToSDB ("chassis_TankDrive_InvertX");
		addToSDB ("chassis_TankDrive_InvertY");
		
		addToSDB ("chassis_TankDrive_InvertY");
		addToSDB ("chassis_TankDrive_InvertY");
	}
}