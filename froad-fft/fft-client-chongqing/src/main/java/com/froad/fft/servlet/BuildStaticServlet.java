package com.froad.fft.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.froad.fft.enums.BuildType;
import com.froad.fft.support.expand.StaticSupport;
import com.froad.fft.util.SpringUtil;

/**
 * 执行静态化
 * @author FQ
 *
 */
public class BuildStaticServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(BuildStaticServlet.class);
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("============接收到指令执行静态化===========");
		
		String ip=request.getRemoteHost();
		logger.info("IP："+ip);
		
		request.setCharacterEncoding("UTF-8");
		String buildType=request.getParameter("buildType");//生成类型
		
		if(buildType == null){
			logger.error("静态化生成类型为空");
			return;
		}
		
		int buildCount = 0;
		
		StaticSupport staticsupport=SpringUtil.getBean("staticSupportImpl", StaticSupport.class);
		
		//首页
		if(BuildType.valueOf(buildType) == BuildType.index){
			buildCount=staticsupport.buildIndex();
		}
		else if(BuildType.valueOf(buildType) == BuildType.site_map){
			buildCount=staticsupport.buildSitemap();
		}
		else if(BuildType.valueOf(buildType) == BuildType.other){
			buildCount=staticsupport.buildOther();
		}
		logger.info("执行静态化："+ buildType + " buildCount："+buildCount);
		
		response.getWriter().write("success");
	}

}
