/**
 * Project Name:froad-cbank-server-boss
 * File Name:BossQueryRefundLogicImpl.java
 * Package Name:com.froad.logic.impl
 * Date:2015年9月1日下午4:45:30
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.db.mongo.BossCommonMongo;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.FieldMapping;
import com.froad.enums.PaymentMethod;
import com.froad.enums.RefundState;
import com.froad.enums.ResultCode;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.BossQueryRefundLogic;
import com.froad.logic.CommonLogic;
import com.froad.po.Client;
import com.froad.po.mongo.RefundHistory;
import com.froad.po.mongo.RefundPaymentInfo;
import com.froad.po.mongo.RefundProduct;
import com.froad.po.mongo.RefundShoppingInfo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.refund.BossQueryRefundDetailRes;
import com.froad.thrift.vo.refund.BossQueryRefundInfoVo;
import com.froad.thrift.vo.refund.BossQueryRefundOrderListReq;
import com.froad.thrift.vo.refund.BossQueryRefundOrderListRes;
import com.froad.thrift.vo.refund.BossQueryRefundPaymentVo;
import com.froad.thrift.vo.refund.BossQueryRefundProductVo;
import com.froad.util.Arith;
import com.froad.util.DateUtil;
import com.froad.util.EmptyChecker;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * ClassName:BossQueryRefundLogicImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月1日 下午4:45:30
 * @author   kevin
 * @version  
 * @see 	 
 */
public class BossQueryRefundLogicImpl implements BossQueryRefundLogic {
	
	
	private BossCommonMongo mongoCommon = new BossCommonMongo();
	
	private CommonLogic commonLogic = new CommonLogicImpl();
	

	public BossQueryRefundOrderListRes getRefundList(BossQueryRefundOrderListReq refundListRequestVo, String flag) {
	//	RefundSupport refundSupport = null;
		String source = null;
		BossQueryRefundOrderListRes responseVo = null;
		PageVo pageVo = null;
		ResultVo resultVo = null;
		MongoPage mongoPage = null;
		List<RefundHistory> refundHisList = null;
		RefundHistory refundHis = null;
		List<BossQueryRefundInfoVo> refundInfoList = null;
		BossQueryRefundInfoVo refundInfoVo = null;
		DBObject queryObj = null;
		responseVo = new BossQueryRefundOrderListRes();
		resultVo = new ResultVo();
		queryObj = new BasicDBObject();
		try {
			source = refundListRequestVo.getSource();
			
			//refundSupport = new RefundSupportImpl();
			pageVo = new PageVo();
			refundInfoList = new ArrayList<BossQueryRefundInfoVo>();
			
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
				refundHisList = mongoCommon.findRefundListByDBObject(queryObj);
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
				if("1".equals(flag)) {
					mongoPage = mongoCommon.findPageForExport(queryObj, mongoPage, MongoTableName.CB_ORDER_REFUNDS, RefundHistory.class);
				} else {
					mongoPage = mongoCommon.findRefundPageByDBObject(queryObj, mongoPage);
				}
				
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
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			responseVo.setResultVo(resultVo);
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
	private BossQueryRefundInfoVo convertToVo(RefundHistory refundHis){
		BossQueryRefundInfoVo refundInfoVo = null;
		RefundPaymentInfo payInfo = null;
		double refundAmt = 0.0d;
		double refundPoint = 0.0d;
		List<BossQueryRefundProductVo> productVoList = null;
		BossQueryRefundProductVo productVo = null;
		RefundShoppingInfo shoppingInfo = null;
		RefundProduct product = null;
		List<BossQueryRefundPaymentVo> payList = null;
		BossQueryRefundPaymentVo payVo = null;
		
		refundInfoVo = new BossQueryRefundInfoVo();
		refundInfoVo.setRefundId(refundHis.get_id());
		refundInfoVo.setOrderId(refundHis.getOrderId());
		refundInfoVo.setRequestTime(refundHis.getCreateTime());
		refundInfoVo.setRefundStatus(refundHis.getRefundState());
		refundInfoVo.setReason(refundHis.getReason());
		refundInfoVo.setClientId(refundHis.getClientId());
		
		// 退款支付信息
		payList = new ArrayList<BossQueryRefundPaymentVo>();
		if (null != refundHis.getPaymentInfo()){
			for (int i = 0; i < refundHis.getPaymentInfo().size(); i++){
				payInfo = refundHis.getPaymentInfo().get(i);
				payVo = new BossQueryRefundPaymentVo();
				
				if (payInfo.getType().equals(PaymentMethod.cash.getCode())){
					refundAmt = Double.parseDouble(String.valueOf(payInfo.getRefundValue()));
				} else if (payInfo.getType().equals(PaymentMethod.froadPoints.getCode())) {
					refundPoint = Double.parseDouble(String.valueOf(payInfo.getRefundValue()));
				} else if (payInfo.getType().equals(PaymentMethod.bankPoints.getCode())) {
					refundPoint = Double.parseDouble(String.valueOf(payInfo.getRefundValue()));
				}
				
				if (payInfo.getType().equals(PaymentMethod.cash.getCode())){
					if (refundHis.getRefundTime() != null) {
						payVo.setPaymentTime(refundHis.getRefundTime());
					} else {
						payVo.setPaymentTime(0);
					}
				} else {
					payVo.setPaymentTime(refundHis.getCreateTime());
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
		refundInfoVo.setRefundTime(refundHis.getRefundTime() != null? refundHis.getRefundTime() : 0);
		
		// 退款商品信息
		productVoList = new ArrayList<BossQueryRefundProductVo>();
		if(refundHis.getShoppingInfo() != null) {
			for (int j = 0; j < refundHis.getShoppingInfo().size(); j++){
				shoppingInfo = refundHis.getShoppingInfo().get(j);
				if(shoppingInfo != null) {
					for (int k = 0; k < shoppingInfo.getProducts().size(); k++){
						product = shoppingInfo.getProducts().get(k);
						productVo = new BossQueryRefundProductVo();
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
						productVo.setPriceSum(Arith.mul(productVo.getPrice(), (double)productVo.getQuantity()) 
										+ Arith.mul(productVo.getVipPrice(), (double)productVo.getVipQuantity()));
						productVo.setMerchantId(refundInfoVo.getMerchantId());
						productVo.setMerchantName(refundInfoVo.getMerchantName());
						productVoList.add(productVo);
					}
				}
			}
		}
		
		refundInfoVo.setProductList(productVoList);
		
		return refundInfoVo;
	}


	@Override
	public BossQueryRefundDetailRes getRefundDetail(String refundId) {
		RefundHistory refundHis = null;
		BossQueryRefundDetailRes responseVo = null;
		BossQueryRefundInfoVo refundInfoVo = null;
		ResultVo resultVo = null;
		responseVo = new BossQueryRefundDetailRes();
		resultVo = new ResultVo();
		try {
			refundHis = mongoCommon.getByRefundId(refundId);
			if (null != refundHis){
				refundInfoVo = convertToVo(refundHis);
			} else {
				refundInfoVo = new BossQueryRefundInfoVo();
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


	@Override
	public ExportResultRes exportRefundOrderList(BossQueryRefundOrderListReq req) {
		ResultVo rb =  new ResultVo();
		ExportResultRes resultRes = new ExportResultRes();
		List<String> header = new ArrayList<String>();
		header.add("退款编号");
		header.add("订单编号");
		header.add("商户");
		header.add("所属银行");
		header.add("退款积分");
		header.add("退款金额");
		header.add("申请时间");
		header.add("退款时间");
		header.add("退款状态");
		List<List<String>> data = new ArrayList<List<String>>();
		Map<String, Client> clientMap = new HashMap<String, Client>();
		int pageNo = 0;
		int pageSize = 20000;
		PageVo pageVo = new PageVo();
		pageVo.setPageSize(pageSize);
		List<BossQueryRefundInfoVo> list = null;
		BossQueryRefundOrderListRes res = null;
		String url = null;
		try {
			
			ExcelWriter excelWriter = new ExcelWriter(pageSize);
			boolean isSuccess = true;
			do {
				pageNo++;
				pageVo.setPageNumber(pageNo);
				req.setPageVo(pageVo);
				res = getRefundList(req, "1");
				if(res != null) {
					list = res.getRefundInfoList();
					
					data = convertExportCollection(list, clientMap);
					
					try {
						excelWriter.write(header, data, "退款订单列表", "退款订单列表");
					} catch (Exception e) {
						LogCvt.error("导出退款订单列表失败", e);
						isSuccess = false;
						break;
					}
					
					pageVo =res.getPageVo();
				}
			} while(res != null && (list != null && list.size() >= pageSize));
			if(isSuccess) {
				url = excelWriter.getUrl();
				if(StringUtils.isNotEmpty(url)) {
					rb.setResultCode(ResultCode.success.getCode());
					rb.setResultDesc(ResultCode.success.getMsg());
				} else {
					rb.setResultCode(ResultCode.failed.getCode());
					rb.setResultDesc(ResultCode.failed.getMsg());
				}
			} else {
				rb.setResultCode(ResultCode.failed.getCode());
				rb.setResultDesc(ResultCode.failed.getMsg());
			}
		} catch (Exception e) {
			LogCvt.error("导出出错", e);
			rb.setResultCode(ResultCode.failed.getCode());
			rb.setResultDesc(ResultCode.failed.getMsg());
		}
		resultRes.setResultVo(rb);
		resultRes.setUrl(url);
		return resultRes;
	}

	private List<List<String>> convertExportCollection(List<BossQueryRefundInfoVo> list, Map<String, Client> clientMap) {
		List<List<String>> perList = new ArrayList<List<String>>();
		
		if(list == null || list.size() == 0) {
			return perList;
		}
		
		for(BossQueryRefundInfoVo vo : list) {
			List<String> rowList = new ArrayList<String>();
			rowList.add(StringUtils.isNotEmpty(vo.getRefundId())? vo.getRefundId() : "--");
			rowList.add(StringUtils.isNotEmpty(vo.getOrderId())? vo.getOrderId() : "--");
			rowList.add(StringUtils.isNotEmpty(vo.getMerchantName()) ? vo.getMerchantName() : "--");
			Client client = clientMap.get(vo.getClientId());
			if(client == null) {
				client = commonLogic.getClientById(vo.getClientId());
				clientMap.put(vo.getClientId(), client);
			} 
			if(client != null && StringUtils.isNotEmpty(client.getBankName())) {
				rowList.add(client.getBankName());
			} else {
				rowList.add("--");
			}
			double point = vo.getRefundPoints();
			rowList.add(point + "");
			double amount = vo.getRefundAmount();
			rowList.add(amount + "");
			if (vo.getRequestTime() != 0) {
				rowList.add(DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(vo.getRequestTime())));
			} else {
				rowList.add("--");
			}
			if (vo.getRefundTime() != 0) {
				rowList.add(DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(vo.getRefundTime())));
			} else {
				rowList.add("--");
			}
			RefundState state = getRefundState(vo.getRefundStatus());
			if(state != null) {
				rowList.add(state.getDescription());
			} else {
				rowList.add("--");
			}
			perList.add(rowList);
		}
		return perList;
		
	}
	
	public static RefundState[] states = null;
	public RefundState getRefundState (String state) {
		if(states == null) {
			states = RefundState.values();
		}
		for(RefundState r : states) {
			if(r.getCode().equals(state)) {
				return r;
			}
		}
		return null;
	}
	
}
	 
