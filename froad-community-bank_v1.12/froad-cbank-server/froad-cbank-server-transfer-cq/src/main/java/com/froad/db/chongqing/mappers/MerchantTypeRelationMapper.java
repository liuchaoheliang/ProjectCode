package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.MerchantTypeRelation;

/**
 *  mybatis接口
  * @ClassName: MerchantTypeRelationMapper
  * @Description: TODO
  * @author share 2014年12月29日
  * @modify share 2014年12月29日
 */
public interface MerchantTypeRelationMapper {

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
	public Long insert(MerchantTypeRelation merchantTypeRelation);
	
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
	public long delete(MerchantTypeRelation merchantTypeRelation);
	
	
	List<MerchantTypeRelation> findMerchantByMerchantId(Long merchantId);
	
}

