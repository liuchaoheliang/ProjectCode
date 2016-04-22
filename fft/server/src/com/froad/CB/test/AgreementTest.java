package com.froad.CB.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.Agreement;
import com.froad.CB.service.AgreementService;


/** 
 * @author FQ 
 * @date 2013-1-29 下午05:30:12
 * @version 1.0
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) 
public class AgreementTest {
	
	@Autowired
	private AgreementService agreementService;
	
	@org.junit.Test
	public void addAgreement(){
		
		Agreement agreement=new Agreement();
		agreement.setType("0");
		agreement.setContent("测试");
		agreement.setState("30");
		Integer id=agreementService.addAgreement(agreement);
		System.out.println("保存ID:"+id);
	}
	
	@org.junit.Test
	public void getAgreementById(){
		Agreement agreement=agreementService.getAgreementById(100001001);
		System.out.println("内容："+agreement.getContent());
	}
	
	@org.junit.Test
	public void updateAgreement(){
		Agreement agreement=new Agreement();
		agreement.setId(100001001);
		agreement.setContent("测试更新协议内容");
		agreement.setRemark("更新测试");
		boolean result=agreementService.updateAgreement(agreement);
		System.out.println("结果:"+result);
	}
	
	
}
