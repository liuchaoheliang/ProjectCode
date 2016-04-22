package com.froad.handler;

import java.util.List;
import java.util.Set;

import com.froad.po.ActiveResultInfo;
import com.froad.po.ActiveSearchProductInfo;
import com.froad.po.WeightActivityTag;

public interface ActiveSearchProductHandler {
	 /**
	  * @Title: findActiveRuleByMerchantIds
	  * @Description: 查询商品活动信息数据逻辑接口.
	  * @author: shenshaocheng 2015年11月6日
	  * @modify: shenshaocheng 2015年11月6日
	  * @param activeSearchMerchantInfo 需要查询的商品信息
	  * @return
	 */	
	public List<ActiveResultInfo> findActiveRuleByProductIds(
			ActiveSearchProductInfo activeSearchProductInfo);
	
	 /**
	  * @Title: findProductIdsByactiveId
	  * @Description: 根据活动Id获取相关所有商品的ID
	  * @author: shenshaocheng 2015年11月10日
	  * @modify: shenshaocheng 2015年11月10日
	  * @param activeId 活动ID
	  * @param clientId 客户端ID
	  * @return
	 */	
	public List<WeightActivityTag> findProductIdsByactiveId(String activeId, String clientId);
	
	 /**
	  * @Title: findProductIdsByMerchant
	  * @Description: 根据商户找到参加活动的所有商品
	  * @author: shenshaocheng 2015年11月23日
	  * @modify: shenshaocheng 2015年11月23日
	  * @param activeId 活动ID
	  * @param clientId 客户端ID
	  * @return
	 */	
	public List<WeightActivityTag> findProductIdsByMerchant(String activeId, String clientId);
	
	 /**
	  * @Title: findProductIdsByOutlet
	  * @Description: 根据门店找到所有参加活动的商品
	  * @author: shenshaocheng 2015年11月23日
	  * @modify: shenshaocheng 2015年11月23日
	  * @param activeId 活动ID
	  * @param clientId 客户端ID
	  * @return
	 */	
	public List<WeightActivityTag> findProductIdsByOutlet(String activeId, String clientId);
	
	/**
	 * 获取已经逾期的活动信息
	 * @param activeIdList
	 * @return
	 */
	public List<ActiveResultInfo> findOverdueActivitiesByIds(
			List<String> activeIdList);
}
