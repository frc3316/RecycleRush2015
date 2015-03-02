package org.usfirst.frc.team3316.robot.chassis.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.utils.Point;
import org.usfirst.frc.team3316.robot.utils.Utils;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class WiggleWiggle extends Command 
{    
	private double [][] voltageValues = new double [][]
		    {
		        {0, 5, 10, 15, 20, 25},
		        {0, 1, -1, 1, -1, 0}
		    };
	
	private int counter;
	
    public  WiggleWiggle() 
    {
    	requires(Robot.chassis);
    }
		
    protected void initialize() 
    {
    	counter = 0;
    }
    	
	protected void execute() 
	{
		double speedCenter = Utils.valueInterpolation(counter, voltageValues);
		Robot.chassis.set(0, 0, speedCenter);
		counter++;
	}

    protected boolean isFinished() 
    {
        return (counter >= voltageValues [0][(voltageValues[0].length) - 1]); //last cell in first row
    }

	protected void end() {}

	protected void interrupted() {}
}
