/**
 * Chassis subsystem
 */
package org.usfirst.frc.team3316.robot.subsystems;

import java.util.HashSet;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.Drive;
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
	 * An object that is passed to navigationThread for integration
	 */
	public static class NavigationIntegrator
	{
		private double x = 0, y = 0, theta = 0;
		
		public void add (double dX, double dY, double dTheta)
		{
			this.x += dX;
			this.y += dY;
			this.theta += dTheta;
		}
		
		public double getX ()
		{
			return x;
		}
		
		public double getY ()
		{
			return y;
		}
		
		public double getTheta ()
		{
			return theta;
		}
		
		public void reset ()
		{
			x = 0;
			y = 0;
			theta = 0;
		}
	}
	
	/*
	 * Thread that calculates the robot's position in the field
	 * Calculates position delta and adds it to each integrator in the set
	 * Also calculates turning rate
	 */
	private class NavigationThread extends Thread
	{	
		private HashSet <NavigationIntegrator> integratorSet;
		private double previousTime = 0;
		
		public void run() 
		{
			double currentTime = System.currentTimeMillis();
			double dT = currentTime - previousTime;
			double heading = Math.toRadians(getHeading());
			
			/*
			 * Calculates change in heading
			 */
			//TODO: implement turning rate calculation
			
			/*
			 * Calculates speeds in field axes
			 */
			double vS, vF; //speeds relative to the robot (forward and sideways)
			vS = encoderCenter.getRate();
			//TODO: check this calculation
			vF = (encoderLeft.getRate() + encoderRight.getRate())/2;
			
			double vX, vY; //speeds relative to field 
			vX = vF*Math.sin(heading) + vS*Math.sin(heading+.5);
			vY = vF*Math.cos(heading) + vS*Math.cos(heading+.5);
			
			/*
			 * Calculates position delta in field axes 
			 */
			double dX, dY, dTheta;
			dX = vX*dT;
			dY = vY*dT;
			
			for (NavigationIntegrator integrator : integratorSet)
			{
				integrator.add(dX, dY, 0);
			}
			
			try 
			{
				previousTime = currentTime;
				sleep(5);
			} 
			catch (InterruptedException e) 
			{
				logger.severe(e);
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
		
		navigationThread = new NavigationThread();
		navigationThread.start();
		
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
    
    public boolean addNavigationIntegrator (NavigationIntegrator integrator)
    {
    	return navigationThread.addIntegrator(integrator);
    }
    
    public boolean removeNavigationIntegrator (NavigationIntegrator integrator)
    {
    	return navigationThread.removeIntegrator(integrator);
    }
    
    //not sure if Z acceleration is necessary
    //public double getAccelZ () {}
}

