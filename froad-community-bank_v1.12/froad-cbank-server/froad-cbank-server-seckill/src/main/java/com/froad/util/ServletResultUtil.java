package com.froad.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.froad.enums.H5ResultCode;
import com.froad.jetty.vo.ResponseVo;
import com.froad.logback.LogCvt;

/**
 * 结果工具类
 * 
 * @author wangzhangxu
 * @date 2015年4月16日 下午1:49:38
 * @version v1.0
 */
public class ServletResultUtil {
	
	/** 异常状态码 */
	public static int ERROR_STATUS = 608;
	
	/**
	 * 返回信息
	 */
	public static void render(String data, HttpServletRequest request, HttpServletResponse response) throws IOException{
		String callback = request.getParameter("callback");
		if (StringUtils.isNotEmpty(callback)) {
			data = callback + "(" + data + ")";
		}
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		LogCvt.debug("Response: " + data);
		out.println(data);
	    out.flush();
	}
	
	/**
	 * 返回信息
	 */
	public static void render(ResponseVo resVo, HttpServletRequest request, HttpServletResponse response) throws IOException{
		String data = JSonUtil.toJSonString(resVo);
		String callback = request.getParameter("callback");
		if (StringUtils.isNotEmpty(callback)) {
			data = callback + "(" + data + ")";
		}
		
		response.setContentType("text/html; charset=utf-8");
		if (!H5ResultCode.success.getCode().equals(resVo.getCode())) {
			response.setStatus(ERROR_STATUS);
		}
		PrintWriter out = response.getWriter();
		LogCvt.debug("Response: " + data);
		out.println(data);
	    out.flush();
	}
	
	/**
	 * 返回信息
	 */
	public static void renderWithCache(String data, HttpServletRequest request, HttpServletResponse response) throws IOException{
		String callback = request.getParameter("callback");
		if (StringUtils.isNotEmpty(callback)) {
			data = callback + "(" + data + ")";
		}
		
		response.setContentType("text/html; charset=utf-8");
		String maxage = PropertiesUtil.getProperty("seckill", "response.cache.maxage");
		response.setHeader("Cache-Control", "max-age=" + maxage);
		PrintWriter out = response.getWriter();
		LogCvt.debug("Response: " + data);
		out.println(data);
	    out.flush();
	}
	
	/**
	 * 返回信息
	 */
	public static void renderWithCache(ResponseVo resVo, HttpServletRequest request, HttpServletResponse response) throws IOException{
		String data = JSonUtil.toJSonString(resVo);
		String callback = request.getParameter("callback");
		if (StringUtils.isNotEmpty(callback)) {
			data = callback + "(" + data + ")";
		}
		
		if (!H5ResultCode.success.getCode().equals(resVo.getCode())) {
			response.setStatus(ERROR_STATUS);
		}
		
		response.setContentType("text/html; charset=utf-8");
		String maxage = PropertiesUtil.getProperty("seckill", "response.cache.maxage");
		response.setHeader("Cache-Control", "max-age=" + maxage);
		PrintWriter out = response.getWriter();
		LogCvt.debug("Response: " + data);
		out.println(data);
	    out.flush();
	}
}
