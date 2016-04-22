package com.froad.timer.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.WayBillCommonMapper;
import com.froad.enums.ResultCode;
import com.froad.enums.ShippingStatus;
import com.froad.enums.WayBillStatus;
import com.froad.logback.LogCvt;
import com.froad.po.Waybill;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.OrderService;
import com.froad.util.AllkindsTimeConfig;
import com.froad.util.MongoTableName;
import com.froad.util.ThriftConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.froad.thrift.vo.order.UpdateSubOrderLogisticVoReq;
import com.froad.thrift.vo.order.UpdateSubOrderLogisticVoRes;
/**
 * 精品商城自动收货
 * @ClassName: TaskConfirmWayBill 
 * @Description: TODO
 * @author: Yaolong Liang
 * @date: 2015年12月2日
 */
public class TaskConfirmWayBill implements Runnable {
	private int COMFIRM=2;// 0-未收货  1-人工确认 2-系统确认
	//确认收货标志
	private String COMFIRMWAYBILL="3";
//	private String[] wayBillStatus=
//		{WayBillStatus.in_transit.getStatus(),WayBillStatus.pick_up.getStatus(),WayBillStatus.exception.getStatus()
//			,WayBillStatus.sign_in.getStatus(),WayBillStatus.sign_out.getStatus(),
//			WayBillStatus.delivery.getStatus(),WayBillStatus.send_back.getStatus()};
	
	
	 OrderService.Iface orderClient=(OrderService.Iface)
			 ThriftClientProxyFactory.createIface(OrderService.class, ThriftConfig.ORDER_SERVICE_HOST, ThriftConfig.ORDER_SERVICE_PORT);
	 SqlSession sqlsession=null;
	@Override
	public void run() {
		LogCvt.info("定时任务:精品商城--确认收货------开始------");	
		sqlsession=MyBatisManager.getSqlSessionFactory().openSession();
		WayBillCommonMapper deliveryWayBillMapper=null;
		deliveryWayBillMapper = sqlsession.getMapper(WayBillCommonMapper.class);
		String[] wayBillStatus=getConfirmWayBillStatus();
		try{
			//时间获取，灵活配置
			Date date=getDateBefore();
			//找出符合条件的运单,7天后自动确认收货
			List<Waybill> deliveryWayBills=deliveryWayBillMapper.findDeliveryWayBillList(date,wayBillStatus);
			//满足条件的运单修改运单状态以及自动确认收货
			if(deliveryWayBills!=null&&deliveryWayBills.size()>0){
				LogCvt.debug("定时任务:精品商城--确认收货-----符合条件的运单有："+deliveryWayBills.size()+"-------");	
				for(Waybill deliveryWayBill:deliveryWayBills){
					LogCvt.debug("定时任务:精品商城--确认收货-----符合条件的子订单号："+deliveryWayBill.getSubOrderId()+"-------");	
					//订单状态修改
					comfirmWayBill(deliveryWayBill,deliveryWayBillMapper);
				}
			}else{
				LogCvt.debug("定时任务:精品商城--确认收货------没有符合条件的运单------");	
			}
		}catch(Exception e){
			LogCvt.error("定时任务:精品商城--确认收货异常,原因为"+e.getMessage(),e);
		}finally{
			if(sqlsession!=null)
				sqlsession.close();
			LogCvt.info("定时任务:精品商城--确认收货------结束------");	
		}
	}
  public void comfirmWayBill(Waybill deliveryWayBill,WayBillCommonMapper deliveryWayBillMapper){
	  //调用order接口，成功后修改表状态
	  UpdateSubOrderLogisticVoReq req=new UpdateSubOrderLogisticVoReq();
	  String subOrderId=deliveryWayBill.getSubOrderId();
	  req.setSubOrderId(subOrderId);
	  req.setClientId(getClientId(subOrderId));
	  req.setDeliveryState(ShippingStatus.receipt.getCode());
	  
	  try {
		  UpdateSubOrderLogisticVoRes res=orderClient.updateSubOrderLogistic(req);
	 if(res.getResultVo().getResultCode().equals(ResultCode.success.getCode())){
		 LogCvt.debug("定时任务:精品商城--调用Order模块成功,返回信息desc:"+res.getResultVo().getResultDesc()+",code:"+res.getResultVo().getResultCode());
		 Waybill deliveryWayBill1 =new Waybill();
		 deliveryWayBill1.setSubOrderId(deliveryWayBill.getSubOrderId());
		 deliveryWayBill1.setShippingState(COMFIRM);
		// deliveryWayBill1.setStatus(WayBillStatus.sign_in.getStatus());
		 deliveryWayBill1.setArriveTime(new Date());
		 deliveryWayBillMapper.updateWayBill(deliveryWayBill1);
		 sqlsession.commit();
	 }else{
		 LogCvt.debug("定时任务:精品商城--调用Order模块失败,返回信息desc:"+res.getResultVo().getResultDesc()+",code:"+res.getResultVo().getResultCode());
	 }
	} catch (Exception e) {
		LogCvt.error("定时任务:精品商城--调用Order模块异常,原因为"+e.getMessage(),e);
	}
	  
  }
  public Date getDateBefore(){
	  Calendar calendar = Calendar.getInstance();
	  calendar.setTime(new Date());
	  calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-AllkindsTimeConfig.getConfirmWayBillAnDayBefore()); 
	  return calendar.getTime();
  }
  public  String getClientId(String subOrderId){
	  MongoManager mongo=new MongoManager();
	  DBObject dbObj = new BasicDBObject();
		dbObj.put("sub_order_id", subOrderId);
		SubOrderMongo suborder = (SubOrderMongo)mongo.findOne(dbObj, MongoTableName.CB_SUBORDER_PRODUCT, SubOrderMongo.class);
		if(suborder!=null){
		return suborder.getClientId();	
		}else{
			LogCvt.error("定时任务:精品商城-----查询此订单号失败,sub_order_id:"+subOrderId);	
		}
	  return "";
  }
  public String[] getConfirmWayBillStatus(){
	  return AllkindsTimeConfig.getConfirmWayBillStatus();
  }
}
