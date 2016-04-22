package com.froad.cbank.coremodule.module.normal.merchant.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;

import com.alibaba.fastjson.JSON;

/**
 * http server
 * @author artPing
 *
 */
public class HttpServer {
	
	/**
	 * 说明   对server端http 请求过来的数据进行解析
	 * 创建日期  2015年12月7日  下午3:22:46
	 * 作者  artPing
	 * 参数  @param request
	 * 参数  @return
	 * 参数  @throws IOException
	 * 参数  @throws UnsupportedEncodingException
	 */
	public static String receivePost(HttpServletRequest request)throws IOException, UnsupportedEncodingException {
		// 读取请求内容
		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		// 将资料解码
		String reqBody = sb.toString();
		return URLDecoder.decode(reqBody, HTTP.UTF_8);
	}
	
	/**
	 * 说明   返回server
	 * 创建日期  2015年12月7日  下午5:12:05
	 * 作者  artPing
	 * 参数  @param response
	 * 参数  @param data
	 * 参数  @throws Exception
	 */
	public static void sendResponseStr(HttpServletResponse response,  String data){
		try {
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(data);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String error(String code,String msg){
		Map<String,String> map=new HashMap<String,String>();
		map.put("code", code);
		map.put("msg", msg);
		return JSON.toJSONString(map);
	}
}
