package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.FFTOrg;
import com.froad.po.OrgRole;

/**
 * 
 * <p>@Title: OrgRoleMapper.java</p>
 * <p>Description: 描述 </p> 组织角色Mapper
 * @author f-road-ll 
 * @version 1.0
 * @created 2016年1月6日
 */
public interface OrgRoleMapper {


    /**
     * 增加 OrgRole
     * @param orgRole
     * @return Long    主键ID
     */
	public Long addOrgRole(OrgRole orgRole);

	/**
     * 批量增加 OrgRole
     * @param orgRoleList
     * @return Boolean    是否成功
     */
	public Boolean addOrgRoleByBatch(@Param("orgRoleList") List<OrgRole> orgRoleList);


    /**
     * 删除 OrgRole 根据组织Id
     * @param orgId 组织Id
     * @return Boolean    是否成功
     */
	public Boolean deleteOrgRoleByOrgId(String orgId);

	/**
     * 删除 OrgRole 根据角色Id
     * @param roleId 角色Id
     * @return Boolean    是否成功
     */
	public Boolean deleteOrgRoleByRoleId(Long roleId);
	
	/**
     * 根据组织orgId查询OrgRole集合
     * @param id 主键Id
     * @return List<OrgRole>    返回结果集
     */
	public List<OrgRole> findOrgRoleByOrgId(String orgId);
	
	
	/**
     * 根据组织id集合查询组织角色
     * @param ids 主键id集合
     * @return 数量
     */
	public List<OrgRole> findOrgRoleByOrgIds(@Param("orgIds") List<String> orgIds);

	
	
	/**
     * OrgRole 查询
     * @param id 主键Id
     * @return List<OrgRole>    返回结果集
     */
	public List<OrgRole> findOrgRole(OrgRole orgRole);
	
}