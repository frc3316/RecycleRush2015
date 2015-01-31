package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;

public class MoveStackerToTote extends MoveStacker{
	 //CR: All MoveStackerTo* commands should inherit from a basic 'MoveStacker' abstract command
	 //    since all of them do the same thing:
	 //     * override an abstract which will set your solenoids
	 //     * override constructor with correct mix/min height variable names
	 //    and then you don't need to rewrite the logic in initialize, isFinished and updateHeightRange
	 //    additionally the basic MoveStacker command should have a 'canRun' function that will be called
	 //    when initializing. In this function we will run any prerequisite checks similar to 'isFull';
	 //    by default the 'canRun' function will simply return true, but if you want to add logic, 
	public MoveStackerToTote() 
    {
        super("STACKER_MOVE_STACKER_TO_TOTE_HEIGHT_MAX", "STACKER_MOVE_STACKER_TO_TOTE_HEIGHT_MIN");
        requires(Robot.stacker);
    }
    
    protected boolean isFinished() 
    {
		double currentHeight = Robot.stacker.getHeight();
        return (currentHeight > heightMin) && (currentHeight < heightMax);
    }

	protected void setSolenoids() {
		Robot.stacker.closeSolenoidStep();
    	Robot.stacker.closeSolenoidTote();
	}
}
