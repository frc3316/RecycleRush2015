package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.subsystems.Chassis.NavigationIntegrator;

public class FieldOrientedNavigation extends RobotOrientedNavigation 
{
	private NavigationIntegrator fieldIntegrator;
	
	private double setpointX, setpointY, setpointHeading;
	
	public FieldOrientedNavigation (double setpointX, double setpointY, double setpointHeading, int set)
	{
		super(0, 0, 0, set);
		
		this.setpointX = setpointX;
		this.setpointY = setpointY;
		this.setpointHeading = setpointHeading;
	}
	
	public void initialize ()
	{
		pidControllerX.setSetpoint(setpointX - fieldIntegrator.getX());
		pidControllerY.setSetpoint(setpointY - fieldIntegrator.getY());
		pidControllerHeading.setSetpoint(setpointHeading - fieldIntegrator.getHeading());
		
		super.initialize();
	}
	
	public void startFieldIntegrator ()
	{
		if (fieldIntegrator != null)
		{
			Robot.chassis.removeNavigationIntegrator(fieldIntegrator);
		}
		fieldIntegrator = new NavigationIntegrator();
		Robot.chassis.addNavigationIntegrator(fieldIntegrator);
	}
}
