package com.froad.fft.thirdparty.util;

import java.lang.reflect.InvocationTargetException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.froad.fft.thirdparty.dto.request.froadmall.FroadMallApiResponse;
import com.froad.fft.thirdparty.dto.request.points.System;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.froad.fft.thirdparty.common.FroadMallCommand;
import com.froad.fft.thirdparty.common.OpenApiCommand;
import com.froad.fft.thirdparty.common.PointsCommand;
import com.froad.fft.thirdparty.common.SystemCommand;
import com.froad.fft.util.RsaUtil;

public class SecretUtil {

	final static Logger logger = Logger.getLogger(SecretUtil.class);
	
	/**
	 * 响应验签
	 * @param bodys
	 * @param responseParAccountPointsApi
	 * @param pkey
	 * @return
	 */
	public static boolean responseVerifyRSA(List<String> bodys, Object resObject, String pkey){
		try {
			PublicKey pk = RsaUtil.initPublicKey(pkey);
			String body = getTreeMap(bodys, resObject);
			Class<?> clazz = resObject.getClass();
			String signMsg;
			try {
				System sys = (System) clazz.getMethod("getSystem").invoke(resObject);
				signMsg = sys.getSignMsg();
			} catch (Exception e) {
				signMsg = (String)clazz.getMethod("getSignMsg").invoke(resObject);
			}
			logger.info("treeMap body："+body);
			logger.info("signMsg:"+signMsg);
			logger.info("publicKey:"+pkey);
			boolean flag = RsaUtil.verifyPublicKey(body, signMsg, pk);
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 得到加签体
	 * @param list
	 * @param requestFroadApi
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static String getTreeMap(List<String> list, Object resObject) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map tree = new TreeMap(); 
		for(String data:list){
			String obj = BeanUtils.getProperty(resObject, data);
			int idx = data.lastIndexOf(".");
			data = data.substring(idx + 1, data.length());
			tree.put(data, obj == null ? "":obj);
		}
		return tree.toString();
	}

	//==============================================积分平台加签
	/**
	 * 查询积分加签
	 * 
	 * @param accountMarked
	 * @param accountMarkedType
	 * @param partnerNo
	 * @return
	 */
	public static String parAccountPoints(String accountMarked,String accountMarkedType, String partnerNo, String signType) {
		String signMsg = "";
		try {
			Map map = new TreeMap();
			map.put("accountMarked", accountMarked==null?"":accountMarked);
			map.put("accountMarkedType", accountMarkedType==null?"":accountMarkedType);
			map.put("partnerNo", partnerNo==null?"":partnerNo);

			if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
				PrivateKey prikey = RsaUtil.initPrivateKey(PointsCommand.POINTS_RSA_PRIVATE_KEY, PointsCommand.POINTS_RSA_PRIVATE_PWD);
				signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
			}
			
		} catch (Exception e) {
			logger.error("查询积分加签异常",e);
		}		
		return signMsg;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>消费和退还积分加签</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 下午2:21:41 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static String consumeOrRefundPointsPoints(String objectNo,String accountId,String partnerNo,String signType){
		String signMsg = null;
		try {
			Map map = new TreeMap();
			map.put("objectNo", objectNo==null?"":objectNo);
			map.put("accountId", accountId==null?"":accountId);
			map.put("partnerNo", partnerNo==null?"":partnerNo);
			if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
				PrivateKey prikey = RsaUtil.initPrivateKey(PointsCommand.POINTS_RSA_PRIVATE_KEY, PointsCommand.POINTS_RSA_PRIVATE_PWD);
				signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
			}
		} catch (Exception e) {
			logger.error("消费/退还积分加签异常",e);
		}
		return signMsg;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>赠送积分加签</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月14日 上午10:17:21 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static String donatePoints(String orgNo,String points,String accountMarked,String partnerNo,String signType){
		String signMsg = null;
		try {
			Map map = new TreeMap();
			map.put("orgNo", orgNo==null?"":orgNo);
			map.put("points", points==null?"":points);
			map.put("accountMarked", accountMarked==null?"":accountMarked);
			map.put("partnerNo", partnerNo==null?"":partnerNo);
			if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
				PrivateKey prikey = RsaUtil.initPrivateKey(PointsCommand.POINTS_RSA_PRIVATE_KEY, PointsCommand.POINTS_RSA_PRIVATE_PWD);
				signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
			}
		} catch (Exception e) {
			logger.error("赠送积分加签异常",e);
		}
		return signMsg;
	}

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>兑充积分加签</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月14日 下午2:15:28 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static String fillPoints(String orgNo,String pointsCateNo,String orgPoints,String phone,String partnerNo,String requestNo,String signType){
		String signMsg = null;
		try {
			Map map = new TreeMap();
			map.put("orgNo", orgNo==null?"":orgNo);
			map.put("pointsCateNo", pointsCateNo==null?"":pointsCateNo);
			map.put("orgPoints", orgPoints==null?"":orgPoints);
			map.put("partnerNo", partnerNo==null?"":partnerNo);
			map.put("phone", phone==null?"":phone);
			map.put("requestNo", requestNo==null?"":requestNo);
			if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
				PrivateKey prikey = RsaUtil.initPrivateKey(PointsCommand.POINTS_RSA_PRIVATE_KEY, PointsCommand.POINTS_RSA_PRIVATE_PWD);
				signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
			}
		} catch (Exception e) {
			logger.error("兑充积分加签异常",e);
		}
		return signMsg;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>提现申请加签</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年2月10日 下午1:31:28 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static String applyForPointsToCash(String accountMarked,String realName,String bankId,String bankCard,String objectNo
			,String orgNo,String partnerNo,String signType){
		String signMsg = null;
		try {
			Map map = new TreeMap();
			map.put("accountMarked", accountMarked==null?"":accountMarked);
			map.put("realName", realName==null?"":realName);
			map.put("bankId", bankId==null?"":bankId);
			map.put("bankCard", bankCard==null?"":bankCard);
			map.put("objectNo", objectNo==null?"":objectNo);
			map.put("orgNo", orgNo==null?"":orgNo);
			map.put("partnerNo", partnerNo==null?"":partnerNo);
			if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
				PrivateKey prikey = RsaUtil.initPrivateKey(PointsCommand.POINTS_RSA_PRIVATE_KEY, PointsCommand.POINTS_RSA_PRIVATE_PWD);
				signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
			}
		} catch (Exception e) {
			logger.error("提现申请加签异常",e);
		}
		return signMsg;
	}
	
	
	//================================================OpenApi加签
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>退款申请加签</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月15日 下午1:36:26 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static String refund(String refundOrderID,String orderID,String orderAmount,String refundAmount,String refundType,
			String orderSupplier,String noticeUrl,String reqID,String partnerID,String signType){
		String signMsg = null;
		try {
			Map<String,String> map = new TreeMap<String,String>();
			map.put("refundOrderID", refundOrderID==null?"":refundOrderID);
			map.put("orderID", orderID==null?"":orderID);
			map.put("orderAmount", orderAmount==null?"":orderAmount);
			map.put("refundAmount", refundAmount==null?"":refundAmount);
			map.put("refundType", refundType==null?"":refundType);
			if(orderSupplier!=null){
				map.put("orderSupplier", orderSupplier);
			}
			map.put("noticeUrl", noticeUrl==null?"":noticeUrl);
			map.put("reqID", reqID==null?"":reqID);
			map.put("partnerID", partnerID==null?"":partnerID);
			map.put("signType", signType==null?"":signType);
			if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
				PrivateKey prikey = RsaUtil.initPrivateKey(OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY, OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
				signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
			}
		} catch (Exception e) {
			logger.error("退款申请加签异常",e);
		}
		return signMsg;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>校验查询加签</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月15日 下午4:06:16 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static String accountCheck(String checkType,String checkContent,String checkTime,String reqID,String partnerID,String signType){
		String signMsg = null;
		try {
			Map map = new TreeMap();
			map.put("checkType", checkType==null?"":checkType);
			map.put("checkContent", checkContent==null?"":checkContent);
			map.put("checkTime", checkTime==null?"":checkTime);
			map.put("reqID", reqID==null?"":reqID);
			map.put("partnerID", partnerID==null?"":partnerID);
			if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
				PrivateKey prikey = RsaUtil.initPrivateKey(OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY, OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
				signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
			}
		} catch (Exception e) {
			logger.error("校验查询加签异常",e);
		}
		return signMsg;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>转账加签</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月16日 上午10:06:06 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static String transferCurrency(String transferID,String transferAmount,String transferSubmitTime,String reqID,String partnerID,String signType){
		String signMsg = null;
		try {
			Map map = new TreeMap();
			map.put("transferID", transferID==null?"":transferID);
			map.put("transferAmount", transferAmount==null?"":transferAmount);
			map.put("transferSubmitTime", transferSubmitTime==null?"":transferSubmitTime);
			map.put("reqID", reqID==null?"":reqID);
			map.put("partnerID", partnerID==null?"":partnerID);
			if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
				PrivateKey prikey = RsaUtil.initPrivateKey(OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY, OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
				signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
			}
		} catch (Exception e) {
			logger.error("转账加签异常",e);
		}
		return signMsg;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>代收代扣加签</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月16日 下午4:07:49 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public static String agencyCollectOrDeduct(String orderID,String orderAmount,String orderSubmitTime,String reqID,String partnerID,String signType){
		String signMsg = null;
		try {
			Map map = new TreeMap();
			map.put("orderID", orderID==null?"":orderID);
			map.put("orderAmount", orderAmount==null?"":orderAmount);
			map.put("orderSubmitTime", orderSubmitTime==null?"":orderSubmitTime);
			map.put("reqID", reqID==null?"":reqID);
			map.put("partnerID", partnerID==null?"":partnerID);
			if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
				PrivateKey prikey = RsaUtil.initPrivateKey(OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY, OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
				signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
			}
		} catch (Exception e) {
			logger.error("代收代扣加签异常",e);
		}
		return signMsg;
	}
	
	
	public static String combineTransaction(
			String orderID,String orderAmount,
			String orderSupplier,String orderType,String totalAmount,String orderSubmitTime,
			String mobileToken,String fftSignNo,String payOrg, String payerMember,String payeeMember,
			String payDirect,String noticeUrl,String reqID,String partnerID,String charset,String signType){
		String signMsg = null;
		try {
			Map map = new TreeMap();
			map.put("orderID", orderID==null?"":orderID);
			map.put("orderAmount", orderAmount==null?"":orderAmount);
			map.put("orderSupplier", orderSupplier==null?"":orderSupplier);
			map.put("orderType", orderType==null?"":orderType);
			map.put("totalAmount", totalAmount==null?"":totalAmount);
			map.put("orderSubmitTime", orderSubmitTime==null?"":orderSubmitTime);
			map.put("mobileToken", mobileToken==null?"":mobileToken);
			map.put("fftSignNo", fftSignNo==null?"":fftSignNo);
			map.put("payOrg", payOrg==null?"":payOrg);
			map.put("payerMember", payerMember==null?"":payerMember);
			map.put("payeeMember", payeeMember==null?"":payeeMember);
			map.put("payDirect", payDirect==null?"":payDirect);
			map.put("noticeUrl", noticeUrl==null?"":noticeUrl);
			map.put("reqID", reqID==null?"":reqID);
			map.put("partnerID", partnerID==null?"":partnerID);
			map.put("charset", charset==null?"":charset);
			map.put("signType", signType==null?"":signType);
			if (signType.equals(SystemCommand.SIGNTYPE_RSA)) {
				PrivateKey prikey = RsaUtil.initPrivateKey(OpenApiCommand.OPENAPI_RSA_PRIVATE_KEY, OpenApiCommand.OPENAPI_RSA_PRIVATE_PWD);
				signMsg = RsaUtil.signPrivateKey(map.toString(), prikey);
			}
		} catch (Exception e) {
			logger.error("合并支付加签异常",e);
		}
		return signMsg;
	}
	
	//---------------------------方付通商城	
	public static boolean froadMallSignCheckPhone(FroadMallApiResponse froadMallApiResponse){
		boolean flag = false;
		MD5Util md5Util = new MD5Util();
		String sgin = md5Util.getMD5ofStr(FroadMallCommand.FROADMALL_CUSTOM_KEY+froadMallApiResponse.getSystem().getMerchantNo()+FroadMallCommand.MERCHANT_PWD);
		if(sgin.equals(froadMallApiResponse.getSystem().getSignMsg())){
			flag = true;
		}else{
			logger.equals("校验方付通商城报文失败");
		}
		return flag;
	}
	
	public static boolean froadMallSignCheckLottery(FroadMallApiResponse froadMallApiResponse){
		boolean flag = false;
		MD5Util md5Util = new MD5Util();
		String sgin = md5Util.getMD5ofStr(FroadMallCommand.FROADMALL_CUSTOM_KEY+FroadMallCommand.MERCHANT_NO+FroadMallCommand.MERCHANT_PWD+froadMallApiResponse.getBody().getTranID());
		if(sgin.equals(froadMallApiResponse.getSystem().getSignMsg())){
			flag = true;
		}else{
			logger.equals("校验方付通商城报文失败");
		}
		return flag;
	}
}
