package com.froad.fft.thirdparty.response.froadmall;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * *******************************************************
 * <p>工程: fft-server</p>
 * <p> 类名: OnlineRechargeServlet.java</p>
 * <p>描述: *-- <b>用于接收方付通商城话费通知</b> --*</p>
 * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn</p>
 * <p>时间: 2014年1月20日 下午3:09:31</p>
 ******************************************************** 
 */
public class OnlineRechargeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(OnlineRechargeServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		logger.info("接收到FroadMall的<<话费充值>>通知");
		req.setCharacterEncoding("UTF-8");
		String spid = req.getParameter("SPID");// 分分通传递的订单号
		String respCode = req.getParameter("respCode");// 响应码
		String respMsg = req.getParameter("respMsg");// 响应消息
		logger.info("SPID: " + spid + "|respCode: " + respCode + "|respMsg: " + respMsg);

	}

}
