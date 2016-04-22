package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mongo.BossCommonMongo;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.AreaMapper;
import com.froad.enums.FieldMapping;
import com.froad.enums.PaymentMethod;
import com.froad.enums.PaymentReason;
import com.froad.enums.ProductType;
import com.froad.enums.ResultCode;
import com.froad.enums.SubOrderType;
import com.froad.logback.LogCvt;
import com.froad.logic.BusinessOrderQueryLogic;
import com.froad.logic.CommonLogic;
import com.froad.po.Area;
import com.froad.po.Client;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.PaymentMongo;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.RecvInfo;
import com.froad.po.mongo.RefundHistory;
import com.froad.po.mongo.RefundPaymentInfo;
import com.froad.po.mongo.RefundProduct;
import com.froad.po.mongo.RefundShoppingInfo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.settlement.Settlement;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.MemberInformationService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.Business.BusinessOrderListReq;
import com.froad.thrift.vo.Business.BusinessOrderPyamentInfoRes;
import com.froad.thrift.vo.Business.BusinessOrderPyamentInfoVo;
import com.froad.thrift.vo.Business.BusinessOrderRefundInfoVo;
import com.froad.thrift.vo.Business.BusinessOrderRefundInfoVoRes;
import com.froad.thrift.vo.Business.BusinessOrderTradeInfoVo;
import com.froad.thrift.vo.Business.BusinessOrderTradeInfoVoRes;
import com.froad.thrift.vo.Business.BusinessOrderVo;
import com.froad.thrift.vo.member.MemberInfoVo;
import com.froad.util.Arith;
import com.froad.util.BeanUtil;
import com.froad.util.BossConstants;
import com.froad.util.BossUtil;
import com.froad.util.Checker;
import com.froad.util.MongoTableName;
import com.froad.util.Util;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class BusinessOrderQueryLogicImpl implements BusinessOrderQueryLogic{

	private final String CB_ORDER = MongoTableName.CB_ORDER;
	private final String CB_PAYMENT = MongoTableName.CB_PAYMENT;
	private final String CB_SUBORDER_PRODUCT = MongoTableName.CB_SUBORDER_PRODUCT;
	private final String CB_SETTLEMENT = MongoTableName.CB_SETTLEMENT;
	private final String CB_PRODUCT_DETAIL = MongoTableName.CB_PRODUCT_DETAIL;
	private final String CB_ORDER_REFUNDS = MongoTableName.CB_ORDER_REFUNDS;
	
	private MongoManager mongo = new MongoManager();
	
	private BossCommonMongo mongoCommon = new BossCommonMongo();
	
	private CommonLogic commonLogic = new CommonLogicImpl();
	
	@SuppressWarnings("unchecked")
	@Override
	public MongoPage getBusinessOrderList(BusinessOrderListReq req) {
		MongoPage page = new MongoPage();
		List<BusinessOrderVo> vos = new ArrayList<BusinessOrderVo>();
		try {
			page = (MongoPage) BeanUtil.copyProperties(MongoPage.class, req.getPageVo());
			Map<String, OrderMongo> orderMap = new HashMap<String, OrderMongo>();
			List<String> orderIds = new ArrayList<String>();
			if(Checker.isNotEmpty(req.getPaymentMethod())){
				DBObject orderQuery = new BasicDBObject();
				orderQuery.put("payment_method", req.getPaymentMethod());
				//订单号模糊查询
				if(Checker.isNotEmpty(req.getOrderId())){
					orderQuery.put(FieldMapping.ID.getMongoField(), BossUtil.getFuzzyStrPattern(req.getOrderId()));
				}
				orderQuery = setupQueryCondition(req, orderQuery);
				
				List<OrderMongo> orderList = mongoCommon.queryOrderInfoByCondition(orderQuery);
				if(Checker.isNotEmpty(orderList)){
					for(OrderMongo order : orderList){
						orderIds.add(order.getOrderId());
						orderMap.put(order.getOrderId(), order);
					}
				}else{
					page.setItems(vos);
					return page;
				}
			}
			
			DBObject subwhere = new BasicDBObject();
			//商品类型 6:精品商品
			subwhere.put("type", SubOrderType.boutique.getCode());
			//订单号模糊查询
			if(Checker.isNotEmpty(req.getOrderId())){
				String orderIdStr = Util.escapeExprSpecialWord(req.getOrderId());
				Pattern pattern = Pattern.compile("^.*" + orderIdStr+ ".*$", Pattern.CASE_INSENSITIVE);
				BasicDBList orderIdConditions = new BasicDBList();
				orderIdConditions.add(new BasicDBObject(FieldMapping.ORDER_ID.getMongoField(), pattern));
				orderIdConditions.add(new BasicDBObject(FieldMapping.SUB_ORDER_ID.getMongoField(), pattern));
				subwhere.put(QueryOperators.OR, orderIdConditions);
			}
			//发货状态
			if (Checker.isNotEmpty(req.getDeliveryStatus())) {
				subwhere.put("delivery_state", req.getDeliveryStatus());
			}
			//订单创建时间用
			if(req.getCreateStartTime() > 0 && req.getCreateEndTime() > 0) {
				BasicDBList createConList = new BasicDBList(); 
				createConList.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE, req.getCreateStartTime())));
				createConList.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE, req.getCreateEndTime())));
				subwhere.put("$and", createConList);
			}
			if(Checker.isNotEmpty(orderIds)){
				subwhere.put(FieldMapping.ORDER_ID.getMongoField(), new BasicDBObject(QueryOperators.IN, orderIds));
			}
			subwhere = setupQueryCondition(req, subwhere);
			
			//按创建时间倒序
			Sort sort = new Sort(FieldMapping.CREATE_TIME.getMongoField(), OrderBy.DESC);
			page.setSort(sort);
			List<SubOrderMongo> subInfos = (List<SubOrderMongo>) mongoCommon.querySubOrderInfoByPage(subwhere, page).getItems();
			subInfos = Checker.isNotEmpty(subInfos) ? subInfos : new ArrayList<SubOrderMongo>();
			for(SubOrderMongo sub : subInfos){
				BusinessOrderVo vo = new BusinessOrderVo();
				OrderMongo order = mongoCommon.findOrderByOrderId(sub.getOrderId());
				Client client = commonLogic.getClientById(sub.getClientId());
				
				vo.setOrderId(sub.getOrderId());
				vo.setSubOrderId(sub.getSubOrderId());
				vo.setClientId(sub.getClientId());
				vo.setClientName(client.getBankName());
				vo.setOrderStatus(sub.getOrderStatus());
				vo.setMemberCode(sub.getMemberCode());
				vo.setPaymentMethod(order.getPaymentMethod());
				vo.setPoinRate(order.getPointRate());
				vo.setCreateTime(sub.getCreateTime());
				vo.setPaymentTime(Checker.isNotEmpty(order.getPaymentTime()) ? order.getPaymentTime() : 0);
				vo.setDeliveryStatus(sub.getDeliveryState());
				vo.setRefundState(sub.getRefundState());
				
				if(Checker.isNotEmpty(sub.getProducts())){
					double totalPrice = 0;
					double cash = 0;
					double bankPoint = 0;
					double fftPoint = 0;
					double pointRate = Checker.isNotEmpty(order.getPointRate()) ? Double.parseDouble(order.getPointRate()) : 0d;
					for(ProductMongo product : sub.getProducts()){
						if(Checker.isNotEmpty(product.getTotalCash()) && Checker.isNotEmpty(product.getTotalPoint())){
							totalPrice += (product.getTotalCash() + (pointRate != 0 ? Arith.div(product.getTotalPoint(), pointRate) : product.getTotalPoint()));
							cash += product.getTotalCash();
							if(PaymentMethod.bankPoints.getCode().equals(order.getPaymentMethod()) 
									|| PaymentMethod.bankPointsAndCash.getCode().equals(order.getPaymentMethod())){
								bankPoint += product.getTotalPoint();
							}else if(PaymentMethod.froadPoints.getCode().equals(order.getPaymentMethod())
									|| PaymentMethod.froadPointsAndCash.getCode().equals(order.getPaymentMethod())){
								fftPoint += product.getTotalPoint();
							}
						}else if(Checker.isNotEmpty(product.getTotalCash())){
							totalPrice += product.getTotalCash();
							cash += product.getTotalCash();
						}else if(Checker.isNotEmpty(product.getTotalPoint())){
							totalPrice += (pointRate != 0 ? Arith.div(product.getTotalPoint(), pointRate) : product.getTotalPoint());
							if(PaymentMethod.bankPoints.getCode().equals(order.getPaymentMethod())){
								bankPoint += product.getTotalPoint();
							}else if(PaymentMethod.froadPoints.getCode().equals(order.getPaymentMethod())){
								fftPoint += product.getTotalPoint();
							}
						}else{
							int total = ((product.getMoney() * product.getQuantity()) + (product.getVipMoney() * product.getVipQuantity()) + product.getDeliveryMoney());
							totalPrice += total;
							if(PaymentMethod.bankPoints.getCode().equals(order.getPaymentMethod())){
								bankPoint += total;
							}else if(PaymentMethod.froadPoints.getCode().equals(order.getPaymentMethod())){
								fftPoint += total;
							}else if(PaymentMethod.cash.getCode().equals(order.getPaymentMethod())){
								cash += total;
							}
						}
					}
					vo.setTotalPrice(Arith.div(totalPrice, 1000, 2));
					vo.setRealPrice(Arith.div(cash, 1000, 2));
					vo.setBankPoints(Arith.div(bankPoint, 1000, 2));
					vo.setFftPoints(Arith.div(fftPoint, 1000, 2));
				}
				
				vos.add(vo);
			}
			
			page.setItems(vos);
		} catch (Exception e) {
			LogCvt.error("查询运营订单分页列表异常", e);
			page.setItems(vos);
		}
		return page;
	}

	@Override
	public BusinessOrderPyamentInfoRes getBusinessOrderPaymentInfo(String clientId, String subOrderId) {
		BusinessOrderPyamentInfoRes res = new BusinessOrderPyamentInfoRes();
		BusinessOrderPyamentInfoVo infoVo = new BusinessOrderPyamentInfoVo();
		ResultVo resultVo = new ResultVo();
		
		//根据子订单号查询子订单表
		SubOrderMongo subOrder = getSubOrderBySubOrderId(clientId, subOrderId);
		if (Checker.isEmpty(subOrder)) {
			resultVo = new ResultVo(ResultCode.failed.getCode(), "子订单号不存在");
			res.setResultVo(resultVo);
			res.setInfoVo(infoVo);
			return res;
		}
		
		//取子订单的大订单号查询大订单表信息
		OrderMongo order = getOrderByOrderId(clientId, subOrder.getOrderId());
		
		//取大订单号查询支付表信息
		List<PaymentMongo> payments = null;
		if (Checker.isNotEmpty(order)) {
			DBObject pdbObject = new BasicDBObject();
			pdbObject.put("order_id", order.getOrderId());
			pdbObject.put("payment_reason", PaymentReason.payment.getCode());
			payments = (List<PaymentMongo>) mongo.findAll(pdbObject, CB_PAYMENT, PaymentMongo.class);
		}
		
		Integer paymentTypeDetails = null;
		if(Checker.isNotEmpty(payments)){
			for(PaymentMongo p : payments){
				if(p.getPaymentTypeDetails() == 20){
					paymentTypeDetails = p.getPaymentTypeDetails();
					break;
				}
			}
		}
		
		//填充订单信息
		if (Checker.isNotEmpty(order)) {
			MemberInformationService.Iface memberInformationService = 
					(MemberInformationService.Iface)ThriftClientProxyFactory.createIface(MemberInformationService.class.getName(), MemberInformationService.class.getSimpleName(), BossConstants.THRIFT_ORDER_HOST, Integer.valueOf(BossConstants.THRIFT_ORDER_PORT));
			MemberInfoVo memberInfo = null;
			try {
				memberInfo = memberInformationService.selectUserByMemberCode(order.getMemberCode(), order.getClientId());
			} catch (Exception e) {
				LogCvt.error("通过会员编号查询会员信息异常", e);
			}
			infoVo = fillOrderInfo(order, paymentTypeDetails, memberInfo, infoVo);
		}
		//填充支付信息
		if (Checker.isNotEmpty(payments)) {
			infoVo = fillPaymentInfo(payments, infoVo);
		}
		
		infoVo.setSubOrderId(subOrderId);
		
		resultVo = new ResultVo(ResultCode.success.getCode(), "查询运营单支付信息成功");
		res.setResultVo(resultVo);
		res.setInfoVo(infoVo);
		return res;
	}
	
	@Override
	public BusinessOrderTradeInfoVoRes getBusinessOrderTradeInfo(String clientId, String subOrderId) {
		BusinessOrderTradeInfoVoRes res = new BusinessOrderTradeInfoVoRes();
		List<BusinessOrderTradeInfoVo> infoVos = new ArrayList<BusinessOrderTradeInfoVo>();
		ResultVo resultVo = new ResultVo();
		
		//根据子订单号查询子订单表
		SubOrderMongo subOrder = getSubOrderBySubOrderId (clientId, subOrderId);
		if (Checker.isEmpty(subOrder)) {
			resultVo = new ResultVo(ResultCode.failed.getCode(), "子订单号不存在");
			res.setResultVo(resultVo);
			res.setInfoVos(infoVos);
			return res;
		}
		
		
		//取子订单的大订单号查询大订单表信息
		OrderMongo order = getOrderByOrderId(clientId, subOrder.getOrderId());
		// 取子订单的大订单号查询结算表信息
		Settlement settle = null;
		if (Checker.isNotEmpty(order) && order.getOrderId() != null) {
			settle = getSettlementBySubOrderId(clientId, order.getOrderId());
		}

		String consignee = null;
		String phone = null;
		RecvInfo recvInfo = null;
		String deliveryAddress = null;
		if (Checker.isNotEmpty(order) && order.getMemberCode() != null && order.getRecvId() != null) {
			recvInfo = mongoCommon.queryRecvInfoByRecvId(order.getRecvId());
			if (Checker.isNotEmpty(recvInfo)) {
				consignee = recvInfo.getConsignee();// 提（收）货人
				phone = recvInfo.getPhone();// 提（收）货手机号
				if (Checker.isNotEmpty(recvInfo.getAreaId())) {
					Area area = findAllAreaListToMap(Long.valueOf(recvInfo.getAreaId()));
					if (Checker.isNotEmpty(area)) {
						String treePathName = area.getTreePathName().replace(",", " ");
						deliveryAddress = treePathName + " " + recvInfo.getAddress();// 提货网点
					}
				}
			}
		}
		
		BusinessOrderTradeInfoVo infoVo = null;
		List<ProductMongo> products = (List<ProductMongo>) subOrder.getProducts();
		if (products != null && products.size() > 0) {
			for (ProductMongo product : products) {
				infoVo = new BusinessOrderTradeInfoVo();
				infoVo.setOrderType(subOrder.getType());//订单类型
				if (ProductType.boutique.getCode().equals(product.getType())) {
					ProductDetail productDetail = getProductByProductId(clientId, product.getProductId());
					//填充商品信息
					infoVo = fillProductInfo(product, order, productDetail, infoVo);
					
					//填充结算信息
					if (Checker.isNotEmpty(settle)) {
						infoVo = fillSettlementInfo(settle, infoVo);
					}
					
					//填充收货信息
					infoVo.setConsignee(consignee);
					infoVo.setPhone(phone);
					infoVo.setDeliveryAddress(deliveryAddress);
					
					infoVos.add(infoVo);
				}
			}
		}
		
		resultVo = new ResultVo(ResultCode.success.getCode(), "查询运营订单的交易信息成功");
		res.setResultVo(resultVo);
		res.setInfoVos(infoVos);
		return res;
	}

	/**
	 * 查询运营订单退款信息
	 * 2015-12-18 
	 * @param clientId
	 * @param subOrderId
	 * @return  BusinessOrderRefundInfoVoRes
	 */
	@Override
	public BusinessOrderRefundInfoVoRes getBusinessOrderRefundInfo(String clientId, String subOrderId) {
		BusinessOrderRefundInfoVoRes res = new BusinessOrderRefundInfoVoRes();
		List<BusinessOrderRefundInfoVo> infoVoList = new ArrayList<BusinessOrderRefundInfoVo>();
		ResultVo resultVo = new ResultVo();
		
		//根据子订单号查询子订单表
		SubOrderMongo subOrder = getSubOrderBySubOrderId (clientId, subOrderId);
		if (Checker.isEmpty(subOrder)) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("子订单号不存在");
			res.setResultVo(resultVo);
			res.setInfoVo(infoVoList);
			return res;
		}
		
		//根据子订单号查询退款信息
		DBObject dbObject = new BasicDBObject();
		dbObject.put("client_id", clientId);
		dbObject.put("shopping_info.sub_order_id", subOrderId);
		dbObject.put("shopping_info.type", SubOrderType.boutique.getCode());//精品商城子订单
		List<RefundHistory> refundOrderList = (List<RefundHistory>) mongo.findAll(dbObject, CB_ORDER_REFUNDS, RefundHistory.class);
		if (Checker.isEmpty(refundOrderList)) {
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc("该订单无退款记录");
			res.setResultVo(resultVo);
			res.setInfoVo(infoVoList);
			return res;
		}
		
		
		BusinessOrderRefundInfoVo infoVo = null;//运营订单退款信息详情
		List<RefundPaymentInfo> paymentInfos = null;
		List<String> productIdList = null;
		for(RefundHistory refundOrder:refundOrderList){
			infoVo = new BusinessOrderRefundInfoVo();
			infoVo.setRefundId(refundOrder.get_id());//退款编号
			infoVo.setApplyTime(refundOrder.getCreateTime());//申请时间
			infoVo.setRefundDesc(refundOrder.getReason());//退款说明
			
			paymentInfos = new ArrayList<RefundPaymentInfo>();
			paymentInfos = refundOrder.getPaymentInfo();
			if (paymentInfos != null && paymentInfos.size() > 0) {
				Double refundValue = 0d;
				Double refundPoint = 0d;
				for (RefundPaymentInfo paymentInfo : paymentInfos) {
					if(PaymentMethod.bankPoints.getCode().equals(paymentInfo.getType())
							|| PaymentMethod.froadPoints.getCode().equals(paymentInfo.getType())
							|| PaymentMethod.creditPoints.getCode().equals(paymentInfo.getType())){
						refundPoint += Double.valueOf(paymentInfo.getRefundValue());
					}else if(PaymentMethod.cash.getCode().equals(paymentInfo.getType())){
						refundValue += Double.valueOf(paymentInfo.getRefundValue());
					}
				}
				refundValue = Arith.div(refundValue, 1000, 2);
				refundPoint = Arith.div(refundPoint, 1000, 2);
				infoVo.setRefundValue(refundValue.toString());
				infoVo.setRefundPoint(refundPoint.toString());
			}
			if (Checker.isNotEmpty(refundOrder.getRefundTime())) {
				infoVo.setRefundTime(refundOrder.getRefundTime());//退款时间
			}
			
			infoVo.setRefundStatus(refundOrder.getRefundState());//退款状态
			//退款对应的商品Id
			productIdList = new ArrayList<String>();
			for(RefundShoppingInfo shopping : refundOrder.getShoppingInfo()){
				if(subOrderId.equals(shopping.getSubOrderId()) &&  SubOrderType.boutique.getCode().equals(shopping.getType())){
					for(RefundProduct product : shopping.getProducts()){
						productIdList.add(product.getProductId());
					}
				}
			}
			infoVo.setProductIdList(productIdList);
			
			//加到返回list中
			infoVoList.add(infoVo);
		} 
		
		
		resultVo = new ResultVo(ResultCode.success.getCode(), "查询运营订单退款信息成功");
		res.setResultVo(resultVo);
		res.setInfoVo(infoVoList);
		return res;
	}
	
	/**
	 * 批量获取所有的地区路径树名
	 */
	private Area findAllAreaListToMap(Long id){
		Area area = new Area();
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			AreaMapper areaMapper = sqlSession.getMapper(AreaMapper.class);
			area = areaMapper.findById(id);
		} catch (Exception e) {
			LogCvt.error("查询所有地区信息异常", e);
		} finally {
			if (sqlSession != null){
				sqlSession.close();
			}
		}
		return area;
	}
	
	/**
	 * 根据订单号查询结算表
	 */
	private Settlement getSettlementBySubOrderId(String clientId, String orderId) {
		Settlement settlement = new Settlement();
		if (Checker.isNotEmpty(clientId) && Checker.isNotEmpty(orderId)) {
			DBObject dbObject = new BasicDBObject();
			dbObject.put("client_id", clientId);
			dbObject.put("order_id", orderId);
			settlement = (Settlement) mongo.findOne(dbObject, CB_SETTLEMENT, Settlement.class);
		}
		return settlement;
		
	}
	
	/**
	 * 根据子订单号查询子订单表
	 */
	private SubOrderMongo getSubOrderBySubOrderId (String clientId, String subOrderId) {
		SubOrderMongo subOrder = null;
		if (Checker.isNotEmpty(clientId) && Checker.isNotEmpty(subOrderId)) {
			DBObject dbObject = new BasicDBObject();
			dbObject.put("client_id", clientId);
			dbObject.put("sub_order_id", subOrderId);
			subOrder = (SubOrderMongo) mongo.findOne(dbObject, CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		}
		return subOrder;
		
	}
	
	/**
	 * 根据大订单号查询大订单表
	 */
	private OrderMongo getOrderByOrderId(String clientId, String orderId) {
		OrderMongo order = null;
		if (Checker.isNotEmpty(clientId) && Checker.isNotEmpty(orderId)) {
			DBObject odbObject = new BasicDBObject();
			odbObject.put("client_id", clientId);
			odbObject.put("_id", orderId);
			order = mongo.findOne(odbObject, CB_ORDER, OrderMongo.class);
		}
		return order;
	}
	
	/**
	 * 根据商品ID查询商品详情表
	 */
	private ProductDetail getProductByProductId(String clientId, String productId) {
		ProductDetail product = new ProductDetail();
		if (Checker.isNotEmpty(clientId) && Checker.isNotEmpty(productId)) {
			DBObject dbObject = new BasicDBObject();
			dbObject.put("client_id", clientId);
			dbObject.put("_id", productId);
			product = (ProductDetail) mongo.findOne(dbObject, CB_PRODUCT_DETAIL, ProductDetail.class);
		}
		return product;
		
	}
	/**
	 * 填充订单信息
	 */
	private BusinessOrderPyamentInfoVo fillOrderInfo(OrderMongo order, Integer paymentTypeDetail, MemberInfoVo memberInfoVo, BusinessOrderPyamentInfoVo infoVo) {
		if (Checker.isNotEmpty(order.getOrderId())) {
			infoVo.setOrderId(order.getOrderId());
		}
		if (order.getCreateTime() != 0) {
			infoVo.setCreateTime(order.getCreateTime());
		}
		if (order.getMemberCode() != 0) {
			infoVo.setMemberCode(order.getMemberCode());
		}
		if (Checker.isNotEmpty(memberInfoVo.getUname()) && (Checker.isEmpty(paymentTypeDetail) || (Checker.isNotEmpty(paymentTypeDetail) && paymentTypeDetail != 20))) {
			//用户的真实姓名，显示为支付帮卡信息中的真实姓名。贴膜卡支付不显示。
			infoVo.setMemberName(memberInfoVo.getUname());
		}else if((Checker.isEmpty(memberInfoVo) || Checker.isEmpty(memberInfoVo.getUname())) && (Checker.isEmpty(paymentTypeDetail) || (Checker.isNotEmpty(paymentTypeDetail) && paymentTypeDetail != 20))){
			infoVo.setMemberName(order.getMemberName());
		}
		if (Checker.isNotEmpty(order.getClientId())) {
			Client client = commonLogic.getClientById(order.getClientId());
			infoVo.setClientId(order.getClientId());
			if (Checker.isNotEmpty(client)) {
				infoVo.setClientName(client.getBankName());
			}
		}
		if (Checker.isNotEmpty(order.getCreateSource())) {
			infoVo.setCreateResource(order.getCreateSource());
		}
		if (Checker.isNotEmpty(order.getPaymentMethod())) {
			infoVo.setPaymentMethod(order.getPaymentMethod());
		}
		if (Checker.isNotEmpty(order.getTotalPrice()) && order.getTotalPrice() != 0) {
			infoVo.setTotalPrice(Arith.div(order.getTotalPrice(), 1000, 2));
		}
		if (Checker.isNotEmpty(order.getRealPrice()) && order.getRealPrice() != 0) {
			infoVo.setRealPrice(Arith.div(order.getRealPrice(), 1000, 2));
		}
		if (Checker.isNotEmpty(order.getFftPoints()) && order.getFftPoints() != 0) {
			infoVo.setFftPoints(Arith.div(order.getFftPoints(), 1000, 2));
		}
		if (Checker.isNotEmpty(order.getBankPoints()) && order.getBankPoints() != 0) {
			infoVo.setBankPoints(Arith.div(order.getBankPoints(), 1000, 2));
		}
		if (Checker.isNotEmpty(order.getOrderStatus())) {
			infoVo.setOrderStatus(order.getOrderStatus());
		}
		if (Checker.isNotEmpty(order.getPaymentTime()) && order.getPaymentTime() != 0) {
			infoVo.setPaymentTime(order.getPaymentTime());
		}
		if(Checker.isNotEmpty(order.getPointRate()) && !"0".equals(order.getPointRate())){
			infoVo.setPoinRate(order.getPointRate());
		}
		
		return infoVo;
	}
	
	/**
	 * 填充支付信息
	 */
	private BusinessOrderPyamentInfoVo fillPaymentInfo(List<PaymentMongo> payments, BusinessOrderPyamentInfoVo infoVo) {
		StringBuilder billNos = new StringBuilder();
		for(int i = 0; i < payments.size(); i++){
			PaymentMongo p = payments.get(i);
			if(i == payments.size() - 1){
				billNos.append(p.getBillNo());
			}else{
				billNos.append(p.getBillNo()).append(",");
			}
		}
		infoVo.setBillNo(billNos.toString());//支付账单号
		return infoVo;
	}
	
	/**
	 * 填充交易信息
	 */
	private BusinessOrderTradeInfoVo fillSettlementInfo(Settlement settle, BusinessOrderTradeInfoVo infoVo) {
		if (Checker.isNotEmpty(settle.getSettleState())) {
			infoVo.setSettleState(settle.getSettleState());
		}
		if (Checker.isNotEmpty(settle.getSettlementId())) {
			infoVo.setSettlement_id(settle.getSettlementId());
		}
		if (settle.getCreateTime() != 0) {
			infoVo.setSettlementTime(settle.getCreateTime());
		}
		return infoVo;
	}
	
	/**
	 * 填充商品信息
	 */
	private BusinessOrderTradeInfoVo fillProductInfo(ProductMongo product, OrderMongo order, ProductDetail productDetail, BusinessOrderTradeInfoVo infoVo) {
		infoVo.setProductCount(product.getQuantity() + product.getVipQuantity());
		infoVo.setDeliveryOption(product.getDeliveryOption());
		int money = 0;
		if(product.getQuantity() != 0 && product.getVipQuantity() != 0){
			money = product.getMoney() + product.getVipMoney();
		}else if(product.getQuantity() != 0){
			money = product.getMoney();
		}else if(product.getVipQuantity() != 0){
			money = product.getVipMoney();
		}
		
		double pointRate = Checker.isNotEmpty(order.getPointRate()) ? Double.parseDouble(order.getPointRate()) : 0d;
		double moneyLittleCount = 0;
		if(Checker.isNotEmpty(product.getTotalCash()) && Checker.isNotEmpty(product.getTotalPoint())){
			moneyLittleCount = product.getTotalCash() + (pointRate != 0 ? Arith.div(product.getTotalPoint(), pointRate) : product.getTotalPoint());
		}else if(Checker.isNotEmpty(product.getTotalCash())){
			moneyLittleCount = product.getTotalCash();
		}else if(Checker.isNotEmpty(product.getTotalPoint())){
			moneyLittleCount = pointRate != 0 ? Arith.div(product.getTotalPoint(), pointRate) : product.getTotalPoint();
		}else{
			moneyLittleCount = (product.getMoney() * product.getQuantity()) + (product.getVipMoney() * product.getVipQuantity()) + product.getDeliveryMoney();
		}
		
		infoVo.setMoneyLittleCount(Arith.div(moneyLittleCount, 1000, 2));//金额小记
		infoVo.setPrice(Arith.div(money, 1000, 2));//购买单价
		infoVo.setProductId(product.getProductId());//商品ID
		infoVo.setProductName(productDetail.getFullName());//精品商城商品全称
		infoVo.setDeliveryTime(productDetail.getDeliveryTime() != null ? productDetail.getDeliveryTime().getTime() : 0);
		return infoVo;
		
	}

	private DBObject setupQueryCondition(BusinessOrderListReq req, DBObject query){
		//客户端
		if (Checker.isNotEmpty(req.getClientId())) {
			query.put("client_id", req.getClientId());
		}
		//用户编号
		if (req.getMemberCode() != 0) {
			query.put("member_code", req.getMemberCode());
		}
		//订单状态
		if(Checker.isNotEmpty(req.getOrderStatus())){
			query.put("order_status", req.getOrderStatus());
		}
		return query;
	}
	
}
