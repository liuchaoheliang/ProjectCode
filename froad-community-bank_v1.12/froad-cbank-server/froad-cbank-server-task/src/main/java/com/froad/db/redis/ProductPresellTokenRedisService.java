package com.froad.db.redis;


public interface ProductPresellTokenRedisService {
	
	/**
	 * 
	 * @Title: decrCount 
	 * @Description: 减少门店预售商品提货数量
	 * @author: froad-huangyihao 2015年4月25日
	 * @modify: froad-huangyihao 2015年4月25日
	 * @param clientId
	 * @param orgCode
	 * @param productId
	 * @param count
	 * @return
	 * @throws
	 */
	Long decrCount(String clientId, String orgCode,String productId, Long count);
	
	/**
	 * 
	 * @Title: getCount 
	 * @Description: 门店预售商品提货数量
	 * @author: froad-huangyihao 2015年4月25日
	 * @modify: froad-huangyihao 2015年4月25日
	 * @param clientId
	 * @param orgCode
	 * @param productId
	 * @return
	 * @throws 
	 * @throws
	 */
	Long getCount(String clientId, String orgCode,String productId);
	
	/**
	 * 
	 * @Title: resetCount 
	 * @Description: 重置门店预售商品提货数量
	 * @author: froad-huangyihao 2015年4月25日
	 * @modify: froad-huangyihao 2015年4月25日
	 * @param clientId
	 * @param orgCode
	 * @param productId
	 * @param count
	 * @return
	 * @throws
	 */
	Long resetCount(String clientId, String orgCode,String productId, Long count);
	
}
