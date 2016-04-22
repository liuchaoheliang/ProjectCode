package com.froad.thirdparty.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.froad.Constants;
import com.froad.logback.LogCvt;

	/**
	 * 类描述：密码相关属性
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年10月14日 上午11:13:00 
	 */
public class PasswordCommand {

	public static String PUBLICKEY;
	
	public static String PRIVATEKEY;
	
	public static String PWD;
	
	public static String ALIAS;
	
	public static String KEY;
	
	public static String SYS_PATH;
	
	static{
		Properties props = new Properties();
		String configPath = System.getProperty(Constants.CONFIG_PATH,"./config");
		try {
			InputStream input= new FileInputStream(configPath.concat("/password.properties"));
			props.load(input);
			input.close();
		}catch (Exception e) {
			LogCvt.error("加载password.properties文件信息异常", e);
		}
		PUBLICKEY=configPath+props.getProperty("PWD_RSA_PUBLIC_KEY");
		PRIVATEKEY=configPath+props.getProperty("PWD_RSA_PRIVATE_KEY");
		PWD=props.getProperty("PWD");
		ALIAS=props.getProperty("ALIAS");
		KEY=props.getProperty("KEY");
	}
	
	public static void main(String[] args) {
		System.out.println(PUBLICKEY);
	}
}
