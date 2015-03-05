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
import org.usfirst.frc.team3316.robot.utils.MovingAverage;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		private double previousF = 0, previousHeading = 0;
		private double speedS = 0; //speed sideways
		
		public NavigationTask ()
		{
			integratorSet = new HashSet <NavigationIntegrator>();
		}
		
		public void run() 
		{
			//Makes sure the first time delta will not be since 1970
			if (previousTime == 0)
			{
				previousTime = System.currentTimeMillis();
			}
			/*
			 * Current variables
			 */
			double currentTime = System.currentTimeMillis();
			double currentHeading = getHeading();
			double currentF = (getDistanceLeft() + getDistanceRight()) / 2; //Forward distance
			
			/*
			 * Calculates deltas between current and previous
			 */
			
			//delta time
			double dT = (currentTime - previousTime) / 1000; //conversion to seconds
			
			//delta heading (theta)
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
			
			//delta forward
			double dF = currentF - previousF;
			
			//delta sideways 
			//this is calculated with the accelerometer because the center wheel slides too much 
			speedS += getAccelX() * dT;
			//Resets the speed accumulator to prevent drift
			if (getSpeedCenter() == 0)
			{
				speedS = 0;
			}
			double dS = speedS*dT;
			
			/*
			 * Calculates angular velocity
			 */
			angularVelocity = (dTheta)/dT;
			
			/*
			 * Adds all of the deltas to each integrator, relatively 
			 * to its starting position
			 */
			for (NavigationIntegrator integrator : integratorSet)
			{
				double dX, dY; //speeds relative to the orientation that the integrator started at
				//headingRad is the average of the previous integrator angle and the angle it will have (in radians)
				double headingRad = (Math.toRadians(integrator.getHeading() + dTheta/2)); 
				dX = (dF * Math.sin(headingRad)) + (dS * Math.sin(headingRad + (0.5 * Math.PI)));
				dY = (dF * Math.cos(headingRad)) + (dS * Math.cos(headingRad + (0.5 * Math.PI)));
				
				//TODO: REMOVE THESE WHEN TESTING IS OVER.
				SmartDashboard.putNumber("dX", dX);
				SmartDashboard.putNumber("dY", dY);
				
				integrator.add(dX, dY, dTheta);
			}
			
			/*
			 * Setting variables for next run
			 */
			previousTime = currentTime;
			previousHeading = currentHeading;
			previousF = currentF;
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
	
	public NavigationIntegrator navigationIntegrator;
	
	private SpeedController left1, left2;
	private SpeedController right1, right2;
	private SpeedController center;
	
	private IMUAdvanced navx;
	private MovingAverage accelXAverage, accelYAverage;
	
	private Encoder encoderLeft, encoderRight, encoderCenter;
	
	private double leftScale, rightScale, centerScale;
	
	private double headingOffset = 0;
	
	private double angularVelocity = 0; //this is constantly calculated by NavigationThread
	
	private double CHASSIS_WIDTH; //initialized in constructor 
	
	Drive defaultDrive;
	
	double leftEncoderScale = 1, 
		   rightEncoderScale = 1, 
		   centerEncoderScale = 1;
	
	public Chassis ()
	{
		navigationIntegrator = new NavigationIntegrator();
		
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
			
			accelXAverage = new MovingAverage(
					(int) config.get("chassis_AccelXAverage_Size"), 
					(int) config.get("chassis_AccelXAverage_UpdateRate"), 
					() -> {return navx.getWorldLinearAccelX();});
			
			accelYAverage = new MovingAverage(
					(int) config.get("chassis_AccelYAverage_Size"), 
					(int) config.get("chassis_AccelYAverage_UpdateRate"), 
					() -> {return navx.getWorldLinearAccelY();});
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
    	updateEncoderScales();
    	return encoderLeft.getDistance() * leftEncoderScale;
    }
    
   public double getDistanceRight ()
    {
	    updateEncoderScales();
    	return encoderRight.getDistance() * rightEncoderScale;
    }
    
    public double getDistanceCenter ()
    {
    	updateEncoderScales();
    	return encoderCenter.getDistance() * centerEncoderScale;
    }
    
    /*
     * Speed
     */
    public double getSpeedLeft ()
    {
    	updateEncoderScales();
      	return encoderLeft.getRate() * leftEncoderScale;
    }
        
    public double getSpeedRight ()
    {
    	updateEncoderScales();
       	return encoderRight.getRate() * rightEncoderScale;
    }
        
    public double getSpeedCenter ()
    {
    	updateEncoderScales();
      	return encoderCenter.getRate() * centerEncoderScale;
    }
    
    /*
     * Acceleration
     */
    private double accelLowPass = 0.0;
    
    public double getAccelX ()
    {
    	return accelLowPass(accelXAverage.get());
    }
    
    public double getAccelY ()
    {
    	return accelLowPass(accelYAverage.get());
    }
    
    private double accelLowPass (double x)
	{
    	updateAccelLowPass();
		if (Math.abs(x) < accelLowPass)
		{
			return 0;
		}
		return x;
	}
    
    private void updateAccelLowPass ()
    {
    	try
    	{
    		accelLowPass = (double) config.get("chassis_AccelLowPass");
    	}
    	catch (ConfigException e)
    	{
    		logger.severe(e);
    	}
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
    		leftScale = (double) config.get("chassis_LeftScale");
    		rightScale = (double) config.get("chassis_RightScale");
    		centerScale = (double) config.get("chassis_CenterScale");
    	}
    	catch (ConfigException e)
    	{
    		logger.severe(e);
    	}
    }
    
    private void updateEncoderScales ()
    {
    	try
    	{
    		leftEncoderScale = (double) config.get("chassis_LeftEncoderScale");
    		rightEncoderScale = (double) config.get("chassis_RightEncoderScale");
    		centerEncoderScale = (double) config.get("chassis_CenterEncoderScale");
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

