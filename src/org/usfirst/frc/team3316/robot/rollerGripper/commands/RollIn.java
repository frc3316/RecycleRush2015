package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.stacker.GamePiece;
import org.usfirst.frc.team3316.robot.stacker.GamePieceType;

import edu.wpi.first.wpilibj.command.WaitCommand;

public class RollIn extends Roll
{
	protected boolean isFinished ()
	{
		return Robot.sensors.stackerSwitchContainer.get(); //is finished when a GamePiece(tote/container) is inside the stacker
	}
	
	protected void end()
	{
		//CR: What if the container MS is pressed a code-cycle before the tote one?
		//    create a loop that checks for number of iterations before pushing the GP to stack.
		//    (this will be implemented in 'isFinished')
		if (Robot.sensors.stackerSwitchContainer.get()) 
    	{
			new WaitCommand(1); // wait a second and then check if the tote switch is pressed
    		if(Robot.sensors.stackerSwitchTote.get())
    		{
    			Robot.stacker.pushToStack(new GamePiece(GamePieceType.GreyTote));
    		}
    		else
    		{
    			Robot.stacker.pushToStack(new GamePiece(GamePieceType.Container));
    		}
    	}
	}
	
	protected void setSpeed() 
    {
    	try
    	{
			speedLeft = (double) config.get("rollerGripper_RollIn_Speed_Left");
			speedRight = (double) config.get("rollerGripper_RollIn_Speed_Right");
		} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
    }
}
