
	 /**
  * 文件名：StockPileLogMapperImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年3月28日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.StockPileLogMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.StockPileLog;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 下午4:00:52 
 */
public class StockPileLogMapperImpl implements StockPileLogMapper {

	@Resource
	StockPileLogMapper stockPileLogMapper;

	@Override
	public Long saveStockPileLog(StockPileLog stockPileLog) {
		stockPileLogMapper.saveStockPileLog(stockPileLog);
		return stockPileLog.getId();
	}


	@Override
	public List<StockPileLog> selectStockPileLogByPage(Page page) {
		return stockPileLogMapper.selectStockPileLogByPage(page);
	}

	
	@Override
	public Integer selectStockPileLogByPageCount(Page page) {
		return stockPileLogMapper.selectStockPileLogByPageCount(page);
	}

}
