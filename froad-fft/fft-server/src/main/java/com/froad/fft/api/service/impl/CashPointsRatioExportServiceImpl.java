
	 /**
  * 文件名：CashPointsRatioExportServiceImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年3月31日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.api.service.impl;

import javax.annotation.Resource;

import com.froad.fft.api.service.CashPointsRatioExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.CashPointsRatioDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.service.CashPointsRatioService;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月31日 下午3:31:06 
 */
public class CashPointsRatioExportServiceImpl implements
		CashPointsRatioExportService {
	@Resource(name="cashPointsRatioServiceImpl")
	private CashPointsRatioService cashPointsRatioService;
	
	@Override
	public Long addCashPointsRatio(ClientAccessType clientAccessType,
			ClientVersion clientVersion, CashPointsRatioDto cashPointsRatioDto) throws FroadException{
		return cashPointsRatioService.savaCashPointsRatio(DtoToBeanSupport.loadByCashPointsRatio(cashPointsRatioDto));
	}


	@Override
	public Boolean updateCashPointsRatioById(ClientAccessType clientAccessType,
			ClientVersion clientVersion, CashPointsRatioDto cashPointsRatioDto) throws FroadException{
		return cashPointsRatioService.updateCashPointsRatioById(DtoToBeanSupport.loadByCashPointsRatio(cashPointsRatioDto));
	}


	@Override
	public CashPointsRatioDto selectBySysClientId(
			ClientAccessType clientAccessType, ClientVersion clientVersion,
			Long sysClientId) throws FroadException{
		return BeanToDtoSupport.loadByCashPointsRatioDto(cashPointsRatioService.selectBySysClientId(sysClientId));
	}

}
