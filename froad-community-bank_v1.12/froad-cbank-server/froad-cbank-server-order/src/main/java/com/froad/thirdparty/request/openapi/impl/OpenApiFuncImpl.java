package com.froad.thirdparty.request.openapi.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.logback.LogCvt;
import com.froad.thirdparty.common.ListCommand;
import com.froad.thirdparty.common.OpenApiCommand;
import com.froad.thirdparty.common.RespCodeCommand;
import com.froad.thirdparty.common.SystemCommand;
import com.froad.thirdparty.dto.request.openapi.CreateMobileTokenApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiOrderDetail;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.thirdparty.dto.request.openapi.QueryParamApiReq;
import com.froad.thirdparty.dto.request.openapi.ResponseFroadApi;
import com.froad.thirdparty.dto.request.openapi.SendSmsApiReq;
import com.froad.thirdparty.dto.request.openapi.SignApiReq;
import com.froad.thirdparty.dto.request.openapi.SignCancelApiReq;
import com.froad.thirdparty.dto.request.openapi.SystemBean;
import com.froad.thirdparty.dto.response.openapi.Order;
import com.froad.thirdparty.exception.AppException;
import com.froad.thirdparty.request.openapi.OpenApiFunc;
import com.froad.thirdparty.util.DateUtil;
import com.froad.thirdparty.util.FreemarkerUtil;
import com.froad.thirdparty.util.SecretUtil;
import com.froad.thirdparty.util.XMLStrHttpClientUtil;
import com.froad.thirdparty.util.XmlBeanUtil;
import com.froad.thirdparty.dto.request.openapi.LimitReq;
import com.froad.util.payment.EsyT;


public class OpenApiFuncImpl implements OpenApiFunc {

	private final static String SIGN_TYPE = SystemCommand.SIGNTYPE_RSA;//加密方式

	private final static String REQ_ID_PREFIX = "CBK002"; //标准版后缀
	
	@Override
	public OpenApiRes refund(OpenApiReq openApiReq) throws AppException {
		LogCvt.info("开始进行<退款申请>功能,交互系统: <openapi>");
		OpenApiRes openApiRes = new OpenApiRes();
		String partnerID = openApiReq.getPartnerID();
		String refundOrderID = openApiReq.getRefundOrderID();//退款订单编号
		String orderID = openApiReq.getOrderID();//订单编号
		String orderAmount = openApiReq.getOrderAmount();//订单金额
		String refundAmount = openApiReq.getRefundAmount();//退款订单金额
		String refundType = openApiReq.getRefundType().getVal();
		String orderSupplier = openApiReq.getOrderSupplier();//订单供应商
		String refundReason = openApiReq.getRefundReason();//退款原因
		String noticeUrl=openApiReq.getNoticeUrl();
		String reqID = REQ_ID_PREFIX + com.froad.util.DateUtil.formatDateTime(com.froad.util.DateUtil.DATE_FORMAT2, new Date()) + EsyT.simpleID();
		String signMsg = SecretUtil.refund(refundOrderID, orderID, orderAmount, refundAmount, refundType, orderSupplier, noticeUrl, reqID, partnerID,SIGN_TYPE);
		Map model = new HashMap();
		model.put("refundOrderID",refundOrderID==null ? "":refundOrderID);
		model.put("orderID", orderID==null ? "":orderID);
		model.put("orderAmount", orderAmount==null ? "":orderAmount);
		model.put("refundAmount", refundAmount==null ? "":refundAmount);
		model.put("refundType", refundType==null ?"":refundType);
		model.put("orderSupplier", orderSupplier==null?"":orderSupplier);
		model.put("refundReason", refundReason==null?"":refundReason);
		model.put("noticeUrl", noticeUrl);
		model.put("reqID", reqID);
		model.put("versions", OpenApiCommand.OPENAPI_VERSION);
		model.put("partnerID", partnerID);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		String requestXmlStr = FreemarkerUtil.templateProcess("openapi/refund_currency.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);

		// 发送请求
		String responseXmlStr = null;
		try {
			//TODO 这个请求地址仅适用于合并支付的退款
			responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.REFUND_COMBINE_URL,requestXmlStr);
		} catch (Exception e) {
			throw new AppException(e);
//			LogCvt.error("发送退款申请 异常", e);
//			openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
//			openApiRes.setResultDesc("发送退款申请异常");
//			return openApiRes;
		}

		ResponseFroadApi responseFroadApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
			responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
			openApiRes.setResultDesc("解析响应报文时异常");
			return openApiRes;
		}
		// 验签
		boolean flag = SecretUtil.responseVerifyRSA(ListCommand.REFUND_CURRENCY,responseFroadApi,OpenApiCommand.OPENAPI_PUBLICKEY);
		LogCvt.info("响应验签结果：" + flag);

		if(flag){
			openApiRes.setResultCode(responseFroadApi.getResultCode());
			openApiRes.setResultDesc(responseFroadApi.getRemark());
			return openApiRes;
		}else{
			LogCvt.info("响应验签不通过");
			openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			openApiRes.setResultDesc("响应报文验签不通过");
			return openApiRes;
		}
	}


	@Override
	public OpenApiRes accountCheck(OpenApiReq openApiReq) throws AppException {
		LogCvt.info("开始进行<校验查询>功能,交互系统: <openapi>");
		OpenApiRes openApiRes = new OpenApiRes();

		String partnerID = openApiReq.getPartnerID();
		String checkOrg = openApiReq.getCheckOrg();//校验机构
		String checkType = openApiReq.getCheckType().getVal();
		String checkContent = openApiReq.getCheckContent();//校验信息内容
		String checkTime = DateUtil.currentTimeToString();//请求时间
		String checkRemark = openApiReq.getCheckRemark();//校验扩展信息
		String reqID = SystemCommand.FROAD_CBANK_PREFIX + DateUtil.currentTimeToString();
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
		String requestXmlStr = FreemarkerUtil.templateProcess("openapi/account_check.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);
		LogCvt.info("账户校验访问地址："+OpenApiCommand.ACCOUNT_CHECK_URL);

		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.ACCOUNT_CHECK_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送贴膜卡校验 异常", e);
			openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
			openApiRes.setResultDesc("发送贴膜卡校验 异常");
			return openApiRes;
		}

		ResponseFroadApi responseFroadApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
			responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
			openApiRes.setResultDesc("解析响应报文时异常");
			return openApiRes;
		}
		// 验签
		boolean flag = SecretUtil.responseVerifyRSA(ListCommand.ACCOUNT_CHECK,
							responseFroadApi,
							OpenApiCommand.OPENAPI_PUBLICKEY);
		LogCvt.info("响应验签结果：" + flag);
		if(flag){
			openApiRes.setResultCode(responseFroadApi.getResultCode());
			openApiRes.setResultDesc(responseFroadApi.getRemark());
			openApiRes.setCheckResultCode(responseFroadApi.getCheckResultCode());
			openApiRes.setCheckResultContent(responseFroadApi.getCheckResultContent());
			return openApiRes;
		}else{
			LogCvt.info("响应验签不通过");
			openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			openApiRes.setResultDesc("响应报文验签不通过");
			return openApiRes;
		}
	}


	@Override
	public OpenApiRes transferCurrency(OpenApiReq openApiReq)
			throws AppException {
		LogCvt.info("开始进行<转账>功能,交互系统: <openapi>");
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
		String client = openApiReq.getClient().getCode();
		String transferCurrency = OpenApiCommand.CURRENCY;
		String transferSubmitTime = DateUtil.currentTimeToString();
		String reqID = SystemCommand.FROAD_CBANK_PREFIX;

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
		String requestXmlStr = FreemarkerUtil.templateProcess("openapi/transfer_currency.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);

		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.TRANSFER_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送转账 异常", e);
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
			LogCvt.error("解析响应时异常", e);
			openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
			openApiRes.setResultDesc("解析响应报文时异常");
			return openApiRes;
		}
		// 验签
		boolean flag = SecretUtil.responseVerifyRSA(ListCommand.TRANSFER_CURRENCY,
							responseFroadApi,
							OpenApiCommand.OPENAPI_PUBLICKEY);
		LogCvt.info("响应验签结果：" + flag);
		if(flag){
			openApiRes.setResultCode(responseFroadApi.getResultCode());
			openApiRes.setResultDesc(responseFroadApi.getRemark());
			return openApiRes;
		}else{
			LogCvt.info("响应验签不通过");
			openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			openApiRes.setResultDesc("响应报文验签不通过");
			return openApiRes;
		}
	}


	@Override
	@Deprecated
	public OpenApiRes agencyCollectOrDeduct(OpenApiReq openApiReq) throws AppException {

		OpenApiRes openApiRes = new OpenApiRes();
		String partnerID = openApiReq.getPartnerID();
		String orderID = openApiReq.getOrderID();
		String orderType = openApiReq.getBillOrderType().getVal();
		String orderAmount = openApiReq.getOrderAmount();
		String orderRemark = openApiReq.getOrderRemark();
		String payType = openApiReq.getPayType().getCode();
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
		String client = openApiReq.getClient().getCode();
		String reqID = SystemCommand.FROAD_CBANK_PREFIX + orderSubmitTime;

		String url = null;
		String noticeUrl = null;
		if(openApiReq.getReqType().getVal() == 0){
			//代收
			url = OpenApiCommand.AGENCY_COLLECT_URL;
			noticeUrl = OpenApiCommand.AGENCY_COLLECT_NOTICE_URL;
			LogCvt.info("开始进行<代收>功能,交互系统: <openapi>");
		}else if(openApiReq.getReqType().getVal() == 1){
			//代扣
			url = OpenApiCommand.AGENCY_DEDUCT_URL;
			noticeUrl = OpenApiCommand.AGENCY_DEDUCT_NOTICE_URL;
			LogCvt.info("开始进行<代扣>功能,交互系统: <openapi>");
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
		String requestXmlStr = FreemarkerUtil.templateProcess("openapi/agency_collect_decuct.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);

		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(url,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送代收|代扣 异常", e);
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
			LogCvt.error("解析响应时异常", e);
			openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
			openApiRes.setResultDesc("解析响应报文时异常");
			return openApiRes;
		}

		// 验签
		boolean flag = SecretUtil.responseVerifyRSA(ListCommand.AGENCY_CURRENCY_DEDUCT,
							responseFroadApi,
							OpenApiCommand.OPENAPI_PUBLICKEY);
		LogCvt.info("响应验签结果：" + flag);
		if(flag){
			openApiRes.setResultCode(responseFroadApi.getResultCode());
			openApiRes.setResultDesc(responseFroadApi.getRemark());
			openApiRes.setFroadBillNo(responseFroadApi.getFroadBillNo());
			return openApiRes;
		}else{
			LogCvt.info("响应验签不通过");
			openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			openApiRes.setResultDesc("响应报文验签不通过");
			return openApiRes;
		}
	}


	@Override
	public OpenApiRes combineOrder(OpenApiReq openApiReq)throws AppException {
		LogCvt.info("开始进行<合并订单支付>功能,交互系统: <openapi>");
		OpenApiRes openApiRes = new OpenApiRes();
		String noticeUrl = OpenApiCommand.COMBINE_ORDER_NOTICE_URL;
		List<OpenApiOrderDetail> orderDetails = openApiReq.getOrderDetails();
		String partnerID = openApiReq.getPartnerID();
		//遍历组装order相关验签字段
		StringBuffer sbOrderId = new StringBuffer();
		StringBuffer sbOrderAmount = new StringBuffer();
		StringBuffer sbOrderSupplier = new StringBuffer();
		String sbOrderID = null ; 
		for (OpenApiOrderDetail openApiOrderDetail : orderDetails) {
			sbOrderId.append(openApiOrderDetail.getOrderID());
			sbOrderID = openApiOrderDetail.getOrderID();
			sbOrderAmount.append(openApiOrderDetail.getOrderAmount());
			sbOrderSupplier.append(openApiOrderDetail.getOrderSupplier());
		}

		String orderType = openApiReq.getBillOrderType().getVal();
		String totalAmount = openApiReq.getTotalAmount();//订单总金额
		String payDirect = openApiReq.getPayDirect().getVal();//设置收款方在方付通的会员身份描述
		String payOrg = openApiReq.getPayOrg();
		String orderSubmitTime = openApiReq.getOrderSubmitTime();
		String orderRemark = openApiReq.getOrderRemark();
		String orderDisplay=openApiReq.getOrderDisplay();
		if(orderDisplay==null||"".equals(orderDisplay)){
			orderDisplay=OpenApiCommand.ORDER_DISPLAY;
		}
		String payType = openApiReq.getPayType().getCode();
		String mobileToken=openApiReq.getMobileToken();
		String payerMember = openApiReq.getPayerMember();
		String reqID = REQ_ID_PREFIX + com.froad.util.DateUtil.formatDateTime(com.froad.util.DateUtil.DATE_FORMAT2, new Date()) + sbOrderID;
		String client = openApiReq.getClient().getCode();
		String payeeMember=openApiReq.getPayeeMember();//收款方为第三方商户时必填
		String payerIdentity=openApiReq.getPayerIdentity();//付款方为方付通时，填20，其它的不填
		
		String signMsg = SecretUtil.combineTransaction(sbOrderId.toString(), 
				sbOrderAmount.toString(), sbOrderSupplier.toString(),
				orderType, totalAmount, orderSubmitTime, mobileToken, null,
				payOrg, payerMember, payeeMember, payDirect, noticeUrl,
				reqID, partnerID, SystemCommand.CHARSET_UTF8, SIGN_TYPE);

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
		model.put("returnUrl", openApiReq.getReturnUrl());
		model.put("payType", payType==null?"":payType);
		model.put("mobileToken", mobileToken);
		model.put("payerMember", payerMember==null?"":payerMember);
		model.put("reqID", reqID==null?"":reqID);
		model.put("client",  client==null?"":client);
		model.put("versions", OpenApiCommand.OPENAPI_VERSION);
		model.put("partnerID", partnerID);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		
		model.put("payerIdentity", payerIdentity);//付款方为方付通时，填20，其它的不填
		model.put("payeeMember", payeeMember);//收款方为第三方商户时，必填

		String requestXmlStr = FreemarkerUtil.templateProcess("openapi/combine_transaction.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);

		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.COMBINE_ORDER_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送合并订单 异常支付 异常", e);
			openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
			openApiRes.setResultDesc("发送合并订单 异常支付  异常");
			openApiRes.setXml("请求报文："+requestXmlStr+"    响应报文："+responseXmlStr);
			throw new AppException(e);
		}

		ResponseFroadApi responseFroadApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
			responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
			openApiRes.setResultDesc("解析响应报文时异常");
			openApiRes.setXml("请求报文："+requestXmlStr+"    响应报文："+responseXmlStr);
			return openApiRes;
		}

		// 验签
		boolean flag = SecretUtil.responseVerifyRSA(ListCommand.COMBINE_TRANS,
							responseFroadApi,
							OpenApiCommand.OPENAPI_PUBLICKEY);
		LogCvt.info("响应验签结果：" + flag);
		openApiRes.setXml("请求报文："+requestXmlStr+"    响应报文："+responseXmlStr);
		if(flag){
			if("1111".equals(responseFroadApi.getResultCode())){
				openApiRes.setResultDesc("未签约快捷银行卡，无法完成支付");
			}
			openApiRes.setResultCode(responseFroadApi.getResultCode());
			openApiRes.setResultDesc(responseFroadApi.getRemark());
			openApiRes.setFroadBillNo(responseFroadApi.getFroadBillNo());
			openApiRes.setPaymentURL(responseFroadApi.getPaymentURL());
			return openApiRes;
		}else{
			LogCvt.info("响应验签不通过");
			openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			openApiRes.setResultDesc("响应报文验签不通过");
			return openApiRes;
		}
	}


    @Override
    public OpenApiRes fastPaySign(SignApiReq openApiReq) throws AppException
    {
        LogCvt.info("开始进行<快捷支付-认证签约>功能,交互系统: <openapi>");
        String orderSubmitTime = DateUtil.currentTimeToString();
        OpenApiRes openApiRes = new OpenApiRes();
        SystemBean systemBean = new SystemBean();

        systemBean.setReqID(SystemCommand.FROAD_CBANK_PREFIX + orderSubmitTime);
        systemBean.setVersions(OpenApiCommand.OPENAPI_VERSION);
        systemBean.setPartnerID(openApiReq.getPartnerID());
        systemBean.setCharset(SystemCommand.CHARSET_UTF8);
        systemBean.setSignType(SIGN_TYPE);

        String signMsg = SecretUtil.fastCardSignEnc(openApiReq, systemBean);

        Map model = new HashMap();
        model.put("memberID", null == openApiReq.getMemberId() ? "" : openApiReq.getMemberId());
        model.put("accountName", null == openApiReq.getAccountName() ? "" : openApiReq.getAccountName());
        model.put("bankCardNo", null == openApiReq.getBankCardNo() ? "" : openApiReq.getBankCardNo());
        model.put("bankCardType", null == openApiReq.getBankCardType() ? "" : openApiReq.getBankCardType().getCode());
        model.put("certificateType", null == openApiReq.getCertificateType() ? "" : openApiReq.getCertificateType().getCode());
        model.put("certificateNo", null == openApiReq.getCertificateNo() ? "" : openApiReq.getCertificateNo());
        model.put("mobilePhone", null == openApiReq.getMobilePhone() ? "" : openApiReq.getMobilePhone());
        model.put("mobileToken", null == openApiReq.getMobileToken() ? "" : openApiReq.getMobileToken());
        model.put("payOrg", null == openApiReq.getPayOrg() ? "" : openApiReq.getPayOrg());
        model.put("singlePenLimit", null == openApiReq.getSinglePenLimit() ? "" : openApiReq.getSinglePenLimit());
        model.put("dailyLimit", null == openApiReq.getDailyLimit() ? "" : openApiReq.getDailyLimit());
        model.put("monthlyLimit", null == openApiReq.getMonthlyLimit() ? "" : openApiReq.getMonthlyLimit());
        model = setSystemData(model, systemBean, signMsg);
        String requestXmlStr = FreemarkerUtil.templateProcess("openapi/fast_card_sign.ftl", model);
        LogCvt.info("请求 xml：\n" + requestXmlStr);

        // 发送请求
        String responseXmlStr = null;
        try
        {
            responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.FAST_CARD_SIGN_URL, requestXmlStr);
        }
        catch (Exception e)
        {
            LogCvt.error("发送快捷支付-认证签约申请 异常", e);
            openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
            openApiRes.setResultDesc("发送快捷支付-认证签约申请异常");
            return openApiRes;
        }

        ResponseFroadApi responseFroadApi = null;
        try
        {
            Map<String, Class> xmlToBeanMap = new HashMap<String, Class>();
            xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
            responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
        }
        catch (Exception e)
        {
            LogCvt.error("解析响应时异常", e);
            openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
            openApiRes.setResultDesc("解析响应报文时异常");
            return openApiRes;
        }

        if("0000".equals(responseFroadApi.getResultCode())){
        	 // 验签
            boolean flag = SecretUtil.responseVerifyRSA(ListCommand.FAST_CARD_SIGN, responseFroadApi, OpenApiCommand.OPENAPI_PUBLICKEY);
            LogCvt.info("响应验签结果：" + flag);

            if (flag)
            {
                openApiRes.setResultCode(responseFroadApi.getResultCode());
                openApiRes.setResultDesc(responseFroadApi.getErrorMsg());
                openApiRes.setSignNo(responseFroadApi.getSignNo());//返回签约成功的协议号
                openApiRes.setSinglePenLimit(responseFroadApi.getSinglePenLimit());
                openApiRes.setDailyLimit(responseFroadApi.getDailyLimit());
                openApiRes.setMonthlyLimit(responseFroadApi.getMonthlyLimit());
                return openApiRes;
            }
            else
            {
                LogCvt.info("响应验签不通过");
                openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
                openApiRes.setResultDesc("响应报文验签不通过");
                return openApiRes;
            }
        }else{
        	 openApiRes.setResultCode(responseFroadApi.getResultCode());
             openApiRes.setResultDesc(responseFroadApi.getErrorMsg());
             return openApiRes;
        }
       
    }

    @Override
    public OpenApiRes fastPaySignCancel(SignCancelApiReq openApiReq) throws AppException
    {
        LogCvt.info("开始进行<快捷支付-认证解约>功能,交互系统: <openapi>");
        String orderSubmitTime = DateUtil.currentTimeToString();
        OpenApiRes openApiRes = new OpenApiRes();
        SystemBean systemBean = new SystemBean();

        systemBean.setReqID(SystemCommand.FROAD_CBANK_PREFIX + orderSubmitTime);
        systemBean.setVersions(OpenApiCommand.OPENAPI_VERSION);
        systemBean.setPartnerID(openApiReq.getPartnerID());
        systemBean.setCharset(SystemCommand.CHARSET_UTF8);
        systemBean.setSignType(SIGN_TYPE);

        String signMsg = SecretUtil.fastCardSignCancelEnc(openApiReq, systemBean);

        Map model = new HashMap();
        model.put("memberID", null == openApiReq.getMemberId() ? "" : openApiReq.getMemberId());
        model.put("bankCardNo", null == openApiReq.getBankCardNo() ? "" : openApiReq.getBankCardNo());
        model.put("payOrg", null == openApiReq.getPayOrg() ? "" : openApiReq.getPayOrg());
        model = setSystemData(model, systemBean, signMsg);
        String requestXmlStr = FreemarkerUtil.templateProcess("openapi/fast_card_sign_cancel.ftl", model);
        LogCvt.info("请求 xml：\n" + requestXmlStr);

        // 发送请求
        String responseXmlStr = null;
        try
        {
            responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.FAST_CARD_SIGN_CANCEL_URL, requestXmlStr);
        }
        catch (Exception e)
        {
            LogCvt.error("发送快捷支付-认证签约取消申请 异常", e);
            openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
            openApiRes.setResultDesc("发送快捷支付-认证签约取消申请异常");
            return openApiRes;
        }

        ResponseFroadApi responseFroadApi = null;
        try
        {
            Map<String, Class> xmlToBeanMap = new HashMap<String, Class>();
            xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
            responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
        }
        catch (Exception e)
        {
            LogCvt.error("解析响应时异常", e);
            openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
            openApiRes.setResultDesc("解析响应报文时异常");
            return openApiRes;
        }

        // 验签
        boolean flag = SecretUtil.responseVerifyRSA(ListCommand.FAST_CARD_SIGN_CANCEL, responseFroadApi, OpenApiCommand.OPENAPI_PUBLICKEY);
        LogCvt.info("响应验签结果：" + flag);

        if (flag)
        {
            openApiRes.setResultCode(responseFroadApi.getResultCode());
            openApiRes.setResultDesc(responseFroadApi.getErrorMsg());
            return openApiRes;
        }
        else
        {
            LogCvt.info("响应验签不通过");
            openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
            openApiRes.setResultDesc("响应报文验签不通过");
            return openApiRes;
        }
    }

    @Override
    public OpenApiRes fastPayMoblieToken(CreateMobileTokenApiReq openApiReq) throws AppException
    {
        LogCvt.info("开始进行<快捷支付-发送短信校验>功能,交互系统: <openapi>");
        String orderSubmitTime = DateUtil.currentTimeToString();
        OpenApiRes openApiRes = new OpenApiRes();
        SystemBean systemBean = new SystemBean();

        systemBean.setReqID(SystemCommand.FROAD_CBANK_PREFIX + orderSubmitTime);
        systemBean.setVersions(OpenApiCommand.OPENAPI_VERSION);
        systemBean.setPartnerID(openApiReq.getPartnerID());
        systemBean.setCharset(SystemCommand.CHARSET_UTF8);
        systemBean.setSignType(SIGN_TYPE);

        String signMsg = SecretUtil.fastCreateMobileTokenEnc(openApiReq, systemBean);

        Map model = new HashMap();
        model.put("bankCardNo", null == openApiReq.getBankCardNo() ? "" : openApiReq.getBankCardNo());
        model.put("mobilePhone", null == openApiReq.getMobilePhone() ? "" : openApiReq.getMobilePhone());
        model.put("type", null == openApiReq.getType() ? "" : openApiReq.getType().getCode());
        model.put("remark", null == openApiReq.getRemark() ? "" : openApiReq.getRemark());
        model.put("payOrg", null == openApiReq.getPayOrg() ? "" : openApiReq.getPayOrg());
        model = setSystemData(model, systemBean, signMsg);
        String requestXmlStr = FreemarkerUtil.templateProcess("openapi/fast_create_mobile_token.ftl", model);
        LogCvt.info("请求 xml：\n" + requestXmlStr);

        // 发送请求
        String responseXmlStr = null;
        try
        {
            responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.FAST_CREATE_MOBILE_TOCKEN_URL, requestXmlStr);
        }
        catch (Exception e)
        {
            LogCvt.error("发送快捷支付-发送短信校验申请 异常", e);
            openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
            openApiRes.setResultDesc("发送快捷支付-发送短信校验申请异常");
            return openApiRes;
        }

        ResponseFroadApi responseFroadApi = null;
        try
        {
            Map<String, Class> xmlToBeanMap = new HashMap<String, Class>();
            xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
            responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
        }
        catch (Exception e)
        {
            LogCvt.error("解析响应时异常", e);
            openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
            openApiRes.setResultDesc("解析响应报文时异常");
            return openApiRes;
        }

        // 验签
        boolean flag = SecretUtil.responseVerifyRSA(ListCommand.FAST_CREAT_MOBILE_TOCKEN, responseFroadApi, OpenApiCommand.OPENAPI_PUBLICKEY);
        LogCvt.info("响应验签结果：" + flag);

        if (flag)
        {
            openApiRes.setResultCode(responseFroadApi.getResultCode());
            openApiRes.setResultDesc(responseFroadApi.getRemark());
            return openApiRes;
        }
        else
        {
            LogCvt.info("响应验签不通过");
            openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
            openApiRes.setResultDesc("响应报文验签不通过");
            return openApiRes;
        }
    }

    @Override
    public OpenApiRes fastPaySendSMS(SendSmsApiReq openApiReq) throws AppException
    {
        LogCvt.info("开始进行<快捷支付-发送短信>功能,交互系统: <openapi>");
        String orderSubmitTime = DateUtil.currentTimeToString();
        OpenApiRes openApiRes = new OpenApiRes();
        SystemBean systemBean = new SystemBean();

        systemBean.setReqID(SystemCommand.FROAD_CBANK_PREFIX + orderSubmitTime);
        systemBean.setVersions(OpenApiCommand.OPENAPI_VERSION);
        systemBean.setPartnerID(openApiReq.getPartnerID());
        systemBean.setCharset(SystemCommand.CHARSET_UTF8);
        systemBean.setSignType(SIGN_TYPE);

        String signMsg = SecretUtil.fastSendSmsEnc(openApiReq, systemBean);

        Map model = new HashMap();

        model.put("mobilePhone", null == openApiReq.getMobilePhone() ? "" : openApiReq.getMobilePhone());
        model.put("createTime", openApiReq.getCreateTime());
        model.put("remark", null == openApiReq.getRemark() ? "" : openApiReq.getRemark());
        model.put("payOrg", null == openApiReq.getPayOrg() ? "" : openApiReq.getPayOrg());
        model = setSystemData(model, systemBean, signMsg);
        String requestXmlStr = FreemarkerUtil.templateProcess("openapi/fast_send_sms.ftl", model);
        LogCvt.info("请求 xml：\n" + requestXmlStr);

        // 发送请求
        String responseXmlStr = null;
        try
        {
            responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.FAST_SEND_SMS_URL, requestXmlStr);
        }
        catch (Exception e)
        {
            LogCvt.error("发送快捷支付-发送短信申请 异常", e);
            openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
            openApiRes.setResultDesc("发送快捷支付-发送短信申请异常");
            return openApiRes;
        }

        ResponseFroadApi responseFroadApi = null;
        try
        {
            Map<String, Class> xmlToBeanMap = new HashMap<String, Class>();
            xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
            responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
        }
        catch (Exception e)
        {
            LogCvt.error("解析响应时异常", e);
            openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
            openApiRes.setResultDesc("解析响应报文时异常");
            return openApiRes;
        }

        // 验签
        boolean flag = SecretUtil.responseVerifyRSA(ListCommand.FAST_SEND_SMS, responseFroadApi, OpenApiCommand.OPENAPI_PUBLICKEY);
        LogCvt.info("响应验签结果：" + flag);

        if (flag)
        {
            openApiRes.setResultCode(responseFroadApi.getResultCode());
            openApiRes.setResultDesc(responseFroadApi.getRemark());
            return openApiRes;
        }
        else
        {
            LogCvt.info("响应验签不通过");
            openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
            openApiRes.setResultDesc("响应报文验签不通过");
            return openApiRes;
        }
    }
 
    
    @Override
    public OpenApiRes setFastPayLimit(LimitReq req) throws AppException {
    	LogCvt.info("开始进行<快捷支付-设置限额>功能,交互系统: <openapi>");
    	
    	OpenApiRes openApiRes = new OpenApiRes();
    	String bankCardNo = req.getBankCardNo();
    	String singlePenLimit = req.getSinglePenLimit();
    	String dailyLimit = req.getDailyLimit();
    	String monthlyLimit = req.getMonthlyLimit();
    	String payOrg = req.getPayOrg();
		SystemBean systemBean = new SystemBean();
		String orderSubmitTime = DateUtil.currentTimeToString();
		systemBean.setReqID(SystemCommand.FROAD_CBANK_PREFIX + orderSubmitTime);
		systemBean.setVersions(OpenApiCommand.OPENAPI_VERSION);
		systemBean.setPartnerID(req.getPartnerID());
		systemBean.setCharset(SystemCommand.CHARSET_UTF8);
		systemBean.setSignType(SIGN_TYPE);
    	
		String signMsg = SecretUtil.fastSetLimitEnc(req, systemBean);

	    Map model = new HashMap();
	    model.put("bankCardNo", null == bankCardNo ? "" : bankCardNo);
        model.put("singlePenLimit", null == singlePenLimit ? "" : singlePenLimit);
        model.put("dailyLimit", null == dailyLimit ? "" : dailyLimit);
        model.put("monthlyLimit", null == monthlyLimit ? "" : monthlyLimit);
        model.put("payOrg", null == payOrg ? "" : payOrg);
        model.put("reqID", null == systemBean.getReqID() ? "" : systemBean.getReqID());
        model.put("partnerID", null == systemBean.getPartnerID() ? "" : systemBean.getPartnerID());
        model.put("charset", null == systemBean.getCharset() ? "" : systemBean.getCharset());
        model.put("signType", null == systemBean.getSignType() ? "" : systemBean.getSignType());
        model = setSystemData(model, systemBean, signMsg);
        String requestXmlStr = FreemarkerUtil.templateProcess("openapi/requst_set_limit_cash.ftl", model);
        LogCvt.info("请求 xml：\n" + requestXmlStr);
        
        // 发送请求
        String responseXmlStr = null;
        try
        {
            responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.FASET_SET_LIMIT_URL, requestXmlStr);
        }
        catch (Exception e)
        {
            LogCvt.error("发送快捷支付-设置限额异常", e);
            openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
            openApiRes.setResultDesc("发送快捷支付-发送设置限额异常");
            return openApiRes;
        }
        ResponseFroadApi responseFroadApi = null;
        try
        {
            Map<String, Class> xmlToBeanMap = new HashMap<String, Class>();
            xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
            responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
        }
        catch (Exception e)
        {
            LogCvt.error("解析响应时异常", e);
            openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
            openApiRes.setResultDesc("解析响应报文时异常");
            return openApiRes;
        }
        if("0000".equals(responseFroadApi.getResultCode())){
        	// 验签
            boolean flag = SecretUtil.responseVerifyRSA(ListCommand.FAST_SET_LIMIT, responseFroadApi, OpenApiCommand.OPENAPI_PUBLICKEY);
            LogCvt.info("响应验签结果：" + flag);

            if (flag)
            {
                openApiRes.setResultCode(responseFroadApi.getResultCode());
                openApiRes.setResultDesc(responseFroadApi.getErrorMsg());
                return openApiRes;
            }
            else
            {
                LogCvt.info("响应验签不通过");
                openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
                openApiRes.setResultDesc("响应报文验签不通过");
                return openApiRes;
            }
        }else{
        	openApiRes.setResultCode(responseFroadApi.getResultCode());
            openApiRes.setResultDesc(responseFroadApi.getErrorMsg());
            return openApiRes;
        }
        
    }

    /**
     * 设置系统参数值
     *
     * @param model      参数map
     * @param systemBean 系统数据bean
     * @param signMsg    加签数据
     * @return 数据map
     */
    private Map<String, Object> setSystemData(Map<String, Object> model, SystemBean systemBean, String signMsg)
    {
        model.put("reqID", null == systemBean.getReqID() ? "" : systemBean.getReqID());
        model.put("versions", null == systemBean.getVersions() ? "" : systemBean.getVersions());
        model.put("client", systemBean.getClient());
        model.put("partnerID", null == systemBean.getPartnerID() ? "" : systemBean.getPartnerID());
        model.put("charset", null == systemBean.getCharset() ? "" : systemBean.getCharset());
        model.put("signType", null == systemBean.getSignType() ? "" : systemBean.getSignType());
        model.put("signMsg", StringUtils.isBlank(signMsg) ? "" : signMsg);

        return model;
    }


	@Override
	public OpenApiRes query(QueryParamApiReq req){
		LogCvt.info("开始进行<账单查询>功能,交互系统: <openapi>");
    	
    	OpenApiRes openApiRes = new OpenApiRes();
    	String queryType = req.getQueryType();
    	String queryOrderType = req.getQueryOrderType();
    	String queryOrderID = req.getQueryOrderID();
    	String queryOrderState=req.getQueryOrderState();
    	String queryTime=req.getQueryTime();
    	String client=req.getClient();
    	
		SystemBean systemBean = new SystemBean();
		String orderSubmitTime = DateUtil.currentTimeToString();
		systemBean.setReqID(SystemCommand.FROAD_CBANK_PREFIX + orderSubmitTime);
		systemBean.setVersions(OpenApiCommand.OPENAPI_VERSION);
		systemBean.setClient(client);
		systemBean.setPartnerID(req.getPartnerID());
		systemBean.setCharset(SystemCommand.CHARSET_UTF8);
		systemBean.setSignType(SIGN_TYPE);
    	
		String signMsg = SecretUtil.query(req, systemBean);

	    Map model = new HashMap();
	    model.put("queryType", null == queryType ? "" : queryType);
        model.put("queryOrderType", null == queryOrderType ? "" : queryOrderType);
        model.put("queryOrderID", null == queryOrderID ? "" : queryOrderID);
        model.put("queryOrderState", null == queryOrderState ? "" : queryOrderState);
        model.put("queryTime", null == queryTime ? "" : queryTime);
        model = setSystemData(model, systemBean, signMsg);
        String requestXmlStr = FreemarkerUtil.templateProcess("openapi/request_query.ftl", model);
        LogCvt.info("请求 xml：\n" + requestXmlStr);
        
        // 发送请求
        String responseXmlStr = null;
        try
        {
            responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.QUERY_TRANS_URL, requestXmlStr);
        }
        catch (Exception e)
        {
            LogCvt.error("发送账单查询异常", e);
            openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
            openApiRes.setResultDesc("发送账单查询额异常");
            return openApiRes;
        }
        ResponseFroadApi responseFroadApi = null;
        try
        {
            Map<String, Class> xmlToBeanMap = new HashMap<String, Class>();
            xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
            xmlToBeanMap.put("queryParam", QueryParamApiReq.class);
            xmlToBeanMap.put("order", Order.class);
            responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
        }
        catch (Exception e)
        {
            LogCvt.error("解析响应时异常", e);
            openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
            openApiRes.setResultDesc("解析响应报文时异常");
            return openApiRes;
        }
        if("0000".equals(responseFroadApi.getSystem().getResultCode())){
        	// 验签
            boolean flag = SecretUtil.responseVerifyRSA(ListCommand.QUERY_TRANS, responseFroadApi, OpenApiCommand.OPENAPI_PUBLICKEY);
            LogCvt.info("响应验签结果：" + flag);

            if (flag)
            {
            	openApiRes.setResultCode(responseFroadApi.getSystem().getResultCode());
            	openApiRes.setOrders(responseFroadApi.getOrders());
                return openApiRes;
            }
            else
            {
                LogCvt.info("响应验签不通过");
                openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
                openApiRes.setResultDesc("响应报文验签不通过");
                return openApiRes;
            }
        }else{
        	openApiRes.setResultCode(responseFroadApi.getSystem().getResultCode());
            return openApiRes;
        }
        
	}


	@Override
	public OpenApiRes whiteListSet(OpenApiReq openApiReq) {
		
		LogCvt.info("开始进行<白名单设置>功能,交互系统: <openapi>");
		OpenApiRes openApiRes = new OpenApiRes();
		
		String payOrg = openApiReq.getPayOrg();
		String merchantID = openApiReq.getMerchantID();
		String merchantName = openApiReq.getMerchantName();
		String accountName = openApiReq.getAccountName();
		String accountNo = openApiReq.getAccountNo();
		String mac = openApiReq.getMac();
		String optionType = openApiReq.getOptionType();
		String requestTime = com.froad.util.DateUtil.formatDateTime(com.froad.util.DateUtil.DATE_TIME_FORMAT4, new Date());
		String client = openApiReq.getClient().getCode();
		
		String partnerID = openApiReq.getPartnerID();
		String reqID = REQ_ID_PREFIX + com.froad.util.DateUtil.formatDateTime(com.froad.util.DateUtil.DATE_FORMAT2, new Date()) + EsyT.simpleID();
		
		String signMsg = SecretUtil.setWhiteList(payOrg, merchantID, merchantName, accountName, accountNo, optionType, requestTime, reqID, partnerID,  SystemCommand.CHARSET_UTF8, SIGN_TYPE);
				

		Map model = new HashMap();
		model.put("payOrg",payOrg == null ? "" : payOrg);
		model.put("merchantID",merchantID ==null ? "":merchantID);
		model.put("merchantName", merchantName == null ? "":merchantName);
		model.put("accountName", accountName == null ? "" : accountName);
		model.put("accountNo", accountNo == null ? "": accountNo);
		model.put("mac", mac == null ? "" : mac);
		model.put("optionType", optionType == null ? "" : optionType);
		model.put("requestTime", requestTime);
		model.put("client",  client==null?"":client);
		model.put("versions", OpenApiCommand.OPENAPI_VERSION);
		model.put("partnerID", partnerID);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("reqID", reqID);
		model.put("signMsg", signMsg);

		String requestXmlStr = FreemarkerUtil.templateProcess("openapi/white_list_set.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);

		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.WHITE_LIST_SET_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送白名单设置 异常", e);
			openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
			openApiRes.setResultDesc("发送白名单设置  异常");
			openApiRes.setXml("请求报文："+requestXmlStr+"    响应报文："+responseXmlStr);
			throw new AppException(e);
		}

		ResponseFroadApi responseFroadApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
			responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
			openApiRes.setResultDesc("解析响应报文时异常");
			openApiRes.setXml("请求报文："+requestXmlStr+"    响应报文："+responseXmlStr);
			return openApiRes;
		}

		// 验签
		boolean flag = SecretUtil.responseVerifyRSA(ListCommand.WHITE_LIST,
							responseFroadApi,
							OpenApiCommand.OPENAPI_PUBLICKEY);
		LogCvt.info("响应验签结果：" + flag);
		openApiRes.setXml("请求报文："+requestXmlStr+"    响应报文："+responseXmlStr);
		if(flag){
			openApiRes.setResultCode(responseFroadApi.getResultCode());
			openApiRes.setResultDesc(responseFroadApi.getErrorMsg());
			openApiRes.setFroadBillNo(responseFroadApi.getFroadBillNo());
			openApiRes.setPaymentURL(responseFroadApi.getPaymentURL());
			return openApiRes;
		}else{
			LogCvt.info("响应验签不通过");
			openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			openApiRes.setResultDesc("响应报文验签不通过");
			return openApiRes;
		}
	}


	@Override
	public OpenApiRes employeeCodeVerify(OpenApiReq openApiReq) {
		LogCvt.info("开始进行<登录验密>功能,交互系统: <openapi>");
		
		OpenApiRes openApiRes = new OpenApiRes();
		String verifyOrg = openApiReq.getVerifyOrg();
		String employeeCode = openApiReq.getEmployeeCode();
		String password = openApiReq.getPassword();
		String verifyTime = com.froad.util.DateUtil.formatDateTime(com.froad.util.DateUtil.DATE_TIME_FORMAT4, new Date());
		
		String client = openApiReq.getClient().getCode();
		
		String partnerID = openApiReq.getPartnerID();
		String reqID = REQ_ID_PREFIX + com.froad.util.DateUtil.formatDateTime(com.froad.util.DateUtil.DATE_FORMAT2, new Date()) + EsyT.simpleID();
		
		String signMsg = SecretUtil.employeeCodeVerify(verifyOrg, employeeCode, password, verifyTime, reqID, partnerID, SIGN_TYPE);
		
		Map model = new HashMap();
		model.put("verifyOrg",verifyOrg == null ? "" : verifyOrg);
		model.put("employeeCode",employeeCode ==null ? "":employeeCode);
		model.put("password", password == null ? "":password);
		model.put("verifyTime", verifyTime == null ? "" : verifyTime);
		model.put("client",  client==null?"":client);
		model.put("versions", OpenApiCommand.OPENAPI_VERSION);
		model.put("partnerID", partnerID);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("reqID", reqID);
		model.put("signMsg", signMsg);
		
		String requestXmlStr = FreemarkerUtil.templateProcess("openapi/employee_code_verify.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);
		
		
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.EMPLOYEE_CODE_VERIFY_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送登录校验 异常", e);
			openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
			openApiRes.setResultDesc("发送登录校验  异常");
			openApiRes.setXml("请求报文："+requestXmlStr+"    响应报文："+responseXmlStr);
			throw new AppException(e);
		}
		
		ResponseFroadApi responseFroadApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
			responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
			openApiRes.setResultDesc("解析响应报文时异常");
			openApiRes.setXml("请求报文："+requestXmlStr+"    响应报文："+responseXmlStr);
			return openApiRes;
		}

		// 验签
		boolean flag = SecretUtil.responseVerifyRSA(ListCommand.CODE_VERIFY,
							responseFroadApi,
							OpenApiCommand.OPENAPI_PUBLICKEY);
		LogCvt.info("响应验签结果：" + flag);
		openApiRes.setXml("请求报文："+requestXmlStr+"    响应报文："+responseXmlStr);
		if(flag){
			openApiRes.setResultCode(responseFroadApi.getResultCode());
			openApiRes.setResultDesc(responseFroadApi.getErrorMsg());
			openApiRes.setVerifyResultCode(responseFroadApi.getVerifyResultCode());
			openApiRes.setVerifyResultContent(responseFroadApi.getVerifyResultContent());
			return openApiRes;
		}else{
			LogCvt.info("响应验签不通过");
			openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			openApiRes.setResultDesc("响应报文验签不通过");
			return openApiRes;
		}
	}
	
	@Override
	public OpenApiRes auditStatusQuery(OpenApiReq openApiReq) {
		LogCvt.info("开始进行<auditStatusQuery-审核状态查询>功能,交互系统: <openapi>");
		
		OpenApiRes openApiRes = new OpenApiRes();
		String bankGroup = StringUtils.defaultIfEmpty(openApiReq.getBankGroup(), "");
		String accountName = StringUtils.defaultIfEmpty(openApiReq.getAccountName(), "");
		String accountNo = StringUtils.defaultIfEmpty(openApiReq.getAccountNo(), "");
		String queryTime = com.froad.util.DateUtil.formatDateTime(com.froad.util.DateUtil.DATE_TIME_FORMAT4, new Date());

//		String client = StringUtils.defaultIfEmpty(openApiReq.getClient().getCode(), "");

		String partnerID = StringUtils.defaultIfEmpty(openApiReq.getPartnerID(), "");
		String charset = SystemCommand.CHARSET_UTF8;
		String reqID = REQ_ID_PREFIX + com.froad.util.DateUtil.formatDateTime(com.froad.util.DateUtil.DATE_FORMAT2, new Date()) + EsyT.simpleID();

		String signMsg = SecretUtil.auditStatusQuery(bankGroup, accountName, accountNo, queryTime, reqID, partnerID, charset, SIGN_TYPE);

		Map model = new HashMap();
		model.put("bankGroup", bankGroup);
		model.put("accountName", accountName);
		model.put("accountNo", accountNo);
		model.put("queryTime", queryTime);

		model.put("versions", OpenApiCommand.OPENAPI_VERSION);
		model.put("partnerID", partnerID);
		model.put("charset", charset);
		model.put("signType", SIGN_TYPE);
		model.put("reqID", reqID);
		model.put("signMsg", signMsg);
		
		String requestXmlStr = FreemarkerUtil.templateProcess("openapi/audit_status_query.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);
		
		
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.AUDIT_STATUS_QUERY_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送审核状态查询 异常", e);
			openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
			openApiRes.setResultDesc("发送审核状态查询  异常");
			openApiRes.setXml("请求报文："+requestXmlStr+"    响应报文："+responseXmlStr);
			throw new AppException(e);
		}
		
		ResponseFroadApi responseFroadApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
			responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
			openApiRes.setResultDesc("解析响应报文时异常");
			openApiRes.setXml("请求报文："+requestXmlStr+"    响应报文："+responseXmlStr);
			return openApiRes;
		}
		
		// 验签
		boolean flag = SecretUtil.responseVerifyRSA(ListCommand.AUDIT_STATUS_QUERY,
				responseFroadApi,
				OpenApiCommand.OPENAPI_PUBLICKEY);
		LogCvt.info("响应验签结果：" + flag);
		openApiRes.setXml("请求报文："+requestXmlStr+"    响应报文："+responseXmlStr);
		if(flag){
			openApiRes.setResultCode(responseFroadApi.getResultCode());
			openApiRes.setResultDesc(responseFroadApi.getErrorMsg());
			openApiRes.setQueryResultCode(responseFroadApi.getQueryResultCode());
			openApiRes.setQueryResultContent(responseFroadApi.getQueryResultContent());
			return openApiRes;
		}else{
			LogCvt.info("响应验签不通过");
			openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			openApiRes.setResultDesc("响应报文验签不通过");
			return openApiRes;
		}
	}


	@Override
	public OpenApiRes bankCardAccCheck(OpenApiReq openApiReq) {
		LogCvt.info("开始进行<bankCardAccCheck-银行卡校验>功能,交互系统: <openapi>");
		
		OpenApiRes openApiRes = new OpenApiRes();
		String bankGroup = StringUtils.defaultIfEmpty(openApiReq.getBankGroup(), "");
		String accountName = StringUtils.defaultIfEmpty(openApiReq.getAccountName(), "");
		String accountNo = StringUtils.defaultIfEmpty(openApiReq.getAccountNo(), "");
		String certificateType = StringUtils.defaultIfEmpty(openApiReq.getCertificateType(), "");
		String certificateNo = StringUtils.defaultIfEmpty(openApiReq.getCertificateNo(), "");
		String queryTime = com.froad.util.DateUtil.formatDateTime(com.froad.util.DateUtil.DATE_TIME_FORMAT4, new Date());

		String partnerID = StringUtils.defaultIfEmpty(openApiReq.getPartnerID(), "");
		String charset = SystemCommand.CHARSET_UTF8;
		String reqID = REQ_ID_PREFIX + com.froad.util.DateUtil.formatDateTime(com.froad.util.DateUtil.DATE_FORMAT2, new Date()) + EsyT.simpleID();

		String signMsg = SecretUtil.bankCardAccountCheck(bankGroup, accountName, accountNo, queryTime, reqID, partnerID, charset, SIGN_TYPE);

		Map model = new HashMap();
		model.put("checkOrg", bankGroup);
		model.put("accountName", accountName);
		model.put("accountNo", accountNo);
		model.put("accountName", accountName);
		model.put("certificateType", certificateType);
		model.put("certificateNo", certificateNo);
		model.put("queryTime", queryTime);
		model.put("versions", OpenApiCommand.OPENAPI_VERSION);
		model.put("partnerID", partnerID);
		model.put("charset", charset);
		model.put("signType", SIGN_TYPE);
		model.put("reqID", reqID);
		model.put("signMsg", signMsg);
		
		String requestXmlStr = FreemarkerUtil.templateProcess("openapi/bank_card_account_check.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);
		
		
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(OpenApiCommand.BANK_CARD_ACCOUNT_CHECK_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送审核状态查询 异常", e);
			openApiRes.setResultCode(RespCodeCommand.EXCEPTION);
			openApiRes.setResultDesc("发送审核状态查询  异常");
			openApiRes.setXml("请求报文："+requestXmlStr+"    响应报文："+responseXmlStr);
			throw new AppException(e);
		}
		
		ResponseFroadApi responseFroadApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseFroadApi", ResponseFroadApi.class);
			responseFroadApi = (ResponseFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			openApiRes.setResultCode(RespCodeCommand.INVALID_XML);
			openApiRes.setResultDesc("解析响应报文时异常");
			openApiRes.setXml("请求报文："+requestXmlStr+"    响应报文："+responseXmlStr);
			return openApiRes;
		}
		
		// 验签
		boolean flag = SecretUtil.responseVerifyRSA(ListCommand.AUDIT_STATUS_QUERY,
				responseFroadApi,
				OpenApiCommand.OPENAPI_PUBLICKEY);
		LogCvt.info("响应验签结果：" + flag);
		openApiRes.setXml("请求报文："+requestXmlStr+"    响应报文："+responseXmlStr);
		if(flag){
			openApiRes.setResultCode(responseFroadApi.getResultCode());
			openApiRes.setResultDesc(responseFroadApi.getErrorMsg());
			openApiRes.setCheckResultCode(responseFroadApi.getCheckResultCode());
			openApiRes.setCheckResultContent(responseFroadApi.getCheckResultContent());
			return openApiRes;
		}else{
			LogCvt.info("响应验签不通过");
			openApiRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			openApiRes.setResultDesc("响应报文验签不通过");
			return openApiRes;
		}
	}


}
