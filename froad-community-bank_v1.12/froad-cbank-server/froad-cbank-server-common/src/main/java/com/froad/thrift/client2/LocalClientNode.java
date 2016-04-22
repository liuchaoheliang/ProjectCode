package com.froad.thrift.client2;

import java.util.HashMap;
import java.util.Map;


/**
 * 本地客户端节点
 * 
 * @author wangzhangxu
 * @date 2015年6月13日 下午9:11:54
 * @version v1.0
 */
public class LocalClientNode {
	
	private Map<String, String> propertiesMap = new HashMap<String, String>();
	
	private String host;
	
	private int port;
	
	private int connectTimeout = 0;
	
	public static final String MAX_ACTIVE = "maxActive";
	public static final String MAX_WAIT = "maxWait";
	public static final String MAX_IDLE = "maxIdle";
	
	public LocalClientNode(){}
	
	public Map<String, String> getPropertiesMap() {
		return propertiesMap;
	}

	public void setPropertiesMap(Map<String, String> propertiesMap) {
		this.propertiesMap = propertiesMap;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	
	public String getServerKey(){
		return host + ":" + port;
	}
	
}
