/**
 * Chassis subsystem
 */
package org.usfirst.frc.team3316.robot.subsystems;

import java.util.HashSet;
import java.util.TimerTask;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.Drive;
import org.usfirst.frc.team3316.robot.chassis.commands.FieldOrientedDrive;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Chassis extends Subsystem 
{	
	/*
	 * An object that is passed to NavigationThread for integration
	 */
	public static class NavigationIntegrator
	{
		private double x = 0, y = 0, heading = 0;
		
		public void add (double dX, double dY, double dTheta)
		{
			this.x += dX;
			this.y += dY;
			this.heading += dTheta;
			/*
			 * makes sure heading is in the range (-180) to (180)
			 */
			this.heading = fixHeading(heading);
		}
		
		/**
		 * Return change in X that has been integrated
		 */
		public double getX ()
		{
			return x;
		}
		/**
		 * Return change in Y that has been integrated
		 */
		public double getY ()
		{
			return y;
		}
		/**
		 * Return change in heading that has been integrated.
		 * Heading returned is in the range (-180) to (180)
		 */
		public double getHeading ()
		{
			return heading;
		}
	}
	
	/*
	 * Runnable that calculates the robot's position in the field
	 * Calculates x, y, and theta delta and adds it to each integrator in the set
	 * Also calculates turning rate in chassis
	 */
	private class NavigationTask extends TimerTask
	{	
		private HashSet <NavigationIntegrator> integratorSet;
		private double previousTime = 0;
		private double previousHeading = 0;
		
		public NavigationTask ()
		{
			integratorSet = new HashSet <NavigationIntegrator>();
		}
		
		public void run() 
		{
			/*
			 * Variable init
			 */
			if (previousTime == 0)
			{
				previousTime = System.currentTimeMillis();
			}
			double currentTime = System.currentTimeMillis();
			double dT = (currentTime - previousTime) / 1000; //conversion to seconds
			double currentHeading = getHeading();
			
			/*
			 * Calculates speeds in field axes
			 */
			double vS, vF; //speeds relative to the robot (forward and sideways)
			vS = encoderCenter.getRate();
			vF = (encoderLeft.getRate() + encoderRight.getRate()) / 2;
			
			/*
			 * Calculates dTheta
			 */
			double dTheta = currentHeading - previousHeading;
			//Since heading is in the range (-180) to (180), when 
			//completing a full turn dTheta will be an absurdly big value
			//Checks if dTheta is an absurdly big value and fixes it
			if (dTheta > 340) //340 is a big number
			{
				dTheta -= 360;
			}
			if (dTheta < -340) //Math.abs(-340) is another big number
			{
				dTheta += 360;
			}
			
			/*
			 * Calculates angular velocity
			 */
			//Calculation from gyro
			angularVelocity = (dTheta)/dT;
			
			/*
			 * Adds all of the deltas to each integrator
			 */
			for (NavigationIntegrator integrator : integratorSet)
			{
				double vX, vY; //speeds relative to the orientation that the integrator started at
				double headingRad = Math.toRadians(integrator.getHeading());
				vX = (vF * Math.sin(headingRad)) + (vS * Math.sin(headingRad + (0.5 * Math.PI)));
				vY = (vF * Math.cos(headingRad)) + (vS * Math.cos(headingRad + (0.5 * Math.PI)));
				
				double dX, dY;
				dX = vX * dT;
				dY = vY * dT;
				
				integrator.add(dX, dY, dTheta);
			}
			
			/*
			 * Setting variables for next run
			 */
			previousTime = currentTime;
			previousHeading = currentHeading;
		}
		
		public boolean addIntegrator (NavigationIntegrator integrator)
		{
			return integratorSet.add(integrator);
		}
		
		public boolean removeIntegrator (NavigationIntegrator integrator)
		{
			return integratorSet.remove(integrator);
		}
	}
	
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	private NavigationTask navigationTask;
	
	private SpeedController left1, left2;
	private SpeedController right1, right2;
	private SpeedController center;
	
	private IMUAdvanced navx;
	
	private Encoder encoderLeft, encoderRight, encoderCenter;
	
	private double leftScale, rightScale, centerScale;
	
	private double headingOffset = 0;
	
	private double angularVelocity = 0; //this is constantly calculated by NavigationThread
	
	private double CHASSIS_WIDTH; //initialized in constructor 
	
	Drive defaultDrive;
	
	public Chassis ()
	{
		left1 = Robot.actuators.chassisMotorControllerLeft1;
		left2 = Robot.actuators.chassisMotorControllerLeft2;
		
		right1 = Robot.actuators.chassisMotorControllerRight1;
		right2 = Robot.actuators.chassisMotorControllerRight2;
		
		center = Robot.actuators.chassisMotorControllerCenter;
		
		navx = Robot.sensors.navx;
		
		encoderLeft = Robot.sensors.chassisEncoderLeft;
		encoderRight = Robot.sensors.chassisEncoderRight;
		encoderCenter = Robot.sensors.chassisEncoderCenter;
		
		try
		{
			CHASSIS_WIDTH = (double) config.get("CHASSIS_WIDTH");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
	
	public void timerInit ()
	{
		navigationTask = new NavigationTask();
		Robot.timer.schedule(navigationTask, 0, 50);
	}
	
    public void initDefaultCommand() 
    {
    	defaultDrive = new FieldOrientedDrive();
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
    
    /*
     * Heading
     */
    public double getHeading ()
    {
    	double headingToReturn = navx.getYaw() + headingOffset;
    	headingToReturn = fixHeading(headingToReturn);
    	
    	return headingToReturn;
    }
    
    /**
     * Sets the current robot angle to the specified angle
     * @param headingToSet the angle specified
     */
    public void setHeading (double headingToSet)
    {
    	double currentHeading = getHeading();
    	double currentOffset = headingOffset;
    	
    	headingOffset = (headingToSet + currentOffset - currentHeading);
    }
    
    /*
     * Angular velocity
     */
    public double getAngularVelocity ()
    {
    	return angularVelocity;
    }
    
    /*
     * Distance 
     */
    public double getDistanceLeft ()
    {
    	return encoderLeft.getDistance();
    }
    
   public double getDistanceRight ()
    {
    	return encoderRight.getDistance();
    }
    
    public double getDistanceCenter ()
    {
    	return encoderCenter.getDistance();
    }
    
    /*
     * Speed
     */
    public double getSpeedLeft ()
    {
      	return encoderLeft.getRate();
    }
        
    public double getSpeedRight ()
    {
       	return encoderRight.getRate();
    }
        
    public double getSpeedCenter ()
    {
      	return encoderCenter.getRate();
    }
    
    /*
     * Acceleration
     */
    public double getAccelX ()
    {
    	return navx.getWorldLinearAccelX();
    }
    
    public double getAccelY ()
    {
    	return navx.getWorldLinearAccelY();
    }
    
    /*
     * Navigation integrator
     */
    public boolean addNavigationIntegrator (NavigationIntegrator integrator)
    {
    	return navigationTask.addIntegrator(integrator);
    }
    
    public boolean removeNavigationIntegrator (NavigationIntegrator integrator)
    {
    	return navigationTask.removeIntegrator(integrator);
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
    
    //Returns the same heading in the range (-180) to (180)
    private static double fixHeading (double heading)
    {
    	double toReturn = heading % 360;
    	
    	if (toReturn < -180)
    	{
    		toReturn += 360;
    	}
    	else if (toReturn > 180)
    	{
    		toReturn -= 360;
    	}
    	return toReturn;
    }
}

