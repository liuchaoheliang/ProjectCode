package com.froad.thirdparty.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.froad.Constants;
import com.froad.logback.LogCvt;


public class UserEngineCommend {

	/**
	 * 账户账务服务地址
	 */
	public static String USER_ENGINE_URL;
	
	/**
	 * 支付相关服务地址
	 */
	public static String PE_ACCT_URL;
	
	public static String VIP_USER_URL;
	
	/**
	 * 解密加密支付密码key
	 */
	public static String PAYPWD_RSA_PRIVATE_KEY;
	
	static{
		Properties props = new Properties();
		FileReader fr = null;
		try {
			fr = new FileReader(new File(System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+"thirdparty.properties"));
			props.load(fr);
		} catch (FileNotFoundException e) {
			LogCvt.error("加载配置文件错误", e);
		} catch (IOException e) {
			LogCvt.error("加载配置文件错误", e);
		}finally{
			try {
				if(fr != null){
					fr.close();
				}
			} catch (IOException e) {
			}
		}
		
		USER_ENGINE_URL = props.getProperty("USER_ENGINE_URL");
		PE_ACCT_URL = props.getProperty("PE_ACCT_URL");
		VIP_USER_URL = props.getProperty("VIP_USER_URL");
		PAYPWD_RSA_PRIVATE_KEY = props.getProperty("PAYPWD_RSA_PRIVATE_KEY");
	}
}
