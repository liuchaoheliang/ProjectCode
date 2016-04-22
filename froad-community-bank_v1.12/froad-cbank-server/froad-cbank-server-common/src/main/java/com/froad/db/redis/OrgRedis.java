package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.OrgLevelEnum;
import com.froad.logback.LogCvt;
import com.froad.po.Org;
import com.froad.util.RedisKeyUtil;

/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: OrgRedis 
 * @Description: 公共管理Redis
 * @Author: ll
 * @Date: 2015年5月6日 上午10:00:49
 */
public class OrgRedis {
	private static RedisManager redisManager = new RedisManager();
	
	
	/**
	 * 获取商户Id下对应的1-2-3-4级orgCode
	 * <key结构cbbank:merchant_org_level:merchant_id>
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank_org_merchant_id
	 * field包含province_agency/city_agency/county_agency/org_code 
	 * @author ll 20150506
	 * @param merchant_id商户id
	 * @return Map<String,String>
	 * @throws
	 */
	public static Map<String,String> getAll_cbbank_merchant_org_level_merchant_id(String merchant_id) {
		String key = RedisKeyUtil.cbbank_merchant_org_level_merchant_id(merchant_id);
		return redisManager.getMap(key);
	}
	
	/**
	 * 
	 * 设置商户Id下对应的1-2-3-4级orgCode
	 * <key结构cbbank:merchant_org_level:merchant_id>
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:client:client_id
	 * field包含 province_agency/city_agency/county_agency/org_code 
	 * @author ll 20150506
	 * @param Client客户端对象
	 * @return boolean是否设置成功
	 * @throws
	 */
	public static Boolean set_cbbank_merchant_org_level_merchant_id(String merchantId,Map<OrgLevelEnum, String> map) {
		/* 缓存该商户下对应的1-2-3-4级机构关系 */
		String key = RedisKeyUtil.cbbank_merchant_org_level_merchant_id(merchantId);

		Map<String, String> hash = new HashMap<String, String>();
		hash.put("province_agency", map.get(OrgLevelEnum.orgLevel_one));//一级orgCode
		hash.put("city_agency", map.get(OrgLevelEnum.orgLevel_two));//二级orgCode
		hash.put("county_agency", map.get(OrgLevelEnum.orgLevel_three));//三级orgCode
		hash.put("org_code", map.get(OrgLevelEnum.orgLevel_four));//四级orgCode

		redisManager.putMap(key, hash);
		return true;
	}
	
	
	/**
	 * 根据client_id+org_code查询机构信息<key结构cbbank:org:client_id_org_code>
	 * 
	 * @param client_id 客户端id
	 * @param org_code 机构编号
	 * @return
	 */
	public static Map<String,String> getAll_cbbank_org_client_id_org_code(String client_id,String org_code) {
		String key = RedisKeyUtil.cbbank_org_client_id_org_code(client_id,org_code) ;
		return redisManager.getMap(key);
	}
	
	/**
	 * 根据client_id+outlet_id查询机构信息<key结构cbbank:outlet_org:client_id_outlet_id>
	 * 
	 * @param client_id 客户端id
	 * @param outlet_id 门店Id
	 * @return
	 */
	public static Map<String,String> getAll_cbbank_outlet_org_client_id_outlet_id(String client_id,String outlet_id) {
		String key = RedisKeyUtil.cbbank_outlet_org_client_id_outlet_id(client_id,outlet_id) ;
		return redisManager.getMap(key);
	}
	
	/**
	 * 根据client_id+merchant_id查询机构信息<key结构cbbank:merchant_org:client_id_merchant_id>
	 * 
	 * @param client_id 客户端id
	 * @param org_code 机构编号
	 * @return
	 */
	public static Map<String,String> getAll_cbbank_merchant_org_client_id_merchant_id(String client_id,String merchant_id) {
		String key = RedisKeyUtil.cbbank_merchant_org_client_id_merchant_id(client_id,merchant_id) ;
		return redisManager.getMap(key);
	}
	
	
	/**
	 * 设置机构Redis信息
	 * <key结构cbbank:org:client_id_org_code>
	 * <key结构cbbank:outlet_org:client_id_outlet_id>
	 * <key结构cbbank:merchant_org:client_id_merchant_id>
	 * @param org 机构对象
	 * @return
	 */
	public static Boolean set_cbbank_org(String key,Org org) {
		boolean result = false;
		try{
			
			Map<String, String> hash = new HashMap<String, String>();
			hash.put("client_id", org.getClientId());	//客户端ID
			hash.put("bank_type", org.getBankType());	//银行类型
			hash.put("org_code", org.getOrgCode());		//机构代码
			hash.put("org_name", org.getOrgName());		//机构名
			
			hash.put("province_agency", ObjectUtils.toString(org.getProvinceAgency(),""));	//一级orgCode
			hash.put("city_agency", ObjectUtils.toString(org.getCityAgency(),""));			//二级orgCode
			hash.put("county_agency", ObjectUtils.toString(org.getCountyAgency(),""));		//三级orgCode
			hash.put("phone", ObjectUtils.toString(org.getPhone(),""));						//联系电话
			hash.put("merchant_id", ObjectUtils.toString(org.getMerchantId(),""));									//机构对应商户ID
			hash.put("outlet_id", ObjectUtils.toString(org.getOutletId(),""));										//机构对应门店ID
			hash.put("area_id", ObjectUtils.toString(org.getAreaId(),""));					//机构地区
			hash.put("org_level", org.getOrgLevel());										//机构级别1-2-3-4-
			
			//redis中对bool类型存0和1
			hash.put("need_review", BooleanUtils.toString(org.getNeedReview(), "1", "0", ""));//是否需要双⼈人审核
			hash.put("org_type", BooleanUtils.toString(org.getOrgType(), "1", "0", ""));	  //0-部⻔门机构，1-业务机构
			hash.put("is_enable", BooleanUtils.toString(org.getIsEnable(), "1", "0", ""));	  //是否有效
			
	
			redisManager.putMap(key, hash);
			result=true;
		}catch(Exception e){
			LogCvt.error(e.getMessage(),e);
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
