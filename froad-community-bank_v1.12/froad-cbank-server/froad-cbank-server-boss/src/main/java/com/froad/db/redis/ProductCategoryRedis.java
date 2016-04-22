/**
 * Project Name:froad-cbank-server-boss
 * File Name:ProductCategoryRedis.java
 * Package Name:com.froad.db.redis
 * Date:2015年11月3日下午7:16:39
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.db.redis;

import java.util.Set;

import org.apache.commons.lang.ObjectUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.util.RedisKeyUtil;

/**
 * ClassName:ProductCategoryRedis
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月3日 下午7:16:39
 * @author   liuyanyun
 * @version  
 * @see 	 
 */
public class ProductCategoryRedis {
	private RedisManager redis = new RedisManager();
	
	public boolean setProductCategoryRedis(String client_id, Long category_id, String product_id) {
		String key = RedisKeyUtil.cbbank_product_category_all_client_id_product_category_id( client_id, category_id);
		Boolean  result = redis.put(key, ObjectUtils.toString(product_id, "")) > -1;
		return result ;
	}
	
	public boolean delProductCategoryRedis(String client_id, Long category_id, String product_id) {
		String key = RedisKeyUtil.cbbank_product_category_all_client_id_product_category_id( client_id, category_id);
		Long result = redis.srem(key, ObjectUtils.toString(product_id, ""));
		return result > -1;
	}
	
	public Set<String> getProductIdByClientIdAndCategoryId(String clientId, Long categoryId) {
		String key = RedisKeyUtil.cbbank_product_category_all_client_id_product_category_id( clientId, categoryId);
		return redis.getSet(key);
	}
	
}
