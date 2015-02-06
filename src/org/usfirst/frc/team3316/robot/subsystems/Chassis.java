/**
 * Chassis subsystem
 */
package org.usfirst.frc.team3316.robot.subsystems;

import java.util.HashSet;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.Drive;
import org.usfirst.frc.team3316.robot.chassis.commands.TankDrive;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
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
			while (heading < -180)
			{
				heading += 360;
			}
			while (heading > 180)
			{
				heading -= 360;
			}
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
		 * Return change in heading that has been integrated
		 * heading returned is in the range (-180) to (180)
		 */
		public double getHeading ()
		{
			return heading;
		}
	}
	
	/*
	 * Thread that calculates the robot's position in the field
	 * Calculates x, y, and theta delta and adds it to each integrator in the set
	 * Also calculates turning rate in chassis
	 */
	private class NavigationThread extends Thread
	{	
		private HashSet <NavigationIntegrator> integratorSet;
		private double previousTime = 0;
		private double previousHeading = 0;
		
		public NavigationThread ()
		{
			integratorSet = new HashSet <NavigationIntegrator>();
		}
		
		public synchronized void run() 
		{
			while (true)
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
				vF = (encoderLeft.getRate() + encoderRight.getRate()) / 2; //TODO: check this calculation
				
				double vX, vY; //speeds relative to field 
				double headingRad = Math.toRadians(currentHeading);
				vX = (vF * Math.sin(headingRad)) + (vS * Math.sin(headingRad + (0.5 * Math.PI)));
				vY = (vF * Math.cos(headingRad)) + (vS * Math.cos(headingRad + (0.5 * Math.PI)));
				
				/*
				 * Calculates position delta in field axes
				 */
				double dX, dY, dTheta;
				dX = vX * dT;
				dY = vY * dT;
				
				dTheta = currentHeading - previousHeading;
				//Since heading is in the range (-180) to (180), when 
				//completing a full turn dTheta will be an absurdly big value
				//Checks if dTheta is an absurdly big value and fixes it
				if (dTheta > 350) //350 is a big number
				{
					dTheta -= 360;
				}
				if (dTheta < -350) //Math.abs(-350) is another big number
				{
					dTheta += 360;
				}
				
				/*
				 * Adds all of the deltas to each integrator
				 */
				for (NavigationIntegrator integrator : integratorSet)
				{
					integrator.add(dX, dY, dTheta);
				}
				
				/*
				 * Calculates angular velocity(ies)
				 */
				//Calculation from gyro
				angularVelocity = (dTheta)/dT; 
				//Calculation fron encoders
				angularVelocityEncoders = (encoderLeft.getRate() - encoderRight.getRate()) / (CHASSIS_WIDTH);
				angularVelocityEncoders = Math.toDegrees(angularVelocityEncoders); //conversion to (degrees/sec)
				
				/*
				 * Setting variables for next run
				 */
				previousTime = currentTime;
				previousHeading = currentHeading;
				try 
				{
					sleep(5);
				} 
				catch (InterruptedException e) 
				{
					logger.severe(e);
				}
			}
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
	
	private NavigationThread navigationThread;
	
	private VictorSP left;
	private VictorSP right;
	private VictorSP center;
	
	private IMUAdvanced navx;
	
	private Encoder encoderLeft, encoderRight, encoderCenter;
	
	private double leftScale, rightScale, centerScale;
	
	private double headingOffset = 0;
	
	private double angularVelocity = 0, angularVelocityEncoders = 0; //this is constantly calculated by NavigationThread
	
	private double CHASSIS_WIDTH; //initialized in constructor 
	
	Drive defaultDrive;
	
	public Chassis ()
	{
		left = Robot.actuators.chassisMotorControllerLeft;
		right = Robot.actuators.chassisMotorControllerRight;
		center = Robot.actuators.chassisMotorControllerCenter;
		
		navx = Robot.sensors.navx;
		
		encoderLeft = Robot.sensors.chassisEncoderLeft;
		encoderRight = Robot.sensors.chassisEncoderRight;
		encoderCenter = Robot.sensors.chassisEncoderCenter;
		
		try
		{
			CHASSIS_WIDTH = (double)config.get("CHASSIS_WIDTH");
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
		
		navigationThread = new NavigationThread();
		navigationThread.start();
		
		// this should be overwritten by the SDB
		defaultDrive = new TankDrive ();
	}
	
    public void initDefaultCommand() 
    {
       setDefaultCommand(defaultDrive);
    }
    
    public boolean set (double left, double right, double center)
    {
    	updateScales();
    	this.left.set(left*leftScale);
    	this.right.set(right*rightScale);
    	this.center.set(center*centerScale);
    	return true;
    }
    
    public double getHeading ()
    {
    	//TODO: need to check whether its Yaw, Pitch or Roll
    	return navx.getYaw() + headingOffset;
    }
    
    public void setHeadingOffset (double offset)
    {
		//TODO: make SDB set this in game start.
    	this.headingOffset = offset;
    }
    
    public double getAngularVelocity ()
    {
    	return angularVelocity;
    }
    
    public double getAngularVelocityEncoders ()
    {
    	return angularVelocityEncoders;
    }
    
    public double getAccelX ()
    {
    	//TODO: need to check whether its X, Y or Z
    	return navx.getWorldLinearAccelX();
    }
    
    public double getAccelY ()
    {
    	//TODO: need to check whether its X, Y or Z
    	return navx.getWorldLinearAccelY();
    }
    
    //TODO: check whether Z acceleration is necessary
    
    public boolean addNavigationIntegrator (NavigationIntegrator integrator)
    {
    	return navigationThread.addIntegrator(integrator);
    }
    
    public boolean removeNavigationIntegrator (NavigationIntegrator integrator)
    {
    	return navigationThread.removeIntegrator(integrator);
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

