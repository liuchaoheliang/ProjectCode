package com.froad.logic;

import java.util.HashMap;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.FFTOrg;
import com.froad.po.FFTOrgRe;
import com.froad.po.OrgRole;

/**
 * 
 * <p>@Title: FFTOrgLogic.java</p>
 * <p>Description: 描述 </p> 组织Logic接口
 * @author f-road-ll 
 * @version 1.0
 * @created 2016年1月5日
 */
public interface FFTOrgLogic {


    /**
     * 增加 FFTOrg
     * @param fftOrg
     * @param roleIds 角色分配
     * @param reOrgIds 权限组织配置
     * @return Long    主键ID
     */
	public Long addFFTOrg(FFTOrg fftOrg,List<Long> roleIds, List<String> reOrgIds) throws FroadServerException, Exception;


	/**
     * 修改FFTOrg
     * @param fftOrg
     * @return Boolean    是否成功
     */
	public Boolean updateFFTOrg(FFTOrg fftOrg,List<Long> roleIds, List<String> reOrgIds) throws FroadServerException, Exception;

    /**
     * 删除 FFTOrg
     * @param id
     * @return Boolean    是否成功
     */
	public Boolean deleteFFTOrg(Long id) throws FroadServerException, Exception;

	/**
     * 组织详情
     * @param id 主键Id
     * @return FFTOrg 结果对象
     */
	public FFTOrg findFFTOrgDetail(Long id) throws Exception;
	
	/**
     * 查询 FFTOrg  by 组织orgId
     * @param fftOrg
     * @return List<FFTOrg>    结果集合 
     */
	public FFTOrg findFFTOrgByOrgId(String orgId) throws Exception ;

	/**
     * 组织列表拉取-查全部
     * @param userId 用户Id
     * @return List<FFTOrg>    结果集合 
     */
	public List<FFTOrg> findFFTOrgByUserId(Long userId) throws Exception;
	
	/**
     * 组织列表拉取-查顶级2级
     * @param userId 用户Id
     * @return List<FFTOrg>    结果集合 
     */
	public List<FFTOrg> findFFTOrgByUserIdTwoLevel(Long userId) throws Exception;
	
	/**
     * 组织列表拉取-查用户下该组织id下一级的权限组织
     * @param userId 用户Id
     * @param orgId 组织Id
     * @return List<FFTOrg>    结果集合 
     */
	public List<FFTOrg> findFFTOrgByUserIdOrgId(Long userId,String orgId) throws Exception;
	
	/**
     * 验证组织下是否有下级组织
     * @param userId 用户Id
     * @param id集合，组织表主键id集合
     * @return HashMap<Long, Boolean>    结果集合 
     */
	public HashMap<Long, Boolean> isNextFFTOrg(Long userId,List<Long> ids) throws Exception;
	
	
    /**
     * 查询 FFTOrg
     * @param fftOrg
     * @return List<FFTOrg>    结果集合 
     */
	public List<FFTOrg> findFFTOrgByList(FFTOrg fftOrg) throws Exception;

	/**
     * 组织ID多集合查询
     * @param orgIds 组织Id集合
     * @return List<FFTOrg>    结果集合 
     */
	public List<FFTOrg> findFFTOrgByOrgIds(List<String> orgIds) throws Exception;
	
	/**
     * 主键ID多集合查询
     * @param ids 主键id集合
     * @return List<FFTOrg>    结果集合 
     */
	public List<FFTOrg> findFFTOrgByIds(List<Long> ids) throws Exception;
	
	/**
     * 银行渠道列表(获取银行组织的一级)
     * @param userId 用户Id
     * @return List<FFTOrg>    结果集合 
     */
	public List<FFTOrg> findFFTOrgInOneByBank(Long userId) throws Exception;
	
	/**
	 * 所属组织-数据权限下拉框
	 * @param userId 用户Id
	 * @param clientId 客户端Id
     * @return List<FFTOrg>    结果集合 
     */
	public List<FFTOrg> findFFTOrgByUserIdPlatform(Long userId,String clientId) throws Exception;
	
	
	/**
     * 分页查询 FFTOrg
     * @param page
     * @param fftOrg
     * @return Page    结果集合 
     */
	public Page<FFTOrg> findFFTOrgByPage(Page<FFTOrg> page, FFTOrg fftOrg) throws Exception;

	
	/**
     * 组织数据权限查询
     * @param orgId 组织Id
     * @return List<FFTOrgRe>    结果集合 
     */
	public List<FFTOrgRe> findFFTOrgReByOrgId(String orgId) throws Exception;
	
	
	/**
     * 组织角色查询
     * @param orgId 组织Id
     * @return List<OrgRole>    结果集合 
     */
	public List<OrgRole> findOrgRoleByOrgId(String orgId) throws Exception;
	
	
	/**
     * 组织角色多集合查询
     * @param orgId 组织Id集合
     * @return List<OrgRole>    结果集合 
     */
	public List<OrgRole> findOrgRoleByOrgIds(List<String> orgIds) throws Exception;
	

}