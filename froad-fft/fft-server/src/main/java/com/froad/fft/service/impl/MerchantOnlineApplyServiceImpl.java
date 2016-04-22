package com.froad.fft.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.MerchantOnlineApplyMapper;
import com.froad.fft.persistent.entity.MerchantOnlineApply;
import com.froad.fft.service.MerchantOnlineApplyService;

@Service("merchantOnlineApplyServiceImpl")
public class MerchantOnlineApplyServiceImpl implements
		MerchantOnlineApplyService {

	private static Logger logger = Logger.getLogger(MerchantOnlineApplyService.class);
	
	@Resource
	private MerchantOnlineApplyMapper merchantOnlineApplyMapper;
	
	@Override
	public Long saveMerchantOnlineAppl(MerchantOnlineApply merchantOnlineApply) {
		return merchantOnlineApplyMapper.saveMerchantOnlineApply(merchantOnlineApply);
	}

	@Override
	public Boolean updateMerchantOnlineApplyById(
			MerchantOnlineApply merchantOnlineApply) {
		if(merchantOnlineApply.getId() == null){
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return merchantOnlineApplyMapper.updateMerchantOnlineApplyById(merchantOnlineApply);
	}

	@Override
	public MerchantOnlineApply selectMerchantOnlineApplyById(Long id) {
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		return merchantOnlineApplyMapper.selectMerchantOnlineApplyById(id);
	}

}
