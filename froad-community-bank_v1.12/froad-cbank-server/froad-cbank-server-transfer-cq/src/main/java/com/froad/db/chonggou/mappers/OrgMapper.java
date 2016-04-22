/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: OrgMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.chonggou.mappers;

import java.util.List;

import com.froad.po.Org;




/**
 * 
 * <p>@Title: OrgMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
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
	 * delete by client id
	 * 
	 * @param clientId
	 */
	public void deleteByClientId(String clientId);
}