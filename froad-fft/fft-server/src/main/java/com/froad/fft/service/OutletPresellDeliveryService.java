
	 /**
  * 文件名：OutletPresellDeliveryService.java
  * 版本信息：Version 1.0
  * 日期：2014年3月29日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.service;

import java.util.List;

import com.froad.fft.persistent.entity.OutletPresellDelivery;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月29日 下午2:23:05 
 */
public interface OutletPresellDeliveryService {
	
	/**
	  * 方法描述：添加
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月29日 下午2:25:16
	  */
	public Boolean saveOutletPresellDelivery(OutletPresellDelivery delivery );
	
	
	/**
	  * 方法描述：条件查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月29日 下午2:25:22
	  */
	public List<OutletPresellDelivery> selectOutletPresellDeliveryByConditions(OutletPresellDelivery delivery);

	
	/**
	  * 方法描述：物理删除
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月29日 下午2:29:14
	  */
	public Boolean deleteOutletPresellDelivery(OutletPresellDelivery delivery);
}
