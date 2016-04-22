
	 /**
  * 文件名：StockPileServiceImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年3月28日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.persistent.api.StockPileMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.StockPile;
import com.froad.fft.service.StockPileService;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 下午6:51:38 
 */
@Service("stockPileServiceImpl")
public class StockPileServiceImpl implements StockPileService {
	private static Logger logger = Logger.getLogger(StockPileServiceImpl.class);

	@Resource
	StockPileMapper stockPileMapper;

	

	@Override
	public Long saveStockPile(StockPile stockPile) {
		if(stockPile==null){
			logger.info("参数为空");
			return null;
		}
		return stockPileMapper.saveStockPile(stockPile);
	}

	

	@Override
	public Boolean updateStockPileById(StockPile stockPile) {
		if(stockPile==null){
			logger.info("参数为空");
			return null;
		}
		return stockPileMapper.updateStockPileById(stockPile);
	}

	

	@Override
	public StockPile findStockPileById(Long id) {
		if(id==null){
			logger.info("查询参数为空");
			return null;
		}
		return stockPileMapper.selectStockPileById(id);
	}

	

	@Override
	public Page findStockPileByPage(Page page) {
		if(page == null){
			page=new Page();
		}
		page.setResultsContent(stockPileMapper.selectStockPileByPage(page));
		page.setTotalCount(stockPileMapper.selectStockPileByPageCount(page));		
		return page;
	}



	

	@Override
	public List<StockPile> selectByCondtions(StockPile stockPile) {
		return stockPileMapper.selectByCondtitons(stockPile);
	}
	


}
