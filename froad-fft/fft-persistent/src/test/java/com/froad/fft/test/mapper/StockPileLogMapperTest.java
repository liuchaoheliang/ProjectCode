package com.froad.fft.test.mapper;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.fft.persistent.api.StockPileLogMapper;
import com.froad.fft.persistent.api.impl.StockPileLogMapperImpl;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.Refunds;
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

public class StockPileLogMapperTest {
	@Resource
	StockPileLogMapperImpl stockPileLogMapperImpl;
	
	@Test
	public void add(){
		StockPileLog stockPileLog=new StockPileLog();
		stockPileLog.setCreateTime(new Date());
		stockPileLog.setUpdateTime(new Date());
		stockPileLog.setProductId(1231231L);
		stockPileLog.setType(Type.return_income);
		stockPileLog.setQuantity(23);
		stockPileLog.setMerchantOutletId(231231L);
		stockPileLog.setContent("content");
		stockPileLog.setOperator("operator");
		long id =stockPileLogMapperImpl.saveStockPileLog(stockPileLog);
		System.out.println(id);
	}
	
	@Test
	public void page(){
		Page page=new Page();
		PageFilter<StockPileLog> pageFilter=new PageFilter<StockPileLog>();
		
		StockPileLog pileLog=new StockPileLog();
//		refunds.setId(1L);
		
		pageFilter.setFilterEntity(pileLog);
		page.setPageSize(1);
		page.setPageFilter(pageFilter);
				
		List<StockPileLog> list=stockPileLogMapperImpl.selectStockPileLogByPage(page);
		int n=stockPileLogMapperImpl.selectStockPileLogByPageCount(page);
		System.out.println(list.size());
		System.out.println(n);
	}
	
	
}
