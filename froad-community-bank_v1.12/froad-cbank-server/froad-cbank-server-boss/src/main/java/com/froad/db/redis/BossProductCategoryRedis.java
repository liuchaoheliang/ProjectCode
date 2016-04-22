package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.po.ProductCategory;
import com.froad.util.RedisKeyUtil;

public class BossProductCategoryRedis {
	/**
	 * set商品分类Redis
	 * @param po
	 */
	public static void productCategoryRedis(ProductCategory po){
        /* redis缓存 */
	    if(po != null){
	        Map<String, String> hash = new HashMap<String, String>();
	        hash.put("name", ObjectUtils.toString(po.getName(), ""));
	        hash.put("tree_path", ObjectUtils.toString(po.getTreePath(), ""));
	        hash.put("parent_id", ObjectUtils.toString(po.getParentId(), ""));
	        hash.put("is_delete", BooleanUtils.toString(po.getIsDelete(), "1", "0", ""));
	        String key = RedisKeyUtil.cbbank_product_category_client_id_product_category_id(po.getClientId(), po.getId());
	        RedisManager redis = new RedisManager();
	        redis.putMap(key, hash);
	    }
	}
	
	/**
	 * update商品分类Redis
	 * @param po
	 */
	public static void updateProductCategoryRedis(ProductCategory po){
        /* redis缓存 */
	    if(po != null){
	        Map<String, String> hash = new HashMap<String, String>();
	        String key = RedisKeyUtil.cbbank_product_category_client_id_product_category_id(po.getClientId(), po.getId());
	        RedisManager redis = new RedisManager();
	        hash.put("name", ObjectUtils.toString(po.getName(), ""));
	        hash.put("tree_path", po.getTreePath());
	        hash.put("parent_id", ObjectUtils.toString(po.getParentId(), ""));
	        hash.put("is_delete", BooleanUtils.toString(po.getIsDelete(), "1", "0", ""));
	        redis.putMap(key, hash);
	    }
	}
	public static Map<String, String> getProductCategoryRedis(String clientId, Long categoryId) {
		String key = RedisKeyUtil.cbbank_product_category_client_id_product_category_id(clientId, categoryId);
		RedisManager redis = new RedisManager();
	    return redis.getMap(key);
		
		
	}
}
