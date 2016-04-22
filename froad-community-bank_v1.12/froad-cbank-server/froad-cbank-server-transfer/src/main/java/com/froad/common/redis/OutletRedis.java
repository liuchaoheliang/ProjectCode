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
 * @Title: OutletRedis.java
 * @Package com.froad.db.redis
 * @Description: TODO
 * @author vania
 * @date 2015年3月25日
 */

package com.froad.common.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import redis.clients.jedis.Jedis;

import com.froad.db.chonggou.entity.OutletCG;
import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.util.RedisKeyUtil;


/**    
 * <p>Title: OutletRedis.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月25日 上午9:22:14   
 */

public class OutletRedis {
	private static RedisManager redisManager = new RedisManager();
	public static void get_cbbank_merchant_outlet_client_id_merchant_id() {
		return ;
	}
	
	public static boolean set_cbbank_merchant_outlet_client_id_merchant_id(OutletCG... outlets) {
		for (OutletCG outlet : outlets) {
			String key = RedisKeyUtil.cbbank_merchant_outlet_client_id_merchant_id(outlet.getClientId(), outlet.getMerchantId());
			redisManager.zadd(key, outlet.getOrderValue(), outlet.getOutletId());
//			Map<String, Double> scoreMembers = new HashMap<String, Double>();
//			scoreMembers.put(outlet.getOutletId(), (double) outlet.getOrderValue());
//			RedisManager.getJedis().zadd(key, scoreMembers);
			LogCvt.info("设置缓存:" + key + "成功!");
		}
		return true;
	}
	public static boolean del_cbbank_merchant_outlet_client_id_merchant_id(OutletCG... outlets) {
//		String[] keys = new String[outlets.length];
//		for (int i = 0; i < outlets.length; i++) {
//			Outlet outlet = outlets[0];
//			keys[i] = RedisKeyUtil.cbbank_merchant_outlet_client_id_merchant_id(outlet.getClientId(), outlet.getMerchantId());
//		}
		int i = 0;
		for (; i < outlets.length; i++) {
			OutletCG outlet = outlets[0];
			String key = RedisKeyUtil.cbbank_merchant_outlet_client_id_merchant_id(outlet.getClientId(), outlet.getMerchantId());
			redisManager.zrem(key, outlet.getOutletId());
			LogCvt.info("删除缓存:" + key + "成功!");
		}
//		return i > -1;
		return true;
	}
	/**
	 * 
	 * @Title: set_cbbank_outlet_client_id_merchant_id_outlet_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param outlets
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public static boolean set_cbbank_outlet_client_id_merchant_id_outlet_id(OutletCG... outlets) {
		for (OutletCG outlet : outlets) {
//			String key = RedisKeyUtil.cbbank_outlet_client_id_merchant_id_outlet_id(outlet.getClientId(), outlet.getMerchantId(), outlet.getOutletId());
			Map<String, String> hash = new HashMap<String, String>();
			hash.put("outlet_name", ObjectUtils.toString(outlet.getOutletName(), ""));
			hash.put("outlet_status", BooleanUtils.toString(outlet.getOutletStatus(), "1", "0", ""));
			hash.put("longitude", ObjectUtils.toString(outlet.getLongitude(), ""));
			hash.put("latitude", ObjectUtils.toString(outlet.getLatitude(), ""));
			hash.put("is_enable", BooleanUtils.toString(outlet.getIsEnable(), "1", "0", ""));
			set_cbbank_outlet_client_id_merchant_id_outlet_id(outlet.getClientId(), outlet.getMerchantId(), outlet.getOutletId(), hash);
		}
		return true;
	}
	
	public static boolean set_cbbank_outlet_client_id_merchant_id_outlet_id(String client_id, String merchant_id, String oulet_id, Map<String, String> hash) {
		String key = RedisKeyUtil.cbbank_outlet_client_id_merchant_id_outlet_id(client_id, merchant_id, oulet_id);
		boolean result = "OK".equals(redisManager.putMap(key, hash));
		LogCvt.info("设置缓存:" + key + "成功!");
		return result;
	}
	
	public static boolean set_cbbank_outlet_client_id_merchant_id_outlet_id(String client_id, String merchant_id, String oulet_id, String field, String value) {
		String key = RedisKeyUtil.cbbank_outlet_client_id_merchant_id_outlet_id(client_id, merchant_id, oulet_id);
		boolean result = redisManager.hset(key, field, value) > -1;
		LogCvt.info("设置缓存:" + key + "成功!");
		return result;
	}
	
	public static boolean del_cbbank_outlet_client_id_merchant_id_outlet_id(OutletCG... outlets) {
		for (OutletCG outlet : outlets) {
			String key = RedisKeyUtil.cbbank_outlet_client_id_merchant_id_outlet_id(outlet.getClientId(), outlet.getMerchantId(), outlet.getOutletId());
			redisManager.hset(key, "is_enable", BooleanUtils.toString(false, "1", "0")) ;
			LogCvt.info("删除缓存:" + key + "成功!");
		}
		return true;
	}
	
	public static Map<String,String> get_cbbank_outlet_client_id_merchant_id_outlet_id(String client_id,String merchant_id,String oulet_id) {
		String key = RedisKeyUtil.cbbank_outlet_client_id_merchant_id_outlet_id(client_id, merchant_id, oulet_id);
		return redisManager.getMap(key);
	}
}
