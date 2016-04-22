package com.froad.jetty.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.froad.enums.H5ResultCode;
import com.froad.enums.MonitorPointEnum;
import com.froad.jetty.service.OrderService;
import com.froad.jetty.service.impl.OrderServiceImpl;
import com.froad.jetty.servlet.AbstractHttpServlet;
import com.froad.jetty.vo.QueryOrderResponseVo;
import com.froad.jetty.vo.ResponseVo;
import com.froad.logback.LogCvt;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.util.ServletResultUtil;

/**
 * 秒杀查询订单接口服务
 * 
 * @author wangzhangxu
 * @date 2015年4月15日 下午6:55:42
 * @version v1.0
 */
public class QueryOrderServlet extends AbstractHttpServlet {
	
	private static final long serialVersionUID = -7670245609933766335L;
	
	private OrderService orderService;
	
	private MonitorService monitorService;
	
	
	/**
	 * 
	 */
	public QueryOrderServlet() {
		super();
	}

	/**
	 * 初始化
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		orderService = new OrderServiceImpl();
		monitorService = new MonitorManager();
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		long start = System.currentTimeMillis();
		String acceptOrderId = request.getParameter("acceptOrderId");
		
		if (StringUtils.isEmpty(acceptOrderId)) {
			LogCvt.debug(H5ResultCode.missingParam.getMsg() + "[acceptOrderId=" + acceptOrderId + "]");
			ServletResultUtil.render(new ResponseVo(H5ResultCode.missingParam), request, response);
			return;
		}
		
		// 统计轮询流量
		monitorService.send(MonitorPointEnum.Seckill_Query_Order_Count);
		
		QueryOrderResponseVo qoResVo = orderService.queryOrder(acceptOrderId);
		if (qoResVo == null) {
			ServletResultUtil.render(new ResponseVo(H5ResultCode.failed), request, response);
		} else {
			String data = JSONObject.toJSONString(qoResVo);
			ServletResultUtil.render(data, request, response);
		}
		
		long end = System.currentTimeMillis();
		
		// 统计轮询耗时
		monitorService.send(MonitorPointEnum.Seckill_Query_Order_Timeout, "" + (end - start));
	}
	
}
