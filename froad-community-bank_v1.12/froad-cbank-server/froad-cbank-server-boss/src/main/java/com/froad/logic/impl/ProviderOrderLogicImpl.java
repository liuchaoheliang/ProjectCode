/**
 * Project Name:froad-cbank-server-boss-1.8.0-SNAPSHOT
 * File Name:ProviderOrderLogic.java
 * Package Name:com.froad.logic.impl
 * Date:2015年11月25日下午4:06:12
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mongo.BossCommonMongo;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.AreaMapper;
import com.froad.db.mysql.mapper.DeliveryCorpMapper;
import com.froad.db.mysql.mapper.WayBillCommonMapper;
import com.froad.enums.DeliveryCorpCodeEnum;
import com.froad.enums.FieldMapping;
import com.froad.enums.ResultCode;
import com.froad.enums.ShippingState;
import com.froad.enums.ShippingStatus;
import com.froad.enums.SubOrderType;
import com.froad.enums.WayBillStatus;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.ProviderOrderLogic;
import com.froad.po.Area;
import com.froad.po.Client;
import com.froad.po.DeliveryCorp;
import com.froad.po.ProviderOrderQuery;
import com.froad.po.ShippingExcelInfo;
import com.froad.po.Waybill;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.RecvInfo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.util.Arith;
import com.froad.util.BossUtil;
import com.froad.util.Checker;
import com.froad.util.WaybillUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * ClassName:ProviderOrderLogicImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月25日 下午4:06:12
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class ProviderOrderLogicImpl implements ProviderOrderLogic {

	@Override
	public MongoPage queryProviderOrderInfoByPage(DBObject reqQuery, MongoPage page)
			throws Exception {
		SqlSession session = null;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			AreaMapper areaMapper = session.getMapper(AreaMapper.class);
			WayBillCommonMapper waybillCommonMapper = session.getMapper(WayBillCommonMapper.class);
			DeliveryCorpMapper deliveryCorpMapper = session.getMapper(DeliveryCorpMapper.class);
			BossCommonMongo mongo = new BossCommonMongo();
			CommonLogic commonLogic = new CommonLogicImpl();
			
			String phone = null;
			//收货人地址列表
			List<OrderMongo> orderList = new ArrayList<OrderMongo>();
			Map<String, OrderMongo> orderMap = new HashMap<String, OrderMongo>();
			List<RecvInfo> recvList = new ArrayList<RecvInfo>();
			Map<String, RecvInfo> recvMap = new HashMap<String, RecvInfo>();
			if(reqQuery.containsField("phone")){
				phone = reqQuery.get("phone").toString();
				DBObject orderQuery = convertToOrderQuery(phone, reqQuery);
				// 若有手机号,先从大订单查
				orderList = mongo.queryOrderInfoByCondition(orderQuery);
				if(Checker.isEmpty(orderList)){
					page.setItems(new ArrayList<ProviderOrderQuery>());
					return page;
				}
				for(OrderMongo o : orderList){
					orderMap.put(o.getOrderId(), o);
				}
				
				// 根据手机号查询收货地址信息
				recvList = mongo.queryRecvInfos(phone);
				for(RecvInfo r : recvList){
					recvMap.put(r.getRecvId(), r);
				}
			}
			
			// 其他条件从子订单查
			DBObject subwhere = convertToSubQuery(reqQuery, orderList);
			//按创建时间倒序
			Sort sort = new Sort(FieldMapping.CREATE_TIME.getMongoField(), OrderBy.DESC);
			page.setSort(sort);
			List<SubOrderMongo> subInfos = (List<SubOrderMongo>) mongo.querySubOrderInfoByPage(subwhere, page).getItems();
			subInfos = Checker.isNotEmpty(subInfos) ? subInfos : new ArrayList<SubOrderMongo>();
			ProviderOrderQuery p = null;
			OrderMongo order = null;
			RecvInfo recvInfo = null;
			String recvAddress = null;
			Waybill waybill = null;
			Client client = null;
			DeliveryCorp deliveryCorp = null;
			List<ProviderOrderQuery> resultList = new ArrayList<ProviderOrderQuery>();
			for(SubOrderMongo sub : subInfos){
				p = new ProviderOrderQuery();
				p.setOrderId(sub.getOrderId());
				p.setSubOrderId(sub.getSubOrderId());
				p.setOrderStatus(sub.getOrderStatus());
				p.setMemberCode(sub.getMemberCode());
				p.setCreateTime(new Date(sub.getCreateTime()));
				p.setShippingStatus(sub.getDeliveryState());
				
				client = commonLogic.getClientById(sub.getClientId());
				p.setClientId(sub.getClientId());
				p.setClientName(client.getBankName());
				
				Integer totalPrice = 0;
				StringBuilder productInfoStr = new StringBuilder();
				for(int i = 0; i < sub.getProducts().size(); i++){
					ProductMongo product = sub.getProducts().get(i);
					totalPrice += product.getDeliveryMoney() + (product.getQuantity() * product.getMoney()) + (product.getVipMoney() * product.getVipQuantity());
					if(i == sub.getProducts().size() - 1){
						productInfoStr.append(product.getProductName()).append("*").append(product.getQuantity()+product.getVipQuantity());
					}else{
						productInfoStr.append(product.getProductName()).append("*").append(product.getQuantity()+product.getVipQuantity()).append(",");
					}
				}
				p.setTotalPrice(Arith.div(totalPrice, 1000, 2));
				p.setProductInfo(productInfoStr.toString());
				
				if(Checker.isEmpty(phone)){
					order = mongo.findOrderByOrderId(sub.getOrderId());
					if(Checker.isNotEmpty(order.getPhone())){
						p.setPhone(order.getPhone());
					}
					
					if(Checker.isNotEmpty(order.getRecvId())){
						recvInfo = mongo.queryRecvInfoByRecvId(order.getRecvId());
						recvAddress = combineRecvAddress(recvInfo, areaMapper);
						p.setRecvAddress(recvAddress);
						p.setConsignee(Checker.isNotEmpty(recvInfo) ? recvInfo.getConsignee() : "");
					}
					
				}else{
					p.setPhone(phone);
					order = orderMap.get(sub.getOrderId());
					if(Checker.isNotEmpty(order) && Checker.isNotEmpty(order.getRecvId())){
						recvInfo = recvMap.get(order.getRecvId());
						recvAddress = combineRecvAddress(recvInfo, areaMapper);
						p.setRecvAddress(recvAddress);
						p.setConsignee(Checker.isNotEmpty(recvInfo) ? recvInfo.getConsignee() : "");
					}
				}
				
				// 物流信息
				waybill = waybillCommonMapper.findDeliveryWayBill(sub.getSubOrderId());
				if(Checker.isNotEmpty(waybill)){
					deliveryCorp = deliveryCorpMapper.findByCorpCode(waybill.getShippingCorpCode());
					p.setShippingCorpCode(Checker.isNotEmpty(deliveryCorp) ? deliveryCorp.getId().toString() : "");
					p.setShippingCorp(waybill.getShippingCorp());
					p.setShippingId(waybill.getShippingId());
					p.setInputTime(waybill.getCreateTime());
				}
				
				resultList.add(p);
			}
			
			page.setItems(resultList);
			
		} catch (Exception e) {
			LogCvt.error("供应商订单列表分页查询异常", e);
			throw e;
		} finally {
			if(session != null) session.close();
		}
		return page;
	}
	
	
	/**
	 * 
	 * convertToOrderQuery:(查询信息转换).
	 *
	 * @author huangyihao
	 * 2015年11月27日 上午10:13:21
	 * @param phone
	 * @param reqQuery
	 * @return
	 *
	 */
	private DBObject convertToOrderQuery(String phone, DBObject reqQuery){
		DBObject query = new BasicDBObject();
		query.put("phone", phone);
		if(reqQuery.containsField("order_id_like")){
			Pattern pattern = BossUtil.getFuzzyStrPattern(reqQuery.get("order_id_like").toString());
			query.put(FieldMapping.ID.getMongoField(), pattern);
		}
		if(reqQuery.containsField(FieldMapping.CLIENT_ID.getMongoField())){
			query.put(FieldMapping.CLIENT_ID.getMongoField(), reqQuery.get(FieldMapping.CLIENT_ID.getMongoField()));
		}
		if(reqQuery.containsField(FieldMapping.MEMBER_CODE.getMongoField())){
			query.put(FieldMapping.MEMBER_CODE.getMongoField(), reqQuery.get(FieldMapping.MEMBER_CODE.getMongoField()));
		}
		return query;
	}
	
	
	/**
	 * 
	 * convertToSubQuery:(查询信息转换).
	 *
	 * @author huangyihao
	 * 2015年11月26日 下午3:53:20
	 * @param reqMap
	 * @param orderList
	 * @return
	 *
	 */
	private DBObject convertToSubQuery(DBObject reqQuery, List<OrderMongo> orderList){
		DBObject subwhere = new BasicDBObject();
		
		subwhere.putAll(reqQuery);
		subwhere.removeField("phone");
		subwhere.removeField("order_id_like");
		subwhere.put(FieldMapping.TYPE.getMongoField(), SubOrderType.boutique.getCode());
		
		if(reqQuery.containsField("order_id_like")){
			Pattern pattern = BossUtil.getFuzzyStrPattern(reqQuery.get("order_id_like").toString());
			BasicDBList orderIdConditions = new BasicDBList();
			orderIdConditions.add(new BasicDBObject(FieldMapping.ORDER_ID.getMongoField(), pattern));
			orderIdConditions.add(new BasicDBObject(FieldMapping.SUB_ORDER_ID.getMongoField(), pattern));
			subwhere.put(QueryOperators.OR, orderIdConditions);
		}
		
		if(Checker.isNotEmpty(orderList)){
			List<String> orderIds = new ArrayList<String>();
			for(OrderMongo o : orderList){
				orderIds.add(o.getOrderId());
			}
			subwhere.put(FieldMapping.ORDER_ID.getMongoField(), new BasicDBObject(QueryOperators.IN, orderIds));
		}
		
		return subwhere;
	}

	
	/**
	 * 
	 * combineRecvAddress:(收货地址信息组合).
	 *
	 * @author huangyihao
	 * 2015年11月26日 下午3:53:41
	 * @param recvInfo
	 * @param areaMapper
	 * @return
	 *
	 */
	private String combineRecvAddress(RecvInfo recvInfo, AreaMapper areaMapper){
		String recvAddress = null;
		if(Checker.isNotEmpty(recvInfo) && Checker.isNotEmpty(recvInfo.getAreaId())){
			Area area = areaMapper.findById(Long.valueOf(recvInfo.getAreaId()));
			if(Checker.isNotEmpty(area)){
				String treePathName = area.getTreePathName().replace(",", " ");
				recvAddress = treePathName + " " + recvInfo.getAddress();
			}
		}
		return recvAddress;
	}
	

	
	@Override
	public List<ShippingExcelInfo> inputShippingInfo(List<ShippingExcelInfo> inputs) throws Exception {
		SqlSession session = null;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			WayBillCommonMapper waybillCommonMapper = session.getMapper(WayBillCommonMapper.class);
			DeliveryCorpMapper deliveryCorpMapper = session.getMapper(DeliveryCorpMapper.class);
			BossCommonMongo mongo = new BossCommonMongo();
			
			if(Checker.isEmpty(inputs)){
				return inputs;
			}
			
			Map<String, DeliveryCorp> corpMap = new HashMap<String, DeliveryCorp>();
			List<DeliveryCorp> corps = deliveryCorpMapper.findAllDeliveryCorp();
			for(DeliveryCorp d : corps){
				corpMap.put(d.getCorpCode(), d);
			}
			
			Iterator<ShippingExcelInfo> iterator = inputs.iterator();
			ShippingExcelInfo excelInfo = null;
			SubOrderMongo subOrder = null;
			Waybill waybill = null;
			Waybill updateWaybill = null;
			DeliveryCorp corp = null;
			boolean flag = false;
			//系统只读取交易编号、物流公司、物流单号、出货状态四列数据
			while(iterator.hasNext()){
				excelInfo = iterator.next();
				
				if(Checker.isEmpty(excelInfo.getOrderId())){
					continue;
				}
				
				if(Checker.isEmpty(excelInfo.getShippingStatusDesc()) || 
						ShippingStatus.unshipped.getDescribe().equals(excelInfo.getShippingStatusDesc())){
					// 如果为空（或者为未发货），则不处理
					continue;
				}
				
				if(ShippingStatus.shipping.getDescribe().equals(excelInfo.getShippingStatusDesc())){
					// 发货中 -- 更新子订单发货状态
					subOrder = mongo.findSubOrderBySubOrderId(excelInfo.getOrderId());
					if(Checker.isEmpty(subOrder)){
						continue;
					}
					
					flag = mongo.modifySubDeliveryState(subOrder.getClientId(), subOrder.getOrderId(), excelInfo.getOrderId(), ShippingStatus.shipping.getCode());
					if(!flag){
						continue;
					}
				}else if(ShippingStatus.shipped.getDescribe().equals(excelInfo.getShippingStatusDesc())){
					// 已发货 -- 更新子订单发货状态, 初始化/更新运单表信息
					
					if(Checker.isEmpty(excelInfo.getShippingCorp()) 
							|| Checker.isEmpty(excelInfo.getShippingId())){
						continue;
					}
					
					subOrder = mongo.findSubOrderBySubOrderId(excelInfo.getOrderId());
					if(Checker.isEmpty(subOrder)){
						continue;
					}
					
					DeliveryCorpCodeEnum corpCodeEnum = DeliveryCorpCodeEnum.getByName(excelInfo.getShippingCorp());
					if(Checker.isEmpty(corpCodeEnum)){
						continue;
					}else if(!corpMap.containsKey(corpCodeEnum.getCode())){
						continue;
					}else{
						corp = corpMap.get(corpCodeEnum.getCode());
					}
					
					// 物流公司录入文字（或物流公司编号）需与系统中配置的物流公司名（或编号）做比对，如未匹配的物流公司不予导入
					String shippingCorp = corp.getName();
					String shippingId = excelInfo.getShippingId();
					if(!WaybillUtil.verifyShippingId(DeliveryCorpCodeEnum.getByName(shippingCorp), shippingId)){
						continue;
					}
					
					Integer count = waybillCommonMapper.getCountByShippingId(shippingId);
					if(count > 0){
						continue;
					}
					
					waybill = waybillCommonMapper.findDeliveryWayBill(excelInfo.getOrderId());
					if(Checker.isEmpty(waybill)){
						waybill = new Waybill();
						waybill.setCreateTime(new Date());
						waybill.setSubOrderId(subOrder.getSubOrderId());
						waybill.setShippingCorpCode(corp.getCorpCode());
						waybill.setShippingCorp(shippingCorp);
						waybill.setShippingId(shippingId);
						waybill.setStatus(WayBillStatus.pick_up.getStatus());
						waybill.setShippingTime(new Date());
						waybill.setShippingState(ShippingState.unreceipted.getState());
						waybillCommonMapper.addDeliveryWayBill(waybill);
					}else{
						updateWaybill = new Waybill();
						updateWaybill.setId(waybill.getId());
						updateWaybill.setSubOrderId(waybill.getSubOrderId());
						updateWaybill.setUpdateTime(new Date());
						updateWaybill.setShippingCorpCode(corp.getCorpCode());
						updateWaybill.setShippingCorp(shippingCorp);
						updateWaybill.setShippingId(shippingId);
						updateWaybill.setStatus(WayBillStatus.in_transit.getStatus());
						updateWaybill.setShippingTime(new Date());
						waybillCommonMapper.updateWayBill(updateWaybill);
					}
					flag = mongo.modifySubDeliveryState(subOrder.getClientId(), subOrder.getOrderId(), subOrder.getSubOrderId(), ShippingStatus.shipped.getCode());
					if(!flag){
						continue;
					}
				}else{
					continue;
				}
				iterator.remove();
			}
			session.commit(true);
		} catch (Exception e) {
			LogCvt.error("导入物流信息异常", e);
			session.rollback(true);
			throw e;
		} finally {
			if(session != null) session.close();
		}
		return inputs;
	}

	
	@Override
	public Boolean shippingByOrderId(String orderId) throws Exception {
		Boolean flag = false;
		try {
			BossCommonMongo mongo = new BossCommonMongo();
			SubOrderMongo subOrder = mongo.findSubOrderBySubOrderId(orderId);
			if(Checker.isEmpty(subOrder)){
				return flag;
			}
			
			flag = mongo.modifySubDeliveryState(subOrder.getClientId(), subOrder.getOrderId(), orderId, ShippingStatus.shipping.getCode());
		} catch (Exception e) {
			LogCvt.error("出库异常", e);
			throw e;
		}
		return flag;
	}


	@Override
	public Boolean updateShippingInfo(Map<String, Object> reqMap) throws FroadBusinessException, Exception {
		Boolean flag = false;
		SqlSession session = null;
		try {
			if(!reqMap.containsKey(FieldMapping.SUB_ORDER_ID.getMongoField())
					|| !reqMap.containsKey("shipping_corp_code")
					|| !reqMap.containsKey("shipping_id")){
				throw new FroadBusinessException(ResultCode.notAllParameters.getCode(), ResultCode.notAllParameters.getMsg());
			}
			
			session = MyBatisManager.getSqlSessionFactory().openSession();
			WayBillCommonMapper waybillCommonMapper = session.getMapper(WayBillCommonMapper.class);
			DeliveryCorpMapper deliveryCorpMapper = session.getMapper(DeliveryCorpMapper.class);
			BossCommonMongo mongo = new BossCommonMongo();
			
			String subOrderId = reqMap.get(FieldMapping.SUB_ORDER_ID.getMongoField()).toString();
			String shippingCorpCode = reqMap.get("shipping_corp_code").toString();
			String shippingId = reqMap.get("shipping_id").toString();
			Date inputTime = null;
			if(reqMap.containsKey("input_time")){
				inputTime = new Date(Long.parseLong(reqMap.get("input_time").toString()));
			}else{
				inputTime = new Date();
			}
			
			DeliveryCorp corp = deliveryCorpMapper.findDeliveryCorpById(Long.valueOf(shippingCorpCode));
			String corpCode = corp.getCorpCode();
			String shippingCorp = corp.getName();
			if(!WaybillUtil.verifyShippingId(DeliveryCorpCodeEnum.getByCode(corpCode), shippingId)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "物流单号不符合规则");
			}
			
			SubOrderMongo subOrder = mongo.findSubOrderBySubOrderId(subOrderId);
			if(Checker.isEmpty(subOrder)){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "查无此订单");
			}
			
			Integer count = waybillCommonMapper.getCountByShippingId(shippingId);
			if(count > 0){
				throw new FroadBusinessException(ResultCode.failed.getCode(), "物流单号已存在");
			}
			
			Waybill waybill = waybillCommonMapper.findDeliveryWayBill(subOrderId);
			Waybill updateWaybill = null;
			if(Checker.isNotEmpty(waybill)){
				updateWaybill =  new Waybill();
				updateWaybill.setId(waybill.getId());
				updateWaybill.setSubOrderId(waybill.getSubOrderId());
				updateWaybill.setShippingCorpCode(corpCode);
				updateWaybill.setShippingCorp(shippingCorp);
				updateWaybill.setShippingId(shippingId);
				updateWaybill.setShippingTime(new Date());
				updateWaybill.setUpdateTime(inputTime);
				waybillCommonMapper.updateWayBill(updateWaybill);
			}else{
				waybill = new Waybill();
				waybill.setCreateTime(inputTime);
				waybill.setSubOrderId(subOrderId);
				waybill.setShippingCorpCode(corpCode);
				waybill.setShippingCorp(shippingCorp);
				waybill.setShippingId(shippingId);
				waybill.setStatus(WayBillStatus.pick_up.getStatus());
				waybill.setShippingTime(new Date());
				waybill.setShippingState(ShippingState.unreceipted.getState());
				waybillCommonMapper.addDeliveryWayBill(waybill);
			}
			
			flag = mongo.modifySubDeliveryState(subOrder.getClientId(), subOrder.getOrderId(), subOrderId, ShippingStatus.shipped.getCode());
			
			if(flag){
				session.commit(true);
			}
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			throw e;
		} catch (Exception e) {
			LogCvt.error("更新物流信息异常", e);
			session.rollback(true);
			throw e;
		} finally {
			if(session != null) session.close();
		}
		return flag;
	}
	
}
