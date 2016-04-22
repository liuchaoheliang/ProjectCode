package com.froad.fft.persistent.api;

import com.froad.fft.persistent.entity.MerchantOnlineApply;

public interface MerchantOnlineApplyMapper {

	public Long saveMerchantOnlineApply(MerchantOnlineApply merchantOnlineApply);
	public Boolean updateMerchantOnlineApplyById(MerchantOnlineApply merchantOnlineApply);
	public MerchantOnlineApply selectMerchantOnlineApplyById(Long id);
	
}
