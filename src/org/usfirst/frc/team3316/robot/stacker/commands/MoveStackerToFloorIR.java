package org.usfirst.frc.team3316.robot.stacker.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;


public class MoveStackerToFloorIR extends MoveStackerToFloor
{
	protected double heightMin, heightMax;
	
	String heightMaxName = "", heightMinName = "";	
	
    public MoveStackerToFloorIR(String heightMaxName, String heightMinName)
    {
        super();
        this.heightMaxName = heightMaxName;
        this.heightMinName = heightMinName;
    }
    
    public MoveStackerToFloorIR ()
	{
		this("stacker_HeightFloorMaximum", 
				"stacker_HeightFloorMinimum");
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
