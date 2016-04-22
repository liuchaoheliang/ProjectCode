package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.FFTOrgRe;

/**
 * 
 * <p>@Title: FFTOrgReReMapper.java</p>
 * <p>Description: 描述 </p> 组织关系-权限组织Mapper
 * @author f-road-ll 
 * @version 1.0
 * @created 2016年1月6日
 */
public interface FFTOrgReMapper {


    /**
     * 增加 FFTOrgRe
     * @param fftOrgRe
     * @return Long    主键ID
     */
	public Long addFFTOrgRe(FFTOrgRe fftOrgRe);

	/**
     * 批量增加 FFTOrgRe
     * @param fftOrgReList
     * @return Boolean    是否成功
     */
	public Boolean addFFTOrgReByBatch(@Param("fftOrgReList") List<FFTOrgRe> fftOrgReList);
	

	/**
     * 删除 FFTOrgRe
     * @param orgId 组织Id
     * @return Boolean    是否成功
     */
	public Boolean deleteFFTOrgRe(String orgId);
	
	
	/**
     * 修改 FFTOrgRe
     * @param fftOrgRe
     * @return Boolean    是否成功
     */
	public Boolean updateFFTOrgRe(FFTOrgRe fftOrgRe);
	
	
	/**
     * 根据组织Id查询FFTOrgRe集合
     * @param id 主键Id
     * @return List<FFTOrgRe>    返回结果集
     */
	public List<FFTOrgRe> findFFTOrgReByOrgId(String orgId);
	
}