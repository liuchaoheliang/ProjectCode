package com.froad.db.redis;

public interface ProductStoreRedisService {
	
	/**
	 * 
	 * @Title: incrStore 
	 * @Description: 还商品库存
	 * @author: froad-huangyihao 2015年4月28日
	 * @modify: froad-huangyihao 2015年4月28日
	 * @param clientId
	 * @param merchantId
	 * @param productId
	 * @param store
	 * @return
	 * @throws
	 */
	Long incrStore(String clientId, String merchantId, String productId, Long store);
	
	/**
	 * 
	 * @Title: getStore 
	 * @Description: 获取商品库存
	 * @author: froad-huangyihao 2015年4月28日
	 * @modify: froad-huangyihao 2015年4月28日
	 * @param clientId
	 * @param merchantId
	 * @param productId
	 * @return
	 * @throws
	 */
	Long getStore(String clientId, String merchantId, String productId);
}
