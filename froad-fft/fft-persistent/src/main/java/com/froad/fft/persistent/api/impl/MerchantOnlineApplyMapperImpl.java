package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.MerchantOnlineApplyMapper;
import com.froad.fft.persistent.entity.MerchantOnlineApply;

public class MerchantOnlineApplyMapperImpl implements MerchantOnlineApplyMapper {

	@Resource
	private MerchantOnlineApplyMapper merchantOnlineApplyMapper;
	
	@Override
	public Long saveMerchantOnlineApply(MerchantOnlineApply merchantOnlineApply) {
		merchantOnlineApplyMapper.saveMerchantOnlineApply(merchantOnlineApply);
		return merchantOnlineApply.getId();
	}

	@Override
	public Boolean updateMerchantOnlineApplyById(
			MerchantOnlineApply merchantOnlineApply) {
		return merchantOnlineApplyMapper.updateMerchantOnlineApplyById(merchantOnlineApply);
	}

	@Override
	public MerchantOnlineApply selectMerchantOnlineApplyById(Long id) {
		return merchantOnlineApplyMapper.selectMerchantOnlineApplyById(id);
	}

}
