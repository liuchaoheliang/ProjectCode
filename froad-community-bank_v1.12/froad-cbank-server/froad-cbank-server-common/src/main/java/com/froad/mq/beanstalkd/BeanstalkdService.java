package com.froad.mq.beanstalkd;

import com.trendrr.beanstalk.BeanstalkClient;
import com.trendrr.beanstalk.BeanstalkJob;

/**
 * Beanstalkd队列客户端接口（支持访问集群）
 * 
 * @author wangzhangxu
 * @date 2015年4月17日 下午12:54:02
 * @version v1.0
 */
public interface BeanstalkdService {
	
	/**
	 * 把数据放入队列
	 * 
	 * @param data 数据
	 * 
	 * @return long 任务ID
	 * 
	 * @author wangzhangxu
	 */
	public long put(String data);
	
	/**
	 * 从队列获取数据
	 * 
	 * @param timeout 数据
	 * 
	 * @return BeanstalkJob 任务
	 * 
	 * @author wangzhangxu
	 */
	public BeanstalkJob get(Integer timeout);
	
	/**
	 * 释放任务到队列中
	 * 
	 * @param BeanstalkJob 任务
	 * 
	 * @author wangzhangxu
	 */
	public void release(BeanstalkJob job);
	
	/**
	 * 释放任务到队列中
	 * 
	 * @param BeanstalkJob 任务
	 * 
	 * @param delay 延时放入队列的时间
	 * 
	 * @author wangzhangxu
	 */
	public void release(BeanstalkJob job, int delay);
	
	/**
	 * 从队列删除任务
	 * 
	 * @param jobId 任务ID
	 * 
	 * @return boolean 成功与否
	 * 
	 * @author wangzhangxu
	 */
	public boolean delete(long jobId);
	
	/**
	 * 使用(切换)队列中的管道
	 * 
	 * @param client 客户端
	 * 
	 * @param tubeName 管道名称
	 * 
	 * @author wangzhangxu
	 */
	public void useTube(BeanstalkClient client, String tubeName);
	
	/**
	 * 获取当前连接池的链接数
	 */
	public int currentPoolClients();
	
	/**
	 * 获取当前访问的集群实例序号
	 */
	public int currentIndex();
	
}
