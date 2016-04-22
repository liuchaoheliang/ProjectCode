package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.po.MerchantCategory;
import com.froad.util.RedisKeyUtil;

public class BossMerchantCategoryRedis {

	/**
	 * set商户分类Redis
	 * @param mo
	 */
	public static void merchantCategoryRedis(MerchantCategory mo){
        /* redis缓存 */
	    if(mo != null){
	       
	        Map<String, String> hash = new HashMap<String, String>();
	        hash.put("name", ObjectUtils.toString(mo.getName(), ""));
	        hash.put("tree_path", ObjectUtils.toString(mo.getTreePath(), ""));
	        hash.put("parent_id", ObjectUtils.toString(mo.getParentId(), ""));
	        hash.put("is_delete", BooleanUtils.toString(mo.getIsDelete(), "1", "0", ""));
	        String key = RedisKeyUtil.cbbank_merchant_category_detail_client_id_category_id(mo.getClientId(), mo.getId());
	        RedisManager redis = new RedisManager();
	        redis.putMap(key, hash);
	    }
	}
	
	/**
	 * update商户分类Redis
	 * @param mo
	 */
	public static void updateMerchantCategoryRedis(MerchantCategory mo){
        /* redis缓存 */
	    if(mo != null){
	        Map<String, String> hash = new HashMap<String, String>();
	        RedisManager redis = new RedisManager();
	        String key = RedisKeyUtil.cbbank_merchant_category_detail_client_id_category_id(mo.getClientId(), mo.getId());
	        hash.put("name", ObjectUtils.toString(mo.getName(), ""));
	        hash.put("tree_path", mo.getTreePath());
	        hash.put("parent_id",ObjectUtils.toString(mo.getParentId(), ""));
	        hash.put("is_delete", BooleanUtils.toString(mo.getIsDelete(), "1", "0", ""));
	        redis.putMap(key, hash);
	    }
	}
	
	
	public static Map<String, String> getMerchantCategoryRedis(String clientId, Long categoryId) {
		String key = RedisKeyUtil.cbbank_merchant_category_detail_client_id_category_id(clientId, categoryId);
		RedisManager redis = new RedisManager();
	    return redis.getMap(key);
		
		
	}
}
