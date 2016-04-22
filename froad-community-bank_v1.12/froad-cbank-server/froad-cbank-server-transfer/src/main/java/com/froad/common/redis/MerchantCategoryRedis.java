/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
  
/**  
 * @Title: MerchantRedis.java
 * @Package com.froad.db.redis
 * @Description: TODO
 * @author vania
 * @date 2015年3月20日
 */

package com.froad.common.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;


import com.froad.db.chonggou.entity.MerchantCategoryCG;
import com.froad.db.redis.RedisService;
import com.froad.db.redis.impl.RedisManager;
import com.froad.util.RedisKeyUtil;


/**    
 * <p>Title: MerchantRedis.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月20日 上午10:11:55   
 */

public class MerchantCategoryRedis {
	
	private static RedisService redis = new RedisManager();
	/**
	 * 
	 * @Title: get_cbbank_merchant_category_detail_client_id_category_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param client_id
	 * @param category_id
	 * @param fields
	 * @return
	 * @return Map<String,String>   返回类型 
	 * @throws
	 */
	public static Map<String,String> get_cbbank_merchant_category_detail_client_id_category_id(String client_id, Long category_id) {
		String key = RedisKeyUtil.cbbank_merchant_category_detail_client_id_category_id(client_id, category_id) ;
		return redis.getMap(key);
	}

	/**
	 * 
	 * @Title: set_cbbank_merchant_category_detail_client_id_category_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantCategorys
	 * @return
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public static boolean set_cbbank_merchant_category_detail_client_id_category_id(MerchantCategoryCG merchantCategory) {
		
		boolean flag = false;
			/* 缓存全部商户 */
//			String key = RedisKeyUtil.cbbank_merchant_category_detail_client_id_category_id() ;
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("name", ObjectUtils.toString(merchantCategory.getName(), ""));
		hash.put("tree_path", ObjectUtils.toString(merchantCategory.getTreePath(),""));
		hash.put("parent_id", ObjectUtils.toString(merchantCategory.getParentId(), ""));
//			String result = RedisManager.getJedis().hmset(key, hash);
		flag = set_cbbank_merchant_category_detail_client_id_category_id(merchantCategory.getClientId(), merchantCategory.getId(), hash);
		return flag;

	}
	
	public static boolean set_cbbank_merchant_category_detail_client_id_category_id(String client_id, Long category_id, Map<String, String> hash) {
		String key = RedisKeyUtil.cbbank_merchant_category_detail_client_id_category_id(client_id, category_id);
		String result = redis.putMap(key, hash);
		return "OK".equals(result);
	}
	public static boolean set_cbbank_merchant_category_detail_client_id_category_id(String client_id, Long category_id, String field, String value) {
		String key = RedisKeyUtil.cbbank_merchant_category_detail_client_id_category_id(client_id, category_id);
		Long result = redis.hset(key, field, value);
		return result > -1;
	}
	
	/**
	 * 
	 * @Title: del_cbbank_merchant_category_detail_client_id_category_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param client_id
	 * @param category_id
	 * @return
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public static boolean del_cbbank_merchant_category_detail_client_id_category_id(String client_id, Long category_id) {
		String key = RedisKeyUtil.cbbank_merchant_category_detail_client_id_category_id(client_id, category_id);
		Long result = redis.del(key);
		return result > 0;
	}
	
	/**
	 * 
	 * @Title: del_cbbank_merchant_category_detail_client_id_category_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param client_id
	 * @param category_id
	 * @param fields
	 * @return
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public static boolean del_cbbank_merchant_category_detail_client_id_category_id(String client_id, Long category_id, String... fields) {
		String key = RedisKeyUtil.cbbank_merchant_category_detail_client_id_category_id(client_id, category_id);
		Long result = redis.hdel(key, fields);
		return result > -1;
	}
//-----------------
	/**
	 * 
	 * @Title: get_cbbank_merchant_category_all_client_id_merchant_category_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param client_id
	 * @param area_id
	 * @return
	 * @return Set<String>    返回类型 
	 * @throws
	 */
	public static Set<String> get_cbbank_merchant_category_all_client_id_merchant_category_id(String client_id, Long category_id) {
		String key = RedisKeyUtil.cbbank_merchant_category_all_client_id_merchant_category_id(client_id, category_id);
		Set<String> result = redis.getSet(key);
		return result;
	}

	/**
	 * 
	 * @Title: set_cbbank_merchant_category_all_client_id_merchant_category_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchant
	 * @return
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public static boolean set_cbbank_merchant_category_all_client_id_merchant_category_id(MerchantCategoryCG merchantCategory, String merchant_id) {
		return set_cbbank_merchant_category_all_client_id_merchant_category_id(merchantCategory.getClientId(), merchantCategory.getId(), merchant_id);
	}
	
	/**
	 * 
	 * @Title: set_cbbank_merchant_category_all_client_id_merchant_category_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param client_id
	 * @param category_id
	 * @param merchant_id
	 * @return
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public static boolean set_cbbank_merchant_category_all_client_id_merchant_category_id(String client_id, Long category_id, String merchant_id) {
		String key = RedisKeyUtil.cbbank_merchant_category_all_client_id_merchant_category_id( client_id, category_id);
		Boolean  result = redis.put(key, ObjectUtils.toString(merchant_id, "")) > -1;
		return result ;
	}
	
	public static boolean del_cbbank_merchant_category_all_client_id_merchant_category_id(String client_id, Long category_id, String merchant_id) {
		String key = RedisKeyUtil.cbbank_area_merchant_client_id_area_id( client_id, category_id);
		Long result = redis.srem(key, ObjectUtils.toString(merchant_id, ""));
		return result > -1;
	}
	

}
