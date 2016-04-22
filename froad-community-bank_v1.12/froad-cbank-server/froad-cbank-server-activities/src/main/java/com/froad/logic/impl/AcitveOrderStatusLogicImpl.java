package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.froad.db.redis.RedisCommon;
import com.froad.db.redis.VouchersRedis;
import com.froad.enums.ActiveAwardType;
import com.froad.enums.ActiveType;
import com.froad.enums.ResultCode;
import com.froad.handler.ActiveDetailRuleHandler;
import com.froad.handler.ActiveOrderDetailHandler;
import com.froad.handler.ActiveOrderHandler;
import com.froad.handler.HandselInfoHandler;
import com.froad.handler.VouchersInfoHandler;
import com.froad.handler.impl.ActiveOrderDetailHandlerImpl;
import com.froad.handler.impl.ActiveOrderHandlerImpl;
import com.froad.handler.impl.HandselInfoHandlerImpl;
import com.froad.handler.impl.VouchersInfoHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveOrderStatusLogic;
import com.froad.po.ActiveOrder;
import com.froad.po.ActiveOrderDetail;
import com.froad.po.FullGiveActive;
import com.froad.po.HandselInfo;
import com.froad.po.Result;
import com.froad.po.UpdateMarketOrderReq;
import com.froad.po.UpdateMarketOrderRes;
import com.froad.po.VouchersInfo;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.ThirdpartyPayService;
import com.froad.thrift.vo.payment.ResultVo;
import com.froad.util.ActiveConstants;
import com.froad.util.Arith;

/**
 * @ClassName: AcitveOrderStatusLogicImpl
 * @Description: 1.订单支付成功，更新数据库订单状态 2.订单支付失败，回退个人资格/全局资格
 * @author froad-zengfanting 2015年11月7日
 * @modify froad-lf 2015年12月04日
 */
public class AcitveOrderStatusLogicImpl implements ActiveOrderStatusLogic {
	private ActiveOrderHandler activeOrderHandler=new ActiveOrderHandlerImpl();
	private ActiveOrderDetailHandler activeOrderDetailHandler=new ActiveOrderDetailHandlerImpl();
	private static VouchersInfoHandler vouchersInfoHandler = new VouchersInfoHandlerImpl();
	private static HandselInfoHandler handselInfoHandler = new HandselInfoHandlerImpl();
	
	/**
	  * @Title: updateOrderStatusSuccess
	  * @Description: 支付成功
	  * @author: zengfanting 2015年11月8日
	  * @modify: lf 2015年12月04日
	  * @param orderReqVo
	  * @return
	  * @see com.froad.logic.ActiveOrderStatusLogic#updateOrderStatusSuccess(com.froad.thrift.vo.active.UpdateMarketOrderReqVo)
	 */
	@Override
	public UpdateMarketOrderRes updateOrderStatusSuccess(UpdateMarketOrderReq updateMarketOrderReq) {

		UpdateMarketOrderRes updateMarketOrderRes = new UpdateMarketOrderRes();
		
		try{
			
			String orderId = updateMarketOrderReq.getOrderId();
			Long memberCode = updateMarketOrderReq.getMemberCode();
			Date updateTime = new Date();
			Date payTime = updateMarketOrderReq.getPayTime();
			String payBillNo = updateMarketOrderReq.getPayBillNo();
			String loginId = updateMarketOrderReq.getLoginId();
			
			//检查促销订单是否存在
			ActiveOrder activeOrder =new ActiveOrder();
			activeOrder.setOrderId(orderId);
			activeOrder.setUpdateTime(updateTime);
			activeOrder.setPayTime(payTime);
			activeOrder.setMemberCode(memberCode);
			activeOrder.setPayBillNo(payBillNo);
			
			// 营销订单主表状态更新
			if(activeOrderHandler.updateActiveOrderByOrderId(activeOrder)<=0){
				LogCvt.info("--更新支付结果----更新数据库失败----");
				updateMarketOrderRes.setResult(new Result(ResultCode.failed, "促销订单状态更新 失败"));
				return updateMarketOrderRes;
			}
			
			// 券状态(其它字段)更新 - 如果更新失败则要看日志手工更新
			// TODO 后面加上监控
			List<String> vouchersIdList = updateMarketOrderReq.getVouchersIdList();
			if( vouchersIdList != null && vouchersIdList.size() > 0 ){
				Boolean updateResult = vouchersInfoHandler.updateVouchersInfoPaySuccessBatch(vouchersIdList, memberCode, orderId);
				LogCvt.info("--更新支付结果----更新券状态:"+updateResult);
				
				// TODO 2016-07-01 00:00:00 去掉下面的代码
				// 去掉所有券码使用次数
				//cutAllVouchersUseNumber(vouchersIdList, memberCode);//comment on 20160201
			}else{
				ActiveOrderDetail activeOrderDetail = new ActiveOrderDetail();
				activeOrderDetail.setOrderId(orderId);
				activeOrderDetail.setType(ActiveType.vouchers.getCode());
				List<ActiveOrderDetail> activeOrderDetailList = activeOrderDetailHandler.findActiveOrderDetail(activeOrderDetail);
				if( activeOrderDetailList != null && activeOrderDetailList.size() > 0 ){
					vouchersIdList = new ArrayList<String>();
					for( ActiveOrderDetail orderDetail : activeOrderDetailList ){
						vouchersIdList.add(orderDetail.getTicketId());
					}
					Boolean updateResult = vouchersInfoHandler.updateVouchersInfoPaySuccessBatch(vouchersIdList, memberCode, orderId);
					LogCvt.info("--更新支付结果----更新券状态:"+updateResult);
					
					// TODO 2016-07-01 00:00:00 去掉下面的代码
					// 去掉所有券码使用次数
					//cutAllVouchersUseNumber(vouchersIdList, memberCode);comment on 20160201
				}else{
					LogCvt.debug("未查询到订单详情列表");
				}
			}
				
			// 处理满赠信息
			List<String> fullGiveActiveIds = updateMarketOrderReq.getFullGiveActiveIds();
			if( fullGiveActiveIds != null && fullGiveActiveIds.size() > 0 ){
				List<FullGiveActive> fullGiveActiveList = disposeFullGiveList(fullGiveActiveIds, updateMarketOrderReq.getClientId(), memberCode, orderId, updateTime, loginId);
				updateMarketOrderRes.setFullGiveActiveList(fullGiveActiveList);
			}
			
			updateMarketOrderRes.setResult(new Result(ResultCode.success));
			return updateMarketOrderRes;
		}catch(Exception e){
			LogCvt.error("促销订单状态更新 异常", e);
			updateMarketOrderRes.setResult(new Result(ResultCode.failed, "促销订单状态更新 异常"));
			return updateMarketOrderRes;
		}
	}
	
	/**
	 * 处理满赠信息 - 多个
	 * */
	private static List<FullGiveActive> disposeFullGiveList(List<String> fullGiveActiveIds, String clientId, Long memberCode, String orderId, Date time, String loginId){
		
		LogCvt.debug("支付成功 - 处理满赠信息");
		
		try{

			List<FullGiveActive> fullGiveActiveList = new ArrayList<FullGiveActive>();
			
			boolean result = false;
			List<HandselInfo> handselInfoList = new ArrayList<HandselInfo>();
			
			for( String activeId : fullGiveActiveIds ){
				
				// 获取缓存中的满赠活动细则
				Map<String, String> active = RedisCommon.readFullCutActive(clientId, activeId);
				// 扣减满赠资格
				result = cutFullGive(active, clientId, memberCode);
				if( result ){
					// 赠送操作
					HandselInfo handselInfo = giveOperation(active, clientId, memberCode, orderId, time, loginId);
					if( handselInfo != null ){
						handselInfoList.add(handselInfo);
					}
				}
			}
			
			// 保存记录
			if( handselInfoList.size() > 0 ){
				LogCvt.debug("支付成功 - 保存满赠信息:"+JSON.toJSONString(handselInfoList));
				result = handselInfoHandler.addHandselInfoByBatch(handselInfoList);
				LogCvt.debug("支付成功 - 处理满赠信息结果:"+result);
				for( HandselInfo handselInfo : handselInfoList ){
					FullGiveActive fullGiveActive = new FullGiveActive();
					fullGiveActive.setActiveId(handselInfo.getHandselActiveId());
					fullGiveActive.setActiveType(handselInfo.getHandselValue());
					if( ActiveAwardType.vouchers.getCode().equals(handselInfo.getHandselValue()) ){
						fullGiveActive.setMonry(Arith.div(handselInfo.getVouchersMoney(), 1000));
					}else if( ActiveAwardType.unionIntegral.getCode().equals(handselInfo.getHandselValue()) ){
						fullGiveActive.setMonry(Double.parseDouble(handselInfo.getUnionIntegral().toString()));
					}
					fullGiveActiveList.add(fullGiveActive);
				}
			}
			
			return fullGiveActiveList;
		}catch(Exception e){
			LogCvt.error("支付成功 - 处理满赠信息 异常", e);
			return null;
		}
	}
	
	/**
	 * 赠送操作
	 * @return 赠送记录
	 * */
	private static HandselInfo giveOperation(Map<String, String> active, String clientId, Long memberCode, String orderId, Date time, String loginId){
		
		String activeId = null;
		
		try{
			
			HandselInfo handselInfo = null;
			
			activeId = active.get("activeId");
			String prePayActiveId = active.get("prePayActiveId");
			String point = active.get("point");
			
			LogCvt.debug("支付成功 - 赠送["+activeId+"]操作");
			
			// 送红包
			if( prePayActiveId != null && !"".equals(prePayActiveId) ){
				
				handselInfo = giveRedPack(active, clientId, memberCode, orderId, time);
				
			// 送联盟积分
			}else if( point != null && !"".equals(point) || Integer.parseInt(point) > 0 ){
				
				handselInfo = giveUnionIntegral(active, clientId, memberCode, orderId, time, loginId, point);
			}else{
				return null;
			}
			
			return handselInfo;
			
		}catch(Exception e){
			LogCvt.error("支付成功 - 赠送["+activeId+"]操作 异常", e);
			return null;
		}
	}
	
	/**
	 * 赠送红包
	 * */
	private static HandselInfo giveRedPack(Map<String, String> active, String clientId, Long memberCode, String orderId, Date time) throws Exception {
		
		String prePayActiveId = active.get("prePayActiveId");
		
		// 查询出可用的一个红包
		VouchersInfo vouchersInfo = vouchersInfoHandler.findAvailableVouchersInfoByActiveId(prePayActiveId);
		if( vouchersInfo == null || vouchersInfo.getId() <= 0 ){
			LogCvt.debug("支付成功 - 赠送["+prePayActiveId+"]红包 - 获取可用红包不成功");
			return null;
		}
		
		// 修改此红包数据信息赠送
		vouchersInfo.setMemberCode(memberCode);
		vouchersInfo.setSendActiveId(prePayActiveId);
		vouchersInfo.setSendTime(time);
		Integer result = vouchersInfoHandler.updateVouchersInfo(vouchersInfo);
		if( result <= 0 ){
			LogCvt.debug("支付成功 - 赠送["+prePayActiveId+"]红包 - 修改红包信息不成功");
			return null;
		}
		
		// 组装赠送信息
		HandselInfo handselInfo = new HandselInfo();
		handselInfo.setClientId(clientId);
		handselInfo.setGiveTime(time);
		handselInfo.setHandselMemberCode(memberCode);
		handselInfo.setHandselType(true);
		handselInfo.setHandselValue(ActiveAwardType.vouchers.getCode());
		handselInfo.setHandselActiveId(active.get("activeId"));
		handselInfo.setFullGiveOrderId(orderId);
		handselInfo.setHandselVouchersActiveId(prePayActiveId);
		handselInfo.setVouchersId(vouchersInfo.getVouchersId());
		handselInfo.setVouchersMoney(Arith.intValue(vouchersInfo.getVouchersMoney()));
		
		return handselInfo;
	}
	
	/**
	 * 赠送联盟积分
	 * */
	private static HandselInfo giveUnionIntegral(Map<String, String> active, String clientId, Long memberCode, String orderId, Date time, String loginId, String point) throws Exception {
		
		
		// 赠送联盟积分
		LogCvt.info("送联盟积分开始"+new Date().getTime());
        ThirdpartyPayService.Iface client = (ThirdpartyPayService.Iface) ThriftClientProxyFactory.createIface(ThirdpartyPayService.class, ActiveConstants.THRIFT_ORDER_HOST, Integer.valueOf(ActiveConstants.THRIFT_ORDER_PORT),60000);
        ResultVo resultVo = client.pointPresent(clientId, loginId, Double.parseDouble(point));
        LogCvt.info("送联盟积分结束"+new Date().getTime());
        if( !ResultCode.success.getCode().equals(resultVo.getResultCode()) ){
        	LogCvt.debug("支付成功 - 赠送联盟积分不成功:"+resultVo.getResultDesc());
        	return null;
        }
        
		// 组装赠送信息
        HandselInfo handselInfo = new HandselInfo();
		handselInfo.setClientId(clientId);
		handselInfo.setGiveTime(time);
		handselInfo.setHandselMemberCode(memberCode);
		handselInfo.setHandselType(true);
		handselInfo.setHandselValue(ActiveAwardType.unionIntegral.getCode());
		handselInfo.setHandselActiveId(active.get("activeId"));
		handselInfo.setFullGiveOrderId(orderId);
		handselInfo.setUnionIntegral(Integer.parseInt(point));
		
		return handselInfo;
	}
	
	/**
	 * 扣减满赠资格 - 单个
	 * */
	private static boolean cutFullGive(Map<String, String> active, String clientId, Long memberCode){
		
		try{
			
			String activeId = active.get("activeId");
			String prePayActiveId = active.get("prePayActiveId");
			String point = active.get("point");

			LogCvt.debug("支付成功 - 扣减满赠活动["+activeId+"]资格");
			
			if( activeId == null || "".equals(activeId) ){
				LogCvt.debug("支付成功 - 扣减满赠活动["+activeId+"]资格 失败");
				return false;
			}
			
			if( (prePayActiveId == null || "".equals(prePayActiveId)) && (point == null || "".equals(point) || Integer.parseInt(point) <= 0) ){
				LogCvt.debug("支付成功 - 扣减满赠活动["+activeId+"]资格 失败 赠送方式配置有误");
				return false;
			}
			
			// 1 减 - 个人限额
			int cutResult = RedisCommon.cutPersonLimit(clientId, activeId, memberCode);
			LogCvt.debug("满赠活动 "+activeId+" 删减个人限额结果 "+(cutResult==0));
			if( cutResult != 0 ){
				return false;
			}
			
			// 2 减 - 活动时间段限额
			cutResult = RedisCommon.cutGlobalLimit(clientId, activeId);
			LogCvt.debug("满赠活动 "+activeId+" 删减时间段限额结果 "+(cutResult==0));
			if( cutResult != 0 ){
				return false;
			}
			
			// 3 减 - 活动全局限额
			cutResult = RedisCommon.cutBaseGlobalLimit(clientId, activeId);
			LogCvt.debug("满赠活动 "+activeId+" 删减全局限额结果 "+(cutResult==0));
			if( cutResult != 0 ){
				return false;
			}
			
			return true;
		}catch(Exception e){
			LogCvt.error("支付成功 - 扣减满赠资格 异常", e);
			return false;
		}
	} 
	
	/**
	 * 去掉所有券码使用次数
	 * // TODO 2016-07-01 00:00:00 去掉此方法
	 * */
	private static void cutAllVouchersUseNumber(List<String> vouchersIdList, Long memberCode){
		for( String vouchersId : vouchersIdList ){
			cutVouchersUseNumber(vouchersId, memberCode);
		}
	}
	
	/**
	 * 去掉券码使用次数
	 * // TODO 2016-07-01 00:00:00 去掉此方法
	 * */
	private static void cutVouchersUseNumber(String vouchersId, Long memberCode){
		try{
			int number = VouchersRedis.getVouchersUseNumber(vouchersId);
			if( number != 999999 && number != -1 && number != 0 ){
				// 减去 券码使用次数
				VouchersRedis.putVouchersUseNumber(vouchersId, number-1);
				
				// 加上 代金券使用人
				VouchersRedis.accumulateVouchersUser(vouchersId, memberCode);
				
			}
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
		}
	}
	
	@Override
	public UpdateMarketOrderRes updateOrderStatusFaile(UpdateMarketOrderReq updateMarketOrderReq) {

		UpdateMarketOrderRes updateMarketOrderRes = new UpdateMarketOrderRes();
		
		try{
			
			Boolean isF2FOrder = updateMarketOrderReq.getIsF2FOrder();
			if( isF2FOrder ){
			
				List<String> vouchersIdList = updateMarketOrderReq.getVouchersIdList();
				if( vouchersIdList == null || vouchersIdList.size() <= 0 ){
					LogCvt.debug("--更新支付结果（交易失败）----无红包券码 无需处理");
					updateMarketOrderRes.setResult(new Result(ResultCode.failed, "面对面订单支付失败处理 - 无红包券码 无需处理"));
					return updateMarketOrderRes;
				}
				
				String orderId = updateMarketOrderReq.getOrderId();
				Date updateTime = new Date();	
			
				ActiveOrder activeOrder=new ActiveOrder();
				activeOrder.setOrderId(orderId);
				activeOrder.setUpdateTime(updateTime);
			
				if( vouchersIdList != null && vouchersIdList.size() > 0 ){
					// 更新券状态
					// TODO 后面加上监控 - 如果更新失败则要看日志手工更新
					Boolean updateResult = vouchersInfoHandler.updateVouchersInfoInitBatch(vouchersIdList);
					LogCvt.info("--更新支付结果（交易失败）----更新券状态:"+updateResult);
					if( updateResult ){
						// 更新订单主表
						updateResult = activeOrderHandler.updateActiveOrderByOrderId(activeOrder)<=0;
						LogCvt.info("--更新支付结果（交易失败）----更新订单主表状态:"+updateResult);
					}
					
					String clientId = updateMarketOrderReq.getClientId();
					long memberCode = updateMarketOrderReq.getMemberCode();
					// 回退红包资格
					Result result = ActiveCreateOrderGoBackLogicImpl.goBackVouchers(vouchersIdList, clientId, memberCode, orderId, 1);
					LogCvt.debug("回退红包资格 结果:"+JSON.toJSONString(result));
				}
			}

//			for (ActiveOrderDetail activeOrderDetail2 : list) {
//				String activeId=activeOrderDetail2.getActiveId();
//				//回退个人资格
//				int result=RedisCommon.rollbackPersonLimit(clientId,activeId, memberCode);
//				
//				if(result!=0){
//					//return  ActiveUtils.getEnumConstant(Integer.toString(result));
//					LogCvt.info("----更新支付结果（交易失败）--code--"
//					+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultCode()
//					+" msg--" 
//					+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultDesc()); 
//				}
//				
//				//回退当日资格
//				result=RedisCommon.rollbackGlobalLimit(clientId, activeId, orderId, 1);
//				if(result!=0){
//					//return ActiveUtils.getEnumConstant(Integer.toString(result));
//					LogCvt.info("----更新支付结果（交易失败）--code--"
//					+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultCode()
//					+" msg--" 
//					+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultDesc());
//				}
//				//回退全局资格
//				result=RedisCommon.rollbackBaseGlobalLimit(clientId, activeId);
//				if(result!=0){
//					//return ActiveUtils.getEnumConstant(Integer.toString(result));
//					LogCvt.info("----更新支付结果（交易失败）--code--"
//					+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultCode()
//					+" msg--" 
//					+ActiveUtils.getEnumConstant(Integer.toString(result)).getResultDesc());
//				}
//			}
			
			updateMarketOrderRes.setResult(new Result(ResultCode.success));
			return updateMarketOrderRes;
		}catch(Exception e){
			LogCvt.error("促销订单状态更新 异常", e);
			updateMarketOrderRes.setResult(new Result(ResultCode.failed, "促销订单状态更新 异常"));
			return updateMarketOrderRes;
		}
	}

}
