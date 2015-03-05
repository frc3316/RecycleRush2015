/**
 * Chassis subsystem
 */
package org.usfirst.frc.team3316.robot.subsystems;

import java.util.Comparator;
import java.util.HashSet;
import java.util.TimerTask;
import java.util.Vector;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.Drive;
import org.usfirst.frc.team3316.robot.chassis.commands.FieldOrientedDrive;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import com.kauailabs.nav6.frc.IMUAdvanced;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.Range;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
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
			//Added for testing
			SmartDashboard.putNumber("vS", vS);
			SmartDashboard.putNumber("vF", vF);
			
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
				SmartDashboard.putNumber("vX", vX);
				SmartDashboard.putNumber("vY", vY);
				
				double dX, dY;
				dX = vX * dT;
				dY = vY * dT;
				
				SmartDashboard.putNumber("dX", dX);
				SmartDashboard.putNumber("dY", dY);
				
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
	
	public NavigationIntegrator navigationIntegrator;
	
	private SpeedController left1, left2;
	private SpeedController right1, right2;
	private SpeedController center;
	
	private IMUAdvanced navx;
	
	private Encoder encoderLeft, encoderRight, encoderCenter;
	
	private double leftScale, rightScale, centerScale;
	
	private double headingOffset = 0;
	
	private double angularVelocity = 0, angularVelocityEncoders = 0; //this is constantly calculated by NavigationThread
	
	private double CHASSIS_WIDTH; //initialized in constructor 
	
	Drive defaultDrive;
	
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
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
	
	public void timerInit ()
	{
		navigationTask = new NavigationTask();
		Robot.timer.schedule(navigationTask, 0, 10);
	}
	
    public void initDefaultCommand() 
    {
    	defaultDrive = new FieldOrientedDrive();
    	setDefaultCommand(defaultDrive);
    }
    
    public boolean set (double left, double right, double center)
    {
    	updateScales();
    	
    	this.left1.set (left *leftScale);
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
    
    public double getAngularVelocityEncoders ()
    {
    	return angularVelocityEncoders;
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
    
    /*
     * Camera
     */
    private class chassisVision
    {
    	private NIVision.Range H_Range = new NIVision.Range(24, 49);
    	private NIVision.Range S_Range = new NIVision.Range(67, 255);
    	private NIVision.Range V_Range = new NIVision.Range(49, 255);
    	
    	public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>{
			double PercentAreaToImageArea;
			double Area;
			double ConvexHullArea;
			double BoundingRectLeft;
			double BoundingRectTop;
			double BoundingRectRight;
			double BoundingRectBottom;
			
			public int compareTo(ParticleReport r)
			{
				return (int)(r.Area - this.Area);
			}
			
			public int compare(ParticleReport r1, ParticleReport r2)
			{
				return (int)(r1.Area - r2.Area);
			}
		};
		
    	public void processTotes ()
        {
    		DBugLogger logger = Robot.logger;
        	if (Robot.sensors.isCameraFound())
        	{
        		Image image = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
        		
        		logger.fine("Image taken, actual buffer number: " + 
        				NIVision.IMAQdxGrab(Robot.sensors.getCameraSession(), image, 1));
        		
        		Image binaryImage = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
        		
        		NIVision.imaqWriteJPEGFile(binaryImage, 
        								   "/home/lvuser/chassisProcessTotes.jpg", 
        								   50, 
        								   new NIVision.RawData());
        		
        		NIVision.imaqReadFile(image, "/home/lvuser/chassisProcessTotes.jpg");
				
        		NIVision.imaqColorThreshold(binaryImage, 
											image, 
											255, 
											NIVision.ColorMode.HSV, 
											H_Range, 
											S_Range, 
											V_Range);

        		int numParticles = NIVision.imaqCountParticles(binaryImage, 1);

				if(numParticles > 0)
				{
					Vector<ParticleReport> particles = new Vector<ParticleReport>();
					for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
					{
						ParticleReport par = new ParticleReport();
						par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(binaryImage, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
						par.Area = NIVision.imaqMeasureParticle(binaryImage, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
						par.ConvexHullArea = NIVision.imaqMeasureParticle(binaryImage, particleIndex, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
						par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryImage, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
						par.BoundingRectLeft = NIVision.imaqMeasureParticle(binaryImage, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
						par.BoundingRectBottom = NIVision.imaqMeasureParticle(binaryImage, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
						par.BoundingRectRight = NIVision.imaqMeasureParticle(binaryImage, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
						particles.add(par);
					}
					particles.sort(null);
					
					ParticleReport report = particles.firstElement(); //biggest particle?
					
					double normalizedWidth, targetWidth;
					NIVision.GetImageSizeResult size;

					size = NIVision.imaqGetImageSize(image);
					normalizedWidth = 2*(report.BoundingRectRight - report.BoundingRectLeft)/size.width;
					targetWidth = 26.0;

					logger.info("Distance: " + targetWidth/(normalizedWidth*12*Math.tan(60*Math.PI/(180*2))));
					
				}
				
				Timer.delay(0.005);	// wait for a motor update time
        	}
        }
    }
}

