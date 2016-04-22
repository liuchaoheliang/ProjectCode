package com.froad.fft.persistent.api;

import com.froad.fft.persistent.entity.MerchantPreferential;

public interface MerchantPreferentialMapper {

	public Long saveMerchantPreferential(MerchantPreferential merchantPreferential);
	public Boolean updateMerchantPreferentialById(MerchantPreferential merchantPreferential);
	public MerchantPreferential selectMerchantPreferentialById(Long id);
}
