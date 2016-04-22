package com.froad.fft.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.froad.fft.service.impl.TransServiceImpl;
import com.froad.fft.util.SpringUtil;


	/**
	 * 类描述：手动触发自然成团的后续处理
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年4月16日 下午1:57:52 
	 */
public class AutoClusterServlet extends HttpServlet {

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(AutoClusterServlet.class);

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("==========触发自然成团的处理============");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String key=request.getParameter("key");
		if(!"ee75cf7c098e4aa58481353b97507aba".equals(key)){
			response.getWriter().write("请输入正确的密钥！");
			return;
		}
		TransServiceImpl transService=(TransServiceImpl)SpringUtil.getBean("transServiceImpl");
		transService.cluster();
		response.getWriter().write("操作成功！");
	}
}
