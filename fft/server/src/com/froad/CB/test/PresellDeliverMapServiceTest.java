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
import com.froad.CB.dao.impl.PresellDeliveryMapDaoImpl;
import com.froad.CB.dao.impl.PresentPointRuleDaoImpl;
import com.froad.CB.po.PresellDeliveryMap;
import com.froad.CB.po.PresentPointRule;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class PresellDeliverMapServiceTest
{
    @Resource
    private PresellDeliveryMapDaoImpl presellDeliveryMapDaoImpl;

//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
//
//    @Test
//    public void add()
//    {
//        PresellDeliveryMap presellDeliveryMap = new PresellDeliveryMap();
//        presellDeliveryMap.setDeliveryId(10000001);
//        presellDeliveryMap.setPresellRackId(10000003);
//        System.out.println(presellDeliveryMapDaoImpl.add(presellDeliveryMap));
//    }
//
//    @Test
//    public void select()
//    {
//        //		PresellDeliveryMap presellDeliveryMap=new PresellDeliveryMap();
//        //		presellDeliveryMap.setId(100000001);
//        JSONObject.toJSONString(presellDeliveryMapDaoImpl.getById(100000001));
//    }
//
//    @Test
//    public void update()
//    {
//        PresellDeliveryMap presellDeliveryMap = new PresellDeliveryMap();
//        presellDeliveryMap.setId(100000001);
//        presellDeliveryMap.setDeliveryId(10000002);
//        presellDeliveryMap.setPresellRackId(10000004);
//
//        System.out.println(presellDeliveryMapDaoImpl.updateById(presellDeliveryMap));
//    }

    @Test
    public void query()
    {
        PresellDeliveryMap presellDeliveryMap = new PresellDeliveryMap();

        presellDeliveryMap.setPresellRackId(10000004);
        presellDeliveryMap.setPageSize(6);
        System.out.println(presellDeliveryMapDaoImpl.getBypager(presellDeliveryMap));
    }
	
	
	@Test
	public void getByPager(){
		PresellDeliveryMap presellDeliveryMap=new PresellDeliveryMap();
		presellDeliveryMap.setPresellRackId(100001021);
		presellDeliveryMapDaoImpl.getBypager(presellDeliveryMap);
	}
}
