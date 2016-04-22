package com.froad.fft.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.MerchantCommentMapper;
import com.froad.fft.persistent.entity.MerchantComment;
import com.froad.fft.service.MerchantCommentService;

@Service("merchantCommentServiceImpl")
public class MerchantCommentServiceImpl implements MerchantCommentService {

	private static Logger logger = Logger.getLogger(MerchantCommentServiceImpl.class);
	
	@Resource
	private MerchantCommentMapper merchantCommentMapper;
	
	@Override
	public Long saveMerchantComment(MerchantComment merchantComment) {
		return merchantCommentMapper.saveMerchantComment(merchantComment);
	}

	@Override
	public Boolean updateMerchantCommentById(MerchantComment merchantComment) {
		if(merchantComment.getId() == null){
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return merchantCommentMapper.updateMerchantCommentById(merchantComment);
	}

	@Override
	public MerchantComment selectMerchantCommentById(Long id) {
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		return merchantCommentMapper.selectMerchantCommentById(id);
	}

	
}
