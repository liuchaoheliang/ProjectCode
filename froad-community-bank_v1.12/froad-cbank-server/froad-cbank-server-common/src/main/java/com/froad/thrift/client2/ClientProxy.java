package com.froad.thrift.client2;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.logback.LogCvt;

/**
 * Thrift客户端代理类
 * 
 * @author wangzhangxu
 * @date 2015年6月13日 下午2:45:30
 */
public class ClientProxy<T> implements InvocationHandler {
	
	private Class<T> clientClazz;
	
	private TSocket socket;
	
	public Object createProxy(Class<T> clientClazz, TSocket socket) {
		this.clientClazz = clientClazz;
		this.socket = socket;
		return Proxy.newProxyInstance(
				clientClazz.getClassLoader(), 
				clientClazz.getInterfaces(),
				this);
	}
	
	@SuppressWarnings({ "rawtypes", "unused"})
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		try {
			
			TProtocol protocol = new TMultiplexedProtocol(
					new TBinaryProtocol(socket), 
					getServiceName(clientClazz));
			
			Class[] argsClass = new Class[]{TProtocol.class};
			
			Constructor<T> cons = (Constructor<T>) clientClazz.getConstructor(argsClass);
			
			T client = (T) cons.newInstance(protocol);
			
			StringBuffer argsBuf = new StringBuffer(103);
			for (int i = 0; args != null && i < args.length; i++) {
				if (i != 0) {
					argsBuf.append(", ");
				}
				argsBuf.append(i).append(": ").append(args[i].toString());
			}
			LogCvt.debug(argsBuf.toString());
			
			return method.invoke(client, args);
			
		} catch (InvocationTargetException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} catch (Throwable e) {
			throw e;
		} 
	}
	
	/**
	 * 获取Service名字
	 * <br/>
	 * Example: com.example.DemoService.Iface.class --> "DemoService"
	 * 
	 * @param ifaceClazz Iface Class Object
	 * 
	 * @return String
	 */
	@SuppressWarnings({"rawtypes"})
	private String getServiceName(Class ifaceClazz) {
		String str = ifaceClazz.getName();
		int i = str.lastIndexOf(".");
		if (i >= 0) {
			str = str.substring(i + 1); // 去掉package
		}
		
		str = str.replace("$Client", ""); // 去掉$Client
		return str;
	}

	public Class<T> getClientClazz() {
		return clientClazz;
	}

	public void setClientClazz(Class<T> clientClazz) {
		this.clientClazz = clientClazz;
	}

	public TSocket getSocket() {
		return socket;
	}

	public void setSocket(TSocket socket) {
		this.socket = socket;
	}
	
	
}
