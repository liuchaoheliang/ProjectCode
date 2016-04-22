/**
 * Project Name:froad-cbank-server-boss
 * File Name:FunctionModuleMapper.java
 * Package Name:com.froad.db.mysql.mapper
 * Date:2015年9月18日上午9:52:21
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.FunctionModule;

/**
 * ClassName:FunctionModuleMapper
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月18日 上午9:52:21
 * @author   kevin
 * @version  
 * @see 	 
 */
public interface FunctionModuleMapper {
	
	public List<FunctionModule> findListByCondition(@Param("clientId")String clientId);
}
