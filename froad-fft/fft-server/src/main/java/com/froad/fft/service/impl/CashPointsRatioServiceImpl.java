
	 /**
  * 文件名：CashPointsRatioServiceImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年3月31日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.froad.fft.persistent.api.CashPointsRatioMapper;
import com.froad.fft.persistent.entity.CashPointsRatio;
import com.froad.fft.service.CashPointsRatioService;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月31日 下午3:03:30 
 */
@Service("cashPointsRatioServiceImpl")
public class CashPointsRatioServiceImpl implements CashPointsRatioService {

	@Resource
	CashPointsRatioMapper cashPointsRatioMapper;

	private static Logger logger = Logger.getLogger(CashPointsRatioServiceImpl.class);
	
	
	@Override
	public Long savaCashPointsRatio(CashPointsRatio cashPointsRatio) {
		if(cashPointsRatio==null){
			logger.info("插入参数为空");
			return null;
		}
		return cashPointsRatioMapper.saveCashPointsRatio(cashPointsRatio);
	}


	@Override
	public Boolean updateCashPointsRatioById(CashPointsRatio cashPointsRatio) {
		if(cashPointsRatio==null){
			logger.info("传入参数为空");
			return null;
		}
		return cashPointsRatioMapper.updateCashPointsRatioById(cashPointsRatio);
	}


	@Override
	@Cacheable("cashPointsRatio")
	public CashPointsRatio selectBySysClientId(Long sysClientId) {
		if(sysClientId==null){
			logger.info("传入参数为空");
			return null;
		}
		return cashPointsRatioMapper.selectBySysClientId(sysClientId);
	}

}
