package com.froad.fft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.PayMapper;
import com.froad.fft.persistent.entity.Pay;
import com.froad.fft.service.PayService;

@Service("payServiceImpl")
public class PayServiceImpl implements PayService {
	
	private static Logger logger = Logger.getLogger(PayServiceImpl.class);
	
	@Resource
	private PayMapper payMapper;

	@Override
	public Long savePay(Pay pay) {
		return payMapper.savePay(pay);
	}

	@Override
	public Boolean updatePayById(Pay pay) {
		return payMapper.updatePayById(pay);
	}

	@Override
	public Pay findPayBySn(String sn) {
		return payMapper.selectPayBySn(sn);
	}

	@Override
	public void saveBatchPay(List<Pay> payList) {
		payMapper.saveBatchPay(payList);
	}

	@Override
	public List<Pay> findPayByTransId(Long transId) {
		return payMapper.selectPayByTransId(transId);
	}

	
}
