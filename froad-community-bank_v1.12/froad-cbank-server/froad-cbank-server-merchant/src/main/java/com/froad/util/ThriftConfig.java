package com.froad.util;

import java.util.Map;


/**
 * 读取thrif.properties文件配置类
 * @ClassName: ThriftConfig 
 * @Description: TODO
 * @author: huangyihao
 * @date: 2015年4月24日
 */
public class ThriftConfig {
	
	//===================thrift.thrift Config================================
	public static String  BANK_SERVICE_HOST;		//银行系统Ip
	
	public static Integer BANK_SERVICE_PORT;		//银行系统端口
	
	public static String ORDER_SERVICE_HOST;		//支付系统Ip
	
	public static Integer ORDER_SERVICE_PORT;		//支付系统端口
	
	public static String SUPPORT_SERVICE_HOST;		//外围系统Ip
	
	public static Integer SUPPORT_SERVICE_PORT;		//外围系统端口
	
	public static String MERCHANT_SERVICE_HOST;		//商户系统IP
	
	public static Integer MERCHANT_SERVICE_PORT;	//商户系统端口
	
	public static String GOODS_SERVICE_HOST;        //商品系统IP
	 
	public static Integer GOODS_SERVICE_PORT;       //商品系统端口
	
	public static String QRCODE_SERVICE_HOST;        //二维码系统IP
	 
	public static Integer QRCODE_SERVICE_PORT;       //二维码系统端口
	
	//===================merchant.thrift Config================================
	public static String DOMAIN_NAME;       		 //访问域名
	
	static {
		Map<String ,String> thriftInfo = PropertiesUtil.loadProperties(Constants.THRIFT_FILE_NAME);
		BANK_SERVICE_HOST = thriftInfo.get(Constants.THRIFT_BANK_HOST);
		BANK_SERVICE_PORT = Integer.valueOf(thriftInfo.get(Constants.THRIFT_BANK_PORT));
		ORDER_SERVICE_HOST = thriftInfo.get(Constants.THRIFT_ORDER_HOST);
		ORDER_SERVICE_PORT = Integer.valueOf(thriftInfo.get(Constants.THRIFT_ORDER_PORT));
		SUPPORT_SERVICE_HOST = thriftInfo.get(Constants.THRIFT_SUPPORT_HOST);
		SUPPORT_SERVICE_PORT = Integer.valueOf(thriftInfo.get(Constants.THRIFT_SUPPORT_PORT));
		MERCHANT_SERVICE_HOST = thriftInfo.get(Constants.THRIFT_MERCHANT_HOST);
		MERCHANT_SERVICE_PORT = Integer.valueOf(thriftInfo.get(Constants.THRIFT_MERCHANT_PORT));
		GOODS_SERVICE_HOST = thriftInfo.get(Constants.GOODS_SERVICE_HOST);
		GOODS_SERVICE_PORT = Integer.valueOf(thriftInfo.get(Constants.GOODS_SERVICE_PORT));
		QRCODE_SERVICE_HOST = thriftInfo.get(Constants.QRCODE_SERVICE_HOST);
		QRCODE_SERVICE_PORT = Integer.valueOf(thriftInfo.get(Constants.QRCODE_SERVICE_PORT));
		
		//加入Merchant配置获取
		Map<String ,String> merchantInfo = PropertiesUtil.loadProperties(Constants.MERCHANT_FILE_NAME);
		DOMAIN_NAME = merchantInfo.get(Constants.DOMAIN_NAME);
	}	
}