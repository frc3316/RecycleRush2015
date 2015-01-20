package org.usfirst.frc.team3316.robot.config;

import java.util.Hashtable;

public class Config 
{
	private static Hashtable<String, Object> variables;
	private static Hashtable<String, Object> constants;
	
	public void add (String key, Object value)
	{
		addToVariables(key, value);
	}
	
	public Object getV (String key)
	{
		return variables.get(key);
	}
	
	public Object getC (String key)
	{
		return constants.get(key);
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
	
	static 
	{
		variables = new Hashtable<String, Object>();
		constants = new Hashtable<String, Object>();

	}
}
