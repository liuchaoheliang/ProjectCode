package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.MerchantPhotoMapper;
import com.froad.fft.persistent.entity.MerchantPhoto;

public class MerchantPhotoMapperImpl implements MerchantPhotoMapper {

	@Resource
	private MerchantPhotoMapper merchantPhotoMapper;
	
	@Override
	public Long saveMerchantPhoto(MerchantPhoto merchantPhoto) {
		merchantPhotoMapper.saveMerchantPhoto(merchantPhoto);
		return merchantPhoto.getId();
	}

	@Override
	public Boolean updateMerchantPhotoById(MerchantPhoto merchantPhoto) {
		
		return merchantPhotoMapper.updateMerchantPhotoById(merchantPhoto);
	}

	@Override
	public MerchantPhoto selectMerchantPhotoById(Long id) {
		return merchantPhotoMapper.selectMerchantPhotoById(id);
	}

}
