/**
 * Chassis subsystem
 */
package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.Drive;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Chassis extends Subsystem 
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	private VictorSP left;
	private VictorSP right;
	private VictorSP center;
	
	private double leftScale, rightScale, centerScale;
	
	Drive defaultDrive;
	
	public Chassis ()
	{
		left = Robot.actuators.chassisLeft;
		right = Robot.actuators.chassisRight;
		center = Robot.actuators.chassisCenter;
		
		//need to init defaultDrive before setting it as the default drive
		//defaultDrive = new StrafeDrive ();
		
		configUpdate();
	}
	
    public void initDefaultCommand() 
    {
       //setDefaultCommand(defaultDrive);
    }
    
    public boolean set (double left, double right, double center)
    {
    	this.left.set(left*leftScale);
    	this.right.set(right*rightScale);
    	this.center.set(center*centerScale);
    	return true;
    }
    
    public double getHeading ()
    {
    	//should return gyro reading
    	return 0;
    }
    
    public double getAngularVelocity ()
    {
    	//should return gyro reading
    	return 0;
    }
    
    public void configUpdate ()
    {
    	try
    	{
    		leftScale = (double)config.get("chassisLeftScale");
    		rightScale = (double)config.get("chassisRightScale");
    		centerScale = (double)config.get("chassisCenterScale");
    		
    		defaultDrive.configUpdate();
    	}
    	catch (ConfigException e)
    	{
    		logger.severe(e.getMessage());
    	}
    }
}

