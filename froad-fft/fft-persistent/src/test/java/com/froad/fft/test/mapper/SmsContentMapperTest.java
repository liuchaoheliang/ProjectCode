package com.froad.fft.test.mapper;
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.impl.SmsContentMapperImpl;
import com.froad.fft.persistent.common.enums.SmsType;
import com.froad.fft.persistent.entity.SmsContent;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"})
public class SmsContentMapperTest {

	@Resource
	private SmsContentMapperImpl smsContentMapperImpl;
	
	@Test
	public void test(){
		SmsContent smsContent = new SmsContent();
		smsContent.setClientId(1L);
		smsContent.setSmsType(SmsType.authcodeRegister);
		System.out.println(JSONObject.toJSONString(smsContentMapperImpl.selectSmsContentToSendMsg(smsContent)));
	}
}
