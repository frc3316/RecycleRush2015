package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.utils.DBugPIDOutput;
import org.usfirst.frc.team3316.robot.utils.DBugPIDSource;

import edu.wpi.first.wpilibj.PIDController;

/**
 * 
 */
public abstract class RobotOrientedNavigation extends RobotOrientedDrive 
{
	private PIDController pidControllerS, pidControllerF, pidControllerTurn;
	private DBugPIDSource pidSourceS, pidSourceF, pidSourceTurn;
	private DBugPIDOutput pidOutputS, pidOutputF, pidOutputTurn;
	
	private double currentS = 0, currentF = 0, currentTurn = 0;
	
	public RobotOrientedNavigation ()
	{
		/*
		 * Init of pid controllers
		 */
		pidSourceS = new DBugPIDSource(() -> {return currentS;} );
		pidSourceF = new DBugPIDSource(() -> {return currentF;} );
		pidSourceTurn = new DBugPIDSource(() -> {return currentTurn;} );
		
		pidOutputS = new DBugPIDOutput();
		pidOutputF = new DBugPIDOutput();
		pidOutputTurn = new DBugPIDOutput();
		
		// PID variables are updated in updatePIDVariables
		pidControllerS = new PIDController(0, //Kp
										   0, //Ki
										   0, //Kd
										   pidSourceS, //PIDSource 
										   pidOutputS, //PIDOutput
										   0.05); //Update rate in seconds
		pidControllerS.setInputRange(-1, 1);
		pidControllerS.setOutputRange(-1, 1);
		
		pidControllerF = new PIDController(0, //Kp
										   0, //Ki
										   0, //Kd
										   pidSourceF, //PIDSource 
										   pidOutputF, //PIDOutput
										   0.05); //Update rate in seconds
		pidControllerF.setInputRange(-1, 1);
		pidControllerF.setOutputRange(-1, 1);
		
		pidControllerTurn = new PIDController(0, //Kp
												 0, //Ki
												 0, //Kd
												 pidSourceTurn, //PIDSource 
												 pidOutputTurn, //PIDOutput
												 0.05); //Update rate in seconds
		pidControllerTurn.setInputRange(-1, 1);
		pidControllerTurn.setOutputRange(-1, 1);
	}
	
	protected void initialize ()
	{
		super.initialize();
		
		currentS = currentF = currentTurn = 0;
		
		pidControllerS.enable();
		pidControllerF.enable();
		pidControllerTurn.enable();
	}
	
	protected void set ()
	{
		updatePIDVariables();
		
		pidControllerS.setSetpoint(setSetpointS());
		pidControllerF.setSetpoint(setSetpointF());
		pidControllerTurn.setSetpoint(setSetpointTurn());
		
		addToCurrents();
		
		setRobotVector(currentS, currentF);
		setRotation(currentTurn);
	}
	
	protected abstract double setSetpointS();
	protected abstract double setSetpointF();
	protected abstract double setSetpointTurn();
	
	private void addToCurrents ()
	{
		currentS += pidOutputS.pidGet();
		currentF += pidOutputF.pidGet();
		currentTurn += pidOutputTurn.pidGet();
	}
	
	protected void end ()
	{
		super.end();
		
		pidControllerS.reset();
		pidControllerF.reset();
		pidControllerTurn.reset();
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
			pidControllerS.setPID(
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerS_KP") / 1000, 
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerS_KI") / 1000, 
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerS_KD") / 1000);
			
			pidControllerF.setPID(
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerF_KP") / 1000, 
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerF_KI") / 1000, 
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerF_KD") / 1000);
			
			pidControllerTurn.setPID(
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerTurn_KP") / 1000, 
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerTurn_KI") / 1000, 
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerTurn_KD") / 1000);
			
			/*
			 * Absolute tolerances
			 */
			pidControllerS.setAbsoluteTolerance(
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerS_AbsoluteTolerance"));
			
			pidControllerF.setAbsoluteTolerance(
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerF_AbsoluteTolerance"));
			
			pidControllerTurn.setAbsoluteTolerance(
					(double)config.get("chassis_RobotOrientedNavigation_PIDControllerTurn_AbsoluteTolerance"));
			
			/*
			 * Output ranges
			 */
			pidControllerS.setOutputRange(
					- (Math.abs(pidControllerS.getError()) ), 
					  (Math.abs(pidControllerS.getError()) ));
			
			pidControllerF.setOutputRange(
					- (Math.abs(pidControllerF.getError()) ), 
					  (Math.abs(pidControllerF.getError()) ));
			
			pidControllerTurn.setOutputRange(
					- (Math.abs(pidControllerTurn.getError()) ), 
					  (Math.abs(pidControllerTurn.getError()) ));
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}
