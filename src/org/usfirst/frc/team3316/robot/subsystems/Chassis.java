/**
 * Chassis subsystem
 */
package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.Drive;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Chassis extends Subsystem 
{
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	private VictorSP left;
	private VictorSP right;
	private VictorSP center;
	
	private IMUAdvanced navx;
	
	private double leftScale, rightScale, centerScale;
	
	Drive defaultDrive;
	
	public Chassis ()
	{
		left = Robot.actuators.chassisMotorControllerLeft;
		right = Robot.actuators.chassisMotorControllerRight;
		center = Robot.actuators.chassisMotorControllerCenter;
		
		navx = Robot.sensors.navx;
		//need to init defaultDrive before setting it as the default drive
		//defaultDrive = new StrafeDrive ();
	}
	
    public void initDefaultCommand() 
    {
       //setDefaultCommand(defaultDrive);
    }
    
    public boolean set (double left, double right, double center)
    {
    	updateScales();
    	this.left.set(left*leftScale);
    	this.right.set(right*rightScale);
    	this.center.set(center*centerScale);
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
    
    public double getHeading ()
    {
    	//TODO: need to check whether its pitch or roll, but it's not going to be yaw for sure
    	return navx.getYaw();
    }
    
    public double getAngularVelocity ()
    {
    	//TODO: should return gyro reading
    	return 0;
    }
    
    public double getAccelX ()
    {
    	//TODO: need to check whether its Y or Z, but it's not going to be X fo sho
    	return navx.getWorldLinearAccelX();
    }
    
    public double getAccelY ()
    {
    	//TODO: need to check whether its X or Z, but it's not going to be Y fo sho
    	return navx.getWorldLinearAccelY();
    }
    
    //not sure if Z acceleration is necessary
    //public double getAccelZ () {}
}

