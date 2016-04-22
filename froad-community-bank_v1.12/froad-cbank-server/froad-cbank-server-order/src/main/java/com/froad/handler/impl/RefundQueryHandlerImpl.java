package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.FieldMapping;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ResultCode;
import com.froad.handler.RefundQueryHandler;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.refund.RefundHistory;
import com.froad.po.refund.RefundPaymentInfo;
import com.froad.po.refund.RefundProduct;
import com.froad.po.refund.RefundShoppingInfo;
import com.froad.support.RefundSupport;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.impl.RefundSupportImpl;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.refund.RefundInfoVo;
import com.froad.thrift.vo.refund.RefundListRequestVo;
import com.froad.thrift.vo.refund.RefundListResponseVo;
import com.froad.thrift.vo.refund.RefundPaymentVo;
import com.froad.thrift.vo.refund.RefundProductVo;
import com.froad.thrift.vo.refund.RefundResponseVo;
import com.froad.util.Arith;
import com.froad.util.EmptyChecker;
import com.froad.util.payment.Const;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class RefundQueryHandlerImpl implements RefundQueryHandler {

	@Override
	public RefundResponseVo getRefundDetail(String refundId) {
		RefundHistory refundHis = null;
		RefundResponseVo responseVo = null;
		RefundSupport refundSupport = null;
		RefundInfoVo refundInfoVo = null;
		ResultVo resultVo = null;
		
		responseVo = new RefundResponseVo();
		resultVo = new ResultVo();
		try {
			refundSupport = new RefundSupportImpl();
			refundHis = refundSupport.getByRefundId(refundId);
			
			if (null != refundHis){
				refundInfoVo = convertToVo(refundHis);
			} else {
				refundInfoVo = new RefundInfoVo();
			}
			
			responseVo.setRefundInfo(refundInfoVo);
			
			resultVo.setResultCode(ResultCode.success.getCode());
		} catch (Exception e){
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("获取退款详情失败");
			LogCvt.error(new StringBuffer("获取退款详情失败：").append(refundId).toString(), e);
		}
		
		responseVo.setResultVo(resultVo);
		
		return responseVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RefundListResponseVo getRefundList(RefundListRequestVo refundListRequestVo) {
		RefundSupport refundSupport = null;
		String source = null;
		RefundListResponseVo responseVo = null;
		PageVo pageVo = null;
		ResultVo resultVo = null;
		MongoPage mongoPage = null;
		List<RefundHistory> refundHisList = null;
		RefundHistory refundHis = null;
		List<RefundInfoVo> refundInfoList = null;
		RefundInfoVo refundInfoVo = null;
		DBObject queryObj = null;
		
		responseVo = new RefundListResponseVo();
		resultVo = new ResultVo();
		queryObj = new BasicDBObject();
		
		try {
			source = refundListRequestVo.getSource();
			
			refundSupport = new RefundSupportImpl();
			pageVo = new PageVo();
			refundInfoList = new ArrayList<RefundInfoVo>();
			
			if (!EmptyChecker.isEmpty(refundListRequestVo.getClientId())) {
				queryObj.put(FieldMapping.CLIENT_ID.getMongoField(), refundListRequestVo.getClientId());
			}
			
			if (!source.equals("1") && !source.equals("2")) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("无效的查找选项");
				responseVo.setResultVo(resultVo);
				return responseVo;
			} else if (source.equals("1")){
				// 根据会员编号(memberCode)查找其所有退款记录
				queryObj.put(FieldMapping.MEMBER_CODE.getMongoField(), refundListRequestVo.getMemberCode());
				if (!EmptyChecker.isEmpty(refundListRequestVo.getStartDate()) && !EmptyChecker.isEmpty(refundListRequestVo.getEndDate())) {
					queryObj.put(FieldMapping.CREATE_TIME.getMongoField(),
							new BasicDBObject("$gte", Long.valueOf(refundListRequestVo.getStartDate()))
								.append("$lte", Long.valueOf(refundListRequestVo.getEndDate())));
				} else if (!EmptyChecker.isEmpty(refundListRequestVo.getStartDate())) {
					queryObj.put(FieldMapping.CREATE_TIME.getMongoField(),
							new BasicDBObject("$gte", Long.valueOf(refundListRequestVo.getStartDate())));
				} else if (!EmptyChecker.isEmpty(refundListRequestVo.getEndDate())) {
					queryObj.put(FieldMapping.CREATE_TIME.getMongoField(),
							new BasicDBObject("$lte", Long.valueOf(refundListRequestVo.getEndDate())));
				}
			
			} else if (source.equals("2")) {
				// 根据开始结束时间(startDate, endDate)查找退款记录
				if (!EmptyChecker.isEmpty(refundListRequestVo.getPageVo().getBegDate()) && !EmptyChecker.isEmpty(refundListRequestVo.getPageVo().getEndDate())) {
					queryObj.put(FieldMapping.CREATE_TIME.getMongoField(),
							new BasicDBObject("$gte", Long.valueOf(refundListRequestVo.getPageVo().getBegDate()))
								.append("$lte", Long.valueOf(refundListRequestVo.getPageVo().getEndDate())));
				} else if (!EmptyChecker.isEmpty(refundListRequestVo.getPageVo().getBegDate())) {
					queryObj.put(FieldMapping.CREATE_TIME.getMongoField(),
							new BasicDBObject("$gte", Long.valueOf(refundListRequestVo.getPageVo().getBegDate())));
				} else if (!EmptyChecker.isEmpty(refundListRequestVo.getPageVo().getEndDate())) {
					queryObj.put(FieldMapping.CREATE_TIME.getMongoField(),
							new BasicDBObject("$lte", Long.valueOf(refundListRequestVo.getPageVo().getEndDate())));
				}
				
				// 根据开始结束时间(startDate, endDate)查找退款记录
				if (!EmptyChecker.isEmpty(refundListRequestVo.getStartDate()) && !EmptyChecker.isEmpty(refundListRequestVo.getEndDate())) {
					queryObj.put(FieldMapping.CREATE_TIME.getMongoField(),
							new BasicDBObject("$gte", Long.valueOf(refundListRequestVo.getStartDate()))
								.append("$lte", Long.valueOf(refundListRequestVo.getEndDate())));
				} else if (!EmptyChecker.isEmpty(refundListRequestVo.getStartDate())) {
					queryObj.put(FieldMapping.CREATE_TIME.getMongoField(),
							new BasicDBObject("$gte", Long.valueOf(refundListRequestVo.getStartDate())));
				} else if (!EmptyChecker.isEmpty(refundListRequestVo.getEndDate())) {
					queryObj.put(FieldMapping.CREATE_TIME.getMongoField(),
							new BasicDBObject("$lte", Long.valueOf(refundListRequestVo.getEndDate())));
				}
				
				if (!EmptyChecker.isEmpty(refundListRequestVo.getOrderId())) {
					queryObj.put(FieldMapping.ORDER_ID.getMongoField(), refundListRequestVo.getOrderId());
				}
				if (!EmptyChecker.isEmpty(refundListRequestVo.getSubOrderId())) {
					queryObj.put(FieldMapping.SUB_ORDER_ID.getMongoField(), refundListRequestVo.getSubOrderId());
				}				
				if (!EmptyChecker.isEmpty(refundListRequestVo.getRefundId())) {
					queryObj.put(FieldMapping.ID.getMongoField(), refundListRequestVo.getRefundId());
				}
			}
			
			if (!EmptyChecker.isEmpty(refundListRequestVo.getStatus())){
				queryObj.put(FieldMapping.REFUND_STATE.getMongoField(), refundListRequestVo.getStatus());
			}

			if (refundListRequestVo.getPageVo().getPageNumber() == 0 || refundListRequestVo.getPageVo().getPageSize() == 0) {
				refundHisList = refundSupport.findListByDBObject(queryObj);
				if (refundHisList != null){
					pageVo.setPageCount(refundHisList.size());
				}
				pageVo.setPageNumber(1);
				pageVo.setPageSize(1);
				pageVo.setTotalCount(pageVo.getPageCount());
			} else {
				mongoPage = new MongoPage();
				mongoPage.setPageNumber(refundListRequestVo.getPageVo().getPageNumber());
				mongoPage.setLastPageNumber(refundListRequestVo.getPageVo().getLastPageNumber());
				if (refundListRequestVo.getPageVo().getFirstRecordTime() > 0){
					mongoPage.setFirstRecordTime(refundListRequestVo.getPageVo().getFirstRecordTime());
				}
				if (refundListRequestVo.getPageVo().getLastRecordTime() > 0) {
					mongoPage.setLastRecordTime(refundListRequestVo.getPageVo().getLastRecordTime());
				}
				mongoPage.setPageSize(refundListRequestVo.getPageVo().getPageSize());
				if (refundListRequestVo.getPageVo().getTotalCount() > 0){
					mongoPage.setTotalCount(refundListRequestVo.getPageVo().getTotalCount());
				}
				mongoPage = refundSupport.findPageByDBObject(queryObj, mongoPage);
				refundHisList = (List<RefundHistory>) mongoPage.getItems();
				pageVo.setPageCount(mongoPage.getPageCount());
				pageVo.setPageNumber(mongoPage.getPageNumber());
				pageVo.setPageSize(mongoPage.getPageSize());
				pageVo.setTotalCount(mongoPage.getTotalCount());
				pageVo.setLastPageNumber(pageVo.getPageNumber());
				pageVo.setHasNext(mongoPage.getHasNext());
				if (null != refundHisList && refundHisList.size() > 0){
					pageVo.setFirstRecordTime(refundHisList.get(0).getCreateTime());
					pageVo.setLastRecordTime(refundHisList.get(refundHisList.size() - 1).getCreateTime());
				}
			}
			responseVo.setPageVo(pageVo);
			
			if (null != refundHisList){
				for (int i = 0; i < refundHisList.size(); i++){
					refundHis = refundHisList.get(i);
					refundInfoVo = convertToVo(refundHis);
					refundInfoList.add(refundInfoVo);
				}
			}

			responseVo.setRefundInfoList(refundInfoList);

		} catch (Exception e){
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(e.getMessage());
			LogCvt.error(new StringBuffer("查询退款列表失败：").append(refundListRequestVo.toString()).toString(), e);
		}
		
		responseVo.setResultVo(resultVo);
		
		return responseVo;
	}

	/**
	 * 转换Mongo PO对象成VO对象
	 * 
	 * @param refundHis
	 * @return
	 */
	private RefundInfoVo convertToVo(RefundHistory refundHis){
		RefundInfoVo refundInfoVo = null;
		RefundPaymentInfo payInfo = null;
		double refundAmt = 0.0d;
		double refundPoint = 0.0d;
		List<RefundProductVo> productVoList = null;
		RefundProductVo productVo = null;
		RefundShoppingInfo shoppingInfo = null;
		RefundProduct product = null;
		List<RefundPaymentVo> payList = null;
		RefundPaymentVo payVo = null;
		
		refundInfoVo = new RefundInfoVo();
		refundInfoVo.setRefundId(refundHis.get_id());
		refundInfoVo.setOrderId(refundHis.getOrderId());
		refundInfoVo.setRequestTime(String.valueOf(refundHis.getCreateTime()));
		refundInfoVo.setRefundStatus(refundHis.getRefundState());
		refundInfoVo.setReason(refundHis.getReason());
		refundInfoVo.setClientId(refundHis.getClientId());
		refundInfoVo.setIsVipRefund(refundHis.getIsVipRefund() == null ? "0" : refundHis.getIsVipRefund().toString());
		// 退款支付信息
		payList = new ArrayList<RefundPaymentVo>();
		if (null != refundHis.getPaymentInfo()){
			for (int i = 0; i < refundHis.getPaymentInfo().size(); i++){
				payInfo = refundHis.getPaymentInfo().get(i);
				payVo = new RefundPaymentVo();
				
				if (payInfo.getType().equals(PaymentMethod.cash.getCode())){
					refundAmt = Double.parseDouble(String.valueOf(payInfo.getRefundValue()));
				} else if (payInfo.getType().equals(PaymentMethod.froadPoints.getCode())) {
					refundPoint = Double.parseDouble(String.valueOf(payInfo.getRefundValue()));
				} else if (payInfo.getType().equals(PaymentMethod.bankPoints.getCode())) {
					refundPoint = Double.parseDouble(String.valueOf(payInfo.getRefundValue()));
				}
				
				if (payInfo.getType().equals(PaymentMethod.cash.getCode())){
					if (refundHis.getRefundTime() != null) {
						payVo.setPaymentTime(String.valueOf(refundHis.getRefundTime()));
					} else {
						payVo.setPaymentTime("");
					}
				} else {
					payVo.setPaymentTime(String.valueOf(refundHis.getCreateTime()));
				}
				
				payVo.setPaymentId(payInfo.getPaymentId());
				payVo.setPaymentType(payInfo.getType());
				payVo.setRefundValue(String.valueOf(Arith.div(Double.parseDouble(String.valueOf(payInfo.getRefundValue())), 1000)));
				payList.add(payVo);
			}
		}
		refundInfoVo.setPayList(payList);
		refundInfoVo.setRefundAmount(Arith.div(refundAmt, 1000));
		refundInfoVo.setRefundPoints(Arith.div(refundPoint, 1000));
		refundInfoVo.setSubOrderId(refundHis.getShoppingInfo().get(0).getSubOrderId());
		refundInfoVo.setMerchantId(refundHis.getShoppingInfo().get(0).getMerchantId());
		refundInfoVo.setMerchantName(refundHis.getShoppingInfo().get(0).getMerchantName());
		refundInfoVo.setRefundTime(String.valueOf(refundHis.getRefundTime()));
		
		OrderMongo order = new OrderSupportImpl().getOrderById(refundInfoVo.getClientId(), refundInfoVo.getOrderId());
		boolean isNewRule = order.getCreateTime() > Const.POINT_NEW_RULE;
		// 退款商品信息
		productVoList = new ArrayList<RefundProductVo>();
		for (int j = 0; j < refundHis.getShoppingInfo().size(); j++){
			shoppingInfo = refundHis.getShoppingInfo().get(j);
			
			for (int k = 0; k < shoppingInfo.getProducts().size(); k++){
				product = shoppingInfo.getProducts().get(k);
				productVo = new RefundProductVo();
				productVo.setProductId(product.getProductId());
				productVo.setProductName(product.getProductName());
				productVo.setImage(product.getImageUrl());
				productVo.setQuantity(product.getQuantity());
				productVo.setPrice(Arith.div(product.getPrice(), 1000));
				if (product.getVipPrice() != null){
					productVo.setVipPrice(Arith.div(product.getVipPrice(), 1000));
				} else {
					productVo.setVipPrice(0);
				}
				if (product.getVipQuantity() != null){
					productVo.setVipQuantity(product.getVipQuantity());
				} else {
					productVo.setVipQuantity(0);
				}
				
			
				if(isNewRule){
					try {
						productVo.setPriceSum(Arith.div(product.getRefundTotalCash(),1000));
					} catch (Exception e) {
						productVo.setPriceSum(
								Arith.mul(productVo.getPrice(), (double)productVo.getQuantity())
								+
								Arith.mul(productVo.getVipPrice(), (double)productVo.getVipQuantity()));
					}
				
				}else{
					productVo.setPriceSum(
							Arith.mul(productVo.getPrice(), (double)productVo.getQuantity())
							+
							Arith.mul(productVo.getVipPrice(), (double)productVo.getVipQuantity()));
				}
				
				
				productVo.setMerchantId(refundInfoVo.getMerchantId());
				productVo.setMerchantName(refundInfoVo.getMerchantName());
				productVoList.add(productVo);
			}
		}
		refundInfoVo.setProductList(productVoList);
		
		return refundInfoVo;
	}
}
