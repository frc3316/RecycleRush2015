package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class WiggleWiggle extends CommandGroup {
    
    public  WiggleWiggle() {
        addSequential(new RightMove(), 0.25);
        addSequential(new LeftMove(), 0.25);
        addSequential(new RightMove(), 0.25);
        addSequential(new LeftMove(), 0.25);
    }
    
    private class RightMove extends Command {
    	
    	public RightMove () {
    		requires(Robot.chassis);
    	}
    	
		protected void initialize() {}
    	
    	protected void execute() {
    		Robot.chassis.set(0, 1, 0);
    	}

        protected boolean isFinished() 
        {
            return false;
        }

		protected void end() {}

		protected void interrupted() {}
    }
    
    private class LeftMove extends Command {
    	
    	public LeftMove () {
    		requires(Robot.chassis);
    	}
    	
		protected void initialize() {}
    	
    	protected void execute() {
    		Robot.chassis.set(1, 0, 0);
    	}

        protected boolean isFinished() 
        {
            return false;
        }

		protected void end() {}

		protected void interrupted() {}
    }
}
