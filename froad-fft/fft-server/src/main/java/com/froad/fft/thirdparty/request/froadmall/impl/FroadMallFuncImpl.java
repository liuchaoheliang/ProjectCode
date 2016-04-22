package com.froad.fft.thirdparty.request.froadmall.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.froad.fft.thirdparty.common.FroadMallCommand;
import com.froad.fft.thirdparty.common.RespCodeCommand;
import com.froad.fft.thirdparty.common.SystemCommand;
import com.froad.fft.thirdparty.dto.request.froadmall.Body;
import com.froad.fft.thirdparty.dto.request.froadmall.FroadMallApiResponse;
import com.froad.fft.thirdparty.dto.request.froadmall.FroadMallReq;
import com.froad.fft.thirdparty.dto.request.froadmall.FroadMallRes;
import com.froad.fft.thirdparty.dto.request.froadmall.Lottery;
import com.froad.fft.thirdparty.request.froadmall.FroadMallFunc;
import com.froad.fft.thirdparty.util.MD5Util;
import com.froad.fft.thirdparty.util.SecretUtil;
import com.froad.fft.thirdparty.util.XMLStrHttpClientUtil;
import com.froad.fft.util.DateUtil;
import com.froad.fft.util.FreeMarkerUtil;
import com.froad.fft.util.XmlBeanUtil;

@Component("froadMallFuncImpl")
public class FroadMallFuncImpl implements FroadMallFunc {

	private static Logger logger = Logger.getLogger(FroadMallFuncImpl.class);
	
	@Resource
	private FreeMarkerUtil freeMarkerUtil;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>手机号码充值前校验</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月17日 下午2:25:32 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	private FroadMallRes checkPhoneNumInfo(FroadMallReq froadMallReq){
		logger.info("开始进行<检查手机号码>功能,交互系统: <froadmall>");
		FroadMallRes  froadMallRes = new FroadMallRes();		
		String money = froadMallReq.getMoney();
		String CZNo = froadMallReq.getCZNo();
		MD5Util md5Util = new MD5Util();
		String signMsg = md5Util.getMD5ofStr(FroadMallCommand.FROADMALL_CUSTOM_KEY+FroadMallCommand.MERCHANT_NO+FroadMallCommand.MERCHANT_PWD+CZNo);
		Map model = new HashMap();
		model.put("version",FroadMallCommand.FROAD_MALL_VERSION);
		model.put("merchantNo", FroadMallCommand.MERCHANT_NO);
		model.put("merchantPwd", md5Util.getMD5ofStr(FroadMallCommand.MERCHANT_PWD));
		model.put("signType", FroadMallCommand.FROAD_MALL_SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("money", money);
		model.put("CZNo", CZNo);
		model.put("requestTime", DateUtil.formatDate2Str());
		model.put("busiCode", FroadMallCommand.APICODE_QUERY_CZNO);
		String requestXmlStr = freeMarkerUtil.getContent("froadmall/check_phone_info.ftl", model);
		logger.info("请求 xml：\n" + requestXmlStr);
		
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(FroadMallCommand.FROAD_MALL_MAIN_URL,requestXmlStr);
		} catch (Exception e) {
			logger.error("发送检查手机号码请求 异常", e);
			froadMallRes.setResultCode(RespCodeCommand.EXCEPTION);
			froadMallRes.setResultDesc("发送检查手机号码请求 异常");
			return froadMallRes;
		}
		
		FroadMallApiResponse froadMallApiResponse = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("FroadMallApiResponse", FroadMallApiResponse.class);
			froadMallApiResponse = (FroadMallApiResponse) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);		
		} catch (Exception e) {
			logger.error("解析响应时异常", e);
			froadMallRes.setResultCode(RespCodeCommand.INVALID_XML);
			froadMallRes.setResultDesc("解析响应报文时异常");
			return froadMallRes;
		}
		boolean flag = SecretUtil.froadMallSignCheckPhone(froadMallApiResponse);
		logger.info("响应验签结果：" + flag);
		

		if(flag){
			froadMallRes.setResultCode(froadMallApiResponse.getSystem().getRespCode());
			froadMallRes.setResultDesc(froadMallApiResponse.getSystem().getRespMsg());
			return froadMallRes;
		}else{
			logger.info("响应验签不通过");
			froadMallRes.setResultCode(RespCodeCommand.CHECK_SIGNMSG_FAIL);
			froadMallRes.setResultDesc("响应报文验签不通过");
			return froadMallRes;
		}
	}

	@Override
	public FroadMallRes onlineRecharge(FroadMallReq froadMallReq) {
		FroadMallRes  froadMallRes = new FroadMallRes();
		if(!RespCodeCommand.SUCCESS.equals(checkPhoneNumInfo(froadMallReq).getResultCode())){
			return froadMallRes;
		}
		logger.info("开始进行<充值话费>功能,交互系统: <froadmall>");
		
		String money = froadMallReq.getMoney();
		String CZNo = froadMallReq.getCZNo();
		String SPID = SystemCommand.FFT_CLIENT_PREFIX + froadMallReq.getSPID();
		MD5Util md5Util = new MD5Util();
		String signMsg = md5Util.getMD5ofStr(FroadMallCommand.FROADMALL_CUSTOM_KEY+FroadMallCommand.MERCHANT_NO
				+FroadMallCommand.MERCHANT_PWD+CZNo+SPID);
		Map model = new HashMap();
		model.put("version",FroadMallCommand.FROAD_MALL_VERSION);
		model.put("merchantNo", FroadMallCommand.MERCHANT_NO);
		model.put("merchantPwd", md5Util.getMD5ofStr(FroadMallCommand.MERCHANT_PWD));
		model.put("signType", FroadMallCommand.FROAD_MALL_SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("money", money);
		model.put("SPID", SPID);
		model.put("CZNo", CZNo);
		model.put("tranDate", froadMallReq.getTranDate());
		//TODO 待添加
//		model.put("merchantReturnUrl", SystemCommand.FROADMALL_ONLINE_RECHARGE_RES);
		model.put("salePrice", froadMallReq.getSalePrice());
		model.put("requestTime", DateUtil.formatDate2Str());
		model.put("busiCode", FroadMallCommand.APICODE_ONLINE);
		String requestXmlStr = freeMarkerUtil.getContent("froadmall/online_recharge.ftl", model);
		logger.info("请求 xml：\n" + requestXmlStr);
		
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(FroadMallCommand.FROAD_MALL_MAIN_URL,requestXmlStr);
		} catch (Exception e) {
			logger.error("发送检查手机号码 异常", e);
			froadMallRes.setResultCode(RespCodeCommand.EXCEPTION);
			froadMallRes.setResultDesc("发送检查手机号码 异常");
			return froadMallRes;
		}
		
		FroadMallApiResponse froadMallApiResponse = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("FroadMallApiResponse", FroadMallApiResponse.class);
			froadMallApiResponse = (FroadMallApiResponse) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);		
		} catch (Exception e) {
			logger.error("解析响应时异常", e);
			froadMallRes.setResultCode(RespCodeCommand.INVALID_XML);
			froadMallRes.setResultDesc("解析响应报文时异常");
			return froadMallRes;
		}
		
		froadMallRes.setResultCode(froadMallApiResponse.getSystem().getRespCode());
		froadMallRes.setResultDesc(froadMallApiResponse.getSystem().getRespMsg());
		return froadMallRes;
	}

	@Override
	public FroadMallRes queryLotteryPeriods(FroadMallReq froadMallReq) {
		logger.info("开始进行<查询彩票期号>功能,交互系统: <froadmall>");
		FroadMallRes  froadMallRes = new FroadMallRes();
		MD5Util md5Util = new MD5Util();
		String signMsg = md5Util.getMD5ofStr(FroadMallCommand.FROADMALL_CUSTOM_KEY+FroadMallCommand.MERCHANT_NO
				+FroadMallCommand.MERCHANT_PWD);
		Map model = new HashMap();
		model.put("version",FroadMallCommand.FROAD_MALL_VERSION);
		model.put("merchantNo", FroadMallCommand.MERCHANT_NO);
		model.put("merchantPwd", md5Util.getMD5ofStr(FroadMallCommand.MERCHANT_PWD));
		model.put("signType", FroadMallCommand.FROAD_MALL_SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		model.put("busiCode", FroadMallCommand.APICODE_QUERY_PERIDLISTNOW);
		model.put("lotteryNo", froadMallReq.getLotteryNo());
		String requestXmlStr = freeMarkerUtil.getContent("froadmall/query_lottery_periods.ftl", model);
		logger.info("请求 xml：\n" + requestXmlStr);
		
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(FroadMallCommand.FROAD_MALL_MAIN_URL,requestXmlStr);
		} catch (Exception e) {
			logger.error("查询彩票期号 异常", e);
			froadMallRes.setResultCode(RespCodeCommand.EXCEPTION);
			froadMallRes.setResultDesc("查询彩票期号 异常");
			return froadMallRes;
		}
		
		FroadMallApiResponse froadMallApiResponse = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("FroadMallApiResponse", FroadMallApiResponse.class);
			xmlToBeanMap.put("body", Body.class);
			xmlToBeanMap.put("lottery",Lottery.class);
			froadMallApiResponse = (FroadMallApiResponse) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);		
		} catch (Exception e) {
			logger.error("解析响应时异常", e);
			froadMallRes.setResultCode(RespCodeCommand.INVALID_XML);
			froadMallRes.setResultDesc("解析响应报文时异常");
			return froadMallRes;
		}
		
		froadMallRes.setResultCode(froadMallApiResponse.getSystem().getRespCode());
		froadMallRes.setResultDesc(froadMallApiResponse.getSystem().getRespMsg());
		froadMallRes.setLotteryList(froadMallApiResponse.getBody() == null ? null :froadMallApiResponse.getBody().getLotteryList());
		return froadMallRes;
		
	}

	@Override
	public FroadMallRes createLotteryTrans(FroadMallReq froadMallReq) {		
		logger.info("开始进行<购买彩票>功能,交互系统: <froadmall>");
		FroadMallRes  froadMallRes = new FroadMallRes();
		String orderID = SystemCommand.FFT_CLIENT_PREFIX+froadMallReq.getOrderID();
		MD5Util md5Util = new MD5Util();
		String signMsg = md5Util.getMD5ofStr(FroadMallCommand.FROADMALL_CUSTOM_KEY+FroadMallCommand.MERCHANT_NO
				+FroadMallCommand.MERCHANT_PWD+froadMallReq.getLotteryNo()+froadMallReq.getPeriod()+froadMallReq.getContent()+orderID);
		Map model = new HashMap();
		model.put("version",FroadMallCommand.FROAD_MALL_VERSION);
		model.put("merchantNo", FroadMallCommand.MERCHANT_NO);
		model.put("merchantPwd", md5Util.getMD5ofStr(FroadMallCommand.MERCHANT_PWD));
		model.put("signType", FroadMallCommand.FROAD_MALL_SIGN_TYPE);
		model.put("signMsg", signMsg);
		model.put("requestTime", DateUtil.formatDate2Str());
		model.put("busiCode", FroadMallCommand.APICODE_CREATEORDER);
		model.put("lotteryNo", froadMallReq.getLotteryNo());
		model.put("period", froadMallReq.getPeriod());
		model.put("playType", froadMallReq.getPlayType());
		model.put("numType", froadMallReq.getNumType());
		model.put("content", froadMallReq.getContent());
		model.put("buyamount", froadMallReq.getBuyamount());
		model.put("amount", froadMallReq.getAmount());
		model.put("numCount", froadMallReq.getNumCount());
		model.put("mobilephone", froadMallReq.getMobilephone());
		model.put("numCount", froadMallReq.getNumCount());
		model.put("orderID", orderID);
		//TODO 待添加
//		model.put("callBackAddr", SystemCommand.FROADMALL_CREATE_LOTTERY_RES);
//		model.put("callBackAddr2", SystemCommand.FROADMALL_LOTTERY_WIN_RES);
		String requestXmlStr = freeMarkerUtil.getContent("froadmall/create_lottery_trans.ftl", model);
		logger.info("请求 xml：\n" + requestXmlStr);
		// 发送请求
		String responseXmlStr = null;
		try {
			responseXmlStr = XMLStrHttpClientUtil.httpPost(FroadMallCommand.FROAD_MALL_MAIN_URL,requestXmlStr);
		} catch (Exception e) {
			logger.error("彩票下单发送 异常", e);
			froadMallRes.setResultCode(RespCodeCommand.EXCEPTION);
			froadMallRes.setResultDesc("彩票下单发送 异常");
			return froadMallRes;
		}
		
		FroadMallApiResponse froadMallApiResponse = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("FroadMallApiResponse", FroadMallApiResponse.class);
			froadMallApiResponse = (FroadMallApiResponse) XmlBeanUtil.xmlToBean(xmlToBeanMap, responseXmlStr);		
		} catch (Exception e) {
			logger.error("解析响应时异常", e);
			froadMallRes.setResultCode(RespCodeCommand.INVALID_XML);
			froadMallRes.setResultDesc("解析响应报文时异常");
			return froadMallRes;
		}
		if(froadMallApiResponse.getBody() != null){
			boolean flag = SecretUtil.froadMallSignCheckLottery(froadMallApiResponse);
			logger.info("响应验签结果：" + flag);
			froadMallRes.setLotteryList(froadMallApiResponse.getBody().getLotteryList());
			froadMallRes.setTransID(froadMallApiResponse.getBody().getTranID());
			froadMallRes.setDescription(froadMallApiResponse.getBody().getDescription());
		}		
		froadMallRes.setResultCode(froadMallApiResponse.getSystem().getRespCode());
		froadMallRes.setResultDesc(froadMallApiResponse.getSystem().getRespMsg());
		return froadMallRes;
	}
	
}
