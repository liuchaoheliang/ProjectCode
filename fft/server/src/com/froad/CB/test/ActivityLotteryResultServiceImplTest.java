package com.froad.CB.test;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.activity.ActivityLotteryResult;
import com.froad.CB.service.activity.impl.ActivityCustInfoServiceImpl;
import com.froad.CB.service.activity.impl.ActivityLotteryResultServiceImpl;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-7-24  
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ActivityLotteryResultServiceImplTest {
	@Resource
	private ActivityLotteryResultServiceImpl activityLotteryResultServiceImpl;
	
	@Test
	public void testAdd() {
		ActivityLotteryResult activityLotteryResult = new ActivityLotteryResult();
		activityLotteryResult.setUserId("1111111111111111111111111111111");
		activityLotteryResult.setLotteryCustId("100001002");
		activityLotteryResult.setType("1");
		activityLotteryResult.setSecuritiesNo("478472938471385kjfdajjdkjldkf8");
		activityLotteryResult.setExpireTime("2013-08-31|12:00:00");
		activityLotteryResultServiceImpl.add(activityLotteryResult);
	}

	@Test
	public void testGetActivityLotteryResultById() {
		activityLotteryResultServiceImpl.getActivityLotteryResultById(Integer.valueOf("100001001"));
	}
//
//	@Test
//	public void testUpdateById() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUpdateBySmsCountId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetActivityLotteryResultBySelective() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetActivityLotteryResultByPager() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetActivityLotteryResultDao() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetActivityLotteryResultDao() {
//		fail("Not yet implemented");
//	}

}
