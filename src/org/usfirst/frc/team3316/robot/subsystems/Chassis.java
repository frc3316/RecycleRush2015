/**
 * Chassis subsystem
 */
package org.usfirst.frc.team3316.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Chassis extends Subsystem 
{
	private VictorSP left = new VictorSP(0);
	private VictorSP right = new VictorSP(1);
	private VictorSP center = new VictorSP(2);
	
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public boolean set (double left, double right)
    {
    	this.left.set(left);
    	this.right.set(right);
    	this.center.set(0);
    	return true;
    }
    
    public boolean set (double left, double right, double center)
    {
    	this.left.set(left);
    	this.right.set(right);
    	this.center.set(center);
    	return true;
    }
}

