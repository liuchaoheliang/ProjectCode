package com.froad.db.mysql.mapper;

import com.froad.db.mysql.po.RoleResource;

public interface RoleResourceMapper {
    int insert(RoleResource record);

    int insertSelective(RoleResource record);
}