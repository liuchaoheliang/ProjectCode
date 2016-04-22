package com.froad.thrift.client;

import com.froad.thrift.client.proxy.DynamicClientProxyFactory;
import com.froad.thrift.service.SeckillOrderService;
import com.froad.util.PropertiesUtil;

/**
 * Thrift服务类
 * 
 * @author wangzhangxu
 * @date 2015年5月3日 下午6:33:52
 * @version v1.0
 */
public class ThriftService {
	
	static final String THRIFT_SERVICE_FILENAME_KEY = "thrift";
	static final String THRIFT_SERVICE_ORDER_HOST_KEY = "seckill.thrift.order.host";
	
	private static SeckillOrderService.Iface seckillOrderService;
	
	public static SeckillOrderService.Iface getSeckillOrderService() {
		if (seckillOrderService == null) {
			String thriftHost = PropertiesUtil.getProperty(THRIFT_SERVICE_FILENAME_KEY, THRIFT_SERVICE_ORDER_HOST_KEY);
			seckillOrderService = (SeckillOrderService.Iface) DynamicClientProxyFactory.createIface(SeckillOrderService.class.getName(), thriftHost, SeckillOrderService.class.getSimpleName());
		}
		return seckillOrderService;
	}
	
}
