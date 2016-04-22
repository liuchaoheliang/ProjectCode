package com.froad.cbank.coremodule.module.normal.bank.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import com.froad.cbank.coremodule.framework.common.util.bus.SpringApplicationContextUtil;
import com.froad.cbank.coremodule.framework.common.util.scp.ScpConfig;
import com.froad.cbank.coremodule.framework.common.util.type.PropertyUtils;

public class Constants {
		
	public static final String CLIENT_ID = "CLIENT_ID";
	
	public static final String USER_ID = "USER_ID";
	
	public static final String USER_ID_COOKIE="b_userId";
	
	public static final String BANKOPERATOR = "BANKOPERATOR";
	
	public static final String FLAG = "bankUserLoginFlag";
	public static final String CODE = "orgCode";
	public static final String USERNAME = "username";

	
	public static final Long MERCHANT_ROLE_ID = 100000000l;//查询所有角色为超级管理员的
	
	public static final Long MINGYOU_PRODUCT = 100000002l;//名优特惠商户
	
	public static final String IMAGE = "png,jpg,jpeg";
	
	public static ScpConfig SCPCONFIG;
	
	public static HashMap<String,String> mapPros;
	
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
	
	public static String get(String key){
		return mapPros.get(key);
	}
	
	public static String getImgLocalUrl(){
		return System.getProperty("java.io.tmpdir");
	}
	
	public static String getImgRemoteUrl(){
		return get("bank.image.remote")+DateUtil.getNewDate().substring(0,6)+"/";
	}
	
	public static String getScpImgUploadPath(){
		return get("scp.uploadpath")+DateUtil.getNewDate().substring(0,6)+"/";
	}
	
	public static String getScpQrcodeUploadPath(){
		return get("scp.qrcodeuploadpath") + DateUtil.getCurDate().substring(0,6) + "/" ;
	}
	
	public static String getScpQrcodeImgPath(){
		return get("scp.qrcodeimgpath") + DateUtil.getCurDate().substring(0,6) + "/" ;
	}
	
	
	public static String getQrocdImgPath(){
		return get("scp.qrodepath");
	}
	
	public static String getBaseImgPath(){
		return get("image.path");
	}
	public static String getFontPath(){
		return get("font.path");
	}
	
}
