package org.usfirst.frc.team3316.robot.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

public class Config 
{
	DBugLogger logger = Robot.logger;
	
	public boolean RobotA; //true if it's robot A, false if it's robot B
	
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
			variables = new Hashtable <String, Object>();
			constants = new Hashtable <String, Object>();
			
			determineRobotA();
			deserializationInit();
		}
	}
	
	/*
	 * Reads the file on the roborio that says whether it is robot A or B
	 * Default is Robot B
	 */
	private void determineRobotA ()
	{
		String line;
		BufferedReader in;
		
		try 
		{
			in = new BufferedReader(new FileReader("/home/lvuser/RobotName/robot.txt"));
			line = in.readLine();
			
			if (line.equals("Robot A"))
			{
				RobotA = true;
			}
			else
			{
				RobotA = false;
			}
		} 
		catch (FileNotFoundException e) 
		{
			logger.severe(e);
		} 
		catch (IOException e) 
		{
			logger.severe(e);
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
	
	@SuppressWarnings({ "unchecked", "resource" })
	private void deserializationInit () 
	{
		try 
		{
			FileInputStream in = new FileInputStream("/home/lvuser/config/configFile.ser");
			ObjectInputStream input = new ObjectInputStream(in);
			
			constants = (Hashtable<String, Object>) input.readObject();
			variables = (Hashtable<String, Object>) input.readObject();
			
			Set <Entry <String, Object> > set;
			
			set = constants.entrySet();
			logger.info(" Logging Constants");
			for (Entry <String, Object> entry : set)
			{
				logger.info(" Key = " + entry.getKey() + " Value = " + entry.getValue());
			}
			
			set = variables.entrySet();
			logger.info(" Logging Variables");
			for (Entry <String, Object> entry : set)
			{
				logger.info(" Key = " + entry.getKey() + " Value = " + entry.getValue());
			}
		} 
		catch (FileNotFoundException e) 
		{
			logger.severe(e);
		}
		catch (IOException e)
		{
			logger.severe(e);
		} 
		catch (ClassNotFoundException e) 
		{
			logger.severe(e);
		}
	}
}
