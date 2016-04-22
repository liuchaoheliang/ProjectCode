package com.froad.thrift.client2;

import org.apache.thrift.transport.TSocket;

/**
 * Thrift服务客户端
 * 
 * @author wangzhangxu
 * @date 2015年6月15日 下午7:10:36
 * @version v1.0
 */
public class ServiceClient {
	
	private LocalClientNode clientNode;
	
	private TSocket socket;
	
	private ThriftConnectionPoolFactory connectionFactory = ThriftConnectionPoolFactory.getInstance();
	
	/**
	 * 创建（获取）客户端
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object createClient(Class ifaceClazz) {
		if (!ifaceClazz.getName().endsWith("$Iface")) {
			throw new RuntimeException("Class '" + ifaceClazz.getName() + "' is not Thrift Iface.");
		}
		
		try {
			String clazzClientName = ifaceClazz.getName().replace("$Iface", "$Client");
			
			Class clientClazz = Class.forName(clazzClientName);
			
			clientNode = connectionFactory.getClientNode(ifaceClazz.getName());
			
			socket = connectionFactory.getSocket(clientNode.getServerKey());
			
			ClientProxy proxy = new ClientProxy();
			
			return proxy.createProxy(clientClazz, socket);
		} catch (Exception e) {
			throw new RuntimeException("Create Proxy Client Error.", e);
		}
	}
	
	/**
	 * 归还连接对象给链接池
	 */
	public void returnClient(){
		if (socket != null) {
			connectionFactory.returnSocket(clientNode.getServerKey(), socket);
		}
	}
	
}
