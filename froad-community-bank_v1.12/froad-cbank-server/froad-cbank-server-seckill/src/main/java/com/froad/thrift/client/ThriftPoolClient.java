package com.froad.thrift.client;

import java.util.HashMap;
import java.util.Map;

import com.froad.thrift.client2.LocalClientNode;
import com.froad.thrift.client2.ThriftConnectionPoolFactory;
import com.froad.thrift.service.MemberInformationService;
import com.froad.thrift.service.MemberSecurityService;
import com.froad.thrift.service.PaymentService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.service.SeckillOrderService;
import com.froad.thrift.service.SmsMessageService;
import com.froad.util.PropertiesUtil;

/**
 * Thrift连接池客户端
 * 
 * @author wangzhangxu
 * @date 2015年5月3日 下午6:33:52
 * @version v1.0
 */
public class ThriftPoolClient {
	
	static final String THRIFT_SERVICE_FILENAME_KEY = "thrift";
	static final String THRIFT_SERVICE_SUPPORT_HOST_KEY = "seckill.thrift.support.host";
	static final String THRIFT_SERVICE_GOODS_HOST_KEY = "seckill.thrift.goods.host";
	static final String THRIFT_SERVICE_ORDER_HOST_KEY = "seckill.thrift.order.host";
	
	static final String THRIFT_conTimeOut_KEY = "thrift.conTimeOut";
	static final String THRIFT_maxActive_KEY = "thrift.borrow.waitTimeOut";
	static final String THRIFT_maxWait_KEY = "thrift.pool.maxActives";
	static final String THRIFT_maxIdle_KEY = "thrift.pool.maxIdles";
	
	public static void initPool(){
		ThriftConnectionPoolFactory connectionFactory = ThriftConnectionPoolFactory.getInstance();
		
		Map<String, String> commonPropMap = new HashMap<String, String>();
		commonPropMap.put(LocalClientNode.MAX_ACTIVE, PropertiesUtil.getProperty(THRIFT_SERVICE_FILENAME_KEY, THRIFT_maxActive_KEY));
		commonPropMap.put(LocalClientNode.MAX_WAIT, PropertiesUtil.getProperty(THRIFT_SERVICE_FILENAME_KEY, THRIFT_maxWait_KEY));
		commonPropMap.put(LocalClientNode.MAX_IDLE, PropertiesUtil.getProperty(THRIFT_SERVICE_FILENAME_KEY, THRIFT_maxIdle_KEY));
		
		int timeout = 0;
		try {
			timeout = Integer.parseInt(PropertiesUtil.getProperty(THRIFT_SERVICE_FILENAME_KEY, THRIFT_conTimeOut_KEY));
		} catch (Exception e) {}
		LocalClientNode supportCN = new LocalClientNode();
		supportCN.getPropertiesMap().putAll(commonPropMap);
		String supportHostIp = PropertiesUtil.getProperty(THRIFT_SERVICE_FILENAME_KEY, THRIFT_SERVICE_SUPPORT_HOST_KEY);
		supportCN.setHost(supportHostIp.split(":")[0]);
		supportCN.setPort(Integer.parseInt(supportHostIp.split(":")[1]));
		supportCN.setConnectTimeout(timeout);
		connectionFactory.registerService(supportCN, SmsMessageService.Iface.class);
		
		LocalClientNode goodsCN = new LocalClientNode();
		goodsCN.getPropertiesMap().putAll(commonPropMap);
		String goodsHostIp = PropertiesUtil.getProperty(THRIFT_SERVICE_FILENAME_KEY, THRIFT_SERVICE_GOODS_HOST_KEY);
		goodsCN.setHost(goodsHostIp.split(":")[0]);
		goodsCN.setPort(Integer.parseInt(goodsHostIp.split(":")[1]));
		goodsCN.setConnectTimeout(timeout);
		connectionFactory.registerService(goodsCN, ProductService.Iface.class);
		
		LocalClientNode orderCN = new LocalClientNode();
		orderCN.getPropertiesMap().putAll(commonPropMap);
		String orderHostIp = PropertiesUtil.getProperty(THRIFT_SERVICE_FILENAME_KEY, THRIFT_SERVICE_ORDER_HOST_KEY);
		orderCN.setHost(orderHostIp.split(":")[0]);
		orderCN.setPort(Integer.parseInt(orderHostIp.split(":")[1]));
		orderCN.setConnectTimeout(timeout);
		connectionFactory.registerService(orderCN, SeckillOrderService.Iface.class);
		connectionFactory.registerService(orderCN, MemberSecurityService.Iface.class);
		connectionFactory.registerService(orderCN, MemberInformationService.Iface.class);
		connectionFactory.registerService(orderCN, PaymentService.Iface.class);
		
	}
	
}
