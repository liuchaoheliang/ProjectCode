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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

import com.alibaba.fastjson.JSON;
import com.froad.logback.LogCvt;

/**
 * 动态代理实现
 * @ClassName DynamicClientProxy
 * @author zxl
 * @date 2015年3月27日 下午2:45:30
 * @param <T>
 */
public class ThriftClientHandler<T> implements InvocationHandler {

	private Class<T> ts = null;

	private String host = null;
	
	private int port = 8888;
	
	private int clientTimeOut = 0;
	
	private String serverName = null ;
	
	public Object createProxy(Class<T> ts, String serverName, String host, int port) {
		return createProxy(ts, serverName, host, port, clientTimeOut);
	}

	public Object createProxy(Class<T> ts, String serverName, String host, int port, int clientTimeOut) {
		this.ts = ts;
		this.host = host;
		this.port = port;
		this.clientTimeOut = clientTimeOut;
		this.serverName = serverName;
		return Proxy.newProxyInstance(ts.getClassLoader(), ts.getInterfaces(), this);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		Object result = null;
		TSocket tsocket = null;
		try {
			long start = System.currentTimeMillis();
			LogCvt.info("正在连接[" + host + ":" + port + "]...");
			tsocket = new TSocket(host, port, clientTimeOut);
			LogCvt.info("连接成功!!!");
			TProtocol protocol = null;
			protocol = new TMultiplexedProtocol(new TBinaryProtocol(tsocket), serverName);
//			protocol = new TMultiplexedProtocol(new TCompactProtocol(tsocket),serverName);
			
			tsocket.open();
			
			Class[] argsClass = new Class[] { TProtocol.class };
			
			Constructor<T> cons = (Constructor<T>) ts.getConstructor(argsClass);
			
			T client = (T) cons.newInstance(protocol);
			LogCvt.info("调用[" + serverName + "." + method.getName() + "]开始, 请求参数=" + JSON.toJSONString(args));
			try {
				result = method.invoke(client, args);				
			} catch (InvocationTargetException e) {
				if(e.getTargetException().getClass() == TApplicationException.class) {
					TApplicationException ex = (TApplicationException) e.getTargetException();
					if(ex.getType() == TApplicationException.MISSING_RESULT) { // 返回值为空  不报org.apache.thrift.TApplicationException: 方法名 failed: unknown result错
						result = null; // 把结果设置为null
					} else {
						throw e;
					}
				} else {
					throw e;
				}
			}
			long end = System.currentTimeMillis();
			LogCvt.info("调用[" + serverName + "." + method.getName() + "]成功, 返回结果=" + JSON.toJSONString(result) + ", 共耗时[" + (end - start) + "ms]!!!");
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		} catch (TTransportException e) {
			throw new Exception("Server is not avaiable",e);
		} catch (Exception e) {
			throw e;
		} catch (Throwable e) {
			throw e;
		} finally {
			if ( tsocket != null ){
				tsocket.close();
			}
		}
		return result;
	}

}
