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

package com.froad.db.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.po.Merchant;
import com.froad.util.Checker;
import com.froad.util.RedisKeyUtil;


/**    
 * <p>Title: MerchantRedis.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月20日 上午10:11:55   
 */

public class MerchantRedis {
	private static RedisManager redisManager = new RedisManager();
	
//	public static final String CBBANK_MERCHANT_CLIENT_ID_MERCHANT_ID = "cbbank:merchant:{0}:{1}"; // key结构为  cbbank:merchant:client_id:merchant_id
//	public static final String CBBANK_AREA_MERCHANT_CLIENT_ID_AREA_ID = "cbbank:area_merchant:{0}:{1}" ; // key结构为    cbbank:area_merchant:client_id:area_id
//	public static final String CBBANK_TOP_MERCHANT_CLIENT_ID = "cbbank:top_merchant:{0}" ; // key结构为    cbbank:top_merchant:client_id
	
//	public static boolean set_cbank_preaudit_merchant_count_client_id_org_code(String client_id, String orgCode, long increment) throws Exception{
//		String key = RedisKeyUtil.cbank_preaudit_merchant_count_client_id_org_code(client_id, orgCode);
//		boolean result = redisManager.incrBy(key, increment) > -1;
//		LogCvt.info("设置缓存:" + key + "成功!");
//		return result;
//		
//	}
	
//	public static long get_cbank_preaudit_merchant_count_client_id_org_code(String client_id, String orgCode) throws Exception{
//		String key = RedisKeyUtil.cbank_preaudit_merchant_count_client_id_org_code(client_id, orgCode);
//		return NumberUtils.toLong(redisManager.getString(key), 0);
//	}
	
	
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
	public static List<String> get_cbbank_merchant_client_id_merchant_id(String client_id, String merchant_id, String... fields){
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
	public static Map<String, String> get_cbbank_merchant_client_id_merchant_id(String client_id, String merchant_id){
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
	public static boolean set_cbbank_merchant_client_id_merchant_id(Merchant... merchants) throws Exception{
		for (Merchant merchant : merchants) {
			/* 缓存全部商户 */
//			String key = RedisKeyUtil.cbbank_merchant_client_id_merchant_id(merchant.getClientId(), merchant.getMerchantId()) ;

			Map<String, String> hash = new HashMap<String, String>();
			hash.put("merchant_name", ObjectUtils.toString(merchant.getMerchantName(), ""));
			hash.put("logo", ObjectUtils.toString(merchant.getLogo(), ""));
			hash.put("phone", ObjectUtils.toString(merchant.getPhone(), ""));
			hash.put("area_id", ObjectUtils.toString(merchant.getAreaId(), ""));
			hash.put("merchant_status", BooleanUtils.toString(merchant.getMerchantStatus(), "1", "0", ""));
			hash.put("org_code", ObjectUtils.toString(merchant.getOrgCode(), ""));
			hash.put("audit_org_code", ObjectUtils.toString(merchant.getAuditOrgCode(), ""));
//			hash.put("need_review", ObjectUtils.toString(merchant.getNeedReview(), ""));
			hash.put("audit_state", ObjectUtils.toString(merchant.getAuditState(), ""));
			hash.put("audit_stage", ObjectUtils.toString(merchant.getAuditStage(), ""));
			hash.put("contract_endtime", String.valueOf(null == merchant.getContractEndtime()?"":merchant.getContractEndtime().getTime()));
			hash.put("is_enable", BooleanUtils.toString(merchant.getIsEnable(), "1", "0", ""));
			
			hash.put("area_id", ObjectUtils.toString(merchant.getAreaId(), ""));
			
			hash.put("disable_status", ObjectUtils.toString(merchant.getDisableStatus(),""));
			
			hash.put("user_org_code", ObjectUtils.toString(merchant.getUserOrgCode(),""));
			// 补齐redis 商户分类和商户类型信息
			if(merchant.getCategoryInfoObjList() != null && !merchant.getCategoryInfoObjList().isEmpty()){
				hash.put("category_id", StringUtils.join(merchant.getCategoryIdList(), ","));
				hash.put("category_name", StringUtils.join(merchant.getCategoryNameList(), ","));
			}
			if(merchant.getTypeInfoObjList() != null && !merchant.getTypeInfoObjList().isEmpty()){
				hash.put("type_id", StringUtils.join(merchant.getTypeIdList(), ","));
				hash.put("type_name", StringUtils.join(merchant.getTypeNameList(), ","));
			}
			
			// StringUtils.defaultIfBlank(str, "");

//			String result = RedisManager.getJedis().hmset(key, hash);
			set_cbbank_merchant_client_id_merchant_id(merchant.getClientId(), merchant.getMerchantId(), hash);
		}
		return true;
		// return result.equals("OK") ? true : false;

	}
	
	public static boolean set_cbbank_merchant_client_id_merchant_id(String client_id, String merchant_id, Map<String, String> hash) throws Exception{
		String key = RedisKeyUtil.cbbank_merchant_client_id_merchant_id(client_id, merchant_id);
		Boolean result = "OK".equals(redisManager.putMap(key, hash));
		LogCvt.info("设置缓存:" + key + "成功!");
		return result;
	}
	public static boolean set_cbbank_merchant_client_id_merchant_id(String client_id, String merchant_id, String field, String value) {
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
	public static boolean del_cbbank_merchant_client_id_merchant_id(String client_id, String merchant_id, String... fields) {
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
	public static boolean del_cbbank_merchant_client_id_merchant_id(String client_id, String merchant_id) {
		String key = RedisKeyUtil.cbbank_merchant_client_id_merchant_id(client_id, merchant_id);
		long result = redisManager.hset(key, "is_enable", BooleanUtils.toString(false, "1", "0")) ;
//		set_cbbank_merchant_client_id_merchant_id(client_id, merchant_id, "is_enable", value);
		LogCvt.info("删除缓存:" + key + "成功!");
		return result > -1;
	}
	
	public static boolean remove_cbbank_merchant_client_id_merchant_id(String client_id, String merchant_id) {
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
	public static Set<String> get_cbbank_area_merchant_client_id_area_id(String client_id, Long area_id) {
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
	public static boolean set_cbbank_bank_merchant_client_id_org_code (Merchant... merchants){
		for (Merchant merchant : merchants) {
			Map<String, String> hash = new HashMap<String, String>();
			hash.put("merchant_id", ObjectUtils.toString(merchant.getMerchantId(), ""));
			hash.put("merchant_name", ObjectUtils.toString(merchant.getMerchantName(), ""));
			hash.put("logo", ObjectUtils.toString(merchant.getLogo(), ""));
			hash.put("phone", ObjectUtils.toString(merchant.getPhone(), ""));
			hash.put("area_id", ObjectUtils.toString(merchant.getAreaId(), ""));
//			hash.put("merchant_status", BooleanUtils.toString(merchant.getMerchantStatus(), "1", "0", ""));
//			hash.put("org_code", ObjectUtils.toString(merchant.getOrgCode(), ""));
			hash.put("user_org_code", ObjectUtils.toString(merchant.getUserOrgCode(),""));
			hash.put("is_enable", BooleanUtils.toString(merchant.getIsEnable(), "1", "0", ""));
//			hash.put("disable_status", ObjectUtils.toString(merchant.getDisableStatus(),""));
			
			set_cbbank_bank_merchant_client_id_org_code(merchant.getClientId(), merchant.getOrgCode(), hash);
		}
		return true;
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
	public static boolean set_cbbank_bank_merchant_client_id_org_code (String clientId,String orgCode,Map<String, String> hash){
		String key = RedisKeyUtil.cbbank_bank_merchant_client_id_org_code(clientId, orgCode);
		
		Boolean result = "OK".equals(redisManager.putMap(key, hash));
		LogCvt.info("设置缓存:" + key + "成功!");
		return result;
	}
	
	public static boolean del_cbbank_bank_merchant_client_id_org_code (String clientId,String orgCode){
		String key = RedisKeyUtil.cbbank_bank_merchant_client_id_org_code(clientId, orgCode);
		long result = redisManager.hset(key, "is_enable", BooleanUtils.toString(false, "1", "0")) ;
		LogCvt.info("删除缓存:" + key + "成功!");
		return result > -1;
	}
	
	public static boolean remove_cbbank_bank_merchant_client_id_org_code (String clientId,String orgCode){
		String key = RedisKeyUtil.cbbank_bank_merchant_client_id_org_code(clientId, orgCode);
		long result = redisManager.del(key) ;
		LogCvt.info("删除缓存:" + key + "成功!");
		return result > -1;
	}
	
	public static Map<String, String> get_cbbank_bank_merchant_client_id_org_code(String clientId,String orgCode){
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
	public static boolean set_cbbank_area_merchant_client_id_area_id(Merchant... merchants) throws Exception{
		for (Merchant merchant : merchants) {
			set_cbbank_area_merchant_client_id_area_id(merchant.getClientId(), merchant.getAreaId(),merchant.getMerchantId());
		}
		return true;
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
	public static boolean set_cbbank_area_merchant_client_id_area_id(String client_id, Long area_id, String merchant_id) throws Exception {
		String key = RedisKeyUtil.cbbank_area_merchant_client_id_area_id( client_id, area_id);
		Long result = redisManager.put(key, ObjectUtils.toString(merchant_id, ""));
		LogCvt.info("设置缓存:" + key + "成功!");
		return result > -1;
	}
	
	public static boolean del_cbbank_area_merchant_client_id_area_id(String client_id, Long area_id, String merchant_id) {
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
	public static Set<String> get_cbbank_top_merchant_client_id(String client_id) {
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
	public static boolean set_cbbank_top_merchant_client_id(Merchant merchant) {
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
	public static boolean set_cbbank_top_merchant_client_id(String client_id, String merchant_id) {
		String key = RedisKeyUtil.cbbank_top_merchant_client_id(client_id);
		Long result = redisManager.put(key, ObjectUtils.toString(merchant_id, ""));
		LogCvt.info("设置缓存:" + key + "成功!");
		return result > -1;
	}
	
	public static boolean del_cbbank_top_merchant_client_id(String client_id, String merchant_id) {
		String key = RedisKeyUtil.cbbank_top_merchant_client_id(client_id);
//		Long result = RedisManager.getJedis().srem(key, ObjectUtils.toString(merchant_id));
		Long result = redisManager.del(key);
		LogCvt.info("删除缓存:" + key + "成功!");
		return result > -1;
	}
	
	
	/**
	 * 更新商户缓存信息
	 * @Title: auditMerchant 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年4月27日
	 * @modify: froad-huangyihao 2015年4月27日
	 * @param audit
	 * @return
	 * @throws
	 */
	public static Boolean updateAuditMerchant(Merchant merchant){
		String key = RedisKeyUtil.cbbank_merchant_client_id_merchant_id(merchant.getClientId(), merchant.getMerchantId());
		Map<String, String> valueMap = new HashMap<String, String>();
		if(Checker.isNotEmpty(merchant.getAuditState())){
			valueMap.put("audit_state", merchant.getAuditState());
		}
		if(Checker.isNotEmpty(merchant.getIsEnable())){
			valueMap.put("is_enable", BooleanUtils.toString(merchant.getIsEnable(), "1", "0", ""));
		}	
		if(Checker.isNotEmpty(merchant.getAuditOrgCode())){
			valueMap.put("audit_org_code", merchant.getAuditOrgCode());
		}
		if(Checker.isNotEmpty(merchant.getAuditStage())){
			valueMap.put("audit_stage", merchant.getAuditStage());
		}
		
		String result = redisManager.putMap(key, valueMap);
		return "OK".equals(result);
	}
}
