package com.froad.fft.service;

import com.froad.fft.persistent.entity.MerchantOutletTag;

public interface MerchantOutletTagService {

	public Long saveMerchantOutletTag(MerchantOutletTag  merchantOutletTag);
	public Boolean updateMerchantOutletTagById(MerchantOutletTag merchantOutletTag);
	public MerchantOutletTag selectMerchantOutletTagById(Long id);
}
