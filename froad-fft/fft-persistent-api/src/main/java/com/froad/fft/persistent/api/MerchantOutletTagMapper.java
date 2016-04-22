package com.froad.fft.persistent.api;

import com.froad.fft.persistent.entity.MerchantOutletTag;

public interface MerchantOutletTagMapper {

	public Long saveMerchantOutletTag(MerchantOutletTag merchantOutletTag);
	public Boolean updateMerchantOutletTagById(MerchantOutletTag merchantOutletTag);
	public MerchantOutletTag selectMerchantOutletTagById(Long id);
}
