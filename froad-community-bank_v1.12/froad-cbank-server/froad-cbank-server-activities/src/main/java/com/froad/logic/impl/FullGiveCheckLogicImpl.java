package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.froad.db.redis.RedisCommon;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.FullGiveCheckLogic;
import com.froad.po.FullGiveCheckReq;
import com.froad.po.OrderProduct;
import com.froad.po.Result;

public class FullGiveCheckLogicImpl implements FullGiveCheckLogic {

	@Override
	public Result fullGiveCheck(FullGiveCheckReq fullGiveCheckReq) {
		
		LogCvt.info("满赠的支付前资格检查 fullGiveCheck 逻辑:");
		
		String clientId = fullGiveCheckReq.getClientId();
		Long memberCode = fullGiveCheckReq.getMemberCode();
		List<String> fullGiveActiveIds = fullGiveCheckReq.getFullGiveActiveIds();
		List<OrderProduct> orderProductList = fullGiveCheckReq.getOrderProductList();
		
		if( ( clientId == null || "".equals(clientId) )
				||
			( memberCode == null || memberCode <= 0 )
				||
			( fullGiveActiveIds == null || fullGiveActiveIds.size() < 1 )
				||
			( orderProductList == null || orderProductList.size() < 1 )
				){
			return new Result(ResultCode.notAllParameters);
		}
		
		for( String activeId : fullGiveActiveIds ){
			// 1 获取缓存中的满赠活动细则
			Map<String, String> active = RedisCommon.readFullCutActive(clientId, activeId);
			LogCvt.debug("满赠活动 "+activeId+" 明细:"+JSON.toJSONString(active));
			if( active == null || active.isEmpty() ){
				LogCvt.debug("满赠活动 "+activeId+" 没有明细");
				return new Result(ResultCode.failed.getCode(), "满赠活动["+activeId+"]明细为空,活动无效");
			}
			
			String readActiveResult = active.get("result");
			String activeName = active.get("activeName");
			// 失效
			if( ResultCode.market_active_rule_invalid.getCode().equals(readActiveResult) ){
				LogCvt.debug("订单中满赠活动 "+activeId+" 已经失效");
				// 删除所属的两个对应redis
				try{
					List<String> ail = new ArrayList<String>();
					ail.add(activeId);
					RedisCommon.delOverdueActivitiesRedisByActiveId(ail);
				}catch(Exception e){
					LogCvt.error("活动 "+activeId+" 失效-删除所属的两个对应redis 异常",e);
				}
				return new Result(ResultCode.failed.getCode(), "满赠活动["+activeName+"-"+activeId+"]已经失效");
			}
			// 初始化满赠活动失败 或 读取满赠活动失败
			if( ResultCode.market_active_rule_init_fail.getCode().equals(readActiveResult) 
					|| ResultCode.market_active_rule_read_fail.getCode().equals(readActiveResult) ){
				LogCvt.debug("满赠活动 "+activeId+" 初始化失败或读取失败");
				return new Result(ResultCode.failed.getCode(), "读取满赠活动["+activeName+"-"+activeId+"]失败");
			}
			
			// 满赠活动还未开始生效
			if( ResultCode.market_active_rule_no_start.getCode().equals(readActiveResult) 
					|| ResultCode.market_active_rule_read_fail.getCode().equals(readActiveResult) ){
				LogCvt.debug("满减赠活动 "+activeId+" 还未开始生效");
				return new Result(ResultCode.failed.getCode(), "读取满赠活动["+activeName+"-"+activeId+"]还未开始生效");
			}
			
			// 2 判断满赠活动的全局限制
			boolean checkResult = RedisCommon.checkBaseGlobalLimit(clientId, activeId);
			LogCvt.debug("满赠活动 "+activeId+" 全局限制:"+checkResult);
			if( !checkResult ){
				LogCvt.debug("满赠活动 "+activeId+"-"+activeName+" 全局限制已满");
				return new Result(ResultCode.failed.getCode(), "满赠活动["+activeName+"-"+activeId+"]的全局限制限制已满");
			}
			
			// 3 判断满赠活动的时间段限制
			checkResult = RedisCommon.checkGlobalLimit(clientId, activeId, null);
			LogCvt.debug("满赠活动 "+activeId+" 时间段限制:"+checkResult);
			if( !checkResult ){
				LogCvt.debug("订单中满赠活动 "+activeId+"-"+activeName+" 时间段限制已满");
				return new Result(ResultCode.failed.getCode(), "满赠活动["+activeName+"-"+activeId+"]的时间段内限制限制已满");
			}
			
			// 4 判断个人的限制
			checkResult = RedisCommon.checkPersonLimit(clientId, activeId, memberCode);
			LogCvt.debug("满赠活动 "+activeId+" 个人限制:"+checkResult);
			if( !checkResult ){
				LogCvt.debug("订单中满赠活动 "+activeId+"-"+activeName+" 个人限制已满");
				return new Result(ResultCode.failed.getCode(), "满赠活动["+activeName+"-"+activeId+"]个人当前限制已满");
			}
		}
		
		return new Result(ResultCode.success);
	}

}
