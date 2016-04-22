package com.froad.db.mongo;

public interface OrderMongoService {

	/**
	 * @Description: 查询是否存在此会员编号的订单
	 * @author: lf 2016年2月24日
	 * @return true存在 - false不存在
	 */
	boolean isExistOrder(Long memberCode, String clientId);
}
