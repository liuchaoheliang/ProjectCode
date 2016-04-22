package com.froad.db.redis;

import java.util.Map;

public interface ProductSeckillRedisService {

	/**
	 * 获取秒杀商品队列的数量
	 * @Title: getSecKillProductQueueCount
	 * @Description: TODO
	 * @author: lf
	 * @return Integer
	 * @throws
	 */
	Integer getSecKillProductQueueCount();
	
	/**
	 * 删除 秒杀商品库存
	 * @Title: delSeckillProductStore
	 * @Description: TODO
	 * @author: lf
	 * @param String client_id, String product_id
	 * @return boolean
	 * @throws
	 */
	boolean delSeckillProductStore(String client_id, String product_id);
	
	/**
	 * 清空 秒杀商品库存
	 * @Title: clearSeckillProductStore
	 * @Description: TODO
	 * @author: lf
	 * @param String client_id, String product_id
	 * @return boolean
	 * @throws
	 */
	boolean clearSeckillProductStore(String client_id, String product_id);
	
	/**
	 * 更新 秒杀商品配置
	 * @Title: updateSeckillProductOfStore
	 * @Description: TODO
	 * @author: lf
	 * @param String client_id, String product_id, String sec_store
	 * @return boolean
	 * @throws
	 */
	boolean updateSeckillProductOfStore(String client_id, String product_id, String sec_store);
}
