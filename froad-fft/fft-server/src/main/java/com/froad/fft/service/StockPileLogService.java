
	 /**
  * 文件名：StockPileLogService.java
  * 版本信息：Version 1.0
  * 日期：2014年3月28日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.StockPileLog;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 下午6:34:41 
 */
public interface StockPileLogService {
	
	
	/**
	  * 方法描述：保存数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午6:47:42
	  */
	public Long saveStockPileLog(StockPileLog stockPileLog );
	
	
	/**
	  * 方法描述：分页查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午6:47:50
	  */
	public Page findStockPileLogByPage(Page page);
}
