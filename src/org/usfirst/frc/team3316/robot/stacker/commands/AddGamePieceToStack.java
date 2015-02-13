package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.rollerGripper.GamePieceCollected;
import org.usfirst.frc.team3316.robot.stacker.GamePiece;
import org.usfirst.frc.team3316.robot.stacker.GamePieceType;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AddGamePieceToStack extends Command 
{
    public AddGamePieceToStack() {}

    protected void initialize() 
    {
    	GamePieceCollected gpCollected = Robot.rollerGripper.getGamePieceCollected();
    	if (gpCollected == GamePieceCollected.Container)
    	{
    		Robot.stacker.pushToStack(new GamePiece(GamePieceType.Container));
    	}
    	else if (gpCollected == GamePieceCollected.Tote)
    	{
    		Robot.stacker.pushToStack(new GamePiece(GamePieceType.Tote));
    	}
    }

    protected void execute() {}

    protected boolean isFinished() 
    {
        return true;
    }

    protected void end() {}

    protected void interrupted() {}
}
