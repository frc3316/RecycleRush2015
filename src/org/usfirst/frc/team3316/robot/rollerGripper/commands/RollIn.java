package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.stacker.GamePiece;
import org.usfirst.frc.team3316.robot.stacker.GamePieceType;

public class RollIn extends Roll
{
	public RollIn() {
		super("rollerGripper_RollIn_SpeedLeft", 
				"rollerGripper_RollIn_SpeedRight");
	}
	
	//TODO: add isFinished by switch and IR
}
