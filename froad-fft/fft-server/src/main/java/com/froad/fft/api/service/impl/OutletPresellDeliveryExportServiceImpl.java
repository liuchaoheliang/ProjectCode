
	 /**
  * 文件名：OutletPresellDeliveryExportServiceImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年3月29日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.api.service.OutletPresellDeliveryExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.OutletPresellDeliveryDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.entity.OutletPresellDelivery;
import com.froad.fft.service.OutletPresellDeliveryService;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月29日 下午2:48:53 
 */
public class OutletPresellDeliveryExportServiceImpl implements
		OutletPresellDeliveryExportService {
	
	@Resource(name="outletPresellDeliveryServiceImpl")
	private OutletPresellDeliveryService outletPresellDeliveryService; 

	@Override
	public Boolean add(ClientAccessType clientAccessType,
			ClientVersion clientVersion, OutletPresellDeliveryDto deliveryDto)throws FroadException {
		
		return outletPresellDeliveryService.saveOutletPresellDelivery(DtoToBeanSupport.loadByOutletPresellDelivery(deliveryDto));
	}

	@Override
	public List<OutletPresellDeliveryDto> selectOutletPresellDeliveryDtoByConditions(
			ClientAccessType clientAccessType, ClientVersion clientVersion,
			OutletPresellDeliveryDto deliveryDto)throws FroadException {
			List<OutletPresellDelivery> tlist=outletPresellDeliveryService.selectOutletPresellDeliveryByConditions(DtoToBeanSupport.loadByOutletPresellDelivery(deliveryDto));
			List<OutletPresellDeliveryDto> list=new ArrayList<OutletPresellDeliveryDto>();
			for(OutletPresellDelivery delivery:tlist){
				list.add(BeanToDtoSupport.loadByOutletPresellDeliveryDto(delivery));							
			}
			return list;
	}

	@Override
	public Boolean deleteOutletPresellDelivery(
			ClientAccessType clientAccessType, ClientVersion clientVersion,
			OutletPresellDeliveryDto deliveryDto)throws FroadException {		
		return outletPresellDeliveryService.deleteOutletPresellDelivery(DtoToBeanSupport.loadByOutletPresellDelivery(deliveryDto));
	}

}
