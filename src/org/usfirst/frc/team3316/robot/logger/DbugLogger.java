/**
 * Robot logger
 */
package org.usfirst.frc.team3316.robot.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DbugLogger {
	private static Logger logger;
	private static FileHandler fh;
	//private int state = 0;
	
    public DbugLogger() {
    	logger = Logger.getLogger(DbugLogger.class.getName());
    	Handler[] handlers = logger.getHandlers();
		for (int i=0; i<handlers.length; i++ ) {
			handlers[i].setLevel( Level.FINEST );
		}
		logger.setLevel(Level.FINEST);
		logger.setUseParentHandlers(false); //disables console output
		try {
		    fh = new FileHandler("/home/lvuser/logs/MyLogFile.log");  
		    logger.addHandler(fh);
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
    
    public void log(String log) {
    	logger.info(log);
    }
}
