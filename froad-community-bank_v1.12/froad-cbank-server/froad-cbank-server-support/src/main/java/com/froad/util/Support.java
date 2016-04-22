/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
  
/**  
 * @Title: Support.java
 * @Package com.froad.support
 * @Description: TODO
 * @author vania
 * @date 2015年4月10日
 */

package com.froad.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.thrift.TException;

import com.froad.thrift.ServerRun;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.QrCodeService;



/**    
 * <p>Title: Support.java</p>    
 * <p>Description:调用二维码模块 </p>   
 * @author wuhelian      
 * @version 1.0    
 * @created 2015年4月17日 下午10:50:02   
 * @modify YaoLong Liang 2015.8.12
 */

public class Support {
	private static Properties props = null;
	/**
	 * 发送短信url
	 */
	public static  String QRCODE_SMSURL = "";//短信发送地址   http://10.43.2.1:8085/message-service/sms
	private static String QR_HOST = null;  //二维码模块ip
	private static int QR_PORT = 8888;   // 二维码模块端口
	private static int QR_TIMEOUT = 60000; // 与银行模块通讯超时时间
	private static String BIZCODE="1301";//社区银行BIZCODE
	static {
		FileReader fr = null;
		try {
			fr = new FileReader(new File(System.getProperty(com.froad.Constants.CONFIG_PATH)+File.separatorChar+"support.properties"));
			props = new Properties();
			props.load(fr);
			QR_HOST = props.getProperty("qrcode.host");
			QR_PORT = Integer.parseInt(props.getProperty("qrcode.port"));
			QR_TIMEOUT = Integer.parseInt(props.getProperty("qrcode.clientTimeout"));
			QRCODE_SMSURL =props.getProperty("qrcode.smsurl");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{if(fr != null){fr.close();}}catch(Exception e){}
		}
	}
	
	public String BizCode(String clientId){
		String[] bizCodes=props.getProperty("bankbizcode").split(",");
		if(bizCodes==null){
			return BIZCODE;
		}
		for(String bizCode:bizCodes){
		   String bizCodeName=bizCode.substring(0, bizCode.indexOf(":"));
		   if(bizCodeName.equals(clientId)){
			   return bizCode.substring(bizCode.indexOf(":")+1, bizCode.length());
		   }
			
		}
		
		return BIZCODE;
	}
	
	/**
     * 获取验证码图片url
     * @param content
     * @return String 二维码url
     */
    public String getQrWordImage(String content)throws TException {
    	QrCodeService.Iface client = (QrCodeService.Iface) ThriftClientProxyFactory.createIface(QrCodeService.class, QR_HOST, QR_PORT, QR_TIMEOUT);
    	return  client.generateWordImage(content); 
    }
    public static void main(String[] args) {
    	String bizCode1="anhui:1301,chongqing:1302";
    	String[] bizCodes=bizCode1.split(",");
    	for(String bizCode:bizCodes){
 		   String bizCodeName=bizCode.substring(0, bizCode.indexOf(":"));
 		   if(bizCodeName.equals("chongqing")){
 			   System.out.println(bizCode.substring(bizCode.indexOf(":")+1, bizCode.length())); 
 		   }
 			
 		}
	}
}
