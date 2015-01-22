/**
 * Chassis subsystem
 */
package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.chassis.commands.Drive;
import org.usfirst.frc.team3316.robot.chassis.commands.StrafeDrive;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Chassis extends Subsystem 
{
	private VictorSP left;
	private VictorSP right;
	private VictorSP center;
	
	Drive defaultDrive = new StrafeDrive ();
	
	public Chassis ()
	{
		left = Robot.actuators.chassisLeft;
		right = Robot.actuators.chassisRight;
		center = Robot.actuators.chassisCenter;
	}
	
    public void initDefaultCommand() 
    {
        setDefaultCommand(defaultDrive);
    }
    
    public boolean set (double left, double right, double center)
    {
    	this.left.set(-left);
    	this.right.set(right);
    	this.center.set(center);
    	return true;
    }
    
    public double getHeading ()
    {
    	//should return gyro or magnetometer reading
    	return 0;
    }
    
    public double getAngularVelocity ()
    {
    	//should return gyro reading
    	return 0;
    }
}

