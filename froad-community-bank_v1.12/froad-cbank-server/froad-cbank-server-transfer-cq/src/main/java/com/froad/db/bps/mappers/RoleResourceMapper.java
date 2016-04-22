package com.froad.db.bps.mappers;

import java.util.List;

public interface RoleResourceMapper {
	
	List<Long> dinstinctRoleId();
	
	List<String> queryResourcesByRoleId(Long roleId);
}
