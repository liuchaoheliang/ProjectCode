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

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.db.chonggou.entity.MerchantCG;
import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.util.RedisKeyUtil;


/**    
 * <p>Title: MerchantRedis.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月20日 上午10:11:55   
 */

public class MerchantRedis {
	private  RedisManager redisManager = null;
	
	public MerchantRedis(RedisManager redis){
		redisManager = redis;
	}
	/**
	 * 获取缓存表数据全部在Redis中缓存 key结构为cbbank:merchant:client_id:merchant_id
	 * 
	 * @Title: get_cbbank_merchant_client_id_merchant_id
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param client_id
	 * @param merchant_id
	 * @param fields
	 * @return
	 * @return List<String> 返回类型
	 * @throws
	 */
	public  List<String> get_cbbank_merchant_client_id_merchant_id(String client_id, String merchant_id, String... fields){
		String key = RedisKeyUtil.cbbank_merchant_client_id_merchant_id(client_id, merchant_id) ;
		List<String> result = redisManager.hmget(key, fields);
		return result;
	}
	
	/**
	 * 
	 * @Title: get_cbbank_merchant_client_id_merchant_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param client_id
	 * @param merchant_id
	 * @return
	 * @throws Exception
	 * @return Map<String,String>    返回类型 
	 * @throws
	 */
	public  Map<String, String> get_cbbank_merchant_client_id_merchant_id(String client_id, String merchant_id){
		String key = RedisKeyUtil.cbbank_merchant_client_id_merchant_id(client_id, merchant_id) ;
		return redisManager.getMap(key);
	}

	/**
	 * 缓存表数据全部在Redis中缓存 key结构为cbbank:merchant:client_id:merchant_id
	 * 
	 * @Title: set_cbbank_merchant_client_id_merchant_id
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchants
	 * @return
	 * @return Boolean 返回类型
	 * @throws
	 */
	public  boolean set_cbbank_merchant_client_id_merchant_id(MerchantCG merchant){
		/* 缓存全部商户 */
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("merchant_name", ObjectUtils.toString(merchant.getMerchantName(), ""));
		hash.put("logo", ObjectUtils.toString(merchant.getLogo(), ""));
		hash.put("phone", ObjectUtils.toString(merchant.getPhone(), ""));
		hash.put("area_id", ObjectUtils.toString(merchant.getAreaId(), ""));
		hash.put("merchant_status", BooleanUtils.toString(merchant.getMerchantStatus(), "1", "0", ""));
		hash.put("org_code", ObjectUtils.toString(merchant.getOrgCode(), ""));
		hash.put("audit_org_code", ObjectUtils.toString(merchant.getAuditOrgCode(), ""));
		hash.put("audit_state", ObjectUtils.toString(merchant.getAuditState(), ""));
		hash.put("audit_stage", ObjectUtils.toString(merchant.getAuditStage(), ""));
		hash.put("contract_endtime", ObjectUtils.toString(merchant.getContractEndtime().getTime(), ""));
		hash.put("is_enable", BooleanUtils.toString(merchant.getIsEnable(), "1", "0", ""));
		hash.put("area_id", ObjectUtils.toString(merchant.getAreaId(), ""));

		boolean b = set_cbbank_merchant_client_id_merchant_id(merchant.getClientId(), merchant.getMerchantId(), hash);
		return b;

	}
	
	public  boolean set_cbbank_merchant_client_id_merchant_id(String client_id, String merchant_id, Map<String, String> hash) {
		String key = RedisKeyUtil.cbbank_merchant_client_id_merchant_id(client_id, merchant_id);
		Boolean result = "OK".equals(redisManager.putMap(key, hash));
		LogCvt.info("设置缓存:" + key + "成功!");
		return result;
	}
	public  boolean set_cbbank_merchant_client_id_merchant_id(String client_id, String merchant_id, String field, String value) {
		String key = RedisKeyUtil.cbbank_merchant_client_id_merchant_id(client_id, merchant_id);
//		Long result = RedisManager.getJedis().hsetnx(key, field, value);
		Long result = redisManager.hset(key, field, value);
		LogCvt.info("设置缓存:" + key + "成功!");
		return result > -1;
	}
	
	/**
	 * 删除缓存表数据全部在Redis中缓存 key结构为cbbank:merchant:client_id:merchant_id
	 * @Title: del_cbbank_merchant_client_id_merchant_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param client_id
	 * @param merchant_id
	 * @param fields
	 * @return
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public  boolean del_cbbank_merchant_client_id_merchant_id(String client_id, String merchant_id, String... fields) {
		String key = RedisKeyUtil.cbbank_merchant_client_id_merchant_id(client_id, merchant_id);
		long result = redisManager.hdel(key, fields);
		LogCvt.info("删除缓存:" + key + "成功!");
		return result > -1;
	}
	
	/**
	 * 删除缓存表数据全部在Redis中缓存 key结构为cbbank:merchant:client_id:merchant_id
	 * @Title: del_cbbank_merchant_client_id_merchant_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param client_id
	 * @param merchant_id
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public  boolean del_cbbank_merchant_client_id_merchant_id(String client_id, String merchant_id) {
		String key = RedisKeyUtil.cbbank_merchant_client_id_merchant_id(client_id, merchant_id);
		long result = redisManager.hset(key, "is_enable", BooleanUtils.toString(false, "1", "0")) ;
//		set_cbbank_merchant_client_id_merchant_id(client_id, merchant_id, "is_enable", value);
		LogCvt.info("删除缓存:" + key + "成功!");
		return result > -1;
	}
	
	public  boolean remove_cbbank_merchant_client_id_merchant_id(String client_id, String merchant_id) {
		String key = RedisKeyUtil.cbbank_merchant_client_id_merchant_id(client_id, merchant_id);
		long result = redisManager.del(key) ;
//		set_cbbank_merchant_client_id_merchant_id(client_id, merchant_id, "is_enable", value);
		LogCvt.info("物理删除缓存:" + key + "成功!");
		return result > -1;
	}

	/**
	 * 查询该客户端下该地区所有商户ID
	 * 
	 * @Title: get_cbbank_area_merchant_client_id_area_id
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param client_id
	 * @param area_id
	 * @return
	 * @return Set<String> 返回类型
	 * @throws
	 */
	public  Set<String> get_cbbank_area_merchant_client_id_area_id(String client_id, Long area_id) {
		String key = RedisKeyUtil.cbbank_area_merchant_client_id_area_id(client_id, area_id);
		Set<String> result = redisManager.getSet(key);
		return result;
	}

	/**
	 * 银行商户缓存
	 * @Title: set_cbbank_bank_merchant_client_id_org_code 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchants
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public  boolean set_cbbank_bank_merchant_client_id_org_code (MerchantCG merchant){
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("merchant_id", ObjectUtils.toString(merchant.getMerchantId(), ""));
		hash.put("merchant_name", ObjectUtils.toString(merchant.getMerchantName(), ""));
		hash.put("logo", ObjectUtils.toString(merchant.getLogo(), ""));
		hash.put("phone", ObjectUtils.toString(merchant.getPhone(), ""));
		hash.put("area_id", ObjectUtils.toString(merchant.getAreaId(), ""));
		hash.put("merchant_status", BooleanUtils.toString(merchant.getMerchantStatus(), "1", "0", ""));
		
		hash.put("is_enable", BooleanUtils.toString(merchant.getIsEnable(), "1", "0", ""));
		
		boolean b = set_cbbank_bank_merchant_client_id_org_code(merchant.getClientId(), merchant.getOrgCode(), hash);
		return b;
	}
	
	/**
	 * 设置银行商户缓存
	 * @Title: set_cbbank_bank_merchant_client_id_org_code 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @param orgCode
	 * @param hash
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public  boolean set_cbbank_bank_merchant_client_id_org_code (String clientId,String orgCode,Map<String, String> hash){
		String key = RedisKeyUtil.cbbank_bank_merchant_client_id_org_code(clientId, orgCode);
		Boolean result = "OK".equals(redisManager.putMap(key, hash));
		LogCvt.info("设置缓存:" + key + "成功!");
		return result;
	}
	
	public  boolean del_cbbank_bank_merchant_client_id_org_code (String clientId,String orgCode){
		String key = RedisKeyUtil.cbbank_bank_merchant_client_id_org_code(clientId, orgCode);
		long result = redisManager.hset(key, "is_enable", BooleanUtils.toString(false, "1", "0")) ;
		LogCvt.info("删除缓存:" + key + "成功!");
		return result > -1;
	}
	
	public  boolean remove_cbbank_bank_merchant_client_id_org_code (String clientId,String orgCode){
		String key = RedisKeyUtil.cbbank_bank_merchant_client_id_org_code(clientId, orgCode);
		long result = redisManager.del(key) ;
		LogCvt.info("删除缓存:" + key + "成功!");
		return result > -1;
	}
	
	public  Map<String, String> get_cbbank_bank_merchant_client_id_org_code(String clientId,String orgCode){
		String key = RedisKeyUtil.cbbank_bank_merchant_client_id_org_code(clientId, orgCode);
		return redisManager.getMap(key);
	}
	
	/**
	 * 缓存该客户端下该地区所有商户ID
	 * 
	 * @Title: set_cbbank_area_merchant_client_id_area_id_merchant_id
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchant
	 * @return
	 * @return Boolean 返回类型
	 * @throws
	 */
	public  boolean set_cbbank_area_merchant_client_id_area_id(MerchantCG merchant){
		boolean b =	set_cbbank_area_merchant_client_id_area_id(merchant.getClientId(), merchant.getAreaId(),merchant.getMerchantId());
		return b;
	}
	
	
	/**
	 * 缓存该客户端下该地区所有商户ID
	 * @Title: set_cbbank_area_merchant_client_id_area_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param client_id
	 * @param area_id
	 * @param merchant_id
	 * @return
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public  boolean set_cbbank_area_merchant_client_id_area_id(String client_id, Long area_id, String merchant_id)  {
		String key = RedisKeyUtil.cbbank_area_merchant_client_id_area_id( client_id, area_id);
		Long result = redisManager.put(key, ObjectUtils.toString(merchant_id, ""));
		LogCvt.info("设置缓存:" + result + "成功!");
		return result > -1;
	}
	
	public  boolean del_cbbank_area_merchant_client_id_area_id(String client_id, Long area_id, String merchant_id) {
		String key = RedisKeyUtil.cbbank_area_merchant_client_id_area_id( client_id, area_id);
		Long result = redisManager.srem(key, ObjectUtils.toString(merchant_id, ""));
		LogCvt.info("删除缓存:" + key + "成功!");
		return result > -1;
	}
	
	/**
	 * 查询该客户端下所有置顶商户ID
	 * @Title: get_cbbank_top_merchant_client_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param client_id
	 * @return
	 * @return Set<String>    返回类型 
	 * @throws
	 */
	public  Set<String> get_cbbank_top_merchant_client_id(String client_id) {
		String key = RedisKeyUtil.cbbank_top_merchant_client_id(client_id);
		Set<String> result = redisManager.getSet(key);
		return result;
	}
	
	/**
	 * 缓存该客户端下所有置顶商户ID
	 * @Title: set_cbbank_top_merchant_client_id 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchant
	 * @return
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public  boolean set_cbbank_top_merchant_client_id(MerchantCG merchant) {
		return set_cbbank_top_merchant_client_id(merchant.getClientId(), merchant.getMerchantId());
	}
	
	/**
	 * 缓存该客户端下所有置顶商户ID
	 * 
	 * @Title: set_cbbank_top_merchant_client_id
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param client_id
	 * @param merchant_id
	 * @return
	 * @return Boolean 返回类型
	 * @throws
	 */
	public  boolean set_cbbank_top_merchant_client_id(String client_id, String merchant_id) {
		String key = RedisKeyUtil.cbbank_top_merchant_client_id(client_id);
		Long result = redisManager.put(key, ObjectUtils.toString(merchant_id, ""));
		LogCvt.info("设置缓存:" + key + "成功!");
		return result > -1;
	}
	
	public  boolean del_cbbank_top_merchant_client_id(String client_id, String merchant_id) {
		String key = RedisKeyUtil.cbbank_top_merchant_client_id(client_id);
//		Long result = RedisManager.getJedis().srem(key, ObjectUtils.toString(merchant_id));
		Long result = redisManager.del(key);
		LogCvt.info("删除缓存:" + key + "成功!");
		return result > -1;
	}
	
//	SREM 

}