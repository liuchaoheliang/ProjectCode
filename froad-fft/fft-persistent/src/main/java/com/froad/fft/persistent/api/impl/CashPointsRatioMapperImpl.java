
	 /**
  * 文件名：CashPointsRatioMapperImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年3月31日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.CashPointsRatioMapper;
import com.froad.fft.persistent.entity.CashPointsRatio;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月31日 下午1:54:45 
 */
public class CashPointsRatioMapperImpl implements CashPointsRatioMapper {
	@Resource
	private CashPointsRatioMapper cashPointsRatioMapper;
	
	@Override
	public Long saveCashPointsRatio(CashPointsRatio cashPointsRatio) {
		cashPointsRatioMapper.saveCashPointsRatio(cashPointsRatio);
		return cashPointsRatio.getId();
	}


	@Override
	public Boolean updateCashPointsRatioById(CashPointsRatio cashPointsRatio) {
		return cashPointsRatioMapper.updateCashPointsRatioById(cashPointsRatio);
	}


	@Override
	public CashPointsRatio selectBySysClientId(Long sysClientId) {
		return cashPointsRatioMapper.selectBySysClientId(sysClientId);
	}

}
