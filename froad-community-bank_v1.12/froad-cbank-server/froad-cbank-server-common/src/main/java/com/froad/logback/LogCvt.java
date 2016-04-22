package com.froad.logback;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

import com.froad.Constants;


/**
 *  logback 日志封装
  * @ClassName: LogCvt
  * @Description: TODO
  * @author share
  * @create-date 2012-10-9 上午09:27:30
  * @modify-date 2012-10-9 上午09:27:30
*
 */
public class LogCvt {
	public final static String NEWLINE = System.getProperty("line.separator", "\n"); // 

	private static final Logger logger = LoggerFactory.getLogger("com.froad.logback");
	static {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(lc);
		lc.reset();
		try {
			//configurator.doConfigure("logConfig.xml");
			String path = System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+"logConfig.xml";
			configurator.doConfigure(new File(path));
		} catch (JoranException e) {
			e.printStackTrace();
		}
		StatusPrinter.print(lc.getStatusManager());
	}

	public Logger getLogger() {
		return logger;
	}

	// --------------------------------------------LogBack
	// Info----------------------------------
	

	
	// Log a message at the INFO level.
	public static void info(String msg) {
		logger.info(msg);
	}
	
	
	// Log an exception (throwable) at the INFO level with an accompanying
	// message.
	public static void info(String msg, Throwable t) {
		logger.info(msg, t);
	}
	
	// Log a message at the DEBUG level.
	public static void debug(String msg) {
		logger.debug(msg);
	}
	
	// Log an exception (throwable) at the DEBUG level with an accompanying
	// message.
	public static void debug(String msg, Throwable t) {
		logger.debug(msg,t);
	}
	
	// Log a message at the WARN level.
	public static void warn(String msg) {
		logger.warn(msg);
	}
	
	// Log a message at the WARN level.
	public static void warn(String msg, Throwable t) {
		logger.warn(msg,t);
	}
	
	// Log a message at the ERROR level.
	public static void error(String msg) {
		logger.error(msg);
	}
	
	public static void error(String msg, Throwable t) {
		logger.error(msg,t);
	}
	
	// -------------------------LogBack
	// Warn--------------------------------------------
	// Log a message at the TRACE level.
	public static void trace(String msg) {
		logger.trace(msg);
	}

	// Log an exception (throwable) at the TRACE level with an accompanying
	// message.
	public static void trace(String msg, Throwable t) {
		logger.trace(msg,t);
		
	}

	private static int numberOfStr(String str, String con) {
			String str1 = " " + str;
			return str1.split(con).length;
	}

	private static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			try {
				pw.close();
				sw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static String checkMsg(String msg) {
		if(msg == null) {
			msg = "";
		}
		return msg;
	}
	
	public static void main(String[] args) {
		
		Exception ex =new Exception();
		
		LogCvt.error(ex.getMessage(),ex);
	}
}



