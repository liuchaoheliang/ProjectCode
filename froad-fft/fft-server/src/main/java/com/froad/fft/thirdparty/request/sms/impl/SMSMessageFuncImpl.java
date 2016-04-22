package com.froad.fft.thirdparty.request.sms.impl;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.froad.jms.notification.base.IRequestSender;
import cn.com.froad.jms.notification.base.request.BizEnum;
import cn.com.froad.jms.notification.base.request.SMSNotifyRequest;

import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianProxyFactory;
import com.froad.fft.dto.SmsDto;
import com.froad.fft.persistent.entity.SmsContent;
import com.froad.fft.persistent.entity.SmsLog;
import com.froad.fft.service.SmsContentService;
import com.froad.fft.service.SmsLogService;
import com.froad.fft.thirdparty.common.SMSMessageCommand;
import com.froad.fft.thirdparty.common.SystemCommand;
import com.froad.fft.thirdparty.dto.request.sms.SmsBean;
import com.froad.fft.thirdparty.request.sms.SMSMessageFunc;
import com.froad.fft.util.StrUtils;

@Service("smsMessageFuncImpl")
public class SMSMessageFuncImpl implements SMSMessageFunc {
	
	private static Logger logger = Logger.getLogger(SMSMessageFuncImpl.class);
	private static HessianProxyFactory factory = new HessianProxyFactory();
	
	@Resource(name="smsLogServiceImpl")
	private SmsLogService smsLogService;
	
	@Resource(name="smsContentServiceImpl")
	private SmsContentService smsContentService;
	
	@Override
	public SmsDto sendSMSMessage(SmsBean smsBean) {		
		SmsContent smsContent = new SmsContent(smsBean.getSmsLog().getSmsType(), smsBean.getSmsLog().getClientId());
		smsContent = smsContentService.selectSmsContentToSendMsg(smsContent);
		if(smsContent == null){
			logger.info("获取短信模版失败");
			return new SmsDto(false, "获取短信模版失败");
		}
		
		//将短信模版和参数转换成对应的实际内容
		String content = StrUtils.getContent(smsContent.getContent(), smsBean.getValueArgs());
		if(smsContent.getIsEnableSuffix()){//判断后缀是否启用
			content += smsContent.getMsgSuffix();
		}
		smsBean.setContent(content);
		SmsLog smsLog = smsBean.getSmsLog();
		Long id = smsLogService.saveSmsLog(smsLog);//将发送短信的信息记录于数据库
		
		if(id == null){
			logger.error("发送短信时向数据库保存短信日志失败: " + JSONObject.toJSONString(smsLog));
			return new SmsDto(false, "保存短信日志失败");
		}
		
		if(smsBean.isCheck()){
			logger.info("开始进行短信频繁校验");
			//TODO : 如果开启的短信频繁验证，则do something
		}
		SMSNotifyRequest sms = new SMSNotifyRequest();
		sms.setBizId(SystemCommand.FFT_CLIENT_PREFIX + id);
		sms.setBizCode(BizEnum.FFT.getCode());
		sms.addMobile(smsLog.getMobile());
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", smsLog.getContent());
		sms.setData(map);
		boolean flag = false;
		try {
			IRequestSender sender = (IRequestSender)factory.create(IRequestSender.class, SMSMessageCommand.SMS_URL);
			logger.info("开始发送短信: 短信地址: " + SMSMessageCommand.SMS_URL + "|参数: " + JSONObject.toJSONString(smsLog));
			flag = sender.send(sms);
		} catch (MalformedURLException e) {
			logger.error("发送短信异常 手机号码为: <" + smsLog.getMobile()+">|短信地址: " + SMSMessageCommand.SMS_URL + "|参数: " + smsLog.getMobile(), e);
			return new SmsDto(false, "发送短信异常");
		}
		if(flag){
			logger.info("向手机号码为<" + smsLog.getMobile()+"> 发送短信成功");
			logger.info("更改短信日志库对应短信发送成功状态结果:" + smsLogService.updateSmsLogStatusById(id));
		}else{
			logger.info("向手机号码为<" + smsLog.getMobile()+"> 发送短信失败");
		}
		return new SmsDto(true, "发送短信成功");
	}


}
