package com.froad.CB.service;

import javax.jws.WebService;

import com.froad.CB.AppException.AppException;
import com.froad.CB.po.transaction.Points;
import com.froad.CB.po.transaction.PointsCurrencyFormula;

@WebService
public interface PointService {

	
	/**
	  * 方法描述：查询会员积分
	  * @param: Points(orgNo,accountMarked)
	  * @return: Points
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 5, 2013 5:55:26 PM
	  */
	public Points queryPoints(Points points)throws Exception;
	
	
	/**
	  * 方法描述：增送积分
	  * @param: Points(orgNo,objectNo,objectDes,objectType,
	  *                point,accountMarked,accountMarkedType)
	  * @return: Points(resultCode为0000表示赠送成功，否则失败)
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 6, 2013 4:09:24 PM
	  */
	public Points presentPoints(Points points) throws AppException;
	
	
	/**
	  * 方法描述：
	  * @param: sellerId 
	  * @return: 该卖家目前启用的积分与货币兑换规则
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-13 下午04:12:58
	 */
	PointsCurrencyFormula getPointsCurrencyFormuleBySellerId(String sellerId);
	
	
	/**
	  * 方法描述：
	  * @param: merchantId
	  * @return: 该商户目前启用的积分与货币兑换规则
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-13 下午04:13:12
	 */
	PointsCurrencyFormula getPointsCurrencyFormuleByMerchantId(String merchantId);
	
	/**
	  * 方法描述：兑充积分
	  * @param: Points(orgPoints,AccountMarked,mobilePhone)
	  * @return: Points
	  * @version: 1.0
	  * @author: 李巧鹏lijinkui@f-road.com.cn
	  * @time: Mar 6, 2013 4:09:24 PM
	  */
	public Points fillPoints(Points points) throws AppException;
}
