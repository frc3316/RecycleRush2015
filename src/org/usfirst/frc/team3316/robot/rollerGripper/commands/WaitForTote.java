package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.utils.GamePieceCollected;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command that finishes when a game piece is completely collected
 */
public class WaitForTote extends Command 
{
	DBugLogger logger = Robot.logger;
	
    public WaitForTote() {}

    protected void initialize() {
    	logger.fine("WaitForGamePiece command initialize");
    }

    protected void execute() {}

    protected boolean isFinished() 
    {
    	GamePieceCollected gp = Robot.rollerGripper.getGamePieceCollected();
        return gp == GamePieceCollected.Tote;
    }

    protected void end() {}

    protected void interrupted() {}
}
