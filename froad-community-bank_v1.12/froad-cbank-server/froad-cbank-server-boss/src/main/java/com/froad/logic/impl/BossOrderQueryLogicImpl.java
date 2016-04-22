package com.froad.logic.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.bson.types.ObjectId;

import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.BossCommonMongo;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.AreaMapper;
import com.froad.db.mysql.mapper.MerchantUserMapper;
import com.froad.db.mysql.mapper.ProductMapper;
import com.froad.enums.CreateSource;
import com.froad.enums.DeliveryType;
import com.froad.enums.FieldMapping;
import com.froad.enums.OrderStatus;
import com.froad.enums.OrderType;
import com.froad.enums.PaymentMethod;
import com.froad.enums.PaymentReason;
import com.froad.enums.RefundState;
import com.froad.enums.ResultCode;
import com.froad.enums.SubOrderType;
import com.froad.enums.TicketStatus;
import com.froad.exceptions.FroadBusinessException;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.BossOrderQueryLogic;
import com.froad.logic.CommonLogic;
import com.froad.po.Area;
import com.froad.po.BossPrefPayOrderDetailInfo;
import com.froad.po.BossPrefPayOrderListInfo;
import com.froad.po.BossPrefPayOrderPageReq;
import com.froad.po.Client;
import com.froad.po.MerchantUser;
import com.froad.po.OrderQueryByBossCondition;
import com.froad.po.Org;
import com.froad.po.PageEntity;
import com.froad.po.PageEntity.OrderByType;
import com.froad.po.PaymentMongoEntity;
import com.froad.po.ProductPresell;
import com.froad.po.Ticket;
import com.froad.po.TicketDto;
import com.froad.po.mongo.MerchantOutletFavorite;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.OutletDetail;
import com.froad.po.mongo.PaymentMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.RecvInfo;
import com.froad.po.mongo.RefundHistory;
import com.froad.po.mongo.RefundProduct;
import com.froad.po.mongo.RefundShoppingInfo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.settlement.Settlement;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.MemberInformationService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.member.MemberInfoVo;
import com.froad.thrift.vo.order.BossGroupOrderListReq;
import com.froad.thrift.vo.order.BossPointOrderListReq;
import com.froad.thrift.vo.order.BossPointOrderListRes;
import com.froad.thrift.vo.order.BossPointOrderVo;
import com.froad.thrift.vo.order.BossPresellBillListReq;
import com.froad.thrift.vo.order.BossPresellBillVo;
import com.froad.thrift.vo.order.BossPresellDetialRes;
import com.froad.thrift.vo.order.BossPresellOrderListReq;
import com.froad.thrift.vo.order.BossQueryOrderVo;
import com.froad.thrift.vo.order.BossSubOrderProductVo;
import com.froad.thrift.vo.order.BossSubOrderVo;
import com.froad.util.Arith;
import com.froad.util.BeanUtil;
import com.froad.util.BossConstants;
import com.froad.util.BossUtil;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.EmptyChecker;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class BossOrderQueryLogicImpl implements BossOrderQueryLogic {
	private final String CB_SUBORDER_PRODUCT = MongoTableName.CB_SUBORDER_PRODUCT;
	private final String CB_ORDER = MongoTableName.CB_ORDER;
	private final String CB_MERCHANT_OUTLETFAVORITE = MongoTableName.CB_MERCHANT_OUTLET_FAVORITE;
	private final String CB_TICKET = MongoTableName.CB_TICKET;
	
	private MongoManager mongo = new MongoManager();
	
	private BossCommonMongo mongoCommon = new BossCommonMongo();
	
	private CommonLogic commonLogic = new CommonLogicImpl();
	
	private static Map<String, String> productPresellMap = new HashMap<String, String>();
	
	private static Map<String, String> orderIdRecvIdMap = new HashMap<String, String>();
	
	private static Map<String, List<RecvInfo>> recvIdMap = new HashMap<String, List<RecvInfo>>();
	
	private static Map<String, String> areaMap = new HashMap<String, String>();
	
	/**
	 * 具体查询规则：以券为主表，以券表中的子订单ID和从表inner join操作
	 * 查询条件中牵扯到以下从表：
	 * 订单表（查询条件：支付开始时间结束时间）
	 * 子订单表（查询条件：大订单ID，会员登录账号）
	 * 结算表（查询条件：结算状态(暂时去掉结算状态查询条件)）;
	 * 按照传统关系型表之间的多表inner join算法进行查询操作。
	 */
	@SuppressWarnings("unchecked")
	public ResultBean getBossGroupOrderList(BossGroupOrderListReq req) {
		MongoPage pageParam = convertPageReqToParam(req.getPageVo());
		//根据查询条件先查询大订单记录
		List<SubOrderMongo> subList = null;
		//根据查询条件查询子订单记录
		if(StringUtils.isNotEmpty(req.getOrderId()) || StringUtils.isNotEmpty(req.getMemberName())) {
			SubOrderMongo qySub = new SubOrderMongo();
			qySub.setOrderId(req.getOrderId());
			qySub.setMemberName(req.getMemberName());
			subList = (List<SubOrderMongo>)mongoCommon.getSubOrderList(qySub, 0, 0);
			if(subList == null || subList.size() == 0) {
				ResultBean result = new ResultBean(ResultCode.success, null);
				return result;
			} 
		}
		
		BasicDBObject twhere = new BasicDBObject();
		if(subList != null) {
			BasicDBList laValues = new BasicDBList();
			for(SubOrderMongo sub : subList) {
				laValues.add(sub.getSubOrderId());
			}
			twhere.put("sub_order_id", new BasicDBObject(QueryOperators.IN, laValues));
			
		}
		
		if(StringUtils.isNotEmpty(req.getTicketId())) {
			twhere.put("ticket_id", req.getTicketId());
		}
		if(StringUtils.isNotEmpty(req.getPhone())) {//手机号
			twhere.put("sms_number", req.getPhone());
		}
		
		String ticketStatus = req.getTicketStatus();
		if(StringUtils.isNotEmpty(ticketStatus)) {//券状态
			BasicDBList statusValues = new BasicDBList();
			String[] statusArray = ticketStatus.split(",");
			if(statusArray != null && statusArray.length > 0) {
				for(String status : statusArray) {
					statusValues.add(new BasicDBObject("status", status));
				}
			}
			twhere.put(QueryOperators.OR, statusValues);
		}
		
		//支付开始时间用券的创建时间来替代
		if(req.getStartTime() > 0 && req.getEndTime() > 0) {
			BasicDBList createConList = new BasicDBList(); 
			createConList.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE, req.getStartTime())));
			createConList.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE, req.getEndTime())));
			twhere.put("$and", createConList);
		}
		
		if(StringUtils.isNotEmpty(req.getProductName())) {
			Pattern pattern = Pattern.compile("^.*" + req.getProductName()+ ".*$", Pattern.CASE_INSENSITIVE); 
			twhere.put("product_name", pattern);
		}
		//查询团购券
		twhere.put("type", "1");
		//过滤待发送的券记录
		twhere.put("status", new BasicDBObject(QueryOperators.NIN, new String[] {TicketStatus.init.getCode(), TicketStatus.init_refunded.getCode()}));
		MongoPage page = mongo.findByPageWithRedis(pageParam, twhere, MongoTableName.CB_TICKET, Ticket.class);
		//查询券列表中每张券对应的购买会员账号、设置支付时间
		if(page != null && page.getItems() != null) {
			List<TicketDto> ticketDtoList = groupAssistPropertiesLogic( (List<Ticket>) page.getItems());
			page.setItems(ticketDtoList);
		}
		ResultBean result = new ResultBean(ResultCode.success, page);
		return result;
	}
	
	/**
	 * 团购查询中非主表展示字段获取
	 * presellAssistTablePropertiesLogic:(这里用一句话描述这个方法的作用).
	 * @author zhouzhiwei
	 * 2015年8月7日 下午3:58:59
	 * @param ticketList
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	private List<TicketDto> groupAssistPropertiesLogic(List<Ticket> ticketList) {
		List<TicketDto> ticketDtoList = new ArrayList<TicketDto>();
		for(Ticket t : ticketList) {
			TicketDto dto = new TicketDto();
			try {
				dto.set_id(t.get_id());
				dto.setClientId(t.get_id());
				dto.setConsumeTime(t.getConsumeTime());
				dto.setCreateTime(t.getCreateTime());
				dto.setDeliveryEndTime(0l);
				dto.setDeliveryStartTime(0l);
				dto.setDeliveryType("");
				dto.setDeliveryUserName("");
				dto.setExpireTime(t.getExpireTime());
				dto.setForgCode(t.getForgCode());
				dto.setImage(t.getImage());
				dto.setLorgCode(t.getLorgCode());
				dto.setMemberCode(t.getMemberCode());
				dto.setMemberName(t.getMemberName());
				dto.setMerchantId(t.getMerchantId());
				dto.setMerchantName(t.getMerchantName());
				dto.setMerchantUserId(t.getMerchantUserId());
				dto.setMerchantUserName(t.getMerchantUserName());
				dto.setMobile(t.getMobile());
				dto.setOrderId(t.getOrderId());
				dto.setOrgCode(t.getOrgCode());
				dto.setOrgName(t.getOrgName());
				dto.setOutletId(t.getOutletId());
				dto.setOutletName(t.getOutletName());
				dto.setPaymentTime(0l);
				dto.setPrice(t.getPrice());
				dto.setProductId(t.getProductId());
				dto.setProductName(t.getProductName());
				dto.setQuantity(t.getQuantity());
				dto.setRefundId(t.getRefundId());
				dto.setRefundTime(t.getRefundTime());
				dto.setSorgCode(t.getSorgCode());
				dto.setStatus(t.getStatus());
				dto.setSubOrderId(t.getSubOrderId());
				dto.setTicketId(t.getTicketId());
				dto.setTorgCode(t.getTorgCode());
				dto.setType(t.getType());

//				BeanUtils.copyProperties(dto, t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ticketDtoList.add(dto);
		}
		
		BasicDBObject mnameValues = new BasicDBObject();
		BasicDBList mnameValuesList = new BasicDBList();
		Set<String> setList = new HashSet<String>();
		for(TicketDto o : ticketDtoList) {
			setList.add(o.getOrderId());
		}
		mnameValuesList.addAll(setList);
		
		mnameValues.put("_id", new BasicDBObject(QueryOperators.IN, mnameValuesList));
		List<OrderMongo> nameOrderList = (List<OrderMongo>) mongo.findAll(mnameValues, MongoTableName.CB_ORDER, OrderMongo.class);
		
		Map<String, OrderMongo> nameOrderMap = new HashMap<String, OrderMongo>();
		for(OrderMongo om : nameOrderList){
			nameOrderMap.put(om.getOrderId(), om);
		}
		
		if(nameOrderList != null) {
			OrderMongo orm = null;
			for(int i = 0; i < ticketDtoList.size(); i++)  {
				orm = nameOrderMap.get(ticketDtoList.get(i).getOrderId());
				if(orm != null){
					ticketDtoList.get(i).setDeliveryUserName(ticketDtoList.get(i).getMemberName());//券表中的memberName存储的时提货人或者收货人姓名
					ticketDtoList.get(i).setMemberName(orm.getMemberName());
					ticketDtoList.get(i).setPaymentTime(orm.getPaymentTime());
				}
			} 
		}
		return ticketDtoList;
	}
	
	
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public ResultBean getBossPresellOrderList(BossPresellOrderListReq req) {
		MongoPage pageParam = convertPageReqToParam(req.getPageVo());
		//根据查询条件先查询大订单记录
		List<SubOrderMongo> subList = null;
		//根据查询条件查询子订单记录
		if(StringUtils.isNotEmpty(req.getOrderId())) {
			SubOrderMongo qySub = new SubOrderMongo();
			qySub.setOrderId(req.getOrderId());
			subList = (List<SubOrderMongo>)mongoCommon.getSubOrderList(qySub, 0, 0);
			if(subList == null || subList.size() == 0) {
				ResultBean result = new ResultBean(ResultCode.success, null);
				return result;
			} 
		}
		
		BasicDBObject twhere = new BasicDBObject();
		if(subList != null) {
			BasicDBList laValues = new BasicDBList();
			for(SubOrderMongo sub : subList) {
				laValues.add(sub.getSubOrderId());
			}
			twhere.put("sub_order_id", new BasicDBObject(QueryOperators.IN, laValues));
		}
	
		if(StringUtils.isNotEmpty(req.getTicketId())) {
			twhere.put("ticket_id", req.getTicketId());
		}
		if(StringUtils.isNotEmpty(req.getMemberName())) {//提货人用户名
			twhere.put("member_name", req.getMemberName());
		}
		if(StringUtils.isNotEmpty(req.getPhone())) {//提货人手机号
			twhere.put("sms_number", req.getPhone());
		}
		String ticketStatus = req.getTicketStatus();
		if(StringUtils.isNotEmpty(ticketStatus)) {//券状态
			BasicDBList statusValues = new BasicDBList();
			String[] statusArray = ticketStatus.split(",");
			if(statusArray != null && statusArray.length > 0) {
				for(String status : statusArray) {
					statusValues.add(new BasicDBObject("status", status));
				}
			}
			twhere.put(QueryOperators.OR, statusValues);
		}
		if(req.getStartTime() > 0 && req.getEndTime() > 0) {
			BasicDBList createConList = new BasicDBList(); 
			createConList.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE, req.getStartTime())));
			createConList.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE, req.getEndTime())));
			twhere.put("$and", createConList);
		}
		
		if(StringUtils.isNotEmpty(req.getProductName())) {
			Pattern pattern = Pattern.compile("^.*" + req.getProductName()+ ".*$", Pattern.CASE_INSENSITIVE); 
			twhere.put("product_name", pattern);
		}
		twhere.put("type", "2");
		twhere.put("status", new BasicDBObject(QueryOperators.NIN, new String[] {TicketStatus.init.getCode(), TicketStatus.init_refunded.getCode()}));
		MongoPage page = mongo.findByPageWithRedis(pageParam, twhere, MongoTableName.CB_TICKET, Ticket.class);
		//查询券列表中每张券对应的设置支付时间
		if(page != null && page.getItems() != null) {
			String deliveryStartTime = null;
			String deliveryEndTime = null;
			String productId = null;
			if (productPresellMap.get(productId) == null) {
				//获取全部的商品信息
				List<ProductPresell> productPreselllist = getProductPresellInfo();
				if (productPreselllist != null && productPreselllist.size() > 0) {
					for (ProductPresell presell:productPreselllist) {
						deliveryStartTime = String.valueOf(presell.getDeliveryStartTime().getTime());
						deliveryEndTime = String.valueOf(presell.getDeliveryEndTime().getTime());
						productId = presell.getProductId();
						productPresellMap.put(productId,deliveryStartTime + "," + deliveryEndTime);
					}
				}
			}
			
			List<TicketDto> ticketDtoList = presellAssistPropertiesLogic((List<Ticket>) page.getItems(), productPresellMap);
			page.setItems(ticketDtoList);
		}
		
		ResultBean result = new ResultBean(ResultCode.success, page);
		return result;
	}
	
	/**
	 * 预售查询中非主表展示字段获取
	 * presellAssistTablePropertiesLogic:(这里用一句话描述这个方法的作用).
	 * @author zhouzhiwei
	 * 2015年8月7日 下午3:58:59
	 * @param ticketList
	 * @return
	 *
	 */
	@SuppressWarnings({"unchecked" })
	private List<TicketDto> presellAssistPropertiesLogic(List<Ticket> ticketList, Map<String, String> productPresellMap) {
		List<TicketDto> ticketDtoList = new ArrayList<TicketDto>();
		for(Ticket t : ticketList) {
			TicketDto dto = new TicketDto();
			try {
				BeanUtils.copyProperties(dto, t);
				ticketDtoList.add(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		BasicDBObject mnameValues = new BasicDBObject();
		BasicDBList mnameValuesList = new BasicDBList();
		BasicDBList subValuesList = new BasicDBList();
		for(TicketDto o : ticketDtoList) {
			mnameValuesList.add(o.getOrderId());
			subValuesList.add(o.getSubOrderId());
		}
		mnameValues.put("_id", new BasicDBObject(QueryOperators.IN, mnameValuesList));
		List<OrderMongo> nameOrderList = (List<OrderMongo>) mongo.findAll(mnameValues, MongoTableName.CB_ORDER, OrderMongo.class);
		
		if(nameOrderList != null) {
			Map<String, SubOrderMongo> subOrderMap = new HashMap<String, SubOrderMongo>();
			BasicDBObject subOrderValues = new BasicDBObject();
			subOrderValues.put("sub_order_id", new BasicDBObject(QueryOperators.IN, subValuesList));
			List<SubOrderMongo> subList = (List<SubOrderMongo>) mongo.findAll(subOrderValues, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
			for(SubOrderMongo sm : subList) {
				List<ProductMongo> proList = sm.getProducts();
				if(proList != null && proList.size() > 0) {
					for(ProductMongo pro : proList) {
						subOrderMap.put(sm.getSubOrderId() + pro.getProductId(), sm);
					}
				}
			}
			
			for(int i = 0; i < ticketDtoList.size(); i++)  {
				for(OrderMongo om : nameOrderList) {
					if(om.getOrderId().equals(ticketDtoList.get(i).getOrderId())) {
						ticketDtoList.get(i).setPaymentTime(om.getPaymentTime());
						ticketDtoList.get(i).setDeliveryUserName(ticketDtoList.get(i).getMemberName());
						ticketDtoList.get(i).setMemberName(om.getMemberName());
					}
				}
				//查询每张券对应的商品的配送方式
				/*BasicDBObject deValues = new BasicDBObject();
				deValues.put("sub_order_id", ticketDtoList.get(i).getSubOrderId());
				deValues.put("products.product_id", ticketDtoList.get(i).getProductId());
				SubOrderMongo subOrder = (SubOrderMongo)mongo.findOne(deValues, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);*/
				SubOrderMongo subOrder = subOrderMap.get(ticketDtoList.get(i).getSubOrderId() + ticketDtoList.get(i).getProductId());
				if(subOrder != null && subOrder.getProducts() != null) {
					for(ProductMongo p : subOrder.getProducts()) {
						if(p.getProductId().equals(ticketDtoList.get(i).getProductId())) {
							ticketDtoList.get(i).setDeliveryType(p.getDeliveryOption());
						}
					}
					
					//获取当前商品的提货时间
					//Map<String, String> proMap = commonLogic.getProductRedis(subOrder.getClientId(), subOrder.getMerchantId(), ticketDtoList.get(i).getProductId());
					if(productPresellMap != null) {
						String deliverTime = productPresellMap.get(ticketDtoList.get(i).getProductId());
						if (deliverTime != null) {
							String startTime[] = deliverTime.split(",");
							String deliverStartTime = startTime[0];
							if(StringUtils.isNotEmpty(deliverStartTime)) {
								ticketDtoList.get(i).setDeliveryStartTime(Long.valueOf(deliverStartTime));
							}
							String deliverEndTime = startTime[1];
							if(StringUtils.isNotEmpty(deliverEndTime)) {
								ticketDtoList.get(i).setDeliveryEndTime(Long.valueOf(deliverEndTime));
							}
						}else {
							LogCvt.info("该商品不存在, productId:" + ticketDtoList.get(i).getProductId());
						}
						
					}
				}
			} 
		}
		return ticketDtoList;
	}
	
	/**
	 * 
	 * orderConditionLogic:团购和预售查询条件过滤组装业务方法
	 * @author zhouzhiwei
	 * 2015年8月17日 上午10:29:01
	 * @param orderList
	 * @param subList
	 * @param orderOptFlag
	 * @param subOptFlag
	 * @return
	 *
	 */
	@SuppressWarnings("unused")
	private Map<String, Object> orderConditionLogic(List<OrderMongo> orderList, List<SubOrderMongo> subList, boolean orderOptFlag, boolean subOptFlag) {
		List<String> lastSubList = new ArrayList<String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String qyFlag = null;
		//如果订单表、子订单表都有查询条件
		if(orderOptFlag && subOptFlag) {
			qyFlag = "1";
			for(OrderMongo order : orderList) {
				for(SubOrderMongo sub : subList) {
					if(order.getOrderId().equals(sub.getOrderId())) {
						lastSubList.add(sub.getSubOrderId());
					}
				}
			}
			if(lastSubList.size() == 0) {
				resultMap.put("queryFlag", "0");
				return resultMap;
			}
		//订单有查询条件并且子订单没有查询条件
		} else if(orderOptFlag && !subOptFlag) {
			qyFlag = "2";
		//订单表没有查询条件且子订单表有查询条件
		} else if(!orderOptFlag && subOptFlag) {
			if(subList == null || subList.size() == 0) {
				resultMap.put("queryFlag", "0");
				return resultMap;
			}
			qyFlag = "3";
			for(SubOrderMongo sub : subList) {
				lastSubList.add(sub.getSubOrderId());
			}
		}
		resultMap.put("queryFlag", "1");
		resultMap.put("lastSubList" , lastSubList);
		resultMap.put("qyFlag", qyFlag);
		return resultMap;
	}
	
	/**
	 * 请求分页参数对象转换成mongo查询分页参数对象
	 * convertPageReqToParam:(这里用一句话描述这个方法的作用).
	 * @author zhouzhiwei
	 * 2015年8月7日 上午11:33:19
	 * @param pageVo
	 * @return
	 *
	 */
	private MongoPage convertPageReqToParam(PageVo pageVo) {
		MongoPage pageParam = new MongoPage();
		//当前页数
		pageParam.setPageNumber(pageVo.getPageNumber());
		pageParam.setPageSize(pageVo.getPageSize());
		pageParam.setLastPageNumber(pageVo.getLastPageNumber());
		pageParam.setLastRecordTime(pageVo.getLastRecordTime());
		pageParam.setFirstRecordTime(pageVo.getFirstRecordTime());
		return pageParam;
	}

	/**
	 * boss查询线上积分兑换订单
	 * convertPageReqToParam:(这里用一句话描述这个方法的作用).
	 * @author liuyanyun
	 * 2015年8月7日 下午5:20:59
	 * @param req
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BossPointOrderListRes queryPointOrderList(BossPointOrderListReq req) {
		LogCvt.debug("收到boss查询线上积分兑换订单请求req:" + req);

		// 根据要求优先查询子订单
		PageVo pageVo = req.getPageVo();
		DBObject queryObj = new BasicDBObject();
		MongoPage mongoPage = (MongoPage) BeanUtil.copyProperties(MongoPage.class, pageVo);

		// 根据查询条件查询子订单记录
		if (req.getClientId() != null && !req.getClientId().equals("")) {
			queryObj.put("client_id",req.getClientId());
		}
		if (req.getSubOrderId() != null && !req.getSubOrderId().equals("")) {
			queryObj.put("sub_order_id", req.getSubOrderId());
		}
		if (req.getOrderStatus() != null && !req.getOrderStatus().equals("")) {
			queryObj.put("order_status", req.getOrderStatus());
		}
		if (req.getProductName() != null && !req.getProductName().equals("")) {
			queryObj.put("products.product_name", req.getProductName());
		}
		queryObj.put("type", SubOrderType.online_points_org.getCode());
		
		// 开始时间&&结束时间
		if (req.startTime > 0 || req.getEndTime() > 0) {
			if (req.getStartTime() > 0 && req.getEndTime() > 0) {
				queryObj.put("create_time",
						new BasicDBObject(QueryOperators.GTE, req.startTime).append(QueryOperators.LTE,
								req.getEndTime()));
			} else if (req.getStartTime() > 0) {
				queryObj.put("create_time", new BasicDBObject(QueryOperators.GTE, req.getStartTime()));
			} else if (req.getEndTime() > 0) {
				queryObj.put("create_time", new BasicDBObject(QueryOperators.LTE, req.getEndTime()));
			}
		}
		
		// 从mongo中查询符合条件的子订单数据
		mongoPage = mongo.findByPageWithRedis(mongoPage, queryObj, CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		
		BossPointOrderListRes pointOrderListRes = new BossPointOrderListRes();
		List<BossPointOrderVo> orders = new ArrayList<BossPointOrderVo>();
		
		if (mongoPage.getItems() != null && mongoPage.getItems().size() != 0) {
			
			// 第一步，查询积分子订单列表，并搜集相关的ID集合
			List<SubOrderMongo> subOrderList = mongoPage.getItems() == null ? new ArrayList<SubOrderMongo>() : (List<SubOrderMongo>) mongoPage.getItems();
			List<String> orderIdList = new ArrayList<String>();
			List<String> clientIdList = new ArrayList<String>();
			for (SubOrderMongo subOrder : subOrderList) {
				orderIdList.add(subOrder.getOrderId());
				clientIdList.add(subOrder.getClientId() + "_" + subOrder.getMemberCode());
			} 
			
			// 第二步，初始化相关扩展信息的Map
			batchQueryOrderMongo(orderIdList);
			
			batchQueryRecvInfoListToMap(clientIdList);
			
			findAllAreaListToMap();
			
			// 第三步，组装原始数据和扩展数据
			SubOrderMongo subOrder = null;
			for (int i = 0; subOrderList != null && i < subOrderList.size(); i++) {
				subOrder = subOrderList.get(i);
				
				BossPointOrderVo order = new BossPointOrderVo();
				
				order = fillOrderInfo(order, subOrder);
				
				order = fillProductInfo(order, subOrder);
				
				orders.add(order);
			}
		
		}
		
		pointOrderListRes.setOrders(orders);
		pointOrderListRes.setResultVo(new ResultVo("0000", "操作成功"));
		pageVo.setPageCount(mongoPage.getPageCount());
		pageVo.setTotalCount(mongoPage.getTotalCount());
		pageVo.setLastPageNumber(pageVo.getPageNumber());
		pageVo.setFirstRecordTime(mongoPage.getFirstRecordTime());
		pageVo.setLastRecordTime(mongoPage.getLastRecordTime());
		pointOrderListRes.setPageVo(pageVo);
		return pointOrderListRes;
	}

	/**
	 * 根据子订单的orderId 查询大订单的recvId
	 * @param orderId
	 * @return orderMongo
	 * 
	 * @author liuyanyun
	 */
	private OrderMongo getOrderMongo(String orderId){
		if (Checker.isEmpty(orderId)) {
			return null;
		}
		
		DBObject queryObj = new BasicDBObject();
		queryObj.put("_id", orderId);
		//根据子订单的订单号orderId 查询大订单的收货人号recvId
		return mongo.findOne(queryObj, CB_ORDER, OrderMongo.class);
	}
	
	/**
	 * 批量查询子订单的orderId 查询大订单的recvId
	 * @param orderIdList
	 * @return orderMongo
	 * 
	 * @author liuyanyun
	 */
	@SuppressWarnings("unchecked")
	private void batchQueryOrderMongo(List<String> orderIdList){
		if (orderIdList == null || orderIdList.size() == 0) {
			return;
		}
		
		Collection<String> keysColl = new ArrayList<String>();
		keysColl.add(FieldMapping.ID.getMongoField());
		keysColl.add(FieldMapping.RECV_ID.getMongoField());
		List<OrderMongo> orderList = (List<OrderMongo>) mongo.findAll(new BasicDBObject(FieldMapping.ID.getMongoField(), new BasicDBObject(QueryOperators.IN, orderIdList)), keysColl, CB_ORDER, OrderMongo.class);
		
		if (orderList != null && orderList.size() > 0) {
			for (OrderMongo order : orderList) {
				orderIdRecvIdMap.put(order.getOrderId(), order.getRecvId());
			}
		}
		
	}
	
	/**
	 * 批量获取所有的地区路径树名
	 */
	public void findAllAreaListToMap(){
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			AreaMapper areaMapper = sqlSession.getMapper(AreaMapper.class);
			List<Area> list = areaMapper.findAllAreaListOnlyIdAndTreePathName();
			for (int i = 0; list != null && i < list.size(); i++) {
				areaMap.put(list.get(i).getId() + "", list.get(i).getTreePathName());
			}
		} catch (Exception e) {
			LogCvt.error("查询所有地区信息异常", e);
		} finally {
			if (sqlSession != null){
				sqlSession.close();
			}
		}
	}
	
	/**
	 * 根据收货人号 recvId 查询收获人地址信息
	 * @param recvId
	 * @return favoriteInfo
	 * 
	 * @author liuyanyun
	 */
	private BossPointOrderVo fillOrderInfo(BossPointOrderVo order, SubOrderMongo subOrder){
		order.setOrderId(subOrder.getSubOrderId());// 订单编号
		order.setCreateTime(subOrder.getCreateTime());// 下单时间
		
		// 根据子订单的订单号orderId 查询大订单的收货人号recvId
		String recvId = orderIdRecvIdMap.get(subOrder.getOrderId());
		if (Checker.isEmpty(recvId)) {
			LogCvt.warn("未找到大订单信息, OrderId=" + subOrder.getOrderId());
			return order;
		}
		
		RecvInfo recvInfo = getRecvInfo(subOrder.getClientId(), subOrder.getMemberCode(), recvId);
		if (Checker.isNotEmpty(recvInfo)) {
			fillAreaInfo(recvInfo, order);
		}
		return order;
	}
	
	/**
	 * 根据收货人号 recvId 查询收获人地址信息
	 * @param recvId
	 * @return favoriteInfo
	 * 
	 * @author liuyanyun
	 */
	private void fillAreaInfo(RecvInfo recvInfo, BossPointOrderVo order){
		order.setReciver(recvInfo.getConsignee());// 提货人
		order.setPhone(recvInfo.getPhone());// 手机号
		order.setAddress(recvInfo.getAddress());// 地址
		order.setCity("");
		if(recvInfo.getAreaId() != null) {
			String treePathName = areaMap.get(recvInfo.getAreaId() + "");
			order.setCity(treePathName == null ? "" : treePathName);
		}
	}
	
	/**
	 * 填充商品信息
	 * @param order
	 * @param subOrder
	 * @return BossPointOrderVo
	 * 
	 * @author liuyanyun
	 */
	private BossPointOrderVo fillProductInfo(BossPointOrderVo order, SubOrderMongo subOrder){
		ProductMongo product = (subOrder.getProducts() == null || subOrder.getProducts().size() == 0) ? null : subOrder.getProducts().get(0);
		if (product == null) {
			return order;
		}
		double money = Arith.mul(product.getMoney(), product.getQuantity());// 普通商品购买金额
		double vipMoney = Arith.mul(product.getVipMoney(), product.getVipQuantity());// VIP商品购买金额
		double totalMoney = Arith.add(money, vipMoney);
		
		order.setTotalMoney(Arith.div(totalMoney, 1000));// 总价
		order.setOrderStatus(subOrder.getOrderStatus());// 订单状态
		order.setProductId(product.getProductId());// 商品ID
		order.setProductName(product.getProductName());// 商品名称
		order.setBuyCount(product.getQuantity() + product.getVipQuantity());// 购买数量s
		return order;
	}
	
	/**
	 * 查询收获人地址信息
	 * @param recvId
	 * @return favoriteInfo
	 * 
	 * @author liuyanyun
	 */
	private RecvInfo getRecvInfo(String clientId, Long memberCode, String recv_id){
		List<RecvInfo> recvInfoList = recvIdMap.get(clientId + "_" + memberCode);
		RecvInfo recvInfo = null;
		
		//为了兼容现在积分兑换列表查询  当recvInfoList 为空时  需要findOne RecvInfo信息
		if (recvInfoList == null || recvInfoList.size() < 0) {
		String id = clientId + "_" + memberCode;
		DBObject dbObject = new BasicDBObject();
		dbObject.put("_id", id);
		MerchantOutletFavorite merchantOutletFavorite = (MerchantOutletFavorite)mongo.findOne(dbObject, CB_MERCHANT_OUTLETFAVORITE, MerchantOutletFavorite.class);
		if (Checker.isNotEmpty(merchantOutletFavorite)) {
			recvInfoList = merchantOutletFavorite.getRecvInfo();
		}
		}
		
		for (int j = 0; recvInfoList != null && j < recvInfoList.size(); j++) {
			if (recv_id.equals(recvInfoList.get(j).getRecvId())) {
				recvInfo = recvInfoList.get(j);
				break;
			}
		}
		return recvInfo;
	}
	
	/**
	 * 批量查询收获人地址信息,并填充到Map
	 * @param mofIdList
	 * @author liuyanyun
	 */
	@SuppressWarnings("unchecked")
	private void batchQueryRecvInfoListToMap(List<String> mofIdList){
		Collection<String> keysColl = new ArrayList<String>();
		keysColl.add(FieldMapping.ID.getMongoField());
		keysColl.add("recv_info");
		
		List<MerchantOutletFavorite> favoriteInfoList = (List<MerchantOutletFavorite>) mongo.findAll(new BasicDBObject(FieldMapping.ID.getMongoField(), new BasicDBObject(QueryOperators.IN, mofIdList)), keysColl, CB_MERCHANT_OUTLETFAVORITE, MerchantOutletFavorite.class);
		
		if (Checker.isEmpty(favoriteInfoList)) {
			return;
		}
		
		for (int i = 0; i < favoriteInfoList.size(); i++){
			recvIdMap.put(favoriteInfoList.get(i).getId(), favoriteInfoList.get(i).getRecvInfo());
		}
	}
	
	/**
	 * 预售交易详情
	 * @param clientId
	 * @param id
	 * @return 
	 */
	@Override
	public BossPresellDetialRes getPresellDetial(String clientId, String id) {
		LogCvt.debug("收到boss预售交易详情请求，请求参数clientId:" + clientId +"  id:" + id);
		
		BossPresellDetialRes presellDetial = new BossPresellDetialRes();
		DBObject queryObj = new BasicDBObject();
		queryObj.put("client_id", clientId);
		queryObj.put("_id", new ObjectId(id));
		queryObj.put("type", "2");
		
		// 根据clientId， id 从mongo中查询符合条件的券数据
		Ticket ticket = new Ticket();
		ticket = mongo.findOne(queryObj, CB_TICKET, Ticket.class);
		if(Checker.isEmpty(ticket)){
			return presellDetial;
		}
		
		presellDetial.setOrderId(ticket.getSubOrderId());//订单号
		presellDetial.setTicketId(ticket.getTicketId());//券号
		presellDetial.setPaymentTime(ticket.getCreateTime());//购买时间
		presellDetial.setProductName(ticket.getProductName());//商品名称
		presellDetial.setTicketStatus(ticket.getStatus());//券状态
		if(ticket.getConsumeTime() != null){
			presellDetial.setConsumeTime(ticket.getConsumeTime());//消费时间
		}
		presellDetial.setConsigneeName(ticket.getMemberName());//提货人信息保持跟列表一样
		presellDetial.setConsigneePhone(ticket.getMobile());//提货人手机号
		if (Checker.isNotEmpty(ticket.getOrgName())) {
			presellDetial.setConsigneeAddress(ticket.getOrgName());//提货网点
		}
		
		//获取子订单的提货方式信息
		SubOrderMongo subOrderMongo = null;
		subOrderMongo = fillDeliveryType(ticket.getSubOrderId());
		for (int i = 0; i < subOrderMongo.getProducts().size(); i++) {
			if (ticket.getProductId().equals(subOrderMongo.getProducts().get(i).getProductId())) {
				String option = subOrderMongo.getProducts().get(i).getDeliveryOption();
				presellDetial.setDeliveryType(option);//提货方式
				break;
			}
		}
		
		if (Checker.isNotEmpty(subOrderMongo)) {
			presellDetial.setMemberName(subOrderMongo.getMemberName());//用户名称
		}
		
		// 根据券表中的订单号orderId 查询大订单的收货人号recvId
		OrderMongo orderInfo = getOrderMongo(ticket.getOrderId());
		String recvId = null;
		if (Checker.isEmpty(orderInfo)) {
			LogCvt.warn("未找到大订单信息, OrderId=" + ticket.getOrderId());
		}else {
			recvId = orderInfo.getRecvId();
		}
		
		
		
		
		//获取提货期信息
		Map<String,String> map = new HashMap<String,String>();
		map = getProductRedis(ticket.getClientId(), ticket.getMerchantId(), ticket.getProductId());
		if(Checker.isNotEmpty(map.get("expire_start_time")) && Checker.isNotEmpty(map.get("expire_end_time"))){
			presellDetial.setDeliveryStartTime(Long.valueOf(map.get("expire_start_time")));//提货开始日期 
			presellDetial.setDeliveryEndTime(Long.valueOf(map.get("expire_end_time")));//提货结束日期
		}
		
		return presellDetial;
	}
	
	/**
	 * 获取子订单的提货方式信息
	 */
	private SubOrderMongo fillDeliveryType(String subOrderId) {
		SubOrderMongo subOrderMongo = new SubOrderMongo();
		DBObject queryObj = new BasicDBObject();
		if(Checker.isNotEmpty(subOrderId)) {
			queryObj.put("sub_order_id", subOrderId);
			queryObj.put("type", "2");
			subOrderMongo = mongo.findOne(queryObj, CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		}
		return subOrderMongo;
	}
	
	/**
	 * 获取提货期信息
	 */
	private Map<String,String> getProductRedis(String clientId,String merchantId,String productId) {
		Map<String,String> map = new HashMap<String,String>();
		if(Checker.isNotEmpty(clientId) && Checker.isNotEmpty(merchantId) && Checker.isNotEmpty(productId)) {
			map = commonLogic.getProductRedis(clientId, merchantId, productId);
		}
		return map;
	}
	
	/**
	 * 预售账单查询
	 */
	public ResultBean getBossPresellBillList(BossPresellBillListReq req) { 
		//根据条件查询精品预售子订单信息
		List<SubOrderMongo> subList = null;
		SubOrderMongo subcon = new SubOrderMongo();
		//subcon.setSubOrderId(req.getSubOrderId());
		subcon.setOrderId(req.getSubOrderId());//根据大订单编号来过滤
		subcon.setClientId(req.getClientId());
		subcon.setType(SubOrderType.presell_org.getCode());
		//req.getProductName() 实为商品编号
		if(StringUtils.isNotEmpty(req.getProductName())) {
			ProductMongo pro = new ProductMongo();
			pro.setProductId(req.getProductName());
			List<ProductMongo> proList = new ArrayList<ProductMongo>();
			proList.add(pro);
			subcon.setProducts(proList);
		}
		subcon.setOrderStatus(req.getOrderStatus());
		subcon.setClientId(req.getClientId());
		subList = mongoCommon.getSubOrderList(subcon, req.getStartTime(), req.getEndTime());
		
		//获取区域
		SqlSession sqlSession = null;
		AreaMapper areaMapper = null;
		Map<String, Area> areaPresellMap = new HashMap<String, Area>();
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			areaMapper = sqlSession.getMapper(AreaMapper.class);
			List<Area> areaList = areaMapper.findAllAreaListOnlyIdAndTreePathName();
			if(areaList != null && areaList.size() > 0) {
				for(Area a : areaList) {
					areaPresellMap.put(a.getId().toString(), a);
				}
			}
		} catch (Exception e) { 
			LogCvt.error("查询Area失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		List<BossPresellBillVo> billList = new ArrayList<BossPresellBillVo>();
		if(subList != null && subList.size() > 0) {
			for(SubOrderMongo sub : subList) {
				BossPresellBillVo billVo = new BossPresellBillVo();
				// 订单创建时间
				//Date date = new Date(sub.getCreateTime());
				//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//String dateString = formatter.format(date);
				// 订单号
				String orderId = sub.getOrderId();
				//String sOrgCode = sub.getSorgCode();
				// 商品
				String product_id = null;
				String product_name = null;
				Integer product_buy_num = null;

				String delivery_option = null;//配送方式
				//String delivery_option_desc = null;
				// 提货点
				//String dOrgCdoe = "";//提货网点
				String dOrgName = "";

				List<ProductMongo> products = sub.getProducts();
				if (products != null && products.size() > 0) {
					for (int i = 0; i < products.size(); i++) {
						ProductMongo product = products.get(i); // 遍历数组，把每一个对象转成  json  对象
						product_id = product.getProductId(); // 得到 每个对象中的属性值
						if (req.getProductName().equals(product_id)) {
							product_name = product.getProductName();
							product_buy_num = product.getQuantity();

							delivery_option = product.getDeliveryOption(); // 0 - 配送  1-自提   2-配送或自提
							//delivery_option_desc = StringUtils.equals("1", delivery_option) ? "自提" : "配送";
							//dOrgCdoe = product.getOrgCode();
							dOrgName = product.getOrgName();
							break;
						} else {
							System.out.println("=======还买有其它商品========");
							product_name = product.getProductName();
							continue; // 跳过其他商品
						}
					}
				}

				BasicDBObject bValues = new BasicDBObject();
				bValues.put("_id", orderId);
				List<OrderMongo> orderLists = (List<OrderMongo>)mongo.findAll(bValues, MongoTableName.CB_ORDER, OrderMongo.class);
				
				OrderMongo order = orderLists.get(0);

				String totalPrice = order.getTotalPrice().toString();//总价
				String realPrice = order.getRealPrice().toString();//现金
				String bankPoints =order.getBankPoints().toString();
				String froadPoints = order.getFftPoints().toString();
				String orderStatus = order.getOrderStatus();
				String paymentMethod =  order.getPaymentMethod();

				
				BasicDBObject payValues = new BasicDBObject();
				payValues.put("order_id", orderId);
				payValues.put("payment_reason", "2");
				payValues.put("is_enable", true);
				List<PaymentMongo> payList = (List<PaymentMongo>)mongo.findAll(payValues, MongoTableName.CB_PAYMENT, PaymentMongo.class);
				
				StringBuffer billNo = new StringBuffer();
				if(payList != null && payList.size() > 0) {
					for (int j = 0; j <  payList.size(); j++) {
						PaymentMongo payment = payList.get(j);
						if(j ==  payList.size() -1) {
							billNo.append(payment.getBillNo());
						} else {
							billNo.append(payment.getBillNo() + ";");
						}
					}
				}
				

				// 收货信息
				String recv_id = null;
				recv_id = order.getDeliverId() != null ? order.getDeliverId() : order.getRecvId();
				String clientId = order.getClientId();
				Long memberCode = order.getMemberCode();
				BasicDBObject outletFavoValues = new BasicDBObject();
				outletFavoValues.put("_id", clientId + "_" + memberCode);
				outletFavoValues.put("recv_info.recv_id", recv_id);
				
				MerchantOutletFavorite outletFavorite = mongo.findOne(outletFavoValues, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE, MerchantOutletFavorite.class);
				List<RecvInfo> recvList = outletFavorite.getRecvInfo();
				String consignee = null;
				String phone = null;
				String address = "--";

				if (recvList != null && recvList.size() > 0) {
					for (int i = 0; i < recvList.size(); i++) {
						RecvInfo recv = recvList.get(i); // 遍历数组，把每一个对象转成json对象
						String recvId = recv.getRecvId();
						if (recv_id.equals(recvId)) {
							consignee = recv.getConsignee();
							phone = recv.getPhone();
							// 配送   及  配送或自提   才有配送地址
							if (StringUtils.equals(delivery_option, DeliveryType.home.getCode())
									|| StringUtils.equals(delivery_option, DeliveryType.home_or_take.getCode())) { 
								String treePathName = "";
								//area_id 查城市
								Area ar = areaPresellMap.get(recv.getAreaId().toString());
								if(ar != null) {
									String temp = ar.getTreePathName();
		                			treePathName=temp.toString().replace(",", "");
								}
								address = recv.getAddress();
								address = treePathName + " " + address;
							}
						}
					}
				}

				BasicDBObject refundValues = new BasicDBObject();
				refundValues.put("order_id", orderId);
				List<RefundHistory> fundList = (List<RefundHistory>)mongo.findAll(refundValues, MongoTableName.CB_ORDER_REFUNDS, RefundHistory.class);
				billVo.setSubOrderId(sub.getOrderId());//订单编号
				billVo.setCreateTime(sub.getCreateTime());//下单时间
				billVo.setPaymentMethod(paymentMethod);//支付方式
				billVo.setProductName(product_name);//商品名称
				//总价
				billVo.setTotalPrice(getMoney(Double.valueOf(totalPrice)));
				billVo.setCashAmount(getMoney(Double.valueOf(realPrice)));//现金
				billVo.setBuyQuantity(product_buy_num);//购买数量
				billVo.setBankPoint(getPoint(Double.valueOf(bankPoints)));//银行积分
				billVo.setFroadPoint(getPoint(Double.valueOf(froadPoints)));//联盟积分
				//配送方式
				billVo.setDeliveryOption(delivery_option);
				//配送地址
				billVo.setAddress(address);
				billVo.setConsignee(consignee);//提(收)货人
				billVo.setConsigneePhone(phone);//提(收)货人手机号
				billVo.setOrderStatus(orderStatus);//订单状态
				//所属行社
				String orgCode = sub.getLorgCode();
				if(Checker.isEmpty(orgCode))     orgCode = sub.getTorgCode();
				if(Checker.isEmpty(orgCode))     orgCode = sub.getSorgCode();
				if(Checker.isEmpty(orgCode))     orgCode = sub.getForgCode();
				Org org = new CommonLogicImpl().queryByOrgCode(clientId, orgCode);
				billVo.setOrgName(org!=null?org.getOrgName():"");
				//提货网点
				billVo.setConsigneeOrgName(dOrgName);
				billVo.setBillNo(billNo.toString());//账单号
				//是否退款
				if(fundList != null && fundList.size() > 0) {
					billVo.setIsExistsRefund("0");
				} else {
					billVo.setIsExistsRefund("1");
				}
				billList.add(billVo);
			}
		}
		ResultBean result = new ResultBean(ResultCode.success, billList);
		return result;
	}
	
		// 默认除法运算精度
		private static final int DEF_DIV_SCALE = 10;
		
		private static double getMoney(double momey) {
	        return div(momey, 1000);
	    }
		
		private static double getPoint(double momey) {
	        return div(momey, 1000);
	    }
		
		private static double div(double v1, double v2) {
			return div(v1, v2, DEF_DIV_SCALE);
		}

		private static double div(double v1, double v2, int scale) {
			if (scale < 0) {
				throw new IllegalArgumentException(
						"The   scale   must   be   a   positive   integer   or   zero");
			}
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		private static int mul(double v1, int v2) {
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			return b1.multiply(b2).intValue();
		}
	
	
	public ResultBean getBossPresellBillListNewTmp(BossPresellBillListReq req) { 
		//根据条件查询精品预售子订单信息
		List<SubOrderMongo> subList = null;
		SubOrderMongo subcon = new SubOrderMongo();
		subcon.setSubOrderId(req.getSubOrderId());
		subcon.setClientId(req.getClientId());
		subcon.setType(SubOrderType.presell_org.getCode());
		if(StringUtils.isNotEmpty(req.getProductName())) {
			ProductMongo pro = new ProductMongo();
			pro.setProductId(req.getProductName());
			List<ProductMongo> proList = new ArrayList<ProductMongo>();
			proList.add(pro);
			subcon.setProducts(proList);
		}
		subcon.setOrderStatus(req.getOrderStatus());
		subcon.setClientId(req.getClientId());
		subList = mongoCommon.getSubOrderList(subcon, req.getStartTime(), req.getEndTime());
		if(subList == null || subList.size() == 0) {
			ResultBean result = new ResultBean(ResultCode.success, null);
			return result;
		}
		
		Set<String> orderIdSet = new HashSet<String>();
		for(SubOrderMongo sb : subList) {
			orderIdSet.add(sb.getOrderId());
		}
		
		//获取支付集合
		BasicDBObject payValues = new BasicDBObject();
		BasicDBList payListValues = new BasicDBList();
		Iterator<String> orderIdIt = orderIdSet.iterator();
		while (orderIdIt.hasNext()) {
			payListValues.add(orderIdIt.next());
		}
		payValues.put("order_id", new BasicDBObject(QueryOperators.IN, payListValues));
		payValues.put("is_enable", true);
		if("4".equals(req.getOrderStatus())) {
			payValues.put("payment_status", "2");
		} else if("5".equals(req.getOrderStatus())) {
			payValues.put("payment_status", "5");
		} else if("6".equals(req.getOrderStatus())) {
			payValues.put("payment_status", "4");
		}
		List<PaymentMongo> payList = (List<PaymentMongo>)mongo.findAll(payValues, MongoTableName.CB_PAYMENT, PaymentMongo.class);
		if(payList == null || payList.size() == 0) {
			ResultBean result = new ResultBean(ResultCode.success, null);
			return result;
		}
		
		//查询券信息
		BasicDBObject ticketValues = new BasicDBObject();
		BasicDBList ticketListValues = new BasicDBList();
		for(SubOrderMongo s : subList) {
			ticketListValues.add(s.getSubOrderId());
		}
		ticketValues.put("sub_order_id", new BasicDBObject(QueryOperators.IN, ticketListValues));
		List<Ticket> ticketList = (List<Ticket>)mongo.findAll(ticketValues, MongoTableName.CB_TICKET, Ticket.class);
		Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
		if(ticketList != null && ticketList.size() > 0) {
			for(Ticket t : ticketList) {
				ticketMap.put(t.getSubOrderId(), t);
			}
		}
		
		//获取大订单集合
		BasicDBObject bValues = new BasicDBObject();
		bValues.put("_id", new BasicDBObject(QueryOperators.IN, payListValues));
		List<OrderMongo> orderLists = (List<OrderMongo>)mongo.findAll(bValues, MongoTableName.CB_ORDER, OrderMongo.class);
		Map<String, OrderMongo> orderMap = new HashMap<String, OrderMongo>();
		for(OrderMongo order : orderLists) {
			orderMap.put(order.getOrderId(), order);
		}
		//获取当前大订单的所有退款信息
		Map<String, RefundHistory> fundMap = new HashMap<String, RefundHistory>();
		BasicDBObject fundValues = new BasicDBObject();
		//fundValues.put("shoppingInfo.products.product_name", req.getProductName());
		fundValues.put("order_id", new BasicDBObject(QueryOperators.IN, payListValues));
		List<RefundHistory> fundList = (List<RefundHistory>)mongo.findAll(fundValues, MongoTableName.CB_ORDER_REFUNDS, RefundHistory.class);
		if(fundList != null && fundList.size() > 0) {
			for(RefundHistory r : fundList) {
				fundMap.put(r.getOrderId(), r);
			}
		}
		
		
		//根据大订单查询整合子订单集合信息,根据大订单ID进行分组整合
		Map<String, List<SubOrderMongo>> orderSubMap = new HashMap<String, List<SubOrderMongo>>();
		BasicDBObject orderValues = new BasicDBObject();
		orderValues.put("order_id", new BasicDBObject(QueryOperators.IN, payListValues));
		List<SubOrderMongo> allSubOrderList = (List<SubOrderMongo>)mongo.findAll(orderValues, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		if(allSubOrderList == null || allSubOrderList.size() == 0) {
			ResultBean result = new ResultBean(ResultCode.success, null);
			return result;
		}
		for(SubOrderMongo s : allSubOrderList) {
			List<SubOrderMongo> slist = orderSubMap.get(s.getOrderId());
			if(slist == null) {
				slist = new ArrayList<SubOrderMongo>();
				slist.add(s);
				orderSubMap.put(s.getOrderId(), slist);
			} else {
				slist.add(s);
				orderSubMap.put(s.getOrderId(), slist);
			}
		}
		
		//遍历每个子订单，判断当前子订单是否有
		List<BossPresellBillVo> billList = new ArrayList<BossPresellBillVo>();
		BigDecimal r = new BigDecimal("1000");
		for(SubOrderMongo sub : subList) {
			Set<String> billNoSet = new HashSet<String>();
			//判断当前子订单是否有支付记录,如果是组合支付则有多条支付记录。
			for(PaymentMongo pay : payList) {
				if(sub.getOrderId().equals(pay.getOrderId())) {
					if(StringUtils.isNotBlank(pay.getBillNo())) {
						billNoSet.add(pay.getBillNo());
					}
				}
			}
			BossPresellBillVo billVo = new BossPresellBillVo();
			billVo.setSubOrderId(sub.getSubOrderId());
			billVo.setCreateTime(sub.getCreateTime());
			
			billVo.setPaymentMethod(orderMap.get(sub.getOrderId()).getPaymentMethod());
			List<ProductMongo> proList = sub.getProducts();
			ProductMongo pro = null;
			for(ProductMongo p : proList) {
				if(req.getProductName().equals(p.getProductId())) {
					pro = p;
					billVo.setProductName(p.getProductName());
					break;
				}
			}
			String refundState = sub.getRefundState();
			if(pro != null ) {
				//获取当前订单下的所有子订单
				BasicDBObject qySub = new BasicDBObject();
				qySub.put("order_id", sub.getOrderId());
				//待优化，后期放到内存中在进行查询处理
			//	List<SubOrderMongo> subOrderList = (List<SubOrderMongo>)mongo.findAll(qySub, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
				List<SubOrderMongo> subOrderList = orderSubMap.get(sub.getOrderId());
				
				if(subOrderList != null && subOrderList.size() > 0) {
					int otherCash = 0;
					for(SubOrderMongo otherSub : subOrderList) {
						refundState = otherSub.getRefundState();
						if(!sub.getSubOrderId().equals(otherSub.getSubOrderId())) {
							List<ProductMongo> otherProList = otherSub.getProducts();
							if(otherProList != null && otherProList.size() > 0) {
								for(ProductMongo p : otherProList) {
									otherCash += p.getMoney() + p.getVipMoney() + p.getDeliveryMoney();
								}
							}
						} else {
							List<ProductMongo> otherProList = otherSub.getProducts();
							if(otherProList != null && otherProList.size() > 0) {
								for(ProductMongo p : otherProList) {
									if(!p.getProductId().equals(pro.getProductId())) {
										otherCash += p.getMoney() + p.getVipMoney() ;
									}
								}
							}
						}
					}
					OrderMongo order = orderMap.get(sub.getOrderId());
					
					int refundQuantity = 0;
					int refundVipQuantity = 0;
					if(refundState != null) {//如果有退款情况
						RefundHistory rh = fundMap.get(sub.getOrderId());
						if(RefundState.REFUND_SUCCESS.getCode().equals(rh.getRefundState()) || RefundState.REFUND_MANUAL_SUCCESS.getCode().equals(rh.getRefundState())) {
							List<RefundShoppingInfo> shList = rh.getShoppingInfo();
							if(shList != null && shList.size() > 0) {
								for(RefundShoppingInfo sh : shList) {
									if(sub.getSubOrderId().equals(sh.getSubOrderId())) {
										List<RefundProduct> rpList = sh.getProducts();
										if(rpList != null && rpList.size() > 0) {
											for(RefundProduct rpt : rpList) {
												if(pro.getProductId().equals(rpt.getProductId())) {
													refundQuantity = rpt.getQuantity();
													refundVipQuantity = rpt.getVipQuantity() == null ? 0 : rpt.getVipQuantity(); // 退款的记录里没有vip_quantity，所以要判断一下，避免空指针
													break;
												}
											}
										}
									}
								}
							}
						}
					}
					
					//判断支付方式
					if(PaymentMethod.cash.getCode().equals(order.getPaymentMethod())) {//现金支付
						if(refundState != null) {//如果有退款情况
							BigDecimal b = new BigDecimal(pro.getMoney() * (pro.getQuantity() - refundQuantity) + pro.getVipMoney() * (pro.getQuantity() - refundVipQuantity));
							billVo.setCashAmount(b.divide(r, 10, BigDecimal.ROUND_HALF_EVEN).doubleValue());
							billVo.setBuyQuantity((pro.getQuantity() - refundQuantity) + (pro.getVipQuantity() - refundVipQuantity));
						} else {
							billVo.setBuyQuantity(pro.getQuantity() + pro.getVipQuantity());
							BigDecimal b = new BigDecimal(pro.getMoney() * pro.getQuantity() + pro.getVipMoney() * pro.getQuantity());
							billVo.setCashAmount(b.divide(r, 10, BigDecimal.ROUND_HALF_EVEN).doubleValue());
						}
						billVo.setBankPoint(0);
						billVo.setFroadPoint(0);
					} else if(PaymentMethod.bankPoints.getCode().equals(order.getPaymentMethod()) ) {//银行积分支付
						billVo.setCashAmount(0.0);
						billVo.setFroadPoint(0.0);
						//分到当前产品的积分值
						/////总金额,积分转现金除以pointRate
						//前台要显示积分换算后的现金值 ，这里直接通过商品数量和单价进行计算
						BigDecimal pointsAmount = new BigDecimal("0.0");
						if(refundState != null) {
							BigDecimal b = new BigDecimal (pro.getMoney() * (pro.getQuantity() - refundQuantity) + pro.getVipMoney() * (pro.getVipQuantity() - refundVipQuantity));
							pointsAmount = b.divide(r, 10, BigDecimal.ROUND_HALF_EVEN);
							billVo.setBuyQuantity((pro.getQuantity() - refundQuantity) + (pro.getVipQuantity() - refundVipQuantity));
						} else {
							BigDecimal b = new BigDecimal(pro.getMoney() * pro.getQuantity() + pro.getVipMoney() * pro.getVipQuantity());
							pointsAmount = b.divide(r, 10, BigDecimal.ROUND_HALF_EVEN);
							billVo.setBuyQuantity(pro.getQuantity() + pro.getVipQuantity());
						}
						billVo.setBankPoint(pointsAmount.multiply(new BigDecimal(order.getPointRate())).doubleValue());
					} else if(PaymentMethod.froadPoints.getCode().equals(order.getPaymentMethod())) {//方付通积分支付
						billVo.setCashAmount(0.0);
						billVo.setBankPoint(0.0);
						BigDecimal pointsAmount = new BigDecimal("0.0");
						if(refundState != null) {
							BigDecimal b = new BigDecimal  (pro.getMoney() * (pro.getQuantity() - refundQuantity) + pro.getVipMoney() * (pro.getVipQuantity() - refundVipQuantity));
							pointsAmount = b.divide(r, 10, BigDecimal.ROUND_HALF_EVEN);
							billVo.setBuyQuantity((pro.getQuantity() - refundQuantity) + (pro.getVipQuantity() - refundVipQuantity));
						} else {
							BigDecimal b = new BigDecimal  (pro.getMoney() * pro.getQuantity() + pro.getVipMoney() * pro.getVipQuantity());
							pointsAmount =  b.divide(r, 10, BigDecimal.ROUND_HALF_EVEN);
							billVo.setBuyQuantity(pro.getQuantity() + pro.getVipQuantity());
						}
						billVo.setFroadPoint(pointsAmount.multiply(new BigDecimal(order.getPointRate())).doubleValue());
					} else if(PaymentMethod.bankPointsAndCash.getCode().equals(order.getPaymentMethod())){
						if(otherCash == 0) {
							BigDecimal rp = new BigDecimal(order.getRealPrice());
							BigDecimal fp = new BigDecimal(order.getFftPoints());
							BigDecimal bp = new BigDecimal(order.getBankPoints());
							billVo.setCashAmount(rp.divide(r, 10, BigDecimal.ROUND_HALF_EVEN).doubleValue());
							billVo.setFroadPoint(fp.divide(r, 10, BigDecimal.ROUND_HALF_EVEN).doubleValue());
							billVo.setBankPoint(bp.divide(r, 10, BigDecimal.ROUND_HALF_EVEN).doubleValue());
							billVo.setBuyQuantity(pro.getQuantity() + pro.getVipQuantity());
						} else {
							BigDecimal totalCash = new BigDecimal(order.getRealPrice());
							BigDecimal totalBankPoint = new BigDecimal(order.getBankPoints() );
							BigDecimal totalFroadPoint = new BigDecimal(order.getFftPoints());
							
							BigDecimal currTotalAmount = new BigDecimal(pro.getMoney() + pro.getVipMoney() );
							BigDecimal otherAmount = new BigDecimal(otherCash);
							//当前是商品总价占所有大订单的总价
							//double curr = currTotalAmount.doubleValue();
							//double other = otherAmount.add(currTotalAmount).doubleValue();
							//int amountRate = (int)curr/other;
							BigDecimal amountRate = currTotalAmount.divide(otherAmount.add(currTotalAmount),10, BigDecimal.ROUND_HALF_EVEN);
							
							//当前商品所占总现金
							BigDecimal currCash = totalCash.multiply(amountRate).divide(r);
							BigDecimal currBankPoint = totalBankPoint.multiply(amountRate).divide(r);
							BigDecimal currFroadPoint = totalFroadPoint.multiply(amountRate).divide(r);
							billVo.setCashAmount(currCash.doubleValue());
							billVo.setBankPoint(currBankPoint.doubleValue());
							billVo.setFroadPoint(currFroadPoint.doubleValue());
							billVo.setBuyQuantity(pro.getQuantity() + pro.getVipQuantity());
						}
					}
				}
			}
			//查询券信息
			Ticket t = ticketMap.get(sub.getSubOrderId());
			if(t != null) {
				billVo.setConsignee(t.getMemberName());
				billVo.setConsigneePhone(t.getMobile());
			}
			billVo.setOrderStatus(sub.getOrderStatus());
			if(billNoSet.size() > 0) {
				StringBuffer tmpsb = new StringBuffer();
				Iterator<String> it = billNoSet.iterator();
				while(it.hasNext()) {
					tmpsb.append(it.next() + ";");
				}
				billVo.setBillNo(tmpsb.toString());
			} else {
				billVo.setBillNo("");
			}
			 
			//billVo.setBuyQuantity(pro.getQuantity() + pro.getVipQuantity());
			if(refundState != null) {
				billVo.setIsExistsRefund("0");
			} else {
				billVo.setIsExistsRefund("1");
			}
			billList.add(billVo);
		}
		ResultBean result = new ResultBean(ResultCode.success, billList);
		return result;

	}
	
	

	/**
	 * 预售交易账单查询
	 */
	@SuppressWarnings("unchecked")
	public ResultBean getBossPresellBillListBak(BossPresellBillListReq req) {
		//根据条件查询精品预售子订单信息
		List<SubOrderMongo> subList = null;
		SubOrderMongo subcon = new SubOrderMongo();
		subcon.setSubOrderId(req.getSubOrderId());
		subcon.setClientId(req.getClientId());
		subcon.setType(SubOrderType.presell_org.getCode());
		if(StringUtils.isNotEmpty(req.getProductName())) {
			ProductMongo pro = new ProductMongo();
			pro.setProductName(req.getProductName());
			List<ProductMongo> proList = new ArrayList<ProductMongo>();
			proList.add(pro);
			subcon.setProducts(proList);
		}
		subcon.setOrderStatus(req.getOrderStatus());
		subcon.setClientId(req.getClientId());
		subList = mongoCommon.getSubOrderList(subcon, req.getStartTime(), req.getEndTime());
		if(subList == null || subList.size() == 0) {
			ResultBean result = new ResultBean(ResultCode.success, null);
			return result;
		}
		
		Set<String> orderIdSet = new HashSet<String>();
		for(SubOrderMongo sb : subList) {
			orderIdSet.add(sb.getOrderId());
		}
		
		//获取支付集合
		BasicDBObject payValues = new BasicDBObject();
		BasicDBList payListValues = new BasicDBList();
		Iterator<String> orderIdIt = orderIdSet.iterator();
		while (orderIdIt.hasNext()) {
			payListValues.add(orderIdIt.next());
		}
		payValues.put("order_id", new BasicDBObject(QueryOperators.IN, payListValues));
		payValues.put("is_enable", true);
		if("4".equals(req.getOrderStatus())) {
			payValues.put("payment_status", "2");
		} else if("5".equals(req.getOrderStatus())) {
			payValues.put("payment_status", "5");
		} else if("6".equals(req.getOrderStatus())) {
			payValues.put("payment_status", "4");
		}
		List<PaymentMongo> payList = (List<PaymentMongo>)mongo.findAll(payValues, MongoTableName.CB_PAYMENT, PaymentMongo.class);
		if(payList == null || payList.size() == 0) {
			ResultBean result = new ResultBean(ResultCode.success, null);
			return result;
		}
		
		//查询券信息
		BasicDBObject ticketValues = new BasicDBObject();
		BasicDBList ticketListValues = new BasicDBList();
		for(SubOrderMongo s : subList) {
			ticketListValues.add(s.getSubOrderId());
		}
		ticketValues.put("sub_order_id", new BasicDBObject(QueryOperators.IN, ticketListValues));
		List<Ticket> ticketList = (List<Ticket>)mongo.findAll(ticketValues, MongoTableName.CB_TICKET, Ticket.class);
		Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
		if(ticketList != null && ticketList.size() > 0) {
			for(Ticket t : ticketList) {
				ticketMap.put(t.getSubOrderId(), t);
			}
		}
		
		//获取大订单集合
		BasicDBObject bValues = new BasicDBObject();
		bValues.put("_id", new BasicDBObject(QueryOperators.IN, payListValues));
		List<OrderMongo> orderLists = (List<OrderMongo>)mongo.findAll(bValues, MongoTableName.CB_ORDER, OrderMongo.class);
		Map<String, OrderMongo> orderMap = new HashMap<String, OrderMongo>();
		for(OrderMongo order : orderLists) {
			orderMap.put(order.getOrderId(), order);
		}
		//获取当前大订单的所有退款信息
		Map<String, RefundHistory> fundMap = new HashMap<String, RefundHistory>();
		BasicDBObject fundValues = new BasicDBObject();
		//fundValues.put("shoppingInfo.products.product_name", req.getProductName());
		fundValues.put("order_id", new BasicDBObject(QueryOperators.IN, payListValues));
		List<RefundHistory> fundList = (List<RefundHistory>)mongo.findAll(fundValues, MongoTableName.CB_ORDER_REFUNDS, RefundHistory.class);
		if(fundList != null && fundList.size() > 0) {
			for(RefundHistory r : fundList) {
				fundMap.put(r.getOrderId(), r);
			}
		}
		
		
		//根据大订单查询整合子订单集合信息,根据大订单ID进行分组整合
		Map<String, List<SubOrderMongo>> orderSubMap = new HashMap<String, List<SubOrderMongo>>();
		BasicDBObject orderValues = new BasicDBObject();
		orderValues.put("order_id", new BasicDBObject(QueryOperators.IN, payListValues));
		List<SubOrderMongo> allSubOrderList = (List<SubOrderMongo>)mongo.findAll(orderValues, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		if(allSubOrderList == null || allSubOrderList.size() == 0) {
			ResultBean result = new ResultBean(ResultCode.success, null);
			return result;
		}
		for(SubOrderMongo s : allSubOrderList) {
			List<SubOrderMongo> slist = orderSubMap.get(s.getOrderId());
			if(slist == null) {
				slist = new ArrayList<SubOrderMongo>();
				slist.add(s);
				orderSubMap.put(s.getOrderId(), slist);
			} else {
				slist.add(s);
				orderSubMap.put(s.getOrderId(), slist);
			}
		}
		
		//遍历每个子订单，判断当前子订单是否有
		List<BossPresellBillVo> billList = new ArrayList<BossPresellBillVo>();
		BigDecimal r = new BigDecimal("1000");
		for(SubOrderMongo sub : subList) {
			boolean payFlag = false;
			Set<String> billNoSet = new HashSet<String>();
			//判断当前子订单是否有支付记录,如果是组合支付则有多条支付记录。
			for(PaymentMongo pay : payList) {
				if(sub.getOrderId().equals(pay.getOrderId())) {
					payFlag = true;
					//sb.append((pay.getBillNo() != null? (pay.getBillNo() + ";") : ""));
					if(StringUtils.isNotBlank(pay.getBillNo())) {
						billNoSet.add(pay.getBillNo());
					}
				}
			}
			if(payFlag) {
				BossPresellBillVo billVo = new BossPresellBillVo();
				billVo.setSubOrderId(sub.getSubOrderId());
				billVo.setCreateTime(sub.getCreateTime());
				billVo.setProductName(req.getProductName());
				billVo.setPaymentMethod(orderMap.get(sub.getOrderId()).getPaymentMethod());
				List<ProductMongo> proList = sub.getProducts();
				ProductMongo pro = null;
				for(ProductMongo p : proList) {
					if(req.getProductName().equals(p.getProductName())) {
						pro = p;
						break;
					}
				}
				String refundState = sub.getRefundState();
				if(pro != null ) {
					//获取当前订单下的所有子订单
					BasicDBObject qySub = new BasicDBObject();
					qySub.put("order_id", sub.getOrderId());
					//待优化，后期放到内存中在进行查询处理
				//	List<SubOrderMongo> subOrderList = (List<SubOrderMongo>)mongo.findAll(qySub, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
					List<SubOrderMongo> subOrderList = orderSubMap.get(sub.getOrderId());
					
					if(subOrderList != null && subOrderList.size() > 0) {
						int otherCash = 0;
						for(SubOrderMongo otherSub : subOrderList) {
							refundState = otherSub.getRefundState();
							if(!sub.getSubOrderId().equals(otherSub.getSubOrderId())) {
								List<ProductMongo> otherProList = otherSub.getProducts();
								if(otherProList != null && otherProList.size() > 0) {
									for(ProductMongo p : otherProList) {
										otherCash += p.getMoney() + p.getVipMoney() + p.getDeliveryMoney();
									}
								}
							} else {
								List<ProductMongo> otherProList = otherSub.getProducts();
								if(otherProList != null && otherProList.size() > 0) {
									for(ProductMongo p : otherProList) {
										if(!p.getProductId().equals(pro.getProductId())) {
											otherCash += p.getMoney() + p.getVipMoney() ;
										}
									}
								}
							}
						}
						OrderMongo order = orderMap.get(sub.getOrderId());
						
						int refundQuantity = 0;
						int refundVipQuantity = 0;
						if(refundState != null) {//如果有退款情况
							RefundHistory rh = fundMap.get(sub.getOrderId());
							if(RefundState.REFUND_SUCCESS.getCode().equals(rh.getRefundState()) || RefundState.REFUND_MANUAL_SUCCESS.getCode().equals(rh.getRefundState())) {
								List<RefundShoppingInfo> shList = rh.getShoppingInfo();
								if(shList != null && shList.size() > 0) {
									for(RefundShoppingInfo sh : shList) {
										if(sub.getSubOrderId().equals(sh.getSubOrderId())) {
											List<RefundProduct> rpList = sh.getProducts();
											if(rpList != null && rpList.size() > 0) {
												for(RefundProduct rpt : rpList) {
													if(pro.getProductId().equals(rpt.getProductId())) {
														refundQuantity = rpt.getQuantity();
														refundVipQuantity = rpt.getVipQuantity() == null ? 0 : rpt.getVipQuantity(); // 退款的记录里没有vip_quantity，所以要判断一下，避免空指针
														break;
													}
												}
											}
										}
									}
								}
							}
						}
						
						//判断支付方式
						if(PaymentMethod.cash.getCode().equals(order.getPaymentMethod())) {//现金支付
							if(refundState != null) {//如果有退款情况
								BigDecimal b = new BigDecimal(pro.getMoney() * (pro.getQuantity() - refundQuantity) + pro.getVipMoney() * (pro.getQuantity() - refundVipQuantity));
								billVo.setCashAmount(b.divide(r, 10, BigDecimal.ROUND_HALF_EVEN).doubleValue());
								billVo.setBuyQuantity((pro.getQuantity() - refundQuantity) + (pro.getVipQuantity() - refundVipQuantity));
							} else {
								billVo.setBuyQuantity(pro.getQuantity() + pro.getVipQuantity());
								BigDecimal b = new BigDecimal(pro.getMoney() * pro.getQuantity() + pro.getVipMoney() * pro.getQuantity());
								billVo.setCashAmount(b.divide(r, 10, BigDecimal.ROUND_HALF_EVEN).doubleValue());
							}
							billVo.setBankPoint(0);
							billVo.setFroadPoint(0);
						} else if(PaymentMethod.bankPoints.getCode().equals(order.getPaymentMethod()) ) {//银行积分支付
							billVo.setCashAmount(0.0);
							billVo.setFroadPoint(0.0);
							//分到当前产品的积分值
							/////总金额,积分转现金除以pointRate
							//前台要显示积分换算后的现金值 ，这里直接通过商品数量和单价进行计算
							BigDecimal pointsAmount = new BigDecimal("0.0");
							if(refundState != null) {
								BigDecimal b = new BigDecimal (pro.getMoney() * (pro.getQuantity() - refundQuantity) + pro.getVipMoney() * (pro.getVipQuantity() - refundVipQuantity));
								pointsAmount = b.divide(r, 10, BigDecimal.ROUND_HALF_EVEN);
								billVo.setBuyQuantity((pro.getQuantity() - refundQuantity) + (pro.getVipQuantity() - refundVipQuantity));
							} else {
								BigDecimal b = new BigDecimal(pro.getMoney() * pro.getQuantity() + pro.getVipMoney() * pro.getVipQuantity());
								pointsAmount = b.divide(r, 10, BigDecimal.ROUND_HALF_EVEN);
								billVo.setBuyQuantity(pro.getQuantity() + pro.getVipQuantity());
							}
							billVo.setBankPoint(pointsAmount.multiply(new BigDecimal(order.getPointRate())).doubleValue());
						} else if(PaymentMethod.froadPoints.getCode().equals(order.getPaymentMethod())) {//方付通积分支付
							billVo.setCashAmount(0.0);
							billVo.setBankPoint(0.0);
							BigDecimal pointsAmount = new BigDecimal("0.0");
							if(refundState != null) {
								BigDecimal b = new BigDecimal  (pro.getMoney() * (pro.getQuantity() - refundQuantity) + pro.getVipMoney() * (pro.getVipQuantity() - refundVipQuantity));
								pointsAmount = b.divide(r, 10, BigDecimal.ROUND_HALF_EVEN);
								billVo.setBuyQuantity((pro.getQuantity() - refundQuantity) + (pro.getVipQuantity() - refundVipQuantity));
							} else {
								BigDecimal b = new BigDecimal  (pro.getMoney() * pro.getQuantity() + pro.getVipMoney() * pro.getVipQuantity());
								pointsAmount =  b.divide(r, 10, BigDecimal.ROUND_HALF_EVEN);
								billVo.setBuyQuantity(pro.getQuantity() + pro.getVipQuantity());
							}
							billVo.setFroadPoint(pointsAmount.multiply(new BigDecimal(order.getPointRate())).doubleValue());
						} else if(PaymentMethod.bankPointsAndCash.getCode().equals(order.getPaymentMethod())){
							if(otherCash == 0) {
								BigDecimal rp = new BigDecimal(order.getRealPrice());
								BigDecimal fp = new BigDecimal(order.getFftPoints());
								BigDecimal bp = new BigDecimal(order.getBankPoints());
								billVo.setCashAmount(rp.divide(r, 10, BigDecimal.ROUND_HALF_EVEN).doubleValue());
								billVo.setFroadPoint(fp.divide(r, 10, BigDecimal.ROUND_HALF_EVEN).doubleValue());
								billVo.setBankPoint(bp.divide(r, 10, BigDecimal.ROUND_HALF_EVEN).doubleValue());
								billVo.setBuyQuantity(pro.getQuantity() + pro.getVipQuantity());
							} else {
								BigDecimal totalCash = new BigDecimal(order.getRealPrice());
								BigDecimal totalBankPoint = new BigDecimal(order.getBankPoints() );
								BigDecimal totalFroadPoint = new BigDecimal(order.getFftPoints());
								
								BigDecimal currTotalAmount = new BigDecimal(pro.getMoney() + pro.getVipMoney() );
								BigDecimal otherAmount = new BigDecimal(otherCash);
								//当前是商品总价占所有大订单的总价
								//double curr = currTotalAmount.doubleValue();
								//double other = otherAmount.add(currTotalAmount).doubleValue();
								//int amountRate = (int)curr/other;
								BigDecimal amountRate = currTotalAmount.divide(otherAmount.add(currTotalAmount),10, BigDecimal.ROUND_HALF_EVEN);
								
								//当前商品所占总现金
								BigDecimal currCash = totalCash.multiply(amountRate).divide(r);
								BigDecimal currBankPoint = totalBankPoint.multiply(amountRate).divide(r);
								BigDecimal currFroadPoint = totalFroadPoint.multiply(amountRate).divide(r);
								billVo.setCashAmount(currCash.doubleValue());
								billVo.setBankPoint(currBankPoint.doubleValue());
								billVo.setFroadPoint(currFroadPoint.doubleValue());
								billVo.setBuyQuantity(pro.getQuantity() + pro.getVipQuantity());
							}
						}
					}
				}
				//查询券信息
				Ticket t = ticketMap.get(sub.getSubOrderId());
				if(t != null) {
					billVo.setConsignee(t.getMemberName());
					billVo.setConsigneePhone(t.getMobile());
				}
				billVo.setOrderStatus(sub.getOrderStatus());
				if(billNoSet.size() > 0) {
					StringBuffer tmpsb = new StringBuffer();
					Iterator<String> it = billNoSet.iterator();
					while(it.hasNext()) {
						tmpsb.append(it.next() + ";");
					}
					billVo.setBillNo(tmpsb.toString());
				} else {
					billVo.setBillNo("");
				}
				 
				//billVo.setBuyQuantity(pro.getQuantity() + pro.getVipQuantity());
				if(refundState != null) {
					billVo.setIsExistsRefund("0");
				} else {
					billVo.setIsExistsRefund("1");
				}
				billList.add(billVo);
			}
		}
		ResultBean result = new ResultBean(ResultCode.success, billList);
		return result;
	}

	/**
	 * 精品预售账单报表导出
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ExportResultRes getBossPresellBillExport(BossPresellBillListReq req) {
		ResultVo rb =  new ResultVo();
		ExportResultRes resultRes = new ExportResultRes();
		List<String> header = new ArrayList<String>();
		header.add("订单编号");
		header.add("下单时间");
		header.add("商品名称");
		header.add("购买数量");
		header.add("支付方式");
		header.add("总价");
		header.add("现金");
		header.add("银行积分");
		header.add("联盟积分");
		header.add("配送方式");
		header.add("配送地址");
		header.add("提（收）货人");
		header.add("提（收）货人手机号");
		header.add("所属行社");
		header.add("提货网点");
		header.add("订单状态");
		header.add("账单号");
		header.add("是否退款");
		
		List<List<String>> allData = new ArrayList<List<String>>();
		List<BossPresellBillVo> list = null;
		String url = null;
		try {
			ResultBean res = getBossPresellBillList(req);
			if (res != null) {
				list = (List<BossPresellBillVo>) res.getData();
				allData = convertExportPreConllection(list);
			}
			
			ExcelWriter excelWriter = new ExcelWriter(allData.size() == 0 ? 1 : allData.size());
			try {
				excelWriter.write(header, allData, "预售账单列表", "预售账单列表");
				url = excelWriter.getUrl();
			} catch (Exception e) {
				LogCvt.error("导出预售账单列表失败", e);
				rb.setResultCode(ResultCode.failed.getCode());
				rb.setResultDesc(ResultCode.failed.getMsg());
			}
			
		} catch (Exception e) {
			LogCvt.error("导出出错", e);
			rb.setResultCode(ResultCode.failed.getCode());
			rb.setResultDesc(ResultCode.failed.getMsg());
		}
		if(StringUtils.isNotEmpty(url)) {
			rb.setResultCode(ResultCode.success.getCode());
			rb.setResultDesc(ResultCode.success.getMsg());
		} else {
			rb.setResultCode(ResultCode.failed.getCode());
			rb.setResultDesc(ResultCode.failed.getMsg());
		}
		resultRes.setResultVo(rb);
		resultRes.setUrl(url);
		return resultRes;
	}
	
	private List<List<String>>  convertExportPreConllection(List<BossPresellBillVo> list) {
		List<List<String>> perList = new ArrayList<List<String>>();
		if(list == null || list.size() == 0) {
			return perList;
		}
		for(BossPresellBillVo vo : list) {
			List<String> rowList = new ArrayList<String>();
			
			rowList.add(StringUtils.isNotEmpty(vo.getSubOrderId())? vo.getSubOrderId() : "--");
			rowList.add(vo.getCreateTime() != 0 ? DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(vo.getCreateTime())) : "--");
			rowList.add(StringUtils.isNotEmpty(vo.getProductName())? vo.getProductName() : "--");
			rowList.add(vo.getBuyQuantity() + "");
			rowList.add(StringUtils.isNotEmpty(vo.getPaymentMethod())? PaymentMethod.getByCode(vo.getPaymentMethod()).getDescribe() : "--");
			//总价
			rowList.add(vo.getTotalPrice() + "");
			rowList.add(vo.getCashAmount() + "");
			rowList.add(vo.getBankPoint() + "");
			rowList.add(vo.getFroadPoint() + "");
			//配送方式
			rowList.add(StringUtils.isNotEmpty(vo.getDeliveryOption())? DeliveryType.getType(vo.getDeliveryOption()).getDescribe() : "--");
			//配送地址
			rowList.add(StringUtils.isNotEmpty(vo.getAddress())? vo.getAddress() : "--");
			rowList.add(StringUtils.isNotEmpty(vo.getConsignee())? vo.getConsignee() : "--");
			rowList.add(StringUtils.isNotEmpty(vo.getConsigneePhone())? vo.getConsigneePhone() : "--");
			//所属行社
			rowList.add(vo.getOrgName() + "");
			//提货网点
			rowList.add(StringUtils.isNotEmpty(vo.getConsigneeOrgName())? vo.getConsigneeOrgName() : "--");
			rowList.add(StringUtils.isNotEmpty(vo.getOrderStatus())? OrderStatus.getType(vo.getOrderStatus()).getDescribe() : "--");
			rowList.add(StringUtils.isNotEmpty(vo.getBillNo())? vo.getBillNo() : "--");
			rowList.add("0".equals(vo.getIsExistsRefund()) ? "是" : "否");
			perList.add(rowList);
		}
		return perList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultBean getOrderList(OrderQueryByBossCondition condition, PageVo pageVo, String flag) throws FroadBusinessException {
		if(EmptyChecker.isNotEmpty(condition.getPaymentId())) {
			DBObject payQuery = new BasicDBObject();
			payQuery.put("payment_id", condition.getPaymentId());
			PaymentMongoEntity payment =(PaymentMongoEntity) mongo.findOne(payQuery, MongoTableName.CB_PAYMENT, PaymentMongoEntity.class);
			 if(EmptyChecker.isEmpty(payment)) {
                ResultCode resultCode = ResultCode.member_order_is_empty;
                throw new FroadBusinessException(resultCode.getCode(), "根据支付编号查询记录为空");
	         }
            condition.setOrderId(payment.getOrder_id());
		}
		if(EmptyChecker.isNotEmpty(condition.getPhone())) {
			List<String> recvIds = new ArrayList<String>();
			List<RecvInfo> recvList = mongoCommon.queryRecvInfos(condition.getPhone());
			for (RecvInfo info : recvList) {
                recvIds.add(info.getRecvId());
            }
			if (EmptyChecker.isNotEmpty(recvIds)) {
	            condition.setRecvId(recvIds);
	        }
		}
		PageEntity<OrderQueryByBossCondition> pageEntity = new PageEntity<OrderQueryByBossCondition>();
        pageEntity.setCondition(condition);

        pageEntity.convert(pageVo);
        // 按时间反序查询
        pageEntity.setOrderByColumn("create_time");
        pageEntity.setOrderByType(OrderByType.desc);

        List<BossQueryOrderVo> resultList = new ArrayList<BossQueryOrderVo>();
        MongoPage mongoPage = mongoCommon.queryOrderOfBossByCondition(pageEntity, flag);
        
        long start1 = System.currentTimeMillis();
        if(mongoPage != null && mongoPage.getItems() != null) {
        	 convertPage(mongoPage, pageVo);
             List<OrderMongo> list = (List<OrderMongo>) mongoPage.getItems();
             for (OrderMongo order : list) {
                BossQueryOrderVo vo = getBossQueryOrderVo(order);
                 resultList.add(vo);
             }
        }
        long end1 = System.currentTimeMillis();
        System.out.println("后期业务处理耗时：" + (end1 - start1) + "ms");
        return new ResultBean(ResultCode.success, resultList);
	}
	
	private BossQueryOrderVo getBossQueryOrderVo(OrderMongo order) {
		BossQueryOrderVo vo = new BossQueryOrderVo();
		vo.setClientId(order.getClientId());
		vo.setCreateSource(order.getCreateSource());
		vo.setCreateTime(order.getCreateTime());
		
		 vo.setPoints(getPoint(order));
         vo.setRealPrice(getRealPrice(order));
         vo.setTotalPrice(getSubTotalMoney(order));
         
         vo.setIsQrcode(order.getIsQrcode());
         vo.setMemberCode(order.getMemberCode() != null? order.getMemberCode() : 0);
         vo.setMerchantUserId(order.getMerchantUserId() != null? order.getMerchantUserId() : 0);
         vo.setOrderId(order.getOrderId());
         vo.setOrderStatus(order.getOrderStatus());
         vo.setPaymentMethod(order.getPaymentMethod());
         vo.setPaymentTime(order.getPaymentTime() != null? order.getPaymentTime() : 0);
         vo.setRemark(order.getRemark());
         return vo;
	}
	
	 private void convertPage(MongoPage page, PageVo vo) {
        vo.setPageCount(page.getPageCount());
        vo.setPageNumber(page.getPageNumber());
        vo.setPageSize(page.getPageSize());
        vo.setTotalCount(page.getTotalCount());
        
        vo.setLastPageNumber(page.getPageNumber());
        vo.setHasNext(page.getPageCount() > page.getPageNumber());
        
        vo.setFirstRecordTime(page.getFirstRecordTime());
        vo.setLastRecordTime(page.getLastRecordTime());
    }

    /**
     * 按支付方式获取积分-按规则获取不同的方付通积分和银行积分
     * 
     * @param orderMongo
     * @return <pre>
     * 
     * @version V1.0 修改人：Arron 日期：2015年4月14日 上午11:38:58
     * 
     * </pre>
     */
    private double getPoint(OrderMongo orderMongo) {
        int point = 0;
        // 积分 or 积分+现金
        if (EmptyChecker.isNotEmpty(orderMongo.getPaymentMethod()) && !OrderStatus.create.getCode().equals(orderMongo.getOrderStatus())) {
            if (StringUtils.equals(PaymentMethod.froadPoints.getCode(), orderMongo.getPaymentMethod())
                    || StringUtils.equals(PaymentMethod.froadPointsAndCash.getCode(), orderMongo.getPaymentMethod())) {
                point = orderMongo.getFftPoints();
            } else if (StringUtils.equals(PaymentMethod.bankPoints.getCode(), orderMongo.getPaymentMethod())
                    || StringUtils.equals(PaymentMethod.bankPointsAndCash.getCode(), orderMongo.getPaymentMethod())) {
                point = orderMongo.getBankPoints();
            }
        }
        return Arith.div(point, 1000);
    }

    private double getRealPrice(OrderMongo order) {
        return Arith.div(order.getRealPrice(), 1000);
    }
    private double getSubTotalMoney(OrderMongo order) {
        return Arith.div(order.getTotalPrice(), 1000);
    }

	   
    
	@SuppressWarnings("unchecked")
	@Override
	public ExportResultRes exportOrderList(OrderQueryByBossCondition condition) {
		Map<String, MerchantUser> merchantUserMap = new HashMap<String, MerchantUser>();
		ResultVo rb =  new ResultVo();
		ExportResultRes resultRes = new ExportResultRes();
		List<String> header = new ArrayList<String>();
		header.add("订单编号");
		header.add("订单状态");
		header.add("用户编号");
		header.add("支付方式");
		header.add("收银交易操作人");
		header.add("总金额");
		header.add("积分类型");
		header.add("积分金额");
		header.add("现金金额");
		header.add("积分兑换比例");
		header.add("赠送积分数");
		header.add("所属客户端");
		header.add("支付时间");
		header.add("订单来源");
		header.add("备注");
		List<List<String>> data = new ArrayList<List<String>>();
		Map<String, Client> clientMap = new HashMap<String, Client>();
		int pageNo = 0;
		int pageSize = 20000;
		PageVo pageVo = new PageVo();
		pageVo.setPageSize(pageSize);
		List<BossQueryOrderVo> list = null;
		ResultBean res = null;
		String url = null;
		try {
			List<MerchantUser> merchantUserList = getAllMerchantUser();
			if(merchantUserList != null && merchantUserList.size() > 0) {
				for(MerchantUser user : merchantUserList) {
					merchantUserMap.put(user.getId() + "", user);
				}
			}
			ExcelWriter excelWriter = new ExcelWriter(pageSize);
			boolean isSuccess = true;
			
			do {
				pageNo++;
				pageVo.setPageNumber(pageNo);
				res = getOrderList(condition, pageVo, "1");
				if(res != null) {
					list = (List<BossQueryOrderVo>)res.getData();
					
					data = convertExportCollection(list, merchantUserMap, clientMap);
					
					try {
						excelWriter.write(header, data, "订单列表", "订单列表");
					} catch (Exception e) {
						LogCvt.error("导出订单列表失败", e);
						isSuccess = false;
						break;
					}
					
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
			LogCvt.error("导出订单列表失败", e);
			rb.setResultCode(ResultCode.failed.getCode());
			rb.setResultDesc(ResultCode.failed.getMsg());
		}
		resultRes.setResultVo(rb);
		resultRes.setUrl(url);
		return resultRes;
	}
	
	
	private List<List<String>> convertExportCollection(List<BossQueryOrderVo> list, Map<String, MerchantUser> merchantUserMap, Map<String, Client> clientMap) {
		List<List<String>> perList = new ArrayList<List<String>>();
		
		if(list == null || list.size() == 0) {
			return perList;
		}
		
		for(BossQueryOrderVo vo : list) {
			List<String> rowList = new ArrayList<String>();
			rowList.add(StringUtils.isNotEmpty(vo.getOrderId())? vo.getOrderId() : "--");
			rowList.add(StringUtils.isNotEmpty(vo.getOrderStatus())? OrderStatus.getType(vo.getOrderStatus()).getDescribe() : "--");
			rowList.add(vo.getMemberCode() + "");
			rowList.add(StringUtils.isNotEmpty(vo.getPaymentMethod())? PaymentMethod.getByCode(vo.getPaymentMethod()).getDescribe() : "--");
			
			if(vo.getMerchantUserId() != 0) {
				MerchantUser u = merchantUserMap.get(vo.getMerchantUserId() + "");
				rowList.add(u != null? u.getUsername() : "--");
			} else {
				rowList.add("--");
			}
			rowList.add(vo.getTotalPrice() + "");
			rowList.add(vo.getPointType() + "");
			rowList.add(vo.getPoints() + "");
			rowList.add(vo.getRealPrice() + "");
			rowList.add(vo.getPointRate());
			rowList.add(vo.getGivePoints() + "");
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
			
			if (vo.getPaymentTime() != 0) {
				rowList.add(DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(vo.getPaymentTime())));
			} else {
				rowList.add("--");
			}
			
			if(CreateSource.android.getCode().equals(vo.getCreateSource())) {
				rowList.add(CreateSource.android.getDescribe());
			} else if(CreateSource.ipad.getCode().equals(vo.getCreateSource())) {
				rowList.add(CreateSource.ipad.getDescribe());
			} else if(CreateSource.iphone.getCode().equals(vo.getCreateSource())) {
				rowList.add(CreateSource.iphone.getDescribe());
			} else if(CreateSource.pc.getCode().equals(vo.getCreateSource())) {
				rowList.add(CreateSource.pc.getDescribe());
			}
			rowList.add(StringUtils.isNotEmpty(vo.getRemark())? vo.getRemark() : "");
			perList.add(rowList);
		}
		return perList;
	}
	
	
	private List<MerchantUser> getAllMerchantUser() {
		List<MerchantUser> result = null;
		SqlSession sqlSession = null;
		MerchantUserMapper merchantUserMapper = null;
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantUserMapper = sqlSession.getMapper(MerchantUserMapper.class);
			result = merchantUserMapper.findAll();
		} catch (Exception e) { 
			LogCvt.error("查询MerchantUser失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result;
	}

	@Override
	public ResultBean getSubOrderDetailOfBossByOrderId(String clientId, String orderId) throws FroadBusinessException {
		List<BossSubOrderVo> listVo = new ArrayList<BossSubOrderVo>();
        List<SubOrderMongo> list = mongoCommon.getSubOrderListByOrderId(clientId, orderId);
        if (EmptyChecker.isEmpty(list)) {
            throw new FroadBusinessException(ResultCode.member_suborder_query_empty.getCode(), "查找子订单列表为空");
        }
        for (SubOrderMongo subOrder : list) {
        	BossSubOrderVo vo = new BossSubOrderVo();
            vo.setMerchantName(subOrder.getMerchantName());
            vo.setMerchantId(subOrder.getMerchantId());
            vo.setType(subOrder.getType());
            vo.setSubOrderId(subOrder.getSubOrderId());
            /**
             * 只有名优特惠查询有发货相关状态
             */
            if (OrderType.special_merchant.getCode().equals(subOrder.getType())) {
                vo.setDeliveryState(subOrder.getDeliveryState());
            }
            List<BossSubOrderProductVo> productList = new ArrayList<BossSubOrderProductVo>();
            for (ProductMongo product : subOrder.getProducts()) {
            	BossSubOrderProductVo productVo = (BossSubOrderProductVo) BeanUtil.copyProperties(BossSubOrderProductVo.class, product);
                // 金额处理
                productVo.setDeliveryMoney(Arith.div(product.getDeliveryMoney(), 1000));
                productVo.setMoney(getMoney(product));
                productVo.setPoints(product.getPoints()==null?0:( product.getPoints()/ 1000));
                productVo.setVipMoney(Arith.div(product.getVipMoney(), 1000));
                /**退款数量*/
                refundProduct(productVo, subOrder.getSubOrderId(), subOrder.getOrderId());
                productList.add(productVo);
            }
            vo.setProducts(productList);
            listVo.add(vo);
        }
        return new ResultBean(ResultCode.success, listVo);
	}
	
	private double getMoney(ProductMongo product) {
		return Arith.div(product.getMoney(), 1000);
	}
	
	private void refundProduct(BossSubOrderProductVo vo, String subOrderId, String orderId) {
		int refundNumber = getRefund(subOrderId, orderId, vo.getProductId());
		vo.setSurplusNumber(vo.getQuantity()-refundNumber); // 总量减去退款数量
		vo.setRefundNumber(refundNumber); // 退款数量
	}
	
	private int getRefund(String subOrderId,String orderId, String productId) {
		int number = 0;
		Map<String, Integer> map = mongoCommon.getRefundedProduct(orderId);
		if(EmptyChecker.isNotEmpty(map)) {
			Integer i = map.get(subOrderId + "_" + productId);
			if(EmptyChecker.isNotEmpty(i)) {
				number = i;
			}
		}
		return number;
	}
	
	/**
     * 查询预售商品的所有商品信息
     * @author liuyanyun
     * 
     */
    private List<ProductPresell> getProductPresellInfo(){
    	
        SqlSession sqlSession = null;
        ProductMapper mapper = null;
        List<ProductPresell> productPresells = new ArrayList<ProductPresell>();
        try { 
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(ProductMapper.class);
            productPresells = mapper.getProductPresellByType();
        } catch (Exception e) { 
            LogCvt.error("获取预售商品信息异常，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        } 
        return productPresells;
    }

	
	@Override
	public MongoPage queryPrefPayOrderList(BossPrefPayOrderPageReq req, MongoPage page) throws Exception {
		try {
			DBObject query = setPrefPayOrderCondition(req);
			Sort sort = new Sort("create_time", OrderBy.DESC);
			page.setSort(sort);
			page = mongoCommon.queryOrderInfoByPage(query, page);
			
			List<OrderMongo> orderList = Checker.isNotEmpty(page.getItems()) ? (List<OrderMongo>) page.getItems() : new ArrayList<OrderMongo>();
			List<BossPrefPayOrderListInfo> infos = new ArrayList<BossPrefPayOrderListInfo>();
			BossPrefPayOrderListInfo info = null;
			Client client = null;
			
			List<PaymentMongo> payList = null;
			StringBuilder billNosStr = null;
			OutletDetail outletDetail = null;
			for(OrderMongo order : orderList){
				info = new BossPrefPayOrderListInfo();
				info.setOrderId(order.getOrderId());
				info.setClientId(order.getClientId());
				client = commonLogic.getClientById(order.getClientId());
				info.setClientName(client != null ? client.getBankName() : "");
				info.setOrderStatus(order.getOrderStatus());
				info.setMemberName(BossUtil.hideIdCardNum(order.getMemberName()));
				info.setPhone(order.getPhone());
				
				if(Checker.isEmpty(req.getOrderType())){
					if(Checker.isNotEmpty(order.getIsPrefPay()) && order.getIsPrefPay() == 1){
						//惠付支付
						info.setOrderType("2");
					}else{
						//面对面支付
						info.setOrderType("1");
					}
				}else{
					info.setOrderType(req.getOrderType());
				}
				
				info.setMerchantName(order.getMerchantName());
				outletDetail = getOutletInfoByOutletId(order.getOutletId());
				info.setOutletName(outletDetail != null ? outletDetail.getOutletName() : "");
				info.setPaymentMethod(order.getPaymentMethod());
				info.setCreateTime(order.getCreateTime());
				info.setPaymentTime(order.getPaymentTime());
				info.setCreateSource(order.getCreateSource());
				info.setTotalPrice(Arith.div(order.getTotalPrice(), 1000, 2));
				info.setCash(Arith.div(order.getRealPrice(), 1000, 2));
				info.setFftPoints(Arith.div(order.getFftPoints(), 1000, 2));
				info.setBankPoints(Arith.div(order.getBankPoints(), 1000, 2));
				info.setPointRate(order.getPointRate());
				
				payList = getPaymentInfos(order.getClientId(), order.getOrderId(), null, PaymentReason.payment.getCode());
				if(Checker.isNotEmpty(payList)){
					billNosStr = new StringBuilder();
					for(int i = 0; i < payList.size(); i++){
						PaymentMongo p = payList.get(i);
						if(StringUtils.isNotBlank(p.getBillNo())){
							billNosStr.append(p.getBillNo()).append("，");
						}
					}
					String billNos = billNosStr.toString();
					info.setBillNos(billNos.length() > 0 ? billNos.substring(0, billNos.length()-1) : billNos);
				}
				
				infos.add(info);
			}
			page.setItems(infos);
		} catch (Exception e) {
			LogCvt.error("面对面惠付订单列表查询异常", e);
			throw e;
		}
		return page;
	}

	
	private DBObject setPrefPayOrderCondition(BossPrefPayOrderPageReq req){
		DBObject query = new BasicDBObject();
		if(Checker.isNotEmpty(req.getOrderId())){
			query.put(FieldMapping.ID.getMongoField(), BossUtil.getFuzzyStrPattern(req.getOrderId()));
		}
		if(Checker.isNotEmpty(req.getMemberName())){
			query.put(FieldMapping.MEMBER_NAME.getMongoField(), BossUtil.getFuzzyStrPattern(req.getMemberName()));
		}
		if(Checker.isNotEmpty(req.getClientId())){
			query.put(FieldMapping.CLIENT_ID.getMongoField(), req.getClientId());
		}
		if(Checker.isNotEmpty(req.getOrderStatus())){
			query.put("order_status", req.getOrderStatus());
		}
		if(Checker.isNotEmpty(req.getPaymentMethod())){
			query.put("payment_method", req.getPaymentMethod());
		}
		if(Checker.isNotEmpty(req.getCreateSource())){
			query.put("create_source", req.getCreateSource());
		}
		if(req.getBegTime() > 0 && req.getEndTime() > 0 && req.getEndTime() > req.getBegTime()){
			DBObject peroid = new BasicDBObject();
			peroid.put(QueryOperators.GTE, req.getBegTime());
			peroid.put(QueryOperators.LTE, req.getEndTime());
			query.put(FieldMapping.CREATE_TIME.getMongoField(), peroid);
		}
		if(Checker.isNotEmpty(req.getOrderType())){
			if("1".equals(req.getOrderType())){
				//面对面订单  isQrcode = 1   ， isPrefPay 没这个字段  或  isPrefPay=0
				query.put("is_qrcode", 1);
				BasicDBList list = new BasicDBList();
				list.add(new BasicDBObject("is_pref_pay", new BasicDBObject(QueryOperators.EXISTS, false)));
				list.add(new BasicDBObject("is_pref_pay", 0));
				query.put(QueryOperators.OR, list);
			}else if("2".equals(req.getOrderType())){
				// 惠付订单 isQrcode = 1  且 isPrefPay=1
				query.put("is_qrcode", 1);
				query.put("is_pref_pay", 1);
			}
		}else{
			BasicDBList typeList = new BasicDBList();
			typeList.add(new BasicDBObject("is_qrcode", 1));//面对面
			typeList.add(new BasicDBObject("is_pref_pay", 1));//惠付
			query.put(QueryOperators.OR, typeList);
		}
		return query;
	}
	
	
	
	
	@Override
	public BossPrefPayOrderDetailInfo getPrefPayOrderDetail(String clientId, String orderId) throws Exception {
		BossPrefPayOrderDetailInfo detailInfo = new BossPrefPayOrderDetailInfo();
		try {
			OrderMongo order = mongoCommon.findOrderByOrderId(orderId);
			if(Checker.isNotEmpty(order)){
				detailInfo.setOrderId(order.getOrderId());
				Client client = commonLogic.getClientById(clientId);
				detailInfo.setClientName(client != null ? client.getBankName() : "");
				detailInfo.setCreateTime(order.getCreateTime());
				detailInfo.setOrderStatus(order.getOrderStatus());
				detailInfo.setCreateSource(order.getCreateSource());
				detailInfo.setMerchantName(order.getMerchantName());
				OutletDetail outletDetail = getOutletInfoByOutletId(order.getOutletId());
				detailInfo.setOutletName(outletDetail != null ? outletDetail.getOutletName() : "");
				
				if(Checker.isNotEmpty(order.getIsPrefPay()) && order.getIsPrefPay() == 1){
					detailInfo.setOrderType("2");
				}else{
					detailInfo.setOrderType("1");
				}
				
				List<PaymentMongo> payments = getPaymentInfos(clientId, orderId, null, PaymentReason.payment.getCode());
				Integer paymentTypeDetail = null;
				PaymentMongo p = null;
				if(Checker.isNotEmpty(payments)){
					StringBuilder billNosStr = new StringBuilder();
					for(int i = 0; i < payments.size(); i++){
						p = payments.get(i);
						if(Checker.isNotEmpty(p.getPaymentTypeDetails()) && p.getPaymentTypeDetails() == 20){
							paymentTypeDetail = p.getPaymentTypeDetails();
						}
						if(Checker.isNotEmpty(p.getBillNo())){
							billNosStr.append(p.getBillNo()).append("，");
						}else{
							billNosStr.append(p.getRemark()).append("，");
						}
					}
					String billNos = billNosStr.toString();
					detailInfo.setBillNos(billNos.length() > 0 ? billNos.substring(0, billNos.length()-1) : billNos);
				}
				
				detailInfo.setMemberName(BossUtil.hideIdCardNum(order.getMemberName()));
				MemberInformationService.Iface memberInformationService = 
						(MemberInformationService.Iface)ThriftClientProxyFactory.createIface(MemberInformationService.class.getName(), MemberInformationService.class.getSimpleName(), BossConstants.THRIFT_ORDER_HOST, Integer.valueOf(BossConstants.THRIFT_ORDER_PORT));
				MemberInfoVo memberInfo = null;
				try {
					memberInfo = memberInformationService.selectUserByMemberCode(order.getMemberCode(), order.getClientId());
				} catch (Exception e) {
					LogCvt.error("通过会员编号查询会员信息异常", e);
					throw e;
				}
				if(Checker.isNotEmpty(memberInfo)) {
					if(Checker.isEmpty(paymentTypeDetail) || paymentTypeDetail != 20){
						//用户的真实姓名，显示为支付帮卡信息中的真实姓名。贴膜卡支付不显示。
						detailInfo.setUserName(memberInfo.getUname());
					}
				}
				detailInfo.setPaymentMethod(order.getPaymentMethod());
				detailInfo.setPaymentTime(order.getPaymentTime());
				
				detailInfo.setTotalPrice(Arith.div(order.getTotalPrice(), 1000, 2));
				detailInfo.setCash(Arith.div(order.getRealPrice(), 1000, 2));
				detailInfo.setFftPoints(Arith.div(order.getFftPoints(), 1000, 2));
				detailInfo.setBankPoints(Arith.div(order.getBankPoints(), 1000, 2));
				detailInfo.setPointRate(order.getPointRate());
				detailInfo.setRedPacketDiscount(Checker.isNotEmpty(order.getCutMoney()) ? Arith.div(order.getCutMoney(), 1000, 2) : null);
				detailInfo.setDiscount(Checker.isNotEmpty(order.getDiscountMoney()) ? Arith.div(order.getDiscountMoney(), 1000, 2) : null);
				
				List<Settlement> settlements = getSettlementInfos(clientId, orderId);
				if(Checker.isNotEmpty(settlements)){
					long settleTime = settlements.get(0).getCreateTime();
					String settleStatus = settlements.get(0).getSettleState();
					detailInfo.setSettleTime(settleTime);
					detailInfo.setSettleStatus(settleStatus);
					for(int i = 0; i < settlements.size(); i++){
						if(settleStatus.equals(settlements.get(i).getSettleState())){
							List<PaymentMongo> settlePayments = getPaymentInfos(clientId, orderId, settlements.get(i).getPaymentId(), PaymentReason.settle.getCode());
							if(Checker.isNotEmpty(settlePayments)){
								StringBuilder settleBillNosStr = new StringBuilder();
								for(int j = 0; j < settlePayments.size(); j++){
									p = settlePayments.get(j);
									if(Checker.isNotEmpty(p.getBillNo())){
										settleBillNosStr.append(p.getBillNo()).append("，");
									}else{
										settleBillNosStr.append(p.getRemark()).append("，");
									}
								}
								String settleBillNos = settleBillNosStr.toString();
								detailInfo.setSettleBillNos(settleBillNos.length() > 0 ? settleBillNos.substring(0, settleBillNos.length()-1) : settleBillNos);
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			LogCvt.error("面对面惠付订单详情查询异常", e);
			throw e;
		}
		return detailInfo;
	}

	
	private List<PaymentMongo> getPaymentInfos(String clientId, String orderId, String paymentId, String paymentReason){
		DBObject pdbObject = new BasicDBObject();
		if(Checker.isNotEmpty(orderId)){
			pdbObject.put("order_id", orderId);
		}
		if(Checker.isNotEmpty(clientId)){
			pdbObject.put("client_id", clientId);
		}
		if(Checker.isNotEmpty(paymentId)){
			pdbObject.put("payment_id", paymentId);
		}
		if(Checker.isNotEmpty(paymentReason)){
			pdbObject.put("payment_reason", paymentReason);
		}
		DBObject sort = new BasicDBObject();
		sort.put("create_time", -1);
		List<PaymentMongo> payments = (List<PaymentMongo>) mongo.findAll(pdbObject, sort, MongoTableName.CB_PAYMENT, PaymentMongo.class);
		return payments;
	}
	
	private List<Settlement> getSettlementInfos(String clientId, String orderId){
		DBObject query = new BasicDBObject();
		query.put("order_id", orderId);
		query.put("client_id", clientId);
		DBObject sort = new BasicDBObject();
		sort.put("create_time", -1);
		List<Settlement> settlements = (List<Settlement>) mongo.findAll(query, sort, MongoTableName.CB_SETTLEMENT, Settlement.class);
		return settlements;
	}
	
	
	private OutletDetail getOutletInfoByOutletId(String outletId){
		if(Checker.isEmpty(outletId)){
			return null;
		}
		return mongo.findOneById(outletId, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
	}
	
}
