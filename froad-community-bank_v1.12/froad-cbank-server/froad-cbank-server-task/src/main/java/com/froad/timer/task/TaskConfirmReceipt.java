package com.froad.timer.task;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.froad.db.mongo.ShippingMongoService;
import com.froad.db.mongo.SubOrderMongoService;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.ResultCode;
import com.froad.enums.ShippingStatus;
import com.froad.enums.SubOrderType;
import com.froad.logback.LogCvt;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.mongo.ShippingOrderMongo;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.SettlementService;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.AllkindsTimeConfig;
import com.froad.util.ServiceFactory;
import com.froad.util.ThriftConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * 
 * 确认收货 - 定时任务
 * 
 * @author lf 2015.03.18
 * @modify lf 2015.03.18
 * 
 * */
public class TaskConfirmReceipt implements Runnable {
	
	private ShippingMongoService shippingMongoService = ServiceFactory.getShippingMongoService();
	private SubOrderMongoService subOrderMongoService = ServiceFactory.getSubOrderMongoService();
	/**
	 * 监控服务
	 * */
	private MonitorService monitorService = new MonitorManager();
	private final String SYS_TIMER = "sys_timer";
	private final String MONGO_KEY_SHIPPING_STATUS = "shipping_status";
	private final String MONGO_KEY_SHIPPING_TIME = "shipping_time";
	private final String MONGO_KEY_RECEIPT_TIME = "receipt_time";
	private final String MONGO_KEY_REMARK = "remark";
	private final String MONGO_KEY__ID = "_id";
	
	private final String COMMAND_SET = "$set";
	
	/***********************Mongo KEY 常量************************/
	private final static  String  ORDER_ID="order_id";
	private final static  String SUB_ORDER_ID="sub_order_id";
	private final static  String TYPE="type";
	private final static  String DELIVERY_STATUS="delivery_state";
	/***********************Mongo KEY 常量************************/
	
	/**
	 * 
	 * cb_shipping表中收货时间到达后修改状态为完成
	 * 
	 * @author lf 2015.03.18
     * @modify lf 2015.03.18
	 * 
	 * */
	@Override
	public void run() {
		
		LogCvt.debug("定时任务:确认收货------开始------");
		
		try{
			// 查询条件
			DBObject queryObj = new BasicDBObject();
			queryObj.put(MONGO_KEY_SHIPPING_STATUS, ShippingStatus.shipped.getCode()); // 已发货
			queryObj.put(MONGO_KEY_SHIPPING_TIME, new BasicDBObject(QueryOperators.LT, DateUtils.addDays(new Date(), -AllkindsTimeConfig.getConfirmReceiptAnDayBefore()).getTime()));  // 配置的天之前
			
			// 查询 7 天前已经发货的信息
			List<ShippingOrderMongo> shippingList = shippingMongoService.findByCondition(queryObj);

			if( shippingList == null || shippingList.size() <= 0 ){
				LogCvt.debug("无收货时间应该已达到的物流信息");
				return ;
			}
			
			LogCvt.debug("收货时间应该已达到的物流信息 " + shippingList.size() + " 条");
			SettlementService.Iface settlementService = 
					(SettlementService.Iface)ThriftClientProxyFactory.createIface(SettlementService.class.getName(), SettlementService.class.getSimpleName(), ThriftConfig.ORDER_SERVICE_HOST, ThriftConfig.ORDER_SERVICE_PORT);
			
			// 循环查询结果
			for( ShippingOrderMongo shipping : shippingList ){
				
				String _id = shipping.getId();
				String orderId = _id.split("_")[0];
				String subOrderId = _id.split("_")[1];
				
				LogCvt.debug("物流信息 " + _id + " 处理 --- 开始");
				
				DBObject valueObj = new BasicDBObject();
				valueObj.put(MONGO_KEY_SHIPPING_STATUS, ShippingStatus.receipt.getCode()); // 状态 - 已收货
				valueObj.put(MONGO_KEY_RECEIPT_TIME, new Date().getTime()); // 收货时间
				valueObj.put(MONGO_KEY_REMARK, SYS_TIMER); // 备注 - 系统定时
				DBObject whereObj = new BasicDBObject();
				whereObj.put(MONGO_KEY__ID, _id);
				// 修改
				boolean shippingFlag = shippingMongoService.modifyShipping(valueObj, whereObj, COMMAND_SET);
				LogCvt.debug("物流信息cb_shipping:[orderId="+orderId+"]|[subOrderId="+subOrderId+"]确认收货"+(shippingFlag?"成功":"失败"));
				if(!shippingFlag){
					return;
				}
				// 名优特惠子订单更新收货状态
				DBObject specialObj = new BasicDBObject();
				specialObj.put(ORDER_ID, orderId);
				specialObj.put(SUB_ORDER_ID, subOrderId);
				specialObj.put(TYPE, SubOrderType.special_merchant.getCode()); // 名优特惠
				DBObject setspecialObj = new BasicDBObject();
				setspecialObj.put(DELIVERY_STATUS, ShippingStatus.receipt.getCode());
				boolean flag = subOrderMongoService.modifySubOrder(setspecialObj, specialObj, COMMAND_SET);
				LogCvt.debug("名优特惠子订单信息cb_suborder_product:[orderId="+orderId+"]|[subOrderId="+subOrderId+"]确认收货"+(flag?"成功":"失败"));
				if(flag){
					// 名优特惠结算
					LogCvt.debug("名优特惠子订单[suborderId="+subOrderId+"]开始进行结算处理");
					ResultVo result = settlementService.specialSettle(subOrderId, 2);
					LogCvt.debug("名优特惠子订单[subOrderId="+subOrderId+"]结算处理结果: 返回码[resultCode="+result.getResultCode()+"]|返回信息[resultDesc="+result.getResultDesc()+"]");
					if( !ResultCode.success.getCode().equals(result.getResultCode()) ){
						// TODO 名优特惠确认结算处理不成功 - 发送监控
						monitorService.send(MonitorPointEnum.Timertask_Confirm_Receipt_Failure);
					}
				}
				
				LogCvt.debug("物流信息 " + _id + " 处理 --- 结束");
			}
			
		} catch(Exception e){
			LogCvt.error("定时任务:确认收货------系统异常------");
			LogCvt.error(e.getMessage(), e);
			// TODO 名优特惠确认结算处理不成功 - 发送监控
			monitorService.send(MonitorPointEnum.Timertask_Confirm_Receipt_Failure);
		} finally {
			LogCvt.debug("定时任务:确认收货------结束------");
		}
		
	}
	
}
