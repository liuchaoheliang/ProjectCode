package com.froad.fft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.bean.Result;
import com.froad.fft.common.AppException;
import com.froad.fft.persistent.api.PayMapper;
import com.froad.fft.persistent.api.ProductPresellMapper;
import com.froad.fft.persistent.api.TransDetailsMapper;
import com.froad.fft.persistent.api.TransMapper;
import com.froad.fft.persistent.common.enums.PayChannel;
import com.froad.fft.persistent.common.enums.PayRole;
import com.froad.fft.persistent.common.enums.PayState;
import com.froad.fft.persistent.common.enums.PayTypeDetails;
import com.froad.fft.persistent.common.enums.SmsType;
import com.froad.fft.persistent.common.enums.TransPayState;
import com.froad.fft.persistent.common.enums.TransState;
import com.froad.fft.persistent.common.enums.TransType;
import com.froad.fft.persistent.entity.Pay;
import com.froad.fft.persistent.entity.ProductPresell;
import com.froad.fft.persistent.entity.SysClient;
import com.froad.fft.persistent.entity.Trans;
import com.froad.fft.persistent.entity.TransDetails;
import com.froad.fft.service.SysClientService;
import com.froad.fft.service.TransCoreService;
import com.froad.fft.support.TransHelper;
import com.froad.fft.support.TransSupport;
import com.froad.fft.support.TransValidateSupport;
import com.froad.fft.thirdparty.common.OpenApiCommand;
import com.froad.fft.thirdparty.common.RespCodeCommand;
import com.froad.fft.thirdparty.common.SystemCommand;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiOrderDetail;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq.RefundType;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq.Client;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq.OrderType;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq.PayDirect;
import com.froad.fft.thirdparty.dto.request.points.PointsReq;
import com.froad.fft.thirdparty.dto.request.points.PointsRes;
import com.froad.fft.thirdparty.dto.request.sms.SmsBean;
import com.froad.fft.thirdparty.dto.response.openapi.NoticeFroadApi;
import com.froad.fft.thirdparty.request.openapi.OpenApiFunc;
import com.froad.fft.thirdparty.request.points.PointsFunc;
import com.froad.fft.thirdparty.request.sms.impl.SMSMessageFuncImpl;
import com.froad.fft.util.SerialNumberUtil;


@Service
public class TransCoreServiceImpl implements TransCoreService{
	
	private static final Logger log=Logger.getLogger(TransCoreServiceImpl.class);
	
	private String orderDisplayPrefix="分分通支付单号";
	
	@Resource
	private TransValidateSupport transValidateSupport;
	
	@Resource
	private TransSupport transSupport;
	
	@Resource
	private TransMapper transMapper;
	
	@Resource
	private TransDetailsMapper transDetailsMapper;
	
	@Resource
	private PayMapper payMapper;
	
	@Resource
	private SysClientService sysClientService;
	
	@Resource
	private ProductPresellMapper productPresellMapper;
	
	@Resource
	private PointsFunc pointsFunc; 
	
	@Resource
	private OpenApiFunc openApiFunc;
	
	@Resource
	private SMSMessageFuncImpl sMSMessageFuncImpl;

	@Override
	public Result createTrans(Trans trans) {
		Result result=transValidateSupport.validate(trans);
		if(Result.FAIL.equals(result.getCode())){
			log.error("交易参数校验不通过，原因："+result.getMsg());
			return result;
		}
		result=transSupport.makeTrans(trans);
		if(Result.FAIL.equals(result.getCode())){
			log.error("交易组装失败，原因："+result.getMsg());
			return result;
		}
		try {
			this.persistTrans(trans);
		} catch (Exception e) {
			log.error("交易数据持久化异常",e);
			return new Result(Result.FAIL,"交易数据持久化异常");
		}
		return new Result(Result.SUCCESS,trans.getSn());
	}

	@Override
	public Result doPay(String sn) {
		if(StringUtils.isEmpty(sn)){
			return new Result(Result.FAIL,"交易序列号为空");
		}
		Trans trans=transMapper.selectBySn(sn);
		Result result=this.validate(trans);
		if(Result.FAIL.equals(result.getCode())){
			log.error(result.getMsg()+"，交易序列号为："+sn);
			return result;
		}
		TransType transType=trans.getType();
		if(TransHelper.isUserTrans(transType)){
			return this.doUserPay(trans);
		}else if(TransHelper.isMerchantTrans(transType)){
			return this.doMerchantPay(trans);
		}else{
			log.error("交易序列号："+sn+"，未知的交易类型："+transType.name());
			return new Result(Result.FAIL,"未知的交易类型："+transType.name());
		}
	}
	
	private Result doUserPay(Trans trans){
		TransType transType=trans.getType();
		if(TransHelper.isProductTrans(transType)){
			return this.productPay(trans);
		}else if(TransType.points_withdraw.equals(transType)){
			return this.withdrawPay(trans);
		}else{
			return new Result(Result.FAIL,"未知的交易类型："+transType.name());
		}
	}
	
	private Result productPay(Trans trans){
		List<Pay> payList=trans.getPayList();
		Pay pay=null,pointsPay=null,cashPay=null;
		PayTypeDetails typeDetails=null;
		for (int i = 0; i < payList.size(); i++) {
			pay=payList.get(i);
			typeDetails=pay.getPayTypeDetails();
			if(PayTypeDetails.PAY_AMOUNT.equals(typeDetails)){
				cashPay=pay;
			}else if(PayTypeDetails.PAY_FFT_POINTS.equals(typeDetails)||
					PayTypeDetails.PAY_BANK_POINTS.equals(typeDetails)){
				pointsPay=pay;
			}
		}
		if(cashPay==null&&pointsPay==null){
			log.error("该笔交易没有支付记录，交易序列号："+trans.getSn());
			return new Result(Result.FAIL,"该笔交易没有支付记录");
		}
		if(pointsPay!=null){
			if(!PayState.pay_wait.equals(pointsPay.getPayState())){
				return new Result(Result.FAIL,"请不要重复发起支付操作");
			}
			Result result=this.payPoints(pointsPay,trans.getClientId());
			if(Result.FAIL.equals(result.getCode())){
				Trans tmpTrans=new Trans();
				tmpTrans.setId(trans.getId());
				tmpTrans.setState(TransState.fail);
				transMapper.updateTransById(tmpTrans);
				return result;
			}
		}
		if(cashPay!=null){
			if(!PayState.pay_wait.equals(cashPay.getPayState())){
				return new Result(Result.FAIL,"请不要重复发起支付操作");
			}
			Trans tmpTrans=new Trans();
			tmpTrans.setId(trans.getId());
			Result result=this.payCash(cashPay, trans);
			if(Result.SUCCESS.equals(result.getCode())){
				if(pointsPay!=null){
					tmpTrans.setPayState(TransPayState.partPayment);
					transMapper.updateTransById(tmpTrans);
				}
			}else{
				tmpTrans.setState(TransState.fail);
				boolean isRefund=true;
				if(pointsPay!=null){
					isRefund=this.refundPoints(pointsPay,trans.getClientId());
				}
				if(!isRefund){
					tmpTrans.setState(TransState.exception);
					tmpTrans.setPayState(TransPayState.partPayment);
				}
				transMapper.updateTransById(tmpTrans);
			}
			return result;
		}
		this.afterProductPay(trans);
		return new Result(Result.SUCCESS,"成功");
	}
	
	private Result payCash(Pay cashPay,Trans trans){
		Result result=this.combinePay(cashPay, trans);
		return result;
	}
	
	private Result withdrawPay(Trans trans){
		//TODO 提现支付--
		
		return new Result(Result.SUCCESS,"成功");
	}
	
	private Result doMerchantPay(Trans trans){
		TransType transType=trans.getType();
		if(TransType.points_rebate.equals(transType)){
			return this.rebatePay(trans);
		}else{
			return new Result(Result.FAIL,"未知的交易类型："+transType.name());
		}
	}
	
	private Result rebatePay(Trans trans){
		//TODO 积分返利支付--
		
		return new Result(Result.SUCCESS,"成功");
	}

	private void persistTrans(Trans trans){
		Long transId=transMapper.saveTrans(trans);
		List<TransDetails> details=trans.getDetailsList();
		if(details!=null&&details.size()>0){
			for (int i = 0; i < details.size(); i++) {
				details.get(i).setTransId(transId);
			}
			transDetailsMapper.saveBatchTransDetails(details);
		}
		List<Pay> payList=trans.getPayList();
		if(payList!=null&&payList.size()>0){
			for (int i = 0; i < payList.size(); i++) {
				payList.get(i).setTransId(transId);
			}
			payMapper.saveBatchPay(payList);
		}
	}
	
	private Result validate(Trans trans){
		if(trans==null){
			return new Result(Result.FAIL,"交易不存在");
		}
		TransState state=trans.getState();
		if(TransState.success.equals(state)){
			return new Result(Result.FAIL,"该笔交易已经成功");
		}
		if(TransState.close.equals(state)){
			return new Result(Result.FAIL,"该笔交易已经关闭");
		}
		TransPayState payState=trans.getPayState();
		if(TransPayState.paid.equals(payState)){
			return new Result(Result.FAIL,"该笔交易已经支付完成");
		}
		if(TransPayState.refunding.equals(payState)){
			return new Result(Result.FAIL,"该笔交易已经进入退款流程");
		}
		if(TransPayState.refunded.equals(payState)){
			return new Result(Result.FAIL,"该笔交易已经全额退款");
		}
		return new Result(Result.SUCCESS,"成功");
	}
	
	
	/**
	  * 方法描述：合并支付
	  * @param: Pay
	  * @param: Trans
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月5日 上午11:33:35
	  */
	private Result combinePay(Pay cashPay,Trans trans){
		String orderId=cashPay.getSn();
		String orderAmount=cashPay.getPayValue();
		SysClient sysClient=sysClientService.findSysClientById((trans.getClientId()));
		String orderSupplier=sysClient.getPartnerId();
		String subDisplay="";
		List<TransDetails> detailsList=trans.getDetailsList();
		if(detailsList!=null&&detailsList.size()>0){
			TransDetails details=detailsList.get(0);
			subDisplay=details.getProductName();
		}
		OpenApiOrderDetail detail = new OpenApiOrderDetail(orderId,orderAmount,orderSupplier,subDisplay);
		List<OpenApiOrderDetail> detailList=new ArrayList<OpenApiOrderDetail>();
		detailList.add(detail);
		// 订单数据
		String orderDisplay=orderDisplayPrefix+orderId;
		OrderType orderType=OrderType.COMBINE;
		PayChannel payChannel=trans.getPayChannel();
		String payOrg=cashPay.getPayOrg();
		String payerMember=cashPay.getFromPhone();
		String totalAmount=cashPay.getPayValue();
		PayDirect direct=null;
		if(PayRole.fft.equals(cashPay.getToRole())){
			direct=PayDirect.FROAD;
		}else{
			direct=PayDirect.F_MERCHANT;
		}
		Client client=Client.valueOf(trans.getCreateSource().name());
		OpenApiReq req = new OpenApiReq(detailList,orderType,totalAmount,payChannel,client,payOrg,payerMember,direct,orderDisplay,orderSupplier);
		Pay tmpPay=new Pay();
		tmpPay.setId(cashPay.getId());
		try {
			OpenApiRes response=openApiFunc.combineOrder(req);
			if(RespCodeCommand.SUCCESS.equals(response.getResultCode())){
				tmpPay.setPayState(PayState.pay_request_success);
				tmpPay.setBillNo(response.getFroadBillNo());
				payMapper.updatePayById(tmpPay);
				String msg=response.getPaymentURL();
				if(StringUtils.isEmpty(msg)){
					msg="成功";
				}
				return new Result(Result.SUCCESS,msg);
			}else{
				tmpPay.setPayState(PayState.pay_request_fail);
				tmpPay.setResultCode(response.getResultCode());
				tmpPay.setResultDesc(response.getResultDesc());
				payMapper.updatePayById(tmpPay);
				return new Result(Result.FAIL,"合并支付请求失败，结果码："+response.getResultCode());
			}
		} catch (AppException e) {
			log.error("合并支付请求异常，交易序列号："+trans.getSn(),e);
			tmpPay.setResultDesc(e.getErrMsg());
			tmpPay.setPayState(PayState.pay_request_fail);
			payMapper.updatePayById(tmpPay);
			return new Result(Result.FAIL,e.getErrMsg());
		}
	}
	
	
	/**
	  * 方法描述：消费积分
	  * @param: Pay
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月3日 下午6:02:31
	  */
	private Result payPoints(Pay pointsPay,Long clientId){
		String objectNo=pointsPay.getSn();
		String accountId=pointsPay.getFromAccountNo();
		String points=pointsPay.getPayValue();
		String accountMarked=pointsPay.getFromUserName();
		SysClient client=sysClientService.findSysClientById(clientId);
		PointsReq req=new PointsReq(objectNo,accountId,points,accountMarked,client.getPartnerId());
		PointsRes pointsRes=null;
		try {
			pointsRes = pointsFunc.consumePoints(req);
		} catch (AppException e) {
			log.error("消费积分出现异常，交易号："+pointsPay.getTransId(),e);
			pointsRes=new PointsRes();
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc(e.getErrMsg());
		}
		Result result=new Result();
		if(RespCodeCommand.SUCCESS.equals(pointsRes.getResultCode())){
			pointsPay.setPayState(PayState.pay_success);
			pointsPay.setPointBillNo(pointsRes.getPayPointsNo());
			result.setCode(Result.SUCCESS);
			result.setMsg("成功");
		}else{
			log.error("积分消费失败，原因："+pointsRes.getResultDesc()+"，交易号："+pointsPay.getTransId());
			pointsPay.setPayState(PayState.pay_fail);
			pointsPay.setResultCode(pointsRes.getResultCode());
			pointsPay.setResultDesc(pointsRes.getResultDesc());
			result.setCode(Result.FAIL);
			result.setMsg(pointsRes.getResultDesc());
		}
		payMapper.updatePayById(pointsPay);
		return result;
	}
	
	/**
	  * 方法描述：退还积分
	  * @param: pointsPay 积分的pay表记录
	  * @param: payPointsNo 积分消费编号
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 8, 2013 6:02:49 PM
	  */
	@Override
	public boolean refundPoints(Pay pointsPay,Long clientId){
		PayState state=pointsPay.getPayState();
		if(PayState.refund_success.equals(state)){
			log.info("积分已经成功退还，不需要重新发起，交易号："+pointsPay.getTransId());
			return true;
		}
		if(!PayState.pay_success.equals(state)&&
				!PayState.refund_fail.equals(state)){
			log.info("积分没有支付成功，或已经退还，交易号："+pointsPay.getTransId());
			return true;
		}
		String objectNo=pointsPay.getSn();
		String accountId=pointsPay.getFromAccountNo();
		String points=pointsPay.getPayValue();
		String accountMarked=pointsPay.getFromUserName();
		String payPointsNo=pointsPay.getPointBillNo();
		SysClient client=sysClientService.findSysClientById(clientId);
		if(client==null){
			log.error("客户端信息不存在，传入的clientId="+clientId);
			return false;
		}
		String partnerId=client.getPartnerId();
		PointsReq req=new PointsReq(objectNo,accountId,points,accountMarked,payPointsNo,partnerId);
		PointsRes pointsRes=null;
		try {
			pointsRes = pointsFunc.refundPoints(req);
		} catch (AppException e) {
			log.error("退还积分出现异常，交易号："+pointsPay.getTransId(),e);
			pointsRes=new PointsRes();
			pointsRes.setResultCode(RespCodeCommand.EXCEPTION);
			pointsRes.setResultDesc(e.getErrMsg());
		}
		if(RespCodeCommand.SUCCESS.equals(pointsRes.getResultCode())){
			pointsPay.setPayState(PayState.refund_success);
			pointsPay.setRefundPointNo(pointsRes.getRefundPointsNo());;
		}else{
			log.error("积分退还失败，原因："+pointsRes.getResultDesc()+"，交易号："+pointsPay.getTransId());
			pointsPay.setPayState(PayState.refund_fail);
			pointsPay.setResultCode(pointsRes.getResultCode());
			pointsPay.setResultDesc(pointsRes.getResultDesc());
		}
		payMapper.updatePayById(pointsPay);
		return PayState.refund_success.equals(pointsPay.getPayState());
	}

	@Override
	public void doNotice(NoticeFroadApi notice) {
		String orderId=notice.getOrder().getOrderID().replace(SystemCommand.FFT_CLIENT_PREFIX, "");
		String stateCode=notice.getOrder().getStateCode();
		String resultCode=notice.getSystem().getResultCode();
		Pay pay=payMapper.selectPayBySn(orderId);
		if(!this.checkNotice(pay, orderId)){
			return;
		}
		boolean payOk=OpenApiCommand.STATE_CODE_PAYED.equals(stateCode);
		Pay persistPay=new Pay();
		persistPay.setId(pay.getId());
		persistPay.setPayState(payOk?PayState.pay_success:PayState.pay_fail);
		persistPay.setResultCode(resultCode);
		payMapper.updatePayById(persistPay);
		Trans tmpTrans=new Trans();
		tmpTrans.setId(pay.getTransId());
		Trans trans=transMapper.selectTransById(pay.getTransId());
		if(payOk){//支付成功
			this.afterProductPay(trans);
			this.afterMerchantPay(trans);
		}else{
			List<Pay> payList=trans.getPayList();
			tmpTrans.setState(TransState.fail);
			Pay tmpPay=null,pointsPay=null;
			PayTypeDetails typeDetails=null;
			for (int i = 0; i < payList.size(); i++) {
				tmpPay=payList.get(i);
				typeDetails=tmpPay.getPayTypeDetails();
				if(PayTypeDetails.PAY_FFT_POINTS.equals(typeDetails)||
						PayTypeDetails.PAY_BANK_POINTS.equals(typeDetails)){
					pointsPay=tmpPay;
					break;
				}
			}
			if(pointsPay!=null){//退积分
				boolean isRefund=this.refundPoints(pointsPay,trans.getClientId());
				if(isRefund){
					tmpTrans.setPayState(TransPayState.refunded);
				}else{
					tmpTrans.setState(TransState.exception);
				}
			}
			transMapper.updateTransById(tmpTrans);
		}
	}
	
	
	@Override
	public void doRefundNotice(NoticeFroadApi notice) {
		String refundOrderId=notice.getOrder().getRefundOrderID();
		String stateCode=notice.getOrder().getStateCode();
		String resultCode=notice.getSystem().getResultCode();
		Pay pay=payMapper.selectPayByRefundOrderId(refundOrderId);
		if(!this.checkRefundNotice(pay, refundOrderId)){
			return;
		}
		boolean refundOk=OpenApiCommand.STATE_CODE_REFUNDED.equals(stateCode);
		Pay persistPay=new Pay();
		persistPay.setId(pay.getId());
		persistPay.setPayState(refundOk?PayState.refund_success:PayState.refund_fail);
		persistPay.setResultCode(resultCode);
		payMapper.updatePayById(persistPay);
		Trans tmpTrans=new Trans();
		tmpTrans.setId(pay.getTransId());
		if(refundOk){//退款成功
			boolean need=this.needRefundPoints(pay.getTransId());
			if(need){
				tmpTrans.setState(TransState.exception);
				tmpTrans.setPayState(TransPayState.refunding);
			}else{
				tmpTrans.setState(TransState.fail);
				tmpTrans.setPayState(TransPayState.refunded);
			}
			this.sendRefundMessage(pay.getTransId());
		}else{
			tmpTrans.setState(TransState.exception);
		}
		transMapper.updateTransById(tmpTrans);
	}
	
	
	private boolean checkNotice(Pay pay,String orderId){
		if(pay==null){
			log.error("支付单号为："+orderId+"的支付记录不存在，无效的支付通知");
			return false;
		}
		if(PayState.pay_success.equals(pay.getPayState())){
			log.error("重复的支付通知，该笔订单已经支付成功。支付单号："+orderId);
			return false;
		}
		if(PayState.pay_fail.equals(pay.getPayState())){
			log.error("重复的支付通知，该笔订单已经支付失败。支付单号："+orderId);
			return false;
		}
		return true;
	}
	
	/**
	  * 方法描述：积分兑换、团购、精品预售支付成功后的处理
	  * @param: Trans 交易对象
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 11, 2014 5:03:08 PM
	  */
	private void afterProductPay(Trans trans){
		TransType transType=trans.getType();
		if(!TransHelper.isProductTrans(transType)){
			return;
		}
		Trans tmpTrans=new Trans();
		tmpTrans.setId(trans.getId());
		tmpTrans.setPayState(TransPayState.paid);
		if(TransType.presell.equals(transType)){//如果是精品预售
			TransDetails details=trans.getDetailsList().get(0);
			Long productId=details.getProductId();
			ProductPresell presell=productPresellMapper.selectByProductId(productId);
		    int trueBuyerNum=presell.getTrueBuyerNumber()+details.getQuantity();
		    presell.setTrueBuyerNumber(trueBuyerNum);
//		    int quantity=trueBuyerNum+presell.getVirtualBuyerNumber();
//		    if(quantity>=presell.getClusteringNumber()){
//		    	presell.setClusterState(ClusterState.success);
//		    }
		    productPresellMapper.updateProductPresellById(presell);
		    log.info("========预售商品参团数量更新完成=======");
		    //TODO 冻结库存--
		    
		}else if(TransType.points_exchange.equals(transType)||
				TransType.group.equals(transType)){//如果是积分兑换或者团购
			tmpTrans.setState(TransState.success);
			//TODO 积分兑换和团购交易支付后处理(发券、冻结库存)，如果积分兑换需要送积分,做相应操作--
			
		}
		transMapper.updateTransById(tmpTrans);
	}
	
	private void afterMerchantPay(Trans trans){
		//TODO 支付后处理--
		
	}

	@Override
	public boolean refund(Pay cashPay,String reason,Long clientId){
		PayState state=cashPay.getPayState();
		if(!PayState.pay_success.equals(state)&&
				!PayState.refund_request_fail.equals(state)&&
				!PayState.refund_fail.equals(state)){
			log.info("该笔支付记录的状态为："+state.getDescribe()+"，不能发起退款申请。支付序号："+cashPay.getSn());
			return true;
		}
		SysClient client=sysClientService.findSysClientById(clientId);
		if(client==null){
			log.error("客户端信息不存在，传入的clientId="+clientId);
			return false;
		}
		String refundOrderId=SerialNumberUtil.buildRefundSn();
		String orderId=cashPay.getSn();
		String refundAmount=cashPay.getPayValue();
		String orderAmount=cashPay.getPayValue();
		String refundReason=reason;
		RefundType refundType=RefundType.ALL;
		String orderSupplier=client.getPartnerId();
		String partnerId=client.getPartnerId();
		OpenApiReq req=new OpenApiReq(refundOrderId,orderId , orderAmount, refundAmount, refundType, orderSupplier, refundReason, partnerId);
		OpenApiRes refundRes=null;
		try {
			refundRes=openApiFunc.refund(req);
		} catch (Exception e) {
			log.error("发送退款请求出现异常，交易号： "+cashPay.getTransId(),e);
		}
		Pay tmpPay=new Pay();
		tmpPay.setId(cashPay.getId());
		tmpPay.setRefundOrderId(refundOrderId);
		if(refundRes!=null&&RespCodeCommand.SUCCESS.equals(refundRes.getResultCode())){
			log.info("======退款请求发送成功，交易号："+cashPay.getTransId());
			tmpPay.setPayState(PayState.refund_request_success);
		}else{
			log.info("=======退款请求发送失败，交易号："+cashPay.getTransId());
			tmpPay.setPayState(PayState.refund_request_fail);
			if(refundRes!=null){
				tmpPay.setResultCode(refundRes.getResultCode());
				tmpPay.setResultDesc(refundRes.getResultDesc());
			}
		}
		payMapper.updatePayById(tmpPay);
		return PayState.refund_request_success.equals(tmpPay.getPayState());
	}
	
	private boolean checkRefundNotice(Pay pay,String orderId){
		if(pay==null){
			log.error("支付单号为："+orderId+"的支付记录不存在，无效的退款通知");
			return false;
		}
		if(PayState.refund_success.equals(pay.getPayState())){
			log.error("重复的退款通知，该笔订单已经退款成功。支付单号："+orderId);
			return false;
		}
		return true;
	}
	
	
	/**
	  * 方法描述：是否需要退还积分
	  * @param: transId
	  * @return: boolean 是|否
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月9日 下午3:06:24
	  */
	private boolean needRefundPoints(Long transId){
		List<Pay> payList=payMapper.selectPayByTransId(transId);
		Pay pay=null;
		PayTypeDetails typeDetails=null;
		for (int i = 0; i < payList.size(); i++) {
			pay=payList.get(i);
			typeDetails=pay.getPayTypeDetails();
			if(PayTypeDetails.PAY_FFT_POINTS.equals(typeDetails)||
					PayTypeDetails.PAY_BANK_POINTS.equals(typeDetails)){
				PayState state=pay.getPayState();
				if(PayState.refund_fail.equals(state)||
						PayState.pay_success.equals(state)){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Result doRefund(String sn, String reason) {
		if(StringUtils.isEmpty(sn)){
			return new Result(Result.FAIL,"请填写交易序号");
		}
		Trans trans=transMapper.selectBySn(sn);
		return this.doRefund(trans, reason);
	}

	@Override
	public Result doRefund(Trans trans, String reason) {
		if(trans==null){
			return new Result(Result.FAIL,"交易不存在");
		}
		if(reason==null||"".equals(reason)){
			return new Result(Result.FAIL,"请填写退款原因");
		}
		List<Pay> payList=trans.getPayList();
		Trans tmpTrans=new Trans();
		tmpTrans.setId(trans.getId());
		int size=payList.size();
		Pay pay=null;
		PayTypeDetails type=null;
		for (int j = 0; j < size; j++) {
			pay=payList.get(j);
			type=pay.getPayTypeDetails();
			if(PayTypeDetails.PAY_AMOUNT.equals(type)){//资金支付
				boolean isOk=this.refund(pay, reason,trans.getClientId());//退款
				if(isOk){//退款请求成功
					tmpTrans.setPayState(TransPayState.refunding);
				}else{
					tmpTrans.setState(TransState.exception);
					log.info("退款请求发送失败，定时任务将会重新发起退款请求");
				}
			}else if(PayTypeDetails.PAY_FFT_POINTS.equals(type)||
					PayTypeDetails.PAY_BANK_POINTS.equals(type)){//积分支付
				boolean isOk=this.refundPoints(pay,trans.getClientId());//退积分
				if(isOk){//积分退还成功
					if(size==1){//纯积分支付
						tmpTrans.setState(TransState.fail);
						tmpTrans.setPayState(TransPayState.refunded);
					}else{
						tmpTrans.setPayState(TransPayState.refunding);
					}
				}else{
					tmpTrans.setState(TransState.exception);
					log.info("积分退还失败，定时任务将会重新发起退分");
				}
			}
		}
		transMapper.updateTransById(tmpTrans);//更新交易状态和支付
		return new Result(Result.SUCCESS,"操作成功");

	}
	
	private void sendRefundMessage(Long transId){
		Trans trans=transMapper.selectTransById(transId);
		SysClient client=sysClientService.findSysClientById(trans.getClientId());
		TransType type=trans.getType();
		if(TransType.presell.equals(type)){
			SmsBean smsBean = new SmsBean(SmsType.presellRefund, 
					client.getId(), trans.getPhone(), null, null,"",false);
			sMSMessageFuncImpl.sendSMSMessage(smsBean);
		}
	}

}
