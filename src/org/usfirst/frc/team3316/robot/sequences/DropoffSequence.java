package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.stacker.commands.OpenGripper;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToFloor;
import org.usfirst.frc.team3316.robot.utils.StackerPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DropoffSequence extends CommandGroup 
{    
    public DropoffSequence() 
    {
    	addSequential(new MoveStackerToFloor());
    	addSequential(new OpenGripper());
    }
    
    DBugLogger logger = Robot.logger;
    
    protected void initialize ()
    {
    	logger.info(this.getName() + " initialize");
    }
    
    protected void end ()
    {
    	logger.info(this.getName() + " end");
    	Robot.stacker.totesCollected = 0;
    }
    
    protected void interrupted ()
    {
    	logger.info(this.getName() + " interrupted");
    	if (Robot.stacker.getPosition() == StackerPosition.Floor)
    	{
        	Robot.stacker.totesCollected = 0;
    	}
    }
}
