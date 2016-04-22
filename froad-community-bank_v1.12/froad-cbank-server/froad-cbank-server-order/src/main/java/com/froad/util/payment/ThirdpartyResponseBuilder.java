package com.froad.util.payment;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.froad.thirdparty.common.OpenApiCommand;
import com.froad.thirdparty.dto.response.openapi.NoticeFroadApi;
import com.froad.thirdparty.dto.response.openapi.Order;
import com.froad.thirdparty.util.RsaUtil;
import com.froad.thirdparty.util.XmlBeanUtil;

/**
 * 将第三方响应转换为Entity
* <p>Function: ThirdpartyResponseBuilder</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-25 下午5:41:01
* @version 1.0
 */
public class ThirdpartyResponseBuilder {

	/**
	 * 消费现金通知处理
	* <p>Function: combineXMLToEntity</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-25 下午5:43:53
	* @version 1.0
	* @param noticeXML
	* @return
	 */
	public static NoticeFroadApi combineXMLToEntity(String noticeXML) {
		NoticeFroadApi noticeFroadApi = null;
		try {
			@SuppressWarnings("rawtypes")
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("noticeFroadApi", NoticeFroadApi.class);
			xmlToBeanMap.put("order", com.froad.thirdparty.dto.response.openapi.Order.class);
			xmlToBeanMap.put("pay", com.froad.thirdparty.dto.response.openapi.Pay.class);
			xmlToBeanMap.put("system", System.class);
			noticeFroadApi = (NoticeFroadApi)XmlBeanUtil.xmlToBean(xmlToBeanMap, noticeXML);
			boolean flag = verifyCombine(noticeFroadApi);
			if (flag){//通知报文校验通过
				return noticeFroadApi;
			} else {// 支付通知校验失败
				Logger.logError("订单合并通知校验失败！无效的通知报文", "noticeXML", noticeXML);
			}
		} catch (Exception e) {
			Logger.logError("解析通知XML时异常", e);
		}
		return null;
	}
	
	/**
	 * 退款现金通知处理
	* <p>Function: refundXMLToEntity</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-25 下午5:45:48
	* @version 1.0
	* @param noticeXML
	* @return
	 */
	public static NoticeFroadApi refundXMLToEntity(String noticeXML) {
		NoticeFroadApi noticeFroadApi = null;
		try {
			@SuppressWarnings("rawtypes")
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("noticeFroadApi", NoticeFroadApi.class);
			xmlToBeanMap.put("order", Order.class);
			xmlToBeanMap.put("system", System.class);
			noticeFroadApi = (NoticeFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, noticeXML);
			boolean flag = verifyRefund(noticeFroadApi);
			if (flag){//通知报文校验通过
				return noticeFroadApi;
			} else {// 通知校验失败
				Logger.logError("订单退款通知校验失败！无效的通知报文", "noticeXML", noticeXML);
			}
		} catch (Exception e) {
			Logger.logError("解析通知XML时异常", e);
		}
		return null;
	}
	
	private static boolean verifyCombine(NoticeFroadApi noticeFroadApi) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, IOException{
		com.froad.thirdparty.dto.response.openapi.System system = noticeFroadApi.getSystem();
		com.froad.thirdparty.dto.response.openapi.Order order = noticeFroadApi.getOrder();
		Map<String, String> signMap = new TreeMap<String, String>();
		signMap.put("orderID", order.getOrderID());
		signMap.put("orderAmount", order.getOrderAmount());
		signMap.put("stateCode", order.getStateCode());
		signMap.put("orderAcquiringTime", order.getOrderAcquiringTime());
		signMap.put("orderCompleteTime", order.getOrderCompleteTime());
		signMap.put("resultCode", system.getResultCode());
		signMap.put("partnerID", system.getPartnerID());
		PublicKey pk = RsaUtil.initPublicKey(com.froad.thirdparty.common.OpenApiCommand.OPENAPI_PUBLICKEY);
		boolean flag = RsaUtil.verifyPublicKey(signMap.toString(), system.getSignMsg(), pk);
		return flag;
	}
	
	private static boolean verifyRefund(NoticeFroadApi notice) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, IOException{
		com.froad.thirdparty.dto.response.openapi.Order order = notice.getOrder();
		com.froad.thirdparty.dto.response.openapi.System system=notice.getSystem();
		Map<String,String> signMap = new TreeMap<String,String>();
		signMap.put("refundOrderID", order.getRefundOrderID());
		signMap.put("refundAmount", order.getRefundAmount());
		signMap.put("stateCode", order.getStateCode());
		signMap.put("resultCode", system.getResultCode());
		signMap.put("partnerID", system.getPartnerID());
		signMap.put("signType", system.getSignType());
		PublicKey pk = RsaUtil.initPublicKey(OpenApiCommand.OPENAPI_PUBLICKEY);
		boolean flag = RsaUtil.verifyPublicKey(signMap.toString(),system.getSignMsg(), pk);
		return flag;
	}
}
