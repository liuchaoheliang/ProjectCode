package com.froad.CB.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.froad.CB.dao.impl.PresellBuyInfoDaoImpl;
import com.froad.CB.dao.impl.PresellDeliveryDaoImpl;
import com.froad.CB.dao.impl.PresentPointRuleDaoImpl;
import com.froad.CB.po.PresellBuyInfo;
import com.froad.CB.po.PresentPointRule;
import com.froad.CB.service.impl.PresellBuyInfoServiceImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class PresellBuyInfoServiceTest {
	@Resource
	private PresellBuyInfoDaoImpl presellBuyInfoDaoImpl;
	private PresellBuyInfoServiceImpl presellBuyInfoServiceImpl;
	
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
	
	
	
	@Test
	public void add(){
		PresellBuyInfo presellBuyInfo=new PresellBuyInfo();
		presellBuyInfo.setMemberCode("120012010");
		presellBuyInfo.setGoodsPresellRackId(100001008);
		presellBuyInfo.setTransId(120000441);		
		presellBuyInfo.setGoodsPresellRackId(1278176281);
		presellBuyInfo.setPresellDeliveryId(213123144);
		presellBuyInfo.setTransId(1231231341);		
		presellBuyInfo.setTotalGoodsNum("32");
		presellBuyInfoDaoImpl.add(presellBuyInfo);
		
		
	}
		
	@Test
	public void select(){
//	JSONObject.toJSONString(presellBuyInfoDaoImpl.getById(100000001));
		PresellBuyInfo presellBuyInfo=new PresellBuyInfo();
		presellBuyInfo.setTransId(100002626);
//		List<PresellBuyInfo> list=presellBuyInfoDaoImpl.getByConditions(presellBuyInfo);
//		PresellBuyInfoServiceImpl presellBuyInfoServiceImpl=new PresellBuyInfoServiceImpl();
		List<PresellBuyInfo> list=presellBuyInfoServiceImpl.getByConditions(presellBuyInfo);
		System.out.println(list.size());
		
	}
	
	@Test
	public void update(){
		
		PresellBuyInfo presellBuyInfo=new PresellBuyInfo();
		presellBuyInfo.setId(100000001);
		presellBuyInfo.setMemberCode("abcdefg");
		presellBuyInfo.setGoodsPresellRackId(127000001);
		presellBuyInfo.setPresellDeliveryId(20003144);
		presellBuyInfo.setTransId(120000441);		
		presellBuyInfo.setTotalGoodsNum("99");
		presellBuyInfoDaoImpl.updateById(presellBuyInfo);	
	}
	
	@Test
	public void getByTransIds(){
		
		List<String> list=new ArrayList<String>();
		list.add("120000447");
		list.add("120000441");
		list.add("120000442");
		System.out.println(presellBuyInfoDaoImpl.getByTransId(list).size());
		
	}
}
