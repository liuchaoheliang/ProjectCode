package com.froad.handler;

import java.util.List;

import com.froad.po.ActiveResultInfo;
import com.froad.po.ActiveSearchMerchantInfo;

public interface ActiveSearchMerchantHandler {
	 /**
	  * @Title: findActiveRuleByMerchantIds
	  * @Description: 查询商户活动信息数据逻辑接口.
	  * @author: shenshaocheng 2015年11月6日
	  * @modify: shenshaocheng 2015年11月6日
	  * @param activeSearchMerchantInfo 需要查询的商户信息
	  * @return
	 */	
	public List<ActiveResultInfo> findActiveRuleByMerchantIds(
			ActiveSearchMerchantInfo activeSearchMerchantInfo);
}
