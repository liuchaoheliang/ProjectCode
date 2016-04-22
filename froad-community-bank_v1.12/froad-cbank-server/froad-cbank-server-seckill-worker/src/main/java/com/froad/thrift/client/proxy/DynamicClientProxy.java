package com.froad.thrift.client.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.froad.thrift.client.protocol.BaseProtocol;

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
	
	private static final Logger log = LoggerFactory.getLogger(DynamicClientProxy.class);
	
	public Object createProxy(Class<T> ts, String host, String serverName) {
		this.ts = ts;
		this.host = host;
		this.serverName = serverName;
		return Proxy.newProxyInstance(ts.getClassLoader(), ts.getInterfaces(), this);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


		TSocket tsocket = null;
		try {
			tsocket = new TSocket(host.split(":")[0], Integer.parseInt(host.split(":")[1]), 10000); 

			TProtocol protocol = BaseProtocol.getProtocol(tsocket, serverName);
			
			tsocket.open();
			
			Class[] argsClass = new Class[] { TProtocol.class };
			
			Constructor<T> cons = (Constructor<T>) ts.getConstructor(argsClass);
			
			T client = (T) cons.newInstance(protocol);
			
			if(args != null){
				for (int i = 0; i < args.length; i++) {
					log.info(new StringBuffer("thrift request arg.") 
						.append(i).append("=").append((args[i] == null ? "null" : args[i].toString())).toString());
				}
			}
			
			return method.invoke(client, args);

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
		
	}

}
