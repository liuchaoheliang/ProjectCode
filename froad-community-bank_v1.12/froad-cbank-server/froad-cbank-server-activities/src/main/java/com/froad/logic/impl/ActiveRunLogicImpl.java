package com.froad.logic.impl;

import java.util.HashSet;
import java.util.Set;

import com.froad.db.redis.SupportsRedis;
import com.froad.enums.ActiveType;
import com.froad.enums.ResultCode;
import com.froad.handler.ActiveBaseRuleHandler;
import com.froad.handler.ActiveRuleInfoHandler;
import com.froad.handler.impl.ActiveBaseRuleHandlerImpl;
import com.froad.handler.impl.ActiveRuleInfoHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveRunLogic;
import com.froad.po.ActiveBaseRule;
import com.froad.po.ActiveTagRelation;
import com.froad.po.PutFullCutReq;
import com.froad.po.Result;
import com.froad.util.RedisKeyUtil;

public class ActiveRunLogicImpl implements ActiveRunLogic {


	
	private static ActiveRuleInfoHandler activeRuleInfoHandler = new ActiveRuleInfoHandlerImpl();
	private static ActiveBaseRuleHandler activeBaseRuleHandler = new ActiveBaseRuleHandlerImpl();
	

	 /**
	  * @Title: putFullCut
	  * @Description: TODO
	  * @author: Joker 2015年12月25日
	  * @modify: Joker 2015年12月25日
	  * @param putFullCutReq
	  * @return
	  * @see com.froad.logic.ActiveRunLogic#putFullCut(com.froad.po.PutFullCutReq)
	  */
	
	
	@Override
	public Result putFullCut(PutFullCutReq putFullCutReq) {
		
		Result result = new Result(ResultCode.success, ",没有一个满足条件营销活动");
		
		String clientId = putFullCutReq.getClientId();
		String itemType = putFullCutReq.getItemType();
		String itemId = putFullCutReq.getItemId();
		Set<String> productIds = putFullCutReq.getProductIds();
		
		ActiveTagRelation fullCutActive = activeRuleInfoHandler.isExistProductActive(clientId, itemType, itemId, null, ActiveType.fullCut.getCode());
		
		ActiveTagRelation fullGiveActive = activeRuleInfoHandler.isExistProductActive(clientId, itemType, itemId, null, ActiveType.fullGive.getCode());
		
		ActiveTagRelation firstPay = activeRuleInfoHandler.isExistProductActive(clientId, itemType, itemId, null, ActiveType.firstPayment.getCode());
		
		if(null != fullCutActive) {
			ActiveBaseRule activeBaseRule = new ActiveBaseRule();
			activeBaseRule.setActiveId(fullCutActive.getActiveId());
			activeBaseRule = activeBaseRuleHandler.findOneActiveBaseRuleByActiveId(activeBaseRule);
			
			//update redis
			Set<String> redisKeys = new HashSet<String>();
			for(String productId : productIds) {
				
				redisKeys.add(RedisKeyUtil.cbbank_active_product_product_id(productId));//组装redis key
			}
			
			SupportsRedis.set_cbbank_active_product_product_id(redisKeys,
					activeBaseRule);
			SupportsRedis.set_cbbank_active_product_info_active_id(
					productIds, fullCutActive.getActiveId());
			
			LogCvt.info("新增的["+productIds.size()+"]个商品已经添加到满减活动["+fullCutActive.getActiveId()+"]中");
			return result = new Result(ResultCode.success, "新增的商品已经添加到满减活动中");
		}
		
		if(null != fullGiveActive) {
			ActiveBaseRule activeBaseRule = new ActiveBaseRule();
			activeBaseRule.setActiveId(fullGiveActive.getActiveId());
			activeBaseRule = activeBaseRuleHandler.findOneActiveBaseRuleByActiveId(activeBaseRule);
			
			//update redis
			Set<String> redisKeys = new HashSet<String>();
			for(String productId : productIds) {
				
				redisKeys.add(RedisKeyUtil.cbbank_active_product_product_id(productId));//组装redis key
			}
			
			SupportsRedis.set_cbbank_active_product_product_id(redisKeys,
					activeBaseRule);
			SupportsRedis.set_cbbank_active_product_info_active_id(
					productIds, fullGiveActive.getActiveId());
			
			LogCvt.info("新增的["+productIds.size()+"]个商品已经添加到满赠活动["+fullGiveActive.getActiveId()+"]中");
			return result = new Result(ResultCode.success, "新增的商品已经添加到满赠活动中");
		}

		if(null != firstPay) {
			ActiveBaseRule activeBaseRule = new ActiveBaseRule();
			activeBaseRule.setActiveId(firstPay.getActiveId());
			activeBaseRule = activeBaseRuleHandler.findOneActiveBaseRuleByActiveId(activeBaseRule);
			
			//update redis
			Set<String> redisKeys = new HashSet<String>();
			for(String productId : productIds) {
				
				redisKeys.add(RedisKeyUtil.cbbank_active_product_product_id(productId));//组装redis key
			}
			
			SupportsRedis.set_cbbank_active_product_product_id(redisKeys,
					activeBaseRule);
			SupportsRedis.set_cbbank_active_product_info_active_id(
					productIds, firstPay.getActiveId());
			
			LogCvt.info("新增的["+productIds.size()+"]个商品已经添加到首单满减活动["+firstPay.getActiveId()+"]中");
			return result = new Result(ResultCode.success, "新增的商品已经添加到首单满减活动中");
		}
		
		LogCvt.info("没有一个满足条件营销活动,该商品不需要添加到营销活动");
		return result;
	}
}
