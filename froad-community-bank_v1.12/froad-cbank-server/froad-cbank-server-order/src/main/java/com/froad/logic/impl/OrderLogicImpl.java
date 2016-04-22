package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.OrderMapper;
import com.froad.db.redis.MerchantRedis;
import com.froad.db.redis.RedisCommon;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.DeliveryFlag;
import com.froad.enums.DeliveryType;
import com.froad.enums.ModuleID;
import com.froad.enums.OrderState;
import com.froad.enums.OrderStatus;
import com.froad.enums.OrderType;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ProductType;
import com.froad.enums.ResultCode;
import com.froad.enums.SettlementStatus;
import com.froad.enums.ShippingStatus;
import com.froad.enums.SubOrderType;
import com.froad.exceptions.FroadBusinessException;
import com.froad.log.OrderLogs;
import com.froad.log.vo.HeadKey;
import com.froad.log.vo.OrderDetailLog;
import com.froad.log.vo.OrderLog;
import com.froad.log.vo.OrderProduct;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.OrderLogic;
import com.froad.logic.RefundLogic;
import com.froad.logic.TicketLogic;
import com.froad.logic.impl.order.OrderLogger;
import com.froad.logic.impl.order.OrderSplitPointCash;
import com.froad.po.OrderQueryCondition;
import com.froad.po.Org;
import com.froad.po.Payment;
import com.froad.po.Product;
import com.froad.po.ProductMonthCount;
import com.froad.po.ProductSeckill;
import com.froad.po.RecvInfo;
import com.froad.po.Store;
import com.froad.po.base.PageEntity;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductBuyLimit;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.ShippingOrderMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.order.OrderResultData;
import com.froad.po.refund.RefundHistory;
import com.froad.po.settlement.Settlement;
import com.froad.support.MemberInformationSupport;
import com.froad.support.OrderSupport;
import com.froad.support.PaymentSupport;
import com.froad.support.SettlementSupport;
import com.froad.support.impl.MemberInformationSupportImpl;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.impl.PaymentSupportImpl;
import com.froad.support.impl.RefundSupportImpl;
import com.froad.support.impl.SettlementSupportImpl;
import com.froad.support.impl.payment.DataWrapImpl;
import com.froad.support.payment.DataWrap;
import com.froad.thirdparty.dto.response.pe.MemberPointsInfo;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.CheckOrderReqVo;
import com.froad.thrift.vo.active.CheckOrderResVo;
import com.froad.thrift.vo.active.CloseMarketOrderReqVo;
import com.froad.thrift.vo.active.CreateMarketOrderReqVo;
import com.froad.thrift.vo.active.CreateResultVo;
import com.froad.thrift.vo.active.FailureGoBackReqVo;
import com.froad.thrift.vo.active.FindMarketOrderReqVo;
import com.froad.thrift.vo.active.FindMarketOrderResVo;
import com.froad.thrift.vo.active.FindMarketSubOrderProductVo;
import com.froad.thrift.vo.active.FindMarketSubOrderVo;
import com.froad.thrift.vo.active.FullGiveActiveVo;
import com.froad.thrift.vo.active.MarketSubOrderProductVo;
import com.froad.thrift.vo.active.MarketSubOrderVo;
import com.froad.thrift.vo.active.OrderProductVo;
import com.froad.thrift.vo.active.UpdateMarketOrderReqVo;
import com.froad.thrift.vo.active.UpdateMarketOrderResVo;
import com.froad.thrift.vo.order.CashierVoReq;
import com.froad.thrift.vo.order.CashierVoRes;
import com.froad.thrift.vo.order.DeliverInfoDetailVo;
import com.froad.thrift.vo.order.GetMemberBuyLimitVoRes;
import com.froad.thrift.vo.order.GetOrderByQrcodeVoRes;
import com.froad.thrift.vo.order.GetSubOrderVoRes;
import com.froad.thrift.vo.order.OrderDetailVo;
import com.froad.thrift.vo.order.OrderSummaryVo;
import com.froad.thrift.vo.order.PointExchangeVo;
import com.froad.thrift.vo.order.ProductDetailVo;
import com.froad.thrift.vo.order.ProductSummaryVo;
import com.froad.thrift.vo.order.QrcodeOrderDetailVo;
import com.froad.thrift.vo.order.QrcodeOrderSummaryVo;
import com.froad.thrift.vo.order.ShippingDetailVo;
import com.froad.thrift.vo.order.SubOrderDetailVo;
import com.froad.util.Arith;
import com.froad.util.EmptyChecker;
import com.froad.util.JSonUtil;
import com.froad.util.PropertiesUtil;
import com.froad.util.RedisKeyUtil;
import com.froad.util.SimpleID;
import com.froad.util.payment.BaseSubassembly;
import com.froad.util.payment.Const;
import com.mongodb.BasicDBObject;

public class OrderLogicImpl implements OrderLogic {
	
	/**
	 * thrift.properties
	 */
	private static final String THRIFT_PROPERTIES_FILE = "thrift";
	
	/**
	 * properties host key
	 */
	private static final String PROPERTIES_HOST_KEY = "thrift.active.host";
	
	/**
	 * properties port key
	 */
	private static final String PROPERTIES_PORT_KEY = "thrift.active.port";
	
	private static SimpleID reqId = new SimpleID(ModuleID.active);
	
	/**
	 * 数据操作层：订单
	 */
	private OrderSupport orderSupport = new OrderSupportImpl();
	
	/**
	 * 数据操作层：券
	 */
	private TicketLogic ticketLogic = new TicketLogicImpl();
	
	/**
	 * 数据操作层：退款
	 */
	private  RefundLogic refundLogic = new RefundLogicImpl();
	
	/**
	 * 数据操作层：支付
	 */
	private  PaymentSupport paymentSupport = new PaymentSupportImpl();
	
	//common项目公共
	private  CommonLogic commonLogic = new CommonLogicImpl();
	
	private SettlementSupport settlementSupport = new SettlementSupportImpl();
	
	private RefundSupportImpl refundSupport = new RefundSupportImpl();
	
	private DataWrap dataWrap = new DataWrapImpl();
	
	private MemberInformationSupport memberInformationSupport = new MemberInformationSupportImpl();	
	
	/**
	 * 检查字段是否为空，为空时抛出异常信息
	 * @param field 字段
	 * @param errorMsg 错误信息
	 * @throws FroadBusinessException 自定义业务异常信息
	 */
	public void dataEmptyChecker(Object field, String errorMsg) throws FroadBusinessException {
		//1.请求数据校验
		if(EmptyChecker.isEmpty(field)){
			LogCvt.error(errorMsg);
			throw new FroadBusinessException(ResultCode.failed.getCode(), errorMsg);
		}
	}
	
	@Override
	public ResultBean addOrder(OrderMongo order,List<SubOrderMongo> subOrderList) {
//		LogCvt.info("Mongo：订单创建");
//		long st = System.currentTimeMillis();
		ResultBean result = new ResultBean(ResultCode.success,"订单创建成功");
		try {
			boolean orderResult = orderSupport.addOrder(order);
			if (orderResult) {
				boolean subOrderResult = orderSupport.addSubOrderList(subOrderList);
				if (!subOrderResult) {
					return new ResultBean(ResultCode.failed,"子订单批量创建失败");
				}
			}else{
				return new ResultBean(ResultCode.failed,"大订单创建失败");
			}
			result = new ResultBean(ResultCode.success,"订单创建成功",order.getOrderId());
		}catch(Exception e){
			LogCvt.error("订单创建-系统异常:"+ e.getMessage(),e);
			result = new ResultBean(ResultCode.failed,"订单创建失败");
		}
//		LogCvt.info("【mongo插入订单  OrderLogicImpl.addOrder 】-耗时（"+(System.currentTimeMillis() - st)+"）毫秒");
		return result;
	}
	
	@Override
	public ResultBean addOrderRedis(OrderMongo order,List<SubOrderMongo> subOrderList) {
		long st = System.currentTimeMillis();
		ResultBean result = new ResultBean(ResultCode.success,"订单缓存添加成功");
		boolean flag = orderSupport.addOrderRedis(order);
		if(!flag){
			return new ResultBean(ResultCode.failed,"订单缓存添加失败");
		}
		LogCvt.info("[订单创建]-订单创建时间缓存|VIP优惠金额缓存-耗时（"+(System.currentTimeMillis() - st)+"）毫秒");
		long st2 = System.currentTimeMillis();
		if(EmptyChecker.isNotEmpty(subOrderList)){
			boolean addflag = orderSupport.addMemberBuyHistory(subOrderList);
			if(!addflag){
				return new ResultBean(ResultCode.failed,"订单缓存添加失败");
			}
		}
		LogCvt.info("[订单创建]-会员历史购买记录缓存--耗时（"+(System.currentTimeMillis() - st2)+"）毫秒");
		return result;
	}
	
	@Override
	public ResultBean addOrderRedisForSeckill(OrderMongo order,List<SubOrderMongo> subOrderList) {
		long st = System.currentTimeMillis();
		ResultBean result = new ResultBean(ResultCode.success,"秒杀订单缓存添加成功");
		boolean flag = orderSupport.addOrderRedis(order);
		if(!flag){
			return new ResultBean(ResultCode.failed,"秒杀订单缓存添加失败");
		}
		LogCvt.info("[秒杀订单创建]-订单创建时间缓存|VIP优惠金额缓存-耗时（"+(System.currentTimeMillis() - st)+"）毫秒");
		long st2 = System.currentTimeMillis();
		if(EmptyChecker.isNotEmpty(subOrderList)){
			boolean addflag = orderSupport.updateMemberBuyHistoryForSeckill(subOrderList,true);
			if(!addflag){
				return new ResultBean(ResultCode.failed,"秒杀订单缓存添加失败");
			}
		}
		LogCvt.info("[秒杀订单创建]-会员历史购买记录缓存--耗时（"+(System.currentTimeMillis() - st2)+"）毫秒");
		return result;
	}
	
	@Override
	public void addSeckillOrderRedis(String reqId,OrderMongo order) {
		orderSupport.addSeckillOrderRedis(reqId,order);
	}
	
	@Override
	public void updateSeckillOrderRedis(String reqId,String resultFlag) {
		orderSupport.updateSeckillOrderRedis(reqId, resultFlag);
	}
	
	@Override
	public ResultBean reduceStore(List<Store> list) {
		//冻结库存
		long st = System.currentTimeMillis();
		LogCvt.info("redis：减库存");
		ResultBean result = new ResultBean(ResultCode.success,"减库存成功");
		List<Store> sucessStoreList = new ArrayList<Store>();
		Jedis jedis = RedisManager.getJedis(RedisManager.write);
		try {
			dataEmptyChecker(list, "库存集合参数不能为空");
			for(Store store : list){
				dataEmptyChecker(store.getClientId(), "clientId不能为空");
				dataEmptyChecker(store.getMerchantId(), "merchantId不能为空");
				dataEmptyChecker(store.getProductId(), "productId不能为空");
				String prodcutKey = RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(store.getClientId(), store.getMerchantId(), store.getProductId());
				LogCvt.info("减库存操作的缓存Key：" + prodcutKey);
				LogCvt.info("操作前库存数：" + jedis.get(prodcutKey));
				LogCvt.info("操作数：" + (0 - store.getReduceStore()));
				Long stock = jedis.decrBy(prodcutKey,store.getReduceStore());
				sucessStoreList.add(store);
				LogCvt.info("操作后库存数：" + stock);
				if (stock < 0) {
					//减库存失败时，还库存
					LogCvt.info("库存操作失败，还库存");
					processStore(sucessStoreList,jedis,true);
					RedisManager.returnJedis(jedis,RedisManager.write);
					return new ResultBean(ResultCode.failed,"减库存失败，[商品ID:"+store.getProductId()+"]库存不足。");
				}
			}
		} catch (FroadBusinessException e) {
			//减库存异常时，还库存
			LogCvt.error("库存操作-业务异常：" + e.getMsg(),e);
			processStore(sucessStoreList,jedis,true);
			RedisManager.returnJedis(jedis,RedisManager.write);
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		RedisManager.returnJedis(jedis,RedisManager.write);
//		LogCvt.info("[订单创建]-redis扣库存(OrderLogicImpl.reduceStore)，耗时（"+(System.currentTimeMillis() - st)+"）毫秒");
		return result;
	}
	
	/**
	 * 库存操作
	 * @param list 库存信息
	 * @param jedis
	 * @param flag true为加库存，false为减库存
	 */
	public void processStore(List<Store> list,Jedis jedis,boolean flag){
		if(EmptyChecker.isNotEmpty(list)){
			LogCvt.info("还原库存操作开始...");
			for(Store store : list){
				int count = flag ? store.getReduceStore() : 0 - store.getReduceStore();
				String prodcutKey = RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(store.getClientId(), store.getMerchantId(), store.getProductId());
				LogCvt.info("减库存操作的缓存Key：" + prodcutKey);
				LogCvt.info("操作前库存数：" + jedis.get(prodcutKey));
				LogCvt.info("操作数：" + ( store.getReduceStore()));
				Long stock = jedis.incrBy(prodcutKey,count);
				LogCvt.info("操作后库存数：" + stock);
			}
			LogCvt.info("还原存操作结束.");
		}
	}
	
	@Override
	public ResultBean increaseStore(List<Store> list) {
		//还库存
		long st = System.currentTimeMillis();
		LogCvt.info("redis：还库存开始");
		ResultBean result = new ResultBean(ResultCode.success,"还库存成功");
		List<Store> sucessStoreList = new ArrayList<Store>();
		Jedis jedis = RedisManager.getJedis(RedisManager.write);
		try {
			dataEmptyChecker(list, "库存集合参数不能为空");
			for(Store store : list){
				dataEmptyChecker(store.getClientId(), "clientId不能为空");
				dataEmptyChecker(store.getMerchantId(), "merchantId不能为空");
				dataEmptyChecker(store.getProductId(), "productId不能为空");
				String prodcutKey = RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(store.getClientId(), store.getMerchantId(), store.getProductId());
				LogCvt.info("还库存操作的缓存Key：" + prodcutKey);
				LogCvt.info("操作前库存数：" + jedis.get(prodcutKey));
				LogCvt.info("操作数：" + store.getReduceStore());
				Long stock = jedis.incrBy(prodcutKey, store.getReduceStore());
				sucessStoreList.add(store);
				LogCvt.info("操作后库存数：" + stock);
				/*if (stock < 0) {
					//减库存失败时，还库存
					LogCvt.info("还库存操作失败");
					processStore(sucessStoreList,jedis,true);
					RedisManager.returnJedis(jedis);
					return new ResultBean(ResultCode.failed,"减库存失败，[商品ID:"+store.getProductId()+"]库存不足。");
				}*/
			}
		}  catch (JedisDataException e) {
			//还库存异常时，还库存
			LogCvt.error("还库存操作-数据库异常:"+e.getMessage(),e);
			processStore(sucessStoreList,jedis,false);
			RedisManager.returnJedis(jedis,RedisManager.write);
			return new ResultBean(ResultCode.failed,e.getMessage());
		} catch (FroadBusinessException e) {
			//还库存异常时，还库存
			LogCvt.error("还库存操作-业务异常:"+e.getMsg(),e);
			processStore(sucessStoreList,jedis,false);
			RedisManager.returnJedis(jedis,RedisManager.write);
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		RedisManager.returnJedis(jedis,RedisManager.write);
		LogCvt.info("redis：还库存结束- OrderLogicImpl.increaseStore -耗时（"+(System.currentTimeMillis() - st)+"）毫秒");
		return result;
	}
	
	@Override
	public ResultBean updateProductStore(List<Store> list) {
		//库存缓存从redis更新至mysql
		LogCvt.info("mysql：商品库存缓存从redis同步至mysql开始");
		long st = System.currentTimeMillis();
		ResultBean result = new ResultBean(ResultCode.success,"商品库存更新成功");
		if(EmptyChecker.isNotEmpty(list)){
			List<Product> productList = new ArrayList<Product>();
			for(Store store : list){
				Product product = new Product();
				product.setClientId(store.getClientId());
				product.setMerchantId(store.getMerchantId());
				product.setProductId(store.getProductId());
				int storeCount = commonLogic.getStoreRedis(store.getClientId(), store.getMerchantId(), store.getProductId());
				product.setStore(storeCount);
				productList.add(product);
			}
			Boolean flag = orderSupport.updateProductStore(productList);
			if(!flag){
				result = new ResultBean(ResultCode.failed,"商品库存更新失败");
			}
		}
		LogCvt.info("mysql：商品库存缓存从redis同步至mysql结束- OrderLogicImpl.updateProductStore -耗时（"+(System.currentTimeMillis() - st)+"）毫秒");
		return result;
	}

	@Override
	public ResultBean updateSeckillProductStore(List<Store> list) {
		//库存缓存从redis更新至mysql
		LogCvt.info("mysql：秒杀模块-商品库存缓存从redis同步至mysql");
		ResultBean result = new ResultBean(ResultCode.success,"商品库存更新成功");
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		try {
			dataEmptyChecker(list, "库存集合参数不能为空");
			OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
			for(Store store : list){
				ProductSeckill product = new ProductSeckill();
				product.setClientId(store.getClientId());
				product.setMerchantId(store.getMerchantId());
				product.setProductId(store.getProductId());
				int storeCount = commonLogic.getSeckillStoreRedis(store.getClientId(), store.getProductId());
				product.setSecStore(storeCount);
				LogCvt.info("秒杀模块-mysql更新商品库存数：[product_id:"+store.getProductId()+" 数量："+storeCount+"]");
				Boolean flag = orderMapper.updateSeckillProductStore(product);
				if(!flag){
					throw new FroadBusinessException(ResultCode.failed.getCode(),"mysql商品库存更新失败");
				}
			}
			sqlSession.commit(true);
			result = new ResultBean(ResultCode.success,"商品库存更新成功");
		}catch(Exception e){
			if(null != sqlSession) {
				sqlSession.rollback(true);
			}
			LogCvt.error("商品库存更-系统异常:" + e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"商品库存更新失败");
		}finally {
			if(null != sqlSession) {
				sqlSession.close();
			}
		}
		return result;
	}
	
	@Override
	public ResultBean getOrderByOrderId(String clientId,String orderId) {
		LogCvt.info("Mongo：大订单查询");
		ResultBean result = new ResultBean(ResultCode.success,"大订单查询成功");
		try {
			dataEmptyChecker(orderId, "orderId不能为空");
			OrderMongo order =  orderSupport.getOrderByOrderId(clientId,orderId);
			result = new ResultBean(ResultCode.success,"大订单查询成功",order);
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}
	
	@Override
	public ResultBean getSubOrderBySubOrderId(String clientId,String subOrderId) {
		LogCvt.info("Mongo：子订单查询");
		ResultBean result = new ResultBean(ResultCode.success,"子订单查询成功");
		try {
			dataEmptyChecker(subOrderId, "orderId不能为空");
			SubOrderMongo order =  orderSupport.getSubOrderBySubOrderId(clientId,subOrderId);
			result = new ResultBean(ResultCode.success,"子订单查询成功",order);
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}
	
	@Override
	public ResultBean getSubOrderListByOrderId(String clientId,String orderId) {
		LogCvt.info("Mongo：子订单集合查询");
		ResultBean result = new ResultBean(ResultCode.success,"子订单集合查询成功");
		try {
			dataEmptyChecker(orderId, "orderId不能为空");
			List<SubOrderMongo> subOrderList =  orderSupport.getSubOrderListByOrderId(clientId,orderId);
			result = new ResultBean(ResultCode.success,"子订单集合查询成功",subOrderList);
		} catch (FroadBusinessException e) {
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}
	
	@Override
	public ResultBean getStoreListByOrderId(String clientId,String orderId) {
		LogCvt.info("Mongo：获取订单组装库存集合");
		ResultBean result = new ResultBean(ResultCode.success,"操作成功");
		try {
			dataEmptyChecker(orderId, "orderId不能为空");
			List<SubOrderMongo> subOrderList =  orderSupport.getSubOrderListByOrderId(clientId,orderId);
			List<Store> storeList = new ArrayList<Store>();
			if(EmptyChecker.isNotEmpty(subOrderList)){
				for(SubOrderMongo subOrderMongo : subOrderList){
					if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
						for(ProductMongo productMongo : subOrderMongo.getProducts()){
							Store store = new Store();
							store.setClientId(subOrderMongo.getClientId());
							
							//取机构的虚拟商户号
							String merchantId = subOrderMongo.getMerchantId();
							if(!(StringUtils.equals(subOrderMongo.getType(), SubOrderType.special_merchant.getCode()) || StringUtils.equals(subOrderMongo.getType(), SubOrderType.group_merchant.getCode()) || StringUtils.equals(subOrderMongo.getType(), SubOrderType.boutique.getCode()))){
								Org org = commonLogic.getOrgByOrgCode(merchantId, clientId);
								if(EmptyChecker.isEmpty(org)){
									LogCvt.error("[定时任务调用库存操作]-通过机构号获取商户号失败！[商品ID："+productMongo.getProductId()+"，机构号："+merchantId+"]");
									throw new FroadBusinessException(ResultCode.failed.getCode(),"还库存操作失败，原因：商品所属机构对应商户号查询为空");
								}
								merchantId = org.getMerchantId();
							}
							
							store.setMerchantId(merchantId);
							store.setProductId(productMongo.getProductId());
							store.setReduceStore(productMongo.getQuantity() + productMongo.getVipQuantity());
							storeList.add(store);
						}
						result = new ResultBean(ResultCode.success,"操作成功",storeList);
					}else{
						LogCvt.error("还库存操作失败，原因：子订单商品查询结果为空，订单号：" + orderId);
						result = new ResultBean(ResultCode.failed,"还库存操作失败，原因：子订单商品查询结果为空");
					}
				}
			}else{
				LogCvt.error("还库存操作失败，原因：子订单查询结果为空，订单号：" + orderId);
				result = new ResultBean(ResultCode.failed,"还库存操作失败，原因：子订单列表查询结果为空");
			}
		} catch (FroadBusinessException e) {
			LogCvt.error("[订单号:"+orderId+"] 还库存操作-业务异常:"+e.getMsg(),e);
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultBean getOrderSummary(PageEntity<OrderQueryCondition> orderQueryCondition) {
		long st = System.currentTimeMillis();
		LogCvt.info("Mongo：获取订单概要");
		ResultBean result = new ResultBean(ResultCode.success,"获取订单概要成功");
		try {
			MongoPage page = orderSupport.getOrderByConditioinOfPage(orderQueryCondition);
		    List<OrderSummaryVo> list = new ArrayList<OrderSummaryVo>();
			List<OrderMongo> orderList = (List<OrderMongo>) page.getItems();
			long firstRecordTime = 0l;
			long lastRecordTime = 0l;
			if(EmptyChecker.isNotEmpty(orderList)){
				firstRecordTime = orderList.get(0).getCreateTime();
				lastRecordTime = orderList.get(orderList.size()-1).getCreateTime();
				for(OrderMongo order : orderList){
					OrderSummaryVo orderSummaryVo = new OrderSummaryVo();
					orderSummaryVo.setOrderId(order.getOrderId());
					orderSummaryVo.setOrderStatus(order.getOrderStatus());
					orderSummaryVo.setTotalPrice(Arith.div(order.getTotalPrice(), 1000));
					orderSummaryVo.setVipDiscount(Arith.div(order.getVipDiscount(), 1000));
					if(StringUtils.equals(order.getOrderStatus(), OrderStatus.create.getCode())){
						orderSummaryVo.setIsEnableCancel(true);//是否可取消
						orderSummaryVo.setIsEnablePay(true);//是否可支付
					}
					if(StringUtils.equals(order.getState(), OrderState.SYSTEM_CLOSED.getCode())){
						orderSummaryVo.setIsEnableCancel(false);//是否可取消
						orderSummaryVo.setIsEnablePay(false);//是否可支付
					}
					//订单支付中+非积分支付的订单可取消
					if(StringUtils.equals(order.getOrderStatus(), OrderStatus.paying.getCode()) 
							&& !(StringUtils.equals(order.getPaymentMethod(), PaymentMethod.bankPoints.getCode())
									|| StringUtils.equals(order.getPaymentMethod(), PaymentMethod.froadPoints.getCode())
									|| StringUtils.equals(order.getPaymentMethod(), PaymentMethod.creditPoints.getCode())
									|| StringUtils.equals(order.getPaymentMethod(), PaymentMethod.invalid.getCode()))){
						orderSummaryVo.setIsEnableCancel(true);//是否可取消
					}
					
					DeliveryFlag deliveryFlag = null;
					boolean isSeckill = BooleanUtils.toBoolean(order.getIsSeckill());
					orderSummaryVo.setIsSeckill(isSeckill);
					
					//VIP订单
					orderSummaryVo.setIsVipOrder(false);
					if(EmptyChecker.isNotEmpty(order.getIsVipOrder()) && order.getIsVipOrder() == 1){
						List<ProductSummaryVo> productSummaryVoList = new ArrayList<ProductSummaryVo>();
						ProductSummaryVo productSummaryVo = new ProductSummaryVo();
						productSummaryVo.setProductId(order.getProductId());
						productSummaryVo.setMerchantName(order.getMerchantName());
						productSummaryVo.setProductName(order.getProductName());
						productSummaryVoList.add(productSummaryVo);
						orderSummaryVo.setProductSummaryVoList(productSummaryVoList);
						orderSummaryVo.setIsVipOrder(true);
						list.add(orderSummaryVo);
						continue;
					}
					
					List<SubOrderMongo> SubOrderMongoList =  orderSupport.getSubOrderListByOrderId(order.getClientId(),order.getOrderId());
					if(EmptyChecker.isNotEmpty(SubOrderMongoList)){
						//秒杀
						if(isSeckill){
							if(EmptyChecker.isNotEmpty(order.getRecvId()) && EmptyChecker.isNotEmpty(order.getDeliverId())){
								deliveryFlag = DeliveryFlag.FILLED;
							}else{
								if(StringUtils.equals(SubOrderMongoList.get(0).getType(), SubOrderType.special_merchant.getCode())){
									deliveryFlag = DeliveryFlag.HOME;
								}
								if(StringUtils.equals(SubOrderMongoList.get(0).getType(), SubOrderType.boutique.getCode())){
									deliveryFlag = DeliveryFlag.HOME;
								}
								if(StringUtils.equals(SubOrderMongoList.get(0).getType(), SubOrderType.group_merchant.getCode())){
									deliveryFlag = DeliveryFlag.FILLED;
								}
								if(StringUtils.equals(SubOrderMongoList.get(0).getType(), SubOrderType.presell_org.getCode())){
									if(StringUtils.equals(SubOrderMongoList.get(0).getProducts().get(0).getDeliveryOption(), DeliveryType.home_or_take.getCode())){
										deliveryFlag = DeliveryFlag.TAKE_HOME;
									}else if(StringUtils.equals(SubOrderMongoList.get(0).getProducts().get(0).getDeliveryOption(), DeliveryType.take.getCode())){
										deliveryFlag = DeliveryFlag.TAKE;
									}else if(StringUtils.equals(SubOrderMongoList.get(0).getProducts().get(0).getDeliveryOption(), DeliveryType.home.getCode())){
										deliveryFlag = DeliveryFlag.HOME;
									}
								}
							} 
						}
						
						List<ProductSummaryVo> productSummaryVoList = new ArrayList<ProductSummaryVo>();
						for(SubOrderMongo subOrder : SubOrderMongoList){
							List<ProductMongo> products = subOrder.getProducts();
							if(EmptyChecker.isNotEmpty(products)){
								for(ProductMongo product : products){
									ProductSummaryVo productSummaryVo = new ProductSummaryVo();
									productSummaryVo.setProductId(product.getProductId());
									productSummaryVo.setProductImage(product.getProductImage());
									productSummaryVo.setProductName(product.getProductName());
									productSummaryVo.setMerchantName(subOrder.getMerchantName());
									productSummaryVo.setProductType(product.getType());
									productSummaryVoList.add(productSummaryVo);
								}
							}
						}
						if(EmptyChecker.isNotEmpty(deliveryFlag)){
							orderSummaryVo.setDeliveryFlag(deliveryFlag.getCode());
						}
						orderSummaryVo.setProductSummaryVoList(productSummaryVoList);
					}
					list.add(orderSummaryVo);
				}
			}
			PageVo pageVo = new PageVo();
			pageVo.setPageCount(page.getPageCount());
			pageVo.setPageNumber(page.getPageNumber());
			pageVo.setPageSize(page.getPageSize());
			pageVo.setTotalCount(page.getTotalCount());
			pageVo.setFirstRecordTime(firstRecordTime);
			pageVo.setLastRecordTime(lastRecordTime);
			pageVo.setHasNext(page.getPageCount()>page.getPageNumber());
			pageVo.setLastPageNumber(page.getPageNumber());
			result = new ResultBean(ResultCode.success,"获取订单概要成功",new Object[]{list,pageVo});
		}catch(Exception e){
			LogCvt.error("获取订单概要-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"获取订单概要失败");
		}
		LogCvt.info("获取订单概要总耗时:"+ (System.currentTimeMillis()-st));
		return result;
	}
	
	/**
	 * 检查预售商品在预售期内
	 * @param subOrderMongo
	 * @return
	 */
	public boolean checkPresellTime(SubOrderMongo subOrderMongo){
		if(EmptyChecker.isNotEmpty(subOrderMongo)){
			if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
				for(ProductMongo productMongo : subOrderMongo.getProducts()){
					
					//取机构的虚拟商户号
    				String merchantId = subOrderMongo.getMerchantId();
    				if(!(StringUtils.equals(subOrderMongo.getType(), SubOrderType.special_merchant.getCode()) || StringUtils.equals(subOrderMongo.getType(), SubOrderType.group_merchant.getCode()) || StringUtils.equals(subOrderMongo.getType(), SubOrderType.boutique.getCode()))){
    					Org org = commonLogic.getOrgByOrgCode(merchantId, subOrderMongo.getClientId());
    					if(EmptyChecker.isEmpty(org)){
    						LogCvt.error("通过机构号获取商户号失败[机构号："+merchantId+"]");
    						return false;
    					}
    					merchantId = org.getMerchantId();
    				}
					
					Map<String,String> productMap = commonLogic.getProductRedis(subOrderMongo.getClientId(), subOrderMongo.getMerchantId(), productMongo.getProductId());
					long productStartTime = Long.parseLong(productMap.get("start_time"));//团购|预售开始时间
					long productEndTime = Long.parseLong(productMap.get("end_time"));//团购|预售结束时间
					long curTime = System.currentTimeMillis();//当前时间
					if(productStartTime > curTime && productEndTime < curTime){
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public ResultBean getOrderDetail(OrderQueryCondition orderQueryCondition) {
		LogCvt.info("Mongo：获取订单详情");
		ResultBean result = new ResultBean(ResultCode.success,"获取订单详情成功");
		try {
			//大订单
			OrderMongo order = orderSupport.getOrderByOrderId(orderQueryCondition.getClientId(),orderQueryCondition.getOrderId());
			if(EmptyChecker.isEmpty(order)){
				OrderLogger.error("订单模块", "获取订单详情", "大订单为空", new Object[]{"clientId",orderQueryCondition.getClientId(),"orderId",orderQueryCondition.getOrderId()});
				return new ResultBean(ResultCode.failed,"获取订单详情失败");
			}
			OrderDetailVo orderDetailVo = new OrderDetailVo();
			orderDetailVo.setOrderId(order.getOrderId());
			orderDetailVo.setCreateTime(order.getCreateTime());
			orderDetailVo.setPaymentMethod(order.getPaymentMethod());
			orderDetailVo.setPaymentTime(order.getPaymentTime() == null ? 0 : order.getPaymentTime());
			orderDetailVo.setOrderStatus(order.getOrderStatus());
			orderDetailVo.setTotalPrice(Arith.div(order.getTotalPrice(), 1000));
			orderDetailVo.setFftPoints(Arith.div(order.getFftPoints(),1000));
			orderDetailVo.setBankPoints(Arith.div(order.getBankPoints(),1000));
			orderDetailVo.setRecvId(EmptyChecker.isEmpty(order.getRecvId()) ? (EmptyChecker.isEmpty(order.getDeliverId())?"":order.getDeliverId()): order.getRecvId());
			orderDetailVo.setVipDiscount(Arith.div(order.getVipDiscount(),1000));
			orderDetailVo.setGiveMoney(EmptyChecker.isEmpty(order.getGiveMoney()) ? 0 : Arith.div(order.getGiveMoney(),1000));
			orderDetailVo.setGivePoints(EmptyChecker.isEmpty(order.getGivePoints()) ? 0 : Arith.div(order.getGivePoints(),1000));
			if(StringUtils.equals(order.getOrderStatus(), OrderStatus.create.getCode())){
				orderDetailVo.setIsEnableCancel(true);
				orderDetailVo.setIsEnablePay(true);
			}
			
			if(StringUtils.equals(order.getState(), OrderState.SYSTEM_CLOSED.getCode())){
				orderDetailVo.setIsEnableCancel(false);//是否可取消
				orderDetailVo.setIsEnablePay(false);//是否可支付
			}
			
			//订单支付中+非积分支付的订单可取消
			if(StringUtils.equals(order.getOrderStatus(), OrderStatus.paying.getCode()) 
					&& !(StringUtils.equals(order.getPaymentMethod(), PaymentMethod.bankPoints.getCode())
							|| StringUtils.equals(order.getPaymentMethod(), PaymentMethod.froadPoints.getCode())
							|| StringUtils.equals(order.getPaymentMethod(), PaymentMethod.creditPoints.getCode())
							|| StringUtils.equals(order.getPaymentMethod(), PaymentMethod.invalid.getCode()))){
				orderDetailVo.setIsEnableCancel(true);//是否可取消
			}
			
			//VIP订单
			orderDetailVo.setIsVipOrder(false);
			if(EmptyChecker.isNotEmpty(order.getIsVipOrder()) && order.getIsVipOrder() == 1) {
				List<ProductDetailVo> productDetailVoList = new ArrayList<ProductDetailVo>();
				ProductDetailVo productDetailVo = new ProductDetailVo();
				productDetailVo.setProductId(order.getProductId());
				productDetailVo.setProductName(order.getProductName());
				productDetailVoList.add(productDetailVo);
				
				List<SubOrderDetailVo> subOrderDetailVoList = new ArrayList<SubOrderDetailVo>();
				SubOrderDetailVo subOrderDetailVo = new SubOrderDetailVo();
				subOrderDetailVo.setMerchantId(EmptyChecker.isNotEmpty(order.getBankLabelID())?order.getBankLabelID():"");
				subOrderDetailVo.setMerchantName(EmptyChecker.isNotEmpty(order.getBankLabelName())?order.getBankLabelName():"");
				subOrderDetailVo.setProductDetailVoList(productDetailVoList);
				subOrderDetailVo.setRefundState(order.getRemark());
				subOrderDetailVoList.add(subOrderDetailVo);
				orderDetailVo.setSubOrderDetailVoList(subOrderDetailVoList);
				orderDetailVo.setIsEnableRefund(RedisCommon.checkUserVipRefund(order.getClientId(),order.getMemberCode()));//在体验期内可以退款
				orderDetailVo.setIsVipOrder(true);
				//查退款状态
				List<RefundHistory> refundList = refundSupport.findListByDBObject(new BasicDBObject().append("order_id", order.getOrderId()).append("is_vip_refund", 1));
				if(EmptyChecker.isNotEmpty(refundList)){
					String refundState = refundList.get(0).getRefundState();
					if(EmptyChecker.isNotEmpty(refundState)){
						subOrderDetailVo.setRefundState(refundState);
					}
				}
				return new ResultBean(ResultCode.success, "获取订单详情成功", orderDetailVo);
			}
			
			//配送
			DeliverInfoDetailVo deliverInfoDetailVo = new DeliverInfoDetailVo();
			//提货人
			if(EmptyChecker.isNotEmpty(order.getDeliverId())){
				RecvInfo deliverInfo = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), order.getDeliverId());
				if(EmptyChecker.isNotEmpty(deliverInfo)){
					deliverInfoDetailVo.setConsignee(deliverInfo.getConsignee());
					deliverInfoDetailVo.setPhone(deliverInfo.getPhone());
					deliverInfoDetailVo.setAddress(deliverInfo.getAddress());
				}
			}
			//收货人
			if(EmptyChecker.isNotEmpty(order.getRecvId())){
				RecvInfo recvInfo = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), order.getRecvId());
				if(EmptyChecker.isNotEmpty(recvInfo)){
					deliverInfoDetailVo.setAddress(recvInfo.getAddress());
					deliverInfoDetailVo.setConsignee(recvInfo.getConsignee());
					deliverInfoDetailVo.setPhone(recvInfo.getPhone());
				}
			}
			orderDetailVo.setDeliverInfoDetailVo(deliverInfoDetailVo);
			
			//获取可退款数量
			Map<String, Integer> enableRefoundMap = refundLogic.getCanRefundProductList(orderQueryCondition.getClientId(),orderQueryCondition.getOrderId());
			
			//获取已退商品数量：
			Map<String, Integer> alreadyRefoundMap = refundLogic.getRefundedProduct(orderQueryCondition.getOrderId());
			
			//是否可退款
			boolean isOrderEnableRefund = false;
			
			//是否纯红包支付
			boolean isRedPacketOrder = false;
			if(order.getTotalPrice() == 0 && order.getBankPoints() == 0 && order.getFftPoints() == 0 && (EmptyChecker.isNotEmpty(order.getCashCouponId()) || EmptyChecker.isNotEmpty(order.getRedPacketId()))){
				isRedPacketOrder = true;
			}
			
			//子订单
			List<SubOrderMongo> subOrderList = orderSupport.getSubOrderListByOrderId(orderQueryCondition.getClientId(),orderQueryCondition.getOrderId());
			List<SubOrderDetailVo> subOrderDetailVoList = new ArrayList<SubOrderDetailVo>();
			if(EmptyChecker.isNotEmpty(subOrderList)){
				for(SubOrderMongo subOrderMongo : subOrderList){
					SubOrderDetailVo subOrderDetailVo = new SubOrderDetailVo();
					subOrderDetailVo.setSubOrderId(subOrderMongo.getSubOrderId());
					subOrderDetailVo.setType(subOrderMongo.getType());
					subOrderDetailVo.setMerchantId(subOrderMongo.getMerchantId());
					subOrderDetailVo.setMerchantName(subOrderMongo.getMerchantName());
					subOrderDetailVo.setRefundState(subOrderMongo.getRefundState());
					subOrderDetailVo.setDeliveryState(subOrderMongo.getDeliveryState());
					
					//是否可确认收货
					if(StringUtils.equals(subOrderMongo.getDeliveryState(), ShippingStatus.shipped.getCode())
							&& (StringUtils.equals(subOrderMongo.getType(), SubOrderType.special_merchant.getCode()) || StringUtils.equals(subOrderMongo.getType(), SubOrderType.boutique.getCode()))){
						subOrderDetailVo.setIsEnableConfirmReceive(true);
					}else{
						subOrderDetailVo.setIsEnableConfirmReceive(false);
					}
					
					//子订单是否可退款
					boolean isSubOrderEnableRefund = false;
					
					/*if(StringUtils.equals(subOrderMongo.getType(), SubOrderType.online_points_org.getCode()) 
							|| StringUtils.equals(subOrderMongo.getType(), SubOrderType.offline_points_org.getCode())){
						isSubOrderEnableRefund = false;
					}
					
					//已经发货的、收货的、提货的，不可退款
					if(StringUtils.equals(subOrderMongo.getDeliveryState(), ShippingStatus.shipped.getCode()) 
							|| StringUtils.equals(subOrderMongo.getDeliveryState(), ShippingStatus.receipt.getCode())
							|| StringUtils.equals(subOrderMongo.getDeliveryState(), ShippingStatus.token.getCode())){
						isSubOrderEnableRefund = false;
					}*/
					
					//是否可查看券码
					subOrderDetailVo.setIsEnableSeeTicket(ticketLogic.isTicketExist(subOrderMongo.getClientId(),subOrderMongo.getSubOrderId()));
					
					//物流配送信息
					ShippingOrderMongo shippingOrderMongo = orderSupport.getShippingInfo(subOrderMongo.getOrderId()+"_"+subOrderMongo.getSubOrderId());
					if(EmptyChecker.isNotEmpty(shippingOrderMongo)){
						ShippingDetailVo shippingDetailVo = new ShippingDetailVo();
						shippingDetailVo.setTrackingNo(shippingOrderMongo.getTrackingNo());
						shippingDetailVo.setDeliveryCorpId(shippingOrderMongo.getDeliveryCorpId());
						shippingDetailVo.setDeliveryCorpName(shippingOrderMongo.getDeliveryCorpName());
						shippingDetailVo.setShippingStatus(shippingOrderMongo.getShippingStatus());
						shippingDetailVo.setShippingTime(shippingOrderMongo.getShippingTime());
						shippingDetailVo.setReceiptTime(shippingOrderMongo.getReceiptTime());
						shippingDetailVo.setRemark(shippingOrderMongo.getRemark());
						subOrderDetailVo.setShippingDetailVo(shippingDetailVo);
					}
					double subTotalMoney = 0.00;
					List<ProductDetailVo> productDetailVoList = new ArrayList<ProductDetailVo>();
					if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
						for(ProductMongo productMongo : subOrderMongo.getProducts()){
							ProductDetailVo productDetailVo = new ProductDetailVo();
							productDetailVo.setProductId(productMongo.getProductId());
							productDetailVo.setProductName(productMongo.getProductName());
							productDetailVo.setProductImage(productMongo.getProductImage());
							productDetailVo.setMoney(Arith.div(productMongo.getMoney(),1000));
							productDetailVo.setQuantity(productMongo.getQuantity());
							productDetailVo.setDeliveryState(productMongo.getDeliveryState());
							productDetailVo.setOrgCode(productMongo.getOrgCode());
							productDetailVo.setOrgName(productMongo.getOrgName());
							double vipMoney = productMongo.getVipMoney() == null ? 0 : productMongo.getVipMoney();
							productDetailVo.setVipMoney(Arith.div(vipMoney,1000));
							int vipQuantity = productMongo.getVipQuantity() == null ? 0 : productMongo.getVipQuantity();
							productDetailVo.setVipQuantity(vipQuantity);
							double deliveryMoney = productMongo.getDeliveryMoney() == null ? 0 : productMongo.getDeliveryMoney();
							productDetailVo.setDeliveryMoney(Arith.div(deliveryMoney,1000));
//							double totalMoney = Arith.add(Arith.add(Arith.mul(Arith.div(productMongo.getMoney(), 1000) , (double)productMongo.getQuantity()) ,  Arith.mul(vipMoney , (double)vipQuantity)) , (double)deliveryMoney);
							double totalMoney = Arith.div(Arith.add(Arith.add(Arith.mul(productMongo.getMoney(),productMongo.getQuantity()) ,  Arith.mul(vipMoney , vipQuantity)) , deliveryMoney),1000);
//							double totalMoney = Arith.div(productMongo.getMoney() * productMongo.getQuantity() + vipMoney * vipQuantity + deliveryMoney, 1000);
							productDetailVo.setTotalMoney(totalMoney);
							if(EmptyChecker.isNotEmpty(productMongo.getActiveId())){
								productDetailVo.setActiveId(productMongo.getActiveId());
							}
							
							if((StringUtils.equals(subOrderMongo.getType(), SubOrderType.group_merchant.getCode()) 
									&& ticketLogic.isProductConsumed(subOrderMongo.getClientId(),subOrderMongo.getMemberCode(),subOrderMongo.getSubOrderId(),productDetailVo.getProductId()))
									|| (StringUtils.equals(subOrderMongo.getType(), SubOrderType.special_merchant.getCode()) 
											&& StringUtils.equals(subOrderMongo.getDeliveryState(), ShippingStatus.receipt.getCode()))){
								productDetailVo.setIsEnableComment(true);//能否去评价
							}
							
							if(SubOrderType.boutique.getCode().equals(subOrderMongo.getType())) {
								productDetailVo.setIsEnableComment(false);//精品商城不能评价
							}
							
							
							productDetailVoList.add(productDetailVo);
							subTotalMoney += totalMoney;
							
							//可退款数量
							int canRefundCount = 0;
							/*if(isEnableRefund){//是否可退款*/
								/*isEnableRefund = EmptyChecker.isNotEmpty(enableRefoundMap) && enableRefoundMap.get(subOrderMongo.getSubOrderId() + "_"+ productMongo.getProductId()) != 0;*/
								if(EmptyChecker.isNotEmpty(enableRefoundMap)  && EmptyChecker.isNotEmpty(enableRefoundMap.get(subOrderMongo.getSubOrderId() + "_"+ productMongo.getProductId()))){
									canRefundCount = enableRefoundMap.get(subOrderMongo.getSubOrderId() + "_"+ productMongo.getProductId());
								}else{
									LogCvt.error("商品可退数量获取为空，参数orderId_subOrderId_productId：" + orderQueryCondition.getOrderId() + "_" +subOrderMongo.getSubOrderId() + "_" + productMongo.getProductId());
								}
							/*}*/
							
							//如果商品可退款，则子订单也可退款（子订单可退款时，大订也可退款）
							if(canRefundCount > 0){
								isSubOrderEnableRefund = true;
							}
							
							//可退款数量
							productDetailVo.setCanRefundCount(canRefundCount);
							
							//预售已经发货的、收货的、提货的，不可退款
							if(StringUtils.equals(SubOrderType.presell_org.getCode(), subOrderMongo.getType()) 
									&& (StringUtils.equals(productDetailVo.getDeliveryState(), ShippingStatus.shipped.getCode()) 
									|| StringUtils.equals(productDetailVo.getDeliveryState(), ShippingStatus.receipt.getCode())
									|| StringUtils.equals(productDetailVo.getDeliveryState(), ShippingStatus.token.getCode()))){
								productDetailVo.setCanRefundCount(0);
							}
							
							//名优特惠已经发货的、收货的，不可退款
							if(StringUtils.equals(SubOrderType.special_merchant.getCode(), subOrderMongo.getType()) 
									&& (StringUtils.equals(subOrderMongo.getDeliveryState(), ShippingStatus.shipped.getCode()) 
	 								|| StringUtils.equals(subOrderMongo.getDeliveryState(), ShippingStatus.receipt.getCode()))){
								isSubOrderEnableRefund = false;
								productDetailVo.setCanRefundCount(0);
							}
							
							//精品已经出库的、发货的、收货的不可退款
							if(StringUtils.equals(SubOrderType.boutique.getCode(), subOrderMongo.getType()) 
									&& (StringUtils.equals(subOrderMongo.getDeliveryState(), ShippingStatus.shipped.getCode()) 
	 								|| StringUtils.equals(subOrderMongo.getDeliveryState(), ShippingStatus.receipt.getCode())
	 								|| StringUtils.equals(subOrderMongo.getDeliveryState(), ShippingStatus.shipping.getCode()))){
								isSubOrderEnableRefund = false;
								productDetailVo.setCanRefundCount(0);
							}
							
							//已退款数量
							int alreadyRefoundCount = 0;
							if(EmptyChecker.isNotEmpty(alreadyRefoundMap)  && EmptyChecker.isNotEmpty(alreadyRefoundMap.get(subOrderMongo.getSubOrderId() + "_"+ productMongo.getProductId()))){
								alreadyRefoundCount = alreadyRefoundMap.get(subOrderMongo.getSubOrderId() + "_"+ productMongo.getProductId());
							}else{
								LogCvt.error("商品已退数量获取为空，参数orderId_subOrderId_productId：" + orderQueryCondition.getOrderId() + "_" +subOrderMongo.getSubOrderId() + "_" + productMongo.getProductId());
							}
							productDetailVo.setRefundCount(alreadyRefoundCount);
							
							//是否已评价
							String commented = EmptyChecker.isEmpty(productMongo.getCommentState()) ? "0" : productMongo.getCommentState();
							productDetailVo.setIsCommented(BooleanUtils.toBoolean(Integer.valueOf(commented)));
							
							//取机构的虚拟商户号
							String merchantId = subOrderMongo.getMerchantId();
							if(!(StringUtils.equals(subOrderMongo.getType(), SubOrderType.special_merchant.getCode()) || StringUtils.equals(subOrderMongo.getType(), SubOrderType.group_merchant.getCode()) || StringUtils.equals(subOrderMongo.getType(), SubOrderType.boutique.getCode()) )){
								Org org = commonLogic.getOrgByOrgCode(merchantId, subOrderMongo.getClientId());
								if(EmptyChecker.isEmpty(org)){
									LogCvt.error("通过机构号获取商户号失败[机构号："+merchantId+"]");
									throw new FroadBusinessException(ResultCode.failed.getCode(),"取消订单失败");
								}
								merchantId = org.getMerchantId();
							}
							
							//精品商城商品没有销售期
							if(!ProductType.boutique.getCode().equals(productMongo.getType())) {
								//商品销售期
								String startTime = null;
								String endTime = null;
								Map<String, String> productMap = orderSupport.getProduct(subOrderMongo.getClientId(), merchantId, productMongo.getProductId());
			                    if (EmptyChecker.isNotEmpty(productMap)) {
		                            startTime = productMap.get("start_time");
		                            endTime = productMap.get("end_time");
			                    }
			                    productDetailVo.setStartTime(startTime == null ? 0 : Long.valueOf(startTime));
			                    productDetailVo.setEndTime(endTime == null ? 0 : Long.valueOf(endTime));
			                    
							}
							
		                    /**
		                     * 可确认收货数量:子订单可确认收货（即子订单下商品已发货），子订单下商品未退款
		                     * 子订单发货和退款的限制规则：
		                     * 1.子订单下商品可发货（没有退款中和已退款的）都可以发货。
		                     * 2.子订单退款规则：子订单已经发货的，不可退款。
		                     */
		                    int receiveCount = 0;
		                    if(subOrderDetailVo.isEnableConfirmReceive == true){
		                    	receiveCount = productMongo.getVipQuantity() + productMongo.getQuantity() - alreadyRefoundCount;
		                    }
		                    productDetailVo.setCanReceiveCount(receiveCount);
						}
					}
					//纯红包支付的订单不允许退款
					if(isRedPacketOrder){
						isOrderEnableRefund = false;
						isSubOrderEnableRefund = false;
					}
					subOrderDetailVo.setIsEnableRefund(isSubOrderEnableRefund);
					subOrderDetailVo.setSubTotalMoney(subTotalMoney);
					subOrderDetailVo.setProductDetailVoList(productDetailVoList);
					subOrderDetailVoList.add(subOrderDetailVo);
					if(isSubOrderEnableRefund){
						isOrderEnableRefund = true;
					}
				}
			}
			
			//秒杀订单
			DeliveryFlag deliveryFlag = null;
			boolean isSeckill = BooleanUtils.toBoolean(order.getIsSeckill());
			orderDetailVo.setIsSeckill(isSeckill);
			if(isSeckill){
				if(EmptyChecker.isNotEmpty(subOrderList)){
					if(EmptyChecker.isNotEmpty(order.getRecvId()) && EmptyChecker.isNotEmpty(order.getDeliverId())){
						deliveryFlag = DeliveryFlag.FILLED;
					}else{
						if(StringUtils.equals(subOrderList.get(0).getType(), SubOrderType.special_merchant.getCode())){
							deliveryFlag = DeliveryFlag.HOME;
						}
						if(StringUtils.equals(subOrderList.get(0).getType(), SubOrderType.group_merchant.getCode())){
							deliveryFlag = DeliveryFlag.FILLED;
						}
						if(StringUtils.equals(subOrderList.get(0).getType(), SubOrderType.presell_org.getCode())){
							if(StringUtils.equals(subOrderList.get(0).getProducts().get(0).getDeliveryOption(), DeliveryType.home_or_take.getCode())){
								deliveryFlag = DeliveryFlag.TAKE_HOME;
							}else if(StringUtils.equals(subOrderList.get(0).getProducts().get(0).getDeliveryOption(), DeliveryType.take.getCode())){
								deliveryFlag = DeliveryFlag.TAKE;
							}else if(StringUtils.equals(subOrderList.get(0).getProducts().get(0).getDeliveryOption(), DeliveryType.home.getCode())){
								deliveryFlag = DeliveryFlag.HOME;
							}
						}
					} 
				}
			}
			if(EmptyChecker.isNotEmpty(deliveryFlag)){
				orderDetailVo.setDeliveryFlag(deliveryFlag.getCode());
			}
			
			orderDetailVo.setIsEnableRefund(isOrderEnableRefund);
			orderDetailVo.setSubOrderDetailVoList(subOrderDetailVoList);
			
			System.out.println(JSON.toJSONString(orderDetailVo,true));
			
			result = new ResultBean(ResultCode.success, "获取订单详情成功", orderDetailVo);
		}catch(Exception e){
			LogCvt.error("获取订单详情-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"获取订单详情失败");
		}
		return result;
	}

	@Override
	public ResultBean deleteOrder(String clientId,String orderId) {
		ResultBean result = new ResultBean(ResultCode.success,"取消订单成功");
		try {
			OrderMongo orderMongo = orderSupport.getOrderByOrderId(clientId,orderId);
			
			//是否退款
			boolean isRefund = false;
			
			//订单创建、支付中、支付失败(非面对面订单)
			if(EmptyChecker.isNotEmpty(orderMongo)){
				if(((StringUtils.equals(orderMongo.getOrderStatus(), OrderStatus.create.getCode())
						|| StringUtils.equals(orderMongo.getOrderStatus(), OrderStatus.paying.getCode()))
						&& StringUtils.equals(orderMongo.getState(), OrderState.NORMAL.getCode()))
						|| (StringUtils.equals(orderMongo.getOrderStatus(), OrderStatus.create.getCode()) 
								&& StringUtils.equals(orderMongo.getState(), OrderState.RETURNED.getCode()))
						|| StringUtils.equals(orderMongo.getOrderStatus(), OrderStatus.payfailed.getCode())){
					
					//支付中
					if(StringUtils.equals(orderMongo.getOrderStatus(), OrderStatus.paying.getCode())){
						LogCvt.info("[取消订单]-订单支付中-取消");
						//积分支付不能取消
						if(StringUtils.equals(orderMongo.getPaymentMethod(),PaymentMethod.bankPoints.getCode()) 
								|| StringUtils.equals(orderMongo.getPaymentMethod(),PaymentMethod.froadPoints.getCode())){
							LogCvt.error("[取消订单]-积分支付的订单不允许取消！[订单号："+orderId+"]");
							throw new FroadBusinessException(ResultCode.failed.getCode(),"积分支付的订单无法取消");
						}
						//积分+现金支付要退款
						if(StringUtils.equals(orderMongo.getPaymentMethod(),PaymentMethod.froadPointsAndCash.getCode()) 
								|| StringUtils.equals(orderMongo.getPaymentMethod(),PaymentMethod.bankPointsAndCash.getCode())){
							LogCvt.info("[取消订单]-现金+积分支付的订单，调用退款API");
							isRefund = true;
						}
						if(StringUtils.equals(orderMongo.getPaymentMethod(),PaymentMethod.creditPoints.getCode())){
							LogCvt.error("[取消订单]-支付方式为赠送积分的订单不允许取消！[订单号："+orderId+"]");
							throw new FroadBusinessException(ResultCode.failed.getCode(),"赠送积分的订单无法取消");
						}
						if(StringUtils.equals(orderMongo.getPaymentMethod(),PaymentMethod.invalid.getCode())){
							LogCvt.error("[取消订单]-支付方式无效，订单不允许取消！[订单号："+orderId+"]");
							throw new FroadBusinessException(ResultCode.failed.getCode(),"该订单支付方式无效，无法取消");
						}
					}
					
					//用户关闭订单
					OrderMongo order = new OrderMongo();
					order.setOrderId(orderId);
					order.setClientId(clientId);
					order.setOrderStatus(OrderStatus.closed.getCode());
					order.setState(OrderState.SYSTEM_CLOSED.getCode());
					boolean flag = orderSupport.updateOrderByCondion(order);
					if (!flag) {
						LogCvt.error("[取消订单]-取消订单失败！[订单号："+orderId+"]");
						throw new FroadBusinessException(ResultCode.failed.getCode(),"取消订单失败");
					}
					
					//VIP订单，订单关闭时，更新缓存
					if(EmptyChecker.isNotEmpty(orderMongo.getIsVipOrder()) && orderMongo.getIsVipOrder() == 1){
						RedisCommon.updateUserVIPOrderRedis(clientId,orderMongo.getMemberCode(),false);
					}
					
					//库存
					List<Store> storeList = new ArrayList<Store>();
					//取消订单成功，更新预售商品门店已提货数量（减提货数量）
					List<SubOrderMongo> subOrderList = orderSupport.getSubOrderListByOrderId(clientId,orderId);
					if(EmptyChecker.isNotEmpty(subOrderList)){
						for(SubOrderMongo subOrderMongo : subOrderList){
							//取机构的虚拟商户号
							String merchantId = subOrderMongo.getMerchantId();
							if(!(StringUtils.equals(subOrderMongo.getType(), SubOrderType.special_merchant.getCode()) || StringUtils.equals(subOrderMongo.getType(), SubOrderType.group_merchant.getCode()) || StringUtils.equals(subOrderMongo.getType(), SubOrderType.boutique.getCode()))){
								Org org = commonLogic.getOrgByOrgCode(merchantId, clientId);
								if(EmptyChecker.isEmpty(org)){
									LogCvt.error("通过机构号获取商户号失败[机构号："+merchantId+"]");
									throw new FroadBusinessException(ResultCode.failed.getCode(),"取消订单失败");
								}
								merchantId = org.getMerchantId();
							}
							
							//还库存、减预售提货数量、减用户购买记录
							if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
								for(ProductMongo productMongo : subOrderMongo.getProducts()){
									//库存操作
									//状态正常时，才还库存
									if(StringUtils.equals(orderMongo.getState(), OrderState.NORMAL.getCode())){
										Store storeInfo = new Store();
										storeInfo.setClientId(subOrderMongo.getClientId());
										storeInfo.setMerchantId(merchantId);
										storeInfo.setProductId(productMongo.getProductId());
										storeInfo.setReduceStore(productMongo.getQuantity() + productMongo.getVipQuantity());
										storeList.add(storeInfo);
									}
									
									//预售自提商品，更新已提货数量（变更：20150608 秒杀不用更新）
									if(orderMongo.getIsSeckill() != 1 && StringUtils.equals(ProductType.presell.getCode(),(productMongo.getType())) && StringUtils.equals(DeliveryType.take.getCode(),productMongo.getDeliveryOption())){
										boolean processFlag = orderSupport.processPresellOutletTokenCountRedis(subOrderMongo.getClientId(),productMongo.getOrgCode(),productMongo.getProductId(),-(productMongo.getQuantity()+productMongo.getVipQuantity()));
										if(!processFlag){
											LogCvt.error("[取消订单]-更新门店某商品已提货数量失败 [商品id:"+productMongo.getProductId() + ",更新数量:"+ (-(productMongo.getQuantity()+productMongo.getVipQuantity()))+ "]");
										}
									}
									
									//减用户购买记录（变更：20150608 秒杀时减少用户秒杀购买记录）
									if(orderMongo.getIsSeckill() == 1){
										orderSupport.subtractMemberBuyHistoryForSeckill(clientId, orderMongo.getMemberCode(), productMongo.getProductId(), Long.valueOf(productMongo.getQuantity()), Long.valueOf(productMongo.getVipQuantity()));
									}else{
										orderSupport.subtractMemberBuyHistory(clientId, orderMongo.getMemberCode(), productMongo.getProductId(), Long.valueOf(productMongo.getQuantity()), Long.valueOf(productMongo.getVipQuantity()));
									}
								}
							}
						}
					}
					result = new ResultBean(ResultCode.success,"取消订单成功",new Object[]{storeList,isRefund,orderMongo});
				}else{
					LogCvt.error("[取消订单]-无法取消订单！[订单号："+orderId+"]，原因：[订单状态："+OrderStatus.getType(orderMongo.getOrderStatus()).getDescribe()+" 数据状态"+OrderState.getType(orderMongo.getState()).getDescription()+"]");
					throw new FroadBusinessException(ResultCode.failed.getCode(),"操作失败，请刷新页面或重试");
				}
			}
		}catch(FroadBusinessException e){
			LogCvt.error("关闭订单-业务异常："+e.getMsg(),e);
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		
		try {
			//大数据平台-调用日志
			OrderMongo order = orderSupport.getOrderById(clientId, orderId);
			List<SubOrderMongo> subOrderList = orderSupport.getSubOrderListByOrderId(clientId, orderId);
			createOrderLog(order, subOrderList, "ORDERMODIFY");
			
			//订单关联营销活动时，调用营销平台回退活动资格
			if(StringUtils.equals(order.getIsActive(), "1")){
				closeMarketOrder(order,false);
			}
		} catch (Exception e) {
			LogCvt.error("取消订单-调用数据平台|调用营销平台-异常：" + e.getMessage(), e);
		}
		
		return result;
	}

	@Override
	public ResultBean shippingOrder(ShippingOrderMongo shippingOrderMongo) {
		LogCvt.info("Mongo：订单发货");
		
		ResultBean result = null;
		
		ShippingOrderMongo shippingInfo = orderSupport.getShippingInfo(shippingOrderMongo.getId());
		if(EmptyChecker.isNotEmpty(shippingInfo)) {
		    boolean isUpdate = orderSupport.updateShippingInfo(shippingOrderMongo);
		    LogCvt.info("修改发货信息结果：" + isUpdate);
		    if(isUpdate) {
		        result = new ResultBean(ResultCode.success, "修改发货信息成功");
		    } else {
		        result = new ResultBean(ResultCode.failed, "修改发货信息失败");
		    }
		} else {
		    LogCvt.info("商户发货：" + JSonUtil.toJSonString(shippingOrderMongo));
    //		修改为已发货
    		shippingOrderMongo.setShippingStatus(ShippingStatus.shipped.getCode());
    		shippingOrderMongo.setShippingTime(System.currentTimeMillis());
    		
    		result = new ResultBean(ResultCode.success,"Mongo订单发货成功");
    		try {
    			boolean flag = orderSupport.addShippingInfo(shippingOrderMongo);
    			if(!flag){
    				result = new ResultBean(ResultCode.failed, "订单发货失败");
    			}
    		}catch(Exception e){
    			LogCvt.error("订单发货-系统异常："+e.getMessage(),e);
    			return new ResultBean(ResultCode.failed,"订单发货失败");
    		}
		}
		return result;
	}
	
	@Override
	public ResultBean receiptOrder(ShippingOrderMongo shippingOrderMongo) {
		//发货状态修改为收货状态
	    ResultBean rb = new ResultBean(ResultCode.success);
	    boolean isUpdate = orderSupport.updateShippingInfoOfShippedToReceiptStatus(shippingOrderMongo);
	    if(!isUpdate) {
	        // 收货失败
	        rb = new ResultBean(ResultCode.failed, "订单收货失败");
	    } else {
	    	//收货成功情况下，修改子订单状态
	        boolean bool = orderSupport.receiptSubOrder(shippingOrderMongo.getId().split("_")[0],shippingOrderMongo.getId().split("_")[1]);
	        LogCvt.info("子订单收货状态修改结果：" + bool);
	    }
	    return rb;
	}

	@Override
	public ResultBean addQrcodeOrder(OrderMongo orderMongo) {
		LogCvt.info("Mongo：面对面支付订单创建");
		ResultBean result = new ResultBean(ResultCode.success,"面对面支付订单创建成功");
		try {
			//更新二维码状态
			if(EmptyChecker.isEmpty(orderMongo)){
				LogCvt.error("orderMongo为null");
			}
			if(EmptyChecker.isEmpty(orderMongo.getClientId())){
				LogCvt.error("ClientId为null");
			}
			if(EmptyChecker.isEmpty(orderMongo.getQrcode())){
				LogCvt.error("Qrcode为null");
			}
			if(EmptyChecker.isEmpty(orderSupport)){
				LogCvt.error("orderSupport为null");
			}
			
			orderSupport.updateQrcodeState(orderMongo.getClientId(),orderMongo.getQrcode(),"0");//设置为失效
			orderMongo.setIsQrcode(BooleanUtils.toInteger(true));
			boolean orderResult = orderSupport.addOrder(orderMongo);
			if (!orderResult) {
				orderSupport.updateQrcodeState(orderMongo.getClientId(),orderMongo.getQrcode(),"1");//设置为有效
				return new ResultBean(ResultCode.failed,"面对面支付订单创建失败");
			}
			result = new ResultBean(ResultCode.success,"面对面支付订单创建成功",orderMongo.getOrderId());
		}catch(Exception e){
			LogCvt.error("面对面支付订单创建-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"面对面支付订单创建失败");
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultBean getQrcodeOrderSummary(PageEntity<OrderQueryCondition> pageParam) {
		LogCvt.info("Mongo：获取面对面支付订单概要");
		ResultBean result = new ResultBean(ResultCode.success,"获取面对面支付订单概要成功");
		try {
			MongoPage page = orderSupport.getQrcodeOrderSummaryList(pageParam);
			List<QrcodeOrderSummaryVo> list = new ArrayList<QrcodeOrderSummaryVo>();
			List<OrderMongo> orderList = (List<OrderMongo>) page.getItems();
			long firstRecordTime = 0l;
			long lastRecordTime = 0l;
			if(EmptyChecker.isNotEmpty(page.getItems())){
				firstRecordTime = orderList.get(0).getCreateTime();
				lastRecordTime = orderList.get(orderList.size()-1).getCreateTime();
				for(OrderMongo order : orderList){
					QrcodeOrderSummaryVo qrcodeOrderSummaryVo = new QrcodeOrderSummaryVo();
					qrcodeOrderSummaryVo.setOrderId(order.getOrderId());
					qrcodeOrderSummaryVo.setOrderStatus(order.getOrderStatus());
					qrcodeOrderSummaryVo.setRealPrice(Arith.div(order.getRealPrice(), 1000));
					qrcodeOrderSummaryVo.setCreateTime(order.getCreateTime());
					qrcodeOrderSummaryVo.setMerchantId(order.getMerchantId());
					qrcodeOrderSummaryVo.setMerchantName(order.getMerchantName());
					if(EmptyChecker.isNotEmpty(order.getIsPrefPay()) && order.getIsPrefPay() == 1){
						qrcodeOrderSummaryVo.setTotalPrice(EmptyChecker.isEmpty(order.getConsumeMoney())? 0 : Arith.div(order.getConsumeMoney(), 1000));
		            }else{
		            	Integer cutMoney = order.getCutMoney() == null ? 0 : order.getCutMoney();
		            	qrcodeOrderSummaryVo.setTotalPrice(Arith.div(order.getTotalPrice()+cutMoney, 1000));
		            }
					qrcodeOrderSummaryVo.setPaymentTime(order.getPaymentTime() == null ? 0L :order.getPaymentTime());
					qrcodeOrderSummaryVo.setOutletId(EmptyChecker.isEmpty(order.getOutletId())? "" : order.getOutletId());
					
					qrcodeOrderSummaryVo.setOutletName(EmptyChecker.isEmpty(order.getOutletName())? "" : order.getOutletName());
					if(StringUtils.equals(order.getOrderStatus(), OrderStatus.create.getCode()) || StringUtils.equals(order.getOrderStatus(), OrderStatus.payfailed.getCode())){
						qrcodeOrderSummaryVo.setIsEnableCancel(true);//是否可取消
						qrcodeOrderSummaryVo.setIsEnablePay(true);//是否可支付
					}
					if(StringUtils.equals(order.getState(), OrderState.SYSTEM_CLOSED.getCode())){
						qrcodeOrderSummaryVo.setIsEnableCancel(false);//是否可取消
						qrcodeOrderSummaryVo.setIsEnablePay(false);//是否可支付
					}
					//订单支付中+非积分支付的订单可取消
					if(StringUtils.equals(order.getOrderStatus(), OrderStatus.paying.getCode()) 
							&& !(StringUtils.equals(order.getPaymentMethod(), PaymentMethod.bankPoints.getCode())
									|| StringUtils.equals(order.getPaymentMethod(), PaymentMethod.froadPoints.getCode())
									|| StringUtils.equals(order.getPaymentMethod(), PaymentMethod.creditPoints.getCode())
									|| StringUtils.equals(order.getPaymentMethod(), PaymentMethod.invalid.getCode()))){
						qrcodeOrderSummaryVo.setIsEnableCancel(true);//是否可取消
					}
					
					list.add(qrcodeOrderSummaryVo);
				}
			}
			PageVo pageVo = new PageVo();
			pageVo.setPageCount(page.getPageCount());
			pageVo.setPageNumber(page.getPageNumber());
			pageVo.setPageSize(page.getPageSize());
			pageVo.setTotalCount(page.getTotalCount());
			pageVo.setFirstRecordTime(firstRecordTime);
			pageVo.setLastRecordTime(lastRecordTime);
			pageVo.setLastPageNumber(page.getPageNumber());
			pageVo.setHasNext(page.getPageCount()>page.getPageNumber());
			result = new ResultBean(ResultCode.success,"获取面对面支付订单概要成功",new Object[]{list,pageVo});
		}catch(Exception e){
			LogCvt.error("获取面对面支付订单概要-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"获取面对面支付订单概要失败");
		}
		return result;
	}

	@Override
	public ResultBean getQrcodeOrderDetail(OrderQueryCondition orderQueryCondition) {
		LogCvt.info("Mongo：获取面对面支付订单详情");
		ResultBean result = new ResultBean(ResultCode.success,"获取面对面支付订单详情成功");
		try {
			String paymentChannel = paymentSupport.queryOrderPaymentChannel(orderQueryCondition.getOrderId());
			QrcodeOrderDetailVo qrcodeOrderDetailVo = orderSupport.getQrcodeOrderDetail(orderQueryCondition,paymentChannel);
			//取商户图片
			MerchantDetail merchantDetail = orderSupport.getMerchantDetail(orderQueryCondition.getClientId(), qrcodeOrderDetailVo.getMerchantId());
			if(EmptyChecker.isNotEmpty(merchantDetail)){
				qrcodeOrderDetailVo.setMerchantImg(merchantDetail.getLogo());
			}
			result = new ResultBean(ResultCode.success,"获取面对面支付订单详情成功",qrcodeOrderDetailVo);
		}catch(Exception e){
			LogCvt.error("获取面对面支付订单详情-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"获取面对面支付订单详情失败");
		}
		return result;
	}
	
	@Override
	public ResultBean updateOrderForPay(OrderMongo order){
		LogCvt.info("Mongo：更新订单");
		OrderLogger.info("订单模块", "支付后修改订单", "请求参数："+JSON.toJSONString(order), null);
		ResultBean result = new ResultBean(ResultCode.success,"更新订单成功");
		try {
			dataEmptyChecker(order, "订单修改参数不能为空");
			dataEmptyChecker(order.getOrderId(), "订单修改参数不能为空");
			dataEmptyChecker(order.getClientId(), "客户端ID不能为空");
			updateQrcodeOrderState(order);
			boolean flag = orderSupport.updateOrderByCondion(order);
			if(!flag){
				LogCvt.error("更新订单失败");
				return new ResultBean(ResultCode.failed,"更新订单失败");
			}
		}catch(Exception e){
			LogCvt.error("更新订单-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"更新订单失败");
		}
		
		try {
			//大数据平台-调用日志
			OrderMongo orderMongo = orderSupport.getOrderById(order.getClientId(), order.getOrderId());
			List<SubOrderMongo> subOrderList = orderSupport.getSubOrderListByOrderId(order.getClientId(), order.getOrderId());
			if(StringUtils.equals(order.getOrderStatus(), OrderStatus.paysuccess.getCode())){
				createOrderLog(orderMongo, subOrderList, "ORDERPAYSUCCESS");
			}else{
				createOrderLog(orderMongo, subOrderList, "ORDERMODIFY");
			}
		} catch (Exception e) {
			LogCvt.error("更新订单-数据平台打印日志异常：" + e.getMessage(), e);
		}
		
		return result;
	}
	
	private void updateQrcodeOrderState(OrderMongo orderMongo){
		//面对面订单支付失败，系统关闭时，将二维码设置为有效，可以继续扫码
		OrderLogger.info("订单模块", "修改面对面订单二维码缓存状态为有效", "请求参数："+JSON.toJSONString(orderMongo), null);
		if(orderMongo.getIsQrcode() == 1 && StringUtils.equals(orderMongo.getOrderStatus(), OrderStatus.sysclosed.getCode())){
			orderSupport.updateQrcodeState(orderMongo.getClientId(),orderMongo.getQrcode(),"1");//设置为有效
		}
	}
	
	@Override
	public ResultBean updateProductSellCount(OrderMongo order){
		long startTime = System.currentTimeMillis();
//		LogCvt.info("Mongo：更新商品销售数量");
		ResultBean result = new ResultBean(ResultCode.success,"更新商品销售数量成功");
		try {
			dataEmptyChecker(order, "订单参数不能为空");
			dataEmptyChecker(order.getOrderId(), "订单ID参数不能为空");
			dataEmptyChecker(order.getClientId(), "客户端ID参数不能为空");
			List<SubOrderMongo> subOrderMongoList = orderSupport.getSubOrderListByOrderId(order.getClientId(),order.getOrderId());
			if(EmptyChecker.isNotEmpty(subOrderMongoList)){
				List<Product> productList = new ArrayList<Product>();
				List<ProductMonthCount> productMonthCountList = new ArrayList<ProductMonthCount>();
				for(SubOrderMongo subOrderMongo : subOrderMongoList){
					List<ProductMongo> productMongoList = subOrderMongo.getProducts();
					if(EmptyChecker.isNotEmpty(productMongoList)){
						for(ProductMongo productMongo : productMongoList){
							Product product = new Product();
					        product.setClientId(subOrderMongo.getClientId());
					        product.setProductId(productMongo.getProductId());
					        product.setSellCount(productMongo.getQuantity()+productMongo.getVipQuantity());
					        product.setMerchantId(subOrderMongo.getMerchantId());
					        product.setType(productMongo.getType());
					        productList.add(product);
					        
					        if(StringUtils.equals(ProductType.group.getCode(), product.getType())){
					        	Calendar ca =  Calendar.getInstance();
					        	ProductMonthCount productMonthCount = new ProductMonthCount();
					        	productMonthCount.setYear(Integer.toString(ca.get(Calendar.YEAR)));
					        	String month = Integer.toString(ca.get(Calendar.MONTH)+1);
					        	if(month.length()==1){
					        		month = "0" + month;
					        	}
					        	productMonthCount.setMonth(month);
					        	productMonthCount.setProductId(productMongo.getProductId());
					        	productMonthCount.setMerchantId(subOrderMongo.getMerchantId());
					        	productMonthCount.setClientId(subOrderMongo.getClientId());
					        	productMonthCount.setSellCount(productMongo.getQuantity()+productMongo.getVipQuantity());
					        	productMonthCount.setSellMoney(Arith.mul(productMongo.getMoney(), productMongo.getQuantity()) + Arith.mul(productMongo.getVipMoney(), productMongo.getVipQuantity()));
					        	productMonthCountList.add(productMonthCount);
					        }
						}
					}
				}
				boolean flag = orderSupport.updateProductSellCount(productList,productMonthCountList);
//		        LogCvt.info("更新商品销售数量"+ (flag ? "成功":"失败") + "[product_id:"+product.getProductId()+" 数量："+product.getSellCount()+"]");
			}
		}catch(Exception e){
			LogCvt.error("更新商品销售数量-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"更新商品销售数量失败");
		}
		LogCvt.info("[更新商品销售数量  updateProductSellCount 耗时]--------------------------: " + (System.currentTimeMillis() - startTime));
		return result;
	}
	
	@Override
	public ResultBean returnProductSellCount(List<Product> products){
		long startTime = System.currentTimeMillis();
//		LogCvt.info("Mongo：更新商品销售数量");
		ResultBean result = new ResultBean(ResultCode.success,"返还商品销售数量成功");
		try {
			dataEmptyChecker(products, "参数不能为空");
			List<Product> productList = new ArrayList<Product>();
			List<ProductMonthCount> productMonthCountList = new ArrayList<ProductMonthCount>();
			for(Product productParam : products){
				dataEmptyChecker(productParam, "订单参数不能为空");
				dataEmptyChecker(productParam.getClientId(), "客户端ID参数不能为空");
				dataEmptyChecker(productParam.getProductId(), "商品ID参数不能为空");
				dataEmptyChecker(productParam.getSellCount(), "商品销量参数不能为空");
				dataEmptyChecker(productParam.getType(), "商品类型参数不能为空");
				
				Product product = new Product();
		        product.setClientId(productParam.getClientId());
		        product.setProductId(productParam.getProductId());
		        product.setSellCount(productParam.getSellCount());
		        product.setMerchantId(productParam.getMerchantId());
		        product.setType(productParam.getType());
		        productList.add(product);
		        
		        if(StringUtils.equals(ProductType.group.getCode(), product.getType())){
		        	dataEmptyChecker(productParam.getCreateTime(), "支付时间参数不能为空");
					dataEmptyChecker(productParam.getCost(), "销售金额参数不能为空");
					dataEmptyChecker(productParam.getMerchantId(), "商户ID参数不能为空");
		        	
		        	Calendar ca =  Calendar.getInstance();
		        	ca.setTime(productParam.getCreateTime());
		        	ProductMonthCount productMonthCount = new ProductMonthCount();
		        	productMonthCount.setYear(Integer.toString(ca.get(Calendar.YEAR)));
		        	String month = Integer.toString(ca.get(Calendar.MONTH)+1);
		        	if(month.length()==1){
		        		month = "0" + month;
		        	}
		        	productMonthCount.setMonth(month);
		        	productMonthCount.setProductId(productParam.getProductId());
		        	productMonthCount.setMerchantId(productParam.getMerchantId());
		        	productMonthCount.setClientId(productParam.getClientId());
		        	productMonthCount.setSellCount(productParam.getSellCount());
		        	productMonthCount.setSellMoney(productParam.getCost());
		        	productMonthCountList.add(productMonthCount);
		        }
				boolean flag = orderSupport.updateProductSellCount(productList,productMonthCountList);
//		        LogCvt.info("更新商品销售数量"+ (flag ? "成功":"失败") + "[product_id:"+product.getProductId()+" 数量："+product.getSellCount()+"]");
			}
		}catch(Exception e){
			LogCvt.error("返还商品销售数量-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"返还商品销售数量失败");
		}
		LogCvt.info("[返还商品销售数量  returnProductSellCount 耗时]--------------------------: " + (System.currentTimeMillis() - startTime));
		return result;
	}
	
	@Override
	public ResultBean updateSeckillProductSellCount(OrderMongo order){
		LogCvt.info("[秒杀模块]-更新秒杀商品销量: order_id:" + order.getOrderId());
		long startTime = System.currentTimeMillis();
		ResultBean result = new ResultBean(ResultCode.success,"更新秒杀商品销售数量成功");
		try {
			dataEmptyChecker(order, "订单参数不能为空");
			dataEmptyChecker(order.getOrderId(), "订单ID参数不能为空");
			dataEmptyChecker(order.getClientId(), "客户端ID参数不能为空");
			List<SubOrderMongo> subOrderMongoList = orderSupport.getSubOrderListByOrderId(order.getClientId(),order.getOrderId());
			if(EmptyChecker.isNotEmpty(subOrderMongoList)){
				List<ProductSeckill> productList = new ArrayList<ProductSeckill>();
				List<ProductMonthCount> productMonthCountList = new ArrayList<ProductMonthCount>();
				for(SubOrderMongo subOrderMongo : subOrderMongoList){
					List<ProductMongo> productMongoList = subOrderMongo.getProducts();
					if(EmptyChecker.isNotEmpty(productMongoList)){
						for(ProductMongo productMongo : productMongoList){
							ProductSeckill product = new ProductSeckill();
					        product.setClientId(subOrderMongo.getClientId());
					        product.setProductId(productMongo.getProductId());
					        product.setTrueBuyerNumber(productMongo.getQuantity()+productMongo.getVipQuantity());
					        product.setMerchantId(subOrderMongo.getMerchantId());
					        product.setType(productMongo.getType());
					        productList.add(product);
					        
					        if(StringUtils.equals(ProductType.group.getCode(), product.getType())){
					        	Calendar ca =  Calendar.getInstance();
					        	ProductMonthCount productMonthCount = new ProductMonthCount();
					        	productMonthCount.setYear(Integer.toString(ca.get(Calendar.YEAR)));
					        	String month = Integer.toString(ca.get(Calendar.MONTH)+1);
					        	if(month.length()==1){
					        		month = "0" + month;
					        	}
					        	productMonthCount.setMonth(month);
					        	productMonthCount.setProductId(productMongo.getProductId());
					        	productMonthCount.setMerchantId(subOrderMongo.getMerchantId());
					        	productMonthCount.setClientId(subOrderMongo.getClientId());
					        	productMonthCount.setSellCount(productMongo.getQuantity()+productMongo.getVipQuantity());
					        	productMonthCount.setSellMoney(Arith.mul(productMongo.getMoney(), productMongo.getQuantity()) + Arith.mul(productMongo.getVipMoney(), productMongo.getVipQuantity()));
					        	productMonthCountList.add(productMonthCount);
					        }
						}
					}
				}
				boolean flag = orderSupport.updateSeckillProductSellCount(productList,productMonthCountList);
//		        LogCvt.info("更新商品销售数量"+ (flag ? "成功":"失败") + "[product_id:"+product.getProductId()+" 数量："+product.getSellCount()+"]");
			}
		}catch(Exception e){
			LogCvt.error("更新商品销售数量-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"更新商品销售数量失败");
		}
		LogCvt.info("[更新商品销售数量  updateProductSellCount 耗时]--------------------------: " + (System.currentTimeMillis() - startTime));
		return result;
	}
	
    @Override
    public boolean updateOrderOfStatusByOrderId(String orderId, String orderStatus, String orderState) {
        return orderSupport.updateOrderOfStatusByOrderId(orderId, orderStatus, orderState);
    }
    
    @Override
    public boolean updateOrderStatusByOrderId(String orderId) {
        return orderSupport.updateOrderStatusByOrderId(orderId);
    }
	

	@Override
	public ResultBean getVipDiscount(OrderQueryCondition orderQueryCondition) {
		LogCvt.info("Redis：获取VIP优惠金额");
		try {
			dataEmptyChecker(orderQueryCondition.getClientId(), "客户端ID不能为空");
			dataEmptyChecker(orderQueryCondition.getMemberCode(), "会员ID不能为空");
			String vipDiscount = orderSupport.getVipDiscount(orderQueryCondition.getClientId(),orderQueryCondition.getMemberCode());
			return new ResultBean(ResultCode.success,"Redis：获取VIP优惠金额成功", vipDiscount == null ? 0 : Arith.div(Double.valueOf(vipDiscount), 1000));
		}catch(Exception e){
			LogCvt.error("获取VIP优惠金额-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"获取VIP优惠金额失败");
		}
	}
	
	@Override
	public ResultBean getPointExchange(PageEntity<OrderQueryCondition> pageParam) {
		LogCvt.info("Mongo：获取积分兑换列表");
		ResultBean result = new ResultBean(ResultCode.success,"获取积分兑换列表成功");
		try {
			MongoPage page = orderSupport.getPointsOrderListOfPage(pageParam);
			List<PointExchangeVo> list = new ArrayList<PointExchangeVo>();
			List<SubOrderMongo> subOrderMongoList = (List<SubOrderMongo>) page.getItems();
			long firstRecordTime = 0l;
			long lastRecordTime = 0l;
			if(EmptyChecker.isNotEmpty(subOrderMongoList)){
				firstRecordTime = subOrderMongoList.get(0).getCreateTime();
				lastRecordTime = subOrderMongoList.get(subOrderMongoList.size()-1).getCreateTime();
				for(SubOrderMongo subOrderMongo : subOrderMongoList){
					PointExchangeVo pointExchangeVo = new PointExchangeVo();
					pointExchangeVo.setProductId(subOrderMongo.getProducts().get(0).getProductId());
					pointExchangeVo.setProductName(subOrderMongo.getProducts().get(0).getProductName());
					pointExchangeVo.setProductImage(subOrderMongo.getProducts().get(0).getProductImage());
					pointExchangeVo.setMoney(Arith.div(subOrderMongo.getProducts().get(0).getMoney(), 1000));
					pointExchangeVo.setQuantity(subOrderMongo.getProducts().get(0).getQuantity());
					pointExchangeVo.setOrderStatus(subOrderMongo.getOrderStatus());
					pointExchangeVo.setExchangeTime(subOrderMongo.getCreateTime());
					pointExchangeVo.setOrderId(subOrderMongo.getOrderId());
					list.add(pointExchangeVo);
				}
			}
			PageVo pageVo = new PageVo();
			pageVo.setPageCount(page.getPageCount());
			pageVo.setPageNumber(page.getPageNumber());
			pageVo.setPageSize(page.getPageSize());
			pageVo.setTotalCount(page.getTotalCount());
			pageVo.setFirstRecordTime(firstRecordTime);
			pageVo.setLastRecordTime(lastRecordTime);
			pageVo.setLastPageNumber(page.getPageNumber());
			pageVo.setHasNext(page.getPageCount()>page.getPageNumber());
			result = new ResultBean(ResultCode.success,"获取订单概要成功",new Object[]{list,pageVo});
		}catch(Exception e){
			LogCvt.error("获取积分兑换列表-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"获取积分兑换列表失败");
		}
		return result;
	}

	@Override
	public ResultBean getPointExchangeDetail(OrderQueryCondition orderQueryCondition) {
		LogCvt.info("Mongo：获取积分兑换订单详情");
		ResultBean result = new ResultBean(ResultCode.success,"获取积分兑换订单详情成功");
		try {
			SubOrderMongo subOrder = orderSupport.getPointsOrderDetail(orderQueryCondition);
			PointExchangeVo pointExchangeVo = new PointExchangeVo();
			pointExchangeVo.setProductId(subOrder.getProducts().get(0).getProductId());
			pointExchangeVo.setProductName(subOrder.getProducts().get(0).getProductName());
			pointExchangeVo.setProductImage(subOrder.getProducts().get(0).getProductImage());
			pointExchangeVo.setMoney(Arith.div(subOrder.getProducts().get(0).getMoney(), 1000));
			pointExchangeVo.setQuantity(subOrder.getProducts().get(0).getQuantity());
			pointExchangeVo.setOrderStatus(subOrder.getOrderStatus());
			pointExchangeVo.setExchangeTime(subOrder.getCreateTime());
			pointExchangeVo.setOrderId(subOrder.getOrderId());
			pointExchangeVo.setOrderStatus(subOrder.getOrderStatus());
			String type = StringUtils.equals(SubOrderType.online_points_org.getCode(), subOrder.getType()) ? "0" : "1";
			pointExchangeVo.setType(type);
			pointExchangeVo.setOrgCode(subOrder.getProducts().get(0).getOrgCode());
			pointExchangeVo.setOrgName(subOrder.getProducts().get(0).getOrgName());
			result = new ResultBean(ResultCode.success,"获取积分兑换订单详情成功",pointExchangeVo);
		}catch(Exception e){
			LogCvt.error("获取积分兑换列表-系统异常："+ e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"获取积分兑换列表失败");
		}
		return result;
	}
	

	@Override
	public ResultBean getDeliverInfoDetail(String clientId,long memberCode,String deliveryId,String recvId) {
		LogCvt.info("Mongo：获取提货信息详情");
		ResultBean result = new ResultBean(ResultCode.success,"获取提货信息详情成功");
		try {
			
			//提货人|收货人详情
			//配送
			DeliverInfoDetailVo deliverInfoDetailVo = new DeliverInfoDetailVo();
			//提货人
			if(EmptyChecker.isNotEmpty(deliveryId)){
				RecvInfo deliverInfo = orderSupport.getRecvInfo(clientId, memberCode, deliveryId);
				if(EmptyChecker.isNotEmpty(deliverInfo)){
					deliverInfoDetailVo.setConsignee(deliverInfo.getConsignee());
					deliverInfoDetailVo.setPhone(deliverInfo.getPhone());
					deliverInfoDetailVo.setAddress(deliverInfo.getAddress());
					deliverInfoDetailVo.setRecvId(deliveryId);
					return new ResultBean(ResultCode.success,"获取提货信息详情成功",deliverInfoDetailVo);
				}
			}
			//收货人
			if(EmptyChecker.isNotEmpty(recvId)){
				RecvInfo recvInfo = orderSupport.getRecvInfo(clientId, memberCode, recvId);
				if(EmptyChecker.isNotEmpty(recvInfo)){
					deliverInfoDetailVo.setAddress(recvInfo.getAddress());
					deliverInfoDetailVo.setConsignee(recvInfo.getConsignee());
					deliverInfoDetailVo.setPhone(recvInfo.getPhone());
					deliverInfoDetailVo.setRecvId(recvId);
					return new ResultBean(ResultCode.success,"获取提货信息详情成功",deliverInfoDetailVo);
				}
			}
			result = new ResultBean(ResultCode.success,"获取提货信息详情成功",deliverInfoDetailVo);
		}catch(Exception e){
			LogCvt.error("获取提货信息详情-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"获取提货信息详情失败");
		}
		return result;
	}
	
	@Override
	public ResultBean getSubOrderProductDetail(String clientId,String orderId,String subOrderId, String productId) {
		LogCvt.info("Mongo：获取子订单商品总价");
		ResultBean result = new ResultBean(ResultCode.success,"获取子订单商品详情");
		try {
			ProductMongo product = orderSupport.getSubOrderProductDetail(clientId,orderId, subOrderId, productId);
			if(EmptyChecker.isEmpty(product)){
				LogCvt.error("获取子订单商品失败，[子订单号" +subOrderId +  " 商品号：" + productId + "]");
				return new ResultBean(ResultCode.failed,"获取子订单商品总价失败");
			}
			double vipMoney = Arith.div(product.getVipMoney() == null ? 0 : product.getVipMoney(), 1000);
			int vipQuantity = product.getVipQuantity() == null ? 0 : product.getVipQuantity();
			double deliveryMoney = Arith.div(product.getVipMoney() == null ? 0 : product.getVipMoney(), 1000);
//			double totalMoney = Arith.div(product.getMoney(), 1000) * product.getQuantity() +  vipMoney * vipQuantity + deliveryMoney;
			double money = Arith.div(product.getMoney(), 1000);
			double totalMoney = Arith.add(Arith.add(Arith.mul(money , (double)product.getQuantity()) ,  Arith.mul(vipMoney,(double)vipQuantity)) , (double)deliveryMoney);
			result = new ResultBean(ResultCode.success,"获取子订单商品总价",totalMoney);
		}catch(Exception e){
			LogCvt.error("获取子订单商品总价-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"获取子订单商品总价失败");
		}
		return result;
	}
    
	@Override
	public ResultBean getOrderByQrcode(String clientId, String qrcode) {
		LogCvt.info("Mongo：通过二维码获取面对面订单:{clientId:"+clientId+",qrcode:"+qrcode+"}");
		ResultBean result = new ResultBean(ResultCode.success,"通过二维码获取面对面订单");
		try {
			//查询二维码状态
			Map<String,String> map = orderSupport.getIdByQrcode(clientId, qrcode);
			String state = null;
			if(EmptyChecker.isNotEmpty(map)){
				state = map.get("state");
				if(EmptyChecker.isEmpty(state)){
					LogCvt.error("通过二维码获取面对面订单失败，原因：二维码状态为空  [client_id:"+clientId+" qrcode:"+qrcode+"]");
					return new ResultBean(ResultCode.failed,"通过二维码获取面对面订单失败");
				}else{
					if(!StringUtils.equals(state,"0") && !StringUtils.equals(state,"1")){
						LogCvt.error("通过二维码获取面对面订单失败，原因：二维码状态有误  [client_id:"+clientId+" qrcode:"+qrcode+"]");
						return new ResultBean(ResultCode.failed,"通过二维码获取面对面订单失败");
					}
				}
			}else{
				LogCvt.error("通过二维码获取面对面订单失败，原因：二维码不存在  [client_id:"+clientId+" qrcode:"+qrcode+"]");
				return new ResultBean(ResultCode.failed,"通过二维码获取面对面订单失败");
			}
			
			//通过二维码查询订单
			OrderMongo orderMongo = orderSupport.getOrderByQrcode(clientId, qrcode);
			if(EmptyChecker.isEmpty(orderMongo)){
				if(StringUtils.equals(state,"1")){
					LogCvt.info("通过二维码获取面对面订单结果为空，原因：订单未创建  [client_id:"+clientId+" qrcode:"+qrcode+"]");
					return new ResultBean(ResultCode.order_not_create);
				}else if(StringUtils.equals(state,"0")){
					LogCvt.info("通过二维码获取面对面订单结果为空，原因：订单或支付失败已关闭，新订单未创建  [client_id:"+clientId+" qrcode:"+qrcode+"]");
					return new ResultBean(ResultCode.order_not_create);
				}else{
					LogCvt.error("通过二维码获取面对面订单为空，[客户端号：" +clientId +  " 二维码：" + qrcode + "]");
					return new ResultBean(ResultCode.failed,"通过二维码获取面对面订单失败");
				}
			}
			GetOrderByQrcodeVoRes getOrderByQrcodeVoRes = new GetOrderByQrcodeVoRes();
			getOrderByQrcodeVoRes.setOrderId(orderMongo.getOrderId());
			getOrderByQrcodeVoRes.setPaymentTime(orderMongo.getPaymentTime() == null ? 0L : orderMongo.getPaymentTime());
			getOrderByQrcodeVoRes.setTotalPrice(Arith.div(orderMongo.getTotalPrice(), 1000));
			getOrderByQrcodeVoRes.setMemberName(orderMongo.getMemberName());
			getOrderByQrcodeVoRes.setOrderStatus(orderMongo.getOrderStatus());
			getOrderByQrcodeVoRes.setMemberCode(orderMongo.getMemberCode());
			getOrderByQrcodeVoRes.setOutletId(orderMongo.getOutletId());
			//增加结算状态
            List<Settlement> settlements = settlementSupport.querySettlement(orderMongo.getOrderId());
            // 面对面结算记录为空
            if (EmptyChecker.isEmpty(settlements)) {
            	getOrderByQrcodeVoRes.setSettlementStatus(SettlementStatus.unsettlemnt.getCode());
            } else {
            	getOrderByQrcodeVoRes.setSettlementStatus(settlements.get(0).getSettleState());
            }
			result = new ResultBean(ResultCode.success,"Mongo获取子订单商品总价",getOrderByQrcodeVoRes);
		}catch(Exception e){
			LogCvt.error("通过二维码获取面对面订单-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"通过二维码获取面对面订单失败");
		}
		return result;
	}
	
	@Override
	public ResultBean updateOrderAfterRefund(String clientId,String orderId,String subOrderId,String refundState,boolean isAllRefund) {
		LogCvt.info("退款后更新订单");
		ResultBean result = new ResultBean(ResultCode.success,"更新订单成功");
		try {
			boolean flag = false;
			if(isAllRefund){
				//全部退款关闭订单
				LogCvt.info("全部退款，关闭订单");
				OrderMongo order = new OrderMongo();
				order.setClientId(clientId);
				order.setOrderId(orderId);
				order.setOrderStatus(OrderStatus.sysclosed.getCode());
				order.setState(OrderState.SYSTEM_CLOSED.getCode());
				flag = orderSupport.updateOrderByCondion(order);
				if (!flag) {
					LogCvt.error("关闭订单失败[订单号："+orderId+"]");
					throw new FroadBusinessException(ResultCode.failed.getCode(),"退款关闭订单失败");
				}
				
				try {
					//大数据平台-调用日志
					OrderMongo orderMongo = orderSupport.getOrderById(clientId, orderId);
					List<SubOrderMongo> subOrderList = orderSupport.getSubOrderListByOrderId(clientId, orderId);
					createOrderLog(orderMongo, subOrderList, "ORDERMODIFY");
					
					//订单关联营销活动时，调用营销平台回退活动资格
					if(StringUtils.equals(order.getIsActive(), "1")){
						closeMarketOrder(order,false);
					}
					
				} catch (Exception e) {
					LogCvt.error("全部退款-调用数据平台|调用营销平台-异常：" + e.getMessage(), e);
				}
				
			}else{
				//更新子订单退款状态
				flag = orderSupport.updateSubOrderAfterRefund(clientId,orderId,subOrderId,refundState);
				if (!flag) {
					LogCvt.error("退款更新子订单失败[订单号："+orderId+"]");
					throw new FroadBusinessException(ResultCode.failed.getCode(),"退款更新子订单失败");
				}
			}
		}catch(Exception e){
			LogCvt.error("退款更新订单-系统异常：" + e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"退款更新订单失败");
		}
		
		return result;
	}
	
	@Override
	public ResultBean reduceSeckillStore(List<Store> storeList) {
		//冻结库存
		LogCvt.info("redis：秒杀模块-减库存");
		ResultBean result = new ResultBean(ResultCode.success,"redis减库存成功");
		List<Store> sucessStoreList = new ArrayList<Store>();
		Jedis jedis = RedisManager.getJedis(RedisManager.write);
		try {
			dataEmptyChecker(storeList, "库存集合参数不能为空");
			for(Store store : storeList){
				dataEmptyChecker(store.getClientId(), "clientId不能为空");
				dataEmptyChecker(store.getProductId(), "productId不能为空");
				String prodcutKey = RedisKeyUtil.cbbank_seckill_product_store_client_id_product_id(store.getClientId(), store.getProductId());
				LogCvt.info("减库存操作的缓存Key：" + prodcutKey);
				LogCvt.info("操作前库存数：" + jedis.get(prodcutKey));
				LogCvt.info("操作数：" + (0 - store.getReduceStore()));
				Long stock = jedis.decrBy(prodcutKey,store.getReduceStore());
				sucessStoreList.add(store);
				LogCvt.info("操作后库存数：" + stock);
				if (stock < 0) {
					//减库存失败时，还库存
					LogCvt.info("秒杀模块-库存操作失败");
					processSeckillStore(sucessStoreList,jedis,true);
					RedisManager.returnJedis(jedis,RedisManager.write);
					return new ResultBean(ResultCode.failed,"秒杀模块-减库存失败，[商品ID:"+store.getProductId()+"]库存不足。");
				}
			}
		} catch (FroadBusinessException e) {
			//减库存异常时，还库存
			LogCvt.error("[秒杀模块]库存操作-业务异常："+e.getMsg(),e);
			processSeckillStore(sucessStoreList,jedis,true);
			RedisManager.returnJedis(jedis,RedisManager.write);
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		RedisManager.returnJedis(jedis,RedisManager.write);
		return result;
	}
	
	@Override
	public ResultBean getMemberBuyLimit(String clientId,String merchantId, long memberCode,boolean isVip, String productId) {
		LogCvt.info("[订单模块]-获取用户可购买数量");
		ResultBean result = new ResultBean(ResultCode.success,"获取用户可购买数量成功");
		
		int vipQuantity = 0; //vip购买数量
		int normalQuantity = 0; //普通购买数量
		int totalQuantity = 0; //总共可购买数量
		String msg = "获取用户可购买数量成功";//响应信息
		
		try {
			int store = commonLogic.getStoreRedis(clientId, merchantId, productId);
			
			//库存充足时
			if(store > 0){
				//查询用户订单表的购买记录      vip购买数量和普通购买数量
				LogCvt.info("获取用户历史购买记录，key="+RedisKeyUtil.cbbank_product_limit_client_id_member_code_product_id(clientId,memberCode,productId));
				Map<String,String> userOrderMap = RedisCommon.getUserOrderCountRedis(clientId,memberCode, productId);
				int historyBuyCount = 0;//用户历史普通价购买数
				int historyVipBuyCount = 0;//用户历史VIP购买数
				if(EmptyChecker.isNotEmpty(userOrderMap)){
					if(EmptyChecker.isNotEmpty(userOrderMap.get("count"))){
						historyBuyCount = Integer.parseInt(userOrderMap.get("count").toString());
					}
					if(EmptyChecker.isNotEmpty(userOrderMap.get("vip_count"))){
						historyVipBuyCount = Integer.parseInt(userOrderMap.get("vip_count").toString());
					}
					LogCvt.info("用户历史购买记录：普通历史购买数："+historyBuyCount+",VIP历史购买数："+historyVipBuyCount);
				}else{
					LogCvt.info("缓存中用户无购买记录");
				}
				
				//获取商品限购数量
				ProductDetail productDetail = orderSupport.queryProductDetail(productId);
				if(EmptyChecker.isEmpty(productDetail)){
					LogCvt.error("[严重错误] mongo | cb_product_detail 查询结果为空，查询条件：{productId："+productId+"}");
					return new ResultBean(ResultCode.failed,"获取商品详情失败");
				}
				
				boolean isMatchVip = false;//是否符合VIP购买资格：用户是VIP，商品为预售商品，有VIP价格
				int vipPrice = 0;//VIP价格
				if(StringUtils.equals(productDetail.getProductType(), ProductType.presell.getCode()) || StringUtils.equals(productDetail.getProductType(), ProductType.boutique.getCode())){
					if(EmptyChecker.isNotEmpty(productDetail.getVipPrice()) && productDetail.getVipPrice() > 0){
						vipPrice = productDetail.getVipPrice();
						if(isVip){
							isMatchVip = true;
							LogCvt.info("用户是VIP，商品是预售商品，有VIP价格：用户有VIP价购买资格");
						}
					}else{
						LogCvt.info("商品价格为空或0");
					}
				}else{
					LogCvt.info("商品不是预售商品");
				}
				LogCvt.info("商品ID:"+productId+" 商品类型:"+ProductType.getType(productDetail.getProductType())+" VIP价:"+vipPrice);
				
				//商品限购
				int maxLimit = 0;//普通最大限购数，0是不限购
				int maxVipLimit = 0;//vip最大限购数，0是不限购
				if(BooleanUtils.toBoolean(productDetail.getIsLimit())){
					ProductBuyLimit buyLimit = productDetail.getBuyLimit();
					maxVipLimit =  buyLimit.getMaxVip();//vip最大限购数，0是不限购
					
					long curTime = System.currentTimeMillis();//当前时间
					//5.如果商品在限购期内
					if((buyLimit.getStartTime() < curTime && buyLimit.getEndTime() > curTime) || (buyLimit.getStartTime() < curTime && buyLimit.getEndTime() == 0) ){
						maxLimit = buyLimit.getMax();
						LogCvt.info("商品普通价限购");
					}else if(StringUtils.equals(productDetail.getProductType(), ProductType.boutique.getCode())) {
						maxLimit = buyLimit.getMax();
						LogCvt.info("精品商城商品普通价限购");
					}else {
						LogCvt.info("商品不在限购期内，普通价不限购");
					}
				}
				LogCvt.info("普通价限购数："+maxLimit +"，VIP价限购数："+maxVipLimit);
				
				Map<String,Object> map = getLimitCount(store,historyBuyCount,historyVipBuyCount,isMatchVip,maxLimit,maxVipLimit);
				msg = (String) map.get("msg");
				vipQuantity = (Integer) map.get("vipQuantity");
				normalQuantity = (Integer) map.get("normalQuantity");
				totalQuantity = (Integer) map.get("totalQuantity");
			}
			
			LogCvt.info("获取用户可购买数量-结果：{普通可购买数："+normalQuantity+"，vip可购买数："+vipQuantity+"，最大可购买数："+totalQuantity+"}");
			GetMemberBuyLimitVoRes response = new GetMemberBuyLimitVoRes();
			response.setQuantity(normalQuantity);
			response.setVipQuantity(vipQuantity);
			response.setTotalQuantity(totalQuantity);
			result = new ResultBean(ResultCode.success,msg,response);
		}catch(Exception e){
			LogCvt.error("获取用户可购买数量-系统异常：" + e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"获取用户可购买数量失败");
		}
		return result;
	}
	
	/**
	 * 获取用户可购买数
	 * @param store
	 * @param historyBuyCount 用户历史普通购买数
	 * @param historyVipBuyCount 用户历史VIP购买数
	 * @param isMatchVip 用户是否符合VIP购买条件
	 * @param maxLimit 普通最大限购数，0是不限购
	 * @param maxVipLimit vip最大限购数，0是不限购
	 * @return
	 */
	public Map<String,Object> getLimitCount(int store,int historyBuyCount,int historyVipBuyCount,boolean isMatchVip,int maxLimit,int maxVipLimit){
		LogCvt.info(" 库存："+store);
		LogCvt.info(" 普通价历史购买数:"+historyBuyCount+" VIP价历史购买数："+historyVipBuyCount);
		LogCvt.info(" 普通价限购数："+maxLimit +"，VIP价限购数："+maxVipLimit);
		
		int historyTotalBuyCount = historyBuyCount + historyVipBuyCount;//用户累计购买总数
		
		int vipQuantity = 0; //vip购买数量
		int normalQuantity = 0; //普通购买数量
		int totalQuantity = 0; //总共可购买数量
		
		String msg = "";//提示信息
		
		if(maxLimit >0){//普通价限购
			if(historyTotalBuyCount >= maxLimit && maxLimit > 0){//普通价购买数已经超过限购数量
				LogCvt.info("用户普通价购买数量超限，普通价限购数："+maxLimit+"，普通价购买数："+historyTotalBuyCount);
				msg = "用户购买数量超过限购数量";
				vipQuantity = 0;
				normalQuantity = 0;
				totalQuantity = 0;
			}else{//普通价购买数未超过限购数量
				totalQuantity = maxLimit - historyTotalBuyCount;
				if(isMatchVip){//用户是VIP、是预售商品、有VIP价格
					if(maxVipLimit > 0){//VIP最大购买数>0
						//历史购买数没有超限
						if(maxVipLimit > historyVipBuyCount){
							LogCvt.info("普通价限购，未超过限购数；VIP价限购，未超过VIP限购数：可购买数量=min[普通价限购数-(普通价已购买数+VIP价已购买数),库存]，VIP可购买数=min[VIP限购数-VIP已购买数,库存]");
							vipQuantity = maxVipLimit-historyVipBuyCount;
							normalQuantity = (totalQuantity - vipQuantity) > 0 ? (totalQuantity - vipQuantity) : 0;
							
							totalQuantity = totalQuantity > store ? store : totalQuantity;
							vipQuantity = vipQuantity > store ? store : vipQuantity;
							normalQuantity = normalQuantity > store ? store : normalQuantity;
						}else{
							LogCvt.info("普通价限购，未超过限购数；VIP价限购，超过VIP限购数：可购买数量=min[普通价限购数-(普通价已购买数+VIP价已购买数),库存]，VIP可购买数=0");
							vipQuantity = 0;
							normalQuantity = (totalQuantity - vipQuantity) > 0 ? (totalQuantity - vipQuantity) : 0;
							
							totalQuantity = totalQuantity > store ? store : totalQuantity;
							normalQuantity = normalQuantity > store ? store : normalQuantity;
						}
					}else{
						LogCvt.info("普通价限购，未超过限购数；VIP不限购：可购买数量=min[普通价限购数-(普通价已购买数+VIP价已购买数),库存]，VIP购买数=可购买数量");
						totalQuantity = totalQuantity > store ? store : totalQuantity;
						normalQuantity = 0;
						vipQuantity = totalQuantity - normalQuantity;
					}
				}else{
					LogCvt.info("普通价限购，未超过限购数；不能VIP价购买：min[普通价限购数-(普通价已购买数+VIP价已购买数),库存]，VIP购买数为0");
					totalQuantity = totalQuantity > store ? store : totalQuantity;
					vipQuantity = 0;
					normalQuantity = totalQuantity - vipQuantity;
				}
			}
		}else{//普通价不限购
			totalQuantity = store;
			if(isMatchVip){//VIP用户，是预售商品，有VIP价
				if(maxVipLimit > 0 ){//有限购数
					if(historyVipBuyCount >= maxVipLimit){
						LogCvt.info("普通价不限购，VIP价限购，且超过VIP限购数：可购买数量=库存，VIP可购买数=0");
						vipQuantity = 0;
						normalQuantity = totalQuantity - vipQuantity;
					}else{
						LogCvt.info("普通价不限购，VIP价限购，未超VIP限购数：可购买数=库存-VIP可购买数，VIP可购买数=VIP限购数-VIP已购数");
						vipQuantity = maxVipLimit - historyVipBuyCount;
						normalQuantity = totalQuantity - vipQuantity;
					}
				}else{
					LogCvt.info("普通价不限购，可以VIP价购买，VIP价不限购：可购买数=库存，VIP可购买数=库存");
					vipQuantity = totalQuantity;
					normalQuantity = totalQuantity - vipQuantity;
				}
			}else{
				LogCvt.info("普通价不限购，不能VIP价购买：可购买数量=库存，VIP可购买数=0");
				vipQuantity = 0;
				normalQuantity = totalQuantity - vipQuantity;
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("vipQuantity", vipQuantity);
		map.put("normalQuantity", normalQuantity);
		map.put("totalQuantity", totalQuantity);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 秒杀库存操作
	 * @param list 库存信息
	 * @param jedis
	 * @param flag true为加库存，false为减库存
	 */
	public void processSeckillStore(List<Store> list,Jedis jedis,boolean flag){
		if(EmptyChecker.isNotEmpty(list)){
			LogCvt.info("秒杀模块-还原库存操作开始...");
			for(Store store : list){
				int count = flag ? store.getReduceStore() : 0 - store.getReduceStore();
				String prodcutKey = RedisKeyUtil.cbbank_seckill_product_store_client_id_product_id(store.getClientId(), store.getProductId());
				LogCvt.info("减库存操作的缓存Key：" + prodcutKey);
				LogCvt.info("操作前库存数：" + jedis.get(prodcutKey));
				LogCvt.info("操作数：" + ( store.getReduceStore()));
				Long stock = jedis.incrBy(prodcutKey,count);
				LogCvt.info("操作后库存数：" + stock);
			}
			LogCvt.info("秒杀模块-还原存操作结束.");
		}
	}

	@Override
	public ResultBean increaseSeckillStore(List<Store> storeList) {
		//还库存
		LogCvt.info("redis：秒杀模块-加库存");
		ResultBean result = new ResultBean(ResultCode.success,"还库存成功");
		List<Store> sucessStoreList = new ArrayList<Store>();
		Jedis jedis = RedisManager.getJedis(RedisManager.write);
		try {
			dataEmptyChecker(storeList, "库存集合参数不能为空");
			for(Store store : storeList){
				dataEmptyChecker(store.getClientId(), "clientId不能为空");
				dataEmptyChecker(store.getProductId(), "productId不能为空");
				String prodcutKey = RedisKeyUtil.cbbank_seckill_product_store_client_id_product_id(store.getClientId(), store.getProductId());
				LogCvt.info("还库存操作的缓存Key：" + prodcutKey);
				LogCvt.info("操作前库存数：" + jedis.get(prodcutKey));
				LogCvt.info("操作数：" + store.getReduceStore());
				Long stock = jedis.incrBy(prodcutKey, store.getReduceStore());
				sucessStoreList.add(store);
				LogCvt.info("操作后库存数：" + stock);
			}
		}  catch (JedisDataException e) {
			//还库存异常时，还库存
			LogCvt.error("[秒杀模块]还库存操作-数据库异常:"+e.getMessage(),e);
			processSeckillStore(sucessStoreList,jedis,false);
			RedisManager.returnJedis(jedis,RedisManager.write);
			return new ResultBean(ResultCode.failed,e.getMessage());
		} catch (FroadBusinessException e) {
			//还库存异常时，还库存
			LogCvt.error("[秒杀模块]还库存操作-业务异常:"+e.getMsg(),e);
			processSeckillStore(sucessStoreList,jedis,false);
			RedisManager.returnJedis(jedis,RedisManager.write);
			return new ResultBean(ResultCode.failed,e.getMsg());
		}
		RedisManager.returnJedis(jedis,RedisManager.write);
		return result;
	}
	
	@Override
	public ResultBean updateOrderForSeckill(OrderMongo orderMongo,SubOrderMongo subOrderMongo) {//TODO
		LogCvt.info("[秒杀模块]-补全收货信息-更新订单");
		ResultBean result = new ResultBean(ResultCode.success,"更新订单成功");
		try {
			//更新大订单
			boolean flag = orderSupport.updateOrderForSeckill(orderMongo);
			if(!flag){
				LogCvt.error("更新大订单失败");
				return new ResultBean(ResultCode.failed,"更新大订单失败");
			}
			
			//更新子订单
			boolean subflag = orderSupport.updateSubOrderForSeckill(subOrderMongo);
			if(!subflag){
				LogCvt.error("更新子订单失败");
				return new ResultBean(ResultCode.failed,"更新子订单失败");
			}
			
		}catch(Exception e){
			LogCvt.error("更新订单-系统异常：" +e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"更新订单失败");
		}
		
		try {
			//大数据平台-调用日志
			OrderMongo order = orderSupport.getOrderById(orderMongo.getClientId(), orderMongo.getOrderId());
			List<SubOrderMongo> subOrderList = orderSupport.getSubOrderListByOrderId(orderMongo.getClientId(), orderMongo.getOrderId());
			createOrderLog(order, subOrderList, "ORDERMODIFY");
		} catch (Exception e) {
			LogCvt.error("秒杀更新订单-数据平台打印日志异常：" + e.getMessage(), e);
		}
		
		return result;
	}
	
	@Override
	public ResultBean addVIPOrder(OrderMongo order) {
		LogCvt.info("[开通VIP]-mongo-创建VIP订单");
		ResultBean result = new ResultBean(ResultCode.success,"创建VIP订单成功");
		try {
			//添加
			boolean flag = orderSupport.addOrder(order);
			if(!flag){
				LogCvt.error("创建VIP订单失败");
				return new ResultBean(ResultCode.failed,"创建VIP订单失败");
			}
		}catch(Exception e){
			LogCvt.error(e.getMessage());
			return new ResultBean(ResultCode.failed,"创建VIP订单失败");
		}
		return result;
	}
	
	@Override
	public ResultBean getSubOrder(String clientId, String subOrderId) {
		LogCvt.info("[查询子订单]-接口：OrderLogicImpl.getSubOrder-开始");
		ResultBean result = new ResultBean(ResultCode.success,"查询子订单成功");
		try {
			GetSubOrderVoRes response = new GetSubOrderVoRes();
			SubOrderMongo subOrder = orderSupport.getSubOrderBySubOrderId(clientId,subOrderId);
			if(EmptyChecker.isEmpty(subOrder)){
				LogCvt.error("查询子订单失败：子订单查询为空");
				return new ResultBean(ResultCode.failed,"查询子订单失败");
			}
			if(EmptyChecker.isEmpty(subOrder.getProducts())){
				LogCvt.error("查询子订单失败：子订单商品查询为空");
				return new ResultBean(ResultCode.failed,"查询子订单失败");
			}
			
			OrderMongo order = orderSupport.getOrderByOrderId(clientId,subOrder.getOrderId());
			if(EmptyChecker.isEmpty(order)){
				LogCvt.error("查询子订单失败：大订单查询为空");
				return new ResultBean(ResultCode.failed,"查询子订单失败");
			}
			
			//获取可退款数量
			Map<String, Integer> enableRefoundMap = refundLogic.getCanRefundProductList(clientId,subOrder.getOrderId());
			System.out.println(JSON.toJSONString(enableRefoundMap));
			
			//获取已退商品数量：
			Map<String, Integer> alreadyRefoundMap = refundLogic.getRefundedProduct(subOrder.getOrderId());
			
			List<ProductDetailVo> productList = new ArrayList<ProductDetailVo>();
			for(ProductMongo productMongo : subOrder.getProducts()){
				ProductDetailVo product = new ProductDetailVo();
				//1.可退款数量
				int canRefundCount = 0;
				if(EmptyChecker.isNotEmpty(enableRefoundMap)  && EmptyChecker.isNotEmpty(enableRefoundMap.get(subOrder.getSubOrderId() + "_"+ productMongo.getProductId()))){
					canRefundCount = enableRefoundMap.get(subOrder.getSubOrderId() + "_"+ productMongo.getProductId());
				}else{
					LogCvt.info("商品可退数量获取为空，参数{orderId：" + subOrder.getOrderId() + ",subOrderId:" +subOrder.getSubOrderId() + ",productId:" + productMongo.getProductId()+"}");
				}
				//（1）预售已经发货的、收货的、提货的，不可退款
				if(StringUtils.equals(productMongo.getDeliveryState(), ShippingStatus.shipped.getCode()) 
						|| StringUtils.equals(productMongo.getDeliveryState(), ShippingStatus.receipt.getCode())
						|| StringUtils.equals(productMongo.getDeliveryState(), ShippingStatus.token.getCode())){
					LogCvt.info("预售已经发货的、收货的、提货的，不可退款，可退款数量为0");
					canRefundCount = 0;
				}
				//（2）名优特惠已经发货的、收货的，不可退款
				if(StringUtils.equals(subOrder.getDeliveryState(), ShippingStatus.shipped.getCode()) 
							|| StringUtils.equals(subOrder.getDeliveryState(), ShippingStatus.receipt.getCode())){
					LogCvt.info("名优特惠已经发货的、收货的，不可退款，可退款数量为0");
					canRefundCount = 0;
				}
				
				//取机构的虚拟商户号
				String merchantId = subOrder.getMerchantId();
				if(!(StringUtils.equals(subOrder.getType(), SubOrderType.special_merchant.getCode()) || StringUtils.equals(subOrder.getType(), SubOrderType.group_merchant.getCode()) || StringUtils.equals(subOrder.getType(), SubOrderType.boutique.getCode()))){
					Org org = commonLogic.getOrgByOrgCode(merchantId, clientId);
					if(EmptyChecker.isEmpty(org)){
						LogCvt.error("通过机构号获取商户号失败[机构号："+merchantId+"]");
						throw new FroadBusinessException(ResultCode.failed.getCode(),"取消订单失败");
					}
					merchantId = org.getMerchantId();
				}
				
				//2.商品销售期
				String startTime = "0";
				String endTime = "0";
				Map<String, String> productMap = orderSupport.getProduct(subOrder.getClientId(), merchantId, productMongo.getProductId());
                if (EmptyChecker.isNotEmpty(productMap) && !ProductType.boutique.getCode().equals(productMongo.getType())) {
                    startTime = productMap.get("start_time");
                    endTime = productMap.get("end_time");
                }
                
                /**
                 * 3.可确认收货数量:子订单可确认收货（即子订单下商品已发货），子订单下商品未退款
                 * 子订单发货和退款的限制规则：
                 * (1).子订单下商品可发货（没有退款中和已退款的）都可以发货。
                 * (2).子订单退款规则：子订单已经发货的，不可退款。
                 */
                //已退款数量
				int alreadyRefoundCount = 0;
				if(EmptyChecker.isNotEmpty(alreadyRefoundMap)  && EmptyChecker.isNotEmpty(alreadyRefoundMap.get(subOrder.getSubOrderId() + "_"+ productMongo.getProductId()))){
					alreadyRefoundCount = alreadyRefoundMap.get(subOrder.getSubOrderId() + "_"+ productMongo.getProductId());
				}else{
					LogCvt.info("商品已退数量获取为空，参数{orderId：" + subOrder.getOrderId() + ",subOrderId:" +subOrder.getSubOrderId() + ",productId:" + productMongo.getProductId()+"}");
				}
                int receiveCount = 0;
                //是否可确认收货
				if(StringUtils.equals(subOrder.getDeliveryState(), ShippingStatus.shipped.getCode())
						&& (StringUtils.equals(subOrder.getType(), SubOrderType.special_merchant.getCode()) || StringUtils.equals(subOrder.getType(), SubOrderType.boutique.getCode()))){
					receiveCount = productMongo.getVipQuantity() + productMongo.getQuantity() - alreadyRefoundCount;
				}
                product.setCanReceiveCount(receiveCount);
				
				product.setProductId(productMongo.getProductId());
				product.setProductImage(productMongo.getProductImage());
				product.setProductName(productMongo.getProductName());
				product.setProductType(productMongo.getType());
				product.setVipMoney(Arith.div(productMongo.getVipMoney(),1000));
				product.setMoney(Arith.div(productMongo.getMoney(), 1000));
				product.setQuantity(productMongo.getQuantity());
				product.setVipQuantity(productMongo.getVipQuantity());
				product.setCanRefundCount(canRefundCount);
				product.setStartTime(Long.valueOf(startTime));
                product.setEndTime(Long.valueOf(endTime));
				productList.add(product);
			}
			
			response.setOrderId(order.getOrderId());
			response.setSubOrderId(subOrder.getSubOrderId());
			response.setPaymentTime(order.getPaymentTime() == null ? 0 : order.getPaymentTime());
			response.setProductList(productList);
			result = new ResultBean(ResultCode.success, "查询子订单成功", response);
		}catch(Exception e){
			LogCvt.error(e.getMessage());
			return new ResultBean(ResultCode.failed,"查询子订单失败");
		}
		return result;
	}
	
	/**
	 * 更新每单位商品抵扣积分/单位商品支付现金金额
	 * @param clientId  客户端ID
	 * @param orderId  订单号
	 * @param point  支付总积分
	 */
	@Override
	public boolean updateUnitProductCutPoint(String clientId,String orderId){
		LogCvt.info("---------------------------------------订单积分拆分---------------------------------------");
		OrderLogger.info("订单模块", "积分拆分|优惠拆分|现金拆分", "请求参数", new Object[]{"clientId",clientId,"orderId",orderId});
		
		OrderMongo order = orderSupport.getOrderById(clientId, orderId);
		if(EmptyChecker.isEmpty(order)){
			LogCvt.error("更新商品抵扣积分失败，原因：大订单查询结果为空！{clientId:"+clientId+",orderId:"+orderId+"}");
			return false;
		}
		
		//使用方付通积分支付
		boolean isPayFroadPoint = false;
		
		//支付积分
		Integer points = 0;
		if(EmptyChecker.isNotEmpty(order.getBankPoints()) && order.getBankPoints()>0){
			points = order.getBankPoints();
		}
		if(EmptyChecker.isNotEmpty(order.getFftPoints()) && order.getFftPoints()>0){
			points = order.getFftPoints();
			isPayFroadPoint = true;
		}
		//积分兑换订单无需更新
		if(EmptyChecker.isNotEmpty(order.getIsPoint()) && order.getIsPoint() == 1){
			LogCvt.info("无需更新商品抵扣积分，订单为积分兑换订单！{clientId:"+clientId+",orderId:"+orderId+"}");
			return true;
		}
		//面对面订单无需更新
		if(EmptyChecker.isNotEmpty(order.getIsQrcode()) && order.getIsQrcode() == 1){
			LogCvt.info("无需更新商品抵扣积分，订单为面对面订单！{clientId:"+clientId+",orderId:"+orderId+"}");
			return true;
		}
		//开通VIP订单无需更新
		if(EmptyChecker.isNotEmpty(order.getIsVipOrder()) && order.getIsVipOrder() == 1){
			LogCvt.info("无需更新商品抵扣积分，订单为开通VIP订单！{clientId:"+clientId+",orderId:"+orderId+"}");
			return true;
		}
		
		List<SubOrderMongo> subOrderList = orderSupport.getSubOrderListByOrderId(clientId, orderId);
		if(order.getIsQrcode() != 1 && order.getIsVipOrder() != 1 && EmptyChecker.isEmpty(subOrderList)){
			LogCvt.error("更新商品抵扣积分失败，原因：子订单查询结果为空！{clientId:"+clientId+",orderId:"+orderId+"}");
			return false;
		}
		
		List<String> productIdList = new ArrayList<String>();
		//总运费
		double totalDeliveryMoney = 0;
		//商品总价
		Map<String,ProductMongo> productMap = new HashMap<String,ProductMongo>();
		if(EmptyChecker.isNotEmpty(subOrderList)){
			for(SubOrderMongo subOrder : subOrderList){
				if(EmptyChecker.isNotEmpty(subOrder.getProducts())){
					for(ProductMongo product : subOrder.getProducts()){
						productIdList.add(product.getProductId());
						totalDeliveryMoney += product.getDeliveryMoney();
						productMap.put(product.getProductId(), product);
					}
				}
			}
		}
		
		//商品ID排序
		if(EmptyChecker.isEmpty(productIdList)){
			LogCvt.error("更新商品抵扣积分出错！商品集合为空");
			return false;
		}
		
		//获取优惠金额
		Map<String,FindMarketSubOrderProductVo> marketProductMap = getMarketProductCutMoney(order);
		setSubOrderProductCutmoney(subOrderList,marketProductMap);
		
		//如果使用积分支付
		OrderSplitPointCash  split = new OrderSplitPointCash();
		split.setUnitProductPoint(productIdList, order, subOrderList, productMap, totalDeliveryMoney, points, isPayFroadPoint);
		
		if(EmptyChecker.isNotEmpty(subOrderList)){
			for(SubOrderMongo subOrder : subOrderList){
				if(EmptyChecker.isNotEmpty(subOrder.getProducts())){
					for(ProductMongo product : subOrder.getProducts()){
						product = productMap.get(product.getProductId());
						if(EmptyChecker.isEmpty(product)){
							LogCvt.error("更新商品抵扣积分出错！商品集合为空");
							return false;
						}
					}
					//保存子订单
					orderSupport.updateUnitProdcutCutPoint(subOrder);
				}
			}
		}
		LogCvt.info("---------------------------------------订单积分拆分---------------------------------------");
		return true;
	}
	
	/**
	 * 设置单位商品抵扣积分
	 * @param productIdList 商品ID集合
	 * @param order 大订单
	 * @param subOrderList  子订单集合
	 * @param productMap 商品MAP
	 * @param totalDeliveryMoney 总运费
	 * @param points 总积分
	 * @param isPayFroadPoint  是否方付通积分支付： true 是，false 否
	 * @param marketProductMap 营销活动商品满减金额
	 */
	public void setUnitProductPoint(List<String> productIdList,OrderMongo order,List<SubOrderMongo> subOrderList,Map<String,ProductMongo> productMap,double totalDeliveryMoney,int points,boolean isPayFroadPoint,Map<String,FindMarketSubOrderProductVo> marketProductMap){
		LogCvt.info("更新单位商品抵扣积分-使用积分支付，计算商品抵扣积分");
		//订单实际总价（订单总价-运费）
		double orderTotalMoney = Arith.div(Arith.sub(order.getTotalPrice(), totalDeliveryMoney), 1000);
		//订单总积分
		double orderTotalPoint = Arith.div(points, 1000);
		
		//商品ID排序
		if(EmptyChecker.isNotEmpty(productIdList)){
			Collections.sort(productIdList);
			//所有商品总抵扣积分
			int sumCutPoint = 0;
			for (int i = 0; i < productIdList.size(); i++) {
				ProductMongo product = productMap.get(productIdList.get(i));
				
				//普通价优惠总金额
				double totalCutMoney = 0;
				//VIP价优惠总金额
				double totalVipCutMoney = 0;
				if(EmptyChecker.isNotEmpty(marketProductMap)){
					FindMarketSubOrderProductVo productVo = marketProductMap.get(product.getProductId());
					if(EmptyChecker.isNotEmpty(productVo)){
						totalCutMoney = productVo.getCutMoney();
						totalVipCutMoney = productVo.getVipCutMoney();
						LogCvt.info("productId="+product.getProductId()+" 商品总满减金额:{cutMoney:"+totalCutMoney+",vipCutMoney:"+totalVipCutMoney+"}");
					}
				}else{
					if(EmptyChecker.isNotEmpty(product.getActiveId())){
						LogCvt.error("调用营销平台-获取商品满减金额为空！{orderId:"+order.getOrderId()+",productId:"+product.getProductId()+"}");
					}
				}
				
				//普通价实付总积分
				double totalPoint = 0;
				//VIP价实付总积分
				double totalVipPoint = 0;
				if(isPayFroadPoint){//使用方付通积分支付，可以有小数
					//商品普通价抵扣积分
					totalPoint = Arith.roundDown(Arith.mul(Arith.div(Arith.sub(Arith.mul(Arith.div(product.getMoney(),1000), (double)product.getQuantity()),totalCutMoney),(double)orderTotalMoney),orderTotalPoint),2);
					LogCvt.debug("【商品普通价抵扣积分】计算过程");
					LogCvt.debug("1.商品普通价总金额("+Arith.mul(Arith.div(product.getMoney(),1000), (double)product.getQuantity())+") = 商品普通价("+Arith.div(product.getMoney(),1000)+")*数量("+product.getQuantity()+")");
					LogCvt.debug("2.商品普通价实付总金额("+Arith.sub(Arith.mul(Arith.div(product.getMoney(),1000), (double)product.getQuantity()),totalCutMoney)+") = 商品普通价总金额("+Arith.mul(Arith.div(product.getMoney(),1000), product.getQuantity())+") - 满减金额("+totalCutMoney+")");
					LogCvt.debug("3.商品普通价实付总金额占比("+Arith.div(Arith.sub(Arith.mul(Arith.div(product.getMoney(),1000), (double)product.getQuantity()),totalCutMoney),(double)orderTotalMoney)+") = 商品普通价总金额("+Arith.sub(Arith.mul(Arith.div(product.getMoney(),1000), (double)product.getQuantity()),totalCutMoney)+") / 订单总金额("+orderTotalMoney+")");
					LogCvt.debug("4.商品普通价实付总积分("+Arith.mul(Arith.div(Arith.sub(Arith.mul(Arith.div(product.getMoney(),1000), (double)product.getQuantity()),totalCutMoney),(double)orderTotalMoney),orderTotalPoint)+") = 商品普通价实付总金额占比("+Arith.div(Arith.sub(Arith.mul(Arith.div(product.getMoney(),1000), product.getQuantity()),totalCutMoney),(double)orderTotalMoney)+") * 订单总积分("+orderTotalPoint+")");
					LogCvt.debug("5.商品普通价抵扣积分("+totalPoint+") = 商品普通价实付总积分("+Arith.mul(Arith.div(Arith.sub(Arith.mul(Arith.div(product.getMoney(),1000), (double)product.getQuantity()),totalCutMoney),(double)orderTotalMoney),orderTotalPoint)+") 去掉第三位小数，仅保留两位小数");
					//商品VIP价抵扣积分
					totalVipPoint = Arith.roundDown(Arith.mul(Arith.div(Arith.sub(Arith.mul(Arith.div(product.getVipMoney(),1000), (double)product.getVipQuantity()),totalVipCutMoney),(double)orderTotalMoney),orderTotalPoint),2);
				}else{
					//商品普通价抵扣积分
					totalPoint = Arith.intValue(Arith.mul(Arith.div(Arith.sub(Arith.mul(Arith.div(product.getMoney(),1000), (double)product.getQuantity()),totalCutMoney),(double)orderTotalMoney),orderTotalPoint));
					//商品VIP价抵扣积分
					totalVipPoint = Arith.intValue(Arith.mul(Arith.div(Arith.sub(Arith.mul(Arith.div(product.getVipMoney(),1000), (double)product.getVipQuantity()),totalVipCutMoney),(double)orderTotalMoney),orderTotalPoint));
				}
				
				//同种商品总抵扣积分
				int totalCutPoint = 0;
				
				//不是最后一种商品
				if(i < productIdList.size()-1 ){
					totalCutPoint = Arith.mul(Arith.add(totalPoint, totalVipPoint),1000);
					sumCutPoint += totalCutPoint;
				}else{//如果是最后一种商品
					totalCutPoint = Arith.mul(orderTotalPoint, 1000) - sumCutPoint;
					if(EmptyChecker.isEmpty(product.getVipQuantity()) || product.getVipQuantity()==0){
						totalPoint = Arith.div(totalCutPoint, 1000);
						totalVipPoint = 0;
					}
					if(EmptyChecker.isEmpty(product.getQuantity()) || product.getQuantity() == 0){
						totalVipPoint = Arith.div(totalCutPoint, 1000);
						totalPoint = 0;
					}
					if(EmptyChecker.isNotEmpty(product.getVipQuantity()) && product.getVipQuantity()>0 && EmptyChecker.isNotEmpty(product.getQuantity()) && product.getQuantity()>0){
						double commonPrice = Arith.mul(Arith.div(product.getMoney(),1000), (double)product.getQuantity());
						totalPoint = Arith.mul(Arith.div(commonPrice,Arith.add(commonPrice, Arith.mul(Arith.div(product.getVipMoney(),1000), (double)product.getVipQuantity()))), totalCutPoint);
						totalVipPoint = Arith.sub(totalCutPoint, totalPoint);
					}
				}
				LogCvt.info("商品ID："+product.getProductId()+"，参与活动ID：+"+product.getActiveId()+"，普通价抵扣总金额："+totalCutMoney+"，普通价实付总积分"+totalPoint+"，Vip价抵扣总金额："+totalVipCutMoney+"，VIP价实付总积分:"+totalVipPoint);
				
				//普通价单位商品的抵扣积分
				int cutPoint = 0;
				//最后一个普通价商品的抵扣积分
				int lastCutPoint = 0;
				if(EmptyChecker.isNotEmpty(product.getQuantity()) && product.getQuantity()>0){
					if(isPayFroadPoint){//使用方付通积分支付，可以有小数
						cutPoint = Arith.mul(Arith.roundDown(Arith.div(totalPoint, (double)product.getQuantity()),2), 1000);
					}else{
						cutPoint = Arith.mul(Arith.intValue(Arith.div(totalPoint, (double)product.getQuantity())), 1000);
					}
					if(product.getQuantity()>1){
						lastCutPoint = Arith.mul(Arith.sub(totalPoint, Arith.mul(Arith.div(cutPoint, 1000), (double)(product.getQuantity()-1))),1000);
					}
				}
				//VIP普通价单位商品的抵扣积分
				int vipCutPoint = 0;
				//最后一个VIP价商品的抵扣积分
				int lastVipCutPoint = 0;
				if(EmptyChecker.isNotEmpty(product.getVipQuantity()) && product.getVipQuantity()>0){
					if(isPayFroadPoint){//使用方付通积分支付，可以有小数
						vipCutPoint = Arith.mul(Arith.roundDown(Arith.div(totalVipPoint, (double)product.getVipQuantity()),2), 1000);
					}else{
						vipCutPoint = Arith.mul(Arith.intValue(Arith.div(totalVipPoint, (double)product.getVipQuantity())), 1000);
					}
					if(product.getQuantity()>1){
						lastVipCutPoint = Arith.mul(Arith.sub(totalVipPoint, Arith.mul(Arith.div(vipCutPoint, 1000), (double)(product.getVipQuantity()-1))),1000);
					}
				}
				
				/*if(cutPoint != 0){
					product.setCutPoint(cutPoint);
				}
				if(vipCutPoint != 0){
					product.setVipCutPoint(vipCutPoint);
				}
				if(lastCutPoint != 0){
					product.setLastCutPoint(lastCutPoint);
				}
				if(lastVipCutPoint != 0){
					product.setLastVipCutPoint(lastVipCutPoint);
				}
				if(totalCutPoint != 0){
					product.setTotalCutPoint(totalCutPoint);
				}*/
				LogCvt.info("商品ID："+product.getProductId()+"，参与活动ID：+"+product.getActiveId()+"，积分抵扣计算结果：{cutPoint："+cutPoint+",vipCutPoint"+vipCutPoint+",lastCutPoint:"+lastCutPoint+",totalCutPoint"+totalCutPoint+"}");
				productMap.put(productIdList.get(i),product);
			}
		}
	}
	
	
	/**
	 * 设置单位商品现金
	 * @param productIdList 商品ID集合
	 * @param order 大订单
	 * @param subOrderList  子订单集合
	 * @param productMap  商品Map
	 * @param totalDeliveryMoney 总运费
	 * @param marketProductMap 营销活动商品满减金额
	 */
	public void setUnitProductCash(List<String> productIdList,OrderMongo order,List<SubOrderMongo> subOrderList,Map<String,ProductMongo> productMap,double totalDeliveryMoney,Map<String,FindMarketSubOrderProductVo> marketProductMap){
		LogCvt.info("更新单位商品现金金额-使用现金支付，计算单位商品现金");
		//订单实际总价（订单总价-运费）
		double orderTotalMoney = Arith.div(Arith.sub(order.getTotalPrice(), totalDeliveryMoney), 1000);
		//订单总现金
		double orderTotalCash = Arith.div(order.getRealPrice(), 1000);
		
		//商品ID排序
		if(EmptyChecker.isNotEmpty(productIdList)){
			Collections.sort(productIdList);
			//所有商品总现金
			int sumCash = 0;
			for (int i = 0; i < productIdList.size(); i++) {
				ProductMongo product = productMap.get(productIdList.get(i));
				
			    //普通价优惠总金额
				double totalCutMoney = 0;
				//VIP价优惠总金额
				double totalVipCutMoney = 0;
				if(EmptyChecker.isNotEmpty(marketProductMap)){
					FindMarketSubOrderProductVo productVo = marketProductMap.get(product.getProductId());
					if(EmptyChecker.isNotEmpty(productVo)){
						totalCutMoney = productVo.getCutMoney();
						totalVipCutMoney = productVo.getVipCutMoney();
						LogCvt.info("productId="+product.getProductId()+" 商品总满减金额:{cutMoney:"+totalCutMoney+",vipCutMoney:"+totalVipCutMoney+"}");
					}
				}else{
					if(EmptyChecker.isNotEmpty(product.getActiveId())){
						LogCvt.error("调用营销平台-获取商品满减金额为空！{orderId:"+order.getOrderId()+",productId:"+product.getProductId()+"}");
					}
				}
				
				//普通价实付总现金金额
				double totalMoney = Arith.roundDown(Arith.mul(Arith.div(Arith.sub(Arith.mul(Arith.div(product.getMoney(),1000), (double)product.getQuantity()),totalCutMoney),(double)orderTotalMoney),orderTotalCash),2);
				//VIP价实付总现金金额
				double totalVipMoney = Arith.roundDown(Arith.mul(Arith.div(Arith.sub(Arith.mul(Arith.div(product.getVipMoney(),1000), (double)product.getVipQuantity()),totalVipCutMoney),(double)orderTotalMoney),orderTotalCash),2);
				
				//同种商品总现金
				int totalCash = 0;
				
				//不是最后一种商品
				if(i < productIdList.size()-1 ){
					totalCash = Arith.mul(Arith.add(totalVipMoney, totalMoney),1000);
					sumCash += totalCash;
				}else{//如果是最后一种商品
					totalCash = Arith.mul(orderTotalCash, 1000) - sumCash;
					if(EmptyChecker.isEmpty(product.getVipQuantity()) || product.getVipQuantity()==0){
						totalMoney = Arith.div(totalCash, 1000);
						totalVipMoney = 0;
					}
					if(EmptyChecker.isEmpty(product.getQuantity()) || product.getQuantity()==0){
						totalVipMoney = Arith.div(totalCash, 1000);
						totalMoney = 0;
					}
					if(EmptyChecker.isNotEmpty(product.getVipQuantity()) && product.getVipQuantity()>0 && EmptyChecker.isNotEmpty(product.getQuantity()) && product.getQuantity()>0){
						double commonPrice = Arith.mul(Arith.div(product.getMoney(),1000), (double)product.getQuantity());
						totalMoney = Arith.mul(Arith.div(commonPrice,Arith.add(commonPrice, Arith.mul(Arith.div(product.getVipMoney(),1000), (double)product.getVipQuantity()))), totalCash);
						totalVipMoney = Arith.sub(totalCash, totalMoney);
					}
				}
				LogCvt.info("商品ID："+product.getProductId()+"，参与活动ID：+"+product.getActiveId()+"，普通价抵扣总金额："+totalCutMoney+"，普通价实付总现金金额"+totalMoney+"，Vip价抵扣总金额："+totalVipCutMoney+"，VIP价实付总现金金额:"+totalVipMoney);
				
				//普通价单位商品现金金额
				int cash = 0;
				//最后一个普通价商品的现金金额
				int lastCash = 0;
				if(EmptyChecker.isNotEmpty(product.getQuantity()) && product.getQuantity()>0){
					cash = Arith.mul(Arith.roundDown(Arith.div(totalMoney, (double)product.getQuantity()),2), 1000);
					if(product.getQuantity()>1){
						lastCash = Arith.mul(Arith.sub(totalMoney, Arith.mul(Arith.div(cash, 1000), (double)(product.getQuantity()-1))),1000);
					}
				}
				//VIP价单位商品的现金金额
				int vipCash = 0;
				//最后一个VIP价商品的现金金额
				int lastVipCash = 0;
				if(EmptyChecker.isNotEmpty(product.getVipQuantity()) && product.getVipQuantity()>0){
					vipCash = Arith.mul(Arith.roundDown(Arith.div(totalVipMoney, (double)product.getVipQuantity()),2), 1000);
					if(product.getQuantity()>1){
						lastVipCash = Arith.mul(Arith.sub(totalVipMoney, Arith.mul(Arith.div(vipCash, 1000), (double)(product.getVipQuantity()-1))),1000);
					}
				}
				
				/*if(cash != 0){
					product.setCash(cash);
				}
				if(vipCash != 0){
					product.setVipCash(vipCash);
				}
				if(lastCash != 0){
					product.setLastCash(lastCash);
				}
				if(lastVipCash != 0){
					product.setLastVipCash(lastVipCash);
				}
				if(totalCash != 0){
					product.setTotalCash(totalCash);
				}*/
				LogCvt.info("商品ID："+product.getProductId()+"，参与活动ID：+"+product.getActiveId()+"，现金金额计算结果：{cash"+cash+",vipCash"+vipCash+",lastVipCash"+lastVipCash+",totalCash"+totalCash+"}");
				productMap.put(productIdList.get(i),product);
			}
		}
	}
	
	@Override
	public boolean deleteUnitProductCutPoint(String clientId,String orderId){
		OrderMongo order = orderSupport.getOrderById(clientId, orderId);
		if(EmptyChecker.isEmpty(order)){
			LogCvt.error("删除商品抵扣积分/实付现金失败，原因：大订单查询结果为空！{clientId:"+clientId+",orderId:"+orderId+"}");
			return false;
		}
		List<SubOrderMongo> subOrderList = orderSupport.getSubOrderListByOrderId(clientId, orderId);
		if(order.getIsQrcode() != 1 && order.getIsVipOrder() != 1 && EmptyChecker.isEmpty(subOrderList)){
			LogCvt.error("删除商品抵扣积分/实付现金失败，原因：子订单查询结果为空！{clientId:"+clientId+",orderId:"+orderId+"}");
			return false;
		}
		if(EmptyChecker.isNotEmpty(subOrderList)){
			for(SubOrderMongo subOrder : subOrderList){
				if(EmptyChecker.isNotEmpty(subOrder.getProducts())){
					orderSupport.deleteUnitProdcutCutPoint(subOrder);
				}
			}
		}
		return true;
	}
	
	@Override
	public void createOrderLog(OrderMongo order,List<SubOrderMongo> subOrderList,String action){
		try {
			OrderLog log = new OrderLog();
			HeadKey key = new HeadKey();
			key.setOrder_id(order.getOrderId());
			log.setKey(key);
			log.setAction(action);
			log.setClient_id(order.getClientId());
			log.setTime(System.currentTimeMillis());
			
			OrderDetailLog data = new OrderDetailLog();
			data.setOrder_id(order.getOrderId());
			
			if(EmptyChecker.isNotEmpty(order.getMerchantId())){
				data.setMerchant_id(order.getMerchantId());
			}
			
			if(EmptyChecker.isNotEmpty(order.getMerchantName())){
				data.setMerchant_name(order.getMerchantName());
			}
			
			if(EmptyChecker.isNotEmpty(order.getMerchantId())){
				Map<String, String> merchantMap = MerchantRedis.get_cbbank_merchant_client_id_merchant_id(order.getClientId(),order.getMerchantId());
				if(EmptyChecker.isNotEmpty(merchantMap)){
					String merchant_category_id = merchantMap.get("category_id");
					String merchant_category_name = merchantMap.get("category_name");
					if(EmptyChecker.isNotEmpty(merchant_category_id)){
						data.setMerchant_category_id(merchant_category_id);
					}
					if(EmptyChecker.isNotEmpty(merchant_category_name)){
						data.setMerchant_category_name(merchant_category_name);
					}
				}
			}
			
			data.setOrder_status(order.getOrderStatus());
			
			if(EmptyChecker.isNotEmpty(order.getForgCode())){
				data.setF_org_code(order.getForgCode());
			}
			
			if(EmptyChecker.isNotEmpty(order.getSorgCode())){
				data.setS_org_code(order.getSorgCode());
			}
			
			if(EmptyChecker.isNotEmpty(order.getTorgCode())){
				data.setT_org_code(order.getTorgCode());
			}
			
			if(EmptyChecker.isNotEmpty(order.getLorgCode())){
				data.setL_org_code(order.getLorgCode());
			}
			
			data.setMember_code(String.valueOf(order.getMemberCode()));
			
			if(EmptyChecker.isNotEmpty(order.getIsSeckill())){
				data.setIs_seckill(String.valueOf(order.getIsSeckill()));
			}
			
			if(EmptyChecker.isNotEmpty(order.getIsVipOrder())){
				data.setIs_vip_order(String.valueOf(order.getIsVipOrder()));
			}
			
			data.setTotal_price(order.getTotalPrice());
			if(EmptyChecker.isNotEmpty(order.getFftPoints())){
				data.setFft_points(order.getFftPoints());
			}
			data.setBank_points(order.getBankPoints());
			
			data.setCash_price(order.getRealPrice());
			data.setPayment_method(order.getPaymentMethod());
			if(EmptyChecker.isNotEmpty(order.getPaymentTime())){
				data.setPayment_time(order.getPaymentTime());
			}
			data.setCreate_time(order.getCreateTime());
			data.setVip_discount(order.getVipDiscount());
			data.setUpdate_time(System.currentTimeMillis());
			
			if(EmptyChecker.isNotEmpty(order.getOutletId())){
				data.setOutlet_id(order.getOutletId());
			}
			
			if(EmptyChecker.isNotEmpty(order.getMerchantUserId())){
				data.setMerchant_user_id(order.getMerchantUserId());
			}
			
			data.setCreate_source(order.getCreateSource());
			
			if(EmptyChecker.isNotEmpty(order.getRecvId())){
				data.setRecv_id(order.getRecvId());
			}
			
			if(EmptyChecker.isNotEmpty(order.getIsQrcode()) && order.getIsQrcode() == 1){
				data.setOrder_type(OrderType.face2face.getCode());
				data.setSub_order_id(order.getOrderId());
			}
			
			if(EmptyChecker.isNotEmpty(order.getIsVipOrder()) && order.getIsVipOrder() == 1){
				data.setOrder_type(OrderType.open_vip.getCode());
				data.setSub_order_id(order.getOrderId());
			}
			
			String paymentType = getPaymentType(order);
			if(EmptyChecker.isNotEmpty(paymentType) ){
				data.setPayment_type(paymentType);
			}
			
			if((EmptyChecker.isNotEmpty(order.getIsQrcode()) && order.getIsQrcode() == 1) || (EmptyChecker.isNotEmpty(order.getIsVipOrder()) && order.getIsVipOrder() == 1)){
				List<OrderProduct> products = new ArrayList<OrderProduct>();
				OrderProduct product = new OrderProduct();
				if(EmptyChecker.isNotEmpty(order.getProductId())){
					product.setProduct_id(order.getProductId());
				}
				if(EmptyChecker.isNotEmpty(order.getProductName())){
					product.setProduct_id(order.getProductName());
				}
				products.add(product);
				data.setProducts(products);
				log.setData(data);
				OrderLogs.addOrder(log);
				LogCvt.info("大数据平台-创建日志："+JSON.toJSONString(log));
			}
			
			if(EmptyChecker.isNotEmpty(subOrderList)){
				for(SubOrderMongo subOrder :subOrderList){
					key.setOrder_id(subOrder.getSubOrderId());
					log.setKey(key);
					
					data.setId(subOrder.getSubOrderId());
					
					data.setSub_order_id(subOrder.getSubOrderId());
					if(StringUtils.equals(subOrder.getType(), SubOrderType.group_merchant.getCode()) || StringUtils.equals(subOrder.getType(), SubOrderType.special_merchant.getCode()) || StringUtils.equals(subOrder.getType(), SubOrderType.boutique.getCode())){
						data.setMerchant_id(subOrder.getMerchantId());
					}else{
						data.setOrg_code(subOrder.getMerchantId());
					}
					
					if(EmptyChecker.isNotEmpty(subOrder.getMerchantName())){
						data.setMerchant_name(subOrder.getMerchantName());
					}
					
					if(EmptyChecker.isNotEmpty(subOrder.getMerchantId())){
						Map<String, String> merchantMap = MerchantRedis.get_cbbank_merchant_client_id_merchant_id(subOrder.getClientId(),subOrder.getMerchantId());
						if(EmptyChecker.isNotEmpty(merchantMap)){
							String merchant_category_id = merchantMap.get("category_id");
							String merchant_category_name = merchantMap.get("category_name");
							if(EmptyChecker.isNotEmpty(merchant_category_id)){
								data.setMerchant_category_id(merchant_category_id);
							}
							if(EmptyChecker.isNotEmpty(merchant_category_name)){
								data.setMerchant_category_id(merchant_category_name);
							}
						}
					}
					
					data.setOrder_type(subOrder.getType());
					
					if(EmptyChecker.isNotEmpty(subOrder.getForgCode())){
						data.setF_org_code(subOrder.getForgCode());
					}
					
					if(EmptyChecker.isNotEmpty(subOrder.getSorgCode())){
						data.setS_org_code(subOrder.getSorgCode());
					}
					
					if(EmptyChecker.isNotEmpty(subOrder.getTorgCode())){
						data.setT_org_code(subOrder.getTorgCode());
					}
					
					if(EmptyChecker.isNotEmpty(subOrder.getLorgCode())){
						data.setL_org_code(subOrder.getLorgCode());
					}
					
					List<OrderProduct> products = new ArrayList<OrderProduct>();
					if(EmptyChecker.isNotEmpty(subOrder.getProducts())){
						for(ProductMongo productMongo : subOrder.getProducts()){
							OrderProduct product = new OrderProduct();
							product.setProduct_id(productMongo.getProductId());
							product.setProduct_name(productMongo.getProductName());
							product.setMoney(productMongo.getMoney());
							product.setVip_money(productMongo.getVipMoney());
							product.setQuantity(productMongo.getQuantity());
							product.setVip_quantity(productMongo.getVipQuantity());
							product.setType(productMongo.getType());
							
							if(StringUtils.equals(subOrder.getType(), SubOrderType.group_merchant.getCode())){
								Map<String,String> productMap = orderSupport.getProduct(subOrder.getClientId(),subOrder.getMerchantId(),productMongo.getProductId());
								if(EmptyChecker.isNotEmpty(productMap) && EmptyChecker.isNotEmpty(productMap.get("category_id"))){
									product.setCategory_id(productMap.get("category_id"));
								}
							}
							
							if(EmptyChecker.isNotEmpty(productMongo.getOrgCode())){
								product.setTake_org_code(productMongo.getOrgCode());
							}
							
							if(EmptyChecker.isNotEmpty(productMongo.getOrgName())){
								product.setTake_org_name(productMongo.getOrgName());
							}
							
							if(EmptyChecker.isNotEmpty(productMongo.getDeliveryOption())){
								product.setDelivery_option(productMongo.getDeliveryOption());
							}
							
							if(EmptyChecker.isNotEmpty(productMongo.getDeliveryState())){
								product.setDelivery_state(productMongo.getDeliveryState());
							}
							
							if(EmptyChecker.isNotEmpty(productMongo.getDeliveryMoney())){
								product.setDelivery_money(productMongo.getDeliveryMoney());
							}
							products.add(product);
						}
					}
					data.setProducts(products);
					log.setData(data);
					OrderLogs.addOrder(log);
					LogCvt.info("大数据平台-创建日志："+JSON.toJSONString(log));
				}
			}
		} catch (Exception e) {
			LogCvt.error("数据平台打印日志异常：" + e.getMessage(), e);
		}
	}
	
	private String getPaymentType(OrderMongo order){
		String result = "";
		if(EmptyChecker.isEmpty(order)){
			return result;
		}
		if(!StringUtils.equals(order.getOrderStatus(), OrderStatus.paysuccess.getCode())){
			return result;
		}
		try {
			List<Payment> plist = dataWrap.queryEnableOfUserPayByOrderId(order.getOrderId());
			if(EmptyChecker.isEmpty(plist)){
				return result;
			}
			
			String pointOrgNo = null;
			int cashType = 0;
			//4-支付成功（获得支付结果-成功）/ 5-支付失败（获得支付结果-失败）
			String paymentStatus = "4"; //所有支付流水成功才算成功
			
			for(Payment payment : plist){
				int paymentType = payment.getPaymentType();
				if(paymentType == 2){//现金
					cashType = payment.getPaymentTypeDetails();
//					paymentCashValue = payment.getPaymentValue();
				}else if(paymentType == 1){//方付通积分
					pointOrgNo = Const.FROAD_POINT_ORG_NO;
//					paymentPointValue = payment.getPaymentValue();
				}else if(paymentType == 3){//银行积分
					pointOrgNo = String.valueOf(payment.getPaymentOrgNo());
//					paymentPointValue = payment.getPaymentValue();
				}
				if(!"4".equals(payment.getPaymentStatus())){
					paymentStatus = payment.getPaymentStatus();
				}
			}
			result = LogBeanClone.getPaymentType(pointOrgNo, cashType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@Override
	public ResultBean checkOrderForMarketActive(OrderMongo order,List<SubOrderMongo> subOrderList){
		if(EmptyChecker.isEmpty(order)){
			OrderLogger.error("订单模块", "校验营销活动失败", "参数错误：订单集合为空", null);
			return new ResultBean(ResultCode.check_market_active_fail,"参数错误");
		}
		OrderLogger.info("订单模块", "校验营销活动", "【OrderMongo】:"+JSON.toJSONString(order)+" /n 【List<SubOrderMongo>】:"+JSON.toJSONString(subOrderList),null);
		
		CheckOrderReqVo checkOrderReqVo = new CheckOrderReqVo();
		checkOrderReqVo.setClientId(order.getClientId());
		checkOrderReqVo.setMemberCode(order.getMemberCode());
		checkOrderReqVo.setReqId(reqId.nextId());
		checkOrderReqVo.setOrderMoney(Arith.div(order.getTotalPrice(), 1000));
		checkOrderReqVo.setIsF2FOrder(false);
		List<String> vouchersIds = new ArrayList<String>();
		if(EmptyChecker.isNotEmpty(order.getRedPacketId())){
			vouchersIds.add(order.getRedPacketId());
		}
		if(EmptyChecker.isNotEmpty(order.getCashCouponId())){
			vouchersIds.add(order.getCashCouponId());
		}
		if(EmptyChecker.isNotEmpty(vouchersIds)){
			checkOrderReqVo.setVouchersIds(vouchersIds);
		}
		
		List<OrderProductVo> orderProductList = new ArrayList<OrderProductVo>();
		
		//面对面订单
		if(order.getIsQrcode() == 1){
			checkOrderReqVo.setIsF2FOrder(true);
			
			OrderProductVo orderProductVo = new OrderProductVo();
			orderProductVo.setProductId(order.getProductId());
			orderProductVo.setProductName("面对面商品");
			double cutMoney = 0;
			if(EmptyChecker.isNotEmpty(order.getCutMoney())){
				cutMoney = Arith.div(order.getCutMoney(), 1000);
			}
			orderProductVo.setGeneralPrice(Arith.add(Arith.div(order.getTotalPrice(), 1000), cutMoney));
			orderProductVo.setGeneralCount(1);
			orderProductVo.setVipCount(0);
			orderProductVo.setVipPrice(0);
			orderProductList.add(orderProductVo);
		}
		
		if(EmptyChecker.isNotEmpty(subOrderList)){
			for(SubOrderMongo subOrder : subOrderList){
				if(EmptyChecker.isNotEmpty(subOrder.getProducts())){
					List<String> sustainActiveIds = new ArrayList<String>();
					for(ProductMongo product : subOrder.getProducts()){
						OrderProductVo productVo = new OrderProductVo();
						productVo.setProductId(product.getProductId());
						productVo.setProductName(product.getProductName());
						if(EmptyChecker.isNotEmpty(product.getActiveId())){
							productVo.setActiveId(product.getActiveId());
						}
						productVo.setGeneralCount(product.getQuantity());
						productVo.setGeneralPrice(Arith.div(product.getMoney(),1000));
						productVo.setVipCount(product.getVipQuantity());
						productVo.setVipPrice(Arith.div(product.getVipMoney(), 1000));
						if(EmptyChecker.isNotEmpty(product.getGiveActiveId())){
							productVo.setActiveIdGive(product.getGiveActiveId());
						}
						orderProductList.add(productVo);
						
						if(EmptyChecker.isNotEmpty(product.getActiveId())){
							sustainActiveIds.add(product.getActiveId());
						}
						
						if(EmptyChecker.isNotEmpty(product.getGiveActiveId())){
							sustainActiveIds.add(product.getGiveActiveId());
						}
					}
					if(EmptyChecker.isNotEmpty(sustainActiveIds)){
						checkOrderReqVo.setSustainActiveIds(sustainActiveIds);
					}
				}else{
					OrderLogger.error("订单模块", "校验营销活动失败", "参数错误：商品集合为空", null);
					return new ResultBean(ResultCode.failed,"参数错误");
				}
			}
		}
		
		if(EmptyChecker.isNotEmpty(orderProductList)){
			checkOrderReqVo.setOrderProductList(orderProductList);
		}
		
		ActiveRunService.Client client = null;
		CheckOrderResVo responseVo = null;
		
        String host = null;
        int port = 0;
        TTransport transport = null;
        TProtocol protocol = null;
        TMultiplexedProtocol multiProtocol = null;

        try {
            host = PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_HOST_KEY);
            port = Integer.valueOf(PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_PORT_KEY));
            
            //设置调用的服务地址，端口
            transport = new TSocket(host, port); 
            transport.open();
            
            //设置传输协议为 TBinaryProtocol 
            protocol = new TBinaryProtocol(transport); 
            multiProtocol = new TMultiplexedProtocol(protocol,ActiveRunService.class.getSimpleName());
            client = new ActiveRunService.Client(multiProtocol);
            OrderLogger.info("订单模块", "创建订单校验-校验营销活动", "请求："+JSON.toJSONString(checkOrderReqVo), null);
            
            //接口调用
            responseVo = client.checkOrder(checkOrderReqVo);
            
            OrderLogger.info("订单模块", "创建订单校验-校验营销活动", "响应："+JSON.toJSONString(responseVo), null);

        } catch (Exception e) {
        	OrderLogger.error("订单模块", "创建订单校验-校验营销活动", "连接异常", new Object[]{"port",port,"host",host});
        	return new ResultBean(ResultCode.check_market_active_fail);
        }  finally {
            IOUtils.closeQuietly(transport);
        }
        
        //设置订单最终成交金额和优惠金额
        if (EmptyChecker.isNotEmpty(responseVo) && EmptyChecker.isNotEmpty(responseVo.getResult()) && StringUtils.equals(responseVo.getResult().getResultCode(), ResultCode.success.getCode())){
        	//设置赠送积分和赠送金额
            if(EmptyChecker.isNotEmpty(responseVo.getFullGiveActiveList())){
            	List<FullGiveActiveVo> fullGiveActiveList = responseVo.getFullGiveActiveList();
        		double giveMoney = 0.0;
        		double givePoint = 0.0;
        		for(FullGiveActiveVo fullGiveActiveVo : fullGiveActiveList){
        			//2-红包，4-联盟积分
        			if("2".equals(fullGiveActiveVo.getActiveType())){
        				giveMoney = Arith.add(giveMoney, fullGiveActiveVo.getMonry());
        			}else if("4".equals(fullGiveActiveVo.getActiveType())){
        				givePoint = Arith.add(givePoint, fullGiveActiveVo.getMonry());
        			}
        		}
        		//更新大订单
        		order.setGiveMoney(Arith.mul(giveMoney,1000));
        		order.setGivePoints(Arith.mul(givePoint,1000));
        		OrderLogger.info("订单模块", "创建订单校验-校验营销活动，更新满赠金额、满赠积分到大订单", "赠送金额（真实）：",new Object[]{"giveMoney",giveMoney,"givePoints",givePoint});
            }
        	
        	OrderResultData result = new OrderResultData();
        	result.setRedPacketOrder(false);
        	if(EmptyChecker.isNotEmpty(responseVo.getOrderDealMoney()) && responseVo.getOrderDealMoney() >= 0){
        		Integer totalPrice = order.getTotalPrice();
        		order.setCutMoney(Arith.mul(Arith.sub(Arith.div(totalPrice, 1000), responseVo.getOrderDealMoney()),1000));
        		order.setTotalPrice(Arith.mul(responseVo.getOrderDealMoney(),1000));
        		
        		//纯红包支付的订单，改支付状态为支付完成
        		if(responseVo.getOrderDealMoney() == 0 && order.getBankPoints() == 0 && order.getFftPoints() == 0
        				&& (EmptyChecker.isNotEmpty(order.getRedPacketId()) || EmptyChecker.isNotEmpty(order.getCashCouponId()))){
        			order.setOrderStatus(OrderStatus.paysuccess.getCode());
        			order.setPaymentMethod(PaymentMethod.cash.getCode());
        			order.setPaymentTime(System.currentTimeMillis());
        			order.setRemark("纯红包支付的订单");
        			if(EmptyChecker.isNotEmpty(subOrderList)){
        				for(SubOrderMongo subOrder : subOrderList){
        					subOrder.setOrderStatus(OrderStatus.paysuccess.getCode());
        				}
        			}
        			result.setRedPacketOrder(true);
        		}
        		
        		OrderLogger.info("订单模块", "创建订单校验-校验营销活动", "调用营销活动平台-更新订单成交金额", new Object[]{"订单原始金额",totalPrice,"优惠金额",order.getCutMoney(),"成交金额",order.getTotalPrice()});
        	}else{
        		OrderLogger.error("订单模块", "创建订单校验-校验营销活动", "订单实际成交金额为空或负数", null);
				return new ResultBean(ResultCode.check_market_active_fail);
			}
            return new ResultBean(ResultCode.success,result);
        } else {
            return new ResultBean(ResultCode.check_market_active_fail);
        }
	}
	
	@Override
	public void createOrderFailureGoBackForMarketActive(OrderMongo orderMongo,List<SubOrderMongo> subOrderList,boolean isJoinMarketActive){
		if(!isJoinMarketActive){
			return;
		}
		if(EmptyChecker.isEmpty(orderMongo) && EmptyChecker.isEmpty(subOrderList)){
			return;
		}
		
		FailureGoBackReqVo reqVo = new FailureGoBackReqVo();
		reqVo.setReqId(reqId.nextId());
		reqVo.setClientId(subOrderList.get(0).getClientId());
		reqVo.setMemberCode(subOrderList.get(0).getMemberCode());
		reqVo.setOrderId(subOrderList.get(0).getOrderId());
		
		List<String> vouchersIdList = new ArrayList<String>();
		if(EmptyChecker.isNotEmpty(orderMongo.getRedPacketId())){
			vouchersIdList.add(orderMongo.getRedPacketId());
		}
		if(EmptyChecker.isNotEmpty(orderMongo.getCashCouponId())){
			vouchersIdList.add(orderMongo.getCashCouponId());
		}
		if(EmptyChecker.isNotEmpty(vouchersIdList)){
			reqVo.setVouchersIdList(vouchersIdList);
		}
		
		List<String> activeIdList = new ArrayList<String>();
		if(EmptyChecker.isNotEmpty(subOrderList)){
			for(SubOrderMongo subOrder : subOrderList){
				if(EmptyChecker.isNotEmpty(subOrder.getProducts())){
					for(ProductMongo product : subOrder.getProducts()){
						if(EmptyChecker.isNotEmpty(product.getActiveId())){
							activeIdList.add(product.getActiveId());
						}
					}
				}else{
					LogCvt.error("回滚营销活动失败，参数错误：商品集合为空");
				}
			}
		}
		
		if(EmptyChecker.isEmpty(activeIdList)){
			LogCvt.info("订单下的商品没有关联活动");
		}
		reqVo.setActiveIdList(activeIdList);
		
		ActiveRunService.Client client = null;
		ResultVo responseVo = null;
		
        String host = null;
        int port = 0;
        TTransport transport = null;
        TProtocol protocol = null;
        TMultiplexedProtocol multiProtocol = null;

        try {
            host = PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_HOST_KEY);
            port = Integer.valueOf(PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_PORT_KEY));
            
            //设置调用的服务地址，端口
            transport = new TSocket(host, port); 
            transport.open();
            
            //设置传输协议为 TBinaryProtocol 
            protocol = new TBinaryProtocol(transport); 
            multiProtocol = new TMultiplexedProtocol(protocol,ActiveRunService.class.getSimpleName());
            client = new ActiveRunService.Client(multiProtocol);
            LogCvt.info("创建订单失败-回滚营销活动资格-请求：" + JSonUtil.toJSonString(reqVo));
            
            //接口调用
            responseVo = client.createOrderFailureGoBack(reqVo);
            
            LogCvt.info("创建订单失败-回滚营销活动资格-响应："+JSonUtil.toJSonString(responseVo));
            

        } catch (Exception e) {
        	LogCvt.error("创建订单失败-回滚营销活动资格-连接异常: " + JSonUtil.toJSonString(responseVo) + " port: " + port + " host: " + host, e);
        }  finally {
            IOUtils.closeQuietly(transport);
        }
	}
	
	@Override
	public void createOrderFailureGoBackForMarketActive(OrderMongo orderMongo,boolean isJoinMarketActive){
		if(!isJoinMarketActive){
			return;
		}
		if(EmptyChecker.isEmpty(orderMongo)){
			return;
		}
		
		FailureGoBackReqVo reqVo = new FailureGoBackReqVo();
		reqVo.setReqId(reqId.nextId());
		reqVo.setClientId(orderMongo.getClientId());
		reqVo.setMemberCode(orderMongo.getMemberCode());
		reqVo.setOrderId(orderMongo.getOrderId());
		
		List<String> vouchersIdList = new ArrayList<String>();
		if(EmptyChecker.isNotEmpty(orderMongo.getRedPacketId())){
			vouchersIdList.add(orderMongo.getRedPacketId());
		}
		if(EmptyChecker.isNotEmpty(orderMongo.getCashCouponId())){
			vouchersIdList.add(orderMongo.getCashCouponId());
		}
		if(EmptyChecker.isNotEmpty(vouchersIdList)){
			reqVo.setVouchersIdList(vouchersIdList);
		}
		
		if(EmptyChecker.isEmpty(vouchersIdList)){
			OrderLogger.error("订单模块", "面对面订单创建", "回滚营销活动失败：面对面订单没有关联红包或券", null);
			return;
		}
		
		ActiveRunService.Client client = null;
		ResultVo responseVo = null;
		
        String host = null;
        int port = 0;
        TTransport transport = null;
        TProtocol protocol = null;
        TMultiplexedProtocol multiProtocol = null;

        try {
            host = PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_HOST_KEY);
            port = Integer.valueOf(PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_PORT_KEY));
            
            //设置调用的服务地址，端口
            transport = new TSocket(host, port); 
            transport.open();
            
            //设置传输协议为 TBinaryProtocol 
            protocol = new TBinaryProtocol(transport); 
            multiProtocol = new TMultiplexedProtocol(protocol,ActiveRunService.class.getSimpleName());
            client = new ActiveRunService.Client(multiProtocol);
            LogCvt.info("创建订单失败-回滚营销活动资格-请求：" + JSonUtil.toJSonString(reqVo));
            
            //接口调用
            responseVo = client.createOrderFailureGoBack(reqVo);
            
            LogCvt.info("创建订单失败-回滚营销活动资格-响应："+JSonUtil.toJSonString(responseVo));
            

        } catch (Exception e) {
        	LogCvt.error("创建订单失败-回滚营销活动资格-连接异常: " + JSonUtil.toJSonString(responseVo) + " port: " + port + " host: " + host, e);
        }  finally {
            IOUtils.closeQuietly(transport);
        }
	}
	
	@Override
	public ResultBean createMarketOrderForMarketActive(OrderMongo order,List<SubOrderMongo> subOrderList){
		ResultBean result = new ResultBean(ResultCode.success,"更新订单营销活动ID成功");
		if(EmptyChecker.isEmpty(order)){
			OrderLogger.error("订单模块", "创建营销订单失败", "参数错误：大订单为空", null);
			return new ResultBean(ResultCode.failed,"创建营销订单失败");
		}
		
		CreateMarketOrderReqVo reqVo = new CreateMarketOrderReqVo();
		reqVo.setReqId(reqId.nextId());
		reqVo.setClientId(order.getClientId());
		reqVo.setMemberCode(order.getMemberCode());
		reqVo.setPhone(order.getPhone());
		reqVo.setOrderId(order.getOrderId());
		Integer cutMoney = 0;
		if(EmptyChecker.isNotEmpty(order.getCutMoney())){
			cutMoney = order.getCutMoney();
		}
		reqVo.setOrderOriMoney(Arith.div(Arith.add(order.getTotalPrice(), cutMoney),1000));
		reqVo.setOrderMoney(Arith.div(order.getTotalPrice(),1000));
		
		List<String> vouchersIdList = new ArrayList<String>();
		if(EmptyChecker.isNotEmpty(order.getRedPacketId())){
			vouchersIdList.add(order.getRedPacketId());
		}
		if(EmptyChecker.isNotEmpty(order.getCashCouponId())){
			vouchersIdList.add(order.getCashCouponId());
		}
		if(EmptyChecker.isNotEmpty(vouchersIdList)){
			reqVo.setVouchersIds(vouchersIdList);
		}
		
		List<MarketSubOrderVo> marketSubOrderList = new ArrayList<MarketSubOrderVo>();
		
		//面对面订单
		if(order.getIsQrcode() == 1){
			MarketSubOrderVo marketSubOrderVo = new MarketSubOrderVo();
			marketSubOrderVo.setSubOrderId(order.getOrderId());
			
			List<MarketSubOrderProductVo> marketSubOrderProductList = new ArrayList<MarketSubOrderProductVo>();
			MarketSubOrderProductVo  productVo = new MarketSubOrderProductVo();
			productVo.setProductId(order.getProductId());
			if(EmptyChecker.isNotEmpty(order.getIsPrefPay()) && order.getIsPrefPay() == 1){
				productVo.setProductName("惠付商品");
			}else{
				productVo.setProductName("面对面商品");
			}
			productVo.setGeneralPrice(Arith.add(Arith.div(order.getTotalPrice(), 1000), Arith.div(order.getCutMoney(), 1000)));
			productVo.setGeneralCount(1);
			productVo.setVipCount(0);
			productVo.setVipPrice(0);
			marketSubOrderProductList.add(productVo);
			
			marketSubOrderVo.setMarketSubOrderProductList(marketSubOrderProductList);
			marketSubOrderList.add(marketSubOrderVo);
		}
		
		if(EmptyChecker.isNotEmpty(subOrderList)){
			for(SubOrderMongo subOrder : subOrderList){
				MarketSubOrderVo subOrderVo = new MarketSubOrderVo();
				subOrderVo.setSubOrderId(subOrder.getSubOrderId());
				
				List<MarketSubOrderProductVo> marketSubOrderProductList = new ArrayList<MarketSubOrderProductVo>();
				if(EmptyChecker.isNotEmpty(subOrder.getProducts())){
					for(ProductMongo product : subOrder.getProducts()){
						MarketSubOrderProductVo productVo = new MarketSubOrderProductVo();
						productVo.setActiveId(product.getActiveId());
						productVo.setActiveIdGive(product.getGiveActiveId());
						productVo.setProductId(product.getProductId());
						productVo.setProductName(product.getProductName());
						productVo.setGeneralPrice(Arith.div(product.getMoney(), 1000));
						productVo.setGeneralCount(product.getQuantity());
						productVo.setVipCount(product.getVipQuantity());
						productVo.setVipPrice(Arith.div(product.getVipMoney(),1000));
						marketSubOrderProductList.add(productVo);
					}
				}else{
					OrderLogger.error("订单模块", "创建营销订单失败", "参数错误：商品集合为空", null);
					return new ResultBean(ResultCode.failed,"创建营销订单失败");
				}
				subOrderVo.setMarketSubOrderProductList(marketSubOrderProductList);
				marketSubOrderList.add(subOrderVo);
			}
		}
		if(EmptyChecker.isEmpty(marketSubOrderList)){
			OrderLogger.error("订单模块", "创建营销订单失败", "订单下的商品没有关联活动、红包或现金券", null);
			return new ResultBean(ResultCode.failed,"创建营销订单失败");
		}
		reqVo.setMarketSubOrderList(marketSubOrderList);
		
		ActiveRunService.Client client = null;
		CreateResultVo responseVo = null;
		
        String host = null;
        int port = 0;
        TTransport transport = null;
        TProtocol protocol = null;
        TMultiplexedProtocol multiProtocol = null;

        try {
            host = PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_HOST_KEY);
            port = Integer.valueOf(PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_PORT_KEY));
            
            //设置调用的服务地址，端口
            transport = new TSocket(host, port); 
            transport.open();
            
            //设置传输协议为 TBinaryProtocol 
            protocol = new TBinaryProtocol(transport); 
            multiProtocol = new TMultiplexedProtocol(protocol,ActiveRunService.class.getSimpleName());
            client = new ActiveRunService.Client(multiProtocol);
            OrderLogger.info("订单模块", "创建营销订单", "请求参数 "+JSonUtil.toJSonString(reqVo), null);
            
            //接口调用
            responseVo = client.createMarketOrder(reqVo);
            if(EmptyChecker.isNotEmpty(responseVo) && StringUtils.equals(responseVo.getResult().getResultCode(), ResultCode.success.getCode()) && EmptyChecker.isNotEmpty(responseVo.getVouchersOrderId())){
            	order.setMarketId(responseVo.getVouchersOrderId());
            }else{
            	OrderLogger.error("订单模块", "创建营销订单失败", "响应结果 "+JSonUtil.toJSonString(responseVo), null);
            	return new ResultBean(ResultCode.failed,"创建营销订单失败");
            }
            
            OrderLogger.info("订单模块", "创建营销订单成功", "响应结果 "+JSonUtil.toJSonString(responseVo), null);

        } catch (Exception e) {
        	LogCvt.error("创建订单-调用创建营销订单-连接异常: " + JSonUtil.toJSonString(responseVo) + " port: " + port + " host: " + host, e);
        }  finally {
            IOUtils.closeQuietly(transport);
        }
        return result;
	}
	
	public void closeMarketOrder(OrderMongo order,boolean isRefundCloseOrder){
		if(EmptyChecker.isEmpty(order)){
			return;
		}
		
		CloseMarketOrderReqVo reqVo = new CloseMarketOrderReqVo();
		reqVo.setReqId(reqId.nextId());
		reqVo.setClientId(order.getClientId());
		reqVo.setMemberCode(order.getMemberCode());
		reqVo.setReason(BooleanUtils.toInteger(isRefundCloseOrder));
		reqVo.setOrderId(order.getOrderId());
		
		ActiveRunService.Client client = null;
		ResultVo responseVo = null;
		
        String host = null;
        int port = 0;
        TTransport transport = null;
        TProtocol protocol = null;
        TMultiplexedProtocol multiProtocol = null;

        try {
            host = PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_HOST_KEY);
            port = Integer.valueOf(PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_PORT_KEY));
            
            //设置调用的服务地址，端口
            transport = new TSocket(host, port); 
            transport.open();
            
            //设置传输协议为 TBinaryProtocol 
            protocol = new TBinaryProtocol(transport); 
            multiProtocol = new TMultiplexedProtocol(protocol,ActiveRunService.class.getSimpleName());
            client = new ActiveRunService.Client(multiProtocol);
            LogCvt.info("订单关闭-调用营销活动-请求：" + JSonUtil.toJSonString(reqVo));
            
            //接口调用
            responseVo = client.closeMarketOrder(reqVo);
            
            LogCvt.info("订单关闭-调用营销活动-响应："+JSonUtil.toJSonString(responseVo));

        } catch (Exception e) {
        	LogCvt.error("订单关闭-调用营销活动-连接异常: " + JSonUtil.toJSonString(responseVo) + " port: " + port + " host: " + host, e);
        }  finally {
            IOUtils.closeQuietly(transport);
        }
	}
	
	@Override
	public void updateMarketOrder(OrderMongo order,boolean isPaySuccess){
		if(EmptyChecker.isEmpty(order)){
			return;
		}
		
		UpdateMarketOrderReqVo reqVo = new UpdateMarketOrderReqVo();
		reqVo.setReqId(reqId.nextId());
		reqVo.setClientId(order.getClientId());
		reqVo.setMemberCode(order.getMemberCode());
		reqVo.setOrderId(order.getOrderId());
		if(EmptyChecker.isNotEmpty(order.getMarketId())){
			reqVo.setMarket_Id(order.getMarketId());
		}
		reqVo.setStatus(isPaySuccess);
		if(EmptyChecker.isNotEmpty(order.getPaymentTime())){
			reqVo.setPayTime(order.getPaymentTime());
		}
		List<String> vouchersIdList = new ArrayList<String>();
		if(EmptyChecker.isNotEmpty(order.getRedPacketId())){
			vouchersIdList.add(order.getRedPacketId());
		}
		if(EmptyChecker.isNotEmpty(order.getCashCouponId())){
			vouchersIdList.add(order.getCashCouponId());
		}
		if(EmptyChecker.isNotEmpty(vouchersIdList)){
			reqVo.setVouchersIdList(vouchersIdList);
		}
		if(EmptyChecker.isNotEmpty(order.getIsQrcode()) && order.getIsQrcode() == 1){
			reqVo.setIsF2FOrder(true);
		}else{
			reqVo.setIsF2FOrder(false);
		}
		
		ActiveRunService.Client client = null;
		UpdateMarketOrderResVo responseVo = null;
		
        String host = null;
        int port = 0;
        TTransport transport = null;
        TProtocol protocol = null;
        TMultiplexedProtocol multiProtocol = null;

        try {
            host = PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_HOST_KEY);
            port = Integer.valueOf(PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_PORT_KEY));
            
            //设置调用的服务地址，端口
            transport = new TSocket(host, port); 
            transport.open();
            
            //设置传输协议为 TBinaryProtocol 
            protocol = new TBinaryProtocol(transport); 
            multiProtocol = new TMultiplexedProtocol(protocol,ActiveRunService.class.getSimpleName());
            client = new ActiveRunService.Client(multiProtocol);
            LogCvt.info("支付成功更新营销订单-调用营销活动-请求：" + JSonUtil.toJSonString(reqVo));
            
            //接口调用
            responseVo = client.updateMarketOrder(reqVo);
            
            //满赠活动，支付完成，更新营销订单的时候，会分别返回满赠金额、满赠积分，要更新到大订单上去
            if(ResultCode.success.getCode().equals(responseVo.getResult().getResultCode()) && EmptyChecker.isNotEmpty(responseVo.getFullGiveActiveList())){
            	List<FullGiveActiveVo> fullGiveActiveList = responseVo.getFullGiveActiveList();
        		double giveMoney = 0.0;
        		double givePoint = 0.0;
        		for(FullGiveActiveVo fullGiveActiveVo : fullGiveActiveList){
        			//2-红包，4-联盟积分
        			if("2".equals(fullGiveActiveVo.getActiveType())){
        				giveMoney = Arith.add(giveMoney, fullGiveActiveVo.getMonry());
        			}else if("4".equals(fullGiveActiveVo.getActiveType())){
        				givePoint = Arith.add(givePoint, fullGiveActiveVo.getMonry());
        			}
        		}
        		//更新大订单
        		boolean flag = orderSupport.updateOrderForGive(order.getClientId(),  order.getOrderId(), Arith.mul(giveMoney,1000), Arith.mul(givePoint,1000));
        		OrderLogger.info("订单模块", "满赠活动订单，更新满赠金额、满赠积分到大订单", BooleanUtils.toString(flag, "操作成功！", "操作失败！"), null);
            }
            
            LogCvt.info("支付成功更新营销订单-调用营销活动-响应："+JSonUtil.toJSonString(responseVo));

        } catch (Exception e) {
        	LogCvt.error("支付成功更新营销订单-调用营销活动-连接异常: " + JSonUtil.toJSonString(responseVo) + " port: " + port + " host: " + host, e);
        }  finally {
            IOUtils.closeQuietly(transport);
        }
	}
	
	@Override
	public ResultBean updateOrderForMarketActive(OrderMongo order) {
		LogCvt.info("Mongo：更新订单营销活动ID");
		ResultBean result = new ResultBean(ResultCode.success,"更新订单营销活动ID成功");
		try {
			dataEmptyChecker(order, "订单修改参数不能为空");
			dataEmptyChecker(order.getOrderId(), "订单修改参数不能为空");
			dataEmptyChecker(order.getClientId(), "客户端ID不能为空");
			boolean flag = orderSupport.updateOrder(order);
			if(!flag){
				LogCvt.error("更新订单营销活动ID失败");
				return new ResultBean(ResultCode.failed,"更新订单失败");
			}
		}catch(Exception e){
			LogCvt.error("更新订单营销活动ID-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"更新订单营销活动ID失败");
		}
		return result;
	}
	
	
	private Map<String,FindMarketSubOrderProductVo> getMarketProductCutMoney(OrderMongo order){
		Map<String,FindMarketSubOrderProductVo> resultMap = new HashMap<String,FindMarketSubOrderProductVo>();
		if(EmptyChecker.isEmpty(order)){
			return null;
		}
		
		FindMarketOrderReqVo reqVo = new FindMarketOrderReqVo();
		reqVo.setReqId(reqId.nextId());
		reqVo.setClientId(order.getClientId());
		reqVo.setOrderId(order.getOrderId());
		
		ActiveRunService.Client client = null;
		FindMarketOrderResVo responseVo = null;
		
        String host = null;
        int port = 0;
        TTransport transport = null;
        TProtocol protocol = null;
        TMultiplexedProtocol multiProtocol = null;

        try {
            host = PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_HOST_KEY);
            port = Integer.valueOf(PropertiesUtil.getProperty(THRIFT_PROPERTIES_FILE, PROPERTIES_PORT_KEY));
            
            //设置调用的服务地址，端口
            transport = new TSocket(host, port); 
            transport.open();
            
            //设置传输协议为 TBinaryProtocol 
            protocol = new TBinaryProtocol(transport); 
            multiProtocol = new TMultiplexedProtocol(protocol,ActiveRunService.class.getSimpleName());
            client = new ActiveRunService.Client(multiProtocol);
            LogCvt.info("调用营销活动平台-计算商品满减金额-请求：" + JSonUtil.toJSonString(reqVo));
            
            //接口调用
            responseVo = client.findMarketOrder(reqVo);
            if(EmptyChecker.isNotEmpty(responseVo) && StringUtils.equals(responseVo.getResult().getResultCode(), ResultCode.success.getCode())){
            	if(EmptyChecker.isNotEmpty(responseVo.getFindMarketSubOrderList())){
            		for(FindMarketSubOrderVo subOrderVo : responseVo.getFindMarketSubOrderList()){
            			if(EmptyChecker.isNotEmpty(subOrderVo.getFindMarketSubOrderProductList())){
            				for(FindMarketSubOrderProductVo productVo : subOrderVo.getFindMarketSubOrderProductList()){
            					resultMap.put(productVo.getProductId(), productVo);
            				}
            			}
            		}
            	}
            }else{
            	OrderLogger.error("订单模块", "调用营销活动平台", "计算商品满减金额-调用失败！响应信息："+JSON.toJSONString(responseVo),null);
            }
            
            OrderLogger.info("订单模块", "调用营销活动平台", "计算商品满减金额，响应信息："+JSON.toJSONString(responseVo),null);

        } catch (Exception e) {
        	LogCvt.error("调用营销活动平台-计算商品满减金额-连接异常: " + JSonUtil.toJSonString(responseVo) + " port: " + port + " host: " + host, e);
        }  finally {
            IOUtils.closeQuietly(transport);
        }
        return resultMap;
	}
	
	/**
	 * 设置满减和优惠金额
	 * @param subOrderList
	 * @param marketProductMap
	 */
	private void setSubOrderProductCutmoney(List<SubOrderMongo> subOrderList,Map<String,FindMarketSubOrderProductVo> marketProductMap){
		boolean isNeedUpdate = false;
		if(EmptyChecker.isNotEmpty(subOrderList)){
			for(SubOrderMongo subOrder : subOrderList){
				if(EmptyChecker.isNotEmpty(subOrder.getProducts())){
					for(ProductMongo product : subOrder.getProducts()){
						if(EmptyChecker.isNotEmpty(marketProductMap) && EmptyChecker.isNotEmpty(marketProductMap.get(product.getProductId()))){
							FindMarketSubOrderProductVo productVo = marketProductMap.get(product.getProductId());
							double totalCutMoney = 0;
							double cutMoney = 0;
							if(productVo.getCutMoney()>0){
								cutMoney = Arith.add(cutMoney, productVo.getCutMoney());
							}
							if(productVo.getVouMoney()>0){
								cutMoney = Arith.add(cutMoney, productVo.getVouMoney());
							}
							if (cutMoney > 0) {
								totalCutMoney = Arith.add(totalCutMoney, cutMoney);
								OrderSplitPointCash split = new OrderSplitPointCash();
								Double[] cutMoneyArray = split.splitMoney(cutMoney, product.getQuantity());
								if(EmptyChecker.isNotEmpty(cutMoneyArray)){
									product.setCutMoneyArray(split.toIntegerArray(cutMoneyArray));
									isNeedUpdate = true;
								}
							}
							
							double vipCutMoney = 0;
							if(productVo.getVipCutMoney()>0){
								vipCutMoney = Arith.add(vipCutMoney, productVo.getVipCutMoney());
							}
							if(productVo.getVipVouMoney()>0){
								vipCutMoney = Arith.add(vipCutMoney, productVo.getVipVouMoney());
							}
							if(vipCutMoney>0){
								totalCutMoney = Arith.add(totalCutMoney, vipCutMoney);
								OrderSplitPointCash split = new OrderSplitPointCash();
								Double[] vipCutMoneyArray = split.splitMoney(vipCutMoney, product.getVipQuantity());
								if(EmptyChecker.isNotEmpty(vipCutMoneyArray)){
									product.setVipCutMoneyArray(split.toIntegerArray(vipCutMoneyArray));
									isNeedUpdate = true;
								}
							}
							if(totalCutMoney>0){
								product.setTotalCutMoney(Arith.mul(totalCutMoney, 1000));
								isNeedUpdate = true;
							}
						}
					}
				}
			}
			OrderLogger.info("订单模块", "积分拆分", "调用营销模块获取优惠金额后，优惠金额拆分结果："+JSON.toJSONString(subOrderList), null);
		}
	}
	
	@Override
	public ResultBean updateSubOrderLogistic(String clientId, String subOrderId, String deliveryState) {
		ResultBean rtb = new ResultBean(ResultCode.success);
		try {
			//参数校验
			dataEmptyChecker(clientId, "客户端ID参数不能为空");
			dataEmptyChecker(subOrderId, "子订单ID参数不能为空");
			dataEmptyChecker(deliveryState, "配送状态参数不能为空");
			
			boolean isSuccess = orderSupport.updateSubOrderLogistic(clientId, subOrderId, deliveryState);
			if(!isSuccess) {
				LogCvt.error("更新子订单配送状态失败");
				return new ResultBean(ResultCode.failed,"更新子订单配送状态失败");
			}
		} catch (Exception e) {
			LogCvt.error("更新子订单配送状态-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"更新子订单配送状态失败");
		}
		return rtb;
	}

	@Override
	public ResultBean checkBeforeCashier(CashierVoReq cashierVoReq) {
		ResultBean result = new ResultBean(ResultCode.success);
		
		CashierVoRes responseVo = new CashierVoRes();
		
		try {
			OrderMongo orderMongo = orderSupport.getOrderById(cashierVoReq.getClientId(), cashierVoReq.getOrderId());
			
			if(EmptyChecker.isNotEmpty(orderMongo)){
				responseVo.setOrderId(orderMongo.getOrderId());
				responseVo.setTotalPrice(Arith.div(orderMongo.getTotalPrice(), 1000));
				
				//使用纯红包支付
				if((EmptyChecker.isNotEmpty(orderMongo.getCashCouponId()) || EmptyChecker.isNotEmpty(orderMongo.getRedPacketId())) 
						&& (EmptyChecker.isEmpty(orderMongo.getTotalPrice()) || orderMongo.getTotalPrice() == 0)
						&& (EmptyChecker.isEmpty(orderMongo.getBankPoints()) || orderMongo.getBankPoints() == 0)
						&& (EmptyChecker.isEmpty(orderMongo.getFftPoints()) || orderMongo.getFftPoints() == 0)){
					responseVo.setBankPoint(0);
					responseVo.setFroadPoint(0);
					responseVo.setCash(0);
					responseVo.setIsNeedCash("2");
				}
				
				//纯现金支付
				if(EmptyChecker.isNotEmpty(orderMongo.getTotalPrice()) &&  orderMongo.getTotalPrice() > 0 
						&& (EmptyChecker.isEmpty(orderMongo.getBankPoints()) || orderMongo.getBankPoints() == 0) 
						&& (EmptyChecker.isEmpty(orderMongo.getFftPoints()) || orderMongo.getFftPoints() == 0)){
					responseVo.setCash(Arith.div(orderMongo.getTotalPrice(), 1000));
					responseVo.setIsNeedCash("1");
				}
				
				//有银行积分支付
				if(EmptyChecker.isNotEmpty(orderMongo.getBankPoints()) &&  orderMongo.getBankPoints() > 0){
					//检查用户方付通积分是否足够
					
					int bankPoint = Arith.intValue(Arith.div(orderMongo.getBankPoints(), 1000));
					ResultBean userPointResult = memberInformationSupport.queryUserPoints(cashierVoReq.getClientId(),cashierVoReq.getMemberName());
					
					//现金
					double cash = 0;
					
					if(EmptyChecker.isNotEmpty(userPointResult) && userPointResult.isSuccess()){
						MemberPointsInfo userPointsInfo = (MemberPointsInfo) userPointResult.getData();
						int userBankPoints = 0;
						if(EmptyChecker.isNotEmpty(userPointsInfo) && EmptyChecker.isNotEmpty(userPointsInfo.getBankPoints())){
							userBankPoints = Integer.valueOf(userPointsInfo.getBankPoints());
							Integer pointRate = BaseSubassembly.acquireBankPointPointRate(cashierVoReq.getClientId());
							if(EmptyChecker.isEmpty(pointRate)){
								OrderLogger.error("订单模块", "校验是否跳收银台", "获取用户银行积分兑换比例为空", new Object[]{"clientId",cashierVoReq.getClientId(),"orderId",cashierVoReq.getOrderId()});
								return new ResultBean(ResultCode.failed,"获取"+orderMongo.getClientId()+"银行积分兑换比例为空");
							}
							if(userBankPoints < bankPoint){
								//计算应扣积分
								double userPointMoney = Arith.roundDown(Arith.div(userBankPoints, pointRate), 2);
								int userNeedPoint = Arith.intValue(Arith.roundUp(Arith.mul(userPointMoney, pointRate), 0));
								//double cutMoney = Arith.div(EmptyChecker.isEmpty(orderMongo.getCutMoney())? 0 : orderMongo.getCutMoney(), 1000);
								cash = Arith.sub(Arith.div(orderMongo.getTotalPrice(), 1000),userPointMoney);
								orderMongo.setBankPoints(Arith.mul(userNeedPoint, 1000));
								//orderMongo.setRealPrice(Arith.mul(cash, 1000));
								//更新订单银行积分和现金
								orderSupport.updateOrder(orderMongo);
								OrderLogger.info("订单模块", "校验是否跳收银台", "用户使用银行积分支付，用户积分不足，计算结果", new Object[]{"积分比例",pointRate,"订单原始积分",bankPoint,"用户现有积分",userBankPoints,"用户现有积分对应的金额",userPointMoney,"用户应扣银行积分",userNeedPoint,"应该支付的现金",cash});
							}else{
								double userPointMoney = Arith.roundDown(Arith.div(bankPoint, pointRate), 2);
								cash = Arith.sub(Arith.div(orderMongo.getTotalPrice(), 1000),userPointMoney);
								OrderLogger.info("订单模块", "校验是否跳收银台", "用户使用银行积分支付，积分足够，计算结果", new Object[]{"积分比例",pointRate,"订单原始积分",bankPoint,"积分对应的金额",userPointMoney,"应该支付的现金",cash});
							}
						}else{
							OrderLogger.info("订单模块", "校验是否跳收银台", "获取用户银行积分为空", new Object[]{"clientId",cashierVoReq.getClientId(),"orderId",cashierVoReq.getOrderId(),"获取银行积分响应",JSON.toJSONString(userPointResult)});
							
							//获取失败，就认为积分为0
							cash = Arith.div(orderMongo.getTotalPrice(), 1000);
							if(EmptyChecker.isNotEmpty(orderMongo.getBankPoints()) && orderMongo.getBankPoints() > 0){
								orderMongo.setBankPoints(0);
								orderSupport.updateOrder(orderMongo);	
							}
						}
					}else{
						OrderLogger.error("订单模块", "校验是否跳收银台", "获取用户银行积分失败", new Object[]{"clientId",cashierVoReq.getClientId(),"orderId",cashierVoReq.getOrderId(),"获取银行积分响应",JSON.toJSONString(userPointResult)});
						//获取失败，就认为积分为0
						cash = Arith.div(orderMongo.getTotalPrice(), 1000);
						if(EmptyChecker.isNotEmpty(orderMongo.getBankPoints()) && orderMongo.getBankPoints() > 0){
							orderMongo.setBankPoints(0);
							orderSupport.updateOrder(orderMongo);	
						}
					}
					responseVo.setCash(cash);
					if(cash > 0){
						responseVo.setIsNeedCash("1");
					}else{
						responseVo.setIsNeedCash("0");
					}
				}
				
				//有方付通积分支付
				if(EmptyChecker.isNotEmpty(orderMongo.getFftPoints()) &&  orderMongo.getFftPoints() > 0){
					//检查用户方付通积分是否足够
					double fftPoint = Arith.div(orderMongo.getFftPoints(), 1000);
					ResultBean userPointResult = memberInformationSupport.queryUserPoints(cashierVoReq.getClientId(),cashierVoReq.getMemberName());
					
					//现金
					double cash = 0;
					if(EmptyChecker.isNotEmpty(userPointResult) && userPointResult.isSuccess()){
						MemberPointsInfo userPointsInfo = (MemberPointsInfo) userPointResult.getData();
						double userFftPoints = 0;
						if(EmptyChecker.isNotEmpty(userPointsInfo) && EmptyChecker.isNotEmpty(userPointsInfo.getFroadPoints())){
							userFftPoints = Double.valueOf(userPointsInfo.getFroadPoints());
							Integer pointRate = BaseSubassembly.acquireFroadPointPointRate(cashierVoReq.getClientId());
							if(EmptyChecker.isEmpty(pointRate)){
								OrderLogger.error("订单模块", "校验是否跳收银台", "获取用户联盟积分兑换比例为空", new Object[]{"clientId",cashierVoReq.getClientId(),"orderId",cashierVoReq.getOrderId()});
								return new ResultBean(ResultCode.failed,"获取联盟积分兑换比例为空");
							}
							if(userFftPoints < fftPoint){
								//计算应扣积分
								double userPointMoney = Arith.roundDown(Arith.div(userFftPoints, pointRate), 2);
								double userNeedPoint = Arith.roundUp(Arith.mul2(userPointMoney, pointRate), 2);
								//double cutMoney = Arith.div(EmptyChecker.isEmpty(orderMongo.getCutMoney())? 0 : orderMongo.getCutMoney(), 1000);
								cash = Arith.sub(Arith.div(orderMongo.getTotalPrice(), 1000),userPointMoney);
								orderMongo.setFftPoints(Arith.mul(userNeedPoint, 1000));
								responseVo.setCash(cash);
								//orderMongo.setRealPrice(Arith.mul(cash, 1000));
								//更新订单银行积分和现金
								orderSupport.updateOrder(orderMongo);
								OrderLogger.info("订单模块", "校验是否跳收银台", "用户使用联盟积分支付，用户积分不足，计算结果", new Object[]{"积分比例",pointRate,"订单原始积分",fftPoint,"用户现有积分",userFftPoints,"用户现有积分对应的金额",userPointMoney,"用户应扣联盟积分",userNeedPoint,"应该支付的现金",cash});
							}else{
								double userPointMoney = Arith.roundDown(Arith.div(fftPoint, pointRate), 2);
								cash = Arith.sub(Arith.div(orderMongo.getTotalPrice(), 1000),userPointMoney);
								responseVo.setCash(cash);
								OrderLogger.info("订单模块", "校验是否跳收银台", "用户使用联盟积分支付，积分足够，计算结果", new Object[]{"积分比例",pointRate,"订单原始积分",fftPoint,"积分对应的金额",userPointMoney,"应该支付的现金",cash});
							}
						}else{
							OrderLogger.info("订单模块", "校验是否跳收银台", "获取用户联盟积分为空", new Object[]{"clientId",cashierVoReq.getClientId(),"orderId",cashierVoReq.getOrderId(),"获取联盟积分响应",JSON.toJSONString(userPointResult)});
							//获取失败，就认为积分为0
							cash = Arith.div(orderMongo.getTotalPrice(), 1000);
							if(EmptyChecker.isNotEmpty(orderMongo.getFftPoints()) && orderMongo.getFftPoints() > 0){
								orderMongo.setFftPoints(0);
								orderSupport.updateOrder(orderMongo);	
							}
						}
					}else{
						OrderLogger.error("订单模块", "校验是否跳收银台", "获取用户联盟积分失败", new Object[]{"clientId",cashierVoReq.getClientId(),"orderId",cashierVoReq.getOrderId(),"获取联盟积分响应",JSON.toJSONString(userPointResult)});
						//获取失败，就认为积分为0
						cash = Arith.div(orderMongo.getTotalPrice(), 1000);
						if(EmptyChecker.isNotEmpty(orderMongo.getFftPoints()) && orderMongo.getFftPoints() > 0){
							orderMongo.setFftPoints(0);
							orderSupport.updateOrder(orderMongo);	
						}
					}
					
					responseVo.setCash(cash);
					if(cash > 0){
						responseVo.setIsNeedCash("1");
					}else{
						responseVo.setIsNeedCash("0");
					}
				}
				OrderLogger.info("订单模块", "检查是否跳收银台", "检查结果", new Object[]{"clientId",cashierVoReq.getClientId(),"orderId",cashierVoReq.getOrderId(),"订单总价",Arith.div(orderMongo.getTotalPrice(), 1000),"银行积分",Arith.div(orderMongo.getBankPoints(), 1000),"方付通积分",Arith.div(orderMongo.getFftPoints(), 1000),"现金",responseVo.getCash(),"是否跳收银台",responseVo.getIsNeedCash()});
				result = new ResultBean(ResultCode.success, responseVo);
				
			}else{
				OrderLogger.error("订单模块", "校验是否跳收银台", "获取大订单为空", new Object[]{"clientId",cashierVoReq.getClientId(),"orderId",cashierVoReq.getOrderId()});
				return new ResultBean(ResultCode.failed,"校验是否跳收银台失败，订单不存在");
			}
		} catch (Exception e) {
			LogCvt.error("[订单模块] - 校验是否跳收银台 - 系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"校验是否跳收银台失败，订单不存在");
		}
		return result;
	}
	
	@Override
	public ResultBean closeOrder(String clientId,String orderId) {
		ResultBean result = new ResultBean(ResultCode.success,"关闭订单成功");
		try {
			OrderMongo orderMongo = orderSupport.getOrderByOrderId(clientId,orderId);
			
			//如果是面对面订单，此接口为系统关闭面对面订单订单(2015.12.21)
			if(EmptyChecker.isNotEmpty(orderMongo) && EmptyChecker.isNotEmpty(orderMongo.getIsQrcode()) && orderMongo.getIsQrcode() == 1 && !StringUtils.equals(OrderStatus.sysclosed.getCode(), orderMongo.getOrderStatus())){
				OrderMongo orderUpdate = new OrderMongo();
				orderUpdate.setOrderId(orderMongo.getOrderId());		
				orderUpdate.setClientId(orderMongo.getClientId());
				orderUpdate.setBankPoints(0);
				orderUpdate.setPaymentMethod("");
				orderUpdate.setFftPoints(0);
				orderUpdate.setPointRate("0");
				orderUpdate.setRealPrice(0);
				orderUpdate.setIsQrcode(1);
				orderUpdate.setQrcode(orderMongo.getQrcode());	
				orderUpdate.setOrderStatus(OrderStatus.sysclosed.getCode());
				OrderLogger.info("关闭订单", "面对面订单支付失败，关闭面对面订单", "请求参数", new Object[]{"clientId",clientId,"orderId",orderId});
				result = updateOrderForPay(orderUpdate);
				closeMarketOrder(orderMongo, false);
			}
		}catch(Exception e){
			LogCvt.error("关闭订单-业务异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,e.getMessage());
		}
		
		try {
			//大数据平台-调用日志
			OrderMongo order = orderSupport.getOrderById(clientId, orderId);
			List<SubOrderMongo> subOrderList = orderSupport.getSubOrderListByOrderId(clientId, orderId);
			createOrderLog(order, subOrderList, "ORDERMODIFY");
			
			//订单关联营销活动时，调用营销平台回退活动资格
			if(StringUtils.equals(order.getIsActive(), "1")){
				closeMarketOrder(order,false);
			}
		} catch (Exception e) {
			LogCvt.error("关闭订单-调用数据平台|调用营销平台-异常：" + e.getMessage(), e);
		}
		
		return result;
	}
	
	@Override
	public ResultBean addPrefPayOrder(OrderMongo orderMongo) {
		LogCvt.info("Mongo：惠付订单创建");
		ResultBean result = new ResultBean(ResultCode.success,"惠付订单创建成功");
		try {
			dataEmptyChecker(orderMongo, "orderMongo为null");
			boolean orderResult = orderSupport.addOrder(orderMongo);
			if (!orderResult) {
				return new ResultBean(ResultCode.failed,"惠付订单创建失败");
			}
			result = new ResultBean(ResultCode.success,"惠付订单创建成功",orderMongo.getOrderId());
		}catch(Exception e){
			LogCvt.error("惠付订单创建-系统异常："+e.getMessage(),e);
			return new ResultBean(ResultCode.failed,"惠付订单创建失败");
		}
		return result;
	}
	
	@Override
	public String getVipOrderId(String clientId, long memberCode) {
		String vipOrderId = null;
		try {
			List<OrderMongo> list = orderSupport.getVipOrderList(clientId, memberCode, true);
			if (EmptyChecker.isNotEmpty(list)) {
				vipOrderId = list.get(0).getOrderId();
			}
		}catch(Exception e){
			LogCvt.error("获取用户最近一笔VIP订单-系统异常："+e.getMessage(),e);
		}
		return vipOrderId;
	}
	
	public static void main(String[] args){
//		System.setProperty("CONFIG_PATH", "./config");
		PropertiesUtil.load();
		OrderLogicImpl logic = new OrderLogicImpl();
		/*OrderQueryCondition orderQueryCondition = new OrderQueryCondition();
		orderQueryCondition.setClientId("anhui");
		orderQueryCondition.setOrderId("0B7405E48000");
		logic.getOrderDetail(orderQueryCondition);*/
		
//		logic.updateUnitProductCutPoint("chongqing", "146F2BB38000");//0F29DC210000  0D821DA18005  134A69C28000  134C16328000
		
//		logic.deleteUnitProductCutPoint("chongqing", "134A9AC28000");
		
		/*OrderQueryCondition condition = new OrderQueryCondition();
		condition.setEndTime(0L);
		condition.setStartTime(1423152000000L);
		condition.setClientId("anhui");
		condition.setMemberCode(40000000935L);
		OrderSupportImpl support = new OrderSupportImpl();
		
		PageEntity<OrderQueryCondition> page = new PageEntity<OrderQueryCondition>();
		page.setCondition(condition);
		page.setFirstRecordTime(0L);
		page.setLastRecordTime(0L);
		page.setLastPageNumber(0);
		ResultBean result = logic.getOrderSummary(page);
		System.out.println(JSON.toJSONString(result,true));*/
		
		
//		OrderQueryCondition condition = new OrderQueryCondition();
//		condition.setEndTime(new Date().getTime());
//		condition.setClientId(10L);
//		condition.setMemberCode(10000L);
//		OrderSupportImpl support = new OrderSupportImpl();
//		PageEntity<OrderQueryCondition> pageCondition = new PageEntity<OrderQueryCondition>();
//		pageCondition.setPageNumber(1);
//		pageCondition.setCondition(condition);
//		MongoPage page = support.getOrderByConditioinOfPage(pageCondition);
		
		//updateOrderForPay(OrderMongo order)
		/*OrderMongo order = new OrderMongo();
		order.setOrderId("01E643280000");*/
//		order.setOrderStatus("2");
		
//		logic.updateProductSellCount(order);
		
		
//		Map<String, Integer> enableRefoundMap = logic.refundLogic.getCanRefundProductList("01A23EC50000");
//		Map<String, Integer> enableRefoundMap = logic.refundLogic.getCanRefundProductList("01E675CA0000");
//		System.out.println(JSON.toJSONString(enableRefoundMap,true));
		
//		System.out.println(logic.ticketLogic.isProductConsumed("anhui", 50000000225L, "02119B9A8000", "01B8FEA18000"));
		
//		System.out.println(logic.paymentSupport.queryOrderPaymentChannel("01BE94150000"));
//		System.out.println(JSON.toJSONString(logic.getOrderByQrcode("anhui","1001BE90158001"),true));
//		System.out.println(logic.ticketLogic.isTicketExist("anhui","02119B9A8000"));
//		condition.setOrderId("02A3BEFF0000");
//		System.out.println(JSON.toJSONString(logic.getOrderDetail(condition),true));
		
		
//		ResultBean result = logic.getMemberBuyLimit("anhui","03652BB08000", 30005240997L,false, "03875E710000");
//		ResultBean result = logic.getStoreListByOrderId("anhui","036543D08000");
//		System.out.println(JSON.toJSONString(result,true));
		
//		System.out.println(JSON.toJSONString(RedisCommon.getOutletRedis("anhui", "040C6C308023", "040C79A08007"),true));
		/*OrderQueryCondition orderQueryCondition = new OrderQueryCondition();
		orderQueryCondition.setClientId("anhui");
		orderQueryCondition.setOrderId("071992248000");
		ResultBean result = logic.getOrderDetail(orderQueryCondition);
		System.out.println(JSON.toJSONString(result,true));*/
		
//		double totalMoney = Arith.div(Arith.add(Arith.add(Arith.mul(79900, 3) ,  Arith.mul(1 , 1)) , 0),1000);

		/*CommonLogic commonLogic = new CommonLogicImpl();
		Map<String,String> map = commonLogic.getVipProductRedis("anhui");
		System.out.println(JSON.toJSONString(map,true));*/


		/*double money = Arith.div(79900, 1000);
		System.out.println(money);
		System.out.println(Arith.mul(money , (double)3));
		double totalMoney = Arith.mul(money , (double)3) +  Arith.mul(0d,0) + 0;
		System.out.println(totalMoney);*/
		
		/*OrderSupportImpl orderSupport = new OrderSupportImpl();
		ProductDetail productDetail = orderSupport.queryProductDetail("08E818610000");
		System.out.println(JSON.toJSONString(productDetail,true));
		if(EmptyChecker.isEmpty(productDetail)){
			LogCvt.error("[严重错误] mongo | cb_product_detail 查询结果为空，查询条件：{productId：08E818610000");
		}*/
		
		
		/*OrderQueryCondition orderQueryCondition = new OrderQueryCondition();
		orderQueryCondition.setClientId("chongqing");
		orderQueryCondition.setOrderId("12759E918000");
		ResultBean result = logic.getOrderDetail(orderQueryCondition);
		System.out.println(JSON.toJSONString(result,true));*/
		
		logic.closeOrder("chongqing", "1555B1B20000");
	}

}
