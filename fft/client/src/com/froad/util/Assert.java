package com.froad.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


public class Assert {
	private static Logger log = Logger.getLogger(Assert.class);

	public static boolean empty(String str) {
		if (str == null || "".equals(str.trim()))
			return true;
		else
			return false;
	}

	public static boolean empty(String[] str) {
		if (str == null || str.length == 0)
			return true;
		else
			return false;
	}

	public static boolean empty(List list) {
		if (list == null || list.size() == 0)
			return true;
		else
			return false;
	}

	public static boolean empty(Map map) {
		if (map == null || map.isEmpty())
			return true;
		else
			return false;
	}

	public static boolean empty(Integer data) {
		if (data == null || data == 0)
			return true;
		else
			return false;
	}

	public static boolean isDouble(String str) {
		boolean result = false;
		try {
			Double.parseDouble(str);
			result = true;
		} catch (NumberFormatException e) {
			log.error(str + " is not double", e);
		}
		return result;
	}

	
	public static boolean isNumberOfGreaterEZero(String data) {
		// 表达式的功能：验证必须为数字（整数或小数）
		String pattern = "[0-9]+(.[0-9]+)?";
		// 对()的用法总结：将()中的表达式作为一个整体进行处理，必须满足他的整体结构才可以。
		// (.[0-9]+)? ：表示()中的整体出现一次或一次也不出现
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(data);
		boolean result=m.matches();
		if(result)
			result=isDouble(data);
		return result;
	}
	
	public static boolean isIntOfGreaterEZero(String data) {
		// 表达式的功能：验证必须为数字（整数或小数）
		String pattern = "[0-9]+";
		// 对()的用法总结：将()中的表达式作为一个整体进行处理，必须满足他的整体结构才可以。
		// (.[0-9]+)? ：表示()中的整体出现一次或一次也不出现
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(data);
		return m.matches();
	}
	
	 public static void main(String[] args) {  
		 String data="11111";
	       log.info("isNumber:" + isNumberOfGreaterEZero(data)) ;  
	       log.info("isIntOfGreaterEZero:" + isIntOfGreaterEZero(data)) ;
	          
	    }  
}
