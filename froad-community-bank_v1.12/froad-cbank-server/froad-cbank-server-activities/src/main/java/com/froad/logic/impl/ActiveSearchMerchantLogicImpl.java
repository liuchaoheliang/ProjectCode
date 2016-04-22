package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.froad.db.redis.RedisCommon;
import com.froad.db.redis.SupportsRedis;
import com.froad.db.redis.impl.RedisManager;
import com.froad.handler.ActiveSearchMerchantHandler;
import com.froad.handler.impl.ActiveSearchMerchantHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveSearchMerchanLogic;
import com.froad.po.ActiveBaseRule;
import com.froad.po.ActiveResultInfo;
import com.froad.po.ActiveSearchMerchantInfo;
import com.froad.thrift.vo.active.FindActiveRuleListResVo;
import com.froad.thrift.vo.active.FindActiveRuleResVo;
import com.froad.thrift.vo.active.FindActiveVo;
import com.froad.util.RedisKeyUtil;
import com.froad.util.Validator;

public class ActiveSearchMerchantLogicImpl implements ActiveSearchMerchanLogic {

	/**
	 * @Fields activeSearchHandler : 查询商户活动信息数据逻辑接口
	 */
	private ActiveSearchMerchantHandler activeSearchHandler = new ActiveSearchMerchantHandlerImpl();

	/**
	 * @Fields activeSearchHandler : Redis处理方法.
	 */
	private RedisManager redisManager = new RedisManager();

	/**
	 * @Title: findActiveRuleByMerchantIds
	 * @Description: 根据商户ID查找相关商户活动信息业务实现
	 * @author: shenshaocheng 2015年11月6日
	 * @modify: shenshaocheng 2015年11月6日
	 * @param activeSearchMerchantInfo
	 * @return
	 * @see com.froad.logic.ActiveSearchMerchanLogic#findActiveRuleByMerchantIds(com.froad.po.ActiveSearchMerchantInfo)
	 */
	@Override
	public FindActiveRuleListResVo findActiveRuleByMerchantIds(
			ActiveSearchMerchantInfo activeSearchMerchantInfo) {
		FindActiveRuleListResVo findActiveRuleListResVo = new FindActiveRuleListResVo();
		if (activeSearchMerchantInfo.getMerchantIdList().size() > 0) {
			// 商户相关活动信息。
			List<ActiveResultInfo> activeResultInfoList = new ArrayList<ActiveResultInfo>();
			// 先从redis中获取，如果redis中没有，则从mysql中获取
			activeResultInfoList = this
					.findActiveRuleFromRedis(activeSearchMerchantInfo);
			if (activeResultInfoList.size() < 1) {
				activeResultInfoList = activeSearchHandler
						.findActiveRuleByMerchantIds(activeSearchMerchantInfo);
				this.SetActiveRuleListResVoToRedis(activeResultInfoList);
			}

			// 组装findActiveRuleListResVo
			// traversalid为了方便组装格式，为当前商户ID
			String traversalid = "";
			findActiveRuleListResVo.setReqId(activeSearchMerchantInfo
					.getReqId());
			findActiveRuleListResVo.setClientId(activeSearchMerchantInfo
					.getClientId());
			FindActiveRuleResVo findActiveRuleResVo = new FindActiveRuleResVo();
			findActiveRuleResVo.findActiveList = new ArrayList<FindActiveVo>();
			findActiveRuleListResVo.findActiveRuleResList = new ArrayList<FindActiveRuleResVo>();
			LogCvt.debug("商户对应活动数目" + activeResultInfoList.size());
			try {
				for (int i = 0; i < activeResultInfoList.size(); i++) {
					FindActiveVo findActiveVo = new FindActiveVo();
					ActiveResultInfo activeResultInfo = activeResultInfoList.get(i);
					// 当前商户ID不一致时，表示前一个商户活动信息已遍历完毕。
					if (!traversalid.equals(activeResultInfo.getId())) {
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
					if(RedisCommon.checkBaseGlobalLimit(
							activeSearchMerchantInfo.getClientId(), activeResultInfo.getActiveId())
							&& RedisCommon.checkGlobalLimit(
									activeSearchMerchantInfo.getClientId(), activeResultInfo.getActiveId(), null)) {
						LogCvt.debug("校验活动" + activeResultInfo.getActiveName() + "全局资格检查和检查客户是否有当日/小时资格");
						if(!Validator.isEmptyStr(activeSearchMerchantInfo.getMemberCode()) 
								&& RedisCommon.checkPersonLimit(
										activeSearchMerchantInfo.getClientId(), 
										activeResultInfo.getActiveId(), 
										Long.parseLong(activeSearchMerchantInfo.getMemberCode()))) {
							LogCvt.debug("校验个人 " + activeSearchMerchantInfo.getMemberCode() + " 活动资格资格");
							findActiveRuleResVo.findActiveList.add(findActiveVo);						
						} else {
							findActiveRuleResVo.findActiveList.add(findActiveVo);
						}					
					}									
				}
				
				// 添加商户信息到返回集中。
				if (!findActiveRuleListResVo.findActiveRuleResList
						.contains(findActiveRuleResVo)) {
					findActiveRuleListResVo.findActiveRuleResList
							.add(findActiveRuleResVo);
				}
			} catch (Exception e) {
				LogCvt.error("查找商户对应活动异常 " + e.getMessage(), e);
			}			
		}

		return findActiveRuleListResVo;
	}

	/**
	 * @Title: findActiveRuleFromRedis
	 * @Description: 处理从redis中获取的商户数据
	 * @author: shenshaocheng 2015年11月9日
	 * @modify: shenshaocheng 2015年11月9日
	 * @param activeSearchMerchantInfo
	 *            商户信息。
	 * @return
	 */
	private List<ActiveResultInfo> findActiveRuleFromRedis(
			ActiveSearchMerchantInfo activeSearchMerchantInfo) {
		List<ActiveResultInfo> activeResultInfoList = new ArrayList<ActiveResultInfo>();
		List<String> merchantIdList = activeSearchMerchantInfo
				.getMerchantIdList();
		for (int i = 0; i < merchantIdList.size(); i++) {
			ActiveResultInfo activeResultInfo = new ActiveResultInfo();
			Set<String> activeSet = redisManager
					.getSet(RedisKeyUtil.cbbank_active_merchant_merchant_id(merchantIdList.get(i)));
			// 遍历从Redis中获取的活动集合
			for (String activeInfo : activeSet) {
				if (activeInfo.indexOf(":") > 1) {
					activeResultInfo.setId(merchantIdList.get(i));
					activeResultInfo.setActiveName(activeInfo.split(":")[0]);
					activeResultInfo.setActiveType(activeInfo.split(":")[1]);
					activeResultInfo.setActiveId(activeInfo.split(":")[2]);
					activeResultInfoList.add(activeResultInfo);
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
	private void SetActiveRuleListResVoToRedis(
			List<ActiveResultInfo> activeResultInfoList) {
		for (ActiveResultInfo activeResultInfo : activeResultInfoList) {
			ActiveBaseRule activeBaseRule = new ActiveBaseRule();
			activeBaseRule.setActiveId(activeResultInfo.getActiveId());
			activeBaseRule.setActiveName(activeResultInfo.getActiveName());
			activeBaseRule.setType(activeResultInfo.getActiveType());
			SupportsRedis.set_cbbank_active_merchant_merchant_id(
					activeResultInfo.getId(), activeBaseRule);
			SupportsRedis.set_cbbank_active_merchant_info_active_id(
					activeResultInfo.getId(), activeBaseRule.getActiveId());			
		}
		
		// 使用了太多对象，强制进行垃圾回收。
		System.gc();
	}
}
