package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.MerchantCommentMapper;
import com.froad.fft.persistent.entity.MerchantComment;

public class MerchantCommentMapperImpl implements MerchantCommentMapper {

	@Resource
	private MerchantCommentMapper merchantCommentMapper;
	
	@Override
	public Long saveMerchantComment(MerchantComment merchantComment) {
		merchantCommentMapper.saveMerchantComment(merchantComment);
		return merchantComment.getId();
	}

	@Override
	public Boolean updateMerchantCommentById(MerchantComment merchantComment) {
		return merchantCommentMapper.updateMerchantCommentById(merchantComment);
	}

	@Override
	public MerchantComment selectMerchantCommentById(Long id) {
		return merchantCommentMapper.selectMerchantCommentById(id);
	}

}
