package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.subsystems.Chassis.NavigationIntegrator;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 */
public class RobotOrientedNavigation extends FieldOrientedDrive 
{
	private class PIDSourceX implements PIDSource
	{
		public double pidGet() 
		{
			return integrator.getX();
		}
	}
	
	private class PIDSourceY implements PIDSource
	{
		public double pidGet() 
		{
			return integrator.getY();
		}
	}
	
	private class PIDOutputX implements PIDOutput
	{
		public void pidWrite (double output) 
		{
			
		}
	}
	
	private NavigationIntegrator integrator;
	
	private double dXSetpoint, dYSetpoint, dHeadingSetpoint;
	
	private PIDController pidControllerX, pidControllerY, pidControllerHeading;
	
	public RobotOrientedNavigation (double dXSetpoint , double dYSetpoint, double dHeadingSetpoint)
	{
		this.dXSetpoint = dXSetpoint;
		this.dYSetpoint = dYSetpoint;
		this.dHeadingSetpoint = dHeadingSetpoint;
	}
	
	protected void initialize ()
	{
		super.initialize();
		integrator = new NavigationIntegrator();
		Robot.chassis.addNavigationIntegrator(integrator);
	}
	
	protected void end ()
	{
		super.end();
		Robot.chassis.removeNavigationIntegrator(integrator);
	}
	
	
}
