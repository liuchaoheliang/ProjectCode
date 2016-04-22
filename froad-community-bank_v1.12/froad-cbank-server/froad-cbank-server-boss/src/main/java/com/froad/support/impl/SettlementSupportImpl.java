/**
 * Project Name:froad-cbank-server-boss
 * File Name:SettlementSupportImpl.java
 * Package Name:com.froad.support.impl
 * Date:2015年8月6日下午3:06:04
 * Copyright (c) 2015, F-Road, Inc.
 */
package com.froad.support.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.enums.FieldMapping;
import com.froad.enums.SettlementStatus;
import com.froad.po.PaymentMongoEntity;
import com.froad.po.settlement.Settlement;
import com.froad.po.settlement.SettlementDto;
import com.froad.po.settlement.SettlementReq;
import com.froad.support.SettlementSupport;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * ClassName: SettlementSupportImpl <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月6日 下午3:06:04
 * 
 * @author zachary.wang
 * @version
 * @see
 */
public class SettlementSupportImpl implements SettlementSupport {

	private MongoManager mongo = new MongoManager();
	
	private static Map<String, String> billNoMap = new HashMap<String, String>();
	
	@SuppressWarnings("unchecked")
	@Override
	public MongoPage queryByPage(SettlementReq req) {
		MongoPage page = req.getPage();
		page.setSort(new Sort("create_time", OrderBy.DESC));

		DBObject dbo = new BasicDBObject();
		// 订单编号
		if (StringUtils.isNotEmpty(req.getOrderId())) {
			dbo.put("order_id", req.getOrderId());
		}

		// 账单编号
		String billNo = req.getBillNo();
		if (StringUtils.isNotBlank(billNo)) {
			PaymentMongoEntity pay = mongo.findOne(new BasicDBObject(FieldMapping.BILL_NO.getMongoField(), billNo),
					MongoTableName.CB_PAYMENT, PaymentMongoEntity.class);
			if (pay == null || StringUtils.isBlank(pay.getPayment_id())) {// 根据订单号查不到直接返回
				return page;
			}
			dbo.put(FieldMapping.PAYMENT_ID.getMongoField(), pay.getPayment_id());
		}

		// 客户端ID
		if (StringUtils.isNotEmpty(req.getClientId())) {
			dbo.put("client_id", req.getClientId());
		}

		// 开始时间&&结束时间
		if (req.getBegDate() > 0 || req.getEndDate() > 0) {
			if (req.getBegDate() > 0 && req.getEndDate() > 0) {
				dbo.put("create_time",
						new BasicDBObject(QueryOperators.GTE, req.getBegDate()).append(QueryOperators.LTE,
								req.getEndDate()));
			} else if (req.getBegDate() > 0) {
				dbo.put("create_time", new BasicDBObject(QueryOperators.GTE, req.getBegDate()));
			} else if (req.getEndDate() > 0) {
				dbo.put("create_time", new BasicDBObject(QueryOperators.LTE, req.getEndDate()));
			}
		}

		// 商户名称
		if (StringUtils.isNotEmpty(req.getMerchantName())) {
			Pattern pattern = Pattern.compile("^.*" + req.getMerchantName() + ".*$", Pattern.CASE_INSENSITIVE);
			dbo.put("merchant_name", pattern);
		}

		// 门店名称
		if (StringUtils.isNotEmpty(req.getOutletName())) {
			Pattern pattern = Pattern.compile("^.*" + req.getOutletName() + ".*$", Pattern.CASE_INSENSITIVE);
			dbo.put("outlet_name", pattern);
		}

		// 商品名称
		if (StringUtils.isNotEmpty(req.getProductName())) {
			Pattern pattern = Pattern.compile("^.*" + req.getProductName() + ".*$", Pattern.CASE_INSENSITIVE);
			dbo.put("product_name", pattern);
		}
		
		// 结算状态
		if (req.getType() != null) {
			dbo.put("settle_state", req.getType().getCode());
		} else {
			dbo.put("settle_state",
					new BasicDBObject(QueryOperators.NE, SettlementStatus.settlementNoInvalid.getCode()));
		}
		
		MongoPage rsPage = mongo.findByPageWithRedis(page, dbo,  MongoTableName.CB_SETTLEMENT, Settlement.class);
		if(rsPage != null ) {
			//获取结算账单号
			List<Settlement> list = (List<Settlement>)rsPage.getItems();
			if(list != null && list.size() > 0) {
				if(StringUtils.isNotBlank(billNo)) {//如果结算账单号是查询条件
					List<SettlementDto> listDto = new ArrayList<SettlementDto>();
					for(Settlement st : list) {
						SettlementDto sdto = converts(st);
						sdto.setBillNo(billNo);
						listDto.add(sdto);
						rsPage.setItems(listDto);
					}
				} else {
					List<SettlementDto> listDto = new ArrayList<SettlementDto>();
					//根据paymentId去支付表中反查结算账单号
					List<String> paymentIdList = new ArrayList<String>();
					Set<String> paymentIdSet = new HashSet<String>();
					for(Settlement st : list) {
						//paymentIdList.add(st.getPaymentId());
						paymentIdSet.add(st.getPaymentId());
					}
					paymentIdList.addAll(paymentIdSet);
					
					if (paymentIdList.size() > 0) {
						Collection<String> keysColl = new ArrayList<String>();
						keysColl.add(FieldMapping.PAYMENT_ID.getMongoField());
						keysColl.add(FieldMapping.BILL_NO.getMongoField());
						List<PaymentMongoEntity> paymentList = (List<PaymentMongoEntity>)mongo.findAll(new BasicDBObject(FieldMapping.PAYMENT_ID.getMongoField(),new BasicDBObject(QueryOperators.IN,paymentIdList)), keysColl, MongoTableName.CB_PAYMENT, PaymentMongoEntity.class);
						if (paymentList != null && paymentList.size() > 0) {
							for (PaymentMongoEntity payment : paymentList) {
								billNoMap.put(payment.getPayment_id(), payment.getBill_no());
							}
						}
					}
					
					String paymentId = null;
					for(Settlement st : list) {
						SettlementDto sdto = converts(st);
						paymentId = sdto.getPaymentId();
						String billno = billNoMap.get(paymentId);
						sdto.setBillNo(billno);
						listDto.add(sdto);
						rsPage.setItems(listDto);
					}
				}
			}
		}
		return rsPage;
	}
	
	private SettlementDto converts(Settlement st) {
		SettlementDto sdto = new SettlementDto();
		sdto.setClientId(st.getClientId());
		sdto.setCreateTime(st.getCreateTime());
		sdto.setId(st.getId());
		sdto.setMerchantId(st.getMerchantId());
		sdto.setMerchantName(st.getMerchantName());
		sdto.setMerchantUserId(st.getMerchantUserId());
		sdto.setMoney(st.getMoney());
		sdto.setOrderId(st.getOrderId());
		sdto.setOutletId(st.getOutletId());
		sdto.setOutletName(st.getOutletName());
		sdto.setPaymentId(st.getPaymentId());
		sdto.setProductCount(st.getProductCount());
		sdto.setProductId(st.getProductId());
		sdto.setProductName(st.getProductName());
		sdto.setRemark(st.getRemark());
		sdto.setSettlementId(st.getSettlementId());
		sdto.setSettleState(st.getSettleState());
		sdto.setSubOrderId(st.getSubOrderId());
		sdto.setTickets(st.getTickets());
		sdto.setType(st.getType());
		return sdto;
	}

	@Override
	public Settlement queryById(String settlementId) {
		DBObject where = new BasicDBObject();
		where.put("_id", JSonUtil.toObjectId(settlementId));
		return mongo.findOne(where, MongoTableName.CB_SETTLEMENT, Settlement.class);
	}

	@Override
	public boolean upateById(String settlementId, String status, String remark) {
		DBObject where = new BasicDBObject();
		where.put("_id",JSonUtil.toObjectId(settlementId));
		
		DBObject value = new BasicDBObject();
		if(remark != null){
			value.put("remark", remark);
		}
		value.put("settle_state", status);
		
		return mongo.update(value, where, MongoTableName.CB_SETTLEMENT, "$set") != -1;
	}
	

}
