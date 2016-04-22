package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.MerchantOutletTagMapper;
import com.froad.fft.persistent.entity.MerchantOutletTag;

public class MerchantOutletTagMapperImpl implements MerchantOutletTagMapper {

	@Resource
	private MerchantOutletTagMapper merchantOutletTagMapper;
	
	@Override
	public Long saveMerchantOutletTag(MerchantOutletTag merchantOutletTag) {
		merchantOutletTagMapper.saveMerchantOutletTag(merchantOutletTag);
		return merchantOutletTag.getId();
	}

	@Override
	public Boolean updateMerchantOutletTagById(
			MerchantOutletTag merchantOutletTag) {
		return merchantOutletTagMapper.updateMerchantOutletTagById(merchantOutletTag);
	}

	@Override
	public MerchantOutletTag selectMerchantOutletTagById(Long id) {
		return merchantOutletTagMapper.selectMerchantOutletTagById(id);
	}

}
