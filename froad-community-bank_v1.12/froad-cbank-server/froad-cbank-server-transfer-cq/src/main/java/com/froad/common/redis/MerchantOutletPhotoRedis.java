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
 * @Title: MerchantOutletPhotoRedis.java
 * @Package com.froad.db.redis
 * @Description: TODO
 * @author vania
 * @date 2015年3月25日
 */

package com.froad.common.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;


import com.froad.db.chonggou.entity.MerchantOutletPhotoCG;
import com.froad.db.redis.RedisService;
import com.froad.db.redis.impl.RedisManager;
import com.froad.util.RedisKeyUtil;


/**    
 * <p>Title: MerchantOutletPhotoRedis.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月25日 下午1:23:57   
 */

public class MerchantOutletPhotoRedis {
	
	private static RedisService redis = new RedisManager();
	
	public static List<String> get_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(String client_id, String merchant_id, String oulet_id, String... fields) {
		String key = RedisKeyUtil.cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(client_id, merchant_id, oulet_id);
		List<String> result = redis.hmget(key, fields);
		return result;
	}
	
	public static Map<String,String> getALL_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(String client_id, String merchant_id, String oulet_id) {
		String key = RedisKeyUtil.cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(client_id, merchant_id, oulet_id);
		Map<String,String> result = redis.getMap(key);
		return result;
	}
	
	/**
	 * 
	 * @Title: set_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantOutletPhoto
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public static boolean set_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(String client_id, MerchantOutletPhotoCG merchantOutletPhoto) {
		boolean flag = false;
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("title", ObjectUtils.toString(merchantOutletPhoto.getTitle(), ""));
		hash.put("source", ObjectUtils.toString(merchantOutletPhoto.getSource(), ""));
		hash.put("large", ObjectUtils.toString(merchantOutletPhoto.getLarge(), ""));
		hash.put("medium", ObjectUtils.toString(merchantOutletPhoto.getMedium(), ""));
		hash.put("thumbnail", ObjectUtils.toString(merchantOutletPhoto.getThumbnail(), ""));
		flag = set_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(client_id, merchantOutletPhoto.getMerchantId(), merchantOutletPhoto.getOutletId(), hash);
		return flag;
	}
	
	public static boolean set_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(String client_id, String merchant_id, String oulet_id, Map<String, String> hash) {
		String key = RedisKeyUtil.cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(client_id, merchant_id, oulet_id);
		String result = redis.putMap(key, hash);
		return "OK".equals(result);
	}
	
	public static boolean set_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(String client_id, String merchant_id, String oulet_id, String field, String value) {
		String key = RedisKeyUtil.cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(client_id, merchant_id, oulet_id);
		long result = redis.hset(key, field, value);
		return result > -1;
	}
	
	public static boolean del_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(String client_id, MerchantOutletPhotoCG merchantOutletPhoto) {
		String key = RedisKeyUtil.cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(client_id, merchantOutletPhoto.getMerchantId(), merchantOutletPhoto.getOutletId());
		long result = redis.del(key);
		return result > 0;
	}
}
