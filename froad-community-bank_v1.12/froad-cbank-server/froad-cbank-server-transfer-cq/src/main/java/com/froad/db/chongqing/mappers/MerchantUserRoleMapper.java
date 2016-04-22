package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.MerchantUserRole;

/**
 *  mybatis接口
  * @ClassName: MerchantTypeRelationMapper
  * @Description: TODO
  * @author share 2014年12月29日
  * @modify share 2014年12月29日
 */
public interface MerchantUserRoleMapper {

	/**
	 *  添加
	  * @Title: insert
	  * @Description: TODO
	  * @author: share 2014年12月29日
	  * @modify: share 2014年12月29日
	  * @param @param merchantTypeRelation
	  * @param @return    
	  * @return Long    
	  * @throws
	 */
	public Long insert(MerchantUserRole merchantUserRole);
	
	/**
	 *   删除
	  * @Title: deleteById
	  * @Description: TODO
	  * @author: share 2014年12月29日
	  * @modify: share 2014年12月29日
	  * @param @param merchantTypeRelation
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public long delete(MerchantUserRole merchantUserRole);
	
	
	public List<MerchantUserRole> findMerchantUserRoleAll();
	
}

