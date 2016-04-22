/**
 * Project Name:froad-cbank-server-boss
 * File Name:MerchantMapper.java
 * Package Name:com.froad.db.mysql.mapper
 * Date:2015年10月27日上午11:06:06
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.Merchant;

/**
 * ClassName:MerchantMapper
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月27日 上午11:06:06
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public interface MerchantMapper {
	
	/**
	 * 
	 * queryMerchantByLicense:(根据商户营业执照号查询).
	 *
	 * @author huangyihao
	 * 2015年10月27日 上午11:07:00
	 * @param license
	 * @return
	 *
	 */
	List<Merchant> queryMerchantByLicense(@Param("license")String license);
	
	/**
	 * 
	 * queryMerchantById:(根据商户id查询).
	 *
	 * @author liuyanyun
	 * 2015年10月28日 下午18:13:00
	 * @param id
	 * @return
	 *
	 */
	Merchant queryMerchantById(@Param("id")String id);
	
	
}
