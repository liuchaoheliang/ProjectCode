package com.froad.CB.test.trans;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.froad.CB.po.TransDetails;
import com.froad.CB.service.impl.TransDetailsServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TransDetailsServiceTest{
	
	@Resource
	private TransDetailsServiceImpl transDetailsServiceImpl;
	
	@Test
	public void addTransDetails() {
		TransDetails transDetails=new TransDetails();
		transDetails.setBankPointsValueAll("10");
		transDetails.setCurrency("RMB");
		transDetails.setState("10");
		transDetails.setRemark("测试");
		
		transDetailsServiceImpl.addTransDetails(transDetails);
	}

	@Test
	public void deleteById() {
		Integer id=100001002;
		
		transDetailsServiceImpl.deleteById(id);
	}

	@Test
	public void getTransDetailsById() {
		Integer id=100001001;
		
		transDetailsServiceImpl.getTransDetailsById(id);
	}

	@Test
	public void updateById() {
		TransDetails transDetails=new TransDetails();
		transDetails.setId(100001001);
		transDetails.setState("30");
		transDetails.setCurrencyValue("3");
		transDetails.setCurrencyValueReal("3");
		
		transDetailsServiceImpl.updateById(transDetails);
	}

	@Test
	public void updateStateById() {
		Integer id=100001001;
		String state="50";
		
		transDetailsServiceImpl.updateStateById(id, state);
	}

}
