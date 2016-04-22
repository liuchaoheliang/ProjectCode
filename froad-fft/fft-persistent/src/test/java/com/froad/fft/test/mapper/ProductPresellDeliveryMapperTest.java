
	 /**
  * 文件名：ProductPresellDeliveryMapperTest.java
  * 版本信息：Version 1.0
  * 日期：2014年4月1日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.test.mapper;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.fft.persistent.api.impl.ProductPresellDeliveryMapperImpl;
import com.froad.fft.persistent.entity.ProductPresellDelivery;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月1日 下午8:27:15 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 

public class ProductPresellDeliveryMapperTest {

	@Resource
	ProductPresellDeliveryMapperImpl deliveryMapperImpl;
	
	@Test
	public void add(){
		ProductPresellDelivery presellDelivery=new ProductPresellDelivery();
		presellDelivery.setCreateTime(new Date());
		presellDelivery.setUpdateTime(new Date());
		presellDelivery.setPresellDeliveryId(231231L);
		presellDelivery.setProductPresellId(33333L);
		Long id =deliveryMapperImpl.saveProductPresellDelivery(presellDelivery);
		System.out.println(id);
	}
	
	@Test
	public void select (){
		List<ProductPresellDelivery> list;
		list=deliveryMapperImpl.selectByProductPresellId(33333L);
		System.out.println(list.size());
	}
	
	@Test
	public void delete(){
		boolean flag=deliveryMapperImpl.deleteById(2L);
		System.out.println(flag);
	}
	
}
