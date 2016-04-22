package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.db.mysql.mapper.AdvertisingMapper;
import com.froad.db.mysql.mapper.WayBillCommonMapper;
import com.froad.db.redis.SupportsRedis;
import com.froad.enums.ResultCode;
import com.froad.enums.WayBillStatus;
import com.froad.logback.LogCvt;
import com.froad.logic.DeliveryWayBillLogic;
import com.froad.po.Advertising;
import com.froad.po.Waybill;
import com.froad.po.Result;

public class DeliveryWayBillLogicImpl implements DeliveryWayBillLogic{
   
	private String STATUS="3";
	private String DIVD="##";
	private String DIVD1="!";
	
	@Override
	public Waybill getDeliveryWayBill(Waybill deliveryWayBill) {
		//根据条件查找订单
		SqlSession sqlSession = null;
		WayBillCommonMapper deliveryWayBillMapper = null;
		try{
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			deliveryWayBillMapper = sqlSession.getMapper(WayBillCommonMapper.class);
		    deliveryWayBill=deliveryWayBillMapper.findOneDeliveryWayBill(deliveryWayBill);
		    if(deliveryWayBill!=null){
		    deliveryWayBill.setStatus(WayBillStatus.getByStatus(deliveryWayBill.getStatus()).getDescribe());
		    }
		} catch (Exception e) { 
			LogCvt.error("查询物流运单失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return deliveryWayBill;
	}
	@Override
	public Result updateDeliveryWayBill(Waybill deliveryWayBill) {
		SqlSession sqlSession = null;
		WayBillCommonMapper deliveryWayBillMapper = null;
		Result result=new Result();
		try{
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			deliveryWayBillMapper = sqlSession.getMapper(WayBillCommonMapper.class);
			deliveryWayBill.setUpdateTime(new Date());
			//先查询是否存在此运单，存在则更新，不存在则插入
			Waybill Oldbill=deliveryWayBillMapper.findOneDeliveryWayBill(deliveryWayBill);
				StringBuffer messages=new StringBuffer();
			if(Oldbill.getMessage()!=null&&Oldbill.getMessage().contains(DIVD1)){
				String[] message=Oldbill.getMessage().split(DIVD);
				messages.append(deliveryWayBill.getMessage()).append(message[message.length-1].toString());
//				message.append(deliveryWayBill.getMessage()).append("##").append(Oldbill.getMessage());
				deliveryWayBill.setMessage(messages.toString());
			}
				LogCvt.debug("运单号为："+deliveryWayBill.getShippingId()+",进行修改");
				if(deliveryWayBillMapper.updateWayBillByCondition(deliveryWayBill)){
					result.setResultDesc("物流信息修改成功");
				}else{
					result.setResultDesc("更新物流运单失败");
					result.setResultCode(ResultCode.failed.getCode());
				}
				
//			}else{
//				LogCvt.debug("物流记录不存在,添加新记录");	
//				deliveryWayBillMapper.addDeliveryWayBill(deliveryWayBill);
//			}
			sqlSession.commit();
		} catch (Exception e) { 
			sqlSession.rollback(true);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("更新物流运单失败");
			LogCvt.error("更新物流运单失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result;
	
	}
	@Override
	public Result updateDeliveryWayBillByCondition(Waybill deliveryWayBill) {
		SqlSession sqlSession = null;
		WayBillCommonMapper deliveryWayBillMapper = null;
		Result result=new Result();
		deliveryWayBill.setStatus(WayBillStatus.sign_in.getStatus());//暂时后台修改确认收货状态
		deliveryWayBill.setUpdateTime(new Date());
		deliveryWayBill.setArriveTime(new Date());
		try{
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			deliveryWayBillMapper = sqlSession.getMapper(WayBillCommonMapper.class);
				LogCvt.debug("更新物流记录,子订单ID为："+deliveryWayBill.getSubOrderId());
				if(deliveryWayBillMapper.updateWayBill(deliveryWayBill)){
					sqlSession.commit();
					result.setResultDesc("物流信息修改成功");
				}else{
					result.setResultDesc("更新物流运单失败");
					result.setResultCode(ResultCode.failed.getCode());	
				}
		} catch (Exception e) { 
			sqlSession.rollback(true);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("更新物流运单失败");
			LogCvt.error("更新物流运单失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result;
	}
	
    
}
