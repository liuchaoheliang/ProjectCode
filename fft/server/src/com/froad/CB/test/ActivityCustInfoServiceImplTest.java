package com.froad.CB.test;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.activity.ActivityCustInfo;
import com.froad.CB.service.activity.impl.ActivityCustInfoServiceImpl;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-7-24  
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ActivityCustInfoServiceImplTest {
	@Resource
	private ActivityCustInfoServiceImpl activityCustInfoServiceImpl;
	
	@Test
	public void testAdd() {
		ActivityCustInfo activityCustInfo = new ActivityCustInfo();
		activityCustInfo.setAccountName("liqiaopeng");
		activityCustInfo.setAccountNumber("44444444444444444");
		activityCustInfo.setIdentificationCard("362323199910206935");
		activityCustInfo.setBatchNumber("1");
		activityCustInfo.setType("1");
		activityCustInfoServiceImpl.add(activityCustInfo);
	}

	@Test
	public void testGetActivityCustInfoById() {
		activityCustInfoServiceImpl.getActivityCustInfoById(Integer.valueOf("100001002"));
	}

//	@Test
//	public void testUpdateById() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetActivityCustInfoBySelective() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetActivityCustInfoByPager() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetActivityCustInfoDao() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetActivityCustInfoDao() {
//		fail("Not yet implemented");
//	}

}
