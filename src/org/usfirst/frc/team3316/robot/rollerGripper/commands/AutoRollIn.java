package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import org.usfirst.frc.team3316.robot.Robot;
import org.usfirst.frc.team3316.robot.config.Config;
import org.usfirst.frc.team3316.robot.config.Config.ConfigException;

/**
 *
 */
public class AutoRollIn extends Roll {
	double left = 0, right = 0;
	double leftScale = 0, rightScale  = 0;
	
    public AutoRollIn() {
        requires(Robot.rollerGripper);
    }

    protected void initialize() {
    	updatesSpeeds();
    }

    protected boolean isFinished() {
        return Robot.rollerGripper.getSwitchGamePiece();
    }

	protected void setSpeeds() {
		
		this.left = left * leftScale;
		this.right = right * rightScale;
	}
	
	private void updatesSpeeds() {
		try
		{
			left = (double) config.get("RollerGripper_Auto_Roll_In_Motor_Speed_Left");
			right = (double) config.get("RollerGripper_Auto_Roll_In_Motor_Speed_Right");
			leftScale = (double) config.get("rollerGripper_LeftScale");
			rightScale = (double) config.get("rollerGripper_RightScale");
			
		}
		catch (ConfigException e)
		{
			logger.severe(e);
		}
	}
}
