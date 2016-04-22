package com.froad.cbank.coremodule.merchant.expand.thrift.proxy;



public class DynamicClientProxyFactory {
	
	/**
	 * 动态代理
	 * @tilte createIface
	 * @author zxl
	 * @date 2015年3月25日 下午1:32:44
	 * @param clazzIfaceName 代理类
	 * @param host Thrift连接
	 * @param serverName Thrift服务名
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object createIface(String clazzIfaceName, String host, String serverName) {

		try {
			
			String clazzClientName = clazzIfaceName + "$Client";
			
			Class clientClazz = Class.forName(clazzClientName);
			
			DynamicClientProxy proxy = new DynamicClientProxy();
			
			return proxy.createProxy(clientClazz, host, serverName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
}
