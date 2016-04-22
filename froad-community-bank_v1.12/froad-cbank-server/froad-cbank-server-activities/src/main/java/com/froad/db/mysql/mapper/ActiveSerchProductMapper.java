package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.ActiveResultInfo;
import com.froad.po.ActiveSearchProductInfo;
import com.froad.po.WeightActivityTag;

public interface ActiveSerchProductMapper {
	
	 /**
	  * @Title: findActiveProductInfo
	  * @Description: 获取商品信息
	  * @author: shenshaocheng 2015年11月7日
	  * @modify: shenshaocheng 2015年11月7日
	  * @param activeSearchProductInfo
	  * @return
	 */	
	public List<ActiveResultInfo> findActiveProductInfo(ActiveSearchProductInfo activeSearchProductInfo);
	
	/**
	 * 获取已经逾期的活动信息
	 * @param activeSearchProductInfo
	 * @return
	 */
	public List<ActiveResultInfo> findOverdueActivitiesByIds(@Param("activeIdList")List<String> activeIdList);
	
	 /**
	  * @Title: productIdSet
	  * @Description: 根据活动ID获取对应相关商品Id集合
	  * @author: shenshaocheng 2015年11月10日
	  * @modify: shenshaocheng 2015年11月10日
	  * @param activeId
	  * @return 商品ID集合
	 */
	public List<WeightActivityTag> findProductIdSet(
			@Param("activeId")String activeId,@Param("clientId")String clientId);
	
	 /**
	  * @Title: findProductIdSetByMerchant
	  * @Description: 根据活动ID获取相应商户的商品集合
	  * @author: shenshaocheng 2015年11月23日
	  * @modify: shenshaocheng 2015年11月23日
	  * @param activeId 活动ID
	  * @param clientId 客户端ID
	  * @return
	 */	
	public List<WeightActivityTag> findProductIdSetByMerchant(
			@Param("activeId")String activeId,@Param("clientId")String clientId);
	
	 /**
	  * @Title: findProductIdSetByOutlet
	  * @Description: 根据活动ID获取相应门店的商品集合
	  * @author: shenshaocheng 2015年11月23日
	  * @modify: shenshaocheng 2015年11月23日
	  * @param activeId
	  * @param clientId
	  * @return
	 */	
	public List<WeightActivityTag> findProductIdSetByOutlet(
			@Param("activeId")String activeId,@Param("clientId")String clientId);
}
