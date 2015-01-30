package org.usfirst.frc.team3316.robot.chassis.test;

import java.util.HashSet;

import org.usfirst.frc.team3316.robot.subsystems.Chassis.NavigationIntegrator;

public class Robot 
{
	static double heading;
	static Encoder encoderLeft, encoderRight, encoderCenter;
	
	static double angularVelocity;
	
	public static double getHeading ()
	{
		return heading;
	}
	
	/*
	 * An object that is passed to navigationThread for integration
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
	private static class NavigationThread extends Thread
	{	
		private HashSet <NavigationIntegrator> integratorSet;
		private double previousTime = 0;
		private double previousHeading = 0;
		
		public NavigationThread ()
		{
			integratorSet = new HashSet <NavigationIntegrator>();
		}
		
		public void run() 
		{
			/*
			 * Variable init
			 */
			double currentTime = System.currentTimeMillis();
			//double dT = (currentTime - previousTime)/1000; //conversion to seconds
			double dT = 0.005; //for testing
			double currentHeading = getHeading();
			/*
			 * Calculates speeds in field axes
			 */
			double vS, vF; //speeds relative to the robot (forward and sideways)
			vS = encoderCenter.getRate();
			vF = (encoderLeft.getRate() + encoderRight.getRate())/2; //TODO: check this calculation
			
			double vX, vY; //speeds relative to field 
			vX = vF*Math.sin(Math.toRadians(currentHeading)) + vS*Math.sin(Math.toRadians(currentHeading)+.5);
			vY = vF*Math.cos(Math.toRadians(currentHeading)) + vS*Math.cos(Math.toRadians(currentHeading)+.5);
			
			/*
			 * Calculates position delta in field axes
			 */
			double dX, dY, dTheta;
			dX = vX*dT;
			dY = vY*dT;
			
			dTheta = currentHeading - previousHeading;
			//Since heading is in the range (-180) to (180), when 
			//completing a full turn dTheta will be an absurdly big value
			//Checks if dTheta is an absurdly big value and fixes it
			if (dTheta > 350) //350 is a big number
			{
				dTheta -= 360;
			}
			if (dTheta < -350) //-350 is a big a number
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
			 * Calculates angular velocity
			 */
			angularVelocity = (dTheta)/dT;
			
			/*
			 * Logging
			 */
			logger.fine("vX = " + String.format("%.3f", vX) + 
						", vY = " + String.format("%.3f", vY) + 
						", dTheta = " + String.format("%f", dTheta) +
						", angularVelocity " + String.format("%.3f", angularVelocity));
			
			for (NavigationIntegrator integrator : integratorSet)
			{
				logger.fine("Integrator X = " + String.format("%.3f", integrator.getX()) +
							", Integrator Y = " + String.format("%.3f", integrator.getY()) + 
							", Integrator Heading = " + String.format("%.3f", integrator.getHeading()));
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
	
	public static DBugLogger logger = new DBugLogger();
	public static Joystick joystickLeft, joystickRight;
	
	public static void main (String[]args)
	{
		joystickLeft = new Joystick();
		joystickRight = new Joystick();
		encoderLeft = new Encoder();
		encoderRight = new Encoder();
		encoderCenter = new Encoder();
		
		logger.fine("Robot Oriented Navigation Test");
		logger.fine("Navigation Thread and Integrator test");
		
		NavigationIntegrator integrator;
		integrator = new NavigationIntegrator();
		
		NavigationThread thread = new NavigationThread();
		logger.fine(String.valueOf(thread.addIntegrator(integrator)));
		thread.start();
		
		for (int g = 0; g <= 54; g++)
		{
			heading = (double)g*7.5;
			for (int h = -3; h <= 3; h++)
			{
				encoderCenter.setRate((double)0.75*h);
				for (int i = -3; i <= 3; i++)
				{
					encoderLeft.setRate((double)0.75*i);
					for (int j = -3; j <= 3; j++)
					{
						encoderRight.setRate((double)0.75*j);
						
						logger.fine("Heading = " + String.format("%.1f", heading) + 
									", Center Rate = " + String.format("%f", encoderCenter.getRate()) +
									", Left Rate = " + String.format("%f", encoderLeft.getRate()) + 
									", Right Rate = " + String.format("%f", encoderRight.getRate()));
						
						thread.run();
					}
				}
			}
		}
	}
}