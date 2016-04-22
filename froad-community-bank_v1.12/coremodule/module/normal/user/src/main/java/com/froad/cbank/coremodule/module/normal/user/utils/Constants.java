package com.froad.cbank.coremodule.module.normal.user.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.froad.cbank.coremodule.framework.common.util.DateUtil;
import com.froad.cbank.coremodule.framework.common.util.bus.SpringApplicationContextUtil;
import com.froad.cbank.coremodule.framework.common.util.scp.ScpConfig;
import com.froad.cbank.coremodule.framework.common.util.type.NumberUtil;
import com.froad.cbank.coremodule.framework.common.util.type.PropertyUtils;

public class Constants {
	
	//private static final String PROPERTIES_PATH = "conf/soft/properties/coremodule-user-web";

	public static Map<String,String> proMap;
	static{
		proMap = new HashMap<String,String>();
		PropertyUtils p = (PropertyUtils) SpringApplicationContextUtil.getBean("propertyUtils");
		Properties pros = p.getProperties();
		Enumeration<?> keys = pros.propertyNames();
		String key;
		while(keys.hasMoreElements()){
			key =  (String)keys.nextElement();
			proMap.put(key, pros.getProperty(key));
		}
		
		if(get("scp.port")==null){
			scpConfig = new ScpConfig(get("scp.host"),get("scp.user"),get("scp.password"));
		}else{
			scpConfig = new ScpConfig(get("scp.host"),Integer.parseInt(get("scp.port")),get("scp.user"),get("scp.password"));
		}
	}
	
	
	public static final int PAGE_NUMBER = 1;	//默认显示页码
	
	public static final int PAGE_SIZE = 20;		//默认每页显示条数
	
	public static final int PAGE_COUNT = 0;		//默认总页数
	
	public static final int TOTAL_COUNT = 0;		//默认总记录数
	
	public static final String RESULT_CODE_SUCCESS = "0000"; //ResultCode成功标识

	public static final int SMS_VALID_TIME = NumberUtil.toInt(get("user.sms.validtime"));	//短信验证过期时间
	
	
	//加密设置开始
	
	public static final String PRIVATEKEY = get("user.pe.privateKey");
	
	public static final String PUBLICKEY = get("user.pe.publicKey");
	
	public static final String ALIAS = get("user.pe.alias");
	
	public static final String KEYNAME = get("user.pe.keyName");
	
	public static final String PWD = get("user.pe.pwd");
	
	//加密设置结束
	
	
	public static final String LOGIN_ID_REGEX = get("user.loginid.regex");
	

	
	
	/**
	 * 图片服务器设置
	 */
	public static ScpConfig scpConfig;
	
	/**
	 * 安全中心申诉链接
	 */
	public static final String SAFE_APPEAL_URL = get("safe.appeal.url");
	
	/**
	 * 消息服务系统
	 */
	public static final String MESSAGE_SERVICE_URL = get("message.service.url");
	
	/**
	 * 登录Token设置
	 */
	public static final class Token {
		public static final String COOKIE_KEY = get("user.token.cookie.key");
		public static final String COOKIE_DOMAIN = get("user.token.cookie.domain");
		public static final int TOKEN_REFRESH_TIME = NumberUtil.toInt(get("user.token.refresh.time"),10);
	}
	
	public static final class IP_LOCATION{
		public static final String API = get("user.iplocation.proxy");
		public static final String AK = get("user.iplocation.ak");
	}
	
	
	public static String get(String key){
		//PropertiesUtil sys = PropertiesUtil.getInstance(PROPERTIES_PATH);
		return proMap.get(key);
	}
	
	public static String getFileTempDir(){
		return System.getProperty("java.io.tmpdir");
	}
	
	public static String getScpUploadDir(){
		return get("scp.uploadpath") + DateUtil.getCurDate().substring(0,6) + "/" ;
	}
	
	public static String getImgRemoteUrl(){
		return get("user.image.remote") + DateUtil.getCurDate().substring(0,6) + "/" ;
	}
}

