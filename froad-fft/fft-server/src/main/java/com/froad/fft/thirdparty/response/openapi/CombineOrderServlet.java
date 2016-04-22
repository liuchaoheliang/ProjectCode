package com.froad.fft.thirdparty.response.openapi;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.froad.fft.service.TransCoreService;
import com.froad.fft.service.impl.TransCoreServiceImpl;
import com.froad.fft.thirdparty.common.OpenApiCommand;
import com.froad.fft.thirdparty.dto.response.openapi.NoticeFroadApi;
import com.froad.fft.thirdparty.dto.response.openapi.Order;
import com.froad.fft.thirdparty.dto.response.openapi.Pay;
import com.froad.fft.thirdparty.dto.response.openapi.System;
import com.froad.fft.thirdparty.util.HttpReqBodyToStrUtil;
import com.froad.fft.util.RsaUtil;
import com.froad.fft.util.SpringUtil;
import com.froad.fft.util.XmlBeanUtil;



	/**
	 * 类描述：接收合并支付的通知
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年4月6日 上午11:38:27 
	 */
public class CombineOrderServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(RefundServlet.class);
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("============接收到合并支付通知===========");
		String noticeXML = HttpReqBodyToStrUtil.toXMLStr(req);			
		NoticeFroadApi noticeFroadApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("noticeFroadApi", NoticeFroadApi.class);
			xmlToBeanMap.put("order", Order.class);
			xmlToBeanMap.put("pay", Pay.class);
			xmlToBeanMap.put("system", System.class);
			noticeFroadApi = (NoticeFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, noticeXML);		
			boolean flag=this.verify(noticeFroadApi);
			if (flag){//通知报文校验通过
				TransCoreService transCoreService = (TransCoreServiceImpl) SpringUtil
				.getBean("transCoreServiceImpl");
				transCoreService.doNotice(noticeFroadApi);
            } else {//支付通知校验失败
                logger.error("订单合并通知校验失败！无效的通知报文："+noticeXML);
            }
		} catch (Exception e) {
			logger.error("解析通知XML时异常", e);
		}
		resp.getWriter().write("success");
	}
	
	private boolean verify(NoticeFroadApi noticeFroadApi) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, IOException{
		System system=noticeFroadApi.getSystem();
		Order order=noticeFroadApi.getOrder();
		Pay pay=noticeFroadApi.getPay();
		
		Map<String, String> encMap = new TreeMap<String, String>();

        encMap.put("orderID", order.getOrderID());
        encMap.put("orderAmount", order.getOrderAmount());
        encMap.put("stateCode", order.getStateCode());
        encMap.put("orderAcquiringTime", order.getOrderAcquiringTime());
        encMap.put("orderCompleteTime", order.getOrderCompleteTime());

        encMap.put("resultCode", system.getResultCode());
        encMap.put("partnerID", system.getPartnerID());

        PublicKey pk = RsaUtil.initPublicKey(OpenApiCommand.OPENAPI_PUBLICKEY);
        boolean flag = RsaUtil.verifyPublicKey(encMap.toString(), system
                .getSignMsg(), pk);
        
        logger.info("收到订单合并支付通知,报文校验结果：" + flag+" 响应码：" + 
				system.getResultCode() + " 分分通支付单号(sn)："+
                order.getOrderID() + " openapi账单号：" + 
				pay.getFroadBillNo()+" 状态码："+
                order.getStateCode());
        
        return flag;
	}
}

