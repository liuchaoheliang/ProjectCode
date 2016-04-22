package com.froad.logic.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.PayTempBean;
import com.froad.common.beans.PaymentPoTemp;
import com.froad.common.beans.ResultBean;
import com.froad.common.beans.payment.RefundTempBean;
import com.froad.enums.CashType;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.OrderState;
import com.froad.enums.OrderStatus;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ResultCode;
import com.froad.enums.SettlementStatus;
import com.froad.enums.SubOrderType;
import com.froad.logic.OrderLogic;
import com.froad.logic.PaymentLogic;
import com.froad.logic.RefundLogic;
import com.froad.logic.SettlementLogic;
import com.froad.logic.TicketLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Org;
import com.froad.po.Payment;
import com.froad.po.Store;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.support.PaymentSupport;
import com.froad.support.impl.PaymentSupportImpl;
import com.froad.thirdparty.common.OpenApiCommand;
import com.froad.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.thirdparty.dto.request.points.PointsReq.QueryOrderType;
import com.froad.thirdparty.dto.request.points.ResponseQueryOrderStatusApi;
import com.froad.thirdparty.dto.response.openapi.NoticeFroadApi;
import com.froad.thirdparty.dto.response.points.PointsApiQueryParams.StateCode;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.froad.util.Arith;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.EsyT;
import com.froad.util.payment.Logger;
import com.pay.user.dto.UserSpecDto;

@Deprecated
public class PaymentLogicImpl implements PaymentLogic {
    
	
	private static final int PAYMENT_TYPE_SETTLE = 0; //结算类型支付流水
	
	private static final int PAYMENT_TYPE_REFUND = 1; //退款类型支付流水
	
	private static final int PAYMENT_TYPE_MEMBER_PAY = 2; //用户支付类型支付流水
	
	private static final int PAYMENT_TYPE_PRESENT_FROADPOINT = 3;//赠送方付通积分流水
	
	private static final int PAYMENT_TYPE_DEDUCT_PRESENT_FROADPOINT = 4;//扣除赠送方付通积分流水

	private static final String ORDER_PAYSUCCESS = "6"; //订单支付成功状态码
	
	private static final String ORDER_PAYFAILED = "5"; //订单支付失败状态码

    private static final String OPENAPI_ORDER_TYPE_CLIENT_PC = "100"; //默认OpenAPI请求客户端类型为PC

    private final String SUCCESS_CODE = "0000";
    
    private OrderLogic orderLogic = new OrderLogicImpl();
    
    private PaymentSupport paymentSupport = new PaymentSupportImpl();
	
    
    /**
     * Copyright © 2015 F-Road. All rights reserved.
     * @ClassName: ValidationOf
     * @Description: 支持订单验证模块(优化代码阅读)
     * @Author: zhaoxiaoyao@f-road.com.cn
     * @Date: 2015年4月24日 下午3:18:59
     */
    class ValidationOf{
    	
    	/**
    	 * Copyright © 2015 F-Road. All rights reserved.
    	 * @Title: validationPayMethodSupport
    	 * @Description: 验证当前客户端分配的支付方式是否支持用户所选
    	 * @Author: zhaoxiaoyao@f-road.com.cn
    	 * @param payTempBean
    	 * @return
    	 * @Return: ResultBean
    	 */
    	ResultBean validationPayMethodAndFoilNum(PayTempBean payTempBean){
    		
    		List<Map<String, String>> listMap = new ArrayList<Map<String,String>>(); //paymentSupport.getPaymentChannelSetFromRedis(payTempBean.getClientId());
    		if(listMap == null){
    			return new ResultBean(ResultCode.payment_paytype_notfound);//未找到当前客户端有效的支付方式记录
    		}
    		
    		boolean isPointsOrgExist = false;
    		boolean isCashOrgExist = false;
    		
    		//--------确认传入的机构号能对应查出的有效的机构号
    		if(BaseSubassembly.isCombinePayment(payTempBean.getPayType())){  //对比积分和现金的积分机构号
    			for (Map<String, String> map : listMap) {
    				if(payTempBean.getCashOrgNo().equals(map.get("payment_org_no"))){
    					isCashOrgExist = true;
    					break;
    				}
    			}
    			for (Map<String, String> map : listMap) {
    				if(payTempBean.getPointOrgNo().equals(map.get("payment_org_no"))){
    					payTempBean.setPointRatio(Integer.valueOf((map.get("point_rate")))); //含有积分的需要从渠道中获取积分比例
    					isPointsOrgExist = true;
    					break;
    				}
    			}
    		}else{
    			if(BaseSubassembly.isPurePointsPayment(payTempBean.getPayType())){ //纯积分
    				for (Map<String, String> map : listMap) {
    					if(payTempBean.getPointOrgNo().equals(map.get("payment_org_no"))){
    						payTempBean.setPointRatio(Integer.valueOf(map.get("point_rate")));
    						isPointsOrgExist = true;
    						break;
    					}
    				}
    				isCashOrgExist = true;
    			}else{ //纯现金
    				for (Map<String, String> map : listMap) {
    					if(payTempBean.getCashOrgNo().equals(map.get("payment_org_no"))){
    						isCashOrgExist = true;
    						break;
    					}
    				}
    				isPointsOrgExist = true;
    			}
    		}
    		
    		if(!StringUtils.isEmpty(payTempBean.getFoilCardNum())){
    			
    			String paymentOrgNo = null;
    			
    			for (Map<String, String> map : listMap) {
    				if(String.valueOf(CashType.foil_card.code()).equals(map.get("type"))){ //获取贴膜卡支付OrgNo
    					paymentOrgNo = map.get("payment_org_no");
    					break;
    				}
    			}
    			
    			if(StringUtils.isEmpty(paymentOrgNo)){
    				//贴膜卡支付OrgNo未能成功获取
    				Logger.logInfo("指定贴膜卡支付，但未能成功获取当前客户端下的贴膜卡支付机构号","clientId",payTempBean.getClientId());
    				return new ResultBean(ResultCode.payment_paytype_unsupport);
    			}
    			
    			ResultBean resultBean = paymentSupport.verifyFoilCardNum(payTempBean.getClientId(), paymentOrgNo, payTempBean.getFoilCardNum());
    			if(!ResultCode.success.getCode().equals(resultBean.getCode())){
    				Logger.logInfo("贴膜卡支付指定手机非有效贴膜卡手机号","foilCardNum",payTempBean.getFoilCardNum());
    				return resultBean;
    			}
    		}
    		
    		if(isPointsOrgExist && isCashOrgExist){//自此检验完毕
    			return new ResultBean(ResultCode.success);
    		}
    		
    		Logger.logInfo("指定的支付方式机构号在对应客户端支付渠道配置信息中不存在","clientId",payTempBean.getClientId());
    		return new ResultBean(ResultCode.payment_paytype_unsupport); // 指定的支付机构号不符
    	}
    	
    	
    	
    	/**
	   	 *
	   	 * Copyright © 2015 F-Road. All rights reserved.
	   	 * @Title: validationOrderPayInfoAbout
	   	 * @Description: 验证订单和支付记录的可行性
	   	 * @Author: zhaoxiaoyao@f-road.com.cn
	   	 * @param order
	   	 * @param payTempBean
	   	 * @return
	   	 * @Return: ResultBean
	   	 */
    	ResultBean validationOrderPrice(OrderMongo order, PayTempBean payTempBean){
   		
	   		double orderAmount = Arith.div(order.getTotalPrice(),1000);
	   		double payAmount = 0.00;
	   		
	   		if(BaseSubassembly.isCombinePayment(payTempBean.getPayType())){
	   			if(payTempBean.getPointAmount() <= 0 || payTempBean.getCashAmount() <= 0){
		   			return new ResultBean(ResultCode.payment_params_error);
		   		}
	   		}else if(BaseSubassembly.isPurePointsPayment(payTempBean.getPayType())){
	   			if(payTempBean.getPointAmount() <= 0){
		   			return new ResultBean(ResultCode.payment_params_error);
		   		}
	   		}else{
	   			if(payTempBean.getCashAmount() <= 0){
		   			return new ResultBean(ResultCode.payment_params_error);
		   		}
	   		}
	   		
	   		//验证价格是否足以支付订单
	   		if(BaseSubassembly.isCombinePayment(payTempBean.getPayType())){  
	   			payAmount = Arith.add(payTempBean.getCashAmount() , Arith.div(payTempBean.getPointAmount(),payTempBean.getPointRatio()));
	   		}else if(BaseSubassembly.isPurePointsPayment(payTempBean.getPayType())){
	   			payAmount = Arith.div(payTempBean.getPointAmount(), payTempBean.getPointRatio());
	   		}else{
	   			//纯现金不涉及比例
	   			payAmount = payTempBean.getCashAmount();
	   		}
	   		if(payAmount < orderAmount){
	   			return new ResultBean(ResultCode.payment_payamount_notequal, "支付金额不足");
	   		}else if (payAmount > orderAmount){
	   			return new ResultBean(ResultCode.payment_payamount_notequal, "支付金额超出");
	   		}
	   		
	   		return new ResultBean(ResultCode.success);
	   	}
    }
    
    @Override
	public ResultBean verifyFoilCardNum(String clientId,String mobileNum) {
    	List<Map<String, String>> chanels = new ArrayList<Map<String,String>>();//paymentSupport.getPaymentChannelSetFromRedis(clientId);
    	if(chanels == null || chanels.size() == 0){
    		return new ResultBean(ResultCode.failed);
    	}
    	
    	for (Map<String, String> map : chanels) {
			if("20".equals(map.get("type"))){ //贴膜卡类型
				return paymentSupport.verifyFoilCardNum(clientId, map.get("type"), mobileNum);
			}
		}
		return new ResultBean(ResultCode.failed);
	}
    
    /**
     * 历史代码结构造成这个方法体居然存在
    * <p>Function: toPayment</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-8 下午5:33:43
    * @version 1.0
    * @returnll
     */
    private Payment toPayment(PaymentPoTemp paymentPoTemp){
    	//使用Mongo
		Payment payment = new Payment();										
		payment.setCreateTime(new Date());
		payment.setPaymentId(paymentPoTemp.getPaymentId());
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
		return payment;
    }
    
	//------------------------- 支付辅助验证方法体模块  ------------------------
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: savePaymentInfo
	 * @Description: 保存当前流水，禁用历史用户支付流水
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param payTempBean
	 * @return
   	 * @Return: boolean
	 */
	private ResultBean savePaymentAndDisableOldPayPayment(PayTempBean payTempBean){
		//--------作废历史支付信息
		//查询出有效的支付记录
		
		long startTime = System.currentTimeMillis();
		List<Payment> payments = paymentSupport.findEnablePaymentsOfUserPayByOrderId(payTempBean.getOrderId());
		if(payments != null) { //存在历史支付记录，作废历史订单
			Logger.logInfo("开始作废支付单，共" + payments.size() + "条");
			Payment paymentTemp;
			int successCount = 0;
			for (Payment payment : payments) {
				paymentTemp = new Payment();
				paymentTemp.setPaymentId(payment.getPaymentId());
				paymentTemp.setIsEnable(false);
				if(paymentSupport.updatePayment(paymentTemp)){
					successCount ++;
				}else{
					Logger.logInfo("作废支付流水失败","paymentId",payment.getPaymentId());
					return new ResultBean(ResultCode.failed);
				}
			}
			Logger.logInfo("作废支付单共: " + payments.size() + "条,成功: " + successCount + "条");
		}
		
		Logger.logInfo("作废支付流水耗时-------------------------------------: " + (System.currentTimeMillis() - startTime));
		
		//--------作废历史支付信息
		List<Payment> payList = new ArrayList<Payment>();
		
		boolean flag = false;
		PaymentPoTemp paymentPoTemp;
		if(BaseSubassembly.isCombinePayment(payTempBean.getPayType())){ //积分&现金
			int payType;
			if(PaymentSupportImpl.FROAD_POINT_ORG_NO.equals(payTempBean.getPointOrgNo())){
				payType = 1;
			}else{
				payType = 3;
			}
			paymentPoTemp = new PaymentPoTemp(payTempBean.getClientId(), payTempBean.getOrderId(), payType, payTempBean.getPointAmount(), payTempBean.getPointOrgNo(),0, payTempBean.getPointRatio(),"2");
			
			paymentPoTemp.setPaymentId(EsyT.simpleID());
			payList.add(toPayment(paymentPoTemp));
			
			flag = paymentSupport.addPaymentToMongo(paymentPoTemp);
			if(flag){
				paymentPoTemp = new PaymentPoTemp(payTempBean.getClientId(), payTempBean.getOrderId(), 2, payTempBean.getCashAmount(), payTempBean.getCashOrgNo(),payTempBean.getCashType(), null,"2");
				if(!StringUtils.isEmpty(payTempBean.getFoilCardNum())){ //使用贴膜卡
					paymentPoTemp.setFromPhone(payTempBean.getFoilCardNum());
				}

				paymentPoTemp.setPaymentId(EsyT.simpleID());
				payList.add(toPayment(paymentPoTemp));
				
				flag = paymentSupport.addPaymentToMongo(paymentPoTemp);
				if(flag){
					return new ResultBean(ResultCode.success,payList);
				}
				Logger.logInfo("保存积分支付流水成功,但现金流水保存失败","paymentTempBean",payTempBean);
				return new ResultBean(ResultCode.failed);
			}
			Logger.logInfo("保存积分支付流水失败,现金流水亦不保存","paymentTempBean",payTempBean);
			return new ResultBean(ResultCode.failed);
		}else{ //纯积分
			if(BaseSubassembly.isPurePointsPayment(payTempBean.getPayType())){
				int payType;
				if(PaymentSupportImpl.FROAD_POINT_ORG_NO.equals(payTempBean.getPointOrgNo())){
					payType = 1; //方付通积分
				}else{
					payType = 3;
				}
				paymentPoTemp = new PaymentPoTemp(payTempBean.getClientId(), payTempBean.getOrderId(), payType,payTempBean.getPointAmount(), payTempBean.getPointOrgNo(),0, payTempBean.getPointRatio(),"2");
				
				paymentPoTemp.setPaymentId(EsyT.simpleID());
				payList.add(toPayment(paymentPoTemp));
				
				flag = paymentSupport.addPaymentToMongo(paymentPoTemp);
				if(flag){
					return new ResultBean(ResultCode.success,payList);
				}
				Logger.logInfo("保存积分支付流水失败","paymentTempBean",payTempBean);
			}else{//纯现金
				paymentPoTemp = new PaymentPoTemp(payTempBean.getClientId(), payTempBean.getOrderId(), 2, payTempBean.getCashAmount(), payTempBean.getCashOrgNo(),payTempBean.getCashType(), null,"2");
				if(!StringUtils.isEmpty(payTempBean.getFoilCardNum())){ //使用贴膜卡
					paymentPoTemp.setFromPhone(payTempBean.getFoilCardNum());
				}
				paymentPoTemp.setPaymentId(EsyT.simpleID());
				payList.add(toPayment(paymentPoTemp));
				flag = paymentSupport.addPaymentToMongo(paymentPoTemp);
				if(flag){
					return new ResultBean(ResultCode.success,payList);
				}
				Logger.logInfo("保存现金支付流水失败","paymentTempBean",payTempBean);
			}
		}
		
		return new ResultBean(ResultCode.failed);
	}
	//---------------------------------生成新支付信息并作废历史支付信息-----------------------------
	
	@Override
	public ResultBean validationAllBeforePayOrder(PayTempBean payTempBean,OrderMongo order) {
		
		ValidationOf validationOf = new ValidationOf();		
		
		//-------支付方式校验
	    ResultBean resultBean = validationOf.validationPayMethodAndFoilNum(payTempBean); //检验支付方式，和支付机构号
		if(!ResultCode.success.getCode().equals(resultBean.getCode())){
			return resultBean;
		}
		//-------支付方式校验完毕
		
		Integer totalPrice = order.getTotalPrice();
		if(totalPrice == null){
			Logger.logInfo("预支付订单没有总价参数","orderId",payTempBean.getOrderId());
			return new ResultBean(ResultCode.payment_order_error);
		}
		
		//-------校验订单信息
		resultBean = validationOf.validationOrderPrice(order, payTempBean);
		if(!ResultCode.success.getCode().equals(resultBean.getCode())){
			return resultBean;
		} 
		
		
		UserSpecDto userSpecDto = paymentSupport.queryByMemberCode(order.getMemberCode());
		if(userSpecDto == null){
			return new ResultBean(ResultCode.payment_member_lack);
		}
		//-------校验订单信息完毕
		
		
		resultBean = savePaymentAndDisableOldPayPayment(payTempBean);//禁用之前所有的支付流水并创建新的支付流水
		if(!ResultCode.success.getCode().equals(resultBean.getCode())){
			Logger.logInfo("保存并禁用历史支付流水处理失败");
			return resultBean;
		}

		return resultBean;
	
	}

	@Override
	public ResultBean doPay(List<Payment> payList,String token) {
		
		//-------------------获取支付信息
		Payment[] paymentArray = sortPaymentByType(payList);
		ResultBean resultBean = paymentSupport.doPayCore(paymentArray,token);//调用支付核心代码
		Logger.logInfo("核心模块支付模块支付结果", "resultBean", resultBean);
		
		return resultBean;
	}

    private Payment[] sortPaymentByType(List<Payment> payments) {
        Payment[] paymentArray;
		if(payments.size() != 1){
			paymentArray = new Payment[2];
			for (int i = 0 ; i < payments.size() ; i ++) {
				if (1 == payments.get(i).getPaymentType() || 3 == payments.get(i).getPaymentType()) { //积分支付在前
					paymentArray[0] = payments.get(i);
					payments.remove(i);
				}
			}
			paymentArray[1] = payments.get(0);
			//多条支付信息
		} else {
			paymentArray = new Payment[1];
			//单条支付信息
			paymentArray[0] = payments.get(0);
		}
        return paymentArray;
    }
	

	@Override
	public ResultBean doPayRedress(OrderMongo order) {
		
		Logger.logInfo("订单支付完毕，进行后续逻辑处理 orderId: " + order.getOrderId());
		
		ResultBean resultBean;
		
		//移除订单定时任务缓存
		boolean flag = paymentSupport.removeTimeOrder(order.getClientId(), order.getOrderId());
		Logger.logInfo("移除缓存订单: " + flag);
		
		//订单状态改为支付完成
		String orderId = order.getOrderId();
		
		OrderMongo orderMongo = new OrderMongo();
		orderMongo.setOrderId(orderId);
		orderMongo.setClientId(order.getClientId());
		orderMongo.setOrderStatus(ORDER_PAYSUCCESS);
		
		resultBean = orderLogic.updateOrderForPay(orderMongo);
		Logger.logInfo("修改订单状态结果: " + resultBean.getCode());
		
		order.setOrderStatus(ORDER_PAYSUCCESS);
		
		//如果大订单上记录有赠送积分数量，则需要对该订单的用户进行赠送积分操作
		if(order.getGivePoints() != null && order.getGivePoints() != 0){
			Logger.logInfo("订单存在赠送积分参数" ,"givePoints", order.getGivePoints());
			resultBean = paymentSupport.autoPresentFroadPoints(order.getMemberCode(), order.getGivePoints(), order.getClientId(),orderId);
			Logger.logInfo("自动赠送积分结果","resultBean",resultBean);
		}
		
		long st = EsyT.currTime();
		
		TicketLogic ticketLogic = new TicketLogicImpl();
		resultBean = ticketLogic.addTicket(order);
		Logger.logInfo("TicketLogicImpl.addTicket处理耗时" + (EsyT.currTime() - st),"resultBean",resultBean);
		
		st = EsyT.currTime();
		SettlementLogic settlementLogic = new SettlementLogicImpl();
		resultBean = settlementLogic.paySettlement(order);
		Logger.logInfo("SettlementLogicImpl.paySettlement处理耗时" + (EsyT.currTime() - st),"resultBean",resultBean);
		
		st = EsyT.currTime();
		OrderLogic orderLogic = new OrderLogicImpl();
		resultBean = orderLogic.updateProductSellCount(order); //增加商品销售数量
		Logger.logInfo("OrderLogicImpl.updateProductSellCount处理结果耗时" + (EsyT.currTime() - st),"resultBean",resultBean);
		
		st = EsyT.currTime();
		resultBean = paymentSupport.updateMerchantMonthCountForPaySuccess(order);//商户月销售统计数量累计
		Logger.logInfo("PaymentSupport.updateMerchantMonthCount处理耗时" + (EsyT.currTime() - st),"resultBean",resultBean);

		return new ResultBean(ResultCode.success);
	}

	@Override
	public ResultBean doPayResressFailed(OrderMongo order) {
		
		Logger.logInfo("订单支付失败","orderId",order.getOrderId());
		
		OrderMongo orderUpdate = new OrderMongo();
		orderUpdate.setOrderId(order.getOrderId());
		orderUpdate.setBankPoints(0);
		orderUpdate.setPaymentMethod("");
		orderUpdate.setFftPoints(0);
		orderUpdate.setPointRate("0");
		orderUpdate.setRealPrice(0);
		orderUpdate.setClientId(order.getClientId());
		
//		boolean flag = paymentSupport.removeTimeOrder(order.getClientId(), order.getOrderId());
//		Logger.logInfo("移除需要定时任务处理的TimeOrder","flag",flag);
		
		ResultBean resultBean = checkAndOperateStore(order,true);
		Logger.logInfo("准备退还库存");
		
		if(!ResultCode.success.getCode().equals(resultBean.getCode())){ //退库存失败
			Logger.logError("退还库存失败","orderId",order.getOrderId());
		}else{
			Logger.logInfo("退还库存成功,标记订单OrderState库存已退");
			orderUpdate.setState(OrderState.RETURNED.getCode()); //库存已退
		}
		Logger.logInfo("标记订单OrderStatus支付失败");
		orderUpdate.setOrderStatus(ORDER_PAYFAILED);//改订单为支付失败
		
		
		return orderLogic.updateOrderForPay(orderUpdate);
	}
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: rollbackPyamnetStep
	 * @Description: 回滚订单流水状态 只用于接收通知和主动获取支付结果 在支付结果不明确时回滚
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @return
	 * @Return: boolean
	 */
	private void rollbackPaymentStep(String paymentId){
		Payment paymentTemp = new Payment();
        paymentTemp.setPaymentId(paymentId);
        paymentTemp.setStep(3);
        boolean flag = paymentSupport.updatePayment(paymentTemp);
        Logger.logInfo("回滚支付流水step到3",new String[]{"paymentId","flag"},new Object[]{paymentId,flag});
	}
	
	@Override
	public ResultBean combinePaymentNotice(String xmlString) {
		
		NoticeFroadApi noticeFroadApi = paymentSupport.combineXMLToEntity(xmlString);
		
		if(noticeFroadApi == null){
			Logger.logError("处理合并支付请求时，将xml转entity失败");
			return new ResultBean(ResultCode.failed);
		}
		Logger.logInfo("成功将XML报文转为NoticeFroadApi对象","noticeFroadApi",noticeFroadApi);
		
		String paymentId = noticeFroadApi.getOrder().getOrderID();
		Logger.logInfo("通知流水","paymentId",paymentId);
		
		Payment payment = paymentSupport.findPaymentByPaymentId(paymentId);
		if(payment == null){
			Logger.logError("OpenAPI通知的合并支付流水号在本平台未找到对应的支付流水");
            return new ResultBean(ResultCode.failed);
        }
		
        if(isPaymentOperationEnd(payment)) {//是否为已经处理的支付流水payment.step=4
        	Logger.logInfo("合并支付流水已到达最终状态，不处理，该通知可能为重复通知","paymentId",paymentId);
            return new ResultBean(ResultCode.success);
        }
        
		if(payment.getStep() != 3 ){
			Logger.logError("合并支付流水通知不符合通知步骤流程，只接收step为3的支付流水状态变迁","paymentId",paymentId);
		    return new ResultBean(ResultCode.failed);
		}
		
		
		//----------  如果是订单已经关闭，则肯定是用户支付过程中取消了订单
		boolean isClosedOrder = false;
		String orderId = payment.getOrderId();
		ResultBean resultBean = orderLogic.getOrderByOrderId(payment.getClientId(),orderId);
		
        if (!isSuccess(resultBean)) {
        	Logger.logError("支付接口获取订单失败,错误原因:" + resultBean.getMsg(),"orderId",orderId);
            return new ResultBean(ResultCode.failed,"订单不存在");
        }

        OrderMongo order = (OrderMongo) resultBean.getData();
        if(OrderStatus.closed.getCode().equals(order.getOrderStatus()) || OrderStatus.sysclosed.getCode().equals(order.getOrderStatus())){
        	isClosedOrder = true;
        }
		//----------
		
		
		Payment original = new Payment();
		original.setStep(3);
		original.setPaymentId(paymentId);
		Payment paymentTemp = new Payment();
        paymentTemp.setStep(4);
        Payment paymentUpdate = paymentSupport.updatePaymentAndLock(original, paymentTemp);
        
        if(paymentUpdate == null){
        	Logger.logError("锁定并修改支付流水为step4失败","paymentId",paymentId);
            return new ResultBean(ResultCode.failed);
        }

		payment = paymentUpdate;
		boolean isSettlePayment = "0".equals(payment.getPaymentReason()); //该笔流水是否为结算流水
		boolean flag;
		
        //resultBean = orderLogic.getOrderByOrderId(payment.getClientId(),payment.getOrderId());
        if(!ResultCode.success.getCode().equals(resultBean.getCode())){
        	//查询订单信息失败
        	Logger.logError("未能获取该笔流水对应的订单信息","orderId",payment.getOrderId());
        	rollbackPaymentStep(paymentId);
            return new ResultBean(ResultCode.failed);
        }
		
        //OrderMongo order = (OrderMongo) resultBean.getData();
		
		String resultCode = noticeFroadApi.getSystem().getResultCode();
		
		if(!resultCode.equals(SUCCESS_CODE)){//不是有效的支付结果通知
			Logger.logInfo("通知支付信息结果非指明成功失败，按照支付中处理，不做任何逻辑变化");
			rollbackPaymentStep(paymentId);
			return new ResultBean(ResultCode.failed);
		}
		
		String stateCode = noticeFroadApi.getOrder().getStateCode();
		
		if("0".equals(stateCode)){
			
			paymentSupport.removeTimePayment(paymentId);
			
			Logger.logInfo("通知支付信息结果已成功，支付信息做相应的修改","paymentId",paymentId);
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setStep(4);
			paymentTemp.setPaymentStatus("4");
			paymentTemp.setRemark("收到OpenAPI异步通知，支付成功");
			flag = paymentSupport.updatePayment(paymentTemp);
			Logger.logInfo("修改支付记录结果: " + flag);
			
			if(isClosedOrder){ //如果被关闭了订单，则自动发起退现金请求
				Logger.logInfo("收到成功通知，订单已经关闭，自动发起现金退款");
				return paymentSupport.systemAutoRefundCash(payment);
			}
			
			if(!isSettlePayment){ //不是结算流水
				Logger.logInfo("用户支付流水支付成功，进行善后处理模块调用","paymentId",paymentId);
				resultBean = doPayRedress(order);//善后处理
				if(!ResultCode.success.getCode().equals(resultBean.getCode())){
					Logger.logError("用户支付流水支付成功，进行善后处理模块调用处理失败","resultBean",resultBean);
					return new ResultBean(ResultCode.failed,resultBean.getMsg());
				}
			}else{
				//结算流程
				resultBean = new SettlementLogicImpl().notitySettlement(paymentId, SettlementStatus.settlementsucc, null);
				Logger.logInfo("结算流水结算成功，调用结算接口通知结算结果","resultBean",resultBean);
			}
			return new ResultBean(ResultCode.success);
		}else if("1".equals(stateCode)){
			
			paymentSupport.removeTimePayment(paymentId);
			
			Logger.logInfo("通知支付信息结果已失败，支付信息做相应的修改，并判断是否需要退还积分","paymentId",paymentId);
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setStep(4);
			paymentTemp.setPaymentStatus("5");
			paymentTemp.setRemark("收到OpenAPI异步通知，支付失败");
			paymentTemp.setResultCode(resultCode);
			
            flag = paymentSupport.updatePayment(paymentTemp);
            Logger.logInfo("修改支付记录结果: " + flag);
			
            if(isClosedOrder){
            	Logger.logInfo("收到失败通知，订单已经关闭，不做任何处理");
            	return new ResultBean(ResultCode.success);
            }
            
			if(!isSettlePayment){
				
				Logger.logInfo("用户支付流水支付失败，进行善后失败处理模块调用","paymentId",paymentId);
				String resultStr = doPayResressFailed(order).toString();
				Logger.logInfo("用户支付流水支付失败，进行善后失败处理模块调用结果","resultStr",resultStr);
				
				//判断订单支付类型
				if(BaseSubassembly.isCombinePayment(order.getPaymentMethod())){
					Logger.logInfo("该笔现金支付流水的订单包含支付积分，需要进行自动退积分操作","orderId",payment.getOrderId());
					//如果是积分&现金支付则需要退积分
					List<Payment> payments = paymentSupport.findEnablePaymentsOfUserPayByOrderId(payment.getOrderId());
					for (Payment p : payments) {
						if((1 == p.getPaymentType() || 3 == p.getPaymentType()) && "2".equals(p.getPaymentReason()) && "4".equals(p.getPaymentStatus()) && p.getIsEnable()){
							if("0".equals("")){
								//该条为积分支付-需要退还积分  条件: 积分支付,用户支付流水,支付成功
								resultBean = paymentSupport.autoRefundPoints(p);
								if(!ResultCode.success.getCode().equals(resultBean.getCode())){
									Logger.logError("通知现金支付失败，发起自动退积分失败",
											new String[]{"paymentId","resultBean"},
											new Object[]{p.getPaymentId(),resultBean});
								}
								return resultBean;
							}
						}else{
							Logger.logInfo("未能退还积分，该支付信息已发生过自动退积分操作或者积分支付未成功");
						}
					}
					
				}
			}else{
				resultBean = new SettlementLogicImpl().notitySettlement(paymentId, SettlementStatus.settlementfailed, null);
				Logger.logInfo("结算流水结算失败，调用结算接口通知结算结果","resultBean",resultBean);
			}
			return new ResultBean(ResultCode.failed);
		}else{
			Logger.logInfo("通知支付信息结果非指明成功失败，按照支付中处理，不做任何逻辑变化");
			rollbackPaymentStep(paymentId);
			return new ResultBean(ResultCode.failed);
		}
	}

	@Override
	public ResultBean refundPyamentNotice(String xmlString) {
		
		NoticeFroadApi noticeFroadApi = paymentSupport.refundXMLToEntity(xmlString);
		if(noticeFroadApi == null){
			Logger.logError("处理合并支付请求时，将xml转entity失败");
			return new ResultBean(ResultCode.failed);
		}
		Logger.logInfo("成功将XML报文转为NoticeFroadApi对象","noticeFroadApi",noticeFroadApi);
		
		String paymentId = noticeFroadApi.getOrder().getRefundOrderID();
		Logger.logInfo("通知流水","paymentId",paymentId);
		
		Payment payment = paymentSupport.findPaymentByPaymentId(paymentId);
		
		if(payment == null){
			Logger.logError("OpenAPI通知的合并支付流水号在本平台未找到对应的支付流水");
			return new ResultBean(ResultCode.failed);
		}
		
		if(isPaymentOperationEnd(payment)) { //已经收到通知过
			Logger.logInfo("合并支付流水已到达最终状态，不处理，该通知可能为重复通知","paymentId",paymentId);
			return new ResultBean(ResultCode.success);
		}
		
		if(payment.getStep() != 3 ){
		    return new ResultBean(ResultCode.failed);
		}
		
		Payment paymentTemp = new Payment();
        paymentTemp.setStep(4);
        Payment original = new Payment();
        original.setPaymentId(paymentId);
        original.setStep(3);
        Payment paymentUpdate = paymentSupport.updatePaymentAndLock(original, paymentTemp);
        
        if(paymentUpdate == null){
        	Logger.logError("合并支付流水通知不符合通知步骤流程，只接收step为3的支付流水状态变迁","paymentId",paymentId);
            return new ResultBean(ResultCode.failed);
        }
		
        boolean isSystemAutoRefund = false;
        ///--------------------
        if("5".equals(payment.getPaymentReason())){
        	isSystemAutoRefund = true;
        }
        //---------------------判断是否为系统自动退现金
        
        
		String resultCode = noticeFroadApi.getSystem().getResultCode();
		boolean flag = false;
		
		RefundLogic refundLogic = new RefundLogicImpl();
		Map<String, Boolean> noticeMap = new HashMap<String, Boolean>();
		if(!"0000".equals(resultCode)){
			if("0301".equals(resultCode)){ //订单不存在的错误码
				//退款失败错误码
				paymentSupport.removeTimePayment(paymentId);
				
				Logger.logInfo("通知退款信息结果已失败，退款信息做相应的修改","paymentId",paymentId);
				paymentTemp = new Payment();
				paymentTemp.setPaymentId(paymentId);
				paymentTemp.setStep(4);
				paymentTemp.setPaymentStatus("5"); 
				paymentTemp.setRemark("收到OpenAPI异步通知，退款失败");
				flag = paymentSupport.updatePayment(paymentTemp);
				Logger.logInfo("修改退款记录结果: " + flag);
				
				if(isSystemAutoRefund){
					return new ResultBean(ResultCode.failed);
				}
				
				noticeMap.put(paymentId, false);
				paymentTemp = null;
				List<Payment> payments = paymentSupport.findAllEnablePaymentsByOrderId(payment.getOrderId());
				for (Payment p : payments) {
					if(1 == p.getPaymentType() && 1 == p.getStep() && "1".equals(p.getPaymentReason())
							&& "1".equals(p.getPaymentStatus())  && p.getIsEnable()){ //退款积分创建记录
						paymentTemp = p;
						break;
					}
				}
				if(paymentTemp != null){
					Logger.logInfo("该笔退款包含积分，将更改积分退款结果为失败");
					String pointPaymentId = paymentTemp.getPaymentId();
					paymentTemp = new Payment();
					paymentTemp.setPaymentId(pointPaymentId);
					paymentTemp.setStep(4);
					paymentTemp.setPaymentStatus("5");
					paymentTemp.setRemark("现金退款失败，此次退积分流水未进行退积分操作");
					flag = paymentSupport.updatePayment(paymentTemp);
					Logger.logInfo("修改退款记录结果: " + flag);
					
					noticeMap.put(pointPaymentId, false);
					
				}
				
				flag = refundLogic.updateApprovalResult(noticeMap);
				Logger.logInfo("调用退款通知接口，通知现金&积分退款失败: " + flag);
				return new ResultBean(ResultCode.failed);
				
			}
			rollbackPaymentStep(paymentId);
			Logger.logInfo("OpenAPI通知退款信息结果非指明成功失败，按照退款中处理，不做任何逻辑变化");
			return new ResultBean(ResultCode.failed);
		}
		
		String stateCode = noticeFroadApi.getOrder().getStateCode();
		if("0".equals(stateCode)){
			paymentSupport.removeTimePayment(paymentId);
			
			Logger.logInfo("通知退款信息结果已成功，退款信息做相应的修改","paymentId",paymentId);
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setStep(4);
			paymentTemp.setPaymentStatus("4"); //支付成功
			paymentTemp.setRemark("收到OpenAPI异步通知，退款成功");
			flag = paymentSupport.updatePayment(paymentTemp);
			Logger.logInfo("修改退款记录结果: " + flag);
			
			if(isSystemAutoRefund){
				return new ResultBean(ResultCode.failed);
			}
			
//			//退款数据的连带处理
//			flag = refundLogic.updateApprovalResult(paymentId, true);
//			PaymentSupportUtils.logInfo("调用退款通知接口，通知现金退款成功: " + flag);
			
			noticeMap.put(paymentId, true);
			
			//-----判断是否有连带的退积分操作
			paymentTemp = null;
			List<Payment> payments = paymentSupport.findAllEnablePaymentsByOrderId(payment.getOrderId());
			for (Payment p : payments) {
				if((1 == p.getPaymentType() || 3 == p.getPaymentType()) && 1 == p.getStep() && "1".equals(p.getPaymentReason())
						&& "1".equals(p.getPaymentStatus())){ //退款积分创建记录
					paymentTemp = p;
					break;
				}
			}
			if(paymentTemp == null){
				Logger.logInfo("该笔退款没有连带的积分退款信息，退款完成");
				flag = refundLogic.updateApprovalResult(noticeMap);
				Logger.logInfo("调用退款通知接口，通知现金已成功退款" + flag);
				return new ResultBean(ResultCode.success);
			}else{
				Logger.logInfo("该笔退款包含积分，将进行积分退款操作");
				ResultBean resultBean = paymentSupport.userGoOnRefundPoints(paymentTemp);
				Logger.logInfo("积分退款结果", "resultBean", resultBean);
				
				if(!ResultCode.success.getCode().equals(resultBean.getCode())){
					Logger.logError("现金退款完成，积分退款失败","积分流水paymentId",paymentTemp.getPaymentId());
					noticeMap.put(paymentTemp.getPaymentId(), false);
					flag = refundLogic.updateApprovalResult(noticeMap);
					Logger.logInfo("调用退款通知接口，通知现金已成功退款，积分退款失败: " + flag);
					return new ResultBean(ResultCode.failed,"现金已成功退款，积分退款失败"); 
				}
				Logger.logInfo("现金退款完成，积分退款成功");
				noticeMap.put(paymentTemp.getPaymentId(), true);
				flag = refundLogic.updateApprovalResult(noticeMap);
				Logger.logInfo("调用退款通知接口，通知现金退款成功积分退款成功: " + flag);
				return new ResultBean(ResultCode.success);
			}
		}else if("1".equals(stateCode)){
			
			paymentSupport.removeTimePayment(paymentId);
			Logger.logInfo("通知退款信息结果已失败，退款信息做相应的修改","paymentId",paymentId);
			paymentTemp = new Payment();
			paymentTemp.setPaymentId(paymentId);
			paymentTemp.setStep(4);
			paymentTemp.setPaymentStatus("5"); 
			paymentTemp.setRemark("收到OpenAPI异步通知，退款失败");
			flag = paymentSupport.updatePayment(paymentTemp);
			Logger.logInfo("修改退款记录结果: " + flag);
			
			if(isSystemAutoRefund){
				return new ResultBean(ResultCode.failed);
			}
			
			noticeMap.put(paymentId, false);
			
			//-----判断是否有连带的退积分操作
			paymentTemp = null;
			List<Payment> payments = paymentSupport.findAllEnablePaymentsByOrderId(payment.getOrderId());
			for (Payment p : payments) {
				if((1 == p.getPaymentType() || 3 == p.getPaymentType()) && 1 == p.getStep() && "1".equals(p.getPaymentReason())
						&& "1".equals(p.getPaymentStatus())  && p.getIsEnable()){ //退款积分创建记录
					paymentTemp = p;
					break;
				}
			}
			if(paymentTemp != null){
				Logger.logInfo("该笔退款包含积分，将更改积分退款结果为失败");
				String pointPaymentId = paymentTemp.getPaymentId();
				paymentTemp = new Payment();
				paymentTemp.setPaymentId(pointPaymentId);
				paymentTemp.setStep(4);
				paymentTemp.setPaymentStatus("5");
				paymentTemp.setRemark("现金退款失败，此次退积分流水未进行退积分操作");
				flag = paymentSupport.updatePayment(paymentTemp);
				Logger.logInfo("修改退款记录结果: " + flag);
				noticeMap.put(pointPaymentId, false);
			}
			flag = refundLogic.updateApprovalResult(noticeMap);
			Logger.logInfo("调用退款通知接口，通知现金退款失败积分退款失败: " + flag);
			return new ResultBean(ResultCode.failed);
		}else{
			rollbackPaymentStep(paymentId);
			Logger.logInfo("通知退款信息结果非指明成功失败，按照退款中处理，不做任何逻辑变化");
			return new ResultBean(ResultCode.failed);
		}
		
	}

	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @Title: verifyPaymentResultForTask
	 * @Description: 提供定时任务主动查询OpenAPI支付结果的统一接口 包含，用户支付，商户结算，用户退款
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @param paymentId
	 * @return
	 * @Return: ResultBean
	 */
	@Override
	public ResultBean verifyPaymentResultForTask(String paymentId) {//主动确认支付结果
		
		Payment payment = paymentSupport.findPaymentByPaymentId(paymentId);
		
		if(payment == null){
			Logger.logInfo("定时任务主动查询OpenAPI支付结果没有找到对应的支付流水记录 paymentId: " + paymentId);
	        return new ResultBean(ResultCode.failed);
	    }
			
		if(payment.getStep() != 3 ){
			Logger.logError("只接收step为3的支付流水状态变迁，该笔流水步骤不支持处理","paymentId",paymentId);
		    return new ResultBean(ResultCode.failed);
		}
		
		Payment original = new Payment();
		original.setPaymentId(paymentId);
		original.setStep(3);
		Payment paymentTemp = new Payment();
        paymentTemp.setStep(4);
        Payment paymentUpdate = paymentSupport.updatePaymentAndLock(original, paymentTemp);
        
        if(paymentUpdate == null){
        	Logger.logError("锁定并修改支付流水为step4失败","paymentId",paymentId);
            return new ResultBean(ResultCode.failed);
        }
		
//		if(!PaymentStatus.pay_request_success.getCode().equals(payment.getPaymentStatus())){
//		    return new ResultBean(ResultCode.failed,"支付状态不是[发起支付请求成功]，不需要检查,PaymentId:"+paymentId);
//		}
//		
		ResultBean checkResult = null;
//		if(payment.getPaymentReason()==null||!payment.getPaymentReason().matches("\\d")){
//		    return new ResultBean(ResultCode.failed,"错误的PaymentReason,无法完成处理,PaymentReson:"+payment.getPaymentReason());
//		}
		
		if(2 == payment.getPaymentType()){ //处理现金
			switch (Integer.valueOf(payment.getPaymentReason())) {
		        case PAYMENT_TYPE_SETTLE:
		            checkResult = verifySettleResultForTask(payment);
		            break;
		        case PAYMENT_TYPE_REFUND:
		            checkResult = verifyRefundResultForTask(payment);
		            break;
		        case PAYMENT_TYPE_MEMBER_PAY:
		            checkResult = verifyUserPaymentResultForTask(payment);
		            break;
		        default:
		        	Logger.logError("定时任务欲校验支付流水无效","paymentReason",payment.getPaymentReason());
		        return new ResultBean(ResultCode.failed);
	        }
		}else{ //处理积分
			
			switch (Integer.valueOf(payment.getPaymentReason())) {
			
				case PAYMENT_TYPE_DEDUCT_PRESENT_FROADPOINT://扣除赠送积分
		            checkResult = verifyPointConsumeResultForTask(payment);
		            break;
				case PAYMENT_TYPE_PRESENT_FROADPOINT://赠送积分
		            checkResult = verifyPointPresentResultForTask(payment);
		            break;
		        case PAYMENT_TYPE_REFUND://退还积分
		            checkResult = verifyPointRefundResultForTask(payment);
		            break;
		        case PAYMENT_TYPE_MEMBER_PAY://用户支付
		            checkResult = verifyPointConsumeResultForTask(payment);
		            break;
		        default:
		        	Logger.logError("定时任务欲校验支付流水无效","paymentReason",payment.getPaymentReason());
		        	
		        return new ResultBean(ResultCode.failed);
	        }
			
		}
		return checkResult;
	}

	private ResultBean verifyPointConsumeResultForTask(Payment payment){
		
		String paymentId = payment.getPaymentId();
        Payment paymentTemp = null;
        PointsRes res;
        
        try {
			res = paymentSupport.queryPointPaymentState(paymentId, QueryOrderType.consume, payment.getClientId());
		} catch (Exception e) {
			rollbackPaymentStep(paymentId);
        	Logger.logError("发送主动确认积分支付结果异常",e);
            return new ResultBean(ResultCode.success,"支付中状态");
		}
        
        boolean isUserPay = "1".equals(payment.getPaymentReason()) ? true : false;
        boolean flag;
        if(!SUCCESS_CODE.equals(res.getResultCode())){
        	rollbackPaymentStep(payment.getPaymentId());
        	return new ResultBean(ResultCode.success,"支付中状态");
        }
        
        ResponseQueryOrderStatusApi orderStatus = res.getOrderStatus();
        
        if(StateCode.not_exists.equals(orderStatus.getQueryParam().getOrderProccessState())){
        	
        	paymentSupport.removeTimePayment(paymentId);
    		
        	if(isUserPay){
    			ResultBean resultBean = orderLogic.getOrderByOrderId(payment.getClientId(),payment.getOrderId());
                if(!isSuccess(resultBean)){
                	Logger.logError("查询订单信息失败","orderId",payment.getOrderId());
                    return new ResultBean(ResultCode.failed); //查询订单失败
                }
                OrderMongo order = (OrderMongo)resultBean.getData();
                
                Logger.logInfo(
                		"查询支付流水在积分平台中并没有对应的账单信息，将认为该订单已失败，订单和支付流水将以失败结果进行处理"
                		,"paymentId",paymentId);
        		String resultStr = doPayResressFailed(order).toString();
        		Logger.logInfo("订单支付失败逻辑处理完毕 ","resultStr",resultStr);
       		 
				paymentTemp = new Payment();
                paymentTemp.setPaymentId(paymentId);
                paymentTemp.setStep(4);
                paymentTemp.setPaymentStatus("5");
                paymentTemp.setRemark("查询支付流水积分平台中并没有对应的账单信息，认为该订单已失败");
                flag = paymentSupport.updatePayment(paymentTemp);
                Logger.logInfo("修改支付记录结果: " + flag);
                return new ResultBean(ResultCode.failed);
                
    		}else{ //扣除赠送积分
    			Logger.logInfo(
                		"查询支付流水在积分平台中并没有对应的账单信息，将认为该订单已失败，订单和支付流水将以失败结果进行处理"
                		,"paymentId",paymentId);
                
				paymentTemp = new Payment();
                paymentTemp.setPaymentId(paymentId);
                paymentTemp.setStep(4);
                paymentTemp.setPaymentStatus("5");
                paymentTemp.setRemark("查询支付流水积分平台中并没有对应的账单信息，认为该订单已失败");
                flag = paymentSupport.updatePayment(paymentTemp);
                Logger.logInfo("修改支付记录结果: " + flag);
                return new ResultBean(ResultCode.failed);
    		}
        }else if(StateCode.success.equals(orderStatus.getQueryParam().getOrderProccessState())){
        	//如果支付成功，主动退款
        	paymentSupport.removeTimePayment(paymentId);
        	
    		if(isUserPay){
    			String resultStr = paymentSupport.autoRefundPoints(paymentSupport.findPaymentByPaymentId(paymentId)).toString();
    			Logger.logInfo("用户异常扣积分成功，自动退还积分","resultStr",resultStr);
    			ResultBean resultBean = orderLogic.getOrderByOrderId(payment.getClientId(),payment.getOrderId());
                if(!isSuccess(resultBean)){
                	Logger.logError("查询订单信息失败","orderId",payment.getOrderId());
                    return new ResultBean(ResultCode.failed); //查询订单失败
                }
                OrderMongo order = (OrderMongo)resultBean.getData();
    			resultBean = doPayResressFailed(order);
    			Logger.logInfo("执行订单支付失败逻辑","resultBean",resultBean);
                return new ResultBean(ResultCode.success);
                
    		}else{
    			Logger.logInfo(
                		"查询支付流水在积分平台中支付已成功"
                		,"paymentId",paymentId);
                
				paymentTemp = new Payment();
                paymentTemp.setPaymentId(paymentId);
                paymentTemp.setStep(4);
                paymentTemp.setPaymentStatus("4");
                paymentTemp.setRemark("查询积分平台支付结果已成功");
                flag = paymentSupport.updatePayment(paymentTemp);
                Logger.logInfo("修改支付记录结果: " + flag);
                return new ResultBean(ResultCode.failed);
    		}
        }else if(StateCode.failed.equals(orderStatus.getQueryParam().getOrderProccessState())){
        	paymentSupport.removeTimePayment(paymentId);
        	if(isUserPay){
        		ResultBean resultBean = orderLogic.getOrderByOrderId(payment.getClientId(),payment.getOrderId());
                if(!isSuccess(resultBean)){
                	Logger.logError("查询订单信息失败","orderId",payment.getOrderId());
                    return new ResultBean(ResultCode.failed); //查询订单失败
                }
                OrderMongo order = (OrderMongo)resultBean.getData();
                
                Logger.logInfo(
                		"查询支付流水在积分平台中支付失败"
                		,"paymentId",paymentId);
        		String resultStr = doPayResressFailed(order).toString();
        		Logger.logInfo("订单支付失败逻辑处理完毕 ","resultStr",resultStr);
       		 
				paymentTemp = new Payment();
                paymentTemp.setPaymentId(paymentId);
                paymentTemp.setStep(4);
                paymentTemp.setPaymentStatus("5");
                paymentTemp.setRemark("查询支付流水在积分平台中支付失败");
                flag = paymentSupport.updatePayment(paymentTemp);
                Logger.logInfo("修改支付记录结果: " + flag);
                return new ResultBean(ResultCode.failed);
        	}else{
        		Logger.logInfo(
        				"查询支付流水在积分平台中支付失败"
                		,"paymentId",paymentId);
                
				paymentTemp = new Payment();
                paymentTemp.setPaymentId(paymentId);
                paymentTemp.setStep(4);
                paymentTemp.setPaymentStatus("5");
                paymentTemp.setRemark("查询支付流水积分平台中并没有对应的账单信息，认为该订单已失败");
                flag = paymentSupport.updatePayment(paymentTemp);
                Logger.logInfo("修改支付记录结果: " + flag);
                return new ResultBean(ResultCode.failed);
        	}
        }else{
        	rollbackPaymentStep(payment.getPaymentId());
        	return new ResultBean(ResultCode.success,"支付中状态");
        }
	}
	
	private ResultBean verifyPointRefundResultForTask(Payment payment){
		
		String paymentId = payment.getPaymentId();
        Payment paymentTemp = null;
        PointsRes res;
        
        try {
			res = paymentSupport.queryPointPaymentState(paymentId, QueryOrderType.refund, payment.getClientId());
		} catch (Exception e) {
			rollbackPaymentStep(paymentId);
        	Logger.logError("发送主动确认积分退款结果异常",e);
            return new ResultBean(ResultCode.success,"支付中状态");
		}
        
        boolean flag;
        if(!SUCCESS_CODE.equals(res.getResultCode())){
        	rollbackPaymentStep(payment.getPaymentId());
        	return new ResultBean(ResultCode.success,"支付中状态");
        }
        
        
        ResponseQueryOrderStatusApi orderStatus = res.getOrderStatus();
        RefundLogic refundLogic = new RefundLogicImpl();
        Map<String, Boolean> noticeMap = new HashMap<String, Boolean>();
        if(StateCode.not_exists.equals(orderStatus.getQueryParam().getOrderProccessState())){
        	
        	paymentSupport.removeTimePayment(paymentId);
    		
			Logger.logInfo(
            		"查询支付流水在积分平台中并没有对应的账单信息，将认为该订单已失败，订单和支付流水以失败结果进行处理"
            		,"paymentId",paymentId);
            
			paymentTemp = new Payment();
            paymentTemp.setPaymentId(paymentId);
            paymentTemp.setStep(4);
            paymentTemp.setPaymentStatus("5");
            paymentTemp.setRemark("查询支付流水积分平台中并没有对应的账单信息，认为该订单已失败");
            flag = paymentSupport.updatePayment(paymentTemp);
            Logger.logInfo("修改支付记录结果: " + flag);
            noticeMap.put(paymentId, false);
            flag = refundLogic.updateApprovalResult(noticeMap);
			Logger.logInfo("调用退款通知接口，通知积分退款失败: " + flag);
            return new ResultBean(ResultCode.failed);
            
        }else if(StateCode.success.equals(orderStatus.getQueryParam().getOrderProccessState())){
        	
        	paymentSupport.removeTimePayment(paymentId);
        	
			Logger.logInfo(
            		"查询支付流水在积分平台中支付已成功"
            		,"paymentId",paymentId);
            
			paymentTemp = new Payment();
            paymentTemp.setPaymentId(paymentId);
            paymentTemp.setStep(4);
            paymentTemp.setPaymentStatus("4");
            paymentTemp.setRemark("查询积分平台支付结果已成功");
            flag = paymentSupport.updatePayment(paymentTemp);
            Logger.logInfo("修改支付记录结果: " + flag);
            
            noticeMap.put(paymentId, false);
            flag = refundLogic.updateApprovalResult(noticeMap);
			Logger.logInfo("调用退款通知接口，通知积分退款成功: " + flag);
            return new ResultBean(ResultCode.failed);
            
            
        }else if(StateCode.failed.equals(orderStatus.getQueryParam().getOrderProccessState())){
        	paymentSupport.removeTimePayment(paymentId);
        	
    		Logger.logInfo(
    				"查询支付流水在积分平台中支付失败"
            		,"paymentId",paymentId);
            
			paymentTemp = new Payment();
            paymentTemp.setPaymentId(paymentId);
            paymentTemp.setStep(4);
            paymentTemp.setPaymentStatus("5");
            paymentTemp.setRemark("查询支付流水积分平台中并没有对应的账单信息，认为该订单已失败");
            flag = paymentSupport.updatePayment(paymentTemp);
            Logger.logInfo("修改支付记录结果: " + flag);
            
            noticeMap.put(paymentId, false);
            flag = refundLogic.updateApprovalResult(noticeMap);
			Logger.logInfo("调用退款通知接口，通知积分退款失败: " + flag);
            return new ResultBean(ResultCode.failed);
            
        }else{
        	rollbackPaymentStep(payment.getPaymentId());
        	return new ResultBean(ResultCode.success,"支付中状态");
        }
        
	}
	
	private ResultBean verifyPointPresentResultForTask(Payment payment){
		String paymentId = payment.getPaymentId();
        Payment paymentTemp = null;
        PointsRes res;
        
        try {
			res = paymentSupport.queryPointPaymentState(paymentId, QueryOrderType.give, payment.getClientId());
		} catch (Exception e) {
			rollbackPaymentStep(paymentId);
        	Logger.logError("发送主动确认积分赠送结果异常",e);
            return new ResultBean(ResultCode.success,"支付中状态");
		}
        
        boolean flag;
        if(!SUCCESS_CODE.equals(res.getResultCode())){
        	rollbackPaymentStep(payment.getPaymentId());
        	return new ResultBean(ResultCode.success,"支付中状态");
        }
        
        
        ResponseQueryOrderStatusApi orderStatus = res.getOrderStatus();
        
        if(StateCode.not_exists.equals(orderStatus.getQueryParam().getOrderProccessState())){
        	
        	paymentSupport.removeTimePayment(paymentId);
    		
			Logger.logInfo(
            		"查询支付流水在积分平台中并没有对应的账单信息，将认为该订单已失败，订单和支付流水以失败结果进行处理"
            		,"paymentId",paymentId);
            
			paymentTemp = new Payment();
            paymentTemp.setPaymentId(paymentId);
            paymentTemp.setStep(4);
            paymentTemp.setPaymentStatus("5");
            paymentTemp.setRemark("查询支付流水积分平台中并没有对应的账单信息，认为该订单已失败");
            flag = paymentSupport.updatePayment(paymentTemp);
            Logger.logInfo("修改支付记录结果: " + flag);
            return new ResultBean(ResultCode.failed);
            
        }else if(StateCode.success.equals(orderStatus.getQueryParam().getOrderProccessState())){
        	
        	paymentSupport.removeTimePayment(paymentId);
        	
			Logger.logInfo(
            		"查询支付流水在积分平台中支付已成功"
            		,"paymentId",paymentId);
            
			paymentTemp = new Payment();
            paymentTemp.setPaymentId(paymentId);
            paymentTemp.setStep(4);
            paymentTemp.setPaymentStatus("4");
            paymentTemp.setRemark("查询积分平台支付结果已成功");
            flag = paymentSupport.updatePayment(paymentTemp);
            Logger.logInfo("修改支付记录结果: " + flag);
            return new ResultBean(ResultCode.failed);
            
            
        }else if(StateCode.failed.equals(orderStatus.getQueryParam().getOrderProccessState())){
        	paymentSupport.removeTimePayment(paymentId);
        	
    		Logger.logInfo(
    				"查询支付流水在积分平台中支付失败"
            		,"paymentId",paymentId);
            
			paymentTemp = new Payment();
            paymentTemp.setPaymentId(paymentId);
            paymentTemp.setStep(4);
            paymentTemp.setPaymentStatus("5");
            paymentTemp.setRemark("查询支付流水积分平台中并没有对应的账单信息，认为该订单已失败");
            flag = paymentSupport.updatePayment(paymentTemp);
            Logger.logInfo("修改支付记录结果: " + flag);
            return new ResultBean(ResultCode.failed);
            
        }else{
        	rollbackPaymentStep(payment.getPaymentId());
        	return new ResultBean(ResultCode.success,"支付中状态");
        }
		
	}
	
	private ResultBean verifyUserPaymentResultForTask(Payment payment){
	    
		String paymentId = payment.getPaymentId();

        Payment paymentTemp = null;
        boolean flag = false;
        
        OpenApiRes res;
        try {
           res = paymentSupport.queryOpenAPIPaymentState(
            		paymentId,
            		payment.getCreateTime(),
            		OpenApiCommand.ORDER_TYPE_TRANS, 
            		OPENAPI_ORDER_TYPE_CLIENT_PC, 
            		payment.getClientId());
        
        } catch (Exception e) {
        	rollbackPaymentStep(paymentId);
        	Logger.logError("发送主动确认OpenAPI支付结果异常",e);
            return new ResultBean(ResultCode.success,"支付中状态");
        }
        
        if(!SUCCESS_CODE.equals(res.getResultCode())){
        	
        	if("0100".equals(res.getResultCode())){ 
        		
        		//由于异常情况，openapi没有收到实际订单支付信息，社区银行单应该认为这笔支付无效，如果含有积分支付，则需要自动退还积分操作
                ResultBean resultBean = orderLogic.getOrderByOrderId(payment.getClientId(),payment.getOrderId());
                if(!isSuccess(resultBean)){
                	Logger.logError("查询订单信息失败","orderId",payment.getOrderId());
                    return new ResultBean(ResultCode.failed); //查询订单失败
                }
                OrderMongo order = (OrderMongo)resultBean.getData();
                
                Logger.logInfo(
                		"查询支付流水在OpenAPI中并没有对应的账单信息，将认为该订单已失败，订单和支付流水将已失败结果进行处理"
                		,"paymentId",paymentId);
        		String resultStr = doPayResressFailed(order).toString();
        		Logger.logInfo("订单支付失败逻辑处理完毕 ","resultStr",resultStr);
        		 
				paymentSupport.removeTimePayment(paymentId);
                
				paymentTemp = new Payment();
                paymentTemp.setPaymentId(paymentId);
                paymentTemp.setStep(4);
                paymentTemp.setPaymentStatus("5");
                paymentTemp.setRemark("查询支付流水在OpenAPI中并没有对应的账单信息，认为该订单已失败");
                flag = paymentSupport.updatePayment(paymentTemp);
                Logger.logInfo("修改支付记录结果: " + flag);
                
                //-------------------------- 判断是否需要退积分
                
                //判断订单支付类型
                if(BaseSubassembly.isCombinePayment(order.getPaymentMethod())){
                    
                	//如果是积分&现金支付则需要退积分
                    List<Payment> payments = paymentSupport.findEnablePaymentsOfUserPayByOrderId(payment.getOrderId());
                    
                    for (Payment p : payments) {
//                        if((1 == p.getPaymentType() || 3 == p.getPaymentType()) && "2".equals(p.getPaymentReason()) 
//                        		&& "4".equals(p.getPaymentStatus()) && "0".equals(p.getAutoRefund()) && p.getIsEnable()){
//                                //该条为积分支付-需要退还积分  条件: 积分支付,用户支付流水,支付成功
//                        	
//                        	Logger.logInfo("该笔支付订单曾含有积分支付，需要进行自动退还积分操作","payment",p);
//                            resultBean = paymentSupport.autoRefundPoints(p);
//                            
//                            if(!ResultCode.success.getCode().equals(resultBean.getCode())){
//                            	Logger.logError("自动退还积分失败","paymentId",p.getPaymentId());
//                            }
//                            return resultBean;
//                        }else{
//                        	Logger.logInfo("未能退还积分，该支付信息已发生过自动退积分或未成功支付");
//                        }
                    }
                }
                return new ResultBean(ResultCode.failed,"订单支付失败");
        	}
        	
        	rollbackPaymentStep(payment.getPaymentId());
        	return new ResultBean(ResultCode.success,"支付中状态");
        }
        
        //查询操作成功
        Logger.logInfo("主动确认支付状态成功");
        List<com.froad.thirdparty.dto.response.openapi.Order> orders = res.getOrders();
        
        com.froad.thirdparty.dto.response.openapi.Order orderOpenApi = orders.get(0);
        
        String stateCode= orderOpenApi.getStateCode();
        
        OrderMongo order = null;
        
        if("0".equals(stateCode) || "1".equals(stateCode)){//有用的操作
        	
            OrderLogic orderLogic = new OrderLogicImpl();
            
            ResultBean resultBean = orderLogic.getOrderByOrderId(payment.getClientId(),payment.getOrderId());
            if(!ResultCode.success.getCode().equals(resultBean.getCode())){
                return new ResultBean(ResultCode.failed); //查询订单失败
            }
            order = (OrderMongo)resultBean.getData();
        }
        
        if ("0".equals(orderOpenApi.getStateCode())) {
        	
        	paymentSupport.removeTimePayment(paymentId);
        	
        	Logger.logInfo("主动查询支付信息结果已成功,支付信息做相应的修改","paymentId",paymentId);
        	
            paymentTemp = new Payment();
            paymentTemp.setPaymentId(paymentId);
            paymentTemp.setStep(4);
            paymentTemp.setPaymentStatus("4"); //支付成功
            paymentTemp.setRemark("主动查询支付结果已成功");
            
            flag = paymentSupport.updatePayment(paymentTemp);
            Logger.logInfo("修改支付记录结果: " + flag);
            
            Logger.logInfo("准备调用支付成功附属逻辑");
            ResultBean resultBean = doPayRedress(order);
            Logger.logInfo("调用支付成功附属逻辑完成","resultBean",resultBean);
           
            if(!isSuccess(resultBean)){
            	Logger.logError("调用支付成功附属逻辑完成,处理失败");
                return new ResultBean(ResultCode.failed,resultBean.getMsg());
            }
            return new ResultBean(ResultCode.success);
        }else if("1".equals(orderOpenApi.getStateCode())){
        	
        	paymentSupport.removeTimePayment(paymentId);
        	
        	Logger.logInfo("主动查询支付信息结果已失败,支付信息做相应的修改","paymentId",paymentId);
        	
        	String resultStr = doPayResressFailed(order).toString();
        	Logger.logInfo("订单支付失败处理后续逻辑: " + resultStr);
        	
            paymentTemp = new Payment();
            paymentTemp.setPaymentId(paymentId);
            paymentTemp.setStep(4);
            paymentTemp.setPaymentStatus("5"); //支付失败
            paymentTemp.setRemark("主动查询支付结果已失败");
            flag = paymentSupport.updatePayment(paymentTemp);
            Logger.logInfo("修改支付记录结果: " + flag);
            
            //-------------------------- 判断是否需要退积分
            ResultBean resultBean;
            //判断订单支付类型
            if(BaseSubassembly.isCombinePayment(order.getPaymentMethod())){
                //如果是积分&现金支付则需要退积分
                List<Payment> payments = paymentSupport.findEnablePaymentsOfUserPayByOrderId(payment.getOrderId());
                for (Payment p : payments) {
//                    if((1 == p.getPaymentType() || 3 == p.getPaymentType()) && "2".equals(p.getPaymentReason()) 
//                    		&& "4".equals(p.getPaymentStatus()) && p.getIsEnable() &&"0".equals(p.getAutoRefund())){
//                        //该条为积分支付-需要退还积分  条件: 积分支付,用户支付流水,支付成功
//                    	Logger.logInfo("该笔订单含有积分支付记录，将进行自动退积分操作");
//                        resultBean = paymentSupport.autoRefundPoints(p);
//                        
//                        Logger.logInfo("自动退积分处理完成","resultBean",resultBean);
//                        
//                        if(!isSuccess(resultBean)){
//                        	Logger.logError("自动退还积分失败","积分paymentId",p.getPaymentId());
//                        }
//                        return resultBean;
//                    }else{
//                    	Logger.logInfo("未能退还积分，该支付信息已发生过自动退积分或未成功支付");
//                    }
                }
            }
            
            return new ResultBean(ResultCode.failed);
        }else{
        	rollbackPaymentStep(paymentId);
            
        	Logger.logInfo("主动查询用户支付流水结果非指明成功失败，按照支付中处理，不做任何逻辑变化");
        	
            return new ResultBean(ResultCode.success,"支付中状态");
        }
	}

	private ResultBean verifyRefundResultForTask(Payment payment) {
	    String paymentId = payment.getPaymentId();
	    
		RefundLogic refundLogic = new RefundLogicImpl();
		Payment paymentTemp = null;
		boolean flag = false;
		try {
			OpenApiRes res = paymentSupport.queryOpenAPIPaymentState(
					paymentId,payment.getCreateTime(),OpenApiCommand.ORDER_TYPE_REFUND, 
					OPENAPI_ORDER_TYPE_CLIENT_PC, payment.getClientId());
			Map<String, Boolean> noticeMap = new HashMap<String, Boolean>();
			if(!SUCCESS_CODE.equals(res.getResultCode())){
				if("0100".equals(res.getResultCode())){
					
					paymentSupport.removeTimePayment(paymentId);
					
					Logger.logInfo("查询支付流水在OpenAPI中并没有对应的账单信息，将认为该订单已失败，订单和支付流水将已失败结果进行处理"
                    		,"paymentId",paymentId);
					paymentTemp = new Payment();
					paymentTemp.setPaymentId(paymentId);
					paymentTemp.setStep(4);
					paymentTemp.setPaymentStatus("5"); 
					paymentTemp.setRemark("查询支付流水在OpenAPI中并没有对应的账单信息，认为该订单已失败");
	                flag = paymentSupport.updatePayment(paymentTemp);
	                Logger.logInfo("修改退款记录结果: " + flag);
					
	                noticeMap.put(paymentId, false);
//					flag = refundLogic.updateApprovalResult(paymentId, false);
//					PaymentSupportUtils.logInfo("调用退款通知接口通知现金退款已失败","flag",flag);
					
					paymentTemp = null;
					List<Payment> payments = paymentSupport.findAllEnablePaymentsByOrderId(payment.getOrderId());
					for (Payment p : payments) {
						if(1 == p.getPaymentType() && 1 == p.getStep() && "1".equals(p.getPaymentReason())
								&& "1".equals(p.getPaymentStatus())){ //退款积分创建记录
							paymentTemp = p;
							break;
						}
					}
					if(paymentTemp != null){
						Logger.logInfo("该笔退款包含积分，将更改积分退款结果为失败");
						String pointPaymentId = paymentTemp.getPaymentId();
						paymentTemp = new Payment();
						paymentTemp.setPaymentId(pointPaymentId);
						paymentTemp.setStep(4);
						paymentTemp.setPaymentStatus("5");
						paymentTemp.setRemark("现金退款失败，此次退积分流水未进行退积分操作");
						flag = paymentSupport.updatePayment(paymentTemp);
						Logger.logInfo("修改退款记录结果: " + flag);
						noticeMap.put(pointPaymentId, false);
					}
					
					flag = refundLogic.updateApprovalResult(noticeMap);
					Logger.logInfo("调用退款通知接口，通知积分退款失败现金退款失败: " + flag);
					
					return new ResultBean(ResultCode.failed);
				}
				
				rollbackPaymentStep(paymentId);
				return new ResultBean(ResultCode.success,"支付中状态");
			}
			
			List<com.froad.thirdparty.dto.response.openapi.Order> orders = res.getOrders();
			com.froad.thirdparty.dto.response.openapi.Order orderOpenApi = orders.get(0);
			if ("0".equals(orderOpenApi.getStateCode())) {
				
				paymentSupport.removeTimePayment(paymentId);
				
				Logger.logInfo("主动查询退款信息结果已成功,退款流水信息做相应的修改","paymentId",paymentId);
				paymentTemp = new Payment();
				paymentTemp.setPaymentId(paymentId);
				paymentTemp.setStep(4);
				paymentTemp.setPaymentStatus("4"); //退款成功
				paymentTemp.setRemark("主动查询退款结果已成功");
				flag = paymentSupport.updatePayment(paymentTemp);
				Logger.logInfo("修改退款记录结果: " + flag);
				
				noticeMap.put(paymentId, true);
				
				//-----判断是否有连带的退积分操作
				paymentTemp = null;
				List<Payment> payments = paymentSupport.findAllEnablePaymentsByOrderId(payment.getOrderId());
				for (Payment p : payments) {
					if((1 == p.getPaymentType() || 3 == p.getPaymentType()) && 1 == p.getStep() && "1".equals(p.getPaymentReason())
							&& "1".equals(p.getPaymentStatus())){ //退款积分创建记录
						paymentTemp = p;
						break;
					}
				}
				if(paymentTemp == null){
					flag = refundLogic.updateApprovalResult(noticeMap);
					Logger.logInfo("调用退款通知接口，通知现金已成功退款: " + flag);
					return new ResultBean(ResultCode.success);
				}else{
					Logger.logInfo("该笔退款包含积分，将执行退积分操作");
					ResultBean resultBean = paymentSupport.userGoOnRefundPoints(paymentTemp);
					Logger.logInfo("退积分操作完成","resultBean",resultBean);
					if(!isSuccess(resultBean)){
						noticeMap.put(paymentTemp.getPaymentId(), false);
						flag = refundLogic.updateApprovalResult(noticeMap);
						Logger.logInfo("调用退款通知接口，通知现金已成功退款，积分退款失败: " + flag);
						return new ResultBean(ResultCode.failed,"现金已成功退款，积分退款失败"); 
					}
					noticeMap.put(paymentTemp.getPaymentId(), true);
					flag = refundLogic.updateApprovalResult(noticeMap);
					Logger.logInfo("调用退款通知接口，通知积分退款成功现金退款成功: " + flag);
					return new ResultBean(ResultCode.success);
				}
			}else if("1".equals(orderOpenApi.getStateCode())){
				
				paymentSupport.removeTimePayment(paymentId);
				
				Logger.logInfo("主动查询退款信息结果已失败,退款流水信息做相应的修改","paymentId",paymentId);
				paymentTemp = new Payment();
				paymentTemp.setPaymentId(paymentId);
				paymentTemp.setStep(4);
				paymentTemp.setPaymentStatus("5"); 
				paymentTemp.setRemark("主动查询退款结果，退款失败");
                flag = paymentSupport.updatePayment(paymentTemp);
                Logger.logInfo("修改退款记录结果: " + flag);
				
                noticeMap.put(paymentId, false);
				
				//-----判断是否有连带的退积分操作
				paymentTemp = null;
				List<Payment> payments = paymentSupport.findAllEnablePaymentsByOrderId(payment.getOrderId());
				for (Payment p : payments) {
					if((1 == p.getPaymentType() || 3 == p.getPaymentType()) && 1 == p.getStep() && "1".equals(p.getPaymentReason())
							&& "1".equals(p.getPaymentStatus())){ //退款积分创建记录
						paymentTemp = p;
						break;
					}
				}
				if(paymentTemp != null){
					Logger.logInfo("该笔退款包含积分，将更改积分退款结果为失败");
					String pointPaymentId = paymentTemp.getPaymentId();
					paymentTemp = new Payment();
					paymentTemp.setPaymentId(pointPaymentId);
					paymentTemp.setStep(4);
					paymentTemp.setPaymentStatus("5");
					paymentTemp.setRemark("现金退款失败，此次退积分流水未进行退积分操作");
					flag = paymentSupport.updatePayment(paymentTemp);
					Logger.logInfo("修改退款记录结果: " + flag);
					noticeMap.put(pointPaymentId, false);
					
				}
				flag = refundLogic.updateApprovalResult(noticeMap);
				Logger.logInfo("调用退款通知接口，通知积分退款失败现金退款失败: " + flag);
				return new ResultBean(ResultCode.failed);
			}else{
				rollbackPaymentStep(paymentId);
				
				Logger.logInfo("主动查询退款信息结果非指明成功失败，按照退款中处理，不做任何逻辑变化");
				//认为是支付中的状态，不做处理
				return new ResultBean(ResultCode.success,"支付中状态");
			}
		
		} catch (Exception e) {
			rollbackPaymentStep(paymentId);
			Logger.logError("发送主动确认OpenAPI退款结果异常",e);
			return new ResultBean(ResultCode.success,"支付中状态");
		}
	}

	private ResultBean verifySettleResultForTask(Payment payment) {
		String paymentId = payment.getPaymentId();
		Payment paymentTemp = null;
		boolean flag = false;
		ResultBean resultBean;
		try {
			OpenApiRes res = paymentSupport.queryOpenAPIPaymentState(
					paymentId,payment.getCreateTime(),OpenApiCommand.ORDER_TYPE_TRANS, 
					OPENAPI_ORDER_TYPE_CLIENT_PC, payment.getClientId());
			if(!"0000".equals(res.getResultCode())){
				if("0100".equals(res.getResultCode())){ 
					Logger.logInfo(
                    		"查询支付流水在OpenAPI中并没有对应的账单信息，将认为该订单已失败，订单和支付流水将已失败结果进行处理"
                    		,"paymentId",paymentId);
					paymentSupport.removeTimePayment(paymentId);
					
					paymentTemp = new Payment();
					paymentTemp.setPaymentId(paymentId);
					paymentTemp.setStep(4);
					paymentTemp.setPaymentStatus("5");
					paymentTemp.setRemark("查询支付流水在OpenAPI中并没有对应的账单信息，认为该订单已失败");
					flag = paymentSupport.updatePayment(paymentTemp);
					Logger.logInfo("修改支付记录结果: " + flag);
					
					resultBean = new SettlementLogicImpl().notitySettlement(paymentId, SettlementStatus.settlementfailed, null);
					Logger.logInfo("结算流水结算失败，调用结算接口通知结算结果","resultBean",resultBean);
					return new ResultBean(ResultCode.failed);
				}
				
				rollbackPaymentStep(paymentId);
				return new ResultBean(ResultCode.success,"支付中状态");
			}
			
			List<com.froad.thirdparty.dto.response.openapi.Order> orders = res.getOrders();
			com.froad.thirdparty.dto.response.openapi.Order orderOpenApi = orders.get(0);
			if ("0".equals(orderOpenApi.getStateCode())) {
				
				paymentSupport.removeTimePayment(paymentId);
				
				Logger.logInfo("主动查询结算结果已成功,支付信息做相应的修改","paymentId",paymentId);
				paymentTemp = new Payment();
				paymentTemp.setPaymentId(paymentId);
				paymentTemp.setStep(4);
				paymentTemp.setPaymentStatus("4"); //支付成功
				paymentTemp.setRemark("主动查询结算结果已成功");
				flag = paymentSupport.updatePayment(paymentTemp);
				Logger.logInfo("修改结算记录结果: " + flag);
				resultBean = new SettlementLogicImpl().notitySettlement(paymentId, SettlementStatus.settlementsucc, null);
				Logger.logInfo("结算流水结算成功，调用结算接口通知结算结果","resultBean",resultBean);
				return new ResultBean(ResultCode.success);
			}else if("1".equals(orderOpenApi.getStateCode())){
				paymentSupport.removeTimePayment(paymentId);
				
				Logger.logInfo("主动查询结算结果已失败,支付信息做相应的修改","paymentId",paymentId);
				paymentTemp = new Payment();
				paymentTemp.setPaymentId(paymentId);
				paymentTemp.setStep(4);
				paymentTemp.setPaymentStatus("5"); //支付失败
				paymentTemp.setRemark("主动查询结算结果已失败");
				flag = paymentSupport.updatePayment(paymentTemp);
				Logger.logInfo("修改支付记录结果: " + flag);
				resultBean = new SettlementLogicImpl().notitySettlement(paymentId, SettlementStatus.settlementfailed, null);
				Logger.logInfo("结算流水结算失败，调用结算接口通知结算结果","resultBean",resultBean);
				return new ResultBean(ResultCode.failed);
			}else{
				rollbackPaymentStep(paymentId);
				//认为是支付中的状态，不做处理
				return new ResultBean(ResultCode.success,"支付中状态");
			}
		
		} catch (Exception e) {
			rollbackPaymentStep(paymentId);
			
            //认为是支付中的状态，不做处理
			Logger.logError("发送主动确认OpenAPI结算结果异常",e);
			return new ResultBean(ResultCode.success,"支付中状态");
		}
	}

    private boolean isPaymentOperationEnd(Payment payment) {
        return payment.getStep() == 4;
    }
	
	@Override
	public ResultBean refundMemberPaymentData(String orderId,Double rufundPresentPoint, Double rufundPayPoint,Double refundPayCash,long memberCode) {
		
		if(StringUtils.isEmpty(orderId)){
			return new ResultBean(ResultCode.failed,"orderId不能为空");
		}
		
		Logger.logInfo("收到退款信息", new String[]{"orderId","rufundPresentPoint","rufundPayPoint","refundPayCash","memberCode"},
				new Object[]{orderId,rufundPresentPoint,rufundPayPoint,refundPayCash,memberCode});
		ResultBean resultBean = null;
		List<Payment> payments = paymentSupport.findAllEnablePaymentsByOrderId(orderId);
		if(payments == null || payments.size() == 0){
			Logger.logError("发起退款orderId： " + orderId + "没有任何可用的支付流水");
			return new ResultBean(ResultCode.failed , "发起退款orderId： "+orderId+"没有任何可用的支付流水");
		}
		
		UserSpecDto specDto = paymentSupport.queryByMemberCode(memberCode);
		Payment paymentTemp = null;
		
		RefundTempBean refundTempBean = new RefundTempBean();
		RefundTempBean resultRefund = new RefundTempBean();
		
		if(rufundPresentPoint != null && rufundPresentPoint > 0){ //需要扣除已赠送的积分
			for (Payment payment : payments) {
				if("3".equals(payment.getPaymentReason())){  //找出赠送积分流水
					paymentTemp = payment;
					break;
				}
			}
			if(paymentTemp == null){
				return new ResultBean(ResultCode.failed,"该订单无赠送积分记录");
			}

			Logger.logInfo("开始进行扣除赠送积分操作","paymentId",paymentTemp.getPaymentId());
			
			PaymentPoTemp refuntPayment = new PaymentPoTemp(
					paymentTemp.getClientId(), orderId, 1, rufundPresentPoint
					, null, 0, null, "4");
			resultBean = paymentSupport.refundPresentFroadPoints(refuntPayment,specDto.getLoginID());
			
			Logger.logInfo("扣除赠送积分操作完成","resultBean",resultBean);
			
			resultRefund =  resultBean.getData() != null ? (RefundTempBean) resultBean.getData() : new RefundTempBean();
			
			if(!isSuccess(resultBean)){
				if(ResultCode.payment_create_payment_faild.getCode().equals(resultBean.getCode())){
					return new ResultBean(ResultCode.failed);
				}
			}
			
			refundTempBean.setPresentPointPaymentId(resultRefund.getPresentPointPaymentId());
			refundTempBean.setRefundPresentPointState(resultRefund.getRefundPresentPointState());
			
		}
		
		paymentTemp = null;
		
		if(refundPayCash != null && refundPayCash > 0){
			
			Logger.logInfo("开始进行退现金操作");
			
			for (Payment p : payments) {
				if("2".equals(p.getPaymentReason()) && 2 == p.getPaymentType()
						&& "4".equals(p.getPaymentStatus())  && p.getIsEnable()){  //找出用户支付现金流水
					paymentTemp = p;
					break;
				}
			}
			
			if(paymentTemp == null){
				return new ResultBean(ResultCode.failed,"该订单无现金消费记录");
			}
			
			PaymentPoTemp refuntCashPayment = new PaymentPoTemp(
					paymentTemp.getClientId(), orderId, 2, refundPayCash, 
					paymentTemp.getPaymentOrgNo(),paymentTemp.getPaymentTypeDetails(),
					null, "1",paymentTemp.getPaymentId());
			
			refuntCashPayment.setBillNo(paymentTemp.getBillNo());
			refuntCashPayment.setFromUserName(paymentTemp.getFromUserName());
			
			//原始用户总支付现金总值
			double paymentCashHis = Arith.div(paymentTemp.getPaymentValue(),1000);
			
			List<Payment> refundPayCashPayment = new ArrayList<Payment>();
			for (Payment payment : payments) { //找出历史退款成功的现金支付流水
				if("1".equals(payment.getPaymentReason()) && 2 == payment.getPaymentType() && "4".equals(payment.getPaymentStatus())){
					refundPayCashPayment.add(payment);
				}
			}
			
			String refundTypeCode = "01"; //全额退款
			if(refundPayCashPayment.size() != 0){
				refundTypeCode = "02"; //部分退款
			}else{
				if(refundPayCash != Arith.div(paymentTemp.getPaymentValue(),1000)){
					refundTypeCode = "02";
				}
			}
			
			//查看是否有退积分操作
			if(rufundPayPoint != null && rufundPayPoint > 0){
				paymentTemp = null;
				//包含退积分记录
				for (Payment p : payments) { //找出已成功支付的积分流水
					if("2".equals(p.getPaymentReason()) && (1 == p.getPaymentType() || 3 == p.getPaymentType())
							&& "4".equals(p.getPaymentStatus())  && p.getIsEnable()){  //找出用户支付积分流水
						paymentTemp = p;
						break;
					}
				}
				
				if(paymentTemp == null){
					return new ResultBean(ResultCode.failed,"该订单无积分消费记录");
				}
				
				PaymentPoTemp refuntPointPayment = new PaymentPoTemp(
						paymentTemp.getClientId(), orderId, paymentTemp.getPaymentType(), rufundPayPoint,
						paymentTemp.getPaymentOrgNo(),paymentTemp.getPaymentTypeDetails(),
						paymentTemp.getPointRate(), "1",paymentTemp.getBillNo(),paymentTemp.getFromAccountNo(),
						paymentTemp.getFromUserName());
				
				resultBean = paymentSupport.refundCashaAndSavePointInfo(refuntCashPayment, refuntPointPayment, paymentCashHis, refundTypeCode);
				resultRefund =  resultBean.getData() != null ? (RefundTempBean) resultBean.getData() : new RefundTempBean();

				if(!isSuccess(resultBean)){
					if(ResultCode.payment_create_payment_faild.getCode().equals(resultBean.getCode())){
						//流水生成失败 FIXME 
						return new ResultBean(ResultCode.failed);
					}
				}
				refundTempBean.setPaymentCashPaymentId(resultRefund.getPaymentCashPaymentId());
				refundTempBean.setPaymentPointPaymentId(resultRefund.getPaymentPointPaymentId());
				refundTempBean.setRefundCashState(resultRefund.getRefundCashState());
				refundTempBean.setRefundPointState(resultRefund.getRefundPointState());
				
			}else{
				resultBean = paymentSupport.refundCashaAndSavePointInfo(refuntCashPayment, null, paymentCashHis, refundTypeCode);
				resultRefund =  resultBean.getData() != null ? (RefundTempBean) resultBean.getData() : new RefundTempBean();

				refundTempBean.setPaymentCashPaymentId(resultRefund.getPaymentCashPaymentId());
				refundTempBean.setRefundCashState(resultRefund.getRefundCashState());
				
				if(!isSuccess(resultBean)){
					if(ResultCode.payment_create_payment_faild.getCode().equals(resultBean.getCode())){
						//流水生成失败 FIXME 
						return new ResultBean(ResultCode.failed);
					}
				}
			}
		}else if (rufundPayPoint != null && rufundPayPoint > 0){
			
			paymentTemp = null;
			//包含退积分记录
			for (Payment p : payments) { //找出已成功支付的积分流水
				if("2".equals(p.getPaymentReason()) && (1 == p.getPaymentType() || 3 == p.getPaymentType()) 
						&& "4".equals(p.getPaymentStatus()) && p.getIsEnable()){  //找出用户支付积分流水
					paymentTemp = p;
					break;
				}
			}
			if(paymentTemp == null){
				return new ResultBean(ResultCode.failed,"该订单无积分消费记录");
			}
			
			PaymentPoTemp refuntPointPayment = new PaymentPoTemp(
					paymentTemp.getClientId(), orderId, paymentTemp.getPaymentType(), rufundPayPoint,
					paymentTemp.getPaymentOrgNo(),paymentTemp.getPaymentTypeDetails(),
					paymentTemp.getPointRate(), "1",paymentTemp.getBillNo(),paymentTemp.getFromAccountNo(),
					paymentTemp.getFromUserName());
			
			resultBean = paymentSupport.refundPointOfUserPay(refuntPointPayment);
			resultRefund =  resultBean.getData() != null ? (RefundTempBean) resultBean.getData() : new RefundTempBean();
			
			refundTempBean.setPaymentPointPaymentId(resultRefund.getPaymentPointPaymentId());
			refundTempBean.setRefundPointState(resultRefund.getRefundPointState());
			
			if(!isSuccess(resultBean)){
				
				if(ResultCode.payment_create_payment_faild.getCode().equals(resultBean.getCode())){
					//流水生成失败 FIXME 
					return new ResultBean(ResultCode.failed);
				}
			}
			
		}
		
		ResultCode[] resultCodes = ResultCode.values();
		for (ResultCode resultCode : resultCodes) {
			if(resultCode.getCode().equals(resultBean.getCode())){
				return new ResultBean(resultCode,refundTempBean);
			}
		}
		
		return new ResultBean(ResultCode.failed,refundTempBean);
	}
	


	@Override
	public ResultBean settleMerchantPaymentData(String orderId, double sellteCash,String clientId,String merchantId,String merchantOutletId) {
		//数据校验
		PaymentPoTemp paymentPoTemp = new PaymentPoTemp(
				clientId, orderId, 2, sellteCash,
				null,
				55,
				null, "0",null,"方付通银行对公户",
				null,merchantId + "|" + merchantOutletId);
		ResultBean resultBean = paymentSupport.transferCashFromFroadToMerchant(paymentPoTemp,merchantId,merchantOutletId);
		if(!EsyT.isResultBeanSuccess(resultBean)){
			EsyT.sendPoint(MonitorPointEnum.order_pay_settle_failed);
		}
		return resultBean;
	}
	

    @Override
    public ResultBean completeOrderPaymentInfo(PayTempBean tempBean, OrderMongo order) {
    	
    	//需要更新的订单bean
    	OrderMongo updateOrder = new OrderMongo();
    	updateOrder.setOrderId(order.getOrderId());
    	updateOrder.setClientId(order.getClientId());
    	
    	
    	PaymentMethod paymentMethod = tempBean.getPayType();
    	
    	String payTypeCode = paymentMethod.getCode();
    	updateOrder.setPaymentMethod(payTypeCode);//设置订单支付类型
    	
    	updateOrder.setPaymentTime(new Date().getTime()); //设置支付时间
    	
    	
    	//--------------------------补全支付参数------------------------
    	switch (paymentMethod) {
		case froadPoints: //方付通积分支付
			updateOrder.setFftPoints(Arith.mul(tempBean.getPointAmount(), 1000)); //设置方付通积分值
			updateOrder.setPointRate(Integer.toString(tempBean.getPointRatio()));
			break;
			
		case bankPoints: //银行积分支付
			updateOrder.setBankPoints(Arith.mul(tempBean.getPointAmount(), 1000)); //设置银行积分值
			updateOrder.setPointRate(Integer.toString(tempBean.getPointRatio()));
			break;
			
		case cash: //现金
			updateOrder.setRealPrice(Arith.mul(tempBean.getCashAmount(), 1000)); //设置方付通积分值
			break;
			
		case froadPointsAndCash: //方付通积分&现金
			updateOrder.setFftPoints(Arith.mul(tempBean.getPointAmount(), 1000)); //设置方付通积分值
			updateOrder.setPointRate(Integer.toString(tempBean.getPointRatio()));
			updateOrder.setRealPrice(Arith.mul(tempBean.getCashAmount(), 1000)); 
			break;
			
		case bankPointsAndCash: //方付通积分&现金
			updateOrder.setBankPoints(Arith.mul(tempBean.getPointAmount(), 1000)); //设置银行积分值
			updateOrder.setPointRate(Integer.toString(tempBean.getPointRatio()));
			updateOrder.setRealPrice(Arith.mul(tempBean.getCashAmount(), 1000)); 
			break;
			
		default:
			Logger.logError("指定的支付方式不存在","paymentMethod",paymentMethod.name());
			break;
		}
    	//--------------------------补全支付参数------------------------
    	
    	long startTime = System.currentTimeMillis();
    	ResultBean resultBean = orderLogic.updateOrderForPay(updateOrder);
    	Logger.logInfo("补全订单信息 调用 updateOrderForPay 接口------------------耗时: " + (System.currentTimeMillis() - startTime));
    	return resultBean;
    }

    private List<Store> getStoreList(SubOrderMongo order){
    	
    	String merchantId = order.getMerchantId();
    	if(!(StringUtils.equals(order.getType(), SubOrderType.special_merchant.getCode()) || StringUtils.equals(order.getType(), SubOrderType.group_merchant.getCode()))){
    		Org org = new CommonLogicImpl().getOrgByOrgCode(merchantId, order.getClientId());
    		merchantId = org.getMerchantId();
    	}
    	
        List<Store> storeList = new ArrayList<Store>();
        
        Store store = null;
        for(ProductMongo product: order.getProducts()){
            store = new Store();
            store.setClientId(order.getClientId());
            store.setMerchantId(merchantId);
            store.setProductId(product.getProductId());
            store.setReduceStore(product.getQuantity());
            store.setStore(product.getQuantity());
            storeList.add(store);
        }
        
        return storeList;
    }
	
    private boolean isSuccess(ResultBean result){
        return result != null && result.getCode() != null&&ResultCode.success.getCode().equals(result.getCode());
    }
    
    @Override
    public ResultBean checkAndOperateStore(OrderMongo order,boolean freeStore) {
    	long startTime = System.currentTimeMillis();
    	
    	if(!freeStore){
    		if(!OrderState.RETURNED.getCode().equals(order.getState())){
                return new ResultBean(ResultCode.success);
            }
    	}
                
        ResultBean querySubOrderReuslt = orderLogic.getSubOrderListByOrderId(order.getClientId(),order.getOrderId());
        @SuppressWarnings("unchecked")
		List<SubOrderMongo> subOrderMongoList = (List<SubOrderMongo>) querySubOrderReuslt.getData();
        List<Store> storeList = new ArrayList<Store>();
        if (isSuccess(querySubOrderReuslt)) {
            for (SubOrderMongo sOrder : subOrderMongoList) {
            	
                storeList.addAll(getStoreList(sOrder));
            }
        } else {
        	Logger.logError("查询库存失败: " + querySubOrderReuslt.getMsg());
            return new ResultBean(ResultCode.failed, querySubOrderReuslt.getMsg());
        }
        
		ResultBean storeResult;
		if (freeStore) {
			storeResult = orderLogic.increaseStore(storeList);
			if (!isSuccess(storeResult)) {
				Logger.logError("释放库存失败: " + storeResult.getMsg());
				return new ResultBean(ResultCode.failed, storeResult.getMsg());
			}
			storeResult = orderLogic.updateProductStore(storeList);
		} else {
			storeResult = orderLogic.reduceStore(storeList);
			if (!isSuccess(storeResult)) {
				Logger.logError("主动扣除库存失败:" + storeResult.getMsg());
				return new ResultBean(ResultCode.failed, "商品库存不足");
			}
			storeResult = orderLogic.updateProductStore(storeList);
		}
		
		if (!isSuccess(storeResult)) {
			Logger.logError("同步库存到mysql失败:" + storeResult.getMsg());
			return new ResultBean(ResultCode.failed, storeResult.getMsg());
		}
        
        Logger.logInfo("库存处理耗时: " + (System.currentTimeMillis() - startTime));
        return new ResultBean(ResultCode.success,"操作库存成功");
    }

    
	@Override
	public ResultBean doPaySeckillingOrderValidationAll(OrderMongo order,PayTempBean tempBean) {
		
		ValidationOf validationOf = new ValidationOf();		
		
		//-------支付方式校验
	    ResultBean resultBean = validationOf.validationPayMethodAndFoilNum(tempBean); //检验支付方式，和支付机构号
		if(!ResultCode.success.getCode().equals(resultBean.getCode())){
			return resultBean;
		}
		
		Integer totalPrice = order.getTotalPrice();
		if(totalPrice == null){
			Logger.logInfo("预支付订单没有总价参数","orderId",tempBean.getOrderId());
			return new ResultBean(ResultCode.payment_order_error,"订单总价值参数缺失");
		}
		
		//-------校验订单信息
		resultBean = validationOf.validationOrderPrice(order, tempBean);
		if(!ResultCode.success.getCode().equals(resultBean.getCode())){
			return resultBean;
		}
		
		
    	
    	PaymentMethod paymentMethod = tempBean.getPayType();
    	String payTypeCode = paymentMethod.getCode();
    	order.setPaymentMethod(payTypeCode);//设置订单支付类型
    	order.setPaymentTime(new Date().getTime()); //设置支付时间
    	
    	//--------------------------补全支付参数------------------------
    	switch (paymentMethod) {
		case froadPoints: //方付通积分支付
			order.setFftPoints(Arith.mul(tempBean.getPointAmount(), 1000)); //设置方付通积分值
			order.setPointRate(Integer.toString(tempBean.getPointRatio()));
			break;
			
		case bankPoints: //银行积分支付
			order.setBankPoints(Arith.mul(tempBean.getPointAmount(), 1000)); //设置银行积分值
			order.setPointRate(Integer.toString(tempBean.getPointRatio()));
			break;
			
		case cash: //现金
			order.setRealPrice(Arith.mul(tempBean.getCashAmount(), 1000)); //设置方付通积分值
			break;
			
		case froadPointsAndCash: //方付通积分&现金
			order.setFftPoints(Arith.mul(tempBean.getPointAmount(), 1000)); //设置方付通积分值
			order.setPointRate(Integer.toString(tempBean.getPointRatio()));
			order.setRealPrice(Arith.mul(tempBean.getCashAmount(), 1000)); 
			break;
			
		case bankPointsAndCash: //方付通积分&现金
			order.setBankPoints(Arith.mul(tempBean.getPointAmount(), 1000)); //设置银行积分值
			order.setPointRate(Integer.toString(tempBean.getPointRatio()));
			order.setRealPrice(Arith.mul(tempBean.getCashAmount(), 1000)); 
			break;
			
		default:
			Logger.logError("指定的支付方式不存在","paymentMethod",paymentMethod.name());
			break;
		}
		
		return new ResultBean(ResultCode.success,order);
	}

	
	/**
     * 秒杀支付逻辑
    * <p>Function: Seckilling</p>
    * <p>Description: </p>
    * @author zhaoxy@thankjava.com
    * @date 2015-5-4 上午10:52:13
    * @version 1.0
     */
	@SuppressWarnings("unchecked")
	@Override
	public ResultBean doPayOrderTypeSeckilling(OrderMongo order,PayTempBean tempBean) {
		ResultBean resultBean = savePaymentAndDisableOldPayPayment(tempBean);//禁用之前所有的支付流水并创建新的支付流水
		if(!isSuccess(resultBean)){
			Logger.logInfo("保存并禁用历史支付流水处理失败");
			return resultBean;
		}
		resultBean = doPay((List<Payment>) resultBean.getData(),"");
		if(isSuccess(resultBean)){
			if(BaseSubassembly.isPurePointsPayment(tempBean.getPayType())){
				resultBean = doPayRedress(order); //执行后续逻辑
			}
		}
		return resultBean;
	}

	@Override
	public ResultBean cancelPayingOrder(OrderMongo orderMongo) {
		Logger.logInfo("收到支付中退款请求","orderMongo",orderMongo);
		
		List<Payment> payments = paymentSupport.findEnablePaymentsOfUserPayByOrderId(orderMongo.getOrderId());
		
//		if(PaymentSupportUtils.isCombinePayment(orderMongo.getPaymentMethod())){ //是否为组合支付（是否包含现金）
			Payment paymentTemp = null;
			for (Payment p : payments) {
				if((1 == p.getPaymentType() || 3 == p.getPaymentType()) && "2".equals(p.getPaymentReason()) && "4".equals(p.getPaymentStatus()) && p.getIsEnable()){
					paymentTemp = p;
				}
			}
			if(paymentTemp != null){
				return paymentSupport.autoRefundPoints(paymentTemp);
			}
			Logger.logInfo("该订单支付没有成功的积分支付记录，不需退还积分");
//		}else{
//			
//		}
		
		return new ResultBean(ResultCode.success);
	}
}
