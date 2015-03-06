package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.subsystems.Chassis.NavigationIntegrator;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RobotOrientedNavigation extends FieldOrientedDrive 
{
	/*
	 * PIDSource classes in order to use wpilib's PIDController
	 */
	private class PIDSourceX implements PIDSource
	{
		public double pidGet() 
		{
			logger.fine("" + integrator.getX());
			return integrator.getX();
		}
	}
	private class PIDSourceY implements PIDSource
	{
		public double pidGet() 
		{
			logger.fine("" + integrator.getY());
			return integrator.getY();
		}
	}
	private class PIDSourceHeading implements PIDSource
	{
		public double pidGet() 
		{
			logger.fine("" + integrator.getHeading());
			return integrator.getHeading();
		}
	}
	
	/*
	 * PIDOutput classes in order to use wpilib's PIDController
	 */
	private class PIDOutputX implements PIDOutput
	{
		public void pidWrite (double output) 
		{
			logger.fine("" + output);
			outputX = output;
		}
	}
	private class PIDOutputY implements PIDOutput
	{
		public void pidWrite (double output) 
		{
			logger.fine("" + output);
			outputY = output;
		}
	}
	private class PIDOutputHeading implements PIDOutput
	{
		public void pidWrite (double output) 
		{
			logger.fine("" + output);
			outputHeading = output;
		}
	}
	
	private NavigationIntegrator integrator;
	
	private PIDController pidControllerX, pidControllerY, pidControllerHeading;
	
	private double setpointX, setpointY, setpointHeading;
	
	private double outputX, outputY, outputHeading;
	
	public RobotOrientedNavigation (double setpointX , double setpointY, double setpointHeading)
	{
		/*
		 * variable init
		 */
		this.setpointX = setpointX;
		this.setpointY = setpointY;
		this.setpointHeading = setpointHeading;
		
		/*
		 * Init of pid controllers
		 */
		// PID variables are updated in updatePIDVariables
		pidControllerX = new PIDController(0, //Kp
										   0, //Ki
										   0, //Kd
										   new PIDSourceX(), //PIDSource 
										   new PIDOutputX(), //PIDOutput
										   0.05); //Update rate in ms
		pidControllerX.setOutputRange(-1, 1);
		
		pidControllerY = new PIDController(0, //Kp
										   0, //Ki
										   0, //Kd
										   new PIDSourceY(), //PIDSource 
										   new PIDOutputY(), //PIDOutput
										   0.05); //Update rate in ms
		pidControllerY.setOutputRange(-1, 1);
		
		pidControllerHeading = new PIDController(0, //Kp
												 0, //Ki
												 0, //Kd
												 new PIDSourceHeading(), //PIDSource 
												 new PIDOutputHeading(), //PIDOutput
												 0.05); //Update rate in ms
		pidControllerHeading.setOutputRange(-1, 1);
		pidControllerHeading.setInputRange(-180, 180);
		pidControllerHeading.setContinuous(true);
	}
	
	protected void initialize ()
	{
		super.initialize();
		
		integrator = new NavigationIntegrator();
		Robot.chassis.addNavigationIntegrator(integrator);
		
		SmartDashboard.putNumber("Setpoint X", setpointX);
		SmartDashboard.putNumber("Setpoint Y", setpointY);
		SmartDashboard.putNumber("Setpoint Heading", setpointHeading);
		
		pidControllerX.setSetpoint(setpointX);
		pidControllerY.setSetpoint(setpointY);
		pidControllerHeading.setSetpoint(setpointHeading);
		
		pidControllerX.enable();
		pidControllerY.enable();
		pidControllerHeading.enable();
	}
	
	protected void set ()
	{
		updatePIDVariables();
		logger.fine("output(" + outputX + ", " + outputY + ", " + outputHeading + ")");
		setFieldVector(outputX, outputY, integrator.getHeading());
		setRotation(outputHeading);
	}
	
	protected boolean isFinished ()
	{
		SmartDashboard.putBoolean("PID X on target", pidControllerX.onTarget());
		SmartDashboard.putBoolean("PID Y on target", pidControllerY.onTarget());
		SmartDashboard.putBoolean("PID Heading on target", pidControllerHeading.onTarget());
		return (pidControllerX.onTarget() && pidControllerY.onTarget() && pidControllerHeading.onTarget());
	}
	
	protected void end ()
	{
		super.end();
		
		Robot.chassis.removeNavigationIntegrator(integrator);
		
		pidControllerX.setSetpoint(0);
		pidControllerY.setSetpoint(0);
		pidControllerHeading.setSetpoint(0);
		
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
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerX_KP") / 1000, 
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerX_KI") / 1000, 
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerX_KD") / 1000);
			
			pidControllerY.setPID(
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerY_KP") / 1000, 
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerY_KI") / 1000, 
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerY_KD") / 1000);
			
			pidControllerHeading.setPID(
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerHeading_KP") / 1000, 
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerHeading_KI") / 1000, 
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerHeading_KD") / 1000);
			
			/*
			 * Absolute tolerances
			 */
			pidControllerX.setAbsoluteTolerance(
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerX_AbsoluteTolerance"));
			
			pidControllerY.setAbsoluteTolerance(
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerY_AbsoluteTolerance"));
			
			pidControllerHeading.setAbsoluteTolerance(
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerHeading_AbsoluteTolerance"));
			
			/*
			 * Output ranges
			 */
			pidControllerX.setOutputRange(
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerX_MinimumOutput"), 
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerX_MaximumOutput"));
			
			pidControllerY.setOutputRange(
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerY_MinimumOutput"), 
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerY_MaximumOutput"));
			
			pidControllerHeading.setOutputRange(
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerHeading_MinimumOutput"), 
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerHeading_MaximumOutput"));
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}
