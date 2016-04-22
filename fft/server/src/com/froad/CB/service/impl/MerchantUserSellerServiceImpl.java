package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.MerchantUserSellerDao;
import com.froad.CB.po.MerchantUserSeller;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-6-3  
 * @version 1.0
 */
@WebService(endpointInterface="com.froad.CB.service.MerchantUserSellerService")
public class MerchantUserSellerServiceImpl implements
		com.froad.CB.service.MerchantUserSellerService {
	private static final Logger logger=Logger.getLogger(MerchantUserSetServiceImpl.class);

	private MerchantUserSellerDao merchantUserSellerDao;
	
	@Override
	public Integer addMerchantUserSeller(MerchantUserSeller merchantUserSeller) {
		if(merchantUserSeller==null){
			logger.info("参数为空，添加失败");
			return null;
		}
		return merchantUserSellerDao.addMerchantUserSeller(merchantUserSeller);
	}

	@Override
	public List<MerchantUserSeller> getMerchantUserSellers(
			MerchantUserSeller merchantUserSeller) {
		if(merchantUserSeller == null){
			logger.info("参数为空，查询失败");
			return null;
		}
		return merchantUserSellerDao.getMerchantUserSellers(merchantUserSeller);
	}

	@Override
	public Integer updateByMerchantSeller(MerchantUserSeller merchantUserSeller) {
		if(merchantUserSeller == null){
			logger.info("参数为空，更新失败");
			return null;
		}
		return merchantUserSellerDao.updateByMerchantSeller(merchantUserSeller);
	}

	@Override
	public Integer deleteByMerchantSeller(MerchantUserSeller merchantUserSeller) {
		if(merchantUserSeller == null){
			logger.info("参数为空，删除失败");
			return null;
		}
        Integer rows = merchantUserSellerDao.deleteByMerchantSeller(merchantUserSeller);
        return rows;
	}

	public MerchantUserSellerDao getMerchantUserSellerDao() {
		return merchantUserSellerDao;
	}

	public void setMerchantUserSellerDao(MerchantUserSellerDao merchantUserSellerDao) {
		this.merchantUserSellerDao = merchantUserSellerDao;
	}
	
	
}
