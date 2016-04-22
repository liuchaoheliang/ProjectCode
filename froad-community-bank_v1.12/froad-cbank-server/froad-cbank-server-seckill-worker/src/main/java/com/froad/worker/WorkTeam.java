package com.froad.worker;

import com.froad.worker.thread.PayOrderWorker;
import com.froad.worker.thread.PlaceOrderWorker;
import com.froad.worker.thread.impl.PayOrderWorkerImpl;
import com.froad.worker.thread.impl.PlaceOrderWorkerImpl;
import com.froad.util.PropertiesUtil;

/**
 * 工作组
 * 
 * @author wangzhangxu
 * @date 2015年4月23日 下午8:42:04
 * @version v1.0
 */
public class WorkTeam {
	
	/** 同时开启的线程数 */
	static int workersPlaceOrder = 1;
	static int workersPayOrder = 1;
	static long sleep = 500;
	
	static final String KEY_FILENAME = "seckill-worker";
	static final String KEY_WORKERS_PLACE_ORDER = "workers.place.order";
	static final String KEY_WORKERS_PAY_ORDER = "workers.pay.order";
	
	static {
		workersPlaceOrder = Integer.parseInt(PropertiesUtil.getProperty(KEY_FILENAME, KEY_WORKERS_PLACE_ORDER));
		//workersPayOrder = Integer.parseInt(PropertiesUtil.getProperty(KEY_FILENAME, KEY_WORKERS_PAY_ORDER));
		
	}
	
	
	public void work() {
		// 下单线程
		for (int i = 0; i < workersPlaceOrder; i++) {
			PlaceOrderWorker worker = new PlaceOrderWorkerImpl();
			worker.setIndex(i);
			
			Thread thread = new Thread(worker);
			thread.start();
			
			try {
				Thread.sleep(sleep); // 每个线程延迟sleep毫秒启动
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		/*
		// 支付线程
		for (int i = 0; i < workersPayOrder; i++) {
			PayOrderWorker worker = new PayOrderWorkerImpl();
			worker.setIndex(i);
			
			Thread thread = new Thread(worker);
			thread.start();
			
			try {
				Thread.sleep(500); // 每个线程延迟500毫秒启动
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		 */
	}

}
