
	 /**
  * 文件名：OutletPresellDeliveryMapperTest.java
  * 版本信息：Version 1.0
  * 日期：2014年3月29日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.test.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.froad.fft.persistent.api.impl.OutletPresellDeliveryMapperImpl;
import com.froad.fft.persistent.entity.OutletPresellDelivery;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月29日 下午1:00:45 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 
public class OutletPresellDeliveryMapperTest {
	@Resource
	OutletPresellDeliveryMapperImpl outletPresellDeliveryMapperImpl;
	
	@Test
	public void add(){
		OutletPresellDelivery delivery=new OutletPresellDelivery(); 
		delivery.setMerchantOutletId(111111L);
		delivery.setPresellDeliveryId(22222L);
		boolean flag=outletPresellDeliveryMapperImpl.saveOutletPresellDelivery(delivery);
		System.out.println(flag);
	}
	
	@Test
	public void select(){
		OutletPresellDelivery delivery=new OutletPresellDelivery(); 
		delivery.setMerchantOutletId(111111L);
		List<OutletPresellDelivery> list=outletPresellDeliveryMapperImpl.selectOutletPresellDeliveryByConditions(delivery);
		System.out.println(list.size());
		
//		System.out.println(JSON.toJSONString(list));
	}
	
	@Test
	public void delete(){
		OutletPresellDelivery delivery=new OutletPresellDelivery(); 
		delivery.setMerchantOutletId(111111L);
		delivery.setPresellDeliveryId(22L);
		boolean flag=outletPresellDeliveryMapperImpl.deleteOutletPresellDelivery(delivery);
		System.out.println(flag);
	}
	
	
}
