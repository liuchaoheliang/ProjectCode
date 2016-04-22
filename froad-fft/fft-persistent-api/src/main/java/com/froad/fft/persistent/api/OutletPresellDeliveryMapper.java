
	 /**
  * 文件名：OutletPresellDeliveryMapper.java
  * 版本信息：Version 1.0
  * 日期：2014年3月29日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.entity.OutletPresellDelivery;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月29日 上午11:42:21 
 */
public interface OutletPresellDeliveryMapper {
	
	/**
	  * 方法描述：保存数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月29日 上午11:55:37
	  */
	public Boolean saveOutletPresellDelivery(OutletPresellDelivery outletPresellDelivery );
	
	
	/**
	  * 方法描述：根据条件查询数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月29日 上午11:55:39
	  */
	public  List<OutletPresellDelivery> selectOutletPresellDeliveryByConditions(OutletPresellDelivery outletPresellDelivery);
	
	
	/**
	  * 方法描述：删除数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月29日 上午11:55:41
	  */
	public Boolean deleteOutletPresellDelivery(OutletPresellDelivery outletPresellDelivery);
}
