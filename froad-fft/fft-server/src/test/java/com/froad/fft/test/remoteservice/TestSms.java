package com.froad.fft.test.remoteservice;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.fft.api.service.impl.SMSExportServiceImpl;
import com.froad.fft.dto.SmsDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.enums.SmsType;
import com.froad.fft.thirdparty.dto.request.sms.SmsBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:context/servlet/applicationContext-mapper-remote.xml",
		"classpath:context/servlet/applicationContext-service-remote.xml",
		"classpath:context/applicationContext.xml",
		"classpath:context/applicationContext-expand.xml"
		}) 
public class TestSms {

	@Resource
	private SMSExportServiceImpl smsExportServiceImpl;
	
	@Test
	public void sendSMS(){
		String[] args = new String[]{"a","b"};
		SmsDto smsDto = new SmsDto(SmsType.authcodeModifiedMobile, "fenfentong", args, "13527459070","127.0.0.1",true);
		System.out.println(smsExportServiceImpl.sendSms(ClientAccessType.chongqing,ClientVersion.version_1_0,smsDto));
	}
}
