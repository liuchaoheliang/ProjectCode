package com.froad.util.active;

import com.froad.util.PropertiesUtil;

/**
 * 活动模块 常量值维护
 * Description : TODO<br/>
 * Project Name : froad-cbank-server-order<br/>
 * File Name : ActiveConst.java<br/>
 * 
 * Date : 2015年11月11日 上午11:39:24 <br/> 
 * @author KaiweiXiang
 * @version
 */
public class ActiveConst {
	/**
	 * thrift.properties
	 */
	private static final String THRIFT_PROPERTIES_FILE = "thrift";
	
	/**
	 * properties host key
	 */
	private static final String PROPERTIES_ACTIVE_HOST_KEY = "thrift.active.host";
	
	/**
	 * properties port key
	 */
	private static final String PROPERTIES_ACTIVE_PORT_KEY = "thrift.active.port";
	
	/**
	 * 活动模块，thrift请求的ip和端口
	 */
	public final static String ACTIVE_HOST = PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_ACTIVE_HOST_KEY).trim();
	public final static int ACTIVE_PORT = Integer.valueOf(PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_ACTIVE_PORT_KEY).trim());
	
	
	/**
	 * 请求成功代码
	 */
	public final static String SUCCESS_CODE = "0000";
	
	/**
	 * 该订单参加营销活动的标识（记录在大订单上）
	 */
	public static final String IS_ACTIVE_TRUE = "1";
}
