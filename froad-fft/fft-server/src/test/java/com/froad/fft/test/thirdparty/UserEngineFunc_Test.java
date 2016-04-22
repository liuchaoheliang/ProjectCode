package com.froad.fft.test.thirdparty;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.thirdparty.request.userengine.impl.UserEngineFuncImpl;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.helper.CreateChannel;
import com.pay.user.helper.MemberType;

public class UserEngineFunc_Test {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:context/**/*.xml");
		UserEngineFuncImpl userEngineFuncImpl = (UserEngineFuncImpl) context.getBean("userEngineFuncImpl");	
		UserSpecDto userSpecDto = new UserSpecDto();
		
//		userSpecDto.setLoginID("fenfentong1");
//		userSpecDto.setLoginPwd("123456");
//		userSpecDto.setRegisterIP("13.14.15.16");
//		userSpecDto.setMemberType(MemberType.PERSONAL.getValue());
//		userSpecDto.setCreateChannel(CreateChannel.FFT.getValue());
//		System.out.println(userEngineFuncImpl.registerMember(userSpecDto).getErrorMsg());
		
//		System.out.println(userEngineFuncImpl.login("fenfentong1", "123456", "192.168.1.1").getErrorMsg());
		System.out.println(JSONObject.toJSONString(userEngineFuncImpl.queryByLoginID("fenfentong")));
	}
}
