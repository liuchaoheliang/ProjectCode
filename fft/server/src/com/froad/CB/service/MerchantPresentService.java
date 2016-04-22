package com.froad.CB.service;

import javax.jws.WebService;

import com.froad.CB.po.merchant.MerchantPresent;

/** 
 * @author FQ 
 * @date 2013-2-4 下午03:23:33
 * @version 1.0
 * 商户介绍
 */
@WebService
public interface MerchantPresentService {
	
	/**
	 * 增加商户介绍
	 * @param merchantPresent
	 * @return
	 */
	public Integer addMerchantPresent(MerchantPresent merchantPresent);
	
	/**
	 * 更新商户介绍信息
	 * @param merchantPresent
	 * @return 更新后的商户介绍信息
	 */
	public boolean updMerchantPresent(MerchantPresent merchantPresent);
	
	
	/**
	 * 通过商户ID查询出商户介绍信息
	 * @param merchantId
	 * @return 商户介绍信息
	 */
	public MerchantPresent getMerchantPresentByMerchantId(String merchantId);
	
	/**
	 * 通过会员ID查询出商户介绍信息
	 * @param userId
	 * @return 商户介绍信息
	 */
	public MerchantPresent getMerchantPresentByUserId(String userId);
	
	/**
	 * 更新商户介绍信息
	 * @param MerchantPresent
	 * @return MerchantPresent
	 */
	public MerchantPresent updateByMerchantId(MerchantPresent merchantPresent) throws Exception;
}
