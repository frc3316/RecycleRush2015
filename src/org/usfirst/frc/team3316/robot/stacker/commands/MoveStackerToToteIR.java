package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;


public class MoveStackerToToteIR extends MoveStackerToTote
{
	protected double heightMin, heightMax;
	
	String heightMaxName = "", heightMinName = "";	
	
    public MoveStackerToToteIR(String heightMaxName, String heightMinName)
    {
        requires(Robot.stacker);
        this.heightMaxName = heightMaxName;
        this.heightMinName = heightMinName;
    }
    
    public MoveStackerToToteIR ()
	{
    	this("stacker_HeightToteMaximum", 
				"stacker_HeightToteMinimum");
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
