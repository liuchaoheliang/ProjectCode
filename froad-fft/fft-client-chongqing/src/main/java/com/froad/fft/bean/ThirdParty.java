package com.froad.fft.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.froad.fft.sysconfig.SysConfigInitialize;

/**
 * *******************************************************
 *<p> 工程: fft-server </p>
 *<p> 类名: ThirdParty.java </p>
 *<p> 描述: *-- <b>对接配置文件thirdparty.properties</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年4月30日 上午11:41:26 </p>
 ********************************************************
 */
public class ThirdParty {

	public static String SSO_SERVER_URL;
	public static String LOCATION_URL;
	
	public static String O2O_RAFFLE_URL;
	
	
	static{
		Logger logger = Logger.getLogger(ThirdParty.class);
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
		SSO_SERVER_URL = props.getProperty("SSO_SERVER_URL");
		LOCATION_URL = props.getProperty("LOCATION_URL");
		O2O_RAFFLE_URL = props.getProperty("O2O_RAFFLE_URL");
	}
	
}
