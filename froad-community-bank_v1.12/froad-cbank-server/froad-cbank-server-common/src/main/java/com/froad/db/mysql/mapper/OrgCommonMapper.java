/**
 * 
 * @Title: OrgCommonMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.Org;

/**
 * 
 * <p>@Title: OrgMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface OrgCommonMapper {

	/**
     * 根据clientId和orgCode查出单个Org
     * @param orgCode 机构编号
     * @return Org对象
     */
	public Org findOrgById(Org org);
	
	
	
    /**
     * 查询 Org
     * @param org
     * @return List<Org>    返回结果集
     */
	public List<Org> findOrg(Org org);
	
	/**
	 *  获取机构信息
	  * @Title: queryByMerchantId
	  * @Description: TODO
	  * @author: share 2015年4月1日
	  * @modify: share 2015年4月1日
	  * @param @param org
	  * @param @return    
	  * @return Org    
	  * @throws
	 */
	public Org queryByOrgInfo(Org org);

	
	/**
     * 根据orgCode集合获取机构对象(循环内部处理)
     * @param clientId 客户端编号
     * @param orgCodeList 机构编号集合
     * @return list<OrgVo> 返回机构对象集合
     */
	public List<Org> findOrgByList(@Param("clientId") String clientId,@Param("orgCodeList")List<String> orgCodeList);
	
	/**
	 *  根据父级机构查询子机构
	 * @param clientId
	 * @param orgCode
	 * @return
	 */
	public List<Org> queryByParentOrgCode(@Param("clientId") String clientId,@Param("parentOrgCode")String orgCode);

	
}