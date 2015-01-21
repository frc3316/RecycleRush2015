package org.usfirst.frc.team3316.robot.chassis.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.Formatter;
import java.util.Calendar;

public class DBugLogger {
	public static Logger logger;
	private static FileHandler fh;
	
	private class DBugFormatter extends Formatter {

	    @Override
	    public String format(LogRecord record) {
	    	return record.getMillis() + ":" + record.getLevel() + ":" + record.getMessage() + "\n";
	    }

	}
	//private int state = 0;
	
    public DBugLogger() {
    	logger = Logger.getLogger(DBugLogger.class.getName());
    	Handler[] handlers = logger.getHandlers();
		for (int i=0; i<handlers.length; i++ ) {
			handlers[i].setLevel( Level.FINEST );
		}
		logger.setLevel(Level.FINEST);
		logger.setUseParentHandlers(false); //disables console output
		
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		    fh = new FileHandler("C:/Logs/" + timeStamp + "_logFile.log");  
		    logger.addHandler(fh);
		    DBugFormatter formatter = new DBugFormatter();
	        fh.setFormatter(formatter);
	    } 
	    catch (SecurityException e) {  
	        e.printStackTrace();  
	    } 
	    catch (IOException e) {  
	        e.printStackTrace();  
	    }
    }
    
    public void severe(String msg) {
    	logger.severe(msg);
    }
    public void warning(String msg) {
    	logger.warning(msg);
    }
    public void info(String msg) {
    	logger.info(msg);
    }
    public void config(String msg) {
    	logger.config(msg);
    }
    public void fine(String msg) {
    	logger.fine(msg);
    }
    public void finer(String msg) {
    	logger.finer(msg);
    }
    public void finest(String msg) {
    	logger.finest(msg);
    }
}
