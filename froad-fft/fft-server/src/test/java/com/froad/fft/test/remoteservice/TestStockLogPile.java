
	 /**
  * 文件名：TeststockPileDto.java
  * 版本信息：Version 1.0
  * 日期：2014年3月29日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.test.remoteservice;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.froad.fft.api.service.impl.StockPileExportServiceImpl;
import com.froad.fft.api.service.impl.StockPileLogExportServiceImpl;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.RefundsDto;
import com.froad.fft.dto.StockPileDto;
import com.froad.fft.dto.StockPileLogDto;
import com.froad.fft.dto.StockPileLogDto.Type;
import com.froad.fft.service.impl.StockPileLogServiceImpl;



	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月29日 上午10:12:08 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:context/servlet/applicationContext-mapper-remote.xml",
		"classpath:context/servlet/applicationContext-service-remote.xml",
		"classpath:context/applicationContext.xml",
		"classpath:context/applicationContext-expand.xml"
		}) 
public class TestStockLogPile {
	@Resource
	StockPileLogExportServiceImpl stockPileLogExportServiceImpl;
	
	@Test
	public void add(){
		StockPileLogDto stockPileLogDto=new StockPileLogDto();
		stockPileLogDto.setCreateTime(new Date());
		stockPileLogDto.setProductId(1231231L);
		stockPileLogDto.setType(Type.replenish_income);
		stockPileLogDto.setContent("content");
		stockPileLogDto.setQuantity(23);
		stockPileLogDto.setMerchantOutletId(231231L);

		stockPileLogDto.setOperator("operator");
		long id =stockPileLogExportServiceImpl.addStockPileLog(null,null,stockPileLogDto);
		System.out.println(id);
	}
	
	
	@Test
	public void page(){
		PageDto page=new PageDto();
		PageFilterDto<StockPileDto> pageFilter=new PageFilterDto<StockPileDto>();
		
		StockPileDto stockPileDto=new StockPileDto();
//		refunds.setId(1L);
		
		pageFilter.setFilterEntity(stockPileDto);
		page.setPageSize(2);
		page.setPageFilterDto(pageFilter);
		
		page= stockPileLogExportServiceImpl.findStockPileLogByPage(null,null,page);
		System.out.println(page.getResultsContent().size()+"条数："+page.getTotalCount());
		
		
	}
	
	
	@Test
	public void update(){
		
		
	}
}
