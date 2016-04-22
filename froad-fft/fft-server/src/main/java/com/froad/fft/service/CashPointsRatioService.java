
	 /**
  * 文件名：CashPointsRatioService.java
  * 版本信息：Version 1.0
  * 日期：2014年3月31日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.service;

import com.froad.fft.persistent.entity.CashPointsRatio;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月31日 下午2:55:46 
 */
public interface CashPointsRatioService {
	
	/**
	  * 方法描述：添加数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月31日 下午2:59:13
	  */
	public Long savaCashPointsRatio(CashPointsRatio cashPointsRatio);
	
	
	/**
	  * 方法描述：更新数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月31日 下午2:59:24
	  */
	public Boolean updateCashPointsRatioById(CashPointsRatio cashPointsRatio);
	
	
	/**
	  * 方法描述：根据客户端查找积分比例
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年3月31日 下午3:01:00
	  */
	public CashPointsRatio selectBySysClientId(Long sysClientId);
}
