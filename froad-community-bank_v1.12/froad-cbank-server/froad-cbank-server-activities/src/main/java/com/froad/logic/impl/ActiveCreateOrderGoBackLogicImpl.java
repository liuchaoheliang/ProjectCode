	package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.froad.db.redis.RedisCommon;
import com.froad.db.redis.RegisterRedis;
import com.froad.db.redis.VouchersRedis;
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
import com.froad.logic.ActiveCreateOrderGoBackLogic;
import com.froad.po.ActiveOrder;
import com.froad.po.ActiveOrderDetail;
import com.froad.po.FailureGoBackReq;
import com.froad.po.Result;
import com.froad.po.VouchersInfo;
import com.froad.util.ActiveUtils;

public class ActiveCreateOrderGoBackLogicImpl implements ActiveCreateOrderGoBackLogic {

	private static VouchersInfoHandler vouchersInfoHandler = new VouchersInfoHandlerImpl();
	private static ActiveOrderHandler activeOrderHandler = new ActiveOrderHandlerImpl();
	private static ActiveOrderDetailHandler activeOrderDetailHandler = new ActiveOrderDetailHandlerImpl();
	
	/**
	  * @ClassName: ActiveCreateOrderGoBackLogic
	  * @Description: 订单创建失败，无条件回退资格
	  * @author froad-zengfanting 2015年11月10日
	  * @modify froad-lf 2015年12月03日
	 */
	@Override
	public Result createOrderfailGoBack(FailureGoBackReq failureGoBackReq) {

		try{
			
			String clientId = failureGoBackReq.getClientId();
			Long memberCode = failureGoBackReq.getMemberCode();
			List<String> activeIdList = failureGoBackReq.getActiveIdList();
			List<String> vouchersIdList = failureGoBackReq.getVouchersIdList();
			
			// 如果需要回退的内容都为空 - 没必要调用此方法
			if( ( activeIdList == null || activeIdList.size() < 1 ) 
				&& 
				( vouchersIdList == null || vouchersIdList.size() < 1 ) ){
				
				LogCvt.debug("如果需要回退的内容都为空 - 没必要调用此方法");
				return new Result(ResultCode.failed, "如果需要回退的内容都为空");
			}
			
			if( activeIdList != null && activeIdList.size() > 1 ){
				// 回退满减资格
				Result result = goBackCutActive(activeIdList, clientId, memberCode);
				LogCvt.debug("回退满减资格 结果:"+JSON.toJSONString(result));
			}
			
			if( vouchersIdList != null && vouchersIdList.size() > 1 ){
				// 回退红包资格
				Result result = goBackVouchers(vouchersIdList, clientId, memberCode, null, 0);
				LogCvt.debug("回退红包资格 结果:"+JSON.toJSONString(result));
				
				//  - 如果更新失败则要看日志手工更新
				// TODO 后面加上监控
				Boolean updateResult = vouchersInfoHandler.updateVouchersInfoInitBatch(vouchersIdList);
				LogCvt.debug("更新券状态:"+updateResult);
			}
			
			LogCvt.debug("回退资格 运行正常");
			return new Result(ResultCode.success);
			
		}catch(Exception ex){
			LogCvt.error("订单创建失败回退资格 系统异常", ex);
			return new Result(ResultCode.failed, "订单创建失败回退资格 系统异常");
		}
	}
	
	/**
	 * 回退代金券/红包资格
	 * */
	public static Result goBackVouchers(List<String> vouchersIdList, String clientId, Long memberCode, String orderId, int type){
		try{
			
			for( String vouchersId : vouchersIdList ){
				
				// 获取代金券/红包活动id
				String activeId = "";
				VouchersInfo vouchersInfo = new VouchersInfo();
				vouchersInfo.setVouchersId(vouchersId);
				vouchersInfo = vouchersInfoHandler.findOneVouchersInfo(vouchersInfo);
				if( vouchersInfo == null || vouchersInfo.getActiveId() == null ){
					LogCvt.debug("回退代金券/红包资格 券码所属活动id查询不到");
					return new Result(ResultCode.failed.getCode(), "回退代金券/红包资格 券码所属活动id查询不到");
				}else{
					activeId = vouchersInfo.getActiveId();
				}
				
				// 回退个人资格
				int backResult = VouchersRedis.rollbackPersonLimit(clientId, activeId, memberCode, orderId, type);
				if(backResult!=0){
					LogCvt.info("个人资格回退 失败--code--"
					+ActiveUtils.getEnumConstant(Integer.toString(backResult)).getResultCode()
					+" msg--" 
					+ActiveUtils.getEnumConstant(Integer.toString(backResult)).getResultDesc());
				}
				
				// 回退时间段资格 无条件回退
				backResult = VouchersRedis.rollbackGlobalLimit(clientId, activeId, orderId, type);
				if(backResult!=0){
					LogCvt.info("时间段资格回退 失败--code--"
					+ActiveUtils.getEnumConstant(Integer.toString(backResult)).getResultCode()
					+" msg--" 
					+ActiveUtils.getEnumConstant(Integer.toString(backResult)).getResultDesc());
				}
				
				// 回退全局资格
			}
			
			return new Result(ResultCode.success);
		
		}catch(Exception ex){
			LogCvt.error("回退代金券/红包资格 异常", ex);
			return new Result(ResultCode.failed, "回退代金券/红包资格 系统异常");
		}
	}
	
	/**
	 * 回退满减资格
	 * */
	public static Result goBackCutActive(List<String> activeIdList, String clientId, Long memberCode){
		try{
			List<String> tmpList=new ArrayList<String>();
			for (String activeId : activeIdList) {
				if(activeId==null){//去重复数据
					continue;
				}else{
					if(tmpList.contains(activeId)){
						continue;	
					}else{
						tmpList.add(activeId);
					}
				}
				Map<String,String> activeMap = null;
				if(activeId.indexOf(ActiveIdCode.ZC.getCode()) > 0) {
					activeMap = RegisterRedis.readRegisterActive(clientId, activeId);
				}else if (activeId.indexOf(ActiveIdCode.MJ.getCode()) > 0) {
					activeMap = RedisCommon.readFullCutActive(clientId, activeId);
				}	
				
				if(null == activeMap) {
					return new Result(ResultCode.failed, "找不到该活动");
				}
				String type = activeMap.get("type");
				if(ActiveType.firstPayment.getCode().equals(type) ) {//首单
					//回退当日资格 无条件回退
					int backResult=RegisterRedis.rollbackGlobalLimit(clientId, activeId, null,0);
					if(backResult!=0){
						LogCvt.info("----订单创建失败交易-当日资格回退失败--code--"
						+ActiveUtils.getEnumConstant(Integer.toString(backResult)).getResultCode()
						+" msg--" 
						+ActiveUtils.getEnumConstant(Integer.toString(backResult)).getResultDesc());
					}
				} else if(ActiveType.fullCut.getCode().equals(type)) {//满减
					//回退个人资格
					int backResult=RedisCommon.rollbackPersonLimit(clientId,activeId, memberCode);
					if(backResult!=0){
						LogCvt.info("----订单创建失败交易-个人资格回退失败--code--"
						+ActiveUtils.getEnumConstant(Integer.toString(backResult)).getResultCode()
						+" msg--" 
						+ActiveUtils.getEnumConstant(Integer.toString(backResult)).getResultDesc());
					}
					
					//回退当日资格 无条件回退
					backResult=RedisCommon.rollbackGlobalLimit(clientId, activeId, null,0);
					if(backResult!=0){
						LogCvt.info("----订单创建失败交易-当日资格回退失败--code--"
						+ActiveUtils.getEnumConstant(Integer.toString(backResult)).getResultCode()
						+" msg--" 
						+ActiveUtils.getEnumConstant(Integer.toString(backResult)).getResultDesc());
					}
					//回退全局资格
					backResult=RedisCommon.rollbackBaseGlobalLimit(clientId, activeId);
					if(backResult!=0){
						LogCvt.info("----订单创建失败交易-全局资格回退失败--code--"
						+ActiveUtils.getEnumConstant(Integer.toString(backResult)).getResultCode()
						+" msg--" 
						+ActiveUtils.getEnumConstant(Integer.toString(backResult)).getResultDesc());
					}
				}
				
			}
			
			return new Result(ResultCode.success);
			
		}catch(Exception ex){
			LogCvt.error("回退满减资格 异常", ex);
			return new Result(ResultCode.failed, "回退满减资格 系统异常");
		}
	}
	
	/**
	 * 删除订单
	 * */
	public static void deleteMarketOrder(String orderId){
		try{
			ActiveOrder activeOrder = new ActiveOrder();
			activeOrder.setOrderId(orderId);
			Integer count = activeOrderHandler.deleteActiveOrder(activeOrder);
			LogCvt.debug("删除营销订单主表 "+count+" 条");
			if( count > 0 ){
				ActiveOrderDetail activeOrderDetail = new ActiveOrderDetail();
				activeOrderDetail.setOrderId(orderId);
				count = activeOrderDetailHandler.deleteActiveOrderDetail(activeOrderDetail);
				LogCvt.debug("删除营销订单详情表 "+count+" 条");
			}
		}catch(Exception e){
			LogCvt.error("删除营销订单 "+orderId+" 异常",e);
		}
	}

}
