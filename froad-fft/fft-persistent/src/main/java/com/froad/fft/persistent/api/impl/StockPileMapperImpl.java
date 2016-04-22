
	 /**
  * 文件名：StockPileMapperImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年3月28日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.StockPileMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.StockPile;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 下午3:59:29 
 */
public class StockPileMapperImpl implements StockPileMapper {
	
	@Resource
	StockPileMapper stockPileMapper;

	@Override
	public Long saveStockPile(StockPile stockPile) {
		stockPileMapper.saveStockPile(stockPile);
		return stockPile.getId();
	}


	@Override
	public Boolean updateStockPileById(StockPile stockPile) {
		return stockPileMapper.updateStockPileById(stockPile);
	}


	@Override
	public StockPile selectStockPileById(Long id) {
		return stockPileMapper.selectStockPileById(id);
	}


	@Override
	public List<StockPile> selectStockPileByPage(Page page) {
		return stockPileMapper.selectStockPileByPage(page);
	}


	@Override
	public Integer selectStockPileByPageCount(Page page) {
		return stockPileMapper.selectStockPileByPageCount(page);
	}


	
	@Override
	public List<StockPile> selectByCondtitons(StockPile stockPile) {
		return stockPileMapper.selectByCondtitons(stockPile);
	}

}
