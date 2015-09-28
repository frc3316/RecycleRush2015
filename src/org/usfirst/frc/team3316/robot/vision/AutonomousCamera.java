package org.usfirst.frc.team3316.robot.vision;

import java.util.Comparator;
import java.util.Vector;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.DriveToYellowTote;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.RGBValue;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutonomousCamera extends Command
{
	DBugLogger logger = Robot.logger;
	boolean finishExecute = false;
	// A structure to hold measurements of a particle
	public class ParticleReport implements Comparator<ParticleReport>,
			Comparable<ParticleReport>
	{
		double PercentAreaToImageArea;
		double Area;
		double ConvexHullArea;
		double BoundingRectLeft;
		double BoundingRectTop;
		double BoundingRectRight;
		double BoundingRectBottom;
		double CenterMassX;
		double CenterMassY;
		public double ToteAngle;
		public double ToteDistance;
		public boolean isTote;
		
		public int compareTo(ParticleReport r)
		{
			return (int) (r.Area - this.Area);
		}

		public int compare(ParticleReport r1, ParticleReport r2)
		{
			return (int) (r1.Area - r2.Area);
		}
	};

	// Structure to represent the scores for the various tests used for target
	// identification
	public class Scores
	{
		double Rectangle;
		double LongAspect;
		double ShortAspect;
		double AreaToConvexHullArea;
	};

	// Images
	int session;
	Image frame;
	Image binaryFrame;
	
	int imaqError;

	// Constants
	NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(24, 49); // Default hue
																// range for
																// yellow tote
	NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(67, 255); // Default
																	// saturation
																	// range for
																	// yellow
																	// tote
	NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(96, 255); // Default
																	// value
																	// range for
																	// yellow
																	// tote
	double AREA_MINIMUM; // Default Area minimum for particle as a
								// percentage of total image area
	double LONG_RATIO = 2.22; // Tote long side = 26.9 / Tote height = 12.1 =
								// 2.22
	double SHORT_RATIO = 1.4; // Tote short side = 16.9 / Tote height = 12.1 =
								// 1.4
	double SCORE_MIN = 75.0; // Minimum score to be considered a tote
	double SCORE_MIN_RECTANGLE; // Minimum score to be considered a tote by ration of rectangle filtering
	
	double RATIO_MIN;
	double RATIO_MAX;
	
	double VIEW_ANGLE; //The view angle of the camera
	
	int X_IMAGE_RES; //The width of the frame
	int Y_IMAGE_RES; //The height of the frame
	int IMAGE_SIZE; // The size of the frame (width * height)
	
	double TARGET_SIZE_SQRT; //A constant for distance computing method
	double OFFSET; //A constant for distance computing method
	
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(
			0, 0, 1, 1);
	Scores scores = new Scores();
	
	public ParticleReport lastReport;
	public AutonomousCamera()
	{
		setRunWhenDisabled(true);
	}

	// Called just before this Command runs the first time
	protected void initialize()
	{
		// Create images
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(
				NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM,
				100.0, 0, 0);

		// Put default values to SmartDashboard so fields will appear
		SmartDashboard.putNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
		SmartDashboard.putNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
		SmartDashboard.putNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
		SmartDashboard.putNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
		SmartDashboard.putNumber("Tote val min", TOTE_VAL_RANGE.minValue);
		SmartDashboard.putNumber("Tote val max", TOTE_VAL_RANGE.maxValue);
		
		// Get constants from the config
		try 
		{
			X_IMAGE_RES = (int) Robot.config.get("AUTONOMOUS_CAMERA_X_IMAGE_RES");
			Y_IMAGE_RES = (int) Robot.config.get("AUTONOMOUS_CAMERA_Y_IMAGE_RES");
			IMAGE_SIZE = X_IMAGE_RES * Y_IMAGE_RES;
		}
		catch (ConfigException e) 
		{
			logger.severe(e);
			finishExecute = true;
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute()
	{
		// Take a frame from the camera
		NIVision.IMAQdxGrab(Robot.sensors.getCameraSession(), frame, 1);
		
		// Get variables/constants from the config
		try 
		{
			SCORE_MIN_RECTANGLE = (double) Robot.config.get("AutonomousCamera_ScoreMinRectangle");
			RATIO_MIN = (double) Robot.config.get("AutonomousCamera_RatioMin");
			RATIO_MAX = (double) Robot.config.get("AutonomousCamera_RatioMax");
			TARGET_SIZE_SQRT = (double) Robot.config.get("AutonomousCamera_TargetSize");
			OFFSET = (double) Robot.config.get("AutonomousCamera_Offset");
			VIEW_ANGLE = (double) Robot.config.get("AutonomousCamera_ViewAngle");
			AREA_MINIMUM = (double)Robot.config.get("AutonomousCamera_AreaMinimum");
		}
		catch (ConfigException e) {
			logger.severe(e);
			finishExecute = true;
		}
		
		// Update threshold values from SmartDashboard. For performance reasons
		// it is recommended to remove this after calibration is finished.
		TOTE_HUE_RANGE.minValue = (int) SmartDashboard.getNumber(
				"Tote hue min", TOTE_HUE_RANGE.minValue);
		TOTE_HUE_RANGE.maxValue = (int) SmartDashboard.getNumber(
				"Tote hue max", TOTE_HUE_RANGE.maxValue);
		TOTE_SAT_RANGE.minValue = (int) SmartDashboard.getNumber(
				"Tote sat min", TOTE_SAT_RANGE.minValue);
		TOTE_SAT_RANGE.maxValue = (int) SmartDashboard.getNumber(
				"Tote sat max", TOTE_SAT_RANGE.maxValue);
		TOTE_VAL_RANGE.minValue = (int) SmartDashboard.getNumber(
				"Tote val min", TOTE_VAL_RANGE.minValue);
		TOTE_VAL_RANGE.maxValue = (int) SmartDashboard.getNumber(
				"Tote val max", TOTE_VAL_RANGE.maxValue);

		// Threshold the image looking for yellow (tote color)
		NIVision.imaqColorThreshold(binaryFrame, frame, 255,
				NIVision.ColorMode.HSV, TOTE_HUE_RANGE, TOTE_SAT_RANGE,
				TOTE_VAL_RANGE);

		// Send particle count to dashboard
		int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Masked particles", numParticles);

		// Send masked image to dashboard to assist in tweaking mask.
		
		//CameraServer.getInstance().setImage(binaryFrame);

		// filter out small particles
		float areaMin = (float) SmartDashboard.getNumber("Area min %",
				AREA_MINIMUM);
		criteria[0].lower = areaMin;
		imaqError = NIVision.imaqParticleFilter4(binaryFrame, binaryFrame,
				criteria, filterOptions, null);

		// Send particle count after filtering to dashboard
		numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Filtered particles", numParticles);

		if (numParticles > 0)
		{
			// Measure particles and sort by particle size
			Vector<ParticleReport> particles = new Vector<ParticleReport>();
			for (int particleIndex = 0; particleIndex < numParticles; particleIndex++)
			{
				ParticleReport par = new ParticleReport();
				par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(
						binaryFrame, particleIndex, 0,
						NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
				par.Area = NIVision.imaqMeasureParticle(binaryFrame,
						particleIndex, 0, NIVision.MeasurementType.MT_AREA);
				par.ConvexHullArea = NIVision.imaqMeasureParticle(binaryFrame,
						particleIndex, 0,
						NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
				par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryFrame,
						particleIndex, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
				par.BoundingRectLeft = NIVision.imaqMeasureParticle(
						binaryFrame, particleIndex, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
				par.BoundingRectBottom = NIVision.imaqMeasureParticle(
						binaryFrame, particleIndex, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
				par.BoundingRectRight = NIVision.imaqMeasureParticle(
						binaryFrame, particleIndex, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
				par.CenterMassX = NIVision.imaqMeasureParticle(
						binaryFrame, particleIndex, 0,
						NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
				par.CenterMassY = NIVision.imaqMeasureParticle(
						binaryFrame, particleIndex, 0,
						NIVision.MeasurementType.MT_CENTER_OF_MASS_Y);
				particles.add(par);
				
			}
			particles.sort(null);

			// This example only scores the largest particle. Extending to score
			// all particles and choosing the desired one is left as an exercise
			// for the reader. Note that the long and short side scores expect a
			// single tote and will not work for a stack of 2 or more totes.
			// Modification of the code to accommodate 2 or more stacked totes
			// is left as an exercise for the reader.
			
			double sizeRatio = ParticleSizeRatio(particles.elementAt(0));
			
			particles.elementAt(0).ToteAngle = computeAngleFromTarget(particles.elementAt(0));
			particles.elementAt(0).ToteDistance = computeDistance(particles.elementAt(0));
			
			scores.Rectangle = RectangleScore(particles.elementAt(0));
			SmartDashboard.putNumber("Rectangle", scores.Rectangle);
			scores.LongAspect = LongSideScore(particles.elementAt(0));
			SmartDashboard.putNumber("Long Aspect", scores.LongAspect);
			scores.ShortAspect = ShortSideScore(particles.elementAt(0));
			SmartDashboard.putNumber("Short Aspect", scores.ShortAspect);
			scores.AreaToConvexHullArea = ConvexHullAreaScore(particles
					.elementAt(0));
			SmartDashboard.putNumber("Convex Hull Area",
					scores.AreaToConvexHullArea);
			SmartDashboard.putNumber("Particle Size Ratio", sizeRatio);
			
			lastReport = particles.elementAt(0);
			
			lastReport.isTote = scores.Rectangle > SCORE_MIN_RECTANGLE && sizeRatio >= RATIO_MIN && sizeRatio <= RATIO_MAX;

			// Send distance and tote status to dashboard. The bounding rect,
			// particularly the horizontal center (left - right) may be useful
			// for rotating/driving towards a tote
			SmartDashboard.putBoolean("IsTote", lastReport.isTote);
			SmartDashboard.putNumber("Tote Distance", lastReport.ToteDistance);
		}
		else
		{
			SmartDashboard.putBoolean("IsTote", false);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished()
	{
		if (finishExecute) 
		{
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end()
	{}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted()
	{}

	// Comparator function for sorting particles. Returns true if particle 1 is
	// larger
	static boolean CompareParticleSizes(ParticleReport particle1,
			ParticleReport particle2)
	{
		// we want descending sort order
		return particle1.PercentAreaToImageArea > particle2.PercentAreaToImageArea;
	}

	/**
	 * Converts a ratio with ideal value of 1 to a score. The resulting function
	 * is piecewise linear going from (0,0) to (1,100) to (2,0) and is 0 for all
	 * inputs outside the range 0-2
	 */
	double ratioToScore(double ratio)
	{
		return (Math.max(0, Math.min(100 * (1 - Math.abs(1 - ratio)), 100)));
	}

	/**
	 * Method to score convex hull area. This scores how "complete" the particle
	 * is. Particles with large holes will score worse than a filled in shape
	 */
	double ConvexHullAreaScore(ParticleReport report)
	{
		return ratioToScore((report.Area / report.ConvexHullArea) * 1.18);
	}
	
	/**
	 * Method to score if the ratio of the particle appears to match the
	 * blocking rectangle.
	 */
	double RectangleScore(ParticleReport report)
	{
		return ratioToScore((report.BoundingRectRight - report.BoundingRectLeft) * (report.BoundingRectBottom - report.BoundingRectTop) / report.Area);
	}

	/**
	 * Method to score if the aspect ratio of the particle appears to match the
	 * long side of a tote.
	 */
	double LongSideScore(ParticleReport report)
	{
		return ratioToScore(((report.BoundingRectRight - report.BoundingRectLeft) / (report.BoundingRectBottom - report.BoundingRectTop))
				/ LONG_RATIO);
	}

	/**
	 * Method to score if the aspect ratio of the particle appears to match the
	 * short side of a tote.
	 */
	double ShortSideScore(ParticleReport report)
	{
		return ratioToScore(((report.BoundingRectRight - report.BoundingRectLeft) / (report.BoundingRectBottom - report.BoundingRectTop))
				/ SHORT_RATIO);
	}
	
	/**
	 * Method to score if height of the blocking rectangle divided by its height appears to match the
	 * size ratio of a tote.
	 */
	double ParticleSizeRatio (ParticleReport report) {
		return (report.BoundingRectBottom - report.BoundingRectTop) / (report.BoundingRectRight - report.BoundingRectLeft);
	}
	
	/**
	 * Method to calculate the angle of the tote in the frame.
	 */
	double computeAngleFromTarget (ParticleReport report)
    {
        double yDiff = (report.CenterMassY - Y_IMAGE_RES / 2);
        double angleToReturn = (yDiff * VIEW_ANGLE) / Y_IMAGE_RES;
        return angleToReturn;
    }
	
	/**
	 * Method to calculate the distance of the tote from the robot.
	 */
	public double computeDistance (ParticleReport report)
    {
        double ConvexHullAreaRatio;
        
        double offset = 1.7890935355724088118454315637414;
        
        ConvexHullAreaRatio = IMAGE_SIZE / report.ConvexHullArea; 
        return (Math.sqrt(ConvexHullAreaRatio) * TARGET_SIZE_SQRT) - OFFSET;
    }

	int photosSaved = 0; //Used to give a unique name for every image.

	/**
	 * Method to save the binary frame.
	 */
	public void saveBinaryFrame()
	{
		NIVision.imaqWriteVisionFile(binaryFrame, "/home/lvuser/Pics/binary_"
				+ photosSaved + ".jpeg", new RGBValue(0, 0, 0, 1));
		photosSaved++;
	}
	
	/**
	 * Method to save the frame.
	 */
	public void saveFrame()
	{
		NIVision.imaqWriteVisionFile(frame, "/home/lvuser/Pics/frame_"
				+ photosSaved + ".jpeg", new RGBValue(0, 0, 0, 1));
		photosSaved++;
	}
}
