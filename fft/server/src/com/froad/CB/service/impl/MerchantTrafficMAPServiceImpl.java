package com.froad.CB.service.impl;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.merchant.MerchantTrafficMAPDAO;
import com.froad.CB.po.merchant.MerchantTrafficMAP;
import com.froad.CB.service.MerchantTrafficMAPService;

@WebService(endpointInterface="com.froad.CB.service.MerchantTrafficMAPService")
public class MerchantTrafficMAPServiceImpl implements MerchantTrafficMAPService{

	private static final Logger logger=Logger.getLogger(MerchantTrafficMAPServiceImpl.class);
	
	private MerchantTrafficMAPDAO merchantTrafficMAPDAO;
	
	@Override
	public Integer addMerchantTrafficMAP(MerchantTrafficMAP merchantTrafficMAP) {
		if(merchantTrafficMAP==null){
			logger.error("参数为空，添加失败");
			return null;
		}
		return merchantTrafficMAPDAO.insert(merchantTrafficMAP);
	}

	@Override
	public boolean updateMerchantTrafficMAP(MerchantTrafficMAP merchantTrafficMAP) {
		if(merchantTrafficMAP==null){
			logger.error("参数为空，修改失败");
			return false;
		}
		merchantTrafficMAPDAO.updateByPrimaryKeySelective(merchantTrafficMAP);
		return true;
	}
	
	
	public void setMerchantTrafficMAPDAO(MerchantTrafficMAPDAO merchantTrafficMAPDAO) {
		this.merchantTrafficMAPDAO = merchantTrafficMAPDAO;
	}

	@Override
	public MerchantTrafficMAP getMerchantTrafficMapByMerchantId(
			String merchantId) {
		if(merchantId==null||"".equals(merchantId)){
			logger.error("商户编号为空，查询失败");
			return null;
		}
		return merchantTrafficMAPDAO.getByMerchantId(merchantId);
	}

	@Override
	public MerchantTrafficMAP getMerchantTrafficMapByUserId(String userId) {
		if(userId==null||"".equals(userId)){
			logger.error("用户编号为空，查询失败");
			return null;
		}
		return merchantTrafficMAPDAO.getByUserId(userId);
	}


}
