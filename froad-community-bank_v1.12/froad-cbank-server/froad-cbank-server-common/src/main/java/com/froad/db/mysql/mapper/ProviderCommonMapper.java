/**
 * Project Name:froad-cbank-server-common-1.8.0-SNAPSHOT
 * File Name:ProviderCommonMapper.java
 * Package Name:com.froad.db.mysql.mapper
 * Date:2015年11月26日上午10:46:05
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.Provider;

/**
 * ClassName:ProviderCommonMapper
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月26日 上午10:46:05
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public interface ProviderCommonMapper {

	/**
	 * 
	 * findByMerchantId:(根据供应商ID查询).
	 *
	 * @author huangyihao
	 * 2015年11月26日 上午10:47:51
	 * @param merchantId
	 * @return
	 *
	 */
	public Provider findByMerchantId(@Param("merchantId")String merchantId);
	
	
	/**
	 * 
	 * findAllProviders:(查询所有供应商).
	 *
	 * @author huangyihao
	 * 2015年12月2日 下午4:28:42
	 * @return
	 *
	 */
	public List<Provider> findAllProviders();
	
	
}
