package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedNavigation;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.stacker.commands.CloseGripper;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToFloor;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DropoffSequence extends CommandGroup 
{    
    public DropoffSequence() 
    {
    	addSequential(new MoveStackerToFloor());
    	addSequential(new CloseGripper());
    	addSequential(new RobotOrientedNavigation(0, -1, 0, 1));
    }
    
    DBugLogger logger = Robot.logger;
    
    protected void initialize ()
    {
    	logger.info(this.getName() + " initialize");
    }
    
    protected void end ()
    {
    	logger.info(this.getName() + " end");
    }
    
    protected void interrupted ()
    {
    	logger.info(this.getName() + " interrupted");
    }
}
