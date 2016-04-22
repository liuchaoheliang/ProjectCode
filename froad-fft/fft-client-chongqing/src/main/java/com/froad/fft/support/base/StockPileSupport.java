
	 /**
  * 文件名：StockPileSupport.java
  * 版本信息：Version 1.0
  * 日期：2014年4月4日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base;

import java.util.List;

import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.StockPileDto;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月4日 下午12:36:09 
 */
public interface StockPileSupport {
	
	
	/**
	  * 方法描述：库存分页查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月4日 下午12:37:02
	  */
	public PageDto getByPage(PageDto page);
	
	
	
	/**
	  * 方法描述：条件查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午9:35:05
	  */
	public List<StockPileDto> getByCondtitons(StockPileDto stockPileDto);
	
	
	
	/**
	  * 方法描述：根据ID更新数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午9:35:41
	  */
	public Boolean updateById(StockPileDto stockPileDto);
}
