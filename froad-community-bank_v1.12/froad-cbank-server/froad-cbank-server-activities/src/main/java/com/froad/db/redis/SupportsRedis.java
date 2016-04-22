/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

package com.froad.db.redis;

import java.util.HashSet;
import java.util.Set;

import com.froad.db.redis.impl.RedisManager;
import com.froad.po.ActiveBaseRule;
import com.froad.util.RedisKeyUtil;


/**    
 * <p>Title: ActiveRedis.java</p>    
 * <p>Description: 描述 </p>   
 * @author yefeifei     
 * @version 1.0    
 * @created 2015年11月08日 上午10:11:55   
 */
public class SupportsRedis{
	
	private static final RedisManager redisManager = new RedisManager();
	
	/**
	 * 根据 商品id 得到 活动列表
	 * */
	public static Set<String> get_cbbank_active_product_info(String productId){
		String productKey = RedisKeyUtil.cbbank_active_product_product_id(productId);
		try{
			// 如果没有重新初始化一次
			if(!redisManager.exists(productKey)){
				
			}
			return redisManager.getSet(productKey);
		}catch(Exception e){
			return new HashSet<String>();
		}
	}
	
	//删除缓存，同时删除 活动-商品和商品-活动
	public static Boolean del_cbbank_active_product_info(String activeId) {
		//活动-商品缓存的key
		String activeProductInfoKey = RedisKeyUtil.cbbank_active_product_info_active_id(activeId);
		if(redisManager.exists(activeProductInfoKey))
		{
			//遍历所有商品,并删除，商品-活动缓存
			Set<String> goodsSet = redisManager.getSet(activeProductInfoKey);
			for(String goods : goodsSet)
			{
				//商品-活动缓存的key
				String productKey = RedisKeyUtil.cbbank_active_product_product_id(goods);
				
				Set<String> productActives = redisManager.getSet(productKey);
				//List<String> list = redisManager.getMSet(productKey);
				if(redisManager.exists(productKey))
				{
					for(String activeInfo : productActives)
					{
						String patten = activeInfo.substring(activeInfo.lastIndexOf(":")+1, activeInfo.length());
						if(activeId.equals(patten))
						{
							redisManager.srem(productKey, activeInfo);
						}
					}
				}
				
			}
			//删除活动-商品缓存
			redisManager.del(activeProductInfoKey);
		}
		return true;
	}
	
	//删除缓存，同时删除 活动-商户和商户-活动
	public static Boolean del_cbbank_active_merchant_info(String activeId) {
		//活动-商户缓存的key
		String activeMerchantInfoKey = RedisKeyUtil.cbbank_active_merchant_info_active_id(activeId);
		if(redisManager.exists(activeMerchantInfoKey))
		{
			//遍历所有商户,并删除，商户-活动缓存
			Set<String> merchantsSet = redisManager.getSet(activeMerchantInfoKey);
			for(String merchant : merchantsSet)
			{
				//商户-活动缓存的key
				String merchantKey = RedisKeyUtil.cbbank_active_merchant_merchant_id(merchant);
				
				Set<String> merchantActives = redisManager.getSet(merchantKey);
				
				if(redisManager.exists(merchantKey))
				{
					for(String activeInfo : merchantActives)
					{
						String patten = activeInfo.substring(activeInfo.lastIndexOf(":")+1, activeInfo.length());
						if(activeId.equals(patten))
						{
							redisManager.srem(merchantKey, activeInfo);
						}
					}
				}

			}
			//删除活动-商户缓存
			redisManager.del(activeMerchantInfoKey);
		}
		return true;
	}
	
	
	public static Boolean set_cbbank_active_product_product_id(
			Set<String> goodsSet, ActiveBaseRule activeBaseRule) {
			try {
				String value = activeBaseRule.getActiveName() + ":" + activeBaseRule.getType()+":"+activeBaseRule.getActiveId();
				for(String productId : goodsSet)
				{
					redisManager.put(productId, value);
				}
				
			}catch (Exception e)
			{
				e.printStackTrace();
			}
			
			
		return true;

	}
	
	public static Boolean set_cbbank_active_product_info_active_id(Set<String> productIdSet, String activeId) {
		//for (WeightActivityTag good : goodsList) {
			try {
				String key = RedisKeyUtil.cbbank_active_product_info_active_id(activeId) ;
				redisManager.putSet(key, productIdSet);
			}catch (Exception e)
			{
				e.printStackTrace();
			}
		//}
		return true;

	}
	
	public static Boolean set_cbbank_active_merchant_merchant_id(String merchantId, ActiveBaseRule activeBaseRule) {
		
//		if(goodsList.size()>0)
//		{
//			for (WeightActivityTag good : goodsList) {
				String key = RedisKeyUtil.cbbank_active_merchant_merchant_id(merchantId) ;
				String value = activeBaseRule.getActiveName() + ":" + activeBaseRule.getType()+":"+activeBaseRule.getActiveId();
				Set<String> setValue = new HashSet<String>();
				setValue.add(value);
				redisManager.putSet(key, setValue);
//			}
//		}
		return true;

	}
	
	public static Boolean set_cbbank_active_merchant_info_active_id(String merchantId, String activeId) {
//		if(goodsList.size()>0)
//		{
//			for (WeightActivityTag good : goodsList) {
				String key = RedisKeyUtil.cbbank_active_merchant_info_active_id(activeId) ;
				Set<String> setValue = new HashSet<String>();
				setValue.add(merchantId);
				redisManager.putSet(key, setValue);
//			}
//		}
		return true;

	}
	
	public static Boolean isProductBindedActive(String productId)
	{
		boolean result = false;
		String productKey = RedisKeyUtil.cbbank_active_product_product_id(productId);
		if(redisManager.exists(productKey))
		{
			result = true;
		}
			
		return result;
	}
	
	public static Boolean deleteRedisKey(String key)
	{
		if(redisManager.exists(key))
		{
			redisManager.del(key);
		}
		return true;
	}

}
