package com.froad.cbank.coremodule.module.normal.merchant.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import com.froad.cbank.coremodule.framework.common.util.DateUtil;
import com.froad.cbank.coremodule.framework.common.util.bus.SpringApplicationContextUtil;
import com.froad.cbank.coremodule.framework.common.util.scp.ScpConfig;
import com.froad.cbank.coremodule.framework.common.util.type.PropertyUtils;

public class Constants {
	
	public static final String CLIENT_ID = "CLIENT_ID";
	
	public static final String MERCHANT_USER = "MERCHANT_USER";
	
	public static final String PLAT_TYPE = "PLAT_TYPE"; 
	
	public static final String CLIENT_IP = "CLIENT_IP";
	
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
		return get("merchant.image.remote") + DateUtil.getCurDate().substring(0,6) + "/";
	}
	
	public static String getScpImgUploadPath(){
		return get("scp.uploadpath") + DateUtil.getCurDate().substring(0,6) + "/" ;
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
