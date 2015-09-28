package org.usfirst.frc.team3316.robot.chassis.commands;

import java.security.acl.LastOwnerException;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.RollContainer;
import org.usfirst.frc.team3316.robot.sequences.AutoToteCollect;
import org.usfirst.frc.team3316.robot.vision.AutonomousCamera.ParticleReport;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToYellowTote extends RobotOrientedNavigation
{
	Command rollContainer, autoToteCollect;
	
	public DriveToYellowTote()
	{
		super(0, 0, 0, 5);
		rollContainer = new RollContainer();
		autoToteCollect = new AutoToteCollect();
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
		if (Robot.autonCamera.lastReport.isTote) {
			pidControllerY.setSetpoint(Robot.autonCamera.lastReport.ToteDistance);
			autoToteCollect.start();
		}
	}
	
	public boolean isFinished ()
	{
		super.isFinished();
		return !autoToteCollect.isRunning() && !rollContainer.isRunning();
	}
}
