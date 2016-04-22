package com.froad.fft.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.MerchantOutletTagMapper;
import com.froad.fft.persistent.entity.MerchantOutletTag;
import com.froad.fft.service.MerchantOutletTagService;

//@Service("merchantOutletTagServiceImpl")
public class MerchantOutletTagServiceImpl implements MerchantOutletTagService {

	private static Logger logger = Logger.getLogger(MerchantOutletTagServiceImpl.class);
	
	@Resource
	private MerchantOutletTagMapper merchantOutletTagMapper;
	
	@Override
	public Long saveMerchantOutletTag(MerchantOutletTag merchantOutletTag) {
		return merchantOutletTagMapper.saveMerchantOutletTag(merchantOutletTag);
	}

	@Override
	public Boolean updateMerchantOutletTagById(
			MerchantOutletTag merchantOutletTag) {
		if (merchantOutletTag.getId() == null) {
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return merchantOutletTagMapper.updateMerchantOutletTagById(merchantOutletTag);
	}

	@Override
	public MerchantOutletTag selectMerchantOutletTagById(Long id) {
		if (id == null) {
			logger.error("查询数据id为空");
			return null;
		}
		return merchantOutletTagMapper.selectMerchantOutletTagById(id);
	}

}
