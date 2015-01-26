package org.usfirst.frc.team3316.robot.anschluss.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Command that opens the anschluss
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
		//CR: move to set function
    	updateMotorSpeed();
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
    
    private void updateMotorSpeed() {
    	try {
			motorSpeed = (double) config.get("closeAnschlussMotorSpeed");
		}
    	catch (ConfigException e) {
    		logger.severe(e);			
		}
    }
}
