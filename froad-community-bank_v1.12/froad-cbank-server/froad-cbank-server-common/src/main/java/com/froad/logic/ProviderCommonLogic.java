/**
 * Project Name:froad-cbank-server-common-1.8.0-SNAPSHOT
 * File Name:ProviderCommonLogic.java
 * Package Name:com.froad.logic
 * Date:2015年11月26日上午10:55:28
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic;

import com.froad.po.Provider;

/**
 * ClassName:ProviderCommonLogic
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月26日 上午10:55:28
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public interface ProviderCommonLogic {

	/**
	 * 
	 * findByMerchantId:(根据供运商ID查询信息).
	 *
	 * @author huangyihao
	 * 2015年11月26日 上午10:56:30
	 * @param merchantId
	 * @return
	 *
	 */
	public Provider findByMerchantId(String merchantId);
	
}
