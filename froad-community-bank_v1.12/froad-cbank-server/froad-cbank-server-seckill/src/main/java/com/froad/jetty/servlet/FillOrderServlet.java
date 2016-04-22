package com.froad.jetty.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.froad.enums.H5ResultCode;
import com.froad.jetty.service.OrderService;
import com.froad.jetty.service.SupportService;
import com.froad.jetty.service.impl.OrderServiceImpl;
import com.froad.jetty.service.impl.SupportServiceImpl;
import com.froad.jetty.servlet.AbstractHttpServlet;
import com.froad.jetty.vo.FillOrderRequestVo;
import com.froad.jetty.vo.ResponseVo;
import com.froad.logback.LogCvt;
import com.froad.util.JSonUtil;
import com.froad.util.ServletResultUtil;

/**
 * 秒杀订单信息填充接口服务
 * 
 * @author wangzhangxu
 * @date 2015年4月15日 下午6:55:42
 * @version v1.0
 */
public class FillOrderServlet extends AbstractHttpServlet {
	
	private static final long serialVersionUID = -7670245609933766335L;
	
	private OrderService orderService;
	
	private SupportService supportService;
	
	/**
	 * 
	 */
	public FillOrderServlet() {
		super();
	}
	
	/**
	 * 初始化
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		orderService = new OrderServiceImpl();
		
		supportService = new SupportServiceImpl();
	}

	/**
	 * 填充订单请求处理
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FillOrderRequestVo reqVo = initParameters(request);
		ResponseVo responseVo = validParameters(reqVo);
		if (!responseVo.success()) {
			ServletResultUtil.render(responseVo, request, response);
			return;
		}
		
		// 验证用户登录
		responseVo = supportService.checkToken(reqVo.getMemberCode(), reqVo.getToken());
		if (!responseVo.success()) {
			ServletResultUtil.render(responseVo, request, response);
			return;
		}
		
		responseVo = orderService.fillOrder(reqVo);
		ServletResultUtil.render(responseVo, request, response);
	}
	
	public FillOrderRequestVo initParameters(HttpServletRequest request){
		FillOrderRequestVo reqVo = new FillOrderRequestVo();
		try {
			String json = IOUtils.toString(request.getInputStream());
			reqVo = JSONObject.parseObject(json, new TypeReference<FillOrderRequestVo>(){});
		} catch (IOException e) {
			LogCvt.error("填充订单接口解析请求数据异常", e);
		}
		
		Long memberCode = null;
		try{
			memberCode = Long.parseLong(request.getHeader("memberCode"));
		}catch(Exception e){}
		reqVo.setMemberCode(memberCode);
		reqVo.setToken(request.getHeader("token"));
		
		reqVo.setClientId((String)request.getAttribute("clientId"));
		// setters end
		
		LogCvt.debug("填充订单接口请求参数：" + JSonUtil.toJSonString(reqVo));
		
		return reqVo;
	}
	
	public ResponseVo validParameters(FillOrderRequestVo reqVo){
		boolean pass = true;
		// required
		if (reqVo.getMemberCode() == null 
				|| StringUtils.isEmpty(reqVo.getToken()) 
				|| StringUtils.isEmpty(reqVo.getClientId()) 
				|| StringUtils.isEmpty(reqVo.getOrderId()) 
				|| StringUtils.isEmpty(reqVo.getRecvId())) {
			pass = false;
		}
		
		ResponseVo resVo = null;
		if (pass) {
			resVo = new ResponseVo(H5ResultCode.success.getCode(), H5ResultCode.success.getMsg());
		} else {
			LogCvt.debug(H5ResultCode.missingParam.getMsg() + reqVo.toString());
			resVo = new ResponseVo(H5ResultCode.missingParam.getCode(), H5ResultCode.missingParam.getMsg());
		}
		return resVo;
	}
}
