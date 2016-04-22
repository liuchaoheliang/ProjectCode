package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.handler.PaymentQueryHandler;
import com.froad.po.PaymentMongoEntity;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.payment.PaymentExceptionType;
import com.froad.thrift.vo.payment.PaymentQueryExcetionVo;
import com.froad.thrift.vo.payment.PaymentQueryPageVo;
import com.froad.thrift.vo.payment.PaymentQueryVo;
import com.froad.thrift.vo.payment.PaymentType;
import com.froad.util.Arith;
import com.froad.util.MongoTableName;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Logger;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class PaymentQueryHandlerImpl implements PaymentQueryHandler{

	private final String COLLECTION_NAME = MongoTableName.CB_PAYMENT;
	
	private MongoManager mongoManager = new MongoManager();
	
	private final int PAY_CASH_FAILD_AUTO_REFUND_POINT_FAILED = 10;
    private final int AUTO_PRESENT_FROAD_POINT = 11;
    private final int REFUND_CASH_SUCCESS_REFUND_POINT_FAILED = 12;
    private final int REFUND_PRESENT_FROAD_POINT = 13; 
	
	@Override
	public PaymentQueryPageVo queryPaymentForBoss(PaymentQueryVo paymentQueryVo) {
		
		Logger.logInfo("收到boss查询支付流水请求","paymentQueryVo",paymentQueryVo);
		
		DBObject queryObj = new BasicDBObject();
		
		PageVo pageVo = paymentQueryVo.getPageVo();
		
		MongoPage mongoPage = new MongoPage();
		mongoPage = new MongoPage(pageVo.getPageNumber(), pageVo.getPageSize());
		mongoPage.setFirstRecordTime(pageVo.getFirstRecordTime());
		mongoPage.setLastPageNumber(pageVo.getLastPageNumber());
		mongoPage.setLastRecordTime(pageVo.getLastRecordTime());
//		mongoPage.setSort(new Sort("create_time", OrderBy.DESC));
		
		//---orderId
		if(!StringUtils.isEmpty(paymentQueryVo.getOrderId())){
			queryObj.put("order_id", paymentQueryVo.getOrderId());
		}
		//---paymentId
		if(!StringUtils.isEmpty(paymentQueryVo.getPaymentId())){
			queryObj.put("payment_id", paymentQueryVo.getPaymentId());
		}
		//---paymentReason
		if(!StringUtils.isEmpty(paymentQueryVo.getPaymentReason())){
			queryObj.put("payment_reason", paymentQueryVo.getPaymentReason());
		}
		
		if(!StringUtils.isEmpty(paymentQueryVo.getPaymentStatus())){
			queryObj.put("payment_status", paymentQueryVo.getPaymentStatus());
		}
		
		if(pageVo.getBegDate() > 0 && pageVo.getEndDate() > 0){
			queryObj.put("create_time", new BasicDBObject(QueryOperators.GTE,pageVo.getBegDate()).append(QueryOperators.LTE,pageVo.getEndDate()));
		}else if(pageVo.getBegDate() > 0){
			queryObj.put("create_time", new BasicDBObject(QueryOperators.GTE,pageVo.getBegDate()));
		}else if(pageVo.getEndDate() > 0){
			queryObj.put("create_time", new BasicDBObject(QueryOperators.LTE,pageVo.getEndDate()));
		}
		
		//---clientId
		if(!StringUtils.isEmpty(paymentQueryVo.getClientId())){
			queryObj.put("client_id", paymentQueryVo.getClientId());
		}
		
		//---fromUserName
		if(!StringUtils.isEmpty(paymentQueryVo.getFromUserName())){
			queryObj.put("from_user_name", paymentQueryVo.getFromUserName());
		}
		
		if(!StringUtils.isEmpty(paymentQueryVo.getBillNo())){
			queryObj.put("bill_no", paymentQueryVo.getBillNo());
		}
		
		mongoPage = mongoManager.findByPageWithRedis(mongoPage, queryObj,COLLECTION_NAME,PaymentMongoEntity.class);
		
		List<PaymentMongoEntity> datas = mongoPage.getItems() == null ? null : (List<PaymentMongoEntity>)mongoPage.getItems();
		List<PaymentQueryVo> list = new ArrayList<PaymentQueryVo>();
		if(datas != null){
			for (PaymentMongoEntity entity : datas) {
				list.add(BaseSubassembly.loadyPaymentQueryVo(entity));
			}
		}
		
		PaymentQueryPageVo pageQueryVo = new PaymentQueryPageVo();
		pageQueryVo.setPaymentQueryVos(list);
		pageQueryVo.setResultVo(new ResultVo("0000", "操作成功"));
		
		pageVo.setPageCount(mongoPage.getPageCount());
		pageVo.setTotalCount(mongoPage.getTotalCount());
		pageVo.setLastPageNumber(pageVo.getPageNumber());
		pageVo.setFirstRecordTime(mongoPage.getFirstRecordTime());
		pageVo.setLastRecordTime(mongoPage.getLastRecordTime());
		
		pageQueryVo.setPageVo(pageVo);
		return pageQueryVo;
	}

	
	
	@Override
	public PaymentQueryPageVo queryPaymentForBossOfException(PaymentQueryExcetionVo excetionVo) {
		
		Logger.logInfo("收到boss查询异常支付流水请求","excetionVo",excetionVo);
		
		PageVo pageVo = excetionVo.getPageVo();
		
		DBObject queryObj = new BasicDBObject();
		
		MongoPage mongoPage = new MongoPage();
		mongoPage = new MongoPage(pageVo.getPageNumber(), pageVo.getPageSize());
		mongoPage.setFirstRecordTime(pageVo.getFirstRecordTime());
		mongoPage.setLastPageNumber(pageVo.getLastPageNumber());
		mongoPage.setLastRecordTime(pageVo.getLastRecordTime());
//		mongoPage.setSort(new Sort("create_time", OrderBy.DESC));
		queryObj.put("is_dispose_exception", "0"); //未处理过异常
		
		PaymentExceptionType exceptype = excetionVo.getExceptionType(); //获取调用方指定的异常类型
		PaymentType type = excetionVo.getType();
		
		
		PaymentQueryPageVo pageQueryVo = new PaymentQueryPageVo();
		
		if(exceptype == null){
			pageQueryVo.setResultVo(new ResultVo("9999", "未指明最小异常类型"));
			return pageQueryVo;
		}
		
		switch (exceptype.getValue()) {
			case PAY_CASH_FAILD_AUTO_REFUND_POINT_FAILED:
				queryObj.put("payment_reason", "2"); //用户支付产生的流水
				queryObj.put("auto_refund", "1");//自动退款失败/按照业务逻辑只有积分支付才会涉及自动退款
				break;
			case AUTO_PRESENT_FROAD_POINT:
				queryObj.put("payment_reason", "3"); //自动赠送积分产生的流水
				queryObj.put("payment_status",new BasicDBObject(QueryOperators.NE,"4"));
				break;
			case REFUND_CASH_SUCCESS_REFUND_POINT_FAILED:
				queryObj.put("payment_reason", "2"); //用户支付产生的流水
				queryObj.put(QueryOperators.OR,new Object[]{new BasicDBObject("payment_type",1),new BasicDBObject("payment_type",3)});
				queryObj.put("payment_status","5");
				break;
			case REFUND_PRESENT_FROAD_POINT:
				queryObj.put("payment_reason", "4"); //用户支付产生的流水
				queryObj.put("payment_status",new BasicDBObject(QueryOperators.NE,"4"));
				break;
		default:
			break;
		}
		
		if(pageVo.getBegDate() > 0 && pageVo.getEndDate() > 0){
			queryObj.put("create_time", new BasicDBObject(QueryOperators.GTE,pageVo.getBegDate()).append(QueryOperators.LTE,pageVo.getEndDate()));
		}else if(pageVo.getBegDate() > 0){
			queryObj.put("create_time", new BasicDBObject(QueryOperators.GTE,pageVo.getBegDate()));
		}else if(pageVo.getEndDate() > 0){
			queryObj.put("create_time", new BasicDBObject(QueryOperators.LTE,pageVo.getEndDate()));
		}
		
		if(!StringUtils.isEmpty(excetionVo.getClientId())){
			queryObj.put("client_id", excetionVo.getClientId());
		}
		
		mongoPage = mongoManager.findByPageWithRedis(mongoPage, queryObj,COLLECTION_NAME,PaymentMongoEntity.class);
		
		List<PaymentMongoEntity> datas = mongoPage.getItems() == null ? null : (List<PaymentMongoEntity>)mongoPage.getItems();
		List<PaymentQueryVo> list = new ArrayList<PaymentQueryVo>();
		if(datas != null){
			for (PaymentMongoEntity entity : datas) {
				list.add(BaseSubassembly.loadyPaymentQueryVo(entity));
			}
		}
		
		pageQueryVo.setPaymentQueryVos(list);
		pageQueryVo.setResultVo(new ResultVo("0000", "操作成功"));
		pageVo.setPageCount(mongoPage.getPageCount());
		pageVo.setTotalCount(mongoPage.getTotalCount());
		pageVo.setLastPageNumber(pageVo.getPageNumber());
		pageVo.setFirstRecordTime(mongoPage.getFirstRecordTime());
		pageVo.setLastRecordTime(mongoPage.getLastRecordTime());
		pageQueryVo.setPageVo(pageVo);
		
		return pageQueryVo;
	}
	
	public static void main(String[] args) {
		System.out.println(Arith.div(700, 100));
	}
}
