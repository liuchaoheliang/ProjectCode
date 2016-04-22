
	 /**
  * 文件名：ReturnSaleSupport.java
  * 版本信息：Version 1.0
  * 日期：2014年4月6日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base;

import java.util.List;

import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.ReturnSaleDto;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月6日 上午10:25:42 
 */
public interface ReturnSaleSupport {
	
	public ReturnSaleDto getById(Long id);
	/**
	  * 方法描述：添加退换货信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月8日 下午1:30:02
	  */
	public Long addReturnSale(ReturnSaleDto returnSaleDto);
	
	/**
	  * 方法描述：退货/换货分页查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月6日 上午10:26:30
	  */
	public PageDto getByPage(PageDto pageDto);
	
	
	/**
	  * 方法描述：条件查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月8日 下午12:23:30
	  */
	public List<ReturnSaleDto> getByConditions(ReturnSaleDto dto);
}
