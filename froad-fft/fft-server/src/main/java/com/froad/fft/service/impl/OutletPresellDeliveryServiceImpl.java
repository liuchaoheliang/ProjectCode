
	 /**
  * 文件名：OutletPresellDeliveryServiceImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年3月29日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.persistent.api.OutletPresellDeliveryMapper;
import com.froad.fft.persistent.entity.OutletPresellDelivery;
import com.froad.fft.service.OutletPresellDeliveryService;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月29日 下午2:32:54 
 */
@Service("outletPresellDeliveryServiceImpl")
public class OutletPresellDeliveryServiceImpl implements
		OutletPresellDeliveryService {

	 private static Logger logger = Logger.getLogger(OutletPresellDeliveryServiceImpl.class);
	
	@Resource
	private OutletPresellDeliveryMapper outletPresellDeliveryMapper;

	@Override
	public Boolean saveOutletPresellDelivery(OutletPresellDelivery delivery) {
		if(delivery==null){
			logger.info("传入参数为空");
			return null;
		}
		return outletPresellDeliveryMapper.saveOutletPresellDelivery(delivery);
	}


	@Override
	public List<OutletPresellDelivery> selectOutletPresellDeliveryByConditions(
			OutletPresellDelivery delivery) {
		if(delivery==null){
			logger.info("传入参数为空");
			return null;
		}
		return outletPresellDeliveryMapper.selectOutletPresellDeliveryByConditions(delivery);
	}


	@Override
	public Boolean deleteOutletPresellDelivery(OutletPresellDelivery delivery) {
		if(delivery ==null || delivery.getMerchantOutletId() ==null || delivery.getPresellDeliveryId()==null){
			logger.info("传入必要参数为空");
			return null;
		}
		return outletPresellDeliveryMapper.deleteOutletPresellDelivery(delivery);
	}

}
