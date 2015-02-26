package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;

/**
 *
 */
public class MoveStackerToFloor extends MoveStacker
{
    public MoveStackerToFloor() 
    {
        super("stacker_MoveStackerToFloor_HeightMax", 
        		"stacker_MoveStackerToFloor_HeightMin");
    }

	protected void setSolenoids() 
	{
		Robot.stacker.closeSolenoidContainer();
		Robot.stacker.openSolenoidUpper();
		Robot.stacker.openSolenoidBottom();
	}
}
