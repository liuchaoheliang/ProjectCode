
	 /**
  * 文件名：OutletPresellDeliverySupportImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年4月7日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.fft.api.service.OutletPresellDeliveryExportService;
import com.froad.fft.dto.OutletPresellDeliveryDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.OutletPresellDeliverySupport;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月7日 下午9:14:39 
 */
@Service
public class OutletPresellDeliverySupportImpl implements
		OutletPresellDeliverySupport {
	@Resource(name="outletPresellDeliveryService")
	private OutletPresellDeliveryExportService outletPresellDeliveryExportService;

	private final ClientAccessType clientAccessType = ClientAccessType.chongqing;
	
	@Override
	public List<OutletPresellDeliveryDto> getByConditions(
			OutletPresellDeliveryDto deliveryDto) {
		return outletPresellDeliveryExportService.selectOutletPresellDeliveryDtoByConditions(clientAccessType, ClientVersion.version_1_0, deliveryDto);
	}

}
