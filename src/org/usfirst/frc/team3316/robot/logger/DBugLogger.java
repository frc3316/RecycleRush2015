package org.usfirst.frc.team3316.robot.logger;

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
	
	private class DBugFormatter extends Formatter 
	{
	    public String format(LogRecord record) 
	    {
	    	return record.getMillis() + ":" + record.getLevel() + ":" + record.getMessage() + "\n";
	    }

	}
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
			String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		    fh = new FileHandler("/home/lvuser/logs/logFile " + timeStamp +".log"); 
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

    //CR: Do we need all these levels? decide which do we actually need and add handling with thrown objects
    public void severe(String msg) {
    	logger.severe(msg);
    }
    public void info(String msg) {
    	logger.info(msg);
    }
    public void fine(String msg) {
    	logger.fine(msg);
    }
}
