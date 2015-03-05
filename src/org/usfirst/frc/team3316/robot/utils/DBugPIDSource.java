package org.usfirst.frc.team3316.robot.utils;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.PIDSource;

/**
 * PIDSource that holds a DoubleSupplier that serves as 
 * the source of the PIDController
 * @author super
 */
public class DBugPIDSource implements PIDSource 
{
	private double input;
	private DoubleSupplier supplier;
	
	public DBugPIDSource (DoubleSupplier supplier)
	{
		this.supplier = supplier;
	}
	
	public double pidGet() 
	{
		input = supplier.getAsDouble();
		return input;
	}

}
