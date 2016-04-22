
	 /**
  * 文件名：StockPileLogMapper.java
  * 版本信息：Version 1.0
  * 日期：2014年3月28日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.StockPileLog;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 下午3:49:25 
 */
public interface StockPileLogMapper {
	
	/**
	  * 方法描述：添加库存日志
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午3:50:53
	  */
	public Long saveStockPileLog(StockPileLog stockPileLog);

	
	/**
	  * 方法描述：分页查询库存日志
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午3:51:10
	  */
	public List<StockPileLog> selectStockPileLogByPage(Page page);
	
	public Integer selectStockPileLogByPageCount(Page page);
}
