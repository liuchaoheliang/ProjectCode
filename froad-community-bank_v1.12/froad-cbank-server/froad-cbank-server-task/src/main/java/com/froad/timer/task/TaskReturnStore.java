package com.froad.timer.task;

import java.util.Date;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.froad.db.mongo.OrderMongoService;
import com.froad.db.redis.TimeOrderRedisService;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.OrderState;
import com.froad.enums.OrderStatus;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.mongo.OrderMongo;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.OrderService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.order.StoreVoReq;
import com.froad.thrift.vo.order.StoreVoRes;
import com.froad.util.AllkindsTimeConfig;
import com.froad.util.ServiceFactory;
import com.froad.util.ThriftConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * 
 * 返回库存 - 定时任务
 * 
 * @author lf 2015.03.20
 * @modify lf 2015.03.24
 * 
 * */
public class TaskReturnStore implements Runnable {

	private OrderMongoService orderMongoService = ServiceFactory.getOrderMongoService();
	private TimeOrderRedisService timeOrderRedisService = ServiceFactory.getTimeOrderRedisService();
	/**
	 * 监控服务
	 * */
	private MonitorService monitorService = new MonitorManager();
	// mongo 表名 字段名 命令
	private final String MONGO_KEY_STATE = "state";
	private final String MONGO_KEY__ID = "_id";
	private final String MONGO_KEY_CLIENT_ID = "client_id";
	private final String MONGO_KEY_ORDER_STATUS = "order_status";
	private final String MONGO_KEY_IS_SECKILL = "is_seckill";
	private final String MONGO_KEY_CREATE_TIME = "create_time";
	
	private final String COMMAND_SET = "$set";
	
	@Override
	public void run() {
		LogCvt.debug("定时任务: 返回库存------开始------");
		try {
			// 查询 缓存redis 中cbbank:time_order(**时间内订单)订单信息
			Set<String> orderInfoList = timeOrderRedisService.getOrderInfo();
			if (CollectionUtils.isEmpty(orderInfoList)) {
				LogCvt.debug("定时任务: 返回库存------完成(缓存cbbank:time_order无订单信息)------");
				return;
			}
			
			LogCvt.debug("定时任务: 返回库存------缓存cbbank:time_order订单信息 "+orderInfoList.size()+" 条------");
			
			OrderService.Iface orderServiceClient = 
					(OrderService.Iface)ThriftClientProxyFactory.createIface(OrderService.class.getName(), OrderService.class.getSimpleName(), ThriftConfig.ORDER_SERVICE_HOST, ThriftConfig.ORDER_SERVICE_PORT);
			
			// 遍历 cbbank:time_order(**时间内订单)订单信息
			for (String orderInfo : orderInfoList) {
				
				LogCvt.debug("缓存 cbbank:time_order 内订单 [clientId:orderId=" + orderInfo+"] --------处理开始");
				
				// ********************************************
				// * 用 mongo 的 findAndModify 进行原子操作 *
				// * 查询出来 - 参数如下: *
				// * 1 _id=order_id ; 2 client_id ; 
				// * 3 state=正常 ; 4 order_status=创建 ;
				// * 5 is_seckill=0(非秒杀);6 create_time<当前时间-30分钟
				// * 修改掉状态 state=库存已退 *
				// * *
				// * 再调用接口退库存 *
				// * *
				// ********************************************
				String[] tValue = orderInfo.split(":");
				String clientId = tValue[0];
				String orderId = tValue[1];
				// 得到 set DBObject
				DBObject setObj = changeDBObjectOfSet();
				// 得到 where DBObject
				DBObject wheObj = changeDBObjectOfWhere(orderId, clientId);
				
				OrderMongo order = orderMongoService.findOrderMongo(wheObj);
				// 如果能查询到, 先退库存再更新订单状态
				if(order != null){
					
					LogCvt.debug("mongo 中订单 [clientId:orderId=" + orderInfo+"] --------满足查询条件");
					
					if( 1 != order.getIsQrcode() && 1 != order.getIsVipOrder() ){ // 不是面对面 && 不是vip订单 - 退库存操作
						
						StoreVoReq reqVo = new StoreVoReq();
						reqVo.setClientId(clientId);
						reqVo.setOrderId(orderId);
						reqVo.setOperationType("1"); //1 加库存（还库存）  0 减库存（扣库存）
						LogCvt.debug("cb_order订单[clientId="+clientId+"]|[orderId="+orderId+"] 开始进行退库存操作");
						// 调用接口退库存
						StoreVoRes resVo = orderServiceClient.storeProcess(reqVo);
						ResultVo resultVo = resVo.getResultVo();
						LogCvt.debug("cb_order订单[clientId="+clientId+"]|[orderId="+orderId+"]处理结果: 返回码[resultCode="+resultVo.getResultCode()+"]|返回信息[resultDesc="+resultVo.getResultDesc()+"]");
						
						if(StringUtils.equals(ResultCode.success.getCode(), resultVo.getResultCode())){
							// 原子操作(查询并修改)
							Object result = orderMongoService.updateOrder(setObj, wheObj, COMMAND_SET);
							if( result != null ){ // 修改成功
								LogCvt.debug("cb_order订单[orderId="+orderId+"]------设置退库存状态成功------");
							}else{
								LogCvt.error("cb_order订单[orderId="+orderId+"]------设置退库存状态失败------");
							}
	//						// TODO 退库存处理成功 - 发送监控
	//						monitorService.send(MonitorPointEnum.Timertask_Return_Store_Success);
						}else{
							// TODO 退库存处理不成功 - 发送监控
							monitorService.send(MonitorPointEnum.Timertask_Return_Store_Failure);
						}
					}else{
						// 原子操作(查询并修改)
						Object result = orderMongoService.updateOrder(setObj, wheObj, COMMAND_SET);
						if( result != null ){ // 修改成功
							LogCvt.debug("cb_order订单[orderId="+orderId+"]------设置退库存状态成功------");
						}else{
							LogCvt.error("cb_order订单[orderId="+orderId+"]------设置退库存状态失败------");
						}
					}
				}else{
					LogCvt.debug("缓存 cbbank:time_order 内订单 [clientId:orderId=" + orderInfo+"] --------不满足查询条件");
				}
				
				LogCvt.debug("缓存 cbbank:time_order 内订单 [clientId:orderId=" + orderInfo+"] --------处理结束");
			}
		} catch (Exception e) {
			LogCvt.error("定时任务: 返回库存------系统异常------");
			LogCvt.error(e.getMessage(), e);
			// TODO 退库存处理不成功 - 发送监控
			monitorService.send(MonitorPointEnum.Timertask_Return_Store_Failure);
		} finally {
			LogCvt.debug("定时任务: 返回库存------结束------");
		}
	}
	
	
	// 转换得到 DBObject 为 sqlScript 的 set 脚本
	private DBObject changeDBObjectOfSet(){
		DBObject dbObj = new BasicDBObject();
		// 状态 set 为 库存已退
		dbObj.put(MONGO_KEY_STATE, OrderState.RETURNED.getCode());
		return dbObj;
	}
	
	// 转换得到 DBObject 为 sqlScript 的 where 脚本
	private DBObject changeDBObjectOfWhere(String orderId, String clientId){
		DBObject dbObj = new BasicDBObject();
		dbObj.put(MONGO_KEY__ID, orderId);
		dbObj.put(MONGO_KEY_CLIENT_ID, clientId);
		dbObj.put(MONGO_KEY_STATE, OrderState.NORMAL.getCode()); // 正常
		dbObj.put(MONGO_KEY_ORDER_STATUS, OrderStatus.create.getCode()); // 创建
		dbObj.put(MONGO_KEY_IS_SECKILL, 0);
		dbObj.put(MONGO_KEY_CREATE_TIME, new BasicDBObject(QueryOperators.LT, DateUtils.addMinutes(new Date(), -AllkindsTimeConfig.getReturnStoreAnMinuteBefore()).getTime())); // 配置的分钟之前
		return dbObj;
	}
	
}
