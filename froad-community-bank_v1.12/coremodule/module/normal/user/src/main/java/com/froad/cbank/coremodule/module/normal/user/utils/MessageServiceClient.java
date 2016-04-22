package com.froad.cbank.coremodule.module.normal.user.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;



/**
 * 消息服务
 */
public class MessageServiceClient {
	
	//社区银行
	private static final String BIZCODE = "1301";

	private static final String SUCCESS_CODE = "0000";
	
	private static final String EMAIL_URL = Constants.MESSAGE_SERVICE_URL + "email";
//	private static final String EMAIL_URL = "http://10.43.2.1:20401/message-service-0.0.1/email";
	
	
	
	
	
	/**
	 * 发送邮件
	 * @param subject 主题
	 * @param content 内容
	 * @param fromAddress 发件人地址
	 * @param fromUserName 发件人名称
	 * @param recAddress 接收人地址
	 * @param ccAddress 抄送人地址
	 * @param bizNo 流水号
	 * @return
	 */
	public static boolean sendEmail(String subject, String content, String fromUserName, List<String> recAddress, List<String> ccAddress){
		LogCvt.info("开始发送邮件");
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("subject", subject);
		jsonObj.put("recAddress", recAddress);
		jsonObj.put("fromAddress", null);
		jsonObj.put("fromUserName", fromUserName);
		jsonObj.put("ccAddress", ccAddress);
		jsonObj.put("attachments", null);
		jsonObj.put("type", "EMAIL");
		Map<String,String> data = new HashMap<String,String>();
		data.put("value", content);
		jsonObj.put("data", data);
		jsonObj.put("templateId", 0);
		jsonObj.put("bizCode", BIZCODE);
		
		JSONObject resJson = null;
		
		resJson = HttpClientUtil.doPost(EMAIL_URL, jsonObj);
		
		if(SUCCESS_CODE.equals(resJson.getString("returnCode"))){
			LogCvt.info("发送邮件成功");
			return true;
		}else{
			LogCvt.info("发送邮件失败：" + resJson.getString("returnMessage"));
			return false;
		}
		
		
		
	}
}
