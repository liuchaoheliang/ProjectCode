package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.PayMapper;
import com.froad.fft.persistent.entity.Pay;

public class PayMapperImpl implements PayMapper {

	@Resource
	private PayMapper payMapper;
	
	@Override
	public Long savePay(Pay pay) {
		payMapper.savePay(pay);
		return pay.getId();
	}

	@Override
	public Boolean updatePayById(Pay pay) {
		return payMapper.updatePayById(pay);
	}

	@Override
	public Pay selectPayById(Long id) {
		return payMapper.selectPayById(id);
	}

	@Override
	public void saveBatchPay(List<Pay> payList) {
		payMapper.saveBatchPay(payList);
	}

	
	
	@Override
	public Pay selectPayBySn(String sn) {
		return payMapper.selectPayBySn(sn);
	}

	
	@Override
	public List<Pay> selectPayByTransId(Long transId) {
		return payMapper.selectPayByTransId(transId);
	}

	@Override
	public Pay selectPayByRefundOrderId(String refundOrderId) {
		return payMapper.selectPayByRefundOrderId(refundOrderId);
	}

}
