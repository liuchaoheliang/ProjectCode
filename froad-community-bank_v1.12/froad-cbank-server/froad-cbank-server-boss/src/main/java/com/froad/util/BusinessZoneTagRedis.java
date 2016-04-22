/**
 * Project Name:froad-cbank-server-common
 * File Name:BusinessZoneTagRedis.java
 * Package Name:com.froad.db.redis
 * Date:2015年10月22日下午6:30:35
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.util;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.po.BusinessZoneTag;
import com.froad.po.FunctionModule;
import com.froad.util.RedisKeyUtil;

/**
 * ClassName:BusinessZoneTagRedis
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月22日 下午6:30:35
 * @author   kevin
 * @version  
 * @see 	 
 */
public class BusinessZoneTagRedis {
	private static RedisManager redisManager = new RedisManager();

	/**
	 * 获取单个fields值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:client_channel:client_id:payment_channel_id
	 * field包含name/full_name/type/ico_url/payment_org_no/is_froad_check_token/point_rate
	 * @author ll 20150320
	 * @param client_id 客户端id
	 * @param type 功能模块类型
	 * @param fields 需要取的field值
	 * @return String
	 * @throws
	 */
	public static String getString(String client_id,String id, String fields) {
		String key = RedisKeyUtil.cbbank_business_zone_tag_client_id_id(client_id,id) ;
		return redisManager.getMapValue(key, fields);
	}
	
	
	/**
	 * 获取所有fields值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:client_channel:client_id:payment_channel_id
	 * field包含name/full_name/type/ico_url/payment_org_no/is_froad_check_token/point_rate
	 * @author ll 20150320
	 * @param client_id 客户端id
	 * @param payment_channel_id 支付渠道id
	 * @return String
	 * @throws
	 */
	public static Map<String, String> getAll_cbbank_business_zone_tag_client_id_id(String client_id,String id){
		String key = RedisKeyUtil.cbbank_business_zone_tag_client_id_id(client_id,id) ;
		return redisManager.getMap(key);
	}
	
	
	/**
	 * 设置所有fields的value值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:client_channel:client_id:payment_channel_id
	 * field包含name/full_name/type/ico_url/payment_org_no/is_froad_check_token/point_rate
	 * @author ll 20150320
	 * @param ClientPaymentChannel支付渠道对象 
	 * @return boolean 是否操作成功
	 * @throws
	 */
	public static Boolean set_cbbank_business_zone_tag_client_id_id(List<BusinessZoneTag> tagList) {
		for (BusinessZoneTag fm : tagList) {
			/* 缓存该客户端下支付渠道信息 */
			String key = RedisKeyUtil.cbbank_business_zone_tag_client_id_id(fm.getClientId(), fm.getId().toString()) ;
			Map<String, String> hash = new HashMap<String, String>();
			hash.put("id", fm.getId().toString());
			hash.put("client_id", fm.getClientId());
			hash.put("zone_name", fm.getZoneName());
			if(fm.getSerialNumber() != null) {
				hash.put("serial_number", fm.getSerialNumber().toString() );
			}
			if(fm.getFareaId() != null) {
				hash.put("f_area_id",  fm.getFareaId().toString() );
			}
			if( fm.getSareaId() != null) {
				hash.put("s_area_id", fm.getSareaId().toString() );
			}
			if( fm.getTareaId() != null) {
				hash.put("t_area_id",fm.getTareaId().toString() );
			}
			if(fm.getOareaId() != null) {
				hash.put("o_area_id", fm.getOareaId().toString());
			}
			if(StringUtils.isNotEmpty(fm.getDesc())) {
				hash.put("desc", fm.getDesc());
			}
			hash.put("status", fm.getStatus());
			hash.put("create_time", fm.getCreateTime().getTime() + "");//积分兑换比例(实时获取)
			redisManager.putMap(key, hash);
		}
		return true;
	}
	
	
	
	public static Boolean set_cbbank_business_zone_tag_client_id_id(BusinessZoneTag fm) {
		/* 缓存该客户端下支付渠道信息 */
		String key = RedisKeyUtil.cbbank_business_zone_tag_client_id_id(fm.getClientId(), fm.getId().toString()) ;
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("id", fm.getId().toString());
		hash.put("client_id", fm.getClientId());
		hash.put("zone_name", fm.getZoneName());
		if(fm.getSerialNumber() != null) {
			hash.put("serial_number", fm.getSerialNumber().toString() );
		}
		if(fm.getFareaId() != null) {
			hash.put("f_area_id",  fm.getFareaId().toString() );
		}
		if( fm.getSareaId() != null) {
			hash.put("s_area_id", fm.getSareaId().toString() );
		}
		if( fm.getTareaId() != null) {
			hash.put("t_area_id",fm.getTareaId().toString() );
		}
		if(fm.getOareaId() != null) {
			hash.put("o_area_id", fm.getOareaId().toString());
		}
		if(StringUtils.isNotEmpty(fm.getDesc())) {
			hash.put("desc", fm.getDesc());
		}
		
		hash.put("status", fm.getStatus());
		hash.put("create_time", fm.getCreateTime().getTime() + "");//积分兑换比例(实时获取)
		redisManager.putMap(key, hash);
		return true;
	}
	
	
	
	
	
	/**
	 * 获取Set集合中的payment_channel_id
	 * 
	 * @Description:使用Redis的集合结构存储SADD key value
	 * key为cbbank:client_channels:client_id
	 * value为payment_channel_id
	 * @author ll 20150323
	 * @param client_id 客户端id
	 * @return Set<String> 返回类型
	 * @throws
	 */
	public static Set<String> get_cbbank_business_zone_tag_client_id(String client_id) {
		String key = RedisKeyUtil.cbbank_business_zone_tag_client_id(client_id) ;
		return redisManager.getSet(key);
	}
	
	
	
	/**
	 * 设置Set集合中的payment_channel_id
	 * 
	 * @Description:使用Redis的集合结构存储SADD key value
	 * key为cbbank:client_channels:client_id
	 * value为payment_channel_id
	 * @author ll 20150323
	 * @param clientPaymentChannels支付渠道对象
	 * @return boolean是否操作成功
	 * @throws
	 */
	public static Boolean set_cbbank_business_zone_tag_client_id(List<BusinessZoneTag> tagList) {
		for (BusinessZoneTag fm : tagList) {
			/* 缓存该客户端下支付渠道Set集合信息 */
			String key = RedisKeyUtil.cbbank_business_zone_tag_client_id(fm.getClientId()) ;
			
			Set<String> valueSet = new HashSet<String>();
			valueSet.add(fm.getId().toString());
			redisManager.putSet(key, valueSet);
		}
		return true;

	}
	
	public static Boolean set_cbbank_business_zone_tag_client_id(String clientId, String id) {
		String key = RedisKeyUtil.cbbank_business_zone_tag_client_id(clientId) ;
		Set<String> valueSet = new HashSet<String>();
		valueSet.add(id);
		redisManager.putSet(key, valueSet);
		return true;
	}
	
	
	
	
	/**
	 * 删除key缓存<key:cbbank:client_channel:client_id:payment_channel_id>
	 * @param merchantAccounts
	 * @return
	 */
	public static boolean del_cbbank_business_zone_tag_client_id(List<BusinessZoneTag> tagList) {
		for (BusinessZoneTag fm : tagList) {
			String key = RedisKeyUtil.cbbank_business_zone_tag_client_id_id(fm.getClientId(), fm.getId().toString()) ;
			redisManager.del(key);
		}
		return true;
	}
	
	public static boolean del_cbbank_business_zone_tag_client_id(BusinessZoneTag tag) {
		String key = RedisKeyUtil.cbbank_business_zone_tag_client_id_id(tag.getClientId(), tag.getId().toString()) ;
		redisManager.del(key);
		return true;
	}
	
	
	/**
	 * 删除key缓存<key:cbbank:client_channel:client_id> Set集合
	 * @param merchantAccounts
	 * @return
	 */
	public static boolean srem_cbbank_business_zone_tag_client_id_id(List<BusinessZoneTag> tagList) {
		
		Long result=0l;
		for (BusinessZoneTag fm : tagList) {
			String key = RedisKeyUtil.cbbank_business_zone_tag_client_id(fm.getClientId()) ;
			//移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略
			result=redisManager.srem(key, fm.getId().toString());
		}
		return result==0l?true:false;
	}
	
	public static boolean srem_cbbank_business_zone_tag_client_id_id(BusinessZoneTag tag) {
		
		Long result=0l;
		String key = RedisKeyUtil.cbbank_business_zone_tag_client_id(tag.getClientId()) ;
		//移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略
		result=redisManager.srem(key, tag.getId().toString());
		return result==0l?true:false;
	}
}
