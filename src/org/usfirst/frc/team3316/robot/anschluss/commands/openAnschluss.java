package org.usfirst.frc.team3316.robot.anschluss.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;
//CR: Add documentation
/**
 *
 */
public class openAnschluss extends Command {

	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	double motorSpeed;
	
    public openAnschluss() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.anschluss);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	try {
			//CR: We might want to use different speeds for opening
			//    and closing the subsystem.
			motorSpeed = (double) config.get("anschlussMotorSpeed");
		}
    	catch (ConfigException e) {
			//CR: Fix debug message
			logger.severe("init in operanschluss commandd");
			logger.severe(e.getMessage());
			
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		Robot.anschluss.set(motorSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.anschluss.isOpened();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
