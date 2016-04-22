
	 /**
  * 文件名：OutletPresellDeliveryExportService.java
  * 版本信息：Version 1.0
  * 日期：2014年3月29日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.api.service;

import java.util.List;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.OutletPresellDeliveryDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月29日 下午2:01:35 
 */
public interface OutletPresellDeliveryExportService {
	
	/**
	  * 方法描述：添加
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月29日 下午2:11:22
	  */
	public Boolean add(ClientAccessType clientAccessType,ClientVersion clientVersion,OutletPresellDeliveryDto deliveryDto)throws FroadException;

	
	/**
	  * 方法描述：条件查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月29日 下午2:11:25
	  */
	public List<OutletPresellDeliveryDto> selectOutletPresellDeliveryDtoByConditions(
			ClientAccessType clientAccessType,ClientVersion clientVersion,OutletPresellDeliveryDto deliveryDto)throws FroadException;
	
	
	/**
	  * 方法描述：物理删除
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月29日 下午2:11:28
	  */
	public Boolean deleteOutletPresellDelivery(ClientAccessType clientAccessType,ClientVersion clientVersion,OutletPresellDeliveryDto deliveryDto)throws FroadException;
}
