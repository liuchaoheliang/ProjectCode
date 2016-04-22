/**
 * Project Name:froad-cbank-server-boss
 * File Name:MerchantCategoryRedis.java
 * Package Name:com.froad.db.redis
 * Date:2015年11月2日下午3:05:41
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.db.redis;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.util.PropertiesUtil;
import com.froad.util.RedisKeyUtil;

/**
 * ClassName:MerchantCategoryRedis
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月2日 下午3:05:41
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class MerchantCategoryRedis {
	
	private RedisManager redis = new RedisManager();
	
	public boolean setMerchantCategoryRedis(String client_id, Long category_id, String merchant_id) {
		String key = RedisKeyUtil.cbbank_merchant_category_all_client_id_merchant_category_id( client_id, category_id);
		Boolean  result = redis.put(key, merchant_id) > -1;
		return result ;
	}
	
	public boolean delMerchantCategoryRedis(String client_id, Long category_id, String merchant_id) {
		String key = RedisKeyUtil.cbbank_merchant_category_all_client_id_merchant_category_id( client_id, category_id);
		Long result = redis.srem(key, ObjectUtils.toString(merchant_id, ""));
		return result > -1;
	}
	
	public Set<String> getMerchantIdByClientIdAndCategoryId(String clientId, Long categoryId) {
		String key = RedisKeyUtil.cbbank_merchant_category_all_client_id_merchant_category_id( clientId, categoryId);
		return redis.getSet(key);
	}
	
	
}
