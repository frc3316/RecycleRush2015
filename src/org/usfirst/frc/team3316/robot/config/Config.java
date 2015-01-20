package org.usfirst.frc.team3316.robot.config;

import java.util.Hashtable;

public class Config 
{
	private class ConfigException extends Exception
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
	
	private Hashtable<String, Object> variables;
	private Hashtable<String, Object> constants;
	
	public void add (String key, Object value)
	{
		addToVariables(key, value);
	}
	
	public Object get (String key) throws Exception
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
		}
		else
		{
			constants.put(key, value);
		}
	}
	
	private void addToVariables (String key, Object value)
	{
		if (variables.containsKey(key))
		{
			variables.replace(key, value);
		}
		else
		{
			variables.put(key, value);
		}
	}
	
	private void initConfig ()
	{
		
	}
	
	private Config ()
	{
		variables = new Hashtable<String, Object>();
		constants = new Hashtable<String, Object>();
		initConfig();
	}
}
