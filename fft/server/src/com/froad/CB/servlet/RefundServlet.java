package com.froad.CB.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Iterator;
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
import com.froad.CB.po.bill.RefundNotice;
import com.froad.CB.service.impl.TransServiceImpl;
import com.froad.util.RsaUtil;

public class RefundServlet extends HttpServlet{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private static final Logger logger=Logger.getLogger(HFCZServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("==========收到账单平台的退款结果通知============");
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
		String noticeXML = buffer.toString();
		logger.info("退款通知报文:" + noticeXML);
		try {
			Document doc = DocumentHelper.parseText(noticeXML);
			Element root = doc.getRootElement();
			Element element = null;
			String name = null;
			Iterator<?> iter = root.elementIterator();
			RefundNotice notice=new RefundNotice();
			while(iter.hasNext()){
				element=(Element)iter.next();
				name=element.getName();
				if (name.equals("order")){// 订单类元素
					Iterator<?> childIter = element.elementIterator();
					Element child=null;
					String childName=null;
					while(childIter.hasNext()) {
						child=(Element)childIter.next();
						childName=child.getName();
						if (childName.equals("trackNo")) {
							notice.setTrackNo(child.getTextTrim());
						}else if (childName.equals("refundOrderID")) {
							notice.setRefundOrderID(child.getTextTrim());
						}else if (childName.equals("refundAmount")) {
							notice.setRefundAmount(child.getTextTrim());
						}else if (childName.equals("stateCode")) {
							notice.setStateCode(child.getTextTrim());
						}
					}

				}else if (name.equals("system")) {// 系统类元素
					Iterator<?> childIter = element.elementIterator();
					Element child=null;
					String childName=null;
					while(childIter.hasNext()) {
						child=(Element)childIter.next();
						childName=child.getName();
						if (childName.equals("resultCode")) {
							notice.setResultCode(child.getTextTrim());
						}else if (childName.equals("partnerID")) {
							notice.setPartnerID(child.getTextTrim());
						}else if (childName.equals("charset")) {
							notice.setCharset(child.getTextTrim());
						}else if (childName.equals("signType")) {
							notice.setSignType(child.getTextTrim());
						}else if (childName.equals("signMsg")) {
							notice.setSignMsg(child.getTextTrim());
						}
					}

				}

			}
			Map<String,String> signMap = new TreeMap<String,String>();
			signMap.put("refundOrderID", notice.getRefundOrderID());
			signMap.put("refundAmount", notice.getRefundAmount());
			signMap.put("stateCode", notice.getStateCode());
			signMap.put("resultCode", notice.getResultCode());
			signMap.put("partnerID", notice.getPartnerID());
			signMap.put("signType", notice.getSignType());
			
			PublicKey pk = RsaUtil.initPublicKey(PayCommand.PUBLICKEY);
			boolean flag = RsaUtil.verifyPublicKey(signMap.toString(),
					notice.getSignMsg(), pk);

			logger.info("校验结果:" +flag);
			logger.info("收到账单系统退款通知,响应码:" + notice.getResultCode() + "退款订单号:"
					+ notice.getRefundOrderID() + "退款金额:" + notice.getRefundAmount() + " 追踪号："+ notice.getTrackNo());
			
			if (flag){//校验报文通过
				notice.setNoticeXML(noticeXML);
				TransServiceImpl transService=(TransServiceImpl)SpringContextUtil.getBean("transService");
				transService.processRefund(notice);
			} else {//退款通知报文校验失败
				logger.info("退款通知校验失败,退款通知报文："+noticeXML);		
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
		} catch(Exception e){
			logger.error("异常", e);
		}finally{
			response.getWriter().write("success");
		}
	}

}
