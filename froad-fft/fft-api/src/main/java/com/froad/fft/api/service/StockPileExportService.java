
	 /**
  * 文件名：StockPileExportService.java
  * 版本信息：Version 1.0
  * 日期：2014年3月28日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.api.service;

import java.util.List;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.StockPileDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 下午7:22:40 
 */
public interface StockPileExportService {
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午7:29:25
	  */
	public Long addStockPile(ClientAccessType clientAccessType,ClientVersion clientVersion,StockPileDto stockPileDto )throws FroadException;
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午7:29:27
	  */
	public Boolean updateStockPileById(ClientAccessType clientAccessType,ClientVersion clientVersion,StockPileDto stockPileDto )throws FroadException;
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午7:29:30
	  */
	public StockPileDto findStockPileById(ClientAccessType clientAccessType,ClientVersion clientVersion,Long id)throws FroadException;
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午7:29:32
	  */
	public PageDto findStockPilebyPage(ClientAccessType clientAccessType,ClientVersion clientVersion,PageDto pageDto)throws FroadException;


	
	/**
	  * 方法描述：条件查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午8:42:55
	  */
	public List<StockPileDto> selectByConditions(ClientAccessType clientAccessType,ClientVersion clientVersion,StockPileDto stockPileDto )throws FroadException;
}
