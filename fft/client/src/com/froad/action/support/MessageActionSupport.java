package com.froad.action.support;

import org.apache.log4j.Logger;
import com.froad.client.message.MessageService;
import com.froad.client.message.SmsLog;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-6-19  
 * @version 1.0
 * Message 发送短信新support
 */
public class MessageActionSupport {
	private static Logger logger = Logger.getLogger(MessageActionSupport.class);
	private MessageService messageService;
	
	/**
	 * 发送短信
	 * @param smsLog
	 * @return
	 */
	public boolean sendMessage(SmsLog smsLog){
		return messageService.sendMessage(smsLog);
	}
	
	public MessageService getMessageService() {
		return messageService;
	}
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
}
