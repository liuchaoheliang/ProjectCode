package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.TransDetailsMapper;
import com.froad.fft.persistent.entity.TransDetails;

public class TransDetailsMapperImpl implements TransDetailsMapper {

	@Resource
	private TransDetailsMapper transDetailsMapper;
	
	@Override
	public Long saveTransDetails(TransDetails transDetail) {
		transDetailsMapper.saveTransDetails(transDetail);
		return transDetail.getId();
	}

	@Override
	public Boolean updateTransDetailsById(TransDetails transDetails) {
		return transDetailsMapper.updateTransDetailsById(transDetails);
	}

	@Override
	public TransDetails selectTransDetailsById(Long id) {
		return transDetailsMapper.selectTransDetailsById(id);
	}

	

	@Override
	public void saveBatchTransDetails(List<TransDetails> list) {
		transDetailsMapper.saveBatchTransDetails(list);
	}

    public List<TransDetails> selectTransDetailsByTransId(Long transId)
    {
        return  transDetailsMapper.selectTransDetailsByTransId(transId);
    }

}
