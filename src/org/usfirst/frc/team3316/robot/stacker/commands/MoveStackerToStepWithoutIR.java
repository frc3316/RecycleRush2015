package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveStackerToStepWithoutIR extends Command
{
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
    public MoveStackerToStepWithoutIR()
    {
        requires(Robot.stacker);
    }

    protected void initialize() 
    {
    	Robot.stacker.closeSolenoidStep();
    	Robot.stacker.openSolenoidTote();
    }

    protected void execute() {
    }

    protected boolean isFinished() 
    {
    	return true;
    }
    protected void end() {
    }

    protected void interrupted() {
    }
}
