package com.froad.fft.test.thirdparty;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.common.enums.SmsType;
import com.froad.fft.thirdparty.dto.request.sms.SmsBean;
import com.froad.fft.thirdparty.request.sms.impl.SMSMessageFuncImpl;

public class SMSMessageFunc_Test {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:context/**/*.xml");
		SMSMessageFuncImpl sMSMessageFuncImpl = (SMSMessageFuncImpl) context.getBean("smsMessageFuncImpl");
		
		System.out.println(JSONObject.toJSONString(sMSMessageFuncImpl.sendSMSMessage(new SmsBean(SmsType.authcodeModifiedMobile, 1L, "13527459070", new String[]{"参数一","参数二"}, "froadfft","127.0.0.1",false))));
	}
}
