package com.froad.fft.extend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.remoting.caucho.HessianServiceExporter;

import com.alibaba.fastjson.JSON;

/**
 * hessian 认证
 * @author FQ
 *
 */
public class AuthHessianServiceExporter extends HessianServiceExporter{

	final static Logger logger = Logger.getLogger(AuthHessianServiceExporter.class);
	
	final static String PASS_IP = "127.0.0.1";
	
	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("Clint Connection IP:"+request.getLocalAddr());

		
		super.handleRequest(request, response);
	}
}
