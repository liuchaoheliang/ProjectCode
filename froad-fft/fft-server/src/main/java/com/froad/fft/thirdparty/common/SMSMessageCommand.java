package com.froad.fft.thirdparty.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.froad.fft.sysconfig.SysConfigInitialize;

public class SMSMessageCommand {

	/**
	 * 短信平台地址
	 */
	public static String SMS_URL;

	static{
		Logger logger = Logger.getLogger(PointsCommand.class);
		Properties props = new Properties();
		final String configFileName = "thirdparty.properties";
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
		
		SMS_URL = props.getProperty("SMS_URL");
	}
}
