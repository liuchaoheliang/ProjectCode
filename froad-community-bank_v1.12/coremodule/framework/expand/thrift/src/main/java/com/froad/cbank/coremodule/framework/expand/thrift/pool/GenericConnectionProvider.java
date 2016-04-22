package com.froad.cbank.coremodule.framework.expand.thrift.pool;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.transport.TSocket;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 连接池接口实现
 * @ClassName GenericConnectionProvider
 * @author zxl
 * @date 2015年3月23日 下午7:45:30
 */
public class GenericConnectionProvider implements ConnectionProvider, InitializingBean, DisposableBean {
	
	/** 服务的IP地址 */
	private String serviceIP;
	
	/** 服务的端口 */
	private int servicePort;
	
	/** 连接超时配置 */
	private int conTimeOut;
	
	/** 可以从缓存池中分配对象的最大数量 */
	private int maxActive = GenericObjectPool.DEFAULT_MAX_ACTIVE;
	
	/** 缓存池中最大空闲对象数量 */
	private int maxIdle = GenericObjectPool.DEFAULT_MAX_IDLE;
	
	/** 缓存池中最小空闲对象数量 */
	private int minIdle = GenericObjectPool.DEFAULT_MIN_IDLE;
	
	/** 阻塞的最大数量 */
	private long maxWait = GenericObjectPool.DEFAULT_MAX_WAIT;
	
	/** 从缓存池中分配对象，是否执行PoolableObjectFactory.validateObject方法 */
	private boolean testOnBorrow = GenericObjectPool.DEFAULT_TEST_ON_BORROW;
	private boolean testOnReturn = GenericObjectPool.DEFAULT_TEST_ON_RETURN;
	private boolean testWhileIdle = GenericObjectPool.DEFAULT_TEST_WHILE_IDLE;
	
	/** 对象缓存池 */
	private ObjectPool objectPool = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		// 对象池
		objectPool = new GenericObjectPool();
		//
		((GenericObjectPool) objectPool).setMaxActive(maxActive);
		((GenericObjectPool) objectPool).setMaxIdle(maxIdle);
		((GenericObjectPool) objectPool).setMinIdle(minIdle);
		((GenericObjectPool) objectPool).setMaxWait(maxWait);
		((GenericObjectPool) objectPool).setTestOnBorrow(testOnBorrow);
		((GenericObjectPool) objectPool).setTestOnReturn(testOnReturn);
		((GenericObjectPool) objectPool).setTestWhileIdle(testWhileIdle);
		((GenericObjectPool) objectPool)
				.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_BLOCK);
		// 设置factory
		ThriftPoolableObjectFactory thriftPoolableObjectFactory = new ThriftPoolableObjectFactory(serviceIP, servicePort, conTimeOut);
		objectPool.setFactory(thriftPoolableObjectFactory);
	}

	@Override
	public void destroy() {
		try {
			objectPool.close();
		} catch (Exception e) {
			throw new RuntimeException("erorr destroy()", e);
		}
	}

	@Override
	public TSocket getConnection() {
		try {
			TSocket socket = (TSocket) objectPool.borrowObject();
			return socket;
		} catch (Exception e) {
			throw new RuntimeException("error getConnection()", e);
		}
	}

	@Override
	public void returnCon(TSocket socket) {
		try {
			objectPool.returnObject(socket);
		} catch (Exception e) {
			throw new RuntimeException("error returnCon()", e);
		}
	}
	
	@Override
	public String getConnectionPoolInfo() {
		return "thriftConnectPool : { maxActive : " + maxActive + ", active : " +objectPool.getNumActive() + ", idle : " + objectPool.getNumIdle()+" }";
	}
	
	public String getServiceIP() {
		return serviceIP;
	}

	public void setServiceIP(String serviceIP) {
		this.serviceIP = serviceIP;
	}

	public int getServicePort() {
		return servicePort;
	}

	public void setServicePort(int servicePort) {
		this.servicePort = servicePort;
	}

	public int getConTimeOut() {
		return conTimeOut;
	}

	public void setConTimeOut(int conTimeOut) {
		this.conTimeOut = conTimeOut;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public long getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public ObjectPool getObjectPool() {
		return objectPool;
	}

	public void setObjectPool(ObjectPool objectPool) {
		this.objectPool = objectPool;
	}
}
