package com.froad.worker.thread;

/**
 * 秒杀下单工人接口
 * 
 * @author wangzhangxu
 * @date 2015年4月23日 下午8:35:54
 * @version v1.0
 */
public interface PlaceOrderWorker extends Runnable {
	
	/**
	 * 设置序号
	 */
	public void setIndex(int index);
	
	/**
	 * 工作
	 */
	public void work();
	
	/**
	 * 停止
	 */
	public void stop();
	
	
}
