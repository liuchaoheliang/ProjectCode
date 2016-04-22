package com.froad.service;

import java.util.List;

/**
 *  消息队列处理接口
 * @author zjz
 *
 */
public interface ProcessService {

	/**
	 *  处理消息队列 
	 * @param msg
	 */
	public abstract void processMsg(String msg);
	
	/**
	 *  处理多个队列消息
	 * @param msg
	 */
	public abstract void processMsg(List<String> msgs);
}
