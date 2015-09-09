package org.usfirst.frc.team3316.robot.sequences;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedNavigation;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.RollContainer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousTest extends CommandGroup {

	double pushTime, xDistance;
    public AutonomousTest() 
    {
    	addSequential(new RobotOrientedNavigation(xDistance, 0, 0, 1));
    	addParallel(new RobotOrientedNavigation(0, 2.11, 0, 3));
    	addSequential(new RollContainer(), pushTime);
    	addSequential(new AutoToteCollect());
    }
    
    DBugLogger logger = Robot.logger;
    Config config= Robot.config;

    protected void initialize() {
    	logger.info(this.getName() + " initialize");

    	try
		{
    		pushTime = (double) config.get("rollerGripper_PushContainer_PushTime");
    		xDistance = (double) config.get("chassis_AutonomousTest_xDistance");
		}
    	
		catch (ConfigException e)
		{
			logger.severe(e);
		}
    }
}