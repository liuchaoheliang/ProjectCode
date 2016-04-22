package com.froad.CB.service;

import javax.jws.WebService;

import com.froad.CB.po.merchant.MerchantTrafficMAP;

/** 
 * @author FQ 
 * @date 2013-2-4 下午03:29:54
 * @version 1.0
 * 商户地图信息
 */
@WebService
public interface MerchantTrafficMAPService {
	
	/**
	 * 增加商户地图信息
	 * @param merchantTrafficMAP
	 * @return
	 */
	public Integer addMerchantTrafficMAP(MerchantTrafficMAP merchantTrafficMAP);

	/**
	 * 更新商户地图信息
	 * @return
	 */
	public boolean updateMerchantTrafficMAP(MerchantTrafficMAP merchantTrafficMAP);
	
	
	/**
	  * 方法描述：按商户编号查询地图信息
	  * @param: merchantId
	  * @return: MerchantTrafficMAP
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 6, 2013 2:17:41 PM
	  */
	public MerchantTrafficMAP getMerchantTrafficMapByMerchantId(String merchantId);
	
	
	/**
	  * 方法描述：按用户编号查询地图信息
	  * @param: userId
	  * @return: MerchantTrafficMAP
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 6, 2013 2:17:41 PM
	  */
	public MerchantTrafficMAP getMerchantTrafficMapByUserId(String userId);
}
