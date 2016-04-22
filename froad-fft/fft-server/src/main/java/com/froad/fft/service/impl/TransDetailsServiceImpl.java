package com.froad.fft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.TransDetailsMapper;
import com.froad.fft.persistent.entity.TransDetails;
import com.froad.fft.service.TransDetailsService;

@Service("transDetailsServiceImpl")
public class TransDetailsServiceImpl implements TransDetailsService {

	private static Logger logger = Logger.getLogger(TransDetailsServiceImpl.class);
	
	@Resource
	private TransDetailsMapper transDetailsMapper;

	@Override
	public Long saveTransDetails(TransDetails transDetails) {
		return transDetailsMapper.saveTransDetails(transDetails);
	}

	@Override
	public Boolean updateTransDetailsById(TransDetails transDetails) {
		if(transDetails.getId() == null){
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return transDetailsMapper.updateTransDetailsById(transDetails);
	}

	@Override
	public TransDetails selectTransDetailsById(Long id) {
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		return transDetailsMapper.selectTransDetailsById(id);
	}

	

	@Override
	public void saveBatchTransDetails(List<TransDetails> list) {
		transDetailsMapper.saveBatchTransDetails(list);
	}

    public List<TransDetails> findTransDetailsByTransId(Long transId)
    {
        return transDetailsMapper.selectTransDetailsByTransId(transId);
    }
}
