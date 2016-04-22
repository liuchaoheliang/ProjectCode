package com.froad.thrift.client;

import com.froad.thrift.client.proxy.DynamicClientProxyFactory;
import com.froad.thrift.service.MemberInformationService;
import com.froad.thrift.service.MemberSecurityService;
import com.froad.thrift.service.PaymentService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.service.SeckillOrderService;
import com.froad.thrift.service.SmsMessageService;
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
	static final String THRIFT_SERVICE_SUPPORT_HOST_KEY = "seckill.thrift.support.host";
	static final String THRIFT_SERVICE_GOODS_HOST_KEY = "seckill.thrift.goods.host";
	static final String THRIFT_SERVICE_ORDER_HOST_KEY = "seckill.thrift.order.host";
	
	private static SmsMessageService.Iface smsMessageService;
	
	private static ProductService.Iface productService;
	
	private static SeckillOrderService.Iface seckillOrderService;
	
	private static MemberSecurityService.Iface memberSecurityService;
	
	private static MemberInformationService.Iface memberInformationService;
	
	private static PaymentService.Iface paymentService;
	
	public static SmsMessageService.Iface getSmsMessageService() {
		if (smsMessageService == null) {
			String thriftHost = PropertiesUtil.getProperty(THRIFT_SERVICE_FILENAME_KEY, THRIFT_SERVICE_SUPPORT_HOST_KEY);
			smsMessageService = (SmsMessageService.Iface) DynamicClientProxyFactory.createIface(SmsMessageService.class.getName(), thriftHost, SmsMessageService.class.getSimpleName());
		}
		return smsMessageService;
	}
	
	public static ProductService.Iface getProductService() {
		if (productService == null) {
			String thriftHost = PropertiesUtil.getProperty(THRIFT_SERVICE_FILENAME_KEY, THRIFT_SERVICE_GOODS_HOST_KEY);
			productService = (ProductService.Iface) DynamicClientProxyFactory.createIface(ProductService.class.getName(), thriftHost, ProductService.class.getSimpleName());
		}
		return productService;
	}
	
	public static SeckillOrderService.Iface getSeckillOrderService() {
		if (seckillOrderService == null) {
			String thriftHost = PropertiesUtil.getProperty(THRIFT_SERVICE_FILENAME_KEY, THRIFT_SERVICE_ORDER_HOST_KEY);
			seckillOrderService = (SeckillOrderService.Iface) DynamicClientProxyFactory.createIface(SeckillOrderService.class.getName(), thriftHost, SeckillOrderService.class.getSimpleName());
		}
		return seckillOrderService;
	}

	public static MemberSecurityService.Iface getMemberSecurityService() {
		if (memberSecurityService == null) {
			String thriftHost = PropertiesUtil.getProperty(THRIFT_SERVICE_FILENAME_KEY, THRIFT_SERVICE_ORDER_HOST_KEY);
			memberSecurityService = (MemberSecurityService.Iface) DynamicClientProxyFactory.createIface(MemberSecurityService.class.getName(), thriftHost, MemberSecurityService.class.getSimpleName());
		}
		return memberSecurityService;
	}

	public static MemberInformationService.Iface getMemberInformationService() {
		if (memberInformationService == null) {
			String thriftHost = PropertiesUtil.getProperty(THRIFT_SERVICE_FILENAME_KEY, THRIFT_SERVICE_ORDER_HOST_KEY);
			memberInformationService = (MemberInformationService.Iface) DynamicClientProxyFactory.createIface(MemberInformationService.class.getName(), thriftHost, MemberInformationService.class.getSimpleName());
		}
		return memberInformationService;
	}
	
	public static PaymentService.Iface getPaymentService() {
		if (paymentService == null) {
			String thriftHost = PropertiesUtil.getProperty(THRIFT_SERVICE_FILENAME_KEY, THRIFT_SERVICE_ORDER_HOST_KEY);
			paymentService = (PaymentService.Iface) DynamicClientProxyFactory.createIface(PaymentService.class.getName(), thriftHost, PaymentService.class.getSimpleName());
		}
		return paymentService;
	}
	
	
}
