package com.froad.support.impl;

import java.text.SimpleDateFormat;
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

import org.apache.commons.lang.BooleanUtils;
import org.apache.ibatis.session.SqlSession;
import org.eclipse.jetty.io.NetworkTrafficListener.Empty;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.db.mongo.impl.CursorHandleImpl;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.OrderMapper;
import com.froad.db.redis.RedisCommon;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.DeliveryType;
import com.froad.enums.FieldMapping;
import com.froad.enums.GivePointState;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.OrderState;
import com.froad.enums.OrderStatus;
import com.froad.enums.OrderType;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.ProductType;
import com.froad.enums.ResultCode;
import com.froad.enums.SettlementStatus;
import com.froad.enums.ShippingStatus;
import com.froad.enums.SubOrderRefundState;
import com.froad.enums.SubOrderType;
import com.froad.exceptions.FroadBusinessException;
import com.froad.log.vo.OrderLog;
import com.froad.logback.LogCvt;
import com.froad.logic.impl.order.OrderLogger;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.DeliverInfo;
import com.froad.po.MerchantOutletFavorite;
import com.froad.po.OrderQueryByBankCondition;
import com.froad.po.OrderQueryByBossCondition;
import com.froad.po.OrderQueryByMerchantPhoneCondition;
import com.froad.po.OrderQueryCondition;
import com.froad.po.OrderQueryMerchantManageCondition;
import com.froad.po.PresellShip;
import com.froad.po.Product;
import com.froad.po.ProductMonthCount;
import com.froad.po.ProductSeckill;
import com.froad.po.QueryBoutiqueOrderByBankDto;
import com.froad.po.RecvInfo;
import com.froad.po.Ticket;
import com.froad.po.base.PageEntity;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.ShippingOrderMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.support.OrderSupport;
import com.froad.thrift.vo.order.OrderSummaryVo;
import com.froad.thrift.vo.order.ProductSummaryVo;
import com.froad.thrift.vo.order.QrcodeOrderDetailVo;
import com.froad.util.Arith;
import com.froad.util.DateUtil;
import com.froad.util.EmptyChecker;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.froad.util.PropertiesUtil;
import com.froad.util.RedisKeyUtil;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;


public class OrderSupportImpl implements OrderSupport {
	
	/**
	 *  MongoDb操作类
	 */
	private MongoManager mongo = new MongoManager();
	
	private RedisManager redis = new RedisManager();
	
	private MonitorService monitorService = new MonitorManager();
	
	@Override
	public ProductDetail queryProductDetail(String productId) {
		return mongo.findOneById(productId, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
	}
	
	@Override
	public boolean addOrder(OrderMongo orderMongo) {
		return mongo.add(orderMongo, MongoTableName.CB_ORDER) != -1;
	}
	
	@Override
	public OrderMongo getOrderByOrderId(String clientId, String orderId) {
		DBObject query = new BasicDBObject();
		query.put("client_id", clientId);
		query.put("_id", orderId);
		return mongo.findOne(query, MongoTableName.CB_ORDER, OrderMongo.class);
	}
	
	@Override
	public List<OrderMongo> getOrderListByOrderIdList(String clientId,List<String> orderIdList) {
		long st = System.currentTimeMillis();
		DBObject query = new BasicDBObject();
//		query.put("client_id", clientId);
		query.put("_id", new BasicDBObject(QueryOperators.IN,orderIdList));
		LogCvt.info("根据"+orderIdList.size()+"个大订单号查询大订单集合，耗时："+(System.currentTimeMillis() - st));
		return (List<OrderMongo>) mongo.findAll(query, MongoTableName.CB_ORDER, OrderMongo.class);
	}
	
	@Override
	public boolean addSubOrderList(List<SubOrderMongo> subOrderMongoList) {
		for(SubOrderMongo subOrderMongo : subOrderMongoList){
			int result = mongo.add(subOrderMongo, MongoTableName.CB_SUBORDER_PRODUCT);
			if(result == -1) return false;
		}
		return true;
	}
	
	@Override
	public boolean addOrderRedis(OrderMongo order){
		//key为cbbank:time_order      value为client_id:order_id
//		LogCvt.info("redis 订单创建时间缓存");
		long st = System.currentTimeMillis();
		//直接支付成功的无需缓存
		if(StringUtils.equals(order.getOrderStatus(), OrderStatus.paysuccess.getCode())){
			OrderLogger.info("订单模块", "订单创建-更新订单创建时间缓存", "订单直接支付成功，无需增加创建时间缓存", new Object[]{"orderId",order.getOrderId()});
			return true;
		}
		String timeOrderKey = RedisKeyUtil.cbbank_time_order_key();
		String timeOrderVlue = RedisKeyUtil.cbbank_time_order_value(order.getClientId(), order.getOrderId());
		Set<String> set = new HashSet<String>();
		set.add(timeOrderVlue);
		redis.putSet(timeOrderKey, set);
		LogCvt.info("[订单创建]- addOrderRedis 订单创建时间缓存，耗时:"+(System.currentTimeMillis()-st));
		
//		boolean saddFlag = RedisManager.getJedis(RedisManager.read).sismember(timeOrderKey, order.getClientId()+":"+order.getOrderId());
//		LogCvt.info("redis 订单的创建时间创建结果为:"+saddFlag);
		
		return true;
	}
	
	@Override
	public boolean processPresellOutletTokenCountRedis(String clientId, String orgCode,String productId,int count){
		//（预售自提）订单创建时[加]，订单取消、订单关闭、退款处理时[减]
		LogCvt.info("redis 更新门店商品已提货数量，Key："+RedisKeyUtil.cbbank_product_presell_token_org_code_product_id(clientId,orgCode,productId));
		String key = RedisKeyUtil.cbbank_product_presell_token_org_code_product_id(clientId,orgCode,productId);
		long result = 0;
		if(count >= 0){
			result = redis.incrBy(key, Long.valueOf(count));
		}else{
			result = redis.decrBy(key, Long.valueOf(count));
		}
		return result != -1;
	}
	
	@Override
	public boolean addMemberBuyHistory(List<SubOrderMongo> list){
		//String clientId,long memberCode,String productId
//		LogCvt.info("redis 会员商品历史购买数");
		if(EmptyChecker.isNotEmpty(list)){
			for(SubOrderMongo subOrderMongo : list){
				if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
					for(ProductMongo productMongo : subOrderMongo.getProducts()){
						String userBuyKey = RedisKeyUtil.cbbank_product_limit_client_id_member_code_product_id(subOrderMongo.getClientId(),subOrderMongo.getMemberCode(),productMongo.getProductId());
						redis.hincrBy(userBuyKey, "count", Long.valueOf(productMongo.getQuantity()));
						redis.hincrBy(userBuyKey, "vip_count", Long.valueOf(productMongo.getVipQuantity()));
						
						//预售自提商品，更新已提货数量
						if(StringUtils.equals(ProductType.presell.getCode(),(productMongo.getType())) && StringUtils.equals(DeliveryType.take.getCode(),productMongo.getDeliveryOption())){
							processPresellOutletTokenCountRedis(subOrderMongo.getClientId(),productMongo.getOrgCode(),productMongo.getProductId(),productMongo.getQuantity()+productMongo.getVipQuantity());
						}
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean updateMemberBuyHistoryForSeckill(List<SubOrderMongo> list,boolean isIncr){
		LogCvt.info("redis [秒杀订单创建]-更新redis会员商品历史购买数");
		if(EmptyChecker.isNotEmpty(list)){
			for(SubOrderMongo subOrderMongo : list){
				if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
					for(ProductMongo productMongo : subOrderMongo.getProducts()){
						String productKey = RedisKeyUtil.cbbank_seckill_product_client_id_product_id(subOrderMongo.getClientId(),productMongo.getProductId());
						String endTimeStr = redis.hget(productKey, "end_time");
						if(EmptyChecker.isEmpty(endTimeStr)){
							LogCvt.error("[秒杀订单创建]-用户购买记录缓存更新-商品秒杀结束时间为空，Key：" + RedisKeyUtil.cbbank_seckill_product_client_id_product_id(subOrderMongo.getClientId(),productMongo.getProductId()));
							continue;
						}
						Long endTime = Long.valueOf(endTimeStr);
						SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
						String endDateYYMMdd = String.valueOf(df.format(new Date(endTime)));
						
						String userBuyKey = RedisKeyUtil.cbbank_seckill_memcnt_member_code_product_id_end_date(String.valueOf(subOrderMongo.getMemberCode()),productMongo.getProductId(),endDateYYMMdd);
						int q = productMongo.getQuantity() == null ? 0 : productMongo.getQuantity();
						int v = productMongo.getVipQuantity() == null ? 0 : productMongo.getVipQuantity();
						Integer count = q + v;
						LogCvt.info("[秒杀订单创建]-更新用户购买记录，用户初始购买数：" + redis.getString(userBuyKey));
						if(isIncr){
							LogCvt.info("[秒杀订单创建]-更新用户购买记录，操作数：" + count);
							redis.incrBy(userBuyKey, Long.parseLong(count.toString()));
						}else{
							LogCvt.info("[秒杀订单创建]-更新用户购买记录，操作数：" + (-count));
							redis.decrBy(userBuyKey, Long.parseLong(count.toString()));
						}
						LogCvt.info("[秒杀订单创建]-更新用户购买记录，更新后用户购买数：" + redis.getString(userBuyKey));
						
						//预售自提商品，更新已提货数量
						/*if(StringUtils.equals(ProductType.presell.getCode(),(productMongo.getType())) && StringUtils.equals(DeliveryType.take.getCode(),productMongo.getDeliveryOption())){
							processPresellOutletTokenCountRedis(subOrderMongo.getClientId(),productMongo.getOrgCode(),productMongo.getProductId(),productMongo.getQuantity()+productMongo.getVipQuantity());
						}*/
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean subtractMemberBuyHistory(String clientId,long memberCode,String productId,Long count,Long vipCount){
		//String clientId,long memberCode,String productId
		String userBuyKey = RedisKeyUtil.cbbank_product_limit_client_id_member_code_product_id(clientId,memberCode,productId);
		LogCvt.info("redis 减去会员商品历史购买数，Key：" + userBuyKey);
		LogCvt.info("参数:{clientId:"+clientId+",memberCode:"+memberCode+",productId:"+productId+",count:"+count+",vipCount:"+vipCount+"}");
		String countStr = redis.hget(userBuyKey, "count");
		LogCvt.info("会员已购买普通数量："+countStr);
		if(EmptyChecker.isNotEmpty(countStr) && Long.valueOf(countStr)>0 && EmptyChecker.isNotEmpty(count) && Long.valueOf(count)>0){
			redis.hincrBy(userBuyKey, "count", -count);
			LogCvt.info("会员已购买普通数量已减，操作数："+(-count));
		}
		String vipCountStr = redis.hget(userBuyKey, "vip_count");
		LogCvt.info("会员已购买VIP数量："+vipCountStr);
		if(EmptyChecker.isNotEmpty(vipCountStr) && Long.valueOf(vipCountStr)>0 && EmptyChecker.isNotEmpty(vipCount) && Long.valueOf(vipCount)>0){
			redis.hincrBy(userBuyKey, "vip_count", -vipCount);
			LogCvt.info("会员已购买VIP数量已减，操作数："+(-vipCount));
		}
		return true;
	}
	
	@Override
	public boolean subtractMemberBuyHistoryForSeckill(String clientId,long memberCode,String productId,Long count,Long vipCount){
		//String clientId,long memberCode,String productId
		String productKey = RedisKeyUtil.cbbank_seckill_product_client_id_product_id(clientId,productId);
		LogCvt.info("[秒杀模块]-redis 减去会员商品历史购买数，Key：" + productKey);
		LogCvt.info("参数:{clientId:"+clientId+",memberCode:"+memberCode+",productId:"+productId+",count:"+count+",vipCount:"+vipCount+"}");
		String endTimeStr = redis.hget(productKey, "end_time");
		if(EmptyChecker.isEmpty(endTimeStr)){
			LogCvt.error("[秒杀订单创建]-用户购买记录缓存更新-商品秒杀结束时间为空，Key：" + RedisKeyUtil.cbbank_seckill_product_client_id_product_id(clientId,productId));
			return false;
		}
		Long endTime = Long.valueOf(endTimeStr);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String endDateYYMMdd = String.valueOf(df.format(new Date(endTime)));
		
		String userBuyKey = RedisKeyUtil.cbbank_seckill_memcnt_member_code_product_id_end_date(String.valueOf(memberCode),productId,endDateYYMMdd);
		Long number = count + vipCount;
		LogCvt.info("[秒杀订单创建]-更新用户购买记录，用户初始购买数：" + redis.getString(userBuyKey));
		LogCvt.info("[秒杀订单创建]-更新用户购买记录，操作数：" + (-number));
		if(Integer.valueOf(redis.getString(userBuyKey)) < 0){
			LogCvt.info("会员已购买数量为负，清零");
			redis.putString(userBuyKey, "0");
		}
		redis.decrBy(userBuyKey, Long.valueOf(number));
		LogCvt.info("[秒杀订单创建]-更新用户购买记录，更新后用户购买数：" + redis.getString(userBuyKey));
		
		return true;
	}
	
	@Override
	public void addSeckillOrderRedis(String reqId,OrderMongo orderMongo){
		//key为cbbank:seckill_res:reqId      field为order_status
		LogCvt.info("redis 更新秒杀订单缓存，请求参数：reqId:"+reqId+" | orderId:"+orderMongo.getOrderId());
		String reqKey = RedisKeyUtil.cbbank_seckill_res_req_id(reqId);
		Map<String,String> map = new HashMap<String,String>();
		map.put("order_id", orderMongo.getOrderId());
		/*map.put("order_status", orderMongo.getOrderStatus());
		if(StringUtils.equals(orderMongo.getOrderStatus(), OrderStatus.paysuccess.getCode())){
			LogCvt.info("秒杀支付成功！");
			map.put("result_flag", "1");
		}
		if(StringUtils.equals(orderMongo.getOrderStatus(), OrderStatus.payfailed.getCode())){
			LogCvt.info("秒杀支付失败！");
			map.put("result_flag", "2");
		}*/
		redis.putMap(reqKey, map);
	}
	
	@Override
	public void updateSeckillOrderRedis(String reqId, String resultFlag) {
		LogCvt.info("redis 更新受理缓存，请求参数：{reqId:" + reqId + ",resultFlag:" + resultFlag + "}");
		String reqKey = RedisKeyUtil.cbbank_seckill_res_req_id(reqId);
		Map<String,String> map = new HashMap<String,String>();
		map.put("result_flag", resultFlag);
		redis.putMap(reqKey, map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderSummaryVo> getOrderSummary(OrderQueryCondition orderQueryCondition) {
		BasicDBObject where = new BasicDBObject();
		where.put("client_id", orderQueryCondition.getClientId());
		where.put("member_code", orderQueryCondition.getMemberCode());
		where.put("is_qrcode", BooleanUtils.toInteger(false));
		where.put("is_point", BooleanUtils.toInteger(false));
		if(EmptyChecker.isNotEmpty(orderQueryCondition.getOrderStatus())){
			where.put("order_status", orderQueryCondition.getOrderStatus());
		}
		if(EmptyChecker.isNotEmpty(orderQueryCondition.getStartTime()) &&  EmptyChecker.isNotEmpty(orderQueryCondition.getEndTime()) &&  orderQueryCondition.getStartTime() > 0 && orderQueryCondition.getEndTime() > 0){
			BasicDBList values = new BasicDBList();
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,orderQueryCondition.getStartTime())));
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,orderQueryCondition.getEndTime())));
			where.put(QueryOperators.AND,values);
		}else if(EmptyChecker.isNotEmpty(orderQueryCondition.getStartTime())){
			where.put("create_time",new BasicDBObject(QueryOperators.GTE,orderQueryCondition.getStartTime()));
		}else if(EmptyChecker.isNotEmpty(orderQueryCondition.getEndTime())){
			where.put("create_time",new BasicDBObject(QueryOperators.LTE,orderQueryCondition.getEndTime()));
		}
		
		List<OrderMongo> orderList = (List<OrderMongo>) mongo.findAll(where, MongoTableName.CB_ORDER, OrderMongo.class);
		List<OrderSummaryVo> list = new ArrayList<OrderSummaryVo>();
		if(EmptyChecker.isNotEmpty(orderList)){
			for(OrderMongo order : orderList){
				OrderSummaryVo orderSummaryVo = new OrderSummaryVo();
				orderSummaryVo.setOrderId(order.getOrderId());
				orderSummaryVo.setOrderStatus(order.getOrderStatus());
				orderSummaryVo.setTotalPrice(Arith.div(order.getTotalPrice(), 1000));
				List<SubOrderMongo> SubOrderMongoList =  this.getSubOrderListByOrderId(order.getClientId(), order.getOrderId());
				List<ProductSummaryVo> productSummaryVoList = new ArrayList<ProductSummaryVo>();
				if(EmptyChecker.isNotEmpty(SubOrderMongoList)){
					for(SubOrderMongo subOrder : SubOrderMongoList){
						if(EmptyChecker.isNotEmpty(subOrder.getProducts())){
							for(ProductMongo product : subOrder.getProducts()){
								ProductSummaryVo productSummary = new ProductSummaryVo();
								productSummary.setProductId(product.getProductId());
								productSummary.setProductImage(product.getProductImage());
								productSummary.setProductName(product.getProductName());
								productSummaryVoList.add(productSummary);
							}
						}
					}
				}
				orderSummaryVo.setProductSummaryVoList(productSummaryVoList);
				list.add(orderSummaryVo);
			}
		}
		LogCvt.info("订单概要查询结果：" + JSonUtil.toJSonString(list));
		return list;
	}
	
	@Override
	public MongoPage getOrderByConditioinOfPage(PageEntity<OrderQueryCondition> pageCondition) {
		long st = System.currentTimeMillis();
		OrderQueryCondition orderQueryCondition = pageCondition.getCondition();
		//查询条件
		BasicDBObject where = new BasicDBObject();
		where.put("client_id", orderQueryCondition.getClientId());
		where.put("member_code", orderQueryCondition.getMemberCode());
		where.put("is_qrcode", BooleanUtils.toInteger(false));
		where.put("is_point", BooleanUtils.toInteger(false));
		if(EmptyChecker.isNotEmpty(orderQueryCondition.getOrderStatus())){
			where.put("order_status", orderQueryCondition.getOrderStatus());
		}
		
		if(EmptyChecker.isNotEmpty(orderQueryCondition.getStartTime()) && EmptyChecker.isNotEmpty(orderQueryCondition.getEndTime()) && orderQueryCondition.getStartTime() > 0 && orderQueryCondition.getEndTime() > 0){
			BasicDBList values = new BasicDBList();
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,orderQueryCondition.getStartTime())));
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,orderQueryCondition.getEndTime())));
			where.put(QueryOperators.AND,values);
		}else if(EmptyChecker.isNotEmpty(orderQueryCondition.getStartTime()) && orderQueryCondition.getStartTime() > 0){
			where.put("create_time",new BasicDBObject(QueryOperators.GTE,orderQueryCondition.getStartTime()));
		}else if(EmptyChecker.isNotEmpty(orderQueryCondition.getEndTime()) && orderQueryCondition.getEndTime() > 0){
			where.put("create_time",new BasicDBObject(QueryOperators.LTE,orderQueryCondition.getEndTime()));
		}
		
		MongoPage pageParam = new MongoPage();
		//排序
		/*if(EmptyChecker.isNotEmpty(pageCondition.getOrderByColumn())){
			pageParam.setSort(new Sort(pageCondition.getOrderByColumn(),OrderBy.DESC));
		}*/
		//当前页数
		pageParam.setPageNumber(pageCondition.getPageNumber());
		pageParam.setPageSize(pageCondition.getPageSize());
		
		convert(pageParam, pageCondition);
		
		/*pageParam.setSort(new Sort("create_time", OrderBy.DESC));*/
		MongoPage page = mongo.findByPageWithRedis(pageParam, where, MongoTableName.CB_ORDER, OrderMongo.class);
		LogCvt.info("大订单概要分页查询结果（getOrderByConditioinOfPage）：" + JSonUtil.toJSonString(page)+"，耗时："+(System.currentTimeMillis()-st));
		return page;
	}

	@Override
	public boolean deleteOrder(String orderId) {
		// 查询条件
		DBObject where = new BasicDBObject();
		where.put("_id", orderId);
		// 更新对象
		DBObject value = new BasicDBObject();
		value.put("order_status", OrderStatus.closed.getCode());
		int result = mongo.update(value, where, MongoTableName.CB_ORDER,"$set");
		return result != -1;
	}
	
	@Override
	public boolean deleteSubOrder(String orderId) {
		// 查询条件
		DBObject where = new BasicDBObject();
		where.put("order_id", orderId);
		// 更新对象
		DBObject value = new BasicDBObject();
		value.put("order_status", OrderStatus.closed.getCode());
		int result = mongo.update(value, where, MongoTableName.CB_SUBORDER_PRODUCT,"$set",false,true);
		return result != -1;
	}

	@Override
	public SubOrderMongo getSubOrderBySubOrderId(String clientId, String subOrderId) {
		DBObject query = new BasicDBObject();
		query.put("client_id", clientId);
		query.put("sub_order_id", subOrderId);
		LogCvt.info("查询子订单数据，条件：" + JSonUtil.toJSonString(query));
		return mongo.findOne(query, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	}
	@Override
	public SubOrderMongo getSubOrderBySubOrderId(String subOrderId) {
	    return this.getSubOrderBySubOrderId("anhui", subOrderId);
	}

	/**
	 *  根据商品ID，订单ID查询子订单商品信息
	  * @Title: getProductMongo
	  * @Description: 根据商品ID，订单ID查询子订单商品信息
	  * @author: share 2015年3月27日
	  * @modify: share 2015年3月27日
	  * @param @param orderId
	  * @param @param subOrderId
	  * @param @param productId
	  * @param @return
	  * @throws
	  * @see com.froad.support.OrderSupport#getProductMongo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ProductMongo getProductMongo(String orderId, String subOrderId,String productId) {
		List<DBObject> pipeLine = new ArrayList<DBObject>();
		DBObject query = new BasicDBObject();
		query.put("order_id", orderId);
		query.put("sub_order_id", subOrderId);
        pipeLine.add(new BasicDBObject("$match", query));
        pipeLine.add(new BasicDBObject("$unwind", "$products"));
        
        DBObject query2 = new BasicDBObject();
		query2.put("products.product_id", productId);
        
        pipeLine.add(new BasicDBObject("$match", query2));
        Cursor cursor = mongo.getReadDBCollection(MongoTableName.CB_SUBORDER_PRODUCT).aggregate(pipeLine, AggregationOptions.builder().build());
        DBObject dbObj = new CursorHandleImpl().handle(cursor);
        if(dbObj != null){
        	 String json = JSonUtil.toJSonString(((DBObject)dbObj.get("products")));
             return JSonUtil.toObject(json, ProductMongo.class);
        }
       return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubOrderMongo> getSubOrderListByOrderId(String clientId, String orderId) {
		DBObject query = new BasicDBObject();
		query.put("client_id", clientId);
		query.put("order_id", orderId);
		List<SubOrderMongo> subOrderList = (List<SubOrderMongo>) mongo.findAll(query, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		return subOrderList;
	}
	
	@Override
	public List<SubOrderMongo> getSubOrderListByOrderId(String orderId) {
	    return getSubOrderListByOrderId("anhui", orderId);
	}
	
	
	/**
	 *  按订单ID及字典的状态查询
	  * @Title: getSubOrderListByOrderId
	  * @Description: 按订单ID及字典的状态查询
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param orderId
	  * @param @return
	  * @throws
	  * @see com.froad.support.OrderSupport#getSubOrderListByOrderId(java.lang.String)
	 */
	@Override
	public List<SubOrderMongo> getSubOrderListByOrderId(String orderId,String[] types) {
		DBObject query = new BasicDBObject();
		query.put("order_id", orderId);
		if(types != null && types.length > 0){
			query.put("type", new BasicDBObject(QueryOperators.IN,types));
		}
		List<SubOrderMongo> subOrderList = (List<SubOrderMongo>) mongo.findAll(query, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		return subOrderList;
	}
	
	
	
	/**
	 *  查询子订单集合
	  * @Title: getSubOrderListByOrderId
	  * @Description: 按订单ID及字典的状态查询
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param orderId
	  * @param @return
	  * @throws
	  * @see com.froad.support.OrderSupport#getSubOrderListByOrderId(java.lang.String)
	 */
	@Override
	public List<SubOrderMongo> getSubOrderListByCondition(SubOrderMongo subOrderMongo) {
		List<SubOrderMongo> subOrderList = new ArrayList<SubOrderMongo>();
		if(EmptyChecker.isNotEmpty(subOrderMongo)){
			return subOrderList;
		}
		DBObject query = new BasicDBObject();
		if(EmptyChecker.isNotEmpty(subOrderMongo.getMerchantId())){
			query.put("merchant_id", subOrderMongo.getMerchantId());
		}
		if(EmptyChecker.isNotEmpty(subOrderMongo.getType())){
			query.put("type", subOrderMongo.getType());
		}
		subOrderList = (List<SubOrderMongo>) mongo.findAll(query, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		return subOrderList;
	}

	/**
	 *  获取用户订单对应的商品的购买数量
	 *  {_id:用户会员号}
	 *  {vip_quantity:VIP身份购买数量}
	 *  {quantity:购买数量}
	  * @Title: countMemberOrder
	  * @Description: 获取用户订单对应的商品的购买数量
	  * @author: share 2015年3月28日
	  * @modify: share 2015年3月28日
	  * @param @param memberCode
	  * @param @param productId
	  * @param @return
	  * @throws
	  * @see com.froad.support.OrderSupport#countMemberOrder(long, java.lang.String)
	 */
	@Override
	public Map<String, Object> countMemberOrder(long memberCode,String productId) {
		List<DBObject> pipeLine = new ArrayList<DBObject>();
		DBObject query = new BasicDBObject();
		query.put("member_code", memberCode);
        pipeLine.add(new BasicDBObject("$match", query));
        pipeLine.add(new BasicDBObject("$unwind", "$products"));
        
        DBObject query2 = new BasicDBObject();
		query2.put("products.product_id", productId);
        pipeLine.add(new BasicDBObject("$match", query2));
        // group 统计
        DBObject groupQuery = new BasicDBObject();
        groupQuery.put("_id", "$member_code");
        // 会员购买总数
        groupQuery.put("vip_quantity", new BasicDBObject("$sum","$products.vip_quantity"));
        // 购买总数
        groupQuery.put("quantity", new BasicDBObject("$sum","$products.quantity"));
        
        
        Cursor cursor = mongo.getReadDBCollection(MongoTableName.CB_SUBORDER_PRODUCT).aggregate(pipeLine, AggregationOptions.builder().build());
        DBObject dbObj = new CursorHandleImpl().handle(cursor);
        if(dbObj != null){
             return JSonUtil.toObject(JSonUtil.toJSonString(dbObj), Map.class);
        }
		return null;
	}
	
	@Override
	public Map<String,String> getProductDeliveryMoney(String clientId, String merchantId,String productId) {
		String deliveryMoneyKey = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(clientId, merchantId, productId);
		Map<String,String> deliveryMoneyMap = redis.getMap(deliveryMoneyKey);
		return deliveryMoneyMap;
	}

	@Override
	public MongoPage getQrcodeOrderSummaryList(PageEntity<OrderQueryCondition> pageCondition) {
		OrderQueryCondition orderQueryCondition = pageCondition.getCondition();
		BasicDBObject where = new BasicDBObject();
		where.put("client_id", orderQueryCondition.getClientId());
		where.put("member_code", orderQueryCondition.getMemberCode());
		where.put("is_qrcode", BooleanUtils.toInteger(true));
		if(EmptyChecker.isNotEmpty(orderQueryCondition.getOrderStatus())){
			where.put("order_status", orderQueryCondition.getOrderStatus());
		}else{
			Object [] obj = {new BasicDBObject("order_status", OrderStatus.payfailed.getCode()),
							 new BasicDBObject("order_status", OrderStatus.create.getCode()),
							 new BasicDBObject("order_status", OrderStatus.paying.getCode()),
							 new BasicDBObject("order_status", OrderStatus.sysclosed.getCode()),
							 new BasicDBObject("order_status", OrderStatus.closed.getCode()),
			                 new BasicDBObject("order_status", OrderStatus.paysuccess.getCode())};
	        where.put(QueryOperators.OR, obj);
		}
		if(EmptyChecker.isNotEmpty(orderQueryCondition.getStartTime()) && EmptyChecker.isNotEmpty(orderQueryCondition.getEndTime()) &&  orderQueryCondition.getStartTime() > 0 && orderQueryCondition.getEndTime() > 0){
			BasicDBList values = new BasicDBList();
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,orderQueryCondition.getStartTime())));
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,orderQueryCondition.getEndTime())));
			where.put(QueryOperators.AND,values);
		}else if(EmptyChecker.isNotEmpty(orderQueryCondition.getStartTime()) && orderQueryCondition.getStartTime() > 0){
			where.put("create_time",new BasicDBObject(QueryOperators.GTE,orderQueryCondition.getStartTime()));
		}else if(EmptyChecker.isNotEmpty(orderQueryCondition.getEndTime()) && orderQueryCondition.getEndTime() > 0){
			where.put("create_time",new BasicDBObject(QueryOperators.LTE,orderQueryCondition.getEndTime()));
		}
		
		MongoPage pageParam = new MongoPage();
		//排序
		/*if(EmptyChecker.isNotEmpty(pageCondition.getOrderByColumn())){
			pageParam.setSort(new Sort(pageCondition.getOrderByColumn(),OrderBy.DESC));
		}*/
		//当前页数
		pageParam.setPageNumber(pageCondition.getPageNumber());
		pageParam.setPageSize(pageCondition.getPageSize());
		
		convert(pageParam, pageCondition);
		
		/*pageParam.setSort(new Sort("create_time", OrderBy.DESC));*/
		MongoPage page = mongo.findByPageWithRedis(pageParam, where, MongoTableName.CB_ORDER, OrderMongo.class);
		LogCvt.info("面对面订单概要查询结果：" + JSON.toJSONString(page,true));
		return page;
	}
	
	@Override
	public QrcodeOrderDetailVo getQrcodeOrderDetail(OrderQueryCondition condition,String paymentChannel){
		BasicDBObject where = new BasicDBObject();
        where.put("client_id", condition.getClientId());
        where.put("member_code", condition.getMemberCode());
        where.put("_id", condition.getOrderId());
        OrderMongo orderMongo = mongo.findOne(where, MongoTableName.CB_ORDER, OrderMongo.class);
        QrcodeOrderDetailVo qrcodeOrderDetailVo = new QrcodeOrderDetailVo();
        if(EmptyChecker.isNotEmpty(orderMongo)){
        	qrcodeOrderDetailVo.setOrderId(orderMongo.getOrderId());
        	qrcodeOrderDetailVo.setOrderStatus(orderMongo.getOrderStatus());
        	qrcodeOrderDetailVo.setRealPrice(Arith.div(orderMongo.getRealPrice(), 1000));
        	qrcodeOrderDetailVo.setCreateTime(orderMongo.getCreateTime());
        	qrcodeOrderDetailVo.setPaymentMethod(orderMongo.getPaymentMethod());
        	qrcodeOrderDetailVo.setMerchantId(orderMongo.getMerchantId());
        	qrcodeOrderDetailVo.setMerchantName(orderMongo.getMerchantName());
        	qrcodeOrderDetailVo.setFftPoints(orderMongo.getFftPoints()== null ? 0 : Arith.div(orderMongo.getFftPoints(),1000));
        	qrcodeOrderDetailVo.setBankPoints(orderMongo.getBankPoints() == null ? 0 : Arith.div(orderMongo.getBankPoints(),1000));
        	qrcodeOrderDetailVo.setPaymentChannel(paymentChannel);
        	qrcodeOrderDetailVo.setTotalPrice(Arith.div(orderMongo.getTotalPrice(), 1000));
        	qrcodeOrderDetailVo.setRemark(orderMongo.getRemark());
        	qrcodeOrderDetailVo.setPaymentTime(orderMongo.getPaymentTime() == null ? 0 : orderMongo.getPaymentTime());
        	qrcodeOrderDetailVo.setOutletId(orderMongo.getOutletId() == null ? "" : orderMongo.getOutletId());
        	
        	qrcodeOrderDetailVo.setOutletName(orderMongo.getOutletName() == null ? "" : orderMongo.getOutletName());
        	if(EmptyChecker.isNotEmpty(orderMongo.getIsPrefPay()) && orderMongo.getIsPrefPay() == 1){
        		qrcodeOrderDetailVo.setConsumeMoney(EmptyChecker.isEmpty(orderMongo.getConsumeMoney())? 0 : Arith.div(orderMongo.getConsumeMoney(), 1000));
            }else{
            	Integer cutMoney = orderMongo.getCutMoney() == null ? 0 : orderMongo.getCutMoney();
            	qrcodeOrderDetailVo.setConsumeMoney(Arith.div(orderMongo.getTotalPrice()+cutMoney, 1000));
            }
        	qrcodeOrderDetailVo.setDiscountMoney(orderMongo.getDiscountMoney() == null ? 0 : Arith.div(orderMongo.getDiscountMoney(), 1000));
        	qrcodeOrderDetailVo.setOutletImg(RedisCommon.getOutletImg(orderMongo.getClientId(),orderMongo.getMerchantId(),orderMongo.getOutletId()));
        	qrcodeOrderDetailVo.setCutMoney(orderMongo.getCutMoney() == null ? 0 : Arith.div(orderMongo.getCutMoney(), 1000));
			double pointMoney = 0;
			if(EmptyChecker.isNotEmpty(orderMongo.getFftPoints()) && orderMongo.getFftPoints()>0){
				double pointRate = 1;
				if(EmptyChecker.isNotEmpty(orderMongo.getPointRate()) && Integer.valueOf(orderMongo.getPointRate()) > 0){
					pointRate = Integer.valueOf(orderMongo.getPointRate());
				}
				pointMoney = Arith.divFloor(Arith.div(orderMongo.getFftPoints(),1000), pointRate);
			}
			if(EmptyChecker.isNotEmpty(orderMongo.getBankPoints()) && orderMongo.getBankPoints()>0){
				double pointRate = 1;
				if(EmptyChecker.isNotEmpty(orderMongo.getPointRate()) && Integer.valueOf(orderMongo.getPointRate()) > 0){
					pointRate = Integer.valueOf(orderMongo.getPointRate());
				}
				pointMoney = Arith.divFloor(Arith.div(orderMongo.getBankPoints(),1000),pointRate);
			}
			qrcodeOrderDetailVo.setPointMoney(pointMoney);
			qrcodeOrderDetailVo.setPhone(orderMongo.getPhone());
        }
        return qrcodeOrderDetailVo;
	}
	
	@Override
	public OrderMongo getOrderByQrcode(String clientId, String qrcode) {
		BasicDBObject where = new BasicDBObject();
        where.put("client_id", clientId);
        where.put("qrcode", qrcode);
        where.put("is_qrcode", BooleanUtils.toInteger(true));
        Object [] obj = {new BasicDBObject("order_status", OrderStatus.payfailed.getCode()),
				 new BasicDBObject("order_status", OrderStatus.create.getCode()),
				 new BasicDBObject("order_status", OrderStatus.paying.getCode()),
                new BasicDBObject("order_status", OrderStatus.paysuccess.getCode())};
        where.put(QueryOperators.OR, obj);
        OrderMongo orderMongo = mongo.findOne(where, MongoTableName.CB_ORDER, OrderMongo.class);
        return orderMongo;
	}

	@Override
	public boolean addShippingInfo(ShippingOrderMongo shippingOrderMongo) {
	    
	    boolean isUpdate = false;
	    
	    DBObject where = new BasicDBObject();
	    
	    String [] condition = shippingOrderMongo.getId().split("_");
	    
	    where.put("order_id", condition[0]);
	    where.put("sub_order_id", condition[1]);
	    where.put("order_status", OrderStatus.paysuccess.getCode());
	    
	    Object [] rs = {SubOrderRefundState.REFUND_SUCCESS.getCode(), SubOrderRefundState.REFUND_PROCESSING.getCode()};
        where.put("refund_state", new BasicDBObject(QueryOperators.NIN, rs)); // 不为退款完成可以发货
	    
	    SubOrderMongo subOrderInfo = mongo.findOne(where, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	    /**
	     * 名优特惠发货是在子订单中， 其他情况下发货都是根据商品来使用
	     */
	    DBObject update = new BasicDBObject();
	    if(OrderType.special_merchant.getCode().equals(subOrderInfo.getType())) {
	        update.put("delivery_state", ShippingStatus.shipped.getCode());
	    } else {
	        update.put("products.$.delivery_state", ShippingStatus.shipped.getCode());
	    }
	    
	    SubOrderMongo subOrder = mongo.findAndModify(new BasicDBObject("$set", update), where, MongoTableName.CB_SUBORDER_PRODUCT, false, SubOrderMongo.class);
	    if(EmptyChecker.isNotEmpty(subOrder)) {
	        isUpdate = mongo.add(shippingOrderMongo, MongoTableName.CB_SHIPPING) != -1;
	    }
		return isUpdate;
	}
	
	@Override
	public ShippingOrderMongo getShippingInfo(String id) {
	    DBObject where = new BasicDBObject();
	    where.put("_id", id);
	    return mongo.findOne(where, MongoTableName.CB_SHIPPING, ShippingOrderMongo.class);
	}
	
	@Override
	public boolean updateShippingInfo(ShippingOrderMongo shippingOrderMongo) {
	    boolean isUpdate = false;
	    DBObject update = new BasicDBObject();
	    update.put("delivery_corp_id", shippingOrderMongo.getDeliveryCorpId());
	    update.put("delivery_corp_name", shippingOrderMongo.getDeliveryCorpName());
	    update.put("tracking_no", shippingOrderMongo.getTrackingNo());
	    update.put("shipping_time", System.currentTimeMillis());
	    ShippingOrderMongo shipping = mongo.findAndModify(new BasicDBObject("$set", update), new BasicDBObject("_id", shippingOrderMongo.getId()), MongoTableName.CB_SHIPPING, false, ShippingOrderMongo.class);
	    if(EmptyChecker.isNotEmpty(shipping)) {
	        isUpdate = true;
	    }
	    return isUpdate;
	}
	
	@Override
	public boolean updateShippingInfoOfShippedToReceiptStatus(ShippingOrderMongo shippingOrderMongo) {
	    
	    boolean isUpdate = false;
	    
	    DBObject where = new BasicDBObject();
	    where.put("_id", shippingOrderMongo.getId());
	    where.put("shipping_status", ShippingStatus.shipped.getCode());
	    
	    DBObject update = new BasicDBObject();
	    update.put("shipping_status", ShippingStatus.receipt.getCode());
	    update.put("receipt_time", System.currentTimeMillis());

	    LogCvt.info("订单查询表："+MongoTableName.CB_SHIPPING+ ", 修改内容为：" + JSonUtil.toJSonString(update) +" ,查询条件：" + JSonUtil.toJSonString(where));
	    
	    ShippingOrderMongo shippingInfo = mongo.findAndModify(new BasicDBObject("$set", update), where, MongoTableName.CB_SHIPPING, false, ShippingOrderMongo.class);
	    if(EmptyChecker.isNotEmpty(shippingInfo)) {
	        LogCvt.info("修改发货为收货状态成功" + JSonUtil.toJSonString(shippingInfo));
	        isUpdate = true;
	    }
	    return isUpdate;
	}
	
	@Override
	public boolean receiptSubOrder(String orderId,String subOrderId) {
	    DBObject where = new BasicDBObject();
	    where.put("order_id", orderId);
	    where.put("sub_order_id", subOrderId);
	    SubOrderMongo subOrder = mongo.findOne(where, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	    DBObject update = new BasicDBObject();
	    SubOrderMongo sub = null;
	    
	    if(EmptyChecker.isEmpty(subOrder)) {
	        LogCvt.error("查询子订单错误， 大订单号：" + orderId + ", 子订单号：" + subOrderId);
	        return false;
	    }
	    
//	    名优特惠订单发货状态在当前订单层级
	    if(OrderType.special_merchant.getCode().equals(subOrder.getType())) {
	        where.put("delivery_state", ShippingStatus.shipped.getCode());
	        update.put("delivery_state", ShippingStatus.receipt.getCode());
	        
	    } else {
//	        除开名优特惠-订单状态都是在商品里面
	        where.put("products.delivery_state", ShippingStatus.shipped.getCode());
	        update.put("products.$.delivery_state", ShippingStatus.receipt.getCode());
	    }
	    sub = mongo.findAndModify(new BasicDBObject("$set", update), where, MongoTableName.CB_SUBORDER_PRODUCT, false, SubOrderMongo.class);
	    if(EmptyChecker.isNotEmpty(sub)) {
	        return true;
	    }
	    return false;
	}
	
	
	@Override
    public MongoPage queryOrderByFacetfaceConditionOfPage(PageEntity<OrderQueryByMerchantPhoneCondition> pageCondition) {
        
	    OrderQueryByMerchantPhoneCondition condition = pageCondition.getCondition();
        
        BasicDBObject where = new BasicDBObject();
        
        where.put("client_id", condition.getClientId());
        where.put("merchant_id", condition.getMerchantId());
        if(EmptyChecker.isNotEmpty(condition.getOutletId()) && !StringUtils.equals(condition.getOutletId().trim(), "0")) {
            where.put("outlet_id", condition.getOutletId());
        }
        where.put("is_qrcode", BooleanUtils.toInteger(true)); // 面对面订单
        
        // 设置当前页和页大小
        MongoPage page = new MongoPage(pageCondition.getPageNumber(), pageCondition.getPageSize());
        
        convert(page, pageCondition);
        
        //排序
        /*if(EmptyChecker.isNotEmpty(pageCondition.getOrderByColumn())){
            page.setSort(new Sort(pageCondition.getOrderByColumn(),OrderBy.DESC));
        }*/
        
        if(EmptyChecker.isNotEmpty(condition.getStatus())) {
            if(StringUtils.equals(condition.getStatus(), OrderStatus.sysclosed.getCode())){
        		Object [] obj = {
   					 new BasicDBObject("order_status", OrderStatus.sysclosed.getCode()),
   					 new BasicDBObject("order_status", OrderStatus.closed.getCode())};
   		    where.put(QueryOperators.OR, obj);
        	}else{
        		where.put("order_status", condition.getStatus());
        	}
            
        }else{//bug3526 不要展示订单关闭的订单（2015.07.08）
			Object [] obj = {new BasicDBObject("order_status", OrderStatus.payfailed.getCode()),
					 new BasicDBObject("order_status", OrderStatus.create.getCode()),
					 new BasicDBObject("order_status", OrderStatus.paying.getCode()),
					 new BasicDBObject("order_status", OrderStatus.sysclosed.getCode()),
					 new BasicDBObject("order_status", OrderStatus.closed.getCode()),
	                 new BasicDBObject("order_status", OrderStatus.paysuccess.getCode())};
		    where.put(QueryOperators.OR, obj);
		}
        
        LogCvt.info("MongoDb表：" + MongoTableName.CB_ORDER + ",条件："+ JSonUtil.toJSonString(where));
        long st = System.currentTimeMillis();
        mongo.findByPageWithRedis(page, where, MongoTableName.CB_ORDER, OrderMongo.class);
        LogCvt.info("queryOrderByFacetfaceConditionOfPage分页查询耗时：（" + (System.currentTimeMillis() - st) + "）毫秒");
        return page;
    }
	
	@Override
	public MongoPage queryBySubOrderByCondition(PageEntity<OrderQueryByMerchantPhoneCondition> pageCondition) {
	    
	    
	    OrderQueryByMerchantPhoneCondition condition = pageCondition.getCondition();
	    
	    BasicDBObject where = new BasicDBObject();
	    
	    where.put("client_id", condition.getClientId());
	    where.put("merchant_id", condition.getMerchantId());
	    if(EmptyChecker.isNotEmpty(condition.getType())) {
	        where.put("type", condition.getType());
	    }
	    
	    /**
	     * 发货状态
	     */
	    if(EmptyChecker.isNotEmpty(condition.getDeliveryStatus())) {
	        where.put("delivery_state", condition.getDeliveryStatus());
	    }
	    
	    /**
	     * 订单状态
	     */
	    if(EmptyChecker.isNotEmpty(condition.getStatus())) {
	        if(StringUtils.equals(condition.getStatus(), OrderStatus.sysclosed.getCode())){
        		Object [] obj = {
   					 new BasicDBObject("order_status", OrderStatus.sysclosed.getCode()),
   					 new BasicDBObject("order_status", OrderStatus.closed.getCode())};
   		    where.put(QueryOperators.OR, obj);
        	}else{
        		where.put("order_status", condition.getStatus());
        	}
	        
	    }else{//bug3526 不要展示订单关闭的订单（2015.07.08）
			Object [] obj = {new BasicDBObject("order_status", OrderStatus.payfailed.getCode()),
					 new BasicDBObject("order_status", OrderStatus.create.getCode()),
					 new BasicDBObject("order_status", OrderStatus.paying.getCode()),
					 new BasicDBObject("order_status", OrderStatus.closed.getCode()),
					 new BasicDBObject("order_status", OrderStatus.sysclosed.getCode()),
	                 new BasicDBObject("order_status", OrderStatus.paysuccess.getCode())};
		    where.put(QueryOperators.OR, obj);
		}
	    
	    // 设置当前页和页大小	    
	    MongoPage page = new MongoPage(pageCondition.getPageNumber(), pageCondition.getPageSize());
	    
	    convert(page, pageCondition);
	    
	    //排序
        /*if(EmptyChecker.isNotEmpty(pageCondition.getOrderByColumn())){
            page.setSort(new Sort(pageCondition.getOrderByColumn(),OrderBy.DESC));
        }*/
        
        LogCvt.info("MongoDb表：" + MongoTableName.CB_SUBORDER_PRODUCT + ", 条件：" + JSonUtil.toJSonString(where));
        long st = System.currentTimeMillis();
	    mongo.findByPageWithRedis(page, where, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	    LogCvt.info("queryBySubOrderByCondition分页查询耗时：（" + (System.currentTimeMillis() - st) + "）毫秒");
	    
	    return page;
	}
	
	@Override
	public OrderMongo getOrderById(String clientId, String orderId) {
	    BasicDBObject where = new BasicDBObject();
	    
	    where.put("_id", orderId);
	    where.put("client_id", clientId);
	    
	    LogCvt.info("Mongo表：" + MongoTableName.CB_ORDER + ", 条件：" + JSonUtil.toJSonString(where));
	    
	    return mongo.findOne(where, MongoTableName.CB_ORDER, OrderMongo.class);
	}
	
	@Override
    public OrderMongo getOrderById(String orderId) {
	    return this.getOrderById("anhui", orderId);
    }
	
	@Override
	public MongoPage queryOrderOfFacetfaceByBank(PageEntity<OrderQueryByBankCondition> pageEntity) {
	    
	    OrderQueryByBankCondition condition = pageEntity.getCondition();
	    
	    MongoPage page = new MongoPage(pageEntity.getPageNumber(), pageEntity.getPageSize());
	    
	    convert(page, pageEntity);
	    
	    BasicDBObject where = new BasicDBObject();
	    
	    //客户端编号
	    where.put("client_id", condition.getClientId());
	    
	    //团购
	    where.put("is_qrcode", BooleanUtils.toInteger(true));
	    
	    //创建时间
        if(EmptyChecker.isNotEmpty(condition.getStartTime()) &&  EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getStartTime() > 0 && condition.getEndTime() > 0 ){
			BasicDBList values = new BasicDBList();
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime())));
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime())));
			where.put(QueryOperators.AND,values);
		}else if(EmptyChecker.isNotEmpty(condition.getStartTime()) && condition.getStartTime() > 0){
			where.put("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime()));
        }else if(EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getEndTime() > 0){
        	where.put("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime()));
        }
        
        /*if (EmptyChecker.isNotEmpty(condition.getOrgLevel())) {
            if (OrgLevelEnum.orgLevel_one.equals(condition.getOrgLevel())) {
                where.put("f_org_code", condition.getOrgCode());
            } else if (OrgLevelEnum.orgLevel_two.equals(condition.getOrgLevel())) {
                where.put("s_org_code", condition.getOrgCode());
            } else if (OrgLevelEnum.orgLevel_three.equals(condition.getOrgLevel())) {
                where.put("t_org_code", condition.getOrgCode());
            } else if (OrgLevelEnum.orgLevel_four.equals(condition.getOrgLevel())) {
                where.put("l_org_code", condition.getOrgCode());
            }
        }*/
        //机构号支持多机构查询（2015-10-30 需求1.5.0 ）
        if (EmptyChecker.isNotEmpty(condition.getOrderOrgCode())) {
            if (EmptyChecker.isNotEmpty(condition.getOrderOrgCode().getForgCodeList())) {
            	if(condition.getOrderOrgCode().getForgCodeList().size()>1){
            		where.put("f_org_code", new BasicDBObject(QueryOperators.IN, condition.getOrderOrgCode().getForgCodeList()));
            	}else{
            		where.put("f_org_code", condition.getOrderOrgCode().getForgCodeList().get(0));
            	}
                
            } else if (EmptyChecker.isNotEmpty(condition.getOrderOrgCode().getSorgCodeList())) {
            	if(condition.getOrderOrgCode().getSorgCodeList().size()>1){
            		where.put("s_org_code", new BasicDBObject(QueryOperators.IN, condition.getOrderOrgCode().getSorgCodeList()));
            	}else{
            		where.put("s_org_code", condition.getOrderOrgCode().getSorgCodeList().get(0));
            	}
                
            } else if (EmptyChecker.isNotEmpty(condition.getOrderOrgCode().getTorgCodeList())) {
            	if(condition.getOrderOrgCode().getTorgCodeList().size()>1){
            		where.put("t_org_code", new BasicDBObject(QueryOperators.IN, condition.getOrderOrgCode().getTorgCodeList()));
            	}else{
            		where.put("t_org_code", condition.getOrderOrgCode().getTorgCodeList().get(0));
            	}
            } else if (EmptyChecker.isNotEmpty(condition.getOrderOrgCode().getLorgCodeList())) {
            	if(condition.getOrderOrgCode().getLorgCodeList().size()>1){
            		where.put("l_org_code", new BasicDBObject(QueryOperators.IN, condition.getOrderOrgCode().getLorgCodeList()));
            	}else{
            		where.put("l_org_code", condition.getOrderOrgCode().getLorgCodeList().get(0));
            	}
            }
        }
        
        if(EmptyChecker.isNotEmpty(condition.getOrderId())) {
            if(condition.getOrderId().length() == 12) {
                where.put("_id", condition.getOrderId());
            } else {
                Pattern pattern = Pattern.compile("^.*" + condition.getOrderId()+ ".*$", Pattern.CASE_INSENSITIVE); 
                where.put("_id", pattern);
            }
        }
        
        if(EmptyChecker.isNotEmpty(condition.getMerchantName())) {
            Pattern pattern = Pattern.compile("^.*" + condition.getMerchantName()+ ".*$", Pattern.CASE_INSENSITIVE);
            //where.put("merchant_name", pattern);
            //面对面直接查门店
            where.put("outlet_name", pattern);
        }
	    
        
        if(EmptyChecker.isNotEmpty(condition.getOrderStatus()) && condition.getOrderStatus().size() == 1) {
        	String tmpStatus = condition.getOrderStatus().get(0);
            where.put("order_status", tmpStatus);
        }
        if(EmptyChecker.isNotEmpty(condition.getOrderStatus()) && condition.getOrderStatus().size() > 1) {
        	Object [] obj = new Object[condition.getOrderStatus().size()];
        	for(int i=0;i<condition.getOrderStatus().size();i++){
        		obj[i] = new BasicDBObject("order_status", condition.getOrderStatus().get(i));
        	}
		    where.put(QueryOperators.OR, obj);
        }
        
        //排序
        /*if(EmptyChecker.isNotEmpty(pageEntity.getOrderByColumn())){
            page.setSort(new Sort(pageEntity.getOrderByColumn(), OrderBy.DESC));
        }*/
        
        LogCvt.info("查询Mongo表：" + MongoTableName.CB_ORDER + ", 查询条件：" + JSonUtil.toJSonString(where));
        long st = System.currentTimeMillis();
	    mongo.findByPageWithRedis(page, where, MongoTableName.CB_ORDER, OrderMongo.class);
	    LogCvt.info("queryOrderOfFacetfaceByBank分页查询耗时：（" + (System.currentTimeMillis() - st) + "）毫秒");
	    
	    //LogCvt.info("mongo查询结果：" + JSonUtil.toJSonString(page));
	    return page;
	}
	
	
	@Override
	public MongoPage queryBoutiqueSubOrderByBank(PageEntity<QueryBoutiqueOrderByBankDto> reqPage) {
		//子订单查询 
		QueryBoutiqueOrderByBankDto reqDto= reqPage.getCondition();
        LogCvt.info("[银行管理平台]-[订单管理]-精品商城列表查询-orderSupportImpl.queryBoutiqueSubOrderByBank...reqPage参数：" + JSonUtil.toJSonString(reqDto));
        DBObject subOrdreWhere = new BasicDBObject();
        
        //客户端ID
        if(org.apache.commons.lang.StringUtils.isNotEmpty(reqDto.getClientId())) {
        	subOrdreWhere.put("client_id", reqDto.getClientId());
        }
        
        subOrdreWhere.put("type", SubOrderType.boutique.getCode());
        
        //创建时间
        if(EmptyChecker.isNotEmpty(reqDto.getStartTime()) &&  EmptyChecker.isNotEmpty(reqDto.getEndTime()) &&  reqDto.getStartTime() > 0 && reqDto.getEndTime() > 0 ){
            BasicDBList values = new BasicDBList();
            values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,reqDto.getStartTime())));
            values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,reqDto.getEndTime())));
            subOrdreWhere.put(QueryOperators.AND,values);
        }else if(EmptyChecker.isNotEmpty(reqDto.getStartTime()) && reqDto.getStartTime() > 0){
        	subOrdreWhere.put("create_time",new BasicDBObject(QueryOperators.GTE,reqDto.getStartTime()));
		}else if(EmptyChecker.isNotEmpty(reqDto.getEndTime()) && reqDto.getEndTime() > 0){
			subOrdreWhere.put("create_time",new BasicDBObject(QueryOperators.LTE,reqDto.getEndTime()));
		}
        
        //订单状态
        if(EmptyChecker.isNotEmpty(reqDto.getOrderStatus())) {
        	String[] statusArr = reqDto.getOrderStatus().split(",");
        	if(statusArr != null && statusArr.length > 0) {
        		if(statusArr.length == 1) {
        			subOrdreWhere.put("order_status", statusArr[0]);
            	} else {
            		Object [] obj = new Object[statusArr.length];
            		for(int i = 0; i < statusArr.length; i++) {
            			obj[i] = new BasicDBObject("order_status", statusArr[i]);
            		}
            		subOrdreWhere.put(QueryOperators.OR, obj);
            	}
        	}
        }
        
        //子订单(2015.12.18 前端用subOrderId传大订单号，用大订单号查询，V1.10换)
        if(EmptyChecker.isNotEmpty(reqDto.getSubOrderId())) {
            if(reqDto.getSubOrderId().length() == 12) {
                subOrdreWhere.put("order_id", reqDto.getSubOrderId());
            } else {
                Pattern pattern = Pattern.compile("^.*" + reqDto.getSubOrderId()+ ".*$", Pattern.CASE_INSENSITIVE);
                subOrdreWhere.put("order_id", pattern);
            }
        }
        
        //供货商名称
        if(EmptyChecker.isNotEmpty(reqDto.getProviderName())) {
            Pattern pattern = Pattern.compile("^.*" + reqDto.getProviderName()+ ".*$", Pattern.CASE_INSENSITIVE);
            subOrdreWhere.put("merchant_name", pattern);
        }
        LogCvt.info("查询Mongo表：" + MongoTableName.CB_SUBORDER_PRODUCT + ", 查询条件：" + JSonUtil.toJSonString(subOrdreWhere));
      
        MongoPage page = new MongoPage(reqPage.getPageNumber(), reqPage.getPageSize());
        //分页参数转换
        convert(page, reqPage);
        //设置返回列
        List<String> collKeys = new ArrayList<String>();
        collKeys.add("client_id");
        collKeys.add("order_id");
        collKeys.add("sub_order_id");
        collKeys.add("merchant_name");
        collKeys.add("delivery_state");
        collKeys.add("order_status");
        collKeys.add("create_time");
        collKeys.add("type");
        collKeys.add("f_org_code");
        collKeys.add("s_org_code");
        collKeys.add("t_org_code");
        collKeys.add("l_org_code");
        collKeys.add("refund_state");
        collKeys.add("phone");
        collKeys.add("operator_name");
        collKeys.add("products.product_id");
        collKeys.add("products.product_name");
        collKeys.add("products.quantity");
        collKeys.add("products.vip_quantity");
        collKeys.add("products.money");
        collKeys.add("products.org_code");
        collKeys.add("products.org_name");
        collKeys.add("products.vip_money");
        collKeys.add("products.delivery_money");
        collKeys.add("products.delivery_option");
        long st = System.currentTimeMillis();
        
        //执行查询
        mongo.findByPageWithRedis(page, subOrdreWhere, collKeys, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
        LogCvt.info("queryBoutiqueSubOrderByBank：（" + (System.currentTimeMillis() - st) + "）毫秒");
        return page;
	}
	
	@Override
	public MongoPage querySubOrderByBank(PageEntity<OrderQueryByBankCondition> pageEntity) {
	    // 子订单查询 
	    
	    OrderQueryByBankCondition condition = pageEntity.getCondition();
	    LogCvt.info("[银行管理平台]-[订单管理]-列表查询-orderSupportImpl.querySubOrderByBank...condition参数：" + JSonUtil.toJSonString(condition));
	    DBObject where = new BasicDBObject();
		
	    //客户端编号
        where.put("client_id", condition.getClientId());
        if(EmptyChecker.isNotEmpty(condition.getType())) {
            
            String [] types = condition.getType().split(",");
            if(types.length == 1) {
                where.put("type", condition.getType());
            } else {
                List<Object> listTypes = new ArrayList<Object>();
                for(int i = 0; i < types.length; i ++) {
                    listTypes.add(new BasicDBObject("type", types[i]));
                }
                where.put(QueryOperators.OR, listTypes);
            }
        }
        
        //创建时间         
        if(EmptyChecker.isNotEmpty(condition.getStartTime()) &&  EmptyChecker.isNotEmpty(condition.getEndTime()) &&  condition.getStartTime() > 0 && condition.getEndTime() > 0 ){
			BasicDBList values = new BasicDBList();
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime())));
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime())));
			where.put(QueryOperators.AND,values);
		}else if(EmptyChecker.isNotEmpty(condition.getStartTime()) && condition.getStartTime() > 0){
			where.put("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime()));
        }else if(EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getEndTime() > 0){
        	where.put("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime()));
        }
        
        if(EmptyChecker.isNotEmpty(condition.getOrderStatus()) && condition.getOrderStatus().size() == 1) {
        	String tmpStatus = condition.getOrderStatus().get(0);
            where.put("order_status", tmpStatus);
        }
        if(EmptyChecker.isNotEmpty(condition.getOrderStatus()) && condition.getOrderStatus().size() > 1) {
        	Object [] obj = new Object[condition.getOrderStatus().size()];
        	for(int i=0;i<condition.getOrderStatus().size();i++){
        		obj[i] = new BasicDBObject("order_status", condition.getOrderStatus().get(i));
        	}
		    where.put(QueryOperators.OR, obj);
        }
        
        //机构条件待补充
        if(EmptyChecker.isNotEmpty(condition.getOrderId())) {
            if(condition.getOrderId().length() == 12) {
                where.put("order_id", condition.getOrderId());
            } else {
                Pattern pattern = Pattern.compile("^.*" + condition.getOrderId()+ ".*$", Pattern.CASE_INSENSITIVE);
                where.put("order_id", pattern);
            }
        }
        
        //子订单
        if(EmptyChecker.isNotEmpty(condition.getSubOrderId())) {
            if(condition.getSubOrderId().length() == 12) {
                where.put("sub_order_id", condition.getSubOrderId());
            } else {
                Pattern pattern = Pattern.compile("^.*" + condition.getSubOrderId()+ ".*$", Pattern.CASE_INSENSITIVE);
                where.put("sub_order_id", pattern);
            }
        }
        //预售、积分兑换增加登录人所属机构的条件
        //condition参数：{"clientId":"anhui","deliveryOption":"1","endTime":1442419199999,
        //	"minOrgLevel":"orgLevel_three","myOrgCode":"3417847417","orgCode":"3417847417",
        //	"orgCodeFlag":"myOrgCode","orgCodes":["3417847417"],"orgLevel":"orgLevel_three","startTime":1434556800000,"type":"2"}
        /*if(condition.getOrgCodes()!=null && condition.getOrgCodes().size()>0 && !"1".equals(condition.getMinOrgLevel().getLevel()) 
				   && OrderType.presell_org.getCode().equals(condition.getType()) 
				   && DeliveryType.take.getCode().equals(condition.getDeliveryOption())){//预售网点自提
        	where.put("products.org_code", new BasicDBObject(QueryOperators.IN,condition.getOrgCodes()));
        }else if(condition.getOrgCodes()!=null && condition.getOrgCodes().size()>0 && !"1".equals(condition.getMinOrgLevel().getLevel()) 
			   && OrderType.offline_points_org.getCode().equals(condition.getType())){//线下积分兑换
		   	where.put("products.org_code", new BasicDBObject(QueryOperators.IN,condition.getOrgCodes()));
        }else if(condition.getOrgCodes()!=null && condition.getOrgCodes().size()>0 && !"1".equals(condition.getMinOrgLevel().getLevel()) 
				   && OrderType.presell_org.getCode().equals(condition.getType()) 
				   && DeliveryType.home.getCode().equals(condition.getDeliveryOption())){//预售配送
        	where.put("merchant_id", "orgCode".equals(condition.getOrgCodeFlag())?condition.getOrgCode():condition.getMyOrgCode());
        }else if(condition.getOrgCodes()!=null && condition.getOrgCodes().size()>0 && !"1".equals(condition.getMinOrgLevel().getLevel()) 
 			   && OrderType.online_points_org.getCode().equals(condition.getType())){//线上积分兑换
        	where.put("merchant_id", "orgCode".equals(condition.getOrgCodeFlag())?condition.getOrgCode():condition.getMyOrgCode());
        }else{
        	 if(OrgLevelEnum.orgLevel_one.equals(condition.getOrgLevel())) {
                 where.put("f_org_code", condition.getOrgCode());
             } else if(OrgLevelEnum.orgLevel_two.equals(condition.getOrgLevel())) {
                 where.put("s_org_code", condition.getOrgCode());
             } else if(OrgLevelEnum.orgLevel_three.equals(condition.getOrgLevel())) {
                 where.put("t_org_code", condition.getOrgCode());
             } else if(OrgLevelEnum.orgLevel_four.equals(condition.getOrgLevel())) {
                 where.put("l_org_code", condition.getOrgCode());
             }
         }*/
        
        //机构号支持多机构查询（2015-10-30 需求1.5.0 ）
        if(OrderType.presell_org.getCode().equals(condition.getType()) && DeliveryType.take.getCode().equals(condition.getDeliveryOption())){
        	//预售网点自提
        	if(EmptyChecker.isNotEmpty(condition.getOrgCodes())){
        		where.put("products.org_code", new BasicDBObject(QueryOperators.IN,condition.getOrgCodes()));
        	}
        }else if(OrderType.offline_points_org.getCode().equals(condition.getType())){
        	//线下积分兑换
        	if(EmptyChecker.isNotEmpty(condition.getOrgCodes())){
        		where.put("products.org_code", new BasicDBObject(QueryOperators.IN,condition.getOrgCodes()));
        	}
        }else{
        	if(EmptyChecker.isNotEmpty(condition.getOrderOrgCode())) {
                if (EmptyChecker.isNotEmpty(condition.getOrderOrgCode().getForgCodeList())) {
                	if(condition.getOrderOrgCode().getForgCodeList().size()>1){
                		where.put("f_org_code", new BasicDBObject(QueryOperators.IN, condition.getOrderOrgCode().getForgCodeList()));	
                	}else{
                		where.put("f_org_code", condition.getOrderOrgCode().getForgCodeList().get(0));
                	}
                    
                } else if (EmptyChecker.isNotEmpty(condition.getOrderOrgCode().getSorgCodeList())) {
                	if(condition.getOrderOrgCode().getSorgCodeList().size()>1){
                		where.put("s_org_code", new BasicDBObject(QueryOperators.IN, condition.getOrderOrgCode().getSorgCodeList()));
                	}else{
                		where.put("s_org_code", condition.getOrderOrgCode().getSorgCodeList().get(0));
                	}
                } else if (EmptyChecker.isNotEmpty(condition.getOrderOrgCode().getTorgCodeList())) {
                	if(condition.getOrderOrgCode().getTorgCodeList().size()>1){
                		where.put("t_org_code", new BasicDBObject(QueryOperators.IN, condition.getOrderOrgCode().getTorgCodeList()));
                	}else{
                		where.put("t_org_code", condition.getOrderOrgCode().getTorgCodeList().get(0));
                	}
                } else if (EmptyChecker.isNotEmpty(condition.getOrderOrgCode().getLorgCodeList())) {
                	if(condition.getOrderOrgCode().getLorgCodeList().size()>1){
                		where.put("l_org_code", new BasicDBObject(QueryOperators.IN, condition.getOrderOrgCode().getLorgCodeList()));
                	}else{
                		where.put("l_org_code", condition.getOrderOrgCode().getLorgCodeList().get(0));
                	}
                }
            }
        }
        
        if(EmptyChecker.isNotEmpty(condition.getMerchantName())) {
            Pattern pattern = Pattern.compile("^.*" + condition.getMerchantName()+ ".*$", Pattern.CASE_INSENSITIVE);
            where.put("merchant_name", pattern);
        }
        //预售
        if (EmptyChecker.isNotEmpty(condition.getType()) && SubOrderType.presell_org.getCode().equals(condition.getType())) { 
        	if(EmptyChecker.isNotEmpty(condition.getDeliveryOption())) {
        		where.put("products.delivery_option", condition.getDeliveryOption());
        	}        	
        }
        LogCvt.info("查询Mongo表：" + MongoTableName.CB_SUBORDER_PRODUCT + ", 查询条件：" + JSonUtil.toJSonString(where));
        
	    MongoPage page = new MongoPage(pageEntity.getPageNumber(), pageEntity.getPageSize());
	    
	    convert(page, pageEntity);
	    
	    Collection<String> keys = new ArrayList<String>();
	    keys.add("client_id");
	    keys.add("order_id");
	    keys.add("sub_order_id");
	    keys.add("merchant_name");
	    keys.add("delivery_state");
	    keys.add("order_status");
	    keys.add("create_time");
	    keys.add("type");
	    keys.add("f_org_code");
	    keys.add("s_org_code");
	    keys.add("t_org_code");
	    keys.add("l_org_code");
	    keys.add("refund_state");
	    keys.add("phone");
	    keys.add("operator_name");
	    
	    keys.add("products.product_id");
	    keys.add("products.product_name");
	    keys.add("products.quantity");
	    keys.add("products.vip_quantity");
	    keys.add("products.money");
	    keys.add("products.org_code");
	    keys.add("products.org_name");
	    keys.add("products.vip_money");
	    keys.add("products.delivery_money");
	    keys.add("products.delivery_option");
	    
	    //排序
        /*if(EmptyChecker.isNotEmpty(pageEntity.getOrderByColumn())){
            page.setSort(new Sort(pageEntity.getOrderByColumn(), OrderBy.DESC));
        }*/
        long st = System.currentTimeMillis();
	    mongo.findByPageWithRedis(page, where,keys, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	    LogCvt.info("querySubOrderByBank：（" + (System.currentTimeMillis() - st) + "）毫秒");
	    return page;
	}
	
	@Override
	public DeliverInfo getDeliveryInfo(String clientId,long memberCode,String deliveryId) {
		String id = clientId+"_"+memberCode;
		List<DBObject> pipeLine = new ArrayList<DBObject>();
		DBObject query = new BasicDBObject();
		query.put("_id", id);
        pipeLine.add(new BasicDBObject("$match", query));
        pipeLine.add(new BasicDBObject("$unwind", "$delivery_info"));
        
        DBObject query2 = new BasicDBObject();
		query2.put("delivery_info.delivery_id", deliveryId);
        
        pipeLine.add(new BasicDBObject("$match", query2));
        Cursor cursor = mongo.getReadDBCollection(MongoTableName.CB_MERCHANT_OUTLET_FAVORITE).aggregate(pipeLine, AggregationOptions.builder().build());
        DBObject dbObj = new CursorHandleImpl().handle(cursor);
        if(dbObj != null){
        	 String json = JSonUtil.toJSonString(((DBObject)dbObj.get("delivery_info")));
             return JSonUtil.toObject(json, DeliverInfo.class);
        }
       return null;
	}
	
	/*@Override
	public RecvInfo getRecvInfo(String clientId,long memberCode,String recvId) {
		String id = clientId+"_"+memberCode;
		List<DBObject> pipeLine = new ArrayList<DBObject>();
		DBObject query = new BasicDBObject();
		query.put("_id", id);
        pipeLine.add(new BasicDBObject("$match", query));
        pipeLine.add(new BasicDBObject("$unwind", "$recv_info"));
        
        DBObject query2 = new BasicDBObject();
		query2.put("recv_info.recv_id", recvId);
        
        pipeLine.add(new BasicDBObject("$match", query2));
        Cursor cursor = mongo.getReadDBCollection(MongoTableName.CB_MERCHANT_OUTLET_FAVORITE).aggregate(pipeLine, AggregationOptions.builder().build());
        DBObject dbObj = new CursorHandleImpl().handle(cursor);
        if(dbObj != null){
        	 String json = JSonUtil.toJSonString(((DBObject)dbObj.get("recv_info")));
             return JSonUtil.toObject(json, RecvInfo.class);
        }
       return null;
	}*/
	
	@Override
	public RecvInfo getRecvInfo(String clientId,long memberCode,String recvId) {
		String id = clientId+"_"+memberCode;
		DBObject query = new BasicDBObject();
		query.put("_id", id);
//		query.put("recv_info.recv_id", recvId);
		MerchantOutletFavorite merchantOutletFavorite =  mongo.findOne(query, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE, MerchantOutletFavorite.class);
		RecvInfo recvInfoResult = new RecvInfo();
		if(EmptyChecker.isNotEmpty(merchantOutletFavorite)){
			if(EmptyChecker.isNotEmpty(merchantOutletFavorite.getRecvInfo())){
				for(RecvInfo recvInfo : merchantOutletFavorite.getRecvInfo()){
					if(StringUtils.equals(recvInfo.getRecvId(), recvId)){
						recvInfoResult.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
						recvInfoResult.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
						recvInfoResult.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
						recvInfoResult.setAreaId(EmptyChecker.isEmpty(recvInfo.getAreaId())?0:recvInfo.getAreaId());
						break;
					}
				}
			}
		}
		return recvInfoResult;
	}
	
	@Override
	public Map<String,RecvInfo> getRecvInfoMap(List<SubOrderMongo> subOrderList,boolean isNeedAreaDetail) {
		LogCvt.info("[收货信息]-查询开始...");
		long st = System.currentTimeMillis();
		//order_id : RecvInfo
		Map<String,RecvInfo> resultMap = new HashMap<String,RecvInfo>();
		
		//大订单ID集合
		List<String> orderIdList = new ArrayList<String>();
		if(EmptyChecker.isEmpty(subOrderList)){
			LogCvt.info("子订单集合为空，查询提货人信息为空");
			return resultMap;
		}else{
			for(SubOrderMongo subOrderMongo : subOrderList){
				if(!orderIdList.contains(subOrderMongo.getOrderId())){
					orderIdList.add(subOrderMongo.getOrderId());
				}
			}
			if(EmptyChecker.isEmpty(orderIdList)){
				LogCvt.info("大订单集合为空，查询提货人信息为空");
				return resultMap;
			}
		}
		
		//查询大订单
		long st1 = System.currentTimeMillis();
		DBObject queryOrder = new BasicDBObject();
		DBObject orderIdCondition = new BasicDBObject();
		orderIdCondition.put(QueryOperators.IN, orderIdList.toArray());
		queryOrder.put("_id", orderIdCondition);
		List<OrderMongo> orderMongoList =  (List<OrderMongo>) mongo.findAll(queryOrder, MongoTableName.CB_ORDER, OrderMongo.class);
		LogCvt.info("[收货信息]-中间查询-大订单查询耗时："+ (System.currentTimeMillis()-st1)+"ms");
		
		//收藏夹表_id集合
		List<String> merchantOutletFavoriteIdList = new ArrayList<String>();
		//收货信息_id_recvId集合
		List<String> clientIdMemberCodeRecvIdList = new ArrayList<String>();
		for(OrderMongo orderMongo : orderMongoList){
			if(EmptyChecker.isNotEmpty(orderMongo.getRecvId()) || EmptyChecker.isNotEmpty(orderMongo.getDeliverId())){
				//_id
				String id = orderMongo.getClientId()+"_"+orderMongo.getMemberCode();
				merchantOutletFavoriteIdList.add(id);
				
				//recv_id
				if(EmptyChecker.isNotEmpty(orderMongo.getRecvId())){
					String clientIdMemberCodeRecvId = id+"_"+orderMongo.getRecvId();
					clientIdMemberCodeRecvIdList.add(clientIdMemberCodeRecvId);
				}else if(EmptyChecker.isNotEmpty(orderMongo.getDeliverId())){
					String clientIdMemberCodeRecvId = id+"_"+orderMongo.getDeliverId();
					clientIdMemberCodeRecvIdList.add(clientIdMemberCodeRecvId);
				}
			}else{
				LogCvt.error("[数据错误]-警告!!!订单无收货/提货人信息(cb_order表recv_id/deliver_id都为空)，orderId = "+orderMongo.getOrderId());
			}
		}
		
		long st2 = System.currentTimeMillis();
		//查询cb_merchant_outlet_favorite
		DBObject query = new BasicDBObject();
		DBObject idCondition = new BasicDBObject();
		idCondition.put(QueryOperators.IN, merchantOutletFavoriteIdList.toArray());
		query.put("_id", idCondition);
		List<MerchantOutletFavorite> merchantOutletFavoriteList =  (List<MerchantOutletFavorite>) mongo.findAll(query, MongoTableName.CB_MERCHANT_OUTLET_FAVORITE, MerchantOutletFavorite.class);
		LogCvt.info("[收货信息]-中间查询-收藏夹地址列表查询耗时："+ (System.currentTimeMillis()-st2)+"ms");
		
		//收货信息clientId_memberCode_recvId : RecvInfo
		Map<String,RecvInfo> recvInfoMap = new HashMap<String,RecvInfo>();
		//收货信息areaId集合
		List<Long> areaIdList = new ArrayList<Long>();
		if(EmptyChecker.isNotEmpty(merchantOutletFavoriteList)){
			for(MerchantOutletFavorite merchantOutletFavorite : merchantOutletFavoriteList){
				if(EmptyChecker.isNotEmpty(merchantOutletFavorite.getRecvInfo())){
					for(RecvInfo recvInfo : merchantOutletFavorite.getRecvInfo()){
						String key = merchantOutletFavorite.getId()+"_"+recvInfo.getRecvId();
						if(clientIdMemberCodeRecvIdList.contains(key)){
							recvInfoMap.put(key, recvInfo);
							if(EmptyChecker.isNotEmpty(recvInfo.getAreaId())){
								areaIdList.add(recvInfo.getAreaId());
							}else{
								LogCvt.error("[数据错误]-警告!!!收货地址无区域信息(cb_merchant_outlet_favorite表area_id为空)，clientId_memberCode_recvId = " + key);
							}
						}
					}
				}
			}
		}
		
		//显示地址所在省市区
		if(isNeedAreaDetail){
			//获取收货地址省市区
			Map<Long,String> areaMap = null;
			if(EmptyChecker.isNotEmpty(areaIdList)){
				areaMap = findAreaNameById(areaIdList);
			}else{
				LogCvt.info("收货信息areaId集合为空");
			}
			if (EmptyChecker.isNotEmpty(recvInfoMap)) {
				Iterator<String> it = recvInfoMap.keySet().iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					RecvInfo recvInfo = recvInfoMap.get(key);
					String address = recvInfo.getAddress();
					String province = null;
					if(EmptyChecker.isNotEmpty(areaMap)){
						province = areaMap.get(recvInfo.getAreaId());
					}
					if(EmptyChecker.isNotEmpty(province)){
						province = province.replaceAll(",", "");
						recvInfo.setAddress(province+address);
					}else{
						LogCvt.error("省市地区不存在："+recvInfo.getAreaId());
					}
					
				}
			}
		}
		
		
		for(OrderMongo orderMongo : orderMongoList){
			String recv_id = "";
			if(EmptyChecker.isNotEmpty(orderMongo.getRecvId())){
				recv_id = orderMongo.getRecvId();
			}else if(EmptyChecker.isNotEmpty(orderMongo.getDeliverId())){
				recv_id = orderMongo.getDeliverId();
			}
			if(EmptyChecker.isNotEmpty(recv_id)){
				//_id
				String id = orderMongo.getClientId()+"_"+orderMongo.getMemberCode()+"_"+recv_id;
				
				if(EmptyChecker.isNotEmpty(recvInfoMap) && EmptyChecker.isNotEmpty(recvInfoMap.get(id))){
					resultMap.put(orderMongo.getOrderId(), recvInfoMap.get(id));
				}else{
					LogCvt.error("[数据错误]-警告!!!查不到收货信息，clientId_memberCode_recvId = "+id);
				}
			}else{
				LogCvt.error("[数据错误]-警告!!!订单没有关联收货信息(cb_order表recv_id/deliver_id都为空)，orderId = "+orderMongo.getOrderId());
			}
		}
		
		LogCvt.info("[收货信息]-查询结束！子订单"+subOrderList.size()+"条，关联大订单"+orderMongoList.size()+"条，关联收货信息"+resultMap.size()+"条，总耗时：" + (System.currentTimeMillis()-st)+"ms");
		return resultMap;
	}
	
	/**
	 * 根据手机号码查找所有的收货地址
	 * @param phone
	 * @return
	 *<pre>
	 *
	 * @Description: 手机号码查找收货地址 
	 * @version V1.0 修改人：Arron 日期：2015年4月28日 下午4:07:21 
	 *
	 *</pre>
	 */
	public List<RecvInfo> queryRecvInfos(String phone) {
	    List<RecvInfo> list = new ArrayList<RecvInfo>();
	    
	    List<DBObject> pipeLine = new ArrayList<DBObject>();
	    
	    pipeLine.add(new BasicDBObject("$unwind", "$recv_info"));
	    pipeLine.add(new BasicDBObject("$match", new BasicDBObject("recv_info.phone", phone)));
	    
	    Cursor cursor = mongo.getReadDBCollection(MongoTableName.CB_MERCHANT_OUTLET_FAVORITE).aggregate(pipeLine, AggregationOptions.builder().build());
	    
	    List<DBObject> dbObj = new CursorHandleImpl().handleList(cursor);
	    
	    if(EmptyChecker.isNotEmpty(dbObj)) {
	        for(DBObject db : dbObj) {
	            String json = JSonUtil.toJSonString(db.get("recv_info"));
	            list.add(JSonUtil.toObject(json, RecvInfo.class));
	        }
	    }
	    return list;
	}
	
	@Override
	public Map<String,String> getIdByQrcode(String clientId,String qrcode) {
		String qrcodeKey = RedisKeyUtil.cbbank_outlet_product_clientId_qrcode(clientId,qrcode);
		return redis.getMap(qrcodeKey);
	}
	
	@Override
	public boolean updateQrcodeState(String clientId,String qrcode,String state) {
		String qrcodeKey = RedisKeyUtil.cbbank_outlet_product_clientId_qrcode(clientId,qrcode);
		Long result = redis.hset(qrcodeKey, "state", state);
		OrderLogger.info("订单模块", "修改二维码缓存状态", "请求数据", new Object[]{"clientId",clientId,"qrcode",qrcode,"state",state,"qrcodeKey",qrcodeKey,"修改结果",result,"修改后的数据",JSON.toJSONString(redis.getMap(qrcodeKey))});
		return result >= 0 ;
	}
	
	@Override
	public boolean updateOrderByCondion(OrderMongo order){
		long startTime = System.currentTimeMillis();
		DBObject where = new BasicDBObject();
		if(EmptyChecker.isNotEmpty(order.getClientId())){
			where.put("client_id", order.getClientId());
		}
		where.put("_id", order.getOrderId());
		
		boolean updateSubOrderFlag = false;
		
		DBObject value = new BasicDBObject();
		if(order.getPaymentMethod()!=null){
			value.put("payment_method", order.getPaymentMethod());
		}
		if(EmptyChecker.isNotEmpty(order.getOrderStatus())){
			value.put("order_status", order.getOrderStatus());
			updateSubOrderFlag = true;
		}
		if(EmptyChecker.isNotEmpty(order.getOrderStatus()) && (StringUtils.equals(order.getOrderStatus(), OrderStatus.sysclosed.getCode()) 
				|| StringUtils.equals(order.getOrderStatus(), OrderStatus.closed.getCode()))){
			value.put("state", OrderState.SYSTEM_CLOSED.getCode());
		}
		if(EmptyChecker.isNotEmpty(order.getState()) && (StringUtils.equals(order.getState(), OrderState.SYSTEM_CLOSED.getCode()) || StringUtils.equals(order.getState(), OrderState.RETURNED.getCode())) || StringUtils.equals(order.getState(), OrderState.NORMAL.getCode())){
			value.put("state", order.getState());
		}
		if(EmptyChecker.isNotEmpty(order.getPaymentTime())){
			value.put("payment_time", order.getPaymentTime());
		}
		if(EmptyChecker.isNotEmpty(order.getRealPrice())){
			value.put("real_price", order.getRealPrice());
		}
		if(EmptyChecker.isNotEmpty(order.getFftPoints())){
			value.put("fft_points", order.getFftPoints());
		}
		if(EmptyChecker.isNotEmpty(order.getBankPoints())){
			value.put("bank_points", order.getBankPoints());
		}
		if(EmptyChecker.isNotEmpty(order.getPointRate())){
			value.put("point_rate", order.getPointRate());
		}
		if(EmptyChecker.isNotEmpty(order.getFee())){
			value.put("fee", order.getFee());
		}
		if(order.getRemark() != null){
			value.put("remark", order.getRemark());
		}
		
		//秒杀的订单，支付失败时，直接关闭 (需求变更时间2015.06.08)
		if(EmptyChecker.isNotEmpty(order.getIsSeckill()) 
				&& order.getIsSeckill() == 1 
				&& EmptyChecker.isNotEmpty(order.getOrderStatus()) 
				&& StringUtils.equals(order.getOrderStatus(), OrderStatus.payfailed.getCode())){
			LogCvt.info("秒杀订单支付失败，关闭订单");
			
			order.setOrderStatus(OrderStatus.sysclosed.getCode());
			value.put("state", OrderState.SYSTEM_CLOSED.getCode());
			value.put("order_status", order.getOrderStatus());
			
			List<SubOrderMongo> subOrderList = getSubOrderListByOrderId(order.getClientId(), order.getOrderId());
			boolean flag = updateMemberBuyHistoryForSeckill(subOrderList,false);
			if(!flag){
				LogCvt.error("秒杀支付失败，更新用户购买记录，失败！");
			}
		}
		
		//秒杀的订单，支付成功，加入业务监控项
		if(EmptyChecker.isNotEmpty(order.getIsSeckill()) 
				&& order.getIsSeckill() == 1 
				&& EmptyChecker.isNotEmpty(order.getOrderStatus()) 
				&& StringUtils.equals(order.getOrderStatus(), OrderStatus.paysuccess.getCode())){
			monitorService.send(MonitorPointEnum.Worker_Order_Success_Count);
		}
		
//		int result = mongo.update(value, where, MongoTableName.CB_ORDER, "$set");
		OrderMongo orderMongo = mongo.findAndModify(new BasicDBObject("$set", value), where, MongoTableName.CB_ORDER, false, OrderMongo.class);
		boolean updateOrderFlag = false;
		if(EmptyChecker.isEmpty(orderMongo)){
			LogCvt.error("大订单信息修改失败");
			return false;
		}else{
			updateOrderFlag = true;
		}
		//如果大订单改状态，子订单也改
		if(updateSubOrderFlag && updateOrderFlag){
			//更新子订单状态
			DBObject subWhere = new BasicDBObject();
			subWhere.put("order_id", order.getOrderId());
			DBObject subValue = new BasicDBObject();
			subValue.put("order_status", order.getOrderStatus());
			int subResult = mongo.updateMulti(subValue, subWhere, MongoTableName.CB_SUBORDER_PRODUCT, "$set");
			if(subResult != -1) {
				//修改成功情况下， 把指定Redis-key删除
				if(StringUtils.equals(OrderStatus.paysuccess.getCode(), order.getOrderStatus())
						|| StringUtils.equals(OrderStatus.closed.getCode(), order.getOrderStatus())
						|| StringUtils.equals(OrderStatus.sysclosed.getCode(), order.getOrderStatus())){
					String redisKey = RedisKeyUtil.cbbank_time_order_key();
					String redisValue = RedisKeyUtil.cbbank_time_order_value(order.getClientId(), order.getOrderId());
					redis.srem(redisKey, redisValue);
					LogCvt.info("修改订单状态:order_status=" + order.getOrderStatus() + ", 移除Redis中[key="+redisKey+"  value=" + redisValue + "]");
				}
				LogCvt.info("更新订单：成功" + "修改条件：" + JSonUtil.toJSonString(where) + ", 订单修改内容为：" + JSonUtil.toJSonString(value));
				
				//订单支付成功时，计算订单的历史VIP优惠金额
				if(StringUtils.equals(OrderStatus.paysuccess.getCode(), order.getOrderStatus())){
					RedisCommon.updateVipDiscount(orderMongo.getClientId(),orderMongo.getMemberCode(),orderMongo.getVipDiscount(),true);
				}
				
				return true;
			}else{
				LogCvt.error("子订单的订单状态修改失败，订单号：" + order.getOrderId());
				return false;
			}
		}
		
		LogCvt.info("[修改订单]orderSupport.updateOrderByCondion耗时: " + (System.currentTimeMillis() - startTime));
		return true;
	}
	
	@Override
	public MongoPage queryOrderByFacetfaceOfMerchantConditionOfPage(PageEntity<OrderQueryMerchantManageCondition> pageEntity) {
	    OrderQueryMerchantManageCondition condition = pageEntity.getCondition();
        
        MongoPage page = new MongoPage(pageEntity.getPageNumber(), pageEntity.getPageSize());
        
        convert(page, pageEntity);
        
        BasicDBObject where = new BasicDBObject();
        
        //客户端编号
        where.put("client_id", condition.getClientId());
        //商户号
        where.put("merchant_id", condition.getMerchantId());
        
        //面对面
        where.put("is_qrcode", BooleanUtils.toInteger(true));
        
        //所属门店
        if(EmptyChecker.isNotEmpty(condition.getOutletId())) {
            where.put("outlet_id", condition.getOutletId());
        }
        
        //创建时间
        if(EmptyChecker.isNotEmpty(condition.getStartTime()) && EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getStartTime() > 0 && condition.getEndTime() > 0 ){
			BasicDBList values = new BasicDBList();
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime())));
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime())));
			where.put(QueryOperators.AND,values);
		}else if(EmptyChecker.isNotEmpty(condition.getStartTime()) && condition.getStartTime() > 0){
			where.put("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime()));
        }else if(EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getEndTime() > 0){
        	where.put("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime()));
        }
        
        //订单状态
        if(EmptyChecker.isNotEmpty(condition.getOrderStatus())) {
        	if(StringUtils.equals(condition.getOrderStatus(), OrderStatus.sysclosed.getCode())){
        		Object [] obj = {
   					 new BasicDBObject("order_status", OrderStatus.sysclosed.getCode()),
   					 new BasicDBObject("order_status", OrderStatus.closed.getCode())};
   		    where.put(QueryOperators.OR, obj);
        	}else{
        		where.put("order_status", condition.getOrderStatus());
        	}
        }else{//bug3526 不要展示订单关闭的订单（2015.07.08）
			Object [] obj = {new BasicDBObject("order_status", OrderStatus.payfailed.getCode()),
					 new BasicDBObject("order_status", OrderStatus.create.getCode()),
					 new BasicDBObject("order_status", OrderStatus.paying.getCode()),
					 new BasicDBObject("order_status", OrderStatus.sysclosed.getCode()),
					 new BasicDBObject("order_status", OrderStatus.closed.getCode()),
	                 new BasicDBObject("order_status", OrderStatus.paysuccess.getCode())};
		    where.put(QueryOperators.OR, obj);
		}
        
        //排序
        /*if(EmptyChecker.isNotEmpty(pageEntity.getOrderByColumn())){
            page.setSort(new Sort(pageEntity.getOrderByColumn(), OrderBy.DESC));
        }*/
        
        LogCvt.info("查询Mongo表：" + MongoTableName.CB_ORDER + ", 查询条件：" + JSonUtil.toJSonString(where));
        long st = System.currentTimeMillis();
        mongo.findByPageWithRedis(page, where, MongoTableName.CB_ORDER, OrderMongo.class);
        LogCvt.info("queryOrderByFacetFaceOfMerchantCondtionOfPage分页查询耗时：（" + (System.currentTimeMillis() - st) + "）毫秒");
        
        //LogCvt.info("mongo查询结果：" + JSonUtil.toJSonString(page));
        return page;
	}
	
	@Override
	public List<?> queryOrderByFacetfaceOfMerchantConditionOfAll(OrderQueryMerchantManageCondition condition) {
	    BasicDBObject where = new BasicDBObject();
        
//      客户端编号
        where.put("client_id", condition.getClientId());
//        商户号
        where.put("merchant_id", condition.getMerchantId());
//      面对面
        where.put("is_qrcode", BooleanUtils.toInteger(true));
        
//      所属门店
        if(EmptyChecker.isNotEmpty(condition.getOutletId())) {
            where.put("outlet_id", condition.getOutletId());
        }
        
//      创建时间
        if(EmptyChecker.isNotEmpty(condition.getStartTime()) && condition.getStartTime() > 0 && EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getEndTime() > 0 ){
			BasicDBList values = new BasicDBList();
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime())));
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime())));
			where.put(QueryOperators.AND,values);
		}else if(EmptyChecker.isNotEmpty(condition.getStartTime()) && condition.getStartTime() > 0){
			where.put("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime()));
        }else if(EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getEndTime() > 0){
        	where.put("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime()));
        }
        
//        订单状态
        if(EmptyChecker.isNotEmpty(condition.getOrderStatus())) {
            where.put("order_status", condition.getOrderStatus());
        }else{//bug3526 不要展示订单关闭的订单（2015.07.08）
			Object [] obj = {new BasicDBObject("order_status", OrderStatus.payfailed.getCode()),
					 new BasicDBObject("order_status", OrderStatus.create.getCode()),
					 new BasicDBObject("order_status", OrderStatus.paying.getCode()),
					 new BasicDBObject("order_status", OrderStatus.sysclosed.getCode()),
					 new BasicDBObject("order_status", OrderStatus.closed.getCode()),
	                 new BasicDBObject("order_status", OrderStatus.paysuccess.getCode())};
		    where.put(QueryOperators.OR, obj);
		}
        
        LogCvt.info("查询Mongo表：" + MongoTableName.CB_ORDER + ", 查询条件：" + JSonUtil.toJSonString(where));
        
	    return mongo.findAll(where, MongoTableName.CB_ORDER, OrderMongo.class);
	}
	
	@Override
	public List<?> querySubOrderOfMerchantConditonOfAll(OrderQueryMerchantManageCondition condition) {
        
        
        BasicDBObject where = new BasicDBObject();
        
//      客户端编号
        where.put("client_id", condition.getClientId());
//        商户号
        where.put("merchant_id", condition.getMerchantId());
        
        // TODO 不根据门店来
//        if(EmptyChecker.isNotEmpty(condition.getOutletId())) {
//            where.put("outlet_id", condition.getOutletId());
//        }
        
//        交易类型
        if (EmptyChecker.isNotEmpty(condition.getType())) {
            where.put("type", condition.getType());
        } else {
            Object[] objSub = { new BasicDBObject("type", OrderType.group_merchant.getCode()), new BasicDBObject("type", OrderType.special_merchant.getCode()) };
            where.put(QueryOperators.OR, objSub);
        }
        
        //名优特惠增加条件筛选：发货状态（ 2015.07.08）
        if(StringUtils.equals(condition.getType(), SubOrderType.special_merchant.getCode()) && EmptyChecker.isNotEmpty(condition.getDeliveryStatus())){
        	where.put("delivery_state", condition.getDeliveryStatus());
        }
        
//      创建时间
        if(EmptyChecker.isNotEmpty(condition.getStartTime()) && EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getStartTime() > 0 && condition.getEndTime() > 0 ){
			BasicDBList values = new BasicDBList();
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime())));
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime())));
			where.put(QueryOperators.AND,values);
		}else if(EmptyChecker.isNotEmpty(condition.getStartTime()) && condition.getStartTime() > 0){
			where.put("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime()));
        }else if(EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getEndTime() > 0){
        	where.put("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime()));
        }
        
//      订单状态
        if(EmptyChecker.isNotEmpty(condition.getOrderStatus())) {
            where.put("order_status", condition.getOrderStatus());
        }
        
        LogCvt.info("查询Mongo表：" + MongoTableName.CB_SUBORDER_PRODUCT + ", 查询条件：" + JSonUtil.toJSonString(where));
        List<?> list = mongo.findAll(where, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
        LogCvt.info("mongo查询结果：" + JSonUtil.toJSonString(list));
	    return list;
	}
	
	public MongoPage querySubOrderOfMerchantConditonOfPage(com.froad.po.base.PageEntity<OrderQueryMerchantManageCondition> pageEntity) {
	    OrderQueryMerchantManageCondition condition = pageEntity.getCondition();
        
        MongoPage page = new MongoPage(pageEntity.getPageNumber(), pageEntity.getPageSize());
        
        convert(page, pageEntity);
        
        BasicDBObject where = new BasicDBObject();
        
        //客户端编号
        where.put("client_id", condition.getClientId());
        //商户号
        where.put("merchant_id", condition.getMerchantId());
        
        //不根据门店来查询
//		if(EmptyChecker.isNotEmpty(condition.getOutletId())) {
//            where.put("outlet_id", condition.getOutletId());
//      }
        
        //交易类型
        if (EmptyChecker.isNotEmpty(condition.getType())) {
            where.put("type", condition.getType());
        } else {
            Object[] objSub = { new BasicDBObject("type", OrderType.group_merchant.getCode()), new BasicDBObject("type", OrderType.special_merchant.getCode()) };
            where.put(QueryOperators.OR, objSub);
        }
        
        //名优特惠增加条件筛选：发货状态（ 2015.07.08）
        if(StringUtils.equals(condition.getType(), SubOrderType.special_merchant.getCode()) && EmptyChecker.isNotEmpty(condition.getDeliveryStatus())){
        	where.put("delivery_state", condition.getDeliveryStatus());
        }
        
        //创建时间
        if(EmptyChecker.isNotEmpty(condition.getStartTime()) && EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getStartTime() > 0 && condition.getEndTime() > 0 ){
			BasicDBList values = new BasicDBList();
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime())));
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime())));
			where.put(QueryOperators.AND,values);
		}else if(EmptyChecker.isNotEmpty(condition.getStartTime()) && condition.getStartTime() > 0){
			where.put("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime()));
        }else if(EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getEndTime() > 0){
        	where.put("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime()));
        }
        
        //订单状态
        if(EmptyChecker.isNotEmpty(condition.getOrderStatus())) {
        	if(StringUtils.equals(condition.getOrderStatus(), OrderStatus.sysclosed.getCode())){
        		Object [] obj = {
   					 new BasicDBObject("order_status", OrderStatus.sysclosed.getCode()),
   					 new BasicDBObject("order_status", OrderStatus.closed.getCode())};
   		    where.put(QueryOperators.OR, obj);
        	}else{
        		where.put("order_status", condition.getOrderStatus());
        	}
        }else{//bug3526 不要展示订单关闭的订单（2015.07.08）
			Object [] obj = {new BasicDBObject("order_status", OrderStatus.payfailed.getCode()),
					 new BasicDBObject("order_status", OrderStatus.create.getCode()),
					 new BasicDBObject("order_status", OrderStatus.paying.getCode()),
					 new BasicDBObject("order_status", OrderStatus.sysclosed.getCode()),
   					 new BasicDBObject("order_status", OrderStatus.closed.getCode()),
	                 new BasicDBObject("order_status", OrderStatus.paysuccess.getCode())};
		    where.put(QueryOperators.OR, obj);
		}
        
        //排序
        /*if(EmptyChecker.isNotEmpty(pageEntity.getOrderByColumn())){
            page.setSort(new Sort(pageEntity.getOrderByColumn(), OrderBy.DESC));
        }*/
        /*page.setSort(new Sort("create_time", OrderBy.DESC));*/
        LogCvt.info("查询Mongo表：" + MongoTableName.CB_SUBORDER_PRODUCT + ", 查询条件：" + JSonUtil.toJSonString(where));
        long st = System.currentTimeMillis();
        mongo.findByPageWithRedis(page, where, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
//        mongo.findByPage(page, where, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
        LogCvt.info("querySubOrderOfMerchantCondtionOfPage分页查询耗时：（" + (System.currentTimeMillis() - st) + "）毫秒");
        
//        LogCvt.info("mongo查询结果：" + JSonUtil.toJSonString(page));
        return page;
	}
	
	@Override
	public MongoPage queryOrderOfBossByCondition(PageEntity<OrderQueryByBossCondition> pageEntity) {
//	    设置分页参数
	    MongoPage page = new MongoPage(pageEntity.getPageNumber(), pageEntity.getPageSize());
	    
	    convert(page, pageEntity);
	    
	    OrderQueryByBossCondition condition = pageEntity.getCondition();
	    
//	    设置查询条件
	    DBObject where = new BasicDBObject();
	    if(EmptyChecker.isNotEmpty(condition.getOrderId())) {
	        where.put("_id", condition.getOrderId());
	    }
	    if(condition.getMemberCode() > 0) {
	        where.put("member_code", condition.getMemberCode());
	    }
	    if(EmptyChecker.isNotEmpty(condition.getCreateSource())) {
	        where.put("create_source", condition.getCreateSource());
	    }
	    
	    if(EmptyChecker.isNotEmpty(condition.getPaymentMethod())) {
	        where.put("payment_method", condition.getPaymentMethod());
	    }
	    
	    
	    if(EmptyChecker.isNotEmpty(condition.getOrderStatus())) {
	        String [] os = condition.getOrderStatus().split(",");
	        if(os.length > 1) {
	            // 支持多状态查询
	            List<Object> orders = new ArrayList<Object>();
	            for (String s : os) {
	                DBObject orderwhere = new BasicDBObject("order_status", s);
	                orders.add(orderwhere);
	            }
	            where.put(QueryOperators.OR, orders);
	        } else {
	            where.put("order_status", condition.getOrderStatus());
	        }
	    }
	    if(EmptyChecker.isNotEmpty(condition.getClientId())) {
	        where.put("client_id", condition.getClientId());
	    }
	    if(EmptyChecker.isNotEmpty(condition.getPhone())) {
	        if(EmptyChecker.isNotEmpty(condition.getRecvId())) {
	            DBObject obj = new BasicDBObject();
	            obj.put("phone", condition.getPhone());
	            obj.put("recv_id", condition.getRecvId());
	            where.put(QueryOperators.OR, obj);
	        } else {
	            where.put("phone", condition.getPhone());
	        }
	    }
	    
//      创建时间
        if(EmptyChecker.isNotEmpty(condition.getStartTime()) && EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getStartTime() > 0 && condition.getEndTime() > 0 ){
			BasicDBList values = new BasicDBList();
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime())));
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime())));
			where.put(QueryOperators.AND,values);
		}else if(EmptyChecker.isNotEmpty(condition.getStartTime()) && condition.getStartTime() > 0){
			where.put("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime()));
        }else if(EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getEndTime() > 0){
        	where.put("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime()));
        }
        
        //排序
        /*if(EmptyChecker.isNotEmpty(pageEntity.getOrderByColumn())){
            page.setSort(new Sort(pageEntity.getOrderByColumn(), OrderBy.DESC));
        }*/
        
        mongo.findByPageWithRedis(page, where, MongoTableName.CB_ORDER, OrderMongo.class);
        
	    return page;
	}
	
	@Override
	public boolean updateOrderStatusByOrderId(String orderId) {
	    
	    DBObject where = new BasicDBObject();
	    where.put("_id", orderId);
	    
	    Object [] obj = {new BasicDBObject("order_status", OrderStatus.payfailed.getCode()), new BasicDBObject("order_status", OrderStatus.create.getCode())};
        where.put(QueryOperators.OR, obj);
	    
//	    LogCvt.info("查询订单条件：" + where);
	    
	    //修改大订单和子订单
	    
	    DBObject update = new BasicDBObject("$set",new BasicDBObject("order_status", OrderStatus.paying.getCode()));
	    
	    
//	    long startTime = System.currentTimeMillis();
	    OrderMongo order = mongo.findAndModify(update, where, MongoTableName.CB_ORDER, false, OrderMongo.class);
	    if(EmptyChecker.isEmpty(order)){
	    	LogCvt.error("大订单更新失败");
	    	return false;
	    }
//	    LogCvt.info("[锁定订单findAndModify 耗时]--------------------------: " + (System.currentTimeMillis() - startTime));
	    
	    
    	if(order.getIsQrcode() != 1){
    		DBObject whereSub = new BasicDBObject();
    		whereSub.put("order_id", orderId);
    		Object[] objSub = {new BasicDBObject("order_status", OrderStatus.payfailed.getCode()), new BasicDBObject("order_status", OrderStatus.create.getCode())};
    		whereSub.put(QueryOperators.OR, objSub);
    		
    		 DBObject updateSub = new BasicDBObject();
    		 updateSub.put("order_status", OrderStatus.paying.getCode());
    		
    		 long startTime = System.currentTimeMillis();
//    		List<SubOrderMongo> subOrderList = (List<SubOrderMongo>) mongo.findAll(whereSub, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
    		int updateSubOrderFlag = mongo.updateMulti(updateSub, whereSub, MongoTableName.CB_SUBORDER_PRODUCT, "$set");
    		LogCvt.info("[更新所有子订单状态 耗时: ]--------------------------: " + (System.currentTimeMillis() - startTime));    		
    		if(updateSubOrderFlag == -1){
    			LogCvt.error("子订单状态更新失败");
    			return false;
    		}
    		
    		/*if(EmptyChecker.isNotEmpty(subOrderList)){
    			startTime = System.currentTimeMillis();
    			for(SubOrderMongo subOrderMongo : subOrderList){
    				whereSub.put("sub_order_id", subOrderMongo.getSubOrderId());
    				SubOrderMongo subOrder = mongo.findAndModify(update, whereSub, MongoTableName.CB_SUBORDER_PRODUCT, false, SubOrderMongo.class);
    				if(EmptyChecker.isEmpty(subOrder)) {
    					LogCvt.error("修改子订单状态失败");
    					return false;
    				}
    			}
    			LogCvt.info("[锁定子订单findAndModify 耗时]--------------------------: " + (System.currentTimeMillis() - startTime));
    		}else{
    			LogCvt.error("子订单查询接口为空，子订单状态更新失败");
    			return false;
    		}*/
 	    }
    	
//	    LogCvt.info("更新订单成功, 订单内容为：" + JSonUtil.toJSonString(order));
	    return true;
	}
	
	@Override
	public boolean updateOrderOfStatusByOrderId(String orderId, String orderStatus, String orderState) {
	    boolean isSuccess = false;
	    
	    DBObject where = new BasicDBObject();
	    
	    where.put("_id", orderId);
	    where.put("order_status", OrderStatus.paying.getCode());
	    
	    LogCvt.info("更新订单状态，查询订单条件：" + where);
	    
	    DBObject update = new BasicDBObject();

	    if(EmptyChecker.isNotEmpty(orderStatus)) {
	        update.put("$set", new BasicDBObject("order_status", orderStatus));
	    }
	    
	    if(EmptyChecker.isNotEmpty(orderState)) {
	        update.put("$set", new BasicDBObject("state", orderState));
	    }
	    
	    OrderMongo order = mongo.findAndModify(update, where, MongoTableName.CB_ORDER, false, OrderMongo.class);
	    //更新子订单状态
	    if(EmptyChecker.isNotEmpty(order)){
	    	DBObject subValue = new BasicDBObject();
	    	subValue.put("order_status", OrderStatus.paying.getCode());
	    	
	    	DBObject subWhere = new BasicDBObject();
	    	subWhere.put("order_id", orderId);
	    	
	    	int subOrder = mongo.updateMulti(update, new BasicDBObject("order_id", orderId), MongoTableName.CB_SUBORDER_PRODUCT, "$set");
	    	
	    	if(subOrder != -1) {
	    		isSuccess = true;
	    		//修改成功情况下， 把指定Redis-key删除
	    		String redisKey = RedisKeyUtil.cbbank_time_order_key();
	    		String redisValue = RedisKeyUtil.cbbank_time_order_value(order.getClientId(), order.getOrderId());
	    		redis.srem(redisKey, redisValue);
	    		LogCvt.info("修改状态" + orderStatus + ", 移除Redis中[key:"+redisKey+"  value:" + redisValue + "]");
	    	}
	    	LogCvt.info("更新订单：" + isSuccess + "修改条件：" + JSonUtil.toJSonString(where) + ", 订单修改内容为：" + JSonUtil.toJSonString(update));
	    }else{
	    	LogCvt.error("大订单的订单状态修改失败，订单号：" + orderId);
	    }
	    
	    return isSuccess;
	}
	
	@Override
	public MongoPage getPointsOrderListOfPage(PageEntity<OrderQueryCondition> pageCondition) {
		OrderQueryCondition orderQueryCondition = pageCondition.getCondition();
		//查询条件
		DBObject where = new BasicDBObject();
		where.put("client_id", orderQueryCondition.getClientId());
		where.put("member_code", orderQueryCondition.getMemberCode());
		where.put("order_status", OrderStatus.paysuccess.getCode());
        if(StringUtils.equals(orderQueryCondition.getQueryFlag(), "0")){
        	where.put("type", SubOrderType.online_points_org.getCode());
        	/*where.put("products.type", ProductType.onlinePoint.getCode());*/
        }else{
        	where.put("type", SubOrderType.offline_points_org.getCode());
        	/*where.put("products.type", ProductType.dotGift.getCode());*/
        }
		
		MongoPage pageParam = new MongoPage();
		//排序
		/*if(EmptyChecker.isNotEmpty(pageCondition.getOrderByColumn())){
			pageParam.setSort(new Sort(pageCondition.getOrderByColumn(),OrderBy.DESC));
		}*/
		//当前页数
		pageParam.setPageNumber(pageCondition.getPageNumber());
		pageParam.setPageSize(pageCondition.getPageSize());
//		新增（优化分页查询count问题）
		convert(pageParam, pageCondition);
		
		/*pageParam.setSort(new Sort("create_time", OrderBy.DESC));*/
		MongoPage page = mongo.findByPageWithRedis(pageParam, where, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		LogCvt.info("积分兑换分页查询结果：" + JSonUtil.toJSonString(page));
		return page;
	}

	@Override
	public String getVipDiscount(String clientId, Long memberCode) {
		String discountKey = RedisKeyUtil.cbbank_history_vip_client_id_member_code_key(clientId, memberCode);
		return redis.getString(discountKey);
	}
	
	@Override
	public boolean updateSubOrderAfterConsumed(String orderId,String subOrderId, String productId,ProductType productType,String outletId, String deliveryState) {
	    DBObject where = new BasicDBObject();
	    
	    where.put("order_id", orderId);
	    where.put("sub_order_id", subOrderId);
	    where.put("products.product_id", productId);
	    
	    DBObject value = new BasicDBObject();
	    
	    //预售商品，更新商品下的提货状态
	    if(ProductType.presell.equals(productType)){
	    	 value.put("products.$.delivery_state", deliveryState);
	    }
	    
	    //团购商品，更新消费门店
	    if(ProductType.group.equals(productType)){
	    	 value.put("products.$.outlet_id", outletId);
	    }
	    LogCvt.info("券消费后更新子订单商品，查询订单条件：" + where + " 更新值：" + value);
	    SubOrderMongo subOrderMongo = mongo.findAndModify(new BasicDBObject("$set", value), where, MongoTableName.CB_SUBORDER_PRODUCT, false, SubOrderMongo.class);
	    if(EmptyChecker.isEmpty(subOrderMongo)) {
	    	LogCvt.error("券消费后更新子订单商品更新失败");
	        return false;
	    }
	    LogCvt.info("更新成功");
	    return true;
	    
	}
	
	@Override
	public boolean updateSubOrderAfterRefund(String clientId,String orderId,String subOrderId,String refundState) {
	    DBObject where = new BasicDBObject();
	    where.put("client_id", clientId);
	    where.put("order_id", orderId);
	    where.put("sub_order_id", subOrderId);
	    
	    DBObject value = new BasicDBObject();
	    //预售商品，更新商品下的提货状态
	    value.put("refund_state", refundState);
	    
	    LogCvt.info("退款操作后更新子订单退款状态，查询订单条件：" + where + " 更新值：" + value);
	    SubOrderMongo subOrderMongo = mongo.findAndModify(new BasicDBObject("$set", value), where, MongoTableName.CB_SUBORDER_PRODUCT, false, SubOrderMongo.class);
	    if(EmptyChecker.isEmpty(subOrderMongo)) {
	    	LogCvt.info("更新失败");
	        return true;
	    }
	    LogCvt.info("更新成功");
	    return true;
	}
	
	@Override
    public boolean updateProductSellCount(Product product,ProductMonthCount productMonthCount) {
        Boolean result = false;
        SqlSession sqlSession = null;
        OrderMapper orderMapper = null;
        try {
        	sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            orderMapper = sqlSession.getMapper(OrderMapper.class);
            result = true;
            //mysql销售量更新
//            LogCvt.info("[订单模块]-mysql-更新商品销售数量，请求参数："+JSON.toJSONString(product));
//            LogCvt.info("mysql更新销售数量："+product.getSellCount());
            orderMapper.updateProductSellCount(product);
            sqlSession.commit(true);
//            LogCvt.info("[订单模块]-mysql-更新商品销售数量，成功！");
        } catch (Exception e) {
            result = false;
            if(null != sqlSession) {
				sqlSession.rollback(true);
			}
            LogCvt.error("[严重错误]-更新销售量失败，系统异常:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
            
        try {    
        	sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
        	orderMapper = sqlSession.getMapper(OrderMapper.class);
            //mysql月度销售量更新
//            LogCvt.info("[订单模块]-mysql-更新商品月度销售数量，请求参数："+JSON.toJSONString(productMonthCount));
            //仅限团购和名优特惠
            if(StringUtils.equals(ProductType.group.getCode(), product.getType())){
            	orderMapper.insertProductMonthCount(productMonthCount);
            	sqlSession.commit(true);
            	orderMapper.updateProductMonthCount(productMonthCount);
            	sqlSession.commit(true);
            }
//            LogCvt.info("[订单模块]-mysql-更新商品月度销售数量，成功！");
        } catch (Exception e) {
            result = false;
            if(null != sqlSession) {
				sqlSession.rollback(true);
			}
            LogCvt.error("[严重错误]-mysql-更新商品月度销售统计失败，系统异常:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
        
        try {    
            //redis销售量更新
//            LogCvt.info("[订单模块]-redis-更新商品销售数量");
            RedisCommon.updateProductSellCountRedis(product.getClientId(), product.getMerchantId(), product.getProductId(),product.getSellCount());
//            LogCvt.info("[订单模块]-redis-更新商品销售数量，成功！");
        } catch (Exception e) {
            result = false;
            LogCvt.error("[严重错误]-redis-更新销售量失败，系统异常:" + e.getMessage(),e); 
        } 
        
        try {        
            //mongo销售量更新
//            LogCvt.info("[订单模块]-mongo-更新商品销售数量");
            DBObject where = new BasicDBObject();
            if(EmptyChecker.isNotEmpty(product.getClientId())){
                where.put("client_id", product.getClientId());
            }
            /*if(EmptyChecker.isNotEmpty(product.getMerchantId())){
                where.put("merchant_id", product.getMerchantId());
            }*/
            where.put("_id", product.getProductId());
            ProductDetail productDetail = mongo.findOne(where, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
            if(EmptyChecker.isEmpty(productDetail)){
            	LogCvt.error("[严重错误]-mongo-更新销售量查询商品详情为空，查询条件:" + where); 
            }
            DBObject value = new BasicDBObject();
            int sellCount = EmptyChecker.isEmpty(productDetail.getSellCount()) ? 0 : productDetail.getSellCount();
            value.put("sell_count", product.getSellCount() + sellCount);
            mongo.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");
//            LogCvt.info("[订单模块]-mongo-更新商品销售数量，成功！");
            
        } catch (Exception e) {
        	result = false;
            LogCvt.error("[严重错误]-mongo-更新销售量失败，系统异常:" + e.getMessage(),e); 
        }
        
//        showSellCount(product.getClientId(), product.getMerchantId(), product.getProductId());
        
        return result;
    }
	
	@Override
	public boolean updateProductSellCount(List<Product> productList,List<ProductMonthCount> productMonthCountList) {
        Boolean result = false;
        SqlSession sqlSession = null;
        OrderMapper orderMapper = null;
        
        long startTime1 = System.currentTimeMillis();
        try {
        	sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            orderMapper = sqlSession.getMapper(OrderMapper.class);
            result = true;
            //mysql销售量更新
//            LogCvt.info("[订单模块]-mysql-更新商品销售数量，请求参数："+JSON.toJSONString(product));
//            LogCvt.info("mysql更新销售数量："+product.getSellCount());
            if(EmptyChecker.isNotEmpty(productList)){
            	for(Product product : productList){
            		orderMapper.updateProductSellCount(product);
            	}
            }
            sqlSession.commit(true);
//            LogCvt.info("[订单模块]-mysql-更新商品销售数量，成功！");
        } catch (Exception e) {
            result = false;
            if(null != sqlSession) {
				sqlSession.rollback(true);
			}
            LogCvt.error("[严重错误]-更新销售量失败，系统异常:" + e.getMessage(),e); 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
        LogCvt.info("[商品销售量更新-销售量-mysql] 耗时:" + (System.currentTimeMillis() - startTime1));  
         
        long startTime2 = System.currentTimeMillis();
        try {
        	if(sqlSession == null ){
        		sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
        	}
        	if(orderMapper == null ){
        		orderMapper = sqlSession.getMapper(OrderMapper.class);
        	}
            //mysql月度销售量更新
//            LogCvt.info("[订单模块]-mysql-更新商品月度销售数量，请求参数："+JSON.toJSONString(productMonthCount));
            //仅限团购和名优特惠
        	if(EmptyChecker.isNotEmpty(productMonthCountList)){
            	for(ProductMonthCount productMonthCount : productMonthCountList){
            		orderMapper.insertProductMonthCount(productMonthCount);
                	sqlSession.commit(true);
                	orderMapper.updateProductMonthCount(productMonthCount);
            	}
            }
        	sqlSession.commit(true);
//            LogCvt.info("[订单模块]-mysql-更新商品月度销售数量，成功！");
        } catch (Exception e) {
            result = false;
            if(null != sqlSession) {
				sqlSession.rollback(true);
			}
            LogCvt.error("[严重错误]-mysql-更新商品月度销售统计失败，系统异常:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
        LogCvt.info("[商品销售量更新-月度销量-mysql] 耗时:" + (System.currentTimeMillis() - startTime2));  
        
        long startTime3 = System.currentTimeMillis();
        try {    
            //redis销售量更新
//            LogCvt.info("[订单模块]-redis-更新商品销售数量");
        	if(EmptyChecker.isNotEmpty(productList)){
            	for(Product product : productList){
            		RedisCommon.updateProductSellCountRedis(product.getClientId(), product.getMerchantId(), product.getProductId(),product.getSellCount());
            	}
            }
//            LogCvt.info("[订单模块]-redis-更新商品销售数量，成功！");
        } catch (Exception e) {
            result = false;
            LogCvt.error("[严重错误]-redis-更新销售量失败，系统异常:" + e.getMessage(),e); 
        } 
        LogCvt.info("[商品销售量更新-商品销量-redis] 耗时:" + (System.currentTimeMillis() - startTime3));  
        
        long startTime4 = System.currentTimeMillis();
        try {        
            //mongo销售量更新
//            LogCvt.info("[订单模块]-mongo-更新商品销售数量");
        	
        	if(EmptyChecker.isNotEmpty(productList)){
            	for(Product product : productList){
            		DBObject where = new BasicDBObject();
            		where.put("_id", product.getProductId());
                    if(EmptyChecker.isNotEmpty(product.getClientId())){
                        where.put("client_id", product.getClientId());
                    }
                    /*if(EmptyChecker.isNotEmpty(product.getMerchantId())){
                        where.put("merchant_id", product.getMerchantId());
                    }*/
//                    ProductDetail productDetail = mongo.findOne(where, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
//                    if(EmptyChecker.isEmpty(productDetail)){
//                    	LogCvt.error("[严重错误]-mongo-更新销售量查询商品详情为空，查询条件:" + where); 
//                    }
                    DBObject value = new BasicDBObject();
//                    int sellCount = EmptyChecker.isEmpty(productDetail.getSellCount()) ? 0 : productDetail.getSellCount();
//                    DBObject inc = new BasicDBObject();
//                    inc.put("sell_count", product.getSellCount());
                    value.put("sell_count", product.getSellCount());
                    mongo.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$inc");
//                    LogCvt.info("[订单模块]-mongo-更新商品销售数量，成功！");
            	}
            }
        } catch (Exception e) {
        	result = false;
            LogCvt.error("[严重错误]-mongo-更新销售量失败，系统异常:" + e.getMessage(),e); 
        }
        LogCvt.info("[商品销售量更新-商品销量-redis] 耗时:" + (System.currentTimeMillis() - startTime4));  
        
//        showSellCount(product.getClientId(), product.getMerchantId(), product.getProductId());
        
        return result;
	}
	
	@Override
	public boolean updateSeckillProductSellCount(List<ProductSeckill> productList,List<ProductMonthCount> productMonthCountList) {
		Boolean result = false;
		SqlSession sqlSession = null;
		OrderMapper orderMapper = null;
		/**
		 * 1.更新mysql
		 */
		long startTime1 = System.currentTimeMillis();
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orderMapper = sqlSession.getMapper(OrderMapper.class);
			result = true;
			//mysql销售量更新
			if(EmptyChecker.isNotEmpty(productList)){
				for(ProductSeckill product : productList){
					LogCvt.info("[秒杀订单]-mysql更新销售数量：{product_id:"+product.getProductId()+",true_buyer_number:"+product.getTrueBuyerNumber()+"}");
					orderMapper.updateSeckillProductSellCount(product);
				}
			}
			sqlSession.commit(true);
            LogCvt.info("[秒杀模块]-mysql更新销售数量，成功！");
		} catch (Exception e) {
			result = false;
			if(null != sqlSession) {
				sqlSession.rollback(true);
			}
			LogCvt.error("[秒杀订单]-【严重错误】-更新销售量失败，系统异常:" + e.getMessage(),e); 
			if(null != sqlSession) {
				sqlSession.close(); 
			}
		}
		LogCvt.info("[商品销售量更新-销售量-mysql] 耗时:" + (System.currentTimeMillis() - startTime1));  
		
		/**
		 * 2.更新mysql月度销售统计
		 */
		long startTime2 = System.currentTimeMillis();
		try {
			if(sqlSession == null ){
        		sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
        	}
        	if(orderMapper == null ){
        		orderMapper = sqlSession.getMapper(OrderMapper.class);
        	}
			//mysql月度销售量更新，仅限团购
			if(EmptyChecker.isNotEmpty(productMonthCountList)){
				for(ProductMonthCount productMonthCount : productMonthCountList){
					LogCvt.info("[秒杀订单]-mysql更新销售数量：{product_id:"+productMonthCount.getProductId()+",sell_money:"+productMonthCount.getSellMoney()+",sell_count:"+productMonthCount.getSellCount()+"}");
					orderMapper.insertProductMonthCount(productMonthCount);
					sqlSession.commit(true);
					orderMapper.updateProductMonthCount(productMonthCount);
				}
			}
			sqlSession.commit(true);
            LogCvt.info("[秒杀订单]-mysql-更新商品月度销售数量，成功！");
		} catch (Exception e) {
			result = false;
			if(null != sqlSession) {
				sqlSession.rollback(true);
			}
			LogCvt.error("[严重错误]-mysql-更新商品月度销售统计失败，系统异常:" + e.getMessage(),e); 
		} finally { 
			if(null != sqlSession) {
				sqlSession.close(); 
			}
		}
		LogCvt.info("[秒杀商品销售量更新-月度销量-mysql] 耗时:" + (System.currentTimeMillis() - startTime2));  
		
		/**
		 * 3.更新redis
		 */
		long startTime3 = System.currentTimeMillis();
		try {    
			//redis销售量更新
			if(EmptyChecker.isNotEmpty(productList)){
				for(ProductSeckill product : productList){
					LogCvt.info("[秒杀订单]-mysql更新销售数量：{key:"+RedisKeyUtil.cbbank_seckill_product_soldcnt_client_id_product_id(product.getClientId(), product.getProductId())+",true_buyer_number:"+product.getTrueBuyerNumber()+"}");
					RedisCommon.updateSeckillProductSellCountRedis(product.getClientId(), product.getMerchantId(), product.getProductId(),product.getTrueBuyerNumber());
				}
			}
            LogCvt.info("[秒杀订单]-redis-更新商品销售数量，成功！");
		} catch (Exception e) {
			result = false;
			LogCvt.error("[严重错误]-redis-更新销售量失败，系统异常:" + e.getMessage(),e); 
		} 
		LogCvt.info("[秒杀商品销售量更新-商品销量-redis] 耗时:" + (System.currentTimeMillis() - startTime3));  
		
		return result;
	}
	
	private void showSellCount(String clientId,String merchantId,String productId){
		/**销售量查询*/
		SqlSession sqlSession = null;
		try {
		    //redis销售数量
		    int redisSellCount = RedisCommon.getProductSellCountRedis(clientId, merchantId, productId);
		    if(redisSellCount==-1){
		    	LogCvt.error("[销售数量更新]-销售数量查询，redis为空，key：" + RedisKeyUtil.cbbank_product_sellcount_client_id_merchant_id_product_id(clientId,merchantId,productId));
		    }
		    
		    //mysql销售数量
		    sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		    OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
		    
		    Product product = new Product();
		    product.setClientId(clientId);
		    product.setMerchantId(merchantId);
		    product.setProductId(productId);
		    Integer mysqlSellCount = orderMapper.getProductSellCount(product);
		    if(EmptyChecker.isEmpty(mysqlSellCount)){
		    	LogCvt.error("[销售数量更新]-销售数量查询，mysql为空，查询条件：clientId:"+clientId+", merchantId:" + merchantId + ", productId:" + productId);
		    }
		    
		    //mongo销售数量
		    DBObject where = new BasicDBObject();
		    if(EmptyChecker.isNotEmpty(clientId)){
		        where.put("client_id", clientId);
		    }
		    if(EmptyChecker.isNotEmpty(merchantId)){
		        where.put("merchant_id", merchantId);
		    }
		    where.put("_id", productId);
		    ProductDetail productSellCount = mongo.findOne(where, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
		    if(EmptyChecker.isEmpty(productSellCount)){
		    	LogCvt.error("[销售数量更新]-销售数量查询，Mongo为空，查询条件：" + where);
		    }
		    Integer mongoSellCount = productSellCount.getSellCount();
			LogCvt.error("[销售数量更新]-销售数量更新结果，redis:"+redisSellCount +", mysql:" + mysqlSellCount + ", mongo:" + mongoSellCount);
			
		} catch (Exception e) {
			if(null != sqlSession) {
				sqlSession.rollback(true);
			}
            LogCvt.error("查询销售量失败，系统异常:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
	}
	
	@Override
	public boolean updateProductStore(List<Product> list) {
		//库存缓存从redis更新至mysql
//		LogCvt.info("mysql：商品库存缓存从redis同步至mysql");
		boolean result = true;
		
//		long st = System.currentTimeMillis();
		long st1 = 0l;
		
		OrderMapper orderMapper = null;
		SqlSession sqlSession = null;
		try {
			st1 = System.currentTimeMillis();
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			LogCvt.info("mysql-sqlSession 获取连接，耗时：" + (System.currentTimeMillis() - st1)+"）毫秒");
			orderMapper = sqlSession.getMapper(OrderMapper.class);
			for(Product product : list){
//				LogCvt.info("mysql更新商品库存数：[product_id:" + product.getProductId()+" 数量：" + product.getStore() +"]");
				Boolean flag = orderMapper.updateProductStore(product);
				if(!flag){
					throw new FroadBusinessException(ResultCode.failed.getCode(),"商品库存更新失败");
				}
			}
			sqlSession.commit(true);
		}catch(Exception e){
			if(null != sqlSession) {
				sqlSession.rollback(true);
			}
			LogCvt.error("更新商品库存失败，系统异常：" + e.getMessage(),e);
			result = false;
		}finally {
			long st2 = System.currentTimeMillis();
			if(null != sqlSession) {
                sqlSession.close(); 
            }
			LogCvt.info("mysql-sqlSession 关闭连接，耗时：" + (System.currentTimeMillis() - st2)+"）毫秒");
			LogCvt.info("mysql-sqlSession 保持连接，耗时：" + (System.currentTimeMillis() - st1)+"）毫秒");
		}
//		LogCvt.info("【mysql 更新库存  OrderLogicImpl.updateProductStore 】-耗时（"+(System.currentTimeMillis() - st)+"）毫秒");
		return result;
	}
	
	@Override
	public SubOrderMongo getPointsOrderDetail(OrderQueryCondition orderQueryCondition) {
		DBObject query = new BasicDBObject();
		query.put("client_id", orderQueryCondition.getClientId());
		query.put("member_code", orderQueryCondition.getMemberCode());
		query.put("order_id", orderQueryCondition.getOrderId());
		return mongo.findOne(query, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	}

	@Override
	public MerchantDetail getMerchantDetail(String clientId, String merchantId) {
		DBObject query = new BasicDBObject();
		query.put("client_id", clientId);
		query.put("_id", merchantId);
		return mongo.findOne(query, MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class);
	}
	
	@Override
	public ProductMongo getSubOrderProductDetail(String clientId,  String orderId,String subOrderId, String productId) {
		//查询条件
		List<DBObject> pipeLine = new ArrayList<DBObject>();
		DBObject where = new BasicDBObject();
		where.put("order_id", orderId);
		where.put("sub_order_id", subOrderId);
		where.put("client_id", clientId);
        pipeLine.add(new BasicDBObject("$match", where));
        pipeLine.add(new BasicDBObject("$unwind", "$products"));
        
        DBObject where2 = new BasicDBObject();
        where2.put("products.product_id", productId);
        pipeLine.add(new BasicDBObject("$match", where2));
        Cursor cursor = mongo.getReadDBCollection(MongoTableName.CB_SUBORDER_PRODUCT).aggregate(pipeLine, AggregationOptions.builder().build());
        DBObject dbObj = new CursorHandleImpl().handle(cursor);
        if(dbObj != null){
        	 String json = JSonUtil.toJSonString(dbObj.get("products"));
        	 LogCvt.info("子订单商品详情查询结果：" + json);
             return JSonUtil.toObject(json, ProductMongo.class);
        }
       return null;
	}
	
	@Override
	public Map<String, String> getProduct(String clientId, String merchantId, String productId) {
        String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(clientId, merchantId, productId);
        RedisManager redis = new RedisManager();
        return redis.getMap(key);
    }
	
	@Override
	public boolean updateOrderForSeckill(OrderMongo orderMongo) {
		DBObject where = new BasicDBObject();
		where.put("_id", orderMongo.getOrderId());
	    DBObject value = new BasicDBObject();
	    value.put("phone", orderMongo.getPhone());
	    value.put("deliver_id", orderMongo.getDeliverId());
	    value.put("recv_id", orderMongo.getRecvId());
	    LogCvt.info("秒杀订单-更新大订单配送信息，查询订单条件：" + where + " 更新值：" + value);
	    int result = mongo.update(value, where, MongoTableName.CB_ORDER, "$set");
		if(result == -1){
			LogCvt.error("大订单信息修改失败");
			return false;
		}
	    return true;
	}

	@Override
	public boolean updateSubOrderForSeckill(SubOrderMongo subOrderMongo) {
		DBObject where = new BasicDBObject();
		where.put("order_id", subOrderMongo.getOrderId());
		
	    DBObject value = new BasicDBObject();
	    if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts().get(0).getOrgCode())){
	    	 value.put("products.$.org_code", subOrderMongo.getProducts().get(0).getOrgCode());
	    }
	    if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts().get(0).getOrgName())){
	    	value.put("products.$.org_name", subOrderMongo.getProducts().get(0).getOrgCode());
	    }
	    if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts().get(0).getDeliveryOption())){
	    	value.put("products.$.delivery_option", subOrderMongo.getProducts().get(0).getOrgCode());
	    }
	    
	    LogCvt.info("秒杀订单-更新子订单配送信息，查询订单条件：" + where + " 更新值：" + value);
	    SubOrderMongo subOrder = mongo.findAndModify(new BasicDBObject("$set", value), where, MongoTableName.CB_SUBORDER_PRODUCT, false, SubOrderMongo.class);
	    if(EmptyChecker.isEmpty(subOrder)) {
	    	LogCvt.info("更新失败");
	        return true;
	    }
	    LogCvt.info("更新成功");
	    return true;
	}

	/**
	 * 优化分页查询
	 * @param mongoPage 分页查询Mongo对象
	 * @param page 分页参数（如：页大小，当前页等）
	 * @return
	 *<pre>
	 *
	 * @version V1.0 修改人：Arron 日期：2015年5月12日 下午7:51:09 
	 *
	 *</pre>
	 */
	private MongoPage convert(MongoPage mongoPage, PageEntity<?> page) {
	    mongoPage.setLastPageNumber(page.getLastPageNumber());
        mongoPage.setLastRecordTime(page.getLastRecordTime());
        mongoPage.setFirstRecordTime(page.getFirstRecordTime());
        return mongoPage;
	}
	
	@Override
	public MongoPage queryGivePointsProductByBoss(
			PageEntity<OrderQueryByBossCondition> pageCondition) {
		OrderQueryByBossCondition condition = pageCondition.getCondition();
		    
	    BasicDBObject where = new BasicDBObject();
	    if(EmptyChecker.isNotEmpty(condition.getClientId())) {
	    	where.put("client_id", condition.getClientId());
	    }
	    if(EmptyChecker.isNotEmpty(condition.getOrderId())) {
	        where.put("order_id", condition.getOrderId());
	    }
	    /*//支付方式
	    if(EmptyChecker.isNotEmpty(condition.getPaymentMethod())) {
	        where.put("payment_method", condition.getPaymentMethod());
	    }*/
	    
	    //订单状态
	    if(EmptyChecker.isNotEmpty(condition.getOrderStatus())) {
	        where.put("order_status", condition.getOrderStatus());
	    }
	    
	    //有送积分的订单
	    where.put("is_give_point", 1);
	    
	    //开始时间、结束时间
	    if(EmptyChecker.isNotEmpty(condition.getStartTime()) && EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getStartTime() > 0 && condition.getEndTime() > 0 ){
			BasicDBList values = new BasicDBList();
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime())));
			values.add(new BasicDBObject("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime())));
			where.put(QueryOperators.AND,values);
		}else if(EmptyChecker.isNotEmpty(condition.getStartTime()) && condition.getStartTime() > 0){
			where.put("create_time",new BasicDBObject(QueryOperators.GTE,condition.getStartTime()));
        }else if(EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getEndTime() > 0){
        	where.put("create_time",new BasicDBObject(QueryOperators.LTE,condition.getEndTime()));
        }

	    //设置当前页和页大小	    
	    MongoPage page = new MongoPage(pageCondition.getPageNumber(), pageCondition.getPageSize());
	    
	    convert(page, pageCondition);
	    
	    //排序
        /*if(EmptyChecker.isNotEmpty(pageCondition.getOrderByColumn())){
            page.setSort(new Sort(pageCondition.getOrderByColumn(),OrderBy.DESC));
        }*/
        
        LogCvt.info("MongoDb表：" + MongoTableName.CB_SUBORDER_PRODUCT + ", 条件：" + JSonUtil.toJSonString(where));
        long st = System.currentTimeMillis();
	    mongo.findByPageWithRedis(page, where, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	    LogCvt.info("[Boss查询]-赠送积分订单分页查询-耗时：（" + (System.currentTimeMillis() - st) + "）毫秒");
	    
	    return page;
	}
	
	@Override
	public SubOrderMongo getSubOrderByClient(String clientId, String orderId,
			String subOrderId) {
		DBObject query = new BasicDBObject();
		query.put(FieldMapping.CLIENT_ID.getMongoField(), clientId);
		if (EmptyChecker.isNotEmpty(orderId)){
			query.put(FieldMapping.ORDER_ID.getMongoField(), orderId);
		}
		query.put(FieldMapping.SUB_ORDER_ID.getMongoField(), subOrderId);
		LogCvt.info("查询子订单数据，条件：" + query.toString());
		return mongo.findOne(query, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	}
	
	@Override
	public void updateGivePointStatus(String clientId,String orderId,boolean isSuccess){
		LogCvt.info("支付完成后赠送积分，更新子订单商品送积分状态，参数：{clientId:"+clientId+",orderId:"+orderId+",isSuccess:"+isSuccess+"}");
		List<SubOrderMongo> subOrderList = getSubOrderListByOrderId(clientId,orderId);
		if(EmptyChecker.isNotEmpty(subOrderList)){
			for(SubOrderMongo subOrderMongo : subOrderList){
				if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
					//子订单是否有赠送积分
					for(ProductMongo productMongo : subOrderMongo.getProducts()){
						//如果有赠送积分
						if(productMongo.getPoints() > 0){
							if(isSuccess){
								productMongo.setGivePointState(GivePointState.GIVE_SUCCESS.getCode());
								RedisCommon.updateUserGivePointsRedis(clientId,subOrderMongo.getMemberCode(),productMongo.getProductId(),true);
							}else{
								productMongo.setGivePointState(GivePointState.GIVE_FAIL.getCode());
								RedisCommon.updateUserGivePointsRedis(clientId,subOrderMongo.getMemberCode(),productMongo.getProductId(),false);
							}
						}
					}
				}
			}
			updateSuborderProdcutGivePointState(clientId,orderId,null,isSuccess,false);
		}
	}
	
	
	@Override
	public void updateRefundPointStatus(String clientId,String subOrderId,String productId,boolean isSuccess){
		LogCvt.info("退款后退赠送积分，更新子订单商品送积分状态，参数：{clientId:"+clientId+",subOrderId:"+subOrderId+",isSuccess:"+isSuccess+"}");
		SubOrderMongo subOrderMongo = getSubOrderBySubOrderId(clientId, subOrderId);
		if(EmptyChecker.isNotEmpty(subOrderMongo)){
			if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
				//子订单是否有赠送积分
				for(ProductMongo productMongo : subOrderMongo.getProducts()){
					//如果有赠送积分
					if(productMongo.getPoints() > 0){
						if(isSuccess){
							productMongo.setGivePointState(GivePointState.REFUND_SUCCESS.getCode());
							RedisCommon.updateUserGivePointsRedis(clientId,subOrderMongo.getMemberCode(),productMongo.getProductId(),false);
						}else{
							productMongo.setGivePointState(GivePointState.REFUND_FAIL.getCode());
						}
					}
				}
			}
			updateSuborderProdcutGivePointState(clientId,subOrderMongo.getOrderId(),subOrderId,isSuccess,true);
		}
	}
	
	/**
	 * 更新子订单商品送积分状态
	 * @param clientId
	 * @param orderId
	 * @param isSuccess 是否赠送    true:退款   false:非退款
	 * @param isRefund 是否退款    true:退款   false:非退款
	 */
	public void updateSuborderProdcutGivePointState(String clientId,String orderId,String subOrderId,boolean isSuccess,boolean isRefund){
		//更新子订单商品的送积分状态
		DBObject where = new BasicDBObject();
		where.put("client_id", clientId);
		where.put("order_id", orderId);
		if(EmptyChecker.isNotEmpty(subOrderId)){
			where.put("sub_order_id", subOrderId);
		}
		
	    DBObject value = new BasicDBObject();
	    if(isSuccess){
	    	if(isRefund){
	    		where.put("products.give_point_state", GivePointState.GIVE_SUCCESS.getCode());
	    		value.put("products.$.give_point_state", GivePointState.REFUND_SUCCESS.getCode());
	    	}else{
	    		where.put("products.give_point_state", GivePointState.NO_GIVE.getCode());
	    		value.put("products.$.give_point_state", GivePointState.GIVE_SUCCESS.getCode());
	    	}
	    }else{
	    	if(isRefund){
	    		where.put("products.give_point_state", GivePointState.GIVE_SUCCESS.getCode());
	    		value.put("products.$.give_point_state", GivePointState.REFUND_FAIL.getCode());
	    	}else{
	    		where.put("products.give_point_state", GivePointState.NO_GIVE.getCode());
	    		value.put("products.$.give_point_state", GivePointState.GIVE_FAIL.getCode());
	    	}
	    }
		int updateSubOrderFlag = mongo.updateMulti(value, where, MongoTableName.CB_SUBORDER_PRODUCT, "$set");
		if(updateSubOrderFlag<0){
			LogCvt.error("更新子订单商品送积分状态，失败！参数：{clientId:"+clientId+",orderId:"+orderId+",(赠送是否成功)isSuccess:"+isSuccess+",(是否为退款)isRefund："+isRefund+"}");
		}else{
			LogCvt.info("更新子订单商品送积分状态，成功！");
		}
	}
	
	@Override
	public void updateUnitProdcutCutPoint(SubOrderMongo subOrderMongo){
		LogCvt.info("更新单位商品抵扣积分/现金金额");
		//更新子订单商品的送积分状态
		DBObject where = new BasicDBObject();
		where.put("client_id", subOrderMongo.getClientId());
		where.put("order_id", subOrderMongo.getOrderId());
		where.put("sub_order_id", subOrderMongo.getSubOrderId());
		
	    if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
	    	for(ProductMongo product : subOrderMongo.getProducts()){
	    		DBObject value = new BasicDBObject();
	    		where.put("products.product_id", product.getProductId());
	    		if(EmptyChecker.isNotEmpty(product.getPointArray())){
	    			value.put("products.$.point_array", product.getPointArray());
	    		}
	    		if(EmptyChecker.isNotEmpty(product.getVipPointArray())){
	    			value.put("products.$.vip_point_array", product.getVipPointArray());
	    		}
	    		if(EmptyChecker.isNotEmpty(product.getTotalPoint())){
	    			value.put("products.$.total_point", product.getTotalPoint());
	    		}
	    		if(EmptyChecker.isNotEmpty(product.getCashArray())){
	    			value.put("products.$.cash_array", product.getCashArray());
	    		}
	    		if(EmptyChecker.isNotEmpty(product.getVipCashArray())){
	    			value.put("products.$.vip_cash_array", product.getVipCashArray());
	    		}
	    		if(EmptyChecker.isNotEmpty(product.getTotalCash())){
	    			value.put("products.$.total_cash", product.getTotalCash());
	    		}
	    		
	    		if(EmptyChecker.isNotEmpty(product.getCutMoneyArray())){
	    			value.put("products.$.cut_money_array", product.getCutMoneyArray());
	    		}
	    		if(EmptyChecker.isNotEmpty(product.getVipCutMoneyArray())){
	    			value.put("products.$.vip_cut_money_array", product.getVipCutMoneyArray());
	    		}
	    		if(EmptyChecker.isNotEmpty(product.getTotalCutMoney())){
	    			value.put("products.$.total_cut_money", product.getTotalCutMoney());
	    		}
	    		if(EmptyChecker.isEmpty(value)){
	    			continue;
	    		}
	    		LogCvt.info("拆分后更新子订单商品："+JSON.toJSONString(value));
	    		int updateSubOrderFlag = mongo.update(value, where, MongoTableName.CB_SUBORDER_PRODUCT, "$set",true,false);
				if (updateSubOrderFlag < 0) {
					LogCvt.error("更新子订单商品抵扣积分，失败！参数：{subOrderId:"+subOrderMongo.getSubOrderId()+",productId:"+product.getProductId()+"}");
	    		}
		    }
	    }
	}
	
	/**
	 * 删除单位商品抵扣积分
	 * @param subOrderMongo
	 */
	@Override
	public void deleteUnitProdcutCutPoint(SubOrderMongo subOrderMongo){
		//更新子订单商品的送积分状态
		DBObject where = new BasicDBObject();
		where.put("client_id", subOrderMongo.getClientId());
		where.put("order_id", subOrderMongo.getOrderId());
		where.put("sub_order_id", subOrderMongo.getSubOrderId());
		
	    if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
	    	for(ProductMongo product : subOrderMongo.getProducts()){
	    		DBObject value = new BasicDBObject();
	    		where.put("products.product_id", product.getProductId());
	    		if(EmptyChecker.isNotEmpty(product.getPointArray())){
	    			value.put("products.$.point_array",1);
	    		}
	    		if(EmptyChecker.isNotEmpty(product.getVipPointArray())){
	    			value.put("products.$.vip_point_array", 1);
	    		}
	    		if(EmptyChecker.isNotEmpty(product.getTotalPoint())){
	    			value.put("products.$.total_point", 1);
	    		}
	    		if(EmptyChecker.isNotEmpty(product.getCashArray())){
	    			value.put("products.$.cash_array", 1);
	    		}
	    		if(EmptyChecker.isNotEmpty(product.getVipCashArray())){
	    			value.put("products.$.vip_cash_array", 1);
	    		}
	    		if(EmptyChecker.isNotEmpty(product.getTotalCash())){
	    			value.put("products.$.total_cash", 1);
	    		}
	    		
	    		if(EmptyChecker.isNotEmpty(product.getCutMoneyArray())){
	    			value.put("products.$.cut_money_array", 1);
	    		}
	    		if(EmptyChecker.isNotEmpty(product.getVipCutMoneyArray())){
	    			value.put("products.$.vip_cut_money_array", 1);
	    		}
	    		if(EmptyChecker.isNotEmpty(product.getTotalCutMoney())){
	    			value.put("products.$.total_cut_money", 1);
	    		}
	    		if(EmptyChecker.isNotEmpty(value)){
	    			int updateSubOrderFlag = mongo.update(value, where, MongoTableName.CB_SUBORDER_PRODUCT, "$unset",true,false);
					if (updateSubOrderFlag < 0) {
						LogCvt.error("删除子订单商品实付积分和现金，失败！参数：{subOrderId:"+subOrderMongo.getSubOrderId()+",productId:"+product.getProductId()+"}");
		    		}
	    		}
		    }
	    }
	}
	

	@Override
	public Page<PresellShip> queryPresellShipByPage(Page<PresellShip> page, PresellShip ship) {

		List<PresellShip> result = new ArrayList<PresellShip>();
		SqlSession sqlSession = null;
		OrderMapper orderMapper = null;
		
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orderMapper = sqlSession.getMapper(OrderMapper.class);

			result = orderMapper.queryPresellShipByPage(page, ship); 
			page.setResultsContent(result);
			
		} catch (Exception e) { 
			LogCvt.error("分页查询PresellShip失败，系统异常:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();
		} 
		
		return page; 
	}
	
	@Override
	public Map<Long,String> findMerchantUserNameByIdList(List<Long> list) {
		long start = System.currentTimeMillis();
		SqlSession sqlSession = null;
		OrderMapper orderMapper = null;
		Map<Long,String> resultMap = new HashMap<Long,String>();
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orderMapper = sqlSession.getMapper(OrderMapper.class);

			List<Map<String,Object>> resultList = orderMapper.findMerchantUserNameByIdList(list);
			if(EmptyChecker.isNotEmpty(resultList)){
				for(Map<String,Object> map : resultList){
					resultMap.put((Long)map.get("id"), (String) map.get("username"));
				}
			}
		} catch (Exception e) { 
			LogCvt.error("orderSupport.findMerchantUserNameByIdList失败，系统异常:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();
		} 
		long end = System.currentTimeMillis();
		LogCvt.info("通过商户ID查询商户用户，商户ID数："+list.size()+"，查询结果数："+resultMap.size()+"，总耗时："+(end-start)+"ms");
		return resultMap;
	}
	
	@Override
	public String findMerchantUserNameById(Long id) {
		long start = System.currentTimeMillis();
		SqlSession sqlSession = null;
		OrderMapper orderMapper = null;
		String result = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orderMapper = sqlSession.getMapper(OrderMapper.class);

			result = orderMapper.findMerchantUserNameById(id);
		} catch (Exception e) { 
			LogCvt.error("orderSupport.findMerchantUserNameById失败，系统异常:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();
		} 
		long end = System.currentTimeMillis();
		LogCvt.info("通过商户ID查询商户用户，商户Id："+id+"，查询结果："+result+"，耗时："+(end-start)+"ms");
		return result;
	}
	
	@Override
	public Map<Long,String> findAreaNameById(List<Long> list) {
		SqlSession sqlSession = null;
		OrderMapper orderMapper = null;
		Map<Long,String> resultMap = new HashMap<Long,String>();
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orderMapper = sqlSession.getMapper(OrderMapper.class);
			List<Map<String,Object>> resultList = null;
			if(EmptyChecker.isNotEmpty(list)){
				resultList = orderMapper.findAreaNameById(list);
			}else{
				LogCvt.error("查询地区列表参数错误(接口：orderSupport.findAreaNameById)");
			}
			if(EmptyChecker.isNotEmpty(resultList)){
				for(Map<String,Object> map : resultList){
					resultMap.put((Long)map.get("id"), (String) map.get("name"));
				}
			}
		} catch (Exception e) { 
			LogCvt.error("orderSupport.findAreaNameById失败，系统异常:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();
		} 
		return resultMap;
	}
	
	@Override
	public boolean updateOrder(OrderMongo order) {
		DBObject where = new BasicDBObject();
		if(EmptyChecker.isNotEmpty(order.getClientId())){
			where.put("client_id", order.getClientId());
		}
		where.put("_id", order.getOrderId());
		
		DBObject value = new BasicDBObject();
		if(EmptyChecker.isNotEmpty(order.getMarketId())){
			value.put("market_id", order.getMarketId());
		}
		
		if(EmptyChecker.isNotEmpty(order.getBankPoints())){
			value.put("bank_points", order.getBankPoints());
		}
		
		if(EmptyChecker.isNotEmpty(order.getFftPoints())){
			value.put("fft_points", order.getFftPoints());
		}
		
		/*if(EmptyChecker.isNotEmpty(order.getRealPrice())){
			value.put("real_price", order.getRealPrice());
		}*/
		
		int updateFlag = mongo.update(value, where, MongoTableName.CB_ORDER, "$set",true,false);
		return updateFlag > 0;
	}
	
	@Override
	public boolean updateOrderForGive(String clientId,String orderId,Integer giveMoney,Integer givePoints) {
		if(EmptyChecker.isEmpty(clientId) || EmptyChecker.isEmpty(orderId) || (giveMoney == 0 && givePoints==0)){
			OrderLogger.error("支付模块", "更新订单上的赠送积分和赠送金额，请求参数错误！", "请求参数：", new Object[]{"clientId",clientId,"orderId",orderId,"giveMoney",giveMoney,"givePoints",givePoints});
			return false;
		}
		DBObject where = new BasicDBObject();
		where.put("client_id", clientId);
		where.put("_id", orderId);
		
		DBObject value = new BasicDBObject();
		if(EmptyChecker.isNotEmpty(giveMoney) && giveMoney > 0){
			value.put("give_money", giveMoney);
		}
		
		if(EmptyChecker.isNotEmpty(givePoints) && givePoints > 0){
			value.put("give_points", givePoints);
		}
		
		int updateFlag = mongo.update(value, where, MongoTableName.CB_ORDER, "$set",true,false);
		OrderLogger.info("支付模块", "更新订单上的赠送积分和赠送金额", "请求参数：", new Object[]{"clientId",clientId,"orderId",orderId,"giveMoney",giveMoney,"givePoints",givePoints});
		return updateFlag > 0;
	}
	
	public static void main(String[] args){
		PropertiesUtil.load();
//		System.setProperty("CONFIG_PATH", "./config/");
		
//	    DBObject where = new BasicDBObject();
//	    where.put("order_id", "ab");
//        where.put("sub_order_id", "bc");
//        where.put("order_status", OrderStatus.paysuccess.getCode());

//        Object [] rs = {SubOrderRefundState.REFUND_SUCCESS.getCode(), SubOrderRefundState.REFUND_PROCESSING.getCode()};
//        where.put("refund_state", new BasicDBObject(QueryOperators.NIN, rs)); // 不为退款完成可以发货
//        
//        System.out.println(JSonUtil.toJSonString(where));

	    /*
        Object [] rs = {SubOrderRefundState.REFUND_SUCCESS.getCode(), SubOrderRefundState.REFUND_PROCESSING.getCode()};
        where.put("refund_state", new BasicDBObject(QueryOperators.NIN, rs)); // 不为退款完成可以发货
        */
//        System.out.println(JSonUtil.toJSonString(where));
//        Object [] rs = {SubOrderRefundState.REFUND_SUCCESS.getCode(), SubOrderRefundState.REFUND_PROCESSING.getCode()};
//        where.put("refund_state", new BasicDBObject(QueryOperators.NIN, rs)); // 不为退款完成可以发货
//        
//        System.out.println(JSonUtil.toJSonString(where));
	    
	    System.setProperty("CONFIG_PATH", "./config/");
	    OrderSupport support = new OrderSupportImpl();
//	    RecvInfo info =  support.getRecvInfo("anhui",50000205821L,"05C2F8B1000E");
	    
	   /* Map<String,String> recvMap = new HashMap<String,String>();
	    List<SubOrderMongo> list = support.getSubOrderListByOrderId("anhui","05C2F6420001");
	    Map<String,RecvInfo> map = support.getRecvInfoMap(list);
	    System.out.println(JSON.toJSONString(map,true));*/
	    
//	    
//	    
	    OrderSupportImpl imp = new OrderSupportImpl();
	    
	   /* List<RecvInfo> list = imp.queryRecvInfos("17785315231");
	    for(RecvInfo info : list) {
	        System.out.println(info.getRecvId());
	    }
	    System.out.println(JSonUtil.toJSonString(list));*/
	    
//	    DBObject obj = new BasicDBObject();
	    
//	    obj.put("order_id", "/ab/");
	    
//	    Pattern pattern = Pattern.compile("/ab/", Pattern.CASE_INSENSITIVE);
	    
	    /*Pattern pattern2 = Pattern.compile("^.*阿里.*$", Pattern.CASE_INSENSITIVE);
	    
	    obj.put("merchant_name", pattern2);
	    
	    System.out.println(obj);
	    
	    System.out.println(JSonUtil.toJSonString(obj));
	    
	    MongoManager m = new MongoManager();
	    
	    List<?> list = m.findAll(obj, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
	    
	    System.out.println(JSonUtil.toJSonString(list));*/
	    
//	    if(EmptyChecker.isNotEmpty(condition.getOrderId())) {
////          Pattern pattern = Pattern.compile("^.*" + condition.getOrderId()+ ".*$", Pattern.CASE_INSENSITIVE); 
//          where.put("order_id", "/" + condition.getOrderId() + "/");
//      }
	    
//	    
//	    DBObject where = new BasicDBObject();
//        
//        where.put("order_id", "123");
//        
//        Object [] obj = {new BasicDBObject("status", OrderStatus.payfailed), new BasicDBObject("status", OrderStatus.create)};
//        
//        where.put(QueryOperators.OR, obj);
//        
//        System.out.println(JSonUtil.toJSonString(where));
//	    
//		OrderQueryCondition condition = new OrderQueryCondition();
//		condition.setEndTime(new Date().getTime());
//		condition.setClientId(10L);
//		condition.setMemberCode(10000L);
//		OrderSupportImpl support = new OrderSupportImpl();
//		List<OrderSummaryVo> list = support.getOrderSummary(condition);
		
//		OrderQueryCondition condition = new OrderQueryCondition();
//		condition.setEndTime(new Date().getTime());
//		condition.setClientId(10L);
//		condition.setMemberCode(10000L);
//		OrderSupportImpl support = new OrderSupportImpl();
//		PageEntity<OrderQueryCondition> pageCondition = new PageEntity<OrderQueryCondition>();
//		pageCondition.setPageNumber(1);
//		pageCondition.setCondition(condition);
//		MongoPage page = support.getOrderByConditioinOfPage(pageCondition);
	    
	    
//	    OrderSupportImpl suport = new OrderSupportImpl();
//	    suport.deleteSubOrder("01A1A8E30000");
//	    suport.updateOrderStatusByOrderId("0128BEB48000");
//	    suport.getSubOrderProductDetail(1000L,"01299B728000","01126E450000");
//	    suport.updateSubOrderAfterConsumed("00D176CA8000","00932adf0000",ProductType.group,"1000",null);
//	    suport.getOrderById("0128BEB48000");
//	    suport.updateSubOrderAfterRefund("anhui","028CCEB98000","028CCEB90000","3");
	    
        
//	    imp.updateSuborderProdcutGivePointState("anhui","09E420418000",null,true,false);
	    
//	    List<Long> listp = new ArrayList<Long>();
//	    for(int i = 27870; i < 28869 ; i++){
//	    	listp.add(Long.valueOf(i));
//	    }
//	    System.out.println(JSON.toJSONString(imp.findMerchantUserNameByIdList(listp)));
	    OrgLevelEnum iitet =OrgLevelEnum.orgLevel_four;
	    System.out.println(JSON.toJSONString(imp.findMerchantUserNameById(73511L)));
		
	}



	@Override
	public Iterator<DBObject> findOrderByPipeline(List<DBObject> pipeline) {
		return mongo.findByPipeline(pipeline, MongoTableName.CB_ORDER);
	}
	
	@Override
	public Map<String,String> getSettlementByOrderIdList(List<String> orderIdList,boolean isShowDescribe) {
		long st = System.currentTimeMillis();
	    DBObject where = new BasicDBObject();
	    where.put("order_id", new BasicDBObject(QueryOperators.IN,orderIdList));
	    
	    DBObject keys = new BasicDBObject();
	    keys.put("order_id", 1);
	    keys.put("settle_state", 1);
	    
	    DBCursor cursor = mongo.findAllToCursor(where, keys, null, MongoTableName.CB_SETTLEMENT);
	    
	    Map<String,String> map = new HashMap<String,String>();
	    if(EmptyChecker.isNotEmpty(cursor)){
	    	while (cursor.hasNext()) {
	    		DBObject dBObject = cursor.next();
	    		if (dBObject.get("settle_state") != null) {
	    			String settlementState = null;
	    			if(isShowDescribe){
	    				settlementState = SettlementStatus.getSettlementByCode((String) dBObject.get("settle_state")).getDescribe();
	    			}else{
	    				settlementState = (String) dBObject.get("settle_state");
	    			}
                	map.put((String) dBObject.get("order_id"),  settlementState);
                }
			}
	    }
	    LogCvt.info("关联查询"+orderIdList.size()+"个大订单的结算信息，耗时：" + (System.currentTimeMillis() - st));
	    return map;
	}

	@Override
	public List<Ticket> getTickets(List<String> orderIdList,List<String> subOrderIdList, List<String> productIdList) {
		DBObject queryObj = null;
		queryObj = new BasicDBObject();
		if(EmptyChecker.isNotEmpty(orderIdList)){
			queryObj.put(FieldMapping.ORDER_ID.getMongoField(), new BasicDBObject(QueryOperators.IN,orderIdList));
		}
		
		if(EmptyChecker.isNotEmpty(subOrderIdList)){
			queryObj.put(FieldMapping.SUB_ORDER_ID.getMongoField(), new BasicDBObject(QueryOperators.IN,subOrderIdList));
		}
		
		if(EmptyChecker.isNotEmpty(productIdList)){
			queryObj.put(FieldMapping.PRODUCT_ID.getMongoField(), new BasicDBObject(QueryOperators.IN,productIdList));
		}
		
		if(EmptyChecker.isEmpty(orderIdList) && EmptyChecker.isEmpty(subOrderIdList) && EmptyChecker.isEmpty(productIdList)){
			return null;
		}

		return (List<Ticket>) mongo.findAll(queryObj, MongoTableName.CB_TICKET,Ticket.class);
		
	}
	
	@Override
	public Iterator<DBObject> findSubOrderByPipeline(List<DBObject> pipeline) { 
		
		return mongo.findByPipeline(pipeline, MongoTableName.CB_SUBORDER_PRODUCT);
	}
	
	@Override
	public Map<String,Integer> getSettlementBySubOrderIdList(List<String> subOrderIdList) {
		long st = System.currentTimeMillis();
	    DBObject where = new BasicDBObject();
	    where.put("sub_order_id", new BasicDBObject(QueryOperators.IN,subOrderIdList));
	    
	    DBObject keys = new BasicDBObject();
	    keys.put("sub_order_id", 1);
	    keys.put("product_id", 1);
	    keys.put("product_count", 1);
	    keys.put("settle_state", 1);
	    
	    DBCursor cursor = mongo.findAllToCursor(where, keys, null, MongoTableName.CB_SETTLEMENT);
	    
	    Map<String,Integer> map = new HashMap<String,Integer>();
	    if(EmptyChecker.isNotEmpty(cursor)){
	    	while (cursor.hasNext()) {
	    		DBObject dBObject = cursor.next();
	    		if (SettlementStatus.settlementsucc.getCode().equals(dBObject.get("settle_state"))) {
                	int settlementNum = 0;
                	String key = dBObject.get("sub_order_id")+"_"+dBObject.get("product_id");
                	if(EmptyChecker.isNotEmpty(map.get(key))){
                		settlementNum = map.get(key);
                	}
					int productCount = dBObject.get("product_count") == null ? 0 : (Integer) dBObject.get("product_count");
                	map.put(key,  productCount + settlementNum);
                }
			}
	    }
	    LogCvt.info("关联查询"+subOrderIdList.size()+"个子订单的结算信息，耗时：" + (System.currentTimeMillis() - st));
	    return map;
	}
	
	@Override
	public boolean updateSubOrderLogistic(String clientId, String subOrderId, String deliveryState) {
		SubOrderMongo subOrderMongo = getSubOrderBySubOrderId(clientId, subOrderId);
		if(EmptyChecker.isEmpty(subOrderMongo)){
			return false;
		}
		
		DBObject where = new BasicDBObject();
		where.put("client_id", clientId);
		where.put("order_id", subOrderMongo.getOrderId());
		where.put("sub_order_id", subOrderId);
		
		LogCvt.info("更新子订单配送状态，查询订单条件：" + where);
		
		DBObject value = new BasicDBObject();
		value.put("delivery_state", deliveryState);
		
		int updateNum = mongo.update(value, where, MongoTableName.CB_SUBORDER_PRODUCT, "$set", true, false);
		return updateNum > 0;
	}
	
	@Override
	public List<OrderMongo> getVipOrderList(String clientId,Long memberCode,boolean isPaySuccess){
		DBObject query = new BasicDBObject();
		query.put("client_id", clientId);
		query.put("member_code", memberCode);
		query.put("is_vip_order", 1);
		if(isPaySuccess){
			query.put("order_status", OrderStatus.paysuccess.getCode());
		}else{
			Object [] obj = {
  					 new BasicDBObject("order_status", OrderStatus.create.getCode()),
  					 new BasicDBObject("order_status", OrderStatus.payfailed.getCode())};
			query.put(QueryOperators.OR, obj);
		}
		DBObject sort = new BasicDBObject();
		sort.put("create_time",-1);
		
		return (List<OrderMongo>) mongo.findAll(query,sort, MongoTableName.CB_ORDER, OrderMongo.class);
	}
}

