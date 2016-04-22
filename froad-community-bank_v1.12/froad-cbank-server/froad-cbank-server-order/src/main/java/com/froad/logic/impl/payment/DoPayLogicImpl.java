package com.froad.logic.impl.payment;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.ResultBean;
import com.froad.enums.ResultCode;
import com.froad.exceptions.PaymentException;
import com.froad.logic.payment.DoPayLogic;
import com.froad.logic.payment.RefundLogic;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.support.impl.payment.AWIPSThirdpartyImpl;
import com.froad.support.impl.payment.DataWrapImpl;
import com.froad.support.payment.AWIPSThirdparty;
import com.froad.support.payment.DataWrap;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Const;
import com.froad.util.payment.EsyT;
import com.froad.util.payment.Logger;
import com.froad.util.payment.ThirdpartyRequestBuilder;

public class DoPayLogicImpl implements DoPayLogic {

	
	//------------------------error msg------------------------
	/**
	 * 请求支付流水为空
	 */
	private final String PAYMENT_IS_NULL = "系统繁忙请稍后重试";
	
	/**
	 * 现金支付受理成功
	 */
	private final String CASH_PAY_ACCESS_SUCCESS = "支付请求完成，等待通知支付结果";
	
	/**
	 * 默认支付失败展示信息（如果第三方支付同步响应为空则展示）
	 */
	private final String DEFAULT_DO_PAY_FAILED = "支付失败，请稍后重试";
	
	//------------------------error msg------------------------
	
	
	//--------------------------beans--------------------------
	private DataWrap dataWrap = new DataWrapImpl();
	private AWIPSThirdparty awipsThirdparty = new AWIPSThirdpartyImpl();
	private RefundLogic refundLogic = new RefundLogicImpl();
	//--------------------------beans--------------------------
	
	
	/**
	 * 为了保证先支付积分再支付现金，需要确保支付流水的先后顺序（尽管也许该流水曾经可能是正常顺序）
	 * <p>Function: sortPayments</p>
	 * <p>Description:</p>
	 * @author zhaoxy@thankjava.com
	 * @date 2015-5-21 下午4:02:37
	 * @version 1.0
	 * @param payments
	 * @return
	 */
	private List<Payment> sortPayments(List<Payment> payments) {

		if(payments.size() == 1){ //单条支付流水不用排序
			return payments;
		}
		
		//判断支付流水类型，确保支付流水的先后顺序
		List<Payment> sortPayments = new ArrayList<Payment>();
		if(BaseSubassembly.isPaymentTypeOfPurePoints(payments.get(0).getPaymentType())){ //积分类型流水
			sortPayments.add(payments.get(0));
			sortPayments.add(payments.get(1));
		}else{
			sortPayments.add(payments.get(1));
			sortPayments.add(payments.get(0));
		}
		return sortPayments;
	}

	@Override
	public ResultBean doPayCoreForTrade(OrderMongo order,List<Payment> payments) throws PaymentException{
		if(payments == null || payments.size() == 0){
			Logger.logError("请求支付接口传入空支付流水信息");
			return new ResultBean(ResultCode.failed,PAYMENT_IS_NULL);
		}
		payments = sortPayments(payments);
		if(payments.size() == 1){ //单一支付类型
			return doPay(order,payments.get(0));
		}else{ //组合支付类型
			ResultBean resultBean = doPay(order,payments.get(0));  //积分支付
			if(!EsyT.isResultBeanSuccess(resultBean)){
				Logger.logInfo("组合支付: 积分支付失败,现金支付取消");
				dataWrap.modifyPaymentToPayFailed(payments.get(1).getPaymentId(), null, "组合支付: 积分支付失败,现金支付取消");
				return resultBean;
			}else{
				resultBean = doPay(order,payments.get(1)); //现金支付
				if(!EsyT.isResultBeanSuccess(resultBean)){ //整体订单支付认为已失败
					//自动退还积分
					ResultBean autoRefundPointResult = refundLogic.autoRefundPointOfUserPay(payments.get(0).getPaymentId());
					Logger.logInfo("组合支付: 积分支付成功,现金支付失败,自动退还积分","autoRefundPointResult",autoRefundPointResult);
				}
				return resultBean;
			}
		}
	}
	
	/**
	 * 支付分发接口
	* <p>Function: doPay</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午9:22:13
	* @version 1.0
	* @param payment
	* @return
	 */
	private ResultBean doPay(OrderMongo order,Payment payment){
		boolean flag = dataWrap.modifyPaymentToPaying(payment.getPaymentId());
		Logger.logInfo("更改流水支付中状态flag: " + flag);
		ResultBean resultBean;
		if(BaseSubassembly.isPaymentTypeOfPurePoints(payment.getPaymentType())){
			//积分支付
			resultBean = pointPay(order,payment);
		}else{
			//现金支付
			resultBean = cashPay(order,payment);
		}
		Logger.logInfo("流水支付结果",new String[]{"payment","resultBean"},new Object[]{payment,resultBean});
		return resultBean;
	}
	
	
	/**
	 * 积分支付接口
	* <p>Function: pointPay</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午9:22:27
	* @version 1.0
	* @param payment
	* @return
	 */
	private ResultBean pointPay(OrderMongo order,Payment payment){ //支付流水已经成功保存
		
		//------组装消费积分请求参数
		PointsReq req = ThirdpartyRequestBuilder.builderPointPayReq(payment);
		String paymentId = payment.getPaymentId();
		
		PointsRes res;
		try {
			res = awipsThirdparty.pointConsume(req);
		} catch (PaymentException e) {
			dataWrap.addPaymentTimeFromRedis(paymentId); //发送请求异常的流水交由定时任务去接管
			dataWrap.modifyPaymentToRequestException(paymentId, "请求支付系统Point异常: " + e.getMessage());
			Logger.logError("发送消费积分请求异常",e);
			throw e;
		}

		dataWrap.modifyPaymentToRequestSuccess(paymentId);
		
		if(Const.SUCCESS_CODE.equals(res.getResultCode())){
			dataWrap.modifyPaymentToPaySuccess(paymentId,res.getResultCode(),res.getResultDesc(),res.getPayPointsNo(),"积分消费成功");
			return new ResultBean(ResultCode.success);
		}else{
			dataWrap.modifyPaymentToPayFailed(paymentId, res.getResultCode(),res.getResultDesc(),"积分支付失败");
			return new ResultBean(ResultCode.failed, res.getResultDesc());
		}
	}
	
	/**
	 * 现金支付接口
	* <p>Function: cashPay</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午9:22:39
	* @version 1.0
	* @param payment
	* @return
	 */
	private ResultBean cashPay(OrderMongo order,Payment payment){ //支付流水已经成功保存
		
		//2016-02-25 增加手机号校验
		if(StringUtils.isEmpty(payment.getFromPhone())){
			return new ResultBean(ResultCode.payment_params_error,"会员没有绑定手机号码，无法进行支付");
		}
				
		String paymentId = payment.getPaymentId();
		OpenApiReq req = ThirdpartyRequestBuilder.builderCashPayReq(payment,  payment.getFromPhone(), order.getCreateSource());
		OpenApiRes res;
		try {
			res = awipsThirdparty.cashConsume(req);
		} catch (PaymentException e) {
			dataWrap.addPaymentTimeFromRedis(paymentId); //发送请求异常的流水交由定时任务去接管
			dataWrap.modifyPaymentToRequestException(paymentId,"请求支付系统OpenAPI异常: " + e.getMessage());
			Logger.logError("发送消费现金请求异常",e);
			throw e;
		}
		
		dataWrap.modifyPaymentToRequestSuccess(paymentId);
		
		if(Const.SUCCESS_CODE.equals(res.getResultCode())){
			dataWrap.addPaymentTimeFromRedis(paymentId); //受理成功的订单需要定时任务去定期查询通知
			dataWrap.modifyPaymentToPayAccessSuccess(paymentId,res.getFroadBillNo(),"现金支付请求受理成功");
			return new ResultBean(ResultCode.success,CASH_PAY_ACCESS_SUCCESS,res.getPaymentURL());
		}else{
			String redultDesc = StringUtils.isEmpty(res.getResultDesc()) ? DEFAULT_DO_PAY_FAILED : res.getResultDesc();
			dataWrap.modifyPaymentToPayAccessFailed(paymentId, res.getResultCode(),redultDesc,"现金支付请求受理失败");
			return new ResultBean(ResultCode.failed, redultDesc);
		}
	}
}
