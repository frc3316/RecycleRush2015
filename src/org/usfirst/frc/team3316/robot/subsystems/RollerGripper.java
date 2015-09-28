package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;
import org.usfirst.frc.team3316.robot.logger.DBugLogger;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.Roll;
import org.usfirst.frc.team3316.robot.rollerGripper.commands.RollJoystick;
import org.usfirst.frc.team3316.robot.utils.GamePieceCollected;
import org.usfirst.frc.team3316.robot.utils.MovingAverage;
import org.usfirst.frc.team3316.robot.utils.StackerPosition;

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
	
	private DigitalInput switchRight;
	private DigitalInput switchLeft;
	
	private double leftScale, rightScale;
	
	private Roll defaultRoll;
	
    public RollerGripper () 
    {
    	gripperLeft = Robot.actuators.rollerGripperMotorControllerLeft;
    	gripperRight = Robot.actuators.rollerGripperMotorControllerRight;
    	
    	gripperGPIR = Robot.sensors.rollerGripperGPIR;
    	gripperSwitchGP = Robot.sensors.rollerGripperSwitchGP;
    	
    	switchRight = Robot.sensors.stackerSwitchRatchetRight;
    	switchLeft = Robot.sensors.stackerSwitchRatchetLeft;

    	try
    	{
    		gpDistanceAverage = new MovingAverage(
    				(int) config.get("rollerGripper_GPDistanceAverage_Size"), 
    				(int) config.get("rollerGripper_GPDistanceAverage_UpdateRate"), 
    				() -> {return 1 / gripperGPIR.getVoltage();} );
    	}
    	catch (ConfigException e)
    	{
    		logger.severe(e);
    	}
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
    
    public double getGPDistance ()
    {
    	return gpDistanceAverage.get();
    }
    
    public boolean getSwitchRight ()
    {
    	return switchRight.get();
    }
    
    public boolean getSwitchLeft ()
    {
    	return switchLeft.get();
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
    	boolean gpSwitch = getSwitchGamePiece();
    	boolean gpSwitchRight = getSwitchRight();
    	boolean gpSwitchLeft = getSwitchLeft();
    	
    	if (!gpSwitch) 
    	{
    		return GamePieceCollected.None;
    	}
    	
    	else if(Robot.stacker.getPosition() == StackerPosition.Floor) 
    	{
	    	if (!gpSwitchRight && !gpSwitchLeft) 
	    	{
	    		return GamePieceCollected.Container;
	    	}
	    	else 
	    	{
	    		return GamePieceCollected.Tote;
	    	}
    	}
    	else 
    	{
    		return GamePieceCollected.Something;
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
}

