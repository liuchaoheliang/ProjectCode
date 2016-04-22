package com.froad.CB.thirdparty;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.froad.jms.notification.base.IRequestSender;
import cn.com.froad.jms.notification.base.request.BizEnum;
import cn.com.froad.jms.notification.base.request.EmailNotifyRequest;
import cn.com.froad.jms.notification.base.request.SMSNotifyRequest;

import com.caucho.hessian.client.HessianProxyFactory;
import com.froad.CB.common.SysCommand;


	/**
	 * 类描述：消息的功能类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Jun 19, 2013 9:35:18 AM 
	 */
public class MessageFunc {

	
	private static Logger logger = Logger.getLogger(MessageFunc.class);
	
	private static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	private static HessianProxyFactory factory = new HessianProxyFactory();
	
	
	/**
	  * 方法描述：发送短信
	  * @param: phoneNumber 手机号
	  * @param: content 短信内容
	  * @param: logId 唯一标识该短信的编号
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jun 18, 2013 4:38:08 PM
	  */
	public static boolean sendMessage(String phoneNumber,String content,String logId){
		logger.info("开始发送短信，手机号："+phoneNumber+"短信内容："+content+"smsLog编号："+logId);
		if(phoneNumber==null||"".equals(phoneNumber)){
			logger.error("手机号为空，短信发送失败");
			return false;
		}
		if(!phoneNumber.matches("1[0-9]{10}")){
			logger.error("手机号格式不正确，短信发送失败，传入的手机号："+phoneNumber);
			return false;
		}
		SMSNotifyRequest sms=new SMSNotifyRequest();
		sms.setBizId(logId);
		sms.setBizCode(BizEnum.FFT.getCode());
		sms.addMobile(phoneNumber);
		Map<String,String> map=new HashMap<String,String>();
		map.put("value", content);
		sms.setData(map);
		IRequestSender sender=null;
		boolean sendOk=false;
		try {
			sender=(IRequestSender) factory.create(IRequestSender.class, SysCommand.SMS_URL);
			sendOk=sender.send(sms);
		} catch (MalformedURLException e) {
			logger.error("短信发送异常",e);
			return false;
		}catch(Exception e){
			logger.error("短信发送异常",e);
			return false;
		}
		logger.info("==========短信发送结果："+sendOk);
		return sendOk;
	}
	
	
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
	public static boolean sendEmail(String subject,String content,List<String> toAddr){
		EmailNotifyRequest mailRequest  = new EmailNotifyRequest();
		mailRequest.setFromUserName("分分通平台");
		mailRequest.setSubject(subject);
		mailRequest.setRecAddress(toAddr);
		mailRequest.setBizCode(BizEnum.FFT.getCode());
		mailRequest.setBizId(dateFormat.format(new Date()));
		HashMap<String,String> map  = new HashMap<String,String>();
		map.put("value",content);
		mailRequest.setData(map);
		
		IRequestSender sender=null;
		boolean sendOk=false;
		try {
			sender=(IRequestSender) factory.create(IRequestSender.class, SysCommand.SMS_URL);
			sendOk=sender.send(mailRequest);
		} catch (MalformedURLException e) {
			logger.error("邮件发送异常",e);
			return false;
		}catch(Exception e){
			logger.error("邮件发送异常",e);
			return false;
		}
		logger.info("==========邮件发送结果："+sendOk);
		return sendOk;
	}
	
}
