
	 /**
  * 文件名：CashPointsRatioExportService.java
  * 版本信息：Version 1.0
  * 日期：2014年3月31日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.CashPointsRatioDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月31日 下午3:18:29 
 */
public interface CashPointsRatioExportService {
	
	/**
	  * 方法描述：添加比例
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月31日 下午3:19:49
	  */
	public Long addCashPointsRatio(ClientAccessType clientAccessType,ClientVersion clientVersion,CashPointsRatioDto cashPointsRatioDto)throws FroadException;
	
	
	/**
	  * 方法描述：更新比例
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月31日 下午3:19:52
	  */
	public Boolean updateCashPointsRatioById(ClientAccessType clientAccessType,ClientVersion clientVersion, CashPointsRatioDto cashPointsRatioDto)throws FroadException;
	
	
	/**
	  * 方法描述：根据client Id 查询比例
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月31日 下午3:19:54
	  */
	public CashPointsRatioDto selectBySysClientId(ClientAccessType clientAccessType,ClientVersion clientVersion,Long sysClientId)throws FroadException;
}
