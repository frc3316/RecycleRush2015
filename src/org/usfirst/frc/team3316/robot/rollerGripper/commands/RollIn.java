package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RollIn extends Command {
	
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	private double rollInSpeed;
	
	
    public RollIn() {
        requires(Robot.rollerGripper);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.rollerGripper.set(rollInSpeed, rollInSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    public void updateSpeed () {
    	try {
			rollInSpeed = (double) config.get("ROLLER_GRIPPER_SPEED_ROLL_IN");
		} catch (ConfigException e) {
			logger.severe(e);
		}
    }
}
