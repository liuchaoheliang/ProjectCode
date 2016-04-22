package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.common.Command;
import com.froad.CB.dao.SmsLogDao;
import com.froad.CB.po.SmsLog;
import com.froad.CB.service.MessageService;
import com.froad.CB.thirdparty.MessageFunc;


@WebService(endpointInterface="com.froad.CB.service.MessageService")
public class MessageServiceImpl implements MessageService{

	private static final Logger logger=Logger.getLogger(MessageServiceImpl.class);
	
	private SmsLogDao smsLogDao;
	
	@Override
	public boolean sendMessage(SmsLog smsLog) {
		if(smsLog==null){
			logger.error("发短信的必填参数为空！");
			return false;
		}
		if(smsLog.getMobile()==null||smsLog.getMessage()==null){
			logger.error("手机号或短信内容为空！");
			return false;
		}
		smsLog.setState(Command.STATE_START);
		smsLog.setResult("2");
		Integer logId=smsLogDao.add(smsLog);
		boolean sendOk=MessageFunc.sendMessage(smsLog.getMobile(), smsLog.getMessage(), logId+"");
		smsLogDao.updateResultById(logId, sendOk);
		logger.info("短信发送结果："+sendOk+",logId="+logId);
		return sendOk;
	}

	@Override
	public boolean sendEmail(String subject, String content, List<String> toAddr) {
		if(subject==null||"".equals(subject)){
			logger.error("邮件主题不能为空");
			return false;
		}
		if(content==null||"".equals(content)){
			logger.error("邮件内容不能为空");
			return false;
		}
		if(toAddr==null||toAddr.size()==0){
			logger.error("邮件接收地址不能为空");
			return false;
		}
		return MessageFunc.sendEmail(subject, content, toAddr);
	}

	
	public void setSmsLogDao(SmsLogDao smsLogDao) {
		this.smsLogDao = smsLogDao;
	}
}
