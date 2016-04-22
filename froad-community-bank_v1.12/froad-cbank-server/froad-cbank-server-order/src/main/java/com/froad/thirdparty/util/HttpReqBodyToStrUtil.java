//package com.froad.thirdparty.util;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//
//import javax.servlet.ServletInputStream;
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.log4j.Logger;
//
//import com.froad.cbank.sysconfig.SystemConfig;
//import com.froad.cbank.util.SystemConfigUtil;
//import com.froad.cbank.util.XmlFormatUtil;
//
///**
// * *******************************************************
// *<p> 工程: fft-server </p>
// *<p> 类名: HttpReqBodyToStrUtil.java </p>
// *<p> 描述: *-- <b>用于将httpservletbody中的字符流转换成字符串</b> --* </p>
// *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
// *<p> 时间: 2014年1月20日 下午1:51:26 </p>
// ********************************************************
// */
//public class HttpReqBodyToStrUtil {
//
//	private static Logger logger = Logger.getLogger(HttpReqBodyToStrUtil.class);
//	
//	public static String toXMLStr(HttpServletRequest req){
//		StringBuffer buffer = new StringBuffer();
//		try {
//			req.setCharacterEncoding("UTF-8");
//			ServletInputStream inputStream = req.getInputStream();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//			String line = null;			
//			while ((line = reader.readLine()) != null) {
//				buffer.append(line);
//			}
//			reader.close();
//			inputStream.close();
//		} catch (UnsupportedEncodingException e) {
//			logger.error("httpservletbody中的字符流转换成字符串发生异常", e);
//		} catch (IOException e) {
//			logger.error("httpservletbody中的字符流转换成字符串发生异常", e);
//		}		
//		String noticeXML = buffer.toString();
//		
//		SystemConfig systemConfig=SystemConfigUtil.getSystemConfig();
//		if(systemConfig.getIsSystemDebug()){
//			logger.info("接收第三方系统请求的xml:\n " + XmlFormatUtil.format(noticeXML));
//		}else{
//			logger.info("接收第三方系统请求的xml:\n" + noticeXML);
//		}
//		return noticeXML;
//	}
//}
