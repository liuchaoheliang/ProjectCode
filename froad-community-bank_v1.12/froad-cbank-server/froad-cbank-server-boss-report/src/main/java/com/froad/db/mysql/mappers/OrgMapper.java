package com.froad.db.mysql.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.Org;

public interface OrgMapper {

	/**
	 * 查询所有机构信息
	 * 
	 * @Title: findAllOrgInfo
	 * @Description: TODO
	 * @author: zachary.wang 2015年08月13日
	 */
	List<Org> findAllOrgInfo();
	
	/**
	 * 查询机构信息
	 */
	List<Org> findOrgList(@Param("clientId")String clientId, @Param("orgLevel")String orgLevel);
}
