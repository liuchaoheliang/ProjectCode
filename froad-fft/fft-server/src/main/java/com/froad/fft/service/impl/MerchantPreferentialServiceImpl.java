package com.froad.fft.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.MerchantPreferentialMapper;
import com.froad.fft.persistent.entity.MerchantPreferential;
import com.froad.fft.service.MerchantPreferentialService;

@Service("merchantPreferentialServiceImpl")
public class MerchantPreferentialServiceImpl implements
		MerchantPreferentialService {
	
	private static Logger logger = Logger.getLogger(MerchantPreferentialServiceImpl.class);
	
	@Resource
	private MerchantPreferentialMapper preferentialMapper;			

	@Override
	public Long saveMerchantPreferential(
			MerchantPreferential merchantPreferential) {
		return preferentialMapper.saveMerchantPreferential(merchantPreferential);
	}

	@Override
	public Boolean updateMerchantPreferentialById(
			MerchantPreferential merchantPreferential) {
		if(merchantPreferential.getId() == null){
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return preferentialMapper.updateMerchantPreferentialById(merchantPreferential);
	}

	@Override
	public MerchantPreferential selectMerchantPreferentialById(Long id) {
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		return preferentialMapper.selectMerchantPreferentialById(id);
	}

}
