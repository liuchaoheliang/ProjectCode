package com.froad.logic;

import com.froad.po.ActiveSearchMerchantInfo;
import com.froad.thrift.vo.active.FindActiveRuleListResVo;

public interface ActiveSearchMerchanLogic {

	 /**
	  * @Title: findActiveRuleByMerchantIds
	  * @Description: 根据商户ID查找相关商户活动信息业务逻辑接口.
	  * @author: shenshaocheng 2015年11月6日
	  * @modify: shenshaocheng 2015年11月6日
	  * @param activeSearchMerchantInfo
	  * @return
	 */	
	public FindActiveRuleListResVo findActiveRuleByMerchantIds(
			ActiveSearchMerchantInfo activeSearchMerchantInfo);
	
}
