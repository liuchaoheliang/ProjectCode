package com.froad.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ConfigServlet extends HttpServlet {
	
	 private static final long serialVersionUID = 5118794568550751611L;  

	/**
	 * Constructor of the object.
	 */
	public ConfigServlet() {
		super();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		  ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());  
		  BaseService.getInstance().init(ctx);  
		  super.init();  
	}

}
