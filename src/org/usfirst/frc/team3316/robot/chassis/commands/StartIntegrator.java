package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is currently only for testing purposes and should be deleted
 */
public class StartIntegrator extends Command 
{
    public StartIntegrator() {}

    protected void initialize() 
    {
    	Robot.chassis.addNavigationIntegrator(Robot.chassis.navigationIntegrator);
    }

    protected void execute() {}

    protected boolean isFinished() 
    {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}