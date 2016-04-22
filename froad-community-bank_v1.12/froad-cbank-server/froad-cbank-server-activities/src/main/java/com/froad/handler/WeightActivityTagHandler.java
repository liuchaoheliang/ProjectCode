package com.froad.handler;

import java.util.List;

import com.froad.po.WeightActivityTag;

public interface WeightActivityTagHandler {
	/**
	 * @Title: findWeightActivityTagByActivityIdAndActivityType
	 * @Description: 通过商户ID获得商品列表
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param activityId，activityType，clientId
	 * @return
	*/
	public List<WeightActivityTag> findWeightActivityTagByActivityIdAndActivityType(String activityId, String activityType, String clientId);
	/**
	 * @Title: findAllWeightActivityTag
	 * @Description: 通过客户端获得权重列表
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param clientId
	 * @return
	*/	
	public List<WeightActivityTag> findAllWeightActivityTag(String clientId);
	/**
	 * @Title: findActive
	 * @Description: 通过itemType，itemId，activeId获得活动
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param itemType，itemId，activeId
	 * @return
	*/	
	public int findActive(String clientId, String itemType, String itemId, String activeId);
	
}
