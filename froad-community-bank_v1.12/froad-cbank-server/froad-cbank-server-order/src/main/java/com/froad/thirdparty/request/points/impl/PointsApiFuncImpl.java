package com.froad.thirdparty.request.points.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.froad.logback.LogCvt;
import com.froad.thirdparty.bean.PointInfoDto;
import com.froad.thirdparty.bean.QueryInfoDto;
import com.froad.thirdparty.bean.TotalPointsInfosDto;
import com.froad.thirdparty.common.ListCommand;
import com.froad.thirdparty.common.PointsCommand;
import com.froad.thirdparty.common.RespCodeCommand;
import com.froad.thirdparty.common.SystemCommand;
import com.froad.thirdparty.dto.request.points.PartnerAccount;
import com.froad.thirdparty.dto.request.points.PointsAccount;
import com.froad.thirdparty.dto.request.points.PointsCard;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.dto.request.points.ResponseCashPointsApi;
import com.froad.thirdparty.dto.request.points.ResponseFillPointsApi;
import com.froad.thirdparty.dto.request.points.ResponseNotifyAccountApi;
import com.froad.thirdparty.dto.request.points.ResponseParAccountPointsApi;
import com.froad.thirdparty.dto.request.points.ResponsePayPointsApi;
import com.froad.thirdparty.dto.request.points.ResponsePresentPointsApi;
import com.froad.thirdparty.dto.request.points.ResponseQueryCardDepositApi;
import com.froad.thirdparty.dto.request.points.ResponseQueryOrderStatusApi;
import com.froad.thirdparty.dto.request.points.ResponseQueryPointsByMobileApi;
import com.froad.thirdparty.dto.request.points.ResponseRefundPointsApi;
import com.froad.thirdparty.dto.request.points.ResponseSendCheckCodeApi;
import com.froad.thirdparty.dto.request.points.ResponseValidateCheckCodeApi;
import com.froad.thirdparty.dto.response.points.BindAccountInfo;
import com.froad.thirdparty.dto.response.points.PointsApiQueryParams.StateCode;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.froad.thirdparty.dto.response.points.RespQueryProtocolApi;
import com.froad.thirdparty.dto.response.points.ResponseBindAccountApi;
import com.froad.thirdparty.dto.response.points.ResponseQueryRatioApi;
import com.froad.thirdparty.exception.AppException;
import com.froad.thirdparty.request.points.PointsApiFunc;
import com.froad.thirdparty.util.DateUtil;
import com.froad.thirdparty.util.FreemarkerUtil;
import com.froad.thirdparty.util.SecretUtil;
import com.froad.thirdparty.util.XMLStrHttpClientUtil;
import com.froad.thirdparty.util.XmlBeanUtil;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.EsyT;
import com.froad.util.payment.Logger;

public class PointsApiFuncImpl implements PointsApiFunc {

	private final static String SIGN_TYPE = SystemCommand.SIGNTYPE_RSA;//加密方式
	
	public PointsRes queryParAccountPointsCore(PointsReq pointsReq)throws AppException {
		
		PointsRes pointsRes=new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		String orgNo = pointsReq.getOrgNo();
		String accountMarked = pointsReq.getAccountMarked();
		String accountMarkedType = pointsReq.getAccountMarkedType();
		
		String signMsg = SecretUtil.parAccountPoints(accountMarked,accountMarkedType,partnerNo,SIGN_TYPE);
		Map model = new HashMap();
		model.put("orgNo",orgNo==null ? "":orgNo);
		model.put("accountMarked", accountMarked==null ? "":accountMarked);
		model.put("accountMarkedType", accountMarkedType==null ? "":accountMarkedType);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = FreemarkerUtil.templateProcess("points/request_paraccountpoints_api.ftl", model);

		LogCvt.info("请求 xml：\n" + requestXmlStr);

		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.QUERY_POINTS_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送查询积分时异常", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("发送查询积分时异常");
			return pointsRes;
		}

		// 解析
		ResponseParAccountPointsApi responseParAccountPointsApi = null;

		try {
			Map<String,Class> xmlToBeanMap=new HashMap<String,Class>();
			xmlToBeanMap.put("responseParAccountPointsApi", ResponseParAccountPointsApi.class);
			xmlToBeanMap.put("accountPointsInfo", PointsAccount.class);
			responseParAccountPointsApi = (ResponseParAccountPointsApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
			pointsRes.setResultDesc("解析响应报文时异常");
			return pointsRes;
		}

		// 验签
		boolean flag = false;
		if (responseParAccountPointsApi.getSystem().getSignType() != null && SystemCommand.SIGNTYPE_RSA.equals(responseParAccountPointsApi.getSystem().getSignType())) {
			flag = SecretUtil
					.responseVerifyRSA(ListCommand.QUERY_POINTS_RESPONSE,
							responseParAccountPointsApi,
							PointsCommand.POINTS_PUBLICKEY);
		}

		LogCvt.info("响应验签结果：" + flag);
		if(flag){
			
			if(RespCodeCommand.SUCCESS.equals(responseParAccountPointsApi.getSystem().getResultCode())){
				if(responseParAccountPointsApi.getAccountPointsInfos()==null || responseParAccountPointsApi.getAccountPointsInfos().size()==0){
					pointsRes.setResultCode(responseParAccountPointsApi.getSystem().getResultCode());
					pointsRes.setResultDesc("没有积分账户");
				}
				else{
					LogCvt.info("积分账户数："+responseParAccountPointsApi.getAccountPointsInfos().size());
					pointsRes.setResultCode(RespCodeCommand.SUCCESS);
					pointsRes.setAccountPointsInfoList(responseParAccountPointsApi.getAccountPointsInfos());
				}
			}else if(responseParAccountPointsApi.getSystem().getResultCode().equals("0025")){
				LogCvt.info("查询响应失败："+responseParAccountPointsApi.getSystem().getResultCode());
				pointsRes.setResultCode(responseParAccountPointsApi.getSystem().getResultCode());
				pointsRes.setResultDesc("会员不存在");
			}else{
				LogCvt.info("查询响应失败："+responseParAccountPointsApi.getSystem().getResultCode());
				pointsRes.setResultCode(responseParAccountPointsApi.getSystem().getResultCode());
				pointsRes.setResultDesc("积分查询失败");
			}
			return pointsRes;
		}
		else{
			LogCvt.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}
	}

	@Override
	public PointsRes queryParAccountPoints(PointsReq pointsReq)throws AppException {
		LogCvt.info("开始进行<查询积分>功能,交互系统: <points>");
		PointsRes res = queryParAccountPointsCore(pointsReq);
		if(RespCodeCommand.NO_POINT_ACOUNT.equals(res.getResultCode())){
			LogCvt.info("用户没有积分账户，自动创建联盟积分帐户");
			PointsReq req = new PointsReq(PointsCommand.FROAD_ORG_NO,
					pointsReq.getAccountMarked(),PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME,
					"", "自动开通积分账户", "1","开通积分账户", "0", pointsReq.getPartnerNo(),"创建积分账户");
			try {
				PointsRes pointsRes = presentPoints(req);
				LogCvt.info("开通积分账户结果：" + JSONObject.toJSONString(pointsRes));
			} catch (AppException e) {
				LogCvt.info("开通积分账户异常：", e);
			}
			return queryParAccountPointsCore(pointsReq);
		}else{
			return res;
		}
	}


	private String getRequestNo(String partnerNo,String objectNo){
		return partnerNo + com.froad.util.DateUtil.formatDateTime(com.froad.util.DateUtil.DATE_FORMAT2, new Date()) + EsyT.simpleID();
	}
	
	@Override
	public PointsRes consumePoints(PointsReq pointsReq) throws AppException {
		LogCvt.info("开始进行<消费积分>功能,交互系统: <points>");
		PointsRes pointsRes=new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		
		String orgNo = pointsReq.getOrgNo();
		String accountMarked =pointsReq.getAccountMarked();
		String accountMarkedType = PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME;
		String objectNo = pointsReq.getObjectNo();
		String objectDes = "积分消费支付单号："+objectNo;
		String objectType = PointsCommand.OBJECT_TYPE_CONSUME;	
		String accountId = pointsReq.getAccountId();
		String points = pointsReq.getPoints();
		String orgPoints = pointsReq.getOrgPoints();
		String businessType = pointsReq.getBusinessType();
		String remark = pointsReq.getRemark();
		String requestNo = getRequestNo(partnerNo,objectNo);
		String signMsg = SecretUtil.consumeOrRefundPointsPoints(objectNo, accountId, partnerNo,requestNo, SIGN_TYPE);
	
		Map model = new HashMap();
		model.put("accountMarked", accountMarked==null ? "":accountMarked);
		model.put("accountMarkedType", accountMarkedType==null ? "":accountMarkedType);
		model.put("orgNo",orgNo==null ? "":orgNo);
		model.put("objectNo",objectNo==null ? "":objectNo);
		model.put("objectDes", objectDes==null?"":objectDes);
		model.put("objectType", objectType==null?"":objectType);
		model.put("accountId", accountId==null?"":accountId);
		model.put("points", points==null?"":points);
		model.put("orgPoints", orgPoints==null?"":orgPoints);
		model.put("businessType", businessType);
		model.put("remark", remark==null?"":remark);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		model.put("requestNo", requestNo);
		String requestXmlStr = FreemarkerUtil.templateProcess("points/request_pay_points_api.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);

		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.CONSUME_POINTS_URL,requestXmlStr);
		} catch (Exception e) {
			throw new AppException(e);
//			LogCvt.error("发送消费积分时异常", e);
//			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
//			pointsRes.setResultDesc("发送消费积分时异常");
//			return pointsRes;
		}
		
		ResponsePayPointsApi responsePayPointsApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responsePayPointsApi", ResponsePayPointsApi.class);
			responsePayPointsApi = (ResponsePayPointsApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
			pointsRes.setResultDesc("解析响应报文时异常");
			return pointsRes;
		}
		
		// 验签
		boolean flag = false;
		if (responsePayPointsApi.getSystem().getSignType() != null && SystemCommand.SIGNTYPE_RSA.equals(responsePayPointsApi.getSystem().getSignType())) {
			flag = SecretUtil
					.responseVerifyRSA(ListCommand.CONSUME_POINTS,
							responsePayPointsApi,
							PointsCommand.POINTS_PUBLICKEY);
		}
		LogCvt.info("响应验签结果：" + flag);
		
		if(flag){
			String resultCode = responsePayPointsApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				pointsRes.setPayPointsNo(responsePayPointsApi.getPayPoints().getPayPointsNo());
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("消费积分成功");
			}else if("4005".equals(resultCode)){
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("当前用户积分不足");
			}else{
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc(responsePayPointsApi.getSystem().getErrorMsg());
			}
			return pointsRes;
		}else{
			LogCvt.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}		
	}


	@Override
	public PointsRes refundPoints(PointsReq pointsReq) throws AppException {
		LogCvt.info("开始进行<退还积分>功能,交互系统: <points>");
		PointsRes pointsRes=new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		
		String orgNo = pointsReq.getOrgNo();
		String accountMarked =pointsReq.getAccountMarked();
		String accountMarkedType = PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME;
		String objectNo = pointsReq.getObjectNo();
		String objectDes = pointsReq.getObjectDes();
		String objectType = pointsReq.getObjectType();	
		String accountId = pointsReq.getAccountId();
		String businessType=pointsReq.getBusinessType();
		String remark = pointsReq.getRemark();
		String payPointsNo = pointsReq.getPayPointsNo();
		String points = pointsReq.getPoints();
		String requestNo = getRequestNo(partnerNo,objectNo);
		String signMsg = SecretUtil.consumeOrRefundPointsPoints(objectNo, accountId, partnerNo,requestNo, SIGN_TYPE);
		
		Map model = new HashMap();
		model.put("accountMarked", accountMarked==null ? "":accountMarked);
		model.put("accountMarkedType", accountMarkedType==null ? "":accountMarkedType);
		model.put("orgNo",orgNo==null ? "":orgNo);
		model.put("payPointsNo", payPointsNo==null?"":payPointsNo);
		model.put("objectNo",objectNo==null ? "":objectNo);
		model.put("objectDes", objectDes==null?"":objectDes);
		model.put("objectType", objectType==null?"":objectType);
		model.put("accountId", accountId==null?"":accountId);
		model.put("points", points==null?"":points);
		model.put("businessType", businessType);
		model.put("remark", remark==null?"":remark);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		model.put("requestNo", requestNo);
		String requestXmlStr = FreemarkerUtil.templateProcess("points/request_refund_points_api.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);
		
		
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.REFUND_POINTS_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送消费积分时异常", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("发送消费积分时异常");
			return pointsRes;
		}
		
		ResponseRefundPointsApi responseRefundPointsApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseRefundPointsApi", ResponseRefundPointsApi.class);
			responseRefundPointsApi = (ResponseRefundPointsApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
			pointsRes.setResultDesc("解析响应报文时异常");
			return pointsRes;
		}
		
		// 验签
		boolean flag = false;
		if (responseRefundPointsApi.getSystem().getSignType() != null && SystemCommand.SIGNTYPE_RSA.equals(responseRefundPointsApi.getSystem().getSignType())) {
			flag = SecretUtil
					.responseVerifyRSA(ListCommand.REFUND_POINTS,
							responseRefundPointsApi,
							PointsCommand.POINTS_PUBLICKEY);
		}
		LogCvt.info("响应验签结果：" + flag);
		if(flag){
			String resultCode = responseRefundPointsApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				pointsRes.setRefundPointsNo(responseRefundPointsApi.getRefundPoints().getRefundPointsNo());
				pointsRes.setResultCode(resultCode);
			}else{
				pointsRes.setResultCode(resultCode);
			}
			pointsRes.setResultDesc(responseRefundPointsApi.getSystem().getErrorMsg());
			return pointsRes;
		}else{
			LogCvt.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}		
	}


	@Override
	public PointsRes presentPoints(PointsReq pointsReq) throws AppException {
		LogCvt.info("开始进行<赠送积分>功能,交互系统: <points>");
		PointsRes pointsRes=new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		
		String orgNo = pointsReq.getOrgNo();
		String accountMarked =pointsReq.getAccountMarked();
		String accountMarkedType = pointsReq.getAccountMarkedType();
		String objectNo = pointsReq.getObjectNo();
		String objectDes = pointsReq.getObjectDes();
		String objectType = pointsReq.getObjectType();	
		String businessType=pointsReq.getBusinessType();
		String remark = pointsReq.getRemark();
		String points = pointsReq.getPoints();
		String requestNo = getRequestNo(partnerNo,objectNo);
		
		String signMsg = SecretUtil.donatePoints(orgNo,points, accountMarked, partnerNo,requestNo, SIGN_TYPE);
		Map model = new HashMap();
		model.put("accountMarked", accountMarked==null ? "":accountMarked);
		model.put("accountMarkedType", accountMarkedType==null ? "":accountMarkedType);
		model.put("orgNo",orgNo==null ? "":orgNo);
		model.put("objectNo",objectNo==null ? "":objectNo);
		model.put("objectDes", objectDes==null?"":objectDes);
		model.put("objectType", objectType==null?"":objectType);
		model.put("points", points==null?"":points);
		model.put("businessType", businessType);
		model.put("remark", remark==null?"":remark);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		model.put("requestNo", requestNo);
		String requestXmlStr = FreemarkerUtil.templateProcess("points/request_present_points_api.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);
		
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.DONATE_POINTS_URL,requestXmlStr);
		} catch (Exception e) {
			throw new AppException(e);
//			LogCvt.error("发送赠送积分时异常", e);
//			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
//			pointsRes.setResultDesc("发送赠送积分时异常");
//			return pointsRes;
		}
		
		ResponsePresentPointsApi responsePresentPointsApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responsePresentPointsApi", ResponsePresentPointsApi.class);
			responsePresentPointsApi = (ResponsePresentPointsApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);		
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
			pointsRes.setResultDesc("解析响应报文时异常");
			return pointsRes;
		}
		
		// 验签
		boolean flag = false;
		if (responsePresentPointsApi.getSystem().getSignType() != null && SystemCommand.SIGNTYPE_RSA.equals(responsePresentPointsApi.getSystem().getSignType())) {
			flag = SecretUtil
					.responseVerifyRSA(ListCommand.DONATE_POINTS,
							responsePresentPointsApi,
							PointsCommand.POINTS_PUBLICKEY);
		}
		LogCvt.info("响应验签结果：" + flag);
		if(flag){
			String resultCode = responsePresentPointsApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				String presentPointsNo=responsePresentPointsApi.getPresentPointsInfo().getPresentPointsNo();
				pointsRes.setPresentPointsNo(presentPointsNo);
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("赠送积分成功");
			}else{
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc(responsePresentPointsApi.getSystem().getErrorMsg());
			}
			return pointsRes;
		}else{
			LogCvt.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}
	}


	@Override
	public PointsRes fillPoints(PointsReq pointsReq) throws AppException {
		LogCvt.info("开始进行<积兑充分>功能,交互系统: <points>");
		PointsRes pointsRes=new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		
		String orgNo = pointsReq.getOrgNo();
		String pointsCateNo = pointsReq.getPointsCateNo();
		String orgPoints = pointsReq.getOrgPoints();
		String phone = pointsReq.getPhone();
		String accountMarked =pointsReq.getAccountMarked();
		String accountMarkedType = pointsReq.getAccountMarkedType();
		String objectNo = pointsReq.getObjectNo();
		String objectDes = pointsReq.getObjectDes();
		String objectType = pointsReq.getObjectType();	
		String remark = pointsReq.getRemark();
		String requestNo = String.valueOf(System.currentTimeMillis());//当前时间，精确到毫秒
		String signMsg = SecretUtil.fillPoints(orgNo, pointsCateNo, orgPoints, phone, partnerNo, requestNo,SIGN_TYPE);
		
		Map model = new HashMap();
		model.put("accountMarked", accountMarked==null ? "":accountMarked);
		model.put("accountMarkedType", accountMarkedType==null ? "":accountMarkedType);
		model.put("orgNo",orgNo==null ? "":orgNo);
		model.put("objectNo",objectNo==null ? "":objectNo);
		model.put("objectDes", objectDes==null?"":objectDes);
		model.put("objectType", objectType==null?"":objectType);
		model.put("pointsCateNo", pointsCateNo==null?"":pointsCateNo);
		model.put("orgPoints", orgPoints==null?"":orgPoints);
		model.put("phone", phone==null?"":phone);
		model.put("requestNo", requestNo==null?"":requestNo);
		model.put("remark", remark==null?"":remark);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = FreemarkerUtil.templateProcess("points/request_fill_points_api.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);
		
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.FILL_POINTS_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送兑充积分时异常", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("发送兑充积分时异常");
			return pointsRes;
		}
		
		ResponseFillPointsApi responseFillPointsApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseFillPointsApi", ResponseFillPointsApi.class);
			responseFillPointsApi = (ResponseFillPointsApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);		
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
			pointsRes.setResultDesc("解析响应报文时异常");
			return pointsRes;
		}
		// 验签
		boolean flag = false;
		if (responseFillPointsApi.getSystem().getSignType() != null && SystemCommand.SIGNTYPE_RSA.equals(responseFillPointsApi.getSystem().getSignType())) {
			flag = SecretUtil
					.responseVerifyRSA(ListCommand.FILL_POINTS,
							responseFillPointsApi,
							PointsCommand.POINTS_PUBLICKEY);
		}
		LogCvt.info("响应验签结果：" + flag);
		if(flag){
			String resultCode = responseFillPointsApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("兑充积分成功");
			}else{
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("兑充积分失败");
			}
			return pointsRes;
		}else{
			LogCvt.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}
	}


	@Override
	public PointsRes applyForPointsToCash(PointsReq pointsReq) throws AppException {
		LogCvt.info("开始进行<积分提现申请>功能,交互系统: <points>");
		PointsRes pointsRes = new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		
		
		String orgNo = pointsReq.getOrgNo();
		String accountMarked =pointsReq.getAccountMarked();
		String accountMarkedType = pointsReq.getAccountMarkedType();
		
		String realName = pointsReq.getRealName();
		String bankId = pointsReq.getBankId(); 
		String bankName = pointsReq.getBankName();
		String bankCard = pointsReq.getBankCard();
		String certNo=pointsReq.getCertNo();
		String businessType = pointsReq.getBusinessType();
		String points = pointsReq.getPoints();
		
		String objectNo = pointsReq.getObjectNo();
		String objectDes = pointsReq.getObjectDes();
		String objectType = pointsReq.getObjectType();	
		String remark = pointsReq.getRemark();
		String requestNo = String.valueOf(System.currentTimeMillis());//当前时间，精确到毫秒
		String signMsg = SecretUtil.applyForPointsToCash(accountMarkedType, realName, bankId, bankCard, objectNo, orgNo, partnerNo, SIGN_TYPE);
		
		Map model = new HashMap();
		model.put("accountMarked", accountMarked);
		model.put("accountMarkedType", accountMarkedType);
		model.put("orgNo",orgNo);
		
		model.put("realName",realName);
		model.put("bankId", bankId);
		model.put("bankName", bankName);
		model.put("bankCard",bankCard);
		model.put("certType", PointsCommand.CERT_TYPE_ID);
		model.put("certNo", certNo);
		model.put("businessType", businessType);
		model.put("noticeUrl", PointsCommand.WITHDRAW_NOTICE_URL);
		model.put("points", points);		
		model.put("objectNo",objectNo);
		model.put("objectDes", objectDes);
		model.put("objectType", objectType);
		model.put("requestNo", requestNo);
		model.put("remark", remark);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = FreemarkerUtil.templateProcess("points/request_cash_points_api.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);
				
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.WITHDRAW_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送提现申请时异常", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("发送提现申请时异常");
			return pointsRes;
		}
		
		ResponseCashPointsApi responseCashPointsApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseCashPointsApi", ResponseCashPointsApi.class);
			responseCashPointsApi = (ResponseCashPointsApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);		
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
			pointsRes.setResultDesc("解析响应报文时异常");
			return pointsRes;
		}
		
		// 验签
		boolean flag = false;
		if (responseCashPointsApi.getSystem().getSignType() != null && SystemCommand.SIGNTYPE_RSA.equals(responseCashPointsApi.getSystem().getSignType())) {
			flag = SecretUtil
					.responseVerifyRSA(ListCommand.CASH_APP,
							responseCashPointsApi,
							PointsCommand.POINTS_PUBLICKEY);
		}
		LogCvt.info("响应验签结果：" + flag);
		
		if(flag){
			String resultCode = responseCashPointsApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("提现申请成功");
				String cashPointsNo=responseCashPointsApi.getCashPointsInfo().getCashPointsNo();
				pointsRes.setCashPointsNo(cashPointsNo);
			}else{
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("提现申请失败");
			}
			return pointsRes;
		}else{
			LogCvt.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}
	}


	@Override
	public PointsRes queryPointsExchageRate(PointsReq pointsReq)throws AppException {
		LogCvt.info("开始进行<积分比例查询>功能,交互系统: <points>");
		PointsRes pointsRes = new PointsRes();
		String orgNo = pointsReq.getOrgNo();
		String requestNo = String.valueOf(System.currentTimeMillis());//当前时间，精确到毫秒
		String partnerNo = pointsReq.getPartnerNo();
		
		String signMsg = SecretUtil.queryPointsExchageRate(orgNo, partnerNo, SIGN_TYPE);
		Map model = new HashMap();
		model.put("orgNo",orgNo);
		model.put("requestNo", requestNo);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = FreemarkerUtil.templateProcess("points/reques_query_bank_rule_api.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);
		
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.WITHDRAW_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送查询积分比例时异常", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("发送查询积分比例时异常");
			return pointsRes;
		}
		
		return null;
	}

	@Override
	public PointsRes notifyAccountRelation(PointsReq pointsReq)
			throws AppException {
		LogCvt.info("开始进行<签约关系通知>功能,交互系统: <points>");
		PointsRes pointsRes = new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		String accountMarked =pointsReq.getAccountMarked();
		String accountMarkedType = pointsReq.getAccountMarkedType();
		String realName = pointsReq.getRealName();
		String bankId = pointsReq.getBankId(); 
		String bankName = pointsReq.getBankName();
		String bankCard = pointsReq.getBankCard();
		String identityNo=pointsReq.getIdentityNo();
		String mobileNum=pointsReq.getMobileNum();
		String cardType=pointsReq.getCardType();
		String protocolNo=pointsReq.getProtocolNo();
		
		String requestNo = String.valueOf(System.currentTimeMillis());//当前时间，精确到毫秒
		String signMsg = SecretUtil.notifyAccountRelation( accountMarked, identityNo, mobileNum, realName, bankId, bankCard,  protocolNo, partnerNo, SIGN_TYPE);
		
		Map model = new HashMap();
		model.put("accountMarked", accountMarked);
		model.put("accountMarkedType", accountMarkedType);
		model.put("identityNo",identityNo);
		model.put("mobileNum",mobileNum);
		
		model.put("realName",realName);
		model.put("bankId", bankId);
		model.put("bankName", bankName);
		model.put("bankCard",bankCard);
		model.put("cardType",cardType);
		model.put("protocolNo",protocolNo);
		
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = FreemarkerUtil.templateProcess("points/request_notify_account_points_api.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);
				
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.NOTIFY_RELATION_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送签约关系通知时异常", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("发送签约关系通知时异常");
			return pointsRes;
		}
		
		ResponseNotifyAccountApi  responseNotifyAccountApi= null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseNotifyAccountApi", ResponseNotifyAccountApi.class);
			xmlToBeanMap.put("bindAccountInfo", BindAccountInfo.class);
			
			responseNotifyAccountApi = (ResponseNotifyAccountApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);		
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
			pointsRes.setResultDesc("解析响应报文时异常");
			return pointsRes;
		}
		
		// 验签
		boolean flag = false;
		if (responseNotifyAccountApi.getSystem().getSignType() != null && SystemCommand.SIGNTYPE_RSA.equals(responseNotifyAccountApi.getSystem().getSignType())) {
			flag = SecretUtil.responseVerifyRSA(ListCommand.NOTIFY_ACCOUNT,
							responseNotifyAccountApi,
							PointsCommand.POINTS_PUBLICKEY);
		}
		LogCvt.info("响应验签结果：" + flag);
		
		if(flag){
			String resultCode = responseNotifyAccountApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				String memberAccountNo=responseNotifyAccountApi.getPartnerAccount().getAccountMarked();
				String bankno=responseNotifyAccountApi.getBindAccountInfo().getBankCard();
				String bankid=responseNotifyAccountApi.getBindAccountInfo().getBankId();
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("签约关系通知成功");
				LogCvt.info("签约关系通知成功,会员标识："+memberAccountNo+",签约银行卡号："+bankno+",签约银行编号："+bankid);
			}else{
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("签约关系通知失败");
				LogCvt.info("签约关系通知失败");
			}
			return pointsRes;
		}else{
			LogCvt.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}
	}


	
	@Override
	public PointsRes queryDeposit(PointsReq pointsReq) throws AppException {
		LogCvt.info("开始进行<充值卡信息查询接口>功能,交互系统: <points>");
		PointsRes pointsRes = new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		String cardPassword=pointsReq.getCardPassword().trim().replace(" ", "");
		String cardNo=pointsReq.getCardNo();
		
		String requestNo = String.valueOf(System.currentTimeMillis());//当前时间，精确到毫秒
		
		Map model = new HashMap();
		model.put("partnerNo", partnerNo);
		model.put("cardPassword", cardPassword);
		model.put("cardNo", cardNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", "");//此查询接口不需要验签
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = FreemarkerUtil.templateProcess("points/reques_query_card_deposit_api.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);
				
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.QUERY_DEPOSIT_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送充值卡信息查询时异常", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("发送充值卡信息查询时异常");
			return pointsRes;
		}
		ResponseQueryCardDepositApi  responseQueryCardDepositApi= null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseQueryCardDepositApi", ResponseQueryCardDepositApi.class);
			xmlToBeanMap.put("pointsCard", PointsCard.class);
			responseQueryCardDepositApi = (ResponseQueryCardDepositApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);		
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
			pointsRes.setResultDesc("解析响应报文时异常");
			return pointsRes;
		}
		
		String resultCode = responseQueryCardDepositApi.getSystem().getResultCode();
		if(RespCodeCommand.SUCCESS.equals(resultCode)){
			pointsRes.setPointsCard(responseQueryCardDepositApi.getPointsCard());
			pointsRes.setResultCode(resultCode);
			pointsRes.setResultDesc("充值卡信息查询成功");
			LogCvt.info("充值卡信息查询成功");
		}else{
			pointsRes.setResultCode(resultCode);
			pointsRes.setResultDesc("无效的充值卡信息");
			LogCvt.info("充值卡信息查询失败，响应码："+resultCode);
		}
		return pointsRes;
		
	}


	@Override
	public PointsRes deposit(PointsReq pointsReq) throws AppException {
		LogCvt.info("开始进行<账户充值接口>功能,交互系统: <points>");
		PointsRes pointsRes = new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		String accountMarked =pointsReq.getAccountMarked();
		String accountMarkedType = pointsReq.getAccountMarkedType();
		String cardPassword=pointsReq.getCardPassword().trim().replace(" ", "");
		
		String requestNo = String.valueOf(System.currentTimeMillis());//当前时间，精确到毫秒
		String signMsg= SecretUtil.deposit(accountMarkedType, cardPassword, partnerNo, SIGN_TYPE);
		Map model = new HashMap();
		model.put("accountMarked", accountMarked);
		model.put("accountMarkedType", accountMarkedType);
		model.put("partnerNo", partnerNo);
		model.put("cardPassword", cardPassword);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = FreemarkerUtil.templateProcess("points/reques_card_deposit_api.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);
				
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.CARD_DEPOSIT_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送账户充值时异常", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("发送账户充值时异常");
			return pointsRes;
		}
		
		ResponseQueryCardDepositApi  responseQueryCardDepositApi= null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseCardDepositApi", ResponseQueryCardDepositApi.class);
			xmlToBeanMap.put("partnerAccount", PartnerAccount.class);
			xmlToBeanMap.put("pointsCard", PointsCard.class);
			
			responseQueryCardDepositApi = (ResponseQueryCardDepositApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);		
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
			pointsRes.setResultDesc("解析响应报文时异常");
			return pointsRes;
		}
		// 验签
		boolean flag = false;
		if (responseQueryCardDepositApi.getSystem().getSignType() != null && SystemCommand.SIGNTYPE_RSA.equals(responseQueryCardDepositApi.getSystem().getSignType())) {
			flag = SecretUtil.responseVerifyRSA(ListCommand.DEPOSIT,responseQueryCardDepositApi,PointsCommand.POINTS_PUBLICKEY);
		}
		LogCvt.info("响应验签结果：" + flag);
		if(flag){
			String resultCode = responseQueryCardDepositApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				String memberAccountNo=responseQueryCardDepositApi.getPartnerAccount().getAccountMarked();
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("账户充值成功");
				pointsRes.setPointsCard(responseQueryCardDepositApi.getPointsCard());
				LogCvt.info("账户充值成功,会员标识："+memberAccountNo);
			}else{
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("账户充值失败");
				LogCvt.info("账户充值失败,响应码："+resultCode);
			}
			return pointsRes;
		}else{
			LogCvt.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}
	}

	@Override
	public PointsRes sendCheckCode(PointsReq pointsReq) throws AppException {
		LogCvt.info("=======消费银行积分前，发送验证码=======");
		PointsRes pointsRes=new PointsRes();
		String accountMarkedType = PointsCommand.ACCOUNT_MARKED_TYPE_PHONE;
		String accountMarked =pointsReq.getAccountMarked();
		String orgNo = pointsReq.getOrgNo();
		String points=pointsReq.getPoints();
		String partnerNo = pointsReq.getPartnerNo();
		
		String signMsg = SecretUtil.sendCheckCode(accountMarked, orgNo, points, partnerNo);
	
		Map model = new HashMap();
		model.put("accountMarked", accountMarked);
		model.put("accountMarkedType", accountMarkedType);
		model.put("orgNo",orgNo);
		model.put("points", points);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = FreemarkerUtil.templateProcess("points/send_check_code_api.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);

		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.BANK_MSG_CODE_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送验证码异常", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("发送验证码异常");
			return pointsRes;
		}
		ResponseSendCheckCodeApi responseSendCheckCodeApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseBankMsgCodeApi", ResponseSendCheckCodeApi.class);
			responseSendCheckCodeApi = (ResponseSendCheckCodeApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
			pointsRes.setResultDesc("解析响应报文时异常");
			return pointsRes;
		}
		
		// 验签
		boolean flag = SecretUtil
					.responseVerifyRSA(ListCommand.SEND_CHECK_CODE,
							responseSendCheckCodeApi,
							PointsCommand.POINTS_PUBLICKEY);
		LogCvt.info("响应验签结果：" + flag);
		if(flag){
			String resultCode = responseSendCheckCodeApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				pointsRes.setCheckResult(responseSendCheckCodeApi.getCheckResult());
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("验证码发送成功");
			}else{
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc(responseSendCheckCodeApi.getSystem().getErrorMsg());
			}
			return pointsRes;
		}else{
			LogCvt.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}		
	}

	@Override
	public PointsRes validateCheckCode(PointsReq pointsReq) throws AppException {
		LogCvt.info("============消费积分，开始校验验证码============");
		PointsRes pointsRes=new PointsRes();
		String accountMarkedType = PointsCommand.ACCOUNT_MARKED_TYPE_PHONE;
		String accountMarked =pointsReq.getAccountMarked();
		String checkCode=pointsReq.getCheckCode();
		String orgNo = pointsReq.getOrgNo();
		String partnerNo = pointsReq.getPartnerNo();
		
		String signMsg = SecretUtil.validateCheckCode(accountMarked, checkCode, orgNo, partnerNo);
	
		Map model = new HashMap();
		model.put("accountMarked", accountMarked);
		model.put("accountMarkedType", accountMarkedType);
		model.put("checkCode", checkCode);
		model.put("orgNo",orgNo);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = FreemarkerUtil.templateProcess("points/validate_check_code_api.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);

		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.VALIDATE_CHECK_CODE_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("校验银行验证码时异常", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("校验银行验证码异常");
			return pointsRes;
		}
		
		ResponseValidateCheckCodeApi responseValidateCheckCodeApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseValidateCheckCodeApi", ResponseValidateCheckCodeApi.class);
			responseValidateCheckCodeApi = (ResponseValidateCheckCodeApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
			pointsRes.setResultDesc("解析响应报文时异常");
			return pointsRes;
		}
		
		// 验签
		boolean flag = SecretUtil
					.responseVerifyRSA(ListCommand.VALIDATE_CHECK_CODE,
							responseValidateCheckCodeApi,
							PointsCommand.POINTS_PUBLICKEY);
		LogCvt.info("响应验签结果：" + flag);
		
		if(flag){
			String resultCode = responseValidateCheckCodeApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("验证码校验通过");
			}else if("4102".equals(resultCode)){
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("验证码错误");
			}else if("4103".equals(resultCode)){
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("验证码已过期");
			}else{
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("验证码校验异常");
			}
			return pointsRes;
		}else{
			LogCvt.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}		

	}

	@Override
	public PointsRes queryBankPointsByMobile(PointsReq pointsReq)
			throws AppException {
		LogCvt.info("============按手机号查询银行积分==============");
		PointsRes pointsRes=new PointsRes();
		String mobilePhone=pointsReq.getMobileNum();
		String orgNo = pointsReq.getOrgNo();
		String partnerNo = pointsReq.getPartnerNo();
		
		String signMsg = SecretUtil.queryBankPointsByMobile(mobilePhone, orgNo, partnerNo);
	
		Map model = new HashMap();
		model.put("mobilePhone", mobilePhone);
		model.put("orgNo",orgNo);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = FreemarkerUtil.templateProcess("points/query_bank_points_by_mobile_api.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);

		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.QUERY_BANK_POINTS_BY_MOBILE_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("按手机号查询银行积分异常", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("查询银行积分异常");
			return pointsRes;
		}
		ResponseQueryPointsByMobileApi responseQueryPointsByMobileApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseQueryPointsByMobileApi", ResponseQueryPointsByMobileApi.class);
			xmlToBeanMap.put("accountPointsInfo", PointsAccount.class);
			responseQueryPointsByMobileApi = (ResponseQueryPointsByMobileApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
			pointsRes.setResultDesc("解析响应报文时异常");
			return pointsRes;
		}
		
		// 验签
		boolean flag =SecretUtil
					.responseVerifyRSA(ListCommand.QUERY_BANK_POINTS,
							responseQueryPointsByMobileApi,
							PointsCommand.POINTS_PUBLICKEY);
		LogCvt.info("响应验签结果：" + flag);
		
		if(flag){
			String resultCode = responseQueryPointsByMobileApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				PointsAccount account=responseQueryPointsByMobileApi.getAccountPointsInfo();
				pointsRes.setPointsAccount(account);
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("银行积分查询成功");
			}else if("4001".equals(resultCode)){
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("该手机号没有银行积分账户");
			}else{
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("银行卡号不存在");
			}
			return pointsRes;
		}else{
			LogCvt.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}		

	}

	@Override
	public PointsRes payPointsByMobile(PointsReq pointsReq) throws AppException {
		LogCvt.info("=====按手机号消费积分=======");
		PointsRes pointsRes=new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		String orgNo = pointsReq.getOrgNo();
		String accountMarked =pointsReq.getAccountMarked();
		String accountMarkedType = PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME;
		String objectNo = pointsReq.getObjectNo();
		String objectDes = pointsReq.getObjectDes();
		String objectType = PointsCommand.OBJECT_TYPE_CONSUME;	
		String accountId = pointsReq.getAccountId();
		String points = pointsReq.getPoints();
		String orgPoints = pointsReq.getOrgPoints();
		String businessType=pointsReq.getBusinessType();
		String remark=pointsReq.getRemark();
		String mobilePhone=pointsReq.getMobileNum();
		
		String requestNo = getRequestNo(partnerNo,objectNo);
		
		String signMsg = SecretUtil.payPointsByMobile(objectNo, accountId, partnerNo,requestNo, SIGN_TYPE);
	
		Map model = new HashMap();
		model.put("accountMarked", accountMarked);
		model.put("accountMarkedType", accountMarkedType);
		model.put("orgNo",orgNo);
		model.put("objectNo",objectNo);
		model.put("objectDes", objectDes);
		model.put("objectType", objectType);
		model.put("accountId", accountId);
		model.put("points", points);
		model.put("orgPoints", orgPoints);
		model.put("businessType", businessType);
		model.put("remark", remark);
		model.put("mobilePhone", mobilePhone);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		model.put("requestNo", requestNo);
		String requestXmlStr = FreemarkerUtil.templateProcess("points/request_pay_points_by_mobile_api.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);

		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.PAY_POINTS_BY_MOBILE_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("发送消费积分时异常(按手机号消费)", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("消费积分异常");
			return pointsRes;
		}
		
		ResponsePayPointsApi responsePayPointsApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responsePayPointsApi", ResponsePayPointsApi.class);
			responsePayPointsApi = (ResponsePayPointsApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
			pointsRes.setResultDesc("解析响应报文时异常");
			return pointsRes;
		}
		
		// 验签
		boolean flag =SecretUtil
					.responseVerifyRSA(ListCommand.CONSUME_POINTS,
							responsePayPointsApi,
							PointsCommand.POINTS_PUBLICKEY);
		LogCvt.info("响应验签结果：" + flag);
		
		if(flag){
			String resultCode = responsePayPointsApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				pointsRes.setPayPointsNo(responsePayPointsApi.getPayPoints().getPayPointsNo());
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("消费积分成功");
			}else if("4005".equals(resultCode)){
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("积分不足");
			}else{
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc(responsePayPointsApi.getSystem().getErrorMsg());
			}
			return pointsRes;
		}else{
			LogCvt.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}

	}

	@Override
	public PointsRes queryRatio(PointsReq pointsReq) {
		LogCvt.info("=====查询积分比例=======");
		PointsRes pointsRes=new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		String orgNo = pointsReq.getOrgNo();
		String signMsg = SecretUtil.queryRatio(orgNo, partnerNo);	
		Map model = new HashMap();
		model.put("orgNo",orgNo);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = FreemarkerUtil.templateProcess("points/query_ratio_api.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);

		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.QUERY_RATIO_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("查询积分比例时出现异常", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("查询积分比例异常");
			return pointsRes;
		}
		
		ResponseQueryRatioApi responseQueryRatioApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseQueryRatioApi", ResponseQueryRatioApi.class);
			responseQueryRatioApi = (ResponseQueryRatioApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
			pointsRes.setResultDesc("解析响应报文时异常");
			return pointsRes;
		}
		
		// 验签
		boolean flag =SecretUtil
					.responseVerifyRSA(ListCommand.QUERY_RATIO,
							responseQueryRatioApi,
							PointsCommand.POINTS_PUBLICKEY);
		LogCvt.info("响应验签结果：" + flag);
		
		if(flag){
			String resultCode = responseQueryRatioApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				pointsRes.setExchangeRate(responseQueryRatioApi.getExchangeRate());
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("积分比例查询成功");
			}else{
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc(responseQueryRatioApi.getErrorMsg());
				LogCvt.error("积分比例查询失败，机构号："+orgNo+"，结果码："+resultCode+"，响应消息："+pointsRes.getResultDesc());
			}
			return pointsRes;
		}else{
			LogCvt.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}
	}

	@Override
	public PointsRes bindBankAccount(PointsReq pointsReq) throws AppException{
		LogCvt.info("=====银行账户签约接口=======");
		PointsRes pointsRes=new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		String accountMarked =pointsReq.getAccountMarked();
		String accountMarkedType = PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME;
		String identityNo=pointsReq.getIdentityNo();//身份证
		String mobileNum=pointsReq.getMobileNum();
		String realName=pointsReq.getRealName();
		String  bankId=pointsReq.getBankId();
		String  bankName=pointsReq.getBankName();
		String  bankCard=pointsReq.getBankCard();
		String  cardType=pointsReq.getCardType();
		String  extend1=pointsReq.getExtend1();
		String  extend2=pointsReq.getExtend2();
		
		String signMsg = SecretUtil.bindBankAccount(accountMarked, identityNo, mobileNum, realName,bankId,bankCard,partnerNo,SIGN_TYPE);
		Map model = new HashMap();
		
		model.put("accountMarked",accountMarked);
		model.put("accountMarkedType",accountMarkedType);
		model.put("identityNo",identityNo);
		model.put("mobileNum",mobileNum);
		model.put("realName",realName);
		model.put("bankId",bankId);
		model.put("bankName",bankName);
		model.put("bankCard",bankCard);
		model.put("cardType",cardType);
		model.put("extend1",extend1);
		model.put("extend2",extend2);
		model.put("cardType",cardType);
		//系统参数
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = FreemarkerUtil.templateProcess("points/bind_bank_bccount.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);

		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.BIND_BANK_ACCOUNT_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("银行账户签约时出现异常", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("银行账户签约异常");
			return pointsRes;
		}
		
		ResponseBindAccountApi responseBindAccountApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseBindAccountApi", ResponseBindAccountApi.class);
			xmlToBeanMap.put("partnerAccount", PartnerAccount.class);
			xmlToBeanMap.put("bindAccountInfo", BindAccountInfo.class);
			responseBindAccountApi = (ResponseBindAccountApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
			pointsRes.setResultDesc("解析响应报文时异常");
			return pointsRes;
		}
		
		// 验签
		boolean flag =SecretUtil.responseVerifyRSA(ListCommand.BING_BANK_ACCOUNT,responseBindAccountApi,PointsCommand.POINTS_PUBLICKEY);
		LogCvt.info("响应验签结果：" + flag);
		
		if(flag){
			String resultCode = responseBindAccountApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				pointsRes.setBindAccountInfo(responseBindAccountApi.getBindAccountInfo());
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("银行账户签约成功");
			}else{
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc(responseBindAccountApi.getSystem().getErrorMsg());
				LogCvt.error("银行账户签约失败，结果码："+resultCode+"，响应消息："+pointsRes.getResultDesc());
			}
			return pointsRes;
		}else{
			LogCvt.info("银行账户签约响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("银行账户签约响应报文验签不通过");
			return pointsRes;
		}
	}

	@Override
	public PointsRes unBindBankAccount(PointsReq pointsReq) throws AppException{
		LogCvt.info("=====银行账户解约接口=======");
		PointsRes pointsRes=new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		String orgNo = pointsReq.getOrgNo();
		String accountMarked =pointsReq.getAccountMarked();
		String accountMarkedType = PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME;
		String  bankCard=pointsReq.getBankCard();
		
		String signMsg = SecretUtil.unbindBankAccount(accountMarked, bankCard, partnerNo, SIGN_TYPE);
		
		Map model = new HashMap();
		model.put("accountMarked",accountMarked);
		model.put("accountMarkedType",accountMarkedType);
		model.put("bankCard",bankCard);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = FreemarkerUtil.templateProcess("points/unbind_bank_bccount.ftl", model);
		LogCvt.info("请求 xml：\n" + requestXmlStr);

		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.UNBIND_BANK_ACCOUNT_URL,requestXmlStr);
		} catch (Exception e) {
			LogCvt.error("银行账户解约时出现异常", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("银行账户解约异常");
			return pointsRes;
		}
		
		ResponseBindAccountApi responseBindAccountApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responseBindAccountApi", ResponseBindAccountApi.class);
			xmlToBeanMap.put("partnerAccount", PartnerAccount.class);
			xmlToBeanMap.put("bindAccountInfo", BindAccountInfo.class);
			responseBindAccountApi = (ResponseBindAccountApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		} catch (Exception e) {
			LogCvt.error("解析响应时异常", e);
			pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
			pointsRes.setResultDesc("解析响应报文时异常");
			return pointsRes;
		}
		
		// 验签
		boolean flag =SecretUtil.responseVerifyRSA(ListCommand.UNBING_BANK_ACCOUNT,responseBindAccountApi,PointsCommand.POINTS_PUBLICKEY);
		LogCvt.info("银行账户解约响应验签结果：" + flag);
		
		if(flag){
			String resultCode = responseBindAccountApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				BindAccountInfo bindAccountInfo=responseBindAccountApi.getBindAccountInfo();
				pointsRes.setBindAccountInfo(bindAccountInfo);
				if(bindAccountInfo!=null && "0".equals(bindAccountInfo.getStatus())){
					pointsRes.setResultCode(resultCode);
					pointsRes.setResultDesc("银行账户解约成功");
				}else{
					pointsRes.setResultCode(resultCode);
					pointsRes.setResultDesc("银行账户解约失败");
//					pointsRes.setResultDesc(responseBindAccountApi.getSystem().getErrorMsg());
					LogCvt.error("====银行账户解约失败====,结果码："+resultCode+"，响应消息："+pointsRes.getResultDesc());
				}
			}else{
				pointsRes.setResultCode(resultCode);
				String errorMsg = responseBindAccountApi.getSystem().getErrorMsg();
				if(StringUtils.isNotBlank(errorMsg)){
					pointsRes.setResultDesc(errorMsg);
				}else{
					pointsRes.setResultDesc("银行账户解约失败");
				}
				LogCvt.error("银行账户解约失败结果码："+resultCode+"，响应消息："+pointsRes.getResultDesc());
			}
			return pointsRes;
		}else{
			LogCvt.info("银行账户解约响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("银行账户解约响应报文验签不通过");
			return pointsRes;
		}
	}
	@Override
    public PointsRes getMyPointsTrans(PointsReq pointsReq) throws AppException {
//        return allInOneListQuery(pointsReq);
      return twoStepsListQuery(pointsReq);
    }
    
    private PointsRes twoStepsListQuery(PointsReq pointsReq) {
        LogCvt.info("=====积分消费接口,分页查询接口=======");
        PointsRes pointsRes=new PointsRes();
        String partnerNo = pointsReq.getPartnerNo();
        String orgNo = pointsReq.getOrgNo();
        String accountMarked =pointsReq.getAccountMarked();
        String accountMarkedType = PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME;
        String protocolNo=pointsReq.getProtocolNo();
        String protocolType=pointsReq.getProtocolType()!=null?pointsReq.getProtocolType().getCode():"";
        String fromTime=pointsReq.getFromTime();
        String toTime=pointsReq.getToTime();
        Integer pageSize=pointsReq.getPageSize();
        Integer pageNum=pointsReq.getPageNum();
        String bankCard=pointsReq.getBankCard();
        String signMsg = SecretUtil.queryMemberProtocols(orgNo, protocolType, accountMarked,partnerNo,SIGN_TYPE);
        
        Map model = new HashMap();
        model.put("orgNo", orgNo);
        model.put("protocolNo", protocolNo);
        model.put("protocolType", protocolType);
        model.put("fromTime", fromTime);
        model.put("toTime", toTime);
        
        
        model.put("accountMarked",accountMarked);
        model.put("accountMarkedType",accountMarkedType);
        
        model.put("partnerNo", partnerNo);
        model.put("charset", SystemCommand.CHARSET_UTF8);
        model.put("signType", SIGN_TYPE);
        model.put("signMsg", signMsg);
        model.put("requestTime", DateUtil.formatDate2Str());
        
        // 发送请求
        String responseXmlStrTotal = null , responseXmlStrList = null;
        try {//发送请求查询
            String requestXmlStr = FreemarkerUtil.templateProcess("points/query_member_protocols.ftl", model);
            LogCvt.info("请求 xml查询积分消费汇总信息：\n" + requestXmlStr);
            responseXmlStrTotal = XMLStrHttpClientUtil.httpPost(PointsCommand.TRADE_POINT_STOTALS_URL,requestXmlStr);
        } catch (Exception e) {
            LogCvt.error("积分消费接口,查询汇总信息时出现异常", e);
            pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
            pointsRes.setResultDesc("积分消费接口,查询汇总信息时出现异");
            return pointsRes;
        }
        
        try {
            model.put("pageSize", pageSize);
            model.put("pageNum", pageNum);
            
            String requestXmlStr = FreemarkerUtil.templateProcess("points/query_member_protocols.ftl", model);
            LogCvt.info("请求 xml查询积分消费列表信息：\n" + requestXmlStr);
            responseXmlStrList = XMLStrHttpClientUtil.httpPost(PointsCommand.QUERY_TRADE_DETAILS_URL,requestXmlStr);
        } catch (Exception e) {
            LogCvt.error("积分消费接口,分页查询时出现异常", e);
            pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
            pointsRes.setResultDesc("积分消费接口,分页查询异常");
            return pointsRes;
        }
        
        Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
        xmlToBeanMap.put("respQueryDetailsApi", RespQueryProtocolApi.class);
        xmlToBeanMap.put("respQueryProtocolApi", RespQueryProtocolApi.class);
        xmlToBeanMap.put("queryInfo", QueryInfoDto.class);
        xmlToBeanMap.put("totalPointsInfo", TotalPointsInfosDto.class);
        xmlToBeanMap.put("protocolInfo", PointInfoDto.class);
        
        RespQueryProtocolApi respQueryProtocolApi = null;
        try {
            respQueryProtocolApi = (RespQueryProtocolApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStrList);
        } catch (Exception e) {
            LogCvt.error("解析分页响应时异常", e);
            pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
            pointsRes.setResultDesc("解析响应报文时异常");
            return pointsRes;
        }
        try {
            RespQueryProtocolApi temp = (RespQueryProtocolApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStrTotal);
            if(temp!=null&&respQueryProtocolApi!=null){
                respQueryProtocolApi.setTotalPointsInfos(temp.getTotalPointsInfos());
            }
        } catch (Exception e) {
            LogCvt.error("解析分页数据响应时异常", e);
            pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
            pointsRes.setResultDesc("解析响应报文时异常");
            return pointsRes;
        }
        
        // 验签
        boolean flag =SecretUtil.responseVerifyRSA(ListCommand.QUERY_MEMBER_PROTOCOL,respQueryProtocolApi,PointsCommand.POINTS_PUBLICKEY);
        LogCvt.info("积分消费接口,分页查询响应验签结果：" + flag);
        
        if(flag){
            String resultCode = respQueryProtocolApi.getSystem().getResultCode();
            if(RespCodeCommand.SUCCESS.equals(resultCode)){
                pointsRes.setResultCode(resultCode);
                pointsRes.setQueryInfo(respQueryProtocolApi.getQueryInfo());
                pointsRes.setTotalPointsInfos(respQueryProtocolApi.getTotalPointsInfos());
                pointsRes.setProtocolInfos(respQueryProtocolApi.getProtocolInfos());
            }else{
                pointsRes.setResultCode(resultCode);
                pointsRes.setResultDesc("积分消费接口,分页查询失败");
                LogCvt.error("积分消费接口,分页查询失败结果码："+resultCode+"，响应消息："+pointsRes.getResultDesc());
            }
            return pointsRes;
        }else{
            LogCvt.info("积分消费接口,分页查询响应验签不通过");
            pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
            pointsRes.setResultDesc("积分消费接口,分页查询响应报文验签不通过");
            return pointsRes;
        }
    }

    private PointsRes allInOneListQuery(PointsReq pointsReq) {
        LogCvt.info("=====积分消费接口,分页查询接口=======");
        PointsRes pointsRes=new PointsRes();
        String partnerNo = pointsReq.getPartnerNo();
        String orgNo = pointsReq.getOrgNo();
        String accountMarked =pointsReq.getAccountMarked();
        String accountMarkedType = PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME;
        String protocolNo=pointsReq.getProtocolNo();
        String protocolType=pointsReq.getProtocolType()!=null?pointsReq.getProtocolType().getCode():"";
        String fromTime=pointsReq.getFromTime();
        String toTime=pointsReq.getToTime();
        Integer pageSize=pointsReq.getPageSize();
        Integer pageNum=pointsReq.getPageNum();
        String bankCard=pointsReq.getBankCard();
        String signMsg = SecretUtil.queryMemberProtocols(orgNo, protocolType, accountMarked,partnerNo,SIGN_TYPE);
        
        Map model = new HashMap();
        model.put("orgNo", orgNo);
        model.put("protocolNo", protocolNo);
        model.put("protocolType", protocolType);
        model.put("fromTime", fromTime);
        model.put("toTime", toTime);
        model.put("pageSize", pageSize);
        model.put("pageNum", pageNum);
        
        
        model.put("accountMarked",accountMarked);
        model.put("accountMarkedType",accountMarkedType);
        
        model.put("partnerNo", partnerNo);
        model.put("charset", SystemCommand.CHARSET_UTF8);
        model.put("signType", SIGN_TYPE);
        model.put("signMsg", signMsg);
        model.put("requestTime", DateUtil.formatDate2Str());
        String requestXmlStr = FreemarkerUtil.templateProcess("points/query_member_protocols.ftl", model);
        LogCvt.info("请求 xml：\n" + requestXmlStr);

        // 发送请求
        String responseXmlStr = null;
        try {
            responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.QUERY_MEMBER_PROTOCOL_URL,requestXmlStr);
        } catch (Exception e) {
            LogCvt.error("积分消费接口,分页查询时出现异常", e);
            pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
            pointsRes.setResultDesc("积分消费接口,分页查询异常");
            return pointsRes;
        }
        
        RespQueryProtocolApi respQueryProtocolApi = null;
        try {
            Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
            xmlToBeanMap.put("respQueryProtocolApi", RespQueryProtocolApi.class);
            xmlToBeanMap.put("queryInfo", QueryInfoDto.class);
            xmlToBeanMap.put("totalPointsInfo", TotalPointsInfosDto.class);
            xmlToBeanMap.put("protocolInfo", PointInfoDto.class);
            
            respQueryProtocolApi = (RespQueryProtocolApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
        } catch (Exception e) {
            LogCvt.error("解析响应时异常", e);
            pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
            pointsRes.setResultDesc("解析响应报文时异常");
            return pointsRes;
        }
        
        // 验签1
        boolean flag =SecretUtil.responseVerifyRSA(ListCommand.QUERY_MEMBER_PROTOCOL,respQueryProtocolApi,PointsCommand.POINTS_PUBLICKEY);
        LogCvt.info("积分消费接口,分页查询响应验签结果：" + flag);
        
        if(flag){
            String resultCode = respQueryProtocolApi.getSystem().getResultCode();
            if(RespCodeCommand.SUCCESS.equals(resultCode)){
                pointsRes.setResultCode(resultCode);
                pointsRes.setQueryInfo(respQueryProtocolApi.getQueryInfo());
                pointsRes.setTotalPointsInfos(respQueryProtocolApi.getTotalPointsInfos());
                pointsRes.setProtocolInfos(respQueryProtocolApi.getProtocolInfos());
            }else{
                pointsRes.setResultCode(resultCode);
                pointsRes.setResultDesc("积分消费接口,分页查询失败");
                LogCvt.error("积分消费接口,分页查询失败结果码："+resultCode+"，响应消息："+pointsRes.getResultDesc());
            }
            return pointsRes;
        }else{
            LogCvt.info("积分消费接口,分页查询响应验签不通过");
            pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
            pointsRes.setResultDesc("积分消费接口,分页查询响应报文验签不通过");
            return pointsRes;
        }
    }
    
    @Override
    public PointsRes queryOrderStatus(PointsReq request) throws AppException{
    	
    	 LogCvt.info("=====积分订单状态主动查询开始=======");
         PointsRes pointsRes=new PointsRes();
         
         String orderId= request.getObjectNo();
         String orderType = request.getOrderType();
         String partnerNo = request.getPartnerNo();
         String charset = SystemCommand.CHARSET_UTF8;
         String signType = SIGN_TYPE;
         String signMsg = SecretUtil.queryOrderStatus(orderId,orderType,partnerNo,SIGN_TYPE);
         
         Map<String,Object> model = new HashMap<String,Object>(7);
         model.put("queryOrderID", orderId);
         model.put("queryOrderType", orderType);
         model.put("partnerNo", partnerNo);
         model.put("charset",charset);
         model.put("signType", signType);
         model.put("signMsg", signMsg);
         model.put("requestTime", DateUtil.formatDate2Str());
         
         String requestXmlStr = FreemarkerUtil.templateProcess("points/query_order_status_api.ftl", model);
         LogCvt.info("请求 xml：\n" + requestXmlStr);

         // 发送请求
         String responseXmlStr = null;
         try {
             responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.QUERY_ORDER_STATUS_URL,requestXmlStr);
         } catch (Exception e) {
        	 throw new AppException(e);
         }
         
         
         ResponseQueryOrderStatusApi requestQueryOrderStatusApi = null;
         try {
             Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
             xmlToBeanMap.put("responseQueryOrderStatusApi", ResponseQueryOrderStatusApi.class);
             
             requestQueryOrderStatusApi = (ResponseQueryOrderStatusApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
         } catch (Exception e) {
             LogCvt.error("解析响应时异常", e);
             pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
             pointsRes.setResultDesc("解析响应报文时异常");
             return pointsRes;
         }
         
         // 验签1
         boolean flag =SecretUtil.responseVerifyRSA(ListCommand.QUERY_ORDER_STATUS,requestQueryOrderStatusApi,PointsCommand.POINTS_PUBLICKEY);
         LogCvt.info("积分订单状态查询接口响应验签结果：" + flag);
         
         if(flag){
             String resultCode = requestQueryOrderStatusApi.getSystem().getResultCode();
             if(resultCode.equals("0026")){//订单不存在
            	 resultCode = RespCodeCommand.SUCCESS;
            	 requestQueryOrderStatusApi.getQueryParam().setStateCode(StateCode.not_exists);
             }
             
             if(RespCodeCommand.SUCCESS.equals(resultCode)){
                 pointsRes.setResultCode(resultCode);
                 pointsRes.setOrderStatus(requestQueryOrderStatusApi);
             }else{
                 pointsRes.setResultCode(resultCode);
                 pointsRes.setResultDesc("主动查询订单状态失败");
                 LogCvt.error("主动查询订单状态失败结果码："+resultCode+"，响应消息："+pointsRes.getResultDesc());
             }
             return pointsRes;
         }else{
             LogCvt.info("主动查询订单状态响应验签不通过");
             pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
             pointsRes.setResultDesc("主动查询订单状态失败响应报文验签不通过");
             return pointsRes;
         }
    }

	@Override
	public PointsRes contractRelationshipQuery(PointsReq pointsReq) {
		
		LogCvt.info("=====查询用户积分签约关系=======");
		
        PointsRes pointsRes = new PointsRes();
        String accountMarked = pointsReq.getAccountMarked();
        String accountMarkedType = PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME;
        String signMsg = SecretUtil.contractRelationshipQuery(accountMarked,SIGN_TYPE);
        
        Map model = new HashMap();
        
        model.put("accountMarked",accountMarked);
        model.put("accountMarkedType",accountMarkedType);
        model.put("charset", SystemCommand.CHARSET_UTF8);
        model.put("signType", SIGN_TYPE);
        model.put("signMsg", signMsg);
        model.put("requestTime", DateUtil.formatDate2Str());
        String requestXmlStr = FreemarkerUtil.templateProcess("points/contractRelationshipQuery.ftl", model);
        LogCvt.info("请求 xml：\n" + requestXmlStr);

        // 发送请求
        String responseXmlStr = null;
        try {
            responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.CONTRACT_RELATIONSHIP_QUERY_URL,requestXmlStr);
        } catch (Exception e) {
            LogCvt.error("积分消费接口,分页查询时出现异常", e);
            pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
            pointsRes.setResultDesc("积分消费接口,分页查询异常");
            return pointsRes;
        }
        
        RespQueryProtocolApi responseBindAccountApi = null;
        try {
            Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
            xmlToBeanMap.put("partnerAccount", PartnerAccount.class);
            xmlToBeanMap.put("bindAccountInfo", com.froad.thirdparty.bean.BindAccountInfo.class);
            xmlToBeanMap.put("responseBindAccountApi", RespQueryProtocolApi.class);
            responseBindAccountApi = (RespQueryProtocolApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
        } catch (Exception e) {
        	 LogCvt.error("查询积分签约关系异常", e);
             pointsRes.setResultCode(RespCodeCommand.INVALID_XML);
             pointsRes.setResultDesc("解析响应报文时异常");
             return pointsRes;
        }
        
        String resultCode = responseBindAccountApi.getSystem().getResultCode();
        if(RespCodeCommand.SUCCESS.equals(resultCode)){
            pointsRes.setResultCode(resultCode);
            List<com.froad.thirdparty.bean.BindAccountInfo> infos = responseBindAccountApi.getBindAccountInfos();
            if(infos != null && infos.size() != 0){
            	pointsRes.setResultDesc(infos.get(0).getProtocolNo());
            }else{
            	pointsRes.setResultDesc("");
            }
        }else{
            pointsRes.setResultCode(resultCode);
            pointsRes.setResultDesc("查询会员积分签约关系失败");
            LogCvt.error("积分消费接口,分页查询失败结果码："+resultCode+"，响应消息："+pointsRes.getResultDesc());
        }
        return pointsRes;
	}
	
}