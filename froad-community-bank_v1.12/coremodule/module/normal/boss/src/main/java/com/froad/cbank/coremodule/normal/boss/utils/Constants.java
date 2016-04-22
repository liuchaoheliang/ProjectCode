package com.froad.cbank.coremodule.normal.boss.utils;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.froad.cbank.coremodule.framework.common.util.bus.SpringApplicationContextUtil;
import com.froad.cbank.coremodule.framework.common.util.scp.ScpConfig;
import com.froad.cbank.coremodule.framework.common.util.type.PropertyUtils;
import com.froad.cbank.coremodule.framework.common.util.type.TimeUtil;

public class Constants {
		
	public static final String CLIENT_ID = "CLIENT_ID";
	
	public static final String USER_ID = "USER_ID";
	
	public static final String BOSS_USER = "BOSS_USER";
	
	public static final String ORIGIN = "Origin";
	
	public static final Long MERCHANT_ROLE_ID = 100000000l;
	
	public static final Long MINGYOU_PRODUCT = 100000002l;
	
	public static ScpConfig SCPCONFIG;
	
	public static HashMap<String,String> mapPros;
	
	public static final int PAGE_NUMBER = 1;	//默认显示页码
	
	public static final int PAGE_SIZE = 20;		//默认每页显示条数
	
	public static final String RESULT_SUCCESS = "0000";
	
	public static final String RESULT_FAIL = "9999";
	
	public static final String BIZCODE = "1302";//社区银行BOSS
	
	public static final String SUBJECT = "用户人工申诉处理结果";//用户人工申诉处理结果
	
	static{
		mapPros = new HashMap<String, String>();
		PropertyUtils p = (PropertyUtils) SpringApplicationContextUtil.getBean("propertyUtils");
		Properties pros = p.getProperties();
		Enumeration<?> en = pros.propertyNames();
		String key;
		while (en.hasMoreElements()) {
			key = (String) en.nextElement();
			mapPros.put(key, pros.getProperty(key));
		}
		
		if(get("scp.port")!=null){
			SCPCONFIG = new ScpConfig(get("scp.host"),Integer.parseInt(get("scp.port")),get("scp.user"),get("scp.password"));
		}else{
			SCPCONFIG = new ScpConfig(get("scp.host"),get("scp.user"),get("scp.password"));
		}
	}
	
	public static final String DEFAULT_PASSWORD = get("boss.user.password");	//用户默认密码
	public static final String MESSAGE_SERVICE_URL = get("message.http.email.url");//发邮件
	public static final String MESSAGE_EMAIL_URL = get("message.email.url");//发邮件
	
	public static String get(String key){
		return mapPros.get(key);
	}
	
	public static String getImgLocalUrl(){
		return System.getProperty("java.io.tmpdir");
	}
	
	public static String getScpFileUploadPath(){
		return get("scp.file.uploadpath");
	}
	
	public static String getImgRemoteUrl(){
		return get("boss.image.remote")+TimeUtil.toString(Calendar.getInstance(), TimeUtil.yearMonthTimeFormat)+"/";
	}
	
	public static String getScpImgUploadPath(){
		return get("scp.uploadpath")+TimeUtil.toString(Calendar.getInstance(), TimeUtil.yearMonthTimeFormat)+"/";
	}
	
	public static String getIpAddr(HttpServletRequest request) {      
		String ip = request.getHeader("X-Forwarded-For");
	   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	       ip = request.getHeader("Proxy-Client-IP");
	   }
	   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	       ip = request.getHeader("WL-Proxy-Client-IP");
	   }
	   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	       ip = request.getHeader("HTTP_CLIENT_IP");
	   }
	   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	       ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	   }
	   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	      ip = request.getRemoteAddr();
	   }
	   return ip;     
	}
	
	
}
