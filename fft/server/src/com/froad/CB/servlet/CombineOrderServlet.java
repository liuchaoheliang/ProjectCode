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
import com.froad.CB.po.bill.PayNotice;
import com.froad.CB.service.impl.TransServiceImpl;
import com.froad.util.RsaUtil;



	/**
	 * 类描述：接收合并订单通知
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 17, 2014 4:49:27 PM 
	 */
public class CombineOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger
            .getLogger(CombineOrderServlet.class);

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        logger.info("==========收到订单合并的通知============");
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
        logger.info("订单合并通知报文:" + xml);
        try {
            Document doc = DocumentHelper.parseText(xml);
            Element element = null;
            String name=null;
            PayNotice notice = new PayNotice();
            Element root = doc.getRootElement();
            Iterator<?> iter=root.elementIterator();
            while(iter.hasNext()){
                element = (Element)iter.next();
                name=element.getName();
                if (name.equals("order")) {
                	Iterator<?> it2 = element.elementIterator();
                    while(it2.hasNext()) {
                        Element element1 = (Element) it2.next();
                        String childName=element1.getName();
                        if (childName.equals("orderID")) {
                            notice.setOrderID(element1.getTextTrim());
                        }else if (childName.equals("orderAmount")) {
                            notice.setOrderAmount(element1.getTextTrim());
                        }else if (childName.equals("orderCurrency")) {
                            notice.setOrderCurrency(element1.getTextTrim());
                        }else if (childName.equals("orderRemark")) {
                            notice.setOrderRemark(element1.getTextTrim());
                        }else if (childName.equals("stateCode")) {
                            notice.setStateCode(element1.getTextTrim());
                        }else if (childName.equals("orderAcquiringTime")) {
                            notice.setOrderAcquiringTime(element1.getTextTrim());
                        }else if (childName.equals("orderCompleteTime")) {
                            notice.setOrderCompleteTime(element1.getTextTrim());
                        }
                    }
                }else if (name.equals("pay")) {
                	Iterator<?> it2 = element.elementIterator();
                    while(it2.hasNext()) {
                        Element element1 = (Element) it2.next();
                        String childName=element1.getName();
                        if (childName.equals("payType")) {
                            notice.setPayType(element1.getTextTrim());
                        }else if (childName.equals("payOrg")) {
                            notice.setPayOrg(element1.getTextTrim());
                        }else if (childName.equals("payOrgNo")) {
                            notice.setPayOrgNo(element1.getTextTrim());
                        }else if (childName.equals("payAmount")) {
                            notice.setPayAmount(element1.getTextTrim());
                        }else if (childName.equals("froadBillNo")) {
                            notice.setFroadBillNo(element1.getTextTrim());
                        }

                    }
                }else if (name.equals("system")) {
                	Iterator<?> it2 = element.elementIterator();
                    while(it2.hasNext()) {
                        Element element1 = (Element) it2.next();
                        String childName=element1.getName();
                        if (childName.equals("resultCode")) {
                            notice.setResultCode(element1.getTextTrim());
                        }else if (childName.equals("partnerID")) {
                            notice.setPartnerID(element1.getTextTrim());
                        }else if (childName.equals("charset")) {
                            notice.setCharset(element1.getTextTrim());
                        }else if (childName.equals("signType")) {
                            notice.setSignType(element1.getTextTrim());
                        }else if (childName.equals("signMsg")) {
                            notice.setSignMsg(element1.getTextTrim());
                        }
                    }
                } else {
                    logger.error("未知的子元素: " + name);
                }

            }

            Map<String, String> encMap = new TreeMap<String, String>();

            encMap.put("orderID", notice.getOrderID());
            encMap.put("orderAmount", notice.getOrderAmount());
            encMap.put("stateCode", notice.getStateCode());
            encMap.put("orderAcquiringTime", notice.getOrderAcquiringTime());
            encMap.put("orderCompleteTime", notice.getOrderCompleteTime());

            encMap.put("resultCode", notice.getResultCode());
            encMap.put("partnerID", notice.getPartnerID());

            PublicKey pk = RsaUtil.initPublicKey(PayCommand.PUBLICKEY);
            boolean flag = RsaUtil.verifyPublicKey(encMap.toString(), notice
                    .getSignMsg(), pk);

            logger.info("校验结果:" + flag+" 收到订单合并通知,响应码:" + notice.getResultCode() + " 订单编号:"
                    + notice.getOrderID() + " 方付通支付号:" + notice.getFroadBillNo());
            if (flag) {//通知报文校验通过
            	TransServiceImpl transService = (TransServiceImpl) SpringContextUtil
				.getBean("transService");
				transService.process(notice);
            } else {//支付通知校验失败
                logger.error("订单合并通知校验失败！无效的通知报文："+xml);
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
        } catch (Exception e) {
            logger.error("异常", e);
        } finally {
            response.getWriter().write("success");
        }
    }


}
