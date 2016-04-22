package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Org;

/**
 * 
 * <p>@Title: OrgMapper.java</p>
 * <p>Description: 描述 </p> 机构信息管理Mapper
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface OrgMapper {


    /**
     * 增加 Org
     * @param org
     * @return Long    主键ID
     */
	public Long addOrg(Org org);



    /**
     * 批量增加 Org
     * @param List<Org>
     * @return Boolean    是否成功
     */
	public Boolean addOrgByBatch(List<Org> orgList);



    /**
     * 删除 Org
     * @param org
     * @return Boolean    是否成功
     */
	public Boolean deleteOrg(Org org);



    /**
     * 修改 Org
     * @param org
     * @return Boolean    是否成功
     */
	public Boolean updateOrg(Org org);


	/**
     * 根据clientId和orgCode查出单个Org
     * @param orgCode 机构编号
     * @return Org对象
     */
	public Org findOrgById(Org org);
	
	
	/**
     * 根据orgCode获取下级叶子机构 
     * @param orgCode 机构编号
     * @return Org对象
     */
	public List<Org> findSubOrgs(Org org);
	
	
	
	

	/**
     * 根据orgCode获取下级所有机构
     * @param orgCode 机构编号
     * @return Org对象
     */
	public List<Org> findAllSubOrgCodes(Org org);
	
	
	
    /**
     * 查询 Org
     * @param org
     * @return List<Org>    返回结果集
     */
	public List<Org> findOrg(Org org);


    /**
     * 分页查询 Org
     * @param page 
     * @param org
     * @param loginOrgCode 当前登录orgCode
     * @return List<Org>    返回分页查询结果集
     */
	public List<Org> findByPage(Page page, @Param("org")Org org,@Param("loginOrgCode") String loginOrgCode);

	
	 /**
     * 查询 Org
     * @param org
     * @return List<Org>    返回结果集
     */
	public List<Org> findOrgByAreaIds(@Param("org") Org filterOrg,@Param("areaIdList") List<Long> areaIds);

	
	/**
     * 获取网点交集集合
     * @param org
     * @param loginOrgCode 当前登录orgCode
     * @return List<Org>    返回orgCode网点交集合集
     */
	public List<Org> findIntersectionOrgCodeList(@Param("org")Org org,@Param("loginOrgCode") String loginOrgCode);
	
	/**
	 * 根据机构名称模糊查询.
	 * @param clientId
	 * @param orgName
	 * @param limit
	 * @param loginOrgCode
	 * @return
	 */
	public List<Org> findOrgByOrgNameOrClientId(@Param("clientId") String clientId,@Param("orgName") String orgName,@Param("limit") Integer limit,@Param("loginOrgCode") String loginOrgCode);
}