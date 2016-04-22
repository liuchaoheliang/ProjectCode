package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.FFTOrg;

/**
 * 
 * <p>@Title: FFTOrgMapper.java</p>
 * <p>Description: 描述 </p> 组织Mapper
 * @author f-road-ll 
 * @version 1.0
 * @created 2016年1月6日
 */
public interface FFTOrgMapper {


    /**
     * 增加 FFTOrg
     * @param fftOrg
     * @return Long    主键ID
     */
	public Long addFFTOrg(FFTOrg fftOrg);



    /**
     * 删除 FFTOrg
     * @param id 主键Id
     * @return Boolean    是否成功
     */
	public Boolean deleteFFTOrg(Long id);

	
	/**
     * 修改 FFTOrg
     * @param fftOrg
     * @return Boolean    是否成功
     */
	public Boolean updateFFTOrg(FFTOrg fftOrg);
	
	/**
     * 查询单个 FFTOrg
     * @param fftOrg
     * @return FFTOrg对象
     */
	public FFTOrg findOnlyFFTOrg(FFTOrg fftOrg);
	
	/**
     * 根据主键Id查询FFTOrg
     * @param id 主键Id
     * @return FFTOrg对象
     */
	public FFTOrg findFFTOrgById(Long id);
	
	/**
     * 根据主键Id查询FFTOrg
     * @param orgId 组织Id
     * @return FFTOrg对象
     */
	public FFTOrg findFFTOrgByOrgId(String orgId);
	
	
	/**
     * 查询id下的子组织信息
     * @param id 主键id
     * @return FFTOrg集合对象
     */
	public List<FFTOrg> findChildList(Long id);
	
	/**
     * 查询多集合id下的子组织信息
     * @param treePathSql sql组装
     * @return FFTOrg集合对象
     */
	public List<FFTOrg> findChildListByIds(@Param("ids") List<Long> ids);
	
	
	/**
     * 查询id下的子组织数量
     * @param ids 主键id集合
     * @return 数量
     */
	public List<FFTOrg> findChildListCount(@Param("ids") List<Long> ids);
	
	/**
     * 查询用户下可查看的boss权限组织数量
     * @param userId 用户Id
     * @param id 主键Id
     * @return FFTOrg集合对象
     */
	public List<FFTOrg> findFFTOrgReByBossUserIdCount(@Param("userId") Long userId,@Param("ids") List<Long> ids);
	
	
	/**
     * 查询用户下可查看的对应平台的权限组织列表
     * @param userId 用户Id
     * @param platform 平台
     * @param clientId 客户端Id
     * @return FFTOrg集合对象
     */
	public List<FFTOrg> findFFTOrgReByUserId(@Param("userId") Long userId,@Param("platform") String platform,@Param("clientId") String clientId);
	
	/**
     * 查询用户下可查看的boss权限组织列表-查顶级2级
     * @param userId 用户Id
     * @return FFTOrg集合对象
     */
	public List<FFTOrg> findFFTOrgReByBossUserIdTwoLevel(Long userId);
	
	/**
     * 查询用户下可查看的组织列表-查顶级2级
     * @param userId 用户Id
     * @return FFTOrg集合对象
     */
	public List<FFTOrg> findFFTOrgReByUserIdTwoLevel();
	
	
	/**
     * 查询用户下可查看的boss权限组织列表-查用户下该组织id下一级的权限组织
     * @param userId 用户Id
     * @param id 主键Id(orgId组织对应的主键id)
     * @return FFTOrg集合对象
     */
	public List<FFTOrg> findFFTOrgReByBossUserIdOrgId(Long userId,Long id);
	
	
	/**
     * 组织Id多集合查询
     * @param orgIds 组织Id集合
     * @return FFTOrg集合对象
     */
	public List<FFTOrg> findFFTOrgByOrgIds(@Param("orgIds") List<String> orgIds);
	
	/**
     * 主键id多集合查询
     * @param ids 主键Id集合
     * @return FFTOrg集合对象
     */
	public List<FFTOrg> findFFTOrgByIds(@Param("ids") List<Long> ids);
	
	
    /**
     * 查询 FFTOrg
     * @param FFTOrg
     * @return List<FFTOrg>    返回结果集
     */
	public List<FFTOrg> findFFTOrg(FFTOrg fftOrg);

	/**
     * 查询 clientId下符合bank的一级组织
     * @param clientIds 客户端Id
     * @return List<FFTOrg>    返回结果集
     */
	public List<FFTOrg> findFFTOrgOneBankByClientIds(@Param("clientIds") List<String> clientIds);
	
	
	/**
     * 查询 FFTOrg 非一级的银行组织
     * @param clientId 客户端Id
     * @return List<FFTOrg>    返回结果集
     */
	public List<FFTOrg> findFFTOrgNotOne(String clientId);
	
	
	/**
     * 分页查询 FFTOrg
     * @param page 
     * @param fftOrg
     * @return List<FFTOrg>    返回分页查询结果集
     */
	public List<FFTOrg> findByPage(Page page, @Param("fftOrg")FFTOrg fftOrg);


}