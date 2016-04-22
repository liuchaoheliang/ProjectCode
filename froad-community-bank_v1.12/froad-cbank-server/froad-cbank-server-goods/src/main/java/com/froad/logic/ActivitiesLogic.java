package com.froad.logic;

import com.froad.thrift.vo.ResultVo;

/**
 * 调用满减活动接口（activities模块）
 * @author wangyan
 *
 */
public interface ActivitiesLogic {
	
	/**
	 * 参加满减活动的商品需要调用满减通知接口
	 * @param clientId
	 * @param productId
	 */
	public ResultVo putFullCut(String clientId,String productId) throws Exception;
}
