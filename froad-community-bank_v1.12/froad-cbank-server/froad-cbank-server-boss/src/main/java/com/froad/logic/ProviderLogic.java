/**
 * Project Name:froad-cbank-server-boss-1.8.0-SNAPSHOT
 * File Name:ProviderLogic.java
 * Package Name:com.froad.logic
 * Date:2015年12月2日下午4:23:41
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic;

import java.util.List;

import com.froad.po.Provider;

/**
 * ClassName:ProviderLogic
 * Reason:	 TODO ADD REASON.
 * Date:     2015年12月2日 下午4:23:41
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public interface ProviderLogic {
	
	/**
	 * 
	 * findAllProviders:(查询所有的供应商).
	 *
	 * @author huangyihao
	 * 2015年12月2日 下午4:25:19
	 * @return
	 *
	 */
	public List<Provider> findAllProviders() throws Exception;
	
}
