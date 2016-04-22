package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.froad.db.redis.RedisCommon;
import com.froad.db.redis.RegisterRedis;
import com.froad.enums.ActiveIdCode;
import com.froad.enums.ActiveType;
import com.froad.enums.ResultCode;
import com.froad.handler.ActiveOrderDetailHandler;
import com.froad.handler.ActiveOrderHandler;
import com.froad.handler.VouchersInfoHandler;
import com.froad.handler.impl.ActiveOrderDetailHandlerImpl;
import com.froad.handler.impl.ActiveOrderHandlerImpl;
import com.froad.handler.impl.VouchersInfoHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveCloseOrderLogic;
import com.froad.po.ActiveOrder;
import com.froad.po.ActiveOrderDetail;
import com.froad.po.CloseMarketOrderReq;
import com.froad.po.Result;
import com.froad.util.ActiveUtils;

/**
  * @ClassName: ActiveCloseOrderLogicImpl
  * @Description:  关闭订单（0-系统关单/取消订单 1-全部退货完毕）
  * @author froad-zengfanting 2015年11月9日
  * @modify froad-zengfanting 2015年11月9日
 */
public class ActiveCloseOrderLogicImpl implements ActiveCloseOrderLogic {
	ActiveOrderHandler activeOrderHandler=new ActiveOrderHandlerImpl();
	ActiveOrderDetailHandler activeOrderDetailHandler=new ActiveOrderDetailHandlerImpl();
	private static VouchersInfoHandler vouchersInfoHandler = new VouchersInfoHandlerImpl();
	
	//系统关闭订单，取消订单
	@Override
	public Result closeOrderBySystem(CloseMarketOrderReq closeMarketOrderReq) {
		
		try{
			
			String clientId = closeMarketOrderReq.getClientId();
			String orderId = closeMarketOrderReq.getOrderId();
			Long  memberCode = closeMarketOrderReq.getMemberCode();
			
			Date updateTime = new Date(); //更新数据状态，时间
			ActiveOrder activeOrder = new ActiveOrder();
			activeOrder.setOrderId(orderId);
			activeOrder.setUpdateTime(updateTime);
			activeOrder.setStatus(false);
			
			if(activeOrderHandler.updateActiveOrderByOrderId(activeOrder)<=0){
				LogCvt.info("--关闭订单（系统关闭订单，取消订单）--促销订单关单,系统关单或取消订单更新数据失败");
				return new Result(ResultCode.failed, "促销订单关单,系统关单或取消订单更新数据失败");
			}
			
			ActiveOrderDetail activeOrderDetail=new ActiveOrderDetail();
			activeOrderDetail.setOrderId(orderId);
			activeOrderDetail.setClientId(clientId);
			List<ActiveOrderDetail> list=activeOrderDetailHandler.findActiveOrderDetail(activeOrderDetail);
			if(list==null||list.size()==0){
				LogCvt.info("--关闭订单（系统关闭订单，取消订单）--获取订单促销订单明细失败");
				return new Result(ResultCode.failed, "获取订单促销订单明细失败");
			}
			
			List<String> temList=new ArrayList<String>();
			for (ActiveOrderDetail activeOrderDetail2 : list) {
				String activeId=activeOrderDetail2.getActiveId();
				String type = activeOrderDetail2.getType();
				if(activeId==null){
					continue;
				}else{
					if(temList.contains(activeId)){
						continue;	
					}else{
						temList.add(activeId);
					}
				}
				
				int result=-1;
				// 回退个人资格 - 根据不同类型回退
				if( ActiveType.fullCut.getCode().equals(type) ){
					result=RedisCommon.rollbackPersonLimit(clientId,activeId, memberCode);
					if(result!=0){
						LogCvt.info("----关闭订单（系统关闭订单，取消订单））-个人资格回退失败--code--"
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultCode()
						+" msg--" 
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultDesc());
					}
					//回退当日资格
					result=RedisCommon.rollbackGlobalLimit(clientId, activeId, orderId, 1);
					if(result!=0){
						LogCvt.info("----关闭订单（系统关闭订单，取消订单）-当日资格回退失败--code--"
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultCode()
						+" msg--" 
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultDesc());
					}
					//回退全局资格
					result=RedisCommon.rollbackBaseGlobalLimit(clientId, activeId);
					if(result!=0){
						LogCvt.info("----关闭订单（系统关闭订单，取消订单）-全局资格回退失败--code--"
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultCode()
						+" msg--" 
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultDesc());
					}
//				}else if( ActiveType.vouchers.getCode().equals(type) ){
//					result=VouchersRedis.rollbackPersonLimit(clientId,activeId, memberCode);
//					if(result!=0){
//						LogCvt.info("----关闭订单（系统关闭订单，取消订单））-个人资格回退失败--code--"
//						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultCode()
//						+" msg--" 
//						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultDesc());
//					}
				}else if( ActiveType.registerGive.getCode().equals(type) ){//首单满减
					//回退整体资格限制
					result= RegisterRedis.rollbackGlobalLimit(clientId, activeId, orderId, 1);
					if(result!=0){
						LogCvt.info("----关闭订单（系统关闭订单，取消订单）-当日资格回退失败--code--"
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultCode()
						+" msg--" 
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultDesc());
					}
					//回退全局资格
					result=RegisterRedis.rollbackBaseGlobalLimit(clientId, activeId);
					if(result!=0){
						LogCvt.info("----关闭订单（系统关闭订单，取消订单）-全局资格回退失败--code--"
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultCode()
						+" msg--" 
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultDesc());
					}
				}
			}
			
			// 券状态修改
			List<String> vouchersIdList = new ArrayList<String>();
			for ( ActiveOrderDetail activeOrderDetail2 : list ) {
				if( ActiveType.vouchers.getCode().equals(activeOrderDetail2.getType()) ){
					vouchersIdList.add(activeOrderDetail2.getTicketId());
				}
			}
			// 如果有红包 - 则回退资格,更新红包状态为初始化
			// TODO 如果更新失败则要看日志手工更新 - 后面加上监控
			try {
				if( vouchersIdList != null && vouchersIdList.size() > 0 ){
					// 回退红包资格
					Result result = ActiveCreateOrderGoBackLogicImpl.goBackVouchers(vouchersIdList, clientId, memberCode, orderId, 1);
					LogCvt.debug("回退红包资格 结果:"+JSON.toJSONString(result));
					Boolean updateResult = vouchersInfoHandler.updateVouchersInfoInitBatch(vouchersIdList);
					LogCvt.debug("更新券状态:"+updateResult);
				}
			} catch (Exception e) {
				LogCvt.debug("更新券状态 异常");
			}
			
			return new Result(ResultCode.success);
		}catch(Exception ex){
			LogCvt.error("订单关单：系统关单或取消订单 异常", ex);
			return new Result(ResultCode.failed, "订单关单：系统关单或取消订单失败");
		}
	}
	
	//客户全部退货
	@Override
	public Result closeOrderByReturn(CloseMarketOrderReq closeMarketOrderReq) {
		
		try{
			
			String clientId = closeMarketOrderReq.getClientId();
			String orderId = closeMarketOrderReq.getOrderId();
			Date updateTime = new Date(); //更新数据状态，时间
			ActiveOrder activeOrder =new ActiveOrder();
			activeOrder.setOrderId(orderId);
			activeOrder.setUpdateTime(updateTime);
			activeOrder.setStatus(false);
			if(activeOrderHandler.updateActiveOrderByOrderId(activeOrder)<=0){
				LogCvt.info("--关闭订单（客户退货完成）--促销订单关单,退货更新数据失败");
				return new Result(ResultCode.failed, "促销订单关单,退货更新数据失败");
			}
			
			ActiveOrderDetail activeOrderDetail=new ActiveOrderDetail();
			activeOrderDetail.setOrderId(orderId);
			activeOrderDetail.setClientId(clientId);
			List<ActiveOrderDetail> list=activeOrderDetailHandler.findActiveOrderDetail(activeOrderDetail);
			if(list==null||list.size()<1){
				LogCvt.info("--关闭订单（客户退货完成）--获取订单促销订单明细失败");
				return new Result(ResultCode.failed, "获取订单促销订单明细失败");
			}
			
			
			List<String> temList=new ArrayList<String>();
			for (ActiveOrderDetail activeOrderDetail2 : list) {
				String activeId=activeOrderDetail2.getActiveId();
				String type = activeOrderDetail2.getType();
				if(activeId==null){
					continue;
				}else{
					if(temList.contains(activeId)){
						continue;	
					}else{
						temList.add(activeId);
					}
				}
				
				if( ActiveType.registerGive.getCode().equals(type) ) {//首单满减
					int backResult=RegisterRedis.rollbackGlobalLimit(clientId, activeId, null,1);
					if(backResult!=0){
						LogCvt.info("----订单创建失败交易-当日资格回退失败--code--"
						+ActiveUtils.getEnumConstant(Integer.toString(backResult)).getResultCode()
						+" msg--" 
						+ActiveUtils.getEnumConstant(Integer.toString(backResult)).getResultDesc());
					}
					
				} else if ( ActiveType.fullCut.getCode().equals(type) ) {//普通满减
					//回退当日资格
					int result=RedisCommon.rollbackGlobalLimit(clientId, activeId, orderId, 1);
					if(result!=0){
						LogCvt.info("----关闭订单（客户退货完成）-当日资格回退失败--code--"
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultCode()
						+" msg--" 
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultDesc());
					}
					//回退全局资格
					result=RedisCommon.rollbackBaseGlobalLimit(clientId, activeId);
					if(result!=0){
						LogCvt.info("----关闭订单（客户退货完成）-当日资格回退失败--code--"
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultCode()
						+" msg--" 
						+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultDesc());
					}
				}
			
			}
			
			return new Result(ResultCode.success);
			
		}catch(Exception ex){
			LogCvt.error("订单关单：全部退货关单 异常", ex);
			return new Result(ResultCode.failed, "订单关单：全部退货关单 异常");
			
		}
	}

}
