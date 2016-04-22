package com.froad.thrift.client.pool;

import org.apache.thrift.transport.TSocket;

/**
 * 连接池接口
 * @ClassName ConnectionProvider
 * @author zxl
 * @date 2015年3月23日 下午7:30:54
 */
public interface ConnectionProvider {
	
	/**
	 * 获取连接
	 * @tilte getConnection
	 * @author zxl
	 * @date 2015年3月23日 下午7:30:20
	 * @return
	 */
	public TSocket getConnection();
	
	/**
	 * 释放连接
	 * @tilte returnCon
	 * @author zxl
	 * @date 2015年3月23日 下午7:30:36
	 * @param socket
	 */
	public void returnCon(TSocket socket);
	
	/**
	 * 获取连接池基本信息
	 * @tilte getConnectionPoolInfo
	 * @author zxl
	 * @date 2015年3月24日 上午9:12:41
	 * @return
	 */
	public String getConnectionPoolInfo();
}
