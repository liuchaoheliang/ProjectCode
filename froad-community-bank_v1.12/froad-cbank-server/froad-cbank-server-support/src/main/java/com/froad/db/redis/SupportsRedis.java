/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

package com.froad.db.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import redis.clients.jedis.Jedis;

import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.po.Activities;
import com.froad.po.AdLocation;
import com.froad.po.AdPosition;
import com.froad.po.Advertising;
import com.froad.po.DeliveryCorp;
import com.froad.po.SmsContent;
import com.froad.po.TerminalStart;
import com.froad.util.RedisKeyUtil;


/**    
 * <p>Title: MerchantRedis.java</p>    
 * <p>Description: 描述 </p>   
 * @author longyunbo      
 * @version 1.0    
 * @created 2015年3月20日 上午10:11:55   
 */
public class SupportsRedis extends SupportRedis{
	
	private static final RedisManager redis = new RedisManager();
	
	/**
	 * 获取地区缓存
	 * @Title: get_cbbank_area_client_id_area_id
	 * @author longyunbo
	 * @version 1.0
	 * @param client_id
	 * @param area_id
	 * @return
	 * @return List<String> 返回类型
	 * @throws
	 */
	public static List<String> get_cbbank_area_client_id_area_id(Long area_id, String... fields) {
		String key = RedisKeyUtil.cbbank_area_client_id_area_id(area_id);
		List<String>  result=null;
		result=redis.getList(key);
		return result;
	}
		
	public static Boolean set_cbbank_area_client_id_area_id(Long area_id,String field, String value){
		String key = RedisKeyUtil.cbbank_area_client_id_area_id(area_id);
		Long result = (long) 0;
		result=redis.hset(key, field, value);
		return result > -1;
	}
	
	
	/** 
	 * 获取物流公司缓存
	* @Title: get_cbbank_deliver_company_client_id_delivery_id 
	* @Description: 
	* @author longyunbo
	* @param  client_id
	* @param  delivery_corp_id
	* @param  fields
	* @return List<String>
	* @throws 
	*/
	public static List<String> get_cbbank_deliver_company_client_id_delivery_id(String client_id, Long delivery_corp_id, String... fields) {
		String key = RedisKeyUtil.cbbank_deliver_company_client_id_delivery_corp_id(client_id, delivery_corp_id);
		List<String> result = null;
		result=redis.hmget(key, fields);
		return result;
	}
	
	
	
	/**
	 * 查询key为cbbank:area:client_id:area_id里所有的filed内容
	 * @param client_id
	 * @param 
	 * @return 返回客户端内Redis所有缓存信息
	 */
	public static Map<String,String> getAll_cbbank_deliver_company_client_id_delivery_id(String client_id,Long delivery_corp_id) {
		String key = RedisKeyUtil.cbbank_deliver_company_client_id_delivery_corp_id(client_id,delivery_corp_id) ;
		Map<String,String> result =redis.getMap(key);
		return converRedisMap(result);
	}
	
	
	
	/** 
	 * 缓存物流公司 
	* @Title: set_cbbank_deliver_company_client_id_delivery_id 
	* @Description: 
	* @author longyunbo
	* @param  deliverycorps
	* @return Boolean
	* @throws 
	*/
	public static Boolean set_deliver_company_client_id_delivery_corp_id(DeliveryCorp deliverycorp) {
		
		
			/* 缓存全部物流公司 */
			String key = RedisKeyUtil.cbbank_deliver_company_client_id_delivery_corp_id(deliverycorp.getCorpCode(), deliverycorp.getId()) ;

			Map<String, String> hash = new HashMap<String, String>();
			hash.put("name", ObjectUtils.toString(deliverycorp.getName()));
			hash.put("url", ObjectUtils.toString(deliverycorp.getUrl()));
			hash.put("orderValue", ObjectUtils.toString(deliverycorp.getOrderValue()));
			hash.put("is_enable", BooleanUtils.toString(deliverycorp.getIsEnable(), "1", "0", ""));
			hash.put("corp_code", ObjectUtils.toString(deliverycorp.getCorpCode()));
//			String result = RedisManager.getJedis().hmset(key, hash);
			set_deliver_company_client_id_delivery_corp_id(deliverycorp.getCorpCode(),deliverycorp.getId(),hash);
		return true;
	}
	
	public static Boolean set_deliver_company_client_id_delivery_corp_id(String crop_code, Long delivery_id, Map<String, String> hash){
		String key = RedisKeyUtil.cbbank_deliver_company_client_id_delivery_corp_id(crop_code, delivery_id);
		String result =redis.putMap(key, hash);
		return "OK".equals(result);
	}
	
	
	public static Boolean set_deliver_company_client_id_delivery_corp_id(String crop_code, Long delivery_id,String field, String value){
		String key = RedisKeyUtil.cbbank_deliver_company_client_id_delivery_corp_id(crop_code, delivery_id);
		Long result = (long) 0;
		result=redis.hset(key, field, value);
		return result > -1;
	}
	
	
	
	/** 
	 * 获取短信模板缓存
	* @Title: get_cbbank_sms_template_client_id_sms_type 
	* @Description: 
	* @author longyunbo
	* @param  client_id
	* @param  sys_type
	* @param  fields
	* @return List<String>
	* @throws 
	*/
	public static List<String> get_cbbank_sms_template_client_id_sms_type(String client_id, int sys_type,String... fields){
		String key = RedisKeyUtil.cbbank_sms_template_client_id_sms_type(client_id, sys_type);
		List<String> result = redis.hmget(key, fields);
		return result;
		
	}
	

	/**
	 * 查询key为cbbank_sms_template_client_id_sms_type里所有的filed内容
	 * @param client_id
	 * @param 
	 * @return 返回客户端内Redis所有缓存信息
	 */
	public static Map<String,String> getAll_cbbank_sms_template_client_id_sms_type(String client_id,int sys_type) {
		String key = RedisKeyUtil.cbbank_sms_template_client_id_sms_type(client_id,sys_type) ;
		Map<String,String> result =redis.getMap(key);
		return converRedisMap(result);
	}
	
	
	/** 
	 * 缓存短信模板
	* @Title: set_cbbank_sms_template_client_id_sms_type 
	* @Description: 
	* @author longyunbo
	* @param  smscontents
	* @return Boolean
	* @throws 
	*/
	public static Boolean set_cbbank_sms_template_client_id_sms_type(SmsContent smscontent){
			String key = RedisKeyUtil.cbbank_sms_template_client_id_sms_type(smscontent.getClientId(),smscontent.getSmsType());
			Map<String,String> hash = new HashMap<String, String>();
			hash.put("content", ObjectUtils.toString(smscontent.getContent()));
			hash.put("msg_suffix", ObjectUtils.toString(smscontent.getMsgSuffix()));
			hash.put("channel", ObjectUtils.toString(smscontent.getChannel()));
			hash.put("sms_type", ObjectUtils.toString(smscontent.getSmsType()));
			hash.put("valid_time", ObjectUtils.toString(smscontent.getValidTime()));
			hash.put("is_enable", BooleanUtils.toString(smscontent.getIsEnable(), "1", "0", ""));
//			String result = RedisManager.getJedis().hmset(key, hash);
			set_cbbank_sms_template_client_id_sms_type(smscontent.getClientId(),smscontent.getSmsType(),hash);
		return true;
	}
	
	
	public static Boolean set_cbbank_sms_template_client_id_sms_type(String client_id, int template_id, Map<String, String> hash){
		String key = RedisKeyUtil.cbbank_sms_template_client_id_sms_type(client_id, template_id);
		String result =redis.putMap(key, hash);
		return "OK".equals(result);
	}
	
	public static Map<String,String> get_cbbank_sms_template_client_id_sms_type(String client_id, int template_id){
		String key = RedisKeyUtil.cbbank_sms_template_client_id_sms_type(client_id, template_id);
		Map<String,String> result = redis.getMap(key);
		return result;
	}
	
	public static Boolean set_cbbank_sms_template_client_id_sms_type(String client_id, int template_id,String field, String value){
		String key = RedisKeyUtil.cbbank_sms_template_client_id_sms_type(client_id, template_id);
//		String result = RedisManager.getJedis().hmset(key, hash);
		Long result = (long) 0;
		result=redis.hset(key, field, value);
		return result > -1;
	}
	
	
	
	/** 
	 * 获取活动表缓存
	* @Title: get_cbbank_activities_client_id_activities_id 
	* @Description: 
	* @author longyunbo
	* @param  client_id
	* @param  activities_id
	* @param  fields
	* @return List<String>
	* @throws 
	*/
	public static List<String> get_cbbank_activities_client_id_activities_id(String client_id, Long activities_id,String... fields){
		String key = RedisKeyUtil.cbbank_activities_client_id_activities_id(client_id, activities_id);
		List<String> result = redis.hmget(key, fields);
		return result;
		
	}
	
	/**
	 * 查询key为cbbank_activities_client_id_activities_id里所有的filed内容
	 * @param client_id
	 * @param 
	 * @return 返回客户端内Redis所有缓存信息
	 */
	public static Map<String,String> getAll_cbbank_activities_client_id_activities_id(String client_id,Long activities_id) {
		String key = RedisKeyUtil.cbbank_activities_client_id_activities_id(client_id,activities_id) ;
		Map<String,String> result =redis.getMap(key);
		return converRedisMap(result);
	}
	
	
	
	/** 缓存活动表
	* @Title: set_cbbank_activities_client_id_activities_id 
	* @Description: 
	* @author longyunbo
	* @param  activities
	* @return Boolean
	* @throws 
	*/
	public static Boolean set_cbbank_activities_client_id_activities_id(Activities activitie){
			String key = RedisKeyUtil.cbbank_activities_client_id_activities_id(activitie.getClientId(), activitie.getId());
			
			Map<String,String> hash = new HashMap<String, String>();
			hash.put("create_time",Long.toString(activitie.getCreateTime().getTime()));
			hash.put("activities_name", ObjectUtils.toString(activitie.getActivitiesName()));
			hash.put("points", ObjectUtils.toString(activitie.getPoints()));
			hash.put("begin_time",Long.toString(activitie.getBeginTime().getTime()));
			hash.put("end_time",Long.toString(activitie.getEndTime().getTime()));
			hash.put("status", ObjectUtils.toString(activitie.getStatus()));
			hash.put("count", Integer.toString(activitie.getCount()));
			
			set_cbbank_activities_client_id_activities_id(activitie.getClientId(),activitie.getId(),hash);
//			String result = RedisManager.getJedis().hmset(key, hash);
		return true;
	}
	
	public static Boolean set_cbbank_activities_client_id_activities_id(String client_id, Long activities_id, Map<String, String> hash){
		String key = RedisKeyUtil.cbbank_activities_client_id_activities_id(client_id, activities_id);
		String result = redis.putMap(key, hash);
		return "OK".equals(result);
	}
	
	
	public static Boolean set_cbbank_activities_client_id_activities_id(String client_id, Long activities_id,String field, String value){
		String key = RedisKeyUtil.cbbank_activities_client_id_activities_id(client_id, activities_id);
		Long result = (long) 0;
		result=redis.hset(key, field, value);
		return result > -1;
	}
	
	
	
	
	
	/** 获取广告位缓存
	* @Title: get_cbbank_adpos_client_id_ad_position_id 
	* @Description: 
	* @author longyunbo
	* @param  client_id
	* @param  ad_position_id
	* @param  fields
	* @return List<String>
	* @throws 
	*/
	public static List<String> get_cbbank_adpos_client_id_ad_position_id(String client_id, Long ad_position_id,String... fields){
		String key = RedisKeyUtil.cbbank_adpos_client_id_ad_position_id(client_id, ad_position_id);
		List<String> result=redis.hmget(key, fields);
		return result;
	}
	
	
	/**
	 * 查询key为cbbank_adpos_client_id_ad_position_id里所有的filed内容
	 * @param client_id
	 * @param 
	 * @return 返回客户端内Redis所有缓存信息
	 */
	public static Map<String,String> getAll_cbbank_adpos_client_id_ad_position_id(String client_id,Long ad_position_id) {
		String key = RedisKeyUtil.cbbank_adpos_client_id_ad_position_id(client_id,ad_position_id) ;
		Map<String,String> result =redis.getMap(key);
		return converRedisMap(result);
	}
	
	public static Map<String,String> getAll_cbbank_adLocation_ad_location_id(Long ad_location_id) {
		String key = RedisKeyUtil.cbbank_adLocation_ad_location_id(ad_location_id) ;
		Map<String,String> result =redis.getMap(key);
		return converRedisMap(result);
	}
	
	public static Map<String,String> getAll_cbbank_terminal_start_client_id_app_type_terminal_type(String client_id, String app_type, String terminal_type){
		String key = RedisKeyUtil.cbbank_terminal_start_client_id_app_type_terminal_type(client_id, app_type, terminal_type);
		Map<String,String> result = redis.getMap(key);
		return converRedisMap(result);
	}
	
	public static Map<String,String> getAll_cbbank_advertising_client_id_advertising_id(String client_id,Long advertising_id){
		String key = RedisKeyUtil.cbbank_advertising_client_id_advertising_id(client_id, advertising_id);
		Map<String,String> result =redis.getMap(key);
		return converRedisMap(result);
	}
	
	
	/** 
	 * 缓存广告位表
	* @Title: set_cbbank_adpos_client_id_ad_position_id 
	* @Description: 
	* @author longyunbo
	* @param  adpositions
	* @return Boolean
	* @throws 
	*/
	public static Boolean set_cbbank_adpos_client_id_ad_position_id(AdPosition adposition){
			String key = RedisKeyUtil.cbbank_adpos_client_id_ad_position_id(adposition.getClientId(), adposition.getId());
			
			Map<String,String> hash = new HashMap<String, String>();
			hash.put("height", ObjectUtils.toString(adposition.getHeight()));
			hash.put("width", ObjectUtils.toString(adposition.getWidth()));
			hash.put("terminal_type", ObjectUtils.toString(adposition.getTerminalType()));
			hash.put("position_style", ObjectUtils.toString(adposition.getPositionStyle()));
			hash.put("name", ObjectUtils.toString(adposition.getName()));
			hash.put("description", ObjectUtils.toString(adposition.getDescription()));
			hash.put("position_page", ObjectUtils.toString(adposition.getPositionPage()));
			hash.put("size_limit", ObjectUtils.toString(adposition.getSizeLimit()));
			hash.put("position_point", ObjectUtils.toString(adposition.getPositionPoint()));
			hash.put("is_enable", BooleanUtils.toString(adposition.getIsEnable(), "1", "0", ""));
//			String result = RedisManager.getJedis().hmset(key, hash);
			set_cbbank_adpos_client_id_ad_position_id(adposition.getClientId(),adposition.getId(),hash);
		return true;
	}
	public static Boolean set_cbbank_adLocation_ad_location_id(AdLocation adLocation){
		
//		String key = RedisKeyUtil.cbbank_adLocation_client_id_ad_location_id(adLocation.getClientId(), adLocation.getId());
		
		Map<String,String> hash = new HashMap<String, String>();
		hash.put("name", ObjectUtils.toString(adLocation.getName()));
		hash.put("terminal_type", ObjectUtils.toString(adLocation.getTerminalType()));
		hash.put("position_page", ObjectUtils.toString(adLocation.getPositionPage()));
		hash.put("size_limit", ObjectUtils.toString(adLocation.getSizeLimit()));
		hash.put("width", ObjectUtils.toString(adLocation.getWidth()));
		hash.put("height", ObjectUtils.toString(adLocation.getHeight()));
		hash.put("param_one_type", ObjectUtils.toString(adLocation.getParamOneType()));
		hash.put("param_two_type", ObjectUtils.toString(adLocation.getParamTwoType()));
		hash.put("param_three_type", ObjectUtils.toString(adLocation.getParamThreeType()));
		hash.put("description", ObjectUtils.toString(adLocation.getDescription()));
		hash.put("enable_status", ObjectUtils.toString(adLocation.getEnableStatus()));
		set_cbbank_adLocation_ad_location_id(adLocation.getId(),hash);
		return true;
	}
	public static Boolean set_cbbank_advertising_client_id_advertising_id(Advertising advertising){
		Map<String,String> hash = new HashMap<String, String>();
		hash.put("title", ObjectUtils.toString(advertising.getTitle()));
		hash.put("ad_location_id", ObjectUtils.toString(advertising.getAdLocationId()));
		hash.put("type", ObjectUtils.toString(advertising.getType()));
		hash.put("order_sn", ObjectUtils.toString(advertising.getOrderSn()));
		hash.put("begin_time", ObjectUtils.toString(advertising.getBeginTime().getTime()));
		hash.put("end_time", ObjectUtils.toString(advertising.getEndTime().getTime()));
		hash.put("param_one_value", ObjectUtils.toString(advertising.getParamOneValue()));
		hash.put("param_two_value", ObjectUtils.toString(advertising.getParamTwoValue()));
		hash.put("param_three_value", ObjectUtils.toString(advertising.getParamThreeValue()));
		hash.put("content", ObjectUtils.toString(advertising.getContent()));
		hash.put("link", ObjectUtils.toString(advertising.getLink()));
		hash.put("path", ObjectUtils.toString(advertising.getPath()));
		hash.put("is_blank_targe", ObjectUtils.toString(advertising.getIsBlankTarge()));
		hash.put("enable_status", ObjectUtils.toString(advertising.getEnableStatus()));
		hash.put("click_count", ObjectUtils.toString(advertising.getClickCount()));
		hash.put("description", ObjectUtils.toString(advertising.getDescription()));
		hash.put("terminal_type", ObjectUtils.toString(advertising.getTerminalType()));
		hash.put("position_page", ObjectUtils.toString(advertising.getPositionPage()));
		set_cbbank_advertising_client_id_advertising_id(advertising.getClientId(),advertising.getId(),hash);
		return true;
	}
	public static Boolean set_cbbank_terminal_start_client_id_app_type_terminal_type(TerminalStart terminalStart){
		Map<String,String> hash = new HashMap<String, String>();
		hash.put("id", ObjectUtils.toString(terminalStart.getId()));
		hash.put("create_time", ObjectUtils.toString(terminalStart.getCreateTime().getTime()));
		hash.put("image_id", ObjectUtils.toString(terminalStart.getImageId()));
		hash.put("image_path", ObjectUtils.toString(terminalStart.getImagePath()));
		hash.put("begin_time", ObjectUtils.toString(terminalStart.getBeginTime().getTime()));
		hash.put("end_time", ObjectUtils.toString(terminalStart.getEndTime().getTime()));
		hash.put("is_enabled", ObjectUtils.toString(terminalStart.getIsEnabled()?"1":"0"));
		set_cbbank_terminal_start_client_id_app_type_terminal_type(terminalStart.getClientId(), terminalStart.getAppType(), terminalStart.getTerminalType(), hash);
		return true;
	}
	
	public static Boolean set_cbbank_adpos_client_id_ad_position_id(String client_id, Long ad_position_id, Map<String, String> hash){
		String key = RedisKeyUtil.cbbank_adpos_client_id_ad_position_id(client_id, ad_position_id);
		String  result=redis.putMap(key, hash);
		return "OK".equals(result);
	}
	
	public static Boolean set_cbbank_adLocation_ad_location_id(Long ad_location_id, Map<String, String> hash){
		String key = RedisKeyUtil.cbbank_adLocation_ad_location_id(ad_location_id);
		String  result=redis.putMap(key, hash);
		return "OK".equals(result);
	}
	
	public static Boolean set_cbbank_advertising_client_id_advertising_id(String client_id, Long advertising_id, Map<String, String> hash){
		String key = RedisKeyUtil.cbbank_advertising_client_id_advertising_id(client_id, advertising_id);
		String  result=redis.putMap(key, hash);
		return "OK".equals(result);
	}
	
	public static Boolean set_cbbank_terminal_start_client_id_app_type_terminal_type(String client_id, String app_type, String terminal_type, Map<String, String> hash){
		String key = RedisKeyUtil.cbbank_terminal_start_client_id_app_type_terminal_type(client_id, app_type, terminal_type);
		String  result=redis.putMap(key, hash);
		return "OK".equals(result);
	}
	
	public static Boolean set_cbbank_adpos_client_id_ad_position_id(String client_id, Long ad_position_id,String field, String value){
		String key = RedisKeyUtil.cbbank_adpos_client_id_ad_position_id(client_id, ad_position_id);
		Long result = (long) 0;
		result=redis.hset(key, field, value);
		return result > -1;
	}
	
	public static Boolean set_cbbank_adLocation_ad_location_id(Long ad_location_id,String field, String value){
		String key = RedisKeyUtil.cbbank_adLocation_ad_location_id(ad_location_id);
		Long result = (long) 0;
		result=redis.hset(key, field, value);
		return result > -1;
	}
	

	 public static void main(String[] args) {  
	        Jedis jedis = new Jedis("127.0.0.1");  
	          
	        Map<String, String> map = new HashMap<String, String>();  
	        map.put("name", "nomouse");  
	        map.put("password", "123456");  
	        map.put("sex", "male");  
	          
	        jedis.connect();  
	          
	        jedis.hmset("user", map);  
	          
	        List<String> list = jedis.hmget("user","name","password","sex");  
	          
	        System.out.println("name=" + list.get(0));  
	        System.out.println("password=" + list.get(1));  
	        System.out.println("password=" + list.get(1));  
	    }  
//	/** 缓存地区表
//	* @Title: set_cbbank_area_client_id_area_id 
//	* @Description: 
//	* @author longyunbo
//	* @param @param client_id
//	* @param @param area_id
//	* @param @return
//	* @return Boolean
//	* @throws 
//	*/
//	public static Boolean set_cbbank_area_client_id_area_id(String client_id, Long area_id) {
//		String key = RedisKeyUtil.cbbank_area_client_id_area_id( client_id, area_id);
//		Long result = RedisManager.getJedis().sadd(key, ObjectUtils.toString(area_id, ""));
//		return result > -1;
//	}

	 public static String get_cbbank_boss_user_login_user_id(long user_id){
		String key = RedisKeyUtil.cbbank_boos_user_login_user_id(user_id);
		String result = redis.getString(key);
		return result;
	 }
	 
	 public static boolean set_cbbank_boss_user_login_user_id(long user_id, int count){
		String key = RedisKeyUtil.cbbank_boos_user_login_user_id(user_id);
		String result = redis.putString(key, ObjectUtils.toString(count, ""));
		return "OK".equals(result);
	 }
	
	 public static String get_cbbank_boss_user_token_token_value(String keyV){
		String key = RedisKeyUtil.cbbank_boss_user_token_token_value(keyV);
		String result = redis.getString(key);
		return result;
	}
	 
	public static boolean del_cbbank_boss_user_token_token_value(String keyV){
		String key = RedisKeyUtil.cbbank_boss_user_token_token_value(keyV);
		long result = redis.del(key);
		return result > -1;
	}
	
	public static boolean set_cbbank_boss_user_token_token_value(String keyV, String value){
		String key = RedisKeyUtil.cbbank_boss_user_token_token_value(keyV);
		String result = redis.putExpire(key,  ObjectUtils.toString(value, ""),1800);
		return "OK".equals(result);
	}
    public static long setLock(String key,int time) throws Exception {
        long result = redis.setnx(key, "1");
        if (result > 0) {
        	redis.expire(key, time * 60); // 设置超时时间
            LogCvt.debug("设置锁[" + key + "]超时时间为" + time * 60 + "秒,成功!");
        }
        return result;
    }
}
