package com.froad.db.ahui.mappers;

import java.util.List;

import com.froad.fft.persistent.entity.MerchantProductComment;

public interface MerchantProductCommentMapper {
	/**
	 * 根据merchant_id查找
	  * @Title: selectMerchantProductCommentById
	  * @Description: TODO
	  * @author: Yaren Liang 2015年5月4日
	  * @modify: Yaren Liang 2015年5月4日
	  * @param @param id
	  * @param @return    
	  * @return MerchantProductComment    
	  * @throws
	 */
	public MerchantProductComment selectMerchantProductCommentById(Long id);
	
	public List<MerchantProductComment> selectMerchantCommentAll(MerchantProductComment merchantProductComment);
	
	

}

