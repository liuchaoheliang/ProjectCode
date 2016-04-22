package com.froad.fft.service;

import com.froad.fft.persistent.entity.MerchantOnlineApply;

public interface MerchantOnlineApplyService {

	public Long saveMerchantOnlineAppl(MerchantOnlineApply merchantOnlineApply);
	public Boolean updateMerchantOnlineApplyById(MerchantOnlineApply merchantOnlineApply);
	public MerchantOnlineApply selectMerchantOnlineApplyById(Long id);
}
