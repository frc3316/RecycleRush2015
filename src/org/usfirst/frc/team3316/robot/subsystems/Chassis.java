/**
 * Chassis subsystem
 */
package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.Drive;
import org.usfirst.frc.team3316.robot.chassis.commands.RobotOrientedDrive;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Chassis extends Subsystem 
{	
	
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	private SpeedController left1, left2;
	private SpeedController right1, right2;
	private SpeedController center;
	
	private double leftScale, rightScale, centerScale;
	
	Drive defaultDrive;
	
	public Chassis ()
	{
		left1 = Robot.actuators.chassisMotorControllerLeft1;
		left2 = Robot.actuators.chassisMotorControllerLeft2;
		
		right1 = Robot.actuators.chassisMotorControllerRight1;
		right2 = Robot.actuators.chassisMotorControllerRight2;
		
		center = Robot.actuators.chassisMotorControllerCenter;
	}
	
    public void initDefaultCommand() 
    {
    	defaultDrive = new RobotOrientedDrive();
    	setDefaultCommand(defaultDrive);
    }
    
    public boolean set (double left, double right, double center)
    {
    	updateScales();
    	
    	this.left1.set (left * leftScale);
    	this.left2.set (left * leftScale);
    	
    	this.right1.set (right * rightScale);
    	this.right2.set (right * rightScale);
    	
    	this.center.set (center * centerScale);
    	
    	return true;
    }
    
    private void updateScales ()
    {
    	try
    	{
    		leftScale = (double)config.get("chassis_LeftScale");
    		rightScale = (double)config.get("chassis_RightScale");
    		centerScale = (double)config.get("chassis_CenterScale");
    	}
    	catch (ConfigException e)
    	{
    		logger.severe(e);
    	}
    }
}

