
	 /**
  * 文件名：ReturnSaleDetailMapperTest.java
  * 版本信息：Version 1.0
  * 日期：2014年4月1日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.test.mapper;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.fft.persistent.api.ReturnSaleDetailMapper;
import com.froad.fft.persistent.api.impl.ReturnSaleDetailMapperImpl;
import com.froad.fft.persistent.entity.ReturnSaleDetail;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月1日 下午6:41:39 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 

public class ReturnSaleDetailMapperTest {
	@Resource
	ReturnSaleDetailMapperImpl returnSaleDetailMapperImpl;
	
	@Test
	public void add(){
		ReturnSaleDetail returnSaleDetail=new ReturnSaleDetail();
		returnSaleDetail.setCreateTime(new Date());
		returnSaleDetail.setUpdateTime(new Date());
		returnSaleDetail.setProductId(11111L);
		returnSaleDetail.setQuantity(23);
		returnSaleDetail.setReturnSaleId(22222L);
		returnSaleDetail.setSecuritiesNo("23213123");
		Long id=returnSaleDetailMapperImpl.saveReturnSaleDetail(returnSaleDetail);
		System.out.println(id);
	}
}
