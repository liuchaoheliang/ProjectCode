package com.froad.logic.impl;

import com.froad.db.redis.SeckillRedis;
import com.froad.logic.SeckillProductLogic;

/**
 * SeckillProductLogic实现类
 * 
 * @author wangzhangxu
 * @date 2015年4月17日 下午12:53:00
 * @version v1.0
 */
public class SeckillProductLogicImpl implements SeckillProductLogic {
	
	public SeckillProductLogicImpl(){
		
	}
	
	@Override
	public long getStock(String clientId, String productId) {
		return SeckillRedis.get_cbbank_seckill_product_store_client_id_product_id(clientId, productId);
	}
	
	@Override
	public long subQueueJobCount() {
		return SeckillRedis.decr_cbbank_seckill_product_queue_count();
	}

	@Override
	public long getCurrentPersecondCount(String ip, String time) {
		return SeckillRedis.get_cbbank_seckill_worker_count_persecond_local_ip_time(ip, time);
	}

	@Override
	public long incrCurrentPersecondCount(String ip, String time) {
		return SeckillRedis.incr_cbbank_seckill_worker_count_persecond_local_ip_time(ip, time);
	}
	
}
