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
 * @Title: RedisTest.java
 * @Package com.froad.thrift
 * @Description: TODO
 * @author vania
 * @date 2015年4月27日
 */

package com.froad.thrift;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.froad.db.redis.impl.RedisManager;
import com.froad.po.MerchantType;
import com.froad.util.PropertiesUtil;
import com.froad.util.RedisKeyUtil;


/**    
 * <p>Title: RedisTest.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年4月27日 下午2:43:41   
 */

public class RedisTest {
	private static RedisManager redisManager = null;
	static {
		PropertiesUtil.load();
		
		redisManager = new RedisManager();
	}
	/** 
	 * @Title: main 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param args
	 * @return void    返回类型 
	 * @throws 
	 */
	public static void main(String[] args) {
		MerchantType merchantType = new MerchantType();
		merchantType.setId(100000001l);
		merchantType.setTypeName("直接优惠");
		merchantType.setType("1");
		merchantType.setIsDelete(false);
		
//		setMerchantRedis(merchantType);
		
		Map<String,String> map = getAll_cbbank_merchant_type_merchant_type_id();
		for (String key : map.keySet()) {
//			System.out.println(key);
			System.out.println(map.get(key));
		}
		
	}

	public static void setMerchantRedis(MerchantType merchantType) {		
//		String key = RedisKeyUtil.cbbank_merchant_type_merchant_type_id();
//		Set<String> set = new HashSet<String>();
//		set.add(String.valueOf(merchantType.getId())+","+merchantType.getTypeName()+","+merchantType.getType());
////		set.add(merchantType.getTypeName());
////		set.add(merchantType.getType());
////		set.addAll((Collection<? extends String>) merchantType);
//	
//		long n = redisManager.putSet(key, set);
		
//		String key = RedisKeyUtil.cbbank_merchant_type_merchant_type_id();
		Map<String,String> hash = new HashMap<String, String>();
//		hash.put(ObjectUtils.toString(merchantType.getId(), ""), ObjectUtils.toString(merchantType.getTypeName(), ""));
		hash.put(ObjectUtils.toString(merchantType.getId(), ""),String.valueOf(merchantType.getId())+","+merchantType.getTypeName()+","+merchantType.getType());
//		hash.put(ObjectUtils.toString(merchantType.getId(), ""), ObjectUtils.toString(merchantType.getType(), ""));
//		String result = redisManager.putMap(key, hash);
	}
	
	public static Map<String,String> getAll_cbbank_merchant_type_merchant_type_id() {
//		String key = RedisKeyUtil.cbbank_merchant_type_merchant_type_id();
//		Map<String,String> result = redisManager.getMap(key);
//		return result;
		return new HashMap<String, String>();
	}
}
