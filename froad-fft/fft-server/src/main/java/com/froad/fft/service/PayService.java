package com.froad.fft.service;

import java.util.List;

import com.froad.fft.persistent.entity.Pay;

/**
 * 支付
 * @author FQ
 *
 */
public interface PayService {

	/**
	 * 保存
	 * @param pay
	 * @return
	 */
	public Long savePay(Pay pay);
	
	/**
	 * 根据ID 更新
	 * @param pay
	 * @return
	 */
	public Boolean updatePayById(Pay pay);
	
	
	/**
	 * 根据sn查找
	 * @param sn
	 * @return
	 */
	public Pay findPayBySn(String sn);
	
	
	
	/**
	 * 批量保存
	 * @param payList
	 */
	public void saveBatchPay(List<Pay> payList);
	
	/**
	 * 根据交易ID 查找支付列表
	 * @param transId
	 * @return
	 */
	public List<Pay> findPayByTransId(Long transId);
}
