
	 /**
  * 文件名：PresellDeliveryTest.java
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

import com.froad.fft.persistent.api.impl.PresellDeliveryMapperImpl;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.PresellDelivery;
import com.froad.fft.persistent.entity.StockPile;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月29日 下午7:42:03 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 

public class PresellDeliveryTest {
	@Resource
	private PresellDeliveryMapperImpl presellDeliveryMapperImpl;
	
	@Test
	public void page(){
		Page page=new Page();
		PageFilter<PresellDelivery> pageFilter=new PageFilter<PresellDelivery>();
		
		PresellDelivery presellDelivery=new PresellDelivery();	
		pageFilter.setFilterEntity(presellDelivery);
		page.setPageSize(1);
		page.setPageFilter(pageFilter);
				
		List<PresellDelivery> list=presellDeliveryMapperImpl.selectPresellDeliveryByPage(page);
		int n=presellDeliveryMapperImpl.selectPresellDeliveryByPageCount(page);
		System.out.println(list.size());
		System.out.println(n);
	}
	
}
