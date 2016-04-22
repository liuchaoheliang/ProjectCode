package com.froad.timer.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.thrift.TException;

import com.froad.db.mongo.OrderMongoService;
import com.froad.db.mongo.SubOrderMongoService;
import com.froad.db.redis.MemberHistoryVipRedisService;
import com.froad.db.redis.MemberProductLimitRedisService;
import com.froad.db.redis.ProductPresellTokenRedisService;
import com.froad.db.redis.TimeOrderRedisService;
import com.froad.enums.DeliveryType;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.OrderState;
import com.froad.enums.OrderStatus;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ResultCode;
import com.froad.enums.SubOrderType;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.service.OrderService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.CloseMarketOrderReqVo;
import com.froad.thrift.vo.order.RefundPayingOrderVoReq;
import com.froad.thrift.vo.order.RefundPayingOrderVoRes;
import com.froad.thrift.vo.order.StoreVoReq;
import com.froad.thrift.vo.order.StoreVoRes;
import com.froad.util.AllkindsTimeConfig;
import com.froad.util.RedisKeyUtil;
import com.froad.util.ServiceFactory;
import com.froad.util.ThriftConfig;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * 
 * @ClassName: TaskCloseTrans 
 * @Description: 定时关闭订单
 * @author: huangyihao
 * @modify: lf 20150519
 * @date: 2015年3月24日
 * @
 */
public class TaskCloseTrans implements Runnable {
	
	private OrderMongoService orderMongoService = ServiceFactory.getOrderMongoService();
	
	private TimeOrderRedisService orderRedisService = ServiceFactory.getTimeOrderRedisService();
	private MemberProductLimitRedisService memberProductLimitRedisService = ServiceFactory.getMemberProductLimitRedisService();
	private SubOrderMongoService subOrderMongoService = ServiceFactory.getSubOrderMongoService();
	private MemberHistoryVipRedisService memberHistoryVipRedisService = ServiceFactory.getMemberHistoryVipRedisService();
	private ProductPresellTokenRedisService productPresellTokenRedisService = ServiceFactory.getProductPresellTokenRedisService();
	private OrderService.Iface orderServiceClient = 
			(OrderService.Iface)ThriftClientProxyFactory.createIface(OrderService.class.getName(), OrderService.class.getSimpleName(), ThriftConfig.ORDER_SERVICE_HOST, ThriftConfig.ORDER_SERVICE_PORT);
	private ActiveRunService.Iface activeRunServiceClient = 
			(ActiveRunService.Iface)ThriftClientProxyFactory.createIface(ActiveRunService.class.getName(), ActiveRunService.class.getSimpleName(), ThriftConfig.ACTIVITIES_SERVICE_HOST, ThriftConfig.ACTIVITIES_SERVICE_PORT);
	/***********************Mongo KEY 常量************************/
	private static final String CLIENTID="client_id";
	private static final String ID="_id";
	private static final String CREATETIME="create_time";
	private static final String ORDERSTATUS="order_status";
	private static final String PAYMENTMETHOD="payment_method";
	private static final String STATE="state";
	private static final String ORDERID="order_id";
	private static final String FLAG="0";
	private static final String needCloseTran="2";//不关单条件
	/***********************Mongo KEY 常量************************/
	/*********************** 常量************************/
	private static int closeNum=0;//定时关单长量
	/*********************** 常量************************/
	/**
	 * 监控服务
	 * */
	private MonitorService monitorService = new MonitorManager();
	
	private final String COMMAND_SET = "$set";
	
	@Override
	public void run() {
		LogCvt.debug("定时任务: 定时关闭订单------开始------");
		
		try {
			
			

			// 查询缓存中cbbank:time_order中所有订单
			Set<String> orderList =  orderRedisService.getOrderInfo();
			if(CollectionUtils.isEmpty(orderList)){
				LogCvt.debug("定时任务: 定时关闭订单------完成(缓存 cbbank:time_order无订单信息)------");
				return;
			}
			
			LogCvt.debug("缓存cbbank:time_order中共有"+orderList.size()+"条订单信息");
//                       _ooOoo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                       O\ = /O
//                   ____/`---'\____
	/*********************************************************************/
	/**                                                                  */
	/** 两小时 - 支付方式(现金) - 支付状态(支付中) - 订单状态(正常)               */
	/**               ①关单(成功后)②退库存                                                                  */
	/**                                                                  */
	/** 两小时 - 支付方式(积分+现金) - 支付状态(支付中) - 订单状态(正常)           */
	/**            ①关单(成功后)②退库存    ③退积分                                                       */
	/**                                                                  */
	/** 两小时 - 支付方式(无) - 支付状态(订单创建) - 订单状态(库存已退)            */
	/**            ①关单                                                                                                  */
	/**                                                                  */
	/** 两小时 - 支付状态(订单支付失败)                                        */
	/**            ①关单                                                                                                   */
	/**                                                                  */
	/*********************************************************************/
			DBObject setObj = null, query = null;
			for(String orderInfo : orderList){
				
				LogCvt.debug("定时任务: 定时关闭订单------"+orderInfo+" 处理开始------");
				
				// 状态都改为 系统关闭
				setObj = getDBObjectOfUpdateOrder_S();
				
				// 查询 两小时 - 支付方式(现金|积分+现金) - 支付状态(支付中) - 订单状态(正常)
				if(!twoHourTimeTask(orderInfo,setObj)){
					continue;
				}
				
				if(!twoHourTimeTask1(orderInfo,setObj)){
				// 查询 两小时 - 支付方式(无) - 支付状态(订单创建) - 订单状态(库存已退)
					continue;
				}
				if(!twoHourTimeTask2(orderInfo,setObj)){
					// 查询 两小时 - 支付状态(订单支付失败)
					continue;
				}
				
				
				LogCvt.debug("定时任务: 定时关闭订单------"+orderInfo+" 处理结束------");
				}
			
		} catch (Exception e) {
			LogCvt.error("定时任务: 定时关闭订单------系统异常------");
			LogCvt.error(e.getMessage(),e);
			// TODO 关闭订单处理异常 - 发送监控
			monitorService.send(MonitorPointEnum.Timertask_Close_Tran_Failure);
		} finally {
			LogCvt.debug("定时任务: 定时关闭订单------结束------");
		}
	}
	
	// 两小时 - 支付状态(订单支付失败)
	private DBObject getDBObjectOfUpdateOrder_Q_3(String orderInfo){
		String [] tValue = orderInfo.split(":");
		String clientId = tValue[0];
		String orderId = tValue[1];
		BasicDBObject query = new BasicDBObject();
		query.put(CLIENTID, clientId);
		query.put(ID, orderId);
		// 创建时间 - 少于配置的小时数
		DBObject ctObj = new BasicDBObject(QueryOperators.LT, DateUtils.addHours(new Date(), -AllkindsTimeConfig.getCloseTransAnHourBefore()).getTime());
		query.put(CREATETIME, ctObj);
		// 支付状态 - 订单支付失败
		query.put(ORDERSTATUS, OrderStatus.payfailed.getCode());
		return query;
	}
	
	// 两小时 - 支付方式(无) - 支付状态(订单创建) - 订单状态(库存已退)
	private DBObject getDBObjectOfUpdateOrder_Q_2(String orderInfo){
		String [] tValue = orderInfo.split(":");
		String clientId = tValue[0];
		String orderId = tValue[1];
		BasicDBObject query = new BasicDBObject();
		query.put(CLIENTID, clientId);
		query.put(ID, orderId);
		// 创建时间 - 少于配置的小时数
		DBObject ctObj = new BasicDBObject(QueryOperators.LT, DateUtils.addHours(new Date(), -AllkindsTimeConfig.getCloseTransAnHourBefore()).getTime());
		query.put(CREATETIME, ctObj);
		// 支付方式(无)，也有可能没有这个字段,vip订单没有此字段
		BasicDBList paymentObject = new BasicDBList();
		paymentObject.add(new BasicDBObject(PAYMENTMETHOD, ""));
		paymentObject.add(new BasicDBObject(PAYMENTMETHOD, null));
		paymentObject.add(new BasicDBObject(PAYMENTMETHOD, new BasicDBObject(QueryOperators.EXISTS,false)));
		query.put(QueryOperators.OR, paymentObject);
	//	query.put(PAYMENTMETHOD, "");
		// 支付状态 - 订单创建
		query.put(ORDERSTATUS, OrderStatus.create.getCode());
		// 订单状态 - 库存已退
		query.put(STATE, OrderState.RETURNED.getCode());
		return query;
	}
	
	// 两小时 - 支付方式(现金)(积分+现金) - 支付状态(支付中) - 订单状态(正常)
	private DBObject getDBObjectOfUpdateOrder_Q(String orderInfo){
		String [] tValue = orderInfo.split(":");
		String clientId = tValue[0];
		String orderId = tValue[1];
		BasicDBObject query = new BasicDBObject();
		query.put(CLIENTID, clientId);
		query.put(ID, orderId);
		// 创建时间 - 少于配置的小时数
		DBObject ctObj = new BasicDBObject(QueryOperators.LT, DateUtils.addHours(new Date(), -AllkindsTimeConfig.getCloseTransAnHourBefore()).getTime());
		query.put(CREATETIME, ctObj);
		// 支付方式 - 现金(积分+现金)
		BasicDBList paymentObject = new BasicDBList();
		paymentObject.add(new BasicDBObject(PAYMENTMETHOD, PaymentMethod.cash.getCode()));
		paymentObject.add(new BasicDBObject(PAYMENTMETHOD, PaymentMethod.froadPointsAndCash.getCode()));
		paymentObject.add(new BasicDBObject(PAYMENTMETHOD, PaymentMethod.bankPointsAndCash.getCode()));
		query.put(QueryOperators.OR, paymentObject);
//		query.append(QueryOperators.OR, new BasicDBObject[]{new BasicDBObject("payment_method", PaymentMethod.cash.getCode()),
//				                                            new BasicDBObject("payment_method", PaymentMethod.froadPointsAndCash.getCode()), 
//				                                            new BasicDBObject("payment_method", PaymentMethod.bankPointsAndCash.getCode())});
		// 支付状态 - 支付中
		query.put(ORDERSTATUS, OrderStatus.paying.getCode());
		// 订单状态 - 正常
		query.put(STATE, OrderState.NORMAL.getCode());
		return query;
	}
	
	// 状态都改为 系统关闭
	private DBObject getDBObjectOfUpdateOrder_S(){
		DBObject setObj = new BasicDBObject();
		setObj.put(STATE, OrderState.SYSTEM_CLOSED.getCode());
		setObj.put(ORDERSTATUS, OrderStatus.sysclosed.getCode());
		return setObj;
	}
	
	// 订单的其它处理
	private void orderProcessOfOther(String orderInfo, OrderMongo orderMongo){
		
		try{
		
			// 清除cbbank:time_order缓存中的过期订单
			boolean result = orderRedisService.removeOrder(orderInfo);
			LogCvt.debug("订单缓存cbbank:time_order["+orderInfo+"]删除"+(result?"成功":"失败"));
			
			// 更新历史会员vip金额
//	    	Long vipDiscount = Long.valueOf(ObjectUtils.toString(orderMongo.getVipDiscount(), "0"));
//			updateVipHistory(orderMongo.getClientId(), orderMongo.getMemberCode(), vipDiscount);
			
			List<SubOrderMongo> subOrderMongos = subOrderMongoService.findByCondition(orderInfo);
//			// 修改状态 - 子订单
			updateStatusByOrderInfo(orderInfo);
//			// 根据子订单根据相关缓存
			updateRedisBySubOrder(subOrderMongos);
		
			// 更新用户VIP购买记录
			if(orderMongo.getIsVipOrder()==1){
				updateUserVIPOrderRedis(orderMongo.getClientId(), orderMongo.getMemberCode(), false);
			}
			//判断是否优惠订单
			closeFavOrder(orderMongo);
		}catch(Exception e){
			LogCvt.error("cb_order订单["+orderInfo+"] 其它处理 - 异常 "+e.getMessage(), e);
		}
	}
	
	// 更新用户VIP购买记录
	public void updateUserVIPOrderRedis(String clientId,Long memberCode,boolean isSuccess){
		String key = RedisKeyUtil.cbbank_vip_order_client_id_member_code(clientId,memberCode);
		LogCvt.info("Redis 更新用户VIP订单状态：{key：" + key + ",是否开通:" + isSuccess + "}");
			if(isSuccess){
				LogCvt.info("用户创建VIP订单：已创建");
				memberHistoryVipRedisService.updateUserVIPOrderRedis(key, String.valueOf(BooleanUtils.toInteger(isSuccess)));
			}else{
				LogCvt.info("用户创建VIP订单：未创建");
				memberHistoryVipRedisService.updateUserVIPOrderRedis(key, String.valueOf(BooleanUtils.toInteger(isSuccess)));
			}
	}
	
	// 退库存(调用第三方的方法-方法内判断是否退积分)
	private void returnStore(String orderInfo){
		String [] tValue = orderInfo.split(":");
		String clientId = tValue[0];
		String orderId = tValue[1];
		StoreVoReq reqVo = new StoreVoReq();
		reqVo.setClientId(clientId);
		reqVo.setOrderId(orderId);
		reqVo.setOperationType("1"); //1 加库存（还库存）  0 减库存（扣库存）
		// 调用接口退库存(调用第三方的方法-方法内判断是否退积分)
		try {
			LogCvt.debug("cb_order订单["+orderInfo+"] 退库存操作 - 开始");
			StoreVoRes resVo = orderServiceClient.storeProcess(reqVo);
			ResultVo resultVo = resVo.getResultVo();
			LogCvt.debug("cb_order订单["+orderInfo+"] 退库存操作 - 处理结果: 返回码[resultCode="+resultVo.getResultCode()+"]|返回信息[resultDesc="+resultVo.getResultDesc()+"]");
			if( !StringUtils.equals(ResultCode.success.getCode(), resultVo.getResultCode()) ){
				// TODO 退库存处理不成功 - 发送监控
				monitorService.send(MonitorPointEnum.Timertask_Return_Store_Failure);
			}
		} catch (Exception e) {
			LogCvt.error("cb_order订单["+orderInfo+"] 退库存操作 - 异常 "+e.getMessage(), e);
			// TODO 退库存处理异常 - 发送监控
			monitorService.send(MonitorPointEnum.Timertask_Return_Store_Failure);
		}
		
	}
	private boolean returnPoints(String orderInfo){
		String [] tValue = orderInfo.split(":");
		String clientId = tValue[0];
		String orderId = tValue[1];
		RefundPayingOrderVoReq rporderVoReq=new RefundPayingOrderVoReq();
		rporderVoReq.setClientId(clientId);
		rporderVoReq.setOrderId(orderId);
		try {
			LogCvt.info("cb_order面对面订单["+orderInfo+"] 退积分操作 - 开始");
			RefundPayingOrderVoRes rporderVoRes= orderServiceClient.refundPayingOrder(rporderVoReq);
			ResultVo resultVo=rporderVoRes.getResultVo();
			if(resultVo!=null){
				LogCvt.debug("cb_order面对面订单["+orderInfo+"] 退积分操作 - 处理结果: 返回码[resultCode="+resultVo.getResultCode()+"]|返回信息[resultDesc="+resultVo.getResultDesc()+"]");
				if(resultVo.getResultCode().equals(ResultCode.success.getCode())){
					return true;
				}else{
					return false;
				}
			}
//			if( !StringUtils.equals(ResultCode.success.getCode(), resultVo.getResultCode()) ){
//				//monitorService.send(MonitorPointEnum.Timertask_Return_Store_Failure);
//				LogCvt.
//			}
		} catch (Exception e) {
			LogCvt.error("cb_order订单["+orderInfo+"] 退积分操作- 异常 "+e.getMessage(), e);
		//	monitorService.send(MonitorPointEnum.Timertask_Return_Store_Failure);
		}
		  LogCvt.info("cb_order面对面订单["+orderInfo+"] 退积分操作 - 结束");
		  return false;
	}
	// 修改状态 - 子订单
	private void updateStatusByOrderInfo(String orderInfo){
		String [] tValue = orderInfo.split(":");
		String clientId = tValue[0];
		String orderId = tValue[1];
		DBObject query = null, setObj = null;
		query = new BasicDBObject();
		query.put(CLIENTID, clientId);
		query.put(ORDERID, orderId);
		setObj = new BasicDBObject();
		setObj.put("order_status", OrderStatus.sysclosed.getCode());
		Boolean result = subOrderMongoService.modifySubOrder(setObj, query, COMMAND_SET);
		LogCvt.info(new StringBuffer("定时关闭订单 - 修改订单 ").append(orderId).append(" 的子订单的状态:").append(result?"成功":"失败").toString());
	}
	
	// 修改状态 - 子订单
//	private void updateStatusBySubOrder(List<SubOrderMongo> subOrderMongos){
//		try{
//			if(CollectionUtils.isNotEmpty(subOrderMongos)){
//				DBObject query = null, setObj = null;
//				for(SubOrderMongo subOrder : subOrderMongos){
//					query = new BasicDBObject();
//					query.put("client_id", subOrder.getClientId());
//					query.put("sub_order_id", subOrder.getSubOrderId());
//					setObj = new BasicDBObject();
//					setObj.put("order_status", OrderStatus.sysclosed.getCode());
//					Boolean result = subOrderMongoService.modifySubOrder(setObj, query, COMMAND_SET);
//					LogCvt.info(new StringBuffer("定时关闭订单 - 修改子订单 ").append(subOrder.getId()).append(" 状态:").append(result?"成功":"失败").toString());
//				}
//			}
//		}catch(Exception e){
//			LogCvt.error("定时关闭订单 - 修改子订单状态 - 异常:"+e.getMessage(),e);
//		}
//	}
	
	
	/**
	 * 
	 * @Title: updateRedisBySubOrder 
	 * @Description: // 根据子订单根据相关缓存
	 * @author: froad-huangyihao 2015年4月25日
	 * @modify: froad-huangyihao 2015年4月25日
	 * @param subOrderMongos
	 * @throws
	 */
	private void updateRedisBySubOrder(List<SubOrderMongo> subOrderMongos){
		if(CollectionUtils.isNotEmpty(subOrderMongos)){
			for(SubOrderMongo sub : subOrderMongos){
				List<ProductMongo> productInfo = sub.getProducts();
				if(CollectionUtils.isNotEmpty(productInfo)){
					for(ProductMongo p : productInfo){
						Long count = -Long.valueOf(ObjectUtils.toString(p.getQuantity(),"0"));
						Long vipCount = -Long.valueOf(ObjectUtils.toString(p.getVipQuantity(),"0"));
						// 更新会员限购缓存
						updateProductLimit(sub.getClientId(), sub.getMemberCode(), p.getProductId(), count, vipCount);
						// 如果是预售子订单和自提预售商品
						if(StringUtils.equals(SubOrderType.presell_org.getCode(), sub.getType()) 
								&& StringUtils.equals(DeliveryType.take.getCode(), p.getDeliveryOption())){
							// 更新门店商品提货数量
							updateProductPresellToken(sub.getClientId(), p.getOrgCode(), p.getProductId(), count);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @Title: updateProductLimit 
	 * @Description: 更新会员限购缓存 - 减少购买数量
	 * @author: froad-huangyihao 2015年4月25日
	 * @modify: froad-huangyihao 2015年4月25日
	 * @param clientId
	 * @param memberCode
	 * @param productId
	 * @param count
	 * @param vipCount
	 * @throws FroadBusinessException
	 */
	private void updateProductLimit(String clientId, Long memberCode, String productId, Long count, Long vipCount){
		String key = RedisKeyUtil.cbbank_product_limit_client_id_member_code_product_id(clientId, memberCode, productId);
		
		Long oldCount = memberProductLimitRedisService.getCount(clientId, memberCode, productId);
		Long oldVipCount = memberProductLimitRedisService.getVipCount(clientId, memberCode, productId);
		
		Long newCount = memberProductLimitRedisService.incrCount(clientId, memberCode, productId, count);
		Long newVipCount = memberProductLimitRedisService.incrVipCount(clientId, memberCode, productId, vipCount);
		
		// 判断是否需要重置count/vip_count
		newCount = memberProductLimitRedisService.resetCount(clientId, memberCode, productId, newVipCount);
		newVipCount = memberProductLimitRedisService.resetVipCount(clientId, memberCode, productId, newVipCount);
		
		if(ObjectUtils.equals(count, 0l) || !ObjectUtils.equals(oldCount, newCount)){
			LogCvt.debug("会员限购缓存<key为"+key+" field为count>[memberCode="+memberCode+"]|[productId="+productId+"] 更新成功");
		}else{
			LogCvt.error("会员限购缓存<key为"+key+" field为count>[memberCode="+memberCode+"]|[productId="+productId+"] 更新失败");
		}
		
		if(ObjectUtils.equals(vipCount, 0l) || !ObjectUtils.equals(oldVipCount, newVipCount)){
			LogCvt.debug("会员限购缓存<key为"+key+" field为vip_count>[memberCode="+memberCode+"]|[productId="+productId+"] 更新成功");
		}else{
			LogCvt.error("会员限购缓存<key为"+key+" field为vip_count>[memberCode="+memberCode+"]|[productId="+productId+"] 更新失败");
		}
		
	}
	
	/**
	 * 
	 * @Title: updateVipHistory 
	 * @Description: 更新会员历史vip优惠金额缓存
	 * @author: froad-huangyihao 2015年4月27日
	 * @modify: froad-huangyihao 2015年4月27日
	 * @param clientId
	 * @param memberCode
	 * @param vipDiscount
	 * @throws
	 */
	private void updateVipHistory(String clientId, Long memberCode, Long vipDiscount){
		Long oldVipDiscount = memberHistoryVipRedisService.getVipDiscount(clientId, memberCode);
		Long newVipDiscount = memberHistoryVipRedisService.decrVipDiscount(clientId, memberCode, vipDiscount);
		// 判断是否需要重置vipdiscount
		newVipDiscount = memberHistoryVipRedisService.resetVipDiscount(clientId, memberCode, newVipDiscount);
		
		if(ObjectUtils.equals(vipDiscount, 0l) || !ObjectUtils.equals(oldVipDiscount, newVipDiscount)){
			LogCvt.debug("会员历史vip优惠金额缓存[memberCode="+memberCode+"] 更新成功");
		}else{
			LogCvt.error("会员历史vip优惠金额缓存[memberCode="+memberCode+"] 更新失败");
		}
		
	}
	
	/**
	 * 
	 * @Title: updateProductPresellToken 
	 * @Description: 更新预售商品门店提货数量
	 * @author: froad-huangyihao 2015年4月27日
	 * @modify: froad-huangyihao 2015年4月27日
	 * @param clientId
	 * @param orgCode
	 * @param productId
	 * @param count
	 * @throws
	 */
	private void updateProductPresellToken(String clientId, String orgCode, String productId, Long count){
		Long oldCount = productPresellTokenRedisService.getCount(clientId, orgCode, productId);
		
		Long newCount = productPresellTokenRedisService.decrCount(clientId, orgCode, productId, count);
		// 判断是否需要重置count
		newCount = productPresellTokenRedisService.resetCount(clientId, orgCode, productId, newCount);
		
		if(ObjectUtils.equals(count, 0) || !ObjectUtils.equals(newCount, oldCount)){
			LogCvt.debug("预售商品[productId="+productId+"]门店[orgCode="+orgCode+"]提货数量更新成功");
		}else{
			LogCvt.error("预售商品[productId="+productId+"]门店[orgCode="+orgCode+"]提货数量更新失败");
		}
	}
	public boolean twoHourTimeTask(String orderInfo,DBObject setObj ){
		DBObject query = getDBObjectOfUpdateOrder_Q(orderInfo);
		LogCvt.debug("定时任务: 定时关闭订单------"+orderInfo+" 查询 1两小时2支付方式(现金|积分+现金)3支付状态(支付中)4订单状态(正常) 并修改状态------");
		
		 if(!updateOrder(setObj,query,orderInfo,FLAG)){
			   return false;
		   }else{
			   LogCvt.debug("定时任务: 定时关闭订单------"+orderInfo+" 查询 1两小时2支付方式(现金|积分+现金)3支付状态(支付中)4订单状态(正常) 并修改状态 不满足条件------");
				return true;
		   }
		
	}
	public boolean twoHourTimeTask1(String orderInfo,DBObject setObj){
		// 查询 两小时 - 支付方式(无) - 支付状态(订单创建) - 订单状态(库存已退)
		DBObject	query = getDBObjectOfUpdateOrder_Q_2(orderInfo);
		LogCvt.debug("定时任务: 定时关闭订单------"+orderInfo+" 查询 1两小时2支付方式(无)3支付状态(订单创建)4订单状态(库存已退) 并修改状态------");
		
		// 更改大订单状态
		   if(!updateOrder(setObj,query,orderInfo,"")){
			   return false;
		   }else{
			   
			   LogCvt.debug("定时任务: 定时关闭订单------"+orderInfo+" 查询 1两小时2支付方式(无)3支付状态(订单创建)4订单状态(库存已退) 并修改状态 不满足条件------");
			   return true;
		   }
	}
	
	
	public boolean twoHourTimeTask2(String orderInfo,DBObject setObj){
		// 查询 两小时 - 支付状态(订单支付失败)
		DBObject query = getDBObjectOfUpdateOrder_Q_3(orderInfo);
		LogCvt.debug("定时任务: 定时关闭订单------"+orderInfo+" 查询 1两小时2支付状态(订单支付失败) 并修改状态------");
		
		// 更改大订单状态
		   if(!updateOrder(setObj,query,orderInfo,"")){
			   return false;
		   }else{
				LogCvt.debug("定时任务: 定时关闭订单------"+orderInfo+" 查询 1两小时2支付状态(订单支付失败) 并修改状态 不满足条件------");
				return true;
		   }
	
	}
	//关闭优惠订单
	public void closeFavOrder(OrderMongo orderMongo){
		if(orderMongo.getIsActive()!=null&&orderMongo.getIsActive().equals("1")){
			//调用营销接口
			CloseMarketOrderReqVo closeVo=new CloseMarketOrderReqVo();
			ResultVo result=null;
			try {
				closeVo.setReqId(orderMongo.getOrderId());
				closeVo.setClientId(orderMongo.getClientId());
				closeVo.setOrderId(orderMongo.getOrderId());
				closeVo.setMemberCode(orderMongo.getMemberCode());
				closeVo.setReason(closeNum);
			 result=activeRunServiceClient.closeMarketOrder(closeVo);
			} catch (TException e) {
				LogCvt.error("定时任务: 定时关闭订单------调用营销优惠接口失败,原因为:"+e.getMessage(),e);
			}
			LogCvt.debug("定时任务: 定时关闭订单------调用营销优惠接口成功,返回结果code="+result.getResultCode()+",desc="+result.getResultDesc());
		}
	}
	
	public boolean updateOrder(DBObject setObj,DBObject query,String orderInfo,String flag){
		//面对面支付中处理,先调用退积分再关单处理
		if(!FaceToFactPaying(setObj,query,orderInfo,flag).equals(needCloseTran)){
			OrderMongo	orderMongo = orderMongoService.updateOrder(setObj, query, COMMAND_SET);
			if( orderMongo != null ){
				
				LogCvt.debug("定时任务: 定时关闭订单------"+orderInfo+" 大订单关单完成------");
				
				// 订单的其它处理
				orderProcessOfOther(orderInfo, orderMongo);
				if( 1 != orderMongo.getIsQrcode()&&flag.equals(FLAG)){ // 不是面对面 - 退库存操作
					returnStore(orderInfo);
				}
				return false;
			}
		}
		return true;
	    }
	
	public String FaceToFactPaying(DBObject setObj,DBObject query,String orderInfo,String flag){
		boolean result=false;
		String needCloseTrans="0";//0代表不属于面对面订单的正常关单,1代表面对面支付调用成功关单,2代表调用失败不关单
        if(flag.equals(FLAG)){
		//先查询出是否为面对面支付中订单
    		OrderMongo	orderMongo=	orderMongoService.findOrderMongo(query);
        	if(orderMongo!=null){
        		if(!orderMongo.getPaymentMethod().equals(PaymentMethod.cash.getCode())){//支付中的面对面-退积分操作,普通订单支付中也需先退积分-->关单-->退库存
        			result=	returnPoints(orderInfo);
        			if(result){//成功关闭订单,失败不处理
        			  needCloseTrans="1";
        		      return needCloseTrans;
        			}else{
        				needCloseTrans="2";
        				return needCloseTrans;
        			}
    			}	
        	}
        	
		}
        return needCloseTrans;
	}
	
	
	}

