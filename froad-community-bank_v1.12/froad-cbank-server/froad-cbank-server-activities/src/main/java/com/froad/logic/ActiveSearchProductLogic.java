package com.froad.logic;

import java.util.List;

import com.froad.po.ActiveResultInfo;
import com.froad.po.ActiveSearchProductInfo;
import com.froad.thrift.vo.active.FindActiveRuleListResVo;

public interface ActiveSearchProductLogic {

	 /**
	  * @Title: findActiveRuleByMerchantIds
	  * @Description: 根据商品ID查找相关商户活动信息业务逻辑接口.
	  * @author: shenshaocheng 2015年11月6日
	  * @modify: shenshaocheng 2015年11月6日
	  * @param activeSearchMerchantInfo
	  * @return
	 */	
	public FindActiveRuleListResVo findActiveRuleByProductIds(
			ActiveSearchProductInfo activeSearchProductInfo);
	
	/**
	 * 清理逾期活动的Redis信息
	 * @param activeIdList 活动ID
	 * @param isOverdue 存入活动ID是否已经逾期
	 * @return 清理数量
	 */
	public List<ActiveResultInfo> delOverdueActivitiesRedisByActiveId(List<String> activeIdList, Boolean isOverdue);
}
