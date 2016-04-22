package com.froad.util;

import java.io.File;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.froad.Constants;
import com.froad.logback.LogCvt;

public class PropertiesUtil {
	
	public static int port = 0;
	
	private static String suffix = ".properties";
	
	static{
		String configPath = "."+File.separatorChar+"config";
		System.setProperty(Constants.CONFIG_PATH, configPath);
		
		Map<String, String> configInfo = loadProperties("init");
		System.setProperty(Constants.WORK_PATH, configInfo.get("log.path"));
		String sport = configInfo.get("port");
		if (null != sport && !sport.trim().equals("")) {
			System.setProperty(Constants.PORT, sport);
			port = Integer.parseInt(sport);
		}
		System.setProperty(Constants.MODULE_NAME, configInfo.get("module.name"));
	}
	
	public static void load(){

	}
	
	/**
	 * 载入properties配置文件
	 * 
	 * @param propertiesName
	 * @return
	 */
	public static Map<String, String> loadProperties(String fileName){
		HashMap <String, String> propertiesMap = null;
		ResourceBundle resourceBundle = null;
		Enumeration<String> enumeration = null;
		String key = null;
		String value = null;
		FileReader fr = null;
		String path = null;
		try {
			propertiesMap = new HashMap<String, String>();
			path = System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+fileName+suffix;
			fr = new FileReader(path);
			resourceBundle = new PropertyResourceBundle(fr);
			enumeration = resourceBundle.getKeys();
			
			while (enumeration.hasMoreElements()){
				key = enumeration.nextElement();
				value = resourceBundle.getString(key);
				propertiesMap.put(key, value);
			}
		} catch (Exception e){
			LogCvt.error("Error while loading properties file: " + path);
			LogCvt.error(e.getMessage(), e);
		} finally{
			try{if(fr!=null){fr.close();}}catch(Exception e){}
		}
		
		return propertiesMap;
	}
	
	/**
	 * 根据key获取properties中的值
	 * 
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static String getProperty(String fileName, String key){
		ResourceBundle resourceBundle = null;
		String value = null;
		String path = System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+fileName+suffix;
		FileReader fr = null;
		try {
			fr = new FileReader(path);
			resourceBundle = new PropertyResourceBundle(fr);
			value = resourceBundle.getString(key);
		} catch (Exception e) {
			LogCvt.error("Error while loading properties file: " + fileName);
			LogCvt.error(e.getMessage(), e);
		} finally{
			try{if(fr!=null){fr.close();}}catch(Exception e){}
		}
		
		return value;
	}
}
