package com.froad.support.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.alibaba.fastjson.JSONObject;
import com.froad.common.beans.PaymentPoTemp;
import com.froad.common.beans.PointInfo;
import com.froad.common.beans.ResultBean;
import com.froad.common.beans.payment.RefundTempBean;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.MerchantMonthCountMapper;
import com.froad.db.redis.RedisService;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.CashType;
import com.froad.enums.OrderType;
import com.froad.enums.PaymentMethod;
import com.froad.enums.PaymentStatus;
import com.froad.enums.RefundState;
import com.froad.enums.ResultCode;
import com.froad.enums.SubOrderType;
import com.froad.exceptions.PaymentException;
import com.froad.po.ClientPaymentChannel;
import com.froad.po.MerchantMonthCount;
import com.froad.po.Payment;
import com.froad.po.PaymentMongoEntity;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.support.OrderSupport;
import com.froad.support.PaymentSupport;
import com.froad.support.impl.payment.DataPersistentImpl;
import com.froad.thirdparty.common.OpenApiCommand;
import com.froad.thirdparty.dto.request.openapi.OpenApiOrderDetail;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.BillOrderType;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.CheckType;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.Client;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.PayDirect;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.PayType;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.RefundType;
import com.froad.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.thirdparty.dto.request.openapi.QueryParamApiReq;
import com.froad.thirdparty.dto.request.points.PointsAccount;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.dto.request.points.PointsReq.QueryOrderType;
import com.froad.thirdparty.dto.response.openapi.NoticeFroadApi;
import com.froad.thirdparty.dto.response.openapi.Order;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.froad.thirdparty.request.openapi.OpenApiFunc;
import com.froad.thirdparty.request.openapi.impl.OpenApiFuncImpl;
import com.froad.thirdparty.request.points.PointsApiFunc;
import com.froad.thirdparty.request.points.impl.PointsApiFuncImpl;
import com.froad.thirdparty.request.userengine.UserEngineFunc;
import com.froad.thirdparty.request.userengine.impl.UserEngineFuncImpl;
import com.froad.thirdparty.util.RsaUtil;
import com.froad.thirdparty.util.XmlBeanUtil;
import com.froad.util.Arith;
import com.froad.util.DateUtil;
import com.froad.util.EmptyChecker;
import com.froad.util.MongoTableName;
import com.froad.util.RedisKeyUtil;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Const;
import com.froad.util.payment.EsyT;
import com.froad.util.payment.Logger;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;


/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: PaymentSupport
 * @Description: 支持mybats最终实现与逻辑层中转以及与第三方系统直接交互等
 * @Author: zhaoxiaoyao@f-road.com.cn
 * @Date: 2015年3月21日 下午4:19:55
 */
@Deprecated
public class PaymentSupportImpl implements PaymentSupport{
	

	//-----------------------常量信息-------------------
	private final String SUCCESS_CODE = "0000";
	
	//**方付通积分机构号
	public static final String FROAD_POINT_ORG_NO = Const.FROAD_POINT_ORG_NO;
	
	private static MongoManager  mongoManager = new MongoManager();
	private static RedisService redisService = new RedisManager();
	
	
	OrderSupport orderSupport = new OrderSupportImpl();
	PointsApiFunc pointsApiFunc = new PointsApiFuncImpl();
	OpenApiFunc openApiFunc = new OpenApiFuncImpl();
	
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: getSessionFactory
	 * @Description: 获取mybatisSession工场
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @return
	 * @Return: SqlSessionFactory
	 */
	private static SqlSessionFactory getSessionFactory(){
		return MyBatisManager.getSqlSessionFactory();
	} 
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: closeSession
	 * @Description: 关闭Session
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param session
	 * @Return: void
	 */
	private static void closeSession(SqlSession session){
		if(session != null){
			session.close();
		}
	}
	
	//-----------------mongo sql----------------------
	
	
	//----------------for payment---------------------
	@Override
	public Payment findPaymentByPaymentId(String paymentId) {
		return new DataPersistentImpl().findByPaymentIdFromMongoDB(paymentId);
	}
	
	@Override
	public List<Payment> findEnablePaymentsOfUserPayByOrderId(String orderId) {
		return new DataPersistentImpl().findEnableOfUserPayByOrderIdFromMongoDB(orderId);
		
	}
	
	@Override
	public List<Payment> findAllEnablePaymentsByOrderId(String orderId) {
		return new DataPersistentImpl().findAllEnableByOrderIdFromMongoDB(orderId);
	}
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: updatePaymentAndLock
	 * @Description: 利用mongoDB findAndModify 锁定流水并修改 原子性操作
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param paymentOriginal
	 * @param paymentTarget
	 * @return
	 * @see com.froad.support.PaymentSupport#updatePaymentAndLock(com.froad.po.Payment, com.froad.po.Payment)
	 */
	@Override
	public Payment updatePaymentAndLock(Payment paymentOriginal,Payment paymentTarget) {
		return new DataPersistentImpl().findAndModifyPaymentFromMongoDB(paymentOriginal, paymentTarget);
	}
	
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: updatePayment
	 * @Description: 普通流水更新
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param paymentTarget
	 * @return
	 * @see com.froad.support.PaymentSupport#updatePayment(com.froad.po.Payment)
	 */
	@Override
	public boolean updatePayment(Payment paymentTarget) {
		return new DataPersistentImpl().updateByPaymentIdFromMongoDB(paymentTarget);
	}
	//----------------for payment---------------------
	
	
	@Override
	public boolean removeTimePayment(String paymentId) {
		return redisService.srem(RedisKeyUtil.cbbank_time_payment_key(), paymentId) != 0;
	}
	
	@Override
	public boolean addTimePayment(String paymentId) {
		return redisService.put(RedisKeyUtil.cbbank_time_payment_key(), paymentId) != 0;
	}
	
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @ClassName: VerifyOpenAPINoticeSign
	 * @Description: 验证OpenAPI通知签文
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @Date: 2015年4月25日 下午1:30:27
	 */
	class VerifyOpenAPINoticeSign{
		
		/**
		 * Copyright © 2015 F-Road. All rights reserved.
		 * @Title: verifyCombine
		 * @Description: 合并通知验签
		 * @Author: zhaoxiaoyao@f-road.com.cn
		 * @param noticeFroadApi
		 * @return
		 * @throws InvalidKeyException
		 * @throws NoSuchAlgorithmException
		 * @throws InvalidKeySpecException
		 * @throws SignatureException
		 * @throws IOException
		 * @Return: boolean
		 */
		boolean verifyCombine(NoticeFroadApi noticeFroadApi) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, IOException{
			com.froad.thirdparty.dto.response.openapi.System system = noticeFroadApi.getSystem();
			com.froad.thirdparty.dto.response.openapi.Order order = noticeFroadApi.getOrder();
			Map<String, String> encMap = new TreeMap<String, String>();
	        encMap.put("orderID", order.getOrderID());
	        encMap.put("orderAmount", order.getOrderAmount());
	        encMap.put("stateCode", order.getStateCode());
	        encMap.put("orderAcquiringTime", order.getOrderAcquiringTime());
	        encMap.put("orderCompleteTime", order.getOrderCompleteTime());
	        encMap.put("resultCode", system.getResultCode());
	        encMap.put("partnerID", system.getPartnerID());
	        PublicKey pk = RsaUtil.initPublicKey(com.froad.thirdparty.common.OpenApiCommand.OPENAPI_PUBLICKEY);
	        boolean flag = RsaUtil.verifyPublicKey(encMap.toString(), system.getSignMsg(), pk);        
	        return flag;
		}
		
		/**
		 * Copyright © 2015 F-Road. All rights reserved.
		 * @Title: verifyRefund
		 * @Description: 退款通知验签
		 * @Author: zhaoxiaoyao@f-road.com.cn
		 * @param notice
		 * @return
		 * @throws InvalidKeyException
		 * @throws NoSuchAlgorithmException
		 * @throws InvalidKeySpecException
		 * @throws SignatureException
		 * @throws IOException
		 * @Return: boolean
		 */
		boolean verifyRefund(NoticeFroadApi notice) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, IOException{
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
	
	
	//==========================================业务================================================
	@Override
	public NoticeFroadApi combineXMLToEntity(String xmlString) {
		NoticeFroadApi noticeFroadApi = null;
		try {
			@SuppressWarnings("rawtypes")
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("noticeFroadApi", NoticeFroadApi.class);
			xmlToBeanMap.put("order", com.froad.thirdparty.dto.response.openapi.Order.class);
			xmlToBeanMap.put("pay", com.froad.thirdparty.dto.response.openapi.Pay.class);
			xmlToBeanMap.put("system", System.class);
			noticeFroadApi = (NoticeFroadApi)XmlBeanUtil.xmlToBean(xmlToBeanMap, xmlString);
			VerifyOpenAPINoticeSign verifyOpenAPINoticeSign = new VerifyOpenAPINoticeSign();
			boolean flag = verifyOpenAPINoticeSign.verifyCombine(noticeFroadApi);
			if (flag){//通知报文校验通过
				return noticeFroadApi;
            } else {//支付通知校验失败
                Logger.logError("订单合并通知校验失败！无效的通知报文" , "xmlString" ,xmlString);
            }
		} catch (Exception e) {
			Logger.logError("解析通知XML时异常", e);
		}
		return null;
	}

	@Override
	public NoticeFroadApi refundXMLToEntity(String xmlString) {
		NoticeFroadApi noticeFroadApi = null;
		try {
			@SuppressWarnings("rawtypes")
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("noticeFroadApi", NoticeFroadApi.class);
			xmlToBeanMap.put("order", Order.class);
			xmlToBeanMap.put("system", System.class);
			noticeFroadApi = (NoticeFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, xmlString);
			VerifyOpenAPINoticeSign verifyOpenAPINoticeSign = new VerifyOpenAPINoticeSign();
			boolean flag = verifyOpenAPINoticeSign.verifyRefund(noticeFroadApi);
			if (flag){//通知报文校验通过
				return noticeFroadApi;
            } else {//通知校验失败
            	Logger.logError("订单合并通知校验失败！无效的通知报文" , "xmlString" ,xmlString);
            }
		} catch (Exception e) {
			Logger.logError("解析通知XML时异常", e);
		}
		return null;
	}
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: getClientFromRedis
	 * @Description: 获取缓存的客户端信息
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param clientId
	 * @return
	 * @Return: Map<String,String>
	 */
	public static com.froad.po.Client getClientFromRedis(String clientId) {
		return BaseSubassembly.acquireClientFromRedis(clientId);
	}
	
	@Override
	public List<ClientPaymentChannel> getPaymentChannelSetFromRedis(String clientId){
		return BaseSubassembly.acquireClientPaymentChannelFromRedis(clientId);
	}
	
	//---------------获取平台公共PartnerNo、银行积分机构号
	public static String acquireOpenAPIPartnerNo(String clientId){
		return BaseSubassembly.acquireOpenAPIPartnerNo(clientId);
	}
	public static String acquirePointPartnerNo(String clientId){
		return BaseSubassembly.acquirePointPartnerNo(clientId);
	}
	//--获取对应的银行机构号
	public static String acquireBankOrgNo(String clientId){
		return BaseSubassembly.acquireBankPointPaymentOrgNo(clientId);
	}
	//---------------获取平台公共PartnerNo、银行积分机构号
	
	@Override
	public boolean removeTimeOrder(String clinetId, String orderId) {
		String timeOrderKey = RedisKeyUtil.cbbank_time_order_key();
		String timeOrderVlue = RedisKeyUtil.cbbank_time_order_value(clinetId, orderId);
		return redisService.srem(timeOrderKey, timeOrderVlue) != 0L;
	}
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: addInitPaymentInfo
	 * @Description: 保存初始化的支付记录
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param clientId
	 * @param orderId   订单号
	 * @param payType   支付类型
	 * @param payValue  支付资金值
	 * @param payOrgNo  支付机构号
	 * @param cashType  现金类型（0 表示积分支付）
	 * @return
	 * @Return: boolean
	 */
	@Override
	public boolean addPaymentToMongo(PaymentPoTemp paymentPoTemp){
		//使用Mongo
		Payment payment = new Payment();										
		payment.setCreateTime(new Date());
		payment.setPaymentId(StringUtils.isEmpty(paymentPoTemp.getPaymentId()) ? EsyT.simpleID() : paymentPoTemp.getPaymentId());
		payment.setClientId(paymentPoTemp.getClientId());
		payment.setOrderId(paymentPoTemp.getOrderId());									//数据体
		payment.setPaymentType(paymentPoTemp.getPayType());
		payment.setPaymentValue(Arith.mul(paymentPoTemp.getPayValue(), 1000)); //价值入库*1000
		payment.setPaymentTypeDetails(paymentPoTemp.getCashType());
		payment.setBillNo(paymentPoTemp.getBillNo());
		payment.setStep(1); //支付记录创建-待支付
		payment.setPaymentStatus("1");//支付记录创建-待支付
		payment.setPaymentOrgNo(paymentPoTemp.getPayOrgNo());
		payment.setIsEnable(true);
//		payment.setAutoRefund("0");
		payment.setPointRate(paymentPoTemp.getPointRate());
		payment.setPaymentReason(paymentPoTemp.getPaymentReason());
		payment.setFromAccountNo(paymentPoTemp.getFromAccountNo());
		payment.setFromUserName(paymentPoTemp.getFromUserName());
//		payment.setToAccountName(paymentPoTemp.getToAccountName());
		payment.setFromPhone(paymentPoTemp.getFromPhone());
		payment.setIsDisposeException("0");
		return new DataPersistentImpl().savePaymentToMongoDB(payment);
	}

	//------------------------------------------------------------------------------------------------
	// points > cash
	//  payPoints  --> order.deleteStatus y -> 主动确认库存 y ->go on ↓ || n -> 退积分    --show  库存不足
	//  payCash  --> order.deleteStatus y -> 主动确认库存 y ->go on  || n -> (退积分&)退现金    --show  库存不足
	//------------------------------------------------------------------------------------------------
	@Override
	public ResultBean doPayCore(Payment[] paymentArray,String token) {
		
		Logger.logInfo("核心支付处理收到支付流水", "paymentArray",paymentArray);
		
		ResultBean resultBean = null;
		
		Payment payStepOne = paymentArray[0];
		
		
		boolean updateSuccess = lockPayment(payStepOne);
		if(!updateSuccess){
		    Logger.logError("将支付步骤 step 改为 第2步失败","paymentId",payStepOne.getPaymentId());
		    return new ResultBean(ResultCode.failed);
		}
		
		
		DoPay doPay = new DoPay();
		
		if(paymentArray.length == 1){//单一支付
			if(payStepOne.getPaymentType() == 1 || payStepOne.getPaymentType() == 3){//积分支付
	            return doPay.pointsPay(payStepOne);
			}else if(payStepOne.getPaymentType() == 2){//资金支付
			    return doPay.cashPay(payStepOne,token);
			}
		}else{//组合支付
			resultBean = doPay.pointsPay(payStepOne); //到此一定是组合支付，那么第一条支付记录一定是积分支付记录
			if(!isSuccess(resultBean)){
				Logger.logError("组合支付交易，在执行积分支付时失败，后续操作已忽略");
				return resultBean;
			}
			//积分支付已成功
			Payment cashPayment = paymentArray[1];
			updateSuccess = lockPayment(cashPayment);
			if(!updateSuccess){
				Logger.logError("将支付步骤 step 改为 第2步失败，将发起自动退积分操作","point paymentId",payStepOne.getPaymentId());
				//由于支付现金更步骤失败，则整个现金流程支付流程失败，自动退还积分
			    String resultStr = autoRefundPoints(findPaymentByPaymentId(payStepOne.getPaymentId())).toString();
			    Logger.logInfo("自动退还积分结果", "resultStr",resultStr);
			    return new ResultBean(ResultCode.failed);
			}
			resultBean = doPay.cashPay(cashPayment,token);
			if(!isSuccess(resultBean)){//现金支付失败
				Logger.logError("现金支付已失败，准备自动退还已支付的积分");
				String resultStr = autoRefundPoints(findPaymentByPaymentId(payStepOne.getPaymentId())).toString();
				Logger.logInfo("自动退还积分结果", "resultStr",resultStr);
				return resultBean;
			}
		}
		return new ResultBean(ResultCode.success);
	}

    private boolean isSuccess(ResultBean resultBean) {
        return ResultCode.success.getCode().equals(resultBean.getCode());
    }

    private boolean lockPayment(Payment payment) {
    	
    	String paymentId = payment.getPaymentId();
    	payment = new Payment();
    	payment.setPaymentId(paymentId);
    	payment.setStep(1);
        Payment paymentTemp = new Payment();
        paymentTemp.setPaymentId(payment.getPaymentId());
        paymentTemp.setStep(2); //支付开始
        boolean updateSuccess = updatePaymentAndLock(payment, paymentTemp) != null;
    	return updateSuccess;
    }
    
    class DoPay{
    	
    	/**
    	 * Copyright © 2015 F-Road. All rights reserved.
    	 * @Title: pointsPay
    	 * @Description: 对支付信息进行支付（积分）
    	 * @Author: zhaoxiaoyao@f-road.com.cn
    	 * @param payment
    	 * @return
    	 * @Return: ResultBean
    	 */
    	ResultBean pointsPay(Payment payment){
    		
    		OrderMongo order = orderSupport.getOrderById(payment.getClientId(),payment.getOrderId());
    		if(EmptyChecker.isEmpty(order)){
    			return new ResultBean(ResultCode.failed); //查询订单失败
    		}
    		
    		String pointPartnerNo = acquirePointPartnerNo(payment.getClientId());
    		
    		if(pointPartnerNo == null){
    			Logger.logError("未能获取到pointPartnerNo信息","clientId",payment.getClientId());
    			return new ResultBean(ResultCode.failed);
    		}
    		
    		UserSpecDto userSpecDto = queryByMemberCode(order.getMemberCode());
    		PointInfo pointInfo = queryUserPoints(userSpecDto.getLoginID(), pointPartnerNo, order.getClientId());
    		
    		if(pointInfo == null){
    			Logger.logInfo("未能获取到用户积分账户信息","loginID",userSpecDto.getLoginID());
    			return new ResultBean(ResultCode.failed, "无法确认用户当前积分信息");
    		}
    		
    		
    		PointsReq req;
    		String consumeAccountId;
    		
    		double payValue = Arith.div(payment.getPaymentValue(),1000);
    		
    		if(FROAD_POINT_ORG_NO.equals(payment.getPaymentOrgNo())){
    			//使用方付通积分支付
    			if(pointInfo.getFroadPoint() < payValue){
    				Logger.logInfo("用户使用方付通积分进行支付，但积分不足");
    				return new ResultBean(ResultCode.payment_point_lack);
    			}
    			
    			consumeAccountId = pointInfo.getFroadAccountId();
    			
    			req = new PointsReq(
    					FROAD_POINT_ORG_NO,//消耗方付通积分机构号的积分
    					payment.getPaymentId(), 
    					payValue, //points
    					userSpecDto.getLoginID(),//accountMarked 
    					pointPartnerNo,  //partnerNo
    					"由社区银行发起的方付通积分消费信息", //businessType
    					null,
    					consumeAccountId//accountId
    					);
    		}else{
    			//使用银行积分支付
    			if(pointInfo.getBankPoint() < payValue){
    				return new ResultBean(ResultCode.payment_point_lack);
    			}
    			consumeAccountId = pointInfo.getBankAccountId();
    			req = new PointsReq(
    					payment.getPaymentOrgNo(),//消耗银行积分机构号
    					payment.getPaymentId(),  
    					Arith.div(payValue,Double.valueOf(payment.getPointRate())),//原始价值
    					userSpecDto.getLoginID(),//accountMarked 
    					pointPartnerNo,  //partnerNo
    					"由社区银行发起的银行积分消费信息", //businessType
    					payValue,
    					consumeAccountId
    					);
    		}
    		
    		//------ 用于在查询积分消费明显时展示订单号
    		req.setRemark(payment.getOrderId());
    		//------
    		
    		Payment paymentTemp = null;
    		
    		boolean flag = false;
    		
    		PointsRes res = null;
    		
    		try {
    			res = consumePoints(req);
    		} catch (Exception e) {
    			
    			addTimePayment(payment.getPaymentId());
    			
    			paymentTemp = new Payment();
    			paymentTemp.setPaymentId(payment.getPaymentId());
    			paymentTemp.setPaymentStatus("3");
    			paymentTemp.setStep(3);
    			
    			paymentTemp.setFromAccountNo(consumeAccountId);
    			
    			paymentTemp.setRemark("发起消耗积分请求异常: " + e.getMessage());
    			flag = updatePayment(paymentTemp);
    			
    			Logger.logError("发起消费积分请求异常,认为该笔订单请求发送成功,结果交由定时任务处理: " + flag, e);
    			throw new PaymentException(e);
    		}
    		
    		Logger.logInfo("积分平台消耗积分请求发送完毕","pointsRes",res);
    		paymentTemp = new Payment();
    		paymentTemp.setPaymentId(payment.getPaymentId());
    		paymentTemp.setPaymentStatus("2");//支付请求发送成功
    		paymentTemp.setStep(3);
    		paymentTemp.setFromAccountNo(consumeAccountId);
    		paymentTemp.setFromUserName(userSpecDto.getLoginID());
    		flag = updatePayment(paymentTemp);
    		
    		Logger.logInfo("发送请求支付信息成功 ,修改支付流水结果: " + flag);
    		
    		//更改支付记录信息
    		if(SUCCESS_CODE.equals(res.getResultCode())){
    			paymentTemp = new Payment();
    			paymentTemp.setPaymentId(payment.getPaymentId());
    			paymentTemp.setPaymentStatus("4");//支付成功
    			paymentTemp.setStep(4);
    			paymentTemp.setResultCode(res.getResultCode());
    			paymentTemp.setResultDesc(res.getResultDesc());
    			paymentTemp.setBillNo(res.getPayPointsNo());
    			flag = updatePayment(paymentTemp);			
    			Logger.logInfo("积分消费成功 ,修改支付流水结果: " + flag);
    			return new ResultBean(ResultCode.success);
    		}else{
    			paymentTemp = new Payment();
    			paymentTemp.setPaymentId(payment.getPaymentId());
    			paymentTemp.setPaymentStatus("5");//支付失败
    			paymentTemp.setStep(4);
    			paymentTemp.setResultCode(res.getResultCode());
    			paymentTemp.setResultDesc(res.getResultDesc());
    			flag = updatePayment(paymentTemp);
    			Logger.logInfo("积分消费失败 ,修改支付流水结果: " + flag);
    			return new ResultBean(ResultCode.failed,res.getResultDesc());
    		}
    	}
    	
    	/**
    	 * Copyright © 2015 F-Road. All rights reserved.
    	 * @Title: cashPay
    	 * @Description: 对支付流水进行支付(现金)
    	 * @Author: zhaoxiaoyao@f-road.com.cn
    	 * @param payment
    	 * @param token
    	 * @return
    	 * @Return: ResultBean
    	 */
    	private ResultBean cashPay(Payment payment,String token){
    		
    		OrderMongo order = orderSupport.getOrderByOrderId(payment.getClientId(),payment.getOrderId());
			if (EmptyChecker.isEmpty(order)) {
				return new ResultBean(ResultCode.failed); // 查询订单失败
			}
			
    		String openAPIPartnerNo = acquireOpenAPIPartnerNo(payment.getClientId());
    		if(openAPIPartnerNo == null){
    			Logger.logError("未能获取到客openAPIPartnerNo信息","clientId",payment.getClientId());
    			return new ResultBean(ResultCode.failed);
    		}
    		
    		UserSpecDto userSpecDto = queryByMemberCode(order.getMemberCode());
    		
    		String userPayPhone = payment.getFromPhone();
    		
    		if(StringUtils.isEmpty(userPayPhone)){
    			//前面有校验会员存在性，此处会员一定存在
    			userPayPhone = userSpecDto.getMobile();
    		}
    		
    		if(StringUtils.isEmpty(userPayPhone)){
    			return new ResultBean(ResultCode.payment_params_error,"会员没有手机号码信息，无法交易");
    		}
    		
    		
    		Payment paymentTemp = null;
    		boolean flag = false;
    		
    		OpenApiRes res;
    		
    		try {
    			res = combineOrderOpenAPIMememberToFroad(
    					payment, 
    					openAPIPartnerNo, 
    					userPayPhone, 
    					token,
    					order.getCreateSource());
    		} catch (Exception e) {
    			addTimePayment(payment.getPaymentId());
    			
    			paymentTemp = new Payment();
    			paymentTemp.setPaymentId(payment.getPaymentId());
    			paymentTemp.setPaymentStatus("3");
    			paymentTemp.setStep(3);
        		paymentTemp.setFromUserName(userSpecDto.getLoginID());
    			paymentTemp.setFromPhone(userPayPhone);
    			paymentTemp.setRemark("发送消费现金请求异常: " + e.getMessage());
    			flag = updatePayment(paymentTemp);
    			Logger.logError("发起消费现金请求异常,认为该笔订单请求发送成功,结果交由定时任务处理: " + flag, e);
    			
    			throw new PaymentException(e);
    		}
    		
    		paymentTemp = new Payment();
    		
    		paymentTemp.setPaymentId(payment.getPaymentId());
    		paymentTemp.setPaymentStatus("2");//支付请求发送成功
    		paymentTemp.setStep(3);
    		paymentTemp.setBillNo(res.getFroadBillNo());
    		paymentTemp.setFromUserName(userSpecDto.getLoginID());
    		paymentTemp.setFromPhone(userPayPhone);
    		flag = updatePayment(paymentTemp);
    		
    		Logger.logInfo("OpenAPI消耗现金请求发送完毕","openApiRes",res);
    		
    		if(SUCCESS_CODE.equals(res.getResultCode())){
    			addTimePayment(payment.getPaymentId());
    			
    			paymentTemp = new Payment();
    			
    			paymentTemp.setPaymentId(payment.getPaymentId());
    			paymentTemp.setResultCode(res.getResultCode());
    			paymentTemp.setRemark("现金支付OpenAPI已成功受理，等待通知结果");
    			
    			flag = updatePayment(paymentTemp);
    			
    			Logger.logInfo("现金消费请求受理成功 ,修改支付流水结果: " + flag);
    			
    			return new ResultBean(ResultCode.success,"OpenAPI受理成功",res.getPaymentURL());
    		}else{
    			paymentTemp = new Payment();
    			
    			paymentTemp.setPaymentId(payment.getPaymentId());
    			paymentTemp.setPaymentStatus("5");
    			paymentTemp.setStep(4);
    			paymentTemp.setResultCode(res.getResultCode());
    			paymentTemp.setResultDesc(res.getResultDesc());
    			flag = updatePayment(paymentTemp);
    			Logger.logInfo("现金消费请求受失败 ,修改支付流水结果: " + flag);
    			return new ResultBean(ResultCode.failed,res.getResultDesc());
    		}
    		
    	}
    }
    
    
	
	//------------------------------------------------账户系统---------------------------------------------
	@Override
	public UserSpecDto queryByMemberCode(Long memberCode) {
		UserEngineFunc func = new UserEngineFuncImpl();
		UserResult userResult = func.queryByMemberCode(memberCode);
		
		if (userResult.getResult()) {// 请求成功
			if (userResult.getUserList() != null && userResult.getUserList().size() == 1) {
				return userResult.getUserList().get(0);
			}
		} 
		return null;
	}

	
	//-----------------------------------------------积分系统-----------------------------------------------
	
	@Override
	public ResultBean autoPresentFroadPoints(Long memberCode, Integer givePoints,String clientId,String orderId) {
		UserSpecDto userSpecDto = queryByMemberCode(memberCode);
		String paymentId = EsyT.simpleID();
		double payVal = Arith.div(givePoints,1000);
		PaymentPoTemp paymentPoTemp = new PaymentPoTemp(
				clientId, orderId,
				1, 
				payVal, 
				FROAD_POINT_ORG_NO, 
				null, 
				null, 
				"3", //指明赠送积分
				paymentId);
		paymentPoTemp.setCashType(0);
		boolean flag = addPaymentToMongo(paymentPoTemp);
		Logger.logInfo("保存赠送积分支付记录: " + flag);
		if(!flag){
			return new ResultBean(ResultCode.failed);
		}
		Payment paymentTemp;
		try {
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setStep(2);
			flag = updatePayment(paymentTemp);
			Logger.logInfo("修改支付步骤为支付中: " + flag);
			PointsRes res = pointsApiFunc.presentPoints(
					new PointsReq(FROAD_POINT_ORG_NO, 
					userSpecDto.getLoginID(),
					"1",
					orderId, 
					"赠送积分"
					, 
					"", 
					"自动赠送积分", payVal + "", acquirePointPartnerNo(clientId), ""));
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setStep(3);
//			paymentTemp.setToUserName(userSpecDto.getLoginID());
			paymentTemp.setPaymentStatus("2");//支付请求发送成功
			flag = updatePayment(paymentTemp);
			Logger.logInfo("修改支付流水记录: " + flag);
			
			if(SUCCESS_CODE.equals(res.getResultCode())){
				paymentTemp = new Payment();
				paymentTemp.setPaymentId(paymentId);
				paymentTemp.setStep(4);
				paymentTemp.setPaymentStatus("4");//支付成功
				paymentTemp.setResultCode(res.getResultCode());
				paymentTemp.setResultDesc(res.getResultDesc());
				
				flag = updatePayment(paymentTemp);
				Logger.logInfo("修改支付流水记录: " + flag);
				return new ResultBean(ResultCode.success);
			}else{
				paymentTemp = new Payment();
				paymentTemp.setPaymentId(paymentId);
				paymentTemp.setStep(4);
				paymentTemp.setPaymentStatus("5");//支付失败
				paymentTemp.setResultCode(res.getResultCode());
				paymentTemp.setResultDesc(res.getResultDesc());
				flag = updatePayment(paymentTemp);
				Logger.logInfo("修改支付流水记录: " + flag);
				return new ResultBean(ResultCode.failed);
			}
		} catch (Exception e) {
			Logger.logError("发送自动赠送积分请求异常", e);
			paymentTemp = new Payment();
//			paymentTemp.setToUserName(userSpecDto.getLoginID())
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setStep(3);
			paymentTemp.setPaymentStatus("3");//支付请求发送失败
			paymentTemp.setRemark("支付请求发送异常: " + e.getMessage());
			flag = updatePayment(paymentTemp);
			Logger.logInfo("修改支付流水记录: " + flag);
			return new ResultBean(ResultCode.failed);
		}
		
	}
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: queryUserPoints
	 * @Description: 查询用户积分信息
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param loginID
	 * @param pointPartnerNo
	 * @param clientId
	 * @return
	 * @Return: PointInfo
	 */
	private PointInfo queryUserPoints(String loginID,String pointPartnerNo,String clientId){
		
		PointsApiFunc pointsFunc = new PointsApiFuncImpl();
		PointsRes res = pointsFunc.queryParAccountPoints(new PointsReq(null, loginID, "1", pointPartnerNo));
		
		if(SUCCESS_CODE.equals(res.getResultCode())){
			List<PointsAccount> pointsAccounts = res.getAccountPointsInfoList();
			PointInfo pointInfo = new PointInfo();
			//取出该用户所有积分信息中属于方付通积分和对应的平台银行积分
			for (PointsAccount pointsAccount : pointsAccounts) {
				if(FROAD_POINT_ORG_NO.equals(pointsAccount.getOrgNo())){
					pointInfo.setFroadPoint(Double.parseDouble(pointsAccount.getPoints()));
					pointInfo.setFroadAccountId(pointsAccount.getAccountId());
					break;
				}
			}
			
			String bankOrgNo = acquireBankOrgNo(clientId);//获取用户银行机构号
			if(StringUtils.isEmpty(bankOrgNo)){ //没有银行机构号，无需遍历银行积分
				return pointInfo;
			}
			for (PointsAccount pointsAccount : pointsAccounts) {
				if(bankOrgNo.equals(pointsAccount.getOrgNo())){
					pointInfo.setBankPoint(Double.parseDouble(pointsAccount.getPoints()));
					pointInfo.setBankAccountId(pointsAccount.getAccountId());
					break;
				}
			}
			return pointInfo;
		}
		return null;
	}
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: consumePoints
	 * @Description: 消耗积分
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param req
	 * @return
	 * @throws Exception
	 * @Return: PointsRes
	 */
	private PointsRes consumePoints(PointsReq req) throws Exception {
		try {
			return pointsApiFunc.consumePoints(req);
		} catch (Exception e) {
			throw new Exception(e);
		}
		
	}
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: refundPoints
	 * @Description: 自动退还积分
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param payment
	 * @return
	 * @throws Exception
	 * @Return: PointsRes
	 */
	@Override
	public ResultBean autoRefundPoints(Payment payment){
		Payment paymentTemp;
		boolean flag = false;
		
		PointsReq req = new PointsReq();
		boolean isBankPoint = payment.getPaymentOrgNo().equals(FROAD_POINT_ORG_NO) ? false : true;
		if(isBankPoint){
			req = new PointsReq(
					payment.getPaymentId()
					, payment.getFromAccountNo()
					, String.valueOf(Arith.div(payment.getPaymentValue(),payment.getPointRate()))
					, payment.getFromUserName()
					, payment.getBillNo()
					, acquirePointPartnerNo(payment.getClientId())
					, "社区银行发起用户主动退积分"
					, "社区银行发起用户主动退积分"
					, payment.getPaymentOrgNo()
					);
		}else{
			req = new PointsReq(
					payment.getPaymentId()
					, payment.getFromAccountNo()
					, String.valueOf(payment.getPaymentValue())
					, payment.getFromUserName()
					, payment.getBillNo()
					, acquirePointPartnerNo(payment.getClientId())
					, "社区银行发起用户主动退积分"
					, "社区银行发起用户主动退积分"
					,""
					);

		}
		
		req.setRemark(payment.getOrderId());
		PointsRes res;
		try {
			res = pointsApiFunc.refundPoints(req);
		} catch (Exception e) {
			
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(payment.getPaymentId());
			paymentTemp.setPaymentStatus("3");
			paymentTemp.setStep(3);
//			paymentTemp.setAutoRefund("1");
			paymentTemp.setRemark("发起退款积分请求异常: " + e.getMessage());
			flag = updatePayment(paymentTemp);
			
			Logger.logError("发起消费积分请求异常,认为该笔订单请求发送成功,结果交由定时任务处理: " + flag, e);
			return new ResultBean(ResultCode.success);
		}
		
		if(SUCCESS_CODE.equals(res.getResultCode())){
			
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(payment.getPaymentId());
			paymentTemp.setRemark("自动退还积分成功: " + res.getResultDesc());
//			paymentTemp.setAutoRefund("2");
//			paymentTemp.setRefundPointsBillNo(res.getRefundPointsNo());
			flag = updatePayment(paymentTemp);
			Logger.logInfo("自动退还积分成功 ,修改支付流水结果: " + flag);
			return new ResultBean(ResultCode.success);
		}else{
			
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(payment.getPaymentId());
//			paymentTemp.setAutoRefund("1");
			paymentTemp.setRemark("自动退还积分失败: " + res.getResultDesc());
			
			flag = updatePayment(paymentTemp);
			Logger.logInfo("自动退还积分失败 ,修改支付流水结果: " + flag);
			return new ResultBean(ResultCode.failed,"退还积分失败");
		}
	}
	
	
	@Override
	public ResultBean userGoOnRefundPoints(Payment payment) {
		
		Payment paymentTemp;
		boolean flag = false;
		
		PointsReq req = new PointsReq();
		boolean isBankPoint = payment.getPaymentOrgNo().equals(FROAD_POINT_ORG_NO) ? false : true;
		if(isBankPoint){
			req = new PointsReq(
					payment.getPaymentId()
					, payment.getFromAccountNo()
					, String.valueOf(Arith.div(Arith.div(payment.getPaymentValue(), 1000),payment.getPointRate()))
					, payment.getFromUserName()
//					, String.valueOf(Arith.div(payment.getPaymentValue(), 1000))
					, payment.getBillNo()
					, acquirePointPartnerNo(payment.getClientId())
					, "社区银行发起用户主动退积分"
					, "社区银行发起用户主动退积分"
					, payment.getPaymentOrgNo()
					);
		}else{
			req = new PointsReq(
					payment.getPaymentId()
					, payment.getFromAccountNo()
					, String.valueOf(Arith.div(payment.getPaymentValue(), 1000))
					, payment.getFromUserName()
//					, ""
					, payment.getBillNo()
					, acquirePointPartnerNo(payment.getClientId())
					, "社区银行发起用户主动退积分"
					, "社区银行发起用户主动退积分"
					, ""
					);

		}
		
		req.setRemark(payment.getOrderId());
		
		paymentTemp = new Payment();
		paymentTemp.setPaymentId(payment.getPaymentId());
		paymentTemp.setStep(2);
		
		flag = updatePayment(paymentTemp);
		
		Logger.logInfo("修改支付信息结果: " + flag);
		
		PointsRes res;
		
		try {
			res = pointsApiFunc.refundPoints(req);
		} catch (Exception e) {
			addTimePayment(payment.getPaymentId());
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(payment.getPaymentId());
			paymentTemp.setPaymentStatus("3");
			paymentTemp.setStep(3);
			paymentTemp.setRemark("发起退积分请求异常: " + e.getMessage());
			flag = updatePayment(paymentTemp);
			Logger.logError("发起退还积分请求异常,认为该笔订单请求发送成功,结果交由定时任务处理: " + flag, e);
			
			return new ResultBean(ResultCode.payment_thirted_request_faild,"请求积分平台异常",payment.getPaymentId());
		}
		
		if(SUCCESS_CODE.equals(res.getResultCode())){
			
			removeTimePayment(payment.getPaymentId());
			
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(payment.getPaymentId());
			paymentTemp.setPaymentStatus("4");
			paymentTemp.setStep(4);
			paymentTemp.setBillNo(res.getPayPointsNo());
			paymentTemp.setResultCode(res.getResultCode());
			paymentTemp.setResultDesc(res.getResultDesc());
//			paymentTemp.setRefundPointsBillNo(res.getRefundPointsNo());
			paymentTemp.setRemark("积分退款，积分平台返回成功结果");	
			flag = updatePayment(paymentTemp);			
			
			Logger.logInfo("用户退还积分成功 ,修改支付流水结果: " + flag);
			return new ResultBean(ResultCode.success);
			
		}else{
			
			removeTimePayment(payment.getPaymentId());
			
			paymentTemp.setPaymentId(payment.getPaymentId());
			paymentTemp.setPaymentStatus("5");//支付失败
			paymentTemp.setStep(4);
			paymentTemp.setResultCode(res.getResultCode());
			paymentTemp.setResultDesc(res.getResultDesc());
			paymentTemp.setRemark("积分退款，积分平台返回成功结果");	
			flag = updatePayment(paymentTemp);
			Logger.logInfo("用户退还积分失败 ,修改支付流水结果: " + flag);
			return new ResultBean(ResultCode.failed,"退还积分失败");
		}
	}
	
	
	//-----------------------------------------------oenapi-----------------------------------------------
	@Override
	public ResultBean refundCashaAndSavePointInfo(PaymentPoTemp paymentCash,PaymentPoTemp paymentPoint,Double paymentCashHis,String refundTypeCode) {
		
		String paymentIdHis = paymentCash.getPaymentId(); //历史支付单信息
		paymentCash.setPaymentId(EsyT.simpleID());
		boolean flag = addPaymentToMongo(paymentCash);
		RefundTempBean refundTempBean = new RefundTempBean();
		
		if(!flag){
			Logger.logError("保存退款现金流水失败");
			return new ResultBean(ResultCode.payment_create_payment_faild,refundTempBean);
		}
		
		refundTempBean.setPaymentCashPaymentId(paymentCash.getPaymentId());
		
		if(paymentPoint != null){
			paymentPoint.setPaymentId(EsyT.simpleID());
			flag = addPaymentToMongo(paymentPoint);
			if(!flag){
				Logger.logError("保存退款积分流水失败");
				return new ResultBean(ResultCode.failed,refundTempBean);
			}else{
				refundTempBean.setPaymentPointPaymentId((paymentPoint.getPaymentId()));
			}
		}
		
		
		refundTempBean.setRefundCashState(RefundState.REFUND_PROCESSING);
		refundTempBean.setRefundPointState(RefundState.REFUND_PROCESSING);
		
		Payment paymentTemp = null;
		RefundType refundType = null;
		RefundType[] refundTypes = RefundType.values();
		for (RefundType r : refundTypes) {
			if(r.getVal().equals(refundTypeCode)){
				refundType = r;
				break;
			}
		}
		
		OpenApiReq req = new OpenApiReq(
				paymentCash.getPaymentId(),
				paymentIdHis, //关联需要退款的历史流水
				paymentCashHis.toString(),
				paymentCash.getPayValue().toString(),
				refundType,
				acquireOpenAPIPartnerNo(paymentCash.getClientId()),
				"社区银行退现金申请", 
				acquireOpenAPIPartnerNo(paymentCash.getClientId()),
				OpenApiCommand.REFUND_NOTICE_URL);
		
		paymentTemp = new Payment();
		paymentTemp.setPaymentId(paymentCash.getPaymentId());
		paymentTemp.setStep(2);
		
		flag = updatePayment(paymentTemp);
		
		Logger.logInfo("修改退款流水记录: " + flag);
		
		OpenApiRes res;
		try {
			res = openApiFunc.refund(req);
		} catch (Exception e) {
			
			addTimePayment(paymentCash.getPaymentId());
			
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentCash.getPaymentId());
			paymentTemp.setRemark("发起退现金请求异常: " + e.getMessage());
			paymentTemp.setPaymentStatus("3");
			paymentTemp.setStep(3);
			
			flag = updatePayment(paymentTemp);
			
			Logger.logInfo("修改支付信息结果: " + flag);
			return new ResultBean(ResultCode.payment_thirted_request_faild,refundTempBean);
		}
		
		
		paymentTemp = new Payment();
		paymentTemp.setPaymentId(paymentCash.getPaymentId());
		paymentTemp.setStep(3);
		flag = updatePayment(paymentTemp);
		Logger.logInfo("修改退款流水记录: " + flag);
		
		if(SUCCESS_CODE.equals(res.getResultCode())){
			
			addTimePayment(paymentCash.getPaymentId());
			
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentCash.getPaymentId());
			paymentTemp.setResultCode(res.getResultCode());
			paymentTemp.setResultDesc(res.getResultDesc());
			paymentTemp.setPaymentStatus("2");
			paymentTemp.setBillNo(res.getFroadBillNo());
			
			flag = updatePayment(paymentTemp);
			Logger.logInfo("修改退款流水记录: " + flag);
			return new ResultBean(ResultCode.success,refundTempBean);
			
		}else{
			Logger.logInfo("发起退现金请求受理失败");
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentCash.getPaymentId());
			paymentTemp.setResultCode(res.getResultCode());
			paymentTemp.setResultDesc(res.getResultDesc());
			paymentTemp.setPaymentStatus("5");
			paymentTemp.setStep(4);
			flag = updatePayment(paymentTemp);
			Logger.logInfo("修改退款流水记录: " + flag);
			
			if(paymentPoint != null){
				paymentTemp = new Payment();
				paymentTemp.setPaymentId(paymentPoint.getPaymentId());
				paymentTemp.setPaymentStatus("5");
				paymentTemp.setStep(4);
				paymentTemp.setRemark("由于现金退款失败，该笔积分退款流水并未实际处理，但结果为失败");
				refundTempBean.setRefundPointState(RefundState.REFUND_FAILED);
				flag = updatePayment(paymentTemp);
				Logger.logInfo("修改退款流水记录: " + flag);
			}
			
			
			refundTempBean.setRefundCashState(RefundState.REFUND_FAILED);
			
			return new ResultBean(ResultCode.failed,refundTempBean);
		}
	}
	
	@Override
	public ResultBean refundPointOfUserPay(PaymentPoTemp paymentPoint) {
		
		paymentPoint.setPaymentId(EsyT.simpleID());
		RefundTempBean refundTempBean = new RefundTempBean();
		String paymentId = paymentPoint.getPaymentId();
		
		boolean flag = addPaymentToMongo(paymentPoint);
		if(!flag){
			Logger.logInfo("保存退款积分流水失败");
			return new ResultBean(ResultCode.payment_create_payment_faild,refundTempBean);
		}
		
		refundTempBean.setPaymentPointPaymentId(paymentId);
		
		PointsReq req = new PointsReq();
		boolean isBankPoint = paymentPoint.getPayOrgNo().equals(FROAD_POINT_ORG_NO) ? false : true;
		if(isBankPoint){
			req = new PointsReq(
					paymentId
					, paymentPoint.getFromAccountNo()
					, String.valueOf(Arith.div(paymentPoint.getPayValue(),paymentPoint.getPointRate()))
					, paymentPoint.getFromUserName()
//					, String.valueOf(paymentPoint.getPayValue())
					, paymentPoint.getBillNo()
					, acquirePointPartnerNo(paymentPoint.getClientId())
					, "社区银行发起用户主动退积分"
					, "社区银行发起用户主动退积分"
					, paymentPoint.getPayOrgNo()
					);
		}else{
			req = new PointsReq(
					paymentId
					, paymentPoint.getFromAccountNo()
					, String.valueOf(paymentPoint.getPayValue())
					, paymentPoint.getFromUserName()
					, paymentPoint.getBillNo()
					, acquirePointPartnerNo(paymentPoint.getClientId())
					, "社区银行发起用户主动退积分"
					, "社区银行发起用户主动退积分"
					,""
					);

		}
		
		req.setRemark(paymentPoint.getOrderId());
		
		Logger.logInfo("用户主动触发退款信息","req",req);
		Logger.logInfo("用户主动触发退款信息","paymentPoint",paymentPoint);
		
		Payment paymentTemp = new Payment();
		paymentTemp.setPaymentId(paymentId);
		paymentTemp.setStep(2);
		Logger.logInfo("修改支付信息结果: " + flag);
		
		PointsRes res;
		
		try {
			res = pointsApiFunc.refundPoints(req);
		} catch (Exception e) {
			addTimePayment(paymentId);
			
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setStep(3);
			paymentTemp.setPaymentStatus("3");
			paymentTemp.setRemark("用户主动申请退积分异常,按照支付请求发送成功处理: " + e.getMessage());
			flag = updatePayment(paymentTemp);
			Logger.logInfo("修改支付信息结果: " + flag);
			refundTempBean.setRefundPointState(RefundState.REFUND_PROCESSING);
			return new ResultBean(ResultCode.payment_thirted_request_faild,refundTempBean);
		}
		
		if(SUCCESS_CODE.equals(res.getResultCode())){
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setStep(4);
			paymentTemp.setPaymentStatus("4");
			paymentTemp.setRemark("用户主动申请退积分已成功退还");
			paymentTemp.setResultCode(res.getResultCode());
			paymentTemp.setResultDesc(res.getResultDesc());
			flag = updatePayment(paymentTemp);
			Logger.logInfo("修改支付信息结果: " + flag);
			refundTempBean.setRefundPointState(RefundState.REFUND_SUCCESS);
			return new ResultBean(ResultCode.success,refundTempBean);
		}else{
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setStep(4);
			paymentTemp.setPaymentStatus("5");
			paymentTemp.setRemark("用户主动申请退积分失败，成功发送退积分请求，但积分系统返回失败结果");
			paymentTemp.setResultCode(res.getResultCode());
			paymentTemp.setResultDesc(res.getResultDesc());
			flag = updatePayment(paymentTemp);
			Logger.logInfo("修改支付信息结果: " + flag);
			refundTempBean.setRefundPointState(RefundState.REFUND_FAILED);
			return new ResultBean(ResultCode.failed,"退款失败，退还积分失败",refundTempBean);
		}
	}
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: combineOrderOpenAPIMememberToFroad
	 * @Description: 通过OpenAPI接口发生资金转变事件(用户资金--->方付通账户)
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @return
	 * @throws Exception 
	 * @Return: OpenApiRes
	 */
	private OpenApiRes combineOrderOpenAPIMememberToFroad(Payment payment,String openAPIPartnerNo,String mobile,String token,String createSourceCode) throws Exception{

		Double payValue = Arith.div(payment.getPaymentValue(), 1000);
		
		List<OpenApiOrderDetail> orderDetails = new ArrayList<OpenApiOrderDetail>();
		OpenApiOrderDetail detail = new OpenApiOrderDetail(
				payment.getPaymentId() //支付流水号
				,
				payValue.toString()//支付金额
				, openAPIPartnerNo, 
				"社区银行订单"
				);
		
		orderDetails.add(detail);
		
		Client client = null;
		Client[] clients = Client.values();
		for (Client c : clients) {
			if(c.getCode().equals(createSourceCode)){
				client = c;
				break;
			}
		}
		
		PayType payType = null;
		PayType[] payTypes = PayType.values();
		for (PayType p : payTypes) {
			if(p.getCode().equals(payment.getPaymentTypeDetails().toString())){
				payType = p;
				break;
			}
		}
		OpenApiReq req = new OpenApiReq(
				orderDetails, 
				BillOrderType.COMBINE,//指定消费现金模式
				payValue.toString(), //支付金额
				payType, //现金支付方式
				client,
				payment.getPaymentOrgNo(), 
				mobile, 
				PayDirect.F_MERCHANT, //直连方式
				"社区银行支付订单", 
				openAPIPartnerNo, 
				"社区银行支付订单", 
				token, //FIXME 需要传入银行的验证码  验证码（银行发送的）
				"",//FIXME 需要从配置文件加载  第三方支付处理完毕的跳转地址 
				DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT4, new Date()), 
				"",
				"10"
				);
		Logger.logInfo("开始现金支付: " + JSONObject.toJSONString(req));
		try {
			OpenApiFunc api = new OpenApiFuncImpl();
			return api.combineOrder(req);
		} catch (Exception e) {
			Logger.logError("发起合并支付请求异常",e);
			throw new Exception(e);
		}
	}
	
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: combineOrderOpenAPIFroadToMerchantOutlet
	 * @Description: 通过OpenAPI接口发生资金转变时间(方付通对公户--->商户门店帐号)
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @return
	 * @throws Exception 
	 * @Return: OpenApiRes
	 */
	private OpenApiRes combineOrderOpenAPIFroadToMerchantOutlet(String paymentId,double paymnetValue,String openAPIPartnerNo,String payOrgNo,String merchantId,String merchantOutletId) throws Exception{
		String payValue = String.valueOf(paymnetValue);
		
		List<OpenApiOrderDetail> orderDetails = new ArrayList<OpenApiOrderDetail>();
		OpenApiOrderDetail detail = new OpenApiOrderDetail(
				paymentId //支付流水号
				,
				payValue//支付金额
				, openAPIPartnerNo, 
				"社区银行结算订单"
				);
		orderDetails.add(detail);
		
		OpenApiReq req = new OpenApiReq(
				orderDetails, 
				BillOrderType.COMBINE,//指定消费现金模式
				payValue, //支付金额
				PayType.TIMELY_PAY, //及时结算
				Client.PC,
				payOrgNo, 
				null, 
				PayDirect.F_MERCHANT, //直连方式
				"社区银行结算订单", 
				openAPIPartnerNo, 
				"社区银行结算订单", 
				"",
				"",
				DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT4, new Date()), 
				merchantId + "_" +merchantOutletId + "|" + OpenApiCommand.MERCHANT_ACCT_QUERY_URL,
				"20"
				);
		Logger.logInfo("开始结算现金: " + JSONObject.toJSONString(req));
		try {
			OpenApiFunc api = new OpenApiFuncImpl();
			return api.combineOrder(req);
		} catch (Exception e) {
			Logger.logError("发起结算请求异常",e);
			throw new Exception(e);
		}
	}
	

	@Override
	public OpenApiRes queryOpenAPIPaymentState(String paymentId,Date createTime,String orderType, String clientType,String clientId) throws Exception {
		QueryParamApiReq req = 
				new QueryParamApiReq(
						OpenApiCommand.QUERY_SINGLE, orderType,paymentId, "",
				acquireOpenAPIPartnerNo(clientId), clientType
         );
		String queryTime = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT4, createTime);
		req.setQueryTime(queryTime + "|" + queryTime);
		Logger.logInfo("主动确认支付状态开始");
		try {
			OpenApiFunc api = new OpenApiFuncImpl();
			return api.query(req);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResultBean refundPresentFroadPoints(PaymentPoTemp paymentPoTemp,String loginID) {
		
		String pointPartnerNo = acquirePointPartnerNo(paymentPoTemp.getClientId());
		PointInfo pointInfo = queryUserPoints(loginID, acquirePointPartnerNo(paymentPoTemp.getClientId()), paymentPoTemp.getClientId());
		if(pointInfo == null){
			return new ResultBean(ResultCode.refund_froad_point_lack);
		}
		
		if(pointInfo.getFroadPoint() < paymentPoTemp.getPayValue()){ //方付通积分不足
			return new ResultBean(ResultCode.refund_froad_point_lack);
		}
		
		String paymentId = EsyT.simpleID();
	
		RefundTempBean refundTempBean = new RefundTempBean();
		refundTempBean.setPresentPointPaymentId(paymentId);
		
		paymentPoTemp.setPayOrgNo(FROAD_POINT_ORG_NO);
		paymentPoTemp.setPaymentId(paymentId);
		boolean flag = addPaymentToMongo(paymentPoTemp);
		
		if(!flag){
			Logger.logError("支付流水生成失败","paymentPoTemp",paymentPoTemp);
			return new ResultBean(ResultCode.payment_create_payment_faild);
		}
		
		PointsReq req;
		
		String consumeAccountId;
		
		double payValue = paymentPoTemp.getPayValue();
		consumeAccountId = pointInfo.getFroadAccountId();
		
		req = new PointsReq(
				FROAD_POINT_ORG_NO,//消耗方付通积分机构号的积分
				paymentPoTemp.getOrderId(), //accountId
				payValue, //points
				loginID,//accountMarked 
				pointPartnerNo,  //partnerNo
				"由社区银行发起的扣除已赠送的积分数目", //businessType
				null,
				pointInfo.getFroadAccountId()
				);
		
		Payment paymentTemp = null;
		
		flag = false;
		
		PointsRes res = null;
		try {
			res = consumePoints(req);
		} catch (Exception e) {
			addTimePayment(paymentId);
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setPaymentStatus("3");
			paymentTemp.setStep(3);
			paymentTemp.setFromAccountNo(consumeAccountId);
			paymentTemp.setRemark("发起扣除赠送积分请求异常: " + e.getMessage());
			flag = updatePayment(paymentTemp);
			Logger.logError("发起扣除赠送积分请求异常,认为该笔订单请求发送成功,结果交由定时任务处理,修改流水信息: " + flag, e);
			refundTempBean.setRefundPresentPointState(RefundState.REFUND_PROCESSING);
			return new ResultBean(ResultCode.payment_thirted_request_faild,"操作失败",refundTempBean);
		}
		
		paymentTemp = new Payment();
		paymentTemp.setPaymentId(paymentId);
		paymentTemp.setPaymentStatus(PaymentStatus.pay_request_success.getCode());//支付请求发送成功
		paymentTemp.setStep(3);
		paymentTemp.setFromAccountNo(consumeAccountId);
		paymentTemp.setFromUserName(loginID);
		
		flag = updatePayment(paymentTemp);
		
		//更改支付记录信息
		if(SUCCESS_CODE.equals(res.getResultCode())){
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setPaymentStatus("4");
			paymentTemp.setStep(4);
			paymentTemp.setResultCode(res.getResultCode());
			paymentTemp.setResultDesc("扣除已赠送的积分成功");
			paymentTemp.setBillNo(res.getPayPointsNo());
			flag = updatePayment(paymentTemp);			
			Logger.logInfo("扣除已赠送的积分成功,修改支付流水: " + flag);
			refundTempBean.setRefundPresentPointState(RefundState.REFUND_SUCCESS);
			return new ResultBean(ResultCode.success,"扣除已赠送积分成功",refundTempBean);
		}else{
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setPaymentStatus(PaymentStatus.pay_fail.getCode());//支付失败
			paymentTemp.setStep(4);
			paymentTemp.setResultCode(res.getResultCode());
			paymentTemp.setResultDesc(res.getResultDesc());
			flag = updatePayment(paymentTemp);
			Logger.logInfo("扣除已赠送的积分失败,修改支付流水: " + flag);
			refundTempBean.setRefundPresentPointState(RefundState.REFUND_FAILED);
			return new ResultBean(ResultCode.success,"扣除已赠送积分成功",refundTempBean);
		}
	}

	@Override
	public ResultBean transferCashFromFroadToMerchant(PaymentPoTemp paymentPoTemp,String merchantId,String merchantOutletId) {
		
		List<ClientPaymentChannel> channels = BaseSubassembly.acquireClientPaymentChannelFromRedis(paymentPoTemp.getClientId());
		String paymentOrgNo = null;
		
		for (ClientPaymentChannel channel : channels) {
			if(String.valueOf(CashType.timely_pay.code()).equals(channel.getType())){ //获取及时支付的paymentOrgNo
				paymentOrgNo = channel.getPaymentOrgNo();
				break;
			}
		}
		
		if(paymentOrgNo == null){
			Logger.logError("clientId: " + paymentPoTemp.getClientId() + " 没有找到对应的即时支付PayOrgNo");
			return new ResultBean(ResultCode.failed);
		}
		
		String paymentId = EsyT.simpleID();
		paymentPoTemp.setPaymentId(paymentId);
		paymentPoTemp.setPayOrgNo(paymentOrgNo);
		boolean flag = addPaymentToMongo(paymentPoTemp);
		
		if(!flag){
			Logger.logError("保存结算流水失败");
			return new ResultBean(ResultCode.failed,"保存结算流水失败");
		}
		Payment paymentTemp = null;

		Logger.logInfo("结算现金开始,该笔支付信息改为支付中.....");
		paymentTemp = new Payment();
		paymentTemp.setPaymentId(paymentId);
		paymentTemp.setStep(2);
		flag = updatePayment(paymentTemp);
		if(!flag){
			Logger.logError("将支付信息 step 改为 第2步失败");
			return new ResultBean(ResultCode.failed,"更改数据失败",paymentId);
		}
		
		OpenApiRes res = null;
		
		try {
			res = combineOrderOpenAPIFroadToMerchantOutlet(paymentId, paymentPoTemp.getPayValue(),
					acquireOpenAPIPartnerNo(paymentPoTemp.getClientId()), paymentOrgNo,merchantId, merchantOutletId);
		} catch (Exception e) {
			addTimePayment(paymentId);
			
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setPaymentStatus(PaymentStatus.pay_request_fail.getCode());//支付请求发送失败
			paymentTemp.setStep(3);
			paymentTemp.setPaymentStatus("3");
			paymentTemp.setRemark("发送结算请求失败: " + e.getMessage());
			
			flag = updatePayment(paymentTemp);
			
			Logger.logError("发送结算请求失败 ，将支付记录改为发送支付请求失败: " + flag, e);
			return new ResultBean(ResultCode.success,"发送结算请求结果未确定",paymentId);
			//此处返回成功是为了告诉结算功能结果不确定，让结算功能将结算状态理解为结算中
		}
		paymentTemp = new Payment();
		paymentTemp.setPaymentId(paymentId);
		paymentTemp.setPaymentStatus("2");//支付请求发送成功
		paymentTemp.setStep(3);
		paymentTemp.setBillNo(res.getFroadBillNo());
		flag = updatePayment(paymentTemp);
		
		if(SUCCESS_CODE.equals(res.getResultCode())){
			addTimePayment(paymentId);
			Logger.logInfo("OpenAPI受理成功");
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setResultCode(res.getResultCode());
			paymentTemp.setResultDesc(res.getResultDesc());
			flag = updatePayment(paymentTemp);
			return new ResultBean(ResultCode.success,"OpenAPI受理成功",paymentId);
		}else{
			
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setPaymentStatus(PaymentStatus.pay_fail.getCode());//支付失败
			paymentTemp.setStep(4);
			paymentTemp.setResultCode(res.getResultCode());
			paymentTemp.setResultDesc(res.getResultDesc());
			paymentTemp.setRemark("OpenAPI受理失败");
			flag = updatePayment(paymentTemp);
			Logger.logInfo("OpenAPI受理失败");
		}
		return new ResultBean(ResultCode.failed,res.getResultDesc(),paymentId);
	}

	@Override
	public ResultBean verifyFoilCardNum(String clientId,String paymentOrgNo, String mobileNum) {
		OpenApiReq req=new OpenApiReq(
				paymentOrgNo,
				CheckType.ACCOUNT_MOBILE,
				mobileNum, null,acquireOpenAPIPartnerNo(clientId));
		
		try {
			OpenApiRes res = new OpenApiFuncImpl().accountCheck(req);
			if(OpenApiCommand.SUCCESS.equals(res.getCheckResultCode())){
				return new ResultBean(ResultCode.success);
			}else{
				return new ResultBean(ResultCode.failed,"该手机号尚未开通贴膜卡支付业务");
			}
		} catch (Exception e) {
			Logger.logError("校验手机贴膜卡号码异常",e);
		}
		
		return new ResultBean(ResultCode.failed,"该手机号尚未开通贴膜卡支付业务");
	}

	@Override
	public ResultBean queryBankPointOfBankNo(String clientId, String bankNo) {
		
		String paymentOrgNo = acquireBankOrgNo(clientId);
		PointsReq req = new PointsReq();
		req.setOrgNo(paymentOrgNo);
		req.setMobileNum(bankNo);
		req.setPartnerNo(acquirePointPartnerNo(clientId));
		PointsRes res = new PointsApiFuncImpl().queryBankPointsByMobile(req);
		if(SUCCESS_CODE.equals(res.getResultCode())){
			PointsAccount pointsAccount = res.getPointsAccount();
			PointInfo pointInfo = new PointInfo();
			String bankOrgNo = acquireBankOrgNo(clientId);//获取用户银行机构号
			if(StringUtils.isEmpty(bankOrgNo)){ //没有银行机构号，无需遍历银行积分
				return new ResultBean(ResultCode.success, pointInfo);
			}
			if(pointsAccount != null){
				pointInfo.setBankPoint(Double.parseDouble(pointsAccount.getPoints()));
				pointInfo.setBankAccountId(pointsAccount.getAccountId());
			}
			return new ResultBean(ResultCode.success, pointInfo);
		}else{
			return new ResultBean(ResultCode.failed, res.getResultDesc());
		}
	}
	
	@Override
	public ResultBean updateMerchantMonthCountForRefund(String clientId,String merchantId,String subOrderType,Long createTime,Integer money,int refundCount){
		
		MerchantMonthCount mmc = new MerchantMonthCount();
		int refundMoney = money;
//		if(OrderType.face2face.getCode().equals(subOrderType)){
//			mmc.setFaceOrderCount(0);
//			mmc.setFaceOrderMoney(refundMoney);
		if(SubOrderType.presell_org.getCode().equals(subOrderType)){
			mmc.setFaceOrderCount(refundCount);
			mmc.setFaceOrderMoney(refundMoney);
//		}else if(OrderType.special_merchant.getCode().equals(orderType)){//保留代码勿删
//			mmc.setSellOrderCount(0);
//			mmc.setSellOrderMoney(refundMoney);
		}else if(SubOrderType.group_merchant.getCode().equals(subOrderType)){
			mmc.setGroupOrderCount(refundCount);
			mmc.setGroupOrderMoney(refundMoney);
		}else{
			return new ResultBean(ResultCode.success, "不需要更新");
		}
		
		SqlSession session = getSessionFactory().openSession(true);
		MerchantMonthCountMapper mapper = session.getMapper(MerchantMonthCountMapper.class);
	    
		Calendar ca =  Calendar.getInstance();
		ca.setTimeInMillis(createTime);
		mmc.setClientId(clientId);
		mmc.setMerchantId(merchantId);
		mmc.setYear(Integer.toString(ca.get(Calendar.YEAR)));
		mmc.setMonth(String.format("%02d",ca.get(Calendar.MONTH)+1));
		mmc.setMonthMoney(refundMoney);
	    mapper.minusMerchantMonthCount(mmc);
	    
	    session.commit(true);
		closeSession(session);
		
		return new ResultBean(ResultCode.success,"更新成功");
	}
	@Override
	public ResultBean updateMerchantMonthCountForPaySuccess(OrderMongo order){
		SqlSession session = getSessionFactory().openSession();
		Calendar ca =  Calendar.getInstance();
		ca.setTimeInMillis(order.getCreateTime());
		MerchantMonthCountMapper mapper = session.getMapper(MerchantMonthCountMapper.class);
		MerchantMonthCount mmc = new MerchantMonthCount();
		mmc.setYear(Integer.toString(ca.get(Calendar.YEAR)));
		mmc.setMonth(String.format("%02d",ca.get(Calendar.MONTH)+1));
		
		if(order.getIsQrcode()==1){
			mmc.setClientId(order.getClientId());
			mmc.setMerchantId(order.getMerchantId());
			Long id = mapper.selectRecordId(mmc);
			if(id==null){
				mapper.insertNewRecord(mmc);
				if(mmc.getId()==null){
					id = mapper.selectRecordId(mmc);
					mmc.setId(id);
				}
			}else{
				mmc.setId(id);
			}
			mmc.setFaceOrderCount(1);
			mmc.setFaceOrderMoney(order.getTotalPrice());
			mmc.setMonthMoney(order.getTotalPrice());
			mapper.plusMerchantMonthCount(mmc);
			session.commit(true);
			closeSession(session);
			return new ResultBean(ResultCode.success);
		}
		
		List<SubOrderMongo> subOrderList = orderSupport.getSubOrderListByOrderId(order.getClientId(),order.getOrderId());
		
		if(subOrderList==null||subOrderList.size()==0){
			return new ResultBean(ResultCode.success);
		}
		
		MerchantMonthCount mmcTemp = null;
		
		Map<String,MerchantMonthCount> record = new HashMap<String,MerchantMonthCount>();
		String key;
		for(SubOrderMongo subOrder : subOrderList){
			List<ProductMongo> pmList = subOrder.getProducts();
			if (pmList == null || pmList.size() == 0) {
				continue;
			}
			
	        mmc.setClientId(subOrder.getClientId());
	        mmc.setMerchantId(subOrder.getMerchantId());

	        key = String.format("%s:%s:%s:%s",subOrder.getClientId(),subOrder.getMerchantId(),mmc.getYear(),mmc.getMonth());
	        mmcTemp = getMMC(mmc,record,key);
	        String orderTypeCode = subOrder.getType();
	        if(OrderType.group_merchant.getCode().equals(orderTypeCode)){
	        	mmcTemp.setGroupOrderCount(1+getIntValue(mmcTemp.getGroupOrderCount()));
	        	for (ProductMongo pm : pmList) {
	        		int money = getIntValue(pm.getVipMoney())*getIntValue(pm.getVipQuantity()) +getIntValue(pm.getMoney())*getIntValue(pm.getQuantity());
        			mmcTemp.setGroupOrderMoney(money+getIntValue(mmcTemp.getGroupOrderMoney()));
        			mmcTemp.setMonthMoney(money+getIntValue(mmcTemp.getMonthMoney()));
	        	}
//	        	保留代码备用，误删
//	        }else if(OrderType.special_merchant.getCode().equals(orderTypeCode)){
//	        	mmcTemp.setSellOrderCount(1+getIntValue(mmcTemp.getSellOrderCount()));
//	        	for (ProductMongo pm : pmList) {
//	        		int money = getIntValue(pm.getVipMoney())*getIntValue(pm.getVipQuantity()) +getIntValue(pm.getMoney())*getIntValue(pm.getQuantity());
//        			mmcTemp.setSellOrderMoney(money+getIntValue(mmcTemp.getSellOrderMoney()));
//        			mmcTemp.setMonthMoney(money+getIntValue(mmcTemp.getMonthMoney()));
//	        	}
	        }else{
	        	continue;
	        }
	        		
		}
		
		
		for(Map.Entry<String, MerchantMonthCount> rec : record.entrySet()){
			mmcTemp = rec.getValue();
			if(mmcTemp.getMonthMoney()==null){
				continue;
			}
			Long id = mapper.selectRecordId(mmcTemp);
			if(id==null){
				mapper.insertNewRecord(mmcTemp);
				if(mmcTemp.getId()==null){
					id = mapper.selectRecordId(mmcTemp);
					mmcTemp.setId(id);
				}
			}else{
				mmcTemp.setId(id);
			}
		    mapper.plusMerchantMonthCount(mmcTemp);
		}
		
		record.clear();
		session.commit(true);
		closeSession(session);
		return new ResultBean(ResultCode.success);
	}


	private MerchantMonthCount getMMC(MerchantMonthCount mmc,Map<String,MerchantMonthCount> record,String key) {
        
        MerchantMonthCount mmcTemp = record.get(key);
        if(mmcTemp == null){
        	mmcTemp =new MerchantMonthCount();
        	try {
				BeanUtils.copyProperties(mmcTemp, mmc);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
        	record.put(key, mmcTemp);
        }
        
        return mmcTemp;
	}

	@Override
	public String queryOrderPaymentChannel(String orderId){
	   
		OrderMongo order = mongoManager.findOne(new BasicDBObject("_id",orderId), MongoTableName.CB_ORDER, OrderMongo.class);

	    String pmCode = order.getPaymentMethod();
	    if(pmCode==null||!pmCode.matches("\\d")){
	    	Logger.logError("不可识别的支付方式");
	        return "";
	    }
	    PaymentMethod pm = BaseSubassembly.codeToPaymentMethod(Integer.valueOf(pmCode));

	    String paymentChannel = null;
	    switch (pm) {
        case bankPoints:
            paymentChannel = "银行积分";
            break;
        case froadPoints:
            paymentChannel = "方付通积分";
            break;
        default:
            paymentChannel="";
            break;
        }
	    
	    if(!paymentChannel.isEmpty()){
	        return paymentChannel;
	    }
	    
	    DBObject cond = new BasicDBObject();
	    cond.put("order_id",orderId);
	    cond.put("is_enable",true);
	    cond.put("payment_reason", "2");
	    
	    @SuppressWarnings("unchecked")
		List<PaymentMongoEntity> paymentList = (List<PaymentMongoEntity>) mongoManager.findAll(cond, MongoTableName.CB_PAYMENT,PaymentMongoEntity.class);
	    
	    for(PaymentMongoEntity pay: paymentList){
	        switch (pay.getPayment_type_details()) {
            case 51:
                paymentChannel = concat(paymentChannel,"快捷支付");
                break;
            case 20:
                paymentChannel = concat(paymentChannel,"贴膜卡支付");
                break;
            case 55:
                paymentChannel = concat(paymentChannel,"即时支付");
                break;
            case 0:
                switch (pm) {
                case bankPointsAndCash:
                    paymentChannel = concat(paymentChannel,"银行积分");
                    break;
                case froadPointsAndCash:
                    paymentChannel = concat(paymentChannel,"联盟积分");
                    break;
                default:
                    break;
                }
                
            default:
                break;
            }
	    }
	    return paymentChannel;
	}
	private String concat(String paymentChannel,String value){
        if(paymentChannel.isEmpty()){
            paymentChannel=value;
        }else{
            paymentChannel = new StringBuilder(paymentChannel).append('+').append(value).toString();
        }
        return paymentChannel;
	}
	
	private int getIntValue(Integer value){
		if(value == null){
			return 0;
		}
		return value;
	}
	
	public static void main(String[] args) {
        System.setProperty("CONFIG_PATH", "./config");
        String reseult = new PaymentSupportImpl().queryOrderPaymentChannel("013C801D8000");
        System.out.println(reseult);
    }


	@Override
	public PointsRes queryPointPaymentState(String paymentId,QueryOrderType orderType,String clientId) throws Exception {
		PointsReq request = new PointsReq();
		request.setOrderType(orderType);
		request.setObjectNo(paymentId);
		request.setPartnerNo(acquirePointPartnerNo(clientId));
		PointsRes res;
		try {
			res = pointsApiFunc.queryOrderStatus(request);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return res;
	}

	@Override
	public ResultBean systemAutoRefundCash(Payment payment) {
		PaymentPoTemp poTemp = new PaymentPoTemp(
				payment.getClientId(), payment.getOrderId(), 
				payment.getPaymentType(),Arith.div(payment.getPaymentValue(),1000), payment.getPaymentOrgNo(), payment.getPaymentTypeDetails(), payment.getPointRate(), "5");
		
		String paymentId = EsyT.simpleID();
		poTemp.setPaymentId(paymentId);
		
		boolean flag = addPaymentToMongo(poTemp);
		Logger.logInfo("保存用系统自动退现金记录 flag: " + flag);
		
		OpenApiReq req = new OpenApiReq(
				paymentId,
				payment.getPaymentId(), //关联需要退款的历史流水
				String.valueOf(Arith.div(payment.getPaymentValue(),1000)),
				String.valueOf(Arith.div(payment.getPaymentValue(),1000)),
				RefundType.ALL,
				acquireOpenAPIPartnerNo(payment.getClientId()),
				"社区银行退现金申请", 
				acquireOpenAPIPartnerNo(payment.getClientId()),
				OpenApiCommand.REFUND_NOTICE_URL);
		
		Payment paymentTemp = new Payment();
		paymentTemp.setPaymentId(paymentId);
		paymentTemp.setStep(2);
		
		flag = updatePayment(paymentTemp);
		
		Logger.logInfo("修改退款流水记录: " + flag);
		
		OpenApiRes res;
		try {
			res = openApiFunc.refund(req);
		} catch (Exception e) {
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setRemark("发起退现金请求异常: " + e.getMessage());
			paymentTemp.setPaymentStatus("3");
			paymentTemp.setStep(3);
			
			flag = updatePayment(paymentTemp);
			
			Logger.logInfo("修改支付信息结果: " + flag);
			return new ResultBean(ResultCode.payment_thirted_request_faild);
		}
		
		
		paymentTemp = new Payment();
		paymentTemp.setPaymentId(paymentId);
		paymentTemp.setStep(3);
		flag = updatePayment(paymentTemp);
		Logger.logInfo("修改退款流水记录: " + flag);
		
		if(SUCCESS_CODE.equals(res.getResultCode())){
			
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setResultCode(res.getResultCode());
			paymentTemp.setResultDesc(res.getResultDesc());
			paymentTemp.setPaymentStatus("2");
			paymentTemp.setBillNo(res.getFroadBillNo());
			
			flag = updatePayment(paymentTemp);
			Logger.logInfo("修改退款流水记录: " + flag);
			return new ResultBean(ResultCode.success);
			
		}else{
			Logger.logInfo("发起退现金请求受理失败");
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setResultCode(res.getResultCode());
			paymentTemp.setResultDesc(res.getResultDesc());
			paymentTemp.setPaymentStatus("5");
			paymentTemp.setStep(4);
			flag = updatePayment(paymentTemp);
			Logger.logInfo("修改退款流水记录: " + flag);
			
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setPaymentStatus("5");
			paymentTemp.setStep(4);
			paymentTemp.setRemark("由于现金退款失败，该笔积分退款流水并未实际处理，但结果为失败");
			flag = updatePayment(paymentTemp);
			Logger.logInfo("修改退款流水记录: " + flag);
			
			return new ResultBean(ResultCode.failed);
		}
	}
}