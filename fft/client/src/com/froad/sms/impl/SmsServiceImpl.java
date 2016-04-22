package com.froad.sms.impl;


import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.froad.action.support.MessageActionSupport;
import com.froad.bean.SmsConfig;
import com.froad.client.message.SmsLog;
import com.froad.sms.SmsService;
import com.froad.util.Command;
import com.froad.util.TemplateConfigUtil;
import com.froad.util.command.SmsLogType;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author FQ
 * @date 2013-3-1 下午01:43:18
 * @version 1.0
 * 发送短信
 */
public class SmsServiceImpl implements SmsService {

	private static final Logger logger = Logger.getLogger(SmsServiceImpl.class);

	private FreemarkerManager freemarkerManagerConfig;

	//private SmsLogActionSupport smsLogActionSupport;
	
	private MessageActionSupport messageActionSupport;
	

	public boolean sendUserRegister(String mobile, String userName, String code) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("username", userName);
		data.put("code", code);

		SmsConfig smsConfig = TemplateConfigUtil
				.getSmsConfig(SmsConfig.USER_REGISTER);
		String templateFilePath = smsConfig.getTemplateFilePath();

		try {
			Configuration cfg = new Configuration();
			String basePath = Command.TEMPLATE_PATH;
			cfg.setDirectoryForTemplateLoading(new File(basePath));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template temp = cfg.getTemplate(templateFilePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(temp, data);
			SmsLog smsLog = new SmsLog();
			smsLog.setMessage(text);
			smsLog.setMobile(mobile);
			smsLog.setType(SmsLogType.SMSLOG_USER_REGISTER);
			boolean result = messageActionSupport.sendMessage(smsLog);	
			return result;
		} catch (TemplateException e) {
			logger.error("sendUserRegister TemplateException:", e);
		} catch (IOException e) {
			logger.error("sendUserRegister IOException:", e);
		} catch (Exception e) {
			logger.error("sendUserRegister Exception:", e);
		}
		return false;
	}

	public boolean sendExchangeTicket(String mobile, String goodsName,
			String code, String useValidityEndTime, String telephone) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("goodsName", goodsName);
		data.put("code", code);
		data.put("useValidityEndTime", useValidityEndTime);
		data.put("telephone", telephone);

		SmsConfig smsConfig = TemplateConfigUtil
				.getSmsConfig(SmsConfig.EXCHANGE_TICKET);
		String templateFilePath = smsConfig.getTemplateFilePath();

		try {
			Configuration cfg = new Configuration();
			String basePath = Command.TEMPLATE_PATH;
			cfg.setDirectoryForTemplateLoading(new File(basePath));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template temp = cfg.getTemplate(templateFilePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(temp, data);
			SmsLog smsLog = new SmsLog();
			smsLog.setMessage(text);
			smsLog.setMobile(mobile);
			smsLog.setType(SmsLogType.SMSLOG_EXCHANGE_TICKET);
			boolean result = messageActionSupport.sendMessage(smsLog);
			
			return result;
		} catch (TemplateException e) {
			logger.error("sendExchangeTicket TemplateException:", e);
		} catch (IOException e) {
			logger.error("sendExchangeTicket IOException:", e);
		} catch (Exception e) {
			logger.error("sendExchangeTicket Exception:", e);
		}
		return false;
	}

	public boolean sendForgePassword(String mobile,String username, String password) {

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("password", password);
		data.put("username",username);
		SmsConfig smsConfig = TemplateConfigUtil.getSmsConfig(SmsConfig.FORGET_PASSWORD);
		String templateFilePath = smsConfig.getTemplateFilePath();

		try {
			Configuration cfg = new Configuration();
			String basePath = Command.TEMPLATE_PATH;
			cfg.setDirectoryForTemplateLoading(new File(basePath));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template temp = cfg.getTemplate(templateFilePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(temp, data);
			SmsLog smsLog = new SmsLog();
			smsLog.setMessage(text);
			smsLog.setMobile(mobile);
			smsLog.setType(SmsLogType.SMSLOG_FORGET_PASSWORD);
			boolean result = messageActionSupport.sendMessage(smsLog);
			
			return result;
		} catch (TemplateException e) {
			logger.error("sendForgePassword TemplateException:", e);
		} catch (IOException e) {
			logger.error("sendForgePassword IOException:", e);
		} catch (Exception e) {
			logger.error("sendForgePassword Exception:", e);
		}
		return false;
	}

	public boolean sendGroupTicket(String mobile, String code,
			String validityStateTime, String validityEndTime, String telephone) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("code", code);
		data.put("validityStateTime", validityStateTime);
		data.put("validityEndTime", validityEndTime);
		data.put("telephone", telephone);

		SmsConfig smsConfig = TemplateConfigUtil
				.getSmsConfig(SmsConfig.GROUP_TICKET);
		String templateFilePath = smsConfig.getTemplateFilePath();

		try {
			Configuration cfg = new Configuration();
			String basePath = Command.TEMPLATE_PATH;
			cfg.setDirectoryForTemplateLoading(new File(basePath));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template temp = cfg.getTemplate(templateFilePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(temp, data);
			SmsLog smsLog = new SmsLog();
			smsLog.setMessage(text);
			smsLog.setMobile(mobile);
			smsLog.setType(SmsLogType.SMSLOG_GROUP_TICKET);
			boolean result = messageActionSupport.sendMessage(smsLog);
			
			return result;
		} catch (TemplateException e) {
			logger.error("sendGroupTicket TemplateException:", e);
		} catch (IOException e) {
			logger.error("sendGroupTicket IOException:", e);
		} catch (Exception e) {
			logger.error("sendGroupTicket Exception:", e);
		}
		return false;
	}

	public boolean sendModifyMobile(String mobile, String code) {

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("code", code);

		SmsConfig smsConfig = TemplateConfigUtil
				.getSmsConfig(SmsConfig.MODIFY_MOBILE);
		String templateFilePath = smsConfig.getTemplateFilePath();
		try {
			Configuration cfg = new Configuration();
			String basePath = Command.TEMPLATE_PATH;
			cfg.setDirectoryForTemplateLoading(new File(basePath));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template temp = cfg.getTemplate(templateFilePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(temp, data);
			SmsLog smsLog = new SmsLog();
			smsLog.setMessage(text);
			smsLog.setMobile(mobile);
			smsLog.setType(SmsLogType.SMSLOG_MODIFY_MOBILE);
			boolean result = messageActionSupport.sendMessage(smsLog);
			
			return result;
		} catch (TemplateException e) {
			logger.error("sendModifyMobile TemplateException:", e);
		} catch (IOException e) {
			logger.error("sendModifyMobile IOException:", e);
		} catch (Exception e) {
			logger.error("sendModifyMobile Exception:", e);
		}
		return false;
	}

	public boolean sendModifyMobileCode(String mobile, String code) {

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("code", code);

		SmsConfig smsConfig = TemplateConfigUtil
				.getSmsConfig(SmsConfig.MODIFY_MOBILE_CODE);
		String templateFilePath = smsConfig.getTemplateFilePath();

		try {
			Configuration cfg = new Configuration();
			String basePath = Command.TEMPLATE_PATH;
			cfg.setDirectoryForTemplateLoading(new File(basePath));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template temp = cfg.getTemplate(templateFilePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(temp, data);
			SmsLog smsLog = new SmsLog();
			smsLog.setMessage(text);
			smsLog.setMobile(mobile);
			smsLog.setType(SmsLogType.SMSLOG_MODIFY_MOBILE_CODE);
			boolean result = messageActionSupport.sendMessage(smsLog);
			
			return result;
		}catch (IOException e) {
			logger.error("sendModifyMobileCode IOException:", e);
		} catch (Exception e) {
			logger.error("sendModifyMobileCode Exception:", e);
		}
		return false;
	}
	
	public boolean sendUserShare(String mobile,String formMobile,String name) {

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("mobile", formMobile);
		data.put("name", name);

		SmsConfig smsConfig = TemplateConfigUtil
				.getSmsConfig(SmsConfig.USER_SHARE);
		String templateFilePath = smsConfig.getTemplateFilePath();

		try {
			Configuration cfg = new Configuration();
			String basePath = Command.TEMPLATE_PATH;
			cfg.setDirectoryForTemplateLoading(new File(basePath));
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template temp = cfg.getTemplate(templateFilePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(temp, data);
			SmsLog smsLog = new SmsLog();
			smsLog.setMessage(text);
			smsLog.setMobile(mobile);
			smsLog.setType(SmsLogType.SMSLOG_USER_SHARE);
			boolean result = messageActionSupport.sendMessage(smsLog);
			return result;
			
		} catch (TemplateException e) {
			logger.error("sendUserShare TemplateException:", e);
		} catch (IOException e) {
			logger.error("sendUserShare IOException:", e);
		} catch (Exception e) {
			logger.error("sendUserShare Exception:", e);
		}
		return false;
	}
	
	public boolean sendClientMsg(String mobile, String text) {
		try {
			// 发送短信
			SmsLog smsLog = new SmsLog();
			smsLog.setMessage(text);
			smsLog.setMobile(mobile);
			smsLog.setType(SmsLogType.SMSLOG_CLIENT_MSG);
			boolean result = messageActionSupport.sendMessage(smsLog);
			return result;
		} catch (Exception e) {
			logger.error("sendUserShare Exception:", e);
		}
		return false;
	}

	// 记录日志
//	public void addSmsLog(String mobile, String content, String type,
//			boolean result) {
//
//		SmsLog smsLog = new SmsLog();
//		smsLog.setMobile(mobile);
//		smsLog.setMessage(content);
//		smsLog.setType(type);
//		smsLog.setState("30");
//		smsLog.setResult(result == true ? "1" : "0");
//
//		smsLogActionSupport.addSmsLog(smsLog);
//	}

//	private static boolean sendMsg(String telNo, String context) {
//
//		boolean flag = false;
//		String operater;
//
//		if (isMobile(telNo)) {
//			operater = "1";
//		} else if (isUnicom(telNo)) {
//			operater = "2";
//		} else if (isTelcomm(telNo)) {
//			operater = "3";
//		} else {
//			operater = "1";
//		}
//
//		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager(); // 多线程
//		// 多线程
//		HttpClient h = new HttpClient(connectionManager);
//		// String url=SysCommand.SHORTMESSAGE_URL+"/Send.do";
//		String url = Command.SHORTMESSAGE_URL;
////		String url = "http://210.14.71.66:12001/Send.do";
//		//http://210.14.71.66:12001
//		PostMethod p = new PostMethod(url);
//
//		logger.info("sms url：" + url);
//		logger.info("telNo:" + telNo);
//		logger.info("operater:" + operater);
//		logger.info("context:" + context);
//
//		p.getParams().setContentCharset("utf-8");
//		NameValuePair[] data = { new NameValuePair("num", telNo),
//				new NameValuePair("m", operater),
//				new NameValuePair("context", context) };
//		p.addParameters(data);
//
//		int status = 0;
//		try {
//			status = h.executeMethod(p);
//		} catch (HttpException e) {
//			logger.error("sendMsg HttpException", e);
//		} catch (ConnectException e) {
//			logger.error("sendMsg ConnectException", e);
//		} catch (SocketTimeoutException e) {
//			logger.error("sendMsg SocketTimeoutException:", e);
//		} catch (IOException e) {
//			logger.error("sendMsg IOException:", e);
//		} finally {
//			logger.info("与短信服务器连接HTTP状态:" + status);
//			if (status == 200) {
//				flag = true;
//			}
//			logger.info("通知返回结果:" + flag);
//
//		}
//		return flag;
//	}

	/**
	 * 方法描述：联通手机号码判断
	 */
	private static boolean isUnicom(String mobile) {
		return mobile.matches("^1(30|31|32|55|56|85|86|45)\\d{8}$");
	}

	/**
	 * 方法描述：电信手机号码判断
	 */
	private static boolean isTelcomm(String mobile) {
		return mobile.matches("^1(33|53|80|89)\\d{8}$");
	}

	/**
	 * 方法描述：移动手机号码判断
	 */
	private static boolean isMobile(String mobile) {
		return mobile.matches("^1(3[4-9]|47|5[012789]|8[278])\\d{8}$");
	}

	public FreemarkerManager getFreemarkerManagerConfig() {
		return freemarkerManagerConfig;
	}

	public void setFreemarkerManagerConfig(
			FreemarkerManager freemarkerManagerConfig) {
		this.freemarkerManagerConfig = freemarkerManagerConfig;
	}

	public MessageActionSupport getMessageActionSupport() {
		return messageActionSupport;
	}

	public void setMessageActionSupport(MessageActionSupport messageActionSupport) {
		this.messageActionSupport = messageActionSupport;
	}
	
}