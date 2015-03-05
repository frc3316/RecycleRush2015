package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.subsystems.Chassis.NavigationIntegrator;
import org.usfirst.frc.team3316.robot.utils.DBugPIDOutput;
import org.usfirst.frc.team3316.robot.utils.DBugPIDSource;

import edu.wpi.first.wpilibj.PIDController;

public class FieldOrientedNavigation extends FieldOrientedDrive 
{
	private RobotOrientedNavigation robotOrientedNavigation;
	
	private NavigationIntegrator integrator;
	
	private PIDController pidControllerX, pidControllerY, pidControllerHeading;
	private DBugPIDSource pidSourceX, pidSourceY, pidSourceHeading;
	private DBugPIDOutput pidOutputX, pidOutputY, pidOutputHeading;
	
	private double setpointX, setpointY, setpointHeading;
	
	public FieldOrientedNavigation(double setpointX, double setpointY, double setpointHeading)
	{
		this.setpointX = setpointX;
		this.setpointY = setpointY;
		this.setpointHeading = setpointHeading;
		
		robotOrientedNavigation = new RobotOrientedNavigation() 
		{	
			protected double setSetpointS() 
			{
				return this.getS();
			}
			
			protected double setSetpointF() 
			{
				return this.getF();
			}
			
			protected double setSetpointTurn() 
			{
				return this.getTurn();
			}
		};
		
		pidSourceX = new DBugPIDSource(() -> {return integrator.getX();} );
		pidSourceY = new DBugPIDSource(() -> {return integrator.getY();} );
		pidSourceHeading = new DBugPIDSource(() -> {return integrator.getHeading();} );
		
		pidOutputX = new DBugPIDOutput();
		pidOutputY = new DBugPIDOutput();
		pidOutputHeading = new DBugPIDOutput();
		
		pidControllerX = new PIDController(0, 
										   0, 
										   0, 
										   pidSourceX, 
										   pidOutputX, 
										   0.05); //in seconds
		
		pidControllerY = new PIDController(0, 
										   0, 
										   0, 
										   pidSourceY, 
										   pidOutputY, 
										   0.05); //in seconds
		
		pidControllerHeading = new PIDController(0, 
										   0, 
										   0, 
										   pidSourceHeading, 
										   pidOutputHeading, 
										   0.05); //in seconds
	}
	
	protected void initialize ()
	{
		integrator = new NavigationIntegrator();
		Robot.chassis.addNavigationIntegrator(integrator);
		
		pidControllerX.setSetpoint(setpointX);
		pidControllerY.setSetpoint(setpointY);
		pidControllerHeading.setSetpoint(setpointHeading);
		
		pidControllerX.enable();
		pidControllerY.enable();
		pidControllerHeading.enable();
		
		robotOrientedNavigation.start();
	}
	
	protected void execute ()
	{
		updatePIDVariables();
		setFieldVector(pidOutputX.pidGet(), pidOutputY.pidGet(), integrator.getHeading());
		setRotation(pidOutputHeading.pidGet());
	}
	
	protected boolean isFinished ()
	{
		return (pidControllerX.onTarget() &&
				pidControllerY.onTarget() &&
				pidControllerHeading.onTarget());
	}
	
	protected void end ()
	{
		robotOrientedNavigation.cancel();
		
		pidControllerX.reset();
		pidControllerY.reset();
		pidControllerHeading.reset();
	}
	
	protected void interrupted ()
	{
		end();
	}
	
	private void updatePIDVariables ()
	{
		try
		{
			/*
			 * Kp, Ki, Kd values
			 */
			pidControllerX.setPID(
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerX_KP") / 1000, 
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerX_KI") / 1000, 
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerX_KD") / 1000);
			
			pidControllerY.setPID(
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerY_KP") / 1000, 
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerY_KI") / 1000, 
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerY_KD") / 1000);
			
			pidControllerHeading.setPID(
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerHeading_KP") / 1000, 
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerHeading_KI") / 1000, 
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerHeading_KD") / 1000);
			
			/*
			 * Absolute tolerances
			 */
			pidControllerX.setAbsoluteTolerance(
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerX_AbsoluteTolerance"));
			
			pidControllerY.setAbsoluteTolerance(
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerY_AbsoluteTolerance"));
			
			pidControllerHeading.setAbsoluteTolerance(
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerHeading_AbsoluteTolerance"));
			
			/*
			 * Output ranges
			 */
			pidControllerX.setOutputRange(
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerX_MinimumOutput"), 
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerX_MaximumOutput"));
			
			pidControllerY.setOutputRange(
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerY_MinimumOutput"), 
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerY_MaximumOutput"));
			
			pidControllerHeading.setOutputRange(
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerHeading_MinimumOutput"), 
					(double)config.get("chassis_FieldOrientedNavigation_PIDControllerHeading_MaximumOutput"));
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}
