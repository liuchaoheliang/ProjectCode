
	 /**
  * 文件名：TransDetailsTest.java
  * 版本信息：Version 1.0
  * 日期：2014年3月31日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.test.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.fft.persistent.api.impl.TransDetailsMapperImpl;
import com.froad.fft.persistent.entity.TransDetails;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月31日 下午5:59:56 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 

public class TransDetailsTest {
	@Resource
	
	TransDetailsMapperImpl detailsMapperImpl;
	TransDetails transDetails=new TransDetails();
	
	@Test
	public void add(){
//		transDetails.setCreateTime(new Date());
//		transDetails.setUpdateTime(new Date());
		transDetails.setTransId(123123L);
		transDetails.setPrice("23");
		transDetails.setProductId(123123L);
		transDetails.setQuantity(123);
		transDetails.setProductName("productName");
		transDetails.setSingle("single");
		transDetails.setSupplyMerchantId(12123L);
		transDetails.setGatherMerchantId(1231L);
		List<TransDetails> list=new ArrayList<TransDetails>();
		list.add(transDetails);
		
//		Long id=detailsMapperImpl.saveTransDetails(transDetails);
//		System.out.println(id);
 		detailsMapperImpl.saveBatchTransDetails(list);
	}
	
	
	
}
