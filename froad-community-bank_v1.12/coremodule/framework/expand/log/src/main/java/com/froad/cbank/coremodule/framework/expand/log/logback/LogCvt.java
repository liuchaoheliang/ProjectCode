package com.froad.cbank.coremodule.framework.expand.log.logback;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

import com.froad.cbank.coremodule.framework.common.util.bus.SpringApplicationContextUtil;
import com.froad.cbank.coremodule.framework.common.util.type.PropertyUtils;

/**
 * logback 日志封装
 * 
 * @ClassName: LogCvt
 * @Description: TODO
 * @author share
 * @create-date 2012-10-9 上午09:27:30
 * @modify-date 2012-10-9 上午09:27:30
 * 
 */
public class LogCvt {

	private static final Logger logger = LoggerFactory.getLogger(LogCvt.class.getName());
	static {

		String port = System.getProperty("TOMCAT_PORT");
		String dir = "";
		String level = "";
		PropertyUtils p = (PropertyUtils) SpringApplicationContextUtil.getBean("propertyUtils");
		
		if (p != null) {
			dir = p.getPropertiesString("PROJECT_NAME");
			level = p.getPropertiesString("LOG_LEVEL");
		}

		if (port == null) {
			port = "0000";
		}
		if("".equals(dir)){
			dir = "default";
		}
		if("".equals(level)){
			level = "INFO";
		}
		System.setProperty("TOMCAT_PORT", port);
		System.setProperty("PROJECT_NAME", dir);
		System.setProperty("LOG_LEVEL", level);

		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(lc);
		lc.reset();
		try {
			// configurator.doConfigure("logConfig.xml");
			configurator.doConfigure(LogCvt.class.getClassLoader()
					.getResourceAsStream("logConfig.xml"));
		} catch (JoranException e) {
			e.printStackTrace();
		}
		StatusPrinter.print(lc.getStatusManager());
	}

	public Logger getLogger() {
		return logger;
	}
	
	public static boolean isDebugEnabled(){
		return logger.isDebugEnabled();
	}

	// --------------------------------------------LogBack
	// Info----------------------------------

	// Log a message at the INFO level.
	public static void info(String msg) {
		msg = checkMsg(msg);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(msg.replaceAll("\n", "\r\n"));
//		MDC.put("LINE", (numberOfStr(stringBuffer.toString(), "\n") - 1) + "");
		logger.info(stringBuffer.toString());
//		MDC.remove("LINE");
	}

	/**
	 * 
	 * @Title: info
	 * @Description: TODO
	 * @author:share
	 * @create-date:2012-7-5 下午08:43:58
	 * @modify-date:2012-7-5 下午08:43:58
	 * @param @param msg-->输入消息
	 * @param @param IF -->线程名.
	 * @return void
	 * @throws
	 */
	public static void info(String msg, String IF) {
		msg = checkMsg(msg);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(msg.replaceAll("\n", "\r\n"));
//		MDC.put("LINE", (numberOfStr(stringBuffer.toString(), "\n") - 1) + "");
//		MDC.put("IF", IF);
		logger.info(stringBuffer.toString());
//		MDC.remove("IF");
//		MDC.remove("LINE");
	}

	// Log an exception (throwable) at the INFO level with an accompanying
	// message.
	public static void info(String msg, Throwable t) {
		msg = checkMsg(msg);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(msg.replaceAll("\n", "\r\n"));
		stringBuffer.append("\r\n");
		stringBuffer.append(getStackTrace(t));
//		MDC.put("LINE", (numberOfStr(stringBuffer.toString(), "\n") - 1) + "");
		logger.info(stringBuffer.toString());
//		MDC.remove("LINE");

	}

	/**
	 * 
	 * @Title: info
	 * @Description: TODO
	 * @author:share
	 * @create-date:2012-7-5 下午08:47:18
	 * @modify-date:2012-7-5 下午08:47:18
	 * @param @param msg -->消息
	 * @param @param t -->错误句柄
	 * @param @param IF -->线程名
	 * @return void
	 * @throws
	 */
	public static void info(String msg, Throwable t, String IF) {
		msg = checkMsg(msg);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(msg.replaceAll("\n", "\r\n"));
		stringBuffer.append("\r\n");
		stringBuffer.append(getStackTrace(t));
//		MDC.put("LINE", (numberOfStr(stringBuffer.toString(), "\n") - 1) + "");
//		MDC.put("IF", IF);
		logger.info(stringBuffer.toString());
//		MDC.remove("IF");
//		MDC.remove("LINE");

	}

	// ---------------------------------------------LogBack
	// Debug------------------------------------

	// Log a message at the DEBUG level.
	public static void debug(String msg) {
		if(isDebugEnabled()){
			msg = checkMsg(msg);
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(msg.replaceAll("\n", "\r\n"));
//			MDC.put("LINE", (numberOfStr(stringBuffer.toString(), "\n") - 1) + "");
			logger.debug(stringBuffer.toString());
//			MDC.remove("LINE");
		}
	}

	/**
	 * 
	 * @Title: debug
	 * @Description: TODO
	 * @author:share
	 * @create-date:2012-7-5 下午08:48:13
	 * @modify-date:2012-7-5 下午08:48:13
	 * @param @param msg -->消息
	 * @param @param IF -->线程名
	 * @return void
	 * @throws
	 */
	public static void debug(String msg, String IF) {
		if(isDebugEnabled()){
			msg = checkMsg(msg);
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(msg.replaceAll("\n", "\r\n"));
//			MDC.put("LINE", (numberOfStr(stringBuffer.toString(), "\n") - 1) + "");
//			MDC.put("IF", IF);
			logger.debug(stringBuffer.toString());
//			MDC.remove("IF");
//			MDC.remove("LINE");
		}
	}

	// Log an exception (throwable) at the DEBUG level with an accompanying
	// message.
	public static void debug(String msg, Throwable t) {
		if(isDebugEnabled()){
			msg = checkMsg(msg);
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(msg.replaceAll("\n", "\r\n"));
			stringBuffer.append("\r\n");
			stringBuffer.append(getStackTrace(t));
//			MDC.put("LINE", (numberOfStr(stringBuffer.toString(), "\n") - 1) + "");
			logger.debug(stringBuffer.toString());
//			MDC.remove("LINE");
		}
	}

	/**
	 * 
	 * @Title: debug
	 * @Description: TODO
	 * @author:share
	 * @create-date:2012-7-5 下午08:48:37
	 * @modify-date:2012-7-5 下午08:48:37
	 * @param @param msg -->消息
	 * @param @param t -->线程句柄
	 * @param @param IF -->线程名
	 * @return void
	 * @throws
	 */
	public static void debug(String msg, Throwable t, String IF) {
		if(isDebugEnabled()){
			msg = checkMsg(msg);
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(msg.replaceAll("\n", "\r\n"));
			stringBuffer.append("\r\n");
			stringBuffer.append(getStackTrace(t));
//			MDC.put("LINE", (numberOfStr(stringBuffer.toString(), "\n") - 1) + "");
//			MDC.put("IF", IF);
			logger.debug(stringBuffer.toString());
//			MDC.remove("IF");
//			MDC.remove("LINE");
		}
	}

	// -----------------------------LogBack
	// Error-------------------------------------

	// Log a message at the ERROR level.
	public static void error(String msg) {
		msg = checkMsg(msg);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(msg.replaceAll("\n", "\r\n"));
//		MDC.put("LINE", (numberOfStr(stringBuffer.toString(), "\n") - 1) + "");
		logger.error(stringBuffer.toString());
//		MDC.remove("LINE");
	}

	/**
	 * 
	 * @Title: error
	 * @Description: TODO
	 * @author:share
	 * @create-date:2012-7-5 下午08:49:07
	 * @modify-date:2012-7-5 下午08:49:07
	 * @param @param msg
	 * @param @param IF
	 * @return void
	 * @throws
	 */
	public static void error(String msg, String IF) {
		msg = checkMsg(msg);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(msg.replaceAll("\n", "\r\n"));
//		MDC.put("LINE", (numberOfStr(stringBuffer.toString(), "\n") - 1) + "");
//		MDC.put("IF", IF);
		logger.error(stringBuffer.toString());
//		MDC.remove("IF");
//		MDC.remove("LINE");
	}

	public static void error(String msg, Throwable t) {
		msg = checkMsg(msg);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(msg.replaceAll("\n", "\r\n"));
		stringBuffer.append("\r\n");
		stringBuffer.append(getStackTrace(t));
//		MDC.put("LINE", (numberOfStr(stringBuffer.toString(), "\n") - 1) + "");
		logger.error(stringBuffer.toString());
//		MDC.remove("LINE");

	}

	/**
	 * 
	 * @Title: error
	 * @Description: TODO
	 * @author:share
	 * @create-date:2012-7-5 下午08:50:05
	 * @modify-date:2012-7-5 下午08:50:05
	 * @param @param msg -->消息
	 * @param @param t -->线程句柄
	 * @param @param IF -->线程名
	 * @return void
	 * @throws
	 * 
	 */
	public static void error(String msg, Throwable t, String IF) {
		msg = checkMsg(msg);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(msg.replaceAll("\n", "\r\n"));
		stringBuffer.append("\r\n");
		stringBuffer.append(getStackTrace(t));
//		MDC.put("LINE", (numberOfStr(stringBuffer.toString(), "\n") - 1) + "");
//		MDC.put("IF", IF);
		logger.error(stringBuffer.toString());
//		MDC.remove("IF");
//		MDC.remove("LINE");

	}

	// -------------------------LogBack
	// Warn--------------------------------------------
	// Log a message at the TRACE level.
	public static void trace(String msg) {
		msg = checkMsg(msg);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(msg.replaceAll("\n", "\r\n"));
//		MDC.put("LINE", (numberOfStr(stringBuffer.toString(), "\n") - 1) + "");
		logger.trace(stringBuffer.toString());
//		MDC.remove("LINE");
	}

	// Log an exception (throwable) at the TRACE level with an accompanying
	// message.
	public static void trace(String msg, Throwable t) {
		msg = checkMsg(msg);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(msg.replaceAll("\n", "\r\n"));
		stringBuffer.append("\r\n");
		stringBuffer.append(getStackTrace(t));
//		MDC.put("LINE", (numberOfStr(stringBuffer.toString(), "\n") - 1) + "");
		logger.trace(stringBuffer.toString());
//		MDC.remove("LINE");

	}

//	private static int numberOfStr(String str, String con) {
//		String str1 = " " + str;
//		return str1.split(con).length;
//
//	}

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
		if (msg == null) {
			msg = "";
		}
		return msg;
	}

	public static void main(String[] args) {

		LogCvt.error("test-error");
	}
}
