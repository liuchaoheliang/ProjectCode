package com.froad.fft.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.froad.fft.sysconfig.SysConfigInitialize;

/**
 * 客户端 url map
 * @author FQ
 *
 */
public class ClientSiteUrlMapCommand {
	
	public static final Map<String,String> CLIENT_SITE_URL ;//客户端 网址
	public static final Map<String,String> CLIENT_STATIC_URL ; //客户端静态化址址
	
	static{
		CLIENT_SITE_URL=new HashMap<String,String>();
		CLIENT_STATIC_URL=new HashMap<String,String>();
		
		Logger logger = Logger.getLogger(ClientSiteUrlMapCommand.class);
		
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
		
		
		CLIENT_SITE_URL.put("243", props.getProperty("CLIENT_CQ_243"));
		
		CLIENT_STATIC_URL.put("243", props.getProperty("CLIENT_CQ_243")+"/buildStatic.api");
	}
}
