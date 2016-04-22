package com.froad.fft.sysconfig;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * *******************************************************
 *<p> 工程: fft-server </p>
 *<p> 类名: SysConfigInitialize.java </p>
 *<p> 描述: *-- <b>用于加载系统基础配置</b> --* </p>
 *<p> 作者: 赵肖瑶 </p>
 *<p> 时间: 2014年1月14日 下午4:26:23 </p>
 ********************************************************
 */
public class SysConfigInitialize {

	/**
	 * 系统配置文件夹的物理地址
	 */
	public static String BASE_CONFIG_PATH;
	
	static{
		
		InputStream inputStream = SysConfigInitialize.class.getClassLoader().getResourceAsStream("sysconfig.properties");
		Logger logger = Logger.getLogger(SysConfigInitialize.class);
		try {
			Properties props = new Properties();
			props.load(inputStream);
			inputStream.close();
			BASE_CONFIG_PATH = props.getProperty("config.path");
		} catch (Exception e) {
			logger.error("加载配置文件: sysconfig.properties 相关信息错误", e);
		}finally{
			logger = null;
		}
	}
}
