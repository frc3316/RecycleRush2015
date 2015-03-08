package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedNavigation;
import org.usfirst.frc.team3316.robot.subsystems.Chassis.NavigationIntegrator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SweepContainerSequence extends Command 
{
	RobotOrientedNavigation pushContainer;
	NavigationIntegrator returnIntegrator;
	RobotOrientedNavigation returnToTrack;

	public SweepContainerSequence ()
	{
		pushContainer = new RobotOrientedNavigation(0, 0, -45, 1);
		returnToTrack = new RobotOrientedNavigation(0, 0, 0, 2);
	}
	
	protected void initialize() 
	{
		returnIntegrator = new NavigationIntegrator();
		Robot.chassis.addNavigationIntegrator(returnIntegrator);
		
		pushContainer.start();
	}

	protected void execute() 
	{
		if (!pushContainer.isRunning())
		{
			returnToTrack = new RobotOrientedNavigation(
					0, 
					0, 
					- returnIntegrator.getHeading(), 
					2);
			
			returnToTrack.start();
		}
	}

	protected boolean isFinished()
	{
		return (!pushContainer.isRunning() && !returnToTrack.isRunning());
	}

	protected void end() 
	{
		Robot.chassis.removeNavigationIntegrator(returnIntegrator);
	}

	protected void interrupted() 
	{
		end();
	}
}
