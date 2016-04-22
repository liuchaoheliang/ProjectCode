/**
 * Project Name:froad-cbank-server-boss
 * File Name:BossQueryPaymentLogicImpl.java
 * Package Name:com.froad.logic.impl
 * Date:2015年9月1日下午5:44:44
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.db.mongo.BossCommonMongo;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.PayType;
import com.froad.enums.PaymentMethod;
import com.froad.enums.PaymentReason;
import com.froad.enums.PaymentStatus;
import com.froad.enums.ResultCode;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.BossQueryPaymentLogic;
import com.froad.po.PaymentMongoEntity;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.payment.BossQueryPaymentListRes;
import com.froad.thrift.vo.payment.BossQueryPaymentVo;
import com.froad.util.Arith;
import com.froad.util.DateUtil;
import com.froad.util.MongoTableName;
import com.froad.util.payment.Const;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * ClassName:BossQueryPaymentLogicImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月1日 下午5:44:44
 * @author   kevin
 * @version  
 * @see 	 
 */
public class BossQueryPaymentLogicImpl implements BossQueryPaymentLogic {
	
	private MongoManager mongo = new MongoManager();
	
	private BossCommonMongo mongoCommon = new BossCommonMongo();

	@Override
	public BossQueryPaymentListRes getPaymentList(BossQueryPaymentVo paymentQueryVo, String flag) {
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
		
		if("1".equals(flag)) {
			mongoPage = mongo.findByPageForExport(mongoPage, queryObj, new BasicDBObject(), MongoTableName.CB_PAYMENT,PaymentMongoEntity.class);
		} else {
			mongoPage = mongo.findByPageWithRedis(mongoPage, queryObj,MongoTableName.CB_PAYMENT,PaymentMongoEntity.class);
			
		}
		
		
		List<PaymentMongoEntity> datas = mongoPage.getItems() == null ? null : (List<PaymentMongoEntity>)mongoPage.getItems();
		List<BossQueryPaymentVo> list = new ArrayList<BossQueryPaymentVo>();
		if(datas != null){
			for (PaymentMongoEntity entity : datas) {
				list.add(loadyPaymentQueryVo(entity));
			}
		}
		
		BossQueryPaymentListRes pageQueryVo = new BossQueryPaymentListRes();
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
	
	
	
	public ExportResultRes exportPaymentList(BossQueryPaymentVo req){
		ResultVo rb =  new ResultVo();
		ExportResultRes resultRes = new ExportResultRes();
		List<String> header = new ArrayList<String>();
		header.add("订单编号");
		header.add("支付编号");
		header.add("支付登陆账号");
		header.add("金额");
		header.add("支付方式");
		header.add("支付时间");
		header.add("支付流水类型");
		header.add("积分兑换比例");
		header.add("账单号");
		header.add("支付状态");
		List<List<String>> data = new ArrayList<List<String>>();
		int pageNo = 0;
		int pageSize = 20000;
		PageVo pageVo = req.getPageVo();
		pageVo.setPageSize(pageSize);
		List<BossQueryPaymentVo> list = null;
		BossQueryPaymentListRes res = null;
		String url = null;
		try {
			
			ExcelWriter excelWriter = new ExcelWriter(pageSize);
			boolean isSuccess = true;
			do {
				pageNo++;
				pageVo.setPageNumber(pageNo);
				req.setPageVo(pageVo);
				res = getPaymentList(req, "1");
				if(res != null) {
					list = res.getPaymentQueryVos();
					
					data = convertExportCollection(list);
					
					try {
						excelWriter.write(header, data, "支付订单列表", "支付订单列表");
					} catch (Exception e) {
						LogCvt.error("导出支付订单列表失败", e);
						isSuccess = false;
						break;
					}
					
					pageVo = res.getPageVo();
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
			LogCvt.error("导出", e);
			rb.setResultCode(ResultCode.failed.getCode());
			rb.setResultDesc(ResultCode.failed.getMsg());
		}
		resultRes.setResultVo(rb);
		resultRes.setUrl(url);
		return resultRes;
	}
	
	
	private List<List<String>> convertExportCollection(List<BossQueryPaymentVo> list) {
		List<List<String>> perList = new ArrayList<List<String>>();
		
		if(list == null || list.size() == 0) {
			return perList;
		}
		
		for(BossQueryPaymentVo vo : list) {
			List<String> rowList = new ArrayList<String>();
			
			rowList.add(StringUtils.isNotEmpty(vo.getOrderId())? vo.getOrderId() : "--");
			rowList.add(StringUtils.isNotEmpty(vo.getPaymentId())? vo.getPaymentId() : "--");
			rowList.add(StringUtils.isNotEmpty(vo.getFromUserName())? vo.getFromUserName() : "--");
			rowList.add(vo.getPaymentValue()+"");
			if(PayType.cash.code == vo.getPaymentType()){
				rowList.add(PaymentMethod.cash.getDescribe());
			}else if(PayType.bankPoint.code == vo.getPaymentType()){
				rowList.add(PaymentMethod.bankPoints.getDescribe());
			}else if(PayType.froadPoint.code == vo.getPaymentType()){
				rowList.add(PaymentMethod.froadPoints.getDescribe());
			}else{
				rowList.add("--");
			}
			if (vo.getCreateTime() != 0) {
				rowList.add(DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(vo.getCreateTime())));
			} else {
				rowList.add("--");
			}
			
			if(PaymentReason.auto_refund.getCode().equals(vo.getPaymentReason())) {
				rowList.add(PaymentReason.auto_refund.getDescribe());
			} else if(PaymentReason.pay_point.getCode().equals(vo.getPaymentReason())) {
				rowList.add(PaymentReason.pay_point.getDescribe());
			} else if(PaymentReason.payment.getCode().equals(vo.getPaymentReason())) {
				rowList.add(PaymentReason.payment.getDescribe());
			} else if(PaymentReason.refund.getCode().equals(vo.getPaymentReason())) {
				rowList.add(PaymentReason.refund.getDescribe());
			} else if(PaymentReason.refund_point.getCode().equals(vo.getPaymentReason())) {
				rowList.add(PaymentReason.refund_point.getDescribe());
			} else if(PaymentReason.settle.getCode().equals(vo.getPaymentReason())) {
				rowList.add(PaymentReason.settle.getDescribe());
			} else {
				rowList.add("--");
			}
			rowList.add(vo.getPointRate() == 0 ? "--" : "1:" + vo.getPointRate());
			rowList.add(StringUtils.isNotEmpty(vo.getBillNo())? vo.getBillNo() : "--");
			if(PaymentStatus.pay_fail.getCode().equals(vo.getPaymentStatus())) {
				rowList.add(PaymentStatus.pay_fail.getDescribe());
			} else if(PaymentStatus.pay_request_fail.getCode().equals(vo.getPaymentStatus())) {
				rowList.add(PaymentStatus.pay_request_fail.getDescribe());
			} else if(PaymentStatus.pay_request_success.getCode().equals(vo.getPaymentStatus())) {
				rowList.add(PaymentStatus.pay_request_success.getDescribe());
			} else if(PaymentStatus.pay_success.getCode().equals(vo.getPaymentStatus())) {
				rowList.add(PaymentStatus.pay_success.getDescribe());
			} else if(PaymentStatus.pay_wait.getCode().equals(vo.getPaymentStatus())) {
				rowList.add(PaymentStatus.pay_wait.getDescribe());
			} else {
				rowList.add("--");
			}
			perList.add(rowList);
		}
		return perList;
	}
	
	
	
	
	public static BossQueryPaymentVo loadyPaymentQueryVo(PaymentMongoEntity mongoEntity){
		if(mongoEntity == null){
			return null;
		}
		BossQueryPaymentVo paymentQueryVo = new BossQueryPaymentVo();
		paymentQueryVo.setId(mongoEntity.getId() == null ? 0 : mongoEntity.getId());
		paymentQueryVo.setCreateTime(mongoEntity.getCreate_time() != null ? mongoEntity.getCreate_time().getTime() : 0);
		paymentQueryVo.setPaymentId(mongoEntity.getPayment_id());
		paymentQueryVo.setClientId(mongoEntity.getClient_id());
		paymentQueryVo.setOrderId(mongoEntity.getOrder_id());
		paymentQueryVo.setBillNo(mongoEntity.getBill_no());
		paymentQueryVo.setPaymentType(mongoEntity.getPayment_type());
		paymentQueryVo.setPaymentValue(mongoEntity.getPayment_value() == null ? 0d : Arith.div(mongoEntity.getPayment_value(),Const.HDOP_1000));
		paymentQueryVo.setPaymentTypeDetails(mongoEntity.getPayment_type_details() == null ? -1 : mongoEntity.getPayment_type_details());
		paymentQueryVo.setStep(mongoEntity.getStep());
		paymentQueryVo.setIsEnable(mongoEntity.getIs_enable());
		paymentQueryVo.setPaymentStatus(mongoEntity.getPayment_status());
		paymentQueryVo.setPaymentOrgNo(mongoEntity.getPayment_org_no());
		paymentQueryVo.setFromAccountName(mongoEntity.getFrom_account_name());
		paymentQueryVo.setFromAccountNo(mongoEntity.getFrom_account_no());
		paymentQueryVo.setToAccountName(mongoEntity.getTo_account_name());
		paymentQueryVo.setToAccountNo(mongoEntity.getTo_account_no());
		paymentQueryVo.setFromPhone(mongoEntity.getFrom_phone());
		paymentQueryVo.setToPhone(mongoEntity.getTo_phone());
		paymentQueryVo.setFromUserName(mongoEntity.getFrom_user_name());
		paymentQueryVo.setToUserName(mongoEntity.getTo_user_name());
		paymentQueryVo.setResultCode(mongoEntity.getResult_code());
		paymentQueryVo.setResultDesc(mongoEntity.getResult_desc());
		paymentQueryVo.setRemark(mongoEntity.getRemark());
		paymentQueryVo.setAutoRefund(mongoEntity.getAuto_refund());
		paymentQueryVo.setPointRate(mongoEntity.getPoint_rate() == null ? 0 : mongoEntity.getPoint_rate());
		paymentQueryVo.setPaymentReason(mongoEntity.getPayment_reason());
		paymentQueryVo.setIsDisposeException(mongoEntity.getIs_dispose_exception());
		paymentQueryVo.setRefundPointsBillNo(mongoEntity.getRefund_points_bill_no());
		return paymentQueryVo;
	}

}
