package org.usfirst.frc.team3316.robot.utils;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * PIDOutput that holds the output as a private variable 
 * and can be accessed through pidGet()
 * @author super
 */
public class DBugPIDOutput implements PIDOutput 
{
	private double output;
	
	/**
	 * THIS METHOD SHOULD ONLY BE CALLED THROUGH A PIDCONTROLLER
	 * DO NOT MESS UP WITH THE OUTPUT
	 * I'M SERIOUS
	 * THIS METHOD SHOULDN'T HAVE BEEN PUBLIC
	 */
	public void pidWrite(double output) 
	{
		this.output = output;
	}
	
	public double pidGet()
	{
		return output;
	}
}
