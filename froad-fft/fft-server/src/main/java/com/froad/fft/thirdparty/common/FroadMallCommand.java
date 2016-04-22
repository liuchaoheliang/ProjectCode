package com.froad.fft.thirdparty.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.froad.fft.sysconfig.SysConfigInitialize;

public class FroadMallCommand {

	
	//业务代码
	public static String APICODE_QUERY_CZNO="10001";//查询手机号
	public static String APICODE_ONLINE="10002";//充值
	//public static String APICODE_QUERY="10003";//订单查询
	public static String APICODE_QUERY_PERIDLISTNOW="60001";//查询当前期数
	public static String APICODE_CREATEORDER="60005";//创建订单
	
	/**方付通商场 URL*/
	private static String FROAD_MALL_URL;
	
	/**请求版本号*/
	public static String FROAD_MALL_VERSION;
	
	/**方付通商场API URL*/
	public static String FROAD_MALL_MAIN_URL;
	
	/**商场加签方式*/
	public static String FROAD_MALL_SIGN_TYPE = "1";
	
	/**商户号*/
	public static String MERCHANT_NO;
	
	/**密码*/
	public static String MERCHANT_PWD;
	
	/**商场自定义密码*/
	public static String FROADMALL_CUSTOM_KEY;
	
	static{
		Logger logger = Logger.getLogger(FroadMallCommand.class);
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
		
		FROAD_MALL_VERSION = props.getProperty("FROAD_MALL_VERSION");
		MERCHANT_NO = props.getProperty("MERCHANT_NO");
		MERCHANT_PWD = props.getProperty("MERCHANT_PWD");
		FROADMALL_CUSTOM_KEY = props.getProperty("FROADMALL_CUSTOM_KEY");
		
		
		FROAD_MALL_URL = props.getProperty("FROAD_MALL_URL");
		FROAD_MALL_MAIN_URL = FROAD_MALL_URL + "froadmallapi.do";
	}
}
