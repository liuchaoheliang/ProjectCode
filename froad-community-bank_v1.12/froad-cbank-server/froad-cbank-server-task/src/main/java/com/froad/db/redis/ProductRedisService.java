package com.froad.db.redis;

import java.util.Map;

public interface ProductRedisService {
	
	/**
	 * 获取缓存中的商品信息
	 * @Title: getProductInfo 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年4月23日
	 * @modify: froad-huangyihao 2015年4月23日
	 * @param clientId
	 * @param merchantId
	 * @param productId
	 * @return
	 * @throws
	 */
	Map<String, String> getProductInfo(String clientId, String merchantId, String productId);
	
	/**
	 * 
	 * @Title: updateClusterState 
	 * @Description: 更新商品缓存的成团状态
	 * @author: froad-huangyihao 2015年4月23日
	 * @modify: froad-huangyihao 2015年4月23日
	 * @param clientId
	 * @param merchantId
	 * @param productId
	 * @param clusterState
	 * @throws
	 */
	Boolean updateClusterState(String clientId, String merchantId, String productId, String clusterState);
}
