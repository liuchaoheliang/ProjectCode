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
 * @Title: MerchantAccountRedis.java
 * @Package com.froad.db.redis
 * @Description: TODO
 * @author vania
 * @date 2015年3月23日
 */

package com.froad.common.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;


import com.froad.db.chonggou.entity.MerchantAccount;
import com.froad.db.redis.RedisService;
import com.froad.db.redis.impl.RedisManager;
import com.froad.util.RedisKeyUtil;


/**    
 * <p>Title: MerchantAccountRedis.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月23日 下午6:27:05   
 */

public class MerchantAccountRedis {
	
	
	private static RedisService redis = new RedisManager();
	
	/**
	 * 
	 * @Title: get_cbbank_merchant_client_id_merchant_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param client_id
	 * @param merchant_id
	 * @param outlet_id
	 * @param fields
	 * @return
	 * @return List<String>    返回类型 
	 * @throws
	 */
	public static List<String> get_cbbank_merchant_client_id_merchant_id(String client_id, String merchant_id, String outlet_id, String... fields) {
		String key = RedisKeyUtil.cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(client_id, merchant_id, outlet_id);
		List<String> result = redis.hmget(key, fields);
		return result;
	}
	
	/**
	 * 
	 * @Title: set_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantAccounts
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public static boolean set_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(MerchantAccount merchantAccount) {
		
		boolean flag = false;
//		for (MerchantAccount merchantAccount : merchantAccounts) {
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("acct_name", ObjectUtils.toString(merchantAccount.getAcctName(), ""));
		hash.put("acct_number", ObjectUtils.toString(merchantAccount.getAcctNumber(), ""));
		hash.put("acct_type", ObjectUtils.toString(merchantAccount.getAcctType(), ""));
		hash.put("opening_bank", ObjectUtils.toString(merchantAccount.getOpeningBank(), ""));
//			hash.put("is_enable", BooleanUtils.toString(merchantAccount.getIsEnable(), "1", "0", ""));
		flag = set_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(merchantAccount.getClientId(), merchantAccount.getMerchantId(), merchantAccount.getOutletId(), hash);
//		}
		return flag;
	} 
	
	public static boolean set_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(String client_id, String merchant_id, String outlet_id, Map<String, String> hash) {
		String key = RedisKeyUtil.cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(client_id, merchant_id, outlet_id);
		String result = redis.putMap(key, hash);
		return "OK".equals(result);
	}
	
	public static boolean set_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(String client_id, String merchant_id, String outlet_id, String field, String value) {
		String key = RedisKeyUtil.cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(client_id, merchant_id, outlet_id);
		Boolean result = redis.hset(key, field, value) > -1;
		return result;
	}
	
	public static boolean del_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(MerchantAccount merchantAccount) {
//		for (MerchantAccount merchantAccount : merchantAccounts) {
		return del_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(merchantAccount.getClientId(), merchantAccount.getMerchantId(), merchantAccount.getOutletId());
//		}
//		return true;
	}
	public static boolean del_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(String client_id, String merchant_id, String outlet_id) {
		String key = RedisKeyUtil.cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(client_id, merchant_id, outlet_id);
		Boolean result = redis.del(key) > 0;
		return result;
	}
	
	public static List<String> get_cbbank_fft_account_client_id(String client_id, String... fields) {
		String key = RedisKeyUtil.cbbank_fft_account_client_id(client_id);
		List<String> result = redis.hmget(key, fields);
		return result;
	}
	
	public static boolean set_cbbank_fft_account_client_id(MerchantAccount... merchantAccounts) {
		for (MerchantAccount merchantAccount : merchantAccounts) {
			Map<String, String> hash = new HashMap<String, String>();
			hash.put("merchant_id", ObjectUtils.toString(merchantAccount.getMerchantId(), ""));
			hash.put("acct_name", ObjectUtils.toString(merchantAccount.getAcctName(), ""));
			hash.put("acct_number", ObjectUtils.toString(merchantAccount.getAcctNumber(), ""));
			hash.put("acct_type", ObjectUtils.toString(merchantAccount.getAcctType(), ""));
			hash.put("opening_bank", ObjectUtils.toString(merchantAccount.getOpeningBank(), ""));
//			hash.put("is_enable", BooleanUtils.toString(merchantAccount.getIsEnable(), "1", "0", ""));
			
			set_cbbank_fft_account_client_id(merchantAccount.getClientId(),  hash);
		}
		return true;
	}
	
	public static boolean set_cbbank_fft_account_client_id(String client_id, Map<String, String> hash) {
		String key = RedisKeyUtil.cbbank_fft_account_client_id(client_id);
		Boolean result = "OK".equals(redis.putMap(key, hash));
		return result;
	}
	
	public static boolean set_cbbank_fft_account_client_id(String client_id, String field, String value) {
		String key = RedisKeyUtil.cbbank_fft_account_client_id(client_id);
		Boolean result = redis.hset(key, field, value) > -1;
		return result;
	}
	
	public static boolean del_cbbank_fft_account_client_id(MerchantAccount... merchantAccounts) {
		for (MerchantAccount merchantAccount : merchantAccounts) {
			del_cbbank_fft_account_client_id(merchantAccount.getClientId());
		}
		return true;
	}
	
	public static boolean del_cbbank_fft_account_client_id(String client_id) {
		String key = RedisKeyUtil.cbbank_fft_account_client_id(client_id);
		Boolean result = redis.del(key) > -1;
		return result;
	}
}
