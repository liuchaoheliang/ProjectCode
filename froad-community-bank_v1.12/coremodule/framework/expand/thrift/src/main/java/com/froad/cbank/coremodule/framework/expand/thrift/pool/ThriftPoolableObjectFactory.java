package com.froad.cbank.coremodule.framework.expand.thrift.pool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * 连接池工厂
 * @ClassName ThriftPoolableObjectFactory
 * @author zxl
 * @date 2015年3月23日 下午7:48:24
 */
public class ThriftPoolableObjectFactory implements PoolableObjectFactory {
	
	/** 服务的IP */
	private String serviceIP;
	
	/** 服务的端口 */
	private int servicePort;
	
	/** 超时设置 */
	private int timeOut;
	
	public ThriftPoolableObjectFactory(String serviceIP, int servicePort,
			int timeOut) {
		this.serviceIP = serviceIP;
		this.servicePort = servicePort;
		this.timeOut = timeOut;
	}
	
	/**
	 * 销毁连接
	 */
	@Override
	public void destroyObject(Object arg0) throws Exception {
		if (arg0 instanceof TSocket) {
			TSocket socket = (TSocket) arg0;
			if (socket.isOpen()) {
				socket.close();
			}
		}
	}
	
	/**
	 * 创建连接
	 */
	@Override
	public Object makeObject() throws Exception {
		try {
			TTransport transport = new TSocket(this.serviceIP, this.servicePort, this.timeOut);
			transport.open();
			return transport;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 验证连接
	 */
	@Override
	public boolean validateObject(Object arg0) {
		try {
			if (arg0 instanceof TSocket) {
				TSocket thriftSocket = (TSocket) arg0;
				if (thriftSocket.isOpen()) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void passivateObject(Object arg0) throws Exception {
		// DO NOTHING
	}

	@Override
	public void activateObject(Object arg0) throws Exception {
		// DO NOTHING
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

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
}
