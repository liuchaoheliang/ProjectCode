///*   
// * Copyright © 2008 F-Road All Rights Reserved.   
// *   
// * This software is the confidential and proprietary information of   
// * Founder. You shall not disclose such Confidential Information   
// * and shall use it only in accordance with the terms of the agreements   
// * you entered into with Founder.   
// *   
// */
//  
///**  
// * @Title: MerchantTypeRedis.java
// * @Package com.froad.db.redis
// * @Description: TODO
// * @author vania
// * @date 2015年3月20日
// */
//
//package com.froad.common.redis;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.lang.ObjectUtils;
//
//import redis.clients.jedis.Jedis;
//
//import com.froad.db.chonggou.entity.MerchantTypeCG;
//import com.froad.db.redis.impl.RedisManager;
//import com.froad.logback.LogCvt;
//import com.froad.util.RedisKeyUtil;
//
//
//
///**    
// * <p>Title: MerchantTypeRedis.java</p>    
// * <p>Description: 描述 </p>   
// * @author vania      
// * @version 1.0    
// * @created 2015年3月23日 下午3:11:26   
// */   
//public class MerchantTypeRedis {
//	private static RedisManager redisManager = new RedisManager();
//	
//	/**
//	 * 
//	 * @Title: get_cbbank_merchant_type_merchant_type_id 
//	 * @author vania
//	 * @version 1.0
//	 * @see: TODO
//	 * @param type_id
//	 * @return
//	 * @return String    返回类型 
//	 * @throws
//	 */
//	public static String get_cbbank_merchant_type_merchant_type_id(Long type_id) {
////		String key = RedisKeyUtil.cbbank_merchant_type_merchant_type_id( type_id) ;
////		return RedisManager.getJedis().get(key);
//		String key = RedisKeyUtil.cbbank_merchant_type_merchant_type_id() ;
//		String result = redisManager.getMapValue(key, ObjectUtils.toString(type_id, ""));
//		
//		return result;
//	}
//	
//	/**
//	 * 
//	 * @Title: getAll_cbbank_merchant_type_merchant_type_id 
//	 * @author vania
//	 * @version 1.0
//	 * @see: TODO
//	 * @param type_id
//	 * @return
//	 * @return Map<String,String>    返回类型 
//	 * @throws
//	 */
//	public static Map<String, String> getAll_cbbank_merchant_type_merchant_type_id(Long type_id) {
//		String key = RedisKeyUtil.cbbank_merchant_type_merchant_type_id();
//		Map<String, String> result = redisManager.getMap(key);
//		return result;
//	}
//
//	/**
//	 * 
//	 * @Title: set_cbbank_merchant_type_detail_client_id_type_id 
//	 * @author vania
//	 * @version 1.0
//	 * @see: TODO
//	 * @param merchantCategorys
//	 * @return
//	 * @return Boolean    返回类型 
//	 * @throws
//	 */
//	public static boolean set_cbbank_merchant_type_merchant_type_id(MerchantTypeCG... merchantTypes) {
//		String key = RedisKeyUtil.cbbank_merchant_type_merchant_type_id();
//		Map<String,String> hash = new HashMap<String, String>();
//		for (MerchantTypeCG merchantType : merchantTypes) {
////			hash.put("merchant_type_id", ObjectUtils.toString(merchantType.getMerchantTypeId(), ""));
////			hash.put("type_name", ObjectUtils.toString(merchantType.getTypeName(), ""));
//			hash.put(ObjectUtils.toString(merchantType.getId(), ""), ObjectUtils.toString(merchantType.getTypeName(), ""));
//		}
//		String result = redisManager.putMap(key, hash);
//		
//		return "OK".equals(result);
//
//	}
//	
//	public static boolean set_cbbank_merchant_type_merchant_type_id(Long type_id, String type_name) {
////		String key = RedisKeyUtil.cbbank_merchant_type_merchant_type_id(type_id);
////		String result = RedisManager.getJedis().set(key, type_name);
////		return "OK".equals(result);
//		String key = RedisKeyUtil.cbbank_merchant_type_merchant_type_id();
//		Long result = redisManager.hset(key, ObjectUtils.toString(type_id, ""), type_name);
//		
//		return result > -1;
//	}
//	
//	/**
//	 * 
//	 * @Title: del_cbbank_merchant_type_merchant_type_id 
//	 * @author vania
//	 * @version 1.0
//	 * @see: TODO
//	 * @param type_id
//	 * @return
//	 * @return Boolean    返回类型 
//	 * @throws
//	 */
//	public static boolean del_cbbank_merchant_type_merchant_type_id(Long type_id) {
////		String key = RedisKeyUtil.cbbank_merchant_type_merchant_type_id(type_id);
////		return RedisManager.getJedis().del(key) > -1;
//		String key = RedisKeyUtil.cbbank_merchant_type_merchant_type_id();
//		long result = redisManager.srem(key, ObjectUtils.toString(type_id, ""));
//		
//		return result > -1;
//	}
//	
//	
//
//}
