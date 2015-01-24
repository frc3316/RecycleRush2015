package org.usfirst.frc.team3316.robot.anschluss.commands;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Command that closes the anschluss
 */
public class closeAnschluss extends Command {

	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	double motorSpeed;
	
    public closeAnschluss() {
    	requires(Robot.anschluss);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	try {
			motorSpeed = (double) config.get("closeAnschlussMotorSpeed");
		}
    	catch (ConfigException e) {
			//CR: fix the debug message
    		StringWriter sw = new StringWriter();
    		e.printStackTrace(new PrintWriter(sw));
    		String exceptionStackTrace = sw.toString();
			logger.severe("exceptionStackTrace");
			logger.severe(e.getMessage());
			
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		Robot.anschluss.set(motorSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.anschluss.isClosed();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}