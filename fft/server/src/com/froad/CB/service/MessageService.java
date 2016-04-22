package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.SmsLog;


	/**
	 * 类描述：消息服务接口
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Jun 18, 2013 5:17:30 PM 
	 */
@WebService
public interface MessageService {

	
	/**
	  * 方法描述：发送短信
	  * @param: SmsLog(mobile,message,type) 
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jun 18, 2013 5:14:21 PM
	  */
	public boolean sendMessage(SmsLog smsLog);
	
	
	/**
	  * 方法描述：发送邮件
	  * @param: subject 邮件主题
	  * @param: content 邮件内容
	  * @param: toAddr 接收邮件的地址列表
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Aug 13, 2013 11:23:19 AM
	  */
	public boolean sendEmail(String subject,String content,List<String> toAddr);
}
