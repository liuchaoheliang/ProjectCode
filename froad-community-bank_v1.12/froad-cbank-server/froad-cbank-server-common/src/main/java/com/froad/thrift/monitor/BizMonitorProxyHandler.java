package com.froad.thrift.monitor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

import com.froad.logback.LogCvt;
import com.froad.thrift.monitor.service.BizMonitorBaseService;

/**
 * 服务功能动态代理处理类
 * 
 * @ClassName: BizMonitorProxyHandler
 * @Description: TODO
 * @author FQ
 * @date 2015年3月28日 上午10:42:40
 *
 */
public class BizMonitorProxyHandler implements InvocationHandler {

	private Object service;

	protected BizMonitorProxyHandler(Object service) {
		this.service = service;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
//		String uuid = UUID.randomUUID().toString().toUpperCase();
////		Thread.currentThread().setName(uuid.replace("-", "").toUpperCase()); // 修改线程名  32位
////		Thread.currentThread().setName(new StringBuilder().append(uuid.substring(0, 8)).append(uuid.substring(9, 13)).append(uuid.substring(14, 18)).append(uuid.substring(19, 23)).append(uuid.substring(24)).toString()); // 修改线程名  32位
//		Thread.currentThread().setName(new StringBuilder().append(uuid.substring(0, 8)).append(uuid.substring(9, 13)).append(uuid.substring(14, 18)).toString()); // 修改线程名 16位
		BizMonitorBaseService bizMonitorBaseService = (BizMonitorBaseService) service;
		ServiceMonitorInfo serviceInfo = bizMonitorBaseService.getServiceInfo();
		LogCvt.debug(new StringBuffer("BizMonitor-opt Call ")
		.append(bizMonitorBaseService.getName()).append(".")
		.append(method.getName()).append(" start...")
		.toString());
		if (args != null)
			for (int i = 0; i < args.length; i++) {
				LogCvt.debug(new StringBuffer("BizMonitor-opt ")
//						.append("Service")
						.append(bizMonitorBaseService.getName()).append(".")
						.append(method.getName())
						.append(" arg.")
						.append(i)
						.append("=")
						.append((args[i] == null ? "null" : args[i].toString()))
						.toString());
			}
		long startTime = System.currentTimeMillis();
		Object result = null;
		try {
			result = method.invoke(this.service, args);
		} catch (Exception e) {
			if (serviceInfo.isBizMethod(method.getName()))
				serviceInfo.updateBizMethodInvokeInfo(method.getName(), false,
						0);
			throw e;
		}
		long endTime = System.currentTimeMillis();
		if (serviceInfo.isBizMethod(method.getName())) {
			serviceInfo.updateBizMethodInvokeInfo(method.getName(), true,
					(endTime - startTime));

			LogCvt.debug(new StringBuffer("BizMonitor-opt Call ")
					.append(bizMonitorBaseService.getName()).append(".")
					.append(method.getName()).append(" cost time=")
					.append((endTime - startTime)).append("ms").toString());
		}

		LogCvt.debug(new StringBuffer("BizMonitor-opt Call ")
				.append(bizMonitorBaseService.getName()).append(".")
				.append(method.getName()).append(" result=")
				.append((result == null ? "null" : result.toString()))
				.append(System.getProperty("line.separator"))
				.toString());
		return result;
	}

}
