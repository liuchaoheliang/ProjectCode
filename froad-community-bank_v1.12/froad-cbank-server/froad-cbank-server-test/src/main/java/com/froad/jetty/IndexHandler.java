package com.froad.jetty;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.froad.logback.LogCvt;

public class IndexHandler extends AbstractHandler {

	@Override
	  public void handle(String target, Request baseRequest,
	      HttpServletRequest request, HttpServletResponse response)
	      throws IOException, ServletException {

		LogCvt.info("IndexHandler");
		
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html;charset=UTF-8");

	    PrintWriter out = response.getWriter();
	    out.write("<h3>Hello World Jetty!</h3>");
	    out.write(Calendar.getInstance().getTime().toString());
	    out.flush();
	    out.close();
	  }

}
