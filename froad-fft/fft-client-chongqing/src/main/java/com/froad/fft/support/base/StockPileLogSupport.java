
	 /**
  * 文件名：StockPileLogSupport.java
  * 版本信息：Version 1.0
  * 日期：2014年4月7日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base;

import com.froad.fft.dto.StockPileLogDto;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月7日 下午10:03:01 
 */
public interface StockPileLogSupport {
	
	/**
	  * 方法描述：添加库存日志
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午10:03:37
	  */
	public Long addStockPileLog(StockPileLogDto stockPileLogDto );
}
