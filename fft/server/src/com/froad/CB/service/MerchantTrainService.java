package com.froad.CB.service;

import javax.jws.WebService;

import com.froad.CB.po.merchant.MerchantTrain;

/** 
 * @author FQ 
 * @date 2013-2-4 下午03:30:23
 * @version 1.0
 * 商户直通车
 */
@WebService
public interface MerchantTrainService {
	
	/**
	 * 增加商户直通车
	 * @param merchantTrain
	 * @return
	 */
	public Integer addMerchantTrain(MerchantTrain merchantTrain);
	
	
	/**
	  * 方法描述：查询商户直通车
	  * @param: merchantId
	  * @return: MerchantTrain
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 6, 2013 3:24:26 PM
	  */
	public MerchantTrain getMerchantTrainByMerchantId(String merchantId);
	
	
	/**
	  * 方法描述：查询商户直通车
	  * @param: userId
	  * @return: MerchantTrain
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 6, 2013 3:24:26 PM
	  */
	public MerchantTrain getMerchantTrainByUserId(String userId);
	
	
	/**
	 * 通过商户直通车信息ID,更新商户直通车信息
	 * @param merchantTrain
	 */
	public boolean updateById(MerchantTrain merchantTrain);
	
}
