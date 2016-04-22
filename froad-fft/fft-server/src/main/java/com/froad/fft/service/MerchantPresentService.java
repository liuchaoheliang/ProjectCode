package com.froad.fft.service;

import com.froad.fft.persistent.entity.MerchantPresent;

public interface MerchantPresentService {

	public Long saveMerchantPresent(MerchantPresent merchantPresent);
	public Boolean updateMerchantPresentById(MerchantPresent merchantPresent);
	public MerchantPresent selectMerchantPresentById(Long id);
}
