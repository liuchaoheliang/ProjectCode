package com.froad.db.redis;


public interface MemberProductLimitRedisService {
	
	/**
	 * 
	 * @Title: incrCount 
	 * @Description: 增加/减少count
	 * @author: froad-huangyihao 2015年4月23日
	 * @modify: froad-huangyihao 2015年4月23日
	 * @param clientId
	 * @param memberCode
	 * @param productId
	 * @param count
	 * @return
	 * @throws
	 */
	Long incrCount(String clientId, Long memberCode, String productId, Long count);
	
	/**
	 * 
	 * @Title: incrVipCount 
	 * @Description: 增加/减少vip_count
	 * @author: froad-huangyihao 2015年4月23日
	 * @modify: froad-huangyihao 2015年4月23日
	 * @param clientId
	 * @param memberCode
	 * @param productId
	 * @param vipCount
	 * @return
	 * @throws
	 */
	Long incrVipCount(String clientId, Long memberCode, String productId, Long vipCount);
	
	/**
	 * 
	 * @Title: resetCount 
	 * @Description: 重置count
	 * @author: froad-huangyihao 2015年4月23日
	 * @modify: froad-huangyihao 2015年4月23日
	 * @param clientId
	 * @param memberCode
	 * @param productId
	 * @param count
	 * @return
	 * @throws
	 */
	Long resetCount(String clientId, Long memberCode, String productId, Long count);
	
	/**
	 * 
	 * @Title: resetVipCount 
	 * @Description: 重置vip_count
	 * @author: froad-huangyihao 2015年4月23日
	 * @modify: froad-huangyihao 2015年4月23日
	 * @param clientId
	 * @param memberCode
	 * @param productId
	 * @param vipCount
	 * @return
	 * @throws
	 */
	Long resetVipCount(String clientId, Long memberCode, String productId, Long vipCount);
	
	/**
	 * 
	 * @Title: getCount 
	 * @Description: 获取count值
	 * @author: froad-huangyihao 2015年4月23日
	 * @modify: froad-huangyihao 2015年4月23日
	 * @param clientId
	 * @param memberCode
	 * @param productId
	 * @return
	 * @throws 
	 */
	Long getCount(String clientId, Long memberCode, String productId);
	
	/**
	 * 
	 * @Title: getVipCount 
	 * @Description: 获取vip_count值
	 * @author: froad-huangyihao 2015年4月23日
	 * @modify: froad-huangyihao 2015年4月23日
	 * @param clientId
	 * @param memberCode
	 * @param productId
	 * @return
	 * @throws 
	 */
	Long getVipCount(String clientId, Long memberCode, String productId);
}
