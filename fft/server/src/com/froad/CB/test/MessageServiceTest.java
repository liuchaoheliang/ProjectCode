package com.froad.CB.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.common.SmsLogType;
import com.froad.CB.po.SmsLog;
import com.froad.CB.service.impl.MessageServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MessageServiceTest {
	
	@Resource
	private MessageServiceImpl messageServiceImpl;

	@Test
	public void sendMessage(){
		SmsLog smsLog=new SmsLog();
		smsLog.setMobile("13112344321");
		smsLog.setMessage("hello world短信内容！");
		smsLog.setType(SmsLogType.SMS_LOG_HFCZ);
		messageServiceImpl.sendMessage(smsLog);
	}
}
