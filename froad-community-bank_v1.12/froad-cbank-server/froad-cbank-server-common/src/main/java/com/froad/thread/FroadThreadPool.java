/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

/**  
 * @Title: FroadThreadPool.java
 * @Package com.froad.thread
 * @Description: TODO
 * @author vania
 * @date 2015年5月9日
 */

package com.froad.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Title: FroadThreadPool.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年5月9日 下午8:01:06
 */

public class FroadThreadPool {
//	private static ExecutorService pool = Executors.newFixedThreadPool(10);
	static int threads = 100; // 初始100个线程
	private static Object lock = new Object();
	private static ThreadPoolExecutor threadPools = null;
	
	private static final int MAX_THREAD_NUM = 200;
	

	static  {
		synchronized (lock) {
			threadPools = new ThreadPoolExecutor(threads, MAX_THREAD_NUM, 100,
					 TimeUnit.MILLISECONDS, new ArrayBlockingQueue(100),
					 new ThreadPoolExecutor.DiscardOldestPolicy());
			threadPools.allowCoreThreadTimeOut(true);		
		}
	}
	
	private FroadThreadPool() {
	}

	/**
	 * 执行任务
	 * @Title: execute 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param command
	 * @return void    返回类型 
	 * @throws
	 */
	public static void execute(Runnable command) {
//		pool.execute(command);
		threadPools.execute(command);
	}

	/**
	 * 获取当前活跃的线程数目
	* <p>Function: getActiveCount</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-14 下午1:42:27
	* @version 1.0
	* @return
	 */
	public static int getActiveCount() {
		return threadPools.getActiveCount();
	}
	
	/**
	 * 销毁线程池
	 * @Title: destroy 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @return void    返回类型 
	 * @throws
	 */
	public static void destroy() { // Destroy
		synchronized (threadPools) {
			if(!threadPools.isShutdown())
				threadPools.shutdown();
//			pool.shutdown(); // clear the list of threads
		}
	}

}
