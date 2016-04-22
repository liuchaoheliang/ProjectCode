package com.froad.logic;

/**
 * 秒杀商品逻辑接口
 * 
 * @author wangzhangxu
 * @date 2015年4月17日 下午7:26:03
 * @version v1.0
 */
public interface SeckillProductLogic {
	
	/**
	 * 获取秒杀商品库存
	 * 
	 * @param clientId 客户端ID
	 * @param productId 秒杀商品ID
	 * 
	 * @return long 库存数
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月17日 下午7:26:03
	 */
	public long getStock(String clientId, String productId);
	
	/**
	 * 队列中任务的个数减1
	 * 
	 * @return long 个数
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月17日 下午7:26:03
	 */
	public long subQueueJobCount();
	
	/**
	 * 获取当前每秒数量
	 * 
	 * @param ip 本机IP
	 * 
	 * @param time 当前一秒的时间
	 * 
	 * @return long 个数
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月17日 下午7:26:03
	 */
	public long getCurrentPersecondCount(String ip, String time);
	
	/**
	 * 当前每秒数量+1
	 * 
	 * @param ip 本机IP
	 * 
	 * @param time 当前一秒的时间
	 * 
	 * @return long 个数
	 * 
	 * @author wangzhangxu
	 * @date 2015年6月8日 下午7:26:03
	 */
	public long incrCurrentPersecondCount(String ip, String time);
	
}
