
	 /**
  * 文件名：ReturnSaleDetailMapperTest.java
  * 版本信息：Version 1.0
  * 日期：2014年4月1日
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

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlShowProfileStatement.Type;
import com.froad.fft.persistent.api.ReturnSaleDetailMapper;
import com.froad.fft.persistent.api.impl.ReturnSaleDetailMapperImpl;
import com.froad.fft.persistent.api.impl.ReturnSaleMapperImpl;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.ReturnSale;
import com.froad.fft.persistent.entity.ReturnSaleDetail;
import com.froad.fft.persistent.entity.StockPile;
import com.froad.fft.persistent.entity.StockPileLog;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月1日 下午6:41:39 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 

public class ReturnSaleMapperTest {
	@Resource
	ReturnSaleMapperImpl returnSaleMapperImpl;
	
	@Test
	public void add(){
		ReturnSale returnSale=new ReturnSale();
		returnSale.setCreateTime(new Date());
		returnSale.setUpdateTime(new Date());
		returnSale.setMerchantOutletId(123123L);
		returnSale.setReason("reason");
		returnSale.setSn("123123123");
		returnSale.setType(com.froad.fft.persistent.entity.ReturnSale.Type.sale_return);
		returnSale.setSysUserId(123123123L);
		Long id=returnSaleMapperImpl.saveReturnSale(returnSale);
		System.out.println(id);
	}
	
	@Test
	public void select(){
		ReturnSale returnSale=new ReturnSale();
		returnSale.setId(2L);
		returnSaleMapperImpl.selectByConditions(returnSale);
	}
	
	
	@Test
	public void page(){
		Page page=new Page();
		PageFilter<ReturnSale> pageFilter=new PageFilter<ReturnSale>();
		
		ReturnSale returnSale=new ReturnSale();
		List<Long> ids=new ArrayList<Long>();
		ids.add(1L);
		ids.add(2L);
		returnSale.setSysUserIds(ids);
		
		pageFilter.setFilterEntity(returnSale);
		page.setPageSize(20);
		page.setPageFilter(pageFilter);
				
		List<ReturnSale> list=returnSaleMapperImpl.findReturnSaleByPage(page);
		int n=returnSaleMapperImpl.findReturnSaleByPageCount(page);
		System.out.println(list.size());
		System.out.println(n);
	}
}
