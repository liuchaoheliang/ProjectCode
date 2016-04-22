
	 /**
  * 文件名：OutletPresellDeliverySupport.java
  * 版本信息：Version 1.0
  * 日期：2014年4月7日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base;

import java.util.List;

import com.froad.fft.dto.OutletPresellDeliveryDto;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月7日 下午9:13:03 
 */
public interface OutletPresellDeliverySupport {
	
	
	/**
	  * 方法描述：条件查询门店提货点关系
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月7日 下午9:13:45
	  */
	public List<OutletPresellDeliveryDto> getByConditions(OutletPresellDeliveryDto deliveryDto);

}
