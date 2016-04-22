package com.froad.util.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.froad.po.Payment;
import com.froad.thirdparty.common.OpenApiCommand;
import com.froad.thirdparty.dto.request.openapi.OpenApiOrderDetail;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.BillOrderType;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.CheckType;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.Client;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.PayDirect;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.PayType;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq.RefundType;
import com.froad.thirdparty.dto.request.openapi.QueryParamApiReq;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.util.Arith;
import com.froad.util.DateUtil;
import com.froad.util.payment.Const.RequestCashType;
import com.froad.util.payment.Const.RequestType;


/**
 * 创建第三方请求体
* <p>Function: ThirdpartyRequestBulider</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-22 上午10:02:05
* @version 1.0
 */
public class ThirdpartyRequestBuilder {

	private static final String POINT_CONSUME = "社区银行积分支付订单";
	private static final String CASH_CONSUME = "社区银行现金支付订单";
	private static final String CASH_SETTLEMENT = "社区银行现金结算订单";
	private static final String POINT_REFUND = "社区银行积分退款订单";
	private static final String CASH_REFUDN_REQ = "社区银行现金退款订单";
	
	/**
	 * 组装积分支付请求体
	* <p>Function: builderPointPayReq</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午10:11:11
	* @version 1.0
	* @param pointInfo
	* @param payment
	* @param loginID
	* @param pointPartnerNo
	* @return
	 */
	public static PointsReq builderPointPayReq(Payment payment){
		String requestSuffix = Const.getRequestSuffix(payment.getClientId(), RequestType.P_CONSUME, RequestCashType.UNDEFINE,POINT_CONSUME);
		String pointPartnerNo = BaseSubassembly.acquirePointPartnerNo(payment.getClientId());
		PointsReq req;
		double payPointValue = Arith.div(payment.getPaymentValue(),1000);
		String paymentOrgNo = payment.getPaymentOrgNo();
		if(Const.FROAD_POINT_ORG_NO.equals(paymentOrgNo)){
			req = new PointsReq(
					paymentOrgNo,
					payment.getPaymentId(), 
					payPointValue, 
					payment.getFromUserName(),
					pointPartnerNo,
					requestSuffix,
					null,
					payment.getFromAccountNo()
					);
		}else{
			req = new PointsReq(
					payment.getPaymentOrgNo(),//消耗银行积分机构号
					payment.getPaymentId(),  
					Arith.div(payPointValue,Double.valueOf(payment.getPointRate())),//原始价值
					payment.getFromUserName(),
					pointPartnerNo,
					requestSuffix,
					payPointValue,
					payment.getFromAccountNo()
					);
		}
		//------ 用于在查询积分消费明细时展示订单号
		req.setRemark(payment.getOrderId());
		return req;
	}
	
	
	/**
	 * 组装现金支付请求体
	* <p>Function: builderCashPayReq</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 下午5:28:24
	* @version 1.0
	* @param payment
	* @param openAPIPartnerNo
	* @param payMobileNum
	* @param createSource
	 */
	public static OpenApiReq builderCashPayReq(Payment payment,String payMobileNum,String createSource){
		
		RequestCashType cashType = RequestCashType.UNDEFINE;
		
		switch (payment.getPaymentTypeDetails()) {
		case 20:
			cashType = RequestCashType.FOIL_CARD;
			break;
		case 51:
			cashType = RequestCashType.BANK_FAST;
			break;
		default:
			break;
		}
		
		String requestSuffix = Const.getRequestSuffix(payment.getClientId(), RequestType.C_CONSUME, cashType,CASH_CONSUME);

		String payAmount = String.valueOf(Arith.div(payment.getPaymentValue(),Const.HDOP_1000));
		List<OpenApiOrderDetail> orderDetails = new ArrayList<OpenApiOrderDetail>();
		OpenApiOrderDetail detail = new OpenApiOrderDetail(
				payment.getPaymentId(), //支付流水号
				payAmount,//支付金额
				BaseSubassembly.acquirePointPartnerNo(payment.getClientId()), 
				requestSuffix
				);
		orderDetails.add(detail);
		
		Client clientType = null;
		Client[] clients = Client.values();
		for (Client c : clients) {
			if(c.getCode().equals(createSource)){
				clientType = c;
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
		com.froad.po.Client client = BaseSubassembly.acquireClientFromRedis(payment.getClientId());
		
		OpenApiReq req = new OpenApiReq(
				orderDetails, 
				BillOrderType.COMBINE,//指定消费现金模式
				payAmount, //支付金额
				payType, //现金支付方式
				clientType,
				payment.getPaymentOrgNo(), 
				payMobileNum, 
				PayDirect.F_MERCHANT, //直连方式
				requestSuffix, 
				BaseSubassembly.acquirePointPartnerNo(payment.getClientId()), 
				requestSuffix, 
				"",
				client.getReturnUrl(), //网银returnURL （到本系统）
				DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT4, new Date()), 
				"",
				"10" //用户支付类型交易
				);
		return req;
	}
	
	/**
	 * 组装结算请求体
	 * builderCashSettleReq:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015年9月14日 下午6:09:24
	 * @param payment
	 * @return
	 *
	 */
	public static OpenApiReq builderCashSettleReq(Payment payment){
		
		String requestSuffix = Const.getRequestSuffix(payment.getClientId(), RequestType.C_CONSUME, RequestCashType.TIMELY_PAY,CASH_SETTLEMENT);
		
		String payValue = String.valueOf(Arith.div(payment.getPaymentValue(), Const.HDOP_1000));
		List<OpenApiOrderDetail> orderDetails = new ArrayList<OpenApiOrderDetail>();
		String openAPIPartnerNo = BaseSubassembly.acquireOpenAPIPartnerNo(payment.getClientId());
		OpenApiOrderDetail detail = new OpenApiOrderDetail(
				payment.getPaymentId() //支付流水号
				,
				payValue//支付金额
				, openAPIPartnerNo, 
				requestSuffix
				);
		orderDetails.add(detail);
		
		String settleMerchantAndOutletId = payment.getSettleMerchantAndOutletId().replace("|", "_");
		
		OpenApiReq req = new OpenApiReq(
				orderDetails, 
				BillOrderType.COMBINE,//指定消费现金模式
				payValue, //支付金额
				PayType.TIMELY_PAY, //及时结算
				Client.PC,
				payment.getPaymentOrgNo(), 
				null, 
				PayDirect.F_MERCHANT, //直连方式
				requestSuffix, 
				openAPIPartnerNo, 
				requestSuffix, 
				"",
				"",
				DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT4, new Date()), 
				settleMerchantAndOutletId + "|" + OpenApiCommand.MERCHANT_ACCT_QUERY_URL,
				"20"
				);
		return req;
	}
	
	/**
	 * 组装校验贴膜卡号请求体
	* <p>Function: builderVerifyFoilCardNumReq</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午11:49:43
	* @version 1.0
	* @param clientId
	* @param foilCardNum
	* @return
	 */
	public static OpenApiReq builderVerifyFoilCardNumReq(String clientId,String foilCardNum){
		OpenApiReq req = new OpenApiReq(
				BaseSubassembly.acquireFoilCardPointPaymentOrgNo(clientId),
				CheckType.ACCOUNT_MOBILE,
				foilCardNum, null,BaseSubassembly.acquireOpenAPIPartnerNo(clientId));
		return req;
	}
	
	/**
	 * 组装退积分请求体
	* <p>Function: builderPointRefundReq</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-25 下午2:57:48
	* @version 1.0
	* @param refundPayment
	* @return
	 */
	public static PointsReq builderPointRefundReq(Payment refundPayment,String payBillNo){
		String requestSuffix = Const.getRequestSuffix(refundPayment.getClientId(), RequestType.P_REFUND, RequestCashType.UNDEFINE,POINT_REFUND);

		double payValue = Arith.div(refundPayment.getPaymentValue(),Const.HDOP_1000);
		PointsReq req;
		boolean isBankPoint = refundPayment.getPaymentOrgNo().equals(Const.FROAD_POINT_ORG_NO) ? false : true;
		if(isBankPoint){
			req = new PointsReq(
					refundPayment.getPaymentId()
					, refundPayment.getFromAccountNo()
					, String.valueOf(Arith.div(payValue,refundPayment.getPointRate()))
					, refundPayment.getFromUserName()
					, payBillNo
					, BaseSubassembly.acquirePointPartnerNo(refundPayment.getClientId())
					, requestSuffix
					, requestSuffix
					, refundPayment.getPaymentOrgNo()
					);
		}else{
			req = new PointsReq(
					refundPayment.getPaymentId()
					, refundPayment.getFromAccountNo()
					, String.valueOf(payValue)
					, refundPayment.getFromUserName()
					, payBillNo
					, BaseSubassembly.acquirePointPartnerNo(refundPayment.getClientId())
					, requestSuffix
					, requestSuffix
					,""
					);

		}
		req.setRemark(refundPayment.getOrderId());
		return req;
	}
	
	
	/**
	 * 组装赠送积分请求体
	* <p>Function: builderFroadPointPresentReq</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-26 下午12:00:22
	* @version 1.0
	* @param paymentPresent
	* @return
	 */
	public static PointsReq builderFroadPointPresentReq(Payment paymentPresent){
		double payValue = Arith.div(paymentPresent.getPaymentValue(),Const.HDOP_1000);
		return new PointsReq(paymentPresent.getPaymentOrgNo(),paymentPresent.getFromUserName(),
				"1",
				paymentPresent.getPaymentId(), 
				"社区银行自动赠送积分订单"
				, 
				"", 
				"社区银行自动赠送积分订单", String.valueOf(payValue), BaseSubassembly.acquirePointPartnerNo(paymentPresent.getClientId()), "");
		
	}
	
	/**
	 * 组装现金退款请求体
	* <p>Function: builderCashRefundReq</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-27 上午9:29:58
	* @version 1.0
	* @param originalPayment
	* @param refundPayment
	* @return
	 */
	public static OpenApiReq builderCashRefundReq(Payment originalPayment,Payment refundPayment){
		double payValueOriginal = Arith.div(originalPayment.getPaymentValue(), Const.HDOP_1000);
		double payValueRefund = Arith.div(refundPayment.getPaymentValue(), Const.HDOP_1000);
		
		RefundType refundType = RefundType.PART;
		if(payValueOriginal == payValueRefund){
			refundType = RefundType.ALL;
		}
		
		String openAPIPartnerNo = BaseSubassembly.acquireOpenAPIPartnerNo(originalPayment.getClientId());
		
		//FIXME ---- 该代码块是用于解决数据迁移时paymentId在老数据中缺少前缀
		String refundPaymentId = originalPayment.getPaymentId();
		if(refundPaymentId.length() == 15){ //进入该方法块证明是老数据需要添加fft前缀
			refundPaymentId = "fft" + refundPaymentId;
		}
		//FIXME ---- 该代码块是用于解决数据迁移时paymentId在老数据中缺少前缀
		
		OpenApiReq req = new OpenApiReq(
				refundPayment.getPaymentId(),
				refundPaymentId,
				String.valueOf(payValueOriginal),
				String.valueOf(payValueRefund),
				refundType,
				openAPIPartnerNo,
				CASH_REFUDN_REQ, 
				openAPIPartnerNo,
				OpenApiCommand.REFUND_NOTICE_URL);
		
		return req;
	}
	
	
	/**
	 * 组装现金结果验证请求体
	* <p>Function: builderCashVerifyReq</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-5 下午3:35:24
	* @version 1.0
	* @param payment
	* @return
	 */
	public static QueryParamApiReq builderCashVerifyReq(Payment payment,String orderType){
		QueryParamApiReq req = new QueryParamApiReq(
				Const.OPENAPI_ORDER_TYPE_QUERY_SINGLE, orderType,
				payment.getPaymentId(), "",
				BaseSubassembly.acquireOpenAPIPartnerNo(payment.getClientId()),
				Const.OPENAPI_ORDER_SOURCES_CLIENT_PC);
		String queryTime = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT4, payment.getCreateTime());
		req.setQueryTime(queryTime + "|" + queryTime);
		return req;
	}
	
	/**
	 * 组装积分结果验证请求体
	* <p>Function: builderPointVerifyReq</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-7 下午1:35:54
	* @version 1.0
	* @param orderType
	* @param payment
	* @return
	 */
	public static PointsReq builderPointVerifyReq(String orderType,Payment payment){
		PointsReq req = new PointsReq();
		req.setOrderType(orderType);
		req.setObjectNo(payment.getPaymentId());
		req.setPartnerNo(BaseSubassembly.acquirePointPartnerNo(payment.getClientId()));
		return req;
	}
}
