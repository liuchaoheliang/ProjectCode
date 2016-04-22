package com.froad.fft.thirdparty.response.points;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.froad.fft.thirdparty.dto.response.points.CashPointsInfo;
import com.froad.fft.thirdparty.util.HttpReqBodyToStrUtil;
import com.froad.fft.util.XmlBeanUtil;

public class ApplyForCashServlet extends HttpServlet{

	private static final long serialVersionUID = -9201050494382450432L;

	private static Logger logger = Logger.getLogger(ApplyForCashServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {	
		logger.info("接收到Points的<<提现申请>>通知");
		
		String noticeXML = HttpReqBodyToStrUtil.toXMLStr(req);			
		logger.info("通知报文:" + noticeXML);
		CashPointsInfo cashPointsInfo = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("cashPointsInfo", CashPointsInfo.class);
			cashPointsInfo = (CashPointsInfo) XmlBeanUtil.xmlToBean(xmlToBeanMap, noticeXML);		
		} catch (Exception e) {
			logger.error("解析通知XML时异常", e);
		}
	}
	
}
