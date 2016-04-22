package com.froad.db.mysql.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.froad.po.FFTUserRole;

/**
 * 
 * <p>@Title: FFTUserRoleMapper.java</p>
 * <p>Description: 描述 </p> 用户角色Mapper
 * @author f-road-ll 
 * @version 1.0
 * @created 2016年1月6日
 */
public interface FFTUserRoleMapper {


    /**
     * 增加 FFTUserRole
     * @param fftUserRole
     * @return Long    主键ID
     */
	public Long addFFTUserRole(FFTUserRole fftUserRole);


	/**
     * 批量增加 FFTUserRole
     * @param fftUserRoleList
     * @return Boolean    是否成功
     */
	public Boolean addFFTUserRoleByBatch(@Param("fftUserRoleList") List<FFTUserRole> fftUserRoleList);
	
	
    /**
     * 删除 FFTUserRole 根据用户Id
     * @param userId 用户Id
     * @return Boolean    是否成功
     */
	public Boolean deleteFFTUserRoleByUserId(Long userId);

	/**
     * 删除 FFTUserRole 根据角色Id
     * @param roleId 角色Id
     * @return Boolean    是否成功
     */
	public Boolean deleteFFTUserRoleByRoleId(Long roleId);
	
	/**
     * 删除 FFTUserRole 根据组织Id
     * @param orgId 组织Id
     * @return Boolean    是否成功
     */
	public Boolean deleteFFTUserRoleByOrgId(String orgId);
	
	
	/**
     * 根据组织Id查询出该组织下用户继承得来的角色信息
     * @param orgId 组织Id
     * @param source 0-继承自组织 1-用户直接分配
     * @return List<FFTUserRole>    返回结果集
     */
	public List<FFTUserRole> findFFTUserRoleByOrgId(@Param("orgId") String orgId,@Param("source") Integer source);
	
	
	/**
     * 根据用户Id查询用户角色信息
     * @param id 主键Id
     * @return List<FFTUserRole>    返回结果集
     */
	public List<FFTUserRole> findFFTUserRoleByUserId(Long userId);
	
	
	/**
     * 根据用户Id查询对应平台platform下的用户角色信息
     * @param userId 用户Id
     * @param platform 平台
     * @return List<FFTUserRole>    返回结果集
     */
	public List<FFTUserRole> findFFTUserRoleByUserIdPlatform(@Param("userId") Long userId,@Param("platform") String platform);
	
	
	

}