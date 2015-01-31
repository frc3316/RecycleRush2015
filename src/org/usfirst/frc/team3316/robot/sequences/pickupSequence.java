package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.RollIn;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToFloor;
import org.usfirst.frc.team3316.robot.stacker.commands.MoveStackerToTote;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class pickupSequence extends CommandGroup 
{    
	DBugLogger logger = Robot.logger;
	Config config = Robot.config;
	
	private double heightMin, heightMax;
	
	protected void initialize() {
		double currentHeight = Robot.stacker.getHeight();
		updateHeightRange("STACKER_MOVE_STACKER_TO_TOTE_HEIGHT_MAX", "STACKER_MOVE_STACKER_TO_TOTE_HEIGHT_MIN");
        if ((currentHeight > heightMin) && (currentHeight < heightMax))
		{
			addSequential(new RollIn());
	        addSequential(new MoveStackerToFloor());
	        addSequential(new MoveStackerToTote());
		}
		
		super.initialize();
	}
    public pickupSequence() 
    {
		//CR: we can't start a pickup sequences if were not in Tote height
    }
    
    protected void updateHeightRange (String heightMaxName, String heightMinName)
    {
    	try 
    	{
			heightMax = (double) config.get(heightMaxName);
			heightMin = (double) config.get(heightMinName);
		} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
    }
}
