package com.froad.logic;

import java.util.List;

import com.froad.exceptions.FroadServerException;
import com.froad.po.FFTUserRole;

/**
 * 
 * <p>@Title: FFTUserRoleLogic.java</p>
 * <p>Description: 描述 </p>用户角色关系Logic接口
 * @author f-road-ll 
 * @version 1.0
 * @created 2016年1月5日
 */
public interface FFTUserRoleLogic {


    /**
     * 增加 FFTUserRole
     * @param FFTUserRole
     * @return Long    主键ID
     */
	public Long addFFTUserRole(Long userId, List<String> orgIds, List<Long> roleIds) throws FroadServerException, Exception;


	/**
     * 修改FFTUserRole
     * @param FFTUserRole
     * @return Boolean    是否成功
     */
	public Boolean updateFFTUserRole(Long userId, List<String> orgIds, List<Long> roleIds) throws FroadServerException, Exception;

    /**
     * 删除 FFTUserRole
     * @param userId
     * @return Boolean    是否成功
     */
	public Boolean deleteFFTUserRole(Long userId) throws FroadServerException, Exception;

	

	/**
     * 用户角色列表
     * @param userId 用户Id
     * @return List<FFTUserRole>    结果集合 
     */
	public List<FFTUserRole> findFFTUserRoleByUserId(Long userId) throws Exception;
	
	

}