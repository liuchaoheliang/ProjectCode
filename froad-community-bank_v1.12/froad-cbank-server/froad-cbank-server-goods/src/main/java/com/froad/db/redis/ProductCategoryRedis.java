package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.po.ProductCategory;
import com.froad.util.RedisKeyUtil;

public class ProductCategoryRedis {

	
	/**
	 * set商品分类Redis
	 * @param productCategory
	 */
	public static void productCategoryRedis(ProductCategory productCategory){
        /* redis缓存 */
	    if(productCategory!=null){
	        Map<String, String> hash = new HashMap<String, String>();
	        hash.put("name", ObjectUtils.toString(productCategory.getName(), ""));
	        hash.put("tree_path", ObjectUtils.toString(productCategory.getTreePath(),""));
	        hash.put("parent_id", ObjectUtils.toString(productCategory.getParentId(), ""));
	        hash.put("is_delete", BooleanUtils.toString(productCategory.getIsDelete(), "1", "0", "0"));
	        String key = RedisKeyUtil.cbbank_product_category_client_id_product_category_id(productCategory.getClientId(), productCategory.getId());
	        RedisManager redis = new RedisManager();
	        redis.putMap(key, hash);
	    }
	}
	 
}
