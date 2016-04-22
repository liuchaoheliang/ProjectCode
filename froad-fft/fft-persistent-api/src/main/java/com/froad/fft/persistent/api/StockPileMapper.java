
	 /**
  * 文件名：StockPileMapper.java
  * 版本信息：Version 1.0
  * 日期：2014年3月28日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.StockPile;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 下午3:51:38 
 */
public interface StockPileMapper {
	
	/**
	  * 方法描述：保存数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午3:55:31
	  */
	public Long saveStockPile(StockPile stockPile );
	
	
	/**
	  * 方法描述：根据Id修改
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午3:55:33
	  */
	public Boolean updateStockPileById(StockPile stockPile);
	
	
	/**
	  * 方法描述：根据Id查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午3:55:35
	  */
	public StockPile  selectStockPileById(Long id);
	
	
	
	/**
	  * 方法描述：分页查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月28日 下午3:57:12
	  */
	public List<StockPile> selectStockPileByPage(Page page);
	
	public Integer selectStockPileByPageCount(Page page);
	
	
	/**
	  * 方法描述：条件查找
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午8:46:11
	  */
	public List<StockPile> selectByCondtitons(StockPile stockPile);
}
