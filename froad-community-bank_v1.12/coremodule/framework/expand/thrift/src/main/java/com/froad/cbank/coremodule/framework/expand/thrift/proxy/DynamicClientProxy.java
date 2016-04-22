package com.froad.cbank.coremodule.framework.expand.thrift.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;

/**
 * 动态代理实现
 * @ClassName DynamicClientProxy
 * @author zxl
 * @date 2015年3月27日 下午2:45:30
 * @param <T>
 */
public class DynamicClientProxy<T> implements InvocationHandler {

	private Class<T> ts = null;

	private String host = null;
	
	private String serverName = null ;
	
	private Map<String,String> methodNames = null;
	
	private static final Logger log = LoggerFactory.getLogger(DynamicClientProxy.class);
	
	public Object createProxy(Class<T> ts, String host, String serverName, Map<String,String> methodNames) {
		this.ts = ts;
		this.host = host;
		this.serverName = serverName;
		this.methodNames = methodNames;
		return Proxy.newProxyInstance(ts.getClassLoader(), ts.getInterfaces(), this);
	}
	
	
	public Object createProxy(Class<T> ts, String host, String serverName) {
		this.ts = ts;
		this.host = host;
		this.serverName = serverName;
		return Proxy.newProxyInstance(ts.getClassLoader(), ts.getInterfaces(), this);
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		long begin = System.currentTimeMillis();
		TSocket tsocket = null;
		ThriftClient thrift = null;
		try {
//			tsocket = new TSocket(host.split(":")[0], Integer.parseInt(host.split(":")[1]), 10000); 
			
			thrift = new ThriftClient();
			
			if(methodNames != null && methodNames.containsKey(method.getName())){
				int timeout = Integer.parseInt(methodNames.get(method.getName()));
				thrift.open(host.split(":")[0], Integer.parseInt(host.split(":")[1]), serverName, timeout);
			}else{
				thrift.open(host.split(":")[0], Integer.parseInt(host.split(":")[1]), serverName);
			}
			
			

//			TProtocol protocol = BaseProtocol.getProtocol(tsocket, serverName);
//			
//			tsocket.open();
			
			Class[] argsClass = new Class[] { TProtocol.class };
			
			Constructor<T> cons = (Constructor<T>) ts.getConstructor(argsClass);
			
			T client = (T) cons.newInstance(thrift.getProtocol());
			
			if(args != null && log.isDebugEnabled()){
				for (int i = 0; i < args.length; i++) {
					log.debug(new StringBuffer("thrift request arg.") 
						.append(i).append("=").append((args[i] == null ? "null" : args[i].toString())).toString());
				}
			}
			
			return method.invoke(client, args);

		} catch (InvocationTargetException e) {
			//异常监控
			Monitor.send(MonitorEnums.thrift_error, "1");
			throw e.getTargetException();
		} catch (TTransportException e) {
			//异常监控
			Monitor.send(MonitorEnums.thrift_error, "1");
			throw new Exception("Server is not avaiable",e);
		} catch (Exception e) {
			//异常监控
			Monitor.send(MonitorEnums.thrift_error, "1");
			throw e;
		} catch (Throwable e) {
			//异常监控
			Monitor.send(MonitorEnums.thrift_error, "1");
			throw e;
		} finally {
			if ( tsocket != null ){
				tsocket.close();
			}
			
			if(thrift != null) {
				thrift.close();
			}
			long end = System.currentTimeMillis();
			log.info("thrift time:[" + (end-begin) +"ms]  serverName:" + serverName + "  method:" + method.getName());
		}
		
	}

}
