package com.froad.fft.persistent.api;

import com.froad.fft.persistent.entity.MerchantComment;

public interface MerchantCommentMapper {

	public Long saveMerchantComment(MerchantComment merchantComment);
	public Boolean updateMerchantCommentById(MerchantComment merchantComment);
	public MerchantComment selectMerchantCommentById(Long id);
	
}
