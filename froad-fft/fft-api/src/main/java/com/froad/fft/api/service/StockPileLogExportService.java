
	 /**
  * 文件名：StockPileExportService.java
  * 版本信息：Version 1.0
  * 日期：2014年3月28日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.StockPileLogDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 下午7:22:40 
 */
public interface StockPileLogExportService {
	
	/**
	  * 方法描述：添加
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午7:26:00
	  */
	public Long addStockPileLog(ClientAccessType clientAccessType,ClientVersion clientVersion,StockPileLogDto stockPileLogDto)throws FroadException;
	
	/**
	  * 方法描述：分页
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午7:26:02
	  */
	public PageDto findStockPileLogByPage(ClientAccessType clientAccessType,ClientVersion clientVersion,PageDto pageDto)throws FroadException;
}
