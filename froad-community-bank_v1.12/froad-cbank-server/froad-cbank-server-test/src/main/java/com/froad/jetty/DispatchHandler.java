package com.froad.jetty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class DispatchHandler extends AbstractHandler {

	public void handle(String target, Request baseRequest,
		      HttpServletRequest request, HttpServletResponse response)
		      throws IOException, ServletException {

		    /**
		     * 这里将是根据不同的请求来分发给不同的Handler来处理
		     */
		    if (target.equals("/index")) {
		      new IndexHandler().handle(target, baseRequest, request, response);
		    } else {
		      new HelloHandler().handle(target, baseRequest, request, response);
		    }
		  }

}
