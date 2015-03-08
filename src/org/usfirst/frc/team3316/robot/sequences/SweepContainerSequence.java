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
	
	private boolean pushContainerStarted, returnToTrackStarted;

	public SweepContainerSequence ()
	{
		pushContainer = new RobotOrientedNavigation(0, 0, -45, 1);
		returnToTrack = new RobotOrientedNavigation(0, 0, 0, 2);
	}
	
	protected void initialize() 
	{
		returnIntegrator = new NavigationIntegrator();
		Robot.chassis.addNavigationIntegrator(returnIntegrator);
		
		pushContainerStarted = returnToTrackStarted = false;
		
		pushContainer.start();
	}

	protected void execute() 
	{
		if (pushContainer.isRunning())
		{
			pushContainerStarted = true;
		}
		
		if (!pushContainer.isRunning() && pushContainerStarted)
		{
			returnToTrack = new RobotOrientedNavigation(
					0, 0, -returnIntegrator.getHeading(), 2);
			returnToTrack.start();
		}
		
		if (returnToTrack.isRunning())
		{
			returnToTrackStarted = true;
		}
	}

	protected boolean isFinished()
	{
		return (!returnToTrack.isRunning() && returnToTrackStarted);
	}

	protected void end() 
	{
		pushContainer.cancel();
		returnToTrack.cancel();
		
		Robot.chassis.removeNavigationIntegrator(returnIntegrator);
	}

	protected void interrupted() 
	{
		end();
	}
}
