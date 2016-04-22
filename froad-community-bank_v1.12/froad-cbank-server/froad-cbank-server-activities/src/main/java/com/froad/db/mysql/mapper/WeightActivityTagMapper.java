package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.WeightActivityTag;

/**
 * @Title: findProductByMerchantId
 * @Description: 通过商户ID找所有的该商户下的产品
 * @author: yefeifei 2015年11月20日
 * @modify: yefeifei 2015年11月20日
 * @param page，activeRuleInfo
 * @return
*/
public interface WeightActivityTagMapper {
	/**
	 * @Title: findWeightActivityTagByActivityId
	 * @Description: 通过activityId，activityType， clientId查找权重表
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param activityId，activityType， clientId
	 * @return
	*/
	public List<WeightActivityTag> findWeightActivityTagByActivityId(@Param("activityId") String activityId, @Param("activityType") String activityType, @Param("clientId") String clientId);
	/**
	 * @Title: findAllWeightActivityTag
	 * @Description: 通过clientId查找权重表
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param clientId
	 * @return
	*/
	public List<WeightActivityTag> findAllWeightActivityTag(@Param("clientId") String clientId);
	/**
	 * @Title: findAllProductIdByItemType
	 * @Description: 通过itemType，clientId查找权重表的产品ID
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param itemType，clientId
	 * @return
	*/
	public List<String> findAllProductIdByItemType(@Param("itemType") String itemType, @Param("clientId") String clientId);
	/**
	 * @Title: findActive
	 * @Description: 查找权重表
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param clientId，itemType， itemId，activeId
	 * @return
	*/
	public int findActive(@Param("clientId") String clientId, @Param("itemType") String itemType, @Param("itemId") String itemId, @Param("activeId") String activeId);
	/**
	 * @Title: countWeightActivityTag
	 * @Description: 查找权重表
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param activityId，activityType， clientId
	 * @return
	*/
	public int countWeightActivityTag(@Param("activityId") String activityId, @Param("activityType") String activityType, @Param("clientId") String clientId);
	
}
