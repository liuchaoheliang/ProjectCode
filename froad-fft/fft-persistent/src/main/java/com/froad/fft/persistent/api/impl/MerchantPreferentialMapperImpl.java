package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.MerchantPreferentialMapper;
import com.froad.fft.persistent.entity.MerchantPreferential;

public class MerchantPreferentialMapperImpl implements
		MerchantPreferentialMapper {
	
	@Resource
	private MerchantPreferentialMapper merchantPreferentialMapper;

	@Override
	public Long saveMerchantPreferential(
			MerchantPreferential merchantPreferential) {
		merchantPreferentialMapper.saveMerchantPreferential(merchantPreferential);
		return merchantPreferential.getId();
	}

	@Override
	public Boolean updateMerchantPreferentialById(
			MerchantPreferential merchantPreferential) {
		return merchantPreferentialMapper.updateMerchantPreferentialById(merchantPreferential);
	}

	@Override
	public MerchantPreferential selectMerchantPreferentialById(Long id) {
		return merchantPreferentialMapper.selectMerchantPreferentialById(id);
	}

}
