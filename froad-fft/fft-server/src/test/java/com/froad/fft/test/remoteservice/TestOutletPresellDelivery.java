
	 /**
  * 文件名：TestOutletPresellDelivery.java
  * 版本信息：Version 1.0
  * 日期：2014年3月29日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.test.remoteservice;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.froad.fft.api.service.OutletPresellDeliveryExportService;
import com.froad.fft.api.service.impl.OutletPresellDeliveryExportServiceImpl;
import com.froad.fft.dto.OutletPresellDeliveryDto;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月29日 下午3:25:47 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:context/servlet/applicationContext-mapper-remote.xml",
		"classpath:context/servlet/applicationContext-service-remote.xml",
		"classpath:context/applicationContext.xml",
		"classpath:context/applicationContext-expand.xml"
		}) 
public class TestOutletPresellDelivery {
	@Resource 
	OutletPresellDeliveryExportService deliveryExportService;
	
	@Test
	public void add(){
		OutletPresellDeliveryDto deliveryDto=new OutletPresellDeliveryDto();		
		deliveryDto.setMerchantOutletId(2131231L);
		deliveryDto.setPresellDeliveryId(444444L);
		
		boolean flag=deliveryExportService.add(null, null, deliveryDto);
		System.out.println(flag);
	}
	
	@Test
	public void select(){
		OutletPresellDeliveryDto deliveryDto=new OutletPresellDeliveryDto();	
		deliveryDto.setMerchantOutletId(2131231L);
		List<OutletPresellDeliveryDto> list=deliveryExportService.selectOutletPresellDeliveryDtoByConditions(null, null, deliveryDto);
		System.out.println(JSON.toJSONString(list));
	}
}
