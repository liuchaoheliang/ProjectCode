package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.merchant.MerchantTraffic;

/** 
 * @author FQ 
 * @date 2013-2-4 下午03:29:22
 * @version 1.0
 * 商户交通信息
 */
@WebService
public interface MerchantTrafficService {
	
	/**
	 * 增加 商户交通信息
	 * @return
	 */
	public Integer addMerchantTraffic(MerchantTraffic merchantTraffic);
	
	
	/**
	 * 通过商户交通信息ID,更新商户交通信息
	 * @param merchantTraffic
	 */
	public void updateMerchantTraffic(MerchantTraffic merchantTraffic);
	
	
	/**
	 * 通过商户ID或者会员ID查询出商户交通信息
	 * @param merchantId
	 * @param userId
	 * @return 商户交通信息
	 */
	public List<MerchantTraffic> getMerchantTrafficInfo(String merchantId,String userId);
	
	
	/**
	  * 方法描述：查询商户交通信息
	  * @param: merchantId
	  * @return: List<MerchantTraffic>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 5, 2013 6:05:38 PM
	  */
	public List<MerchantTraffic> getMerchantTrafficInfoByMerchantId(String merchantId);
	
	
	/**
	  * 方法描述：查询商户交通信息
	  * @param: userId
	  * @return: List<MerchantTraffic>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 5, 2013 6:05:52 PM
	  */
	public List<MerchantTraffic> getMerchantTrafficInfoByUserId(String userId);
}
