package com.froad.CB.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.AppException.AppException;
import com.froad.CB.po.UserCertification;
import com.froad.CB.service.impl.UserCertificationServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class UserCertificationServiceTest {

	@Resource
	private UserCertificationServiceImpl userCertificationServiceImpl;
	
	@Test
	public void add(){
		UserCertification cert=new UserCertification();
		cert.setUserId("100001001");
		cert.setAccountName("张三");
		cert.setAccountNo("66601234");
		cert.setState("30");
		cert.setCertificationType("01");//认证类型：00手机贴膜卡认证，01信通卡认证
		cert.setChannelId("100001001");
		try {
			userCertificationServiceImpl.add(cert);
		} catch (AppException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getCertByUserId(){
		String userId="100001001";
		UserCertification cert=userCertificationServiceImpl.getUserCertByUserId(userId);
		System.out.println("cert.acctName==》"+cert.getAccountName());
	}
	
	@Test
	public void getCertBySelective(){
		UserCertification cert=new UserCertification();
		
		List<UserCertification> list=userCertificationServiceImpl.getUserCertBySelective(cert);
		System.out.println("list.size=="+list.size());
	}
}
