package com.froad.thrift.monitor;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.logback.LogCvt;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.monitor.service.BizMonitorService;
import com.froad.thrift.monitor.vo.BizMethodInfo;

/**
 * 服务功能动态代理类
 * 
 * @ClassName: BizMonitor
 * @Description: TODO
 * @author FQ
 * @date 2015年4月8日 上午11:45:51
 *
 */
public class BizMonitor {

	private ServiceMonitorInfo serviceInfo = new ServiceMonitorInfo();
	private BaseServer server;

	public BizMonitor(BaseServer server) {
		this.server = server;
	}

	/**
	 * 生成代理类
	 * 
	 * @param service
	 * @return
	 */
	public Object wrapper(Object service) {
		BizMonitorBaseService bizMonitorBaseService = (BizMonitorBaseService) service;
		bizMonitorBaseService.setServiceInfo(serviceInfo);
		bizMonitorBaseService.setServer(server);
		try {
			registerServiceInfo(service,bizMonitorBaseService.getName());
		} catch (TException e) {
			e.printStackTrace();
		}
		return Proxy.newProxyInstance(service.getClass().getClassLoader(),
				service.getClass().getInterfaces(), new BizMonitorProxyHandler(
						service));
	}

	/**
	 * 注册服务方法信息，主要是是业务方法，便于后面监控
	 * 
	 * @param service
	 */
	private void registerServiceInfo(Object service,String serviceName) {
		Class<?>[] interfaces = service.getClass().getInterfaces();
		for (int i = 0; i < interfaces.length; i++) {
			Class<?> iface = interfaces[i];
			Method[] methods = iface.getMethods();
			for (Method m : methods) {
				if (isBizMonitorServiceIfaceMethod(m))
					continue;
				String methodName = m.getName();
				Class<?>[] type = m.getParameterTypes();
				BizMethodInfo biz = new BizMethodInfo();
				biz.setName(methodName);
				biz.setArgsNum((byte) type.length);
				biz.setArgsType(getArgsType(type));
				LogCvt.debug(new StringBuffer(
						"BizMonitor-opt ").append(serviceName).append(" RSI=").append(
						biz.toString()).toString());
				serviceInfo.addServiceBizMethod(biz);
			}
		}
	}

	private List<String> getArgsType(Class<?>[] types) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < types.length; i++) {
			list.add(types[i].getName());
		}
		return list;
	}

	private Class<BizMonitorService.Iface> iface = BizMonitorService.Iface.class;

	private boolean isBizMonitorServiceIfaceMethod(Method m) {
		Method[] methods = iface.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].equals(m))
				return true;
		}
		return false;
	}
}
