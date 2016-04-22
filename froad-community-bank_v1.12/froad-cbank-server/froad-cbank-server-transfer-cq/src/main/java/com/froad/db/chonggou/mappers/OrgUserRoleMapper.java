package com.froad.db.chonggou.mappers;

import java.util.List;

import com.froad.db.chonggou.entity.OrgUserRole;



public interface OrgUserRoleMapper {

	/**
	 * 
	  * @Title: addOrgUserRole
	  * @Description: TODO
	  * @author: Yaren Liang 2015年7月29日
	  * @modify: Yaren Liang 2015年7月29日
	  * @param @param orgUserRole
	  * @param @return    
	  * @return Long    
	  * @throws
	 */
	public Long addOrgUserRole(OrgUserRole orgUserRole);
	
	public void deleteOrgUserRole(String clientId);
	
	public List<OrgUserRole> selectAll();
	
}

