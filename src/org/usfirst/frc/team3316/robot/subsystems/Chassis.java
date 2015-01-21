/**
 * Chassis subsystem
 */
package org.usfirst.frc.team3316.robot.subsystems;

import org.usfirst.frc.team3316.robot.commands.chassis.Drive;
import org.usfirst.frc.team3316.robot.commands.chassis.TankDrive;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Chassis extends Subsystem 
{
	private VictorSP left;
	private VictorSP right;
	private VictorSP center;
	
	Drive defaultDrive = new TankDrive ();
	
	public Chassis ()
	{
		//left = Actuators.chassisLeft
		//right = Actuators.chassisRight
		//center = Actuators.chassisCenter
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
}

