package com.froad.fft.thirdparty.request.points.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.froad.fft.common.AppException;
import com.froad.fft.thirdparty.common.ListCommand;
import com.froad.fft.thirdparty.common.PointsCommand;
import com.froad.fft.thirdparty.common.RespCodeCommand;
import com.froad.fft.thirdparty.common.SystemCommand;
import com.froad.fft.thirdparty.dto.request.points.PointsAccount;
import com.froad.fft.thirdparty.dto.request.points.PointsReq;
import com.froad.fft.thirdparty.dto.request.points.PointsRes;
import com.froad.fft.thirdparty.dto.request.points.ResponseCashPointsApi;
import com.froad.fft.thirdparty.dto.request.points.ResponseFillPointsApi;
import com.froad.fft.thirdparty.dto.request.points.ResponseParAccountPointsApi;
import com.froad.fft.thirdparty.dto.request.points.ResponsePayPointsApi;
import com.froad.fft.thirdparty.dto.request.points.ResponsePresentPointsApi;
import com.froad.fft.thirdparty.dto.request.points.ResponseRefundPointsApi;
import com.froad.fft.thirdparty.request.points.PointsFunc;
import com.froad.fft.thirdparty.util.SecretUtil;
import com.froad.fft.thirdparty.util.XMLStrHttpClientUtil;
import com.froad.fft.util.DateUtil;
import com.froad.fft.util.FreeMarkerUtil;
import com.froad.fft.util.XmlBeanUtil;

@Component("pointsFuncImpl")
public class PointsFuncImpl implements PointsFunc {

	private final static Logger logger = Logger.getLogger(PointsFuncImpl.class);

	private final static String SIGN_TYPE = SystemCommand.SIGNTYPE_RSA;//加密方式
	
	@Resource
	private FreeMarkerUtil freeMarkerUtil;


	@Override
	public PointsRes queryParAccountPoints(PointsReq pointsReq)throws AppException {
		logger.info("开始进行<查询积分>功能,交互系统: <points>");
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
		String requestXmlStr = freeMarkerUtil.getContent("points/request_paraccountpoints_api.ftl", model);

		logger.info("请求 xml：\n" + requestXmlStr);

		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.QUERY_POINTS_URL,requestXmlStr);
		} catch (Exception e) {
			logger.error("发送查询积分时异常", e);
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
			logger.error("解析响应时异常", e);
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

		logger.info("响应验签结果：" + flag);
		if(flag){
			if(RespCodeCommand.SUCCESS.equals(responseParAccountPointsApi.getSystem().getResultCode())){
				if(responseParAccountPointsApi.getAccountPointsInfos()==null || responseParAccountPointsApi.getAccountPointsInfos().size()==0){
					pointsRes.setResultCode(responseParAccountPointsApi.getSystem().getResultCode());
					pointsRes.setResultDesc("没有积分账户");
				}
				else{
					logger.info("积分账户数："+responseParAccountPointsApi.getAccountPointsInfos().size());
					pointsRes.setResultCode(RespCodeCommand.SUCCESS);
					pointsRes.setAccountPointsInfoList(responseParAccountPointsApi.getAccountPointsInfos());
				}
			}else{
				logger.info("查询响应失败："+responseParAccountPointsApi.getSystem().getResultCode());
				pointsRes.setResultCode(responseParAccountPointsApi.getSystem().getResultCode());
				pointsRes.setResultDesc("积分查询失败");
			}
			return pointsRes;
		}
		else{
			logger.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}
	}


	@Override
	public PointsRes consumePoints(PointsReq pointsReq) throws AppException {
		logger.info("开始进行<消费积分>功能,交互系统: <points>");
		PointsRes pointsRes=new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		
		String orgNo = pointsReq.getOrgNo();
		String accountMarked =pointsReq.getAccountMarked();
		String accountMarkedType = PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME;
		String objectNo = pointsReq.getObjectNo();
		String objectDes = pointsReq.getObjectDes();
		String objectType = PointsCommand.OBJECT_TYPE_CONSUME;	
		String accountId = pointsReq.getAccountId();
		String remark = pointsReq.getRemark();
		String points = pointsReq.getPoints();
		String signMsg = SecretUtil.consumeOrRefundPointsPoints(objectNo, accountId, partnerNo, SIGN_TYPE);
	
		Map model = new HashMap();
		model.put("accountMarked", accountMarked==null ? "":accountMarked);
		model.put("accountMarkedType", accountMarkedType==null ? "":accountMarkedType);
		model.put("orgNo",orgNo==null ? "":orgNo);
		model.put("objectNo",objectNo==null ? "":objectNo);
		model.put("objectDes", objectDes==null?"":objectDes);
		model.put("objectType", objectType==null?"":objectType);
		model.put("accountId", accountId==null?"":accountId);
		model.put("points", points==null?"":points);
		model.put("remark", remark==null?"":remark);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = freeMarkerUtil.getContent("points/request_pay_points_api.ftl", model);
		logger.info("请求 xml：\n" + requestXmlStr);

		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.CONSUME_POINTS_URL,requestXmlStr);
		} catch (Exception e) {
			logger.error("发送消费积分时异常", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("发送消费积分时异常");
			return pointsRes;
		}
		
		ResponsePayPointsApi responsePayPointsApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responsePayPointsApi", ResponsePayPointsApi.class);
			responsePayPointsApi = (ResponsePayPointsApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);
		
		} catch (Exception e) {
			logger.error("解析响应时异常", e);
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
		logger.info("响应验签结果：" + flag);
		
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
				pointsRes.setResultDesc("消费积分失败");
			}
			return pointsRes;
		}else{
			logger.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}		
	}


	@Override
	public PointsRes refundPoints(PointsReq pointsReq) throws AppException {
		logger.info("开始进行<退还积分>功能,交互系统: <points>");
		PointsRes pointsRes=new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		
		String orgNo = pointsReq.getOrgNo();
		String accountMarked =pointsReq.getAccountMarked();
		String accountMarkedType = PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME;
		String objectNo = pointsReq.getObjectNo();
		String objectDes = pointsReq.getObjectDes();
		String objectType = pointsReq.getObjectType();	
		String accountId = pointsReq.getAccountId();
		String remark = pointsReq.getRemark();
		String payPointsNo = pointsReq.getPayPointsNo();
		String points = pointsReq.getPoints();
		String signMsg = SecretUtil.consumeOrRefundPointsPoints(objectNo, accountId, partnerNo, SIGN_TYPE);
		
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
		model.put("remark", remark==null?"":remark);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = freeMarkerUtil.getContent("points/request_refund_points_api.ftl", model);
		logger.info("请求 xml：\n" + requestXmlStr);
		
		
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.REFUND_POINTS_URL,requestXmlStr);
		} catch (Exception e) {
			logger.error("发送消费积分时异常", e);
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
			logger.error("解析响应时异常", e);
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
		logger.info("响应验签结果：" + flag);
		if(flag){
			String resultCode = responseRefundPointsApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				pointsRes.setPayPointsNo(responseRefundPointsApi.getRefundPoints().getRefundPointsNo());
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("退还积分成功");
			}else{
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("退还积分失败");
			}
			return pointsRes;
		}else{
			logger.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}		
	}


	@Override
	public PointsRes donatePoints(PointsReq pointsReq) throws AppException {
		logger.info("开始进行<赠送积分>功能,交互系统: <points>");
		PointsRes pointsRes=new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		
		String orgNo = pointsReq.getOrgNo();
		String accountMarked =pointsReq.getAccountMarked();
		String accountMarkedType = pointsReq.getAccountMarkedType();
		String objectNo = pointsReq.getObjectNo();
		String objectDes = pointsReq.getObjectDes();
		String objectType = pointsReq.getObjectType();	
		String remark = pointsReq.getRemark();
		String points = pointsReq.getPoints();
		
		String signMsg = SecretUtil.donatePoints(orgNo,points, accountMarked, partnerNo, SIGN_TYPE);
		Map model = new HashMap();
		model.put("accountMarked", accountMarked==null ? "":accountMarked);
		model.put("accountMarkedType", accountMarkedType==null ? "":accountMarkedType);
		model.put("orgNo",orgNo==null ? "":orgNo);
		model.put("objectNo",objectNo==null ? "":objectNo);
		model.put("objectDes", objectDes==null?"":objectDes);
		model.put("objectType", objectType==null?"":objectType);
		model.put("points", points==null?"":points);
		model.put("remark", remark==null?"":remark);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = freeMarkerUtil.getContent("points/request_present_points_api.ftl", model);
		logger.info("请求 xml：\n" + requestXmlStr);
		
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.DONATE_POINTS_URL,requestXmlStr);
		} catch (Exception e) {
			logger.error("发送赠送积分时异常", e);
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc("发送赠送积分时异常");
			return pointsRes;
		}
		
		ResponsePresentPointsApi responsePresentPointsApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("responsePresentPointsApi", ResponsePresentPointsApi.class);
			responsePresentPointsApi = (ResponsePresentPointsApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);		
		} catch (Exception e) {
			logger.error("解析响应时异常", e);
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
		logger.info("响应验签结果：" + flag);
		if(flag){
			String resultCode = responsePresentPointsApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("赠送积分成功");
			}else{
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("赠送积分失败");
			}
			return pointsRes;
		}else{
			logger.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}
	}


	@Override
	public PointsRes fillPoints(PointsReq pointsReq) throws AppException {
		logger.info("开始进行<积兑充分>功能,交互系统: <points>");
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
		String requestXmlStr = freeMarkerUtil.getContent("points/request_fill_points_api.ftl", model);
		logger.info("请求 xml：\n" + requestXmlStr);
		
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.FILL_POINTS_URL,requestXmlStr);
		} catch (Exception e) {
			logger.error("发送兑充积分时异常", e);
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
			logger.error("解析响应时异常", e);
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
		logger.info("响应验签结果：" + flag);
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
			logger.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}
	}


	@Override
	public PointsRes applyForPointsToCash(PointsReq pointsReq) throws AppException {
		logger.info("开始进行<积分提现申请>功能,交互系统: <points>");
		PointsRes pointsRes = new PointsRes();
		String partnerNo = pointsReq.getPartnerNo();
		
		
		String orgNo = pointsReq.getOrgNo();
		String accountMarked =pointsReq.getAccountMarked();
		String accountMarkedType = pointsReq.getAccountMarkedType();
		
		String realName = pointsReq.getRealName();
		String bankId = pointsReq.getBankId(); 
		String bankName = pointsReq.getBankName();
		String bankCard = pointsReq.getBankCard(); 
		String businessType = pointsReq.getBusinessType();
		String noticeUrl = PointsCommand.WITHDRAW_NOTICE_URL;
		String points = pointsReq.getPoints();
		
		String objectNo = pointsReq.getObjectNo();
		String objectDes = pointsReq.getObjectDes();
		String objectType = pointsReq.getObjectType();	
		String remark = pointsReq.getRemark();
		String requestNo = String.valueOf(System.currentTimeMillis());//当前时间，精确到毫秒
		String signMsg = SecretUtil.applyForPointsToCash(accountMarkedType, realName, bankId, bankCard, objectNo, orgNo, partnerNo, SIGN_TYPE);
		
		Map model = new HashMap();
		model.put("accountMarked", accountMarked==null ? "":accountMarked);
		model.put("accountMarkedType", accountMarkedType==null ? "":accountMarkedType);
		model.put("orgNo",orgNo==null ? "":orgNo);
		
		model.put("realName",realName==null ? "":realName);
		model.put("bankId", bankId==null?"":bankId);
		model.put("bankName", bankName==null?"":bankName);
		model.put("bankCard",bankCard==null ? "":bankCard);
		model.put("businessType", businessType==null?"":businessType);
		model.put("noticeUrl", noticeUrl==null?"":noticeUrl);
		model.put("points", points);		
		model.put("objectNo",objectNo==null ? "":objectNo);
		model.put("objectDes", objectDes==null?"":objectDes);
		model.put("objectType", objectType==null?"":objectType);
		model.put("requestNo", requestNo==null?"":requestNo);
		model.put("remark", remark==null?"":remark);
		model.put("partnerNo", partnerNo);
		model.put("charset", SystemCommand.CHARSET_UTF8);
		model.put("signType", SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		String requestXmlStr = freeMarkerUtil.getContent("points/request_cash_points_api.ftl", model);
		logger.info("请求 xml：\n" + requestXmlStr);
				
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(PointsCommand.WITHDRAW_URL,requestXmlStr);
		} catch (Exception e) {
			logger.error("发送提现申请时异常", e);
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
			logger.error("解析响应时异常", e);
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
		logger.info("响应验签结果：" + flag);
		
		if(flag){
			String resultCode = responseCashPointsApi.getSystem().getResultCode();
			if(RespCodeCommand.SUCCESS.equals(resultCode)){
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("提现申请成功");
			}else{
				pointsRes.setResultCode(resultCode);
				pointsRes.setResultDesc("提现申请失败");
			}
			return pointsRes;
		}else{
			logger.info("响应验签不通过");
			pointsRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			pointsRes.setResultDesc("响应报文验签不通过");
			return pointsRes;
		}
	}
}