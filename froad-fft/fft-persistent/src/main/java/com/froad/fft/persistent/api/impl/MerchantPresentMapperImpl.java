package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.MerchantPresentMapper;
import com.froad.fft.persistent.entity.MerchantPresent;

public class MerchantPresentMapperImpl implements MerchantPresentMapper {

	@Resource
	private MerchantPresentMapper merchantPresentMapper;
	
	@Override
	public Long saveMerchantPresent(MerchantPresent merchantPresent) {
		merchantPresentMapper.saveMerchantPresent(merchantPresent);
		return merchantPresent.getId();
	}

	@Override
	public Boolean updateMerchantPresentById(MerchantPresent merchantPresent) {
		return merchantPresentMapper.updateMerchantPresentById(merchantPresent);
	}

	@Override
	public MerchantPresent selectMerchantPresentById(Long id) {
		return merchantPresentMapper.selectMerchantPresentById(id);
	}

}
