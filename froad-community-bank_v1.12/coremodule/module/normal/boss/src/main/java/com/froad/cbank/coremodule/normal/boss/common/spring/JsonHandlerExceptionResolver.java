package com.froad.cbank.coremodule.normal.boss.common.spring;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


/**
 * 错误拦截，返回json格式数据
 * @ClassName JsonHandlerExceptionResolver
 * @author zxl
 * @date 2015年4月23日 上午11:07:03
 */
public class JsonHandlerExceptionResolver implements HandlerExceptionResolver, Ordered{
	
	private Properties exceptionMappings;
	
	private int order = Ordered.LOWEST_PRECEDENCE;
	
	private Log logger = LogFactory.getLog(getClass());
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		try{
			logger.error("异常拦截:"+ex.getMessage(), ex);
			String msg = findMatchingMsg(this.exceptionMappings, ex);
			if(msg != null){
				String[] m = msg.split(",");
				String error = "";
				if(m.length>1){
					error = m[1];
				}else{
					error = ex.getMessage();
				}
				int status = Integer.parseInt(m[0]);
				
				responeJson(response,status,error);
			}	
		}catch (Exception e){
		}
		return new ModelAndView();
	}
	
	/**
	 * 错误信息返回
	 * @tilte responeJson
	 * @author zxl
	 * @date 2015年4月23日 上午11:38:47
	 * @param response
	 * @param status
	 * @param msg
	 * @throws IOException 
	 */
	protected void responeJson(HttpServletResponse response,int status,String msg) throws IOException{
		response.setContentType("application/json");
		response.setStatus(status);
		response.getWriter().write("{\"code\":\"500\",\"message\":\""+msg+"\"}");
		response.flushBuffer();
	}
	
	/**
	 * 错误匹配
	 * @tilte findMatchingMsg
	 * @author zxl
	 * @date 2015年4月23日 上午11:39:17
	 * @param exceptionMappings
	 * @param ex
	 * @return
	 */
	protected String findMatchingMsg(Properties exceptionMappings, Exception ex) {
		for (Enumeration<?> names = exceptionMappings.propertyNames(); names.hasMoreElements();) {
			String exceptionMapping = (String) names.nextElement();
			
			if (exceptionMapping.contains(ex.getClass().getName())) {
				return exceptionMappings.getProperty(exceptionMapping);
			}
		}
		return exceptionMappings.getProperty(Exception.class.getName());
	}
	
	public Properties getExceptionMappings() {
		return exceptionMappings;
	}

	public void setExceptionMappings(Properties exceptionMappings) {
		this.exceptionMappings = exceptionMappings;
	}
	
	
	
	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int getOrder() {
		return order;
	}
	
}
