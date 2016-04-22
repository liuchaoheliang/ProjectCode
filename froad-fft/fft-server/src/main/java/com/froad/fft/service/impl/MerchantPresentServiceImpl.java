package com.froad.fft.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.MerchantPresentMapper;
import com.froad.fft.persistent.entity.MerchantPresent;
import com.froad.fft.service.MerchantPresentService;

@Service("merchantPresentServiceImpl")
public class MerchantPresentServiceImpl implements MerchantPresentService {

	private static Logger logger = Logger.getLogger(MerchantPresentServiceImpl.class);
	
	@Resource
	private MerchantPresentMapper merchantPresentMapper;
	
	@Override
	public Long saveMerchantPresent(MerchantPresent merchantPresent) {
		return merchantPresentMapper.saveMerchantPresent(merchantPresent);
	}

	@Override
	public Boolean updateMerchantPresentById(MerchantPresent merchantPresent) {
		if(merchantPresent.getId() == null){
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return merchantPresentMapper.updateMerchantPresentById(merchantPresent);
	}

	@Override
	public MerchantPresent selectMerchantPresentById(
			Long id) {
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		return merchantPresentMapper.selectMerchantPresentById(id);
	}

}
