package com.froad.jetty.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.froad.util.ServletResultUtil;

/**
 * 秒杀服务器时间戳接口
 * 
 * @author wangzhangxu
 * @date 2015年4月27日 上午11:13:33
 * @version v1.0
 */
public class TimestampServlet extends AbstractHttpServlet {
	
	private static final long serialVersionUID = -230842814110148544L;
	
	/**
	 * 初始化
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String data = "{\"timestamp\":" + System.currentTimeMillis() + "}";
		ServletResultUtil.render(data, request, response);
	}
	
}
