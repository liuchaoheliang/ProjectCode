package com.froad.common.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import redis.clients.jedis.Jedis;

import com.froad.db.chonggou.entity.MerchantUserCG;
import com.froad.db.redis.RedisService;
import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.util.RedisKeyUtil;

/**    
 * <p>Title: MerchantUserRedis.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月29日 下午6:27:05   
 */
public class MerchantUserRedis {
	
	private static RedisService redis = new RedisManager();

	public static boolean set_cbbank_merchant_outlet_user_client_id_merchant_id_user_id(MerchantUserCG merchantUser){
		String key = RedisKeyUtil.cbbank_merchant_outlet_user_client_id_merchant_id_user_id(merchantUser.getClientId(), merchantUser.getMerchantId(), merchantUser.getId());
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("username", ObjectUtils.toString(merchantUser.getUsername(), ""));
		valueMap.put("password", ObjectUtils.toString(merchantUser.getPassword(), ""));
		valueMap.put("outlet_id", ObjectUtils.toString(merchantUser.getOutletId(), ""));
		valueMap.put("merchant_role_id", ObjectUtils.toString(merchantUser.getMerchantRoleId(), ""));
		String result = redis.putMap(key, valueMap);
		return "OK".equals(result);
	}
	
	public static boolean del_cbbank_merchant_outlet_user_client_id_merchant_id_user_id(MerchantUserCG merchantUser){
		String key = RedisKeyUtil.cbbank_merchant_outlet_user_client_id_merchant_id_user_id(merchantUser.getClientId(), merchantUser.getMerchantId(), merchantUser.getId());
		long  result = redis.del(key);
		return result > -1;
	}
	
	public static String get_cbbank_merchant_token_token_value(String token_value){
		String key = RedisKeyUtil.cbbank_merchant_token_token_value(token_value);
		String result = redis.getString(key);
		return result;
	}
	
	public static boolean del_cbbank_merchant_token_token_value(String token_value){
		String key = RedisKeyUtil.cbbank_merchant_token_token_value(token_value);
		long result = redis.del(key);
		return result > -1;
	} 
	
	public static boolean set_cbbank_merchant_token_token_value(String token_value, long user_id){
		String key = RedisKeyUtil.cbbank_merchant_token_token_value(token_value);
		String  result = redis.putExpire(key,  ObjectUtils.toString(user_id, ""),1800);
		return "OK".equals(result);
	}
	
	public static String get_cbbank_merchant_outlet_user_login_client_id_merchant_id_merchant_user_id(String client_id, String merchant_id, long merchant_user_id){
		String key = RedisKeyUtil.cbbank_merchant_outlet_user_login_client_id_merchant_id_merchant_user_id(client_id, merchant_id, merchant_user_id);
		String result = redis.getString(key);
		return result;
	}
	
	public static boolean set_cbbank_merchant_outlet_user_login_client_id_merchant_id_merchant_user_id(String client_id, String merchant_id, long merchant_user_id, int count){
		String key = RedisKeyUtil.cbbank_merchant_outlet_user_login_client_id_merchant_id_merchant_user_id(client_id, merchant_id, merchant_user_id);
		String result = redis.putString(key, ObjectUtils.toString(count, ""));
		return "OK".equals(result);
	}
}