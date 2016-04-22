package com.froad.mq.beanstalkd.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.froad.logback.LogCvt;
import com.froad.util.PropertiesUtil;
import com.trendrr.beanstalk.BeanstalkClient;
import com.trendrr.beanstalk.BeanstalkException;
import com.trendrr.beanstalk.BeanstalkPool;

/**
 * SimpleClusterBeanstalkManager
 * 
 * @author wangzhangxu
 * @date 2015年4月17日 上午11:31:32
 * @version v1.0
 */
public class SimpleClusterBeanstalkManager {
	
	private volatile static SimpleClusterBeanstalkManager instance;
	
	static final String KEY_BEANSTALKD_FILENAME = "beanstalkd";
	
	static final String KEY_BEANSTALKD_CLUSTERS_COUNT = "beanstalkd.clusters.count";
	static final String KEY_BEANSTALKD_CLUSTER_POOL_SIZE = "beanstalkd.cluster.pool.size";
	
	static final String KEY_BEANSTALKD_CLUSTER_HOST_PREFIX = "beanstalkd.cluster.host.";
	static final String KEY_BEANSTALKD_CLUSTER_PORT_PREFIX = "beanstalkd.cluster.port.";
	
	private static Map<Integer, BeanstalkPool> aliveClusters;
	private static Map<Integer, BeanstalkPool> errorClusters;
	
	private int currentIndex = 0;
	
	private BeanstalkPool current;
	
	/** 集群数量 */
	static int CLUSTER_COUNT;
	
	/** 每个实例的最大连接数 */
	static int POOL_SIZE;
	
	static {
		init();
		
	}
	
	private SimpleClusterBeanstalkManager(){}
	
	public static SimpleClusterBeanstalkManager getInstance() {
		if (instance == null) {
			synchronized (SimpleClusterBeanstalkManager.class) {
				if (instance == null) {
					instance = new SimpleClusterBeanstalkManager();
				}
			}
		}
		return instance;
	}
	
	private static void init(){
		CLUSTER_COUNT = Integer.parseInt(PropertiesUtil.getProperty(KEY_BEANSTALKD_FILENAME, KEY_BEANSTALKD_CLUSTERS_COUNT));
		POOL_SIZE = Integer.parseInt(PropertiesUtil.getProperty(KEY_BEANSTALKD_FILENAME, KEY_BEANSTALKD_CLUSTER_POOL_SIZE));
		
		aliveClusters = new HashMap<Integer, BeanstalkPool>();
		for (int i = 0; i < CLUSTER_COUNT; i++) {
			String host = PropertiesUtil.getProperty(KEY_BEANSTALKD_FILENAME, KEY_BEANSTALKD_CLUSTER_HOST_PREFIX + i);
			int port = Integer.parseInt(PropertiesUtil.getProperty(KEY_BEANSTALKD_FILENAME, KEY_BEANSTALKD_CLUSTER_PORT_PREFIX + i));
			BeanstalkPool beanstalkPool = new BeanstalkPool(host, port, POOL_SIZE);
			try {
				// 尝试获取一个客户端, 完成连接池的初始化
				BeanstalkClient client = beanstalkPool.getClient(); 
				client.useTube("test");
				client.close();
				LogCvt.debug("Beanstalkd集群实例(" + i + ") - [" + host + ":" + port + "]初始化成功.");
			} catch (BeanstalkException e) {
				throw new RuntimeException("Beanstalkd集群管理器初始化连接池异常", e);
			}
			aliveClusters.put(i, beanstalkPool);
		}
		
		errorClusters = new HashMap<Integer, BeanstalkPool>();
	}
	
	public BeanstalkPool getCluster(){
		return getCluster(0);
	}
	
	
	/**
	  * 方法描述：循环取实例（直到取一个可用的实例为止）
	  * @param: 当前实例索引
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年8月3日 上午11:35:21
	  */
	public BeanstalkPool getCluster(int index) {
		if (index < 0 || index >= CLUSTER_COUNT) {
			throw new RuntimeException("无效的Beanstalkd集群实例序号.");
		}
		
		if (aliveClusters == null || aliveClusters.size() == 0) {
			throw new RuntimeException("没有找到可用的Beanstalkd集群实例.");
		}
		
		current = aliveClusters.get(index);
		if (current != null) {
			currentIndex = index;
			return current;
		}
		
		Iterator<Integer> it = aliveClusters.keySet().iterator();
		Integer i = null;
		while(it.hasNext()){
			i = it.next();
			
			if (i == index) {
				continue;
			}
			
			current = aliveClusters.get(i);
			if (current != null) {
				currentIndex = i;
				break;
			}
		}
		
		return current;
	}
	
	/**
	 * 获取index序号的下一个实例
	 */
	public BeanstalkPool getNextCluster(int index) {
		if (index < 0 || index >= CLUSTER_COUNT) {
			throw new RuntimeException("无效的Beanstalkd集群实例序号.");
		}
		
		if (aliveClusters == null || aliveClusters.size() == 0) {
			throw new RuntimeException("没有找到可用的Beanstalkd集群实例.");
		}
		
		if (aliveClusters.size() == 1) {
			current = aliveClusters.get(0);
			currentIndex = 0;
			return current;
		}
		
		index++;
		
		Iterator<Integer> it = aliveClusters.keySet().iterator();
		Integer i = null;
		while(it.hasNext()){
			i = it.next();
			
			if (i >= index) {
				current = aliveClusters.get(i);
				currentIndex = i;
				break;
			}
		}
		
		if (current == null) {
			current = aliveClusters.get(0);
			currentIndex = 0;
		}
		
		return current;
	}
	
	/**
	 * 切换到可用的集群实例
	 */
	public synchronized void changeCluster(){
		if (current == null) {
			LogCvt.debug("忽略切换Beanstalkd集群实例.[current=null]");
			return;
		}
		
		int oldIndex = currentIndex;
		
		BeanstalkPool error = current;
		
		errorClusters.put(currentIndex, error);
		
		aliveClusters.remove(currentIndex);
		
		current = getCluster(currentIndex);
		
		LogCvt.info("成功切换Beanstalkd集群实例 (" + oldIndex + ")到集群实例(" + currentIndex + ")");
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}

	public BeanstalkPool getCurrent() {
		return current;
	}
	
}
