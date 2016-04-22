package com.froad.mq.beanstalkd.impl;

import java.nio.charset.Charset;

import com.froad.logback.LogCvt;
import com.froad.mq.beanstalkd.BeanstalkdService;
import com.trendrr.beanstalk.BeanstalkClient;
import com.trendrr.beanstalk.BeanstalkException;
import com.trendrr.beanstalk.BeanstalkJob;
import com.trendrr.beanstalk.BeanstalkPool;

/**
 * Beanstalkd队列客户端接口实现类
 * 
 * @author wangzhangxu
 * @date 2015年4月17日 下午12:40:52
 * @version v1.0
 */
public class BeanstalkdServiceImpl implements BeanstalkdService {

	static final String charsetName = "utf-8";
	
	private static SimpleClusterBeanstalkManager manager;
	
	/** 主机序号 */
	private int master;
	
	private String tubeName;
	
	private BeanstalkPool cluster;
	
	public BeanstalkdServiceImpl(int master){
		this(master, null);
	}
	
	public BeanstalkdServiceImpl(int master, String tubeName){
		this.master = master;
		this.tubeName = tubeName;
		
		manager = SimpleClusterBeanstalkManager.getInstance();
		
		cluster = manager.getCluster(master);
		// getClient();
	}
	
	private BeanstalkClient getClient(){
		if (cluster == null) {
			cluster = manager.getCluster(master);
		}
		
		BeanstalkClient client = null;
		try {
			client = cluster.getClient();
			useTube(client, tubeName);
		} catch (BeanstalkException e) {
			LogCvt.error("Beanstalkd集群实例操作异常：get client", e);
		}
		
		return client;
	}
	
	public int getMaster() {
		return master;
	}
	
	@Override
	public long put(String data) {
		long priority = 65536;
		int delay = 0, timeToRun = 120;
		byte[] bytes = data.getBytes(Charset.forName(charsetName));
		
		long jobId = -1;
		BeanstalkClient client = null;
		try {
			client = getClient();
			jobId = client.put(priority, delay, timeToRun, bytes);
		} catch (BeanstalkException e) {
			LogCvt.error("Beanstalkd集群实例操作异常：put error", e);
		} finally {
			if (client != null) {
				client.close();
			}
		}
		
		if (jobId < 0) {
			manager.changeCluster();
			
			// try it again
			try {
				client = getClient();
				jobId = getClient().put(priority, delay, timeToRun, bytes);
			} catch (BeanstalkException e) {
				LogCvt.error("Beanstalkd集群实例操作异常：put error again", e);
			} finally {
				if (client != null) {
					client.close();
				}
			}
		}
		
		if (jobId > 0) {
			LogCvt.info("Beanstalkd Put: JobId=" + jobId + ", Message=" + data);
		}
		
		return jobId;
	}
	
	@Override
	public boolean delete(long jobId) {
		BeanstalkClient client = null;
		try {
			client = getClient();
			client.deleteJob(jobId);
		} catch (BeanstalkException e) {
			LogCvt.error("Beanstalkd集群实例操作异常： delete error. [jobId=" + jobId + "]", e);
			return false;
		} finally {
			if (client != null) {
				client.close();
			}
		}
		
		return true;
	}
	
	@Override
	public BeanstalkJob get(Integer timeoutSeconds) {
		BeanstalkJob job = null;
		
		BeanstalkClient client = null;
		try {
			client = getClient();
			job = client.reserve(timeoutSeconds);
		} catch (BeanstalkException e) {
			LogCvt.error("Beanstalkd集群实例操作异常： reserve error", e);
		} finally {
			if (client != null) {
				client.close();
			}
		}
		return job;
	}
	
	@Override
	public void release(BeanstalkJob job){
		release(job, 0);
	}
	
	@Override
	public void release(BeanstalkJob job, int delay){
		if (job == null) {
			return;
		}
		
		if (delay < 0) {
			delay = 0;
		}
		
		int priority = 65536;
		BeanstalkClient client = null;
		try {
			client = getClient();
			client.release(job, priority, delay);
		} catch (BeanstalkException e) {
			LogCvt.error("Beanstalkd集群实例操作异常： release error", e);
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}
	
	@Override
	public void useTube(BeanstalkClient client, String tubeName) {
		this.tubeName = tubeName;
		
		try {
			client.useTube(tubeName);
			client.watchTube(tubeName);
			client.ignoreTube("default");
		} catch (BeanstalkException e) {
			LogCvt.error("Beanstalkd集群实例操作异常： use tube error", e);
		}
	}
	
	@Override
	public int currentPoolClients() {
		return manager.getCurrent() == null ? 0: manager.getCurrent().getPoolSize();
	}

	@Override
	public int currentIndex() {
		return manager.getCurrentIndex();
	}
	
	
}
