package com.froad.logic;

import java.util.List;
import com.froad.exceptions.FroadServerException;
import com.froad.po.FFTUserOrg;

/**
 * 
 * <p>@Title: FFTUserOrgLogic.java</p>
 * <p>Description: 描述 </p>用户组织关系Logic接口
 * @author f-road-ll 
 * @version 1.0
 * @created 2016年1月5日
 */
public interface FFTUserOrgLogic {


    /**
     * 增加 FFTUserOrg
     * @param FFTUserOrg
     * @return Long    主键ID
     */
	public Long addFFTUserOrg(FFTUserOrg fftUserOrg) throws FroadServerException, Exception;


	/**
     * 修改FFTUserOrg
     * @param FFTUserOrg
     * @return Boolean    是否成功
     */
	public Boolean updateFFTUserOrg(FFTUserOrg fftUserOrg) throws FroadServerException, Exception;

	
    /**
     * 删除 FFTUserOrg
     * @param userId
     * @return Boolean    是否成功
     */
	public Boolean deleteFFTUserOrg(Long userId) throws FroadServerException, Exception;


	/**
     * 用户所属组织列表
     * @param userId 用户Id
     * @return List<String>    orgId结果集合 
     */
	public List<String> findFFTUserOrgByUserId(Long userId) throws Exception;
	
	

}