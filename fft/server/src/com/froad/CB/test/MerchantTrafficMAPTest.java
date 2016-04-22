package com.froad.CB.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.merchant.MerchantTrafficMAP;
import com.froad.CB.service.MerchantTrafficMAPService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MerchantTrafficMAPTest {
	@Autowired
	MerchantTrafficMAPService merchantService;
	@Test
	public void test(){
		MerchantTrafficMAP merchantTrafficMAP = new MerchantTrafficMAP();
		merchantTrafficMAP.setMerchantId("100001002");
		merchantTrafficMAP.setCoordinate("1100,111");
		merchantService.updateMerchantTrafficMAP(merchantTrafficMAP);
	}
}
