package com.froad.cbank.coremodule.framework.common.monitor;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 监控
 * @ClassName Monitor
 * @author zxl
 * @date 2015年5月9日 上午10:55:17
 */
public class Monitor {

	private static ThreadPoolExecutor threadPools = null;
	private static SocketAddress sa = null;
	
	static {
		
		sa = new InetSocketAddress("127.0.0.1", 10055);
//		sa = new InetSocketAddress("10.43.1.7", 10055);
		
		int threads = 50;
		threadPools = new ThreadPoolExecutor(threads, Runtime.getRuntime()
				.availableProcessors() * threads, 100, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(100),
				new ThreadPoolExecutor.DiscardOldestPolicy());
		threadPools.allowCoreThreadTimeOut(true);
		
	}


	public Monitor() {
	}
	
	
	/**
	 * 发送
	 * @tilte send
	 * @author zxl
	 * @date 2015年5月9日 下午12:09:47
	 * @param enums 监控枚举
	 * @param value 发送值
	 */
	public static void send(MonitorEnums enums, String value) {
		threadPools.execute(new MonitorManager(enums,value,sa,Thread.currentThread().getName()));
	}
	
	public static void main(String[] args) {
		Monitor.send(MonitorEnums.thrift_error, "1");
	}

}
