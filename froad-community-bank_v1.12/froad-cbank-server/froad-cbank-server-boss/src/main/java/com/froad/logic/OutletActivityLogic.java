/**
 * Project Name:froad-cbank-server-boss
 * File Name:BossOutletRecommendActivityLogic.java
 * Package Name:com.froad.logic
 * Date:2015年10月23日下午2:25:32
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic;



import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadBusinessException;
import com.froad.po.InputRelateOutletActivityPo;
import com.froad.po.OutletWeightActivityTag;
import com.froad.po.RecommendActivityTag;
import com.froad.po.RelateOutletActivityPo;
import com.froad.po.WeightActivityTag;

/**
 * ClassName:BossOutletRecommendActivityLogic
 * Reason:	 门店推荐活动标签管理
 * Date:     2015年10月23日 下午2:25:32
 * @author   liuyanyun
 * @version  
 * @see 	 
 */
public interface OutletActivityLogic {
	
	/**
	 * findOutletTagByPage(门店推荐活动列表)
	 * 
	 * @author liuyanyun
	 * 2015年10月23日 下午2:28:26
	 * @param recommend
	 * @param page
	 * @return 
	 * @throws Exception
	 */
	public Page<RecommendActivityTag> findOutletTagByPage(RecommendActivityTag recommend, Page<RecommendActivityTag> page) throws Exception;
	
	/**
	 * findOutletTagDetail:(商户推荐活动详情)
	 * 
	 * @author huangyihao
	 * 2015年10月23日 下午5:23:18
	 * @param id
	 * @param clientId
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	public RecommendActivityTag findOutletTagDetail(Long activityId, String clientId, String activityNo, String operator) throws FroadBusinessException, Exception;
	
	/**
	 * findRelateOutletInfoByPage:(关联门店信息分页查询).
	 *
	 * @author liuyanyun
	 * 2015年10月26日 上午10:13:25
	 * @param weightTag
	 * @param page
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	public Page<OutletWeightActivityTag> findRelateOutletInfoByPage(WeightActivityTag weightTag, Page<OutletWeightActivityTag> page) throws FroadBusinessException, Exception;
	
	/**
	 * enableOutletRecommendActivityTag:(启用/禁用门店推荐活动标签).
	 *
	 * @author liuyanyun
	 * 2015年10月26日 下午13:58:27
	 * @param recommend
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	public Boolean enableOutletRecommendActivityTag(RecommendActivityTag recommend) throws FroadBusinessException, Exception;
	
	/**
	 * adjustOutletWeight:(调整关联门店权重).
	 *
	 * @author liuyanyun
	 * 2015年10月26日 下午14:10:41
	 * @param weight
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	public Boolean adjustOutletWeight(WeightActivityTag weight) throws FroadBusinessException, Exception;
	
	/**
	 * addOutletActivityTag:(添加门店推荐活动).
	 *
	 * @author liuyanyun
	 * 2015年10月27日 上午10:49:27
	 * @param recommend
	 * @param relateOutlets
	 * @return
	 *
	 */
	public Boolean addOutletActivityTag(RecommendActivityTag recommend) throws Exception;
	
	/**
	 * deleteRelateOutlet:(删除关联门店).
	 *
	 * @author liuyanyun
	 * 2015年10月27日 下午15:11:02
	 * @param id
	 * @param clientId
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	public Boolean deleteRelateOutlet(WeightActivityTag weightTag) throws FroadBusinessException, Exception;
	
	/**
	 * updateOutletActivityTag:(更新门店推荐活动标签信息).
	 *
	 * @author liuyanyun
	 * 2015年10月27日 下午15:15:10
	 * @param recommend
	 * @return
	 * @throws Exception
	 *
	 */
	public Boolean updateOutletActivityTag(RecommendActivityTag recommend) throws Exception;
	
	/**
	 * relateOutletInfo:(关联门店信息).
	 *
	 * @author liuyanyun
	 * 2015年10月27日 下午15:34:46
	 * @param po
	 * @return
	 * @throws Exception
	 *
	 */
	public Boolean relateOutletInfo(RelateOutletActivityPo po) throws FroadBusinessException, Exception;
	
	/**
	 * 
	 * inputrelateOutletInfo:(导入关联门店信息).
	 *
	 * @author liuyanyun
	 * 2015年10月28日 下午14:49:25
	 * @param infos
	 * @param clientId
	 * @param activityNo
	 * @param operator
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	public Boolean inputrelateOutletInfo(List<InputRelateOutletActivityPo> infos, String clientId, Long activityId, String activityNo, String operator) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * queryMerchantNameByLicense:(根据营业执照查询商户名称).
	 *
	 * @author huangyihao
	 * 2015年10月27日 下午4:29:32
	 * @param license
	 * @return
	 *
	 */
	public String queryOutletNameAndMerchantNameByOutletId(String clientId, String license) throws FroadBusinessException, Exception;

	
	/**
	 * 
	 * findAllRelateOutlets:(这里用一句话描述这个方法的作用).
	 *
	 * @author huangyihao
	 * 2016年1月12日 上午11:50:35
	 * @param activityId
	 * @param clientId
	 * @param activityNo
	 * @return
	 *
	 */
	public List<WeightActivityTag> findAllRelateOutlets(Long activityId, String clientId, String activityNo) throws Exception;
	
}
