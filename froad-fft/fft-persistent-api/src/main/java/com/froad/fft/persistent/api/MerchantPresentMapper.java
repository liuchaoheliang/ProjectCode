package com.froad.fft.persistent.api;

import com.froad.fft.persistent.entity.MerchantPresent;

public interface MerchantPresentMapper {

	public Long saveMerchantPresent(MerchantPresent merchantPresent);
	public Boolean updateMerchantPresentById(MerchantPresent merchantPresent);
	public MerchantPresent selectMerchantPresentById(Long id);
}
