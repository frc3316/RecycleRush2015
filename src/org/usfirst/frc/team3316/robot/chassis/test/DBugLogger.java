package org.usfirst.frc.team3316.robot.chassis.test;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

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
		logger.setUseParentHandlers(true); //disables console output if 'false' is given as a parameter
		
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		    fh = new FileHandler("C:/Logs/logfile-" + timeStamp + ".log"); 
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
    public void severe(Exception e) {
    	StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionStackTrace = sw.toString();
		logger.severe(exceptionStackTrace);
		logger.severe(e.getMessage());
    }
    public void info(String msg) {
    	logger.info(msg);
    }
    public void fine(String msg) {
    	logger.fine(msg);
    }
}
