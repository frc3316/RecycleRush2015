package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.rollerGripper.GamePieceCollected;
import org.usfirst.frc.team3316.robot.stacker.GamePieceType;
import org.usfirst.frc.team3316.robot.stacker.StackerPosition;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class MoveStackerToTote extends MoveStacker
{
	protected void setSolenoids() 
	{
		Robot.stacker.closeSolenoidUpper();
    	Robot.stacker.closeSolenoidBottom();
	}
}
