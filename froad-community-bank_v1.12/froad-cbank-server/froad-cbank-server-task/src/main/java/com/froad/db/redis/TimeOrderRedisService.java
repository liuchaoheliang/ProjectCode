package com.froad.db.redis;

import java.util.Set;

public interface TimeOrderRedisService {
	
	/**
	 * 获取缓存cbbank:time_order中的所有订单
	 * @Title: getOrderInfo 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年4月23日
	 * @modify: froad-huangyihao 2015年4月23日
	 * @return
	 * @throws
	 */
	Set<String> getOrderInfo();
	
	/**
	 * 删除缓存中的订单
	 * @Title: removeOrder 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年4月23日
	 * @modify: froad-huangyihao 2015年4月23日
	 * @param orderInfo
	 * @return
	 * @throws
	 */
	Boolean removeOrder(String orderInfo);
}
