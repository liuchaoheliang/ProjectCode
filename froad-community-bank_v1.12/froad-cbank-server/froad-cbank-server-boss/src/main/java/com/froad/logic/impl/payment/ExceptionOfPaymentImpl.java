/**
 * Project Name:Froad Cbank Server Boss
 * File Name:ExceptionOfPaymentImpl.java
 * Package Name:com.froad.logic.impl
 * Date:2015-9-18上午9:58:42
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.logic.impl.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.payment.ExceptionOfPayment;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.RefundHistory;
import com.froad.po.mongo.RefundPaymentInfo;
import com.froad.po.mongo.RefundShoppingInfo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.settlement.Settlement;
import com.froad.support.impl.payment.DataPersistentImpl;
import com.froad.support.impl.payment.ThirdpartyPayImpl;
import com.froad.support.payment.DataPersistent;
import com.froad.support.payment.ThirdpartyPay;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.payment.BossOrderVo;
import com.froad.thrift.vo.payment.BossPaymentDetialVo;
import com.froad.thrift.vo.payment.BossPaymentPage;
import com.froad.thrift.vo.payment.BossPaymentVo;
import com.froad.thrift.vo.payment.BossQueryConditionVo;
import com.froad.thrift.vo.payment.BossRefundProductVo;
import com.froad.thrift.vo.payment.BossRefundShoppingVo;
import com.froad.thrift.vo.payment.BossRefundVo;
import com.froad.thrift.vo.payment.BossSettlementVo;
import com.froad.thrift.vo.payment.BossSubOrder;
import com.froad.thrift.vo.payment.CombineSettleReq;
import com.froad.util.Arith;
import com.froad.util.BeanUtil;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Const;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * ClassName:ExceptionOfPaymentImpl Reason: TODO ADD REASON. Date: 2015-9-18
 * 上午9:58:42
 * 
 * @author Zxy
 * @version
 * @see
 */
public class ExceptionOfPaymentImpl implements ExceptionOfPayment {

	DataPersistent dataPersistent = new DataPersistentImpl();
	ThirdpartyPay thirdpartyPay = new ThirdpartyPayImpl();
	
	@Override
	public BossPaymentPage queryOfSettleFailed(BossQueryConditionVo condition) {

		BossPaymentPage paymentPage = new BossPaymentPage();

		// 组建创建时间
		// 组建订单号
		DBObject queryDB = conditionOfSettleForFailed();

		if (StringUtils.isNotEmpty(condition.getOrderId())) {
			queryDB.put("order_id", condition.getOrderId());
		}

		if (StringUtils.isNotEmpty(condition.getClientId())) {
			queryDB.put("client_id", condition.getClientId());
		}

		PageVo pageVo = condition.getPageVo();

		if (pageVo.getBegDate() > 0 && pageVo.getEndDate() > 0) {
			queryDB.put("create_time", new BasicDBObject(QueryOperators.GTE, pageVo.getBegDate()).append(QueryOperators.LTE, pageVo.getEndDate()));
		} else if (pageVo.getBegDate() > 0) {
			queryDB.put("create_time", new BasicDBObject(QueryOperators.GTE, pageVo.getBegDate()));
		} else if (pageVo.getEndDate() > 0) {
			queryDB.put("create_time", new BasicDBObject(QueryOperators.LTE, pageVo.getEndDate()));
		}

		MongoPage mongoPage = new MongoPage();
		mongoPage = new MongoPage(pageVo.getPageNumber(), pageVo.getPageSize());
		mongoPage.setSort(new Sort("create_time", OrderBy.ASC));
		mongoPage.setFirstRecordTime(pageVo.getFirstRecordTime());
		mongoPage.setLastPageNumber(pageVo.getLastPageNumber());
		mongoPage.setLastRecordTime(pageVo.getLastRecordTime());
		mongoPage = dataPersistent.findByPaymentByConditionByPay(mongoPage, queryDB);

		List<Payment> payments = mongoPage.getItems() == null ? null : (List<Payment>) mongoPage.getItems();
		List<BossPaymentVo> dataRes = new ArrayList<BossPaymentVo>();
		if (payments != null && payments.size() > 0) {
			for (Payment entity : payments) {
				dataRes.add(BaseSubassembly.toPaymentVo(entity));
			}
		}

		paymentPage.setPayments(dataRes);
		paymentPage.setResultVo(new ResultVo("0000", "操作成功"));

		pageVo.setPageCount(mongoPage.getPageCount());
		pageVo.setTotalCount(mongoPage.getTotalCount());
		pageVo.setLastPageNumber(pageVo.getPageNumber());
		pageVo.setFirstRecordTime(mongoPage.getFirstRecordTime());
		pageVo.setLastRecordTime(mongoPage.getLastRecordTime());

		paymentPage.setPageVo(pageVo);
		return paymentPage;

	}

	@Override
	public BossPaymentPage queryOfRefundFailed(BossQueryConditionVo condition) {
		BossPaymentPage paymentPage = new BossPaymentPage();
		
		DBObject queryDB = conditionOfRefundForFailed();
		if (StringUtils.isNotEmpty(condition.getOrderId())) {
			queryDB.put("order_id", condition.getOrderId());
		}

		if (StringUtils.isNotEmpty(condition.getClientId())) {
			queryDB.put("client_id", condition.getClientId());
		}

		PageVo pageVo = condition.getPageVo();

		if (pageVo.getBegDate() > 0 && pageVo.getEndDate() > 0) {
			queryDB.put("create_time", new BasicDBObject(QueryOperators.GTE, pageVo.getBegDate()).append(QueryOperators.LTE, pageVo.getEndDate()));
		} else if (pageVo.getBegDate() > 0) {
			queryDB.put("create_time", new BasicDBObject(QueryOperators.GTE, pageVo.getBegDate()));
		} else if (pageVo.getEndDate() > 0) {
			queryDB.put("create_time", new BasicDBObject(QueryOperators.LTE, pageVo.getEndDate()));
		}

		MongoPage mongoPage = new MongoPage();
		mongoPage = new MongoPage(pageVo.getPageNumber(), pageVo.getPageSize());
		mongoPage.setSort(new Sort("create_time", OrderBy.ASC));
		mongoPage.setFirstRecordTime(pageVo.getFirstRecordTime());
		mongoPage.setLastPageNumber(pageVo.getLastPageNumber());
		mongoPage.setLastRecordTime(pageVo.getLastRecordTime());
		mongoPage = dataPersistent.findByPaymentByConditionByPay(mongoPage, queryDB);

		List<Payment> payments = mongoPage.getItems() == null ? null : (List<Payment>) mongoPage.getItems();
		List<BossPaymentVo> dataRes = new ArrayList<BossPaymentVo>();
		if (payments != null && payments.size() > 0) {
			for (Payment entity : payments) {
				dataRes.add(BaseSubassembly.toPaymentVo(entity));
			}
		}

		paymentPage.setPayments(dataRes);
		paymentPage.setResultVo(new ResultVo("0000", "操作成功"));

		pageVo.setPageCount(mongoPage.getPageCount());
		pageVo.setTotalCount(mongoPage.getTotalCount());
		pageVo.setLastPageNumber(pageVo.getPageNumber());
		pageVo.setFirstRecordTime(mongoPage.getFirstRecordTime());
		pageVo.setLastRecordTime(mongoPage.getLastRecordTime());

		paymentPage.setPageVo(pageVo);
		return paymentPage;
	}
	
	@Override
	public BossPaymentPage queryOfPayRefundPointFailed(BossQueryConditionVo condition) {
		BossPaymentPage paymentPage = new BossPaymentPage();
		
		DBObject queryDB = conditionOfPayRefundForPointFailed();
		if (StringUtils.isNotEmpty(condition.getOrderId())) {
			queryDB.put("order_id", condition.getOrderId());
		}

		if (StringUtils.isNotEmpty(condition.getClientId())) {
			queryDB.put("client_id", condition.getClientId());
		}

		PageVo pageVo = condition.getPageVo();

		if (pageVo.getBegDate() > 0 && pageVo.getEndDate() > 0) {
			queryDB.put("create_time", new BasicDBObject(QueryOperators.GTE, pageVo.getBegDate()).append(QueryOperators.LTE, pageVo.getEndDate()));
		} else if (pageVo.getBegDate() > 0) {
			queryDB.put("create_time", new BasicDBObject(QueryOperators.GTE, pageVo.getBegDate()));
		} else if (pageVo.getEndDate() > 0) {
			queryDB.put("create_time", new BasicDBObject(QueryOperators.LTE, pageVo.getEndDate()));
		}

		MongoPage mongoPage = new MongoPage();
		mongoPage = new MongoPage(pageVo.getPageNumber(), pageVo.getPageSize());
		mongoPage.setSort(new Sort("create_time", OrderBy.ASC));
		mongoPage.setFirstRecordTime(pageVo.getFirstRecordTime());
		mongoPage.setLastPageNumber(pageVo.getLastPageNumber());
		mongoPage.setLastRecordTime(pageVo.getLastRecordTime());
		mongoPage = dataPersistent.findByPaymentByConditionByPay(mongoPage, queryDB);

		List<Payment> payments = mongoPage.getItems() == null ? null : (List<Payment>) mongoPage.getItems();
		List<BossPaymentVo> dataRes = new ArrayList<BossPaymentVo>();
		if (payments != null && payments.size() > 0) {
			for (Payment entity : payments) {
				dataRes.add(BaseSubassembly.toPaymentVo(entity));
			}
		}

		paymentPage.setPayments(dataRes);
		paymentPage.setResultVo(new ResultVo("0000", "操作成功"));

		pageVo.setPageCount(mongoPage.getPageCount());
		pageVo.setTotalCount(mongoPage.getTotalCount());
		pageVo.setLastPageNumber(pageVo.getPageNumber());
		pageVo.setFirstRecordTime(mongoPage.getFirstRecordTime());
		pageVo.setLastRecordTime(mongoPage.getLastRecordTime());

		paymentPage.setPageVo(pageVo);
		return paymentPage;
	}
	
	@Override
	public BossPaymentPage queryOfPayRefundCashFailed(BossQueryConditionVo condition) {
		BossPaymentPage paymentPage = new BossPaymentPage();
		
		DBObject queryDB = conditionOfPayRefundForCashFailed();
		if (StringUtils.isNotEmpty(condition.getOrderId())) {
			queryDB.put("order_id", condition.getOrderId());
		}

		if (StringUtils.isNotEmpty(condition.getClientId())) {
			queryDB.put("client_id", condition.getClientId());
		}

		PageVo pageVo = condition.getPageVo();

		if (pageVo.getBegDate() > 0 && pageVo.getEndDate() > 0) {
			queryDB.put("create_time", new BasicDBObject(QueryOperators.GTE, pageVo.getBegDate()).append(QueryOperators.LTE, pageVo.getEndDate()));
		} else if (pageVo.getBegDate() > 0) {
			queryDB.put("create_time", new BasicDBObject(QueryOperators.GTE, pageVo.getBegDate()));
		} else if (pageVo.getEndDate() > 0) {
			queryDB.put("create_time", new BasicDBObject(QueryOperators.LTE, pageVo.getEndDate()));
		}

		MongoPage mongoPage = new MongoPage();
		mongoPage = new MongoPage(pageVo.getPageNumber(), pageVo.getPageSize());
		mongoPage.setSort(new Sort("create_time", OrderBy.ASC));
		mongoPage.setFirstRecordTime(pageVo.getFirstRecordTime());
		mongoPage.setLastPageNumber(pageVo.getLastPageNumber());
		mongoPage.setLastRecordTime(pageVo.getLastRecordTime());
		mongoPage = dataPersistent.findByPaymentByConditionByPay(mongoPage, queryDB);

		List<Payment> payments = mongoPage.getItems() == null ? null : (List<Payment>) mongoPage.getItems();
		List<BossPaymentVo> dataRes = new ArrayList<BossPaymentVo>();
		if (payments != null && payments.size() > 0) {
			for (Payment entity : payments) {
				dataRes.add(BaseSubassembly.toPaymentVo(entity));
			}
		}

		paymentPage.setPayments(dataRes);
		paymentPage.setResultVo(new ResultVo("0000", "操作成功"));

		pageVo.setPageCount(mongoPage.getPageCount());
		pageVo.setTotalCount(mongoPage.getTotalCount());
		pageVo.setLastPageNumber(pageVo.getPageNumber());
		pageVo.setFirstRecordTime(mongoPage.getFirstRecordTime());
		pageVo.setLastRecordTime(mongoPage.getLastRecordTime());

		paymentPage.setPageVo(pageVo);
		return paymentPage;
	}
	
	/**
	 * 组建查询条件 --> 结算失败 conditionOfSettleForFailed:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy 2015-9-18 上午10:26:31
	 * @return
	 *
	 */
	private DBObject conditionOfSettleForFailed() {
		DBObject queryDB = new BasicDBObject();
		queryDB.put("payment_reason", "0");// 结算类型
		queryDB.put("payment_status", "5");// 结算失败
		queryDB.put("is_dispose_exception", "0");
		return queryDB;
	}
	
	private DBObject conditionOfRefundForFailed(){
		DBObject queryDB = new BasicDBObject();
		queryDB.put("payment_reason", "1");// 退款类型
		queryDB.put("payment_status", "5");// 退款失败
		queryDB.put("is_dispose_exception", "0");
		return queryDB;
	}
	private DBObject conditionOfPayRefundForPointFailed(){
		DBObject queryDB = new BasicDBObject();
		queryDB.put("payment_reason", "5");// 自动退款类型
		queryDB.put("payment_status", "5");// 退款失败
		queryDB.put("payment_type_details", 0);// 积分类型
		queryDB.put("is_dispose_exception", "0");
		return queryDB;
	}
	private DBObject conditionOfPayRefundForCashFailed(){
		DBObject queryDB = new BasicDBObject();
		queryDB.put("payment_reason", "5");// 自动退款类型
		queryDB.put("payment_status", "5");// 退款失败
		queryDB.put("payment_type", 2);// 积分类型
		queryDB.put("is_dispose_exception", "0");
		return queryDB;
	}
	

	@Override
	public BossPaymentDetialVo queryOfSettleDetial(String paymentId) {
		BossPaymentDetialVo paymentDetialVo = new BossPaymentDetialVo();
		if (!StringUtils.isEmpty(paymentId)) {
			Payment payment = dataPersistent.findPaymentByCondition(paymentId);
			if (null == payment)
				return paymentDetialVo;

//			List<PaymentVo> payments = new ArrayList<PaymentVo>();
//			payments.add(BaseSubassembly.toPaymentVo(payment));

			paymentDetialVo.setPayment(BaseSubassembly.toPaymentVo(payment));

			/**********************************************/
			Settlement settlement = dataPersistent.findSettlementByCondition(paymentId);
			String[] excludeTargetField = new String[] { "money" };
			if (null != settlement) {
				BossSettlementVo settlementVo = (BossSettlementVo) BeanUtil.copyProperties(BossSettlementVo.class, settlement, excludeTargetField);
				settlementVo.setMoney(Arith.div(settlement.getMoney(), 1000, 3));
				paymentDetialVo.setSettlementVo(settlementVo);
			}else{
				paymentDetialVo.setPayment(null);
			}
			/**********************************************/
			String orderId = payment.getOrderId();
			OrderMongo order = dataPersistent.findOrderByCondition(orderId);
			if (null != order) {
				excludeTargetField = new String[] { "fftPoints", "bankPoints", "totalPrice" };
				BossOrderVo orderVo = (BossOrderVo) BeanUtil.copyProperties(BossOrderVo.class, order, excludeTargetField);
				if (order.getFftPoints() != null && order.getFftPoints() > 0) {
					orderVo.setFftPoints(Arith.div(order.getFftPoints(), 1000, 3));
				}
				if (order.getBankPoints() != null && order.getBankPoints() > 0) {
					orderVo.setBankPoints(Arith.div(order.getBankPoints(), 1000, 3));
				}
				if (order.getTotalPrice() != null && order.getTotalPrice() > 0) {
					orderVo.setTotalPrice(Arith.div(order.getTotalPrice(), 1000, 3));
				}
				paymentDetialVo.setOrderVo(orderVo);
			}
			
			/** 异常类型 */
			paymentDetialVo.setExceptionType("结算异常");
			/** 异常描述 */
			paymentDetialVo.setExceptionDesc("结算失败");
			/** 处理建议 */
			paymentDetialVo.setProposal("再次结算");

			// SettlementVo

		}
		return paymentDetialVo;
	}
	
	@Override
	public BossPaymentDetialVo queryOfRefundDetial(String paymentId) {
		BossPaymentDetialVo paymentDetialVo = new BossPaymentDetialVo();
		if (StringUtils.isEmpty(paymentId)) {
			return paymentDetialVo;
		}
		
		Payment payment = dataPersistent.findPaymentByCondition(paymentId);
		if(payment == null){
			return paymentDetialVo;
		}
		
		String[] excludeTargetField;
		String orderId = payment.getOrderId();
		OrderMongo order = dataPersistent.findOrderByCondition(orderId);
		if (null != order) {
			excludeTargetField = new String[] { "fftPoints", "bankPoints", "totalPrice" };
			BossOrderVo orderVo = (BossOrderVo) BeanUtil.copyProperties(BossOrderVo.class, order, excludeTargetField);
			if (order.getFftPoints() != null && order.getFftPoints() > 0) {
				orderVo.setFftPoints(Arith.div(order.getFftPoints(), 1000, 3));
			}
			if (order.getBankPoints() != null && order.getBankPoints() > 0) {
				orderVo.setBankPoints(Arith.div(order.getBankPoints(), 1000, 3));
			}
			if (order.getTotalPrice() != null && order.getTotalPrice() > 0) {
				orderVo.setTotalPrice(Arith.div(order.getTotalPrice(), 1000, 3));
			}
			paymentDetialVo.setOrderVo(orderVo);
		}
		
		RefundHistory refund = dataPersistent.findRefundByPaymentId(paymentId);
		if(refund != null){
			BossRefundVo refundVo = (BossRefundVo) BeanUtil.copyProperties(BossRefundVo.class, refund);
			refundVo.setRefundId(refund.get_id());
			RefundShoppingInfo shopping = refund.getShoppingInfo().get(0);
			BossRefundShoppingVo refundShoppingVo = (BossRefundShoppingVo)BeanUtil.copyProperties(BossRefundShoppingVo.class, shopping);
			List<BossRefundProductVo> bossRefundProductVos = (List<BossRefundProductVo>)BeanUtil.copyProperties(BossRefundProductVo.class,shopping.getProducts());
			refundShoppingVo.setRefundProductVo(bossRefundProductVos);
			refundVo.setRefundShoppingVo(refundShoppingVo);
			
			List<RefundPaymentInfo> payInfos = refund.getPaymentInfo();
			if(payInfos != null){
				for (RefundPaymentInfo refundPaymentInfo : payInfos) {
					if("1".equals(refundPaymentInfo.getType())){
						refundVo.setCash(Arith.div(refundPaymentInfo.getRefundValue(), 1000));
					}else if ("2".equals(refundPaymentInfo.getType())){
						refundVo.setFftPoints(Arith.div(refundPaymentInfo.getRefundValue(), 1000));
					}else if ("3".equals(refundPaymentInfo.getType())){
						refundVo.setBankPoints(Arith.div(refundPaymentInfo.getRefundValue(), 1000));
					}
				}
			}
			paymentDetialVo.setRefund(refundVo);
		}
		
		BossPaymentVo paymentVo = (BossPaymentVo)BeanUtil.copyProperties(BossPaymentVo.class, payment);
		paymentDetialVo.setPayment(paymentVo);
		
		/** 异常类型 */
		paymentDetialVo.setExceptionType("退款异常");
		/** 异常描述 */
		paymentDetialVo.setExceptionDesc("退款失败");
		/** 处理建议 */
		paymentDetialVo.setProposal("再次退款");
		return paymentDetialVo;
	}
	

	@Override
	public BossPaymentDetialVo queryOfPayRefundPointFailedDetial(String paymentId) {
		BossPaymentDetialVo paymentDetialVo = new BossPaymentDetialVo();
		if (StringUtils.isEmpty(paymentId)) {
			return paymentDetialVo;
		}
		
		Payment payment = dataPersistent.findPaymentByCondition(paymentId);
		if(payment == null){
			return paymentDetialVo;
		}
		BossPaymentVo pVo = (BossPaymentVo) BeanUtil.copyProperties(BossPaymentVo.class,payment);
		paymentDetialVo.setPayment(pVo);
		
		String[] excludeTargetField;
		String orderId = payment.getOrderId();
		OrderMongo order = dataPersistent.findOrderByCondition(orderId);
		if (null != order) {
			excludeTargetField = new String[] { "fftPoints", "bankPoints", "totalPrice" };
			BossOrderVo orderVo = (BossOrderVo) BeanUtil.copyProperties(BossOrderVo.class, order, excludeTargetField);
			if (order.getFftPoints() != null && order.getFftPoints() > 0) {
				orderVo.setFftPoints(Arith.div(order.getFftPoints(), 1000, 3));
			}
			if (order.getBankPoints() != null && order.getBankPoints() > 0) {
				orderVo.setBankPoints(Arith.div(order.getBankPoints(), 1000, 3));
			}
			if (order.getTotalPrice() != null && order.getTotalPrice() > 0) {
				orderVo.setTotalPrice(Arith.div(order.getTotalPrice(), 1000, 3));
			}
			paymentDetialVo.setOrderVo(orderVo);
		}
		List<SubOrderMongo> subOrders = dataPersistent.querySubOrderByOrderId(orderId);
		List<ProductMongo> p = new ArrayList<ProductMongo>();
		if(subOrders != null && subOrders.size() != 0){
			for (SubOrderMongo sbO : subOrders) {
				p.addAll(0, sbO.getProducts());
			}
			
			List<BossSubOrder> bossSubOrders = (List<BossSubOrder>)BeanUtil.copyProperties(BossSubOrder.class,p);
			paymentDetialVo.setSubOrders(bossSubOrders);
		}
		
		
		/** 异常类型 */
		paymentDetialVo.setExceptionType("积分退款失败");
		/** 异常描述 */
		paymentDetialVo.setExceptionDesc("支付异常，自动退还积分失败");
		/** 处理建议 */
		paymentDetialVo.setProposal("再次退还积分");
		return paymentDetialVo;
	}

	@Override
	public BossPaymentDetialVo queryOfPayRefundCashFailedDetial(String paymentId) {
		BossPaymentDetialVo paymentDetialVo = new BossPaymentDetialVo();
		if (StringUtils.isEmpty(paymentId)) {
			return paymentDetialVo;
		}
		
		Payment payment = dataPersistent.findPaymentByCondition(paymentId);
		if(payment == null){
			return paymentDetialVo;
		}
		BossPaymentVo pVo = (BossPaymentVo) BeanUtil.copyProperties(BossPaymentVo.class,payment);
		paymentDetialVo.setPayment(pVo);
		
		String[] excludeTargetField;
		String orderId = payment.getOrderId();
		OrderMongo order = dataPersistent.findOrderByCondition(orderId);
		if (null != order) {
			excludeTargetField = new String[] { "fftPoints", "bankPoints", "totalPrice" };
			BossOrderVo orderVo = (BossOrderVo) BeanUtil.copyProperties(BossOrderVo.class, order, excludeTargetField);
			if (order.getFftPoints() != null && order.getFftPoints() > 0) {
				orderVo.setFftPoints(Arith.div(order.getFftPoints(), 1000, 3));
			}
			if (order.getBankPoints() != null && order.getBankPoints() > 0) {
				orderVo.setBankPoints(Arith.div(order.getBankPoints(), 1000, 3));
			}
			if (order.getTotalPrice() != null && order.getTotalPrice() > 0) {
				orderVo.setTotalPrice(Arith.div(order.getTotalPrice(), 1000, 3));
			}
			paymentDetialVo.setOrderVo(orderVo);
		}
		List<SubOrderMongo> subOrders = dataPersistent.querySubOrderByOrderId(orderId);
		List<ProductMongo> p = new ArrayList<ProductMongo>();
		if(subOrders != null && subOrders.size() != 0){
			for (SubOrderMongo sbO : subOrders) {
				p.addAll(0, sbO.getProducts());
			}
			
			List<BossSubOrder> bossSubOrders = (List<BossSubOrder>)BeanUtil.copyProperties(BossSubOrder.class,p);
			paymentDetialVo.setSubOrders(bossSubOrders);
		}
		
		/** 异常类型 */
		paymentDetialVo.setExceptionType("现金退款失败");
		/** 异常描述 */
		paymentDetialVo.setExceptionDesc("支付关单，自动退还现金失败");
		/** 处理建议 */
		paymentDetialVo.setProposal("再次退还现金");
		return paymentDetialVo;
	}
	
	@Override
	public ResultBean retryOfSettle(String paymentId) {
		if(StringUtils.isEmpty(paymentId)){
			return new ResultBean(ResultCode.failed,"paymentId为空");
		}
		
		Payment payment = dataPersistent.findPaymentByCondition(paymentId);
		if(payment == null || !"0".equals(payment.getPaymentReason())){
			return new ResultBean(ResultCode.failed,"paymentId无效");
		}
		Settlement settlement = dataPersistent.findSettlementByCondition(paymentId);
		if(settlement == null || !"3".equals(settlement.getSettleState())){
			LogCvt.error("申请再次结算无效，结算记录为空或者结算记录非失败 paymentId " + paymentId);
			return new ResultBean(ResultCode.failed,"申请再次结算无效，结算记录为空或者结算记录非失败 paymentId " + paymentId);
		}
		
		//检查是否重复结算
//		Payment settlePayment = dataPersistent.queryPaymentOfSettlementingOrSettlemented(payment.getOrderId());
//		if(settlePayment != null){
//			return new ResultBean(ResultCode.failed,"该订单下存在结算中或已结算完成记录，无法再次结算");
//		}
		
		if(!dataPersistent.findAndModifyPaymentToProcessed(paymentId)){
			return new ResultBean(ResultCode.failed,"该笔结算请求处理中");
		}
		payment.setPaymentId(BaseSubassembly.simpleIDPayment());
		settlement.setId(null);
		settlement.setCreateTime(new Date().getTime());
		settlement.setSettlementId(BaseSubassembly.simpleIDSettlement());
		settlement.setSettleState("1");
		settlement.setRemark("由boss系统后台发起的结算请求");
		settlement.setPaymentId(payment.getPaymentId());
		
		//持久化新的结算数据
		boolean flag = dataPersistent.saveSettlement(settlement);
		if(!flag){
			return new ResultBean(ResultCode.failed,"保存新结算记录失败");
		}
		
		payment.setCreateTime(new Date());
		payment.setBillNo("");
		payment.setStep(2);
		payment.setPaymentStatus("1");
		payment.setRemark("");
		payment.setResultCode("");
		payment.setResultDesc("");
		
		flag = dataPersistent.savePayment(payment);
		if(!flag){
			return new ResultBean(ResultCode.failed,"保存新支付流水（结算）失败");
		}
		
		CombineSettleReq req = new CombineSettleReq();
		req.setClientId(payment.getClientId());
		req.setPaymentId(payment.getPaymentId());
		req.setMerchantAndOutletId(payment.getToAccountName());
		req.setPaymentOrgNo(payment.getPaymentOrgNo());
		req.setPayValue(String.valueOf(Arith.div(payment.getPaymentValue(),Const.HDOP_1000)));
		ResultVo resultVo = thirdpartyPay.cashCombineSettle(req);
		if(resultVo == null){
			dataPersistent.modifyPaymentToRequestException(payment.getPaymentId(), "结算请求发送异常");
			dataPersistent.addPaymentTimeFromRedis(payment.getPaymentId());
			//结算请求发送异常
			return new ResultBean(ResultCode.success,"结算请求发送异常，请稍后确认结算结果");
		}
		dataPersistent.modifyPaymentToRequestSuccess(payment.getPaymentId());
		if("0000".equals(resultVo.getResultCode())){
			dataPersistent.addPaymentTimeFromRedis(payment.getPaymentId());
			//结算请求发送成功
			dataPersistent.modifyPaymentToPayAccessSuccess(payment.getPaymentId(), resultVo.getResultDesc(), "结算请求受理成功");
			return new ResultBean(ResultCode.success,"结算请求发送成功，等待获取最终结算结果");
		}
		dataPersistent.modifyPaymentToPayFailed(payment.getPaymentId(), resultVo.getResultCode(), resultVo.getResultDesc());
		dataPersistent.modifySettlementToFailed(settlement.getSettlementId());
		return new ResultBean(ResultCode.failed,"结算失败：" + resultVo.getResultDesc());
	}

	@Override
	public ResultVo retryOfRefund(String refundId) {
		return thirdpartyPay.retryRefund(refundId);
	}

	@Override
	public ResultVo refundOfCash(String paymentId) {
		return thirdpartyPay.refundPayCash(paymentId);
	}

	@Override
	public ResultVo refundOfPoint(String paymentId) {
		return thirdpartyPay.refundPayPoint(paymentId);
	}

}
