package com.froad.fft.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.MerchantPhotoMapper;
import com.froad.fft.persistent.entity.MerchantPhoto;
import com.froad.fft.service.MerchantPhotoService;

@Service("merchantPhotoServiceImpl")
public class MerchantPhotoServiceImpl implements MerchantPhotoService {

	private static Logger logger = Logger.getLogger(MerchantPhotoServiceImpl.class);

	@Resource
	private MerchantPhotoMapper merchantPhotoMapper;
	
	@Override
	public Long saveMerchantPhoto(MerchantPhoto merchantPhoto) {
		return merchantPhotoMapper.saveMerchantPhoto(merchantPhoto);
	}

	@Override
	public Boolean updateMerchantPhotoById(MerchantPhoto merchantPhoto) {
		if(merchantPhoto.getId() == null){
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return merchantPhotoMapper.updateMerchantPhotoById(merchantPhoto);
	}

	@Override
	public MerchantPhoto selectMerchantPhotoById(Long id) {
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		return merchantPhotoMapper.selectMerchantPhotoById(id);
	}

}
