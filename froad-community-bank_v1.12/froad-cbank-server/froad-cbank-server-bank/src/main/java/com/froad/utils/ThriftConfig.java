package com.froad.utils;

import java.util.Map;
import com.froad.util.PropertiesUtil;


/**
 * 读取thrif.properties文件配置类
 * @ClassName: ThriftConfig 
 * @Description: TODO
 * @author: huangyihao
 * @date: 2015年4月24日
 */
public class ThriftConfig {
	
	public static String ORDER_SERVICE_HOST;		//支付系统Ip
	
	public static Integer ORDER_SERVICE_PORT;		//支付系统端口
	
//	public static String SUPPORT_SERVICE_HOST;		//外围系统Ip
//	
//	public static Integer SUPPORT_SERVICE_PORT;		//外围系统端口
//	
	public static String MERCHANT_SERVICE_HOST;		//商户系统IP
	
	public static Integer MERCHANT_SERVICE_PORT;	//商户系统接口
	
	static {
		Map<String ,String> thriftInfo = PropertiesUtil.loadProperties(Constants.THRIFT_FILE_NAME);
		ORDER_SERVICE_HOST = thriftInfo.get(Constants.THRIFT_ORDER_HOST);
		ORDER_SERVICE_PORT = Integer.valueOf(thriftInfo.get(Constants.THRIFT_ORDER_PORT));
//		SUPPORT_SERVICE_HOST = thriftInfo.get(Constants.THRIFT_SUPPORT_HOST);
//		SUPPORT_SERVICE_PORT = Integer.valueOf(thriftInfo.get(Constants.THRIFT_SUPPORT_PORT));
		MERCHANT_SERVICE_HOST = thriftInfo.get(Constants.THRIFT_MERCHANT_HOST);
		MERCHANT_SERVICE_PORT = Integer.valueOf(thriftInfo.get(Constants.THRIFT_MERCHANT_PORT));
	}
	
}
