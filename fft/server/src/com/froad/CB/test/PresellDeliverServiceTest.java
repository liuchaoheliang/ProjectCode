package com.froad.CB.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.froad.CB.dao.impl.PresellDeliveryDaoImpl;
import com.froad.CB.dao.impl.PresentPointRuleDaoImpl;
import com.froad.CB.po.PresellDelivery;
import com.froad.CB.po.PresentPointRule;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class PresellDeliverServiceTest {
	@Resource
	private PresellDeliveryDaoImpl presellDeliveryDaoImpl;
	
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
	
	
	
	@Test
	public void add(){
		PresellDelivery presellDelivery=new PresellDelivery();
		presellDelivery.setName("浦东新区提货点");
		presellDelivery.setAddress("测试地址");
		presellDelivery.setTelephone("13038389991");
		presellDeliveryDaoImpl.add(presellDelivery);
		presellDeliveryDaoImpl.add(presellDelivery);
		presellDeliveryDaoImpl.add(presellDelivery);
		presellDeliveryDaoImpl.add(presellDelivery);
		presellDeliveryDaoImpl.add(presellDelivery);
		presellDeliveryDaoImpl.add(presellDelivery);
		presellDeliveryDaoImpl.add(presellDelivery);
	}
		
	@Test
	public void select(){
		
		PresellDelivery presellDelivery=new PresellDelivery();
		
		presellDelivery=presellDeliveryDaoImpl.getByPager(presellDelivery);
		System.out.println(presellDelivery.getList().size());
//		JSONObject.toJSONString(presellDeliveryDaoImpl.getById(100000001));
		
	}
	
	@Test
	public void update(){
		PresellDelivery presellDelivery=new PresellDelivery();
		presellDelivery.setId(100000001);
		presellDelivery.setName("修改浦东新区提货点");
		presellDelivery.setAddress("修改测试地址");
		presellDelivery.setTelephone("13272549765");
		presellDelivery.setState("1");
		presellDeliveryDaoImpl.updateById(presellDelivery);
	}
}
