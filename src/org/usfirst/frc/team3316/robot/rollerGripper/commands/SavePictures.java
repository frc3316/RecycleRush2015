package org.usfirst.frc.team3316.robot.rollerGripper.commands;

import java.nio.ByteBuffer;

import org.usfirst.frc.team3316.robot.Robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SavePictures extends Command {

    public SavePictures() {}

    protected void initialize() {
    	Image image = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		
		NIVision.IMAQdxGrab(Robot.sensors.getCameraSession(), image, 1);
		
		NIVision.imaqWriteJPEGFile(image,
								   "/home/lvuser/chassisProcessTotes.jpg", 
								   50, 
								   // NIVision.RawData());
								   new NIVision.RawData(ByteBuffer.allocate(0)));
    }

    protected void execute() {}

    protected boolean isFinished() {
        return true;
    }

    protected void end() {}
    
    protected void interrupted() {}
}
