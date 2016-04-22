package com.froad.util;

/**
 * @ClassName: ActiveConstants
 * @Description: 常量类
 * @author froad-shenshaocheng 2015年11月11日
 * @modify froad-shenshaocheng 2015年11月11日
 */
public class ActiveConstants {

	// 常量
	/**
	 * double int之间转换的倍数
	 */
	public static final int DOUBLE_INTGER_CHANGE = 1000;

	public static final String NORMAL_RESPONSE = "0000";

	public static final String ABNORMAL_RESPONSE = "9999";

	public static final String SUPPORT = "support";

	/**
	 * goods的thrift.properties配置文件名称
	 */
	public static final String THRIFT_PROPERTIES = "active.properties";

	// thrift配置文件(凑单用到，需要调用门店的排序功能，改功能由商品模块维护)
	/**
	 * 调用商品商户门店服务的IP
	 */
//	public static final String THRIFT_MERCHANT_HOST = ActivePropertiesUtil
//			.getThriftProperty().getProperty("thrift.merchant.host");
	/**
	 * 调用商品商户门店服务的端口
	 */
//	public static final String THRIFT_MERCHANT_PORT = ActivePropertiesUtil
//			.getThriftProperty().getProperty("thrift.merchant.port");

	/**
	 * 下载红包券码每个sheet有多少行数 默认5万
	 */
	public static final String VOUCHERS_DOWN_ROWNUM = ActivePropertiesUtil
			.getActiveProperties().getProperty("vouchers.down.rownum");

	
	/**
	 * 每次查询2万条
	 */	
	public static final String VOUCHERS_DOWN_SEARCH_COUNT = ActivePropertiesUtil
			.getActiveProperties().getProperty("vouchers.down.search.count");
	
	/**
	 * 调用订单服务的IP
	 */
	public static final String THRIFT_ORDER_HOST = ActivePropertiesUtil
			.getActiveProperties().getProperty("thrift.order.host");
	/**
	 * 调用订单服务的端口
	 */
	public static final String THRIFT_ORDER_PORT = ActivePropertiesUtil
			.getActiveProperties().getProperty("thrift.order.port");
	
}
