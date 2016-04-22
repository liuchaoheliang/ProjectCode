package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.froad.db.redis.RedisCommon;
import com.froad.db.redis.SupportsRedis;
import com.froad.handler.ActiveSearchProductHandler;
import com.froad.handler.impl.ActiveSearchProductHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveSearchProductLogic;
import com.froad.po.ActiveBaseRule;
import com.froad.po.ActiveResultInfo;
import com.froad.po.ActiveSearchProductInfo;
import com.froad.thrift.vo.active.FindActiveRuleListResVo;
import com.froad.thrift.vo.active.FindActiveRuleResVo;
import com.froad.thrift.vo.active.FindActiveVo;
import com.froad.util.RedisKeyUtil;
import com.froad.util.Validator;

public class ActiveSearchProductLogicImpl implements ActiveSearchProductLogic{

	/**
	 * @Fields activeSearchHandler : 查询商品活动信息数据逻辑接口
	 */	
	private ActiveSearchProductHandler activeSearchHandler = new ActiveSearchProductHandlerImpl();
	
	 /**
	  * @Title: findActiveRuleByMerchantIds
	  * @Description: 根据商户ID查找相关商品活动信息业务实现
	  * @author: shenshaocheng 2015年11月6日
	  * @modify: shenshaocheng 2015年11月6日
	  * @param activeSearchMerchantInfo
	  * @return
	  * @see com.froad.logic.ActiveSearchMerchanLogic#findActiveRuleByMerchantIds(
	  * com.froad.po.ActiveSearchMerchantInfo)
	  */	
	@Override
	public FindActiveRuleListResVo findActiveRuleByProductIds(
			ActiveSearchProductInfo activeSearchProductInfo) {
		FindActiveRuleListResVo findActiveRuleListResVo = new FindActiveRuleListResVo();
		if (activeSearchProductInfo.getProductIdList().size() > 0) {
			LogCvt.debug("商品ID " + activeSearchProductInfo.getProductIdList().size() + " 个");
			// 获取商品相关活动信息。
			List<ActiveResultInfo> activeResultInfoList  = new ArrayList<ActiveResultInfo>();
			// 先从redis中获取，如果redis中没有，则从mysql中获取
			activeResultInfoList = this.findActiveRuleFromRedis(activeSearchProductInfo);			
			if(activeResultInfoList.size() < 1) {
				activeResultInfoList = activeSearchHandler.findActiveRuleByProductIds(activeSearchProductInfo);
				this.SetActiveRuleListResVoToRedis(activeResultInfoList);
			}	
			
			// 组装findActiveRuleListResVo
			// traversalid为了方便组装格式，为当前商品ID
			String traversalid = "";
			findActiveRuleListResVo.setReqId(activeSearchProductInfo.getReqId());
			findActiveRuleListResVo.setClientId(activeSearchProductInfo.getClientId());
			FindActiveRuleResVo findActiveRuleResVo = new FindActiveRuleResVo();
			findActiveRuleResVo.findActiveList = new ArrayList<FindActiveVo>();	
			findActiveRuleListResVo.findActiveRuleResList = new ArrayList<FindActiveRuleResVo>();
			LogCvt.debug("商品对应活动数目" + activeResultInfoList.size());
			try {
				for (int i = 0; i < activeResultInfoList.size(); i++) {
					FindActiveVo findActiveVo = new FindActiveVo();
					ActiveResultInfo activeResultInfo = activeResultInfoList.get(i);							
					// 当前商户ID不一致时，表示前一个商品活动信息已遍历完毕。
					if (!traversalid.equals(activeResultInfo.getId())) {				    	
				    	// 添加商品信息到返回集中。
				    	if(findActiveRuleResVo.findActiveList != null 
				    			&& findActiveRuleResVo.findActiveList.size() > 0) {
				    		findActiveRuleListResVo.findActiveRuleResList.add(findActiveRuleResVo);
				    	}
				    	findActiveRuleResVo = new FindActiveRuleResVo();
				    	findActiveRuleResVo.findActiveList = new ArrayList<FindActiveVo>();
				    	findActiveRuleResVo.setId(activeResultInfo.getId());
				    	traversalid = activeResultInfo.getId();			    		
				    }	
					
					findActiveVo.setActiveId(activeResultInfo.getActiveId());
				    findActiveVo.setActiveName(activeResultInfo.getActiveName());
				    findActiveVo.setActiveType(activeResultInfo.getActiveType());
				    // 如果用户没有登陆，则只判断活动是否存在余额和剩余活动次数，
					// 否则需要校验用户是否拥有参加活动资格				   
					if (RedisCommon.checkBaseGlobalLimit(
							activeSearchProductInfo.getClientId(),
							activeResultInfo.getActiveId())
							&& RedisCommon.checkGlobalLimit(
									activeSearchProductInfo.getClientId(),
									activeResultInfo.getActiveId(), null)) {
						LogCvt.debug("校验活动" + activeResultInfo.getActiveName()
								+ "全局资格检查和检查客户是否有当日/小时资格");
						if (!Validator.isEmptyStr(activeSearchProductInfo
								.getMemberCode())) {
							if (RedisCommon.checkPersonLimit(
									activeSearchProductInfo.getClientId(),
									activeResultInfo.getActiveId(), Long
											.parseLong(activeSearchProductInfo
													.getMemberCode()))) {
								LogCvt.debug("校验个人 "
										+ activeSearchProductInfo
												.getMemberCode() + " 活动资格资格");
								findActiveRuleResVo.findActiveList
										.add(findActiveVo);
							}
						} else {
							findActiveRuleResVo.findActiveList
							.add(findActiveVo);
						}
					} 
				}	
				// 最后一个商品加入到返回集
				findActiveRuleListResVo.findActiveRuleResList.add(findActiveRuleResVo);
			} catch (Exception e) {
				LogCvt.error("查找商品对应活动异常 ：" + e.getMessage(), e);
			}
			
		}
		
		return findActiveRuleListResVo;
	}
	
	 /**
	  * @Title: findActiveRuleFromRedis
	  * @Description: 处理从redis中获取的商品数据 
	  * @author: shenshaocheng 2015年11月9日
	  * @modify: shenshaocheng 2015年11月9日
	  * @param activeSearchMerchantInfo 商户信息。
	  * @return
	 */	
	private List<ActiveResultInfo> findActiveRuleFromRedis(
			ActiveSearchProductInfo activeSearchProductInfo) {
		List<ActiveResultInfo> activeResultInfoList = new ArrayList<ActiveResultInfo>();
		List<String> productIdList = activeSearchProductInfo.getProductIdList();
		List<String> activeIdList = new ArrayList<String>();
		for(int i = 0; i < productIdList.size(); i++) {			
			Set<String> activeSet = SupportsRedis.get_cbbank_active_product_info((productIdList.get(i)));
			// 遍历从Redis中获取的活动集合
			for (String activeInfo : activeSet) {
				if(activeInfo.indexOf(":") > 1) {
					ActiveResultInfo activeResultInfo = new ActiveResultInfo();
					activeResultInfo.setId(productIdList.get(i));
					activeResultInfo.setActiveName(activeInfo.split(":")[0]);
					activeResultInfo.setActiveType(activeInfo.split(":")[1]);
					activeResultInfo.setActiveId(activeInfo.split(":")[2]);
					activeResultInfoList.add(activeResultInfo);
					activeIdList.add(activeInfo.split(":")[2]);
				}
			}
		}
		// 移除Redis中已经逾期的活动
		List<ActiveResultInfo> delActiveList = this.delOverdueActivitiesRedisByActiveId(activeIdList, false);
		if(delActiveList != null && delActiveList.size() > 0) {
			for(ActiveResultInfo activeInfo : activeResultInfoList){
				for(ActiveResultInfo delActiveInfo : delActiveList) {
					if(activeInfo.getActiveId().equals(delActiveInfo.getActiveId())) {
						activeResultInfoList.remove(activeInfo);
					}
				}
			}
		}
		
		return activeResultInfoList;
	}
	
	 /**
	  * @Title: SetActiveRuleListResVoToRedis
	  * @Description: 把Mysql的结果集放回Redis中
	  * @author: shenshaocheng 2015年11月9日
	  * @modify: shenshaocheng 2015年11月9日
	  * @return
	 */	
	private void SetActiveRuleListResVoToRedis(List<ActiveResultInfo> activeResultInfoList) {
		for(ActiveResultInfo activeResultInfo : activeResultInfoList) {
			ActiveBaseRule activeBaseRule = new ActiveBaseRule();
			activeBaseRule.setActiveId(activeResultInfo.getActiveId());
			activeBaseRule.setActiveName(activeResultInfo.getActiveName());
			activeBaseRule.setType(activeResultInfo.getActiveType());
			Set<String> active2productSet = new HashSet<String>();
			Set<String> product2activeSet = new HashSet<String>();
			active2productSet.add(RedisKeyUtil.cbbank_active_product_product_id(activeResultInfo.getId().toString()));	
			product2activeSet.add(activeResultInfo.getId());
			SupportsRedis.set_cbbank_active_product_info_active_id(product2activeSet, activeResultInfo.getActiveId());
			SupportsRedis.set_cbbank_active_product_product_id(active2productSet, activeBaseRule);			
		}
		// 使用了太多对象，占用内存，强制进行垃圾回收。
		System.gc();
	}

	@Override
	public List<ActiveResultInfo> delOverdueActivitiesRedisByActiveId(
			List<String> activeIdList, Boolean isOverdue) {
		List<ActiveResultInfo> activeResultList = new ArrayList<ActiveResultInfo>();
		if(isOverdue && activeIdList != null && activeIdList.size() > 0) {
			activeResultList = RedisCommon.delOverdueActivitiesRedisByActiveId(activeIdList);		
		} else if(!isOverdue && activeIdList != null && activeIdList.size() > 0) {
			// 获取商品相关活动信息。
			List<ActiveResultInfo> activeResultInfoList  = new ArrayList<ActiveResultInfo>();
			activeResultInfoList = this.activeSearchHandler.findOverdueActivitiesByIds(activeIdList);
			for(ActiveResultInfo activeResultInfo : activeResultInfoList) {
				SupportsRedis.del_cbbank_active_product_info(activeResultInfo.getActiveId());
				SupportsRedis.del_cbbank_active_merchant_info(activeResultInfo.getActiveId());
				ActiveResultInfo activeResultInfo1 = new ActiveResultInfo();
				activeResultInfo1.setActiveId(activeResultInfo.getActiveId());
				activeResultList.add(activeResultInfo1);
			}	
		}
		
		return activeResultList;
	}
}
