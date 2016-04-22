package com.froad.db.bps.mappers;

import java.util.List;

import com.froad.db.bps.entity.Role;

public interface RoleMapper {
	
	List<Role> selectAllRoles();
}
