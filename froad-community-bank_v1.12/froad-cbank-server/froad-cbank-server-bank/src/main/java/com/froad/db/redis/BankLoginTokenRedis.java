package com.froad.db.redis;


import java.util.UUID;

import com.froad.db.redis.impl.RedisManager;
import com.froad.util.RedisKeyUtil;



/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: BankLoginTokenRedis
 * @Description: 银行用户登录/银行联合登录Redis
 * @Author: ll
 * @Date: 2015年3月27日 上午10:00:49
 */
public class BankLoginTokenRedis {

	private static RedisManager redisManager = new RedisManager();
	
	/**
	 * 获取缓存 key中存储的值
	 * 
	 * @Description key结构为cbbank:bank_token:token<key-value>
	 * 银行用户登录value为对应的userid
	 * 银行联合登录value为org_code+username
	 * @author ll 20150327
	 * @version 1.0
	 * @param token 
	 * @return value值
	 * @throws
	 */
	public static String get_cbbank_bank_token_token(String token) {
		String key = RedisKeyUtil.cbbank_bank_token_token(token);
		return redisManager.getString(key);
	}
	
	
	
	/**
	 * 设置银行用户登录的Token
	 * 
	 * @Description key结构为cbbank:bank_token:token<key-value>
	 * 银行用户登录value为对应的userid
	 * 银行联合登录value为org_code+username   设置失效时间30分钟
	 * @author ll 20150328
	 * @param user_id
	 * @return String 返回token
	 * @throws
	 */
	public static String set_cbbank_bank_token_token(Long user_id) {
		String token=UUID.randomUUID().toString().replaceAll("-", "");
		String key = RedisKeyUtil.cbbank_bank_token_token(token);
		redisManager.putExpire(key, user_id+"", 1800);
		return token;
	}
	
	/**
	 * key:cbbank:bank_token:userid  value:token
	 * @param user_id
	 * @return
	 */
	public static String set_cbbank_bank_token_userid(String user_id,String token) {
		String key = RedisKeyUtil.cbbank_bank_token_token(user_id);
		redisManager.putExpire(key,token, 1800);
		return token;
	}
	
	
	/**
	 * 设置银行联合登录的Token
	 * 
	 * @Description key结构为cbbank:bank_token:token<key-value>
	 * 银行用户登录value为对应的userid
	 * 银行联合登录value为org_code+username   设置失效时间30分钟
	 * @author ll 20150328
	 * @param user_id
	 * @return String 返回token
	 * @throws
	 */
	public static String set_cbbank_bank_token_token(String orgCode,String userName) {
		String token=UUID.randomUUID().toString().replaceAll("-", "");
		//===>key:token  value:orgCode+"_"+username
		String key = RedisKeyUtil.cbbank_bank_token_token(token);
		redisManager.putExpire(key, orgCode+"_"+userName, 1800);
		
		//===>key:orgCode+"_"+username  value:token 
		key = RedisKeyUtil.cbbank_bank_token_token(orgCode+"_"+userName);
		redisManager.putExpire(key, token, 1800);
		
		return token;

	}
	
	
	/**
	 * 重新设置token值的失效时间30分钟
	 * 
	 * @Description key结构为cbbank:bank_token:token<key-value>
	 * 银行用户登录value为对应的userid
	 * 银行联合登录value为org_code+username   设置失效时间30分钟
	 * @param value 需要存储的value值
	 * @param token 需要的token值，根据该值取key
	 * @param boolean 是否操作成功
	 * @return
	 */
	public static Boolean set_cbbank_bank_token_token_Time(String userId,String token) {
		String key = RedisKeyUtil.cbbank_bank_token_token(token);
		boolean result = redisManager.putExpire(key, userId, 1800).equals("OK")?true:false;
		
		key = RedisKeyUtil.cbbank_bank_token_token(userId);
		result = redisManager.putExpire(key, token, 1800).equals("OK")?true:false;
		
		return result;
	}
	
	/**
	 * 
	 * @Title: del_cbbank_bank_token_token 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年4月7日
	 * @modify: froad-huangyihao 2015年4月7日
	 * @param token
	 * @return
	 * @throws
	 */
	public static Boolean del_cbbank_bank_token_token(String token){
		String key = RedisKeyUtil.cbbank_bank_token_token(token);
		return redisManager.del(key) > 0;
	}
	
	/**
	 * 删除userid的key
	 * key:cbbank:bank_token:userid  value:token
	 * @param userId
	 * @return
	 */
	public static Boolean del_cbbank_bank_token_userid(String userId){
		String key = RedisKeyUtil.cbbank_bank_token_token(userId);
		return redisManager.del(key) > 0;
	}
	
	
}
