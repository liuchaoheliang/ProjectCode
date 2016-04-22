package com.froad.util.payment;


import com.alibaba.fastjson.JSONObject;
import com.froad.logback.LogCvt;

/**
 * 包装易于支付模块易读日志
* <p>Function: Logger</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-20 上午11:57:29
* @version 1.0
 */
public class Logger {
	
	private static final String PAYMENT_LOGGER_PREFIX = "[支付模块]";
	private static final String MESSAGE_INFO = " - 信息 : \"";
	private static final String DATA = " | 参数列 : ";
	private static final String EXCEPTION = " 异常 : ";
	
	private static boolean isBaseJDKSimpleType(Class<?> type) {
		if (String.class == type) {
			return true;
		}
		if (Integer.class == type || int.class == type) {
			return true;
		}
		if (Long.class == type || long.class == type) {
			return true;
		}
		if (Double.class == type || double.class == type) {
			return true;
		}
		if (Boolean.class == type || boolean.class == type) {
			return true;
		}
		if (Float.class == type || float.class == type) {
			return true;
		}
		if (Character.class == type || char.class == type) {
			return true;
		}
		if (Byte.class == type || byte.class == type) {
			return true;
		}
		return false;
	}
	
	public static void logInfoDeltaT(String message,long startTime){
		StringBuffer sb = new StringBuffer();
		sb.append(PAYMENT_LOGGER_PREFIX);
		sb.append(MESSAGE_INFO);
		sb.append(message + "\" ---------------------> 耗时(ms) : " + (EsyT.currTime() - startTime));
		LogCvt.info(sb.toString());
	}
	
	public static void logInfoDeltaT(String message,Object data,long startTime){
		StringBuffer sb = new StringBuffer();
		sb.append(PAYMENT_LOGGER_PREFIX);
		sb.append(DATA);
		if(data != null){
			Class<?> type = data.getClass();
			if(isBaseJDKSimpleType(type)){
				sb.append(data);
			}else{
				sb.append(JSONObject.toJSONString(data));
			}
		}
		sb.append(MESSAGE_INFO);
		sb.append(message + "\" ---------------------> 耗时(ms) : " + (EsyT.currTime() - startTime));
		LogCvt.info(sb.toString());
	}
	
	public static void logInfo(String message) {
		StringBuffer sb = new StringBuffer();
		sb.append(PAYMENT_LOGGER_PREFIX);
		sb.append(MESSAGE_INFO);
		sb.append(message + "\"");
		LogCvt.info(sb.toString());
	}
	
	public static void logInfo(String message,String paramsName, Object data) {
		StringBuffer sb = new StringBuffer();
		sb.append(PAYMENT_LOGGER_PREFIX);
		sb.append(MESSAGE_INFO);
		sb.append(message + "\"");
		sb.append(DATA);
		if(data == null){
			sb.append("1、" + paramsName + " = null");
		}else{
			Class<?> type = data.getClass();
			if(isBaseJDKSimpleType(type)){
				sb.append("1、" + paramsName + " = " + data);
			}else{
				sb.append("1、" + paramsName + " = " + JSONObject.toJSONString(data));
			}
		}
		LogCvt.info(sb.toString());
	}
	
	public static void logInfo(String message,String[] paramsNames, Object[] datas) {
		StringBuffer sb = new StringBuffer();
		sb.append(PAYMENT_LOGGER_PREFIX);
		sb.append(MESSAGE_INFO);
		sb.append(message + "\"");
		sb.append(DATA);
		Class<?> type = null;
		for (int i = 0; i < paramsNames.length; i++) {
			if(datas[i] == null){
				sb.append((i+1) + "、" + paramsNames[i] + " = null ");
			}else{
				type = datas[i].getClass();
				if(isBaseJDKSimpleType(type)){
					sb.append((i+1) + "、" + paramsNames[i] + " = " + datas[i] + " ");
				}else{
					sb.append((i+1) + "、" + paramsNames[i] + " = " + JSONObject.toJSONString(datas[i]) + " ");
				}
			}
		}
		LogCvt.info(sb.toString());
	}
	
	public static void logError(String message) {
		StringBuffer sb = new StringBuffer();
		sb.append(PAYMENT_LOGGER_PREFIX);
		sb.append(MESSAGE_INFO);
		sb.append(message + "\"");
		LogCvt.error(sb.toString());
	}
	
	public static void logError(String message,String paramsName, Object data) {
		StringBuffer sb = new StringBuffer();
		sb.append(PAYMENT_LOGGER_PREFIX);
		sb.append(MESSAGE_INFO);
		sb.append(message + "\"");
		sb.append(DATA);
		if(data == null){
			sb.append("1、" + paramsName + " = null");
		}else{
			Class<?> type = data.getClass();
			if(isBaseJDKSimpleType(type)){
				sb.append("1、" + paramsName + " = " + data);
			}else{
				sb.append("1、" + paramsName + " = " + JSONObject.toJSONString(data));
			}
		}
		LogCvt.error(sb.toString());
	}
	
	public static void logError(String message,String[] paramsNames, Object[] datas) {
		StringBuffer sb = new StringBuffer();
		sb.append(PAYMENT_LOGGER_PREFIX);
		sb.append(MESSAGE_INFO);
		sb.append(message + "\"");
		sb.append(DATA);
		Class<?> type = null;
		for (int i = 0; i < paramsNames.length; i++) {
			if(datas[i] == null){
				sb.append((i+1) + "、" + paramsNames[i] + " = null" + " ");
			}else{
				type = datas[i].getClass();
				if(isBaseJDKSimpleType(type)){
					sb.append((i+1) + "、" + paramsNames[i] + " = " + datas[i] + " ");
				}else{
					sb.append((i+1) + "、" + paramsNames[i] + " = " + JSONObject.toJSONString(datas[i]) + " ");
				}
			}
		}
		LogCvt.error(sb.toString());
	}
	
	public static void logError(String message, Throwable t) {
		StringBuffer sb = new StringBuffer();
		sb.append(PAYMENT_LOGGER_PREFIX);
		sb.append(MESSAGE_INFO);
		sb.append(message + "\"");
		sb.append(EXCEPTION + t.getMessage());
		LogCvt.error(sb.toString(),t);
	}
    
}
