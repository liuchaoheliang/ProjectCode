package com.froad.db.redis;


public interface MemberHistoryVipRedisService {
	
	/**
	 * 
	 * @Title: decrVipDiscount 
	 * @Description: 减少会员历史订单VIP优惠金额
	 * @author: froad-huangyihao 2015年4月25日
	 * @modify: froad-huangyihao 2015年4月25日
	 * @param clientId
	 * @param memberCode
	 * @param vipDiscount
	 * @return
	 * @throws
	 */
	Long decrVipDiscount(String clientId, Long memberCode, Long vipDiscount);
	
	/**
	 * 
	 * @Title: getVipDiscount 
	 * @Description: 获取会员vip优惠金额
	 * @author: froad-huangyihao 2015年4月25日
	 * @modify: froad-huangyihao 2015年4月25日
	 * @param clientId
	 * @param memberCode
	 * @return
	 * @throws 
	 */
	Long getVipDiscount(String clientId, Long memberCode);
	
	/**
	 * 
	 * @Title: resetVipDiscount 
	 * @Description: 重置会员vip优惠金额
	 * @author: froad-huangyihao 2015年4月25日
	 * @modify: froad-huangyihao 2015年4月25日
	 * @param clientId
	 * @param memberCode
	 * @param vipDiscount
	 * @return
	 * @throws
	 */
	Long resetVipDiscount(String clientId, Long memberCode, Long vipDiscount);
	
	/**
	 * 
	 * @Title: updateUserVIPOrderRedis 
	 * @Description: 更新用户VIP购买记录
	 * @author: froad-zhangkai 2015年7月23日
	 * @modify: froad-zhangkai 2015年7月23日
	 * @param key
	 * @param isSuccess
	 * @return
	 * @throws
	 */
	Boolean updateUserVIPOrderRedis(String key, String isSuccess);
}
