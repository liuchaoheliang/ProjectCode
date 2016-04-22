/**
 * Project Name:froad-cbank-server-boss-report
 * File Name:MessageServiceClient.java
 * Package Name:com.froad.util
 * Date:2015年8月24日下午3:55:02
 * Copyright (c) 2015, F-Road, Inc.
 */
package com.froad.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.froad.logback.LogCvt;



/**
 * 消息服务
 */
public class MessageServiceClient {
	
	//社区银行BOSS
	private static final String BIZCODE_BOSS = "1302";
	
	private static final String SUCCESS_CODE = "0000";
	
	private static final String EMAIL_URL = PropertiesUtil.getProperty("thirdparty", "MAIL_SERVICE_URL");
//	private static final String EMAIL_URL = "http://10.43.2.1:20401/message-service-0.0.1/email";
	
	static final String KEY_FILENAME = "boss-report";
	static final String KEY_MAIL_SUBJECT = ".mail.subject";
	static final String KEY_MAIL_CONTENT = ".mail.content";
	static final String KEY_MAIL_TOLIST = ".mail.tolist";
	static final String KEY_MAIL_CCLIST = ".mail.cclist";
	
	public static boolean sendEmailByBossReport(String clientId, String fromUserName, Map<String, byte[]> atts){
		String subject = PropertiesUtil.getProperty(KEY_FILENAME, clientId + KEY_MAIL_SUBJECT);
		String content = PropertiesUtil.getProperty(KEY_FILENAME, clientId + KEY_MAIL_CONTENT);
		
		String tolist = PropertiesUtil.getProperty(KEY_FILENAME, clientId + KEY_MAIL_TOLIST);
		List<String> recAddress = new ArrayList<String>();
		String[] tolistArr = tolist.split(",");
		for (int i = 0; i < tolistArr.length; i++) {
			recAddress.add(tolistArr[i]);
		}
		
		String cclist = PropertiesUtil.getProperty(KEY_FILENAME, clientId + KEY_MAIL_CCLIST);
		List<String> ccAddress = new ArrayList<String>();
		String[] cclistArr = cclist.split(",");
		for (int i = 0; i < cclistArr.length; i++) {
			ccAddress.add(cclistArr[i]);
		}
		
		return sendEmail(EMAIL_URL, subject, content, fromUserName, recAddress, ccAddress, atts, BIZCODE_BOSS);
	}
	
	/**
	 * 发送邮件
	 * @param mailServerUrl 邮件服务的url
	 * @param subject 主题
	 * @param content 内容
	 * @param fromAddress 发件人地址
	 * @param fromUserName 发件人名称
	 * @param recAddress 接收人地址
	 * @param ccAddress 抄送人地址
	 * @param bizNo 流水号
	 * @param atts 附件
	 * @return
	 */
	public static boolean sendEmail(String mailServerUrl, String subject, String content, String fromUserName, List<String> recAddress, List<String> ccAddress, Map<String, byte[]> atts, String bizcode){
		LogCvt.info("开始发送邮件");
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("subject", subject);
		jsonObj.put("recAddress", recAddress);
		jsonObj.put("fromAddress", null);
		jsonObj.put("fromUserName", fromUserName);
		jsonObj.put("ccAddress", ccAddress);
		jsonObj.put("attachments", atts);
		jsonObj.put("type", "EMAIL");
		Map<String,String> data = new HashMap<String,String>();
		data.put("value", content);
		jsonObj.put("data", data);
		jsonObj.put("templateId", 0);
		jsonObj.put("bizCode", bizcode);
		
		JSONObject resJson = null;
		String res = HttpClientUtil.httpPost(mailServerUrl, jsonObj.toJSONString());
		resJson = JSONObject.parseObject(res);
		
		if(SUCCESS_CODE.equals(resJson.getString("returnCode"))){
			LogCvt.info("发送邮件成功");
			return true;
		}else{
			LogCvt.info("发送邮件失败：" + resJson.getString("returnMessage"));
			return false;
		}
		
		
		
	}
}
