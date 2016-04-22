package com.froad.CB.service.impl;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.merchant.MerchantTrainDAO;
import com.froad.CB.po.merchant.MerchantTrain;
import com.froad.CB.service.MerchantTrainService;

@WebService(endpointInterface="com.froad.CB.service.MerchantTrainService")
public class MerchantTrainServiceImpl implements MerchantTrainService{

	private static final Logger logger=Logger.getLogger(MerchantTrainServiceImpl.class);
	
	private MerchantTrainDAO merchantTrainDAO;
	
	@Override
	public Integer addMerchantTrain(MerchantTrain merchantTrain) {
		if(merchantTrain==null){
			logger.error("参数为空，添加失败");
			return null;
		}
		return merchantTrainDAO.insert(merchantTrain);
	}

	@Override
	public MerchantTrain getMerchantTrainByMerchantId(String merchantId) {
		if(merchantId==null||"".equals(merchantId)){
			logger.error("商户编号为空，查询失败");
			return null;
		}
		return merchantTrainDAO.selectByMerchantId(merchantId);
	}

	@Override
	public MerchantTrain getMerchantTrainByUserId(String userId) {
		if(userId==null||"".equals(userId)){
			logger.error("用户编号为空，查询失败");
			return null;
		}
		return merchantTrainDAO.selectByUserId(userId);
	}

	@Override
	public boolean updateById(MerchantTrain merchantTrain) {
		if(merchantTrain==null||merchantTrain.getId()==null||
				"".equals(merchantTrain.getId())){
			logger.error("参数为空，更新失败");
			return false;
		}
		merchantTrainDAO.updateByPrimaryKeySelective(merchantTrain);
		return true;
	}

	public void setMerchantTrainDAO(MerchantTrainDAO merchantTrainDAO) {
		this.merchantTrainDAO = merchantTrainDAO;
	}

}
