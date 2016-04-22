/**  
 * @Title: MerchantAccountRedis.java
 * @Package com.froad.db.redis
 * @Description: TODO
 * @author vania
 * @date 2015年3月23日
 */

package com.froad.db.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.po.MerchantAccount;
import com.froad.util.Checker;
import com.froad.util.RedisKeyUtil;


/**    
 * <p>Title: MerchantAccountRedis.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月23日 下午6:27:05   
 */

public class MerchantAccountRedis {
	
	private static RedisManager redisManager = new RedisManager();
//	private static RedisService redis = new RedisManager();
	
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
	public static List<String> get_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(String client_id, String merchant_id, String outlet_id, String... fields) {
		String key = RedisKeyUtil.cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(client_id, merchant_id, outlet_id);
		List<String> result = redisManager.hmget(key, fields);
		return result;
	}
	
	/**
	 * 获取所有fields值
	 * 
	 * @Description:<使用Redis的哈希结构存储HSET key field value
	 *	key为cbbank:merchant_outlet_account:client_id:merchant_id:outlet_id
	 *	field包含acct_name/acct_number/acct_type/opening_bank
	 * @author ll 20151019
	 * @param client_id 客户端Id
	 * @param merchant_id 商户Id
	 * @param outlet_id 门店Id
	 * @return Map<String,String> 所有value值
	 * @throws
	 */
	public static Map<String,String> getAll_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(String client_id,String merchant_id,String outlet_id) {
		String key = RedisKeyUtil.cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(client_id, merchant_id, outlet_id);
		return redisManager.getMap(key);
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
		
		Map<String, String> hash = new HashMap<String, String>();
		if(Checker.isNotEmpty(merchantAccount.getAcctName())){
			hash.put("acct_name", ObjectUtils.toString(merchantAccount.getAcctName(), ""));
		}
		if(Checker.isNotEmpty(merchantAccount.getAcctNumber())){
			hash.put("acct_number", ObjectUtils.toString(merchantAccount.getAcctNumber(), ""));
		}
		if(Checker.isNotEmpty(merchantAccount.getAcctType())){
			hash.put("acct_type", ObjectUtils.toString(merchantAccount.getAcctType(), ""));
		}
		if(Checker.isNotEmpty(merchantAccount.getOpeningBank())){
			hash.put("opening_bank", ObjectUtils.toString(merchantAccount.getOpeningBank(), ""));
		}
		
		flag = set_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(merchantAccount.getClientId(), merchantAccount.getMerchantId(), merchantAccount.getOutletId(), hash);
		return flag;
	} 
	
	public static boolean set_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(String client_id, String merchant_id, String outlet_id, Map<String, String> hash) {
		String key = RedisKeyUtil.cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(client_id, merchant_id, outlet_id);
		String result = redisManager.putMap(key, hash);
		return "OK".equals(result);
	}
	
	public static boolean set_cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(String client_id, String merchant_id, String outlet_id, String field, String value) {
		String key = RedisKeyUtil.cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(client_id, merchant_id, outlet_id);
		Boolean result = redisManager.hset(key, field, value) > -1;
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
		Boolean result = redisManager.del(key) > 0;
		return result;
	}
	
	public static List<String> get_cbbank_fft_account_client_id(String client_id, String... fields) {
		String key = RedisKeyUtil.cbbank_fft_account_client_id(client_id);
		List<String> result = redisManager.hmget(key, fields);
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
		Boolean result = "OK".equals(redisManager.putMap(key, hash));
		return result;
	}
	
	public static boolean set_cbbank_fft_account_client_id(String client_id, String field, String value) {
		String key = RedisKeyUtil.cbbank_fft_account_client_id(client_id);
		Boolean result = redisManager.hset(key, field, value) > -1;
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
		Boolean result = redisManager.del(key) > -1;
		return result;
	}
}
