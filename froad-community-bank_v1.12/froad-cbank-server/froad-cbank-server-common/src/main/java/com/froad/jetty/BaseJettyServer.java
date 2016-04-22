package com.froad.jetty;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务器启动基类，用于服务参数控制
 * 
 * @ClassName: BaseServer
 * @Description: TODO
 * @author FQ
 * @date 2015年3月28日 上午10:41:06
 *
 */
public abstract class BaseJettyServer {

	private final Map<String, String> options = new HashMap<String, String>();

	public void setOption(String key, String value) {
		this.options.put(key, value);
	}

	public Map<String, String> getOptions() {
		return this.options;
	}

	/**
	 * reload params
	 * 
	 * @param options
	 *            服务参数
	 */
	public abstract void reinitialize();

	/**
	 * 停止
	 */
	public abstract void stop();
}
