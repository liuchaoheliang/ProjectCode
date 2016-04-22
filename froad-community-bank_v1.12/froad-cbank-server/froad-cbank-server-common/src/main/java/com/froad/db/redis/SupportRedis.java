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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.po.Area;
import com.froad.po.HotWordDetaill;
import com.froad.po.mongo.ProductDetail;
import com.froad.util.RedisKeyUtil;


/**    
 * <p>Title: MerchantRedis.java</p>    
 * <p>Description: 描述 </p>   
 * @author longyunbo      
 * @version 1.0    
 * @created 2015年3月20日 上午10:11:55   
 */
public class SupportRedis {
	
	private static final RedisManager redis = new RedisManager();
	

	/**
	 * 获取地区缓存 单个
	 * @Title: get_cbbank_area_area_id
	 * @author lf
	 * @version 1.0
	 * @see: TODO
	 * @param area_id
	 * @return
	 * @return Map<String, String>
	 * @throws
	 */
	public static Map<String, String> get_cbbank_area_area_id(Long area_id){
		String key = RedisKeyUtil.cbbank_area_area_id(area_id);
		Map<String, String> result = redis.getMap(key);
		return result;
	}
	
	/**
	 *  Rdis获取的Map，如果为空业务返回Map不是空对象
	 *  如果size是空返回null，利于前端检查
	  * @Title: converRedisMap
	  * @author: share 2015年4月11日
	  * @modify: share 2015年4月11日
	  * @param @param redisMap
	  * @param @return    
	  * @return Map<String,String>    
	  * @throws
	 */
	public static Map<String,String> converRedisMap(Map<String,String> redisMap){
		if(redisMap != null && redisMap.isEmpty()){
			return null;
		}
		return redisMap;
	}
	/**
	 * 查询key为cbbank:client:client:id里所有的filed内容
	 * @param client_id
	 * @param 
	 * @return 返回客户端内Redis所有缓存信息
	 */
	public static Map<String,String> getAll_cbbank_area_client_id_area_id(Long area_id) {
		String key = RedisKeyUtil.cbbank_area_client_id_area_id(area_id) ;
		Map<String,String> result =redis.getMap(key);
		return converRedisMap(result);
//		Jedis jedis = RedisManager.getJedis();
//		Map<String,String> result = null;
//		try{
//			result = jedis.hgetAll(key);
//		}catch(Exception e){
//			LogCvt.error(e.getMessage(), e);
//		}finally{
//			RedisManager.returnJedis(jedis);
//		}
//		return result;
	}
	
	/**
	 * 获取地区缓存 多个
	 * @Title: getAll_cbbank_area_area_id
	 * @author lf
	 * @version 1.0
	 * @see: TODO  现在的代理方式不支持 待二期完善
	 * @param area_id
	 * @return
	 * @return Map<String, String>
	 * @throws
	 */
	public static void getAll_cbbank_area_area_id(Area area){
		try{
			String key = "cbbank:area:*";
			Jedis rm = RedisManager.getJedis(RedisManager.read);
			java.util.Set<String> setS = rm.hkeys(key);
			LogCvt.debug(JSON.toJSONString(setS));
		}catch(Exception e){
			LogCvt.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * 缓存地区表
	 * @Title: set_cbbank_area_client_id_area_id
	 * @author longyunbo
	 * @version 1.0
	 * @param area
	 * @return
	 * @return Boolean 返回类型
	 * @throws
	 */
	public static Boolean set_cbbank_area_client_id_area_id(Area area) {
			/* 缓存全部地区 */

		    // 应该判断 area 里面没有值的属性，不要put到Map中
			Map<String, String> hash = new HashMap<String, String>();
			hash.put("client_id", ObjectUtils.toString(area.getClientId()));
			hash.put("name", ObjectUtils.toString(area.getName()));
			hash.put("vague_letter", ObjectUtils.toString(area.getVagueLetter()));
			hash.put("tree_path", ObjectUtils.toString(area.getTreePath()));
			hash.put("parent_id", ObjectUtils.toString(area.getParentId()));
			hash.put("is_enable", BooleanUtils.toString(area.getIsEnable(), "1", "0", ""));
			hash.put("tree_path_name", ObjectUtils.toString(area.getTreePathName()));
			hash.put("area_code", ObjectUtils.toString(area.getAreaCode()));
			
			return set_cbbank_area_client_id_area_id(area.getId(),hash);
//			String result = RedisManager.getJedis().hmset(key, hash);
		
	}
	
	public static Boolean set_cbbank_area_client_id_area_id(Long area_id, Map<String, String> hash){
		String key = RedisKeyUtil.cbbank_area_client_id_area_id(area_id);
		LogCvt.debug("key = "+key);
		String result="";
		result=redis.putMap(key, hash);
		return "OK".equals(result);
	}
	
    public static boolean set_cbbank_hotword_h5_redis(String key, List<HotWordDetaill> productDetails) throws Exception {
        String[] serStrArr = new String[productDetails.size()] ;
        for (int i = 0; i < productDetails.size(); i++) {
            serStrArr[i] = JSON.toJSONString(productDetails.get(i));
        }
        Long result = redis.rpush(key, serStrArr);
        if (result > 0) {
            int cacheTimeout = 5 * 60;// 设置超时时间 为 5分钟
            LogCvt.debug("设置个人H5热词[" + key + "]成功,缓存数据条数[" + serStrArr.length + "]条!");
            redis.expire(key, cacheTimeout); // 设置超时时间
            LogCvt.debug("设置个人H5热词[" + key + "]超时时间为" + cacheTimeout + "秒,成功!");
        }
        return result > 0;
    }
    public static List<HotWordDetaill> get_cbbank_hotword_h5_redis(String key,long start,long end) throws Exception {
        List<HotWordDetaill> result = null;
        List<String> list = redis.lrange(key, start, end);
        LogCvt.debug("Redis缓存使用lrange分页查询H5热词key=[" + key + "]的数据, start=[" + start + "], end=[" + end + "], 共返回["+list.size()+"]条数据!");
        if (CollectionUtils.isNotEmpty(list)) {
            result = new ArrayList<HotWordDetaill>();
            for (String serStr : list) {
                result.add(JSON.parseObject(serStr, HotWordDetaill.class));
            }
        }
        return result;
    }
}
