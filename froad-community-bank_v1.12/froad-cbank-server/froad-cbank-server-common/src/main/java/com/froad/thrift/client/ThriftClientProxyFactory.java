/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
package com.froad.thrift.client;


/**
 * 
 * <p>Title: ThriftClientProxyFactory.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年4月29日 下午4:50:55
 */
public class ThriftClientProxyFactory {
	private static final int CLIENTTIMEOUT = 0; // 默认超时时间
	private ThriftClientProxyFactory(){}
	
	/**
	 * 动态代理
	 * @Title: createIface 
	 * @author vania
	 * @version 1.0
	 * @see: 
	 * @param clazzIfaceName
	 * @param serverName
	 * @param host
	 * @param port
	 * @param clientTimeOut
	 * @return
	 * @return Object    返回类型 
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object createIface(String clazzServiceName, String serverName, String host, int port, int clientTimeOut) {

		try {

			String clazzClientName = clazzServiceName + "$Client";

			Class clientClazz = Class.forName(clazzClientName);

			ThriftClientHandler proxy = new ThriftClientHandler();

			return proxy.createProxy(clientClazz, serverName, host, port, clientTimeOut);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	
	/**
	 * 动态代理
	 * @Title: createIface 
	 * @author vania
	 * @version 1.0
	 * @see: 
	 * @param clazzIfaceName
	 * @param serverName
	 * @param host
	 * @param port
	 * @return
	 * @return Object    返回类型 
	 * @throws
	 */
	public static Object createIface(String clazzServiceName, String serverName, String host, int port) {
		return createIface(clazzServiceName, serverName, host, port, CLIENTTIMEOUT);
	}
	
	/**
	 * 动态代理
	 * @Title: createIface 
	 * @author vania
	 * @version 1.0
	 * @see: 
	 * @param clazzService
	 * @param host
	 * @param port
	 * @return
	 * @return Object    返回类型 
	 * @throws
	 */
	public static Object createIface(Class<?> clazzService, String host, int port) {
		return createIface(clazzService.getName(), clazzService.getSimpleName(), host, port, CLIENTTIMEOUT);
	}
	
	/**
	 * 动态代理
	 * @Title: createIface 
	 * @author vania
	 * @version 1.0
	 * @see: 
	 * @param clazzService
	 * @param host
	 * @param port
	 * @param clientTimeOut
	 * @return
	 * @return Object    返回类型 
	 * @throws
	 */
	public static Object createIface(Class<?> clazzService, String host, int port, int clientTimeOut) {
		return createIface(clazzService.getName(), clazzService.getSimpleName(), host, port, clientTimeOut);
	}
	
	
	
}
