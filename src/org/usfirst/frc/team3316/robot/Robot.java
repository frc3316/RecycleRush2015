/**
 * Le robot
 */
package org.usfirst.frc.team3316.robot;

import java.util.Timer;

import org.usfirst.frc.team3316.robot.sequences.AutonomousDriveForward;
import org.usfirst.frc.team3316.robot.sequences.AutonomousNone;
import org.usfirst.frc.team3316.robot.subsystems.Anschluss;
import org.usfirst.frc.team3316.robot.subsystems.Chassis;
import org.usfirst.frc.team3316.robot.subsystems.Stacker;
import org.usfirst.frc.team3316.robot.subsystems.RollerGripper;
import org.usfirst.frc.team3316.robot.vision.AutonomousCamera;
import org.usfirst.frc.team3316.robot.chassis.heading.SetHeadingSDB;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.humanIO.Joysticks;
import org.usfirst.frc.team3316.robot.humanIO.SDB;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.robotIO.Actuators;
import org.usfirst.frc.team3316.robot.robotIO.Sensors;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot
{
    public static Command autonDriveForward, autonNone;
    public static AutonomousCamera autonCamera;
    SendableChooser autonChooser;
    
    public static Config config;
    public static DBugLogger logger;
    public static Timer timer;
    /*
     * Human IO
     */
    public static Joysticks joysticks;
    public static SDB sdb;
    /*
     * Robot IO
     */
    public static Actuators actuators;
    public static Sensors sensors;
    
    /*
     * Subsystems
     */
	public static Chassis chassis;
	public static Anschluss anschluss;
	public static Stacker stacker;
	public static RollerGripper rollerGripper;
	
	/*
	 * Compressor
	 */
	public static Compressor comp;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() 
    {
    	/*
    	 * Above all else
    	 */
    	logger = new DBugLogger();
    	config = new Config();
    	
    	/*
    	 * Human IO (that does not require subsystems)
    	 */
    	joysticks = new Joysticks();
    	
    	/*
    	 * Robot IO
    	 */
    	actuators = new Actuators();
    	sensors = new Sensors();
    	
    	/*
    	 * Subsystems
    	 */
    	chassis = new Chassis();
		anschluss = new Anschluss();
    	rollerGripper = new RollerGripper();
    	stacker = new Stacker();
    	
    	/*
    	 * Compressor
    	 */
    	comp = new Compressor();
    	
    	/*
    	 * Human IO (that requires subsystems)
    	 */
    	joysticks.initButtons();
    	sdb = new SDB();
    	
    	/*
    	 * Timer
    	 */
    	
    		timer = new Timer();
    		chassis.timerInit();
    		stacker.timerInit();
    		rollerGripper.timerInit();
    		sdb.timerInit();
    	
    	/*
    	 * Pre-match Init
    	 */
    	(new SetHeadingSDB()).start();
    	
    	autonDriveForward = new AutonomousDriveForward();
    	autonNone = new AutonomousNone();
    	autonCamera = new AutonomousCamera();
    	
    	autonChooser = new SendableChooser();
    	autonChooser.addDefault("None", autonNone);
    	autonChooser.addObject("Drive Forward", autonDriveForward);
    	
    	SmartDashboard.putData("Auton Chooser", autonChooser);
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() 
    {
    	if (autonChooser != null && autonChooser.getSelected() != null)
    	{
        	((Command) autonChooser.getSelected()).start();
    	}
    	autonCamera.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() 
    {
    	if (autonChooser != null && autonChooser.getSelected() != null)
    	{
    		((Command) autonChooser.getSelected()).cancel();
    	}
    }
    
    /**
     * * This function is called when the disabled button is hit.		
     * You can use it to reset subsystems before shutting down.		
     */		
    public void disabledInit() {}		
		
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
