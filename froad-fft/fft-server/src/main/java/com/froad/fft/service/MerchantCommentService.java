package com.froad.fft.service;

import com.froad.fft.persistent.entity.MerchantComment;

public interface MerchantCommentService {

	public Long saveMerchantComment(MerchantComment merchantComment);
	
	public Boolean updateMerchantCommentById(MerchantComment merchantComment);
	
	public MerchantComment selectMerchantCommentById(Long id);
}
