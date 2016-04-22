package com.froad.db.bps.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.bps.entity.Resource;

public interface ResourceMapper {
	
	List<Resource> selectAllResources();
	
	Resource selectByResourceId(@Param("resourceId")Long resourceId);
	
}
