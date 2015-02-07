package org.usfirst.frc.team3316.robot.chassis.test;


import java.util.HashSet;

public class Robot 
{
	static double heading;
	static Encoder encoderLeft, encoderRight, encoderCenter;
	
	static double angularVelocity;
	static double angularVelocityEncoders;
	
	static final double CHASSIS_WIDTH = 0.5322;
	
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
			double dT = 0.01; //for testing
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
			 * Calculates angular velocity(ies)
			 */
			//Calculation from gyro
			angularVelocity = (dTheta)/dT; 
			//Calculation fron encoders
			angularVelocityEncoders = (encoderLeft.getRate() - encoderRight.getRate()) / (CHASSIS_WIDTH);
			angularVelocityEncoders = Math.toDegrees(angularVelocityEncoders); //conversion to (degrees/sec)
			
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
				
				logger.fine(integrator.toString());
				
				logger.fine("vX = " + String.format("%.3f", vX) + 
						", vY = " + String.format("%.3f", vY) + 
						", dTheta = " + String.format("%f", dTheta) +
						", angularVelocity " + String.format("%.3f", angularVelocity) + 
						", angularVelocityEncoders " + String.format("%.3f", angularVelocityEncoders));
				
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
		
		NavigationIntegrator integrator1, integrator2, integrator3;
		integrator1 = new NavigationIntegrator();
		integrator2 = new NavigationIntegrator();
		integrator3 = new NavigationIntegrator();
		
		NavigationThread thread = new NavigationThread();
		
		encoderCenter.setRate(0.25);
		encoderLeft.setRate(1.5);
		encoderRight.setRate(1.5);
		heading = 0;
		
		logger.fine(String.valueOf(thread.addIntegrator(integrator1)));
		
		for (int h = 0; h < 99; h++)
		{
			logger.fine("Heading = " + String.format("%.1f", heading) + 
						", Center Rate = " + String.format("%f", encoderCenter.getRate()) +
						", Left Rate = " + String.format("%f", encoderLeft.getRate()) + 
						", Right Rate = " + String.format("%f", encoderRight.getRate()));
			
			thread.run();
		}
		
		encoderCenter.setRate(1);
		encoderLeft.setRate(1);
		encoderRight.setRate(1);
		heading = 45;
		
		thread.run();
		
		logger.fine(String.valueOf(thread.addIntegrator(integrator2)));
		
		for (int h = 0; h < 99; h++)
		{
			logger.fine("Heading = " + String.format("%.1f", heading) + 
						", Center Rate = " + String.format("%f", encoderCenter.getRate()) +
						", Left Rate = " + String.format("%f", encoderLeft.getRate()) + 
						", Right Rate = " + String.format("%f", encoderRight.getRate()));
			
			thread.run();
		}
		
		encoderCenter.setRate(0.5);
		encoderLeft.setRate(0);
		encoderRight.setRate(0);
		heading = 0;
		
		thread.run();
		
		logger.fine(String.valueOf(thread.addIntegrator(integrator3)));
		
		for (int h = 0; h < 100; h++)
		{
			logger.fine("Heading = " + String.format("%.1f", heading) + 
						", Center Rate = " + String.format("%f", encoderCenter.getRate()) +
						", Left Rate = " + String.format("%f", encoderLeft.getRate()) + 
						", Right Rate = " + String.format("%f", encoderRight.getRate()));
			
			thread.run();
		}
	}
}