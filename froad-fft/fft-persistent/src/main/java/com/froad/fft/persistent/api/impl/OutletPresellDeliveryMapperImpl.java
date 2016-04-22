
	 /**
  * 文件名：OutletPresellDeliveryMapperImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年3月29日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.OutletPresellDeliveryMapper;
import com.froad.fft.persistent.entity.OutletPresellDelivery;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月29日 下午12:07:53 
 */
public class OutletPresellDeliveryMapperImpl implements
		OutletPresellDeliveryMapper {

	@Resource
	OutletPresellDeliveryMapper outletPresellDeliveryMapper;

	@Override
	public Boolean saveOutletPresellDelivery(
			OutletPresellDelivery outletPresellDelivery) {
		
		return outletPresellDeliveryMapper.saveOutletPresellDelivery(outletPresellDelivery);
	}


	@Override
	public List<OutletPresellDelivery> selectOutletPresellDeliveryByConditions(
			OutletPresellDelivery outletPresellDelivery) {
		
		return outletPresellDeliveryMapper.selectOutletPresellDeliveryByConditions(outletPresellDelivery);
	}


	@Override
	public Boolean deleteOutletPresellDelivery(
			OutletPresellDelivery outletPresellDelivery) {
		return outletPresellDeliveryMapper.deleteOutletPresellDelivery(outletPresellDelivery);
	}

}
