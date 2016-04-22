package com.froad.db.redis;

import java.util.Set;

public interface TimePaymentRedisService {
	
	/**
	 * 获取缓存cbbank_time_payment中的所有支付单
	 * @Title: getPaymentInfo 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年4月23日
	 * @modify: froad-huangyihao 2015年4月23日
	 * @return
	 * @throws
	 */
	Set<String> getPaymentInfo();
}
