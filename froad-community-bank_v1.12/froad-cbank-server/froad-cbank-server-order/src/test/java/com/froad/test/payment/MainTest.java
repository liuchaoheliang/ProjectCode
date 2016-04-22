package com.froad.test.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.froad.common.beans.payment.PayThriftVoBean;
import com.froad.enums.CashType;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.PaymentMethod;
import com.froad.logic.MemberInformationLogic;
import com.froad.logic.impl.MemberInformationLogicImpl;
import com.froad.logic.impl.payment.DoPayLogicImpl;
import com.froad.logic.impl.payment.DoPayTrailingImpl;
import com.froad.logic.impl.payment.PaymentCoreLogicImpl;
import com.froad.logic.impl.payment.RefundLogicImpl;
import com.froad.logic.payment.DoPayTrailing;
import com.froad.logic.payment.PaymentCoreLogic;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.support.OrderSupport;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.impl.PaymentSupportImpl;
import com.froad.support.impl.payment.DataPersistentImpl;
import com.froad.support.impl.payment.DataWrapImpl;
import com.froad.support.payment.DataPersistent;
import com.froad.support.payment.DataWrap;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.request.openapi.OpenApiFunc;
import com.froad.thirdparty.request.openapi.impl.OpenApiFuncImpl;
import com.froad.thirdparty.request.points.PointsApiFunc;
import com.froad.thirdparty.request.points.impl.PointsApiFuncImpl;
import com.froad.util.Arith;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Const;
import com.froad.util.payment.EsyT;
import com.froad.util.payment.Logger;
import com.froad.util.payment.TimeHelper;
import com.froad.util.payment.TimeHelper.TimeType;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class MainTest {

	
	static{
		
		System.setProperty("CONFIG_PATH", "./config");
	}
	
	public static void main(String[] args) {
		PaymentSupportImpl paymentSupportImpl = new PaymentSupportImpl();
		DoPayLogicImpl doPayLogicImpl = new DoPayLogicImpl();
		PaymentCoreLogic paymentCoreLogic = new PaymentCoreLogicImpl();
		OrderSupport orderSupport = new OrderSupportImpl();
		DataWrap dataWrap = new DataWrapImpl();
		DataPersistentImpl dataPersistentImpl = new DataPersistentImpl();
		MemberInformationLogic memberInformationLogic = new MemberInformationLogicImpl(); 
		RefundLogicImpl refundLogicImpl = new RefundLogicImpl(); 
		OpenApiFunc openApiFunc = new OpenApiFuncImpl();
		PointsApiFunc pointsAPIFunc = new PointsApiFuncImpl();
		DataPersistent dataPersistent = new DataPersistentImpl();
//		printJSON(BaseSubassembly.acquireClientFromRedis("chongqing"));
		
//		printJSON(BaseSubassembly.acquireClientPaymentChannelFromRedis("chongqing"));
		
//		printJSON(BaseSubassembly.acquireOpenAPIPartnerNo("chongqing"));
		
//		Payment payment = new Payment();
//		payment.setPaymentId("01BB40CF8000");
//		payment.setIsDisposeException("0");
//		payment.setIsEnable(true);
//		payment.setFromAccountNo("o2oUser");
//		printSTRING(paymentSupportImpl.updatePayment(payment));
		
//		Payment paymentOriginal = new Payment();
//		paymentOriginal.setPaymentId("01BB40CF8000");
//		paymentOriginal.setIsDisposeException("0");
//		Payment paymentTarget = new Payment();
//		paymentTarget.setPaymentId("01BB40CF8000");
//		paymentTarget.setIsDisposeException("1");;
//		printJSON(paymentSupportImpl.updatePaymentAndLock(paymentOriginal, paymentTarget));
		
//		printJSON(paymentSupportImpl.findPaymentByPaymentId("01BB40CF8000"));
		
//		printJSON(paymentSupportImpl.findEnablePaymentsOfUserPayByOrderId("01BB60508000"));
		
//		List<Payment> payments = new ArrayList<Payment>();
//		Payment paymentPoint = new Payment();
//		paymentPoint.setPaymentType(3);
//		Payment paymentCash = new Payment();
//		paymentCash.setPaymentType(2);
//		payments.add(paymentPoint);
//		payments.add(paymentCash);
//		doPayLogicImpl.doPayCoreForGeneralTrade(payments);
		
//		PayThriftVoBean payThriftVoBean = new PayThriftVoBean();
//		payThriftVoBean.setClientId("anhui");
//		payThriftVoBean.setPayType(Integer.valueOf(PaymentMethod.bankPoints.getCode()));
//		payThriftVoBean.setCashType(CashType.bank_fast_pay.code());
//		payThriftVoBean.setCashOrgNo("664");
//		payThriftVoBean.setPointOrgNo("100010043");
//		payThriftVoBean.setPointAmount(2);
//		payThriftVoBean.setCashAmount(0.1);
////		payThriftVoBean.setFoilCardNum("13527459000");
////		payThriftVoBean.setOrderId("05D499720000");
//		payThriftVoBean.setOrderId("0D3F85740000");
//		printJSON(paymentCoreLogic.doPayCoreForGeneralTrade(payThriftVoBean));
		
		
//		paymentCoreLogic.refundUserPaymentCapital("0D3F85740000", 2d, 2d, null);
		
//		printJSON(refundLogicImpl.autoRefundPointOfUserPay("0D2E10C38000"));
//		printJSON(refundLogicImpl.autoRefundCashOfUserPay("0D2E41100000"));
		
//		printJSON(paymentCoreLogic.refundUserPaymentCapital("048764A70000", null, 1d, 0d));
		
//		printJSON(paymentCoreLogic.verifyPaymentResult("03F478208000"));
		
		
//		OrderMongo order = orderSupport.getOrderById("052C35060000");
//		paymentSupportImpl.updateMerchantMonthCountForPaySuccess(order);

//		System.out.println(wrapData.findAndModifyPaymentToStepFour("01BB60AF8000"));
		

//		printSTRING(EsyT.simpleID());
		
//		printSTRING(EsyT.nowToNaturalYearDaysOffset());

//		System.out.println(EsyT.simpleID());
//		memberInformationLogic.employeeCodeVerify("chongqing", "d", "d", "d", "100");
		
//		printJSON(dataWrap.queryByPaymentId("150307124441971"));
		
//		Payment payment = new Payment();
//		payment.setPaymentId("gggg");
//		payment.setIsEnable(false);
//		payment.setOrderId("orderId");
//		payment.setCreateTime(new Date());
//		payment.setPaymentType(1);
//		printJSON(dataPersistentImpl.savePaymentToMongoDB(payment));
		
//		Payment paymentUpdate = new Payment();
//		paymentUpdate.setPaymentId("paymentId_");
//		paymentUpdate.setOrderId("orderId_");
//		paymentUpdate.setPaymentType(0);
//		printJSON(dataPersistentImpl.updateByPaymentIdFromMongoDB(paymentUpdate));
		
//		Payment paymentOriginal = new Payment();
//		paymentOriginal.setOrderId("orderId_");
//		paymentOriginal.setPaymentId("paymentId");
//		Payment paymentTarget = new Payment();
//		paymentTarget.setClientId("b");
//		printJSON(dataPersistentImpl.findAndModifyPaymentFromMongoDB(paymentOriginal, paymentTarget));
		
//		Payment query = new Payment();
//		query.setOrderId("orderId");
//		query.setPaymentId("gg");
//		printJSON(dataPersistentImpl.findByPaymentConditionFromMongoDB(query));
//		List<String> list = new ArrayList<String>();
//		list.add("1");
//		list.get(10);
		
//		OpenApiReq req = new OpenApiReq("210", "王尼玛", "5555555555555555555", "50022144452584658445", "1", "80160000018");
//		printJSON((openApiFunc.bankCardAccCheck(req)));
//		PointsReq req = new PointsReq("vip11");
//		pointsAPIFunc.contractRelationshipQuery(req);
		
//		Payment payment = new Payment();
//		payment.setPaymentId("0C308BA00000");
//		payment.setIsDisposeException("1");
//		dataPersistent.updateByPaymentIdFromMongoDB(payment);
		
//		System.out.println(Arith.div(1199, 2140));
		
//		printJSON(memberInformationLogic.queryMemberInfoByMemberCode(52001975252l, "chongqing"));
//		Date date = TimeHelper.parseDate("2016-01-09|11:30:30", TimeType.DEFAULT);
//		System.out.println(new Date().after(date));
		
		double dou = 0.0d;
		System.out.println(dou == 0);
	}
	
	private static void printSTRING(Object obj){
		System.out.println(obj.toString());
	}
	
	private static void printJSON(Object obj){
		System.out.println(JSONObject.toJSONString(obj));
	}
}
