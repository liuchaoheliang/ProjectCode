package com.froad.CB.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.po.TempPoint;
import com.froad.CB.service.impl.TempPointServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TempPointServiceTest {
	
	@Resource
	private TempPointServiceImpl tempPointServiceImpl;

	@Test
	public void getByPager(){
		TempPoint point=new TempPoint();
		TempPoint pager=tempPointServiceImpl.getTempPointByPager(point);
		System.out.println("pager: "+pager);
	}
}
