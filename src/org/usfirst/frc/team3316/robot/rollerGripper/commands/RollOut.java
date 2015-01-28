package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;

public class RollOut extends Roll{

	protected void execute () {
    	Robot.rollerGripper.set(speed, speed);
	}
}
