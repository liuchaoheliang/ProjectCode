package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.FFTUserOrg;

/**
 * 
 * <p>@Title: FFTUserOrgMapper.java</p>
 * <p>Description: 描述 </p> 用户组织Mapper
 * @author f-road-ll 
 * @version 1.0
 * @created 2016年1月6日
 */
public interface FFTUserOrgMapper {


    /**
     * 增加 FFTUserOrg
     * @param fftUserOrg
     * @return Long    主键ID
     */
	public Long addFFTUserOrg(FFTUserOrg fftUserOrg);


	/**
     * 批量增加 FFTUserOrg
     * @param fftUserOrgList
     * @return Boolean    是否成功
     */
	public Boolean addFFTUserOrgByBatch(@Param("fftUserOrgList") List<FFTUserOrg> fftUserOrgList);

	

    /**
     * 删除 FFTUserOrg
     * @param userId 用户Id
     * @return Boolean    是否成功
     */
	public Boolean deleteFFTUserOrgByUserId(Long userId);

	/**
     * 删除 FFTUserOrg
     * @param orgId 组织Id
     * @return Boolean    是否成功
     */
	public Boolean deleteFFTUserOrgByOrgId(String orgId);
	
	
	/**
     * 根据用户Id查询用户组织信息
     * @param userId 用户Id
     * @return List<FFTUserOrg>    返回结果集
     */
	public List<FFTUserOrg> findFFTUserOrgByUserId(Long userId);
	
	
	/**
     * 根据组织Id查询用户组织信息
     * @param orgId 组织Id
     * @return List<FFTUserOrg>    返回结果集
     */
	public List<FFTUserOrg> findFFTUserOrgByOrgId(String orgId);
	
	
	/**
     * 根据组织Id查询有效用户的用户组织信息
     * @param orgId 组织Id
     * @return List<FFTUserOrg>    返回结果集
     */
	public List<FFTUserOrg> findFFTUserOrgByOrgIdStatus(String orgId);
	
}