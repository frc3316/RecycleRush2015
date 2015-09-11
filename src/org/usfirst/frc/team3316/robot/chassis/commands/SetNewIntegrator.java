package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.subsystems.Chassis.NavigationIntegrator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetNewIntegrator extends Command 
{
	public SetNewIntegrator() {}

	protected void initialize() 
	{	
		Robot.chassis.removeNavigationIntegrator(Robot.chassis.testIntegrator);
		Robot.chassis.testIntegrator = new NavigationIntegrator();
		Robot.chassis.addNavigationIntegrator(Robot.chassis.testIntegrator);
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
