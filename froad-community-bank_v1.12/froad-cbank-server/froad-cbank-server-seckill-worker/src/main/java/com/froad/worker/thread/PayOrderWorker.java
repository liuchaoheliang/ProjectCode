package com.froad.worker.thread;

/**
 * 秒杀支付订单工人接口
 * 
 * @author wangzhangxu
 * @date 2015年6月07日 下午11:35:54
 * @version v1.0
 */
public interface PayOrderWorker extends Runnable {
	
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
