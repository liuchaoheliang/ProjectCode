
	 /**
  * 文件名：StockPileLogServiceImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年3月28日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.persistent.api.StockPileLogMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.StockPileLog;
import com.froad.fft.service.StockPileLogService;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 下午6:50:08 
 */
@Service("stockPileLogServiceImpl")
public class StockPileLogServiceImpl implements StockPileLogService {
	private static Logger logger = Logger.getLogger(StockPileLogServiceImpl.class);

	@Resource
	StockPileLogMapper stockPileLogMapper;
	
	@Override
	public Long saveStockPileLog(StockPileLog stockPileLog) {
		if(stockPileLog==null){
			logger.info("插入数据为空");
			return null;
		}
		return stockPileLogMapper.saveStockPileLog(stockPileLog);
	}


	@Override
	public Page findStockPileLogByPage(Page page) {
		if(page == null){
			page=new Page();
		}
		page.setResultsContent(stockPileLogMapper.selectStockPileLogByPage(page));
		page.setTotalCount(stockPileLogMapper.selectStockPileLogByPageCount(page));		
		return page;
	}

}
