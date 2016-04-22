package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.Org;

/**
 * 
 * <p>@Title: OrgLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface OrgLogic {


    /**
     * 增加 Org
     * @param org
     * @return Long    主键ID
     */
	public Long addOrg(Org org)  throws FroadServerException, Exception;



	/**
     * 批量增加 Org
     * @param List<Org>
     * @return Boolean    是否成功
     */
	public Boolean addOrgByBatch(List<Org> orgList) throws FroadServerException, Exception;
	
	
    /**
     * 删除 Org
     * @param org
     * @return Boolean    是否成功
     */
	public Boolean deleteOrg(Org org) throws FroadServerException, Exception;



    /**
     * 修改 Org
     * @param org
     * @return Boolean    是否成功
     */
	public Boolean updateOrg(Org org) throws FroadServerException, Exception;



    /**
     * 查询 Org
     * @param org
     * @return List<Org>    结果集合 
     */
	public List<Org> findOrg(Org org);



    /**
     * 分页查询 Org
     * @param page
     * @param org
     * @param loginOrgCode 当前登录orgCode
     * @return Page    结果集合 
     */
	public Page<Org> findOrgByPage(Page<Org> page, Org org,String loginOrgCode);


	
	/**
     * 根据机构码查出所有子机构
     * @param orgCode 机构编号
     * @return List<Org>    子机构集合
     */
	public List<Org> findSubOrgs(String clientId,String orgCode);


	/**
	 * 查询全部子机构编码，包含当前机构编码
	 * @param orgCode 机构编码
	 * @param clientId 客户端id
	 * @return
	 */
	public List<String> findAllSubOrgCodes(String clientId,String orgCode);
	

	/**
	 * 查询全部子机构对象，包含当前机构编码
	 * @param orgCode 机构编码
	 * @param clientId 客户端id
	 * @return
	 */
	public List<Org> findAllSubOrgs(String clientId,String orgCode);
	
	
	/**
     * 查询市级区级机构(逗号隔开)
     * @param orgCode 机构编号
     * @param clientId 客户端编号
     * @return string 返回市级区级机构编号
     */
	public String findSuperOrgCodeByType(String clientId,String orgCode);
	
	
	/**
     * 查询areaId集合下的有效机构所属的法人行社列表
     * @param clientId 客户端编号
     * @param areaIds  区Id集合
     * @return list<OrgVo> 返回机构对象集合
     */
	public List<Org> findOrgByAreaIds(String clientId, List<Long> areaIds);
	
	
	/**
	 * 获取网点交集集合
	 * @param clientId 客户端id
	 * @param loginOrgCode 登录人所属机构编号
	 * @param filterOrgCode 过滤条件机构编号
	 * @return 二者orgCode下级网点交集集合
	 */
	 public List<String> findIntersectionOrgCodeList(String clientId,String loginOrgCode, String filterOrgCode);
	
	/**
	 * 通过机构信息查询机构列表.
	 * @param org
	 * @param limit
	 * @return
	 */
	public List<Org> findOrgByOrgNameOrClientId(String clientId,String orgName,Integer limit,String loginOrgCode);	
}