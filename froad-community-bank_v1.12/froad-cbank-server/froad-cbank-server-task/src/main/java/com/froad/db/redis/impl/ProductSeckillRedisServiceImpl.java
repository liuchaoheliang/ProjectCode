package com.froad.db.redis.impl;

import com.froad.db.redis.ProductSeckillRedisService;
import com.froad.logback.LogCvt;
import com.froad.util.RedisKeyUtil;

public class ProductSeckillRedisServiceImpl implements ProductSeckillRedisService {
	private RedisManager manager = new RedisManager();

	/**
	 * 获取秒杀商品队列的数量
	 * @Title: getSecKillProductQueueCount
	 * @Description: TODO
	 * @author: lf
	 * @return Integer
	 * @throws
	 */
	@Override
	public Integer getSecKillProductQueueCount() {
		
		Integer count = null;
		
		try{
			
			String key = RedisKeyUtil.cbbank_seckill_product_queue_count();
			String result = manager.getString(key);
			if( result == null ){
				return 0;
			}
			count = new Integer(Integer.parseInt(result));
			
		} catch (Exception e) {
			LogCvt.error("获取秒杀商品队列的数量 异常 "+e.getMessage(), e);
		} 
		return count;
	}

	/**
	 * 删除 秒杀商品库存
	 * @Title: delSeckillProductStore
	 * @Description: TODO
	 * @author: lf
	 * @param String client_id, String product_id
	 * @return boolean
	 * @throws
	 */
	@Override
	public boolean delSeckillProductStore(String client_id, String product_id) {
		// TODO Auto-generated method stub
		
		long result = 0;
		
		try{
			
			String key = RedisKeyUtil.cbbank_seckill_product_store_client_id_product_id(client_id, product_id);
			result = manager.del(key);
			
		} catch (Exception e) {
			LogCvt.error("删除 秒杀商品库存 异常 "+e.getMessage(), e);
		} 
		
		return result > -1;
	}

	/**
	 * 清空 秒杀商品库存
	 * @Title: clearSeckillProductStore
	 * @Description: TODO
	 * @author: lf
	 * @param String client_id, String product_id
	 * @return boolean
	 * @throws
	 */
	@Override
	public boolean clearSeckillProductStore(String client_id, String product_id){

		String result = null;
		
		try{
			
			String key = RedisKeyUtil.cbbank_seckill_product_store_client_id_product_id(client_id, product_id);
			result = manager.putString(key, "0");
			
		} catch (Exception e) {
			LogCvt.error("清空 秒杀商品库存 异常 "+e.getMessage(), e);
		} 
		
		return "OK".equals(result);
	}
	
	/**
	 * 更新 秒杀商品配置 库存数量
	 * @Title: updateSeckillProductOfStore
	 * @Description: TODO
	 * @author: lf
	 * @param String client_id, String product_id, String sec_store
	 * @return boolean
	 * @throws
	 */
	@Override
	public boolean updateSeckillProductOfStore(String client_id, String product_id, String sec_store) {
		// TODO Auto-generated method stub
		
		long result = 0;
		
		try{
			
			String key = RedisKeyUtil.cbbank_seckill_product_client_id_product_id(client_id, product_id);
			result = manager.hset(key, "sec_store", sec_store);
			
		} catch (Exception e) {
			LogCvt.error("更新 秒杀商品配置 异常 "+e.getMessage(), e);
		} 
		
		return result > -1;
	}
	
}
