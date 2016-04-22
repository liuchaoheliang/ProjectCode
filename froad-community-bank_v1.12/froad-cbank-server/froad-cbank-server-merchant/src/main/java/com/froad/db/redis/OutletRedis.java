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

package com.froad.db.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.po.Outlet;
import com.froad.po.mongo.OutletDetailSimpleInfo;
import com.froad.util.Checker;
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
	public static Set<String> get_cbbank_merchant_outlet_client_id_merchant_id(String clientId, String merchantId) {
		String key = RedisKeyUtil.cbbank_merchant_outlet_client_id_merchant_id(clientId, merchantId);
		return redisManager.getSet(key);
	}
	
	public static boolean set_cbbank_merchant_outlet_client_id_merchant_id(Outlet... outlets) {
		for (Outlet outlet : outlets) {
			String key = RedisKeyUtil.cbbank_merchant_outlet_client_id_merchant_id(outlet.getClientId(), outlet.getMerchantId());
			redisManager.zadd(key, outlet.getOrderValue(), outlet.getOutletId());
//			Map<String, Double> scoreMembers = new HashMap<String, Double>();
//			scoreMembers.put(outlet.getOutletId(), (double) outlet.getOrderValue());
//			RedisManager.getJedis().zadd(key, scoreMembers);
			LogCvt.info("设置缓存:" + key + "成功!");
		}
		return true;
	}
	public static boolean del_cbbank_merchant_outlet_client_id_merchant_id(Outlet... outlets) {
//		String[] keys = new String[outlets.length];
//		for (int i = 0; i < outlets.length; i++) {
//			Outlet outlet = outlets[0];
//			keys[i] = RedisKeyUtil.cbbank_merchant_outlet_client_id_merchant_id(outlet.getClientId(), outlet.getMerchantId());
//		}
		int i = 0;
		for (; i < outlets.length; i++) {
			Outlet outlet = outlets[0];
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
	public static boolean set_cbbank_outlet_client_id_merchant_id_outlet_id(Outlet... outlets) {
		for (Outlet outlet : outlets) {
//			String key = RedisKeyUtil.cbbank_outlet_client_id_merchant_id_outlet_id(outlet.getClientId(), outlet.getMerchantId(), outlet.getOutletId());
			Map<String, String> hash = new HashMap<String, String>();
			hash.put("outlet_name", ObjectUtils.toString(outlet.getOutletName(), ""));
			hash.put("outlet_status", BooleanUtils.toString(outlet.getOutletStatus(), "1", "0", ""));
			hash.put("longitude", ObjectUtils.toString(outlet.getLongitude(), ""));
			hash.put("latitude", ObjectUtils.toString(outlet.getLatitude(), ""));
			hash.put("is_enable", BooleanUtils.toString(outlet.getIsEnable(), "1", "0", ""));
			hash.put("disable_status", ObjectUtils.toString(outlet.getDisableStatus(), ""));
//			hash.put("is_default", BooleanUtils.toString(outlet.getIsDefault(), "1", "0", ""));
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
	
	public static boolean del_cbbank_outlet_client_id_merchant_id_outlet_id(Outlet... outlets) {
		for (Outlet outlet : outlets) {
			String key = RedisKeyUtil.cbbank_outlet_client_id_merchant_id_outlet_id(outlet.getClientId(), outlet.getMerchantId(), outlet.getOutletId());
			redisManager.hset(key, "is_enable", BooleanUtils.toString(false, "1", "0")) ;
			LogCvt.info("删除缓存:" + key + "成功!");
		}
		return true;
	}
	
	public static boolean remove_cbbank_outlet_client_id_merchant_id_outlet_id(Outlet... outlets) {
		for (Outlet outlet : outlets) {
			String key = RedisKeyUtil.cbbank_outlet_client_id_merchant_id_outlet_id(outlet.getClientId(), outlet.getMerchantId(), outlet.getOutletId());
			redisManager.del(key) ;
			LogCvt.info("物理删除缓存:" + key + "成功!");
		}
		return true;
	}
	
	public static Map<String, String> get_cbbank_outlet_client_id_merchant_id_outlet_id(String clientId, String merchantId, String outletId) {
		String key = RedisKeyUtil.cbbank_outlet_client_id_merchant_id_outlet_id(clientId, merchantId, outletId);
		return redisManager.getMap(key);
	}
	
	/**
	 * 银行机构商户门店缓存
	 * @Title: set_cbbank_bank_merchant_outlet_client_id_org_code 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchant
	 * @param outlet
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public static boolean set_cbbank_bank_outlet_client_id_org_code (String orgCode, Outlet outlet){
		if(outlet == null || StringUtils.isBlank(orgCode)) return false;
		Map<String, String> hash = new HashMap<String, String>();
		
		hash.put("merchant_id", ObjectUtils.toString(outlet.getMerchantId(), ""));
		hash.put("outlet_id", ObjectUtils.toString(outlet.getOutletId(), ""));
		hash.put("outlet_name", ObjectUtils.toString(outlet.getOutletName(), ""));
		
		hash.put("is_enable", BooleanUtils.toString(outlet.getIsEnable(), "1", "0", ""));
		hash.put("disable_status", ObjectUtils.toString(outlet.getDisableStatus(),""));
		
		String key = RedisKeyUtil.cbbank_bank_outlet_client_id_org_code(outlet.getClientId(), orgCode);
		Boolean result = "OK".equals(redisManager.putMap(key, hash));
		LogCvt.info("设置缓存:" + key + "成功!");
		return result;
	}
	
	public static boolean del_cbbank_bank_outlet_client_id_org_code(String clientId, String orgCode) {
		String key = RedisKeyUtil.cbbank_bank_outlet_client_id_org_code(clientId, orgCode);
		long result = redisManager.hset(key, "is_enable", BooleanUtils.toString(false, "1", "0"));
		LogCvt.info("删除缓存:" + key + "成功!");
		return result > -1;
	}

	public static boolean remove_cbbank_bank_outlet_client_id_org_code(String clientId, String orgCode) {
		String key = RedisKeyUtil.cbbank_bank_outlet_client_id_org_code(clientId, orgCode);
		long result = redisManager.del(key);
		LogCvt.info("物理删除缓存:" + key + "成功!");
		return result > -1;
	}

	public static Map<String ,String> get_cbbank_bank_outlet_client_id_org_code(String clientId,String orgCode) {
		String key = RedisKeyUtil.cbbank_bank_outlet_client_id_org_code(clientId, orgCode);
		return redisManager.getMap(key);
	}
	
	public static boolean set_cbbank_near_outlet_condition(int index, String condition, List<OutletDetailSimpleInfo> outletDetailSimpleInfos) throws Exception {
		String key = RedisKeyUtil.cbbank_near_outlet_index_condition(index, condition);
		return set_cbbank_near_outlet_condition(key, outletDetailSimpleInfos);
	}
	
	public static boolean set_cbbank_near_outlet_condition(String key, List<OutletDetailSimpleInfo> outletDetailSimpleInfos) throws Exception {
		String[] serStrArr = new String[outletDetailSimpleInfos.size()] ;
		for (int i = 0; i < outletDetailSimpleInfos.size(); i++) {
//			serStrArr[i] = ObjectSerializeUtil.serializeObject(outletDetailSimpleInfos.get(i));
//			serStrArr[i] = JSonUtil.toJSonString(outletDetailSimpleInfos.get(i));
			serStrArr[i] = JSON.toJSONString(outletDetailSimpleInfos.get(i));
		}
		Long result = redisManager.rpush(key, serStrArr);
		if (result > 0) {
			int cacheTimeout = 5 * 60;// 设置超时时间 为 5分钟
			LogCvt.info("设置个人H5附近搜索门缓存[" + key + "]成功,缓存数据条数[" + serStrArr.length + "]条!");
			redisManager.expire(key, cacheTimeout); // 设置超时时间
			LogCvt.info("设置个人H5附近搜索门缓存[" + key + "]超时时间为" + cacheTimeout + "秒,成功!");
		}
		return result > 0;
	}
	
	public static List<OutletDetailSimpleInfo> get_cbbank_near_outlet_condition(int index, String condition,long start,long end) throws Exception {
		String key = RedisKeyUtil.cbbank_near_outlet_index_condition(index, condition);
		return get_cbbank_near_outlet_condition(key,start, end);
	}
	
	public static List<OutletDetailSimpleInfo> get_cbbank_near_outlet_condition(String key,long start,long end) throws Exception {
		List<OutletDetailSimpleInfo> result = null;
		List<String> list = redisManager.lrange(key, start, end);
		LogCvt.info("Redis缓存使用lrange分页查询key=[" + key + "]的数据, start=[" + start + "], end=[" + end + "], 共返回["+list.size()+"]条数据!");
		if (CollectionUtils.isNotEmpty(list)) {
			result = new ArrayList<OutletDetailSimpleInfo>();
			for (String serStr : list) {
//				result.add(ObjectSerializeUtil.deserializeObject(serStr, OutletDetailSimpleInfo.class));
				result.add(JSON.parseObject(serStr, OutletDetailSimpleInfo.class));
			}
		}
		return result;
	}
	
	public static long count_cbbank_near_outlet_condition(int index, String condition) throws Exception {
		String key = RedisKeyUtil.cbbank_near_outlet_index_condition(index, condition);
		return getListSize(key);
	}
	
	public static boolean set_cbbank_hottest_outlet_condition(int index, String condition, List<OutletDetailSimpleInfo> outletDetailSimpleInfos) throws Exception {
		String key = RedisKeyUtil.cbbank_hottest_outlet_condition(index, condition);
		return set_cbbank_hottest_outlet_condition(key, outletDetailSimpleInfos);
	}
	
	public static boolean set_cbbank_hottest_outlet_condition(String key, List<OutletDetailSimpleInfo> outletDetailSimpleInfos) throws Exception {
		String[] serStrArr = new String[outletDetailSimpleInfos.size()] ;
		for (int i = 0; i < outletDetailSimpleInfos.size(); i++) {
//			serStrArr[i] = ObjectSerializeUtil.serializeObject(outletDetailSimpleInfos.get(i));
//			serStrArr[i] = JSonUtil.toJSonString(outletDetailSimpleInfos.get(i));
			serStrArr[i] = JSON.toJSONString(outletDetailSimpleInfos.get(i));
		}
		Long result = redisManager.rpush(key, serStrArr);
		if (result > 0) {
			int cacheTimeout = 5 * 60;// 设置超时时间 为 5分钟
			LogCvt.info("设置个人H5搜索最热门店缓存[" + key + "]成功,缓存数据条数[" + serStrArr.length + "]条!");
			redisManager.expire(key, cacheTimeout); // 设置超时时间
			LogCvt.info("设置个人H5搜索最热门店缓存[" + key + "]超时时间为" + cacheTimeout + "秒,成功!");
		}
		return result > 0;
	}
	
	public static List<OutletDetailSimpleInfo> get_cbbank_hottest_outlet_condition(int index, String condition, long start, long end) throws Exception {
		String key = RedisKeyUtil.cbbank_hottest_outlet_condition(index, condition);
		return get_cbbank_hottest_outlet_condition(key, start, end);
	}
	
	public static List<OutletDetailSimpleInfo> get_cbbank_hottest_outlet_condition(String key,long start,long end) throws Exception {
		List<OutletDetailSimpleInfo> result = null;
		LogCvt.info("Redis缓存使用lrange分页查询key=[" + key + "]的数据, start=[" + start + "], end=[" + end + "]");
		List<String> list = redisManager.lrange(key, start, end);
		if (CollectionUtils.isNotEmpty(list)) {
			result = new ArrayList<OutletDetailSimpleInfo>();
			for (String serStr : list) {
//				result.add(ObjectSerializeUtil.deserializeObject(serStr, OutletDetailSimpleInfo.class));
				result.add(JSON.parseObject(serStr, OutletDetailSimpleInfo.class));
			}
		}
		return result;
	}
	
	public static long count_cbbank_hottest_outlet_condition(int index, String condition) throws Exception {
		String key = RedisKeyUtil.cbbank_hottest_outlet_condition(index, condition);
		return getListSize(key);
	}
	
	public static long getListSize(String key) throws Exception {
		return redisManager.llen(key);
	}

	public static long setLock(String key) throws Exception {
		long result = redisManager.setnx(key, "1");
		if (result > 0) {
			int cacheTimeout = 5 * 60;// 设置超时时间 为 5分钟
			redisManager.expire(key, cacheTimeout); // 设置超时时间
			LogCvt.info("设置锁[" + key + "]超时时间为" + cacheTimeout + "秒,成功!");
		}
		return result;
	}
	
	public static boolean exists(String key){
		return redisManager.exists(key);
	}
	public static Boolean updateOutletRedis(Outlet outlet){
		String key =RedisKeyUtil.cbbank_outlet_client_id_merchant_id_outlet_id(outlet.getClientId(),outlet.getMerchantId(),outlet.getOutletId());
		Map<String, String> valueMap = new HashMap<String, String>();
		if(Checker.isNotEmpty(outlet.getAuditState())){
			valueMap.put("audit_state", outlet.getAuditState());
		}
		if(Checker.isNotEmpty(outlet.getIsEnable())){
			valueMap.put("is_enable", BooleanUtils.toString(outlet.getIsEnable(), "1", "0", ""));
		}
		if(Checker.isNotEmpty(outlet.getEditAuditState())){
			valueMap.put("edit_audit_state", BooleanUtils.toString(outlet.getIsEnable(), "1", "0", ""));
		}
		String result = redisManager.putMap(key, valueMap);
		return "OK".equals(result);
	}
}
