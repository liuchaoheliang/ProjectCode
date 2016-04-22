package com.froad.fft.thirdparty.response.froadmall;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class LotteryWinServlet extends HttpServlet{

	private static final long serialVersionUID = 4341286491644604044L;

	private static Logger logger = Logger.getLogger(LotteryWinServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("接收到FroadMall的<<彩票中奖|派奖>>通知");
		
		req.setCharacterEncoding("UTF-8");		
		String tranID=req.getParameter("tranID");//第三方彩票订单号
		String awdAmount = req.getParameter("awdAmount");// 中奖金额
		String prizeGrade = req.getParameter("prizeGrade");// 中奖等级
		String prizeCount = req.getParameter("prizeCount");// 中奖注数
		String status=req.getParameter("status");//0：中奖  00：已派奖
		
		logger.info("transID: "+tranID+"|awdAmout: "+awdAmount+"|prizeGrade: "+prizeGrade+"|prizeCount: "+prizeCount+"|status: "+status);
	}
}
