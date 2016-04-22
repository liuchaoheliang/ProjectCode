/**
 * Project Name:froad-cbank-server-common-1.8.0-SNAPSHOT
 * File Name:ProviderMerchantRedis.java
 * Package Name:com.froad.db.redis
 * Date:2015年11月26日上午10:24:36
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.FieldMapping;
import com.froad.po.Provider;
import com.froad.util.Checker;
import com.froad.util.RedisKeyUtil;

/**
 * ClassName:ProviderMerchantRedis
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月26日 上午10:24:36
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class ProviderMerchantRedis {
	
	private static RedisManager redisManager = new RedisManager();
	
	/**
	 * 
	 * set_cbbank_provider_merchant_Id:(设置供应商redis缓存).
	 *
	 * @author huangyihao
	 * 2015年11月26日 上午10:46:30
	 * @param provider
	 * @return
	 *
	 */
	public static Boolean set_cbbank_provider_merchant_Id(Provider provider){
		boolean flag = false;
		String key = RedisKeyUtil.cbbank_provider_merchant_Id(provider.getMerchantId());
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put(FieldMapping.MERCHANT_NAME.getMongoField(), ObjectUtils.toString(provider.getMerchantName(), ""));
		valueMap.put("address", ObjectUtils.toString(provider.getAddress(), ""));
		valueMap.put("phone", ObjectUtils.toString(provider.getPhone(), ""));
		valueMap.put("status", ObjectUtils.toString(provider.getStatus(), ""));
		valueMap.put("description", ObjectUtils.toString(provider.getDescription(), ""));
		flag = "OK".equals(redisManager.putMap(key, valueMap));
		return flag;
	}
	
	
	/**
	 * 
	 * getAll_cbbank_provider_merchant_Id:(根据供应商ID查询redis缓存).
	 *
	 * @author huangyihao
	 * 2015年11月26日 上午10:46:56
	 * @param merchantId
	 * @return
	 *
	 */
	public static Map<String, String> getAll_cbbank_provider_merchant_Id(String merchantId){
		String key = RedisKeyUtil.cbbank_provider_merchant_Id(merchantId);
		return redisManager.getMap(key);
	}
	
	
}
