package com.froad.logic.impl;

import java.util.Date;
import java.util.Map;

import com.froad.db.redis.RedisCommon;
import com.froad.db.redis.RegisterRedis;
import com.froad.enums.ActiveAwardType;
import com.froad.enums.ResultCode;
import com.froad.handler.HandselInfoHandler;
import com.froad.handler.RegistDetailRuleHandler;
import com.froad.handler.VouchersInfoHandler;
import com.froad.handler.impl.HandselInfoHandlerImpl;
import com.froad.handler.impl.RegistDetailRuleHandlerImpl;
import com.froad.handler.impl.VouchersInfoHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.RegisteredRunLogic;
import com.froad.po.HandselInfo;
import com.froad.po.RegistDetailRule;
import com.froad.po.RegisteredHandsel;
import com.froad.po.Result;
import com.froad.po.VouchersInfo;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.ThirdpartyPayService;
import com.froad.thrift.vo.payment.ResultVo;
import com.froad.util.ActiveConstants;

public class RegisteredRunLogicImpl implements RegisteredRunLogic {

	private RegistDetailRuleHandler registDetailRuleHandler = new RegistDetailRuleHandlerImpl();
	private static VouchersInfoHandler vouchersInfoHandler = new VouchersInfoHandlerImpl();
	private static HandselInfoHandler handselInfoHandler = new HandselInfoHandlerImpl();
	
	@Override
	public Result registeredHandsel(RegisteredHandsel registeredHandsel) {
				
		LogCvt.info("注册 赠送 registeredHandsel 逻辑:");
		
		Long memberCode = registeredHandsel.getMemberCode();
		String clientId = registeredHandsel.getClientId();
		String loginId = registeredHandsel.getLoginId();//联盟积分参数
		
		try{
			
			// 查询当前客户端有效的注册送规则
			RegistDetailRule registDetailRule = registDetailRuleHandler.findNowEffectiveRegisteredHandsel(clientId);
			if( registDetailRule == null || registDetailRule.getActiveId() == null ){
				LogCvt.debug("当前客户端注册送活动无效");
				return new Result(ResultCode.failed.getCode(), "当前客户端无有效的注册送活动");
			}
			
			
			// 查询注册送规则redis
			String activeId = registDetailRule.getActiveId();
			Map<String, String> registActive = RegisterRedis.readRegisterActive(clientId, activeId);
			
			// 判断规则状态
			String readResult = registActive.get("result");
			if( !"0".equals(readResult) ){
				LogCvt.debug("活动 "+activeId+" 当前客户端注册送活动无效");
				return new Result(ResultCode.failed.getCode(), "当前客户端注册送活动无效");
			}
			
			// 判断活动的时间段限制
			boolean checkResult = RegisterRedis.checkGlobalLimit(clientId, activeId, null);
			LogCvt.debug("活动 "+activeId+" 时间段限制:"+checkResult);
			if( !checkResult ){
				LogCvt.debug("活动 "+activeId+" 时间段内限制已满");
				return new Result(ResultCode.failed.getCode(), "时间段内限制已满");
			}
			
			// 减 - 活动时间段限额
			int cutResult = RegisterRedis.cutGlobalLimit(clientId, activeId);
			LogCvt.debug("活动 "+activeId+" 删减时间段限额结果 "+(cutResult==0));
//			
			// 奖励方式判断
			String awardType = registActive.get("awardType");
			Result result = null;
			// 送红包
			if( ActiveAwardType.vouchers.getCode().equals(awardType) ){
				
				result = handselOfVouchers(registActive, memberCode);
				
			// 送联盟积分
			}else if( ActiveAwardType.unionIntegral.getCode().equals(awardType) ){
				
				result = handselOfUnion(registActive, memberCode, loginId);
			}
			if( !ResultCode.success.getCode().equals(result.getResultCode()) ){
				return result;
			}
			
			return new Result(ResultCode.success);
		}catch(Exception e){
			LogCvt.error("注册赠送 异常", e);
			return new Result(ResultCode.failed);
		}
	}

	/**
	 * 送红包
	 * */
	private static Result handselOfVouchers(Map<String, String> registActive, Long memberCode){
		try{
			
			String activeId = registActive.get("activeId");
			String vouchersId = registActive.get("vouchersActiveId");
			VouchersInfo vouchersInfo = vouchersInfoHandler.findAvailableVouchersInfoByActiveId(vouchersId);
			if( vouchersInfo == null || vouchersInfo.getId() <= 0 ){
				return new Result(ResultCode.failed.getCode(), "已无可用红包");
			}
			
			vouchersInfo.setMemberCode(memberCode);
			vouchersInfo.setSendActiveId(activeId);
			vouchersInfo.setSendTime(new Date());
			vouchersInfo.setUpdateTime(new Date());
			
			int result = vouchersInfoHandler.updateVouchersInfo(vouchersInfo);
			LogCvt.debug("注册赠送红包 - 修改赠送的红包状态:受影响的行数-"+result);
			if( result <= -1 ){
				LogCvt.error("注册赠送红包 失败\n"
						+ "activeId-"+activeId+" : memberCode-"+memberCode+" : vouchersId-"+vouchersInfo.getVouchersId());
				return new Result(ResultCode.failed.getCode(), "注册赠送红包 失败");
			}else{
				// TODO 记录赠送信息
				registActive.put("vouchers_id", vouchersInfo.getVouchersId());
				registActive.put("vouchers_active_id", vouchersInfo.getActiveId());
				initHandselInfo(registActive, memberCode);
				return new Result(ResultCode.success);
			}
			
		}catch(Exception e){
			LogCvt.error("注册赠送红包 异常", e);
			return new Result(ResultCode.failed.getCode(), "注册赠送红包 异常");
		}
	}
	
	/**
	 * 送联盟积分
	 * */
	private static Result handselOfUnion(Map<String, String> registActive, Long memberCode, String loginId){
		try{
			Result result = new Result(ResultCode.success,"注册赠送联盟积分成功");

			String clientId = registActive.get("clientId");
			String activeId = registActive.get("activeId");
			Double point = Double.valueOf(registActive.get("perUnionIntegral"));
			int cutResult = RegisterRedis.cutIntegralLimit(clientId, activeId);
			if(cutResult == 0) {
				ResultVo vo = registerGiveUnionPoint(clientId, loginId, point);
				result.setResultCode(vo.getResultCode());
				result.setResultDesc(vo.getResultDesc());
				HandselInfo handselInfo = setHandselInfo(registActive, memberCode);
				Long success = handselInfoHandler.addHandselInfo(handselInfo);
				if(success<1){
					LogCvt.error("记录注册赠送赠送信息失败");
				}
			}
			return result;
			
		}catch(Exception e){
			LogCvt.error("注册赠送记录赠送信息 异常", e);
		}
		return null;
	}
	
    private static ResultVo registerGiveUnionPoint(String clientId, String loginId, Double pointValue){
        try {
            LogCvt.info("送联盟积分开始"+new Date().getTime());
            ThirdpartyPayService.Iface client = (ThirdpartyPayService.Iface) ThriftClientProxyFactory.createIface(ThirdpartyPayService.class, ActiveConstants.THRIFT_ORDER_HOST, Integer.valueOf(ActiveConstants.THRIFT_ORDER_PORT),60000);
            ResultVo resultVo = client.pointPresent(clientId, loginId, pointValue);
            LogCvt.info("送联盟积分结束"+new Date().getTime());
           return resultVo;
        }catch (Exception e) {
            LogCvt.error("送联盟积分异常：" +e,e);
            LogCvt.error("orderhost" +ActiveConstants.THRIFT_ORDER_HOST+"orderport:"+ActiveConstants.THRIFT_ORDER_PORT+",clientId:"+clientId+",loginId:"+loginId+",pointValue:"+pointValue);
        }
        return null;
    }
	
	
	/**
	 * 记录赠送信息
	 * */
	private static void initHandselInfo(Map<String, String> registActive, Long memberCode){
		try{
			
			HandselInfo handselInfo = setHandselInfo(registActive, memberCode);
			Long success = handselInfoHandler.addHandselInfo(handselInfo);
			if(success<1){
				Result result = new Result(ResultCode.failed,"注册赠送失败,memberCode:"+memberCode);
				LogCvt.error("注册赠送记录赠送信息 异常,memberCode:"+memberCode);
			}
		}catch(Exception e){
			LogCvt.error("注册赠送记录赠送信息 异常", e);
		}
	}
	
	private static HandselInfo setHandselInfo(Map<String, String> registActive, Long memberCode) {
		
		HandselInfo handselInfo = new HandselInfo();
		handselInfo.setClientId(registActive.get("clientId"));
		handselInfo.setHandselMemberCode(memberCode);
		handselInfo.setHandselType(false);//赠送方式 0注册送 1首单送
		handselInfo.setHandselValue(ActiveAwardType.vouchers.getCode());
		handselInfo.setHandselActiveId(registActive.get("activeId"));
		handselInfo.setCutMoney(0);//满减额度
		handselInfo.setHandselVouchersActiveId(registActive.get("activeId"));//注册送活动
		handselInfo.setHandselVouchersActiveId(registActive.get("vouchers_active_id"));//代金券所属的活动ID
		handselInfo.setVouchersId(registActive.get("vouchers_id"));//代金券ID
		handselInfo.setProductId(registActive.get("productId"));
		Integer perBankIntegral = registActive.get("perBankIntegral") == null ? 0 : Integer.valueOf(registActive.get("perBankIntegral"));
		handselInfo.setBankIntegral(Integer.valueOf(perBankIntegral));
		Integer perUnionIntegral = registActive.get("perUnionIntegral") == null ? 0 : Integer.valueOf(registActive.get("perUnionIntegral"));
		handselInfo.setUnionIntegral(Integer.valueOf(perUnionIntegral));
		handselInfo.setCreAwardType(null);
		handselInfo.setCreMemberCode(0L);
		handselInfo.setCreVouchersActiveId(null);
		handselInfo.setCreVouchersId(null);
		handselInfo.setCreVouchersMoney(0);
		handselInfo.setCreProductId(null);
		handselInfo.setFullGiveOrderId(null);
		handselInfo.setGiveTime(new Date());
		return handselInfo;
		
	}
	
}
