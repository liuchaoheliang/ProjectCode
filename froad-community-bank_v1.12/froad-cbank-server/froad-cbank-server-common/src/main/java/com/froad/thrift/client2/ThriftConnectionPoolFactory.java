package com.froad.thrift.client2;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool.Config;
import org.apache.thrift.transport.TSocket;

/**
 * Thrift连接池工厂
 * 
 * @author wangzhangxu
 * @date 2015年6月15日 下午7:10:36
 * @version v1.0
 */
public class ThriftConnectionPoolFactory {

	private volatile static ThriftConnectionPoolFactory instance;
	
	private Map<String, ThriftSocketFactory> socketFactoryMap = new HashMap<String, ThriftSocketFactory>();

	private Map<String, LocalClientNode> serviceMap = new HashMap<String, LocalClientNode>();
	
	static int count = 0;

	private ThriftConnectionPoolFactory() {
	}

	public static ThriftConnectionPoolFactory getInstance() {
		if (instance == null) {
			synchronized (ThriftConnectionPoolFactory.class) {
				if (instance == null) {
					instance = new ThriftConnectionPoolFactory();
				}
			}
		}
		return instance;
	}
	
	public void registerService(LocalClientNode clientNode, Class clazz) {
		String serverKey = clientNode.getServerKey();
		if (null == socketFactoryMap.get(serverKey)) {
			ThriftSocketFactory socketFactory = new ThriftSocketFactory(clientNode);
			socketFactoryMap.put(serverKey, socketFactory);
		}
		
		serviceMap.put(clazz.getName(), clientNode);
	}

	public LocalClientNode getClientNode(String serviceName) {
		return serviceMap.get(serviceName);
	}
	
	public TSocket getSocket(String serverNodeKey) {
		ThriftSocketFactory socketFactory = socketFactoryMap.get(serverNodeKey);
		if (socketFactory == null) {
			throw new RuntimeException("Get ThriftSocketFactory is null.");
		}
		
		return socketFactory.getSocket();
	}
	
	public TSocket getSocket(LocalClientNode clientNode) {
		String key = clientNode.toString();
		return getSocket(key);
	}

	public void returnSocket(String clientNodeKey, TSocket socket) {
		if (clientNodeKey == null) {
			throw new RuntimeException("ReturnSocket clientNodeKey is null.");
		}
		
		if (socket == null) {
			throw new RuntimeException("ReturnSocket socket is null.");
		}
		
		ThriftSocketFactory socketFactory = socketFactoryMap.get(clientNodeKey);
		if (socketFactory == null) {
			throw new RuntimeException("Get ThriftSocketFactory is null.");
		}
		
		try {
			socketFactory.getSocketPool().returnObject(socket);
		} catch (Exception e) {
			throw new RuntimeException("Return Socket Pool Error.", e);
		}
	}

	/**
	 * TSocket连接池工厂类
	 */
	class ThriftSocketFactory extends BasePoolableObjectFactory {
		
		private LocalClientNode clientNode;
		
		private GenericObjectPool socketPool;

		public ThriftSocketFactory(LocalClientNode clientNode) {
			this.clientNode = clientNode;
			Config conifg = new Config();

			String maxWait = clientNode.getPropertiesMap().get(LocalClientNode.MAX_WAIT);
			if (maxWait != null) {
				try {
					conifg.maxWait = Integer.parseInt(maxWait);
				} catch (Exception e){}
			}
			
			String maxActive = clientNode.getPropertiesMap().get(LocalClientNode.MAX_ACTIVE);
			if (maxActive != null) {
				try {
					conifg.maxActive = Integer.parseInt(maxActive);
				} catch (Exception e){}
			}
			
			String maxIdle = clientNode.getPropertiesMap().get(LocalClientNode.MAX_IDLE);
			if (maxIdle != null) {
				try {
					conifg.maxIdle = Integer.parseInt(maxIdle);
				} catch (Exception e){}
			}
			
			socketPool = new GenericObjectPool(this, conifg);
		}
		
		public TSocket getSocket() {
			long start = System.currentTimeMillis();
			try {
				TSocket socket = (TSocket) socketPool.borrowObject();
				if (!socket.isOpen()) {
					socket.open();
				}
				return socket;
			} catch (Exception e) {
				long end = System.currentTimeMillis();
				throw new RuntimeException("Get TSocket Error. (Wait milliseconds: " + (end - start) + ")", e);
			}
		}
		
		@Override
		public Object makeObject() throws Exception {
			if (clientNode.getConnectTimeout() < 0) {
				return new TSocket(clientNode.getHost(), clientNode.getPort());
			}
			// connectTimeout is seconds
			// TSocket parameter is MilliSeconds
			return new TSocket(clientNode.getHost(), clientNode.getPort(), clientNode.getConnectTimeout());
		}
		
		public GenericObjectPool getSocketPool() {
			return socketPool;
		}

		public void setSocketPool(GenericObjectPool socketPool) {
			this.socketPool = socketPool;
		}

	}

}
