package com.froad.CB.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.froad.CB.common.SpringContextUtil;
import com.froad.CB.common.constant.PayCommand;
import com.froad.CB.po.bill.PayNotice;
import com.froad.CB.service.impl.TransServiceImpl;
import com.froad.util.RsaUtil;

/**
 * 类描述：仅适用于接收账单平台(代收|代扣)通知地址
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2012
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: Nov 8, 2012 6:14:40 PM
 */
public class BillInformServlet extends HttpServlet {

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(BillInformServlet.class);

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("==========收到账单平台的通知============");
		request.setCharacterEncoding("UTF-8");
		ServletInputStream inputStream = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream, "UTF-8"));
		String line = null;
		StringBuffer buffer = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		reader.close();
		String xml = buffer.toString();
		logger.info("账单平台的通知报文:" + xml);
		try {
			Document doc = DocumentHelper.parseText(xml);
			List<?> orderNodes = doc.selectNodes("/noticeFroadApi/order/*");
			List<?> payNodes = doc.selectNodes("/noticeFroadApi/pay/*");
			List<?> systemNodes = doc.selectNodes("/noticeFroadApi/system/*");
			Element element = null;
			String name = null;
			PayNotice bill = new PayNotice();
			for (int i = 0; i < orderNodes.size(); i++) {// 解出order子元素
				element = (Element) orderNodes.get(i);
				name = element.getName();
				if (name.equals("orderID")) {
					bill.setOrderID(element.getTextTrim());
				} else if (name.equals("orderAmount")) {
					bill.setOrderAmount(element.getTextTrim());
				} else if (name.equals("orderCurrency")) {
					bill.setOrderCurrency(element.getTextTrim());
				} else if (name.equals("orderRemark")) {
					bill.setOrderRemark(element.getTextTrim());
				} else if (name.equals("stateCode")) {
					bill.setStateCode(element.getTextTrim());
				} else if (name.equals("orderAcquiringTime")) {
					bill.setOrderAcquiringTime(element.getTextTrim());
				} else if (name.equals("orderCompleteTime")) {
					bill.setOrderCompleteTime(element.getTextTrim());
				} else {
					logger.error("未知的order子元素: " + name);
				}
			}
			for (int i = 0; i < payNodes.size(); i++) {// 解出pay子元素
				element = (Element) payNodes.get(i);
				name = element.getName();
				if (name.equals("payType")) {
					bill.setPayType(element.getTextTrim());
				} else if (name.equals("payOrg")) {
					bill.setPayOrg(element.getTextTrim());
				} else if (name.equals("payOrgNo")) {
					bill.setPayOrgNo(element.getTextTrim());
				} else if (name.equals("payAmount")) {
					bill.setPayAmount(element.getTextTrim());
				} else if (name.equals("froadBillNo")) {
					bill.setFroadBillNo(element.getTextTrim());
				} else {
					logger.error("未知的pay子元素：" + name);
				}
			}
			for (int i = 0; i < systemNodes.size(); i++) {// 解出system子元素
				element = (Element) systemNodes.get(i);
				name = element.getName();
				if (name.equals("resultCode")) {
					bill.setResultCode(element.getTextTrim());
				} else if (name.equals("partnerID")) {
					bill.setPartnerID(element.getTextTrim());
				} else if (name.equals("charset")) {
					bill.setCharset(element.getTextTrim());
				} else if (name.equals("signType")) {
					bill.setSignType(element.getTextTrim());
				} else if (name.equals("signMsg")) {
					bill.setSignMsg(element.getTextTrim());
				} else {
					logger.error("未知的system子元素：" + name);
				}
			}
			Map<String, String> signMap = new TreeMap<String, String>();
			signMap.put("orderID", bill.getOrderID());
			signMap.put("orderAmount", bill.getOrderAmount());
			signMap.put("stateCode", bill.getStateCode());
			signMap.put("orderAcquiringTime", bill.getOrderAcquiringTime());
			signMap.put("orderCompleteTime", bill.getOrderCompleteTime());
			signMap.put("resultCode", bill.getResultCode());
			signMap.put("partnerID", bill.getPartnerID());

			PublicKey pk = RsaUtil.initPublicKey(PayCommand.PUBLICKEY);
			boolean flag = RsaUtil.verifyPublicKey(signMap.toString(), bill
					.getSignMsg(), pk);

			logger.info("校验结果:"+flag);
			logger.info("收到支付系统通知,响应码:" + bill.getResultCode() + "生活平台流水:"
					+ bill.getOrderID() + "交易金额:" + bill.getOrderAmount()
					+ "账单平台流水:" + bill.getFroadBillNo());

			if (flag) {//支付通知校验通过
				TransServiceImpl transService = (TransServiceImpl) SpringContextUtil
				.getBean("transService");
				transService.process(bill);
			}else{//支付通知校验失败
				logger.error("支付通知校验失败！交易流水:" + bill.getOrderID());
			}
		} catch (DocumentException e) {
			logger.error("异常", e);
		} catch (InvalidKeyException e) {
			logger.error("异常", e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("异常", e);
		} catch (InvalidKeySpecException e) {
			logger.error("异常", e);
		} catch (SignatureException e) {
			logger.error("异常", e);
		}catch(Exception e){
			logger.error("异常", e);
		}finally{
			response.getWriter().write("success");
		}
	}
}
