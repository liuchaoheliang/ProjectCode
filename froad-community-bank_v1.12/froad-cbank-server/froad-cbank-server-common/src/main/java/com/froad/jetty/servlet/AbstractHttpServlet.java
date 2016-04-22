package com.froad.jetty.servlet;

import org.eclipse.jetty.servlet.DefaultServlet;

/**
 * 基类
 * 
 * @author wangzhangxu
 * @date 2015年4月20日 下午1:08:19
 * @version v1.0
 */
public abstract class AbstractHttpServlet extends DefaultServlet {
	
	private static final long serialVersionUID = 4804844218618253942L;
	
	
	public String errorCodeMsg(String code, String msg){
		return "{\"code\":\"" + code + "\", \"msg\":\"" + msg + "\"}";
	}
	
	
}
