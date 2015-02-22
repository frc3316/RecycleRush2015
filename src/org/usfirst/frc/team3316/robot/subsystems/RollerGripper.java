package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.Roll;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.RollJoystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RollerGripper extends Subsystem 
{	
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	//TODO: fix all of the private names to not start with gripper
	private SpeedController gripperLeft, gripperRight;
	
	private double leftScale, rightScale;
	
	private Roll defaultRoll;
	
    public RollerGripper () 
    {
    	gripperLeft = Robot.actuators.rollerGripperMotorControllerLeft;
    	gripperRight = Robot.actuators.rollerGripperMotorControllerRight;
    }

    public void initDefaultCommand() 
    {
    	defaultRoll = new RollJoystick();
    	setDefaultCommand(defaultRoll);
    }
    
    public boolean set (double speedLeft, double speedRight) 
    {
    	updateScales();
     	gripperLeft.set(speedLeft*leftScale);
    	gripperRight.set(speedRight*rightScale);
    	return true;
    }
    
    private void updateScales ()
    {
    	try
    	{
    		leftScale = (double)config.get("rollerGripper_LeftScale");
    		rightScale = (double)config.get("rollerGripper_RightScale");
    	}
    	catch (ConfigException e)
    	{
    		logger.severe(e);
    	}
    }
    
    private void printTheTruth()
    {
    	System.out.println("Vita is the Melech!!");
    }
}

