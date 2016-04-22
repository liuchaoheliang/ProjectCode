/**
 * Project Name:froad-cbank-server-boss
 * File Name:BusinessZoneTagMapper.java
 * Package Name:com.froad.db.mysql.mapper
 * Date:2015年10月22日下午3:44:28
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.BusinessZoneTag;
import com.froad.po.BusinessZoneTagDto;
import com.froad.po.BusinessZoneTagOutlet;

/**
 * ClassName:BusinessZoneTagMapper
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月22日 下午3:44:28
 * @author   kevin
 * @version  
 * @see 	 
 */
public interface BusinessZoneTagMapper {
	
	
	public int insert(BusinessZoneTag tag);
	
	
	public int update(@Param("tag") BusinessZoneTag tag);
	
	
	public List<BusinessZoneTagDto> findByPage(Page page, @Param("tag") BusinessZoneTag tag);
	
	public List<BusinessZoneTag> findExportListByCondition( @Param("tag") BusinessZoneTag tag); 
	
	
	public int findByConditionCount(@Param("tag") BusinessZoneTag tag);
	
	
	public BusinessZoneTag findById(Long id);
	
	
	//public List<BusinessZoneTagOutlet> findTagOutletList(Long id);
	
	
}
