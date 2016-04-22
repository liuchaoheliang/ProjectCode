package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.smsLog.SmsLog;
import com.froad.client.smsLog.SmsLogService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 短信发送记录  client service  ActionSupport
 */
public class SmsLogActionSupport {
	private static Logger logger = Logger.getLogger(SmsLogActionSupport.class);
	private SmsLogService smsLogService;
	
	public Integer addSmsLog(SmsLog smsLog){
		return smsLogService.add(smsLog);
	}
	
	public SmsLogService getSmsLogService() {
		return smsLogService;
	}
	public void setSmsLogService(SmsLogService smsLogService) {
		this.smsLogService = smsLogService;
	}
	
}
