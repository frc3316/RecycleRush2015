/**
 * Robot logger
 */
package org.usfirst.frc.team3316.robot.logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import edu.wpi.first.wpilibj.DriverStation;

public class DBugLogger {
	public static Logger logger;
	private static FileHandler fh;
	//private int state = 0;
	
    public DBugLogger() {
    	logger = Logger.getLogger(DBugLogger.class.getName());
        //CR: make the file log finest and keep the console output, just set it to info.
    	Handler[] handlers = logger.getHandlers();
		for (int i=0; i<handlers.length; i++ ) {
			handlers[i].setLevel( Level.FINEST );
		}
		logger.setLevel(Level.FINEST);
		logger.setUseParentHandlers(false); //disables console output
		
		try {
            //CR: File name cannot have colons (":").
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
		    fh = new FileHandler("/home/lvuser/logs/logFile " + timeStamp +".log");  
		    logger.addHandler(fh);
            //CR: Create new formatter with a single line format which is easily parsable.
		    SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);
	    } 
	    catch (SecurityException e) {  
	        e.printStackTrace();  
	    } 
	    catch (IOException e) {  
	        e.printStackTrace();  
	    }
    }
    

    //CR: Do we need all these levels? decide which do we actually need and add handling with thrown objects
    public void severe(String msg) {
    	this.logger.severe(msg);
    }
    public void warning(String msg) {
    	this.logger.warning(msg);
    }
    public void info(String msg) {
    	this.logger.info(msg);
    }
    public void config(String msg) {
    	this.logger.config(msg);
    }
    public void fine(String msg) {
    	this.logger.fine(msg);
    }
    public void finer(String msg) {
    	this.logger.finer(msg);
    }
    public void finest(String msg) {
    	this.logger.finest(msg);
    }
}
