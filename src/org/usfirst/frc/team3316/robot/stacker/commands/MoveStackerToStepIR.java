package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;


public class MoveStackerToStepIR extends MoveStackerToStep
{
	protected double heightMin, heightMax;
	
	String heightMaxName = "", heightMinName = "";	
	
    public MoveStackerToStepIR(String heightMaxName, String heightMinName)
    {
        super();
        this.heightMaxName = heightMaxName;
        this.heightMinName = heightMinName;
    }
    
    public MoveStackerToStepIR ()
	{
    	this("stacker_HeightStepMaximum", 
				"stacker_HeightStepMinimum");
	}

    protected boolean isFinished()
    {
    		updateHeightRange();
    		double currentHeight = Robot.stacker.getHeight();
    		return (currentHeight > heightMin) && (currentHeight < heightMax);
    }
    
    protected void updateHeightRange ()
    {
    	try 
    	{
			heightMax = (double) config.get(heightMaxName);
			heightMin = (double) config.get(heightMinName);
		} 
    	catch (ConfigException e) 
    	{
			logger.severe(e);
		}
    }
}
