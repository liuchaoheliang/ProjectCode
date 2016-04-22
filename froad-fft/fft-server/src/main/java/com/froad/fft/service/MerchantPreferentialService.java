package com.froad.fft.service;

import com.froad.fft.persistent.entity.MerchantPreferential;

public interface MerchantPreferentialService {
	public Long saveMerchantPreferential(MerchantPreferential merchantPreferential);
	public Boolean updateMerchantPreferentialById(MerchantPreferential merchantPreferential);
	public MerchantPreferential selectMerchantPreferentialById(Long id);
}
