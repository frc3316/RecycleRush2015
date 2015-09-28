package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.RollContainer;
import org.usfirst.frc.team3316.robot.sequences.AutoToteCollect;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

public class DriveToYellowTote extends RobotOrientedNavigation
{
	protected class PIDCameraSourceY implements PIDSource
	{
		PIDSourceY defaultSource = new PIDSourceY();
		
		public double pidGet() 
		{
			if (Robot.autonCamera.lastReport.isTote) 
			{
				return Robot.autonCamera.lastReport.ToteDistance;
			}
			else return defaultSource.pidGet(); 
		}
	}
	
	Command rollContainer, autoToteCollect;
	
	public DriveToYellowTote()
	{
		super(0, 0, 0, 5);
		
		rollContainer = new RollContainer();
		autoToteCollect = new AutoToteCollect();
		
		pidControllerY = new PIDController(0, // Kp
				0, // Ki
				0, // Kd
				new PIDSourceY(), // PIDSource
				new PIDOutputY(), // PIDOutput
				0.05); // Update rate in ms
		pidControllerY.setOutputRange(-1, 1);
	}
	
	public void intialize() 
	{
		super.initialize();
		
		setDefaultSetPoint();
		
		rollContainer.start();
	}
	
	private void setDefaultSetPoint() 
	{
		try 
		{
			pidControllerY.setSetpoint((double) config.get("chassis_DriveToYellowTote_DefaultSetPoint"));
		}
		catch (ConfigException e) {
			logger.severe(e);
		}
	}
	
	public void set ()
	{
		super.set();
		
		if (Robot.autonCamera.lastReport.isTote) 
		{
			autoToteCollect.start();
		}
	}
	
	public boolean isFinished ()
	{
		super.isFinished();
		return !autoToteCollect.isRunning() && !rollContainer.isRunning();
	}
}
