package com.froad.fft.test.mapper;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.fft.persistent.api.StockPileLogMapper;
import com.froad.fft.persistent.api.StockPileMapper;
import com.froad.fft.persistent.api.impl.StockPileMapperImpl;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.StockPile;
import com.froad.fft.persistent.entity.StockPile.WarnType;
import com.froad.fft.persistent.entity.StockPileLog;
import com.froad.fft.persistent.entity.StockPileLog.Type;


	 /**
 * 文件名：StockPileLogMapperTest.java
 * 版本信息：Version 1.0
 * 日期：2014年3月28日
 * author: 刘超 liuchao@f-road.com.cn
 */

/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 下午5:08:29 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/applicationContext.xml","classpath:context/applicationContext-remote.xml"}) 

public class StockPileMapperTest {
	
	@Resource
	StockPileMapperImpl stockPileMapperImpl;
	
	@Test
	public void add(){
		StockPile stockPile=new StockPile();
		stockPile.setCreateTime(new Date());
		stockPile.setUpdateTime(new Date());
		stockPile.setProductId(1231231L);
		stockPile.setWarnType(WarnType.scale);
		stockPile.setWarnValue("warnValue");
		stockPile.setQuantity(23);
		stockPile.setLastIncomeTime(new Date());
		stockPile.setMerchantOutletId(231231L);
		stockPile.setTotalQuantity(123);
		stockPile.setRemark("remark");		
		long id =stockPileMapperImpl.saveStockPile(stockPile);
		System.out.println(id);
	}
	
	
	@Test
	public void update(){
		StockPile stockPile=new StockPile();
		stockPile.setId(2L);
		stockPile.setProductId(1231231L);
		stockPile.setWarnType(WarnType.fixed);
		stockPile.setWarnValue("rewarnValue");
		stockPile.setQuantity(333);
		stockPile.setLastIncomeTime(new Date());
		stockPile.setMerchantOutletId(231231212423L);
		stockPile.setTotalQuantity(1111);
		stockPile.setRemark("xxremark");		
		boolean flag =stockPileMapperImpl.updateStockPileById(stockPile);
		System.out.println(flag);
	}
	
	
	@Test
	public void page(){
		Page page=new Page();
		PageFilter<StockPile> pageFilter=new PageFilter<StockPile>();
		
		StockPile pileLog=new StockPile();
//		refunds.setId(1L);
		
		pageFilter.setFilterEntity(pileLog);
		page.setPageSize(1);
		page.setPageFilter(pageFilter);
				
		List<StockPile> list=stockPileMapperImpl.selectStockPileByPage(page);
		int n=stockPileMapperImpl.selectStockPileByPageCount(page);
		System.out.println(list.size());
		System.out.println(n);
	}
}
