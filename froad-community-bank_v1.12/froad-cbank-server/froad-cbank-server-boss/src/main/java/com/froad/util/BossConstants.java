package com.froad.util;

/**
 * boss管理平台需要用到的常量类
 * @author wangyan
 *
 */
public class BossConstants {

    /**
     * goods的thrift.properties配置文件名称
     */
    public static final String THRIFT_PROPERTIES= "thrift.properties";
    
    /**
     * 二维码模块thrift服务的IP
     */
    public static final String THRIFT_QRCODE_HOST = BossPropertiesUtil.getThriftProperty().getProperty("thrift.qrcode.host");
    /**
     * 二维码模块thrift服务的端口
     */
    public static final String THRIFT_QRCODE_PORT = BossPropertiesUtil.getThriftProperty().getProperty("thrift.qrcode.port");
	
    public static final String THRIFT_ORDER_HOST = BossPropertiesUtil.getThriftProperty().getProperty("thrift.order.host");
    
    public static final String THRIFT_ORDER_PORT = BossPropertiesUtil.getThriftProperty().getProperty("thrift.order.port");
}
