package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Stacker extends Subsystem {
	
    Config config = Robot.config;
    DBugLogger logger = Robot.logger;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Stacker () {
    	
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

