package com.froad.CB.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.MerchantUserSet;
import com.froad.CB.service.MerchantUserSetService;


/** 
 * @author FQ 
 * @date 2013-1-29 下午05:30:12
 * @version 1.0
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) 
public class MerchantUserSetTest {
	
	@Autowired
	private MerchantUserSetService merchantUserSetService;
	
	@org.junit.Test
	public void addAgreement(){
		
		MerchantUserSet mu=new MerchantUserSet();
		mu.setMerchantId("100001001");
		mu.setUserId("abcdddd");
		mu.setLoginName("lance");
		mu.setBeCode("1000");
		mu.setBeCodepwd("123456");
		mu.setBeName("张三");
		mu.setBeSpec("测试");
		mu.setIsAdmin("1");
		mu.setState("30");
		mu.setRemark("测试");
		
		System.out.println("id:"+merchantUserSetService.addMerchantUserSet(mu));
	}
	
	@org.junit.Test
	public void updateMerchantUserSet(){
		MerchantUserSet mu=new MerchantUserSet();
//		mu.setMerchantId("100001001");
		mu.setUserId("10000006585");
		//mu.setLoginName("lance");
		mu.setBeCode("1018");
		mu.setBeCodepwd("123456");
//		mu.setBeName("张三");
		mu.setBeSpec("测试3");
//		mu.setIsAdmin("1");
		mu.setBelongStoreId("");
		
		mu.setState("30");
		mu.setRemark("测试3");
		merchantUserSetService.updateByUserIdAndBecode(mu);
	}
	
	
}
