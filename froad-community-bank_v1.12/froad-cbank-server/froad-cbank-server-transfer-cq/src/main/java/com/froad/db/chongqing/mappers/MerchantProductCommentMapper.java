package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.MerchantProductComment;
import com.froad.cbank.persistent.entity.base.PageEntity;
	/**
	 * mybatis接口
	  * @ClassName: MerchantProductCommentMapper
	  * @Description: TODO
	  * @author wuhelian 2015年1月8日
	  * @modify wuhelian 2015年1月8日
	 */

public interface MerchantProductCommentMapper {

	/**
	 * 
	  * @Title: selectMerchantProductCommentById
	  * @Description: TODO
	  * @author: Yaren Liang 2015年6月30日
	  * @modify: Yaren Liang 2015年6月30日
	  * @param @param id
	  * @param @return    
	  * @return MerchantProductComment    
	  * @throws
	 */
	public MerchantProductComment selectMerchantProductCommentById(Long id);
	
	public List<MerchantProductComment> selectMerchantCommentAll(MerchantProductComment merchantProductComment);

	
}
	
