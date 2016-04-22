package com.froad.fft.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.froad.fft.sysconfig.SysConfigInitialize;

/**
 * 常量
 * @author FQ
 *
 */
public class Constants {

	//当前运行环境是否为正式线上环境
//	public static Boolean IS_PRODUCTION_ENVI;
	
	//服务端运行IP地址
	public static String SERVER_IP_ADDR;
	
	static{
		Logger logger = Logger.getLogger(Constants.class);
		Properties props = new Properties();
		final String configFileName = "system.properties";
		File file = new File(SysConfigInitialize.BASE_CONFIG_PATH + configFileName);
		try {
			InputStream inputStream =new FileInputStream(file);
			props.load(inputStream);
			inputStream.close();			
		} catch (FileNotFoundException e) {
			logger.error("加载配置文件: "+configFileName+" 相关信息错误", e);
		} catch (IOException e) {
			logger.error("加载配置文件: "+configFileName+" 相关信息错误", e);
		}finally{
			logger = null;
		}
		
//		try {
//			IS_PRODUCTION_ENVI = Boolean.parseBoolean(props.getProperty("IS_PRODUCTION_ENVI"));
//		} catch (Exception e) {
//			IS_PRODUCTION_ENVI = false;
//		}
		
		SERVER_IP_ADDR = props.getProperty("SERVER_IP_ADDR");

	}
}
