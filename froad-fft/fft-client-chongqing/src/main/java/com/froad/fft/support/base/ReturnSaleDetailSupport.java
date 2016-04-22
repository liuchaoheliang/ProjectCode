
	 /**
  * 文件名：ReturnSaleDetailSupport.java
  * 版本信息：Version 1.0
  * 日期：2014年4月7日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base;

import java.util.List;

import com.froad.fft.dto.ReturnSaleDetailDto;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月7日 下午10:08:47 
 */
public interface ReturnSaleDetailSupport {
	
	
	/**
	  * 方法描述：添加退货换货详情
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午10:09:23
	  */
	public Long addReturnSaleDetai(ReturnSaleDetailDto returnSaleDetailDto);
	
	
	/**
	  * 方法描述：条件查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月10日 下午8:27:10
	  */
	public List<ReturnSaleDetailDto> getByConditions(ReturnSaleDetailDto returnSaleDetailDto);
	
}
