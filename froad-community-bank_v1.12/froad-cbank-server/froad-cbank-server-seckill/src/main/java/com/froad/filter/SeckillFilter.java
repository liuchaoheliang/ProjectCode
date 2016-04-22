package com.froad.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.froad.enums.H5ResultCode;
import com.froad.jetty.vo.ResponseVo;
import com.froad.logback.LogCvt;
import com.froad.util.ServletResultUtil;

/**
 * 秒杀系统过滤器
 * 
 * @author wangzhangxu
 * @date 2015年4月29日 下午5:37:19
 * @version v1.0
 */
public class SeckillFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String path = request.getRequestURL().toString();
		
		if (StringUtils.isEmpty(path) || path.endsWith(".jsp")) {
			// LogCvt.debug("忽略请求: " + path);
			
		} else {
			LogCvt.debug("Request URL: " + path + ", Method: " + request.getMethod());
			
			String referer = request.getHeader("Referer");
			LogCvt.debug("请求来源[Referer]: " + referer);
			if (referer == null || "".equals(referer)) {
				ServletResultUtil.render(new ResponseVo(H5ResultCode.missingParam), request, response);
				return;
			}
			
			String[] client = referer.split("/");
			if (client.length < 4 && (client[3] == null || "".equals(client[3]))) {
				ServletResultUtil.render(new ResponseVo(H5ResultCode.missingParam), request, response);
				return;
			}
			
			request.setAttribute("clientId", client[3]);
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
