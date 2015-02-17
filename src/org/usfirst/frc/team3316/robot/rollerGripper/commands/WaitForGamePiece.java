package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.rollerGripper.GamePieceCollected;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command that finishes when a game piece is completely collected
 */
public class WaitForGamePiece extends Command 
{
    public WaitForGamePiece() {}

    protected void initialize() {}

    protected void execute() {}

    protected boolean isFinished() 
    {
        return (Robot.rollerGripper.getGamePieceCollected() == GamePieceCollected.Container ||
        		Robot.rollerGripper.getGamePieceCollected() == GamePieceCollected.Tote);
    }

    protected void end() {}

    protected void interrupted() {}
}
