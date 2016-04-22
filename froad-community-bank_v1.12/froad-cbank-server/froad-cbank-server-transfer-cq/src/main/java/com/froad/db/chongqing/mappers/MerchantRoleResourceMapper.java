package com.froad.db.chongqing.mappers;

import com.froad.cbank.persistent.entity.MerchantRoleResource;

/**
 *  mybatis接口
  * @ClassName: MerchantRoleResourceMapper
  * @Description: TODO
  * @author share 2014年12月29日
  * @modify share 2014年12月29日
 */
public interface MerchantRoleResourceMapper {

	/**
	 *  添加
	  * @Title: insert
	  * @Description: TODO
	  * @author: share 2014年12月29日
	  * @modify: share 2014年12月29日
	  * @param @param merchantRoleResource
	  * @param @return    
	  * @return Long    
	  * @throws
	 */
	public Long insert(MerchantRoleResource merchantRoleResource);
	
	/**
	 *   删除
	  * @Title: deleteById
	  * @Description: TODO
	  * @author: share 2014年12月29日
	  * @modify: share 2014年12月29日
	  * @param @param merchantRoleResource
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public long delete(MerchantRoleResource merchantRoleResource);
	
}

