package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.Roll;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.RollJoystick;
import org.usfirst.frc.team3316.robot.utils.GamePieceCollected;
import org.usfirst.frc.team3316.robot.utils.MovingAverage;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RollerGripper extends Subsystem 
{	
	Config config = Robot.config;
	DBugLogger logger = Robot.logger;
	
	//TODO: fix all of the private names to not start with gripper
	private SpeedController gripperLeft, gripperRight;
	
	private AnalogInput gripperGPIR;
	private MovingAverage gpDistanceAverage;
	
	private DigitalInput gripperSwitchGP;
	
	private double leftScale, rightScale;
	
	// Variables for getGamePieceCollected(). They are set in updateDistanceVariables().
	private double toteDistanceMin, 
				   toteDistanceMax,
				   containerDistanceMin, 
				   containerDistanceMax,
				   somethingDistanceThreshold,
				   unsureDistanceThreshold;
	
	private Roll defaultRoll;
	
    public RollerGripper () 
    {
    	gripperLeft = Robot.actuators.rollerGripperMotorControllerLeft;
    	gripperRight = Robot.actuators.rollerGripperMotorControllerRight;
    	
    	gripperGPIR = Robot.sensors.rollerGripperGPIR;
    	gpDistanceAverage = new MovingAverage(20, 20, () -> {return 1 / gripperGPIR.getVoltage();} );
    	
    	gripperSwitchGP = Robot.sensors.rollerGripperSwitchGP;
    }
    
    public void timerInit ()
    {
    	gpDistanceAverage.timerInit();
    }

    public void initDefaultCommand() 
    {
    	defaultRoll = new RollJoystick();
    	setDefaultCommand(defaultRoll);
    }
    
    public boolean set (double speedLeft, double speedRight) 
    {
    	updateScales();
     	gripperLeft.set(speedLeft*leftScale);
    	gripperRight.set(speedRight*rightScale);
    	return true;
    }
    
    //TODO: fix name to be getIRGPDistance
    public double getGPIRDistance ()
    {
    	return gpDistanceAverage.get();
    }
    
    public boolean getSwitchGamePiece ()
    {
    	return gripperSwitchGP.get();
    }
    
    /**
     * Returns which gamepiece there is in the roller gripper (if any)
     * @return Tote for tote, Container for container, Unsure if there 
     * is something in but can't figure out which and none if empty 
     */
    public GamePieceCollected getGamePieceCollected ()
    {
    	updateDistanceVariables();
    	
    	double gpDistance = getGPIRDistance();
    	boolean gpSwitch = getSwitchGamePiece();
    	
    	if (gpSwitch)
    	{
    		if (gpDistance > toteDistanceMin && gpDistance < toteDistanceMax)
    		{
    			return GamePieceCollected.Tote;
    		}
    		else if (gpDistance > containerDistanceMin && gpDistance < containerDistanceMax)
    		{
    			return GamePieceCollected.Container;
    		}
    		else
    		{
    			return GamePieceCollected.Something;
    		}
    	}
    	else
    	{
    		if (gpDistance < somethingDistanceThreshold)
    		{
    			return GamePieceCollected.Something;
    		}
    		else if (gpDistance < unsureDistanceThreshold)
    		{
    			return GamePieceCollected.Unsure;
    		}
    		else
    		{
    			return GamePieceCollected.None;
    		}
    	}
    }
    
    private void updateDistanceVariables () 
    {
    	try 
    	{
			toteDistanceMin = (double) config.get("rollerGripper_ToteDistanceMinimum");
			toteDistanceMax = (double) config.get("rollerGripper_ToteDistanceMaximum");
			
			containerDistanceMin = (double) config.get("rollerGripper_ContainerDistanceMinimum");
			containerDistanceMax = (double) config.get("rollerGripper_ContainerDistanceMaximum");
			
			somethingDistanceThreshold = (double) config.get("rollerGripper_SomethingDistanceThreshold");
			unsureDistanceThreshold = (double) config.get("rollerGripper_UnsureDistanceThreshold"); 
		} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
	}
    
    private void updateScales ()
    {
    	try
    	{
    		leftScale = (double)config.get("rollerGripper_LeftScale");
    		rightScale = (double)config.get("rollerGripper_RightScale");
    	}
    	catch (ConfigException e)
    	{
    		logger.severe(e);
    	}
    }
    
    private void printTheTruth()
    {
    	System.out.println("Vita is the Melech!!");
    }
}

