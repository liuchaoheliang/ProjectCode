package com.froad.logic.impl.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.ResultBean;
import com.froad.common.beans.payment.RefundTempBean;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.PaymentMethod;
import com.froad.enums.RefundResource;
import com.froad.enums.RefundState;
import com.froad.enums.ResultCode;
import com.froad.exceptions.PaymentException;
import com.froad.handler.impl.RefundHandlerImpl;
import com.froad.logic.payment.RefundLogic;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.refund.RefundHistory;
import com.froad.po.refund.RefundPaymentInfo;
import com.froad.po.refund.RefundProduct;
import com.froad.po.refund.RefundShoppingInfo;
import com.froad.support.OrderSupport;
import com.froad.support.RefundSupport;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.impl.RefundSupportImpl;
import com.froad.support.impl.payment.AWIPSThirdpartyImpl;
import com.froad.support.impl.payment.DataWrapImpl;
import com.froad.support.payment.AWIPSThirdparty;
import com.froad.support.payment.DataWrap;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.froad.util.Arith;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Const;
import com.froad.util.payment.EsyT;
import com.froad.util.payment.Logger;
import com.froad.util.payment.ThirdpartyRequestBuilder;
import com.froad.util.refund.ProxyRefundType;

public class RefundLogicImpl implements RefundLogic {

	
	private DataWrap dataWrap = new DataWrapImpl();
	private AWIPSThirdparty awipsThirdparty = new AWIPSThirdpartyImpl();
	private OrderSupport orderSupport = new OrderSupportImpl();
	private RefundSupport refundSupport = new RefundSupportImpl();
	
	/**
	 * 构造现金退款对象
	* <p>Function: toBuilderRefundOfCash</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-2 上午9:27:37
	* @version 1.0
	* @param originalPayment
	* @param refundPaymentId
	* @param isAuto
	* @return
	 */
	private Payment toBuilderRefundOfCash(Payment originalPayment,String refundPaymentId,boolean isAuto){
		Payment refund = new Payment();
		refund.setOrderId(originalPayment.getOrderId());
		refund.setClientId(originalPayment.getClientId());
		refund.setPaymentId(refundPaymentId);
		refund.setFromUserMemberCode(originalPayment.getFromUserMemberCode());
		refund.setFromAccountName(originalPayment.getFromAccountName());
		refund.setFromAccountNo(originalPayment.getFromAccountNo());
		refund.setPaymentTypeDetails(originalPayment.getPaymentTypeDetails());
		refund.setPaymentReason(isAuto ? "5" : "1");
		refund.setPaymentOrgNo(originalPayment.getPaymentOrgNo());
		refund.setPaymentType(originalPayment.getPaymentType());
		refund.setFromAccountNo(originalPayment.getFromAccountNo());
		refund.setPaymentValue(originalPayment.getPaymentValue());
		refund.setFromUserName(originalPayment.getFromUserName());
		refund.setFromPhone(originalPayment.getFromPhone());
		refund.setBillNo(originalPayment.getBillNo());
		return refund;
	}
	
	
	/**
	 * 构造积分退款对象
	* <p>Function: toBuilderRefundOfPoint</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-2 上午9:27:51
	* @version 1.0
	* @param originalPayment
	* @param refundPaymentId
	* @param isAuto
	* @return
	 */
	private Payment toBuilderRefundOfPoint(Payment originalPayment,String refundPaymentId,boolean isAuto){
		Payment refund = new Payment();
		refund.setOrderId(originalPayment.getOrderId());
		refund.setClientId(originalPayment.getClientId());
		refund.setPaymentId(refundPaymentId);
		refund.setPaymentTypeDetails(0); //代表是积分
		refund.setFromUserMemberCode(originalPayment.getFromUserMemberCode());
		refund.setPaymentReason(isAuto ? "5" : "1"); 
		refund.setPaymentOrgNo(originalPayment.getPaymentOrgNo());
		refund.setPaymentType(originalPayment.getPaymentType());
		refund.setFromAccountNo(originalPayment.getFromAccountNo());
		refund.setPointRate(originalPayment.getPointRate());
		refund.setPaymentValue(originalPayment.getPaymentValue());
		refund.setFromUserName(originalPayment.getFromUserName());
		return refund;
	}
			
	@Override
	public ResultBean autoRefundPointOfUserPay(String paymentId) {
		
		Payment originalPayment = dataWrap.queryByPaymentId(paymentId);
		
		Logger.logInfo("获取到历史支付积分记录", "originalPayment", originalPayment);
		
		String refundPaymentId = EsyT.simpleID();
		
		Payment refundPayment = toBuilderRefundOfPoint(originalPayment, refundPaymentId, true);
		
		boolean flag = dataWrap.initializePayment(refundPayment);
		if(!flag){
			Logger.logError("保存积分自动退款流水失败");
			return new ResultBean(ResultCode.failed);
		}
		
		PointsReq req = ThirdpartyRequestBuilder.builderPointRefundReq(refundPayment,originalPayment.getBillNo());
		PointsRes res;
		
		try {
			res = awipsThirdparty.pointRefund(req);
		} catch (PaymentException e) {
			EsyT.sendPoint(MonitorPointEnum.order_pay_auto_refund_point_failed);
			dataWrap.modifyPaymentToRequestException(refundPaymentId, "请求支付系统Point异常: " + e.getMessage());
			Logger.logError("自动退积分请求发送异常",e);
			return new ResultBean(ResultCode.failed);
		}
		
		dataWrap.modifyPaymentToRequestSuccess(refundPaymentId);
		
		if(Const.SUCCESS_CODE.equals(res.getResultCode())){
			dataWrap.modifyPaymentToPaySuccess(refundPaymentId,res.getResultCode(),"自动退还积分成功",res.getRefundPointsNo(),"积分退款成功");
			return new ResultBean(ResultCode.success);
		}else{
			EsyT.sendPoint(MonitorPointEnum.order_pay_auto_refund_point_failed);
			dataWrap.modifyPaymentToPayFailed(refundPaymentId, res.getResultCode(),res.getResultDesc(),"积分退款失败");
			return new ResultBean(ResultCode.failed, res.getResultDesc());
		}
		
	}
	
	@Override
	public ResultBean autoRefundCashOfUserPay(String paymentId) {
		
		Payment originalPayment = dataWrap.queryByPaymentId(paymentId);
		
		Logger.logInfo("获取到历史支付现金记录", "originalPayment", originalPayment);
		
		String refundPaymentId = EsyT.simpleID();
		
		Payment refundPayment = toBuilderRefundOfCash(originalPayment, refundPaymentId,true);
		
		boolean flag = dataWrap.initializePayment(refundPayment);
		if(!flag){
			Logger.logError("保存现金自动退款流水失败");
			return new ResultBean(ResultCode.failed);
		}
		
		OpenApiReq req = ThirdpartyRequestBuilder.builderCashRefundReq(originalPayment, refundPayment);
		OpenApiRes res;
		
		try {
			res = awipsThirdparty.cashRefund(req);
		} catch (PaymentException e) {
			dataWrap.addPaymentTimeFromRedis(refundPaymentId);
			dataWrap.modifyPaymentToRequestException(refundPaymentId,"请求支付系统OpenAPI异常: " + e.getMessage());
			Logger.logError("自动退现金请求发送异常",e);
			return new ResultBean(ResultCode.failed);
		}
		
		dataWrap.modifyPaymentToRequestSuccess(refundPaymentId);
		
		if(Const.SUCCESS_CODE.equals(res.getResultCode())){
			dataWrap.addPaymentTimeFromRedis(refundPaymentId);
			dataWrap.modifyPaymentToPayAccessSuccess(refundPaymentId,res.getResultCode(),res.getResultDesc(),"现金退款请求受理成功");
			return new ResultBean(ResultCode.success);
		}else{
			EsyT.sendPoint(MonitorPointEnum.order_pay_auto_refund_point_failed);
			dataWrap.modifyPaymentToPayAccessFailed(refundPaymentId, res.getResultCode(),res.getResultDesc(),"现金退款请求受理失败");
			return new ResultBean(ResultCode.failed, res.getResultDesc());
		}
		
	}

	@Override
	public ResultBean userRefundGoOnPointOfUserPay(Payment payment) {
		if(payment == null){
			Logger.logError("发起用户退积分失败，请求参数为空");
		}
		//查询用户已经支付过的积分流水
		Payment originalPayment = dataWrap.queryPaymentOfUserPaiedTypePoint(payment.getOrderId());
		if(originalPayment == null){
			Logger.logError("发起用户退积分失败，未能获取成功支付的积分流水记录");
		}
		
		PointsReq req = ThirdpartyRequestBuilder.builderPointRefundReq(payment,originalPayment.getBillNo());
		PointsRes res;
		try {
			res = awipsThirdparty.pointRefund(req);
		} catch (PaymentException e) {
			EsyT.sendPoint(MonitorPointEnum.order_pay_auto_refund_point_failed);
			dataWrap.modifyPaymentToRequestException(payment.getPaymentId(), "请求支付系统Point异常: " + e.getMessage());
			Logger.logError("继续退积分请求发送异常",e);
			return new ResultBean(ResultCode.failed);
		}
		
		dataWrap.modifyPaymentToRequestSuccess(payment.getPaymentId());
		
		if(Const.SUCCESS_CODE.equals(res.getResultCode())){
			dataWrap.modifyPaymentToPaySuccess(payment.getPaymentId(),res.getResultCode(),res.getResultDesc(),res.getRefundPointsNo(),"积分退款成功");
			return new ResultBean(ResultCode.success);
		}else{
			dataWrap.modifyPaymentToPayFailed(payment.getPaymentId(), res.getResultCode(),res.getResultDesc(),"积分退款失败");
			return new ResultBean(ResultCode.failed, res.getResultDesc());
		}
	}

	@Override
	public ResultBean refundUserPaymentCapital(String orderId,Double refundPresentPoint, Double refundPayPoint,Double refundPayCash) {
		
		if(StringUtils.isEmpty(orderId)){
			return new ResultBean(ResultCode.failed,"orderId不能为空");
		}
		
		Logger.logInfo("收到退款模块请求的退款信息", 
				new String[]{"orderId","rufundPresentPoint","rufundPayPoint","refundPayCash"},
				new Object[]{orderId,refundPresentPoint,refundPayPoint,refundPayCash});
		
		List<Payment> payments = dataWrap.queryEnableOfUserPayByOrderId(orderId);
		if(payments == null || payments.size() == 0){
			return new ResultBean(ResultCode.failed , "退款订单号: " + orderId + "，没有任何有效的支付记录");
		}
		
		RefundTempBean refundTempBean = new RefundTempBean();
		if(refundPresentPoint != null && refundPresentPoint > 0){ //需要退款已赠送的积分（扣除已赠送积分）
			Payment present = dataWrap.queryPaymentOfPresentedTypeFroadPoint(orderId);
			if(present == null){
				Logger.logError("订单未能成功发起赠送积分");
				return new ResultBean(ResultCode.failed, "该笔订单未能成功发起赠送积分");
			}
			
			String refundPaymentIdOfPresent = EsyT.simpleID();
			
			Payment refund = new Payment();
			refund.setOrderId(orderId);
			refund.setClientId(present.getClientId());
			refund.setPaymentId(refundPaymentIdOfPresent);
			refund.setPaymentTypeDetails(present.getPaymentTypeDetails());
			refund.setPaymentReason("4");
			refund.setPaymentOrgNo(present.getPaymentOrgNo());
			refund.setPaymentType(present.getPaymentType());
			refund.setFromAccountNo(present.getFromAccountNo());
			refund.setPaymentValue(Arith.mul(refundPresentPoint, Const.HDOP_1000));
			refund.setFromUserName(present.getFromUserName());
			refund.setFromUserMemberCode(present.getFromUserMemberCode());
			
			ResultBean resultBean = new BusinessExpandLogicImpl().deductFroadPoint(refund);
			
			if(!EsyT.isResultBeanSuccess(resultBean)){
				Logger.logError("扣除已赠送的方付通积分失败","resultBean",resultBean);
				
				//FIXME : 扣除已赠送的方付通积分失败
				
				return resultBean;
			}else{
				refundTempBean.setPresentPointPaymentId(refundPaymentIdOfPresent);
				refundTempBean.setRefundPresentPointState(RefundState.REFUND_SUCCESS);
			}
		}
		
		if(refundPayCash != null && refundPayCash > 0){ //退款指定现金
			Payment cash = dataWrap.queryPaymentOfUserPaiedTypeCash(orderId);
			if(cash == null){
				Logger.logError("订单未能成功发起现金支付");
				return new ResultBean(ResultCode.failed, "该笔订单未能成功发起现金支付");
			}
			
			String refundPaymentIdOfCash = EsyT.simpleID();
			String refundPaymentIdOfPoint = null;
			Payment refundCash = toBuilderRefundOfCash(cash, refundPaymentIdOfCash,false);
			
			if(refundPayPoint != null && refundPayPoint > 0){
				//积分&现金
				Payment point = dataWrap.queryPaymentOfUserPaiedTypePoint(orderId);
				if(point == null){
					Logger.logError("订单未能成功发起积分支付,未能获取用户已支付的积分流水");
					return new ResultBean(ResultCode.failed, "该笔订单未能成功发起积分支付");
				}
				refundPaymentIdOfPoint = EsyT.simpleID();
				Payment refundPoint = toBuilderRefundOfPoint(point, refundPaymentIdOfPoint, false);
				//重写对象退款金额
				refundPoint.setPaymentValue(Arith.mul(refundPayPoint,Const.HDOP_1000));
				refundPoint.setRemark("待退款积分流水");
				dataWrap.initializePayment(refundPoint);
				refundTempBean.setPaymentPointPaymentId(refundPaymentIdOfPoint);
				refundTempBean.setRefundPointState(RefundState.REFUND_PROCESSING);
			}
			
			//重写对象退款金额
			refundCash.setPaymentValue(Arith.mul(refundPayCash,Const.HDOP_1000));
			dataWrap.initializePayment(refundCash);
			refundTempBean.setPaymentCashPaymentId(refundPaymentIdOfCash);
			refundTempBean.setRefundCashState(RefundState.REFUND_PROCESSING);
			
			OpenApiReq req = ThirdpartyRequestBuilder.builderCashRefundReq(cash, refundCash);
			OpenApiRes res;
			try {
				res = awipsThirdparty.cashRefund(req);
			} catch (PaymentException e) {
				dataWrap.addPaymentTimeFromRedis(refundPaymentIdOfCash);
				dataWrap.modifyPaymentToRequestException(refundPaymentIdOfCash, "请求支付系统OpenAPI异常: " + e.getMessage());
				Logger.logError("用户退现金请求发送异常",e);
				return new ResultBean(ResultCode.success,refundTempBean);
			}
			dataWrap.modifyPaymentToRequestSuccess(refundPaymentIdOfCash);
			if(Const.SUCCESS_CODE.equals(res.getResultCode())){
				dataWrap.addPaymentTimeFromRedis(refundPaymentIdOfCash);
				dataWrap.modifyPaymentToPayAccessSuccess(refundPaymentIdOfCash,res.getResultCode(),res.getResultDesc(),"现金退款请求受理成功");
				return new ResultBean(ResultCode.success,refundTempBean);
			}else{
				dataWrap.modifyPaymentToPayAccessFailed(refundPaymentIdOfCash, res.getResultCode(),res.getResultDesc(),"现金退款请求受理失败");
				refundTempBean.setRefundCashState(RefundState.REFUND_FAILED);

				if(refundPaymentIdOfPoint != null){
					dataWrap.modifyPaymentToPayFailed(refundPaymentIdOfPoint, null,null,"现金退款失败，该退款流水未执行退款操作");
					refundTempBean.setRefundPointState(RefundState.REFUND_FAILED);
				}
				return new ResultBean(ResultCode.failed, res.getResultDesc(),refundTempBean);
			}
			
		}else if(refundPayPoint != null && refundPayPoint > 0){ //由于前面的现金判断在此处的积分判断之前，一定能保证此处是纯积分退款，因为上一个if已经将积分&现金 || 现金&积分 这个条件囊括
			Payment point = dataWrap.queryPaymentOfUserPaiedTypePoint(orderId);
			if(point == null){
				Logger.logError("订单未能成功发起积分支付");
				return new ResultBean(ResultCode.failed, "该笔订单未能成功发起积分支付");
			}
			String refundPaymentIdOfPoint = EsyT.simpleID();
			Payment refundPoint = toBuilderRefundOfPoint(point, refundPaymentIdOfPoint, false);
			refundPoint.setPaymentValue(Arith.mul(refundPayPoint,Const.HDOP_1000));
			dataWrap.initializePayment(refundPoint);
			
			refundTempBean.setPaymentPointPaymentId(refundPaymentIdOfPoint);
			refundTempBean.setRefundPointState(RefundState.REFUND_PROCESSING);
			
			PointsReq req = ThirdpartyRequestBuilder.builderPointRefundReq(refundPoint,point.getBillNo());
			PointsRes res;
			try {
				res = awipsThirdparty.pointRefund(req);
			} catch (PaymentException e) {
				
				dataWrap.addPaymentTimeFromRedis(refundPaymentIdOfPoint);//交由定时任务核实退款结果
				dataWrap.modifyPaymentToRequestException(refundPaymentIdOfPoint,"请求支付系统Point异常: " + e.getMessage());
				Logger.logError("用户退积分请求发送异常",e);
				return new ResultBean(ResultCode.success,refundTempBean);
			}
			
			dataWrap.modifyPaymentToRequestSuccess(refundPaymentIdOfPoint);
			
			if(Const.SUCCESS_CODE.equals(res.getResultCode())){
				dataWrap.modifyPaymentToPaySuccess(refundPaymentIdOfPoint,res.getResultCode(),res.getResultDesc(),res.getRefundPointsNo(),"积分退款成功");
				refundTempBean.setRefundPointState(RefundState.REFUND_SUCCESS);
				return new ResultBean(ResultCode.success,refundTempBean);
			}else{
				dataWrap.modifyPaymentToPayFailed(refundPaymentIdOfPoint, res.getResultCode(),res.getResultDesc(),"积分退款失败");
				refundTempBean.setRefundPointState(RefundState.REFUND_FAILED);
				return new ResultBean(ResultCode.failed, res.getResultDesc(),refundTempBean);
			}
		}else{
			Logger.logError("退款金额参数错误");
			return new ResultBean(ResultCode.failed);
		}
	}

	@Override
	public ResultBean cancelPayingOrderToRefundPaiedPoint(OrderMongo order) {
		Payment point = dataWrap.queryPaymentOfUserPaiedTypePoint(order.getOrderId());
		if(point != null){
			
			//如果系统已经成功发起过自动退积分则直接返回OK
			Payment autoReundedPoint = dataWrap.queryPaymentOfAutoRefundTypePoint(order.getOrderId());
			if(autoReundedPoint != null){
				return new ResultBean(ResultCode.success);
			}
			
			return autoRefundPointOfUserPay(point.getPaymentId());
		}
		return new ResultBean(ResultCode.success);
	}

	
	private RefundHistory toBuilderRefundHistory(OrderMongo order,String refundReason){
		RefundHistory refundHistory = new RefundHistory();
		refundHistory.set_id(RefundHandlerImpl.simpleId.nextId());
		refundHistory.setCreateTime(new Date().getTime());
		refundHistory.setMemberCode(order.getMemberCode().toString());
		refundHistory.setOrderId(order.getOrderId());
		refundHistory.setReason(refundReason);
		refundHistory.setRefundResource(RefundResource.SYSTEM_REFUND.getCode());
		refundHistory.setRefundState(RefundState.REFUND_INIT.getCode());
		refundHistory.setIsVipRefund(1);
		refundHistory.setClientId(order.getClientId());
		
		List<RefundShoppingInfo> shoppingInfos = new ArrayList<RefundShoppingInfo>();
		RefundShoppingInfo shoppingInfo = new RefundShoppingInfo();
		shoppingInfo.setMerchantId("froad");
		shoppingInfo.setMerchantName("方付通");
		shoppingInfo.setType("");
		
		List<RefundProduct> products = new ArrayList<RefundProduct>();
		RefundProduct product = new RefundProduct();
		product.setProductId(order.getProductId());
		product.setProductName(order.getProductName());
		product.setPrice(order.getTotalPrice());
		product.setQuantity(1);
		product.setVipPrice(0);
		product.setVipQuantity(0);
		product.setImageUrl("");
		if(BaseSubassembly.isPurePointsPayment(order.getPaymentMethod())){
			product.setRefundTotalCash(0);
		}else{
			Payment paiedCash = dataWrap.queryPaymentOfUserPaiedTypeCash(order.getOrderId());
			product.setRefundTotalCash(paiedCash.getPaymentValue());
		}
		products.add(product);
		
		shoppingInfo.setProducts(products);
		shoppingInfo.setSubOrderId(order.getOrderId());
		
		shoppingInfos.add(shoppingInfo);
		refundHistory.setShoppingInfo(shoppingInfos);
		
		return refundHistory;
	}

	@Override
	public ResultBean proxyUserToAutoRefundAllUserPay(OrderMongo order,boolean isAuto,ProxyRefundType proxyRefundType,String refundReason) {
		
		//生成退款记录信息
		RefundHistory refundHisory = toBuilderRefundHistory(order,refundReason);
		String refundId = refundHisory.get_id();
		refundSupport.insertRefundHistory(refundHisory);
		
		List<RefundPaymentInfo> paymentInfo = new ArrayList<RefundPaymentInfo>();
		
		
		List<Payment> payments = dataWrap.queryEnableOfUserPayByOrderId(order.getOrderId());
		
		if(payments == null || payments.size() == 0){
			return new ResultBean(ResultCode.failed,"自动退款失败未找到支付记录");
		}
		
		Payment cashPay = null,pointPay = null;
		
		//是否为组合依赖退款
		boolean isRelyOn = payments.size() == 1 ? false : true;
		
		//分类支付流水
		if(isRelyOn){
			Payment temp = payments.get(0);
			if(temp.getPaymentType() == 2){ //现金支付
				cashPay = temp;
				pointPay = payments.get(1);
			}else{
				pointPay = temp;
				cashPay = payments.get(1);
			}
		}else{
			Payment temp = payments.get(0);
			if(temp.getPaymentType() == 2){ //现金支付
				cashPay = temp;
			}else{
				pointPay = temp;
			}
		}
		//分类支付流水
		
		//进行退款操作
		if(isRelyOn){ //组合退款，先退现金，生成待退款积分记录
			String refundPaymentIdOfCash = EsyT.simpleID();
			Payment refundCash = toBuilderRefundOfCash(cashPay, refundPaymentIdOfCash,isAuto);
			dataWrap.initializePayment(refundCash);
			RefundPaymentInfo paymentInfoCash = new RefundPaymentInfo();
			paymentInfoCash.setPaymentId(refundCash.getPaymentId());
			paymentInfoCash.setRefundValue(refundCash.getPaymentValue());
			paymentInfoCash.setType(PaymentMethod.cash.getCode());
			
			String refundPaymentIdOfPoint = EsyT.simpleID();
			Payment refundPoint = toBuilderRefundOfPoint(pointPay, refundPaymentIdOfPoint, isAuto);
			dataWrap.initializePayment(refundPoint);
			RefundPaymentInfo paymentInfoPoint = new RefundPaymentInfo();
			paymentInfoPoint.setPaymentId(refundPoint.getPaymentId());
			paymentInfoPoint.setRefundValue(refundPoint.getPaymentValue());
			if(Const.FROAD_POINT_ORG_NO.equals(refundPoint.getPaymentOrgNo())){
				paymentInfoPoint.setType(PaymentMethod.froadPoints.getCode());
			}else{
				paymentInfoPoint.setType(PaymentMethod.bankPoints.getCode());
			}
			
			paymentInfo.add(paymentInfoCash);
			paymentInfo.add(paymentInfoPoint);
			
			OpenApiReq req = ThirdpartyRequestBuilder.builderCashRefundReq(cashPay, refundCash);
			OpenApiRes res;
			
			try {
				res = awipsThirdparty.cashRefund(req);
			} catch (PaymentException e) {
				
				refundSupport.updateRefundState(refundId, RefundState.REFUND_PROCESSING.getCode(), paymentInfo);
				
				dataWrap.addPaymentTimeFromRedis(refundPaymentIdOfCash);
				dataWrap.modifyPaymentToRequestException(refundPaymentIdOfCash,"请求支付系统OpenAPI异常: " + e.getMessage());
				Logger.logError("用户退现金请求发送异常",e);
				return new ResultBean(ResultCode.success,"退款处理中",refundId);
			}
			dataWrap.modifyPaymentToRequestSuccess(refundPaymentIdOfCash);
			if(Const.SUCCESS_CODE.equals(res.getResultCode())){
				refundSupport.updateRefundState(refundId, RefundState.REFUND_PROCESSING.getCode(), paymentInfo);
				
				dataWrap.addPaymentTimeFromRedis(refundPaymentIdOfCash);
				dataWrap.modifyPaymentToPayAccessSuccess(refundPaymentIdOfCash,res.getResultCode(),res.getResultDesc(),"现金退款请求受理成功");
				return new ResultBean(ResultCode.success,"退款申请成功",refundId);
			}else{
				paymentInfoCash.setResultCode(res.getResultCode());
				paymentInfoCash.setResultDesc("现金退款请求受理失败");
				paymentInfoPoint.setResultCode(res.getResultCode());
				paymentInfoPoint.setResultDesc("现金支付失败，积分未退款");

				refundSupport.updateRefundState(refundId, RefundState.REFUND_FAILED.getCode(), paymentInfo);

				dataWrap.modifyPaymentToPayAccessFailed(refundPaymentIdOfCash, res.getResultCode(),res.getResultDesc(),"现金退款请求受理失败");
				dataWrap.modifyPaymentToPayFailed(refundPaymentIdOfPoint, null,null,"现金退款失败，该退款流水未执行退款操作");
				
				return new ResultBean(ResultCode.failed, res.getResultDesc());
			}
		}else{
			if(cashPay != null){
				//现金退款
				String refundPaymentIdOfCash = EsyT.simpleID();
				Payment refundCash = toBuilderRefundOfCash(cashPay, refundPaymentIdOfCash,isAuto);
				dataWrap.initializePayment(refundCash);
				RefundPaymentInfo paymentInfoCash = new RefundPaymentInfo();
				paymentInfoCash.setPaymentId(refundCash.getPaymentId());
				paymentInfoCash.setRefundValue(refundCash.getPaymentValue());
				paymentInfoCash.setType(PaymentMethod.cash.getCode());
				paymentInfo.add(paymentInfoCash);
				OpenApiReq req = ThirdpartyRequestBuilder.builderCashRefundReq(cashPay, refundCash);
				OpenApiRes res;
				try {
					res = awipsThirdparty.cashRefund(req);
				} catch (PaymentException e) {
					refundSupport.updateRefundState(refundId, RefundState.REFUND_PROCESSING.getCode(), paymentInfo);
					
					dataWrap.addPaymentTimeFromRedis(refundPaymentIdOfCash);
					dataWrap.modifyPaymentToRequestException(refundPaymentIdOfCash, "请求支付系统OpenAPI异常: " + e.getMessage());
					Logger.logError("用户退现金请求发送异常",e);
					return new ResultBean(ResultCode.success,"退款处理中",refundId);
				}
				dataWrap.modifyPaymentToRequestSuccess(refundPaymentIdOfCash);
				if(Const.SUCCESS_CODE.equals(res.getResultCode())){
					refundSupport.updateRefundState(refundId, RefundState.REFUND_PROCESSING.getCode(), paymentInfo);
					
					dataWrap.addPaymentTimeFromRedis(refundPaymentIdOfCash);
					dataWrap.modifyPaymentToPayAccessSuccess(refundPaymentIdOfCash,res.getResultCode(),res.getResultDesc(),"现金退款请求受理成功");
					return new ResultBean(ResultCode.success,"退款申请成功",refundId);
				}else{
					
					paymentInfoCash.setResultCode(res.getResultCode());
					paymentInfoCash.setResultDesc("现金退款请求受理失败");
					refundSupport.updateRefundState(refundId, RefundState.REFUND_FAILED.getCode(), paymentInfo);
					
					dataWrap.modifyPaymentToPayAccessFailed(refundPaymentIdOfCash, res.getResultCode(),res.getResultDesc(),"现金退款请求受理失败");
					return new ResultBean(ResultCode.failed, res.getResultDesc());
				}
			}else{
				//积分退款
				String refundPaymentIdOfPoint = EsyT.simpleID();
				Payment refundPoint = toBuilderRefundOfPoint(pointPay, refundPaymentIdOfPoint, isAuto);
				dataWrap.initializePayment(refundPoint);
				RefundPaymentInfo paymentInfoPoint = new RefundPaymentInfo();
				paymentInfoPoint.setPaymentId(refundPoint.getPaymentId());
				paymentInfoPoint.setRefundValue(refundPoint.getPaymentValue());
				if(Const.FROAD_POINT_ORG_NO.equals(refundPoint.getPaymentOrgNo())){
					paymentInfoPoint.setType(PaymentMethod.froadPoints.getCode());
				}else{
					paymentInfoPoint.setType(PaymentMethod.bankPoints.getCode());
				}
				paymentInfo.add(paymentInfoPoint);
				
				PointsReq req = ThirdpartyRequestBuilder.builderPointRefundReq(refundPoint,pointPay.getBillNo());
				PointsRes res;
				try {
					res = awipsThirdparty.pointRefund(req);
				} catch (PaymentException e) {
					refundSupport.updateRefundState(refundId, RefundState.REFUND_PROCESSING.getCode(), paymentInfo);

					dataWrap.addPaymentTimeFromRedis(refundPaymentIdOfPoint);//交由定时任务核实退款结果
					dataWrap.modifyPaymentToRequestException(refundPaymentIdOfPoint,"请求支付系统Point异常: " + e.getMessage());
					Logger.logError("用户退积分请求发送异常",e);
					return new ResultBean(ResultCode.success,"退款处理中",refundId);
				}
				
				dataWrap.modifyPaymentToRequestSuccess(refundPaymentIdOfPoint);
				
				if(Const.SUCCESS_CODE.equals(res.getResultCode())){
					paymentInfoPoint.setResultCode(Const.SUCCESS_CODE);
					paymentInfoPoint.setResultDesc("积分退款完成");
					refundSupport.updateRefundState(refundId, RefundState.REFUND_SUCCESS.getCode(), paymentInfo);
					
					dataWrap.modifyPaymentToPaySuccess(refundPaymentIdOfPoint,res.getResultCode(),res.getResultDesc(),res.getRefundPointsNo(),"积分退款成功");
//					if(1 == order.getIsVipOrder()){
//						//纯积分退款立刻修改订单备注
//						OrderMongo orderUpdate = new OrderMongo();
//						orderUpdate.setOrderId(order.getOrderId());
//						orderUpdate.setClientId(order.getClientId());
//						orderUpdate.setRemark("自动退款已完成");
//						boolean flag = orderSupport.updateOrderByCondion(orderUpdate);
//						Logger.logInfo("纯积分开通失败，修改remark结果: " + flag);
//					}
					return new ResultBean(ResultCode.success,"退款完毕",refundId);
				}else{
					paymentInfoPoint.setResultCode(res.getResultCode());
					paymentInfoPoint.setResultDesc("积分退款失败");
					refundSupport.updateRefundState(refundId, RefundState.REFUND_FAILED.getCode(), paymentInfo);
					 
					dataWrap.modifyPaymentToPayFailed(refundPaymentIdOfPoint, res.getResultCode(),res.getResultDesc());
					return new ResultBean(ResultCode.failed, res.getResultDesc());
				}
			}
		}
	}

}
