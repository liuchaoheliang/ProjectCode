package com.froad.fft.thirdparty.request.openapi.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.froad.fft.common.AppException;
import com.froad.fft.thirdparty.common.ListCommand;
import com.froad.fft.thirdparty.common.OpenApiCommand;
import com.froad.fft.thirdparty.common.RespCodeCommand;
import com.froad.fft.thirdparty.common.SystemCommand;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiOrderDetail;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.fft.thirdparty.dto.request.openapi.ResponseFroadApi;
import com.froad.fft.thirdparty.request.openapi.OpenApiFunc;
import com.froad.fft.thirdparty.util.SecretUtil;
import com.froad.fft.thirdparty.util.XMLStrHttpClientUtil;
import com.froad.fft.util.DateUtil;
import com.froad.fft.util.FreeMarkerUtil;
import com.froad.fft.util.XmlBeanUtil;

@Component("openApiFuncImpl")
public class OpenApiFuncImpl implements OpenApiFunc {

	private static Logger logger = Logger.getLogger(OpenApiFuncImpl.class);
	
	private final static String SIGN_TYPE = SystemCommand.SIGNTYPE_RSA;//加密方式

	//private final static String PARTNER_ID = SystemCommand.OPENAPI_PARTNER_ID;
	
	@Resource
	private FreeMarkerUtil freeMarkerUtil;
	
	@Override
	public OpenApiRes refund(OpenApiReq openApiReq) throws AppException {
		logger.info("开始进行<退款申请>功能,交互系统: <openapi>");
		OpenApiRes openApiRes = new OpenApiRes();
		
		String partnerID = openApiReq.getPartnerID();
		String refundOrderID = openApiReq.getRefundOrderID();//退款订单编号		
		String orderID = openApiReq.getOrderID();//订单编号		
		String orderAmount = openApiReq.getOrderAmount();//订单金额		
		String refundAmount = openApiReq.getRefundAmount();//退款订单金额		
		String refundType = openApiReq.getRefundType().getVal();		
		String orderSupplier = openApiReq.getOrderSupplier();//订单供应商		
		String refundReason = openApiReq.getRefundReason();//退款原因		
		String reqID = SystemCommand.FFT_CLIENT_PREFIX + DateUtil.currentTimeToString();
		String signMsg = SecretUtil.refund(refundOrderID, orderID, orderAmount, refundAmount, refundType, orderSupplier, OpenApiCommand.REFUND_NOTICE_URL, reqID, partnerID,SIGN_TYPE);
		
		Map model = new HashMap();
		model.put("refundOrderID",refundOrderID==null ? "":refundOrderID);
		model.put("orderID", orderID==null ? "":orderID);
		model.put("orderAmount", orderAmount==null ? "":orderAmount);
		model.put("refundAmount", refundAmount==null ? "":refundAmount);
		model.put("refundType", refundType==null ?"":refundType);
		model.put("orderSupplier", orderSupplier==null?"":orderSupplier);
		model.put("refundReason", refundReason==null?"":refundReason);
		model.put("noticeUrl", OpenApiCommand.REFUND_NOTICE_URL);
		model.put("reqID", reqID);
		model.put("versions", OpenApiCommand.OPENAPI_VERSION);
		model.put("partnerID", partnerID);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		String requestXmlStr = freeMarkerUtil.getContent("openapi/refund_currency.ftl", model);
		logger.info("请求 xml：\n" + requestXmlStr);
		
		// 发送请求
		String responseXmlStr = null;
		try {
			//TODO 这个请求地址仅适用于合并支付的退款
			responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.REFUND_COMBINE_URL,requestXmlStr);
		} catch (Exception e) {
			logger.error("发送退款申请 异常", e);
			openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
			openApiRes.setResultDesc("发送退款申请异常");
			return openApiRes;
		}
		
		ResponseFroadApi responseFroadApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
			responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);		
		} catch (Exception e) {
			logger.error("解析响应时异常", e);
			openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
			openApiRes.setResultDesc("解析响应报文时异常");
			return openApiRes;
		}
		
		// 验签
		boolean flag = SecretUtil.responseVerifyRSA(ListCommand.REFUND_CURRENCY,
							responseFroadApi,
							OpenApiCommand.OPENAPI_PUBLICKEY);
		logger.info("响应验签结果：" + flag);
		
		if(flag){
			openApiRes.setResultCode(responseFroadApi.getResultCode());
			openApiRes.setResultDesc(responseFroadApi.getRemark());
			return openApiRes;
		}else{
			logger.info("响应验签不通过");
			openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			openApiRes.setResultDesc("响应报文验签不通过");
			return openApiRes;
		}
	}


	@Override
	public OpenApiRes accountCheck(OpenApiReq openApiReq) throws AppException {
		logger.info("开始进行<校验查询>功能,交互系统: <openapi>");
		OpenApiRes openApiRes = new OpenApiRes();
		
		String partnerID = openApiReq.getPartnerID();
		String checkOrg = openApiReq.getCheckOrg();//校验机构
		String checkType = openApiReq.getCheckType().getVal();
		String checkContent = openApiReq.getCheckContent();//校验信息内容
		String checkTime = DateUtil.currentTimeToString();//请求时间
		String checkRemark = openApiReq.getCheckRemark();//校验扩展信息
		String reqID = SystemCommand.FFT_CLIENT_PREFIX + DateUtil.currentTimeToString();
		String signMsg = SecretUtil.accountCheck(checkType, checkContent, checkTime, reqID, partnerID, SIGN_TYPE);

		Map model = new HashMap();
		model.put("checkOrg",checkOrg==null ? "":checkOrg);
		model.put("checkType", checkType==null ? "":checkType);
		model.put("checkContent", checkContent==null ? "":checkContent);
		model.put("checkTime", checkTime);
		model.put("checkRemark", checkRemark==null ?"":checkRemark);
		model.put("reqID", reqID);
		model.put("versions", OpenApiCommand.OPENAPI_VERSION);
		model.put("partnerID", partnerID);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		String requestXmlStr = freeMarkerUtil.getContent("openapi/account_check.ftl", model);
		logger.info("请求 xml：\n" + requestXmlStr);
		
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.ACCOUNT_CHECK_URL,requestXmlStr);
		} catch (Exception e) {
			logger.error("发送退款申请 异常", e);
			openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
			openApiRes.setResultDesc("发送退款申请 异常");
			return openApiRes;
		}
		
		ResponseFroadApi responseFroadApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
			responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);		
		} catch (Exception e) {
			logger.error("解析响应时异常", e);
			openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
			openApiRes.setResultDesc("解析响应报文时异常");
			return openApiRes;
		}
		// 验签
		boolean flag = SecretUtil.responseVerifyRSA(ListCommand.ACCOUNT_CHECK,
							responseFroadApi,
							OpenApiCommand.OPENAPI_PUBLICKEY);
		logger.info("响应验签结果：" + flag);		
		if(flag){
			openApiRes.setResultCode(responseFroadApi.getResultCode());
			openApiRes.setResultDesc(responseFroadApi.getRemark());
			openApiRes.setCheckResultCode(responseFroadApi.getCheckResultCode());
			openApiRes.setCheckResultContent(responseFroadApi.getCheckResultContent());
			return openApiRes;
		}else{
			logger.info("响应验签不通过");
			openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			openApiRes.setResultDesc("响应报文验签不通过");
			return openApiRes;
		}		
	}


	@Override
	public OpenApiRes transferCurrency(OpenApiReq openApiReq)
			throws AppException {
		logger.info("开始进行<转账>功能,交互系统: <openapi>");
		OpenApiRes openApiRes = new OpenApiRes();
		String partnerID = openApiReq.getPartnerID();
		String transferID = openApiReq.getTransferID();//转账编号
		String transferType = openApiReq.getTransferType().getVal();
		String transferOrg = openApiReq.getTransferOrg();//转账机构 
		String transferAmount = openApiReq.getTransferAmount();//转账金额
		String payerMember = openApiReq.getPayerMember();//付款方 （账户号|账户名 如: 62000123|方付通）
		String payerMemberMsg = openApiReq.getPayerMemberMsg();//付款方帐户加密信息
		String payeeMember = openApiReq.getPayeeMember();//收款方 （账户号|账户名 如: 62000123|方付通）
		String payeeMemberMsg = openApiReq.getPayeeMemberMsg();//收款方加密信息
		String transferDisplay = OpenApiCommand.TRANSFER_DISPLAY;//转账显示名
		String transferRemark = openApiReq.getTransferRemark();//拓展信息
		String client = openApiReq.getClient().getVal();
		String transferCurrency = OpenApiCommand.CURRENCY;
		String transferSubmitTime = DateUtil.currentTimeToString();
		String reqID = SystemCommand.FFT_CLIENT_PREFIX;
		
		String signMsg = SecretUtil.transferCurrency(transferID, transferAmount, transferSubmitTime, reqID, partnerID, SIGN_TYPE);
		
		Map model = new HashMap();
		model.put("transferID",transferID==null ? "":transferID);
		model.put("transferType", transferType==null ? "":transferType);
		model.put("transferOrg", transferOrg==null ? "":transferOrg);
		model.put("transferAmount", transferAmount==null ? "":transferAmount);
		model.put("payerMember", payerMember==null ? "":payerMember);
		model.put("payerMemberMsg", payerMemberMsg==null ? "":payerMemberMsg);
		model.put("payeeMember", payeeMember==null ?"":payeeMember);
		model.put("payeeMemberMsg", payeeMemberMsg==null ?"":payeeMemberMsg);
		model.put("transferDisplay", transferDisplay==null ?"":transferDisplay);
		model.put("transferRemark", transferRemark==null ?"":transferRemark);
		model.put("client", client==null ?"":client);
		model.put("transferCurrency", transferCurrency==null ?"":transferCurrency);		
		model.put("transferSubmitTime", transferSubmitTime);
		model.put("reqID", reqID);
		model.put("versions", OpenApiCommand.OPENAPI_VERSION);
		model.put("partnerID", partnerID);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		String requestXmlStr = freeMarkerUtil.getContent("openapi/transfer_currency.ftl", model);
		logger.info("请求 xml：\n" + requestXmlStr);
		
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.TRANSFER_URL,requestXmlStr);
		} catch (Exception e) {
			logger.error("发送转账 异常", e);
			openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
			openApiRes.setResultDesc("发送转账 异常");
			return openApiRes;
		}
		
		ResponseFroadApi responseFroadApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
			responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);		
		} catch (Exception e) {
			logger.error("解析响应时异常", e);
			openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
			openApiRes.setResultDesc("解析响应报文时异常");
			return openApiRes;
		}
		// 验签
		boolean flag = SecretUtil.responseVerifyRSA(ListCommand.TRANSFER_CURRENCY,
							responseFroadApi,
							OpenApiCommand.OPENAPI_PUBLICKEY);
		logger.info("响应验签结果：" + flag);
		if(flag){
			openApiRes.setResultCode(responseFroadApi.getResultCode());
			openApiRes.setResultDesc(responseFroadApi.getRemark());
			return openApiRes;
		}else{
			logger.info("响应验签不通过");
			openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			openApiRes.setResultDesc("响应报文验签不通过");
			return openApiRes;
		}
	}


	@Override
	public OpenApiRes agencyCollectOrDeduct(OpenApiReq openApiReq) throws AppException {
		
		OpenApiRes openApiRes = new OpenApiRes();
		String partnerID = openApiReq.getPartnerID();
		String orderID = openApiReq.getOrderID();
		String orderType = openApiReq.getOrderType().getVal();
		String orderAmount = openApiReq.getOrderAmount();
		String orderRemark = openApiReq.getOrderRemark();
		String payType = openApiReq.getPayChannel().getCode();
		String payOrg = openApiReq.getPayOrg();
		String payerType = openApiReq.getPayerType().getVal();
		String payerWay = openApiReq.getPayerWay().getVal();
		String payerMember = openApiReq.getPayerMember();
		String payerAmount = openApiReq.getPayerAmount();
		String payeeType = openApiReq.getPayeeType().getVal();
		String payeeWay = openApiReq.getPayeeWay().getVal();
		String payeeMember = openApiReq.getPayeeMember();
		String payeeAmount = openApiReq.getPayeeAmount();		
		String payerMemberMsg = openApiReq.getPayerMemberMsg();
		String payeeMemberMsg = openApiReq.getPayeeMemberMsg();
		String orderSubmitTime = DateUtil.currentTimeToString();
		String orderCurrency = OpenApiCommand.CURRENCY;
		String client = openApiReq.getClient().getVal();
		String reqID = SystemCommand.FFT_CLIENT_PREFIX + orderSubmitTime;
		
		String url = null;
		String noticeUrl = null;
		if(openApiReq.getReqType().getVal() == 0){
			//代收
			url = OpenApiCommand.AGENCY_COLLECT_URL;
			noticeUrl = OpenApiCommand.AGENCY_COLLECT_NOTICE_URL;
			logger.info("开始进行<代收>功能,交互系统: <openapi>");
		}else if(openApiReq.getReqType().getVal() == 1){
			//代扣
			url = OpenApiCommand.AGENCY_DEDUCT_URL;
			noticeUrl = OpenApiCommand.AGENCY_DEDUCT_NOTICE_URL;
			logger.info("开始进行<代扣>功能,交互系统: <openapi>");
		}
		
		String signMsg = SecretUtil.agencyCollectOrDeduct(orderID, orderAmount, orderSubmitTime, reqID,  partnerID, SIGN_TYPE);
		
		Map model = new HashMap();
		model.put("orderID",orderID==null ? "":orderID);
		model.put("orderType", orderType==null ? "":orderType);
		model.put("orderAmount", orderAmount==null ? "":orderAmount);
		model.put("orderRemark", orderRemark==null ? "":orderRemark);
		model.put("payType", payType==null ? "":payType);
		model.put("payOrg", payOrg==null ? "":payOrg);
		model.put("payerType", payerType==null ?"":payerType);
		model.put("payerWay", payerWay==null ?"":payerWay);
		model.put("payerMember", payerMember==null ?"":payerMember);
		model.put("payerAmount", payerAmount==null ?"":payerAmount);
		model.put("payeeType", payeeType==null ?"":payeeType);
		model.put("payeeWay", payeeWay==null ?"":payeeWay);		
		model.put("payeeMember", payeeMember==null?"":payeeMember);
		model.put("payeeAmount", payeeAmount==null?"":payeeAmount);
		model.put("noticeUrl", noticeUrl==null?"":noticeUrl);
		model.put("payerMemberMsg", payerMemberMsg==null?"":payerMemberMsg);
		model.put("payeeMemberMsg", payeeMemberMsg==null?"":payeeMemberMsg);
		model.put("orderSubmitTime", orderSubmitTime==null?"":orderSubmitTime);
		model.put("orderCurrency",orderCurrency);
		model.put("orderDisplay", OpenApiCommand.ORDER_DISPLAY);
		model.put("orderFailureTime", "");
		model.put("returnUrl", "");
		model.put("client", client == null ?"":client);
		model.put("reqID", reqID);
		model.put("versions", OpenApiCommand.OPENAPI_VERSION);
		model.put("partnerID", partnerID);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		String requestXmlStr = freeMarkerUtil.getContent("openapi/agency_collect_decuct.ftl", model);
		logger.info("请求 xml：\n" + requestXmlStr);
		
		// 发送请求
		String responseXmlStr = null;
		try {			
			responseXmlStr = XMLStrHttpClientUtil.httpPost(url,requestXmlStr);
		} catch (Exception e) {
			logger.error("发送代收|代扣 异常", e);
			openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
			openApiRes.setResultDesc("发送代收|代扣  异常");
			return openApiRes;
		}
		
		ResponseFroadApi responseFroadApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
			responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);		
		} catch (Exception e) {
			logger.error("解析响应时异常", e);
			openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
			openApiRes.setResultDesc("解析响应报文时异常");
			return openApiRes;
		}
		
		// 验签
		boolean flag = SecretUtil.responseVerifyRSA(ListCommand.AGENCY_CURRENCY_DEDUCT,
							responseFroadApi,
							OpenApiCommand.OPENAPI_PUBLICKEY);
		logger.info("响应验签结果：" + flag);
		if(flag){
			openApiRes.setResultCode(responseFroadApi.getResultCode());
			openApiRes.setResultDesc(responseFroadApi.getRemark());
			openApiRes.setFroadBillNo(responseFroadApi.getFroadBillNo());
			return openApiRes;
		}else{
			logger.info("响应验签不通过");
			openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			openApiRes.setResultDesc("响应报文验签不通过");
			return openApiRes;
		}
	}


	@Override
	public OpenApiRes combineOrder(OpenApiReq openApiReq)
			throws AppException {
		logger.info("开始进行<合并订单支付>功能,交互系统: <openapi>");
		OpenApiRes openApiRes = new OpenApiRes();
		String noticeUrl = OpenApiCommand.COMBINE_ORDER_NOTICE_URL;
		List<OpenApiOrderDetail> orderDetails = openApiReq.getOrderDetails();
		String partnerID = openApiReq.getPartnerID();
		//遍历组装order相关验签字段
		StringBuffer sbOrderId = new StringBuffer();
		StringBuffer sbOrderAmount = new StringBuffer();
		StringBuffer sbOrderSupplier = new StringBuffer();
		for (OpenApiOrderDetail openApiOrderDetail : orderDetails) {
			sbOrderId.append(openApiOrderDetail.getOrderID());
			sbOrderAmount.append(openApiOrderDetail.getOrderAmount());
			sbOrderSupplier.append(openApiOrderDetail.getOrderSupplier());
		}
		
		String orderType = openApiReq.getOrderType().getVal();
		String totalAmount = openApiReq.getTotalAmount();//订单总金额
		String payDirect = openApiReq.getPayDirect().getVal();//设置收款方在方付通的会员身份描述
		String payOrg = openApiReq.getPayOrg();
		String orderSubmitTime = DateUtil.currentTimeToString();
		String orderRemark = openApiReq.getOrderRemark();
		String orderDisplay=openApiReq.getOrderDisplay();
		if(orderDisplay==null||"".equals(orderDisplay)){
			orderDisplay=OpenApiCommand.ORDER_DISPLAY;
		}
		String payType = openApiReq.getPayChannel().getCode();
		String payerMember = openApiReq.getPayerMember();
		String reqID = SystemCommand.FFT_CLIENT_PREFIX + orderSubmitTime;
		String client = openApiReq.getClient().getVal();
		
		
		
		String signMsg = SecretUtil.combineTransaction(sbOrderId.toString(), sbOrderAmount.toString(), sbOrderSupplier.toString(), 
				orderType, totalAmount, orderSubmitTime, null, null, payOrg, payerMember, null, payDirect, noticeUrl, reqID, partnerID, SystemCommand.CHARSET_UTF8, SIGN_TYPE);
		
		Map model = new HashMap();
		model.put("orderDetails",orderDetails);
		model.put("orderType",orderType==null ? "":orderType);
		model.put("totalAmount", totalAmount==null ? "":totalAmount);
		model.put("orderCurrency", OpenApiCommand.CURRENCY);
		model.put("payDirect", payDirect==null ? "":payDirect);
		model.put("payOrg", payOrg==null ? "":payOrg);
		model.put("orderSubmitTime", orderSubmitTime==null?"":orderSubmitTime);
		model.put("orderRemark", orderRemark==null?"":orderRemark);
		model.put("orderDisplay", orderDisplay);
		model.put("noticeUrl", noticeUrl==null?"":noticeUrl);
		model.put("payType", payType==null?"":payType);
		model.put("payerMember", payerMember==null?"":payerMember);
		model.put("reqID", reqID==null?"":reqID);
		model.put("client",  client==null?"":client);
		model.put("versions", OpenApiCommand.OPENAPI_VERSION);
		model.put("partnerID", partnerID);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		
		String requestXmlStr = freeMarkerUtil.getContent("openapi/combine_transaction.ftl", model);
		logger.info("请求 xml：\n" + requestXmlStr);
		
		// 发送请求
		String responseXmlStr = null;
		try {			
			responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.COMBINE_ORDER_URL,requestXmlStr);
		} catch (Exception e) {
			logger.error("发送合并订单 异常支付 异常", e);
			openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
			openApiRes.setResultDesc("发送合并订单 异常支付  异常");
			return openApiRes;
		}
		
		ResponseFroadApi responseFroadApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
			responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);		
		} catch (Exception e) {
			logger.error("解析响应时异常", e);
			openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
			openApiRes.setResultDesc("解析响应报文时异常");
			return openApiRes;
		}
		
		// 验签
		boolean flag = SecretUtil.responseVerifyRSA(ListCommand.COMBINE_TRANS,
							responseFroadApi,
							OpenApiCommand.OPENAPI_PUBLICKEY);
		logger.info("响应验签结果：" + flag);
		if(flag){
			openApiRes.setResultCode(responseFroadApi.getResultCode());
			openApiRes.setResultDesc(responseFroadApi.getRemark());
			openApiRes.setFroadBillNo(responseFroadApi.getFroadBillNo());
			openApiRes.setPaymentURL(responseFroadApi.getPaymentURL());
			return openApiRes;
		}else{
			logger.info("响应验签不通过");
			openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			openApiRes.setResultDesc("响应报文验签不通过");
			return openApiRes;
		}
	}
}
