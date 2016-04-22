package com.froad.fft.persistent.api;

import com.froad.fft.persistent.entity.CashPointsRatio;


	/**
	 * 类描述：现金和积分比例
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年3月31日 上午11:57:42 
	 */
public interface CashPointsRatioMapper {

	
	/**
	  * 方法描述：保存
	  * @param: CashPointsRatio
	  * @return: Long
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月31日 下午12:00:38
	  */
	public Long saveCashPointsRatio(CashPointsRatio cashPointsRatio);
	
	
	/**
	  * 方法描述：更新
	  * @param: CashPointsRatio
	  * @return: Boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月31日 下午12:02:22
	  */
	public Boolean updateCashPointsRatioById(CashPointsRatio cashPointsRatio);
	
	
	/**
	  * 方法描述：查询
	  * @param: sysClientId
	  * @return: CashPointsRatio
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年3月31日 下午12:00:43
	  */
	public CashPointsRatio selectBySysClientId(Long sysClientId);
}
